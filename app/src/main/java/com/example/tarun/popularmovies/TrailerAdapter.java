package com.example.tarun.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private final Context context;
    private List<Trailers> trailerList;
    private static final String YOUTUBE_VIDEO_BASE_URL = "https://www.youtube.com";
    private static final String YOUTUBE_IMAGE_BASE_URL = "https://img.youtube.com";
    private static final String YOUTUBE_VIDEO_PATH = "watch";
    private static final String YOUTUBE_VIDEO_Query_Parameter = "v";
    private static final String YOUTUBE_IMAGE_PATH = "vi";
    private static final String YOUTUBE_IMAGE_POSTFIX = "0.jpg";
    // --Commented out by Inspection (18/09/18, 2:22 PM):private String button;

    public String sendbutton()
    {
        Trailers load;
        return youTubeVideoUrlBuilder(trailerList.get(0).getKey());
    }
    public TrailerAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.trailers, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trailers currentItem = trailerList.get(position);
        String ImageUrl = ImageUrlBuilder(currentItem.getKey());
        Picasso.with(context)
                .load(ImageUrl)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.brokenimage)
                .into(holder.imageView);
        holder.trailer = currentItem;
    }

    @Override
    public int getItemCount() {
        if (trailerList == null) {
            return 0;
        }
        return trailerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final ImageView imageView;
        private Trailers trailer;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.trailer_thumbnail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String videoUrl = youTubeVideoUrlBuilder(trailer.getKey());
             //       button = videoUrl;
                    intent.setData(Uri.parse(videoUrl));
                    if (intent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
    private static String youTubeVideoUrlBuilder(String key) {
        return Uri.parse(YOUTUBE_VIDEO_BASE_URL)
                .buildUpon()
                .appendEncodedPath(YOUTUBE_VIDEO_PATH)
                .appendQueryParameter(YOUTUBE_VIDEO_Query_Parameter, key)
                .build().toString();
    }

    private static String ImageUrlBuilder(String key) {
        return Uri.parse(YOUTUBE_IMAGE_BASE_URL)
                .buildUpon()
                .appendEncodedPath(YOUTUBE_IMAGE_PATH)
                .appendEncodedPath(key)
                .appendEncodedPath(YOUTUBE_IMAGE_POSTFIX)
                .build().toString();
    }
    public void setTrailerList(List<Trailers> trailerList) {
        this.trailerList = trailerList;
        notifyDataSetChanged();
    }
}
