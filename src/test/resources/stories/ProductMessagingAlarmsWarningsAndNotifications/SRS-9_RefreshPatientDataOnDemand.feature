@safety
Feature: [SysRS-68664] Refresh Patient Data On Demand

Narrative: The system shall provide the possibility to refresh patient data on demand. In a way that the user can continue to browse outdated data whilst having the choice of refreshing on demand.


@SRS-9.01
@ToPlaywright
@edge
@integration
@change_detection
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68664
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Automated/Data_Security_Data_Privacy_And_Service
Scenario: Refresh timeline from notification banner when new and updated patient data are available
Given the user is logged out from OncoCare application
And [API] the following resources are not available in PDS:
| resourceFilePath                                       |
| patients/resource/Hanna_Procedure_5f2ed53e.json        |
| patients/resource/Hanna_DiagnosticReport_d17b645f.json |
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient 83a2a278-ff45-e749-4968-bd121b0100c6, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue 83a2a278-ff45-e749-4968-bd121b0100c6
Given [API] the following patient is uploaded to PDS: patients/OncoTestPatient_Hanna.json
And [API] the following resource is uploaded to PDS:
| patient_resource                     | resource                                               |
| patients/resource/Hanna_Patient.json | patients/resource/Hanna_DiagnosticReport_d17b645f.json |
And [API] an optional ETL process is started in 120 seconds and finished within 180 seconds with status Succeeded for patient 83a2a278-ff45-e749-4968-bd121b0100c6
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Hanna" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the "Pathology and Labs" swimlane contains the following events:
| event_type | name_on_label          |
| MARKER     | Diagnostic Report Test |
And the following event is not available on the "Treatment and Plan" swimlane:
| event_type | name_on_label              |
| MARKER     | Throat culture (procedure) |
And the following event point is selected on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Diagnostic Report Test |
And the sidebar content contains the following data:
| data_type | title                | value                              |
| KEY-VALUE | Conclusion           | Conclusion of L2 details - default |
| KEY-VALUE | Status               | final                              |
| LINK      | Open complete report | <N/A>                              |
When [API] the following resources are uploaded to PDS:
| patient_resource                     | resource                                                       |
| patients/resource/Hanna_Patient.json | patients/resource/Hanna_Procedure_5f2ed53e.json                |
| patients/resource/Hanna_Patient.json | patients/resource/Hanna_DiagnosticReport_d17b645f_updated.json |
Then [API] a mandatory ETL process is started in 180 seconds and finished within 180 seconds with status Succeeded for patient 83a2a278-ff45-e749-4968-bd121b0100c6
And [API] an optional ETL process is started in 120 seconds and finished within 180 seconds with status Succeeded for patient 83a2a278-ff45-e749-4968-bd121b0100c6
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then the patient timeline is reloaded
And the following patient sync banner appears in 30000 milliseconds, regex: "Patient data last updated: (0|1) min ago"
And the "Pathology and Labs" swimlane contains the following events:
| event_type | name_on_label                  |
| MARKER     | Diagnostic Report Test Changed |
And the "Treatment and Plan" swimlane contains the following events:
| event_type | name_on_label              |
| MARKER     | Throat culture (procedure) |
And the sidebar is displayed
And the sidebar header contains the following data:
| title                          | creation_date | report_type        |
| Diagnostic Report Test Changed | May 11, 2015  | Pathology and Labs |
And the sidebar content contains the following data:
| data_type | title                | value                                |
| KEY-VALUE | Conclusion           | Conclusion of L2 details - version 1 |
| KEY-VALUE | Status               | final                                |
| LINK      | Open complete report | <N/A>                                |
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label              |
| MARKER     | Throat culture (procedure) |
Then the sidebar header contains the following data:
| title                      | creation_date | report_type        |
| Throat culture (procedure) | Jun 05, 2020  | Treatment and Plan |
And the sidebar content contains the following data:
| data_type | title  | value        |
| KEY-VALUE | Status | completed    |
| TABLE     | Note   | <DATA_TABLE> |
And the sidebar contains the following "Note" table:
| Text      | Time       | Author  |
| Test note | 2020-06-05 | <EMPTY> |

