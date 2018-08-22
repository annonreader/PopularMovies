package com.example.tarun.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    private static final String EXTRA_MOVIE = "movie";

    private List<movie> movies;
    private Context context;

    public MovieAdapter(Context context, List<movie> movies) {
        this.movies = movies;
        this.context = context;
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclervw, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position) {
        movie currentMovie = movies.get(position);
        Picasso.with(context)
                .load(FavouriteAdapter.urlStringFromPosterPath(currentMovie.getPoster()))
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.brokenimage)
                .into(holder.imageView);

        holder.mo = currentMovie;
    }

    @Override
    public int getItemCount() {
        if (movies == null) {
            return 0;
        }
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        private movie mo;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detail = new Intent(context, DetailActivity.class);
                //    intent.putExtra(EXTRA_MOVIE, movie);
              //      movie fdata = mo.getAdapterPosition();
                    String imageurl=mo.getPoster();
                    Log.v("this is the movie url",imageurl);
                    detail.putExtra("id",mo.getId());
                    detail.putExtra("image",imageurl);
                    detail.putExtra("date",mo.getmDate());
                    detail.putExtra("overview",mo.getmOverview());
                    detail.putExtra("rating",mo.getmRating());
                    detail.putExtra("title",mo.getmTitle());
                    context.startActivity(detail);
                }
            });
        }
    }
}


