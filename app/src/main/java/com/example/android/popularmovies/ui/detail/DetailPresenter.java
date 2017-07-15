package com.example.android.popularmovies.ui.detail;

import android.util.Log;

import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.Review;
import com.example.android.popularmovies.model.Video;
import com.example.android.popularmovies.repository.MainRepository;
import com.example.android.popularmovies.utils.Constants;
import com.example.android.popularmovies.utils.CustomResourceSubscriber;
import com.example.android.popularmovies.vo.Resource;

import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Created by ibnumuzzakkir on 16/06/2017.
 * Android Developer
 * Garena Indonesia
 */

public class DetailPresenter implements DetailContract.UserActionListener {
    private DetailContract.View mView;
    private MainRepository mainRepository;

    public DetailPresenter(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public void setView(DetailContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getTrailerList(String id) {
        mainRepository.getVideos(id)
                .subscribe(new CustomResourceSubscriber<Resource<List<Video>>>() {
                    @Override
                    protected void onNextAndCompleted(@NonNull Resource<List<Video>> body) {
                        mView.setAllAdapter(body.data, Constants.ADAPTER_TYPE.TRAILER_ADAPTER);
                    }

                    @Override
                    protected void onError(String errorMessage) {

                    }
                });
    }

    @Override
    public void getReviewList(String id) {
        mainRepository.getReviews(id)
                .subscribe(new CustomResourceSubscriber<Resource<List<Review>>>() {
                    @Override
                    protected void onNextAndCompleted(@NonNull Resource<List<Review>> body) {
                        mView.setAllAdapter(body.data, Constants.ADAPTER_TYPE.REVIEW_ADAPTER);
                    }

                    @Override
                    protected void onError(String errorMessage) {

                    }
                });
    }
}
