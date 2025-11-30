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
package com.ge.onchron.reporter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.jbehave.core.annotations.Scope;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.Story;
import org.jbehave.core.steps.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;

import static com.ge.onchron.verif.utils.OrcanosReporterUtils.prepareDescription;

public class FFToLogConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger( FFToLogConverter.class );

    public static void main( String[] args ) {
        LOGGER.warn( "THIS LOG FILE CAN BE USED ONLY FOR UPLOADING MANUAL TEST CASES TO ORCANOS WITHOUT RESULTS!" );
        LOGGER.info( "----------------------------------- FFToLogConverter started -----------------------------------" );
        long argsCount = Arrays.stream( args ).count();
        if ( argsCount == 0 ) {
            throw new RuntimeException( "At least one argument is required to provide feature files path" );
        }

        String argOfFeatureFilePaths = args[0];
        LOGGER.info( STR."Arg of feature file paths: \{args[0]}" );
        Collection<File> featureFiles = getFeatureFiles( argOfFeatureFilePaths );
        LOGGER.info( STR."Feature files to process: \{featureFiles}" );

        List<String> scenarioFilters = new ArrayList<>();
        if ( argsCount > 1 ) {
            String argOfScenarioFilters = args[1];
            LOGGER.info( STR."Arg of scenario filters: \{args[1]}" );
            scenarioFilters = Arrays.asList( argOfScenarioFilters.split( "," ) );
        }

        LOGGER.info( STR."Scenario filters: \{scenarioFilters}" );

        TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();
        testDefinitionSteps.finishAll();

        for ( File featureFile : featureFiles ) {
            String storyText = readContentFromFile( featureFile );
            Story story = new CustomGherkinStoryParser().parseStory( storyText );
            List<String> backgroundSteps = story.getLifecycle().getBeforeSteps( Scope.SCENARIO );

            for ( Scenario scenario : story.getScenarios() ) {
                // Currently, Scenario Outlines are handled as one Test Case => Later, handle example tables separately?
                Set<String> properties = scenario.getMeta().getPropertyNames();
                Scenario scenarioWithBackgroundSteps = createScenarioWithBackgroundSteps( scenario, backgroundSteps );

                // Check if the scenario is required based on the provided filters
                boolean requiredScenario = scenarioFilters.isEmpty() || CollectionUtils.containsAny( properties, scenarioFilters );
                if ( requiredScenario ) {
                    processScenario( scenarioWithBackgroundSteps, properties, testDefinitionSteps );
                }
            }
        }
        LOGGER.info( "----------------------------------- FFToLogConverter ended -----------------------------------" );
    }

    private static void processScenario( Scenario scenario, Set<String> properties, TestDefinitionSteps testDefinitionSteps ) {
        try {
            String descriptionJson = prepareDescription( scenario.getTitle(), properties );
            testDefinitionSteps.setDescription( descriptionJson );
        } catch ( JsonProcessingException e ) {
            throw new RuntimeException( STR."Cannot create descriptor json: \{e}" );
        }
        testDefinitionSteps.setTestName( scenario.getTitle() );
        testDefinitionSteps.initialize();

        List<String> initialSteps = scenario.getSteps();

        if ( scenario.hasExamplesTable() && scenario.getExamplesTable().getRowCount() != 0 ) {
            int exampleCount = scenario.getExamplesTable().getRowCount();
            List<String> scenarioOutlineSteps = new ArrayList<>();
            for ( int i = 0; i < exampleCount; i++ ) {
                List<String> preparedSteps = new ArrayList<>();
                Map<String, String> exampleRow = scenario.getExamplesTable().getRow( i );
                for ( String step : initialSteps ) {
                    for ( Map.Entry<String, String> entry : exampleRow.entrySet() ) {
                        String parameterName = entry.getKey();
                        String parameterValue = entry.getValue();
                        String parameterPlaceholder = "<" + parameterName + ">";
                        step = step.replace( parameterPlaceholder, parameterValue );
                    }
                    // Replace remaining html spec characters
                    step = replaceSpecCharsToHtmlCode( step );
                    preparedSteps.add( step );
                }
                scenarioOutlineSteps.addAll( preparedSteps );
            }
            createStepDescription( testDefinitionSteps, scenarioOutlineSteps );
        } else {
            List<String> preparedSteps = new ArrayList<>();
            // Replace  html spec characters
            for ( String scenarioStep : initialSteps ) {
                scenarioStep = replaceSpecCharsToHtmlCode( scenarioStep );
                preparedSteps.add( scenarioStep );
            }
            createStepDescription( testDefinitionSteps, preparedSteps );
        }
        testDefinitionSteps.finishAll();
    }

    /**
     * @param featureFilesPaths Feature file paths separated by comma
     *                          Examples of valid parameter:
     *                          1. "C:\\Test\\Feature_files"
     *                          2. "C:\\Test\\Feature_files\\Test1.feature"
     *                          3. "C:\\Test\\Feature_files\\Test1.feature,C:\\Test\\Feature_files\\Test2.feature"
     *                          4. "src\\test\\resources\\stories\\AccessControl" (the Working directory shall be the base project dir)
     * @return List of all feature files need to be processed
     */
    private static Collection<File> getFeatureFiles( String featureFilesPaths ) {
        List<String> ffPaths = Arrays.asList( featureFilesPaths.split( "," ) );
        Collection<File> requiredFeatureFiles = new HashSet<>();
        ffPaths.forEach( ffPath -> {
            File folder = new File( ffPath );
            Collection<File> files = FileUtils.listFiles(
                    folder,
                    new RegexFileFilter( "(.*?)\\.(feature)$" ),
                    DirectoryFileFilter.DIRECTORY
            );
            requiredFeatureFiles.addAll( files );
        } );
        return requiredFeatureFiles;
    }

    private static String readContentFromFile( File file ) {
        String fileContent = null;
        try {
            fileContent = Files.readString( file.toPath() );
        } catch ( IOException e ) {
            System.out.println( "Error while reading data from file '" + file.toPath() + "': " + e.getMessage() );
        }
        return fileContent;
    }

    private static void createStepDescription( TestDefinitionSteps testDefinitionSteps, List<String> scenarioSteps ) {
        String prevRealKeyWord = "Given";
        for ( String step : scenarioSteps ) {

            // Convert table to html table if step has a table in the feature file
            if ( step.contains( "\n|" ) ) {
                String tableStr = step.substring( step.indexOf( "\n|" ) );
                ExamplesTable table = new ExamplesTable( tableStr );

                StringBuilder htmlTable = new StringBuilder();
                htmlTable.append( "<table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:500px\">" );

                htmlTable.append( "<tr>" );
                table.getHeaders().forEach( header -> htmlTable.append( STR."<th>\{header}</th>" ) );
                htmlTable.append( "</tr>" );

                for ( int i = 0; i < table.getRowCount(); i++ ) {
                    htmlTable.append( "<tr>" );
                    Parameters rowParams = table.getRowsAsParameters().get( i );
                    for ( String header : table.getHeaders() ) {
                        htmlTable.append( STR."<td>\{rowParams.valueAs( header, String.class )}</td>" );
                    }
                    htmlTable.append( "</tr>" );
                }

                htmlTable.append( "</table>" );
                step = step.replace( tableStr, htmlTable.toString() );
            }

            String stepKeyWord = step.substring( 0, step.indexOf( ' ' ) );
            String stepWithoutKeyWord = step.replace( stepKeyWord + " ", "" );

            if ( !stepKeyWord.equals( "And" ) ) {
                prevRealKeyWord = stepKeyWord;
            } else {
                step = step.replaceFirst( stepKeyWord, prevRealKeyWord );
                stepKeyWord = prevRealKeyWord;
            }

            String stepDescription;
            if ( stepKeyWord.equals( "Given" ) ) {
                stepDescription = step.replace( "Given", "Precondition:" );
            } else if ( stepKeyWord.equals( "When" ) ) {
                stepDescription = step.replace( "When", "Action:" );
            } else if ( stepKeyWord.equals( "Then" ) ) {
                stepDescription = step.replace( "Then", "Check that" );
            } else {
                throw new RuntimeException( "Not known step keyword: " + stepKeyWord );
            }

            testDefinitionSteps.addStep( stepDescription, false );
            testDefinitionSteps.logEvidence( stepWithoutKeyWord, "!!! THIS IS ONLY FOR TEST CASE UPLOAD WITHOUT RESULTS !!!", true , true, false);
        }
    }

    private static String replaceSpecCharsToHtmlCode( String scenarioStep ) {
        scenarioStep = scenarioStep.replaceAll( "<", "&lt;" );
        scenarioStep = scenarioStep.replaceAll( ">", "&gt;" );
        scenarioStep = scenarioStep.replaceAll( "--", "&hyphen;&hyphen;" );
        return scenarioStep;
    }

    private static Scenario createScenarioWithBackgroundSteps( Scenario scenario, List<String> backgroundSteps ) {
        List<String> allScenarioSteps = new ArrayList<>();
        allScenarioSteps.addAll( backgroundSteps );
        allScenarioSteps.addAll( scenario.getSteps() );
        return new Scenario(
                scenario.getTitle(),
                scenario.getMeta(),
                scenario.getGivenStories(),
                scenario.getExamplesTable(),
                allScenarioSteps
        );
    }

}
