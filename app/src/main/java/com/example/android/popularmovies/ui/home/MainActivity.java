package com.example.android.popularmovies.ui.home;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.MovieApp;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.adapter.MovieAdapter;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    @Inject
    MainPresenter mainPresenter;

    @BindView(R.id.main_recyclerview)
    RecyclerView mMovieRV;
    @BindView(R.id.main_progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.main_no_data)
    TextView mMainNoData;

    private MainContract.UserActionListener mActionListener;
    private MovieAdapter movieAdapter;
    private GridLayoutManager mGridLayoutManager;
    private Parcelable mState;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(getClass().getName(), "onSaveInstanceState()");
        mState = mMovieRV.getLayoutManager().onSaveInstanceState();
        outState.putParcelable("state_list", mState);
        mActionListener.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(getClass().getName(),"onRestoreInstanceState");
        if(savedInstanceState != null){
            mState = savedInstanceState.getParcelable("state_list");
            mActionListener.onRestoreInstanceState(savedInstanceState);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mState != null) {
            Log.d(getClass().getName(), mState.toString());
            mMovieRV.getLayoutManager().onRestoreInstanceState(mState);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupActivityComponent();
        mActionListener = mainPresenter;
        mainPresenter.setView(this);
        mActionListener.loadData();
        checkSourceIntent();
        mGridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mMovieRV.setLayoutManager(mGridLayoutManager);
    }


    private void setupActivityComponent() {
        MovieApp.get()
                .getAppComponent()
                .plus(new MainActivityModule(this))
                .inject(this);
    }

    @Override
    public void setAdapter(List<Movie> movies, int typeAdapter) {
        if(movies.size() > 0){
            mMainNoData.setVisibility(View.GONE);
            mMovieRV.setVisibility(View.VISIBLE);
            movieAdapter = new MovieAdapter(movies, this, typeAdapter);
            mMovieRV.setAdapter(movieAdapter);
        }else{
            mMainNoData.setVisibility(View.VISIBLE);
            mMovieRV.setVisibility(View.GONE);
        }

    }


    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sort_most_popular:
                mActionListener.getMovies(Constants.SORT_TYPE.POPULAR_MOVIES);
                return true;
            case R.id.sort_top_rated:
                mActionListener.getMovies(Constants.SORT_TYPE.TOP_RATED_MOVIES);
                return true;
            case R.id.sort_favorite:
                mActionListener.getMovies(Constants.SORT_TYPE.FAVORITE);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void checkSourceIntent(){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            if (extras.containsKey(Constants.INTENT_TAG.TAG_SOURCE)) {
                int mSource = getIntent().getIntExtra(Constants.INTENT_TAG.TAG_SOURCE,0);
                switch (mSource){
                    case Constants.SORT_TYPE.FAVORITE:
                        mActionListener.getMovies(Constants.SORT_TYPE.FAVORITE);
                        break;
                }
            }
        }
    }
}
