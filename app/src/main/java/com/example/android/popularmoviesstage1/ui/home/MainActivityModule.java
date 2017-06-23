package com.example.android.popularmoviesstage1.ui.home;

import com.example.android.popularmoviesstage1.api.NetworkService;
import com.example.android.popularmoviesstage1.di.scope.ActivityScope;
import com.example.android.popularmoviesstage1.repository.MainRepository;

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
    MainPresenter provideMainPresenter(MainRepository mainRepository) {
        return new MainPresenter(mainRepository);
    }

}
