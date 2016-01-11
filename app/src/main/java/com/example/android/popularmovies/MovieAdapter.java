package com.example.android.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by brandon.miller on 12/27/2015.
 * Source: http://developer.android.com/guide/topics/ui/layout/gridview.html
 */

public class MovieAdapter extends ArrayAdapter<MovieInfo> {
    private final static String LOG_TAG = MovieAdapter.class.getSimpleName();

    private Context mContext = this.getContext();

    public MovieAdapter(Context c, List<MovieInfo> movieInfoList) {

        super(c, 0, movieInfoList);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieInfo movie = getItem(position);

        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item, parent, false);
            imageView = (ImageView) convertView.findViewById(R.id.image_movie);
            imageView.setAdjustViewBounds(true);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/" + movie.poster_path).into(imageView);

        return imageView;
    }
}