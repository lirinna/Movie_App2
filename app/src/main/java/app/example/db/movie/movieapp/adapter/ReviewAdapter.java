package app.example.db.movie.movieapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import app.example.db.movie.movieapp.R;
import app.example.db.movie.movieapp.model.Review;
import app.example.db.movie.movieapp.model.Trailer;

/**
 * Created by Katy on 27.03.2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private static final String TAG = ReviewAdapter.class.getSimpleName();
    private Review[] mReview;
    private TextView mAuthor;
    private TextView mContent;


    public ReviewAdapter() {

    }


    public class ReviewViewHolder extends RecyclerView.ViewHolder {


        public ReviewViewHolder(View view) {
            super(view);
            mAuthor = view.findViewById(R.id.tv_review_author);
            mContent = view.findViewById(R.id.tv_review_content);

        }


    }

    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.review_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new ReviewAdapter.ReviewViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        String author = mReview[position].getAuthor();
        String content = mReview[position].getContent();

        mAuthor.setText(author);
        mContent.setText(content);

        Log.e(TAG, "author: " + author);
        Log.e(TAG, "content: " + content);
    }


    @Override
    public int getItemCount() {
        if (null == mReview) return 0;
        return mReview.length;
    }

    public void setReviewData(Review[] reviewData) {
        mReview = reviewData;
        notifyDataSetChanged();
    }
}