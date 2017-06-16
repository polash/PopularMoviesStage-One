package com.sksanwar.popularmoviesstage_one.Utils;


import com.sksanwar.popularmoviesstage_one.Model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sksho on 12-May-17.
 */

public class DataParsing {

    public static List<Movie>
    getMovieDataFromJson(String movieData)
            throws JSONException {

        final String MOVIE_RESULT = "results";
        final String MOVIE_TITLE = "original_title";
        final String MOVIE_POSTER = "poster_path";
        final String MOVIE_OVERVIEW = "overview";
        final String MOVIE_RELEASE_DATE = "release_date";
        final String MOVIE_BACKDROP = "backdrop_path";
        final String MOVIE_VOTE_AVERAGE = "vote_average";

        //Movie List Instance
        List<Movie> movieList = new ArrayList<>();

        JSONObject movieDataObject = new JSONObject(movieData);

        JSONArray resultArray = movieDataObject.optJSONArray(MOVIE_RESULT);

        for (int i = 0; i < resultArray.length(); i++) {

            JSONObject movies = resultArray.optJSONObject(i);

            movieList.add(new Movie(movies.getString(MOVIE_TITLE)
                    , movies.getString(MOVIE_RELEASE_DATE)
                    , movies.getString(MOVIE_POSTER)
                    , movies.getDouble(MOVIE_VOTE_AVERAGE)
                    , movies.getString(MOVIE_OVERVIEW)
                    , movies.getString(MOVIE_BACKDROP)));
        }
        return movieList;
    }
}
