package com.sksanwar.popularmoviesstage_one.Utils;

import android.net.Uri;
import android.util.Log;

import com.sksanwar.popularmoviesstage_one.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by sksho on 12-May-17.
 */

public class NetworkUtils {
    /**
     * Static String CONSTANT Variable that holds the URL BODY
     **/
    final static String SCHEME = "http";
    final static String AUTHORITY = "api.themoviedb.org";
    final static String PATH_3 = "3";
    final static String PATH_MOVIE = "movie";

    //TAG for logging
    private static final String TAG = NetworkUtils.class.getSimpleName();

    /**
     * Movie API Key Hiding into build gradle file
     * <p>
     * How to hide your API Key
     * https://www.linkedin.com/pulse/how-hide-your-api-key-android-apps-allan-caine
     */
    private static final String API_KEY = BuildConfig.API_KEY;

    /**
     * URI builder with String
     * Resources: "https://developer.android.com/reference/android/net/Uri.Builder.html"
     * "http://stackoverflow.com/questions/19167954/use-uri-builder-in-android-or-create-url-with-variables"
     *
     * @param queryForMovie
     * @return
     */
    public static URL buildUrl(String queryForMovie) {

        //Thread sleep time will show the Progressbar to user that data is being loaded from the server
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Building the URL to get the data from the server
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(PATH_3)
                .appendPath(PATH_MOVIE)
                .appendPath(queryForMovie)
                .appendQueryParameter("api_key", API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return url;
    }

    /**
     * This Method checks the response from the JSON also the http connection
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            //now need to check weather the connection is successful or not
            if (urlConnection.getResponseCode() == 200) {

                jsonResponse = readFromStream(urlConnection.getInputStream());
            } else {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem retrieving the News JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;

    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
