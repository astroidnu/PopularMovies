package com.example.android.popularmovies.api;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.Review;
import com.example.android.popularmovies.data.Video;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ibnumuzzakkir on 15/06/2017.
 * Android Developer
 * Garena Indonesia
 */

public interface NetworkService {
    @GET("movie/{sort}")
    Flowable<ApiResponse<BaseApiResponse<List<Movie>>>> getMoviesData(@Path("sort") String sort, @Query("api_key") String api_key);

    @GET("movie/{id}/videos")
    Flowable<ApiResponse<BaseApiResponse<List<Video>>>> getVideoData(@Path("id") String movieId, @Query("api_key") String api_key);

    @GET("movie/{id}/reviews")
    Flowable<ApiResponse<BaseApiResponse<List<Review>>>> getReviewData(@Path("id") String movieId, @Query("api_key") String api_key);
}
