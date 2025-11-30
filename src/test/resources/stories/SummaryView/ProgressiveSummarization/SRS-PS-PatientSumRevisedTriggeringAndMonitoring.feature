Feature: [SRS-PS-RevisedTriggeringAndMonitoring]

Narrative: Progressive Summarization monitoring and triggering revised

@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68498
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: 5.1.2 - isAnyEntityUpdated - Monitoring list is updated and progressive summary report is generated for the patient when new event is available for the patient
Given the following patient was deleted from PDS: patients/TEST_PS_Miles.json
And [API] all additional "DocumentReference" resources were deleted from DPSA with patient reference ID: "d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a"
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a, triggering the ETL Step Function fulfill-etl-workflow
And the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a
And [API] the following patient is uploaded to PDS: patients/TEST_PS_Miles.json
And [API] an optional ETL process is started in 60 seconds and finished within 180 seconds with status Succeeded for patient d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a
When [API] the Progressive Summarization scheduled event timing is configured to trigger in 2 minutes
And the user waits 2 minutes
Then [API] 1 "DocumentReference" resource with category "http://loinc.org|60591-5" is generated in DPSA for patient "d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a"
When the user waits 10000 milliseconds
And the following request is sent to Precision Insights by service user app:
| service         | method | endpoint_path                                           | content_type     |
| patient-summary | GET    | monitoring/patient/d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a | application/json |
And the user stores response json as "monitoringResponse_1"
When [API] the following resource is uploaded to PDS:
| patient_resource                             | resource                                             |
| patients/resource/TEST_PS_Miles_patient.json | patients/resource/TEST_PS_Miles_PathologyReport.json |
When [API] the Progressive Summarization scheduled event timing is configured to trigger in 2 minutes
And the user waits 2 minutes
Then [API] 2 "DocumentReference" resource with category "http://loinc.org|60591-5" is generated in DPSA for patient "d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a"
When the user waits 10000 milliseconds
And the following request is sent to Precision Insights by service user app:
| service         | method | endpoint_path                                           | content_type     |
| patient-summary | GET    | monitoring/patient/d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a | application/json |
Then the response body contains the following text: 'isPatientNeverTriggered: false'
And the response body contains the following text: 'isCutDateUpdated: false'
When the user stores response json as "monitoringResponse_2"
Then the value of "latestTriggerAt" property in "monitoringResponse_2" is later than "monitoringResponse_1"
And the value of "latestPatientVersionTriggered" property in "monitoringResponse_2" is greater than "monitoringResponse_1"

