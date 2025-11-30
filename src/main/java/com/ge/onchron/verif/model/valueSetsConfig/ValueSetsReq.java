package com.ge.onchron.verif.model.valueSetsConfig;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
public class ValueSetsReq {
    public ValueSets valueset;
    public String name;

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        ValueSetsReq that = (ValueSetsReq) o;
        return Objects.equals( valueset, that.valueset ) &&
                Objects.equals( name, that.name );
    }
}
