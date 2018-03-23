package app.example.db.movie.movieapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.net.URL;

import app.example.db.movie.movieapp.adapter.MovieAdapter;
import app.example.db.movie.movieapp.model.Movie;
import app.example.db.movie.movieapp.utilities.JsonUtils;
import app.example.db.movie.movieapp.utilities.NetworkUtils;

// https://github.com/udacity/Advanced_Android_Development/blob/master/app/src/main/java/com/example/android/sunshine/app/ForecastFragment.java

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String SORTING_TITLE_KEY = "sortingTitle";
    private static final String SORTING_QUERY_KEY = "sortingQuery";

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private Movie[] mMovie = null;
    private String mSortingQuery;
    private String mSortingTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview_movie);

        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

        /*
        * Use this setting to improve performance if you know that changes in content do not
        * change the child layout size in the RecyclerView
        */
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);
        mMovieAdapter.notifyDataSetChanged();

        loadMovieData();


        // recovering the instance state
        /* https://github.com/fjoglar/android-dev-challenge/blob/master/articles/lesson-06-lifecycle.md */
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(SORTING_QUERY_KEY) && savedInstanceState.containsKey(SORTING_TITLE_KEY)) {
                mSortingQuery = savedInstanceState.getString(SORTING_QUERY_KEY);
                mSortingTitle = savedInstanceState.getString(SORTING_TITLE_KEY);
                setTitle(mSortingTitle);
                new FetchMovieData().execute(mSortingQuery);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SORTING_TITLE_KEY, mSortingTitle);
        outState.putString(SORTING_QUERY_KEY, mSortingQuery);
    }

    private void loadMovieData() {
        if (!isOnline()) return;
        showMovieDataView();

        String defaultList = "popular";
        new FetchMovieData().execute(defaultList);
        setTitle("Popular Movies");
    }

    //https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!isOnline()) return false;
        int id = item.getItemId();
        int pref_popular = R.id.pref_popular;
        int pref_top_rated = R.id.pref_top_rated;

        if (id == pref_popular) {
            Log.e(TAG, "pop");
            mSortingQuery = "popular";
            mSortingTitle = "Popular Movies";
            new FetchMovieData().execute(mSortingQuery);
            setTitle(mSortingTitle);
        } else if (id == pref_top_rated) {
            Log.e(TAG, "top rated");
            mSortingQuery = "top_rated";
            mSortingTitle = "Top Rated Movies";
            new FetchMovieData().execute(mSortingQuery);
            setTitle(mSortingTitle);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(Movie movieItem) {
        if (!isOnline()) return;
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("movieObject", movieItem);
        startActivity(intent);
    }


    private void showMovieDataView() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    public class FetchMovieData extends AsyncTask<String, Void, Movie[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Movie[] doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String movie = params[0];
            URL movieUrl = NetworkUtils.buildUrl(movie);

            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieUrl);

                mMovie = JsonUtils
                        .parseMovieJson(jsonMovieResponse);

                return mMovie;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movie[] movieData) {
            if (movieData != null) {
                showMovieDataView();
                mMovieAdapter.setMovieData(movieData);

            }
        }
    }


}
