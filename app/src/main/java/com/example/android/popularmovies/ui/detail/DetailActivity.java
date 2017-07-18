package com.example.android.popularmovies.ui.detail;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.MovieApp;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.adapter.ReviewAdapter;
import com.example.android.popularmovies.adapter.TrailerAdapter;
import com.example.android.popularmovies.data.Favorite;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.ui.home.MainActivity;
import com.example.android.popularmovies.utils.Constants;
import com.squareup.picasso.Picasso;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import bolts.Task;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Module;

/**
 * Created by ibnumuzzakkir on 16/06/2017.
 * Android Developer
 * Garena Indonesia
 */
@Module
public class DetailActivity extends AppCompatActivity implements DetailContract.View {
    @Inject
    DetailPresenter detailPresenter;

    @BindView(R.id.detail_movie_description)
    TextView mMovieDescription;
    @BindView(R.id.detail_movie_header)
    ImageView mMovieHeader;
    @BindView(R.id.detail_movie_collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.detail_movie_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.detail_movie_poster)
    ImageView mPoster;
    @BindView(R.id.detail_movie_release_date)
    TextView mReleaseDate;
    @BindView(R.id.detail_movie_count_average)
    TextView mCountAverage;
    @BindView(R.id.detail_movie_progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.detail_movie_btn_favorite)
    FloatingActionButton mBtnFavorite;
    @BindView(R.id.detail_movie_movie_trailer_list)
    RecyclerView mMovieTrailerRV;
    @BindView(R.id.detail_movie_movie_review_list)
    RecyclerView mReviewRV;
    @BindView(R.id.detail_movie_no_review)
    LinearLayout mLayoutNoReview;
    @BindView(R.id.detail_movie_no_trailer)
    LinearLayout mLayoutNoTrailer;
    @BindView(R.id.no_review_title)
    TextView mNoReviewTitle;

    private DetailContract.UserActionListener mActionListener;
    private TrailerAdapter trailerAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);
        setupActivityComponent();
        mActionListener = detailPresenter;
        detailPresenter.setView(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        showLoading();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){
            if(extras.containsKey(Constants.INTENT_TAG.TAG_DATA)){
                Movie movie = getIntent().getParcelableExtra(Constants.INTENT_TAG.TAG_DATA);
                showData(movie);
                mActionListener.getReviewAndTrailerList(String.valueOf(movie.getId()));
                mActionListener.checkFavorite(movie.getId());
                mBtnFavorite.setOnClickListener(v -> mActionListener.saveFavorite(movie));
            }

            if(extras.containsKey(Constants.INTENT_TAG.TAG_SOURCE)){
                int source = getIntent().getIntExtra(Constants.INTENT_TAG.TAG_SOURCE,0);
                backToMain(source);
            }
        }
    }

    private void setupActivityComponent() {
        MovieApp.get()
                .getAppComponent()
                .plus(new DetailActivityModule(this))
                .inject(this);
    }


    @Override
    public void showData(Movie movie){
        if(movie != null){
            mCollapsingToolbarLayout.setTitle(movie.getTitle());
            mMovieDescription.setText(movie.getOverview());
            Picasso.with(getApplicationContext())
                    .load(Constants.BASE_URL_IMAGE_BACKDROP+"/w500"+ movie.getBackdropPath())
                    .into(mMovieHeader);
            try{
                setPalette(Constants.BASE_URL_IMAGE_BACKDROP+"/w342"+movie.getBackdropPath());
            }catch (Exception e){
                Log.e(getClass().getName(), e.getMessage());
            }
            Picasso.with(getApplicationContext())
                    .load(Constants.BASE_URL_IMAGE_BACKDROP+"/w342"+movie.getPosterPath())
                    .into(mPoster);

            mCountAverage.setText(getResources().getString(R.string.movie_vote_average, String.valueOf(movie.getVoteAverage())));
            mReleaseDate.setText(movie.getReleaseDate());
        }
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public <T> void setAllAdapter(List<T> data, int adapterId){
        switch (adapterId){
            case Constants.ADAPTER_TYPE.TRAILER_ADAPTER:
                if(data != null && data.size() > 0){
                    mMovieTrailerRV.setVisibility(View.VISIBLE);
                    mLayoutNoTrailer.setVisibility(View.GONE);
                    mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                    mMovieTrailerRV.setLayoutManager(mLinearLayoutManager);
                    trailerAdapter = new TrailerAdapter(data, this);
                    mMovieTrailerRV.setAdapter(trailerAdapter);
                }else{
                    mMovieTrailerRV.setVisibility(View.GONE);
                    mNoReviewTitle.setText(getResources().getString(R.string.movie_no_review));
                    mLayoutNoTrailer.setVisibility(View.VISIBLE);
                }

                break;
            case Constants.ADAPTER_TYPE.REVIEW_ADAPTER:
                if(data != null && data.size() > 0){
                    mReviewRV.setVisibility(View.VISIBLE);
                    mLayoutNoReview.setVisibility(View.GONE);
                    mLinearLayoutManager = new LinearLayoutManager(this);
                    mReviewRV.setLayoutManager(mLinearLayoutManager);
                    reviewAdapter = new ReviewAdapter(data, this);
                    mReviewRV.setAdapter(reviewAdapter);
                }else{
                    mReviewRV.setVisibility(View.GONE);
                    mNoReviewTitle.setText(getResources().getString(R.string.movie_no_review));
                    mLayoutNoReview.setVisibility(View.VISIBLE);
                }
                break;
        }

    }

    @Override
    public void isFavorite(boolean stat) {
        if(stat){
            mBtnFavorite.setColorFilter(getResources().getColor(R.color.colorAccent));
        }else{
            mBtnFavorite.setColorFilter(getResources().getColor(R.color.colorWhite));
        }
    }

    @Override
    public void backToMain(int source) {
        switch (source){
            case Constants.SORT_TYPE.FAVORITE:
                mToolbar.setNavigationOnClickListener(v -> {
                    Intent i = new Intent(DetailActivity.this, MainActivity.class);
                    i.putExtra("source", Constants.SORT_TYPE.FAVORITE);
                    startActivity(i);
                });
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                this.finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    public void setPalette(String url) throws MalformedURLException {
        Task.call(new Callable<Bitmap>() {
            @Override
            public Bitmap call() throws Exception {
                try {
                    URL mUrl = new URL(url);
                    HttpURLConnection connection=(HttpURLConnection)mUrl.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input=connection.getInputStream();
                    Bitmap myBitmap= BitmapFactory.decodeStream(input);
                    Palette.from(myBitmap).generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            Palette.Swatch mutedColor = palette.getVibrantSwatch();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mutedColor != null) {
                                mCollapsingToolbarLayout.setBackgroundColor(mutedColor.getRgb());
                                mBtnFavorite.setBackgroundTintList(ColorStateList.valueOf(mutedColor.getRgb()));
                                mCollapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(mutedColor.getRgb()));
                                getWindow().setStatusBarColor(palette.getDarkMutedColor(mutedColor.getRgb()));
                            }
                        }
                    });
                    return myBitmap;
                } catch (IOException e) {
                    Log.d(getClass().getName(),  e.getMessage());
                    return null;
                }
            }
        }, Task.BACKGROUND_EXECUTOR);
    }

}
