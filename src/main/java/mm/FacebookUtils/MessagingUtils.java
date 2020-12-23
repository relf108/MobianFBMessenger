package mm.FacebookUtils;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.types.Conversation;
import com.restfb.types.Message;
import com.restfb.types.send.IdMessageRecipient;
import com.restfb.types.send.SendResponse;
import com.restfb.Parameter;

public class MessagingUtils {

    public static FacebookClient pageClient = new DefaultFacebookClient(Version.LATEST);

    public static void sendMessage() {
        String conversationId = getRecipientIDs().get(0);
        IdMessageRecipient recipient = new IdMessageRecipient(getRecipientIDs().get(1));
        SendResponse resp = pageClient.publish(conversationId + "/messages", SendResponse.class,
                Parameter.with("recipient", recipient), Parameter.with("message", "Uraaaa!!!"));
        System.out.println(resp.getResult());

    }

    /// Oth element in returned array will be the conversation id
    public static ArrayList<String> getRecipientIDs() {
        ArrayList<String> recipientIds = new ArrayList<>();
        Connection<Conversation> conversations = pageClient.fetchConnection("me/conversations", Conversation.class);
        for (List<Conversation> conversationPage : conversations) {
            for (Conversation conversation : conversationPage) {
                String id = conversation.getId(); // use this conversation_id
                recipientIds.add(id);
                Connection<Message> messages = pageClient.fetchConnection(id + "/messages", Message.class,
                        Parameter.with("fields", "message,created_time,from,id"));
                messages.forEach(s -> s.forEach(k -> System.out
                        .println(k.getFrom() + " " + k.getId() + " " + k.getMessage() + " " + k.getSubject() + " ")));
                messages.forEach(s -> s.forEach(k -> recipientIds.add(k.getFrom().getId())));
            }
        }
        return recipientIds;
    }

    public static List<Conversation> getConversations(DefaultFacebookClient client, String appSecret) {
        ArrayList<Conversation> conversationsList = new ArrayList<>();
        Connection<Conversation> conversations = client.fetchConnection("me/conversations", Conversation.class);
        for (List<Conversation> conversationPage : conversations) {
            for (Conversation conversation : conversationPage) {
                conversationsList.add(conversation);

            }
        }
        return conversationsList;
    }
}
