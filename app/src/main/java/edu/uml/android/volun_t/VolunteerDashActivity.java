package edu.uml.android.volun_t;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by adam on 2/22/17.
 */

public class VolunteerDashActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference db;
    User user;
    private Button CommunityButton, profileButton, offerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vdash);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        // Get user reference
        db.child("users").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                // Update greetings
                TextView greeting = (TextView) findViewById(R.id.user_greeting_vol);
                TextView level = (TextView) findViewById(R.id.user_level);
                TextView progressText = (TextView) findViewById(R.id.user_level_progress_num);
                ProgressBar progress = (ProgressBar) findViewById(R.id.user_level_progress);
                greeting.setText("Hello, " + user.getFirst() + "!");
                level.setText(Integer.toString(user.getLevel()));
                progress.setProgress(user.getLevelProgress() * 10);
                progressText.setText(Integer.toString(user.getLevelProgress()) + "/10");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERROR", "Failed to read value.", error.toException());
            }
        });

        CommunityButton = (Button) findViewById(R.id.your_community_button_vol);
        profileButton = (Button) findViewById(R.id.your_profile_button_vol);
        offerButton = (Button) findViewById(R.id.offer_help_button_vol);

        CommunityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VolunteerDashActivity.this, CommunityActivity.class));
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VolunteerDashActivity.this, ProfileActivity.class));
            }
        });

        offerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VolunteerDashActivity.this, OfferHelpActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sign_out:
                signUserOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean signUserOut() {
        auth.signOut();
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            Toast.makeText(VolunteerDashActivity.this, "You've been signed out!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(VolunteerDashActivity.this, StartupActivity.class));
            finish();
        }
        return true;
    }

}
