package mm;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainView extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    
    String appId = getParameters().getRaw().get(0);
    String appSecret = getParameters().getRaw().get(1);
    FacebookOauth.oAuth(primaryStage, appId, appSecret);

  }

  public static void startApp(String[] args) {
    if (args.length != 2)
      throw new IllegalArgumentException("You must provide an App ID and App secret");
    launch(args);
  }

}
