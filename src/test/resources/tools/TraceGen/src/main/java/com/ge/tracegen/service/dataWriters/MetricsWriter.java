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
package com.ge.tracegen.service.dataWriters;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ge.tracegen.models.Metrics;
import com.ge.tracegen.models.Scenario;
import com.ge.tracegen.models.Trace;
import com.google.gson.Gson;

import static com.ge.tracegen.Constants.GENERATED_FILES_PATH;
import static com.ge.tracegen.utils.FileUtils.saveFile;

public class MetricsWriter extends BaseTraceabilityProcessor {

    public MetricsWriter( List<Trace> traceability ) {
        super( traceability );
    }

    public void writeMetricsToJson() {
        int totalRequirements = traceability.size();
        long coveredRequirements = traceability.stream()
                                               .filter( trace -> !trace.getScenarios().isEmpty() )
                                               .count();
        long coveredRequirementsByLinks = traceability.stream()
                                                      .filter( Trace::hasLink )
                                                      .count();

        int totalTestCases = getFilteredScenarios().size();

        long automatedTestCases = getScenariosFromTraceabilityList().stream()
                                                                    .filter( Scenario::isAutomated )
                                                                    .count();
        Metrics metrics = new Metrics(
                totalRequirements,
                (int) coveredRequirements,
                (int) coveredRequirementsByLinks,
                totalTestCases,
                (int) automatedTestCases );

        System.out.println( metrics );

        String metricsJson = new Gson().toJson( metrics );
        Path metricsFilePath = Paths.get( GENERATED_FILES_PATH, "metrics.json" );
        saveFile( metricsFilePath, metricsJson );
        System.out.println( "Metrics saved into: " + metricsFilePath + "\n" );
    }

    /**
     * Traceability list contains a scenario more times if the scenario is linked to more requirements.
     * This method eliminates the duplicated scenarios and returns a list which contains one scenario only once.
     *
     * @return filtered scenarios
     */
    private List<Scenario> getFilteredScenarios() {
        List<Scenario> uniqueScenarios = new ArrayList<>();
        getScenariosFromTraceabilityList().forEach( scenario -> {
            Optional<Scenario> presentInUniqueList = uniqueScenarios.stream()
                                                                    .filter( sc -> sc.getId().equals( scenario.getId() ) )
                                                                    .findAny();
            if ( presentInUniqueList.isEmpty() ) {
                uniqueScenarios.add( scenario );
            }
        } );
        return uniqueScenarios;
    }

}
