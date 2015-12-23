package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.GenericArrayType;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MoviesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesFragment extends Fragment {
    private ArrayAdapter<String> mMovieAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

    public MoviesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoviesFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static MoviesFragment newInstance(String param1, String param2) {
//        MoviesFragment fragment = new MoviesFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] data = {
                "Jurassic World",
                "Ant Man",
                "Star Wars Episode VII",
                "The Night Before"
        };
        List<String> topMovies = new ArrayList<String>(Arrays.asList(data));



        mMovieAdapter = new ArrayAdapter<String>(getActivity(),
                                                 R.layout.list_item_movie,
                                                 R.id.list_item_movie_textview,
                                                 topMovies);

        //TODO: Delete this
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_main, container, false);

        //TODO: Remove this and add it in an OnStart method
        FetchMovieTask fetchMovieTask = new FetchMovieTask();
        fetchMovieTask.execute();

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_movies);
        listView.setAdapter(mMovieAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                //Toast.makeText(MoviesFragment.this, mMovieAdapter.getItem(position), Toast.LENGTH_SHORT).show();
                                                //Toast.makeText(getActivity(), mMovieAdapter.getItem(position), Toast.LENGTH_SHORT).show();
                                                Intent showDetailActivityIntent = new Intent(getActivity(),DetailActivity.class);
                                                startActivity(showDetailActivityIntent);

                                            }
                                        }
        );

        return rootView;
    }

    public class FetchMovieTask extends AsyncTask<Void, Void, String[]> {

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String moviesJsonStr = null;

        private String[] getMovieDataFromJson(String moviesJsonStr, int numMovies)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            //TODO: Update these
            final String OWM_LIST = "list";
            final String OWM_WEATHER = "weather";
            final String OWM_TEMPERATURE = "temp";
            final String OWM_MAX = "max";
            final String OWM_MIN = "min";
            final String OWM_DESCRIPTION = "main";

            JSONObject moviesJSON = new JSONObject(moviesJsonStr);
            //TODO: It breaks here probably
            //hardcode this value above instead
            JSONArray moviesArray = moviesJSON.getJSONArray("results");

            String[] resultStrs = new String[numMovies];

            for(int i = 0; i < moviesArray.length(); i++) {
                // For now, using the format "Day, description, hi/low"
                String title;

                // Get the JSON object representing the day
                JSONObject movieInfo = moviesArray.getJSONObject(i);

                // The date/time is returned as a long.  We need to convert that
                // into something human-readable, since most people won't read "1400356800" as
                // "this saturday".
                long dateTime;
                // Cheating to convert this to UTC time, which is what we want anyhow

//Begin commented stuff
/*                dateTime = dayTime.setJulianDay(julianStartDay+i);
                day = getReadableDateString(dateTime);

                // description is in a child array called "weather", which is 1 element long.
                JSONObject weatherObject = movieInfo.getJSONArray(OWM_WEATHER).getJSONObject(0);
                description = weatherObject.getString(OWM_DESCRIPTION);

                // Temperatures are in a child object called "temp".  Try not to name variables
                // "temp" when working with temperature.  It confuses everybody.
                JSONObject temperatureObject = movieInfo.getJSONObject(OWM_TEMPERATURE);
                double high = temperatureObject.getDouble(OWM_MAX);
                double low = temperatureObject.getDouble(OWM_MIN);

                highAndLow = formatHighLows(high, low);*/
                resultStrs[i] = "test" + getString(i);
            }

            //For debugging purposes only
//            for (String s : resultStrs) {
//                Log.v(LOG_TAG, "Forecast entry: " + s);
//            }
            return resultStrs;

        }

        @Override
        protected String[] doInBackground(Void... params) {

            //TODO: Update this if/when I pass in a param
            if (params.length != 0) {
                Log.e(LOG_TAG, "Incorrect number of params passed to refresh operation.");
            }

            String sortBy = "popularity.desc";
            //TODO: add this functionality
            int numMovies = 3;
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
                return getMovieDataFromJson(moviesJsonStr, numMovies);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error parsing JSON string: " + moviesJsonStr +"\nError: " + e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {

            if(result != null) {
                mMovieAdapter.clear();
                mMovieAdapter.addAll(result);
            }
        }
        //TODO: Delete this comment
        //end of pasted stuff
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
