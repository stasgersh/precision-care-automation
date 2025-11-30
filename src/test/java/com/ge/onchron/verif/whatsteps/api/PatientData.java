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

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import com.ge.onchron.verif.howsteps.PatientDataSteps;
import com.ge.onchron.verif.howsteps.TestDefinitionSteps;
import com.ge.onchron.verif.model.StringList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.thucydides.core.annotations.Steps;

import static com.ge.onchron.verif.TestSystemParameters.TEST_DATA_CLASSPATH;
import static com.ge.onchron.verif.utils.FileUtils.readFromFile;

public class PatientData {

    @Steps
    private PatientDataSteps patientDataSteps;
    private TestDefinitionSteps testDefinitionSteps = TestDefinitionSteps.getInstance();


    @Given( "[API] the following {resource|resources} {is|are} not available in PDS: $resourceFilePath" )
    @When( "[API] the following {resource|resources} {is|are} deleted from PDS: $resourceFilePath" )
    public void deleteResourcesFromPds( StringList resourceFilePaths ) {
        patientDataSteps.deleteResourcesFromPds( resourceFilePaths.getList() );
    }

    @Given( "[API] all additional \"$resourceType\" resources were deleted from DPSA with patient reference ID: \"$patientId\"" )
    public void deleteResourcesWithPatientReferenceId( String resourceType, String patientId ) {
        testDefinitionSteps.addStep(
                String.format( "Check All additional %s resources were deleted from DPSA with patient ID: %s", resourceType, patientId ) );
        patientDataSteps.deleteResourcesWithPatientReferenceId( resourceType, patientId );
        testDefinitionSteps.logEvidence(
                String.format( "All additional %s resources were deleted from DPSA with patient ID: %s", resourceType, patientId ),
                String.format( "All additional %s resources were deleted from DPSA with patient ID: %s", resourceType, patientId ),
                true );
    }

    @Given( "[API] the following patient is uploaded to PDS: $resourceFilePath" )
    @Alias( "the following patient was uploaded to PDS: $resourceFilePath" )
    @When( "[API] the following patient is uploaded to PDS: $resourceFilePath" )
    public void uploadPatientJacket( String resourceFilePath ) {
        testDefinitionSteps.addStep( "Upload patient to PDS" );
        patientDataSteps.uploadPatientJacketFromFile( resourceFilePath );
        testDefinitionSteps.logEvidence( STR."the following patient is uploaded to PDS: \{resourceFilePath}",
                "Bundle was uploaded to PDS successfully", true );
    }

    //Use this method with 'waitForETLProcess' (Monitoring step for  ETL process complete)
    @Given( "[API] the following patient is uploaded to PDS with no fixed wait: $resourceFilePath" )
    @When( "[API] the following patient is uploaded to PDS with no fixed wait: $resourceFilePath" )
    @Alias( "the following patient is uploaded to PDS with no fixed wait: $resourceFilePath" )
    public void uploadPatientJacketWithNoFixedWait( String resourceFilePath ) {
        testDefinitionSteps.addStep( "Upload patient to PDS" );
        patientDataSteps.uploadPatientJacketFromFileWithNoFixedWait( resourceFilePath );
        testDefinitionSteps.logEvidence( STR."the following patient is uploaded to PDS: \{resourceFilePath}",
                "Bundle was uploaded to PDS successfully", true );
    }

    @Given( "[API] the following bundle is uploaded to PDS: $resourceFilePath" )
    @Alias( "the following bundle was uploaded to PDS: $resourceFilePath" )
    public void uploadBundle( String resourceFilePath ) {
        testDefinitionSteps.addStep( "Upload bundle to PDS" );
        patientDataSteps.uploadResourceFromFile( resourceFilePath );
        testDefinitionSteps.logEvidence( STR."the following bundle is uploaded to PDS: \{resourceFilePath}",
                "Bundle was uploaded to PDS successfully", true );
    }

    @Given( "[API] the following bundle is uploaded to DCC: $resourceFilePath" )
    @When( "the following bundle was uploaded to DCC: $resourceFilePath" )
    public void uploadBundleToDCC( String resourceFilePath ) {
        String resourceBundle = readFromFile( TEST_DATA_CLASSPATH + resourceFilePath );
        testDefinitionSteps.addStep( "Upload bundle to PDS" );
        patientDataSteps.uploadBundle( resourceBundle );
        testDefinitionSteps.logEvidence( STR."the following bundle is uploaded to PDS: \{resourceFilePath}",
                "Bundle was uploaded to PDS successfully", true );
    }

