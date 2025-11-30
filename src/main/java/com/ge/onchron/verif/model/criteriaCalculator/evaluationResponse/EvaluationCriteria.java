package com.ge.onchron.verif.model.criteriaCalculator.evaluationResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class EvaluationCriteria {
    public String id;
    public String display;
    public String expression;
    public String value;
}
