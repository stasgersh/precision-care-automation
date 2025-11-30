package com.ge.onchron.verif.model.summaryCard.summaryCardItemData;

import com.ge.onchron.verif.model.clinicalConfiguration.RowBadgeItem;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SummaryCardItemDataTrial extends SummaryCardItemData {

    public String trialDescription;
    public RowBadgeItem rowBadgeItem;

    @Override
    public Object getData() {
        return trialDescription + rowBadgeItem;
    }


}
