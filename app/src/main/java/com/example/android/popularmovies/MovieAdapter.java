package com.example.android.popularmovies;

import android.content.Context;
import android.graphics.Movie;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
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

    public int getCount() {
        return mThumbIds.length;
    }

    //TODO: Delete this if possible?
//    public MovieInfo getItem(int position) {
//        return null;
//    }

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
            //imageView = new ImageView(mContext);

            //imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            //imageView.setLayoutParams(new GridView.LayoutParams(, 855));
            imageView.setAdjustViewBounds(true);

            //imageView.setLayoutParams(new GridView.LayoutParams());

//            imageView.setImageResource();
            //imageView.setImageResource();
//            imageView.setScaleType(ImageView.ScaleType.FIT_START);
//            imageView.setScaleType(ImageView.ScaleType.FIT_END);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        //here is where it set it according to the documentation
        //imageView.setImageResource(mThumbIds[position]);
        //imageView.setImageResource(mThumbIds[position]);

        Log.v(LOG_TAG, movie.poster_path);

        //TODO: Here is where I need to get things working
        //Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/fYzpM9GmpBlIC893fNjoWCwE24H.jpg").into(imageView);
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/" + movie.poster_path).into(imageView);

        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7
    };
}