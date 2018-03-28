package app.example.db.movie.movieapp.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;


import app.example.db.movie.movieapp.R;
import app.example.db.movie.movieapp.model.Review;


/**
 * Created by Katy on 27.03.2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private static final String TAG = ReviewAdapter.class.getSimpleName();
    private Review[] mReview;
    private TextView mAuthor;
    private TextView mContent;

    private final ReviewAdapterOnClickHandler mClickHandler;

    private int mExpandedPosition = -1;
    private int previousExpandedPosition = -1;

    public interface ReviewAdapterOnClickHandler {
        void onClick(Review reviewItem);
    }

    public ReviewAdapter(ReviewAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public ReviewViewHolder(View view) {
            super(view);
            mAuthor = view.findViewById(R.id.tv_review_author);
            mContent = view.findViewById(R.id.tv_review_content);
            //  mMoreText = view.findViewById(R.id.tv_read_more);
            view.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Review reviewItem = mReview[adapterPosition];
            mClickHandler.onClick(reviewItem);
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
    public void onBindViewHolder(ReviewViewHolder holder, final int position) {
        String author = mReview[position].getAuthor();
        String content = mReview[position].getContent();

        mAuthor.setText(author);
        mContent.setText(content);

/*
        // https://stackoverflow.com/questions/27203817/recyclerview-expand-collapse-items/27205234#27205234
        final boolean isExpanded = position == mExpandedPosition;
        mContent.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);

        if (isExpanded)
            previousExpandedPosition = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1 : position;
                notifyItemChanged(previousExpandedPosition);
                notifyItemChanged(position);
            }
        });
*/
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
