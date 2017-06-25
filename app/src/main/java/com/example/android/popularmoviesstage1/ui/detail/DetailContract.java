package com.example.android.popularmoviesstage1.ui.detail;

import com.example.android.popularmoviesstage1.model.Movie;

import java.net.MalformedURLException;

/**
 * Created by ibnumuzzakkir on 16/06/2017.
 * Android Developer
 * Garena Indonesia
 */

public class DetailContract {
    public interface View{
        void showData(Movie movie) throws MalformedURLException;
        void showLoading();

    }

    public interface UserActionListener{

    }
}
