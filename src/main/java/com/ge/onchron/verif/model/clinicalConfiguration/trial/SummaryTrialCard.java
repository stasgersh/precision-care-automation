package com.ge.onchron.verif.model.clinicalConfiguration.trial;

import com.ge.onchron.verif.model.summaryCard.summaryCardItemData.SummaryCardItemData;
import com.ge.onchron.verif.model.summaryCard.summaryCardItemData.SummaryCardItemDataTrial;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SummaryTrialCard extends SummaryCardItemData {

    public SummaryCardItemDataTrial summaryTrialCardItem;

    @Override
    public SummaryCardItemDataTrial getData() {
        return summaryTrialCardItem;

    }


}
