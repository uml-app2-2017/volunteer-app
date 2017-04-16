package edu.uml.android.volun_t;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class ClientDashActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference db;
    private User user;
    private Button profileButton, askHelpButton, plansButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cdash);
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        profileButton = (Button) findViewById(R.id.your_profile_button_client);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ClientDashActivity.this, ProfileActivity.class));
            }
        });

        askHelpButton = (Button) findViewById(R.id.ask_for_help_button_client);
        askHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ClientDashActivity.this, AskHelpActivity.class));
            }
        });

        plansButton = (Button) findViewById(R.id.your_plans_button_client);
        plansButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClientDashActivity.this, PlansCActivity.class));
            }
        });

        // Get user reference
        db.child("users").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                // Update greetings
                TextView greeting = (TextView) findViewById(R.id.user_greeting_client);
                greeting.setText("Hello, " + user.getFirst() + "!");
                // Get number of accepted plans
                plansButton.setText(plansButton.getText() + " (" + user.getAcceptedPosts().size() + ")");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERROR", "Failed to read value.", error.toException());
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
            case R.id.reset_password:
                resetPass();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void resetPass() {
        db.child("users").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String email = user.getEmail();
                auth.sendPasswordResetEmail(email);
                Toast.makeText(getApplicationContext(), "Password reset email sent!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERROR", "Failed to read value.", error.toException());
            }
        });
    }

    public boolean signUserOut() {
        auth.signOut();
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            Toast.makeText(ClientDashActivity.this, "You've been signed out!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ClientDashActivity.this, StartupActivity.class));
            finish();
        }
        return true;
    }

}
