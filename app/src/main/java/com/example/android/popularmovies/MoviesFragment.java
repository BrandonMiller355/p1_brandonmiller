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
import android.widget.GridView;
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
    private MovieAdapter mMovieAdapter;

/*    MovieInfo[] movies = {
            new MovieInfo("title", "2345 id", "/fYzpM9GmpBlIC893fNjoWCwE24H.jpg"),
            new MovieInfo("title2", "345 id", "/5aGhaIHYuQbqlHWvWYqMCnj40y2.jpg"),
            new MovieInfo("title3", "45 id", "/jjBgi2r5cRt36xF6iNUEhzscEcb.jpg"),
            new MovieInfo("title4", "345 id", "/D6e8RJf2qUstnfkTslTXNTUAlT.jpg"),
            new MovieInfo("title5", "345 id", "/kqjL17yufvn9OVLyXYpvtyrFfak.jpg"),
            new MovieInfo("title6", "345 id", "/fqe8JxDNO8B8QfOGTdjh6sPCdSC.jpg"),
            new MovieInfo("title7", "345 id", "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"),
            new MovieInfo("title8", "345 id", "/mSvpKOWbyFtLro9BjfEGqUw5dXE.jpg"),
            new MovieInfo("title9", "345 id", "/vgAHvS0bT3fpcpnJqT6uDTUsHTo.jpg"),
            new MovieInfo("title10", "345 id", "/5JU9ytZJyR3zmClGmVm9q4Geqbd.jpg"),
            new MovieInfo("title11", "345 id", "/oXUWEc5i3wYyFnL1Ycu8ppxxPvs.jpg"),
            new MovieInfo("title12", "345 id", "/50d0XQQETSyg3bwBXhC7K33pKgc.jpg"),
            new MovieInfo("title13", "345 id", "/q0R4crx2SehcEEQEkYObktdeFy.jpg"),
            new MovieInfo("title14", "345 id", "/cWERd8rgbw7bCMZlwP207HUXxym.jpg"),
            new MovieInfo("title15", "345 id", "/z2sJd1OvAGZLxgjBdSnQoLCfn3M.jpg"),
            new MovieInfo("title16", "345 id", "/aAmfIX3TT40zUHGcCKrlOZRKC7u.jpg"),
            new MovieInfo("title17", "345 id", "/p2SdfGmQRaw8xhFbexlHL7srMM8.jpg"),
            new MovieInfo("title18", "345 id", "/t90Y3G8UGQp0f0DrP60wRu9gfrH.jpg"),
            new MovieInfo("title19", "345 id", "/2ZckiMTfSkCep2JTtZbr73tnQbN.jpg"),
            new MovieInfo("title20", "345 id", "/rSZs93P0LLxqlVEbI001UKoeCQC.jpg"),
            new MovieInfo("title20", "345 id", "/rSZs93P0LLxqlVEbI001UKoeCQC.jpg"),
            new MovieInfo("title20", "345 id", "/rSZs93P0LLxqlVEbI001UKoeCQC.jpg"),
            new MovieInfo("title20", "345 id", "/rSZs93P0LLxqlVEbI001UKoeCQC.jpg"),
            new MovieInfo("title20", "345 id", "/rSZs93P0LLxqlVEbI001UKoeCQC.jpg")
    };*/

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

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // It okay to put this before the adapter?
        //TODO: Remove this and add it in an OnStart method (after implementing menu options)
        FetchMovieTask fetchMovieTask = new FetchMovieTask();
        fetchMovieTask.execute();


        /*begin trying stuff:
        *
        * it was:
        mMovieAdapter = new MovieAdapter(getActivity(),
                                                 Arrays.asList(movies));

        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_movies);
        gridView.setAdapter(mMovieAdapter);
        *
        * */

        mMovieAdapter = new MovieAdapter(getActivity(), new ArrayList<MovieInfo>());

        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_movies);
        gridView.setAdapter(mMovieAdapter);

        // Get a reference to the ListView, and attach this adapter to it.
