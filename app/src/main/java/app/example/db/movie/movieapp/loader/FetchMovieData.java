package app.example.db.movie.movieapp.loader;

import android.os.AsyncTask;

import java.net.URL;

import app.example.db.movie.movieapp.adapter.MovieAdapter;
import app.example.db.movie.movieapp.model.Movie;
import app.example.db.movie.movieapp.utilities.JsonUtils;
import app.example.db.movie.movieapp.utilities.NetworkUtils;

/**
 * Created by Katy on 25.03.2018.
 */

public class FetchMovieData extends AsyncTask<String, Void, Movie[]> {

    private MovieAdapter mMovieAdapter;
    private Movie[] mMovie = null;

    public FetchMovieData(MovieAdapter adapter) {
        this.mMovieAdapter = adapter;
    }

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
            mMovieAdapter.setMovieData(movieData);
        }
    }
}