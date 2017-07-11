package com.example.android.popularmovies;

import android.app.Application;

import com.example.android.popularmovies.di.component.AppComponent;
import com.example.android.popularmovies.di.component.DaggerAppComponent;
import com.example.android.popularmovies.di.module.AppModule;
import com.example.android.popularmovies.di.module.NetworkModule;

/**
 * Created by ibnumuzzakkir on 15/06/2017.
 * Android Developer
 * Garena Indonesia
 */

public class MovieApp extends Application{
    private AppComponent appComponent = createAppComponent();

    private static MovieApp instance;

    public static MovieApp get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    protected AppComponent createAppComponent() {
        return appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return  appComponent;
    }

}
