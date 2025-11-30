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
package com.ge.tracegen.service.dataReaders;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.jbehave.core.model.Story;
import org.jbehave.core.parsers.gherkin.GherkinStoryParser;

import com.ge.tracegen.models.Scenario;
import com.ge.tracegen.models.Trace;

import static com.ge.tracegen.utils.FileUtils.readContentFromFile;

public class FeatureFileReader {

    String featureFilesDirPath;

    public FeatureFileReader( String featureFilesDirPath ) {
        this.featureFilesDirPath = featureFilesDirPath;
    }

    public List<Trace> getTraceability() {
        return prepareTraceabilityFromFeatureFiles();
    }

    public List<Trace> prepareTraceabilityFromFeatureFiles() {
        Collection<File> featureFiles = getFeatureFiles( featureFilesDirPath );
        List<Trace> observedTraceability = new ArrayList<>();

        featureFiles.forEach( featureFile -> {
            String storyText = readContentFromFile( featureFile );
            Story story = new GherkinStoryParser().parseStory( storyText );

            Trace trace = new Trace();

            String reqId = StringUtils.substringBetween( story.getDescription().asString(), "[", "]" );
            if ( reqId == null ) {
                throw new RuntimeException( "Requirement ID was not found in feature file: " + featureFile.getAbsolutePath() );
            }
            trace.setReqId( reqId );

            // Set safety flag (it is used when merging the initial and the observed traceability)
            Optional<String> isSafety = story.getMeta()
                                             .getPropertyNames()
                                             .stream()
                                             .filter( property -> property.toLowerCase().contains( "safety" ) ).findFirst();
            trace.setSafety( isSafety.isPresent() );

            story.getScenarios().forEach( scenario -> {
                // Currently, Scenario Outlines are handled as one Test Case => Later, handle example tables separately?
                Set<String> properties = scenario.getMeta().getPropertyNames();
                String scenarioId = properties.stream()
                                              .filter( property -> property.startsWith( "SRS-" ) )
                                              .findFirst()
                                              // If no scenario in the feature file then scenario.getTitle() returns Feature title + narrative text
                                              .orElseThrow( () -> new RuntimeException( "Scenario ID was not found for: " + scenario.getTitle() ) );
                Optional<String> isManual = properties.stream()
                                                      .filter( property -> property.toLowerCase().contains( "manual" ) ).findFirst();

                // remove test id and manual annotation in order to set additional annotations
                properties.removeIf( property -> property.equals( scenarioId ) || property.toLowerCase().contains( "manual" ) );

                Scenario sc = new Scenario();
                sc.setId( scenarioId );
                sc.setAutomated( isManual.isEmpty() );
                sc.setTitle( scenario.getTitle() );
                sc.setAdditionalAnnotations( properties );
                trace.addScenario( sc );
            } );
            observedTraceability.add( trace );

        } );
        return observedTraceability;
    }

    private Collection<File> getFeatureFiles( String featureFilesDirPath ) {
        // Story (aka. Feature file) parser
        File folder = new File( featureFilesDirPath );
        return FileUtils.listFiles(
                folder,
                new RegexFileFilter( "(.*?)\\.(feature)$" ),
                DirectoryFileFilter.DIRECTORY
        );
    }

}
