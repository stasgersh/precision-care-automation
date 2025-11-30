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
package com.ge.tracegen.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ge.tracegen.models.Scenario;
import com.ge.tracegen.models.Trace;
import com.ge.tracegen.service.dataReaders.FeatureFileReader;
import com.ge.tracegen.service.dataReaders.InitialTraceReader;
import com.ge.tracegen.service.dataWriters.BaseTraceabilityProcessor;
import com.ge.tracegen.service.dataWriters.TestCaseWriter;
import com.ge.tracegen.service.dataWriters.TraceabilityWriter;

public class TraceabilityGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger( TraceabilityGenerator.class );

    List<Trace> initialTraceability;
    List<Trace> observedTraceability;

    public TraceabilityGenerator( String initialTraceabilityFilePath, String featureFilesDirPath ) {
        initialTraceability = new InitialTraceReader( initialTraceabilityFilePath ).getTraceability();
        observedTraceability = new FeatureFileReader( featureFilesDirPath ).getTraceability();

        Map<Trace, String> mismatchTrace = searchTraceabilityMismatch( initialTraceability, observedTraceability );
        if ( !mismatchTrace.isEmpty() ) {
            throw new RuntimeException( "Traceability mismatch found, please check the provided data! Mismatch: " + mismatchTrace );
        }
    }

    public List<Trace> getMergedTraceability() {
        return mergeTraceabilities();
    }

    public void writeTestCasesToCSV() {
        new TestCaseWriter( observedTraceability ).generateTestCasesToCSV();
    }

    public void writeTraceabilityToCSV() {
        new TraceabilityWriter( getMergedTraceability() ).generateTraceabilityToCSV();
    }

    private static Map<Trace, String> searchTraceabilityMismatch( List<Trace> initialTraceability, List<Trace> observedTraceability ) {
        Map<Trace, String> requirementMismatch = new HashMap<>();
        observedTraceability.forEach( trace -> {
            List<Trace> foundTrace = initialTraceability.stream()
                                                        .filter( req -> req.getReqId().equals( trace.getReqId() ) )
                                                        .collect( Collectors.toList() );
            if ( foundTrace.isEmpty() ) {
                requirementMismatch.put(
                        trace,
                        String.format( "Requirement ID %s was not found in initial traceability! Probably, it is an obsolete requirement.", trace.getReqId() ) );
            } else if ( foundTrace.size() > 1 ) {
                requirementMismatch.put(
                        trace,
                        String.format( "Requirement ID was found %s times in initial traceability! Please check the initial traceability.", foundTrace.size() ) );
            } else if ( trace.isSafety() != foundTrace.get( 0 ).isSafety() ) {
                requirementMismatch.put(
                        trace,
                        String.format( "Safety flag for initial traceability is set to '%s' but the observed safety flag is '%s'", trace.isSafety(), foundTrace.get( 0 ).isSafety() ) );
            }

        } );
        System.out.println( "requirementMismatch: " + requirementMismatch + "\n" );
        return requirementMismatch;
    }

    private List<Trace> mergeTraceabilities() {
        List<Trace> mergedTraceability = new ArrayList<>();
        initialTraceability.forEach( initialTrace -> {
            List<Trace> observedTraces = observedTraceability.stream()
                                                             .filter( ot -> ot.getReqId().equals( initialTrace.getReqId() ) )
                                                             .collect( Collectors.toList() );
            if ( observedTraces.size() > 1 ) {
                throw new RuntimeException( String.format( "Req ID %s is present in %s feature files", initialTrace.getReqId(), observedTraces.size() ) );
            }
            if ( !observedTraces.isEmpty() && observedTraces.get( 0 ).getScenarios().isEmpty() ) {
                throw new RuntimeException( "Scenario was not found in the feature file for requirement: " + observedTraces.get( 0 ).getReqId() );
            }

            if ( initialTrace.hasLink() && !observedTraces.isEmpty() && !observedTraces.get( 0 ).getScenarios().isEmpty() ) {
                // Improvement opportunity: compare the linked and actual scenario IDs (link is not needed if the scenario exists in the corresponding feature file)
                LOGGER.info( "Requirement has link and observed scenario too: " + observedTraces.get( 0 ).getReqId() );
            }

            if ( initialTrace.hasLink() ) {
                verifyInitialScenarios( initialTrace, observedTraceability );
            }
            Trace mergedTrace = new Trace( initialTrace );
            if ( !observedTraces.isEmpty() ) {
                Trace observedTrace = observedTraces.get( 0 );
                observedTrace.getScenarios().forEach( observedSc -> {
                    boolean removedInitScenario = mergedTrace.getScenarios().removeIf( sc -> sc.getId().equals( observedSc.getId() ) );
                    if ( removedInitScenario ) {
                        LOGGER.warn( String.format(
                                "The scenario with ID %s was already present in initial traceability and in the feature file but only the one from feature file is kept!",
                                observedSc.getId() ) );
                    }
                } );

                mergedTrace.addScenarios( observedTrace.getScenarios() );
            }
            mergedTraceability.add( mergedTrace );
        } );
        return mergedTraceability;
    }

    /**
     * This method verifies that the scenarios which were provided in the initial traceability do really exist in the feature files
     *
     * @param initialTrace
     * @param observedTraceability
     */
    private void verifyInitialScenarios( Trace initialTrace, List<Trace> observedTraceability ) {
        List<Scenario> allAvailableScenarios = new BaseTraceabilityProcessor( observedTraceability ).getScenariosFromTraceabilityList();
        List<Scenario> notAvailableScenarios = new ArrayList<>();
        initialTrace.getScenarios().forEach( requiredScenario -> {
            Optional<Scenario> foundScenario = allAvailableScenarios.stream()
                                                                    .filter( scenario -> scenario.getId().equals( requiredScenario.getId() ) )
                                                                    .findFirst();
            if ( foundScenario.isEmpty() ) {
                notAvailableScenarios.add( requiredScenario );
            }
        } );
        if ( !notAvailableScenarios.isEmpty() ) {
            throw new RuntimeException( "Following scenarios from initial traceability are not available in the feature files:\n" + notAvailableScenarios );
        }
    }

}
