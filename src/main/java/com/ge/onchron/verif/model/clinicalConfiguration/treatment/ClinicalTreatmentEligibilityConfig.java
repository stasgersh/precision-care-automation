package com.ge.onchron.verif.model.clinicalConfiguration.treatment;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ge.onchron.verif.model.clinicalConfiguration.CriteriaConfig;
import com.ge.onchron.verif.model.clinicalConfiguration.PatientFilterConfig;
import com.ge.onchron.verif.model.criteriaCalculator.Variable;
import lombok.*;

import java.util.List;

@Data
@Builder
@JsonSerialize(using = ClinicalTreatmentEligibilityConfigSerializer.class)
public class ClinicalTreatmentEligibilityConfig {

    private String id;
    private String type;
    private String status;
    private String studyCompletion;
    private String name;
    private String description;
    private String phase;
    private String link;

    private PatientFilterConfig patientFilter;
    private List<Variable> variables;
    private List<CriteriaConfig> criteria;

}
