package com.example.android.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MoviesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesFragment extends Fragment {
    private MovieAdapter mMovieAdapter;
    private static final String LOG_TAG = MoviesFragment.class.getSimpleName();

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        UpdateMovies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMovieAdapter = new MovieAdapter(getActivity(), new ArrayList<MovieInfo>());

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_movies);
        gridView.setAdapter(mMovieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent showDetailActivityIntent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_UID, mMovieAdapter.getItem(i).id)
                        .putExtra(Intent.EXTRA_TITLE, mMovieAdapter.getItem(i).original_title)
                        .putExtra(Intent.EXTRA_TEXT, mMovieAdapter.getItem(i).poster_path)
                        .putExtra(Intent.EXTRA_DATA_REMOVED, mMovieAdapter.getItem(i).release_date)
                        .putExtra(Intent.EXTRA_SUBJECT, mMovieAdapter.getItem(i).plot_synopsis)
                        .putExtra(Intent.EXTRA_PHONE_NUMBER, mMovieAdapter.getItem(i).vote_average);
                startActivity(showDetailActivityIntent);
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.moviesfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

/*        //noinspection SimplifiableIfStatement
        if (id == R.id.) {
            UpdateWeather();
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public void UpdateMovies() {
        FetchMovieTask fetchMovieTask = new FetchMovieTask();
        fetchMovieTask.execute();
    }

    public class FetchMovieTask extends AsyncTask<Void, Void, MovieInfo[]> {

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String moviesJsonStr = null;

        private MovieInfo[] getMovieDataFromJson(String moviesJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String RESULTS = "results";
            final String TITLE = "title";
            final String ID = "id";
            final String POSTER_PATH = "poster_path";
            final String RELEASE_DATE = "release_date";
            final String VOTE_AVERAGE = "vote_average";
            final String PLOT_SYNOPSIS = "overview";

            JSONObject moviesJSON = new JSONObject(moviesJsonStr);
            JSONArray moviesArray = moviesJSON.getJSONArray(RESULTS);

            //TODO: Rename this variable
            MovieInfo[] movies2 = new MovieInfo[moviesArray.length()];

            //for(int i = 0; i < moviesArray.length(); i++) {
            for(int i = 0; i < moviesArray.length(); i++) {
                // For now, using the format "Day, description, hi/low"
                String title;
                String poster_path;
                String id;
                String releaseDate;
                String voteAverage;
                String plotSynopsis;

                // Get the JSON object representing the movie
                JSONObject movieInfo = moviesArray.getJSONObject(i);

                title = movieInfo.getString(TITLE);
                poster_path = movieInfo.getString(POSTER_PATH);
                id = movieInfo.getString(ID);
                releaseDate = movieInfo.getString(RELEASE_DATE);
                voteAverage = movieInfo.getString(VOTE_AVERAGE);
                plotSynopsis = movieInfo.getString(PLOT_SYNOPSIS);

                movies2[i] = new MovieInfo(title, id, poster_path, releaseDate, voteAverage, plotSynopsis);

                //TODO: Change this back
                //resultStrs[i] = "title: " + title + ", id: " + id + ", posterpath: " + poster_path;
                //resultStrs[i] = poster_path;
            }

            return movies2;

        }

        @Override
        protected MovieInfo[] doInBackground(Void... params) {

            //TODO: Update this if/when I pass in a param
            if (params.length != 0) {
                Log.e(LOG_TAG, "Incorrect number of params passed to refresh operation.");
            }


            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String sortBy = sharedPreferences.getString(getString(R.string.pref_sortby_key),
                    getString(R.string.pref_sortby_default));

            String apiKey = getString(R.string.my_api_key);

            try {
                final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
                Log.v(LOG_TAG, getString(R.string.my_api_key));
                //example Url = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=[yourapikey]";
                final String SORT_BY_PARAM = "sort_by";
                final String API_KEY_PARAM = "api_key";

                //TODO: Update this
                Uri uri = Uri.parse(BASE_URL).buildUpon()
                        //.appendQueryParameter(POSTAL_CODE_PARAM, params[0])
                        .appendQueryParameter(SORT_BY_PARAM, sortBy)
                        //.appendQueryParameter(COUNT_PARAM, Integer.toString(numMovies))
                        .appendQueryParameter(API_KEY_PARAM, apiKey)
                        .build();

                // For debugging purposes only
                Log.v(LOG_TAG, "The built url is: " + uri.toString());

                URL url = new URL(uri.toString());

                //urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                } else {
                    //continue
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }

                if (stringBuffer.length() == 0) {
                    return null;
                } else {
                    //continue
                }

                moviesJsonStr = stringBuffer.toString();

                // For debugging purposes only.
                Log.v(LOG_TAG, moviesJsonStr);

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMovieDataFromJson(moviesJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error parsing JSON string: " + moviesJsonStr +"\nError: " + e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(MovieInfo[] result) {

            if(result != null) {
                mMovieAdapter.clear();
                mMovieAdapter.addAll(result);
            }
        }
    }
}
