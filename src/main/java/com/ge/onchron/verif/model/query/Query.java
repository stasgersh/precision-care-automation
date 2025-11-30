package com.ge.onchron.verif.model.query;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Query {
    String alias;
    DataQuery query;

    public DataQuery getQuery() {
        return this.query;
    }

    public void setQuery(DataQuery query) {
        this.query = query;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
