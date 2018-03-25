package app.example.db.movie.movieapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Katy on 23.03.2018.
 */


public class MovieContract {

    public static final String AUTHORITY = "app.example.db.movie.movieapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_MOVIES = "movies";


    private MovieContract() {
    }

    public static class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_NAME_MOVIE_ID = "movie_id"; // delete
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_OVERVIEW = "overview";
        public static final String COLUMN_NAME_POSTER = "poster";
        public static final String COLUMN_NAME_RELEASE_DATE = "release_date";
        public static final String COLUMN_NAME_VOTE_AVERAGE = "vote_average";


    }

}
