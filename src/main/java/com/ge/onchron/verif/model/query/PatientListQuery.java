package com.ge.onchron.verif.model.query;

import java.util.List;

public class PatientListQuery {
    private Integer size;
    private Object show;
    private Object sort;


    public void setShow(Object show) {
        this.show = show;
    }

    public Object getShow() {
        return show;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getSize() {
        return size;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public Object getSort() {
        return sort;
    }

}
