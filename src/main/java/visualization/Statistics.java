package visualization;

import animal.Animal;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import map.Field;
import simulation.Engine;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

public class Statistics extends GridPane {
  private final Engine engine;
  private DNAStatistics dnaStatistics;
  private Double predatorAvgEnergy = 0.0;
  private Double preyAvgEnergy = 0.0;
  private Double predatorAvgNumberOfKids = 0.0;
  private Double preyAvgNumberOfKids = 0.0;

  public Statistics(Engine engine, DNAStatistics dnaStatistics)
  {
    this.engine = engine;
    this.dnaStatistics = dnaStatistics;
  }

  public void updateStatistics(Animal trackedAnimal)
  {
    DecimalFormat df = new DecimalFormat();
    df.setMaximumFractionDigits(2);
    this.getChildren().clear();
    aliveAnimalsOperations();
    dnaStatistics.update();
    int i = -1;
    this.add(new Text("Day: " + engine.getDay()), 0, ++i);
    this.add(new Text("Number of Plants: " + numberOfPlants()), 0, ++i);
    this.add(new Text("Number of Animals: " + numberOfAnimals()), 0, ++i);

    this.add(new Text("------------------------------"), 0, ++i);

    this.add(new Text("Number of Alive Predators: " +  engine.getAlivePredators().size()), 0, ++i);
    this.add(new Text("Average Predator Energy: " + df.format(predatorAvgEnergy)), 0, ++i);
    this.add(new Text("Dominant Predator DNA:"), 0 , ++i);
    this.add(new Text(dnaStatistics.getDominatingPredatorDNA()), 0 , ++i);
    this.add(new Text("Animals with dominant DNA: " + dnaStatistics.getPredatorCounter()), 0 , ++i);
    this.add(new Text("Avg Predator children: " + df.format(predatorAvgNumberOfKids)), 0 , ++i);
    this.add(new Text("Avg Predator life length: " + df.format(averageLifeLength(engine.getDeadPredators()))), 0 , ++i);
    this.add(new Text("Average Predator speed: " + df.format(engine.getAveragePredatorSpeed())), 0, ++i);
    this.add(new Text("Average Predator energy multiplier: " + df.format(engine.getAveragePredatorEnergyMultiplier())), 0, ++i);

    this.add(new Text("------------------------------"), 0, ++i);

    this.add(new Text("Number of Alive Preys: " + engine.getAlivePreys().size()), 0, ++i);
    this.add(new Text("Average Prey Energy: " + df.format(preyAvgEnergy)), 0, ++i);
    this.add(new Text("Dominant Prey DNA:"), 0 , ++i);
    this.add(new Text(dnaStatistics.getDominatingPreyDNA()), 0 , ++i);
    this.add(new Text("Animals with dominant DNA: " + dnaStatistics.getPreyCounter()), 0 , ++i);
    this.add(new Text("Avg Prey children: " + df.format(preyAvgNumberOfKids)), 0 , ++i);
    this.add(new Text("Avg Prey life length: " + df.format(averageLifeLength(engine.getDeadPreys()))), 0 , ++i);
    this.add(new Text("Average Prey speed: " + df.format(engine.getAveragePreySpeed())), 0, ++i);
    this.add(new Text("Average Prey energy multiplier: " + df.format(engine.getAveragePreyEnergyMultiplier())), 0, ++i);
    this.add(new Text(""), 0 , ++i);

    if(trackedAnimal != null)
    {
      this.add(new Text("Animal Status: " + selectedAnimalStatus(trackedAnimal)), 0, ++i);
      this.add(new Text("Animal Energy: " + df.format(trackedAnimal.getEnergy())), 0, ++i);
      this.add(new Text("Animal Genome:"), 0, ++i);
      this.add(new Text(trackedAnimal.getDNA().toString()), 0, ++i);
      this.add(new Text("Animal children: " + trackedAnimal.getNumberOfChildren()), 0, ++i);
      this.add(new Text("Animal speed: " + df.format(trackedAnimal.getSpeed())), 0, ++i);
      this.add(new Text("Animal energy multiplier: " + df.format(trackedAnimal.getDNA().getEnergyConsumption())), 0, ++i);
      this.add(new Text("Day of birth: " + df.format(trackedAnimal.getDayOfBirth())), 0, ++i);
    }
  }

  private int numberOfAnimals()
  {
    return engine.getAlivePredators().size() + engine.getDeadPredators().size() + engine.getAlivePreys().size() + engine.getDeadPreys().size();
  }

  private String selectedAnimalStatus(Animal animal)
  {
    if(animal.isDead())
      return "Dead " + animal.getDayOfDeath();
    return "Alive";
  }

  private int numberOfPlants() {
    int counter = 0;
    Collection<Field> colOfFields = engine.getFields().values();
    for (Field current : colOfFields) {
      if (current.getContainsGrass()) {
        counter++;
      }
    }
    return counter;
  }

  private void aliveAnimalsOperations() {
    this.predatorAvgEnergy = getAvgEnergy(engine.getAlivePredators());
    this.preyAvgEnergy = getAvgEnergy(engine.getAlivePreys());
    this.predatorAvgNumberOfKids = getAvgNumberOfKids(engine.getAlivePredators());
    this.preyAvgNumberOfKids = getAvgNumberOfKids(engine.getAlivePreys());
  }

  private double getAvgNumberOfKids(ArrayList<Animal> list) {
    double sumOfKids = 0.0;
    for (Animal animal : list) {
      sumOfKids += animal.getNumberOfChildren();
    }
    return sumOfKids/list.size();
  }

  private double getAvgEnergy(ArrayList<Animal> list) {
    double energySum = 0.0;
    for (Animal animal : list) {
      energySum += animal.getEnergy();
    }
    return energySum/list.size();
  }

  private double round(double sum, int number)
  {
    return (double)((int)(sum/number*100))/100;
  }

  private double averageLifeLength(ArrayList<Animal> list) {
    double counter = 0;
    for(Animal animal:list)
    {
      counter += (animal.getDayOfDeath() - animal.getDayOfBirth());
    }
    return round(counter, list.size());
  }
}
