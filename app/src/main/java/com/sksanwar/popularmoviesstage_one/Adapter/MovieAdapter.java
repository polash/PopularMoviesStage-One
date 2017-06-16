package com.sksanwar.popularmoviesstage_one.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sksanwar.popularmoviesstage_one.Model.Movie;
import com.sksanwar.popularmoviesstage_one.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sksho on 12-May-17.
 */

public class MovieAdapter extends
        RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    final private String BASE_URL_POSTER = "http://image.tmdb.org/t/p/";
    final private String POSTER_SIZE = "w185";

    final private MovieAdapterOnclickHandler mClickHandler;

    private List<Movie> mMovies;
    private Context mContext;
    private ImageView imageView;

    public MovieAdapter(Context mContext, List<Movie> mMovies,
                        MovieAdapterOnclickHandler mClickHandler) {
        this.mContext = mContext;
        this.mMovies = mMovies;
        this.mClickHandler = mClickHandler;
    }

    public void setMovieData(List<Movie> movieData) {
        mMovies = movieData;
        notifyDataSetChanged();
    }

    public List<Movie> getMovies() {
        return mMovies;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View moviesView = inflater.inflate(R.layout.movie_item_list, parent, false);

        return new ViewHolder(moviesView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie currentMovie = mMovies.get(position);
        String image_url = BASE_URL_POSTER +
                POSTER_SIZE + currentMovie.getPosterPath();
        Picasso.with(mContext).load(image_url).into(imageView);
    }

    @Override
    public int getItemCount() {
        if (null == mMovies) return 0;
        return mMovies.size();
    }

    /**
     * Interface for handling the click events
     */
    public interface MovieAdapterOnclickHandler {
        void onClick(Movie movie);
    }

    /**
     * ViewHolder Class
     */
    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public ViewHolder(View itemView) {

            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_image_id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition(); //getting the adapter position
            Movie mv = mMovies.get(adapterPosition);
            mClickHandler.onClick(mv);
        }
    }

}
