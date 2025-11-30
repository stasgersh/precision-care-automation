@safety
Feature: [SysRS-68661] Information Message When New or Updated Data Available

Narrative: The system shall inform the users when new or updated data is available.


Background:
Given the user is logged out from OncoCare application

@SRS-61.01
@edge
@integration
@change_detection
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68661
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Automated/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Notification about new patient data is displayed when new patient related data is available in PDS
Given [API] the following resource is not available in PDS:
| resourceFilePath                                             |
| patients/resource/Hanna_Observation_BodyWeight_6ad92729.json |
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient 83a2a278-ff45-e749-4968-bd121b0100c6, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue 83a2a278-ff45-e749-4968-bd121b0100c6
Given [API] the following patient is uploaded to PDS: patients/OncoTestPatient_Hanna.json
And [API] an optional ETL process is started in 60 seconds and finished within 180 seconds with status Succeeded for patient 83a2a278-ff45-e749-4968-bd121b0100c6
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the page is reloaded
And the "OncoTestPatient, Hanna" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the "Encounters" swimlane contains the following events:
| event_type | text_on_event_point | name_on_label       | date_on_timeline_axis       |
| CLUSTER    | 2                   | 2 events            | Jan 29, 2013 - Feb 25, 2013 |
| MARKER     | <EMPTY>             | Body Weight 80.4 kg | Feb 25, 2014                |
When [API] the following resource is uploaded to PDS:
| patient_resource                     | resource                                                     |
| patients/resource/Hanna_Patient.json | patients/resource/Hanna_Observation_BodyWeight_6ad92729.json |
Then [API] an optional ETL process is started in 60 seconds and finished within 180 seconds with status Succeeded for patient 83a2a278-ff45-e749-4968-bd121b0100c6
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
And the message displayed in the banner with the following parameters:
| banner_message_regex           | button_in_banner | semantic_color                   | bar_size(px) |
| Patient data update available. | Refresh          | low severity notification yellow | 40           |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then the patient timeline is reloaded
And the following patient sync banner appears in 30000 milliseconds, regex: "Patient data last updated: (0|1|2|3) min ago"
When the user clicks on the 'All time' button on the timeline toolbar
Then the "Encounters" swimlane contains the following events:
| event_type | text_on_event_point | name_on_label       | date_on_timeline_axis       |
| CLUSTER    | 2                   | 2 events            | Jan 29, 2013 - Feb 25, 2013 |
| MARKER     | <EMPTY>             | Body Weight 80.4 kg | Feb 25, 2014                |
| MARKER     | <EMPTY>             | Body Weight 85.3 kg | Feb 25, 2016                |
When the user clicks on the following event point on the "Encounters" swimlane:
| event_type | text_on_event_point | name_on_label       | date_on_timeline_axis |
| MARKER     | <EMPTY>             | Body Weight 85.3 kg | Feb 25, 2016          |
Then the sidebar is displayed
And the sidebar header contains the following data:
| title               | creation_date | report_type |
| Body Weight 85.3 kg | Feb 25, 2016  | Encounters  |

