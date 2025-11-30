package com.ge.onchron.verif.model.valueSetsConfig;


import java.util.ArrayList;
import java.util.Objects;

public class ValueSetsResponse {
    public ArrayList<ValueSetsListItemResponse> valuesets;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueSetsResponse that = (ValueSetsResponse) o;
        return Objects.equals(valuesets, that.valuesets);
    }
}
