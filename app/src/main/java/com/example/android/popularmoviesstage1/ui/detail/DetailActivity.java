package com.example.android.popularmoviesstage1.ui.detail;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmoviesstage1.MovieApp;
import com.example.android.popularmoviesstage1.R;
import com.example.android.popularmoviesstage1.model.Movie;
import com.example.android.popularmoviesstage1.utils.Constants;
import com.squareup.picasso.Picasso;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

    private DetailContract.UserActionListener mActionListener;

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
            if(extras.containsKey("data")){
                Movie movie = getIntent().getParcelableExtra("data");
                showData(movie);
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
                setPalette(Constants.BASE_URL_IMAGE_BACKDROP+"/w185/"+movie.getPosterPath());
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
