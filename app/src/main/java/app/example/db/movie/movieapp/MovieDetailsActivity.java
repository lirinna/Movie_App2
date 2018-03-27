package app.example.db.movie.movieapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import app.example.db.movie.movieapp.adapter.TrailerAdapter;
import app.example.db.movie.movieapp.data.MovieContract;
import app.example.db.movie.movieapp.loader.FetchTrailerData;
import app.example.db.movie.movieapp.model.Movie;
import app.example.db.movie.movieapp.model.Trailer;

/**
 * Created by Katy on 22.02.2018.
 */

public class MovieDetailsActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler {
    private static final String TAG = MovieDetailsActivity.class.getSimpleName();

    private static final String TRAILER = "videos";

    String id;
    String title;
    String date;
    String poster;
    String vote_average;
    String overview;
    LikeButton likeButton;
    private boolean setFavoriteFlag = false;

    private TrailerAdapter mTrailerAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        likeButton = findViewById(R.id.star_button);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTrailerAdapter = new TrailerAdapter(this);

        Movie movieObject = getIntent().getParcelableExtra("movieObject");
        if (movieObject != null) {

            id = movieObject.getId();
            title = movieObject.getTitle();
            date = movieObject.getReleaseDate();
            poster = movieObject.getPosterPath();
            vote_average = movieObject.getVoteAverage();
            overview = movieObject.getOverview();

            Log.d(TAG, id);
            Log.d(TAG, title);
            Log.d(TAG, date);
            Log.d(TAG, poster);
            Log.d(TAG, vote_average);
            Log.d(TAG, overview);

            TextView textView_title = findViewById(R.id.details_title);
            textView_title.setText(title);

            TextView textView_date = findViewById(R.id.details_date);
            textView_date.setText(date);

            ImageView imageView_poster = findViewById(R.id.details_poster);
            Picasso.with(this)
                    .load("http://image.tmdb.org/t/p/w300".concat(poster))
                    .into(imageView_poster);

            TextView textView_vote_average = findViewById(R.id.details_vote_average);
            textView_vote_average.setText(vote_average);

            TextView textView_overview = findViewById(R.id.details_overview);
            textView_overview.setText(overview);
        }


        checkIfFavorite();

        if (setFavoriteFlag) {
            likeButton.setLiked(true);
        } else {
            likeButton.setLiked(false);
        }


        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

                ContentValues contentValues = new ContentValues();

                contentValues.put(MovieContract.MovieEntry.COLUMN_NAME_MOVIE_ID, id);
                contentValues.put(MovieContract.MovieEntry.COLUMN_NAME_TITLE, title);
                contentValues.put(MovieContract.MovieEntry.COLUMN_NAME_OVERVIEW, overview);
                contentValues.put(MovieContract.MovieEntry.COLUMN_NAME_POSTER, poster);
                contentValues.put(MovieContract.MovieEntry.COLUMN_NAME_RELEASE_DATE, date);
                contentValues.put(MovieContract.MovieEntry.COLUMN_NAME_VOTE_AVERAGE, vote_average);

                Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);

                if (uri != null) {
                    Log.e(TAG, "uri: " + uri.toString());
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {

                Cursor result = getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, MovieContract.MovieEntry.COLUMN_NAME_MOVIE_ID + " = ?", new String[]{id}, null);
                int idIndex = result.getColumnIndex(MovieContract.MovieEntry._ID);

                int MovieIndex = result.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME_MOVIE_ID);

                result.moveToFirst();
                String idToDelete = result.getString(idIndex);
                String MovieIndexToDelete = result.getString(MovieIndex);

                if (result != null) {
                    Log.e(TAG, "delete uri: " + result);
                    Log.e(TAG, "delete idToDelete: " + idToDelete);
                    Log.e(TAG, "MovieIndex: " + MovieIndex);
                    Log.e(TAG, "MovieIndexToDelete: " + MovieIndexToDelete);
                    Log.e(TAG, "id: " + id);
                }

                Uri uri = MovieContract.MovieEntry.CONTENT_URI;
                uri = uri.buildUpon().appendPath(idToDelete).build();

                //  Delete a single row of data using a ContentResolver
                getContentResolver().delete(uri, String.valueOf(idIndex), new String[]{idToDelete});


                if (uri != null) {
                    Log.e(TAG, "delete urfi: " + uri.toString());
                }


            }
        });


        mRecyclerView = findViewById(R.id.recyclerview_trailer);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(manager);


        mRecyclerView.setHasFixedSize(true);

        mTrailerAdapter = new TrailerAdapter(this);
        mRecyclerView.setAdapter(mTrailerAdapter);

        getTrailers();


    }

    public void getTrailers() {


        Object trailers = new FetchTrailerData(mTrailerAdapter).execute(TRAILER, id);


        if (trailers != null) {
            Log.e(TAG, "trailers: " + trailers.toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void checkIfFavorite() {
        Cursor mCursor = getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                MovieContract.MovieEntry.COLUMN_NAME_MOVIE_ID);
        if (mCursor != null) {
            while (mCursor.moveToNext()) {
                String movieId = mCursor.getString(1);
                if (movieId.equals(id)) {
                    setFavoriteFlag = true;
                }
                Log.e(TAG, "movieId: " + movieId);
            }
        }
        assert mCursor != null;
        mCursor.close();

    }

    @Override
    public void onClick(Trailer trailerItem) {
        String key = trailerItem.getKey();
        String trailerUrl = makeYoutubeUrl(key);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
        startActivity(intent);
    }

    private String makeYoutubeUrl(String trailerKey) {
        String newU = "https://www.youtube.com/watch?v=" + trailerKey;
        Log.e(TAG, newU);
        return newU;
    }
}
