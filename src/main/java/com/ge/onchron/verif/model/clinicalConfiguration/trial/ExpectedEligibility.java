package com.ge.onchron.verif.model.clinicalConfiguration.trial;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class ExpectedEligibility {
        private final String eligibilityKey;
        private final String eligibilityValueContent;
        private final List<String> keywords;
    }
