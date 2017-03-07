package edu.uml.android.volun_t;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by adam on 3/5/17.
 */

public class PlansCActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vplans);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);

        // Set up the tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        CategoryCAdapter adapter = new CategoryCAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Accepted");
        tabLayout.getTabAt(1).setText("Pending");
        tabLayout.getTabAt(2).setText("Completed");
    }

}
