package edu.uml.android.volun_t;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by adam on 2/16/17.
 */

public class User {

    private String email, first, last, address, phone, make, model, plate;
    private int seats, type;  // type = 0 when client, type = 1 when volunteer
    private boolean handicap;
    private int level, levelProgress;
    private ArrayList<String> pendingPosts, acceptedPosts, completedPosts;

    public User() {
        email = "";
        first = "";
        last = "";
        phone = "";
        make = "";
        model = "";
        plate = "";
        level = 0;
        levelProgress = 0;
        seats = 0;
        type = -1;
        handicap = false;
        this.pendingPosts = new ArrayList<>();
        this.acceptedPosts = new ArrayList<>();
        this.completedPosts = new ArrayList<>();
    }

    public User(String email, String first, String last, String address,
                String phone, String make, String model, String plate, int seats, int type,
                boolean handicap, int level, int levelProgress, ArrayList<String> waitingPosts,
                ArrayList<String> acceptedPosts, ArrayList<String> completedPosts) {
        this.email = email;
        this.first = first;
        this.last = last;
        this.address = address;
        this.phone = phone;
        this.make = make;
        this.model = model;
        this.plate = plate;
        this.seats = seats;
        this.type = type;
        this.handicap = handicap;
        this.level = level;
        this.levelProgress = levelProgress;
        this.pendingPosts = waitingPosts;
        this.acceptedPosts = acceptedPosts;
        this.completedPosts = completedPosts;
    }

    public String getEmail() { return email; }
    public String getFirst() { return first; }
    public String getLast() { return last; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public String getPlate() { return plate; }
    public int getSeats() { return seats; }
    public int getType() { return type; }
    public boolean getHandicap() { return handicap; }
    public int getLevel() { return level; }
    public int getLevelProgress() { return levelProgress; }
    public void setLevel(int level) { this.level = level; }
    public void setLevelProgress(int lp) { this.levelProgress = lp; }
    public ArrayList<String> getPendingPosts() {
        return pendingPosts;
    }
    public ArrayList<String> getAcceptedPosts() {
        return acceptedPosts;
    }
    public ArrayList<String> getCompletedPosts() {
        return completedPosts;
    }
    public void setWaitingPosts(ArrayList<String> waitingPosts) {
        this.pendingPosts = waitingPosts;
    }
    public void setAcceptedPosts(ArrayList<String> acceptedPosts) {
        this.acceptedPosts = acceptedPosts;
    }
    public void setCompletedPosts(ArrayList<String> completedPosts) {
        this.completedPosts = completedPosts;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setHandicap(boolean handicap) {
        this.handicap = handicap;
    }
}