@SRS-61.02
@integration
@change_detection
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68661
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Automated/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Notification about new patient data is displayed when updated patient data is available in PDS (patient banner, page refresh)
Given the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient 83a2a278-ff45-e749-4968-bd121b0100c6, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue 83a2a278-ff45-e749-4968-bd121b0100c6
Given [API] the following resource is uploaded to PDS with no fixed wait:
| patient_resource                     | resource                                                        |
| patients/resource/Hanna_Patient.json | patients/resource/Hanna_Observation_ECOG_1177eb9e_original.json |
And [API] the following patient is uploaded to PDS with no fixed wait: patients/OncoTestPatient_Hanna.json
And [API] a mandatory ETL process is started in 180 seconds and finished within 180 seconds with status Succeeded for patient 83a2a278-ff45-e749-4968-bd121b0100c6
And [API] an optional ETL process is started in 60 seconds and finished within 180 seconds with status Succeeded for patient 83a2a278-ff45-e749-4968-bd121b0100c6
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Hanna" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the patient banner includes the following data:
| data_type | title | value |
| KEY-VALUE | ECOG  | 2     |
When [API] the following resource is uploaded to PDS:
| patient_resource                     | resource                                                       |
| patients/resource/Hanna_Patient.json | patients/resource/Hanna_Observation_ECOG_1177eb9e_updated.json |
Then [API] an optional ETL process is started in 60 seconds and finished within 180 seconds with status Succeeded for patient 83a2a278-ff45-e749-4968-bd121b0100c6
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
And the message displayed in the banner with the following parameters:
| banner_message_regex           | button_in_banner | semantic_color                   | bar_size(px) |
| Patient data update available. | Refresh          | low severity notification yellow | 40           |
When the user clicks on the browser's refresh button
Then the patient timeline is reloaded
And the following patient sync banner appears in 30000 milliseconds, regex: "Patient data last updated: (0|1|2|3) min ago"
And the patient banner includes the following data:
| data_type | title | value |
| KEY-VALUE | ECOG  | 3     |

@SRS-61.06
@integration
@change_detection
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68661
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Automated/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Notification about patient data change is not displayed if the data is changed while the corresponding patient was not selected
Given [API] the following resource is not available in PDS:
| resourceFilePath                                                   |
| patients/resource/Hanna_Observation_Condition_Herpes_0aac4eee.json |
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient 83a2a278-ff45-e749-4968-bd121b0100c6, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue 83a2a278-ff45-e749-4968-bd121b0100c6
When [API] the following patient is uploaded to PDS with no fixed wait: patients/OncoTestPatient_Hanna.json
Then [API] a mandatory ETL process is started in 180 seconds and finished within 180 seconds with status Succeeded for patient 83a2a278-ff45-e749-4968-bd121b0100c6
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Hanna" patient is selected
And the user navigated to the "Medical history" view
And the "Comorbidities" section is available in Medical history
And the "Comorbidities" section includes the following data in Medical history:
| data_type   | title | value                  | color |
| EMPTY_STATE | N/A   | No Comorbidities found | grey  |
When the user selects the "OncoTestPatient, Juno" patient
And [API] the following resource is uploaded to PDS:
| patient_resource                     | resource                                                           |
| patients/resource/Hanna_Patient.json | patients/resource/Hanna_Observation_Condition_Herpes_0aac4eee.json |
Then [API] a mandatory ETL process is started in 180 seconds and finished within 180 seconds with status Succeeded for patient 83a2a278-ff45-e749-4968-bd121b0100c6
And [API] an optional ETL process is started in 60 seconds and finished within 180 seconds with status Succeeded for patient 83a2a278-ff45-e749-4968-bd121b0100c6
When the user selects the "OncoTestPatient, Hanna" patient
Then the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
And the message displayed in the banner with the following parameters:
| banner_message_regex           | button_in_banner | semantic_color                   | bar_size(px) |
| Patient data update available. | Refresh          | low severity notification yellow | 40           |
When the user clicks on the browser's refresh button
Then the patient summary view is loaded
And the patient data sync banner is displayed
And the message displayed in the banner matches to regex: "Patient data last updated: (0|1|2|3|4) min ago"
When the user navigates to the "Medical history" view
Then the "Comorbidities" section is available in Medical history
And the "Comorbidities" section contains the following single table:
| Name                              | Effective date |
| Human herpes simplex virus type 2 | Jun 27, 2019   |

