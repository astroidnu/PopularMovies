package com.example.android.popularmovies.ui.detail;

import android.util.Log;

import com.example.android.popularmovies.data.Review;
import com.example.android.popularmovies.data.Video;
import com.example.android.popularmovies.model.MovieModel;
import com.example.android.popularmovies.repository.MainRepository;
import com.example.android.popularmovies.utils.Constants;
import com.example.android.popularmovies.vo.Resource;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by ibnumuzzakkir on 16/06/2017.
 * Android Developer
 * Garena Indonesia
 */

public class DetailPresenter implements DetailContract.UserActionListener {
    private DetailContract.View mView;
    private MainRepository mainRepository;
    private MovieModel mMovieModel;

    public DetailPresenter(MainRepository mainRepository, MovieModel movieModel) {
        this.mainRepository = mainRepository;
        mMovieModel = movieModel;
    }

    public void setView(DetailContract.View mView) {
        this.mView = mView;
    }

    @Override
    public <T> void getReviewAndTrailerList(String id) {
        Flowable.zip(mainRepository.getVideos(id), mainRepository.getReviews(id), new BiFunction<Resource<List<Video>>, Resource<List<Review>>, HashMap<Integer, List<T>>>() {
            @Override
            public HashMap<Integer, List<T>> apply(@NonNull Resource<List<Video>> trailerList, @NonNull Resource<List<Review>> reviewList) throws Exception {
                HashMap<Integer, List<T>> datas = new HashMap<>();
                datas.put(Constants.ADAPTER_TYPE.TRAILER_ADAPTER, (List<T>) trailerList.data);
                datas.put(Constants.ADAPTER_TYPE.REVIEW_ADAPTER, (List<T>) reviewList.data);
                return datas;
            }
        }).subscribe(new ResourceSubscriber<HashMap<Integer, List<T>>>() {
            @Override
            public void onNext(HashMap<Integer, List<T>> data) {
                mView.setAllAdapter(data.get(Constants.ADAPTER_TYPE.TRAILER_ADAPTER), Constants.ADAPTER_TYPE.TRAILER_ADAPTER);
                mView.setAllAdapter(data.get(Constants.ADAPTER_TYPE.REVIEW_ADAPTER), Constants.ADAPTER_TYPE.REVIEW_ADAPTER);
            }

            @Override
            public void onError(Throwable t) {
                Log.e(getClass().getName(), t.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
