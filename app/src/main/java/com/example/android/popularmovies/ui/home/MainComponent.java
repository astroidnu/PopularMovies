package com.example.android.popularmovies.ui.home;

import com.example.android.popularmovies.di.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by ibnumuzzakkir on 16/06/2017.
 * Android Developer
 * Garena Indonesia
 */
@ActivityScope
@Subcomponent(
        modules = MainActivityModule.class
)
public interface MainComponent {
    MainActivity inject(MainActivity mainActivity);
}
