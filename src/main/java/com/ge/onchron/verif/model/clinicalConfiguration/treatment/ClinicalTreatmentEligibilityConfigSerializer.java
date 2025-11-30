package com.ge.onchron.verif.model.clinicalConfiguration.treatment;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;

import java.io.IOException;

public class ClinicalTreatmentEligibilityConfigSerializer extends JsonSerializer<ClinicalTreatmentEligibilityConfig> {


    @Override
    public void serialize(ClinicalTreatmentEligibilityConfig value, JsonGenerator gen, com.fasterxml.jackson.databind.SerializerProvider serializers) throws IOException {

        gen.writeStartObject();

        if (value.getId() != null) {
            gen.writeStringField("id", value.getId());
        }
        if (value.getType() != null) {
            gen.writeStringField("type", value.getType());
        }
        if (value.getStatus() != null) {
            gen.writeStringField("status", value.getStatus());
        }
        if (value.getStudyCompletion() != null) {
            gen.writeStringField("studyCompletion", value.getStudyCompletion());
        }
        if (value.getDescription() != null) {
            gen.writeStringField("description", value.getDescription());
        }
        if (value.getName() != null) {
            gen.writeStringField("name", value.getName());
        }
        if (value.getPhase() != null) {
            gen.writeStringField("phase", value.getPhase());
        }
        if (value.getLink() != null) {
            gen.writeStringField("link", value.getLink());
        }
        if (value.getVariables() != null) {
            gen.writeObjectField("variables", value.getVariables());
        }
        if (value.getCriteria() != null) {
            gen.writeObjectField("criteria", value.getCriteria());
        }
        if (value.getPatientFilter() != null) {
            gen.writeObjectField("patientsFilter", value.getPatientFilter());
        }
        gen.writeEndObject();
    }
}