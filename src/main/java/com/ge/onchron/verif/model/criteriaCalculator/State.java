package com.ge.onchron.verif.model.criteriaCalculator;

import lombok.ToString;

import java.util.ArrayList;
import java.util.Objects;

@ToString
public class State {
    public String id;
    public ArrayList<Item> items;
    public String expression;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State that = (State) o;
        return Objects.equals(items, that.items) && Objects.equals(expression, that.expression);
    }
}