@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68498
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: 5.1.2 - isCutDateUpdated - Progressive summary report is regenerated for the patient when cut date is updated
Given the following patient was deleted from PDS: patients/TEST_PS_Miles.json
And [API] the following resource is not available in PDS:
| resourceFilePath                                            |
| patients/resource/TEST_PS_Miles_HistoryAndPhysicalNote.json |
| patients/resource/TEST_PS_Miles_Encounter.json              |
And [API] all additional "DocumentReference" resources were deleted from DPSA with patient reference ID: "d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a"
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a, triggering the ETL Step Function fulfill-etl-workflow
And the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a
And [API] the following patient is uploaded to PDS: patients/TEST_PS_Miles.json
And [API] an optional ETL process is started in 60 seconds and finished within 240 seconds with status Succeeded for patient d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a
When [API] the Progressive Summarization scheduled event timing is configured to trigger in 2 minutes
And the user waits 2 minutes
Then [API] 1 "DocumentReference" resource with category "http://loinc.org|60591-5" is generated in DPSA for patient "d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a"
And [API] an optional ETL process is started in 60 seconds and finished within 180 seconds with status Succeeded for patient d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a
When the following request is sent to Precision Insights by service user app:
| service         | method | endpoint_path                                           | content_type     |
| patient-summary | GET    | monitoring/patient/d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a | application/json |
Then the response body contains the following text: '"latestTriggeredCutDate":"2003-09-09"'
When [API] the [DEFAULT-TEST-USER] user turns ON the CLP visualization
And the [DEFAULT-TEST-USER] user logs in to OncoCare
And the user selects the "TEST_PS, Miles" patient
Then the Progressive Summarization Overview is displayed
And the 'What has changed' section has the title: "Since last oncology encounter (Sep 09, 2003)"
When [API] the following resource is uploaded to PDS:
| patient_resource                             | resource                                       |
| patients/resource/TEST_PS_Miles_patient.json | patients/resource/TEST_PS_Miles_Encounter.json |
When [API] the following resource is uploaded to PDS:
| patient_resource                             | resource                                                    |
| patients/resource/TEST_PS_Miles_patient.json | patients/resource/TEST_PS_Miles_HistoryAndPhysicalNote.json |
When [API] the Progressive Summarization scheduled event timing is configured to trigger in 2 minutes
And the user waits 2 minutes
Then [API] 2 "DocumentReference" resource with category "http://loinc.org|60591-5" is generated in DPSA for patient "d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a"
And [API] an optional ETL process is started in 60 seconds and finished within 240 seconds with status Succeeded for patient d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a
When the following request is sent to Precision Insights by service user app:
| service         | method | endpoint_path                                           | content_type     |
| patient-summary | GET    | monitoring/patient/d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a | application/json |
Then the response body contains the following text: '"latestTriggeredCutDate":"2004-01-01"'
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then the Progressive Summarization Overview is displayed
And the 'What has changed' section has the title: "Since last oncology encounter (Jan 01, 2004)"

@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68498
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Summary_View/Progressive_summarization
Scenario: aiPatientSummary entity update does not trigger summarization for summarized patient
Given I have replaced the placeholder parameters below with valid UUIDs in 'patients/prepared/ps/TEST_PS_SmartSch2.json' file:
| param_value_to_replace      |
| --PATIENT.ID.2--            |
| --DIAGNOSTIC.REPORT.ID.3--  |
| --DOCUMENT.REFERENCE.ID.2-- |
| --ENCOUNTER.ID.2--          |
| --DIAGNOSTIC.REPORT.ID.4--  |
And I have uploaded the following patient to DPSA: patients/prepared/ps/TEST_PS_SmartSch2.json
And I wait the fulfillment ETL workflows to be finished
And the [DEFAULT-TEST-USER] user logs in to OncoCare
And the user selects the patient with the following Patient ID: <--PATIENT.ID.2-->
When I wait for AI summarization ondemand triggering and report generation to be finished
And the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then the Progressive Summarization Overview is displayed
And the 'What has changed' section has the title: "Since last oncology encounter (Sep 09, 2005)"
When I send the following request to CareIntellect for Oncology application:
| service         | method | endpoint_path                                     |
| patient-summary | GET    | /${ps.monitoring.path}/patient/<--PATIENT.ID.2--> |
Then the received HTTP status code is 200
And the "summaryStatus" parameter should have "ACCEPTED" or "COMPLETED" status
And the user saves the "latestTriggerAt" timestamp for patient "<--PATIENT.ID.2-->" as <latestTriggerAt_noted>
When I have updated the patient summary config with below parameters:
| parameter_name          | updated_parameter_value                                                                                        |
| monitoredListUpdateRule | expression that specifies time in every 20 minutes (e.g. a rule that runs every 20th minute: "*/20 * * * ? *") |
Then the received HTTP status code is 201
When I open the Summary lambda logs in AWS console
Then I see the following log record:
| log_record                                                             |
| "message": "Execute triggering accepted patientId: <--PATIENT.ID.2-->" |
And "<latestTriggerAt_noted>" timestamp is equal to the timestamp of the log record
When I wait for AI summarization scheduled triggering and report generation to be finished (time specified in monitoredListUpdateRule)
Then I do not see new "Execute triggering accepted" record in Summary Event lambda logs for the following patient ID: "<--PATIENT.ID.2-->"
When I send the following request to CareIntellect for Oncology application:
| service         | method | endpoint_path                                     |
| patient-summary | GET    | /${ps.monitoring.path}/patient/<--PATIENT.ID.2--> |
Then the received HTTP status code is 200
And the "latestTriggerAt" timestamp in the response body equals to "latestTriggerAt_noted" timestamp

