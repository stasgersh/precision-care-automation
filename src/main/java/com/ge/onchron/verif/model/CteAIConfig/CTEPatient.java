package com.ge.onchron.verif.model.CteAIConfig;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CTEPatient {
    private String id;

    @JsonSerialize(using = TreatmentListSerializer.class)
    private List<CTETreatment> treatments;
}
