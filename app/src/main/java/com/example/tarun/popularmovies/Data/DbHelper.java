package com.example.tarun.popularmovies.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movies.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + TaskContract.MovieEntry.TABLE_NAME
                + " (" +
                TaskContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TaskContract.MovieEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL, " +
                TaskContract.MovieEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                TaskContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH + " TEXT NOT NULL, " +
                TaskContract.MovieEntry.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL, " +
                TaskContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE + " TEXT NOT NULL, " +
                TaskContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL " +
                " );";
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TaskContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
