Feature: [SysRS-CACHE] CACHING

Narrative: Placeholder for caching

@integration
@SRS-CACHE.01
@configChange
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68661
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Automated/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario Outline: New patient data is updated on Timeline view with proactive caching and no caching
Given the following request was sent to Precision Insights by service user app:
| service | method | endpoint_path | payload                | content_type     |
| core    | PUT    | /configs/app  | configuration/<config> | application/json |
And [API] the following resource is not available in PDS:
| resourceFilePath                                             |
| patients/resource/Hanna_Observation_BodyWeight_6ad92729.json |
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient 83a2a278-ff45-e749-4968-bd121b0100c6, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue 83a2a278-ff45-e749-4968-bd121b0100c6
Given [API] the following patient is uploaded to PDS: patients/OncoTestPatient_Hanna.json
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
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
Examples:
| config                          |
| app_config_cache_off.json       |
| app_config_proactive_cache.json |

@integration
@change_detection
@SRS-CACHE.02
@configChange
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68499
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Automated/Functional_Service/Summary_View/Progressive_summarization
Scenario Outline: Progressive summarization data can be refreshed with latest patient data with proactive caching and no caching
Given the following request was sent to Precision Insights by service user app:
| service | method | endpoint_path | payload                | content_type     |
| core    | PUT    | /configs/app  | configuration/<config> | application/json |
And [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And [API] the patient with ID "sLpokXQEPjo8AfP9CxDgyy" is deleted from PS monitoring list
And [API] the following resources are not available in PDS:
| resourceFilePath                                  |
| patients/resource/TEST_PS_Charlie_Encounter2.json |
| patients/resource/TEST_PS_Charlie_DocRef2.json    |
| patients/resource/TEST_PS_Charlie_DocRef3.json    |
And [API] all additional "DocumentReference" resources were deleted from DPSA with patient reference ID: "sLpokXQEPjo8AfP9CxDgyy"
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient sLpokXQEPjo8AfP9CxDgyy, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue sLpokXQEPjo8AfP9CxDgyy
Given [API] the following patient is uploaded to PDS with no fixed wait: patients/TEST_PS_Charlie.json
And [API] the "/summarization" post-processing metadata is generated into the following resources:
| resource                                 |
| DiagnosticReport/aJ4dbYhBxyo8Fy2CXYJXv4  |
| DiagnosticReport/2xLzcJas2NXQsCZYgTGNLF  |
| DocumentReference/9gk39bByqAN1FtcFW8xJq6 |
And [API] a mandatory ETL process is started in 180 seconds and finished within 180 seconds with status Succeeded for patient sLpokXQEPjo8AfP9CxDgyy
And [API] an optional ETL process is started in 60 seconds and finished within 180 seconds with status Succeeded for patient sLpokXQEPjo8AfP9CxDgyy
When the [DEFAULT-TEST-USER] user logs in to OncoCare
And the user selects the "TEST_PS, Charlie" patient
Then the patient summary view is loaded
And the Progressive Summarization Overview is displayed
And the 'What has changed' section has the title: "Since last oncology encounter"
And [API] 1 "DocumentReference" resource with category "http://loinc.org|60591-5" is generated in DPSA for patient "sLpokXQEPjo8AfP9CxDgyy"
And [API] a mandatory ETL process is started in 180 seconds and finished within 180 seconds with status Succeeded for patient sLpokXQEPjo8AfP9CxDgyy
And [API] an optional ETL process is started in 60 seconds and finished within 180 seconds with status Succeeded for patient sLpokXQEPjo8AfP9CxDgyy
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then the patient summary view is loaded
And the following patient sync banner appears in 60000 milliseconds, regex: "Patient data last updated: (0|1) min ago"
And the Progressive Summarization Overview is displayed
And the 'What has changed' section has the title: "Since last oncology encounter (Sep 09, 2003)"
When [API] the following resources are uploaded to PDS with no fixed wait:
| patient_resource                               | resource                                          |
| patients/resource/TEST_PS_Charlie_Patient.json | patients/resource/TEST_PS_Charlie_Encounter2.json |
| patients/resource/TEST_PS_Charlie_Patient.json | patients/resource/TEST_PS_Charlie_DocRef2.json    |
| patients/resource/TEST_PS_Charlie_Patient.json | patients/resource/TEST_PS_Charlie_DocRef3.json    |
Then [API] the "/summarization" post-processing metadata is generated into the following resources:
| resource                                 |
| DocumentReference/qRqiWy4iJ5WyY98dFTRZgm |
| DocumentReference/uQ57rEYfhxm2WjAaPWi7NL |
And [API] a mandatory ETL process is started in 180 seconds and finished within 180 seconds with status Succeeded for patient sLpokXQEPjo8AfP9CxDgyy
And [API] an optional ETL process is started in 60 seconds and finished within 180 seconds with status Succeeded for patient sLpokXQEPjo8AfP9CxDgyy
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then the patient summary view is loaded
And the following patient sync banner appears in 60000 milliseconds, regex: "Patient data last updated: (0|1) min ago"
And the Progressive Summarization Overview is displayed
And the 'What has changed' section has the title: "Since last oncology encounter (Sep 09, 2003)"
When [API] the patient with ID "sLpokXQEPjo8AfP9CxDgyy" is deleted from PS monitoring list
And the user clicks on the browser's refresh button
Then the patient summary view is loaded
And the Progressive Summarization Overview is displayed
And the 'What has changed' section has the title: "Since last oncology encounter (Sep 09, 2003)"
And [API] 2 "DocumentReference" resources with category "http://loinc.org|60591-5" are generated in DPSA for patient "sLpokXQEPjo8AfP9CxDgyy"
And [API] a mandatory ETL process is started in 180 seconds and finished within 180 seconds with status Succeeded for patient sLpokXQEPjo8AfP9CxDgyy
And [API] an optional ETL process is started in 60 seconds and finished within 180 seconds with status Succeeded for patient sLpokXQEPjo8AfP9CxDgyy
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then the patient summary view is loaded
And the following patient sync banner appears in 60000 milliseconds, regex: "Patient data last updated: (0|1) min ago"
And the Progressive Summarization Overview is displayed
And the 'What has changed' section has the title: "Since last oncology encounter (Nov 09, 2003)"
And the 'What has changed' section has the following event list table:
| Event            | Date         | Options                          |
| Pathology Report | Dec 02, 2003 | [REPORT_BUTTON] [IMAGING_BUTTON] |
When the user hovers the 1st source link in 'Summary of recent reports' section
Then the AI tooltip contains the following root resource items:
| report_title     | date         | author    | status  |
| Pathology Report | Dec 02, 2003 | No author | current |
Examples:
| config                          |
| app_config_cache_off.json       |
| app_config_proactive_cache.json |

@integration
@change_detection
@SRS-CACHE.03
@configChange
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68499
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Automated/Functional_Service/Summary_View/Progressive_summarization
Scenario Outline: Summary view is updated with latest data with proactive caching and no caching
Given the following request was sent to Precision Insights by service user app:
| service | method | endpoint_path | payload                | content_type     |
| core    | PUT    | /configs/app  | configuration/<config> | application/json |
And [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And [API] the following resources are not available in PDS:
| resourceFilePath                                    |
| patients/resource/Sigrid_Oncology_Note_a222abc.json |
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient 7cfe48ab-eb05-9ee1-84a4-f59c8a84e05a, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue 7cfe48ab-eb05-9ee1-84a4-f59c8a84e05a
Given [API] the following patient is uploaded to PDS: patients/OncoTestPatient_Sigrid.json
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the page is reloaded
And the "OncoTestPatient, Sigrid" patient is selected
And the "Summary" view is selected
And the "Oncology note" group has 1 card
And the 1st card in "Oncology note" group equals the following data:
| data_type   | data                   |
| EMPTY_STATE | No oncology note found |
When [API] the following resources are uploaded to PDS with no fixed wait:
| patient_resource                      | resource                                            |
| patients/resource/Sigrid_Patient.json | patients/resource/Sigrid_Oncology_Note_a222abc.json |
Then [API] a mandatory ETL process is started in 180 seconds and finished within 180 seconds with status Succeeded for patient 7cfe48ab-eb05-9ee1-84a4-f59c8a84e05a
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then the patient summary view is loaded
And the following patient sync banner appears in 30000 milliseconds, regex: "Patient data last updated: (0|1|2|3) min ago"
And the 1st card in "Oncology note" group equals the following data:
| data_type   | data                                              |
| CARD_TITLE  | Oncology note - most recent                       |
| NORMAL_TEXT | History and physical note                         |
| KEY_VALUE   | [key]: Date                 [value]: Mar 30, 2020 |
Examples:
| config                          |
| app_config_cache_off.json       |
| app_config_proactive_cache.json |

@sanity
@change_detection
@configChange
@SRS-CACHE.04
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68511
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Treatment_Adherence
Scenario Outline: Synthetic and future event's planned date updates if the protocol start date was changed - with proactive caching and no caching
Given the following request was sent to Precision Insights by service user app:
| service | method | endpoint_path | payload                | content_type     |
| core    | PUT    | /configs/app  | configuration/<config> | application/json |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_TA, Higgins" patient is selected
And the "Timeline" view is selected
And the timeline toolbar section is displayed
And the user has set the following treatment protocol configuration for the patient:
| view_mode           | protocol                             | trial_arm                           | start_date   |
| Treatment adherence | CC-94676 for Prostate Cancer Patient | CC94676 for Prostate Cancer Patient | Nov 07, 2024 |
And the patient timeline is loaded
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label             | date_on_timeline_axis |
| MARKER     | Androgen Receptor Degrade | Nov 07, 2024          |
Then the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type   | badge_text |
| STATUS_BADGE | Done       |
And the sidebar contains the following adherence dates:
| date_type      | value        |
| Planned date   | Nov 07, 2024 |
| Scheduled date | Nov 07, 2024 |
| Actual date    | Nov 07, 2024 |
When the user clicks on the following event point on the "Imaging" swimlane:
| event_type | name_on_label | date_on_timeline_axis |
| MARKER     | PSMA-PET scan | May 12, 2025          |
Then the sidebar is displayed
And the sidebar contains the following adherence dates:
| date_type      | value        |
| Planned date   | May 09, 2025 |
| Scheduled date | May 12, 2025 |
| Actual date    | Not found    |
When the user sets the following treatment protocol configuration for the patient:
| start_date   |
| Nov 08, 2024 |
Then the patient timeline is loaded
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label             | date_on_timeline_axis |
| MARKER     | Androgen Receptor Degrade | Nov 07, 2024          |
Then the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type   | badge_text |
| STATUS_BADGE | Done       |
And the sidebar contains the following adherence dates:
| date_type      | value        |
| Planned date   | Nov 08, 2024 |
| Scheduled date | Nov 07, 2024 |
| Actual date    | Nov 07, 2024 |
When the user clicks on the following event point on the "Imaging" swimlane:
| event_type | name_on_label | date_on_timeline_axis |
| MARKER     | PSMA-PET scan | May 12, 2025          |
Then the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type   | badge_text |
| STATUS_BADGE | Missed     |
And the sidebar contains the following adherence dates:
| date_type      | value        |
| Planned date   | May 10, 2025 |
| Scheduled date | May 12, 2025 |
| Actual date    | Not found    |
When the user sets the following treatment protocol configuration for the patient:
| start_date   |
| Nov 25, 2024 |
Then the patient timeline is loaded
When the user clicks on the following event point on the "Imaging" swimlane:
| event_type | name_on_label | date_on_timeline_axis |
| MARKER     | PSMA PET scan | May 27, 2025          |
Then the sidebar is displayed
And the sidebar contains the following adherence dates:
| date_type      | value        |
| Planned date   | May 27, 2025 |
| Scheduled date | Not found    |
Examples:
| config                          |
| app_config_cache_off.json       |
| app_config_proactive_cache.json |