@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68498
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: 5.1.4 - cutDate date must be older than today
Given [API] the AI data visualization is switched ON for [DEFAULT-TEST-USER] user
And the following patient was deleted from PDS: patients/TEST_PS_Miles.json
And [API] the following resource is not available in PDS:
| resourceFilePath                                            |
| patients/resource/TEST_PS_Miles_HistoryAndPhysicalNote.json |
| patients/resource/TEST_PS_Miles_Encounter.json              |
And [API] all additional "DocumentReference" resources were deleted from DPSA with patient reference ID: "d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a"
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a, triggering the ETL Step Function fulfill-etl-workflow
And the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a
And the dates of the following patient's resources are prepared and uploaded to PDS: patients/prepared/ps/TEST_PS_Miles_prepared.json
And [API] an optional ETL process is started in 60 seconds and finished within 180 seconds with status Succeeded for patient d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a
When [API] the Progressive Summarization scheduled event timing is configured to trigger next minute
Then [API] 1 "DocumentReference" resource with category "http://loinc.org|60591-5" is generated in DPSA for patient "d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a"
And [API] an optional ETL process is started in 60 seconds and finished within 180 seconds with status Succeeded for patient d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a
When the following request is sent to Precision Insights by service user app:
| service         | method | endpoint_path                                           | content_type     |
| patient-summary | GET    | monitoring/patient/d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a | application/json |
Then the response body contains the following text: '"latestTriggeredCutDate":"2002-02-03'
When the [DEFAULT-TEST-USER] user logs in to OncoCare
And the user selects the "TEST_PS, Miles" patient
Then the Progressive Summarization Overview is displayed
And the 'What has changed' section has the title: "Since last oncology encounter (Feb 03, 2002)"

@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68498
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: 5.1.5 - Summarization triggered when configuration update modifies cutDate sort order from DESC to ASC
Given [API] the AI data visualization is switched ON for [DEFAULT-TEST-USER] user
And [API] the following resource is not available in PDS:
| resourceFilePath                                            |
| patients/resource/TEST_PS_Miles_HistoryAndPhysicalNote.json |
| patients/resource/TEST_PS_Miles_Encounter.json              |
And the following patient was deleted from PDS: patients/TEST_PS_Miles.json
And [API] all additional "DocumentReference" resources were deleted from DPSA with patient reference ID: "d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a"
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a, triggering the ETL Step Function fulfill-etl-workflow
And the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a
And [API] the following patient is uploaded to PDS: patients/TEST_PS_Miles.json
And [API] an optional ETL process is started in 60 seconds and finished within 180 seconds with status Succeeded for patient d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a
When [API] the Progressive Summarization scheduled event timing is configured to trigger in 2 minutes
And the user waits 2 minutes
Then [API] 1 "DocumentReference" resource with category "http://loinc.org|60591-5" is generated in DPSA for patient "d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a"
And [API] a mandatory ETL process is started in 180 seconds and finished within 180 seconds with status Succeeded for patient d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a
When the [DEFAULT-TEST-USER] user logs in to OncoCare
And the user selects the "TEST_PS, Miles" patient
Then the Progressive Summarization Overview is displayed
And the 'What has changed' section has the title: "Since last oncology encounter (Sep 09, 2003)"
When the following request is sent to Precision Insights by service user app:
| service         | method | endpoint_path | payload                                                                   | content_type     |
| patient-summary | PUT    | config        | configuration/modified_configs/ps-config-modified-cutdate-sort-order.json | application/json |
Then the received HTTP status code is 201
When [API] the Progressive Summarization scheduled event timing is configured to trigger in 2 minutes
And the user waits 2 minutes
Then [API] 2 "DocumentReference" resource with category "http://loinc.org|60591-5" is generated in DPSA for patient "d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a"
And [API] a mandatory ETL process is started in 180 seconds and finished within 180 seconds with status Succeeded for patient d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then the Progressive Summarization Overview is displayed
And the 'What has changed' section has the title: "Since last oncology encounter (Feb 03, 2002)"

