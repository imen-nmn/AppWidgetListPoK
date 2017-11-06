package com.imen_nmn.widgetpok.greenDaoUtils;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by imen_nmn on 21/03/17.
 */


@Entity(indexes = {
        @Index(value = "id DESC")
})

@Root(name = "result", strict = false)

public class ResultLocation implements Serializable {

    static final long serialVersionUID = 536871555 ;
    @Id
    private Long id;
    @Element(name = "areaName" ,required= false)
    public String areaName;
    @Element(name = "country",required= false)
    public String country;
    @Element(name = "region",required= false)
    public String region;
    @Element(name = "latitude",required= false)
    public String latitude;
    @Element(name = "longitude",required= false)
    public String longitude;
    @Element(name = "population",required= false)
    public String population;
    @Element(name = "weatherUrl",required= false)
    public String weatherUrl;

    @Convert(converter = WeatherTypeConverter.class, columnType = String.class)
    private String type;
    private long resultConditionId;

    @ToOne(joinProperty = "resultConditionId")
    private ResultCondition resultCondition ;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1407625657)
    private transient ResultLocationDao myDao;
    @Generated(hash = 639057947)
    private transient Long resultCondition__resolvedKey;
    @Generated(hash = 1261502139)
    public ResultLocation() {
    }
    @Generated(hash = 930115048)
    public ResultLocation(Long id, String areaName, String country, String region,
            String latitude, String longitude, String population, String weatherUrl,
            String type, long resultConditionId) {
        this.id = id;
        this.areaName = areaName;
        this.country = country;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
        this.population = population;
        this.weatherUrl = weatherUrl;
        this.type = type;
        this.resultConditionId = resultConditionId;
    }
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    @NotNull
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getWeatherUrl() {
        return weatherUrl;
    }

    public void setWeatherUrl(String weatherUrl) {
        this.weatherUrl = weatherUrl;
    }

    public long getResultConditionId() {
        return resultConditionId;
    }

    public void setResultConditionId(long resultConditionId) {
        this.resultConditionId = resultConditionId;
    }

    @Override
    public String toString() {
        return "The area name  is " + areaName  +
                " \nlat = " + latitude  +
                ", long = " + longitude  ;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 905618508)
    public ResultCondition getResultCondition() {
        long __key = this.resultConditionId;
        if (resultCondition__resolvedKey == null
                || !resultCondition__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ResultConditionDao targetDao = daoSession.getResultConditionDao();
            ResultCondition resultConditionNew = targetDao.load(__key);
            synchronized (this) {
                resultCondition = resultConditionNew;
                resultCondition__resolvedKey = __key;
            }
        }
        return resultCondition;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 17165782)
    public void setResultCondition(@NotNull ResultCondition resultCondition) {
        if (resultCondition == null) {
            throw new DaoException(
                    "To-one property 'resultConditionId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.resultCondition = resultCondition;
            resultConditionId = resultCondition.getId();
            resultCondition__resolvedKey = resultConditionId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1375382138)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getResultLocationDao() : null;
    }

}
