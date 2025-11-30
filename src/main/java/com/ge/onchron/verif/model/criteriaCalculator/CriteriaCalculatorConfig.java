package com.ge.onchron.verif.model.criteriaCalculator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.cucumber.core.internal.com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CriteriaCalculatorConfig {
    public String id;
    public String type;
    public Metadata metadata;
    public ArrayList<Variable> variables;
    public ArrayList<Output> outputs;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String constants;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CriteriaCalculatorConfig that = (CriteriaCalculatorConfig) o;
        return Objects.equals(getType(), that.getType()) &&
                Objects.equals(getMetadata(), that.getMetadata()) &&
                Objects.equals(getVariables(), that.getVariables()) &&
                Objects.equals(getOutputs(), that.getOutputs());
    }
}
