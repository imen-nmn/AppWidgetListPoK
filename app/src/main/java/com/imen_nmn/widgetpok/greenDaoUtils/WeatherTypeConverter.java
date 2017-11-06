package com.imen_nmn.widgetpok.greenDaoUtils;

import org.greenrobot.greendao.converter.PropertyConverter;

public class WeatherTypeConverter implements PropertyConverter<String, String> {
    @Override
    public String convertToEntityProperty(String databaseValue) {
        return String.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(String entityProperty) {
        return entityProperty;
    }
}
