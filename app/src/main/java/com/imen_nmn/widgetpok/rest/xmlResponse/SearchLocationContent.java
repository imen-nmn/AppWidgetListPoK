package com.imen_nmn.widgetpok.rest.xmlResponse;

import com.imen_nmn.widgetpok.greenDaoUtils.ResultLocation;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mobilepowered on 21/03/17.
 */

@Root(name = "search_api", strict = false)

public class SearchLocationContent implements Serializable {
    @ElementList(name = "result", inline = true, required = false)
    public ArrayList<ResultLocation> results;

    public ArrayList<ResultLocation> getResults() {
        return results;
    }

    public void setResults(ArrayList<ResultLocation> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "SearchApiContent{" +
                "result=" + results.toString() +
                '}';
    }
}
