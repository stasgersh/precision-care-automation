package com.ge.onchron.verif.model.clinicalConfiguration.trial;

import com.ge.onchron.verif.model.clinicalConfiguration.CriteriaStatus;
import com.ge.onchron.verif.model.clinicalConfiguration.CriteriaType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClinicalCriteriaItem {

    private final String criteriaDescription;
    private final CriteriaStatus criteriaStatus;
    private final CriteriaType criteriaType;
    private final String eligibilityValue;

}
