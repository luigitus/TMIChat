package net.lubot.tmichat;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import net.lubot.tmichat.model.FFZEmotes.Emoticon;
import net.lubot.tmichat.model.FFZEmotes.FFZSet;
import net.lubot.tmichat.model.FFZUser;
import net.lubot.tmichat.model.Room;

import org.java_websocket.drafts.Draft_17_irc;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "WebSocket";
    public static final String BASE_URL = "https://api.frankerfacez.com/v1/";
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private FFZInterface ffzAPI = retrofit.create(FFZInterface.class);

    private WebView chat;
    private EditText input;
    private ChatConnection chatConnection;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
        chat = (WebView) findViewById(R.id.webView);
        input = (EditText) findViewById(R.id.editText);
        chat.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        input.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String i = input.getText().toString();
                    /*
                    if (StringUtils.startsWith(i,"/changeChannel ")) {
                        Log.d("ChatInput", i.replace("/changeChannel ", ""));
                        chatConnection.setChannel(i.replace("/changeChannel ", ""));
                    }
                    */
                    chatConnection.send("PRIVMSG #" + chatConnection.getChannel() + " :" + i + "\n");

                    return true;
                    //webSocket.sendText(input.getText().toString());
                }
                return false;
            }
        });

        //String chatJS = readAsset("chat.js");
        chat.loadUrl("file:///android_asset/chat.html");
        //chat.loadUrl("javascript:(function (){document.getElementsByTagName('head')[0].appendChild(document.createElement('script')).innerHTML=\"" + chatJS +"\";}());");
        connectWebSocket();
        getFFZUser("luigitus");
    }

    private String readAsset(String file) {
        InputStream input;
        String text = "";
        try {
            input = getAssets().open(file);
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            text = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    private void connectWebSocket() {
        URI uri = null;
        try {
            uri = new URI("ws://main-ws.tmi.twitch.tv");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        chatConnection = new ChatConnection(uri, new Draft_17_irc());
        chatConnection.connect();
        chatConnection.setChannel("luigitus");
        chatConnection.setChat(chat);
    }

    private void getFFZUser(String user) {
        Call<FFZUser> User = ffzAPI.getUser(user);
        User.enqueue(new Callback<FFZUser>() {
            @Override
            public void onResponse(Call<FFZUser> call, Response<FFZUser> response) {
                Room user = response.body().getRoom();
                Log.d("FFZUser", user.getSet() + "");
                Call<FFZSet> set = ffzAPI.getSet(user.getSet() + "");
                set.enqueue(new Callback<FFZSet>() {
                    @Override
                    public void onResponse(Call<FFZSet> call, Response<FFZSet> response) {
                        List<Emoticon> emoticons = response.body().getSet().getEmoticons();
                        chatConnection.setFFZEmotes(emoticons);
                    }

                    @Override
                    public void onFailure(Call<FFZSet> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<FFZUser> call, Throwable t) {

            }
        });
    }

}
