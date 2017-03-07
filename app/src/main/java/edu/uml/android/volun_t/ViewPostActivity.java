package edu.uml.android.volun_t;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by adam on 3/7/17.
 */

public class ViewPostActivity extends AppCompatActivity {

    User user;
    DatabaseReference db;
    Post post;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String category;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpost);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseDatabase.getInstance().getReference();
        post = (Post) getIntent().getSerializableExtra("post");
        category = getIntent().getStringExtra("category");

        // Get the type of user and set view accordingly
        db.child("users").child(uid).addValueEventListener(new ValueEventListener() {
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
        Button accept = (Button) findViewById(R.id.accept_button);
        Button complete = (Button) findViewById(R.id.complete_button);
        Button cancel = (Button) findViewById(R.id.cancel_button);
        Button back = (Button) findViewById(R.id.back_button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (user.getType() == 0) {
            if (category.equals("pending")) {
                cancel.setVisibility(View.VISIBLE);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Cancel plan here and delete post
                    }
                });
            } else if (category.equals("accepted")) {
                cancel.setVisibility(View.VISIBLE);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Cancel plan here and delete post
                    }
                });
                complete.setVisibility(View.VISIBLE);
                complete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Change status of post to complete
                    }
                });
            }
        } else if (user.getType() == 1) {
            if (category.equals("accepted")) {
                cancel.setVisibility(View.VISIBLE);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Cancel plan here
                    }
                });
            }
            if (category.equals("pending")) {
                accept.setVisibility(View.VISIBLE);
                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Accept post here
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
                if (user.getType() == 0) {
                    startActivity(new Intent(ViewPostActivity.this, PlansCActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(ViewPostActivity.this, PlansVActivity.class));
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
