package com.example.android.popularmovies.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.popularmovies.data.DaoSession;
import com.example.android.popularmovies.data.Review;
import com.example.android.popularmovies.data.ReviewDao;

import java.util.List;

/**
 * Created by ibnumuzzakkir on 7/16/17.
 * Android Engineer
 * SCO Project
 */

public class ReviewModel extends BaseModel {
    private ReviewDao mReviewDao;

    public ReviewModel(DaoSession daoSession) {
        super(daoSession);
        mReviewDao = daoSession.getReviewDao();
    }

    @NonNull
    public void insertReview(Review review){
        mReviewDao.insertOrReplace(review);
    }

    @Nullable
    public List<Review> getAllReview(){
        return mReviewDao.loadAll();
    }
}
