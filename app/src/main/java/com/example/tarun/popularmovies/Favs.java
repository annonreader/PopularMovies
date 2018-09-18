package com.example.tarun.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.tarun.popularmovies.Data.TaskContract.MovieEntry;

import static android.content.ContentValues.TAG;

class Favs extends AsyncTaskLoader<Cursor> {


    public Favs(Context context) {
        super(context);
    }

    private Cursor mTaskData = null;

    @Override
    protected void onStartLoading() {
        if (mTaskData != null) {
            // Delivers any previously loaded data immediately
            deliverResult(mTaskData);
        } else {
            // Force a new load
            forceLoad();
        }
    }


    @Override
    public Cursor loadInBackground() {
        try {

            String[] projection = new String[]{
                    MovieEntry.COLUMN_MOVIE_ID,
                    MovieEntry.COLUMN_MOVIE_TITLE,
                    MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE,
                    MovieEntry.COLUMN_MOVIE_RELEASE_DATE,
                    MovieEntry.COLUMN_MOVIE_OVERVIEW,
                    MovieEntry.COLUMN_MOVIE_POSTER_PATH
            };
          return getContext().getContentResolver().query(MovieEntry.CONTENT_URI,
                    projection,
                    null,
                    null,
                    MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE);

        } catch (Exception e) {
            Log.e(TAG, "Failed to asynchronously load data.");
            e.printStackTrace();
            return null;
        }
    }
    public void deliverResult(Cursor data) {
        mTaskData = data;
        super.deliverResult(data);
    }

}

