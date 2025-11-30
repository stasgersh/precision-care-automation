package com.ge.onchron.verif.model.clinicalConfiguration;

import com.ge.onchron.verif.model.enums.ArrowDirection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@AllArgsConstructor
public class CriteriaTableHeaderItem {

    private CriteriaItemHeader columnName;
    private boolean usedForSorting;
    private ArrowDirection arrowDirection;

    @Getter
    public enum CriteriaItemHeader {
        ELIGIBILITY_CRITERIA("Eligibility criteria"),
        STATUS("Status"),
        TYPE("Type"),
        ELIGIBILITY_VALUE("Eligibility value(s)");

        private final String criteriaItemHeader;

        CriteriaItemHeader(String criteriaItemHeader) {
            this.criteriaItemHeader = criteriaItemHeader;
        }

        public static CriteriaTableHeaderItem.CriteriaItemHeader fromString(String text) {
            for (CriteriaTableHeaderItem.CriteriaItemHeader criteriaItemHeader : CriteriaTableHeaderItem.CriteriaItemHeader.values()) {
                if (criteriaItemHeader.criteriaItemHeader.equalsIgnoreCase(text)) {
                    return criteriaItemHeader;
                }
            }
            // Handle case where no matching enum constant is found
            return null; // or
        }
    }
}
