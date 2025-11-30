package com.ge.onchron.verif.howsteps.detailedPatientData.medicalHistory;

import com.ge.onchron.verif.howsteps.detailedPatientData.DetailedPatientDataSteps;
import com.ge.onchron.verif.pages.sections.DetailedPatientData;
import com.ge.onchron.verif.pages.sections.modal.LabHistoryModal;


public class LabHistorySteps extends DetailedPatientDataSteps {

    LabHistoryModal labHistoryModal;
    
    @Override
    protected DetailedPatientData getDetailedPatientData() {
        return labHistoryModal.getDetailedPatientData();
    }

    @Override
    protected void waitUntilLoadingSkeletonDisappears() {
        labHistoryModal.waitUntilLoadingSkeletonDisappears();
    }
}
