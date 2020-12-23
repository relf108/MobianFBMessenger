package mm;

import com.restfb.DefaultFacebookClient;
import com.restfb.Version;

import javafx.application.Application;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import mm.FacebookUtils.FacebookOauth;
import mm.FacebookUtils.LoggedInFacebookClient;
import mm.Scenes.ConversationSelect;

public class MainView extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    stage.show();
    String appId = getParameters().getRaw().get(0);
    String appSecret = getParameters().getRaw().get(1);
    FacebookOauth.oAuth(stage, appId, appSecret);
    //If token is null user has not logged in
   // LoggedInFacebookClient client = new LoggedInFacebookClient(appId, appSecret);
  }

  public static void startApp(String[] args) {
    if (args.length != 2)
      throw new IllegalArgumentException("You must provide an App ID and App secret");
    launch(args);
  }

}
