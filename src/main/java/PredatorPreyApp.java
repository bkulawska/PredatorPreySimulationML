import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import visualization.MainGrid;

public class PredatorPreyApp extends Application {

  @Override
  public void start(Stage primaryStage)
  {
    try
    {
      primaryStage.setScene(new Scene(new MainGrid(), 800, 800));
      primaryStage.show();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
}
