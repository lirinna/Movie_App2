package app.example.db.movie.movieapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import app.example.db.movie.movieapp.adapter.ReviewAdapter;
import app.example.db.movie.movieapp.adapter.TrailerAdapter;
import app.example.db.movie.movieapp.data.MovieContract;
import app.example.db.movie.movieapp.loader.FetchReviewData;
import app.example.db.movie.movieapp.loader.FetchTrailerData;
import app.example.db.movie.movieapp.model.Movie;
import app.example.db.movie.movieapp.model.Review;
import app.example.db.movie.movieapp.model.Trailer;

/**
 * Created by Katy on 22.02.2018.
 */

public class MovieDetailsActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler {
    private static final String TAG = MovieDetailsActivity.class.getSimpleName();

    private static final String TRAILER = "videos";
    private static final String REVIEW = "reviews";

    String id;
    String votes;
    String title;
    String date;
    String poster;
    String vote_average;
    String overview;
    LikeButton likeButton;
    private boolean setFavoriteFlag = false;

    private TrailerAdapter mTrailerAdapter;
    private ReviewAdapter mReviewAdapter;

    private RecyclerView mRecyclerViewTrailer;
    private RecyclerView mRecyclerViewReview;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        likeButton = findViewById(R.id.star_button);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        mTrailerAdapter = new TrailerAdapter(this);
        mReviewAdapter = new ReviewAdapter();


        Movie movieObject = getIntent().getParcelableExtra("movieObject");
        if (movieObject != null) {

            id = movieObject.getId();
            votes = movieObject.getVotes();
            title = movieObject.getTitle();
            date = movieObject.getReleaseDate();
            poster = movieObject.getPosterPath();
            vote_average = movieObject.getVoteAverage();
            overview = movieObject.getOverview();



            Log.e(TAG, "id "+ id);
            if (votes != null)
            Log.e(TAG, "votes: "+ votes);
            Log.e(TAG,"title: "+ title);
            Log.e(TAG,"date: "+ date);
            Log.e(TAG, "poster: "+poster);
            Log.e(TAG, "vote_average: "+vote_average);
            Log.e(TAG, "overview: "+overview);


            TextView textView_titleN = findViewById(R.id.details_titleN);
            textView_titleN.setText(title);

            ImageView imageView_posterN = findViewById(R.id.details_posterN);
            Picasso.with(this)
                    .load("http://image.tmdb.org/t/p/w342".concat(poster))
                    .into(imageView_posterN);

            imageView_posterN.setAlpha(0.5f);


            // Date
            String CurrentString = date;
            String[] separated = CurrentString.split("-");
            String first = separated[0];

            TextView textView_dateN = findViewById(R.id.details_dateN);
            textView_dateN.setText(first);


            TextView textView_vote_averageN = findViewById(R.id.details_vote_averageN);
            textView_vote_averageN.setText(vote_average);


            RatingBar mRatingBar = findViewById(R.id.rating);
            RatingBar mRatingBar2 = findViewById(R.id.rating2);

            float number = Float.parseFloat(vote_average);
            Log.e(TAG, "number Rating:" + String.valueOf(number));

            float newValue = calculateRating(number);
            Log.e(TAG, "newValue Rating:" + newValue);

            mRatingBar.setRating(newValue);

            mRatingBar2.setRating(5);


            TextView textView_votes = findViewById(R.id.votes);
            textView_votes.setText(votes);

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

                Log.e(TAG, "ID IN LIKEBUTTON: " + id);

                contentValues.put(MovieContract.MovieEntry.COLUMN_NAME_MOVIE_ID, id);
                contentValues.put(MovieContract.MovieEntry.COLUMN_NAME_VOTES, votes);
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

                assert result != null;
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


        mRecyclerViewTrailer = findViewById(R.id.recyclerview_trailer);
        mRecyclerViewReview = findViewById(R.id.recyclerview_review);

        GridLayoutManager trailerGridManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        GridLayoutManager reviewGridManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        mRecyclerViewTrailer.setLayoutManager(trailerGridManager);
        mRecyclerViewReview.setLayoutManager(reviewGridManager);

       // mRecyclerViewTrailer.setHasFixedSize(true);


        mRecyclerViewTrailer.setAdapter(mTrailerAdapter);
        mRecyclerViewReview.setAdapter(mReviewAdapter);

        getTrailers();
        getReviews();


    }


    private float calculateRating(float number) {
        float calculated;
        float first = (number / 10);
        calculated = (first * 5);
        return calculated;
    }


    public void getTrailers() {

        Log.e(TAG, "id in TRAILERS: " + id);
        Object trailers = new FetchTrailerData(mTrailerAdapter).execute(TRAILER, id);

        if (trailers != null) {
            Log.e(TAG, "trailers: " + trailers.toString());
        }
    }

    public void getReviews() {
        Object reviews = new FetchReviewData(mReviewAdapter).execute(REVIEW, id);

        if (reviews != null) {
            Log.e(TAG, "reviews: " + reviews.toString());
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

        Log.e(TAG, "onClick für Trailer");
    }

    private String makeYoutubeUrl(String trailerKey) {
        String newU = "https://www.youtube.com/watch?v=" + trailerKey;
        Log.e(TAG, newU);
        return newU;
    }





}
