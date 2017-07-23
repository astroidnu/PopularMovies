package com.example.android.popularmovies.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ibnumuzzakkir on 7/21/17.
 * Android Engineer
 * SCO Project
 */

public class Favorite {
    @SerializedName("id")
    public long id;
    @SerializedName("title")
    public String title;

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
