package edu.uml.android.volun_t;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.util.TimeUtils;


/**
 * Created by adam on 3/6/17.
 */

public class DatabaseUtilsLoader extends AsyncTaskLoader<DatabaseUtils> {

    public DatabaseUtilsLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public DatabaseUtils loadInBackground() {
        DatabaseUtils db = new DatabaseUtils();
        while (!db.isDone()) {}
        return db;
    }

}
