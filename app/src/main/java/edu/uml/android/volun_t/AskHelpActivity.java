package edu.uml.android.volun_t;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by adam on 3/2/17.
 */

public class AskHelpActivity extends AppCompatActivity {

    FirebaseUser user;
    DatabaseReference db;
    Button mPostButton, mCancelButton;
    EditText mTitleField, mDescriptionField, mStreetField, mCityField, mStateField, mZipField, mSeatsField;
    DatePicker mDateField;
    TimePicker mTimeField;
    CheckBox mHandicapField;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_askhelp);

        // Get all the fields
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference();
        mPostButton = (Button) findViewById(R.id.post_button);
        mCancelButton = (Button) findViewById(R.id.cancel_button);
        mTitleField = (EditText) findViewById(R.id.title_field);
        mDescriptionField = (EditText) findViewById(R.id.description_field);
        mStreetField = (EditText) findViewById(R.id.street_field);
        mCityField = (EditText) findViewById(R.id.city_field);
        mStateField = (EditText) findViewById(R.id.state_field);
        mZipField = (EditText) findViewById(R.id.zip_field);
        mSeatsField = (EditText) findViewById(R.id.seats_field);
        mDateField = (DatePicker) findViewById(R.id.date_field);
        mTimeField = (TimePicker) findViewById(R.id.time_field);
        mHandicapField = (CheckBox) findViewById(R.id.handicap_field);

        // On click listeners for the buttons
        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void submitPost() {
        if (!checkFields()) return;

        final UUID postId = UUID.randomUUID();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Calendar now = Calendar.getInstance();
        Calendar when = Calendar.getInstance();
        String title = mTitleField.getText().toString();
        String desc = mDescriptionField.getText().toString();
        String street = mStreetField.getText().toString();
        String city = mCityField.getText().toString();
        String state = mStateField.getText().toString();
        state = state.toUpperCase();
        String zip = mZipField.getText().toString();
        String seats = mSeatsField.getText().toString();
        boolean handicap = mHandicapField.isChecked();
        when.set(Calendar.YEAR, mDateField.getYear());
        when.set(Calendar.MONTH, mDateField.getMonth());
        when.set(Calendar.DAY_OF_MONTH, mDateField.getDayOfMonth());
        when.set(Calendar.HOUR, mTimeField.getHour());
        when.set(Calendar.MINUTE, mTimeField.getMinute());
        String location = street + " " + city + ", " + state + ", " + zip;

        Post post = new Post(now, when, null, uid, null, location, title, desc, Integer.parseInt(seats), handicap, false, postId.toString());
        db.child("pendingPosts").child(postId.toString()).setValue(post);

        db.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                ArrayList<String> temp = currentUser.getPendingPosts();
                temp.add(postId.toString());
                currentUser.setWaitingPosts(temp);
                db.child("users").child(user.getUid()).setValue(currentUser);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERROR", "Failed to read value.", error.toException());
            }
        });

        Toast.makeText(AskHelpActivity.this, "Post Submitted!", Toast.LENGTH_SHORT).show();
        finish();
    }

    public boolean checkFields() {
        boolean allClear = true;
        Calendar calendar = Calendar.getInstance();
        if (mTitleField.getText().length() == 0) {
            mTitleField.setError("Cannot be empty.");
            allClear = false;
        }
        if (mDescriptionField.getText().length() == 0) {
            mDescriptionField.setError("Cannot be empty.");
            allClear = false;
        }
        if (mStreetField.getText().length() == 0) {
            mStreetField.setError("Cannot be empty.");
            allClear = false;
        }
        if (mCityField.getText().length() == 0) {
            mCityField.setError("Cannot be empty.");
            allClear = false;
        }
        if (mStateField.getText().length() == 0) {
            mStateField.setError("Cannot be empty.");
            allClear = false;
        }
        if (mZipField.getText().length() == 0) {
            mZipField.setError("Cannot be empty.");
            allClear = false;
        }
        if (mSeatsField.getText().length() == 0) {
            mSeatsField.setError("Cannot be empty.");
            allClear = false;
        }
        // If year is behind
        if (mDateField.getYear() < calendar.get(Calendar.YEAR)) {
            Toast.makeText(AskHelpActivity.this, "Date cannot be in the past.", Toast.LENGTH_SHORT).show();
            allClear = false;
        }
        // If month is behind
        if (mDateField.getYear() <= calendar.get(Calendar.YEAR) && mDateField.getMonth() < calendar.get(Calendar.MONTH)) {
            Toast.makeText(AskHelpActivity.this, "Date cannot be in the past.", Toast.LENGTH_SHORT).show();
            allClear = false;
        }
        // If day is behind
        if (mDateField.getMonth() <= calendar.get(Calendar.MONTH) && mDateField.getDayOfMonth() < calendar.get(Calendar.DAY_OF_MONTH)) {
            Toast.makeText(AskHelpActivity.this, "Date cannot be in the past.", Toast.LENGTH_SHORT).show();
            allClear = false;
        }

        if (allClear)
            return true;
        else
            return false;
    }

}
