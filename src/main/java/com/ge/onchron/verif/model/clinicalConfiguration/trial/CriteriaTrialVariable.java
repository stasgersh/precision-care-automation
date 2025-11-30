package com.ge.onchron.verif.model.clinicalConfiguration.trial;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ge.onchron.verif.model.criteriaCalculator.Definition;
import io.cucumber.core.internal.com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CriteriaTrialVariable {

    public String id;
    public Definition definition;
    public String display;
    public String value;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Boolean ai;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        com.ge.onchron.verif.model.criteriaCalculator.Variable that = (com.ge.onchron.verif.model.criteriaCalculator.Variable) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(definition, that.definition) &&
                Objects.equals(display, that.display) &&
                Objects.equals(value, that.value);
    }
}
