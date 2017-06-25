package com.example.android.popularmovies.repository;

import com.example.android.popularmovies.api.ApiWrapper;
import com.example.android.popularmovies.api.NetworkService;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.utils.Constants;
import com.example.android.popularmovies.vo.Resource;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ibnumuzzakkir on 16/06/2017.
 * Android Developer
 * Garena Indonesia
 */

public class MainRepository extends BaseRepository {
    public MainRepository(NetworkService networkService) {
        super(networkService);
    }
    public Flowable<Resource<List<Movie>>> getMovies(String sort) {
        return networkService.getMoviesData(sort,Constants.TOKEN)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ApiWrapper::fetchApi);
    }
}
