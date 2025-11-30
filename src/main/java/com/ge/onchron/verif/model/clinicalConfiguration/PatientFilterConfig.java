package com.ge.onchron.verif.model.clinicalConfiguration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PatientFilterConfig {

    private Filter filter;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class Filter {
        private List<Must> must;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class Must {
        @JsonProperty("attributes.gender")
        private GenderCondition attributesGender;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class GenderCondition {
        private String eq;
    }

}
