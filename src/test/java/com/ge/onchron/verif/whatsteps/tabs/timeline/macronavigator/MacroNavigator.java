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
package com.ge.onchron.verif.whatsteps.tabs.timeline.macronavigator;

import java.util.List;
import java.util.stream.Collectors;

import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;

import com.ge.onchron.verif.howsteps.MacroNavigatorSteps;
import com.ge.onchron.verif.model.MacroNavigatorEvent;
import com.ge.onchron.verif.model.MacroNavigatorSwimlane;
import net.thucydides.core.annotations.Steps;

public class MacroNavigator {

    @Steps
    private MacroNavigatorSteps macroNavigatorSteps;
    private final TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();


    @Given( "the macro-navigator $isVisible displayed" )
    @Then( "the macro-navigator $isVisible displayed" )
    public void checkMacroNavigatorVisibility( Boolean isVisible ) {
        testDefinitionSteps.addStep("Check macro-navigator visibility");
        macroNavigatorSteps.checkMacroNavigatorVisibility( isVisible );
        testDefinitionSteps.logEvidence(STR."Macro-navigator is \{isVisible ? "" : "not"} visible",
                STR."Macro-navigator was \{isVisible ? "" : "not"} visible (see previous logs)", true);
    }

    @Given( "the \"$swimlaneName\" swimlane on macro-navigator contains the below event points: $macroNavEvents" )
    @Then( "the \"$swimlaneName\" swimlane on macro-navigator contains the below event points: $macroNavEvents" )
    public void checkMacroNavigatorEvents( String swimlaneName, List<MacroNavigatorEvent> macroNavEvents ) {
        testDefinitionSteps.addStep(STR."Check event points on \{swimlaneName} swimlane");
        macroNavigatorSteps.checkMacroNavigatorEvents( swimlaneName, macroNavEvents );
        macroNavEvents.forEach(e -> testDefinitionSteps.logEvidence(STR."\{swimlaneName} swimlane contains event \{e}",
                STR."Event is present on \{swimlaneName} swimlane (see previous logs)", true, false));
        testDefinitionSteps.logEvidence("All above events are present on the swimlanes", "All events were found (See previous logs)", true);
    }

    @Given( "the swimlanes are available on the macro-navigator in the following order: $swimlanes" )
    @Then( "the swimlanes are available on the macro-navigator in the following order: $swimlanes" )
    public void checkSwimlanesOnMacroNavigator( List<MacroNavigatorSwimlane> swimlanes ) {
        testDefinitionSteps.addStep("Check swimlanes order on the macro-navigator");
        macroNavigatorSteps.checkSwimlanesOnMacroNavigator( swimlanes );
        testDefinitionSteps.logEvidence(STR."the swimlanes are available on the macro-navigator in the following order:\n" +
                swimlanes.stream().map(MacroNavigatorSwimlane::toString).collect(Collectors.joining("\n")),
                "The swimlanes were present in the expected order (see previous logs)", true);
    }

}
