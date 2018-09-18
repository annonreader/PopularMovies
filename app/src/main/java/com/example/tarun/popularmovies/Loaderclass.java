package com.example.tarun.popularmovies;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import android.content.AsyncTaskLoader;



class Loaderclass extends AsyncTaskLoader<ArrayList<movie>> {

    private static  ArrayList<movie> last ;
    private static final String LOG_TAG = Loaderclass.class.getSimpleName();
    private static  String rawurl;
    private String jsonresponse;

   /* public void receiveurl(String url)
    {
        rawurl = url;
    }*/

    public Loaderclass(Context context,String url) {
        super(context);
        rawurl = url;
    }

    @Override
    protected void onStartLoading() {

        if(last!=null)
            deliverResult(last);
        else {
            Log.d(LOG_TAG,"yes i am called again");
            forceLoad();
        }
    }

    @Override
    public ArrayList<movie> loadInBackground() {


        if(rawurl==null || TextUtils.isEmpty(rawurl))
            return null;
        // jsonresponse;
        try {
            URL geturl = NetworkUtils.buildUrl(rawurl);

            jsonresponse = NetworkUtils.makehttprequest(geturl);
        } catch (IOException e) {
            Log.e(LOG_TAG, "error", e);
            return null;
        }
        last =  new ArrayList<movie>();
        String posterpath;
        String id;
        Double rating;
        String title;
        String releasedate;
        String overview;
        String backdrop;
        try {
            JSONObject jobj = new JSONObject(jsonresponse);
            JSONArray result = jobj.getJSONArray("results");

            for (int i = 0; i < result.length(); i++) {
                movie contract = new movie();
                JSONObject firstresult = result.getJSONObject(i);
                posterpath = firstresult.getString("poster_path");
                id = firstresult.getString("id");
                rating = firstresult.getDouble("vote_average");
                title = firstresult.getString("title");
                releasedate = firstresult.getString("release_date");
                overview = firstresult.getString("overview");
                backdrop = firstresult.getString("backdrop_path");

                contract.setBackdrop(backdrop);
                contract.setId(id);
                contract.setPoster(posterpath);
                contract.setmRating(rating.toString());
                contract.setmTitle(title);
                contract.setmDate(releasedate);
                contract.setmOverview(overview);
                last.add(contract);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "inside parsejson", e);
        }

        return last;
    }

    public void deliverResult(ArrayList<movie> data)
    {
        last = data;
        super.deliverResult(data);
    }



}


