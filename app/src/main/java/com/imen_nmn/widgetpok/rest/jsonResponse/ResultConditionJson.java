package com.imen_nmn.widgetpok.rest.jsonResponse;

import java.io.Serializable;

/**
 * Created by mobilepowered on 21/03/17.
 */

public class ResultConditionJson implements Serializable {
    static final long serialVersionUID = 536871008;
    String tempC ;
    String weatherDesc ;
    String weatherIconUrl ;

    @Override
    public String toString() {
        return "ResultCondition{" +
                "tempC='" + tempC + '\'' +
                ", weatherDesc='" + weatherDesc + '\'' +
                ", weatherIconUrl='" + weatherIconUrl + '\'' +
                '}';
    }
}
