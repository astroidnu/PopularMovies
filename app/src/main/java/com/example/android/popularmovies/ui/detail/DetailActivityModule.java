package com.example.android.popularmovies.ui.detail;

import com.example.android.popularmovies.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ibnumuzzakkir on 16/06/2017.
 * Android Developer
 * Garena Indonesia
 */
@Module
public class DetailActivityModule {
    private DetailActivity detailActivity;
    public DetailActivityModule(DetailActivity detailActivity) {
        this.detailActivity = detailActivity;
    }

    @Provides
    @ActivityScope
    DetailActivity provideDetailActivity() {
        return detailActivity;
    }


    @Provides
    @ActivityScope
    DetailPresenter provideDetailPresenter() {
        return new DetailPresenter();
    }

}
