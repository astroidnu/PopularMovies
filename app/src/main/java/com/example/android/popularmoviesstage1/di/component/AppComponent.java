package com.example.android.popularmoviesstage1.di.component;

import com.example.android.popularmoviesstage1.di.module.AppModule;
import com.example.android.popularmoviesstage1.di.module.NetworkModule;
import com.example.android.popularmoviesstage1.ui.detail.DetailActivityModule;
import com.example.android.popularmoviesstage1.ui.detail.DetailComponent;
import com.example.android.popularmoviesstage1.ui.home.MainActivityModule;
import com.example.android.popularmoviesstage1.ui.home.MainComponent;

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
