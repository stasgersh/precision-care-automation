/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2021, 2021, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;

import static org.junit.Assert.fail;

public class Table {

    private String title;
    private List<Map<String, String>> rows;
    private List<TableHeaderItem> headerItems;

    public Table( List<Map<String, String>> rows ) {
        this.rows = rows;
    }

    public Table( String title, List<Map<String, String>> rows ) {
        this.title = title;
        this.rows = rows;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public List<Map<String, String>> getRows() {
        return rows;
    }

    public void setHeaderItems( List<TableHeaderItem> headerItems ) {
        this.headerItems = headerItems;
    }

    public List<TableHeaderItem> getHeaderItems() {
        return headerItems;
    }

    @Override
    public String toString() {
        return "Table{" +
                "title='" + title + '\'' +
                ", rows=" + rows +
                ", headerItems=" + headerItems +
                '}';
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Table table = (Table) o;
        return Objects.equals( title, table.title ) && rows.equals( table.rows );
    }

    public boolean hasSameTitleAndRows( Object o, boolean withSameRowOrder ) {
        if ( withSameRowOrder ) {
            return this.equals( o );
        } else {
            if ( this == o ) return true;
            if ( o == null || getClass() != o.getClass() ) return false;
            Table table = (Table) o;
            return Objects.equals( title, table.title ) && CollectionUtils.isEqualCollection( this.getRows(), table.getRows() );
        }
    }

    public boolean hasSameRows( Object o, boolean withSameRowOrder ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Table table = (Table) o;
        if ( withSameRowOrder ) {
            return this.getRows().equals( table.getRows() );
        } else {
            return CollectionUtils.isEqualCollection( this.getRows(), table.getRows() );
        }
    }

}
