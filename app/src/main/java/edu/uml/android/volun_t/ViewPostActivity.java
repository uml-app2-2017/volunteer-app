package edu.uml.android.volun_t;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by adam on 3/7/17.
 */

public class ViewPostActivity extends AppCompatActivity {

    User user;
    DatabaseReference db;
    Post post;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String category;
    static boolean sent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpost);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        sent = false;

        db = FirebaseDatabase.getInstance().getReference();
        post = (Post) getIntent().getSerializableExtra("post");
        category = getIntent().getStringExtra("category");

        // Get the type of user and set view accordingly
        db.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                setupViews();
                setupButtons();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERROR", "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public void onResume() {
        sent = false;
        super.onResume();
    }

    public void setupViews() {
        TextView title = (TextView) findViewById(R.id.title);
        TextView description = (TextView) findViewById(R.id.description);
        TextView location = (TextView) findViewById(R.id.location);
        TextView timePosted = (TextView) findViewById(R.id.time_posted);
        TextView timeRequested = (TextView) findViewById(R.id.time_requested);
        TextView timeCompleted = (TextView) findViewById(R.id.time_completed);
        TextView requester = (TextView) findViewById(R.id.requester);
        TextView taker = (TextView) findViewById(R.id.taker);
        TextView status = (TextView) findViewById(R.id.status);

        title.setText(post.getTitle());
        description.setText(post.getDescription());
        location.setText(post.getLocation());
        timePosted.setText("Submitted on: " + post.getTimePosted());
        timeRequested.setText("Scheduled for: " + post.getTimeScheduled());
        if (post.getTimeCompleted() == null)
            timeCompleted.setText("Completed on: Not yet completed.");
        else
            timeCompleted.setText("Completed on: " + post.getTimeCompleted());
        requester.setText("Requested by: " + post.getRequesterName());
        if (post.getTakerName() == null)
            taker.setText("Taken by: Not yet taken.");
        else
            taker.setText("Taken by: " + post.getTakerName());
        if (post.isCompleted()) {
            status.setText("COMPLETED");
            status.setTextColor(getResources().getColor(R.color.colorGreenFont));
        } else if (post.getTakerName() != null) {
            status.setText("ACCEPTED");
            status.setTextColor(getResources().getColor(R.color.colorGreenFont));
        } else {
            status.setText("PENDING");
            status.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    public void setupButtons() {
        Button here = (Button) findViewById(R.id.here_button);
        Button accept = (Button) findViewById(R.id.accept_button);
        Button complete = (Button) findViewById(R.id.complete_button);
        Button cancel = (Button) findViewById(R.id.cancel_button);
        Button back = (Button) findViewById(R.id.back_button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (user.getType() == 0) {
            if (category.equals("pending")) {
                cancel.setVisibility(View.VISIBLE);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewPostActivity.this);
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        // Remove from user's pending
                                        ArrayList<String> temp = user.getPendingPosts();
                                        temp.remove(post.getPostId());
                                        user.setWaitingPosts(temp);
                                        db.child("users").child(uid).setValue(user);
                                        // Remove from actual pending
                                        db.child("pendingPosts").child(post.getPostId()).removeValue();
                                        Toast.makeText(ViewPostActivity.this, "Post deleted!", Toast.LENGTH_SHORT).show();
                                        finish();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        dialog.cancel();
                                        break;
                                }
                            }
                        };

                        builder.setMessage("Are you sure you want to delete this post?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();
                    }
                });
            } else if (category.equals("accepted")) {
                cancel.setVisibility(View.VISIBLE);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewPostActivity.this);
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        // Remove from user's accepted
                                        ArrayList<String> temp = user.getAcceptedPosts();
                                        temp.remove(post.getPostId());
                                        user.setAcceptedPosts(temp);
                                        db.child("users").child(uid).setValue(user);
                                        // Remove from taker's accepted
                                        db.child("users").child(post.getTakerUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                User taker = dataSnapshot.getValue(User.class);
                                                ArrayList<String> temp = taker.getAcceptedPosts();
                                                temp.remove(post.getPostId());
                                                taker.setAcceptedPosts(temp);
                                                db.child("users").child(post.getTakerUid()).setValue(taker);
                                            }
                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                        // Remove from actual pending
                                        db.child("acceptedPosts").child(post.getPostId()).removeValue();
                                        Toast.makeText(ViewPostActivity.this, "Post deleted!", Toast.LENGTH_SHORT).show();
                                        sendNotificationToUser(post.getTakerUid(), "You can still help out though!",
                                                user.getFirst() + " has cancelled their plans.");
                                        finish();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        dialog.cancel();
                                        break;
                                }
                            }
                        };

                        builder.setMessage("Are you sure you want to delete this post?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();
                    }
                });
                complete.setVisibility(View.VISIBLE);
                complete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Change status of post to complete
                        // Move post from user's accepted to completed
                        ArrayList<String> acc = user.getAcceptedPosts(), comp = user.getCompletedPosts();
                        acc.remove(post.getPostId());
                        comp.add(post.getPostId());
                        user.setAcceptedPosts(acc);
                        user.setCompletedPosts(comp);
                        db.child("users").child(uid).setValue(user);
                        // Move post from taker's accepted to complted
                        db.child("users").child(post.getTakerUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                User taker = dataSnapshot.getValue(User.class);
                                ArrayList<String> acc = taker.getAcceptedPosts(), comp = taker.getCompletedPosts();
                                acc.remove(post.getPostId());
                                comp.add(post.getPostId());
                                taker.setAcceptedPosts(acc);
                                taker.setCompletedPosts(comp);
                                // Update user level
                                int level = taker.getLevel(), levelProgress = taker.getLevelProgress();
                                levelProgress++;
                                if (levelProgress == 10) {
                                    levelProgress = 0;
                                    level++;
                                }
                                taker.setLevel(level);
                                taker.setLevelProgress(levelProgress);
                                db.child("users").child(post.getTakerUid()).setValue(taker);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        // Move post from acceptedPosts to completedPosts
                        post.setCompleted(true);
                        Calendar cal = Calendar.getInstance();
                        post.setTimeCompleted(post.getFormattedTime(cal));
                        db.child("acceptedPosts").child(post.getPostId()).removeValue();
                        db.child("completedPosts").child(post.getPostId()).setValue(post);
                        Toast.makeText(ViewPostActivity.this, "Post completed!", Toast.LENGTH_SHORT).show();
                        sendNotificationToUser(post.getTakerUid(), "Thank you for helping!",
                                user.getFirst() + " has completed your post!");
                        finish();
                    }
                });
            }
        } else if (user.getType() == 1) {
            if (category.equals("accepted")) {
                here.setVisibility(View.VISIBLE);
                here.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendNotificationToUser(post.getRequesterUid(), user.getFirst() + " is waiting for you.",
                                "Your ride is here!");
                    }
                });
                cancel.setVisibility(View.VISIBLE);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewPostActivity.this);
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        // Cancel plan here
                                        // Remove post from takers accepted
                                        db.child("users").child(post.getTakerUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                User taker = dataSnapshot.getValue(User.class);
                                                ArrayList<String> acc = taker.getAcceptedPosts();
                                                acc.remove(post.getPostId());
                                                taker.setAcceptedPosts(acc);
                                                db.child("users").child(post.getTakerUid()).setValue(taker);
                                                // Move from requesters accepted to pending
                                                db.child("users").child(post.getRequesterUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        User requester = dataSnapshot.getValue(User.class);
                                                        ArrayList<String> acc = requester.getAcceptedPosts();
                                                        ArrayList<String> pen = requester.getPendingPosts();
                                                        acc.remove(post.getPostId());
                                                        pen.add(post.getPostId());
                                                        requester.setAcceptedPosts(acc);
                                                        requester.setWaitingPosts(pen);
                                                        db.child("users").child(post.getRequesterUid()).setValue(requester);
                                                        post.setTaken(false);
                                                        post.setTakerUid(null);
                                                        post.setTakerName(null);
                                                        // Move from accepted to pending
                                                        db.child("acceptedPosts").child(post.getPostId()).removeValue();
                                                        db.child("pendingPosts").child(post.getPostId()).setValue(post);
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                });
                                            }
                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                        Toast.makeText(ViewPostActivity.this, "Post cancelled!", Toast.LENGTH_SHORT).show();
                                        sendNotificationToUser(post.getRequesterUid(), "Your plan has been reopenned to the public.",
                                                user.getFirst() + " has cancelled your plans.");
                                        finish();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        dialog.cancel();
                                        break;
                                }
                            }
                        };

                        builder.setMessage("Are you sure you want to delete this post?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();
                    }
                });
            }
            if (category.equals("pending")) {
                accept.setVisibility(View.VISIBLE);
                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Accept post here
                        // Add to accepted for user
                        ArrayList<String> acc = user.getAcceptedPosts();
                        acc.add(post.getPostId());
                        user.setAcceptedPosts(acc);
                        db.child("users").child(uid).setValue(user);
                        // Move from pending to accepted for requester
                        db.child("users").child(post.getRequesterUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                User requester = dataSnapshot.getValue(User.class);
                                ArrayList<String> acc = requester.getAcceptedPosts(), pen = requester.getPendingPosts();
                                acc.add(post.getPostId());
                                pen.remove(post.getPostId());
                                requester.setAcceptedPosts(acc);
                                requester.setWaitingPosts(pen);
                                db.child("users").child(post.getRequesterUid()).setValue(requester);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        // Move from pendingPosts to acceptedPosts
                        post.setTaken(true);
                        post.setTakerUid(uid);
                        post.setTakerName(user.getFirst() + " " + user.getLast());
                        db.child("pendingPosts").child(post.getPostId()).removeValue();
                        db.child("acceptedPosts").child(post.getPostId()).setValue(post);
                        Toast.makeText(ViewPostActivity.this, "You accepted this post!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void sendNotificationToUser(String uid, String message, String title) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        Map notification = new HashMap<>();
        notification.put("uid", uid);
        notification.put("message", message);
        notification.put("title", title);

        if (!sent) ref.child("notificationRequests").push().setValue(notification);
        sent = true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        super.onNavigateUp();
    }

}
