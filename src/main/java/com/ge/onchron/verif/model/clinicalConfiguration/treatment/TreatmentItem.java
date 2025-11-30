package com.ge.onchron.verif.model.clinicalConfiguration.treatment;

import com.ge.onchron.verif.model.clinicalConfiguration.AIIndicator;
import com.ge.onchron.verif.model.clinicalConfiguration.trial.ClinicalCriteriaItem;
import com.ge.onchron.verif.model.clinicalConfiguration.OverallMatchingScore;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class TreatmentItem {

    private String treatmentID;
    private String treatmentName;
    private String treatmentMetadata;
    private OverallMatchingScore overallMatchingScore;
    private String description;
    private AIIndicator aiIndicator;
    private List<ClinicalCriteriaItem> clinicalCriteriaItems;

    public boolean isEmptyTrialsFields() {
        return  treatmentName.isEmpty() && description.isEmpty() && overallMatchingScore.name().isEmpty();
    }

}
