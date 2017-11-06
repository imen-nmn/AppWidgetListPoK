package com.imen_nmn.widgetpok.rest.xmlResponse;

import com.imen_nmn.widgetpok.greenDaoUtils.ResultCondition;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by mobilepowered on 21/03/17.
 */
@Root(name = "data", strict = false)
public class SearchWeatherContent {
    @Element(name = "current_condition", required = false)
    public ResultCondition resultCondition;

    public ResultCondition getResultCondition() {
        return resultCondition;
    }

    public void setResultCondition(ResultCondition resultCondition) {
        this.resultCondition = resultCondition;
    }

    @Override
    public String toString() {
        return "SearchWeatherContent{" +
                "resultCondition=" + resultCondition.toString() +
                '}';
    }
}
