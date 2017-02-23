package edu.uml.android.volun_t;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class StartupActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        // Get the firebase authentication instance
        auth = FirebaseAuth.getInstance();

        Button LoginButton = (Button) findViewById(R.id.LoginButton);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(StartupActivity.this,SignInActivity.class));
            }

        });

        Button CreateButton = (Button) findViewById(R.id.CreateButton);
        CreateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(StartupActivity.this,SignUpActivity.class));
            }

        });
    }
}
