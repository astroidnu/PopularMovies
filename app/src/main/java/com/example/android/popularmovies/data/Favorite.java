package com.example.android.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by ibnumuzzakkir on 7/18/17.
 * Android Engineer
 * SCO Project
 */
@Entity
public class Favorite implements Parcelable {
    @Id
    @SerializedName("id")
    public long id;
    @SerializedName("title")
    public String title;

    protected Favorite(Parcel in) {
        id = in.readLong();
        title = in.readString();
    }


    @Generated(hash = 459811785)
    public Favorite() {
    }


    @Generated(hash = 570556283)
    public Favorite(long id, String title) {
        this.id = id;
        this.title = title;
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
        dest.writeLong(id);
        dest.writeString(title);
    }


    public long getId() {
        return this.id;
    }


    public void setId(long id) {
        this.id = id;
    }

}
