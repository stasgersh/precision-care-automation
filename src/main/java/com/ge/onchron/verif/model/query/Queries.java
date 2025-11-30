package com.ge.onchron.verif.model.query;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Queries {
    ArrayList<Query> queries;

    @JsonProperty("queries")
    public ArrayList<Query> getQueries() {
        return this.queries;
    }

    public void setQueries(ArrayList<Query> queries) {
        this.queries = queries;
    }
}
