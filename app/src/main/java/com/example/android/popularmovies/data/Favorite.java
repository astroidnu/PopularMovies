package com.example.android.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by ibnumuzzakkir on 7/18/17.
 * Android Engineer
 * SCO Project
 */
@Entity
public class Favorite implements Parcelable {
    @Unique
    @SerializedName("id")
    public String id;
    @SerializedName("title")
    public String title;

    protected Favorite(Parcel in) {
        id = in.readString();
        title = in.readString();
    }

    @Generated(hash = 909337631)
    public Favorite(String id, String title) {
        this.id = id;
        this.title = title;
    }

    @Generated(hash = 459811785)
    public Favorite() {
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
    }
}
