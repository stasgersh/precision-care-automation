package com.ge.onchron.verif.model.summaryCard.summaryCardItemData;


import lombok.*;
import org.openqa.selenium.support.Color;


@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SummaryCardItemDataColor extends SummaryCardItemData {

    private final Color color;

    @Override
    public Object getData() {
        return color;
    }

}
