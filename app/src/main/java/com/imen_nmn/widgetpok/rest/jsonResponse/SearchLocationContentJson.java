package com.imen_nmn.widgetpok.rest.jsonResponse;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by imen_nmn on 21/03/17.
 */

public class SearchLocationContentJson implements Serializable {
    @SerializedName("search_api")
    @Expose
    public SearchApi searchApi ;

    public SearchApi getSearchApi() {
        return searchApi;
    }

    public void setSearchApi(SearchApi searchApi) {
        this.searchApi = searchApi;
    }

    public List<ResultLocationJson> getResults() {
        return searchApi.getResults();
    }


    @Override
    public String toString() {
        return "SearchApiContent{" +
                "result=" + searchApi.getResults().toString() +
                '}';
    }


    private class SearchApi implements Serializable{
        @SerializedName("result")
        @Expose
        public List<ResultLocationJson> results = new ArrayList<ResultLocationJson>() ;

        public List<ResultLocationJson> getResults() {
            return results;
        }

        public void setResults(List<ResultLocationJson> results) {
            this.results = results;
        }


    }

}

