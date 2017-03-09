package edu.uml.android.volun_t;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by adam on 3/5/17.
 */

public class PostAdapter extends ArrayAdapter<Post> {

    private String category;

    public PostAdapter(Context context, ArrayList<Post> posts, String category) {
        super(context, 0, posts);
        this.category = category;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.post_item, parent, false);
        }

        final Post currentPost = getItem(position);

        // Set stuff here
        TextView title = (TextView) listItemView.findViewById(R.id.title);
        title.setText(currentPost.getTitle());
        TextView dateTime = (TextView) listItemView.findViewById(R.id.date_time);
        dateTime.setText(currentPost.getTimeScheduled());
        TextView desc = (TextView) listItemView.findViewById(R.id.description);
        desc.setText(currentPost.getDescription());
        TextView location = (TextView) listItemView.findViewById(R.id.location);
        location.setText(currentPost.getLocation());

        // On click listener for post
        LinearLayout postLayout = (LinearLayout) listItemView.findViewById(R.id.post);
        postLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewPostActivity.class);
                intent.putExtra("post", currentPost);
                intent.putExtra("category", category);
                getContext().startActivity(intent);
            }
        });

        return listItemView;
    }

}