/*        ListView listView = (ListView) rootView.findViewById(R.id.listview_movies);
        listView.setAdapter(mMovieAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                Intent showDetailActivityIntent = new Intent(getActivity(),DetailActivity.class)
                                                        .putExtra(Intent.EXTRA_TEXT, mMovieAdapter.getItem(position));
                                                startActivity(showDetailActivityIntent);
                                            }
                                        }
        );*/

        return rootView;
    }

    public class FetchMovieTask extends AsyncTask<Void, Void, MovieInfo[]> {

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String moviesJsonStr = null;

        private MovieInfo[] getMovieDataFromJson(String moviesJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            //TODO: Update these
            final String RESULTS = "results";
            final String TITLE = "title";
            final String ID = "id";
            final String POSTER_PATH = "poster_path";
//            final String OWM_MAX = "max";
//            final String OWM_MIN = "min";
//            final String OWM_DESCRIPTION = "main";

            JSONObject moviesJSON = new JSONObject(moviesJsonStr);
            //TODO: It breaks here probably
            //hardcode this value above instead
            JSONArray moviesArray = moviesJSON.getJSONArray(RESULTS);

            String[] resultStrs = new String[moviesArray.length()];
            MovieInfo[] movies2 = new MovieInfo[moviesArray.length()];

            //for(int i = 0; i < moviesArray.length(); i++) {
            for(int i = 0; i < resultStrs.length; i++) {
                // For now, using the format "Day, description, hi/low"
                String title;
                String poster_path;
                String id;

                // Get the JSON object representing the movie
                JSONObject movieInfo = moviesArray.getJSONObject(i);

                // The date/time is returned as a long.  We need to convert that
                // into something human-readable, since most people won't read "1400356800" as
                // "this saturday".
                long dateTime;
                // Cheating to convert this to UTC time, which is what we want anyhow

                //dateTime = dayTime.setJulianDay(julianStartDay+i);
                //day = getReadableDateString(dateTime);

                title = movieInfo.getString(TITLE);
                poster_path = movieInfo.getString(POSTER_PATH);
                id = movieInfo.getString(ID);

                movies2[i] = new MovieInfo(title, id, poster_path);


//                // description is in a child array called "weather", which is 1 element long.
//                JSONObject movieObject = movieInfo.getJSONArray(TITLE).getJSONObject(0);
//                description = movieObject.getString(OWM_DESCRIPTION);

                // Temperatures are in a child object called "temp".  Try not to name variables
                // "temp" when working with temperature.  It confuses everybody.
//                JSONObject temperatureObject = movieInfo.getJSONObject(OWM_TEMPERATURE);
//                double high = temperatureObject.getDouble(OWM_MAX);
//                double low = temperatureObject.getDouble(OWM_MIN);

                //highAndLow = formatHighLows(high, low);
                //TODO: Change this back
                //resultStrs[i] = "title: " + title + ", id: " + id + ", posterpath: " + poster_path;
                resultStrs[i] = poster_path;
            }

            //For debugging purposes only
            for (String s : resultStrs) {
                Log.v(LOG_TAG, "movie entry: " + s);
            }
            return movies2;

        }

        @Override
        protected MovieInfo[] doInBackground(Void... params) {

            //TODO: Update this if/when I pass in a param
            if (params.length != 0) {
                Log.e(LOG_TAG, "Incorrect number of params passed to refresh operation.");
            }

            String sortBy = "popularity.desc";
            //TODO: add this functionality

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

                // TODO: Here is where I should load that stuff ? Nope, did it above
/*                MovieInfo[] movies = new MovieInfo[result.length];

                for(int i = 0; i < result.length; i++) {
                    movies[i].original_title = result[i];
                    movies[i].id = result[i];
                    movies[i].poster_path = result[i];
                }*/

                /*MovieInfo[] movies = {
                        new MovieInfo("title", "2345 id", "/fYzpM9GmpBlIC893fNjoWCwE24H.jpg"),
                        new MovieInfo("title2", "345 id", "/5aGhaIHYuQbqlHWvWYqMCnj40y2.jpg"),
                        new MovieInfo("title3", "45 id", "/jjBgi2r5cRt36xF6iNUEhzscEcb.jpg"),
                        new MovieInfo("title4", "345 id", "/D6e8RJf2qUstnfkTslTXNTUAlT.jpg"),
                        new MovieInfo("title5", "345 id", "/kqjL17yufvn9OVLyXYpvtyrFfak.jpg"),
                        new MovieInfo("title6", "345 id", "/fqe8JxDNO8B8QfOGTdjh6sPCdSC.jpg"),
                        new MovieInfo("title7", "345 id", "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"),
                        new MovieInfo("title8", "345 id", "/mSvpKOWbyFtLro9BjfEGqUw5dXE.jpg"),
                        new MovieInfo("title9", "345 id", "/vgAHvS0bT3fpcpnJqT6uDTUsHTo.jpg"),
                        new MovieInfo("title10", "345 id", "/5JU9ytZJyR3zmClGmVm9q4Geqbd.jpg"),
                        new MovieInfo("title11", "345 id", "/oXUWEc5i3wYyFnL1Ycu8ppxxPvs.jpg"),
                        new MovieInfo("title12", "345 id", "/50d0XQQETSyg3bwBXhC7K33pKgc.jpg"),
                        new MovieInfo("title13", "345 id", "/q0R4crx2SehcEEQEkYObktdeFy.jpg"),
                        new MovieInfo("title14", "345 id", "/cWERd8rgbw7bCMZlwP207HUXxym.jpg"),
                        new MovieInfo("title15", "345 id", "/z2sJd1OvAGZLxgjBdSnQoLCfn3M.jpg"),
                        new MovieInfo("title16", "345 id", "/aAmfIX3TT40zUHGcCKrlOZRKC7u.jpg"),
                        new MovieInfo("title17", "345 id", "/p2SdfGmQRaw8xhFbexlHL7srMM8.jpg"),
                        new MovieInfo("title18", "345 id", "/t90Y3G8UGQp0f0DrP60wRu9gfrH.jpg"),
                        new MovieInfo("title19", "345 id", "/2ZckiMTfSkCep2JTtZbr73tnQbN.jpg"),
                        new MovieInfo("title20", "345 id", "/rSZs93P0LLxqlVEbI001UKoeCQC.jpg"),
                        new MovieInfo("title20", "345 id", "/rSZs93P0LLxqlVEbI001UKoeCQC.jpg"),
                        new MovieInfo("title20", "345 id", "/rSZs93P0LLxqlVEbI001UKoeCQC.jpg"),
                        new MovieInfo("title20", "345 id", "/rSZs93P0LLxqlVEbI001UKoeCQC.jpg"),
                        new MovieInfo("title20", "345 id", "/rSZs93P0LLxqlVEbI001UKoeCQC.jpg")
                };*/

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
