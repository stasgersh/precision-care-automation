package com.ge.onchron.verif.model.query;

import java.util.List;
import java.util.Map;

public class DataQuery {
    private Object from;
    private Object show;
    private Map<String, Object> filter;
    Boolean dedup;

    public Boolean getDedup() {
        return dedup;
    }

    public void setDedup(Boolean dedup) {
        this.dedup = dedup;
    }

    public void setFrom(Object from) {
        this.from = from;
    }

    public void setShow(Object show) {
        this.show = show;
    }

    public void setFilter(Map<String, Object> filter) {
        this.filter = filter;
    }

    public Object getFrom() {
        return from;
    }

    public Object getShow() {
        return show;
    }

    public Map<String, Object> getFilter() {
        return filter;
    }
}
