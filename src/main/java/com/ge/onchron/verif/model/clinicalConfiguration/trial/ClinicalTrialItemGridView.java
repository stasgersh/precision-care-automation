package com.ge.onchron.verif.model.clinicalConfiguration.trial;

import com.ge.onchron.verif.model.clinicalConfiguration.CriteriaTableHeaderItem;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ClinicalTrialItemGridView {

    private TrialItem trialItem;
    private List<CriteriaTableHeaderItem> criteriaTableHeaderItems;
    private List<ClinicalCriteriaItem> clinicalCriteriaItems;

}
