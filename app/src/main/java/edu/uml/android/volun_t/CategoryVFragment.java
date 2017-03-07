package edu.uml.android.volun_t;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by adam on 3/5/17.
 */

public class CategoryVFragment extends Fragment implements LoaderManager.LoaderCallbacks<DatabaseUtils> {

    private PostAdapter mPostAdapter;
    private TextView mEmptyStateTextView;
    private String category;

    public CategoryVFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.plans_list, container, false);

        ListView postListView = (ListView) rootView.findViewById(R.id.list);

        mEmptyStateTextView = (TextView) rootView.findViewById(R.id.empty_view);
        postListView.setEmptyView(mEmptyStateTextView);

        mPostAdapter = new PostAdapter(this.getContext(), new ArrayList<Post>());

        postListView.setAdapter(mPostAdapter);

        // Get the list of all posts here
        category = this.getArguments().getString("category");

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null, this);

        return rootView;
    }

    @Override
    public Loader<DatabaseUtils> onCreateLoader(int id, Bundle args) {
        DatabaseUtilsLoader loader = new DatabaseUtilsLoader(this.getActivity());
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<DatabaseUtils> loader, DatabaseUtils dbUtil) {
        mPostAdapter.clear();
        if (category.equals("accepted") && dbUtil.getCurrentUserAcceptedPosts().size() != 0)
            mPostAdapter.addAll(dbUtil.getCurrentUserAcceptedPosts());
        else if (category.equals("completed") && dbUtil.getCurrentUserCompletedPosts().size() != 0)
            mPostAdapter.addAll(dbUtil.getCurrentUserCompletedPosts());
        else
            mEmptyStateTextView.setText("No posts to show here.");
        ProgressBar loading = (ProgressBar) getView().findViewById(R.id.loading_indicator);
        loading.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<DatabaseUtils> loader) {
        // Loader reset, so we can clear out our existing data.
        mPostAdapter.clear();
    }

}
