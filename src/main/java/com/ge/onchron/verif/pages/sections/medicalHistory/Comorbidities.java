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
package com.ge.onchron.verif.pages.sections.medicalHistory;

import org.openqa.selenium.support.PageFactory;

import com.ge.onchron.verif.pages.sections.DetailedPatientData;
import com.ge.onchron.verif.pages.tabs.MedicalHistory;

import static com.ge.onchron.verif.model.enums.MedicalHistorySection.COMORBIDITIES;

public class Comorbidities extends MedicalHistory {

    public DetailedPatientData getDetailedPatientData() {
        return PageFactory.initElements( getMedicalHistorySection( COMORBIDITIES ), DetailedPatientData.class );
    }

}
