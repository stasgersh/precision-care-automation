package com.ge.onchron.verif.whatsteps.tabs.Response;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.howsteps.TreatmentAndResponseSteps;
import com.ge.onchron.verif.model.EmptyStateMessage;
import com.ge.onchron.verif.model.treatmentAndResponse.StudyCard;
import com.ge.onchron.verif.model.treatmentAndResponse.TreatmentCard;
import java.util.List;
import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.pages.utils.TableUtils.getExampleTableAsList;

public class TreatmentAndResponse {

    private TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Steps
    private TreatmentAndResponseSteps treatmentAndResponseSteps;

    @Given( "the Treatment and response view is loaded" )
    @Then( "the Treatment and response view is loaded" )
    public void waitUntilTRVLoaded() {
        testDefinitionSteps.addStep( "Check Treatment and response view" );
        treatmentAndResponseSteps.waitUntilResponseViewLoaded();
        testDefinitionSteps.logEvidence( "the Treatment and response view is loaded", "the Treatment and response view is loaded", true );
    }

    @Given( "the \"$cardTitle\" treatment card contains the following data: $treatmentCardDetails" )
    @Then( "the \"$cardTitle\" treatment card contains the following data: $treatmentCardDetails" )
    public void checkCardDetails( String cardTitle, TreatmentCard treatmentCard ) {
        testDefinitionSteps.addStep( "Check treatment card details" );
        treatmentAndResponseSteps.checkCardDetails( cardTitle, treatmentCard );
        testDefinitionSteps.logEvidence( STR."the \{cardTitle} treatment card contains the following data: \{treatmentCard}",
                "Treatment card was displayed on the UI and had the expected details", true );
    }

    @Then( "the \"$iconName\" icon is visible at imaging study $badgeText" )
    public void verifyIcon( String iconName, String badgeText ) {
        testDefinitionSteps.addStep( STR."Verify \{iconName} icon" );
        treatmentAndResponseSteps.iconVisibility( iconName, badgeText );
        testDefinitionSteps.logEvidence( STR."The \{iconName} icon is visible",
                STR."The \{iconName} icon is visible", true );
    }

    @Then( "the \"$linkName\" link is visible at imaging study $badgeText" )
    public void verifyLinkText( String linkName, String badgeText ) {
        testDefinitionSteps.addStep( STR."Verify \{linkName} icon" );
        treatmentAndResponseSteps.buttonTextVisibility( linkName, badgeText );
        testDefinitionSteps.logEvidence( STR."The \{linkName} icon is visible",
                STR."The \{linkName} icon is visible", true );
    }

    @When( "the user clicks on $buttonName button at TreatmentCard" )
    public void clickOnButtonAtTreatmentCard( String buttonName ) {
        testDefinitionSteps.addStep( "Click on button at TreatmentCard" );
        treatmentAndResponseSteps.expandOrCollapseTreatmentCard( buttonName );
        testDefinitionSteps.logEvidence( STR."the user clicked on \{buttonName} button",
                "the button clicked successfully", true );
    }

    @When( "the user clicks \"$buttonText\" of \"$studyName\" at imaging study $badgeText" )
    public void clickButtonForStudy( String buttonText, String studyName, String badgeText ) {
        testDefinitionSteps.addStep( STR."Click on \"\{buttonText}\" of \"\{studyName}\" at imaging study \{badgeText}" );
        treatmentAndResponseSteps.clickButtonForStudy( buttonText, studyName, badgeText );
        testDefinitionSteps.logEvidence( STR."the user clicks \"\{buttonText}\" of \"\{studyName}\" at imaging study \{badgeText}",
                STR."The \"\{buttonText}\" of \"\{studyName}\" at imaging study \{badgeText} clicked successfully", true );
    }

    @When( "the user clicks \"Open complete report\" of \"$treatmentCardTitle\" treatment card" )
    public void openCompleteReportOnTreatmentCard( String treatmentCardTitle ) {
        testDefinitionSteps.addStep( "Click on \"open complete report\" On Treatment Card" );
        treatmentAndResponseSteps.openCompleteReportForTreatmentCard( treatmentCardTitle );
        testDefinitionSteps.logEvidence( STR."the user clicks \"Open complete report\" of \"\{treatmentCardTitle}\" treatment card",
                STR."\"Open complete report\" of \"\{treatmentCardTitle}\" treatment card clicked", true );
    }

    @When( "the user hovers on the AI extracted text of \"$studyTitle\" on the Response view" )
    public void moveMouseOnTheResponseData( String studyTitle ) {
        testDefinitionSteps.addStep( STR."the user hovers on the AI extracted text of \{studyTitle}" );
        treatmentAndResponseSteps.hoverOnAIGeneratedTextOfStudy( studyTitle );
        testDefinitionSteps.logEvidence( STR."the user hovered on the AI extracted text of\{studyTitle}",
                STR."The user successfully hovered on the AI extracted text of\{studyTitle}", true );
    }

    @When( "the user hovers on the \"$graphName\" on the side legend area" )
    public void hoverOverLegend( String graphName ) {
        testDefinitionSteps.addStep( STR."Move the mouse over on the \{graphName} legend" );
        treatmentAndResponseSteps.moveMouseOnLegend( graphName );
        testDefinitionSteps.logEvidence(
                STR."The user hovers on the \{graphName} on the side legend area",
                STR."The user successfully hovered on the \{graphName} on the side legend area",
                true );
    }

