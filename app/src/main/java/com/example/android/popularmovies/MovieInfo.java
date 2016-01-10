package com.example.android.popularmovies;

/**
 * Created by brandon.miller on 12/27/2015.
 */
public class MovieInfo {
    String original_title;
    String id;
    String poster_path;
    String release_date;
    String vote_average;
    String plot_synopsis;

    public MovieInfo(String original_title, String id, String poster_path, String release_date, String vote_average, String plot_synopsis) {
        this.original_title = original_title;
        this.id = id;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.plot_synopsis = plot_synopsis;
    }
}
