package net.lubot.tmichat;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;

public class TwitchEmote implements Comparable<TwitchEmote> {
    private String id;
    private ArrayList<Position> positions;

    public TwitchEmote() {
        positions = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Position> getPositions() {
        Collections.reverse(positions);
        return positions;
    }

    public void addPosition(String s, String e) {
        Position p = new Position();
        p.setStart(s);
        p.setEnd(String.valueOf(Integer.parseInt(e)+1));
        positions.add(p);
    }

    @Override
    public int compareTo(@NonNull TwitchEmote another) {

        int start = Integer.parseInt(this.getPositions().get(0).getStart());
        int startAnother = Integer.parseInt(another.getPositions().get(0).getStart());

        return start - startAnother;
    }
}
