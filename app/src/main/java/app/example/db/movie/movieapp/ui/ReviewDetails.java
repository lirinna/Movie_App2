package app.example.db.movie.movieapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import app.example.db.movie.movieapp.R;
import app.example.db.movie.movieapp.model.Review;

public class ReviewDetails extends AppCompatActivity {

    private static final String TAG = ReviewDetails.class.getSimpleName();

    private TextView mAuthor;
    private TextView mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuthor = findViewById(R.id.tv_review_author_details);
        mContent = findViewById(R.id.tv_review_content_details);

        Review reviewObject = getIntent().getParcelableExtra("reviewObject");
        if (reviewObject != null) {
            String author = reviewObject.getAuthor();
            String content = reviewObject.getContent();

            mAuthor.setText(author);
            mContent.setText(content);

            Log.e(TAG, "mAuthor: " + mAuthor);
            Log.e(TAG, "mContent: " + mContent);


        }
    }
}
