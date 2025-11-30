package com.ge.onchron.verif.model.clinicalConfiguration;

public enum CriteriaType {

    INCLUSION("Inclusion"),
    EXCLUSION("Exclusion"),

    //This is not an existing type, but only for comparison purpose
    EMPTY("Empty") ;


    private final String clinicalCriteriaType;

    CriteriaType(String clinicalCriteriaType) {
        this.clinicalCriteriaType = clinicalCriteriaType;
    }

    public static CriteriaType fromString(String text) {
        for (CriteriaType criteriaType : CriteriaType.values()) {
            if (criteriaType.clinicalCriteriaType.equalsIgnoreCase(text)) {
                return criteriaType;
            }
        }
        // Handle case where no matching enum constant is found
        return null; // or
    }
}
