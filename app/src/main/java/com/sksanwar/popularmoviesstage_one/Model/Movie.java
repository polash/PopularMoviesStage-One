package com.sksanwar.popularmoviesstage_one.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sksho on 12-May-17.
 */

public class Movie implements Parcelable {

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    String mTitle;
    String mReleaseDate;
    String mPosterPath;
    double mVoteAverage;
    String mOverView;
    String mBackdrops;

    public Movie() {
    }

    public Movie(String title, String releaseDate, String posterPath,
                 double voteAverage, String overView, String backDrops) {
        this.mTitle = title;
        this.mReleaseDate = releaseDate;
        this.mPosterPath = posterPath;
        this.mVoteAverage = voteAverage;
        this.mOverView = overView;
        this.mBackdrops = backDrops;
    }

    protected Movie(Parcel in) {
        this.mTitle = in.readString();
        this.mReleaseDate = in.readString();
        this.mPosterPath = in.readString();
        this.mVoteAverage = Double.parseDouble(in.readString());
        this.mOverView = in.readString();
        this.mBackdrops = in.readString();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(double mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

    public String getOverView() {
        return mOverView;
    }

    public void setOverView(String mOverView) {
        this.mOverView = mOverView;
    }

    public String getBackdrops() {
        return mBackdrops;
    }

    public void setmBackdrops(String mBackdrops) {
        this.mBackdrops = mBackdrops;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "mTitle='" + mTitle + '\'' +
                ", mReleaseDate='" + mReleaseDate + '\'' +
                ", mPosterPath='" + mPosterPath + '\'' +
                ", mVoteAverage='" + mVoteAverage + '\'' +
                ", mOverView='" + mOverView + '\'' +
                ", mBackdrops='" + mBackdrops + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mReleaseDate);
        dest.writeString(this.mPosterPath);
        dest.writeString(String.valueOf(this.mVoteAverage));
        dest.writeString(this.mOverView);
        dest.writeString(this.mBackdrops);
    }
}
