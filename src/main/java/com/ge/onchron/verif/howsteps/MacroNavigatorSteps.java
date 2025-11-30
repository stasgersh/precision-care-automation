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
 * forbidden except by express written permission of GE HealthCare.
 */
package com.ge.onchron.verif.howsteps;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import com.ge.onchron.verif.model.MacroNavigatorEvent;
import com.ge.onchron.verif.model.MacroNavigatorSwimlane;
import com.ge.onchron.verif.pages.sections.MacroNavigator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MacroNavigatorSteps {

    private MacroNavigator macroNavigator;
    private static final Logger LOGGER = LoggerFactory.getLogger( MacroNavigatorSteps.class );

    public void checkMacroNavigatorVisibility( Boolean isVisible ) {
        boolean observedVisibility = macroNavigator.isVisible();
        LOGGER.info(STR."Macro navigator visibility: \{observedVisibility}");
        assertThat( "Macro-navigator visibility is not ok.", observedVisibility, is( equalTo( isVisible ) ) );
    }

    public void checkMacroNavigatorEvents( String swimlaneName, List<MacroNavigatorEvent> expectedMacroNavEvents ) {
        macroNavigator.waitUntilLoadingSkeletonDisappears();
        List<MacroNavigatorEvent> observedMacroNavEvents = macroNavigator.getEventsFromSwimlaneOnMacroNavigator( swimlaneName );
        LOGGER.info(STR."Observed events:\\n\{observedMacroNavEvents.stream().map(MacroNavigatorEvent::toString).collect(Collectors.joining("\\n"))}");
        assertEquals( String.format( "Expected and observed macro-nav events do not match on %s swimlane", swimlaneName ), expectedMacroNavEvents, observedMacroNavEvents );
    }

    public void checkSwimlanesOnMacroNavigator( List<MacroNavigatorSwimlane> expectedSwimlanes ) {
        macroNavigator.waitUntilLoadingSkeletonDisappears();
        List<MacroNavigatorSwimlane> observedSwimlanes = macroNavigator.getSwimlanes();
        LOGGER.info(STR."Observed swimlanes:\\n\{observedSwimlanes.stream().map(MacroNavigatorSwimlane::toString).collect(Collectors.joining("\\n"))}");
        assertEquals( "Expected and observed macro-nav swimlanes do not match", expectedSwimlanes, observedSwimlanes );
    }

}
