package com.ge.onchron.verif.model.clinicalConfiguration.trial;

import lombok.*;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class ClinicalTrialItemMetadata {

    private String configId;
    private String phase;
    private RecruitmentStatus recruitmentStatus;
    private LocalDate date;

    public ClinicalTrialItemMetadata(){}

    @Getter
    public enum RecruitmentStatus {
        NOT_YET_RECRUITING("Not yet recruiting"),
        RECRUITING("Recruiting"),
        ENROLLING_BY_INVITATION("Enrolling by invitation"),
        ACTIVE_NOT_RECRUITING("Active, not recruiting"),
        SUSPENDED("Suspended"),
        TERMINATED("Terminated"),
        COMPLETED("Completed"),
        WITHDRAWN("Withdrawn"),
        UNKNOWN("Unknown");

        private final String status;

        RecruitmentStatus(String phaseValue) {
            this.status =phaseValue;
        }
    }

    public static RecruitmentStatus fromString(String text) {
        if (text == null || text.trim().isEmpty()) {
            return RecruitmentStatus.UNKNOWN;
        }

        for (RecruitmentStatus score : RecruitmentStatus.values()) {
            if (score.status.equalsIgnoreCase(text)) {
                return score;
            }
        }
        return null;
    }

}
