package com.example.android.popularmovies;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Brandon on 12/22/2015.
 */
public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //TODO: Delete this debugging line
        Log.d(DetailActivity.class.toString(), "The text that was passed is: " + getIntent().getExtras().getString(Intent.EXTRA_TEXT)
                + getIntent().getExtras().getString(Intent.EXTRA_UID)
                + getIntent().getExtras().getString(Intent.EXTRA_TITLE));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {

        public DetailFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            ImageView imageDetailView = (ImageView) rootView.findViewById(R.id.detail_image1);
            Picasso.with(rootView.getContext()).load("http://image.tmdb.org/t/p/w185/" + getActivity().getIntent().getExtras().getString(Intent.EXTRA_TEXT)).into(imageDetailView);

/*
            TextView textView = (TextView) rootView.findViewById(R.id.detail_text);
            textView.setText(getActivity().getIntent().getExtras().getString(Intent.EXTRA_TEXT));

            TextView textView2 = (TextView) rootView.findViewById(R.id.detail_text2);
            textView2.setText(getActivity().getIntent().getExtras().getString(Intent.EXTRA_UID));
*/

            TextView textView3 = (TextView) rootView.findViewById(R.id.detail_text3);
            textView3.setText(getActivity().getIntent().getExtras().getString(Intent.EXTRA_TITLE));

            TextView releaseDate = (TextView) rootView.findViewById(R.id.releaseDate);
            releaseDate.setText(getActivity().getIntent().getExtras().getString(Intent.EXTRA_DATA_REMOVED));

            TextView voteAverage = (TextView) rootView.findViewById(R.id.voteAverage);
            voteAverage.setText(getActivity().getIntent().getExtras().getString(Intent.EXTRA_PHONE_NUMBER));

            TextView plotSynopsis = (TextView) rootView.findViewById(R.id.plotSynopsis);
            plotSynopsis.setText(getActivity().getIntent().getExtras().getString(Intent.EXTRA_SUBJECT));

            return rootView;
        }
    }

}
