package com.ge.onchron.verif.model.criteriaCalculator.evaluationResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ge.onchron.verif.model.criteriaCalculator.Metadata;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class EvaluationOutput {
    public String value;
    public String display;
    public String expression;
    public Metadata metadata;
    public ArrayList<EvaluationCriteria> criteria;
}
