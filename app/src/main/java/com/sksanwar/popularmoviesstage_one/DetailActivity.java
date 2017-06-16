package com.sksanwar.popularmoviesstage_one;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.sksanwar.popularmoviesstage_one.Model.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_MOVIES = "EXTRA_MOVIES";
    final private String BASE_URL = "http://image.tmdb.org/t/p/";
    final private String BACKDROP_IMAGE_SIZE = "w300";
    final private String POSTER_IMAGE_SIZE = "w185";
    Movie dataReceivedFromMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Finding the views with their ID's
        Viewholder holder = new Viewholder();

        holder.movieTitle = (TextView) findViewById(R.id.tv_title_content);
        holder.backDropsImageView = (ImageView) findViewById(R.id.thumbnail_imageView_id);
        holder.posterImageView = (ImageView) findViewById(R.id.poster_view_id);
        holder.movieReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        holder.movieRating = (TextView) findViewById(R.id.tv_rating);
        holder.movieOverview = (TextView) findViewById(R.id.tv_overview_content);

        //Receiving the data from the Mainactivity
        dataReceivedFromMainActivity = getIntent().getExtras().getParcelable(EXTRA_MOVIES);

        //Showing the BackDrop Thumbnail
        String backDropImage = BASE_URL + BACKDROP_IMAGE_SIZE + dataReceivedFromMainActivity.getBackdrops();
        Picasso.with(this).load(backDropImage).into(holder.backDropsImageView);

        //Showing poster Image
        String posterImage = BASE_URL + POSTER_IMAGE_SIZE + dataReceivedFromMainActivity.getPosterPath();
        Picasso.with(this).load(posterImage).into(holder.posterImageView);

        //Showing the Original Title of the Movie
        holder.movieTitle.setText(dataReceivedFromMainActivity.getTitle());

        //Showing the release date of the movie
        holder.movieReleaseDate.setText(dataReceivedFromMainActivity.getReleaseDate());

        //Showing the movie Rating
        holder.movieRating.setText(String.valueOf(dataReceivedFromMainActivity.getVoteAverage()));

        //Showing the Overview of the movie
        holder.movieOverview.setText(dataReceivedFromMainActivity.getOverView());
    }

    /**
     * viewholder class for the views
     */
    static class Viewholder {
        ImageView backDropsImageView;
        ImageView posterImageView;
        TextView movieTitle;
        TextView movieReleaseDate;
        TextView movieRating;
        TextView movieOverview;
    }
}
