package com.ge.onchron.verif.model.dataQueryConfig;

import java.util.Objects;

public class DataQueryIdentifier {
    public String system;
    public String value;

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        DataQueryIdentifier that = (DataQueryIdentifier) o;
        return Objects.equals(system, that.system)
                && Objects.equals(value, that.value);
    }
}
