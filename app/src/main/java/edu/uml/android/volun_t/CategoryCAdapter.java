package edu.uml.android.volun_t;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by adam on 3/5/17.
 */

public class CategoryCAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public CategoryCAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            Fragment frag = new CategoryCFragment();
            Bundle bundle = new Bundle();
            bundle.putString("category", "accepted");
            frag.setArguments(bundle);
            return frag;
        } else if (position == 1) {
            Fragment frag = new CategoryCFragment();
            Bundle bundle = new Bundle();
            bundle.putString("category", "pending");
            frag.setArguments(bundle);
            return frag;
        } else {
            Fragment frag = new CategoryCFragment();
            Bundle bundle = new Bundle();
            bundle.putString("category", "completed");
            frag.setArguments(bundle);
            return frag;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
