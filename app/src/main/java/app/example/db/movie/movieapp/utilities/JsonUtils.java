package app.example.db.movie.movieapp.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.example.db.movie.movieapp.model.Movie;
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
    private static final String MOVIE_RESULTS = "results";

    private static final String TRAILER_NAME = "name";
    private static final String TRAILER_KEY = "key";


    public static Movie[] parseMovieJson(String json) throws JSONException {

        JSONObject movieData = new JSONObject(json);
        JSONArray JSONArray_Results = movieData.getJSONArray(MOVIE_RESULTS);
        Movie[] movieArray = new Movie[JSONArray_Results.length()];
        JSONObject jsonObject_result;

        for (int i = 0; i < JSONArray_Results.length(); i++) {
            jsonObject_result = JSONArray_Results.getJSONObject(i);
            Movie movie = new Movie();
            movie.setId(jsonObject_result.optString(MOVIE_ID));
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


}
