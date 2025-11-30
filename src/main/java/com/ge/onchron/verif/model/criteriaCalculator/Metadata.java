package com.ge.onchron.verif.model.criteriaCalculator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.cucumber.core.internal.com.fasterxml.jackson.annotation.JsonInclude;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Metadata {
    public String name;
    public String description;
    public String status;
    public String phase;
    public String link;
    public Boolean usingAi;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    //public String expressionRelation;
    public String type;
    public List<String> dependency;


    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Metadata that = (Metadata) o;
        return Objects.equals(name, that.name)
                && Objects.equals(description, that.description)
                && Objects.equals(phase, that.phase)
                && Objects.equals(status, that.status)
                && Objects.equals(link, that.link)
                && Objects.equals(usingAi, that.usingAi);
    }
}
