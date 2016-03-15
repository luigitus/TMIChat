package net.lubot.tmichat;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.WebView;

import net.lubot.tmichat.model.FFZEmotes.Emoticon;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageHandler {

    private WebView chat;
    private List<Emoticon> emoticons;

    public MessageHandler() {

    }

    public void handleMessage(String rawIrcMessage) {
        if (StringUtils.contains(rawIrcMessage,"PRIVMSG")) {
            String tags = rawIrcMessage.substring(0, StringUtils.indexOf(rawIrcMessage, " "));
            rawIrcMessage = rawIrcMessage.substring(StringUtils.indexOf(rawIrcMessage, " ") + 2);
            String name = rawIrcMessage.substring(0 ,StringUtils.indexOf(rawIrcMessage, "!"));
            String message = rawIrcMessage.substring(StringUtils.indexOf(rawIrcMessage, ":")+1);
            Log.d("User", name);
            Log.d("Message", rawIrcMessage);
            final String[] spilt = tags.split(";");
            for (String s : spilt) {
                // 0: color (if set)
                // 1: display-name (if set)
                // 2: emotes
                // 3: mod
                // 4: room-id
                // 5: subscriber
                // 6: turbo
                // 7: user-id
                // 8: user-type
                //Log.d("Tags", s.substring(StringUtils.indexOf(s, "=")+1));
                Log.d("Tags", s);
            }
            if (StringUtils.equals(spilt[2], "emotes=")) {
                Log.d("emotes", "empty");
            } else {
                Log.d("emotes", "not empty");
               message = insertTwitchEmotes(message ,spilt[2].substring(StringUtils.indexOf(spilt[2], "=")+1));
            }

            if (emoticons != null) {
                message = insertFFZEmotes(message);
            }

            final String m = message;
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    chat.loadUrl("javascript:addMessage(\"" + "" + "\", \"" + spilt[1].substring(StringUtils.indexOf(spilt[1], "=")+1) + "\", \"" + m + "\"" + ")" );
                }
            });
        }
    }

    private String insertTwitchEmotes(String messages, String emotes_tag) {
        List<String> split = null;
        ArrayList<TwitchEmote> emotes = new ArrayList<>();
        StringBuilder buffer = new StringBuilder(messages);
        Log.d("buffer", buffer.toString().length() + "");
        Log.d("tags", emotes_tag);
        if (StringUtils.contains(emotes_tag, "/")) {
            System.out.println("emotes_tags " + "contains two emotes (at least), splitting");
            split = Arrays.asList(emotes_tag.split("/"));

            for (String s : split) {
                Log.d("emote_tag", s);
                if (StringUtils.contains(s, ",")) {
                    // Emote is used more than once
                    TwitchEmote twitchEmote = new TwitchEmote();
                    twitchEmote.setId(StringUtils.substringBefore(s, ":"));
                    List<String> positions = Arrays.asList(StringUtils.substringAfter(s, ":").split(","));
                    //Collections.reverse(positions);
                    for (int i = 0; i < positions.size(); i++) {
                        String cursor = positions.get(i);
                        String[] curPos = cursor.split("-");
                        twitchEmote.addPosition(curPos[0], curPos[1]);
                    }
                    emotes.add(twitchEmote);
                    Log.d("TwitchEmote", "position: ");
                    Log.d("emote","Emote is used at least two times, splitting");
                    Log.d("emote_multi", StringUtils.substringBefore(s, ":"));
                   /* for (String a : emote_positions) {
                        String[] curPos = a.split("-");
                        buffer.replace(Integer.parseInt(curPos[0]), Integer.parseInt(curPos[1])+1, StringUtils.replace("<img class='emoticon' src='http://static-cdn.jtvnw.net/emoticons/v1/{id}/1.0' srcset='http://static-cdn.jtvnw.net/emoticons/v1/{id}/2.0 2x'>", "{id}", twitchEmote.getId()));
                        Log.d("emote_positions", a);
                    } */
                } else {
                    TwitchEmote twitchEmote = new TwitchEmote();
                    twitchEmote.setId(StringUtils.substringBefore(s, ":"));
                    String[] curPos = StringUtils.substringAfter(s, ":").split("-");
                    twitchEmote.addPosition(curPos[0], curPos[1]);
                    emotes.add(twitchEmote);
                    Log.d("emote", StringUtils.substringBefore(s, ":"));
                    Log.d("emote_positions_single", StringUtils.substringAfter(s, ":"));
                    //buffer.replace(Integer.parseInt(curPos[0]), Integer.parseInt(curPos[1])+1, StringUtils.replace("<img class='emoticon' src='http://static-cdn.jtvnw.net/emoticons/v1/{id}/1.0' srcset='http://static-cdn.jtvnw.net/emoticons/v1/{id}/2.0 2x'>", "{id}", emoteID));
                }
            }
            Log.d("Message", buffer.toString());
        }

        if (!emotes.isEmpty()) {
            /*
            Log.d("Emote Sorting", "Before");
            for (TwitchEmote emote : emotes) {
                Log.d("TwitchEmote", "id: " + emote.getId());
                for (Position p : emote.getPositions()) {
                    Log.d("TwitchEmote", "position: start: " + p.getStart() + " end: " + p.getEnd());
                }
            }
            */
            Collections.sort(emotes);
            /*
            Log.d("Emote Sorting", "After");
            for (TwitchEmote emote : emotes) {
                Log.d("TwitchEmote", "id: " + emote.getId());
                for (Position p : emote.getPositions()) {
                    Log.d("TwitchEmote", "position: start: " + p.getStart() + " end: " + p.getEnd());
                }
            }
            */
            Collections.reverse(emotes);
            /*
            Log.d("Emote Sorting", "Reverse");
            for (TwitchEmote emote : emotes) {
                Log.d("TwitchEmote", "id: " + emote.getId());
                for (Position p : emote.getPositions()) {
                    Log.d("TwitchEmote", "position: start: " + p.getStart() + " end: " + p.getEnd());
                }
            }
            */

            for (TwitchEmote emote : emotes) {
                for (Position p : emote.getPositions()) {
                    int start = Integer.parseInt(p.getStart());
                    int end = Integer.parseInt(p.getEnd());
                    buffer.replace(start, end, StringUtils.replace("<img class='emoticon' src='http://static-cdn.jtvnw.net/emoticons/v1/{id}/1.0' srcset='http://static-cdn.jtvnw.net/emoticons/v1/{id}/2.0 2x'>", "{id}", emote.getId()));
                }
            }
        } else {
            //single, lonely emote
        }
        Log.d("buffer", buffer.toString());
        return buffer.toString();
    }

    private String insertFFZEmotes(String message) {
        for (int i = 0; i < emoticons.size(); i++) {
            Emoticon emoticon = emoticons.get(i);
            if (containsFFZEmote(message, emoticon.getName())) {
                message = StringUtils.replace(message, emoticon.getName(), getFFZTemplate(emoticon));
            }
        }
        return message;
    }

    private boolean containsFFZEmote(String message, String emote) {
        Pattern p = Pattern.compile("\\b" + emote +"\\b");
        Matcher m = p.matcher(message);
        return m.find();
    }

    private String getFFZTemplate(Emoticon emoticon) {
        boolean size2 = StringUtils.isNotEmpty(emoticon.getUrls().get2());
        boolean size4 = StringUtils.isNotEmpty(emoticon.getUrls().get4());

        if (size2 && size4) {
            String base = "<img class='ffz-emoticon' src='http://cdn.frankerfacez.com/emoticon/{id}/1' srcset='http://cdn.frankerfacez.com/emoticon/{id}/1 1x, http://cdn.frankerfacez.com/emoticon/{id}/2 2x, http://cdn.frankerfacez.com/emoticon/{id}/4 4x'>";
            return StringUtils.replace(base, "{id}", emoticon.getId() + "");
        }

        if (size2) {
            String base = "<img class='ffz-emoticon' src='http://cdn.frankerfacez.com/emoticon/{id}/1' srcset='http://cdn.frankerfacez.com/emoticon/{id}/1 1x, http://cdn.frankerfacez.com/emoticon/{id}/2 2x'>";
            return StringUtils.replace(base, "{id}", emoticon.getId() + "");
        }

        String base = "<img class='ffz-emoticon' src='http://cdn.frankerfacez.com/emoticon/{id}/1' srcset='http://cdn.frankerfacez.com/emoticon/{id}/1 1x'>";
        return StringUtils.replace(base, "{id}", emoticon.getId() + "");
    }

    public void setChat(WebView view) {
        this.chat = view;
    }

    public void setEmoticons(List<Emoticon> emoticons) {
        this.emoticons = emoticons;
    }

}
