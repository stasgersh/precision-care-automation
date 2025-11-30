package com.ge.onchron.verif.model.clinicalConfiguration;

import lombok.*;

@Getter
@ToString
public enum OverallMatchingScore {

    COMPLETE_MATCH("Complete match"),
    STRONG_MATCH("Strong match"),
    PROBABLE_MATCH("Probable match"),
    LIKELY_NO_MATCH("Likely no match"),
    NO_MATCH("No match"),
    UNCERTAIN("Uncertain"),
    PENDING("Pending");

    private String matchingScore;

    OverallMatchingScore(String matchingScore) {
        this.matchingScore = matchingScore;
    }

    public static OverallMatchingScore fromString(String text) {
        for (OverallMatchingScore score : OverallMatchingScore.values()) {
            if (score.matchingScore.equalsIgnoreCase(text)) {
                return score;
            }
        }
        // Handle case where no matching enum constant is found
        return null; // or
    }
}
