package simulation;

import animal.Animal;
import animal.Predator;
import animal.PredatorDNA;
import animal.Prey;
import animal.PreyDNA;
import map.Field;
import map.WorldMap;
import utilities.FileParser;
import vector2d.Vector2d;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map;

public class Engine {
  private int day = 0;
  private final WorldMap map;
  private final int dayCost;
  private final int grassGain;
  private final int newGrass;
  private FileOutputStream outputStream;

  public Engine(FileParser fileParser)
  {
    String FILENAME = fileParser.resultFilename;
    String root = System.getProperty("user.dir");
    String filePath = root+ File.separator+"src"+File.separator+"main" +File.separator+"resources" + File.separator + FILENAME;
    try {
      this.outputStream = new FileOutputStream(filePath);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    Field.setHuntSuccessRate(fileParser.huntSuccessRate);
    Field.setKilledPreyEnergyMultiplier(fileParser.killedPreyEnergyMultiplier);
    Field.setPredatorsAreSharingPrey(fileParser.predatorsAreSharingPrey);
    PreyDNA.setMutationChance(fileParser.preyMutationChance);
    PreyDNA.setEnergyMultipliers(fileParser.preyEnergyMultiplier1, fileParser.preyEnergyMultiplier2, fileParser.preyEnergyMultiplier3);
    PreyDNA.setSpeedMultipliers(fileParser.preySpeedMultiplier1, fileParser.preySpeedMultiplier2, fileParser.preyEnergyMultiplier3);
    PredatorDNA.setMutationChance(fileParser.predatorMutationChance);
    PredatorDNA.setEnergyMultipliers(fileParser.predatorEnergyMultiplier1, fileParser.predatorEnergyMultiplier2, fileParser.predatorEnergyMultiplier3);
    PredatorDNA.setSpeedMultipliers(fileParser.predatorSpeedMultiplier1, fileParser.predatorSpeedMultiplier2, fileParser.predatorSpeedMultiplier3);
    newGrass = fileParser.dailyGrassSpawn;
    Predator.setMinimalMatingEnergy(fileParser.startPredatorsEnergy*0.07);
    Prey.setMinimalMatingEnergy(fileParser.startPreysEnergy*0.07);
    map = new WorldMap(fileParser.width, fileParser.height);
    this.grassGain = fileParser.grassIncome;
    this.dayCost = fileParser.dayCost;
    placeNAnimalOnMap(fileParser.numberOfPredators, fileParser.numberOfPreys, fileParser.startPredatorsEnergy,
                      fileParser.startPreysEnergy, fileParser.width, fileParser.height);
    map.plantGrass(fileParser.numberOfGrass);
  }

  public Map<Vector2d, Field> getFields()
  {
    return map.getFields();
  }

  public int getDay()
  {
    return day;
  }

  public ArrayList<Animal> getDeadPredators() {
    return map.getDeadPredators();
  }

  public ArrayList<Animal> getDeadPreys() {
    return map.getDeadPreys();
  }

  public ArrayList<Animal> getAlivePredators() {
    return map.getAlivePredators();
  }

  public ArrayList<Animal> getAlivePreys() {
    return map.getAlivePreys();
  }

  public void nextDay()
  {
    map.cleanDeadAnimals();
    map.moveAllAnimals();
    map.makeAnimalsToEatGrass(grassGain);
    map.hunt(day);
    map.takeEnergyFromAnimals(day, dayCost);
    map.mateAnimals(day);
    map.updateKnowledge();
    map.plantGrass(newGrass);
    map.saveData(outputStream, day);
    day++;
  }

  private void placeNAnimalOnMap(int n_predators, int n_preys, double startPredatorEnergy, double startPreyEnergy, int width, int height)
  {
    Predator.setInitialEnergy(startPredatorEnergy);
    Prey.setInitialEnergy(startPreyEnergy);
    ArrayList<Vector2d> list = new ArrayList<>();
    for(int i =0; i<width;i++) {
      for(int j =0; j<height; j++) {
        list.add(new Vector2d(i,j));
      }
    }
    int i = 0;
    while(list.size() > 0 && i < n_preys) {
      int pos = (int)(Math.random() * list.size());
      new Prey(map, list.get(pos));
      list.remove(pos);
      i++;
    }
    i = 0;
    while(list.size() > 0 && i < n_predators) {
      int pos = (int)(Math.random() * list.size());
      new Predator(map, list.get(pos));
      list.remove(pos);
      i++;
    }
  }

  public double getAveragePredatorSpeed() {
    return getAlivePredators().stream().mapToDouble(predator -> predator.getDNA().getSpeed()).average().orElse(0.0);
  }

  public double getAveragePredatorEnergyMultiplier() {
    return getAlivePredators().stream().mapToDouble(predator -> predator.getDNA().getEnergyConsumption()).average().orElse(0.0);
  }

  public double getAveragePreySpeed() {
    return getAlivePreys().stream().mapToDouble(prey -> prey.getDNA().getSpeed()).average().orElse(0.0);
  }

  public double getAveragePreyEnergyMultiplier() {
    return getAlivePreys().stream().mapToDouble(prey -> prey.getDNA().getEnergyConsumption()).average().orElse(0.0);
  }
}
