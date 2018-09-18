package com.example.tarun.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

class TrailerLoader extends AsyncTaskLoader<ArrayList<Trailers>> {

    private static final String LOG_TAG = TrailerLoader.class.getSimpleName();

    private static  ArrayList<Trailers> trail ;
    private static String id;

    public TrailerLoader(Context context,String id) {
        super(context);
        TrailerLoader.id = id;
    }

    @Override
    protected void onStartLoading() {

        if(trail!=null)
            deliverResult(trail);
        else {
            Log.d(LOG_TAG,"yes i am called again");
            forceLoad();
        }
    }

    @Override
    public ArrayList<Trailers> loadInBackground() {

        String rawurl = "https://api.themoviedb.org/3/movie/";
        if(rawurl ==null || TextUtils.isEmpty(rawurl))
            return null;
        String jsonresponse;
        try {
            URL geturl = NetworkUtils.buildtrailer(rawurl,id);

            jsonresponse = NetworkUtils.makehttprequest(geturl);
        } catch (IOException e) {
            Log.e(LOG_TAG, "error", e);
            return null;
        }
        trail =  new ArrayList<Trailers>();
         String id;
         String iso6391;
         String iso31661;
         String key;
         String name;
        String site;
        Integer size;
         String type;
        try {
            JSONObject jobj = new JSONObject(jsonresponse);
            JSONArray result = jobj.getJSONArray("results");

            for (int i = 0; i < result.length(); i++) {
                Trailers contract = new Trailers();
                JSONObject firstresult = result.getJSONObject(i);
                id = firstresult.getString("id");
                iso6391 = firstresult.getString("iso_639_1");
                if(iso6391 == null)
                    iso6391="";
                iso31661 = firstresult.getString("iso_3166_1");
                if(iso31661 == null)
                    iso31661 = "";
                key = firstresult.getString("key");
                name = firstresult.getString("name");
                site = firstresult.getString("site");
                size = firstresult.getInt("size");
                type = firstresult.getString("type");

                contract.setId(id);
                contract.setIso6391(iso6391);
                contract.setIso31661(iso31661);
                contract.setKey(key);
                contract.setName(name);
                contract.setSite(site);
                contract.setSize(size);
                contract.setType(type);
                trail.add(contract);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "inside parsejson", e);
        }

        return trail;
    }
}
