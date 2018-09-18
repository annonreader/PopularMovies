package com.example.tarun.popularmovies;

import android.content.Context;
import android.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ReviewLoader extends AsyncTaskLoader<ArrayList<Review>> {
    private ArrayList<Review> reviews;
    private String movieId;
    private static final String LOG_TAG = ReviewLoader.class.getSimpleName();


    public ReviewLoader(Context context, String movieId) {
        super(context);
        this.movieId = movieId;
    }

    @Override
    protected void onStartLoading() {
        if (reviews == null) {
            forceLoad();
        }
    }

    @Override
    public ArrayList<Review> loadInBackground() {
        String rawurl = "https://api.themoviedb.org/3/movie/";
        if(TextUtils.isEmpty(rawurl))
            return null;
        String jsonresponse;
        try {
            URL geturl = NetworkUtils.buildreview(rawurl,movieId);

            jsonresponse = NetworkUtils.makehttprequest(geturl);
        } catch (IOException e) {
            Log.e(LOG_TAG, "error", e);
            return null;
        }
        reviews =  new ArrayList<Review>();
        String id;
        String author;
        String content;
        String url;
        try {
            JSONObject jobj = new JSONObject(jsonresponse);
            JSONArray result = jobj.getJSONArray("results");

            for (int i = 0; i < result.length(); i++) {
                Review contract = new Review();
                JSONObject firstresult = result.getJSONObject(i);
                id = firstresult.getString("id");
                author = firstresult.getString("author");
                if(author == null)
                    author="";
                url = firstresult.getString("url");
                if(url == null)
                    url = "";
                content = firstresult.getString("content");

                contract.setId(id);
                contract.setAuthor(author);
                contract.setContent(content);
                contract.setUrl(url);

                reviews.add(contract);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "inside parsejson", e);
        }

        return reviews;
    }

    @Override
    public void deliverResult(ArrayList<Review> data) {
        reviews = data;
        super.deliverResult(data);
    }
}
