package com.example.android.popularmovies.ui.home;

import com.example.android.popularmovies.data.Movie;

import java.util.List;

/**
 * Created by ibnumuzzakkir on 16/06/2017.
 * Android Developer
 * Garena Indonesia
 */

public class MainContract {
    public interface View{
        void setAdapter(List<Movie> movieList, int typeAdapter);
        void showLoading();
        void hideLoading();

    }
    public interface UserActionListener{
        void getMovies(int sortId);
    }
}

