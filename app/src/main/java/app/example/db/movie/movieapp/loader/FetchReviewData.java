package app.example.db.movie.movieapp.loader;

import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;

import app.example.db.movie.movieapp.adapter.ReviewAdapter;
import app.example.db.movie.movieapp.model.Review;
import app.example.db.movie.movieapp.utilities.JsonUtils;
import app.example.db.movie.movieapp.utilities.NetworkUtils;

/**
 * Created by Katy on 27.03.2018.
 */

public class FetchReviewData extends AsyncTask<String, Void, Review[]> {
    private static final String TAG = FetchReviewData.class.getSimpleName();

    private Review[] mReview = null;
    private ReviewAdapter mReviewAdapter;

    public FetchReviewData(ReviewAdapter adapter) {
        this.mReviewAdapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Review[] doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }
        String reviews = params[0];
        String id = params[1];
        Log.e(TAG, "reviews: " + reviews);
        Log.e(TAG, "id: " + id);

        URL trailerUrl = NetworkUtils.buildUrlReview(reviews, id);

        try {
            String jsonMovieResponse = NetworkUtils
                    .getResponseFromHttpUrl(trailerUrl);

            mReview = JsonUtils
                    .parseReviewJson(jsonMovieResponse);
            return mReview;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Review[] reviewData) {
        if (reviewData != null) {
            mReviewAdapter.setReviewData(reviewData);
        }

    }

}