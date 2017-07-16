package com.example.android.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.Review;

import java.util.List;

/**
 * Created by ibnumuzzakkir on 7/15/17.
 * Android Engineer
 * SCO Project
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{
    private List<Review> mReviews;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public <T> ReviewAdapter(List<T> reviews, Context context){
        mReviews = (List<Review>)reviews;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_review, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Review review = mReviews.get(position);
        holder.mReviewTitle.setText(review.getAuthor());
        holder.mReviewContent.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mReviewTitle;
        private TextView mReviewContent;

        public ViewHolder(View itemView) {
            super(itemView);
            mReviewTitle = (TextView) itemView.findViewById(R.id.review_title);
            mReviewContent = (TextView) itemView.findViewById(R.id.review_content);
        }
    }
}
