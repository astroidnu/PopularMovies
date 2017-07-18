package com.example.android.popularmovies.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.popularmovies.data.DaoSession;
import com.example.android.popularmovies.data.Favorite;
import com.example.android.popularmovies.data.FavoriteDao;

import java.util.List;

/**
 * Created by ibnumuzzakkir on 7/18/17.
 * Android Engineer
 * SCO Project
 */

public class FavoriteModel extends BaseModel {

    private FavoriteDao mFavoriteDao;

    public FavoriteModel(DaoSession daoSession) {
        super(daoSession);
        mFavoriteDao =  daoSession.getFavoriteDao();
    }

    @NonNull
    public void insertFavorite(Favorite favorite){
        mFavoriteDao.insertOrReplace(favorite);
    }

    @Nullable
    public void deleteFavorite(Favorite favorite){
        mFavoriteDao.delete(favorite);
    }

    @NonNull
    public List<Favorite> selectAllFavorite(String id){
       return mFavoriteDao.queryBuilder().where(FavoriteDao.Properties.Id.eq(id)).list();
    }

}
