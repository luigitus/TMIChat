package net.lubot.tmichat.model;

public class Room {

    private Integer _id;
    private Integer Tid;
    private Object css;
    private String displayName;
    private String id;
    private Boolean isGroup;
    private Object moderatorBadge;
    private Integer set;

    /**
     *
     * @return
     * The Id
     */
    public Integer get_Id() {
        return _id;
    }

    /**
     *
     * @param Id
     * The _id
     */
    public void set_Id(Integer Id) {
        this._id = Id;
    }

    /**
     *
     * @return
     * The Tid
     */
    public Integer getTid() {
        return Tid;
    }

    /**
     *
     * @param Tid
     * The _tid
     */
    public void setTid(Integer Tid) {
        this.Tid = Tid;
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
     * The displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     *
     * @param displayName
     * The display_name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The isGroup
     */
    public Boolean getIsGroup() {
        return isGroup;
    }

    /**
     *
     * @param isGroup
     * The is_group
     */
    public void setIsGroup(Boolean isGroup) {
        this.isGroup = isGroup;
    }

    /**
     *
     * @return
     * The moderatorBadge
     */
    public Object getModeratorBadge() {
        return moderatorBadge;
    }

    /**
     *
     * @param moderatorBadge
     * The moderator_badge
     */
    public void setModeratorBadge(Object moderatorBadge) {
        this.moderatorBadge = moderatorBadge;
    }

    /**
     *
     * @return
     * The set
     */
    public Integer getSet() {
        return set;
    }

    /**
     *
     * @param set
     * The set
     */
    public void setSet(Integer set) {
        this.set = set;
    }

}