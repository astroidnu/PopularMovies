package com.example.android.popularmoviesstage1.repository;

import com.example.android.popularmoviesstage1.api.NetworkService;

/**
 * Created by ibnumuzzakkir on 16/06/2017.
 * Android Developer
 * Garena Indonesia
 */

public class BaseRepository {
    protected NetworkService networkService;

    public BaseRepository(NetworkService networkService) {
        this.networkService = networkService;
    }

}
