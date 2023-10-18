package utilities;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;

public class rowColUtilities {
  public static ColumnConstraints createCol(double percent) {
    ColumnConstraints col = new ColumnConstraints();
    col.setPercentWidth(percent);
    return col;
  }

  public static RowConstraints createRow(double percent) {
    RowConstraints row = new RowConstraints();
    row.setPercentHeight(percent);
    return row;
  }
}
