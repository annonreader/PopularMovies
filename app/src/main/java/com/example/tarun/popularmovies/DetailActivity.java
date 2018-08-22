package com.example.tarun.popularmovies;

import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.content.ContentValues;
import android.content.Entity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarun.popularmovies.Data.TaskContract;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static com.example.tarun.popularmovies.Data.TaskContract.MovieEntry.*;

public class DetailActivity extends AppCompatActivity {
    // --Commented out by Inspection (6/19/18, 9:52 PM):private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    private Toast mToast;


    movie m = new movie();

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

        Intent receive = getIntent();
        if (receive.hasExtra("id")) {
            String id = receive.getStringExtra("id");
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
  /*      String id="";
        if(receive.hasExtra("id"))
        {
            id=
        }*/

        ImageView fav = findViewById(R.id.fav);
        fav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mToast != null) {
                    mToast.cancel();
                }
                ImageView im = findViewById(R.id.fav);
                onSaveClicked();
                im.setImageResource(R.drawable.heart_on);
                String toastmessage = "Movie added to favourite";
                mToast = Toast.makeText(getApplicationContext(), toastmessage, Toast.LENGTH_SHORT);
                mToast.show();
            }


            private void onSaveClicked() {
                // DetailActivity da = new DetailActivity();
                String title = m.getmTitle();
                String date = m.getmDate();
                String rating = m.getmRating();
                String description = m.getmOverview();
                String poster = m.getPoster();
                String id = m.getId();
                ContentValues values = new ContentValues();
                values.put(COLUMN_MOVIE_OVERVIEW, description);
                values.put(COLUMN_MOVIE_VOTE_AVERAGE, rating);
                values.put(COLUMN_MOVIE_RELEASE_DATE, date);
                values.put(COLUMN_MOVIE_TITLE, title);

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



    }
    public void setrating (String rating)
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
}