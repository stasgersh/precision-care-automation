package com.ge.onchron.verif.model.criteriaCalculator.evaluationResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class EvaluationResponse {
    public String id;
    public Long timestamp;
    public Long patient_latest_version;
    public com.ge.onchron.verif.model.criteriaCalculator.Metadata metadata;
    public String patient_id;
    public Map<String, EvaluationOutput> attributes;
}
