package com.ge.onchron.verif.model.clinicalConfiguration;

public enum CriteriaStatus {

    INCLUSION_MET("Inclusion met"),
    INCLUSION_NOT_MET("Inclusion not met"),
    EXCLUSION_MET("Exclusion met"),
    EXCLUSION_NOT_MET("Exclusion not met"),
    MISSING("Missing"),
    IN_PROGRESS("In progress"),
    EMPTY("Empty");

    private final String clinicalCriteriaOverallMatchingStatus;

    CriteriaStatus(String clinicalCriteriaOverallMatchingStatus) {
        this.clinicalCriteriaOverallMatchingStatus = clinicalCriteriaOverallMatchingStatus;
    }

    public static CriteriaStatus fromString(String text) {
        for (CriteriaStatus matchingScore : CriteriaStatus.values()) {
            if (matchingScore.clinicalCriteriaOverallMatchingStatus.equalsIgnoreCase(text)) {
                return matchingScore;
            }
        }
        // Handle case where no matching enum constant is found
        return null; // or
    }
}
