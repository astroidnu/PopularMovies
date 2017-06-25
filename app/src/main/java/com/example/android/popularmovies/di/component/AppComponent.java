package com.example.android.popularmovies.di.component;

import com.example.android.popularmovies.di.module.AppModule;
import com.example.android.popularmovies.di.module.NetworkModule;
import com.example.android.popularmovies.ui.detail.DetailActivityModule;
import com.example.android.popularmovies.ui.detail.DetailComponent;
import com.example.android.popularmovies.ui.home.MainActivityModule;
import com.example.android.popularmovies.ui.home.MainComponent;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ibnumuzzakkir on 15/06/2017.
 * Android Developer
 * Garena Indonesia
 */
@Singleton
@Component(
        modules = {
                AppModule.class,
                NetworkModule.class
        }
)
public interface AppComponent {
    MainComponent plus(MainActivityModule mainActivityModule);
    DetailComponent plus(DetailActivityModule detailActivityModule);
}
