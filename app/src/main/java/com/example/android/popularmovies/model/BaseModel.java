package com.example.android.popularmovies.model;

import com.example.android.popularmovies.data.DaoSession;

/**
 * Created by ibnumuzzakkir on 7/16/17.
 * Android Engineer
 * SCO Project
 */

public class BaseModel {
    protected final DaoSession mDaoSession;

    public BaseModel(DaoSession daoSession) {
        mDaoSession = daoSession;
    }
}
