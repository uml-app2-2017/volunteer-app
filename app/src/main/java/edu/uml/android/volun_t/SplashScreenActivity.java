package edu.uml.android.volun_t;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by adam on 2/24/17.
 */

public class SplashScreenActivity extends AppCompatActivity {

    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = FirebaseDatabase.getInstance().getReference();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            getUserTypeAndLogin();
        } else {
            startActivity(new Intent(SplashScreenActivity.this, StartupActivity.class));
        }
    }

    protected void getUserTypeAndLogin() {
        // Get user ID
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();

        FirebaseMessaging.getInstance().subscribeToTopic(uid);

        db.child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                int type = user.getType();
                if (type == 0) {
                    startActivity(new Intent(SplashScreenActivity.this, ClientDashActivity.class));
                    finish();
                } else if (type == 1) {
                    startActivity(new Intent(SplashScreenActivity.this, VolunteerDashActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERROR", "Failed to read value.", error.toException());
            }
        });
    }

}
