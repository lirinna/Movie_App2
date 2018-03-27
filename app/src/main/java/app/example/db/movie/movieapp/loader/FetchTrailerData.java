package app.example.db.movie.movieapp.loader;

import android.os.AsyncTask;

import java.net.URL;

import app.example.db.movie.movieapp.model.Trailer;
import app.example.db.movie.movieapp.utilities.JsonUtils;
import app.example.db.movie.movieapp.utilities.NetworkUtils;

/**
 * Created by Katy on 27.03.2018.
 */

public class FetchTrailerData extends AsyncTask<String, Void, Trailer[]> {


    private Trailer[] mTrailer = null;

    public FetchTrailerData() {

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
        String trailer = params[0];
        URL trailerUrl = NetworkUtils.buildUrl(trailer);

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

    }

}