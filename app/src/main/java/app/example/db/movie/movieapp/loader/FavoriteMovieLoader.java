package app.example.db.movie.movieapp.loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import app.example.db.movie.movieapp.adapter.FavoritesCursorAdapter;
import app.example.db.movie.movieapp.data.MovieContract;


/**
 * Created by Katy on 26.03.2018.
 * Used Sources: https://github.com/udacity/ud851-Exercises/blob/student/Lesson09-ToDo-List/T09.06-Solution-Delete/app/src/main/java/com/example/android/todolist/MainActivity.java
 */

public class FavoriteMovieLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = FavoriteMovieLoader.class.getSimpleName();
    private FavoritesCursorAdapter mFavoritesAdapter;
    private Context mContext;


    public FavoriteMovieLoader(Context context, FavoritesCursorAdapter favoritesAdapter) {
        this.mContext = context;
        this.mFavoritesAdapter = favoritesAdapter;
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle loaderArgs) {
        return new AsyncTaskLoader<Cursor>(mContext) {
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
                    return getContext().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
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
        mFavoritesAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mFavoritesAdapter.swapCursor(null);
    }
}
