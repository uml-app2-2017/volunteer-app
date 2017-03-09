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

    static int star_on = 17301516;
    static int star_off = 17301515;
   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        // Create a list of Achievements
        ArrayList<Achievement> achs = new ArrayList<Achievement>();
        achs.add(new Achievement("The Airborne Toxic Event", "All I Ever Wanted", star_off));
        achs.add(new Achievement("Blackstreet", "No Diggity", star_off));
        achs.add(new Achievement("Blue Swede", "Hooked On A Feeling", star_on));
        achs.add(new Achievement("Bastille", "Icarus", star_off));
        achs.add(new Achievement("Avicii", "Addicted To You", star_on));
        achs.add(new Achievement("Takafumi Wada", "Assailing Ones", star_on));
        achs.add(new Achievement("Hurts", "Somebody To Die For", star_off));
        achs.add(new Achievement("Mystery Skulls", "Ghost", star_off));
        achs.add(new Achievement("SNBRN", "Raindrops", star_on));
        achs.add(new Achievement("One Republic", "I Lived", star_on));

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
