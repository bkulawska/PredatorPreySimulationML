package visualization;

import animal.Animal;
import animal.AnimalDNA;
import animal.PredatorDNA;
import animal.PreyDNA;
import simulation.Engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DNAStatistics {
  private final Engine engine;
  private Map<AnimalDNA, Integer> activeGenotype;
  private AnimalDNA dominatingPredatorDNA = null;
  private AnimalDNA dominatingPreyDNA = null;
  private Integer preyCounter = 0;
  private Integer predatorCounter = 0;
  public DNAStatistics(Engine engine)
  {
    this.engine = engine;
  }

  public String getDominatingPreyDNA() {
    if(dominatingPreyDNA != null)
      return dominatingPreyDNA.toString();
    return "";
  }

  public PreyDNA getPreyDNA() {
    return (PreyDNA) dominatingPreyDNA;
  }

  public PredatorDNA getPredatorDNA() {
    return (PredatorDNA) dominatingPredatorDNA;
  }

  public String getDominatingPredatorDNA() {
    if(dominatingPredatorDNA != null)
      return dominatingPredatorDNA.toString();
    return "";
  }

  public int getPredatorCounter() {
    return predatorCounter;
  }

  public int getPreyCounter() {
    return preyCounter;
  }

  public void update()
  {
    DNACounter res = updateFromTable(engine.getAlivePredators());
    dominatingPredatorDNA = res.dna();
    predatorCounter = res.counter();
    res =  updateFromTable(engine.getAlivePreys());
    dominatingPreyDNA = res.dna();
    preyCounter = res.counter();
  }

  private DNACounter updateFromTable(ArrayList<Animal> list) {
    AnimalDNA dominatingDNA = null;
    activeGenotype = new HashMap<>();
    int counter = 0;
    for(Animal animal:list) {
      if(activeGenotype.containsKey(animal.getDNA())) {
        Integer value = activeGenotype.get(animal.getDNA());
        activeGenotype.remove(animal.getDNA());
        value = value + 1;
        if(counter < value) {
          counter = value;
          dominatingDNA = animal.getDNA();
        }
        activeGenotype.put(animal.getDNA(), value);
      }
      else {
        activeGenotype.put(animal.getDNA(), 1);
        if(1>counter) {
          counter = 1;
          dominatingDNA = animal.getDNA();
        }
      }
    }
    return new DNACounter(dominatingDNA, counter);
  }
}
