package com.example.tarun.popularmovies.Data;

import android.app.DownloadManager;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Switch;

import static com.example.tarun.popularmovies.Data.TaskContract.MovieEntry.TABLE_NAME;

public class MovieContentProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    static final int MOVIES = 100;
    private DbHelper mOpenHelper;


    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = TaskContract.AUTHORITY;
        matcher.addURI(authority, TaskContract.PATH_MOVIE, MOVIES);
        return matcher;
    }


    @Override
    public boolean onCreate() {

        mOpenHelper = new DbHelper(getContext());

        return true;
    }



    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor retcursor;
        switch(match)
        {
            case MOVIES:
                retcursor = db.query(TABLE_NAME,strings,s,strings1,null,null,s1);
                break;
                default:
                    throw new UnsupportedOperationException("unknown uri "+uri);

        }
        retcursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retcursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        Uri returnuri;
        switch (match)
        {
            case MOVIES:
                long id = db.insert(TABLE_NAME,null,contentValues);
                if(id>0)
                {
                    returnuri = ContentUris.withAppendedId(TaskContract.MovieEntry.CONTENT_URI,id);
                }
                else
                    throw new android.database.SQLException("failed to insert "+uri);
                break;
                default:
                    throw new UnsupportedOperationException("unknown uri "+uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);
        return returnuri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
