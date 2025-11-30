package com.ge.onchron.verif.model.clinicalConfiguration;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class ExpectedCriteria {
    private final String eligibilityKey;
    private final List<String> keywords;

}
