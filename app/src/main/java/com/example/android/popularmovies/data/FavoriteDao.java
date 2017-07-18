package com.example.android.popularmovies.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "FAVORITE".
*/
public class FavoriteDao extends AbstractDao<Favorite, Long> {

    public static final String TABLENAME = "FAVORITE";

    /**
     * Properties of entity Favorite.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
    }


    public FavoriteDao(DaoConfig config) {
        super(config);
    }
    
    public FavoriteDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FAVORITE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: id
                "\"TITLE\" TEXT);"); // 1: title
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FAVORITE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Favorite entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Favorite entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public Favorite readEntity(Cursor cursor, int offset) {
        Favorite entity = new Favorite( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // title
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Favorite entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Favorite entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Favorite entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Favorite entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}