@ToPlaywright
@SRS-61.08
@sanity
@change_detection
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68661
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Notification about new patient data is displayed when DPSA CLP and AI summarization post processing is finished
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And [API] the following resources are not available in PDS:
| resourceFilePath                                     |
| patients/resource/Sigrid_DocumentRef_358f2b70.json   |
| patients/resource/Sigrid_ImagingReport_32a61187.json |
| patients/resource/Sigrid_DocumentRef_d2ca0794.json   |
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient 7cfe48ab-eb05-9ee1-84a4-f59c8a84e05a, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue 7cfe48ab-eb05-9ee1-84a4-f59c8a84e05a
Given [API] the following patient is uploaded to PDS: patients/OncoTestPatient_Sigrid.json
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the page is reloaded
And the "OncoTestPatient, Sigrid" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the patient banner includes the following data:
| data_type | title           | value                    |
| KEY-VALUE | ECOG            | No ECOG found            |
| KEY-VALUE | Stage - initial | No Stage - initial found |
| KEY-VALUE | Histology type  | No Histology type found  |
And the full time range is displayed on the timeline
And the "Imaging" swimlane contains the following events:
| event_type | text_on_event_point | name_on_label            | date_on_timeline_axis       |
| MARKER     | <EMPTY>             | Diagnostic imaging study | Apr 25, 2010                |
| MARKER     | <EMPTY>             | Diagnostic imaging study | Jan 24, 2013                |
| MARKER     | <EMPTY>             | Diagnostic imaging study | Jan 25, 2014                |
| CLUSTER    | 4                   | 4 events                 | Jan 28, 2016 - Mar 11, 2016 |
When [API] the following resources are uploaded to PDS with no fixed wait:
| patient_resource                      | resource                                             |
| patients/resource/Sigrid_Patient.json | patients/resource/Sigrid_DocumentRef_358f2b70.json   |
| patients/resource/Sigrid_Patient.json | patients/resource/Sigrid_ImagingReport_32a61187.json |
| patients/resource/Sigrid_Patient.json | patients/resource/Sigrid_DocumentRef_d2ca0794.json   |
Then [API] a mandatory ETL process is started in 180 seconds and finished within 180 seconds with status Succeeded for patient 7cfe48ab-eb05-9ee1-84a4-f59c8a84e05a
And [API] a mandatory ETL process is started in 180 seconds and finished within 180 seconds with status Succeeded for patient 7cfe48ab-eb05-9ee1-84a4-f59c8a84e05a
And 3 optional ETL processes are finished within 500 seconds with status Succeeded for patient 7cfe48ab-eb05-9ee1-84a4-f59c8a84e05a
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
And the user clicks on the browser's refresh button
Then the patient timeline is loaded
And the following patient sync banner appears in 30000 milliseconds, regex: "Patient data last updated: (0|1|2|3) min ago"
And the patient banner includes the following data:
| data_type | title           | value                 |
| CLP_INFO  | ECOG            | 1                     |
| CLP_INFO  | Stage - initial | IIB                   |
| CLP_INFO  | Histology type  | [keywords]: carcinoma |
When the user clicks on the 'All time' button on the timeline toolbar
Then the "Imaging" swimlane contains the following events:
| event_type | text_on_event_point | name_on_label            | date_on_timeline_axis       |
| MARKER     | <EMPTY>             | Diagnostic imaging study | Apr 25, 2010                |
| MARKER     | <EMPTY>             | Diagnostic imaging study | Jan 24, 2013                |
| MARKER     | <EMPTY>             | Diagnostic imaging study | Jan 25, 2014                |
| CLUSTER    | 4                   | 4 events                 | Jan 28, 2016 - Mar 11, 2016 |
When the user clicks on the following event point on the "Imaging" swimlane:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 4                   | 4 events      |
Then the cluster pill is opened
And the following events are displayed in the cluster pill:
| event_name                             | date_on_tooltip |
| Diagnostic imaging study               | Jan 28, 2016    |
| Diagnostic imaging study               | Jan 29, 2016    |
| Diagnostic imaging study               | Jan 30, 2016    |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Mar 11, 2016    |
When the user selects the following event from the cluster pill:
| event_name                             |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST |
Then the sidebar is displayed
And the sidebar header contains the following data:
| title                                  | creation_date | report_type |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Mar 11, 2016  | Imaging     |
And the sidebar content contains the following data:
| data_type | title                | value                                              |
| KEY-VALUE | Conclusion           | STABLE                                             |
| KEY-VALUE | Status               | final                                              |
| LINK      | Open complete report | <N/A>                                              |
| CLP_INFO  | <N/A>                | [keywords]: CT Thorax Abdomen Pelvis with Contrast |