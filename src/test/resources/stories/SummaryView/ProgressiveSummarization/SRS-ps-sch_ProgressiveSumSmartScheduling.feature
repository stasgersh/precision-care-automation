Feature: [SysRS-ps-sch] Progressive Summarization - Smart Scheduling

Narrative: Progressive Summarization - Smart Scheduling F19409 (No requirement in SRS for this feature)

@deletePatientAfterTest
@sanity
@F19409.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68498
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: Patient is automatically added to monitoring list and progressive summary report is generated for the patient at a scheduled time
Given the user is logged out from OncoCare application
And [API] the Progressive Summarization scheduled event timing is configured not to trigger next minute
When [API] the ids of the following patient's resources are prepared and uploaded to PDS: patients/prepared/ps/TEST_PS_SmartSch1.json
Then [API] 0 "DocumentReference" resource with category "http://loinc.org|60591-5" is generated in DPSA for patient "--PATIENT.ID.1--"
When [API] the Progressive Summarization scheduled event timing is configured to trigger next minute
Then [API] the Progressive Summarization monitoring list contains the following patient: --PATIENT.ID.1--
And [API] 1 "DocumentReference" resource with category "http://loinc.org|60591-5" is generated in DPSA for patient "--PATIENT.ID.1--"
When [API] the ids of the following patient's resources are prepared and uploaded to PDS: patients/prepared/ps/TEST_PS_SmartSch1_additionalResource.json
And [API] the Progressive Summarization scheduled event timing is configured to trigger next minute
Then [API] 2 "DocumentReference" resource with category "http://loinc.org|60591-5" is generated in DPSA for patient "--PATIENT.ID.1--"

@deletePatientAfterTest
@sanity
@F19409.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68498
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: Patient is added to monitoring list and progressive summary report is generated for the patient when user selects a patient who was not on the monitoring list
Given [API] the Progressive Summarization monitoring list event timing is configured not to trigger next minute
And [API] the ids of the following patient's resources are prepared and uploaded to PDS: patients/prepared/ps/TEST_PS_SmartSch2.json
And [API] the AI data visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
When the user selects the patient with id --PATIENT.ID.2--
Then the patient summary view is loaded
And the Progressive Summarization Overview is displayed
And the 'Brief history' section has the following empty state message: "No reports found"
And the 'Summary of recent reports' section has the following empty state message: "No reports found"
And the 'What has changed' section has the following empty state message: "No new events found"
And [API] the Progressive Summarization monitoring list contains the following patient: --PATIENT.ID.2--
And [API] a mandatory ETL process is started in 180 seconds and finished within 180 seconds with status Succeeded for patient --PATIENT.ID.2--
And [API] a mandatory ETL process is started in 300 seconds and finished within 300 seconds with status Succeeded for patient --PATIENT.ID.2--
And 1 optional ETL processes are finished within 600 seconds with status Succeeded for patient --PATIENT.ID.2--
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then the Progressive Summarization Overview is displayed
And the 'Brief history' section has an AI badge with text: "AI generated"
And the 'Summary of recent reports' section has an AI badge with text: "AI generated"
And the 'What has changed' section has the title: "Since last oncology encounter (Sep 09, 2005)"

@sanity
@F19409.03
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68498
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: Patients can be deleted from PS monitoring list
Given [API] the following bundle is uploaded to DPSA if not uploaded before: patients/prepared/ps/TEST_PS_SmartSch3.json
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "TEST_PS, SmartSch3" patient
Then the Progressive Summarization Overview is displayed
And [API] the Progressive Summarization monitoring list contains the following patient: patient-ps-schedule
When [API] the Progressive Summarization monitoring list is deleted
Then [API] the Progressive Summarization monitoring list is empty

@deletePatientAfterTest
@manual
@sanity
@F19409.04
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68498
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Manual/Summary_View/Progressive_summarization
Scenario: Patient is deleted from PS monitoring list when TTL is expired
Given I have replaced the placeholder parameters below with valid UUIDs in 'patients/prepared/ps/TEST_PS_SmartSch2.json' file:
| param_value_to_replace      |
| --PATIENT.ID.2--            |
| --DIAGNOSTIC.REPORT.ID.3--  |
| --DOCUMENT.REFERENCE.ID.2-- |
| --ENCOUNTER.ID.2--          |
| --DIAGNOSTIC.REPORT.ID.4--  |
And I have updated the patient summary config with below parameters:
| parameter_name          | updated_parameter_value                                                                                                |
| monitoredListUpdateRule | expression that specifies time in the past 5 minutes (e.g. a rule that runs every hour at xx:25: "25 * * * ? *")       |
| monitoringTtlSeconds    | 120                                                                                                                    |
And I have uploaded the following patient to DPSA: patients/prepared/ps/TEST_PS_SmartSch2.json
When I send the following request to CareIntellect for Oncology application on behalf of the service user:
| service         | method | endpoint_path          | content_type     |
| patient-summary | GET    | /${ps.monitoring.path} | application/json |
Then the received HTTP status code is 200
And the "patients" list in the response does not contain object with id <<--PATIENT.ID.2-->>
When I login to CareIntellect for Oncology application as [DEFAULT-TEST-USER] user
And I selected the "TEST_PS, SmartSch2" patient
Then the patient summary view is loaded
And the Progressive Summarization Overview is displayed
When I send the following request to CareIntellect for Oncology application on behalf of the service user:
| service         | method | endpoint_path          | content_type     |
| patient-summary | GET    | /${ps.monitoring.path} | application/json |
Then the received HTTP status code is 200
And the "patients" list in the response contains the item with id <<--PATIENT.ID.2-->>
And the "expiresAt" in the item with id <<--PATIENT.ID.2-->> has a unix timestamp representing a date within the next 120 seconds
When I wait until the TTL is expired (120 seconds)
Then the patient with id <<--PATIENT.ID.2-->> disappears from the response of the below request in 2 days (typically within 1 hour):
| service         | method | endpoint_path          | content_type     |
| patient-summary | GET    | /${ps.monitoring.path} | application/json |

@sanity
@change_detection
@F19409.05
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68498
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: Events list since the last oncology encounter is updated if new event is available for the patient
Given [API] the following resource is not available in PDS:
| resourceFilePath                                |
| patients/resource/TEST_PS_Charles_f672ba07.json |
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient 95e74bbb-be19-4d1d-a10b-b5d2d84eb602, triggering the ETL Step Function fulfill-etl-workflow
And the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue 95e74bbb-be19-4d1d-a10b-b5d2d84eb602
And [API] the following patient is uploaded to PDS: patients/TEST_PS_Charles.json
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "TEST_PS, Charles" patient is selected
And the Progressive Summarization Overview is displayed
And the 'What has changed' section has the title: "Since last oncology encounter (Sep 09, 2003)"
And the 'What has changed' section has the following event list table:
| Event                 | Date         | Options                          |
| CT ABDOMEN AND PELVIS | Oct 02, 2003 | [REPORT_BUTTON] [IMAGING_BUTTON] |
When [API] the following resource is uploaded to PDS:
| patient_resource                               | resource                                        |
| patients/resource/TEST_PS_Charles_patient.json | patients/resource/TEST_PS_Charles_f672ba07.json |
Then the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then the patient summary view is loaded
And the Progressive Summarization Overview is displayed
And the 'What has changed' section has the following event list table:
| Event                 | Date         | Options                          |
| Pathology Report      | Oct 05, 2003 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| CT ABDOMEN AND PELVIS | Oct 02, 2003 | [REPORT_BUTTON] [IMAGING_BUTTON] |