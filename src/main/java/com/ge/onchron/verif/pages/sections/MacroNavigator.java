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
package com.ge.onchron.verif.pages.sections;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;

import com.ge.onchron.verif.model.MacroNavigatorEvent;
import com.ge.onchron.verif.model.MacroNavigatorSwimlane;
import com.ge.onchron.verif.pages.SimplePage;
import com.ge.onchron.verif.pages.elements.MacroNavigatorSwimlaneElement;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class MacroNavigator extends SimplePage {

    private final String MACRO_NAVIGATOR_CLASS = "TimelineDashboard-module_macro-nav";
    private final String MACRO_NAVIGATOR_DETAILS_CONTAINER_CLASS = "MacroNavigator-module_container";
    private final String MACRO_NAVIGATOR_EVENTS_SELECTOR = "[class*='MacroNavigator-module_marker']:not([class*='halo'])";
    private final String MACRO_NAVIGATOR_EVENTS_HALO_SELECTOR = "[class*='MacroNavigator-module_marker-halo']";
    private final String MACRO_NAVIGATOR_TIMELINE_AXIS_SELECTOR = "[class*='MacroNavigator-module_first']";
    private final String MACRO_NAVIGATOR_TIMEBOX_CLASS = "MacroNavigator-module_timebox-wrap";
    private final String MACRO_NAVIGATOR_SWIMLANE_NAME_SELECTOR = "[class*='MacroNavigator-module_headcol']";
    private final String MACRO_NAVIGATOR_SWIMLANE_LANE_SELECTOR = "[class*='MacroNavigator-module_lane']";
    private final String MACRO_NAVIGATOR_SKELETON_LOADER_SELECTOR = "[class*='SkeletonLoader-module_container']";

    @FindBy( css = "[class*='" + MACRO_NAVIGATOR_CLASS + "']" )
    private WebElementFacade macroNavigator;

    @FindBy( css = "[class*='" + MACRO_NAVIGATOR_DETAILS_CONTAINER_CLASS + "']" )
    private WebElementFacade macroNavigatorDetailsContainer;

    public boolean isVisible() {
        return macroNavigator.isVisible();
    }

    public void waitUntilLoadingSkeletonDisappears() {
        waitUntilLoadingSkeletonDisappears( macroNavigatorDetailsContainer, By.cssSelector( MACRO_NAVIGATOR_SKELETON_LOADER_SELECTOR ) );
    }

    public List<MacroNavigatorSwimlane> getSwimlanes() {
        List<MacroNavigatorSwimlaneElement> swimlaneElements = getSwimlaneWebelements();
        return swimlaneElements.stream()
                               .map( MacroNavigatorSwimlaneElement::getSwimlane )
                               .collect( Collectors.toList() );
    }

    public List<MacroNavigatorEvent> getEventsFromSwimlaneOnMacroNavigator( String requiredSwimlaneName ) {
        MacroNavigatorSwimlaneElement macroNavigatorSwimlane = getSwimlaneWebelements()
                .stream()
                .filter( swimlaneElement -> swimlaneElement.getSwimlane().getName().equals( requiredSwimlaneName ) )
                .findFirst()
                .orElseThrow( () -> new RuntimeException( "Swimlane on macro-navigator was not found: " + requiredSwimlaneName ) );

        List<WebElementFacade> eventWebelements = macroNavigatorSwimlane.getLane().thenFindAll( By.cssSelector( MACRO_NAVIGATOR_EVENTS_SELECTOR ) );
        List<WebElementFacade> eventHaloWebelements = macroNavigatorSwimlane.getLane().thenFindAll( By.cssSelector( MACRO_NAVIGATOR_EVENTS_HALO_SELECTOR ) );
        List<MacroNavigatorEvent> events = new ArrayList<>();
        for ( int i = 0; i < eventWebelements.size(); i++ ) {
            String actualClass = eventWebelements.get( i ).getAttribute( "class" );
            MacroNavigatorEvent event = new MacroNavigatorEvent( i );
            boolean isFiltered = !actualClass.contains( "visible" );
            event.isFiltered( isFiltered );
            boolean isSelected = eventHaloWebelements.get( i ).getAttribute( "class" ).contains( "selected" );
            event.setSelected( isSelected );
            events.add( event );
        }
        return events;

    }

    private List<MacroNavigatorSwimlaneElement> getSwimlaneWebelements() {
        List<WebElementFacade> macroNavElements = macroNavigatorDetailsContainer.thenFindAll( By.cssSelector( "[class*='flex']" ) );
        List<MacroNavigatorSwimlaneElement> macroNavigatorSwimlanes = new ArrayList<>();

        macroNavElements.removeFirst(); // Removing first lane of the macronavigator ("hide navigator" and macro-nav timeline axis)
        macroNavElements.forEach( macroNavElement -> {
            String swimlaneName = macroNavElement.then( By.cssSelector( MACRO_NAVIGATOR_SWIMLANE_NAME_SELECTOR ) ).getText();
            boolean isFiltered = macroNavElement.getAttribute( "class" ).contains( "deselected" );
            MacroNavigatorSwimlane swimlane = new MacroNavigatorSwimlane( swimlaneName, isFiltered );
            WebElementFacade lane = macroNavElement.then( By.cssSelector( MACRO_NAVIGATOR_SWIMLANE_LANE_SELECTOR ) );
            macroNavigatorSwimlanes.add( new MacroNavigatorSwimlaneElement( swimlane, lane ) );
        } );
        return macroNavigatorSwimlanes;
    }

}
