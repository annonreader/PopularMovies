package com.example.tarun.popularmovies;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

class NetworkUtils  {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();


    //    final static String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/popular?";
    //   final static String MOVIE_BASE="https://api.themoviedb.org/3/movie/top_rated?";
    private final static String API_PARAM="api_key";
    private final static String VIDEOS="videos";
    private final static String REVIEWS="reviews";
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p";
    private static final String BACKGROUND_IMAGE_SIZE = "w342";

    //////////////////

    public static URL buildUrl(String movieurl)
    {

        Log.v("inside buildurl","movieurl");
        Uri builturi = Uri.parse(movieurl).buildUpon()
                .appendQueryParameter(API_PARAM,BuildConfig.MyMovieApiKey)
                .build();

        URL url = null;

        try {
            url = new URL(builturi.toString());
            Log.d(LOG_TAG,builturi.toString());
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildtrailer(String trailerurl,String id)
    {
        Uri trail = Uri.parse(trailerurl)
                .buildUpon()
                .appendEncodedPath(id)
                .appendEncodedPath(VIDEOS).appendQueryParameter(API_PARAM,BuildConfig.MyMovieApiKey)
                .build();
        URL url = null;

        try {
            url = new URL(trail.toString());
            Log.d(LOG_TAG,trail.toString());
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildreview(String reviewurl,String id)
    {
        Uri trail = Uri.parse(reviewurl)
                .buildUpon()
                .appendEncodedPath(id)
                .appendEncodedPath(REVIEWS).appendQueryParameter(API_PARAM,BuildConfig.MyMovieApiKey)
                .build();
        URL url = null;

        try {
            url = new URL(trail.toString());
            Log.d(LOG_TAG,trail.toString());
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        return url;
    }
    //connecting to the internet


    public static String makehttprequest(URL url)throws IOException
    {
        String jsonresponse = null;
        if(url==null)
            return null;
        HttpURLConnection urlConnection = null;
        InputStream reader  = null;
        try
        {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200)
            {
                Log.v(LOG_TAG,""+urlConnection.getResponseCode());
                reader = urlConnection.getInputStream();
                jsonresponse = readFromStream(reader);
            }
            else
            {
                Log.e(LOG_TAG, "error while connecting" + urlConnection.getResponseCode());
            }
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "error while parsing json Response", e);
        }
        finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (reader != null)
                reader.close();
        }
        return jsonresponse;
    }

    //retrieving data from the internet

    private static String readFromStream(InputStream reader)throws IOException
    {
        StringBuilder output = new StringBuilder();
        if (reader != null) {
            InputStreamReader isr = new InputStreamReader(reader, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            while (line != null) {
                output.append(line);
                line = br.readLine();
            }
            //   Log.v("imout",LOG_TAG);
        }
        return output.toString();
    }


}