@SRS-9.02
@integration
@change_detection
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68664
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Automated/Data_Security_Data_Privacy_And_Service
Scenario: The latest available data is loaded when refreshing patient data from patient banner (Last sync with hospital systems is updated)
Given the user is logged out from OncoCare application
And [API] the following resources are not available in PDS:
| resourceFilePath                                       |
| patients/resource/Hanna_DiagnosticReport_d17b645f.json |
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient 83a2a278-ff45-e749-4968-bd121b0100c6, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue 83a2a278-ff45-e749-4968-bd121b0100c6
Given [API] the following patient is uploaded to PDS: patients/OncoTestPatient_Hanna.json
And [API] the following resource is uploaded to PDS:
| patient_resource                     | resource                                               |
| patients/resource/Hanna_Patient.json | patients/resource/Hanna_DiagnosticReport_d17b645f.json |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Hanna" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the "Pathology and Labs" swimlane contains the following events:
| event_type | name_on_label          |
| MARKER     | Diagnostic Report Test |
And the following event point is selected on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Diagnostic Report Test |
And the sidebar content contains the following data:
| data_type | title                | value                              |
| KEY-VALUE | Conclusion           | Conclusion of L2 details - default |
| KEY-VALUE | Status               | final                              |
| LINK      | Open complete report | <N/A>                              |
When [API] the following resource is uploaded to PDS:
| patient_resource                     | resource                                                       |
| patients/resource/Hanna_Patient.json | patients/resource/Hanna_DiagnosticReport_d17b645f_updated.json |
Then the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When [API] the following resource is uploaded to PDS:
| patient_resource                     | resource                                                         |
| patients/resource/Hanna_Patient.json | patients/resource/Hanna_DiagnosticReport_d17b645f_updated_2.json |
And the user waits ${change.detection.timeout.in.millisec} milliseconds
And the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then the patient timeline is reloaded
And the following patient sync banner appears in 30000 milliseconds, regex: "Patient data last updated: (0|1) min ago"
And the "Pathology and Labs" swimlane contains the following events:
| event_type | name_on_label                    |
| MARKER     | Diagnostic Report Test Changed 2 |
And the sidebar is displayed
And the sidebar header contains the following data:
| title                            | creation_date | report_type        |
| Diagnostic Report Test Changed 2 | May 11, 2015  | Pathology and Labs |
And the sidebar content contains the following data:
| data_type | title                | value                                |
| KEY-VALUE | Conclusion           | Conclusion of L2 details - version 2 |
| KEY-VALUE | Status               | final                                |
| LINK      | Open complete report | <N/A>                                |

@manual
@SRS-9.03
@integration
@change_detection
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68664
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: The latest available data is loaded when refreshing patient data from patient banner (Last sync with hospital systems is NOT updated)
And [API] the following patient is uploaded to PDS: patients/OncoTestPatient_Hanna.json
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Hanna" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following patient sync banner appears in 30000 milliseconds, regex: "Patient data last updated: (0|1|2) min ago"
And I opened the "Network" tab in DEV Tools
When the following request is sent to Precision Insights by service user app:
| service     | method | endpoint_path                                | payload                                                              | content_type     |
| fulfillment | PUT    | ${fulfillment.base.url}/value-set/weight-vs  | configuration/modified_configs/fulfillment/valueset/weight-vs_1.json | application/json |
Then the received HTTP status code was 200
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
And I noted the "latestVersion" parameter from the latest /status response as <LATEST_VERSION_1>
When the following request is sent to Precision Insights by service user app:
| service     | method | endpoint_path                                | payload                                                         | content_type     |
| fulfillment | PUT    | ${fulfillment.base.url}/value-set/weight-vs  | configuration/repo/default/fulfillment/valuesets/weight-vs.json | application/json |
Then the received HTTP status code was 200
And I have waited for the next /status request in the application
And I noted the "latestVersion" parameter from the latest /status response as <LATEST_VERSION_2>
And I compare the <LATEST_VERSION_1> with the <LATEST_VERSION_2>
Then <LATEST_VERSION_1> and <LATEST_VERSION_2> have different timestamps
When I click on on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then the following patient sync banner appears in 30000 milliseconds, regex: "Patient data last updated: (0|1|2) min ago"
And the following endpoints were called with query param "?version=<LATEST_VERSION_2>":
| endpoint  |
| /banner   |
| /timeline |