    @Given( "[API] the following bundle is uploaded to DPSA if not uploaded before: $resourceFilePath" )
    @When( "[API] the following bundle is uploaded to DPSA if not uploaded before: $resourceFilePath" )
    public void uploadPatientJacketIfNotPresent( String resourceFilePath ) {
        testDefinitionSteps.addStep( "Upload bundle to DPSA if not uploaded before" );
        patientDataSteps.uploadPatientJacketFromFileIfNotPresent( resourceFilePath );
        testDefinitionSteps.logEvidence( STR."the following bundle is uploaded to DPSA if not uploaded before: \{resourceFilePath}",
                "Bundle was uploaded to DPSA successfully if not uploaded before", true );
    }

    @Given( "[API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [$dataQuery] by: $resourceFilePath" )
    @When( "[API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [$dataQuery] by: $resourceFilePath" )
    public void uploadPatientJacketIfNotPresentInDPSAndFulfillment(List<String> dataQuery, String resourceFilePath ) {
        testDefinitionSteps.addStep( "Upload bundle to DPSA if not uploaded before and missing in fulfillment" );
        patientDataSteps.uploadPatientJacketFromFileIfNotInFulfillment(dataQuery, resourceFilePath );
        testDefinitionSteps.logEvidence( STR."the following bundle is uploaded to DPSA if not uploaded before: \{resourceFilePath}",
                "Bundle was uploaded to DPSA successfully if not uploaded before", true );
    }

    @Given( "[API] the following bundle is uploaded to PDS with no fixed wait: $resourceFilePath" )
    @When( "[API] the following bundle is uploaded to PDS with no fixed wait: $resourceFilePath" )
    @Alias( "the following bundle was uploaded to PDS with no fixed wait: $resourceFilePath" )
    public void uploadBundleWithNoFixedWait( String resourceFilePath ) {
        testDefinitionSteps.addStep( "Upload bundle to PDS without waiting." );
        patientDataSteps.uploadResourceFromFile( resourceFilePath, false );
        testDefinitionSteps.logEvidence( STR."the following bundle is uploaded to PDS: \{resourceFilePath}",
                "Bundle was uploaded to PDS successfully", true );
    }

    @Given( "[API] the following patient was deleted from PDS: $resourceFilePath" )
    @When( "[API] the following patient is deleted from PDS: $resourceFilePath" )
    @Alias( "the following patient was deleted from PDS: $resourceFilePath" )
    public void deletedPatientJacket( String resourceFilePath ) {
        testDefinitionSteps.addStep( "Delete patient from PDS" );
        patientDataSteps.deletePatientJacketFromFile( resourceFilePath );
        testDefinitionSteps.logEvidence( STR."the following patient is deleted from PDS: \{resourceFilePath}",
                "Bundle was deleted to PDS successfully", true );
    }

    @Given( "[API] the following {resource is|resource was|resources were} uploaded to PDS: $resourceFilePath" )
    @When( "[API] the following {resource is|resources are} uploaded to PDS: $resourceFilePath" )
    public void uploadResource( ExamplesTable resourceTable ) {
        for ( Map<String, String> resourceRow : resourceTable.getRows() ) {
            testDefinitionSteps.addStep( STR."Upload \{resourceRow} resource to PDS" );
            String patientResourceFilePath = resourceRow.get( "patient_resource" );
            String resourceFilePath = resourceRow.get( "resource" );
            patientDataSteps.uploadSingleResource( patientResourceFilePath, resourceFilePath, true );
            testDefinitionSteps.logEvidence( STR."the following resources \{resourceTable.getHeaders()} uploaded to PDS: \{resourceFilePath}",
                    "Bundle was uploaded to PDS successfully", true );
        }
    }

    @Given( "[API] the following {resource is|resource was|resources were} uploaded to PDS with no fixed wait: $resourceFilePath" )
    @When( "[API] the following {resource is|resources are} uploaded to PDS with no fixed wait: $resourceFilePath" )
    public void uploadResourceWithNoFixedWait( ExamplesTable resourceTable ) {
        for ( Map<String, String> resourceRow : resourceTable.getRows() ) {
            testDefinitionSteps.addStep( STR."Upload \{resourceRow} resource to PDS" );
            String patientResourceFilePath = resourceRow.get( "patient_resource" );
            String resourceFilePath = resourceRow.get( "resource" );
            patientDataSteps.uploadSingleResource( patientResourceFilePath, resourceFilePath, false );
            testDefinitionSteps.logEvidence( STR."the following resources \{resourceTable.getHeaders()} uploaded to PDS: \{resourceFilePath}",
                    "Bundle was uploaded to PDS successfully", true );
        }
    }

