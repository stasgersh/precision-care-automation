package com.ge.onchron.verif.model.clinicalConfiguration.treatment;

import com.ge.onchron.verif.model.clinicalConfiguration.trial.ClinicalCriteriaItem;
import com.ge.onchron.verif.model.clinicalConfiguration.CriteriaTableHeaderItem;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ClinicalTreatmentItemGridView {

    private TreatmentItem treatmentItem;
    private List<CriteriaTableHeaderItem> criteriaTableHeaderItems;
    private List<ClinicalCriteriaItem> clinicalCriteriaItems;

}
