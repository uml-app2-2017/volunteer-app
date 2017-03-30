package edu.uml.android.volun_t;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        loginButton = (Button) findViewById(R.id.login_button);
        emailField = (EditText) findViewById(R.id.sign_in_email_field);
        passwordField = (EditText) findViewById(R.id.sign_in_password_field);
        TextView forgot = (TextView) findViewById(R.id.forgot_password);
        forgot.setPaintFlags(forgot.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        // Send password reset email if user forgot it
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.child("users").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
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
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Set loading icon visibility to true
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

                auth = FirebaseAuth.getInstance();
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // TODO Set loading icon visibility to false
                                if (!task.isSuccessful()) {
                                    passwordField.setError("Password incorrect or account does not exist.");
                                } else {
                                    if (auth.getCurrentUser().isEmailVerified())
                                        getUserTypeAndLogin();
                                    else
                                        createPopup();
                                }
                            }
                        });
            }
        });

    }

    protected void createPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your email has not been verified, so you may not sign in at this time." +
                " Would you like to resend the verification email?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            user.sendEmailVerification();
                            auth.signOut();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        auth.signOut();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    protected void getUserTypeAndLogin() {
        // Get user ID
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String uid = firebaseUser.getUid();

        db.child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                int type = user.getType();
                if (type == 0) {
                    startActivity(new Intent(SignInActivity.this, ClientDashActivity.class));
                    finish();
                } else if (type == 1) {
                    startActivity(new Intent(SignInActivity.this, VolunteerDashActivity.class));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        super.onNavigateUp();
    }



}