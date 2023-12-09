package visualization;

import animal.Animal;
import javafx.application.Platform;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import simulation.Engine;
import utilities.FileParser;
import utilities.rowColUtilities;
import javafx.scene.control.Button;

public class Simulation extends BorderPane {
  private final Engine engine;
  private Animal animalSelected = null;
  private int animationStatus = 0;
  private int simulationSpeed = 500;
  private final Board board;
  private final Statistics statistics;
  private final DNAStatistics dnaStatistics;
  private int selectedAnimalCounter = 0;
  private final Button dominantDNA = new Button("Select Dominant DNA");
  private int dominantDNAState = 0;
  private BorderPane speedValue = new BorderPane();

  public Simulation(FileParser fileParser) {
    engine = new Engine(fileParser);
    dnaStatistics = new DNAStatistics(this.engine);
    statistics = new Statistics(engine, dnaStatistics);
    board = new Board(engine, fileParser.width, fileParser.height, this, dnaStatistics);
    this.setLeft(leftSide());
    Thread thread = createThread();
    thread.setDaemon(true);
    thread.start();
    generatePane();
  }

  public int getAnimationStatus() {
    return animationStatus;
  }


  private void updateEngine() {
    engine.nextDay();
    generatePane();
  }

  public void generatePane() {
    this.setCenter(null);
    dnaStatistics.update();
    statistics.updateStatistics(animalSelected);
    board.updateBoard(0);
    this.setCenter(board);
  }

  protected void setAnimalSelected(Animal animalSelected) {
    selectedAnimalCounter++;
    this.animalSelected = animalSelected;
    this.animalSelected.setAncestryFactor(selectedAnimalCounter);
    statistics.updateStatistics(animalSelected);
  }

  private GridPane leftSide() {
    GridPane left = new GridPane();
    left.getRowConstraints().add(rowColUtilities.createRow(20));
    left.getRowConstraints().add(rowColUtilities.createRow(80));
    left.getColumnConstraints().add(rowColUtilities.createCol(100));
    left.setMinWidth(150);
    left.add(buttons(), 0, 0);
    statistics.updateStatistics(null);
    left.add(statistics, 0, 1);
    return left;
  }

  private GridPane buttons() {
    GridPane buttonArea = new GridPane();
    GridPane upperButtonArea = new GridPane();
    BorderPane lowerButtonArea = new BorderPane();
    Button slower = new Button("slower");
    Button start = new Button("start");
    Button stop = new Button("stop");
    Button faster = new Button("faster");
    Button animal = new Button("Unselect");
    upperButtonArea.add(slower, 0, 0);
    upperButtonArea.add(start, 1, 0);
    upperButtonArea.add(stop, 2, 0);
    upperButtonArea.add(faster, 3, 0);
    lowerButtonArea.setLeft(animal);
    lowerButtonArea.setRight(dominantDNA);
    speedValue.setLeft(new Text("Current Refresh Time: " + simulationSpeed+ "ms"));
    buttonArea.add(upperButtonArea, 0, 0);
    buttonArea.add(lowerButtonArea, 0, 1);
    buttonArea.add(speedValue, 0, 2);
    slower.setOnAction(e -> slowSimulation());
    start.setOnAction(e-> startSimulation());
    stop.setOnAction(e->stopSimulation());
    faster.setOnAction(e->accelerateSimulation());
    animal.setOnAction(e->resetSelectedAnimal());
    dominantDNA.setOnAction(e->dominantDNAAction());
    return buttonArea;
  }

  private void startSimulation() {
    if(dominantDNAState == 1)
      dominantDNAAction();
    animationStatus = 1;
  }

  private void stopSimulation() {
    animationStatus = 0;
  }

  private void accelerateSimulation() {
    if (simulationSpeed > 100)
      simulationSpeed -= 100;
    speedValue.setLeft(new Text("Current Refresh Time: " + simulationSpeed+ "ms"));
  }

  private void slowSimulation() {
    if (simulationSpeed < 3000)
      simulationSpeed += 100;
    speedValue.setLeft(new Text("Current Refresh Time: " + simulationSpeed+ "ms"));
  }

  private void resetSelectedAnimal() {
    animalSelected = null;
    statistics.updateStatistics(null);
  }

  private void dominantDNAAction()
  {
    if(animationStatus == 0) {
      if (dominantDNAState != 0)
      {
        dominantDNA.setText("Select dominant DNA");
        dominantDNAState = 0;
      } else
      {
        dominantDNA.setText("Unselect dominant DNA");
        dominantDNAState = 1;
      }
      board.updateBoard(dominantDNAState);
    }
  }

  private Thread createThread() {
    return new Thread(() -> {
      Runnable runnable = this::updateEngine;
      while (true) {
        try {
          Thread.sleep(simulationSpeed);
        } catch (InterruptedException ignore) {
        }
        if (animationStatus == 1) {
          Platform.runLater(runnable);
        }
      }
    });
  }
}