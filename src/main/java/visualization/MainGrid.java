package visualization;

import javafx.scene.layout.GridPane;
import utilities.FileParser;
import utilities.rowColUtilities;

public class MainGrid extends GridPane {
  private final FileParser file;
  public MainGrid() throws Exception
  {
    file = new FileParser();
    Simulation simulation1 = new Simulation(file);
    this.add(simulation1,0,0);
    this.getColumnConstraints().add(rowColUtilities.createCol(100));
    if(file.numberOfSimulations == 1)
      oneSimulation();
    else
      twoSimulations();
  }

  private void oneSimulation()
  {
    this.getRowConstraints().add(rowColUtilities.createRow(100));
  }

  private void twoSimulations()
  {
    Simulation simulation2 = new Simulation(file);
    this.add(simulation2,0,2);
    this.getRowConstraints().add(rowColUtilities.createRow(49.75));
    this.getRowConstraints().add(rowColUtilities.createRow(0.5));
    this.getRowConstraints().add(rowColUtilities.createRow(49.75));
  }
}
