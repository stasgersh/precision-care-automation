package com.ge.onchron.verif.model.clinicalConfiguration;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AIIndicator {

    public boolean isOverallMatchingScoreAiIconDisplayed;
    public boolean isDisclaimerIconDisplayed;
    public String aiDisclaimerDescription;
    public boolean isAIHighlighted;
}
