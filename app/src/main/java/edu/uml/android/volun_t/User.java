package edu.uml.android.volun_t;

/**
 * Created by adam on 2/16/17.
 */

public class User {

    private String email, first, last, address, phone, make, model, plate;
    private int seats, type;  // type = 0 when client, type = 1 when volunteer
    private boolean handicap;
    private int level, levelProgress;

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
    }

    public User(String email, String first, String last, String address,
                String phone, String make, String model, String plate, int seats, int type,
                boolean handicap, int level, int levelProgress) {
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

}
