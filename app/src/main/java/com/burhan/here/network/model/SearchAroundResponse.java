package com.burhan.here.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by BURHAN on 2/5/2017.
 */

public class SearchAroundResponse {

    @SerializedName("results")
    private SearchResult results;


    public SearchResult getResults() {
        return results;
    }

    public void setResults(SearchResult results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "SearchAroundResponse{" +
                "results=" + results +
                '}';
    }
}
