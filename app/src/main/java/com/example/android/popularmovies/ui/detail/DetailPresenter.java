package com.example.android.popularmovies.ui.detail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.android.popularmovies.data.DataContract;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.Review;
import com.example.android.popularmovies.data.Video;
import com.example.android.popularmovies.repository.MainRepository;
import com.example.android.popularmovies.utils.Constants;
import com.example.android.popularmovies.vo.Resource;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by ibnumuzzakkir on 16/06/2017.
 * Android Developer
 * Garena Indonesia
 */

public class DetailPresenter implements DetailContract.UserActionListener {
    private DetailContract.View mView;
    private MainRepository mainRepository;
    private Context mContext;

    private List<Video> mListTrailer;
    private List<Review> mListReview;

    public DetailPresenter(MainRepository mainRepository,Context context) {
        this.mainRepository = mainRepository;
        mContext = context;
    }

    public void setView(DetailContract.View mView) {
        this.mView = mView;
    }

    @Override
    public <T> void getReviewAndTrailerList(String id) {
        mView.showLoading(true);
        Flowable.zip(mainRepository.getVideos(id), mainRepository.getReviews(id), new BiFunction<Resource<List<Video>>, Resource<List<Review>>, HashMap<Integer, List<T>>>() {
            @Override
            public HashMap<Integer, List<T>> apply(@NonNull Resource<List<Video>> trailerList, @NonNull Resource<List<Review>> reviewList) throws Exception {
                HashMap<Integer, List<T>> datas = new HashMap<>();
                datas.put(Constants.ADAPTER_TYPE.TRAILER_ADAPTER, (List<T>) trailerList.data);
                datas.put(Constants.ADAPTER_TYPE.REVIEW_ADAPTER, (List<T>) reviewList.data);
                return datas;
            }
        }).subscribe(new ResourceSubscriber<HashMap<Integer, List<T>>>() {
            @Override
            public void onNext(HashMap<Integer, List<T>> data) {
                mListReview = (List<Review>) data.get(1);
                mListTrailer = (List<Video>) data.get(0);
                setAdapter(data);
            }

            @Override
            public void onError(Throwable t) {
                Log.e(getClass().getName(), t.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public <T> void setAdapter(HashMap<Integer, List<T>> data) {
        for(int i = 0; i<data.size();i++){
            mView.setAllAdapter(data.get(i), i);
        }
        mView.showLoading(false);
    }

    @Override
    public void saveFavorite(Movie movie) throws Exception {
        Cursor cursor =
                mContext.getContentResolver().query(Uri.parse(DataContract.FavoriteEntry.CONTENT_URI+"/"+ String.valueOf(movie.getId())),
                        new String[]{DataContract.FavoriteEntry.FAVORITE_COLUMN_MOVIES_TITLE},
                        null,
                        null,
                        null);
        if(cursor != null){
            if(cursor.getCount() == 0){
                ContentValues contentValues = new ContentValues();
                contentValues.put(DataContract.FavoriteEntry.FAVORITE_COLUMN_MOVIES_ID,movie.getId());
                contentValues.put(DataContract.FavoriteEntry.FAVORITE_COLUMN_MOVIES_TITLE,movie.getTitle());
                contentValues.put(DataContract.FavoriteEntry.FAVORITE_COLUMN_MOVIES_ORIGINAL_TITLE,movie.getOriginalTitle());
                contentValues.put(DataContract.FavoriteEntry.FAVORITE_COLUMN_MOVIES_POSTER_PATH,movie.getPosterPath());
                contentValues.put(DataContract.FavoriteEntry.FAVORITE_COLUMN_MOVIES_BACKDROP_PATH,movie.getBackdropPath());
                contentValues.put(DataContract.FavoriteEntry.FAVORITE_COLUMN_MOVIES_TITLE,movie.getTitle());
                contentValues.put(DataContract.FavoriteEntry.FAVORITE_COLUMN_MOVIES_ADULT,movie.isAdult());
                contentValues.put(DataContract.FavoriteEntry.FAVORITE_COLUMN_MOVIES_ORIGINAL_LANGUAGE,movie.getOriginalLanguage());
                contentValues.put(DataContract.FavoriteEntry.FAVORITE_COLUMN_MOVIES_OVERVIEW,movie.getOverview());
                contentValues.put(DataContract.FavoriteEntry.FAVORITE_COLUMN_MOVIES_POPULARITY,movie.getPopularity());
                contentValues.put(DataContract.FavoriteEntry.FAVORITE_COLUMN_MOVIES_VOTE_AVERAGE,movie.getVoteAverage());
                contentValues.put(DataContract.FavoriteEntry.FAVORITE_COLUMN_MOVIES_VOTE_COUNT,movie.getVoteCount());
                contentValues.put(DataContract.FavoriteEntry.FAVORITE_COLUMN_MOVIES_RELEASE_DATE,movie.getReleaseDate());
                mContext.getContentResolver().insert(DataContract.FavoriteEntry.CONTENT_URI, contentValues);
                mView.isFavorite(true);
            }else{
                mContext.getContentResolver().delete(Uri.parse(DataContract.FavoriteEntry.CONTENT_URI + "/" + movie.getId()),null,null);
                mView.isFavorite(false);
            }
        }
    }

    @Override
    public void checkFavorite(long id) {
        Cursor cursor =
                mContext.getContentResolver().query(Uri.parse(DataContract.FavoriteEntry.CONTENT_URI+"/"+ String.valueOf(id)),
                        new String[]{DataContract.FavoriteEntry.FAVORITE_COLUMN_MOVIES_TITLE},
                        null,
                        null,
                        null);
        if (cursor != null) {
            if(cursor.getCount() == 0){
                mView.isFavorite(false);
            }else{
                mView.isFavorite(true);
            }
        }
        cursor.close();
    }

    @Override
    public void urlShareContent() {
        if(mListTrailer.size() >0){
            mView.shareContent(mListTrailer.get(0).getKey());
        }
    }
}
