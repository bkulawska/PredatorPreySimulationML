package utilities;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;

public class FileParser {
  public int width;
  public int height;
  public int numberOfPredators;
  public int numberOfPreys;
  public int numberOfGrass;
  public int dayCost;
  public int grassIncome;
  public int dailyGrassSpawn;
  public double startPredatorsEnergy;
  public double startPreysEnergy;
  public int numberOfSimulations;
  public double preyMutationChance;
  public double predatorMutationChance;
  public double preyEnergyMultiplier1;
  public double preyEnergyMultiplier2;
  public double preyEnergyMultiplier3;
  public double preySpeedMultiplier1;
  public double preySpeedMultiplier2;
  public double preySpeedMultiplier3;
  public double predatorEnergyMultiplier1;
  public double predatorEnergyMultiplier2;
  public double predatorEnergyMultiplier3;
  public double predatorSpeedMultiplier1;
  public double predatorSpeedMultiplier2;
  public double predatorSpeedMultiplier3;
  public String resultFilename;
  public boolean predatorsAreSharingPrey;
  public double huntSuccessRate;
  public double killedPreyEnergyMultiplier;

  public FileParser() throws Exception
  {
    String root = System.getProperty("user.dir");
    String FileName="source.json";
    String filePath = root+ File.separator+"src"+File.separator+"main" +File.separator+"resources" + File.separator + FileName;
    JSONParser jsonParser = new JSONParser();
    Object object = jsonParser.parse(new FileReader(filePath));
    JSONObject file = (JSONObject) object;
    width = ((Long) file.get("width")).intValue();
    height = ((Long) file.get("height")).intValue();
    numberOfPredators = ((Long) file.get("numberOfPredators")).intValue();
    dailyGrassSpawn = ((Long) file.get("dailyGrassSpawn")).intValue();
    numberOfPreys = ((Long) file.get("numberOfPreys")).intValue();
    numberOfGrass = ((Long) file.get("numberOfGrass")).intValue();
    dayCost = ((Long) file.get("dayCost")).intValue();
    grassIncome = ((Long) file.get("energyFromGrass")).intValue();
    startPredatorsEnergy = (Double) file.get("startPredatorEnergy");
    preyMutationChance = (Double) file.get("preyMutationChance");
    predatorMutationChance = (Double) file.get("predatorMutationChance");
    startPreysEnergy = (Double) file.get("startPreyEnergy");
    preyEnergyMultiplier1 = (Double) file.get("preyEnergyMultiplier1");
    preyEnergyMultiplier2 = (Double) file.get("preyEnergyMultiplier2");
    preyEnergyMultiplier3 = (Double) file.get("preyEnergyMultiplier3");
    preySpeedMultiplier1 = (Double) file.get("preySpeedMultiplier1");
    preySpeedMultiplier2 = (Double) file.get("preySpeedMultiplier2");
    preySpeedMultiplier3 = (Double) file.get("preySpeedMultiplier3");
    predatorEnergyMultiplier1 = (Double) file.get("predatorEnergyMultiplier1");
    predatorEnergyMultiplier2 = (Double) file.get("predatorEnergyMultiplier2");
    predatorEnergyMultiplier3 = (Double) file.get("predatorEnergyMultiplier3");
    predatorSpeedMultiplier1 = (Double) file.get("predatorSpeedMultiplier1");
    predatorSpeedMultiplier2 = (Double) file.get("predatorSpeedMultiplier2");
    predatorSpeedMultiplier3 = (Double) file.get("predatorSpeedMultiplier3");
    killedPreyEnergyMultiplier = (Double) file.get("killedPreyEnergyMultiplier");
    huntSuccessRate = (Double) file.get("huntSuccessRate");
    predatorsAreSharingPrey = (Boolean) file.get("predatorsAreSharingPrey");
    resultFilename = (String) file.get("resultFilename");
    numberOfSimulations = ((Long) file.get("numberOfMaps")).intValue();
    if (width <= 0 || height <= 0)
      throw new RuntimeException("Wrong Data! Width and height must be positive values");
    if (numberOfSimulations != 1 && numberOfSimulations !=2)
      throw new RuntimeException("Wrong number of Simulations! Correct number of simulations is 1 or 2");
  }
}
