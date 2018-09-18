package com.example.tarun.popularmovies;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import android.app.LoaderManager;


import com.example.tarun.popularmovies.Data.TaskContract;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {


    private static final String popurl = "https://api.themoviedb.org/3/movie/popular?";
    private static final String topurl = "https://api.themoviedb.org/3/movie/top_rated?";
    private static final int loadervariable = 22;
    private static final int toploader = 33;
    private static final int LOADER=1;
    private static final String POSITION = "position";
    private static final String ACTIONBAR="actionbar";
    // --Commented out by Inspection (18/09/18, 2:22 PM):private static  ArrayList<movie> data;
    private  RecyclerView rv;
    // --Commented out by Inspection (18/09/18, 2:22 PM):private static ArrayList<movie> fdata ;

    private static final String CURRENT_LOADER="currentloader";
    private static String action;
    private static int currentloader;

    // --Commented out by Inspection (18/09/18, 2:22 PM):private static final String LOG_TAG = MainActivity.class.getSimpleName();
    // --Commented out by Inspection (6/19/18, 9:52 PM):private Toast mToast;


    //   private final MovieAsyncTask movieAsyncTask = new MovieAsyncTask();


    // --Commented out by Inspection (18/09/18, 2:22 PM):private Toast toptoast;
    // --Commented out by Inspection (18/09/18, 2:22 PM):private Toast poptoast;

    private boolean internet_connection() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = Objects.requireNonNull(cm).getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

     //   findViewById(R.id.pgbar).setVisibility(View.VISIBLE);
        if(savedInstanceState == null)
        {
            currentloader=loadervariable;
            String urlpath = popurl;
            action="Popular Movies";
        }
         rv = findViewById(R.id.recycle);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            rv.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        }
        else
        {
            rv.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));

        }

        initLoader(MainActivity.this);


    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
       //     findViewById(R.id.pgbar).setVisibility(View.VISIBLE);
            currentloader = savedInstanceState.getInt(CURRENT_LOADER);
            getLoaderManager().initLoader(currentloader, null,  this).forceLoad();
            GridLayoutManager gridLayoutManager = (GridLayoutManager)rv.getLayoutManager();
            savedInstanceState.putInt(POSITION,gridLayoutManager.findFirstVisibleItemPosition());
      //      findViewById(R.id.pgbar).setVisibility(View.GONE);
            ActionBar actionBar = getSupportActionBar();

            Objects.requireNonNull(actionBar).setTitle((savedInstanceState.getString(ACTIONBAR)));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        GridLayoutManager gridLayoutManager = (GridLayoutManager)rv.getLayoutManager();
        outState.putInt(POSITION,gridLayoutManager.findFirstVisibleItemPosition());
        outState.putInt(CURRENT_LOADER, currentloader);
        outState.putString(ACTIONBAR,action);
    }


    private void initLoader(Context context)
    {

        if(internet_connection())
        {
            findViewById(R.id.iView).setVisibility(View.GONE);
            findViewById(R.id.tView).setVisibility(View.GONE);
            findViewById(R.id.tView2).setVisibility(View.GONE);
        //    urlpath = popurl;
            ActionBar actionBar = getSupportActionBar();
            Objects.requireNonNull(actionBar).setTitle(action);
            getLoaderManager().initLoader(currentloader,null,this);
   //         findViewById(R.id.pgbar).setVisibility(View.GONE);
        }
        else
        {

            findViewById(R.id.iView).setVisibility(View.VISIBLE);
            findViewById(R.id.tView).setVisibility(View.VISIBLE);
            findViewById(R.id.tView2).setVisibility(View.VISIBLE);
        }
    }

    // @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.popular:

                if(internet_connection()){
                    Boolean x = internet_connection();
                //onResume();
                    findViewById(R.id.iView).setVisibility(View.GONE);
                    findViewById(R.id.tView).setVisibility(View.GONE);
                    findViewById(R.id.tView2).setVisibility(View.GONE);

                    Log.v("internet connection",x+"");
                currentloader = loadervariable;
          //      urlpath=popurl;
                getLoaderManager().destroyLoader(LOADER);
                getLoaderManager().destroyLoader(toploader);
                getLoaderManager().initLoader(currentloader,null,this).forceLoad();
                ActionBar actionBar = getSupportActionBar();
                Objects.requireNonNull(actionBar).setTitle(R.string.Pop_Movies);}
                else
                    {currentloader = loadervariable;
                        getLoaderManager().destroyLoader(currentloader);
                        findViewById(R.id.iView).setVisibility(View.VISIBLE);
                        findViewById(R.id.tView).setVisibility(View.VISIBLE);
                        findViewById(R.id.tView2).setVisibility(View.VISIBLE);

                    }
                return true;
            case R.id.rating:
                if(internet_connection()){
                currentloader = toploader;
                    findViewById(R.id.iView).setVisibility(View.GONE);
                    findViewById(R.id.tView).setVisibility(View.GONE);
                    findViewById(R.id.tView2).setVisibility(View.GONE);

          //      onResume();
           //     urlpath = topurl;
                getLoaderManager().destroyLoader(LOADER);
                getLoaderManager().destroyLoader(loadervariable);
                getLoaderManager().initLoader(currentloader,null,this).forceLoad();
                //getLoaderManager().initLoader(LOADER,null,this);
                ActionBar actionBar1 = getSupportActionBar();
                Objects.requireNonNull(actionBar1).setTitle(R.string.Top_Rated);}
                else
                {currentloader=toploader;
                    getLoaderManager().destroyLoader(currentloader);

                    findViewById(R.id.iView).setVisibility(View.VISIBLE);
                    findViewById(R.id.tView).setVisibility(View.VISIBLE);
                    findViewById(R.id.tView2).setVisibility(View.VISIBLE);
                }
                return true;

            case R.id.favr:
                currentloader = LOADER;
                getLoaderManager().destroyLoader(loadervariable);
                getLoaderManager().destroyLoader(toploader);
                getLoaderManager().initLoader(currentloader,null,this).forceLoad();
                ActionBar actionBar2 = getSupportActionBar();
                Objects.requireNonNull(actionBar2).setTitle(R.string.favs);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }



    private void setupRecyclerAcapter(ArrayList<movie> data, int id)
    {
        MovieAdapter adapter;
        if(id==1) {
            adapter = new MovieAdapter(MainActivity.this, data);
            rv.setAdapter(adapter);
        }
        if(id==3)
        {
            adapter = new MovieAdapter(MainActivity.this,data);
            rv.setAdapter(adapter);
        }
        else if(id==2)
        {
            FavouriteAdapter mAdapter = new FavouriteAdapter(this);
            rv.setAdapter(mAdapter);
            mAdapter.swapCursor((Cursor) data);

        }
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, Bundle args) {

//        findViewById(R.id.pgbar).setVisibility(View.VISIBLE);

        if(id == loadervariable){
            action = "Popular Movies";
            ActionBar actionBar1 = getSupportActionBar();
            Objects.requireNonNull(actionBar1).setTitle(R.string.Pop_Movies);
        return new Loaderclass(this,popurl);

        }

        if(id==toploader) {
            action = "Top Rated";
            ActionBar actionBar1 = getSupportActionBar();
            Objects.requireNonNull(actionBar1).setTitle(R.string.Top_Rated);
            return new Loaderclass(this, topurl);
        }
        else if(id==LOADER) {
            action = "Favourites";
            ActionBar actionBar1 = getSupportActionBar();
            Objects.requireNonNull(actionBar1).setTitle(R.string.favs);
            String[] projection = new String[]{
                    TaskContract.MovieEntry.COLUMN_MOVIE_ID,
                    TaskContract.MovieEntry.COLUMN_MOVIE_TITLE,
                    TaskContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE,
                    TaskContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE,
                    TaskContract.MovieEntry.COLUMN_MOVIE_OVERVIEW,
                    TaskContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH
            };
            return new CursorLoader(this, TaskContract.MovieEntry.CONTENT_URI, projection, null, null, null);
        }
            return null;
    }

    @Override
    public void onLoadFinished(Loader load, Object o) {
        if(load.getId() == loadervariable) {
            setupRecyclerAcapter((ArrayList<movie>)o,1);
        }
        if(load.getId()==toploader)
        {
            setupRecyclerAcapter((ArrayList<movie>)o,3);
        }
        else if(load.getId()==LOADER){
            FavouriteAdapter fav = new FavouriteAdapter(this);
            rv.setAdapter(fav);
            fav.swapCursor((Cursor)o);
        }
//        findViewById(R.id.pgbar).setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        setupRecyclerAcapter(null,1);
    }

    }



