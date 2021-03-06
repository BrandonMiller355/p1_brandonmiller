package com.example.android.popularmovies;

import android.content.Intent;
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

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class DetailFragment extends Fragment {

        public DetailFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            ImageView imageDetailView = (ImageView) rootView.findViewById(R.id.detail_image1);
            Picasso.with(rootView.getContext()).load("http://image.tmdb.org/t/p/w185/"
                    + getActivity().getIntent().getExtras().getString(Intent.EXTRA_TEXT))
                    .into(imageDetailView);

            TextView title = (TextView) rootView.findViewById(R.id.title);
            title.setText(getActivity().getIntent().getExtras().getString(Intent.EXTRA_TITLE));

            TextView releaseDate = (TextView) rootView.findViewById(R.id.releaseDate);
            releaseDate.setText("Release Date: "
                    + getActivity().getIntent().getExtras().getString(Intent.EXTRA_DATA_REMOVED));

            TextView voteAverage = (TextView) rootView.findViewById(R.id.voteAverage);
            voteAverage.setText("Vote Average: "
                    + getActivity().getIntent().getExtras().getString(Intent.EXTRA_PHONE_NUMBER)
                    + "/10.00");

            TextView plotSynopsis = (TextView) rootView.findViewById(R.id.plotSynopsis);
            plotSynopsis.setText("Description: "
                    + getActivity().getIntent().getExtras().getString(Intent.EXTRA_SUBJECT));

            return rootView;
        }
    }

}
