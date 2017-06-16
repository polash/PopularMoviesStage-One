package com.sksanwar.popularmoviesstage_one;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.sksanwar.popularmoviesstage_one.Adapter.MovieAdapter;
import com.sksanwar.popularmoviesstage_one.Model.Movie;
import com.sksanwar.popularmoviesstage_one.Utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.sksanwar.popularmoviesstage_one.Utils.DataParsing.getMovieDataFromJson;


public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieAdapterOnclickHandler {

    /**
     * Constant for Query for Top Rated movies and Popular Movies
     **/
    private static final String EXTRA_MOVIES = "EXTRA_MOVIES";
    private static final String POPULAR_MOVIE_QUERY = "popular";
    private static final String TOPRATED_MOVIE_QUERY = "top_rated";
    private final int viewCacheSize = 20;
    Movie movieObject;
    private List<Movie> mMovies;
    /**
     * Views variable
     **/
    private RecyclerView mRecyclerView;
    private MovieAdapter adapter;
    private ProgressBar mLoadingIndicator;
    private View emptyView;

    /**
     * OnCreate method where all the views are populated and adapter is seted
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_image);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemViewCacheSize(viewCacheSize);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        getPopularMovies();

        adapter = new MovieAdapter(this, mMovies, this);
        mRecyclerView.setAdapter(adapter);

    }

    /**
     * savedInstant method
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        List<Movie> movies = adapter.getMovies();
        if (movies != null && !movies.isEmpty()) {
            outState.putParcelableArrayList(EXTRA_MOVIES, (ArrayList<? extends Parcelable>) movies);
        }
        super.onSaveInstanceState(outState);
    }

    /**
     * This method shows when recycler view and error message view shown
     */
    private void showMovieDataResult() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * When there will be no connection of internet then this method will be visible
     */
    private void showConnectionError() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        emptyView.setVisibility(View.VISIBLE);
    }

    /**
     * Method for getting popular movies from the API
     */
    private void getPopularMovies() {
        showMovieDataResult();
        getSupportActionBar().setTitle("Pop Movies");
        new FetchMovieData().execute(POPULAR_MOVIE_QUERY);
    }

    /**
     * Method for getting top rated movies from the API
     */
    private void getTopRatedMovies() {
        showMovieDataResult();
        getSupportActionBar().setTitle("Top Movies");
        new FetchMovieData().execute(TOPRATED_MOVIE_QUERY);
    }

    /**
     * Menu layout populated here with Menuinflater
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    /**
     *
     * @param item demonstrate which item will be selected with switch case
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.popularity:
                getPopularMovies();
                item.setChecked(true);
                return true;
            case R.id.top_rated:
                getTopRatedMovies();
                item.setChecked(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * References
     * http://stackoverflow.com/questions/2906925/how-do-i-pass-an-object-from-one-activity-to-another-on-android
     * http://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
     * https://developer.android.com/guide/components/activities/parcelables-and-bundles.html
     * @param movies
     */
    @Override
    public void onClick(Movie movies) {
        Context context = this;

        //creating class instance for passing it to intent
        Class<DetailActivity> detailActivityClass = DetailActivity.class;

        //getting all data from the movie parceable class and stored in an object to pass
        movieObject = new Movie(movies.getTitle(), movies.getReleaseDate(), movies.getPosterPath(),
                movies.getVoteAverage(), movies.getOverView(), movies.getBackdrops());

        //bundle instance
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_MOVIES, movieObject);

        //Intent function to send to detail activity
        Intent dataSendToDetailActivity = new Intent(context, detailActivityClass).putExtras(bundle);
        startActivity(dataSendToDetailActivity);
    }

    /**
     * AsyncTask for backGround Data loading
     */
    class FetchMovieData extends
            AsyncTask<String, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(String... params) {
            String movieJsonString = null;
            if (params == null) {
                return null;
            }
            String movie_query = params[0];
            URL movieRequestURL = NetworkUtils.buildUrl(movie_query);
            try {
                movieJsonString = NetworkUtils.getResponseFromHttpUrl(movieRequestURL);
                return mMovies = getMovieDataFromJson(movieJsonString);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies == null) {
                showConnectionError();
            } else {
                adapter.setMovieData(movies);
            }
        }
    }
}
