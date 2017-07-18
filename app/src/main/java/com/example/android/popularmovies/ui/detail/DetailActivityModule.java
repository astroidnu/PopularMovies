package com.example.android.popularmovies.ui.detail;

import com.example.android.popularmovies.api.NetworkService;
import com.example.android.popularmovies.data.DaoSession;
import com.example.android.popularmovies.di.scope.ActivityScope;
import com.example.android.popularmovies.model.FavoriteModel;
import com.example.android.popularmovies.model.MovieModel;
import com.example.android.popularmovies.model.ReviewModel;
import com.example.android.popularmovies.model.TrailerModel;
import com.example.android.popularmovies.repository.MainRepository;

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
    MainRepository provideMainRepository(NetworkService networkService) {
        return new MainRepository(networkService);
    }

    @Provides
    @ActivityScope
    FavoriteModel provideReviewModel(DaoSession daoSession){
        return new FavoriteModel(daoSession);
    }

    @Provides
    @ActivityScope
    MovieModel provideMovieModel(DaoSession daoSession){
        return new MovieModel(daoSession);
    }

    @Provides
    @ActivityScope
    DetailPresenter provideDetailPresenter(MainRepository mainRepository, FavoriteModel favoriteModel, MovieModel movieModel) {
        return new DetailPresenter(mainRepository, favoriteModel, movieModel);
    }

}
