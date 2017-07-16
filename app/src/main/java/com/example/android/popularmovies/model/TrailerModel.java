package com.example.android.popularmovies.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.popularmovies.data.DaoSession;
import com.example.android.popularmovies.data.Video;
import com.example.android.popularmovies.data.VideoDao;

import java.util.List;

/**
 * Created by ibnumuzzakkir on 7/16/17.
 * Android Engineer
 * SCO Project
 */

public class TrailerModel extends BaseModel {
    private VideoDao mVideoDao;

    public TrailerModel(DaoSession daoSession) {
        super(daoSession);
        mVideoDao = daoSession.getVideoDao();
    }

    @NonNull
    public void insertTrailer(Video video){
        mVideoDao.insertOrReplace(video);
    }

    @Nullable
    public List<Video> getAllTrailer(){
        return mVideoDao.loadAll();
    }
}
