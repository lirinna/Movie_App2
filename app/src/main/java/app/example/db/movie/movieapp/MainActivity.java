package app.example.db.movie.movieapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import app.example.db.movie.movieapp.adapter.FavoritesCursorAdapter;
import app.example.db.movie.movieapp.adapter.MovieAdapter;
import app.example.db.movie.movieapp.data.MovieContract;
import app.example.db.movie.movieapp.loader.FetchMovieData;
import app.example.db.movie.movieapp.model.Movie;

// https://github.com/udacity/Advanced_Android_Development/blob/master/app/src/main/java/com/example/android/sunshine/app/ForecastFragment.java

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler, LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String SORTING_TITLE_KEY = "sortingTitle";
    private static final String SORTING_QUERY_KEY = "sortingQuery";

    public static final int TASK_LOADER_ID = 0;

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    FavoritesCursorAdapter favoritesAdapter;

    private String mSortingQuery;
    private String mSortingTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview_movie);

        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
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
                if (mSortingQuery.equals("favorites")) {

                    favoritesAdapter = new FavoritesCursorAdapter(this);
                    mRecyclerView.setAdapter(favoritesAdapter);
                    getSupportLoaderManager().initLoader(
                            TASK_LOADER_ID, null, this);
                    setTitle(mSortingTitle);

                } else {
                    new FetchMovieData(mMovieAdapter).execute(mSortingQuery);
                }

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

        String defaultList = "popular";
        new FetchMovieData(mMovieAdapter).execute(defaultList);
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
        int pref_favorites = R.id.pref_favorite;

        if (id == pref_popular) {
            Log.e(TAG, "pop");
            mSortingQuery = "popular";
            mSortingTitle = "Popular Movies";

            mMovieAdapter = new MovieAdapter(this);
            mRecyclerView.setAdapter(mMovieAdapter);

            new FetchMovieData(mMovieAdapter).execute(mSortingQuery);
            setTitle(mSortingTitle);
        } else if (id == pref_top_rated) {
            Log.e(TAG, "top rated");
            mSortingQuery = "top_rated";
            mSortingTitle = "Top Rated Movies";

            mMovieAdapter = new MovieAdapter(this);
            mRecyclerView.setAdapter(mMovieAdapter);

            new FetchMovieData(mMovieAdapter).execute(mSortingQuery);
            setTitle(mSortingTitle);
        } else if (id == pref_favorites) {
            Log.e(TAG, "favorites");
            mSortingQuery = "favorites";
            mSortingTitle = "Favorite Movies";

            favoritesAdapter = new FavoritesCursorAdapter(this);
            mRecyclerView.setAdapter(favoritesAdapter);
            getSupportLoaderManager().initLoader(
                    TASK_LOADER_ID, null, this);
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


    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle loaderArgs) {
        return new AsyncTaskLoader<Cursor>(this) {
            Cursor mMovies = null;

            @Override
            protected void onStartLoading() {
                if (mMovies != null) {
                    deliverResult(mMovies);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    return getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            MovieContract.MovieEntry.COLUMN_NAME_MOVIE_ID);
                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(Cursor data) {
                mMovies = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        favoritesAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        favoritesAdapter.swapCursor(null);
    }

}
