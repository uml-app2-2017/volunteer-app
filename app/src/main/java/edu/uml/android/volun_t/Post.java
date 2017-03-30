package edu.uml.android.volun_t;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by adam on 3/5/17.
 */

public class Post implements Serializable {

    private String timePosted;
    private String timeScheduled;
    private String timeCompleted;
    private String requesterUid;
    private String takerUid;
    private String requesterName;
    private String takerName;
    private String location;
    private String title;
    private String description;
    private int numPeople;
    private boolean handicap;
    private boolean taken;
    private String postId;
    private boolean completed;

    public Post() {}

    public Post(Calendar timePosted, Calendar timeScheduled, Calendar timeCompleted,
                String requesterUid, String takerUid, String location, String title,
                String description, int numPeople, boolean handicap, boolean taken, String postId,
                boolean completed, String requesterName, String takerName) {

        this.timePosted = getFormattedTime(timePosted);
        this.timeScheduled = getFormattedTime(timeScheduled);
        this.timeCompleted = getFormattedTime(timeCompleted);
        this.requesterUid = requesterUid;
        this.takerUid = takerUid;
        this.location = location;
        this.title = title;
        this.description = description;
        this.numPeople = numPeople;
        this.handicap = handicap;
        this.taken = taken;
        this.postId = postId;
        this.completed = completed;
        this.requesterName = requesterName;
        this.takerName = takerName;
    }

    public String getFormattedTime(Calendar c) {
        try {
            String posted = "" + (c.get(Calendar.MONTH)+1) + "/" + c.get(Calendar.DAY_OF_MONTH) + "/" +
                    + c.get(Calendar.YEAR) + "  @  " + c.get(Calendar.HOUR) + ":"
                    + String.format("%02d", c.get(Calendar.MINUTE));
            return posted;
        } catch (NullPointerException e) {
            return "";
        }
    }

    public String getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(String timePosted) {
        this.timePosted = timePosted;
    }

    public String getTimeScheduled() {
        return timeScheduled;
    }

    public void setTimeScheduled(String timeScheduled) {
        this.timeScheduled = timeScheduled;
    }

    public String getTimeCompleted() {
        return timeCompleted;
    }

    public void setTimeCompleted(String timeCompleted) {
        this.timeCompleted = timeCompleted;
    }

    public String getRequesterUid() {
        return requesterUid;
    }

    public void setRequesterUid(String uid) {
        this.requesterUid = uid;
    }

    public String getTakerUid() {
        return takerUid;
    }

    public void setTakerUid(String uid) {
        this.takerUid = uid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }

    public boolean isHandicap() {
        return handicap;
    }

    public void setHandicap(boolean handicap) {
        this.handicap = handicap;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public String getPostId() { return postId; }

    public void setPostId(String postId) { this.postId = postId; }

    public boolean isCompleted() { return completed; }

    public void setCompleted(boolean comp) { completed = comp; }

    public String getRequesterName() { return requesterName; }

    public String getTakerName() { return takerName; }

    public void setTakerName(String name) { this.takerName = name; }

    public boolean acceptPost(String takerUid) {
        this.takerUid = takerUid;
        taken = true;
        return true;
    }

    @Override
    public String toString() { return ""; }

}
