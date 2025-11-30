package com.ge.onchron.verif.model.valueSetsConfig;

import com.ge.onchron.verif.model.criteriaCalculator.Metadata;

import java.util.ArrayList;
import java.util.Objects;

public class Include {

    public String system;
    public ArrayList<Concept> concept;

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Include that = (Include) o;
        return Objects.equals(system, that.system)
                && Objects.equals(concept, that.concept);
    }
}
