package net.lubot.tmichat.model.FFZEmotes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Urls {

    @SerializedName("1")
    @Expose
    private String _1;
    @SerializedName("2")
    @Expose
    private String _2;
    @SerializedName("4")
    @Expose
    private String _4;

    /**
     *
     * @return
     * The _1
     */
    public String get1() {
        return _1;
    }

    /**
     *
     * @param _1
     * The 1
     */
    public void set1(String _1) {
        this._1 = _1;
    }

    /**
     *
     * @return
     * The _2
     */
    public String get2() {
        return _2;
    }

    /**
     *
     * @param _2
     * The 2
     */
    public void set2(String _2) {
        this._2 = _2;
    }

    /**
     *
     * @return
     * The _4
     */
    public String get4() {
        return _4;
    }

    /**
     *
     * @param _4
     * The 4
     */
    public void set4(String _4) {
        this._4 = _4;
    }

}
