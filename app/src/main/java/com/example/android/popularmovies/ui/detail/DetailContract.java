package com.example.android.popularmovies.ui.detail;

import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.Video;

import java.net.MalformedURLException;
import java.util.List;

/**
 * Created by ibnumuzzakkir on 16/06/2017.
 * Android Developer
 * Garena Indonesia
 */

public class DetailContract {
    public interface View{
        void showData(Movie movie) throws MalformedURLException;
        void showLoading();
        void setAdapter(List<Video> movieList);

    }

    public interface UserActionListener{

    }
}
