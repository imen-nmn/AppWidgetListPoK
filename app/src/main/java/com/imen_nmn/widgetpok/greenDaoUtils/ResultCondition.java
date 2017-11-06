package com.imen_nmn.widgetpok.greenDaoUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by mobilepowered on 21/03/17.
 */

@Entity
@Root(name = "current_condition", strict = false)
public class ResultCondition implements Serializable {
    static final long serialVersionUID = 536871008;
    @Id
    private Long id;
    @Element(name = "temp_C", required = false)
    String tempC ;
    @Element(name = "weatherDes", required = false)
    String weatherDesc ;
    @Element(name = "weatherIconUrl", required = false)
    String weatherIconUrl ;

    @Generated(hash = 1262079391)
    public ResultCondition(Long id, String tempC, String weatherDesc,
            String weatherIconUrl) {
        this.id = id;
        this.tempC = tempC;
        this.weatherDesc = weatherDesc;
        this.weatherIconUrl = weatherIconUrl;
    }

    @Generated(hash = 1902981467)
    public ResultCondition() {
    }

    public String getTempC() {
        return tempC;
    }

    public void setTempC(String tempC) {
        this.tempC = tempC;
    }


    public String getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(String weatherDesc) {
        this.weatherDesc = weatherDesc;
    }

    public String getWeatherIconUrl() {
        return weatherIconUrl;
    }

    public void setWeatherIconUrl(String weatherIconUrl) {
        this.weatherIconUrl = weatherIconUrl;
    }

    @Override
    public String toString() {
        return "ResultCondition{" +
                "id=" + id +
                ", tempC='" + tempC + '\'' +
                ", weatherDesc='" + weatherDesc + '\'' +
                ", weatherIconUrl='" + weatherIconUrl + '\'' +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
