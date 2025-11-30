package com.ge.onchron.verif.model.valueSetsConfig;

import com.ge.onchron.verif.model.criteriaCalculator.Metadata;

import java.util.Objects;

public class Concept {
    public String code;
    public String display;

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Concept that = (Concept) o;
        return Objects.equals(code, that.code)
                && Objects.equals(display, that.display);
    }
}