    @Given( "the dates of the following patient's resources are prepared and uploaded to PDS: $resourceFilePath" )
    public void prepareDatesAndUploadPatientJacket( String resourceFilePath ) {
        testDefinitionSteps.addStep( STR."Upload resources with prepared dates of patient to PDS" );
        patientDataSteps.prepareDateParamsAndUploadPatientJacket( resourceFilePath );
        testDefinitionSteps.logEvidence( STR."the dates of the following patient's resources are prepared and the resource was uploaded to PDS: \{resourceFilePath}",
                "Bundle was uploaded to PDS successfully", true );
    }

    @Given( "[API] the ids of the following patient's resources are prepared and uploaded to PDS: $resourceFilePath" )
    @When( "[API] the ids of the following patient's resources are prepared and uploaded to PDS: $resourceFilePath" )
    public void prepareIdsAndUploadPatientJacket( String resourceFilePath ) {
        testDefinitionSteps.addStep( STR."Upload resources with prepared ids of patient to PDS" );
        patientDataSteps.prepareIdsAndUploadPatientJacket( resourceFilePath );
        testDefinitionSteps.logEvidence( STR."the ids of the following patient's resources are prepared and the resource was uploaded to PDS: \{resourceFilePath}",
                "Bundle was uploaded to PDS successfully", true );
    }

    @Given( "[API] the \"$versionType\" timestamp of the patient with fhir id \"$patientId\" is saved" )
    @When( "[API] the \"$versionType\" timestamp of the patient with fhir id \"$patientId\" is saved" )
    public void saveLatestVersionTimestamp( String versionType, String patientId ) {
        testDefinitionSteps.addStep( String.format( "Save \"%s\" timestamp of patient %s", versionType, patientId ) );
        String latestVersion = patientDataSteps.saveLatestVersionTimestamp( versionType, patientId );
        testDefinitionSteps.logEvidence(
                String.format( "The \"%s\" timestamp of patient %s is saved", versionType, patientId ),
                String.format( "The \"%s\" timestamp of patient %s is saved: %s", versionType, patientId, latestVersion ),
                true );
    }

    @Then( "the following patient's data was not changed in DPSA: $resourceFilePath" )
    public void comparePatientWithUploadedData( String resourceFilePath ) {
        testDefinitionSteps.addStep( STR."Compare following patient with uploaded data in DPSA \{resourceFilePath}" );
        patientDataSteps.compareWithStoredData( resourceFilePath );
        testDefinitionSteps.logEvidence( "Patient data was not changed in DPSA",
                "Patient data was not changed in DPSA", true );
    }

    @Given( "[API] the \"$postProcessingType\" post-processing metadata is generated into the following resources: $resourceFilePaths" )
    @Then( "[API] the \"$postProcessingType\" post-processing metadata is generated into the following resources: $resourceFilePaths" )
    public void waitForPostProcessingData( String postProcessingType, StringList resourceFilePaths ) {
        testDefinitionSteps.addStep(
                String.format( "Check that the %s post-processing metadata is generated into the following resources: %s", postProcessingType, resourceFilePaths ) );
        List<String> resourceFilePathsCopy = new ArrayList<>(resourceFilePaths.getList());
        patientDataSteps.waitForPostProcessingData( postProcessingType, resourceFilePaths.getList() );
        testDefinitionSteps.logEvidence(
                String.format( "The %s post-processing metadata is generated into the following resources: %s", postProcessingType, resourceFilePathsCopy ),
                String.format( "The %s post-processing metadata is generated into the following resources: %s", postProcessingType, resourceFilePathsCopy ),
                true );
    }

    @Then( "[API] $numberOfResources \"$docType\" {resource|resources} with category \"$category\" {is|are} generated in DPSA for patient \"$patientId\"" )
    public void checkResourceAvailabilityWithCategory( int numberOfResources, String docType, String category, String patientId ) {
        testDefinitionSteps.addStep(
                String.format( "Check that %s \"%s\" resources with category \"%s\" are generated in DPSA for patient \"patientId\"",
                        numberOfResources, numberOfResources, category, patientId ) );
        patientDataSteps.checkResourceAvailabilityWithCategory( numberOfResources, docType, category, patientId );
        testDefinitionSteps.logEvidence(
                String.format( "%s \"%s\" resources with category \"%s\" are generated in DPSA for patient \"patientId\"",
                        numberOfResources, numberOfResources, category, patientId ),
                String.format( "%s \"%s\" resources with category \"%s\" are generated in DPSA for patient \"patientId\"",
                        numberOfResources, numberOfResources, category, patientId ),
                true );
    }

}
