package edu.uml.android.volun_t;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by adam on 3/5/17.
 */

public class PostAdapter extends ArrayAdapter<Post> {

    public PostAdapter(Context context, ArrayList<Post> posts) {
        super(context, 0, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.post_item, parent, false);
        }

        Post currentPost = getItem(position);

        // Set stuff here
        TextView title = (TextView) listItemView.findViewById(R.id.title);
        title.setText(currentPost.getTitle());
        TextView name = (TextView) listItemView.findViewById(R.id.name);
        name.setText("NAME GOES HERE");
        TextView desc = (TextView) listItemView.findViewById(R.id.description);
        desc.setText(currentPost.getDescription());

        return listItemView;
    }

}
