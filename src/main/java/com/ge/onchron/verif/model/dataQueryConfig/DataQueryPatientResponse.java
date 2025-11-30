package com.ge.onchron.verif.model.dataQueryConfig;

import java.util.ArrayList;
import java.util.Objects;

public class DataQueryPatientResponse {
    public String patient;
    public long version;
    public ArrayList<DataQueryResult> results;

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        DataQueryPatientResponse that = (DataQueryPatientResponse) o;
        return Objects.equals(patient, that.patient)
                && Objects.equals(version, that.version)
                && Objects.equals(results, that.results);
    }
}
