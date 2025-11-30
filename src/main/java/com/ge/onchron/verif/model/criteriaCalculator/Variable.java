package com.ge.onchron.verif.model.criteriaCalculator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Variable {
    public String id;
    public Definition definition;
    public String display;
    public String value;

    //This boolean parameter is for exter
    public boolean usingAi;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable that = (Variable) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(definition, that.definition) &&
                Objects.equals(display, that.display) &&
                Objects.equals(value, that.value);
    }
}
