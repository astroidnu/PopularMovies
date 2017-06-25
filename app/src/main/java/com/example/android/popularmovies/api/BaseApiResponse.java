package com.example.android.popularmovies.api;

/**
 * Created by ibnumuzzakkir on 15/06/2017.
 * Android Developer
 * Garena Indonesia
 */


public class BaseApiResponse<T> {
    public int page;
    public int total_results;
    public int total_pages;
    public T results;
}
