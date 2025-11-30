package com.ge.onchron.verif.model.dataQueryConfig;

import java.util.ArrayList;
import java.util.Objects;

public class DataQueryListResponse {
    public ArrayList<DataQueryEntry> entries;
    public Object nextPageToken;

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        DataQueryListResponse that = (DataQueryListResponse) o;
        return Objects.equals(entries, that.entries)
                && Objects.equals(nextPageToken, that.nextPageToken);
    }
}
