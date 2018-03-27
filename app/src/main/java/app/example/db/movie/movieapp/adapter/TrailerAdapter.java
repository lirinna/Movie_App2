package app.example.db.movie.movieapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import app.example.db.movie.movieapp.R;
import app.example.db.movie.movieapp.model.Trailer;

/**
 * Created by Katy on 27.03.2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private static final String TAG = TrailerAdapter.class.getSimpleName();
    private Trailer[] mTrailer;

    /**
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    private final TrailerAdapterOnClickHandler mClickHandler;


    /**
     * The interface that receives onClick messages.
     */
    public interface TrailerAdapterOnClickHandler {
        void onClick(Trailer trailerItem);
    }

    public TrailerAdapter(TrailerAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }



    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mPosterImageView;

        public TrailerViewHolder(View view) {
            super(view);
            mPosterImageView =  view.findViewById(R.id.iv_movie_trailer);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Trailer TrailerItem = mTrailer[adapterPosition];
            mClickHandler.onClick(TrailerItem);
        }
    }


    //TODO MAGE LAYOUT CHANGE!!!

    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new TrailerAdapter.TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.TrailerViewHolder holder, int position) {
        String name= mTrailer[position].getName();
        final String key = mTrailer[position].getKey();


        String url = "https://img.youtube.com/vi/"+key+"/hqdefault.jpg";

        Picasso.with(holder.mPosterImageView.getContext())
                .load(url)
                .into(holder.mPosterImageView);

        Log.e(TAG, name);
        Log.e(TAG, key);
    }


    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our movie array
     */
    @Override
    public int getItemCount() {
        if (null == mTrailer) return 0;
        return mTrailer.length;
    }

    public void setTrailerData(Trailer[] trailerData) {
        mTrailer = trailerData;
        notifyDataSetChanged();
    }
}
