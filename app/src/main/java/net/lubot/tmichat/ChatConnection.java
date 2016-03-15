package net.lubot.tmichat;

import android.util.Log;
import android.webkit.WebView;

import net.lubot.tmichat.model.FFZEmotes.Emoticon;

import org.apache.commons.lang3.StringUtils;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.List;

public class ChatConnection extends WebSocketClient {

    private final String TAG = "WebSocket";
    MessageHandler messageHandler;

    private boolean isConnected = false;
    String channel = null;

    public ChatConnection(URI serverURI, Draft draft) {
        super(serverURI, draft);
        messageHandler = new MessageHandler();
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d(TAG, "Connected to WebSocket server.");
        isConnected = true;
        send("CAP REQ :twitch.tv/tags twitch.tv/commands\n");
        send("PASS" + Config.OAUTH + "\n");
        send("NICK" + Config.USER +"\n");
        send("JOIN #" + channel + "\n" );
    }

    @Override
    public void onMessage(String message) {
        Log.d(TAG, "Message: " + message);
        if (message.startsWith("PING")) {
            Log.d(TAG, "Playing Ping Pong with TMI");
            send("PONG\n");
            return;
        }
        messageHandler.handleMessage(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        isConnected = false;
        Log.d(TAG, "Connection closed, Code: " + code + " Reason: " + reason + " Remote: " + remote);
    }

    @Override
    public void onError(Exception ex) {
        Log.d(TAG, ex.getMessage());
    }

    public boolean isConnected() {
        return this.isConnected;
    }

    public void setChannel(String channel) {
        if (StringUtils.isNotEmpty(this.channel) && isConnected) {
            send("PART #" + this.channel + "\n");
        }
        this.channel = channel;
        if (isConnected) {
            send("JOIN #" + channel + "\n");
        }
    }

    public void setChat(WebView view) {
        messageHandler.setChat(view);
    }

    public void setFFZEmotes(List<Emoticon> emoticons) {
        messageHandler.setEmoticons(emoticons);
    }

    public String getChannel() {
        return this.channel;
    }
}
