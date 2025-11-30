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
package com.ge.onchron.verif.whatsteps.popups.labels;

import com.ge.onchron.verif.howsteps.*;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.When;

import com.ge.onchron.verif.model.StringList;
import net.thucydides.core.annotations.Steps;

public class LabelApi {

    private TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Steps
    private LabelApiSteps labelApiSteps;

    @Given( "[API] the following labels are created: $labelNames" )
    @When( "[API] the user creates the following labels: $labelNames" )
    public void createLabels( StringList labelNames ) {
        testDefinitionSteps.addStep( STR."Call API to create the following labels:\n\{labelNames.getList()}" );
        labelApiSteps.createLabels( labelNames.getList() );
        testDefinitionSteps.logEvidence(STR."The following labels were created:\n\{labelNames.getList()}",
            STR."\{labelNames} were created",
            true);
    }

}
