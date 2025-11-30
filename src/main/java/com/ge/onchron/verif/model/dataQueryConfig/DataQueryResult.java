package com.ge.onchron.verif.model.dataQueryConfig;

import java.util.ArrayList;
import java.util.Objects;

public class DataQueryResult {
    public String alias;
    public ArrayList<DataQueryEntry> entries;
    public ArrayList<String> name;
    public Object nextPageToken;

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        DataQueryResult that = (DataQueryResult) o;
        return Objects.equals(alias, that.alias)
                && Objects.equals(entries, that.entries)
                && Objects.equals(name, that.name)
                && Objects.equals(nextPageToken, that.nextPageToken);
    }
}
