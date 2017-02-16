package edu.uml.android.volun_t;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText inputEmail, inputPassword;
    private Button buttonSignUp, buttonCancel;
    private TextView textAlreadyHaveAcct;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        // Get the firebase authentication instance
        auth = FirebaseAuth.getInstance();
        /* Find the views
        inputEmail = (EditText) findViewById(R.id.email_field);
        inputPassword = (EditText) findViewById(R.id.password_field);
        buttonSignUp = (Button) findViewById(R.id.signup_button);
        buttonCancel = (Button) findViewById(R.id.cancel_button);
        textAlreadyHaveAcct = (TextView) findViewById(R.id.already_text);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        */
        // Set up on click listeners
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Make sure the email and password fields are filled in
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                // If email is empty
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter an email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // If password is empty
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter a password! (Must be 6 or more characters long.)", Toast.LENGTH_SHORT).show();
                    return;
                }
                // If password is too short
                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password must be 6 or more characters long.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // We should be good to go, no create the user
                progressBar.setVisibility(View.VISIBLE);
                auth.createUserWithEmailAndPassword(email, password)
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
                                    Toast.makeText(SignUpActivity.this, "Account created!" + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        textAlreadyHaveAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}