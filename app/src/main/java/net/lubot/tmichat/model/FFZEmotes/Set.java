package net.lubot.tmichat.model.FFZEmotes;

import java.util.ArrayList;
import java.util.List;

public class Set {

    private Integer Type;
    private Object css;
    private Object description;
    private List<Emoticon> emoticons = new ArrayList<Emoticon>();
    private Object icon;
    private Integer id;
    private String title;

    /**
     *
     * @return
     * The Type
     */
    public Integer getType() {
        return Type;
    }

    /**
     *
     * @param Type
     * The _type
     */
    public void setType(Integer Type) {
        this.Type = Type;
    }

    /**
     *
     * @return
     * The css
     */
    public Object getCss() {
        return css;
    }

    /**
     *
     * @param css
     * The css
     */
    public void setCss(Object css) {
        this.css = css;
    }

    /**
     *
     * @return
     * The description
     */
    public Object getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(Object description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The emoticons
     */
    public List<Emoticon> getEmoticons() {
        return emoticons;
    }

    /**
     *
     * @param emoticons
     * The emoticons
     */
    public void setEmoticons(List<Emoticon> emoticons) {
        this.emoticons = emoticons;
    }

    /**
     *
     * @return
     * The icon
     */
    public Object getIcon() {
        return icon;
    }

    /**
     *
     * @param icon
     * The icon
     */
    public void setIcon(Object icon) {
        this.icon = icon;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

}