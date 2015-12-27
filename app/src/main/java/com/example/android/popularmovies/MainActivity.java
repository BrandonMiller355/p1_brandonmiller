// Credits: http://developer.android.com/guide/topics/ui/layout/gridview.html

package com.example.android.popularmovies;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        MovieAdapter movieAdapter;
        movieAdapter = new MovieAdapter(this, new ArrayList<MovieInfo>());
        GridView gridView = (GridView) findViewById(R.id.gridview_movies);
        gridView.setAdapter(movieAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "" + i, Toast.LENGTH_SHORT).show();
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MoviesFragment())
                    .commit();
        }

//        MoviesFragment.FetchMovieTask fetchMovieTask = new MoviesFragment.FetchMovieTask();
//        fetchMovieTask.execute();

        //Log.v("main", getString(R.string.my_api_key));

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
}
