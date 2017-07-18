package com.example.android.popularmovies.ui.home;

import com.example.android.popularmovies.api.NetworkService;
import com.example.android.popularmovies.data.DaoSession;
import com.example.android.popularmovies.di.scope.ActivityScope;
import com.example.android.popularmovies.model.MovieModel;
import com.example.android.popularmovies.repository.MainRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ibnumuzzakkir on 16/06/2017.
 * Android Developer
 * Garena Indonesia
 */
@Module
public class MainActivityModule {
    private MainActivity mainActivity;
    public MainActivityModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Provides
    @ActivityScope
    MainActivity provideMainActivity() {
        return mainActivity;
    }

    @Provides
    @ActivityScope
    MainRepository provideMainRepository(NetworkService networkService) {
        return new MainRepository(networkService);
    }

    @Provides
    @ActivityScope
    MovieModel provideMovieModel(DaoSession daoSession){
        return new MovieModel(daoSession);
    }

    @Provides
    @ActivityScope
    MainPresenter provideMainPresenter(MainRepository mainRepository, MovieModel movieModel) {
        return new MainPresenter(mainRepository, movieModel);
    }

}
