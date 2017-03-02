package edu.uml.android.volun_t;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Ashley on 2/28/2017.
 */

public class CommunityActivity extends AppCompatActivity {
   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        // Create a list of songs
        ArrayList<Achievement> achs = new ArrayList<Achievement>();
        achs.add(new Achievement("The Airborne Toxic Event", "All I Ever Wanted", 17301515));
        achs.add(new Achievement("Blackstreet", "No Diggity", 17301515));
        achs.add(new Achievement("Blue Swede", "Hooked On A Feeling", 17301516));
        achs.add(new Achievement("Bastille", "Icarus", 17301515));
        achs.add(new Achievement("Avicii", "Addicted To You", 17301516));
        achs.add(new Achievement("Takafumi Wada", "Assailing Ones", 17301516));
        achs.add(new Achievement("Hurts", "Somebody To Die For", 17301515));
        achs.add(new Achievement("Mystery Skulls", "Ghost", 17301515));
        achs.add(new Achievement("SNBRN", "Raindrops", 17301516));
        achs.add(new Achievement("One Republic", "I Lived", 17301516));

        // Create an {@link AchievementAdapter}, whose data source is a list of {@link Achievement}s. The
        // adapter knows how to create list items for each item in the list.
        AchievementAdapter adapter = new AchievementAdapter(this, achs);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // song_list.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link AchievementAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Song} in the list.
        listView.setAdapter(adapter);
    }
}
