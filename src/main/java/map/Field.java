package map;

import animal.Animal;
import animal.Predator;
import animal.Prey;
import vector2d.Vector2d;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Field {
    private static double KILLED_PREY_ENERGY_MULTIPLIER = 2;
    private static double HUNT_SUCCESS_RATE = 2;
    private static boolean PREDATORS_ARE_SHARING_PREY = false;
    private final Vector2d position;
    private final ArrayList<Predator> predators = new ArrayList<>();
    private final ArrayList<Prey> preys = new ArrayList<>();
    private boolean containsGrass = false;
    public Field(Vector2d position) {
        this.position = position;
    }
  
    public void plantGrass() {
        containsGrass = true;
    }
  
    public Vector2d getPosition() {
        return position;
    }
  
    public boolean isEmpty() {
        return predators.size() == 0 && preys.size() == 0 && !containsGrass;
    }
  
    public boolean containsAnimals() {
        return predators.size() != 0 || preys.size() != 0;
    }
  
    public boolean containsPreys() {
        return preys.size() != 0;
    }
  
    public boolean getContainsGrass() {
        return containsGrass;
    }
  
    public void eatGrass(double aEnergy) {
        if(containsGrass) {
            for (Prey prey : preys) {
                prey.eatGrass(aEnergy/preys.size());
            }
        }
        containsGrass = false;
    }
  
    public void mate(int day, Vector2d position) {
        if(preys.size() > 1) {
            List<Prey> preys_sorted = getSortedPreys();
            if(preys_sorted.get(preys.size() - 2).canMate() && preys_sorted.get(preys.size() - 1).canMate()) {
                new Prey(preys_sorted.get(preys.size() - 2), preys_sorted.get(preys.size() - 1), day, position);
            }
        }
        if(predators.size() > 1) {
            List<Predator> predators_sorted = getSortedPredators();
            if(predators_sorted.get(predators.size() - 2).canMate() && predators_sorted.get(predators.size() - 1).canMate()) {
                new Predator(predators_sorted.get(predators.size() - 2), predators_sorted.get(predators.size() - 1), day, position);
            }
        }
    }
  
    public void insertNewAnimal(Animal animal)
    {
        if(animal instanceof Prey) {
            insertAnimalIntoPreysTable((Prey) animal);
        }else{
            insertAnimalIntoPredatorTable((Predator) animal);
        }
    }
  
    private void insertAnimalIntoPredatorTable(Predator animal) {
        predators.add(animal);
    }
  
    private void insertAnimalIntoPreysTable(Prey animal) {
        preys.add(animal);
    }
  
    public Animal getStrongestAnimal() {
        if(preys.size() == 0) {
            return getSortedPredators().get(predators.size() - 1);
        }
        if(predators.size() == 0) {
            return getSortedPreys().get(preys.size() - 1);
        }
        Predator bestPredator = getSortedPredators().get(predators.size() - 1);
        Prey bestPrey = getSortedPreys().get(preys.size() - 1);
        if(bestPredator.getEnergy() > bestPrey.getEnergy())
            return bestPredator;
        return bestPrey;
    }
  
    private List<Predator> getSortedPredators() {
        return predators.stream().sorted(Comparator.comparingDouble(Predator::getEnergy)).toList();
    }
  
    private List<Prey> getSortedPreys() {
        return preys.stream().sorted(Comparator.comparingDouble(Prey::getEnergy)).toList();
    }

    private List<Prey> getSortedPreysBySpeed() {
        return preys.stream().sorted(Comparator.comparingDouble(Prey::getSpeed)).toList();
    }

    private List<Predator> getSortedPredatorsBySpeed() {
        return predators.stream().sorted(Comparator.comparingDouble(Predator::getSpeed)).toList();
    }

    public void deleteAnimal(Animal animal)
    {
        if(animal instanceof Prey) {
            preys.remove((Prey) animal);
        }else{
            predators.remove((Predator) animal);
        }
    }

    public static void setHuntSuccessRate(double huntSuccessRate) {
        HUNT_SUCCESS_RATE = huntSuccessRate;
    }

    public static void setKilledPreyEnergyMultiplier(double killedPreyEnergyMultiplier) {
        KILLED_PREY_ENERGY_MULTIPLIER = killedPreyEnergyMultiplier;
    }

    public static void setPredatorsAreSharingPrey(boolean predatorsAreSharingPrey) {
        PREDATORS_ARE_SHARING_PREY = predatorsAreSharingPrey;
    }

    public void hunt(int day) {
        if(preys.size() > 0  && predators.size() > 0) {
            List<Prey> preyList = getSortedPreysBySpeed();
            List<Predator> predatorsList = getSortedPredatorsBySpeed();
            if(PREDATORS_ARE_SHARING_PREY) {
                shareFoodSystem(day, preyList, predatorsList);
            } else {
                eachPredatorEatsWhatItHunts(day, preyList, predatorsList);
            }
        }
    }

    private void shareFoodSystem(int day, List<Prey> preyList, List<Predator> predatorsList) {
        var ref = new Object() {
            double energyFromKilledPreys = 0.0;
        };
        for(int i = 0; i < Math.min(preys.size(), predators.size()); i++) {
            double predatorSpeed = predatorsList.get(predators.size() - 1 - i).getSpeed();
            Prey prey = preyList.get(i);
            if(predatorSpeed > prey.getSpeed()) {
                if(Math.random() < (predatorSpeed - prey.getSpeed())*HUNT_SUCCESS_RATE) {
                    ref.energyFromKilledPreys += prey.kill(day) * KILLED_PREY_ENERGY_MULTIPLIER;
                }
            }
        }
        predators.forEach(predator -> predator.eatPrey(ref.energyFromKilledPreys /predators.size()));
    }

    private void eachPredatorEatsWhatItHunts(int day, List<Prey> preyList, List<Predator> predatorsList) {
        for(int i = 0; i < Math.min(preys.size(), predators.size()); i++) {
            Predator predator = predatorsList.get(predators.size() - 1 - i);
            Prey prey = preyList.get(i);
            if(predator.getSpeed() > prey.getSpeed()) {
                if(Math.random() < (predator.getSpeed() - prey.getSpeed())*HUNT_SUCCESS_RATE) {
                    predator.eatPrey(prey.kill(day)*KILLED_PREY_ENERGY_MULTIPLIER);
                }
            }
        }
    }
}
  