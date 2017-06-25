package com.example.android.popularmovies.ui.detail;

import com.example.android.popularmovies.di.scope.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by ibnumuzzakkir on 16/06/2017.
 * Android Developer
 * Garena Indonesia
 */
@ActivityScope
@Subcomponent(
        modules = DetailActivityModule.class
)
public interface DetailComponent {
    DetailActivity inject(DetailActivity detailActivity);
}
