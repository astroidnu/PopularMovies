package com.example.android.popularmovies.ui.detail;

import android.util.Log;

import com.example.android.popularmovies.adapter.MovieAdapter;
import com.example.android.popularmovies.data.Favorite;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.Review;
import com.example.android.popularmovies.data.Video;
import com.example.android.popularmovies.model.FavoriteModel;
import com.example.android.popularmovies.model.MovieModel;
import com.example.android.popularmovies.model.ReviewModel;
import com.example.android.popularmovies.model.TrailerModel;
import com.example.android.popularmovies.repository.MainRepository;
import com.example.android.popularmovies.utils.Constants;
import com.example.android.popularmovies.vo.Resource;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
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
    private FavoriteModel mFavoriteModel;
    private MovieModel mMovieModel;

    private List<Video> mListTrailer;
    private List<Review> mListReview;

    public DetailPresenter(MainRepository mainRepository, FavoriteModel favoriteModel, MovieModel movieModel) {
        this.mainRepository = mainRepository;
        mFavoriteModel = favoriteModel;
        mMovieModel = movieModel;
    }

    public void setView(DetailContract.View mView) {
        this.mView = mView;
    }

    @Override
    public <T> void getReviewAndTrailerList(String id) {
        mView.showLoading(true);
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
        mView.showLoading(false);
    }

    @Override
    public void saveFavorite(Movie movie) {
        Favorite favoriteList = selectFavorite(Long.valueOf(movie.getId()));
        if(favoriteList == null){
            Favorite favorite = new Favorite();
            favorite.setId(movie.getId());
            favorite.setTitle(movie.getTitle());
            mFavoriteModel.insertFavorite(favorite);
            mMovieModel.insertMovie(movie);
            mView.isFavorite(true);
        }else{
            mFavoriteModel.deleteFavorite(favoriteList);
            mMovieModel.deleteMovie(movie);
            Flowable.just("Hello");
            mView.isFavorite(false);
        }
    }

    @Override
    public void checkFavorite(long id) {
        Favorite favoriteList = selectFavorite(id);
        if(favoriteList != null){
            mView.isFavorite(true);
        }else{
            mView.isFavorite(false);
        }
    }

    @Override
    public void urlShareContent() {
        if(mListTrailer.size() >0){
            mView.shareContent(mListTrailer.get(0).getKey());
        }
    }


    private Favorite selectFavorite(long id) {
        return  mFavoriteModel.selectFavorite(id);
    }
}
