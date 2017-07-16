package com.example.android.popularmovies.di.module;

import com.example.android.popularmovies.MovieApp;
import com.example.android.popularmovies.api.NetworkService;
import com.example.android.popularmovies.utils.Constants;
import com.example.android.popularmovies.utils.CustomCallAdapterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ibnumuzzakkir on 15/06/2017.
 * Android Developer
 * Garena Indonesia
 */
@Module
public class NetworkModule {
    private MovieApp movieApp;

    public NetworkModule(MovieApp movieApp){
        this.movieApp = movieApp;
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkhttpClient() {
        File httpCacheDirectory = new File(movieApp.getCacheDir(), "httpCache");
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(chain -> {
                    try {
                        return chain.proceed(chain.request());
                    } catch (Exception e) {
                        Request offlineRequest = chain.request().newBuilder()
                                .header("Cache-Control", "public, only-if-cached," +
                                        "max-stale=" + 60 * 60 * 24)
                                .build();
                        return chain.proceed(offlineRequest);
                    }
                })
                .cache(cache)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    public Retrofit provideRestAdapter(OkHttpClient okHttpClient) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(okHttpClient)
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(new CustomCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create());
        return builder.build();
    }

    @Provides
    @Singleton
    public NetworkService provideOchaService(Retrofit restAdapter) {
        return restAdapter.create(NetworkService.class);
    }
}
