package com.example.android.popularmovies.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.popularmovies.data.DaoSession;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.MovieDao;

import java.util.List;

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
    public List<Movie> getAllMovies(){
        return mMovieDao.loadAll();
    }
}
