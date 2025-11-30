/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2022, 2022, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.model.detailedDataItems;

import com.ge.onchron.verif.model.Table;

public class TableItem extends DetailedDataItem {

    private Table table;

    public TableItem( String name ) {
        setName( name );
    }

    public TableItem( String name, Table table ) {
        setName( name );
        this.table = table;
    }

    public Table getValue() {
        return table;
    }

    @Override
    public String toString() {
        return "TableItem{" +
                "name=" + getName() +
                " table=" + table +
                '}';
    }

}
