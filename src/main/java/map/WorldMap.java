package map;

import animal.Animal;
import animal.AnimalImpl;
import animal.Prey;
import observer.IFieldAvailabilityObserver;
import observer.IFieldAvailabilityPublisher;
import observer.IPositionChangeObserver;
import vector2d.Vector2d;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WorldMap implements IPositionChangeObserver, IFieldAvailabilityPublisher {
  private final Map<Vector2d, Field> fields = new LinkedHashMap<>();
  private final ArrayList<Animal> alivePredators = new ArrayList<>();
  private final ArrayList<Animal> alivePreys = new ArrayList<>();
  private final ArrayList<Animal> deadPredators = new ArrayList<>();
  private final ArrayList<Animal> deadPreys = new ArrayList<>();
  private final Vector2d leftBottomCorner = new Vector2d(0,0);
  private final Vector2d rightTopCorner;
  private final GrassSectors sectors;
  private final LinkedList<IFieldAvailabilityObserver> subscribers = new LinkedList<>();

  public WorldMap(int width, int height) {
    rightTopCorner = new Vector2d(width-1, height-1);
    sectors = new GrassSectors(leftBottomCorner, rightTopCorner, this);
  }

  public ArrayList<Animal> getDeadPredators() {
    return deadPredators;
  }

  public ArrayList<Animal> getDeadPreys() {
    return deadPreys;
  }

  public ArrayList<Animal> getAlivePredators() {
    return alivePredators;
  }

  public ArrayList<Animal> getAlivePreys() {
    return alivePreys;
  }

  public Map<Vector2d, Field> getFields()
  {
    return fields;
  }

  public void moveAllAnimals() {
    for(Animal animal:alivePreys)
      moveAnimal(animal, false);
    for(Animal animal:alivePredators)
      moveAnimal(animal, true);
  }

  private void moveAnimal(Animal animal, boolean isPredator) {
    animal.move(getEnvironment(animal, isPredator));
  }

  public void takeEnergyFromAnimals(int day, double energy) {
    for(Animal animal:alivePreys)
      animal.takeEnergy(energy, day);
    for(Animal animal:alivePredators)
      animal.takeEnergy(energy, day);
  }

  public void plantGrass(int n)
  {
    sectors.plantGrass(fields, n);
  }

  public void makeAnimalsEatGrass(int aEnergy) {
    Collection<Field> colOfFields = fields.values();
    for (Field current : colOfFields) {
      if (current.containsPreys()) {
        current.eatGrass(aEnergy);
      }
    }
  }

  public void mateAnimals(int day) {
    //MatingAssistant matingOperator = new MatingAssistant(fields, day, rightTopCorner, this);
    //matingOperator.startMating();
    fields.values().forEach(field -> field.mate(day, field.getPosition()));
  }

  public void cleanDeadAnimals() {
    deleteAnimals(alivePreys, deadPreys);
    deleteAnimals(alivePredators, deadPredators);
  }

  public void place(Animal animal) {
    Vector2d position = animal.getPosition();
    if(animal instanceof Prey) {
      alivePreys.add(animal);
    } else {
      alivePredators.add(animal);
    }
    animal.addObserver(this);
    if (!fields.containsKey(position)) {
      fields.put(position, new Field(position));
      makePositionUnavailable(position);
    }
    fields.get(position).insertNewAnimal(animal);
  }

  public Vector2d adjustPosition(Vector2d position) {
    int x = position.x;
    int y = position.y;
    if(position.x > rightTopCorner.x)
      x = x - rightTopCorner.x - 1;
    if(position.x < leftBottomCorner.x)
      x = x + rightTopCorner.x + 1;
    if(position.y < leftBottomCorner.y)
      y = y + rightTopCorner.y + 1;
    if(position.y > rightTopCorner.y)
      y = y - rightTopCorner.y-1;
    return new Vector2d(x,y);
  }

  @Override
  public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
    fields.get(oldPosition).deleteAnimal(animal);
    if(fields.get(oldPosition).isEmpty()) {
      fields.remove(oldPosition);
      newFreePosition(oldPosition);
    }
    if (!fields.containsKey(newPosition)) {
      fields.put(newPosition, new Field(newPosition));
      makePositionUnavailable(newPosition);
    }
    fields.get(newPosition).insertNewAnimal(animal);
  }

  public void updateKnowledge() {
    for (Animal predator : alivePredators) {
      updateKnowledge(predator, true);
    }
    for (Animal prey : alivePreys) {
      updateKnowledge(prey, false);
    }
  }

  public void updateKnowledge(Animal animal, boolean isPredator) {
    animal.updateKnowledge(getEnvironment(animal, isPredator));
  }

  private Environment getEnvironment(Animal animal, boolean isPredator) {
    var animalPurview = animal.getPurview();
    var environmentFields = fields.values().stream()
            .filter(field -> animalPurview.contains(field.getPosition()))
            .toList();
    return new Environment(environmentFields, animal, isPredator);
  }

  @Override
  public void addObserver(IFieldAvailabilityObserver observer) {
    subscribers.add(observer);
  }

  @Override
  public void removeObserver(IFieldAvailabilityObserver observer) {
    subscribers.remove(observer);
  }

  public void hunt(int day) {
    fields.values().forEach(field -> field.hunt(day));
  }

  private void deleteAnimals(ArrayList<Animal> alivePredators, ArrayList<Animal> deadPredators) {
    List<Animal> list = alivePredators.stream().filter(Animal::isDead).toList();
    list.forEach(animal -> {
      alivePredators.remove(animal);
      Field field = fields.get(animal.getPosition());
      field.deleteAnimal(animal);
      if(field.isEmpty()) {
        fields.remove(animal.getPosition());
        newFreePosition(animal.getPosition());
      }
      deadPredators.add(animal);
    });
  }

  private void newFreePosition(Vector2d position) {
    for(IFieldAvailabilityObserver observer:subscribers) {
      observer.setPositionAsAvailable(position);
    }
  }

  private void makePositionUnavailable(Vector2d position) {
    for(IFieldAvailabilityObserver observer:subscribers) {
      observer.setPositionAsUnavailable(position);
    }
  }

  public void saveData(FileOutputStream file, int day) {
    int grassNumber = (int) fields.values().stream().filter(Field::getContainsGrass).count();
    String dayData = day + ", " + grassNumber + ", " + alivePreys.size() + ", " + alivePredators.size() + "\n";
    try {
      file.write(dayData.getBytes(StandardCharsets.UTF_8));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void endDay() {
    alivePredators.forEach(Animal::resetDayReport);
    alivePreys.forEach(Animal::resetDayReport);
  }
}
