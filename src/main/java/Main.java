import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import visualization.MainGrid;

public class Main extends Application {


  public static void main(String[] args) {
    launch(args);
  }

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
