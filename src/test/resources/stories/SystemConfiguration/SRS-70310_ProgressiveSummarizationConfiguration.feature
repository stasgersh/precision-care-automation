Feature: [SysRS-70310] Progressive Summarization Configuration

Narrative: The system shall allow GEHC service user to configure progressive summarization with the following:
           - Enable/Disable progressive summarization functionality.
           - Type of events that enumerated in “what has changed”.
           - Type of event that represents last visit.
           Note: Last visit event and types of events for what has changes are data model entities to be configured in the Data configuration (fulfillment), type specific link behavior is additional configuration which is not considered in the scope


@SRS-70310.01
@configChange
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70310
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/System_Configuration
Scenario: Service User to modify the configuration of Progressive summarization via API - Pathology report cutdate and preferred report type order
Given [API] the patient with ID "PavKolUwC5BO" is deleted from PS monitoring list
And the following request was sent to Precision Insights by service user app:
| service         | method | endpoint_path | payload                                                                            | content_type     |
| patient-summary | PUT    | config        | configuration/modified_configs/ps-config-modified-cutdate-and-preferred-order.json | application/json |
And the received HTTP status code was 201
And [API] all additional "DocumentReference" resources were deleted from DPSA with patient reference ID: "PavKolUwC5BO"
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient PavKolUwC5BO, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue PavKolUwC5BO
Given [API] the following patient is uploaded to PDS: patients/TEST_PS_Petter.json
And [API] the "/summarization" post-processing metadata is generated into the following resources:
| resource                       |
| DocumentReference/A2vk5lvJhCwZ |
And [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "TEST_PS, Petter" patient
Then the patient summary view is loaded
And the Progressive Summarization Overview is displayed
And [API] a mandatory ETL process is started in 180 seconds and finished within 180 seconds with status Succeeded for patient PavKolUwC5BO
And [API] a mandatory ETL process is started in 180 seconds and finished within 180 seconds with status Succeeded for patient PavKolUwC5BO
And 1 optional ETL processes are finished within 600 seconds with status Succeeded for patient PavKolUwC5BO
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then the Progressive Summarization Overview is displayed
And the 'What has changed' section has the title: "Since last oncology encounter (Feb 03, 2008)"
When the user clicks "Show all 7 events" on 'What has changed' section
Then the 'What has changed' section has the following event list table:
| Event                           | Date         | Options                          |
| Emergency department note       | Feb 02, 2012 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Radiation oncology Summary note | Oct 07, 2009 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Tumor board Note                | Jul 07, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Progress Notes                  | May 19, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| CT ABDOMEN AND PELVIS 2         | Apr 15, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| CT ABDOMEN AND PELVIS           | Mar 15, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                | Feb 03, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
When the user clicks "Show more" on 'Summary of recent reports' section
And the user hovers the 4th source link in 'Summary of recent reports' section
Then the AI tooltip contains the following root resource items:
| report_title              | date         | author    | status  |
| Emergency department note | Feb 02, 2012 | No author | current |

@SRS-70310.02
@configChange
@Platform:Rest_API
@api
@Category:Negative
@Traceability:SysRS-70310
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/System_Configuration
Scenario Outline: Service User cannot upload an invalid summary config via API
When the following request is sent to Precision Insights by service user app:
| service         | method | endpoint_path | payload       | content_type     |
| patient-summary | PUT    | config        | <config_file> | application/json |
Then the received HTTP status code is 400
Examples:
| config_file                                                                 |
| configuration/invalid_configs/patient-summary/patient-summary-invalid1.json |
| configuration/invalid_configs/patient-summary/patient-summary-invalid2.json |
