package mm;

import com.restfb.DefaultFacebookClient;
import com.restfb.Version;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.scope.ScopeBuilder;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import static com.restfb.logging.RestFBLogger.CLIENT_LOGGER;

public class FacebookOauth {
  private static final String SUCCESS_URL = "https://www.facebook.com/connect/login_success.html";

  public static void oAuth(Stage stage, String appId, String appSecret) {
    // parse arguments

    WebView webView = new WebView();
    WebEngine webEngine = webView.getEngine();

    // create the scene
    stage.setTitle("Facebook Login");
    // use quite a wide window to handle cookies popup nicely
    stage.setScene(new Scene(new VBox(webView), Screen.getPrimary().getBounds().getWidth(),
        Screen.getPrimary().getBounds().getHeight(), Color.web("#666970")));
    stage.show();

    // obtain Facebook access token by loading login page
    DefaultFacebookClient facebookClient = new DefaultFacebookClient(Version.LATEST);
    String loginDialogUrl = facebookClient.getLoginDialogUrl(appId, SUCCESS_URL, new ScopeBuilder());
    webEngine.load(loginDialogUrl + "&display=popup&response_type=code");
    webEngine.locationProperty().addListener((property, oldValue, newValue) -> {
      if (newValue.startsWith(SUCCESS_URL)) {
        // extract access token
        CLIENT_LOGGER.debug(newValue);
        int codeOffset = newValue.indexOf("code=");
        String code = newValue.substring(codeOffset + "code=".length());
        AccessToken accessToken = facebookClient.obtainUserAccessToken(appId, appSecret, SUCCESS_URL, code);

        // trigger further code's execution
        consumeAccessToken(accessToken);

        // close the app as goal was reached
        stage.close();

      } else if ("https://www.facebook.com/dialog/close".equals(newValue)) {
        throw new IllegalStateException("dialog closed");
      }
    });
  }

  private static void consumeAccessToken(AccessToken accessToken) {
    CLIENT_LOGGER.debug("Access token: " + accessToken.getAccessToken());
    CLIENT_LOGGER.debug("Expires: " + accessToken.getExpires());
  }
}
