package mm.FacebookUtils;

import com.restfb.DefaultFacebookClient;

public class LoggedInFacebookClient extends DefaultFacebookClient {
    public AccessToken accessTokenObj;
    public LoggedInFacebookClient(String appId, String appSecret) {
        accessTokenObj = this.obtainAppAccessToken(appId, appSecret);
        this.accessToken = accessTokenObj.getAccessToken();
    }

}