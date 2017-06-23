package com.example.android.popularmoviesstage1.ui.home;

import com.example.android.popularmoviesstage1.di.scope.ActivityScope;

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
