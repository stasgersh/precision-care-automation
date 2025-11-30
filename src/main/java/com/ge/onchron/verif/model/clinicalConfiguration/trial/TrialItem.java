package com.ge.onchron.verif.model.clinicalConfiguration.trial;

import com.ge.onchron.verif.model.clinicalConfiguration.AIIndicator;
import com.ge.onchron.verif.model.clinicalConfiguration.OverallMatchingScore;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class TrialItem {

    private String trialName;
    private OverallMatchingScore overallMatchingScore;
    private ClinicalTrialItemMetadata clinicalTrialItemMetadata;
    private String description;
    private AIIndicator aiIndicator;
    private List<ClinicalCriteriaItem> clinicalCriteriaItems;

    public boolean isEmptyTrialsFields() {
        return trialName.isEmpty() && description.isEmpty() &&
                clinicalTrialItemMetadata.getConfigId().isEmpty() &&
                clinicalTrialItemMetadata.getPhase().isEmpty() &&
                clinicalTrialItemMetadata.getRecruitmentStatus().getStatus().isEmpty() &&
                overallMatchingScore.name().isEmpty();
    }
}
