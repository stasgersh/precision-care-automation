package com.ge.onchron.verif.model.valueSetsConfig;

import java.util.Objects;

public class ValueSetsListItemResponse {
    public String version;
    public String name;
    public String description;

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        ValueSetsListItemResponse that = (ValueSetsListItemResponse) o;
        return Objects.equals( version, that.version ) &&
                Objects.equals( name, that.name ) &&
                Objects.equals( description, that.description );
    }

}
