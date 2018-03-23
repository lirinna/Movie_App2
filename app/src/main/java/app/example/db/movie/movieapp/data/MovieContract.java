package app.example.db.movie.movieapp.data;

import android.provider.BaseColumns;

/**
 * Created by Katy on 23.03.2018.
 */

// https://developer.android.com/training/data-storage/sqlite.html

public class MovieContract {

    private MovieContract() {
    }

    public static class MovieEntry implements BaseColumns {

        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_NAME_MOVIE_ID = "movie_id"; // delete
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_OVERVIEW = "overview";
        public static final String COLUMN_NAME_POSTER = "poster";
        public static final String COLUMN_NAME_RELEASE_DATE = "release_date";
        public static final String COLUMN_NAME_VOTE_AVERAGE = "vote_average";


    }

}
