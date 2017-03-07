package edu.uml.android.volun_t;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by adam on 3/6/17.
 */

public class DatabaseUtils {

    private ArrayList<Post> pendingPosts = new ArrayList<>();
    private ArrayList<Post> acceptedPosts = new ArrayList<>();
    private ArrayList<Post> completedPosts = new ArrayList<>();
    private boolean pen = false, acc = false, com = false;
    int numPending = 0, numAccepted = 0, numCompleted = 0;
    private User currentUser;

    public DatabaseUtils() {
        getUser();
    }

    public boolean isDone() {
        if (pen && acc && com) {
            return true;
        } else
            return false;
    }

    public ArrayList<Post> getCurrentUserPendingPosts() {
        return pendingPosts;
    }

    public ArrayList<Post> getCurrentUserAcceptedPosts() {
        return acceptedPosts;
    }

    public ArrayList<Post> getCurrentUserCompletedPosts() {
        return completedPosts;
    }

    private void setPendingPosts() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        for (int i = 0; i < currentUser.getPendingPosts().size(); i++) {
            db.child("pendingPosts").child(currentUser.getPendingPosts().get(i))
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Post post = dataSnapshot.getValue(Post.class);
                    addToPending(post);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void setAcceptedPosts() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        for (int i = 0; i < currentUser.getAcceptedPosts().size(); i++) {
            db.child("acceptedPosts").child(currentUser.getAcceptedPosts().get(i))
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Post post = dataSnapshot.getValue(Post.class);
                    addToAccepted(post);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }
    }

    private void setCompletedPosts() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        for (int i = 0; i < currentUser.getCompletedPosts().size(); i++) {
            db.child("completedPosts").child(currentUser.getPendingPosts().get(i))
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Post post = dataSnapshot.getValue(Post.class);
                            addToCompleted(post);
                            if (DatabaseUtils.this.completedPosts.size() == numCompleted)
                                com = true;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
    }

    private void addToPending(Post post) {
        pendingPosts.add(post);
        if (pendingPosts.size() == numPending)
            pen = true;
    }

    private void addToAccepted(Post post) {
        acceptedPosts.add(post);
        if (acceptedPosts.size() == numAccepted)
            acc = true;
    }

    private void addToCompleted(Post post) {
        completedPosts.add(post);
        if (completedPosts.size() == numCompleted)
            pen = true;
    }

    private void getUser() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                DatabaseUtils.this.setUser(user);
                DatabaseUtils.this.setPendingPosts();
                DatabaseUtils.this.setAcceptedPosts();
                DatabaseUtils.this.setCompletedPosts();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setUser(User user) {
        this.currentUser = user;
        numPending = user.getPendingPosts().size();
        numAccepted = user.getAcceptedPosts().size();
        numCompleted = user.getCompletedPosts().size();
        if (numPending == 0) pen = true;
        if (numAccepted == 0) acc = true;
        if (numCompleted == 0) com = true;
    }

}
