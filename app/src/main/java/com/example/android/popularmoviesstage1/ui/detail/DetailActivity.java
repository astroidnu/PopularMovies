package com.example.android.popularmoviesstage1.ui.detail;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmoviesstage1.MovieApp;
import com.example.android.popularmoviesstage1.R;
import com.example.android.popularmoviesstage1.model.Movie;
import com.example.android.popularmoviesstage1.utils.Constants;
import com.squareup.picasso.Picasso;


import javax.inject.Inject;

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

        Movie movie = getIntent().getParcelableExtra("data");
        showLoading();
        showData(movie);
    }

    private void setupActivityComponent() {
        MovieApp.get()
                .getAppComponent()
                .plus(new DetailActivityModule(this))
                .inject(this);
    }

    @Override
    public void showData(Movie movie) {
        if(movie != null){
            mCollapsingToolbarLayout.setTitle(movie.getTitle());
            mMovieDescription.setText(movie.getOverview());
            Picasso.with(getApplicationContext())
                    .load(Constants.BASE_URL_IMAGE_BACKDROP+"/w500"+ movie.getBackdropPath())
                    .into(mMovieHeader);
            Picasso.with(getApplicationContext())
                    .load(Constants.BASE_URL_IMAGE_BACKDROP+"/w342"+movie.getBackdropPath())
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

}
