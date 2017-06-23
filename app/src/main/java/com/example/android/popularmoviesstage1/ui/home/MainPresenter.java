package com.example.android.popularmoviesstage1.ui.home;

import android.util.Log;

import com.example.android.popularmoviesstage1.api.MainResponse;
import com.example.android.popularmoviesstage1.model.Movie;
import com.example.android.popularmoviesstage1.repository.MainRepository;
import com.example.android.popularmoviesstage1.utils.CustomResourceSubscriber;
import com.example.android.popularmoviesstage1.vo.Resource;

import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Created by ibnumuzzakkir on 16/06/2017.
 * Android Developer
 * Garena Indonesia
 */

public class MainPresenter implements MainContract.UserActionListener {
    private MainContract.View mView;

    private MainRepository mainRepository;

    public MainPresenter(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }


    public void setView(MainContract.View mView) {
        this.mView = mView;
    }


    private void getMoviesData(String sort_type) {
        mView.showLoading();
        mainRepository.getMovies(sort_type)
                .subscribe(new CustomResourceSubscriber<Resource<List<Movie>>>() {
                    @Override
                    protected void onNextAndCompleted(@NonNull Resource<List<Movie>> body) {
                        mView.setAdapter(body.data);
                        mView.hideLoading();
//                        Log.d(getClass().getName(), String.valueOf(body.data));
//                       for(Movie movie : body.data.movies){
//                           Log.d(getClass().getName(), String.valueOf(movie.id));
//                       }
                    }

                    @Override
                    protected void onError(String errorMessage) {
                        Log.e(getClass().getName(),errorMessage);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getMovies(int sortId) {
        switch (sortId){
            case 0:
                getMoviesData("popular");
                break;
            case 1:
                getMoviesData("top_rated");
                break;
            default:
                getMoviesData("popular");
                break;
        }
    }
}
