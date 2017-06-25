package com.example.android.popularmovies.utils;

import android.util.Log;

import com.example.android.popularmovies.vo.Resource;
import com.example.android.popularmovies.vo.Status;

import java.io.IOException;



import io.reactivex.annotations.NonNull;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by Rizky Fadillah on 05/06/2017.
 * Android Developer
 */

public abstract class CustomResourceSubscriber<T> extends ResourceSubscriber<T> {

    @Override
    public void onNext(T t) {
        Resource resource = (Resource) t;
        if (resource.status == Status.SUCCESS) {
            onNextAndCompleted(t);
        } else {
            onError(resource.message);
        }
    }

    protected abstract void onNextAndCompleted(@NonNull T body);

    @Override
    public void onError(Throwable e) {
        if (e instanceof IOException) {
            onError("No internet connection");
        } else {
            Log.d(getClass().getName(), e.getMessage());
            onError("Something's wrong");
        }
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onError(String errorMessage);

}
