package edu.uml.android.volun_t;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by adam on 3/2/17.
 */

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference db;
    int userType = -1;
    User user;
    // Client views
    TextView cNameText, cAddressText, cPhoneText, cEmailText;
    EditText cNameEdit, cAddressEdit, cPhoneEdit;
    // Volunteer views
    TextView vNameText, vAddressText, vPhoneText, vMakeText, vPlateText, vSeatsText, vHandicapText, vEmailText;
    EditText vNameEdit, vAddressEdit, vPhoneEdit, vMakeEdit, vPlateEdit, vSeatsEdit;
    CheckBox vHandicapEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);

        // Set up firebase utils
        auth = FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference();

        // Get the type of user and set view accordingly
        db.child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                userType = user.getType();
                if (userType == 0) {
                    setContentView(R.layout.activity_cprofile);
                    setupClientViews();
                } else if (userType == 1) {
                    setContentView(R.layout.activity_vprofile);
                    setupVolunteerViews();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERROR", "Failed to read value.", error.toException());
            }
        });

    }

    private void setupClientViews() {
        cNameText = (TextView) findViewById(R.id.profile_name_field);
        cAddressText = (TextView) findViewById(R.id.profile_address_field);
        cPhoneText = (TextView) findViewById(R.id.profile_phone_field);
        cEmailText = (TextView) findViewById(R.id.profile_email_field);
        cNameEdit = (EditText) findViewById(R.id.profile_name_field_edit);
        cAddressEdit = (EditText) findViewById(R.id.profile_address_field_edit);
        cPhoneEdit = (EditText) findViewById(R.id.profile_phone_field_edit);

        cNameText.setText(user.getFirst() + " " + user.getLast());
        cNameEdit.setText(user.getFirst() + " " + user.getLast());
        cAddressText.setText(user.getAddress());
        cAddressEdit.setText(user.getAddress());
        cPhoneText.setText(user.getPhone());
        cPhoneEdit.setText(user.getPhone());
        cEmailText.setText(user.getEmail());
    }

    private void setupVolunteerViews() {
        vNameText = (TextView) findViewById(R.id.profile_name_field);
        vAddressText = (TextView) findViewById(R.id.profile_address_field);
        vPhoneText = (TextView) findViewById(R.id.profile_phone_field);
        vEmailText = (TextView) findViewById(R.id.profile_email_field);
        vMakeText = (TextView) findViewById(R.id.profile_car_make_field);
        vPlateText = (TextView) findViewById(R.id.profile_license_plate_field);
        vSeatsText = (TextView) findViewById(R.id.profile_available_seats_field);
        vHandicapText = (TextView) findViewById(R.id.profile_handicapable_field);
        vNameEdit = (EditText) findViewById(R.id.profile_name_field_edit);
        vAddressEdit = (EditText) findViewById(R.id.profile_address_field_edit);
        vPhoneEdit = (EditText) findViewById(R.id.profile_phone_field_edit);
        vMakeEdit = (EditText) findViewById(R.id.profile_car_make_field_edit);
        vPlateEdit = (EditText) findViewById(R.id.profile_license_plate_field_edit);
        vSeatsEdit = (EditText) findViewById(R.id.profile_available_seats_field_edit);
        vHandicapEdit = (CheckBox) findViewById(R.id.profile_handicapable_field_edit);

        vNameText.setText(user.getFirst() + " " + user.getLast());
        vNameEdit.setText(user.getFirst() + " " + user.getLast());
        vAddressText.setText(user.getAddress());
        vAddressEdit.setText(user.getAddress());
        vPhoneText.setText(user.getPhone());
        vPhoneEdit.setText(user.getPhone());
        vEmailText.setText(user.getEmail());
        vMakeText.setText(user.getMake() + " " + user.getModel());
        vMakeEdit.setText(user.getMake() + " " + user.getModel());
        vPlateText.setText(user.getPlate());
        vPlateEdit.setText(user.getPlate());
        vSeatsText.setText("" + user.getSeats());
        vSeatsEdit.setText("" + user.getSeats());
        if (user.getHandicap()) {
            vHandicapText.setText("Yes");
            vHandicapEdit.setChecked(true);
        } else {
            vHandicapText.setText("No");
            vHandicapEdit.setChecked(false);
        }
    }

    public void updateUserToDatabse() {
        db.child("users").child(auth.getCurrentUser().getUid()).setValue(user);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if (userType == 0) {
                    startActivity(new Intent(ProfileActivity.this, ClientDashActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(ProfileActivity.this, VolunteerDashActivity.class));
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
