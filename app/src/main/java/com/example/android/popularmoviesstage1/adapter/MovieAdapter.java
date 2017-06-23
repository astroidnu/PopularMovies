package com.example.android.popularmoviesstage1.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.popularmoviesstage1.R;
import com.example.android.popularmoviesstage1.model.Movie;
import com.example.android.popularmoviesstage1.ui.detail.DetailActivity;
import com.example.android.popularmoviesstage1.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ibnumuzzakkir on 16/06/2017.
 * Android Developer
 * Garena Indonesia
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<Movie> mMovies;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public MovieAdapter(List<Movie> movies, Context context){
        mMovies = movies;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        Picasso.with(mContext)
                .load(Constants.BASE_URL_IMAGE+movie.getPosterPath())
                .into(holder.mMovieCover);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra("data", movie);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mMovieCover;
        public ViewHolder(View itemView) {
            super(itemView);
            mMovieCover = (ImageView) itemView.findViewById(R.id.item_movie_cover);
        }
    }
}
