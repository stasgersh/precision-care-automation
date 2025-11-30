/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */
package com.ge.onchron.verif.whatsteps.api;

import org.jbehave.core.annotations.Given;

import com.ge.onchron.verif.howsteps.AdherenceSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import net.thucydides.core.annotations.Steps;

public class Adherence {

    @Steps
    private AdherenceSteps adherenceSteps;

    private TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Given( "[API] the following treatment protocol was uploaded: $resourceFilePath" )
    public void uploadTreatment( String resourceFilePath ) {
        testDefinitionSteps.addStep( STR."Uploading treatment protocol: \{resourceFilePath}" );
        adherenceSteps.uploadTreatment( resourceFilePath );
        testDefinitionSteps.logEvidence( STR."The following treatment protocol was uploaded: \{resourceFilePath}", STR."The following treatment protocol was uploaded: \\{resourceFilePath}", true );
    }
}
