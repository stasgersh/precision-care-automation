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
package com.ge.onchron.verif.whatsteps.api;

import com.ge.onchron.verif.howsteps.AuditLogSteps;
import com.ge.onchron.verif.howsteps.CriteriaCalculatorSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.model.ExamplesTable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Collectors;

public class AuditLog {

    @Steps
    private AuditLogSteps auditLogSteps;

    @Steps
    private CriteriaCalculatorSteps criteriaCalculatorSteps;

    private TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();

    @Then( "audit log is found by the service user with below parameters: $auditLogParams" )
    public void checkAuditLog( ExamplesTable auditLogParams ) {
        testDefinitionSteps.addStep( STR."Check audit logs: \{auditLogParams.getRows()}" );
        auditLogSteps.findAuditLog( auditLogParams );
        testDefinitionSteps.logEvidence( STR."Audit logs were found.", STR."Audit logs were found.", true );
    }

    @Then( "audit log is found by the service user with below parameters and no correlation id: $auditLogParams" )
    public void checkAuditLogNoCorrelation( ExamplesTable auditLogParams ) {
        testDefinitionSteps.addStep( STR."Check audit logs: \{auditLogParams.getRows()}" );
        auditLogSteps.findAuditLog( auditLogParams , false);
        testDefinitionSteps.logEvidence( STR."Audit logs were found.", STR."Audit logs were found.", true );
    }

    @Then( "audit log is found by the service user and by $objectDetailsValuePattern pattern, and by generatedUUID, and with below parameters: $auditLogParams" )
    public void checkAuditLogUsingObjectDetailsValuePatternAndGeneratedUUID(ExamplesTable auditLogParams, String objectDetailsValuePattern) {
        Map<String, String> paramsMap = createParametersMapWithPlaceholdersByObjectDetailsValuePattern(auditLogParams, objectDetailsValuePattern, criteriaCalculatorSteps.randomUUID);
        ExamplesTable examplesTableWithUUID = auditLogSteps.convertMapToExampleTable(paramsMap);
        testDefinitionSteps.addStep( STR."Check audit logs with generatedUUID: \{auditLogParams.getRows()}" );
        auditLogSteps.findAuditLog( examplesTableWithUUID );
        testDefinitionSteps.logEvidence( STR."Audit logs were found.", STR."Audit logs were found.", true );
    }

    @Then( "the found audit log item contains \"$dateJsonPath\" property in the following format: \"$dateTimeFormat\"" )
    public void checkDateTimeOfAuditLog(String dateJsonPath, String dateTimeFormat ) {
        testDefinitionSteps.addStep( STR."Check that the audit log date and time format is: \{dateTimeFormat}" );
        auditLogSteps.checkDateTimeOfAuditLog(dateJsonPath, dateTimeFormat );
        testDefinitionSteps.logEvidence(
                STR."The audit log date and time format is: \{dateTimeFormat}",
                STR."The audit log date and time format is: \{dateTimeFormat}",
                true );
    }

    //TODO : consider to move this method to other class and parameterize
    private @NotNull Map<String, String> createParametersMapWithPlaceholders(ExamplesTable auditLogParams, String randomUUID) {
        Map<String, String> paramsMap = auditLogParams.getRows().stream()
                .collect(Collectors.toMap(
                        row -> row.get("property_path"),
                        row -> row.get("property_value").equals("${ID_VALUE}")
                                ? String.format("custom/%s configuration creation", randomUUID)  // Replace placeholder with UUID
                                : row.get("property_value")
                ));
        return paramsMap;
    }
    private @NotNull Map<String, String> createParametersMapWithPlaceholdersByObjectDetailsValuePattern(ExamplesTable auditLogParams,String objectDetailsValuePattern, String randomUUID) {
        Map<String, String> paramsMap = auditLogParams.getRows().stream()
                .collect(Collectors.toMap(
                        row -> row.get("property_path"),
                        row -> row.get("property_value").equals("${ID_VALUE}")
                                ? String.format(objectDetailsValuePattern, randomUUID)  // Replace placeholder with UUID
                                : row.get("property_value")
                ));
        return paramsMap;
    }

}
