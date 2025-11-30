/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2022, 2022, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.model.enums;

public enum BadgeType {
    IMPORTANT_EVENT,
    CYCLED_EVENT,   // Kept for future improvements, currently cycle badge is a simple label
    CLP,
    COMMENT,
    LABEL,
    DOSE_CHANGED,
    CLASSIFICATION,
    INFORMATION,
    STATUS_BADGE,
    TRIAL_MATCHING_STATUS_BADGE,
    SYNC_PROGRESS_BADGE
}
