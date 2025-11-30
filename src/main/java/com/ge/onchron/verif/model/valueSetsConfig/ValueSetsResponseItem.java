package com.ge.onchron.verif.model.valueSetsConfig;

import java.util.Objects;

public class ValueSetsResponseItem {
    public String name;
    public ValueSets valueset;

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        ValueSetsResponseItem that = (ValueSetsResponseItem) o;
        return Objects.equals(name, that.name)
                && Objects.equals(valueset, that.valueset);
    }
}
