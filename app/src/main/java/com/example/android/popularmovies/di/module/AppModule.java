package com.example.android.popularmovies.di.module;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.popularmovies.MovieApp;
import com.example.android.popularmovies.data.DaoMaster;
import com.example.android.popularmovies.data.DaoSession;
import com.example.android.popularmovies.di.scope.ActivityScope;
import com.example.android.popularmovies.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ibnumuzzakkir on 15/06/2017.
 * Android Developer
 * Garena Indonesia
 */
@Module
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return application;
    }

    @Singleton
    @Provides
    DaoSession provideDaoSession() {
        String DbName = Constants.DB_NAME;
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(MovieApp.get(), DbName);
        Log.d("New DB Name: ", DbName);
        SQLiteDatabase db = devOpenHelper.getWritableDatabase();
        Log.d("DB PATH", db.getPath());
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }

}
