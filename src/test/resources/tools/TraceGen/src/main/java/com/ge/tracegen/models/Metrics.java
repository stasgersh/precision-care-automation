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
package com.ge.tracegen.models;

import static com.ge.tracegen.utils.MathUtils.round;

public class Metrics {

    private int totalRequirements;
    private int coveredRequirements;
    private int coveredRequirementsByLinks;
    private int uncoveredRequirements;
    private double coveredRequirementsPercentage;

    private int totalTestCases;
    private int automatedTestCases;
    private int manualTestCases;
    private double automatedTestCasesPercentage;

    public Metrics( int totalRequirements, int coveredRequirements, int coveredRequirementsByLinks, int totalTestCases, int automatedTestCases ) {
        this.totalRequirements = totalRequirements;
        this.coveredRequirements = coveredRequirements;
        this.coveredRequirementsByLinks = coveredRequirementsByLinks;
        this.uncoveredRequirements = totalRequirements - coveredRequirements;
        this.coveredRequirementsPercentage = round( (double) coveredRequirements / (double) totalRequirements * 100, 1 );

        this.totalTestCases = totalTestCases;
        this.automatedTestCases = automatedTestCases;
        this.manualTestCases = totalTestCases - automatedTestCases;
        this.automatedTestCasesPercentage = round( (double) automatedTestCases / (double) totalTestCases * 100, 1 );
    }

    @Override
    public String toString() {
        return "Metrics:\n" +
                "totalRequirements: " + totalRequirements + "\n" +
                "coveredRequirements=" + coveredRequirements + "\n" +
                "coveredRequirementsByLinks=" + coveredRequirementsByLinks + "\n" +
                "uncoveredRequirements=" + uncoveredRequirements + "\n" +
                "coveredRequirementsPercentage=" + coveredRequirementsPercentage + "\n" +
                "totalTestCases=" + totalTestCases + "\n" +
                "automatedTestCases=" + automatedTestCases + "\n" +
                "manualTestCases=" + manualTestCases + "\n" +
                "automatedTestCasesPercentage=" + automatedTestCasesPercentage + "\n";
    }

}
