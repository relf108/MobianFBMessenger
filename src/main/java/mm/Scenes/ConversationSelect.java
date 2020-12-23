package mm.Scenes;

import com.restfb.DefaultFacebookClient;
import com.restfb.types.Conversation;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mm.FacebookUtils.LoggedInFacebookClient;
import mm.FacebookUtils.MessagingUtils;

public class ConversationSelect extends Scene {

    /// Set this scene to the current view
    public ConversationSelect(ScrollPane root, Stage Stage, DefaultFacebookClient client, String appSecret) {
        super(root);
        VBox verticalLayout = new VBox();

        for (Conversation conversation : MessagingUtils.getConversations(client, appSecret)) {
            Label conversationLabel = new Label(conversation.getSenders().get(0).toString());
            HBox horizontalLayout = new HBox();
            horizontalLayout.getChildren().add(conversationLabel);
            verticalLayout.getChildren().add(horizontalLayout);
        }

        // verticalLayout.getChildren().add();
        root.setContent(verticalLayout);
    }
}
