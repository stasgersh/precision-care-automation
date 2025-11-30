package com.ge.onchron.verif.model.valueSetsConfig;

import com.ge.onchron.verif.model.criteriaCalculator.Metadata;

import java.util.ArrayList;
import java.util.Objects;

public class Compose {
    public ArrayList<Include> include;

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Compose that = (Compose) o;
        return Objects.equals(include, that.include);
    }
}
