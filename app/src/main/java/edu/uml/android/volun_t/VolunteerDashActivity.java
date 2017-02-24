package edu.uml.android.volun_t;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by adam on 2/22/17.
 */

public class VolunteerDashActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vdash);

        auth = FirebaseAuth.getInstance();
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
