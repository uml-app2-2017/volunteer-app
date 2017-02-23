package edu.uml.android.volun_t;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by adam on 2/16/17.
 */

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference db;
    private Button loginButton;
    private EditText emailField, passwordField;
    private int mUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        loginButton = (Button) findViewById(R.id.login_button);
        emailField = (EditText) findViewById(R.id.sign_in_email_field);
        passwordField = (EditText) findViewById(R.id.sign_in_password_field);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailField.getText().toString().trim();
                final String password = passwordField.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignInActivity.this, "Auth Failed:  " + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    if (getUserType() == 1) {
                                        if (mUserType == 0) {
                                            startActivity(new Intent(SignInActivity.this, ClientDashActivity.class));
                                            finish();
                                        }
                                        if (mUserType == 1) {
                                            startActivity(new Intent(SignInActivity.this, VolunteerDashActivity.class));
                                            finish();
                                        }
                                    } else {
                                        Toast.makeText(SignInActivity.this, "Error signing in. Try again later.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });

    }

    protected int getUserType() {
        // Get user ID
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String uid = firebaseUser.getUid();
        int type = -1;

        db.child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                mUserType = user.getType();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERROR", "Failed to read value.", error.toException());
            }
        });

        return 1;
    }


}