package edu.uml.android.volun_t;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference db;
    private EditText inputEmail, inputPassword, inputPassword2;
    private EditText inputFirstV, inputLastV, inputAddressV, inputPhoneV, inputMake, inputModel, inputLicense, inputSeats;
    private EditText inputFirstC, inputLastC, inputAddressC, inputPhoneC;
    private CheckBox checkHandicap;
    private Button buttonSignUp, buttonCancel;
    private RadioButton radioOffer, radioNeed;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Get the firebase authentication instance
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
        // Find the views
        inputEmail = (EditText) findViewById(R.id.email_field);
        inputPassword = (EditText) findViewById(R.id.password_field);
        inputPassword2 = (EditText) findViewById(R.id.password_field_2);
        inputFirstV = (EditText) findViewById(R.id.first_name_volunteer_field);
        inputLastV = (EditText) findViewById(R.id.last_name_volunteer_field);
        inputAddressV = (EditText) findViewById(R.id.address_volunteer_field);
        inputPhoneV = (EditText) findViewById(R.id.phone_volunteer_field);
        inputMake = (EditText) findViewById(R.id.make_field);
        inputModel = (EditText) findViewById(R.id.model_field);
        inputLicense = (EditText) findViewById(R.id.license_plate_field);
        inputSeats = (EditText) findViewById(R.id.seats_field);
        inputFirstC = (EditText) findViewById(R.id.first_name_client_field);
        inputLastC = (EditText) findViewById(R.id.last_name_client_field);
        inputAddressC = (EditText) findViewById(R.id.address_client_field);
        inputPhoneC = (EditText) findViewById(R.id.phone_client_field);
        checkHandicap = (CheckBox) findViewById(R.id.handicap_field);
        buttonSignUp = (Button) findViewById(R.id.signup_button);
        buttonCancel = (Button) findViewById(R.id.cancel_button);
        radioNeed = (RadioButton) findViewById(R.id.radio_need_help);
        radioOffer = (RadioButton) findViewById(R.id.radio_offer_help);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        // Set up on click listeners
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Make sure all fields have been entered
                final User user = createUser();
                if(!validateFields(user)) return;
                // We should be good to go, no create the user
                progressBar.setVisibility(View.VISIBLE);
                auth.createUserWithEmailAndPassword(user.getEmail(), user.getPass())
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Error in creating account: " + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Account created!",
                                            Toast.LENGTH_SHORT).show();
                                    addUserToDatabase(user);
                                    startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });

        radioNeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout needView = (LinearLayout) findViewById(R.id.layout_need_help);
                LinearLayout offerView = (LinearLayout) findViewById(R.id.layout_offer_help);
                LinearLayout vehicleView = (LinearLayout) findViewById(R.id.layout_vehicle_info);
                needView.setVisibility(View.VISIBLE);
                offerView.setVisibility(View.GONE);
                vehicleView.setVisibility(View.GONE);
                buttonSignUp.setVisibility(View.VISIBLE);
                buttonCancel.setVisibility(View.VISIBLE);
            }
        });

        radioOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout needView = (LinearLayout) findViewById(R.id.layout_need_help);
                LinearLayout offerView = (LinearLayout) findViewById(R.id.layout_offer_help);
                LinearLayout vehicleView = (LinearLayout) findViewById(R.id.layout_vehicle_info);
                needView.setVisibility(View.GONE);
                offerView.setVisibility(View.VISIBLE);
                vehicleView.setVisibility(View.VISIBLE);
                buttonSignUp.setVisibility(View.VISIBLE);
                buttonCancel.setVisibility(View.VISIBLE);
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, StartupActivity.class));
            }
        });
    }

    protected User createUser() {
        String email = inputEmail.getText().toString().trim();
        String pass1 = inputPassword.getText().toString().trim();
        String pass2 = inputPassword2.getText().toString().trim();

        String first = "", last = "", address = "", phone = "", make = "", model = "", plate = "";
        int seats = 0, type = -1;
        boolean handicap = false;

        if (radioOffer.isChecked()) {
            type = 1;
            first = inputFirstV.getText().toString().trim();
            last = inputLastV.getText().toString().trim();
            address = inputAddressV.getText().toString().trim();
            phone = inputPhoneV.getText().toString().trim();
            make = inputMake.getText().toString().trim();
            model = inputModel.getText().toString().trim();
            plate = inputLicense.getText().toString().trim();
            if (!TextUtils.isEmpty(inputSeats.getText().toString().trim()))
                seats = Integer.parseInt(inputSeats.getText().toString().trim());
            handicap = checkHandicap.isChecked();
        } else if (radioNeed.isChecked()) {
            type = 0;
            first = inputFirstC.getText().toString().trim();
            last = inputLastC.getText().toString().trim();
            address = inputAddressC.getText().toString().trim();
            phone = inputPhoneC.getText().toString().trim();
        }

        return new User(email, pass1, pass2, first, last, address, phone, make, model, plate, seats, type, handicap);

    }

    // Returns TRUE if all values in fields are enough to sign up for an account
    protected boolean validateFields(User user) {
        // If passwords do not match
        if (!user.getPass().equals(user.getPass2())) {
            Toast.makeText(getApplicationContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return false;
        }
        // If password is too short
        if (user.getPass().length() < 6) {
            Toast.makeText(getApplicationContext(), "Password must be 6 or more characters long.", Toast.LENGTH_SHORT).show();
            return false;
        }
        // See if other fields were missing
        if (TextUtils.isEmpty(user.getEmail()) || TextUtils.isEmpty(user.getFirst()) || TextUtils.isEmpty(user.getLast()) ||
                TextUtils.isEmpty(user.getAddress()) || TextUtils.isEmpty(user.getPhone())) {
            Toast.makeText(getApplicationContext(), "Missing personal information, please check.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (user.getType() == 1) {
            if (TextUtils.isEmpty(user.getMake()) || TextUtils.isEmpty(user.getModel()) || TextUtils.isEmpty(user.getPlate()) ||
                user.getSeats() <= 0) {
                Toast.makeText(getApplicationContext(), "Missing vehicle information, please check.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    protected boolean addUserToDatabase(User user) {
        // Get user ID
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String uid = firebaseUser.getUid();

        db.child("users").child(uid).child("email").setValue(user.getEmail());
        db.child("users").child(uid).child("first_name").setValue(user.getFirst());
        db.child("users").child(uid).child("last_name").setValue(user.getLast());
        db.child("users").child(uid).child("address").setValue(user.getAddress());
        db.child("users").child(uid).child("phone_number").setValue(user.getPhone());

        if (user.getType() == 0) {
            db.child("users").child(uid).child("type").setValue("client");
        } else if (user.getType() == 1) {
            db.child("users").child(uid).child("type").setValue("volunteer");
            db.child("users").child(uid).child("make").setValue(user.getMake());
            db.child("users").child(uid).child("model").setValue(user.getModel());
            db.child("users").child(uid).child("license_plate").setValue(user.getPlate());
            db.child("users").child(uid).child("number_of_seats").setValue(user.getSeats());
            db.child("users").child(uid).child("handicap_access").setValue(user.getHandicap());
        } else {
            db.child("users").child(uid).child("type").setValue("!UNKNOWN!");
            return false;
        }

        return true;
    }

}