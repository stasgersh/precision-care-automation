package com.ge.onchron.verif.model;

import lombok.Data;

@Data
public class FilterCondition {

        private final String attributeName;
        private final String type;
        private final String condition;
        private final String value;
}
