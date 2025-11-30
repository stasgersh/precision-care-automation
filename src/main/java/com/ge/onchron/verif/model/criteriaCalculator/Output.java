package com.ge.onchron.verif.model.criteriaCalculator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.cucumber.core.internal.com.fasterxml.jackson.annotation.JsonInclude;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Output {
    public String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String constants;
    public String type;
    public Metadata metadata;
    public String display;
    public ArrayList<State> states;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Output that = (Output) o;
        return Objects.equals(display, that.display) &&
                Objects.equals(type, that.type) &&
                Objects.equals(metadata, that.metadata) &&
                Objects.equals(states, that.states);
    }
}
