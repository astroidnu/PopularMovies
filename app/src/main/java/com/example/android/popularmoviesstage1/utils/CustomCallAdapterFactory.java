package com.example.android.popularmoviesstage1.utils;

import android.support.annotation.Nullable;

import com.example.android.popularmoviesstage1.api.ApiResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Flowable;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * Created by ibnumuzzakkir on 15/06/2017.
 * Android Developer
 * Garena Indonesia
 */

public class CustomCallAdapterFactory extends CallAdapter.Factory {
    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != Flowable.class) {
            return null;
        }
        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Class<?> rawObservableType = getRawType(observableType);
        if (rawObservableType != ApiResponse.class) {
            throw new IllegalArgumentException("type must be a resource");
        }
        if (! (observableType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("resource must be parameterized");
        }
        Type bodyType = getParameterUpperBound(0, (ParameterizedType) observableType);
        return new FlowableCallAdapter<>(bodyType);
    }
}
