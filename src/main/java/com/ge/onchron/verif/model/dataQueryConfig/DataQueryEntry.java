package com.ge.onchron.verif.model.dataQueryConfig;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Objects;

public class DataQueryEntry {
    public String id;
    public String patient_id;
    public long patient_version;
    public String type;
    public String date;
    public long latest_patient_version;
    public JsonNode attributes;
    public ArrayList<String> provenance;
    public ArrayList<String> subtypes;
    public String sync_status;
    public long sync_timestamp;


    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        DataQueryEntry that = (DataQueryEntry) o;
        return Objects.equals(provenance, that.provenance)
                && Objects.equals(patient_id, that.patient_id)
                && Objects.equals(attributes, that.attributes)
                && Objects.equals(id, that.id)
                && Objects.equals(type, that.type)
                && Objects.equals(patient_version, that.patient_version)
                && Objects.equals(date, that.date)
                && Objects.equals(subtypes, that.subtypes)
                && Objects.equals(latest_patient_version, that.latest_patient_version)
                && Objects.equals(sync_status, that.sync_status)
                && Objects.equals(sync_timestamp, that.sync_timestamp);
    }
}
