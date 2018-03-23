package app.example.db.movie.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import app.example.db.movie.movieapp.model.Movie;

/**
 * Created by Katy on 22.02.2018.
 */

public class MovieDetailsActivity extends AppCompatActivity {
    private static final String TAG = MovieDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Movie movieObject = getIntent().getParcelableExtra("movieObject");
        if (movieObject != null) {

            String id = movieObject.getId();
            String title = movieObject.getTitle();
            String date = movieObject.getReleaseDate();
            String poster = movieObject.getPosterPath();
            String vote_average = movieObject.getVoteAverage();
            String overview = movieObject.getOverview();

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


}
