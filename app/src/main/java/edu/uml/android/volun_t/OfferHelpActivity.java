package edu.uml.android.volun_t;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by adam on 3/2/17.
 */

public class OfferHelpActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<DatabaseUtils> {

    private PostAdapter mPostAdapter;
    private TextView mEmptyStateTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plans_list);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);

        ListView postListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        postListView.setEmptyView(mEmptyStateTextView);

        mPostAdapter = new PostAdapter(this, new ArrayList<Post>(), "pending");

        postListView.setAdapter(mPostAdapter);

        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(0, null, this);
    }

    @Override
    public Loader<DatabaseUtils> onCreateLoader(int id, Bundle args) {
        DatabaseUtilsLoader loader = new DatabaseUtilsLoader(this);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<DatabaseUtils> loader, DatabaseUtils dbUtil) {
        mPostAdapter.clear();
        if (dbUtil.getAllPendingPosts().size() != 0)
            mPostAdapter.addAll(dbUtil.getAllPendingPosts());
        else
            mEmptyStateTextView.setText("No posts to show here.");
        ProgressBar loading = (ProgressBar) findViewById(R.id.loading_indicator);
        loading.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<DatabaseUtils> loader) {
        // Loader reset, so we can clear out our existing data.
        mPostAdapter.clear();
    }

}

