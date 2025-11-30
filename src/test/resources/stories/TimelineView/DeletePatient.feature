Feature: Delete patients

Narrative: The system shall back to default settings after deletion from DPSA.

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68520,SysRS-68467
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_View
Scenario: Patient level settings are deleted from DB after patient was deleted from DPSA
Given [API] the following patient is uploaded to PDS: summary/amitHaze_bundle.json
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, AmitHaze" patient is selected
When the user navigates to the "Timeline" view
Then the patient timeline is displayed
When the user set the following timeline filter options:
| filter_group       | checkbox_name | selected |
| Imaging modalities | CT            | true     |
| Swimlanes          | Imaging       | true     |
| Imaging modalities | MR            | true     |
Then the filter counter icon on the Filter button is displayed with number: 3
When the user zooms out the timeline until it is possible
Then the below events on "Imaging" swimlane are visible in the viewport:
| event_type | name_on_label                                                      | text_on_event_point | date_on_timeline_axis |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST                             | <EMPTY>             | Sep 28, 2011          |
| MARKER     | Computed tomography, abdomen and pelvis; with contrast material(s) | <EMPTY>             | Jan 16, 2000          |
When the following patient was deleted from PDS: summary/amitHaze_bundle.json
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient testPatientIdAmitHaze, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue testPatientIdAmitHaze
When the user selects the "OncoTestPatient, Juno" patient
And the user clicks on the browser's refresh button
Then the Patient header section is displayed
When the user opens the patient select menu
Then the following user is not present in the patient list:
| name                      | mrn                   |
| OncoTestPatient, AmitHaze | testPatientIdAmitHaze |
When [API] the following patient is uploaded to PDS: summary/amitHaze_bundle.json
And the user waits 1 minutes
And the user clicks on the browser's refresh button
Then the Patient page is loaded
When the user selects the "OncoTestPatient, AmitHaze" patient
And the user navigates to the "Timeline" view
Then the patient timeline is loaded
And the filter counter icon on the Filter button is not displayed