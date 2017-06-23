package com.example.android.popularmoviesstage1.ui.detail;

import com.example.android.popularmoviesstage1.di.scope.ActivityScope;

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