    @Then( "the selected study for $badgeText is displayed in the Response group with the following details: $selectedStudy" )
    public void checkSelectedStudiesDetails( String badgeText, StudyCard selectedStudy ) {
        testDefinitionSteps.addStep( STR."Checking the selected study for \{badgeText}" );
        treatmentAndResponseSteps.checkSelectedStudyDetailsFor( badgeText, selectedStudy );
        testDefinitionSteps.logEvidence(
                STR."The selected study for \{badgeText} is displayed in the Response group with the following details: \{selectedStudy.toString()}",
                STR."The selected study for \{badgeText} is displayed in the Response group with the following details: \{selectedStudy.toString()}",
                true );
    }

    @Then( "the TreatmentCard state changed" )
    public void treatmentCardStateChanged() {
        testDefinitionSteps.addStep( "Change Treatment Card State" );
        treatmentAndResponseSteps.checkTreatmentCardState();
        testDefinitionSteps.logEvidence( "TreatmentCard state changed",
                "TreatmentCard state changed", true );
    }

    @Then( "the study A card displays empty state with the following message: \"$emptyStateText\"" )
    public void checkStudyAEmptyState( String emptyStateText ) {
        testDefinitionSteps.addStep( STR."Checking that study A card is displaying the empty state message \{emptyStateText}" );
        treatmentAndResponseSteps.checkStudyAEmptyState( emptyStateText );
        testDefinitionSteps.logEvidence(
                STR."The study A card displays empty state with the following message: \{emptyStateText}",
                STR."The study A card displayed the empty state with the following message: \{emptyStateText}",
                true );
    }

    @Then( "the study $badgeText card $doesOrDoesNotHave truncated text" )
    public void checkAISummaryIsTruncated( String badge, Boolean doesOrDoesNotHave ) {
        testDefinitionSteps.addStep( STR."Check the AI summarization of study \{badge} \{doesOrDoesNotHave ? "has" : "does not have"} truncated text" );
        treatmentAndResponseSteps.checkAISummarizationIsTruncated( badge, doesOrDoesNotHave );
        testDefinitionSteps.logEvidence( (STR."the AI summarization of study \{badge} \{doesOrDoesNotHave ? "has" : "does not have"} truncated text"),
                (STR."the AI summarization of study \{badge} \{doesOrDoesNotHave ? "has" : "does not have"} truncated text"), true );
    }

    @Then( "the Measurements table is displayed with the following details: $measurementTable" )
    public void checkMeasurementTable( ExamplesTable examplesTable ) {
        List<String> measurementTable = getExampleTableAsList( examplesTable );
        testDefinitionSteps.addStep( STR."Checking the Measurement table" );
        treatmentAndResponseSteps.checkMeasurementTable( measurementTable );
        testDefinitionSteps.logEvidence(
                STR."The Measurement table is displayed with the following details: \{measurementTable}",
                STR."The Measurement table is displayed with the following details: \{measurementTable}",
                true );
    }

    @Then( "the Response column displays the following text for \"Trends\" section: \"$emptyTrendsChartText\"" )
    public void checkEmptyTrendsChart( String emptyTrendsChartText ) {
        testDefinitionSteps.addStep( STR."Checking that Trends chart is displaying the empty state message \{emptyTrendsChartText}" );
        treatmentAndResponseSteps.checkEmptyTrendsChart( emptyTrendsChartText );
        testDefinitionSteps.logEvidence(
                STR."The Trends chart displayed the empty state with the following message: \{emptyTrendsChartText}",
                STR."The Trends chart displayed the empty state with the following message: \{emptyTrendsChartText}",
                true );
    }

    @Then( "a popover is displayed with the following full text: \"$fullText\"" )
    public void checkLegendTooltipText( String fullText ) {
        testDefinitionSteps.addStep( STR."Checking that legend tooltip is displaying the \{fullText} text" );
        treatmentAndResponseSteps.checkLegendTooltipText( fullText );
        testDefinitionSteps.logEvidence(
                STR."The legend tooltip displayed the \{fullText} text",
                STR."The legend tooltip displayed the \{fullText} text",
                true );
    }

    @Then( "the alert message presented: $message" )
    public void checkMessagePresented( String message ) {
        testDefinitionSteps.addStep( "check alert message" );
        treatmentAndResponseSteps.checkAlertMessage( message );
        testDefinitionSteps.logEvidence( STR."the alert message \{message} presented",
                STR."the alert message \{message} presented", true );
    }

    @Then( "the Response column displays the following empty message: $emptyMessageDetails" )
    public void checkEmptyResponseText( EmptyStateMessage emptyStateMessageDetails ) {
        testDefinitionSteps.addStep( STR."Checking empty state text on Response view" );
        treatmentAndResponseSteps.checkEmptyResponseColumnText( emptyStateMessageDetails );
        testDefinitionSteps.logEvidence(
                STR."the Response column displays the following empty message: \{emptyStateMessageDetails.toString()}",
                STR."the Response column displayed the following empty message: \{emptyStateMessageDetails.toString()}",
                true );
    }

    @Then( "the $module is not visible on the Response view" )
    public void checkModuleIsNotVisible( String module ) {
        testDefinitionSteps.addStep( STR."Checking \{module} is not visible" );
        treatmentAndResponseSteps.checkModuleIsNotVisible( module );
        testDefinitionSteps.logEvidence(
                STR."the \{module} is not visible",
                STR."the \{module} was not visible",
                true );
    }
}
