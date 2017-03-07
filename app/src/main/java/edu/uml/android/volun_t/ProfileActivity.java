package edu.uml.android.volun_t;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

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
                } else if (userType == 1) {
                    setContentView(R.layout.activity_vprofile);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERROR", "Failed to read value.", error.toException());
            }
        });

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
