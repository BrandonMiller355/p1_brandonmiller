package com.example.android.popularmovies;

/**
 * Created by brandon.miller on 12/27/2015.
 */
public class MovieInfo {
    String original_title;
    String id;
    String poster_path;

    public MovieInfo(String original_title, String id, String poster_path) {
        this.original_title = original_title;
        this.id = id;
        this.poster_path = poster_path;
    }
}
