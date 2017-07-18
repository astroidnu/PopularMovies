package com.example.android.popularmovies.data;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.android.popularmovies.data.Favorite;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.Review;
import com.example.android.popularmovies.data.Video;

import com.example.android.popularmovies.data.FavoriteDao;
import com.example.android.popularmovies.data.MovieDao;
import com.example.android.popularmovies.data.ReviewDao;
import com.example.android.popularmovies.data.VideoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig favoriteDaoConfig;
    private final DaoConfig movieDaoConfig;
    private final DaoConfig reviewDaoConfig;
    private final DaoConfig videoDaoConfig;

    private final FavoriteDao favoriteDao;
    private final MovieDao movieDao;
    private final ReviewDao reviewDao;
    private final VideoDao videoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        favoriteDaoConfig = daoConfigMap.get(FavoriteDao.class).clone();
        favoriteDaoConfig.initIdentityScope(type);

        movieDaoConfig = daoConfigMap.get(MovieDao.class).clone();
        movieDaoConfig.initIdentityScope(type);

        reviewDaoConfig = daoConfigMap.get(ReviewDao.class).clone();
        reviewDaoConfig.initIdentityScope(type);

        videoDaoConfig = daoConfigMap.get(VideoDao.class).clone();
        videoDaoConfig.initIdentityScope(type);

        favoriteDao = new FavoriteDao(favoriteDaoConfig, this);
        movieDao = new MovieDao(movieDaoConfig, this);
        reviewDao = new ReviewDao(reviewDaoConfig, this);
        videoDao = new VideoDao(videoDaoConfig, this);

        registerDao(Favorite.class, favoriteDao);
        registerDao(Movie.class, movieDao);
        registerDao(Review.class, reviewDao);
        registerDao(Video.class, videoDao);
    }
    
    public void clear() {
        favoriteDaoConfig.clearIdentityScope();
        movieDaoConfig.clearIdentityScope();
        reviewDaoConfig.clearIdentityScope();
        videoDaoConfig.clearIdentityScope();
    }

    public FavoriteDao getFavoriteDao() {
        return favoriteDao;
    }

    public MovieDao getMovieDao() {
        return movieDao;
    }

    public ReviewDao getReviewDao() {
        return reviewDao;
    }

    public VideoDao getVideoDao() {
        return videoDao;
    }

}
