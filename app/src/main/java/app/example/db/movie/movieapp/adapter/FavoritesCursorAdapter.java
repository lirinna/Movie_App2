package app.example.db.movie.movieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import app.example.db.movie.movieapp.MovieDetailsActivity;
import app.example.db.movie.movieapp.R;
import app.example.db.movie.movieapp.data.MovieContract;
import app.example.db.movie.movieapp.model.Movie;

/**
 * Created by Katy on 25.03.2018.
 * Used Sources: https://github.com/udacity/ud851-Exercises/blob/student/Lesson09-ToDo-List/T09.05-Solution-QueryAllTasks/app/src/main/java/com/example/android/todolist/CustomCursorAdapter.java
 */

public class FavoritesCursorAdapter extends RecyclerView.Adapter<FavoritesCursorAdapter.MovieViewHolder> {
    private static final String TAG = FavoritesCursorAdapter.class.getSimpleName();

    private Cursor mCursor;
    private Context mContext;

    public FavoritesCursorAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        int posterIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME_POSTER);
        String poster = mCursor.getString(posterIndex);

        Picasso.with(holder.mPosterImageView.getContext())
                .load(makeImageUrl(poster))
                .into(holder.mPosterImageView);

    }

    private String makeImageUrl(String imagePath) {
        String newU = "http://image.tmdb.org/t/p/w185" + imagePath;
        Log.e(TAG, newU);
        return newU;
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mPosterImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mPosterImageView = itemView.findViewById(R.id.iv_movie_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);

            int idIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME_MOVIE_ID);
            int titleIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME_TITLE);
            int overviewIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME_OVERVIEW);
            int posterIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME_POSTER);
            int releaseDateIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME_RELEASE_DATE);
            int voteAverageIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME_VOTE_AVERAGE);

            String id = mCursor.getString(idIndex);
            String title = mCursor.getString(titleIndex);
            String overview = mCursor.getString(overviewIndex);
            String poster = mCursor.getString(posterIndex);
            String releaseDate = mCursor.getString(releaseDateIndex);
            String voteAverage = mCursor.getString(voteAverageIndex);

            Movie movieItem = new Movie(id, title, overview, poster, releaseDate, voteAverage);

            Intent intent = new Intent(view.getContext(), MovieDetailsActivity.class);
            intent.putExtra("movieObject", movieItem);
            view.getContext().startActivity(intent);

        }
    }
}
