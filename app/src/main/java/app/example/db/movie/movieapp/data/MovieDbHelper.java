package app.example.db.movie.movieapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Katy on 23.03.2018.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " +
                MovieContract.MovieEntry.TABLE_NAME + " (" +
                MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieContract.MovieEntry.COLUMN_NAME_MOVIE_ID + " TEXT MOT NULL, " +
                MovieContract.MovieEntry.COLUMN_NAME_TITLE + " TEXT MOT NULL, " +
                MovieContract.MovieEntry.COLUMN_NAME_OVERVIEW + " TEXT MOT NULL, " +
                MovieContract.MovieEntry.COLUMN_NAME_POSTER + " TEXT MOT NULL, " +
                MovieContract.MovieEntry.COLUMN_NAME_RELEASE_DATE + " TEXT MOT NULL, " +
                MovieContract.MovieEntry.COLUMN_NAME_VOTE_AVERAGE + " TEXT MOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
