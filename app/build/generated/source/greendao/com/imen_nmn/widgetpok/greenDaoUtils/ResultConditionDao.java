package com.imen_nmn.widgetpok.greenDaoUtils;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "RESULT_CONDITION".
*/
public class ResultConditionDao extends AbstractDao<ResultCondition, Long> {

    public static final String TABLENAME = "RESULT_CONDITION";

    /**
     * Properties of entity ResultCondition.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property TempC = new Property(1, String.class, "tempC", false, "TEMP_C");
        public final static Property WeatherDesc = new Property(2, String.class, "weatherDesc", false, "WEATHER_DESC");
        public final static Property WeatherIconUrl = new Property(3, String.class, "weatherIconUrl", false, "WEATHER_ICON_URL");
    }


    public ResultConditionDao(DaoConfig config) {
        super(config);
    }
    
    public ResultConditionDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"RESULT_CONDITION\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"TEMP_C\" TEXT," + // 1: tempC
                "\"WEATHER_DESC\" TEXT," + // 2: weatherDesc
                "\"WEATHER_ICON_URL\" TEXT);"); // 3: weatherIconUrl
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"RESULT_CONDITION\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ResultCondition entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String tempC = entity.getTempC();
        if (tempC != null) {
            stmt.bindString(2, tempC);
        }
 
        String weatherDesc = entity.getWeatherDesc();
        if (weatherDesc != null) {
            stmt.bindString(3, weatherDesc);
        }
 
        String weatherIconUrl = entity.getWeatherIconUrl();
        if (weatherIconUrl != null) {
            stmt.bindString(4, weatherIconUrl);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ResultCondition entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String tempC = entity.getTempC();
        if (tempC != null) {
            stmt.bindString(2, tempC);
        }
 
        String weatherDesc = entity.getWeatherDesc();
        if (weatherDesc != null) {
            stmt.bindString(3, weatherDesc);
        }
 
        String weatherIconUrl = entity.getWeatherIconUrl();
        if (weatherIconUrl != null) {
            stmt.bindString(4, weatherIconUrl);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ResultCondition readEntity(Cursor cursor, int offset) {
        ResultCondition entity = new ResultCondition( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // tempC
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // weatherDesc
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // weatherIconUrl
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ResultCondition entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTempC(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setWeatherDesc(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setWeatherIconUrl(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ResultCondition entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ResultCondition entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ResultCondition entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
