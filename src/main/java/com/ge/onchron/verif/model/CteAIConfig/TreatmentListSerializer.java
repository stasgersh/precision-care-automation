package com.ge.onchron.verif.model.CteAIConfig;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class TreatmentListSerializer extends JsonSerializer<List<CTETreatment>> {

    @Override
    public void serialize(List<CTETreatment> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        for (CTETreatment treatment : value) {
            gen.writeString(treatment.getTreatment());
        }
        gen.writeEndArray();
    }
}
