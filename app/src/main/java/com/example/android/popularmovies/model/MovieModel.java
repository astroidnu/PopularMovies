package com.example.android.popularmovies.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.popularmovies.data.DaoSession;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.MovieDao;

import org.greenrobot.greendao.rx.RxDao;
import org.greenrobot.greendao.rx.RxQuery;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ibnumuzzakkir on 7/16/17.
 * Android Engineer
 * SCO Project
 */

public class MovieModel extends BaseModel {
    private MovieDao mMovieDao;

    public MovieModel(DaoSession daoSession) {
        super(daoSession);
        mMovieDao = daoSession.getMovieDao();
    }

    @NonNull
    public void insertMovie(Movie movie){
        mMovieDao.insertOrReplace(movie);
    }

    @Nullable
    public Flowable<List<Movie>> getAllMovies(){
        return  Flowable.just(mMovieDao.loadAll())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Nullable
    public void deleteMovie(Movie movie){
         mMovieDao.delete(movie);
    }
}
