package com.ge.onchron.verif.model.criteriaCalculator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties
@Data
public class CriteriaCalculatorResponse {
    private List<CriteriaCalculatorResult> results;
}
