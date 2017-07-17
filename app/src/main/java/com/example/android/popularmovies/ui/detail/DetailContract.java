package com.example.android.popularmovies.ui.detail;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.Review;
import com.example.android.popularmovies.data.Video;

import java.net.MalformedURLException;
import java.util.HashMap;
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
        <T> void setAllAdapter(List<T> data, int adapterId);
        void isFavorite(boolean stat);
    }

    public interface UserActionListener{
        <T> void getReviewAndTrailerList(String id);
        <T>void setAdapter(HashMap<Integer, List<T>> data);
        void saveFavorite(Movie movie);
    }
}
