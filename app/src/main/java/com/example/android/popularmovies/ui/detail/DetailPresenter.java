package com.example.android.popularmovies.ui.detail;

import android.util.Log;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.Review;
import com.example.android.popularmovies.data.Video;
import com.example.android.popularmovies.model.MovieModel;
import com.example.android.popularmovies.model.ReviewModel;
import com.example.android.popularmovies.model.TrailerModel;
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
    private ReviewModel mReviewModel;
    private TrailerModel mTrailerModel;

    private List<Video> mListTrailer;
    private List<Review> mListReview;

    public DetailPresenter(MainRepository mainRepository, MovieModel movieModel,  ReviewModel reviewModel,TrailerModel trailerModel) {
        this.mainRepository = mainRepository;
        mMovieModel = movieModel;
        mReviewModel = reviewModel;
        mTrailerModel = trailerModel;
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
                mListReview = (List<Review>) data.get(1);
                mListTrailer = (List<Video>) data.get(0);
                setAdapter(data);
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

    @Override
    public <T> void setAdapter(HashMap<Integer, List<T>> data) {
        for(int i = 0; i<data.size();i++){
            mView.setAllAdapter(data.get(i), i);
        }
    }

    @Override
    public void saveFavorite(Movie movie) {
//        movie.setFavorite(true);
        if(mListReview != null && mListTrailer != null && movie != null){
            mMovieModel.insertMovie(movie);
            for(Review review:mListReview){
                mReviewModel.insertReview(review);
            }
            for(Video video:mListTrailer){
                mTrailerModel.insertTrailer(video);
            }
        }else{
            Log.d(getClass().getName(), "Please wait until complete");
        }
    }
}
