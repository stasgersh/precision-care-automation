package com.ge.onchron.verif.model.clinicalConfiguration;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RowBadgeItem {

        public OverallMatchingScore trialOverallMatchingScore;
        public Boolean isAiIconBadgeDisplayed;
    }
