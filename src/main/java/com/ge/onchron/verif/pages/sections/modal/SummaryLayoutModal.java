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
package com.ge.onchron.verif.pages.sections.modal;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.fail;

import com.ge.onchron.verif.model.summaryCard.SummaryGroup;
import com.ge.onchron.verif.pages.SimplePage;
import com.ge.onchron.verif.pages.elements.SummaryGroupElement;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import static com.ge.onchron.verif.TestSystemParameters.USER_SETTINGS_CHANGED;
import static com.ge.onchron.verif.pages.utils.DragAndDropUtils.dragAndDropToElement;
import static com.ge.onchron.verif.pages.utils.PageUtils.currentlyContainsElements;
import static net.serenitybdd.core.Serenity.setSessionVariable;

public class SummaryLayoutModal extends SimplePage {

    private final String SUMMARY_LAYOUT_MODAL_SELECTOR = "[class*='SummaryLayoutModal-module_summary-layout-modal']";
    private final String COLUMNS_CONTAINER_SELECTOR = "[class*='SummaryLayoutModal-module_columns']";
    private final String COLUMN_HEADER_SELECTOR = "[class*='SummaryLayoutModal-module_column__header']";
    private final String FOOTER_SELECTOR = "[class*='SummaryLayoutModal-module_modal-content__footer']";
    private final String DRAGGABLE_GROUP_SELECTOR = "[class*='SummaryLayoutModal-module_drag-drop-list__item']";

    private static final int MAX_TIME_TO_DISAPPEAR_MODAL_IN_MILLIS = 60000;

    @FindBy( css = SUMMARY_LAYOUT_MODAL_SELECTOR )
    private WebElementFacade summaryLayoutModal;

    @FindBy( css = COLUMNS_CONTAINER_SELECTOR )
    private WebElementFacade columnsContainer;

    @FindBy( css = FOOTER_SELECTOR )
    private WebElementFacade footer;

    public boolean isCurrentlyVisible() {
        return summaryLayoutModal.isCurrentlyVisible();
    }

    public List<SummaryGroup> getDraggableGroups() {
        List<SummaryGroupElement> groupsElements = getDraggableGroupsElements();
        return groupsElements.stream().map( SummaryGroupElement::getSummaryGroup ).collect( Collectors.toList() );
    }

    public List<SummaryGroupElement> getDraggableGroupsElements() {
        List<WebElementFacade> columns = getColumns();
        List<SummaryGroupElement> groups = new ArrayList<>();
        for ( int columnIndex = 0; columnIndex < columns.size(); columnIndex++ ) {
            WebElementFacade column = columns.get( columnIndex );
            List<WebElementFacade> groupsElements = column.thenFindAll( By.cssSelector( DRAGGABLE_GROUP_SELECTOR ) );

            for ( int groupIndex = 0; groupIndex < groupsElements.size(); groupIndex++ ) {
                WebElementFacade groupWebElement = groupsElements.get( groupIndex );
                List<WebElementFacade> groupSubElements = groupWebElement.thenFindAll( By.xpath( "child::*" ) );
                String name = groupSubElements.get( 0 ).getText();
                boolean isHidden = groupSubElements.get( 1 ).getText().equals( "Show" );
                SummaryGroup group = new SummaryGroup( name, columnIndex, groupIndex );
                group.setHidden( isHidden );
                SummaryGroupElement groupElement = new SummaryGroupElement( group, groupWebElement );
                groups.add( groupElement );
            }
        }
        return groups;
    }

    public List<WebElementFacade> getColumns() {
        return columnsContainer.thenFindAll( By.xpath( "child::*" ) );
    }

    public List<String> getColumnNames() {
        List<String> columnNames = new ArrayList<>();
        getColumns().forEach( column -> {
            String name = column.then( By.cssSelector( COLUMN_HEADER_SELECTOR ) ).getText();
            columnNames.add( name );
        } );
        return columnNames;
    }

    public void setGroups( List<SummaryGroup> requiredGroups ) {
        // Sort groups based on column index and then based on index inside the column
        requiredGroups.sort( Comparator
                .comparing( SummaryGroup::getColumnIndex )
                .thenComparing( SummaryGroup::getGroupIndexInColumn )
        );

        for ( int i = requiredGroups.size() - 1; i >= 0; i-- ) {    // Start from the last item
            SummaryGroup requiredGroup = requiredGroups.get( i );

            // Need to read these elements in every iteration
            List<WebElementFacade> columns = getColumns();
            List<SummaryGroupElement> summaryGroupElements = getDraggableGroupsElements();

            SummaryGroupElement foundGroup = summaryGroupElements.stream()
                                                                 .filter( summaryGroupElement ->
                                                                         summaryGroupElement.getSummaryGroup().getName().equals( requiredGroup.getName() ) )
                                                                 .findFirst()
                                                                 .orElseThrow( () -> new RuntimeException(
                                                                         String.format( "Group was not found on Summary order and visibility modal: %s", requiredGroup.getName() ) ) );

            // Set group visibility
            List<WebElementFacade> foundGroupSubItems = foundGroup.getGroupElement().thenFindAll( By.xpath( "child::*" ) );
            boolean isHidden = foundGroupSubItems.get( 1 ).getText().equals( "Show" );

            if ( requiredGroup.isHidden() != isHidden ) {
                elementClicker( foundGroupSubItems.get( 1 ) );
            }

            //  Move group
            int columnIndex = requiredGroup.getColumnIndex();
            WebElementFacade moveToThisElement;
            if ( !currentlyContainsElements( columns.get( columnIndex ), By.cssSelector( DRAGGABLE_GROUP_SELECTOR ) ) ) {
                moveToThisElement = columns.get( columnIndex );
            } else {
                WebElementFacade column = columns.get( columnIndex );
                List<WebElementFacade> groupsElements = column.thenFindAll( By.cssSelector( DRAGGABLE_GROUP_SELECTOR ) );
                moveToThisElement = groupsElements.getFirst();
            }
            dragAndDropToElement( foundGroup.getGroupElement(), moveToThisElement );
        }
    }

    public void clickOnButton( String buttonText ) {
        List<WebElementFacade> buttons = footer.thenFindAll( By.tagName( "button" ) );
        WebElementFacade button = buttons.stream()
                                         .filter( btn -> btn.getText().equals( buttonText ) )
                                         .findFirst()
                                         .orElseThrow( () -> new RuntimeException( String.format( "Button was not found on Summary order and visibility modal: %s", buttonText ) ) );
        if ( buttonText.equals( "Save" ) ) {
            setSessionVariable( USER_SETTINGS_CHANGED ).to( true );
        }
        elementClicker( button );
    }

    public void waitForDisappearModal() {
        WebDriverWait wait = new WebDriverWait( getDriver(), Duration.ofMillis( MAX_TIME_TO_DISAPPEAR_MODAL_IN_MILLIS ) );
        try {
            wait.until( ExpectedConditions.invisibilityOfElementLocated( By.cssSelector( SUMMARY_LAYOUT_MODAL_SELECTOR ) ) );
        } catch ( TimeoutException e ) {
            fail( String.format( "Summary layout modal did not disappear during the waiting period: %s ms", MAX_TIME_TO_DISAPPEAR_MODAL_IN_MILLIS ) );

        }
    }

}
