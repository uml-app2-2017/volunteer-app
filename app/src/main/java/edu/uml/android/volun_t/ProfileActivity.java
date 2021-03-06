package edu.uml.android.volun_t;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

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
    TextView vNameText, vAddressText, vPhoneText, vMakeText, vModelText, vPlateText, vSeatsText, vHandicapText, vEmailText;
    EditText vNameEdit, vAddressEdit, vPhoneEdit, vMakeEdit, vModelEdit, vPlateEdit, vSeatsEdit;
    CheckBox vHandicapEdit;

    FloatingActionButton edit_button, cancel_button;
    private Boolean edit_mode = FALSE;

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
        db.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                userType = user.getType();
                if (userType == 0) {
                    setContentView(R.layout.activity_cprofile);
                    setupClientViews();
                    edit_button = (FloatingActionButton) findViewById(R.id.edit_profile_button);
                    cancel_button = (FloatingActionButton) findViewById(R.id.cancel_edit_profile_button);

                    edit_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(edit_mode == FALSE) {
                                edit_button.setImageResource(R.mipmap.ic_done_white);
                                cancel_button.setVisibility(View.VISIBLE); //

                                cNameText.setVisibility(View.GONE);
                                cAddressText.setVisibility(View.GONE);
                                cPhoneText.setVisibility(View.GONE);

                                cNameEdit.setVisibility(View.VISIBLE);
                                cAddressEdit.setVisibility(View.VISIBLE);
                                cPhoneEdit.setVisibility(View.VISIBLE);

                                edit_mode = TRUE;
                            } else if (edit_mode == TRUE){
                                edit_button.setImageResource(R.mipmap.ic_pencil_white);
                                cancel_button.setVisibility(View.GONE); //

                                cNameText.setVisibility(View.VISIBLE);
                                cAddressText.setVisibility(View.VISIBLE);
                                cPhoneText.setVisibility(View.VISIBLE);

                                cNameEdit.setVisibility(View.GONE);
                                cAddressEdit.setVisibility(View.GONE);
                                cPhoneEdit.setVisibility(View.GONE);

                                edit_mode = FALSE;

                                // Temp place holder for setting to Firebase
                                cNameText.setText(cNameEdit.getText());
                                cAddressText.setText(cAddressEdit.getText());
                                cPhoneText.setText(cPhoneEdit.getText());

                                int temp = cNameText.getText().toString().lastIndexOf(" ");
                                user.setFirst(cNameText.getText().toString().substring(0, temp));
                                user.setLast(cNameText.getText().toString().substring(temp+1, cNameText.getText().toString().length()));
                                user.setPhone(cPhoneText.getText().toString());
                                user.setAddress(cAddressText.getText().toString());
                                updateUserToDatabase();
                            }
                        }
                    });

                    cancel_button.setOnClickListener(new View.OnClickListener() { //
                        @Override
                        public void onClick(View view) {
                            edit_button.setImageResource(R.mipmap.ic_pencil_white);
                            cancel_button.setVisibility(View.GONE);

                            cNameText.setVisibility(View.VISIBLE);
                            cAddressText.setVisibility(View.VISIBLE);
                            cPhoneText.setVisibility(View.VISIBLE);

                            cNameEdit.setVisibility(View.GONE);
                            cAddressEdit.setVisibility(View.GONE);
                            cPhoneEdit.setVisibility(View.GONE);

                            cNameEdit.setText(cNameText.getText());
                            cAddressEdit.setText(cAddressText.getText());
                            cPhoneEdit.setText(cPhoneText.getText());

                            edit_mode = FALSE;
                        }
                    });
                } else if (userType == 1) {
                    setContentView(R.layout.activity_vprofile);
                    setupVolunteerViews();
                    edit_button = (FloatingActionButton) findViewById(R.id.edit_profile_button);
                    cancel_button = (FloatingActionButton) findViewById(R.id.cancel_edit_profile_button); //

                    edit_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(edit_mode == FALSE) {
                                edit_button.setImageResource(R.mipmap.ic_done_white);
                                cancel_button.setVisibility(View.VISIBLE); //

                                vNameText.setVisibility(View.GONE);
                                vAddressText.setVisibility(View.GONE);
                                vPhoneText.setVisibility(View.GONE);
                                vMakeText.setVisibility(View.GONE);
                                vModelText.setVisibility(View.GONE);
                                vPlateText.setVisibility(View.GONE);
                                vSeatsText.setVisibility(View.GONE);
                                vHandicapText.setVisibility(View.GONE);

                                vNameEdit.setVisibility(View.VISIBLE);
                                vAddressEdit.setVisibility(View.VISIBLE);
                                vPhoneEdit.setVisibility(View.VISIBLE);
                                vMakeEdit.setVisibility(View.VISIBLE);
                                vModelEdit.setVisibility(View.VISIBLE);
                                vPlateEdit.setVisibility(View.VISIBLE);
                                vSeatsEdit.setVisibility(View.VISIBLE);
                                vHandicapEdit.setVisibility(View.VISIBLE);

                                edit_mode = TRUE;
                            } else if (edit_mode == TRUE){
                                edit_button.setImageResource(R.mipmap.ic_pencil_white);
                                cancel_button.setVisibility(View.GONE); //

                                vNameText.setVisibility(View.VISIBLE);
                                vAddressText.setVisibility(View.VISIBLE);
                                vPhoneText.setVisibility(View.VISIBLE);
                                vMakeText.setVisibility(View.VISIBLE);
                                vModelText.setVisibility(View.VISIBLE);
                                vPlateText.setVisibility(View.VISIBLE);
                                vSeatsText.setVisibility(View.VISIBLE);
                                vHandicapText.setVisibility(View.VISIBLE);

                                vNameEdit.setVisibility(View.GONE);
                                vAddressEdit.setVisibility(View.GONE);
                                vPhoneEdit.setVisibility(View.GONE);
                                vMakeEdit.setVisibility(View.GONE);
                                vModelEdit.setVisibility(View.GONE);
                                vPlateEdit.setVisibility(View.GONE);
                                vSeatsEdit.setVisibility(View.GONE);
                                vHandicapEdit.setVisibility(View.GONE);

                                edit_mode = FALSE;

                                vNameText.setText(vNameEdit.getText());
                                vAddressText.setText(vAddressEdit.getText());
                                vPhoneText.setText(vPhoneEdit.getText());
                                vMakeText.setText(vMakeEdit.getText());
                                vModelText.setText(vModelEdit.getText());
                                vPlateText.setText(vPlateEdit.getText());
                                vSeatsText.setText(vSeatsEdit.getText());
                                if(vHandicapEdit.isChecked()){
                                    vHandicapText.setText("Yes");
                                } else {vHandicapText.setText("No");}

                                // Update to firebase db
                                int temp = vNameText.getText().toString().lastIndexOf(" ");
                                user.setFirst(vNameText.getText().toString().substring(0, temp));
                                user.setLast(vNameText.getText().toString().substring(temp+1, vNameText.getText().toString().length()));
                                user.setPhone(vPhoneText.getText().toString());
                                user.setAddress(vAddressText.getText().toString());
                                user.setMake(vMakeText.getText().toString());
                                user.setModel(vModelText.getText().toString());
                                user.setPlate(vPlateText.getText().toString());
                                user.setSeats(Integer.parseInt(vSeatsText.getText().toString()));
                                user.setHandicap(vHandicapEdit.isChecked());
                                updateUserToDatabase();
                            }
                        }
                    });

                    cancel_button.setOnClickListener(new View.OnClickListener() { //
                        @Override
                        public void onClick(View view) {
                            InputMethodManager inputManager = (InputMethodManager)
                                    getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ?
                                    null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            edit_button.setImageResource(R.mipmap.ic_pencil_white);
                            cancel_button.setVisibility(View.GONE);

                            vNameText.setVisibility(View.VISIBLE);
                            vAddressText.setVisibility(View.VISIBLE);
                            vPhoneText.setVisibility(View.VISIBLE);
                            vMakeText.setVisibility(View.VISIBLE);
                            vModelText.setVisibility(View.VISIBLE);
                            vPlateText.setVisibility(View.VISIBLE);
                            vSeatsText.setVisibility(View.VISIBLE);
                            vHandicapText.setVisibility(View.VISIBLE);

                            vNameEdit.setVisibility(View.GONE);
                            vAddressEdit.setVisibility(View.GONE);
                            vPhoneEdit.setVisibility(View.GONE);
                            vMakeEdit.setVisibility(View.GONE);
                            vModelEdit.setVisibility(View.GONE);
                            vPlateEdit.setVisibility(View.GONE);
                            vSeatsEdit.setVisibility(View.GONE);
                            vHandicapEdit.setVisibility(View.GONE);

                            vNameEdit.setText(vNameText.getText());
                            vAddressEdit.setText(vAddressText.getText());
                            vPhoneEdit.setText(vPhoneText.getText());
                            vMakeEdit.setText(vMakeText.getText());
                            vModelEdit.setText(vModelText.getText());
                            vPlateEdit.setText(vPlateText.getText());
                            vSeatsEdit.setText(vSeatsText.getText());
                            if(vHandicapText.getText() == "Yes"){
                                vHandicapEdit.setChecked(TRUE);
                            } else {vHandicapEdit.setChecked(FALSE);}

                            edit_mode = FALSE;
                        }
                    });
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
        vModelText = (TextView) findViewById(R.id.profile_car_model_field);
        vPlateText = (TextView) findViewById(R.id.profile_license_plate_field);
        vSeatsText = (TextView) findViewById(R.id.profile_available_seats_field);
        vHandicapText = (TextView) findViewById(R.id.profile_handicapable_field);
        vNameEdit = (EditText) findViewById(R.id.profile_name_field_edit);
        vAddressEdit = (EditText) findViewById(R.id.profile_address_field_edit);
        vPhoneEdit = (EditText) findViewById(R.id.profile_phone_field_edit);
        vMakeEdit = (EditText) findViewById(R.id.profile_car_make_field_edit);
        vModelEdit = (EditText) findViewById(R.id.profile_car_model_edit);
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
        vMakeText.setText(user.getMake());
        vMakeEdit.setText(user.getMake());
        vModelText.setText(user.getModel());
        vModelEdit.setText(user.getModel());
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

    public void updateUserToDatabase() {
        db.child("users").child(auth.getCurrentUser().getUid()).setValue(user);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onNavigateUp();
        super.onBackPressed();
    }

}
