package app.example.db.movie.movieapp.loader;

import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;

import app.example.db.movie.movieapp.MovieDetailsActivity;
import app.example.db.movie.movieapp.adapter.TrailerAdapter;
import app.example.db.movie.movieapp.model.Trailer;
import app.example.db.movie.movieapp.utilities.JsonUtils;
import app.example.db.movie.movieapp.utilities.NetworkUtils;

/**
 * Created by Katy on 27.03.2018.
 */

public class FetchTrailerData extends AsyncTask<String, Void, Trailer[]> {
    private static final String TAG = FetchTrailerData.class.getSimpleName();

    private Trailer[] mTrailer = null;
    private TrailerAdapter mTrailerAdapter;

    public FetchTrailerData(TrailerAdapter adapter) {
        this.mTrailerAdapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Trailer[] doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }
        String videos = params[0];
        String id = params[1];
        Log.e(TAG, "videos: " + videos);
        Log.e(TAG, "id: " + id);

        URL trailerUrl = NetworkUtils.buildUrlTrailer(videos,id);

        try {
            String jsonMovieResponse = NetworkUtils
                    .getResponseFromHttpUrl(trailerUrl);

            mTrailer = JsonUtils
                    .parseTrailerJson(jsonMovieResponse);
            return mTrailer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Trailer[] trailerData) {
        if (trailerData != null) {
            mTrailerAdapter.setTrailerData(trailerData);
        }

    }

}