package edu.uml.android.volun_t;

/**
 * Created by adam on 2/16/17.
 */

public class User {

    private String email, pass, pass2, first, last, address, phone, make, model, plate;
    private int seats, type;  // type = 0 when client, type = 1 when volunteer
    private boolean handicap;

    public User() {
        email = "";
        pass = "";
        pass2 = "";
        first = "";
        last = "";
        phone = "";
        make = "";
        model = "";
        plate = "";
        seats = 0;
        type = -1;
        handicap = false;
    }

    public User(String email, String pass, String pass2, String first, String last, String address,
                String phone, String make, String model, String plate, int seats, int type,
                boolean handicap) {
        this.email = email;
        this.pass = pass;
        this.pass2 = pass2;
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
    }

    public String getEmail() { return email; }
    public String getPass() { return pass; }
    public String getPass2() { return pass2; }
    public String getFirst() { return first; }
    public String getLast() { return last; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public String getPlate() { return plate; }
    public int getSeats() { return seats; }
    public int getType() { return type; }
    public boolean getHandicap() {return handicap; }

}
