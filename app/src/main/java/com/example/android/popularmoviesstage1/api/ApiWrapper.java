package com.example.android.popularmoviesstage1.api;


import com.example.android.popularmoviesstage1.vo.Resource;

/**
 * Created by ibnumuzzakkir on 15/06/2017.
 * Android Developer
 * Garena Indonesia
 */


public class ApiWrapper {
    public static <T> Resource<T> fetchApi(ApiResponse<BaseApiResponse<T>> response) {
        if (response.isSuccessful()) {
            if(response.body != null){
                return Resource.success(response.body.results);
            }
        } else {
            return Resource.error(response.errorMessage, null);
        }
        return Resource.error("Somethings Wrong", null);
    }
}
