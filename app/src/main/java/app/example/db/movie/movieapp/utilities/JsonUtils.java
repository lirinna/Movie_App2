package app.example.db.movie.movieapp.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.example.db.movie.movieapp.R;
import app.example.db.movie.movieapp.model.Movie;
import app.example.db.movie.movieapp.model.Review;
import app.example.db.movie.movieapp.model.Trailer;

/**
 * Created by Katy on 22.02.2018.
 */

public class JsonUtils {

    private static final String MOVIE_TITLE = "title";
    private static final String MOVIE_ID = "id";
    private static final String MOVIE_POSTER_PATH = "poster_path";
    private static final String MOVIE_OVERVIEW = "overview";
    private static final String MOVIE_VOTE_AVERAGE = "vote_average";
    private static final String MOVIE_RELEASE_DATE = "release_date";
    private static final String MOVIE_VOTES = "vote_count";
    private static final String MOVIE_RESULTS = "results";

    private static final String TRAILER_NAME = "name";
    private static final String TRAILER_KEY = "key";

    private static final String REVIEW_AUTHOR = "author";
    private static final String REVIEW_CONTENT = "content";


    public static Movie[] parseMovieJson(String json) throws JSONException {

        JSONObject movieData = new JSONObject(json);
        JSONArray JSONArray_Results = movieData.getJSONArray(MOVIE_RESULTS);
        Movie[] movieArray = new Movie[JSONArray_Results.length()];
        JSONObject jsonObject_result;

        for (int i = 0; i < JSONArray_Results.length(); i++) {
            jsonObject_result = JSONArray_Results.getJSONObject(i);
            Movie movie = new Movie();
            movie.setId(jsonObject_result.optString(MOVIE_ID));
            movie.setVotes(jsonObject_result.optString(MOVIE_VOTES));
            movie.setTitle(jsonObject_result.optString(MOVIE_TITLE));
            movie.setPosterPath(jsonObject_result.optString(MOVIE_POSTER_PATH));
            movie.setOverview(jsonObject_result.optString(MOVIE_OVERVIEW));
            movie.setVoteAverage(jsonObject_result.optString(MOVIE_VOTE_AVERAGE));
            movie.setReleaseDate(jsonObject_result.optString(MOVIE_RELEASE_DATE));
            movieArray[i] = movie;
        }
        return movieArray;
    }

    public static Trailer[] parseTrailerJson(String json) throws JSONException {

        JSONObject trailerData = new JSONObject(json);
        JSONArray JSONArray_Results = trailerData.getJSONArray(MOVIE_RESULTS);
        Trailer[] trailerArray = new Trailer[JSONArray_Results.length()];
        JSONObject jsonObject_result;

        for (int i = 0; i < JSONArray_Results.length(); i++) {
            jsonObject_result = JSONArray_Results.getJSONObject(i);
            Trailer trailer = new Trailer();
            trailer.setKey(jsonObject_result.optString(TRAILER_KEY));
            trailer.setName(jsonObject_result.optString(TRAILER_NAME));
            trailerArray[i] = trailer;
        }
        return trailerArray;
    }

    public static Review[] parseReviewJson(String json) throws JSONException {

        JSONObject reviewData = new JSONObject(json);
        JSONArray JSONArray_Results = reviewData.getJSONArray(MOVIE_RESULTS);
        Review[] reviewArray = new Review[JSONArray_Results.length()];
        JSONObject jsonObject_result;

        for (int i = 0; i < JSONArray_Results.length(); i++) {
            jsonObject_result = JSONArray_Results.getJSONObject(i);
            Review review = new Review();
            review.setAuthor(jsonObject_result.optString(REVIEW_AUTHOR));
            review.setContent(jsonObject_result.optString(REVIEW_CONTENT));
            reviewArray[i] = review;
        }
        return reviewArray;
    }

}
