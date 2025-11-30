package com.ge.onchron.verif.model.clinicalConfiguration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CriteriaConfig {
    public String id;
    public String type;
    public String title;
    public Boolean missing;
    public List<String> allowMissingVariables;
    public List<String> dependency;
    public String expression;
    public ArrayList<SubCriteria> subCriteria;


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class Explainability {
        public String displayValue;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class SubCriteria {
        public String id;
        public String title;
        public String description;
        public String expression;
        public Explainability explainability;
    }
}
