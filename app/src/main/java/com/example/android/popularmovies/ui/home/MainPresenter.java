package com.example.android.popularmovies.ui.home;

import android.content.Intent;
import android.util.Log;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.model.MovieModel;
import com.example.android.popularmovies.repository.MainRepository;
import com.example.android.popularmovies.utils.CustomResourceSubscriber;
import com.example.android.popularmovies.vo.Resource;

import org.reactivestreams.Subscriber;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by ibnumuzzakkir on 16/06/2017.
 * Android Developer
 * Garena Indonesia
 */

public class MainPresenter implements MainContract.UserActionListener {
    private MainContract.View mView;

    private MainRepository mainRepository;
    private MovieModel mMovieModel;

    public MainPresenter(MainRepository mainRepository, MovieModel movieModel) {
        this.mainRepository = mainRepository;
        mMovieModel = movieModel;
    }


    public void setView(MainContract.View mView) {
        this.mView = mView;
    }


    private void getMoviesData(String sort_type, int typeAdapter) {
        mView.showLoading();
        mainRepository.getMovies(sort_type)
                .subscribe(new CustomResourceSubscriber<Resource<List<Movie>>>() {
                    @Override
                    protected void onNextAndCompleted(@NonNull Resource<List<Movie>> body) {
                        mView.setAdapter(body.data, typeAdapter);
                        mView.hideLoading();
                    }

                    @Override
                    protected void onError(String errorMessage) {
                        Log.e(getClass().getName(), errorMessage);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getMovies(int sortId) {
        switch (sortId) {
            case 0:
                getMoviesData("popular", sortId);
                break;
            case 1:
                getMoviesData("top_rated", sortId);
                break;
            case 2:
                mMovieModel.getAllMovies().subscribe(new ResourceSubscriber<List<Movie>>() {
                    @Override
                    public void onNext(List<Movie> movies) {
                        mView.setAdapter(movies, sortId);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
                break;
            default:
                getMoviesData("popular", sortId);
                break;
        }
    }
}
