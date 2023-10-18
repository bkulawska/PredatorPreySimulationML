package visualization;

import animal.Animal;
import animal.Predator;
import animal.Prey;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import map.Field;
import simulation.Engine;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Board extends GridPane {
  private final Engine engine;
  private final int width;
  private final int height;
  private final Simulation simulation;
  private final DNAStatistics dnaStatistics;
  private final List<Pane> tmpObjects = new LinkedList<>();

  public Board(Engine engine, int width, int height, Simulation simulation, DNAStatistics dnaStatistics)
  {
    this.dnaStatistics = dnaStatistics;
    this.engine = engine;
    this.width = width;
    this.height = height;
    this.simulation = simulation;
    this.prepareFields();
    prepareBoard();
  }

  public void updateBoard(int dominantAnimalState)
  {
    this.getChildren().removeAll(tmpObjects);
    addTmpObjects(dominantAnimalState);
  }

  private void prepareFields()
  {
    for(int i = 0; i <  width; i++)
    {
      this.getColumnConstraints().add(createCol(100*(1/ (double) width)));
    }
    for (int i = 0; i < height; i++)
    {
      this.getRowConstraints().add(createRow(100*(1/ (double) height)));
    }
  }

  private void addTmpObjects(int dominantAnimalState)
  {
    tmpObjects.clear();
    Collection<Field> colOfFields = engine.getFields().values();
    for (Field current : colOfFields)
    {
      if (current.containsAnimals())
      {
        Pane circle = new Pane();
        circle.setShape(new Circle(0.05));
        circle.setStyle(setColour(current, dominantAnimalState));
        circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            if(simulation.getAnimationStatus() == 0) {
              simulation.setAnimalSelected(current.getStrongestAnimal());
            }
          }
        });
        this.add(circle, current.getPosition().x,current.getPosition().y);
        tmpObjects.add(circle);
      }
      else
      {
        if(current.getContainsGrass())
        {
          Pane square = new Pane();
          square.setStyle("-fx-background-color: #00B500");
          this.add(square, current.getPosition().x, current.getPosition().y);
          tmpObjects.add(square);
        }
      }
    }
  }

  private void prepareBoard()
  {
    for (int row = 0; row < height; row++)
    {
      for (int col = 0; col < width; col++)
      {
        StackPane square = new StackPane();
        square.setStyle("-fx-background-color: #00FF00;");
        this.add(square, col, row);
      }
    }
  }

  private ColumnConstraints createCol(double percent)
  {
    ColumnConstraints col = new ColumnConstraints();
    col.setPercentWidth(percent);
    return col;
  }

  private RowConstraints createRow(double percent)
  {
    RowConstraints row = new RowConstraints();
    row.setPercentHeight(percent);
    return row;
  }
  private String setColour(Field field, int dominantAnimalState)
  {
    Animal strongestAnimal = field.getStrongestAnimal();
    if(strongestAnimal instanceof Prey) {
      Prey prey = (Prey) strongestAnimal;
      if(dominantAnimalState  == 1 && prey.getDNA().equals(dnaStatistics.getPreyDNA())) {
        return "-fx-background-color: #E0005F;";
      }
      return "-fx-background-color: #F2E8E0;";
    }
    Predator predator = (Predator) strongestAnimal;
    if(dominantAnimalState  == 1 && predator.getDNA().equals(dnaStatistics.getPredatorDNA())) {
      return "-fx-background-color: #0F00FE;";
    }
    return "-fx-background-color: #000000;";
  }
}
