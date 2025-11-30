/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE HealthCare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE HealthCare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.whatsteps.popups;

import org.jbehave.core.annotations.Then;

import com.ge.onchron.verif.TooltipSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.TestSystemParameters.TEST_DATA_CLASSPATH;
import static com.ge.onchron.verif.utils.FileUtils.isFilePresent;
import static com.ge.onchron.verif.utils.FileUtils.readFromFile;

public class Tooltip {

    @Steps
    private TooltipSteps tooltipSteps;

    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Then( "tooltip is displayed with text: $rawText" )
    public void checkTooltipText( String rawText ) {
        String text = isFilePresent( TEST_DATA_CLASSPATH + rawText ) ? readFromFile( TEST_DATA_CLASSPATH + rawText ) : rawText;
        testDefinitionSteps.addStep( String.format( "Check that tooltip is displayed with text: %s", text ) );
        tooltipSteps.checkTooltipText( text );
        testDefinitionSteps.logEvidence(
                String.format( "Tooltip is displayed with text: %s", text ),
                String.format( "Tooltip is displayed with text: %s", text ), true );
    }

}
