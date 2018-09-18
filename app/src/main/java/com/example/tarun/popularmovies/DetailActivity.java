package com.example.tarun.popularmovies;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarun.popularmovies.Data.TaskContract;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.tarun.popularmovies.Data.TaskContract.MovieEntry.*;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks{
    // --Commented out by Inspection (6/19/18, 9:52 PM):private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    // --Commented out by Inspection (18/09/18, 2:22 PM):private Toast mToast;
    private static final int TRAILER_LOADER = 20;
    private TrailerAdapter trailerAdapter;
    private RecyclerView trailerRecyclerView;
    RecyclerView reviewRecyclerView;
    private ReviewAdapter reviewAdapter;
    TextView empty;

    private static final int REVIEW_LOADER = 21;

    // --Commented out by Inspection (18/09/18, 2:22 PM):private Context context;
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    // --Commented out by Inspection (18/09/18, 2:25 PM):private Toast mToast;

    private final movie m = new movie();
    private  boolean favourite;
    private String movieid;
    Toast mToast;
    private String backdrop;
    ImageView frameLayout;
    // --Commented out by Inspection (6/19/18, 9:52 PM):TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailactivity);

        final TextView movietitle;
        TextView date;
        TextView rating;
        final TextView overview;
        final ImageView movieposter;
         empty = findViewById(R.id.empty_review_tv);
   //      FrameLayout frameLayout = findViewById(R.id.frame);


        trailerRecyclerView = findViewById(R.id.trailerrecycle);
        reviewRecyclerView = findViewById(R.id.reviewrecycle);

        Intent receive = getIntent();
        if (receive.hasExtra("id")) {
            String id = receive.getStringExtra("id");
            movieid = id;
            favourite = checkInDb(id);

            m.setId(id);
        }

        if (receive.hasExtra("image")) {
            String poster = receive.getStringExtra("image");
            m.setPoster(poster);
            movieposter = findViewById(R.id.posterimage);
            Picasso.with(this)
                    .load(FavouriteAdapter.urlStringFromPosterPath(m.getPoster()))
                    .placeholder(R.drawable.ic_image)
                    .error(R.drawable.brokenimage)
                    .into(movieposter);

        }
        if (receive.hasExtra("title")) {
            String receivetitle = receive.getStringExtra("title");
            m.setmTitle(receivetitle);
            movietitle = findViewById(R.id.moviename);
            movietitle.setText(receivetitle);
        }
        if (receive.hasExtra("date")) {
            String receivedate = receive.getStringExtra("date");
            m.setmDate(receivedate);
            date = findViewById(R.id.year);
            date.setText(receivedate);
        }
        if (receive.hasExtra("rating")) {
            String receiverating = receive.getStringExtra("rating");
            m.setmRating(receiverating);
            setrating(receiverating);
            rating = findViewById(R.id.rating);
            rating.setText(receiverating);
        }
        if (receive.hasExtra("overview")) {
            String summary = receive.getStringExtra("overview");
            m.setmOverview(summary);
            overview = findViewById(R.id.lasttext);
            overview.setText(summary);
        }
        if(receive.hasExtra("backdrop"))
        {
         backdrop = receive.getStringExtra("backdrop");
         m.setBackdrop(backdrop);
         Log.d("back",backdrop);
            frameLayout = findViewById(R.id.iv_movie_poster);
            Picasso.with(this)
                    .load(FavouriteAdapter.urlStringFromBackgroundPath(m.getBackdrop()))
                    .placeholder(R.drawable.ic_image)
                    .error(R.drawable.brokenimage)
                    .into(frameLayout);
        }
  /*      String id="";
        if(receive.hasExtra("id"))
        {
            id=
        }*/
        final ImageView fav = findViewById(R.id.fav);
        if(favourite)
            fav.setImageResource(R.drawable.heart_on);
        else
            fav.setImageResource(R.drawable.heart_off);
        fav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mToast != null) {
                    mToast.cancel();
                }
                ImageView im = findViewById(R.id.fav);
                if(favourite)
                {
                    fav.setImageResource(R.drawable.heart_off);
                    favourite = false;
                    deleteFromDb();
                    Toast.makeText(getApplicationContext(), "DELETE from DB", Toast.LENGTH_LONG).show();
                }
                else{
                onSaveClicked();
                im.setImageResource(R.drawable.heart_on);
                favourite = true;
                }
            }
            private void onSaveClicked() {
                // DetailActivity da = new DetailActivity();
                String title = m.getmTitle();
                String date = m.getmDate();
                String rating = m.getmRating();
                String description = m.getmOverview();
                String poster = m.getPoster();
                String id = m.getId();
                String backdrop = m.getBackdrop();
                ContentValues values = new ContentValues();
                values.put(COLUMN_MOVIE_OVERVIEW, description);
                values.put(COLUMN_MOVIE_VOTE_AVERAGE, rating);
                values.put(COLUMN_MOVIE_RELEASE_DATE, date);
                values.put(COLUMN_MOVIE_TITLE, title);
                values.put(COLUMN_MOVIE_BACKDROP,backdrop);
                values.put(COLUMN_MOVIE_POSTER_PATH, poster);

                values.put(COLUMN_MOVIE_ID, id);
                Uri uri = getContentResolver().insert(CONTENT_URI, values);
                if (uri != null) {
                    String msg = "MOVIE ADDED TO FAVOURITES";
                    Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
                }

            }
        });
        //      detailinfo.execute(R.string.api_base_url+);

        if(internet_connection()) {
            getLoaderManager().initLoader(TRAILER_LOADER, null, DetailActivity.this).forceLoad();
            trailerAdapter = new TrailerAdapter(DetailActivity.this);

            trailerRecyclerView.setLayoutManager(new LinearLayoutManager(DetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
            trailerRecyclerView.setAdapter(trailerAdapter);
        }

        if(internet_connection())
        {
            getLoaderManager().initLoader(REVIEW_LOADER, null, DetailActivity.this).forceLoad();
            reviewAdapter = new ReviewAdapter(this);

            reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            reviewRecyclerView.setAdapter(reviewAdapter);
        }



        Button b = findViewById(R.id.button_watch_trailer);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //           trailerAdapter.sendbutton();
                if(!internet_connection())
                {
                    Toast mToast = Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT);
                    mToast.show();
                    return;
                }
                Log.v(LOG_TAG,trailerAdapter.sendbutton());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                        String videoUrl = trailerAdapter.sendbutton();
                        if(videoUrl.isEmpty())
                        {
                            Toast mToast = Toast.makeText(getApplicationContext(),"Trailer not available",Toast.LENGTH_SHORT);
                            mToast.show();
                            return;
                        }

                //       button = videoUrl;
                intent.setData(Uri.parse(videoUrl));
                if (intent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
                    getApplicationContext().startActivity(intent);
                }
            }
        });

    }


    private void deleteFromDb() {
        getContentResolver().delete(TaskContract.MovieEntry.CONTENT_URI.buildUpon().appendEncodedPath(movieid).build(), null, null);
    }

    private boolean checkInDb(String movieId) {
        String queryUri = TaskContract.MovieEntry.CONTENT_URI + "/" + movieId;
        String[] projection = new String[]{movieId};
        Cursor cursor = getContentResolver().query(Uri.parse(queryUri), projection, null, null, null);
        if (cursor != null && cursor.moveToNext()) {
            cursor.close();
            return true;
        }
        return false;
    }

    private void setrating(String rating)
    {
        float rat = Float.valueOf(rating);
        rat /= 2;
        int round = (int) rat;
        switch (round)
        {
            case 1:
                ImageView r1 = findViewById(R.id.rating_1_star);
                r1.setImageResource(R.drawable.fullstar);
                break;
            case 2:
                ImageView r11 = findViewById(R.id.rating_1_star);
                r11.setImageResource(R.drawable.fullstar);
                ImageView r2 = findViewById(R.id.rating_2_star);
                r2.setImageResource(R.drawable.fullstar);
                break;
            case 3:
                ImageView r111 = findViewById(R.id.rating_1_star);
                r111.setImageResource(R.drawable.fullstar);
                ImageView r22 = findViewById(R.id.rating_2_star);
                r22.setImageResource(R.drawable.fullstar);
                ImageView r3 = findViewById(R.id.rating_3_star);
                r3.setImageResource(R.drawable.fullstar);
                break;
            case 4:
                ImageView r1111 = findViewById(R.id.rating_1_star);
                r1111.setImageResource(R.drawable.fullstar);
                ImageView r222 = findViewById(R.id.rating_2_star);
                r222.setImageResource(R.drawable.fullstar);
                ImageView r33 = findViewById(R.id.rating_3_star);
                r33.setImageResource(R.drawable.fullstar);
                ImageView r4 = findViewById(R.id.rating_4_star);
                r4.setImageResource(R.drawable.fullstar);
                break;
            case 5:

                ImageView r11111 = findViewById(R.id.rating_1_star);
                r11111.setImageResource(R.drawable.fullstar);
                ImageView r2222 = findViewById(R.id.rating_2_star);
                r2222.setImageResource(R.drawable.fullstar);
                ImageView r333 = findViewById(R.id.rating_3_star);
                r333.setImageResource(R.drawable.fullstar);
                ImageView r44 = findViewById(R.id.rating_4_star);
                r44.setImageResource(R.drawable.fullstar);
                ImageView r5 = findViewById(R.id.rating_5_star);
                r5.setImageResource(R.drawable.fullstar);
                break;
                default:return;
        }
        int r = Math.round(rat);
        if(r>round){
           // r-=round;
            if(r==1)
            {
                ImageView iv = findViewById(R.id.rating_1_star);
                iv.setImageResource(R.drawable.halfstar);
            }

            if(r==2)
            {
                ImageView iv = findViewById(R.id.rating_2_star);
                iv.setImageResource(R.drawable.halfstar);
            }
            if(r==3)
            {
                ImageView iv = findViewById(R.id.rating_3_star);
                iv.setImageResource(R.drawable.halfstar);
            }
            if(r==4)
            {
                ImageView iv = findViewById(R.id.rating_4_star);
                iv.setImageResource(R.drawable.halfstar);
            }
            if(r==5)
            {
                ImageView iv = findViewById(R.id.rating_5_star);
                iv.setImageResource(R.drawable.halfstar);
            }
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle bundle) {
        if (id == TRAILER_LOADER) {
            //movie m = new movie();
            return new TrailerLoader(this,m.getId());
        }
         if (id == REVIEW_LOADER) {
            return new ReviewLoader(this,m.getId());
        }
        return null;
    }


    @Override
    public void onLoadFinished(Loader loader, Object o) {
        int id = loader.getId();
        if (id == TRAILER_LOADER) {
            trailerAdapter.setTrailerList((List<Trailers>) o);
        } else if (id == REVIEW_LOADER) {
            ArrayList<Review> reviews = (ArrayList<Review>) o;
            if (reviews != null && reviews.size() > 0) {
                List<String> reviewContent = new ArrayList<>();
                List<String> author = new ArrayList<>();
                empty.setVisibility(View.INVISIBLE);
                reviewContent = getReviewContent(reviews);
                author = getauthorContent(reviews);
                reviewAdapter.setAuthors(author);
                reviewAdapter.setReviews(reviewContent);
            } else {
                empty.setVisibility(View.VISIBLE);
            }
        }
    }



    @Override
    public void onLoaderReset(Loader loader) {
        int id = loader.getId();
        if (id == TRAILER_LOADER) {
            trailerAdapter.setTrailerList(null);
        }
        else if (id == REVIEW_LOADER) {
            reviewAdapter.setReviews(null);
        }
    }
    private boolean internet_connection() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = Objects.requireNonNull(cm).getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    private ArrayList<String> getReviewContent(ArrayList<Review> reviews) {
        if (reviews == null || reviews.size() == 0) return null;
        ArrayList<String> reviewContent = new ArrayList<>();
        for (int i = 0; i < reviews.size(); i++) {
            reviewContent.add(reviews.get(i).getContent());
        }
        return reviewContent;
    }
    private ArrayList<String> getauthorContent(ArrayList<Review> reviews) {
        if (reviews == null || reviews.size() == 0) return null;
        ArrayList<String> reviewContent = new ArrayList<>();
        for (int i = 0; i < reviews.size(); i++) {
            reviewContent.add(reviews.get(i).getAuthor());
        }
        return reviewContent;
    }

}