@configChange
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68498
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: 5.1.6 - onDemandHandling was set to MONITORING and does not evaluate triggering - only updates monitoring list
Given [API] the AI data visualization is switched ON for [DEFAULT-TEST-USER] user
And the following patient was deleted from PDS: patients/TEST_PS_Miles.json
And [API] all additional "DocumentReference" resources were deleted from DPSA with patient reference ID: "d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a"
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a, triggering the ETL Step Function fulfill-etl-workflow
And the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a
And [API] the following patient is uploaded to PDS: patients/TEST_PS_Miles.json
And [API] an optional ETL process is started in 60 seconds and finished within 180 seconds with status Succeeded for patient d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a
And the following request was sent to Precision Insights by service user app:
| service         | method | endpoint_path | payload                                                                            | content_type     |
| patient-summary | PUT    | config        | configuration/modified_configs/ps-config-modified-onDemandHandling_monitoring.json | application/json |
And the received HTTP status code was 201
When the [DEFAULT-TEST-USER] user logs in to OncoCare
And the user selects the "TEST_PS, Miles" patient
Then the Progressive Summarization Overview is displayed
And [API] 0 "DocumentReference" resource with category "http://loinc.org|60591-5" is generated in DPSA for patient "d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a"
And [API] the Progressive Summarization monitoring list contains the following patient: d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a
When the following request is sent to Precision Insights by service user app:
| service         | method | endpoint_path                                           | content_type     |
| patient-summary | GET    | monitoring/patient/d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a | application/json |
Then the monitoring response does not contain the following properties:
| property                      |
| latestTriggerAt               |
| latestTriggeredCutDate        |
| latestPatientVersionTriggered |
| summaryId                     |
| summaryStatus                 |

@configChange
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68498
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: 5.1.7 - onDemandHandling was set to NONE and does not evaluate triggering - does not update monitoring list
Given [API] the AI data visualization is switched ON for [DEFAULT-TEST-USER] user
And the following patient was deleted from PDS: patients/TEST_PS_Miles.json
And [API] the patient with ID "d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a" is deleted from PS monitoring list
And [API] all additional "DocumentReference" resources were deleted from DPSA with patient reference ID: "d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a"
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a, triggering the ETL Step Function fulfill-etl-workflow
And the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a
And [API] the following patient is uploaded to PDS: patients/TEST_PS_Miles.json
And [API] an optional ETL process is started in 60 seconds and finished within 180 seconds with status Succeeded for patient d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a
And the following request was sent to Precision Insights by service user app:
| service         | method | endpoint_path | payload                                                                      | content_type     |
| patient-summary | PUT    | config        | configuration/modified_configs/ps-config-modified-onDemandHandling_none.json | application/json |
And the received HTTP status code was 201
When the [DEFAULT-TEST-USER] user logs in to OncoCare
And the user selects the "TEST_PS, Miles" patient
Then the Progressive Summarization Overview is displayed
And [API] 0 "DocumentReference" resource with category "http://loinc.org|60591-5" is generated in DPSA for patient "d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a"
And [API] the Progressive Summarization monitoring list is empty
When the following request is sent to Precision Insights by service user app:
| service         | method | endpoint_path                                           | content_type     |
| patient-summary | GET    | monitoring/patient/d4e9b3f8-6a21-4f5b-9c7d-2e8a1b9c0f3a | application/json |
Then the received HTTP status code is 404
And [API] the Progressive Summarization monitoring list is empty