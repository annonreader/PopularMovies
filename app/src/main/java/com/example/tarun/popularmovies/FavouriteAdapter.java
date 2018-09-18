package com.example.tarun.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tarun.popularmovies.Data.TaskContract;
import com.squareup.picasso.Picasso;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    // --Commented out by Inspection (18/09/18, 2:22 PM):private static final String EXTRA_MOVIE = "movie";
    private final Context context;
    private Cursor cursor;
    private static final String POSTER_SIZE = "w500";
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p";
    // --Commented out by Inspection (18/09/18, 2:22 PM):public  ImageView myImageView;

    public FavouriteAdapter(Context context) {
        this.context = context;
    }

    public static String urlStringFromPosterPath(String imagePath) {
        return Uri.parse(IMAGE_BASE_URL)
                .buildUpon()
                .appendEncodedPath(POSTER_SIZE)
                .appendEncodedPath(imagePath)
                .build().toString();
    }
    @NonNull
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclervw, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.ViewHolder holder, int position) {
        if (cursor == null || cursor.getCount() == 0) {
            return;
        }
        cursor.moveToPosition(position);
        String posterUriString = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH));

        Picasso.with(context)
                .load(urlStringFromPosterPath(posterUriString))
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.brokenimage)
                .into(holder.myImageView);

    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }

    public void swapCursor(Cursor cursor) {
        this.cursor = cursor;
        if (cursor != null) {
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView myImageView;

        ViewHolder(View itemView) {
            super(itemView);
            myImageView = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detail = new Intent(context, DetailActivity.class);
                    movie fdata = getCurrentMovie(getAdapterPosition());
                    String imageurl=fdata.getPoster();
                    Log.v("this is the movie url",imageurl);
                    detail.putExtra("id",fdata.getId());
                    detail.putExtra("image",imageurl);
                    detail.putExtra("date",fdata.getmDate());
                    detail.putExtra("overview",fdata.getmOverview());
                    detail.putExtra("rating",fdata.getmRating());
                    detail.putExtra("title",fdata.getmTitle());

                    context.startActivity(detail);
                }
            });
        }
    }

    private movie getCurrentMovie(int adapterPosition) {

        cursor.moveToPosition(adapterPosition);
        movie movie = new movie();
        String movieId = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.MovieEntry.COLUMN_MOVIE_ID));
        movie.setId((movieId));

        String movieTitle = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.MovieEntry.COLUMN_MOVIE_TITLE));
        movie.setmTitle(movieTitle);

        String moviePosterUriString = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH));
        movie.setPoster(moviePosterUriString);



        String movieReleaseData = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE));
        movie.setmDate(movieReleaseData);

        String movieRate = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE));
        movie.setmRating((movieRate));

        String moviePlot = cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.MovieEntry.COLUMN_MOVIE_OVERVIEW));
        movie.setmOverview(moviePlot);

        return movie;
    }

}
