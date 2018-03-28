package app.example.db.movie.movieapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import app.example.db.movie.movieapp.R;
import app.example.db.movie.movieapp.model.Review;

public class ReviewDetails extends AppCompatActivity {

    private static final String TAG = ReviewDetails.class.getSimpleName();
    private String mAuthor;
    private String mContent;

    private TextView tv_Author;
    private TextView tv_Content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_Author = findViewById(R.id.tv_review_author_details);
        tv_Content = findViewById(R.id.tv_review_content_details);

        Review reviewObject = getIntent().getParcelableExtra("reviewObject");
        if (reviewObject != null) {
            mAuthor = reviewObject.getAuthor();
            mContent = reviewObject.getContent();

            tv_Author.setText(mAuthor);
            tv_Content.setText(mContent);

            Log.e(TAG, "mAuthor: " + mAuthor);
            Log.e(TAG, "mContent: " + mContent);


        }
    }
}
