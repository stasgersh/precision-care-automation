/*
 * -GE CONFIDENTIAL-
 * Type: Source Code
 *
 * Copyright (c) 2024, 2024, GE Healthcare
 * All Rights Reserved
 *
 * This unpublished material is proprietary to GE Healthcare. The methods and
 * techniques described herein are considered trade secrets and/or
 * confidential. Reproduction or distribution, in whole or in part, is
 * forbidden except by express written permission of GE Healthcare.
 */

package com.ge.onchron.verif.whatsteps.clp;

import java.util.List;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.ge.onchron.verif.howsteps.AITooltipSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.model.AITooltipSection;
import net.thucydides.core.annotations.Steps;

public class AITooltip {

    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Steps
    private AITooltipSteps AIsteps;

    @Then( "the AI tooltip is displayed with \"$AIDisclaimerText\" disclaimer text" )
    public void checkDisplayedAIDisclaimer( String AIDisclaimerText ) {
        testDefinitionSteps.addStep( STR."Check AI tooltip is displayed with disclaimer text" );
        AIsteps.checkDisplayedAIDisclaimer( AIDisclaimerText );
        testDefinitionSteps.logEvidence( "The AI tooltip was displayed with AI disclaimer text",
                "The AI tooltip was successfully displayed with AI disclaimer text", true );
    }

    @Then( "the AI tooltip displays the following text: \"$fullText\"" )
    public void checkTruncatedBannerTooltipText( String fullText ) {
        testDefinitionSteps.addStep( STR."Check that the AI tooltip on banner is displayed with the following text: \"\{fullText}\"" );
        AIsteps.checkTruncatedBannerTooltipText( fullText );
        testDefinitionSteps.logEvidence(
                STR."the AI tooltip on banner displays the following text: \"\{fullText}\"",
                STR."the AI tooltip on banner displayed the following text: \"\{fullText}\"", true );
    }

    @When( "the user clicks on the title of the following root resource item on the AI tooltip: $aiTooltipSection" )
    public void clickOnTitleOnAITooltipSection( AITooltipSection AITooltipSection ) {
        testDefinitionSteps.addStep( String.format( "the user clicks on the title of the following root resource item on the AI tooltip: %s", AITooltipSection ) );
        AIsteps.clickOnTitleOnAITooltipSection( AITooltipSection );
        testDefinitionSteps.logEvidence(
                String.format( "the user clicked on the title of the following root resource item on the AI tooltip: %s", AITooltipSection ),
                String.format( "the user clicked on the title of the following root resource item on the AI tooltip: %s", AITooltipSection ), true );
    }

    @Then( "the AI tooltip contains the following root resource items: $aiTooltipSections" )
    public void checkAITooltipSections( List<AITooltipSection> AITooltipSections ) {
        testDefinitionSteps.addStep( String.format( "Check that the AI tooltip contains the following root resource items: %s", AITooltipSections ) );
        AIsteps.checkAITooltipSections( AITooltipSections );
        testDefinitionSteps.logEvidence(
                String.format( "the AI tooltip contains the following root resource items: %s", AITooltipSections ),
                String.format( "the AI tooltip contains the following root resource items: %s", AITooltipSections ), true );
    }
}
