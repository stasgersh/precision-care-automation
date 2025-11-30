/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2023, 2023, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.howsteps.detailedPatientData.medicalHistory;

import com.ge.onchron.verif.howsteps.detailedPatientData.DetailedPatientDataSteps;
import com.ge.onchron.verif.pages.sections.DetailedPatientData;
import com.ge.onchron.verif.pages.sections.medicalHistory.Comorbidities;

public class ComorbiditiesSteps extends DetailedPatientDataSteps {

    private Comorbidities comorbiditiesSection;

    @Override
    public DetailedPatientData getDetailedPatientData() {
        comorbiditiesSection.waitUntilLoadingSkeletonDisappears();
        return comorbiditiesSection.getDetailedPatientData();
    }

    @Override
    public void waitUntilLoadingSkeletonDisappears() {
        comorbiditiesSection.waitUntilLoadingSkeletonDisappears();
    }

}
