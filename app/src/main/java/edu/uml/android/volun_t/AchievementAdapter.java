package edu.uml.android.volun_t;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ashley on 2/28/2017.
 */

public class AchievementAdapter extends ArrayAdapter<Achievement> {
/**
 * Create a new {@link AchievementAdapter} object.
 *
 * @param context is the current context (i.e. Activity) that the adapter is being created in.
 * @param ach is the list of {@link Achievement}s to be displayed.
 */
public AchievementAdapter(Context context, ArrayList<Achievement> ach) {
        super(context, 0, ach);
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.community_item_list, parent, false);
        }

        // Get the {@link Achievement} object located at this position in the list
        final Achievement currentAch = getItem(position);

        // Find the TextView in the community_item_list.xml layout with the ID ach_name_view.
        TextView AchNameTextView = (TextView) listItemView.findViewById(R.id.ach_name_view);
        // Get the Achievement Name from the currentAch object and set this text on
        // the name TextView.
        AchNameTextView.setText(currentAch.getAchName());

        // Find the TextView in the community_item_list.xml layout with the ID ach_desc_view.
        TextView AchDescTextView = (TextView) listItemView.findViewById(R.id.ach_desc_view);
        // Get the Achievement Description from the currentAch object and set this text on
        // the description TextView.
        AchDescTextView.setText(currentAch.getAchDesc());

        // Find the ImageView in the community_list_item.xml layout with the ID starImage.
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.starImage);
        // Check if an image is provided for this achievement or not

        if (currentAch.hasImage()) {
        // If an image is available, display the provided image based on the resource ID
        imageView.setImageResource(currentAch.getImageResourceId());
        // Make sure the view is visible
        imageView.setVisibility(View.VISIBLE);
        } else {
        // Otherwise hide the ImageView (set visibility to GONE)
        imageView.setVisibility(View.GONE);
        }

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }
}