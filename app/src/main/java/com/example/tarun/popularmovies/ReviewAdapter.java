package com.example.tarun.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private Context context;
    private List<String> reviews;
    private List<String> authors;
    public ReviewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reviews, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String currentItem = reviews.get(position);
        String author  = authors.get(position);
        holder.author.setText(author);
        holder.reviewTv.setText(currentItem);
    }

    @Override
    public int getItemCount() {
        if (reviews == null) {
            return 0;
        }
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView reviewTv;
        TextView author;
        public ViewHolder(View itemView) {
            super(itemView);
            reviewTv = itemView.findViewById(R.id.review_content);
            author = itemView.findViewById(R.id.review_author);
        }
    }

    public void setAuthors(List<String> authors)
    {
        this.authors = authors;
        notifyDataSetChanged();
    }
    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }
}