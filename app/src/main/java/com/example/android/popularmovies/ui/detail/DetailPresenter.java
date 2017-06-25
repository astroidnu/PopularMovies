package com.example.android.popularmovies.ui.detail;

/**
 * Created by ibnumuzzakkir on 16/06/2017.
 * Android Developer
 * Garena Indonesia
 */

public class DetailPresenter implements DetailContract.UserActionListener {
    private DetailContract.View mView;

    public DetailPresenter() {}

    public void setView(DetailContract.View mView) {
        this.mView = mView;
    }
}
