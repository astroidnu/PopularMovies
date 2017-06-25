package com.example.android.popularmovies.ui.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.popularmovies.MovieApp;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.adapter.MovieAdapter;
import com.example.android.popularmovies.model.Movie;
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

    private MainContract.UserActionListener mActionListener;
    private MovieAdapter movieAdapter;
    private GridLayoutManager mGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupActivityComponent();
        mActionListener = mainPresenter;
        mainPresenter.setView(this);
        mActionListener.getMovies(Constants.SORT_TYPE.POPULAR_MOVIES);
    }

    private void setupActivityComponent() {
        MovieApp.get()
                .getAppComponent()
                .plus(new MainActivityModule(this))
                .inject(this);
    }

    @Override
    public void setAdapter(List<Movie> movies) {
        mGridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mMovieRV.setLayoutManager(mGridLayoutManager);
        movieAdapter = new MovieAdapter(movies, this);
        mMovieRV.setAdapter(movieAdapter);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
