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
package com.ge.onchron.verif.whatsteps.api;

import org.jbehave.core.annotations.Given;

import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.howsteps.UserSettingsSteps;
import com.ge.onchron.verif.model.UserCredentials;
import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.model.enums.TimelineDirection.NEWEST_ON_LEFT;
import static com.ge.onchron.verif.model.enums.TimelineDirection.NEWEST_ON_RIGHT;

public class UserSettings {

    private TestDefinitionSteps testDefinitionSteps =
            TestDefinitionSteps.getInstance();
    @Steps
    private UserSettingsSteps userSettingsSteps;

    @Given( "[API] the timeline direction is set by the user [$userCredentials] to show the newest event on right" )
    public void setTimelineDirectionToNewestOnRight( UserCredentials userCredentials ) {
        testDefinitionSteps.addStep(
                "set timeline direction to show the newest event on right" );
        userSettingsSteps.setTimelineDirection( userCredentials, NEWEST_ON_RIGHT );
        testDefinitionSteps.logEvidence( STR.
                        "The timeline direction is set by the user \{userCredentials.getAlias()} to show the newest event on right",
                "The timeline direction was set",
                true );

    }

    @Given( "[API] the timeline direction is set by the user [$userCredentials] to show the newest event on left" )
    public void setTimelineDirectionToNewestOnLeft( UserCredentials userCredentials ) {
        testDefinitionSteps.addStep(
                "set timeline direction to show the newest event on left" );
        userSettingsSteps.setTimelineDirection( userCredentials, NEWEST_ON_LEFT );
        testDefinitionSteps.logEvidence( STR.
                        "The timeline direction is set by the user \{userCredentials.getAlias()} to show the newest event on right",
                "The timeline direction was set",
                true );
    }

}
