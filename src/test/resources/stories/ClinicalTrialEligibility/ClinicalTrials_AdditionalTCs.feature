@cte
Feature: [SysRS-68494] Summary dashboard - Treatment card view: Trial options

Narrative: The system shall display clinical trial option card with top three highest ranking clinical trials and their respective matching confidence score (See SysRS-68446).
             - Trials in Summary dashboard are shown if there is at least one trial available.
             - If none are available there should be an empty state for Clinical trial options in the Summary dashboard.
           stretch: Number of clinical trials to display in the card is a configurable parameter.

@trialBreastConfigChange
@trialProstateConfigChange
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68478,SysRS-68488,SysRS-69205,SysRS-68494
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Trial_Eligibility/Trials_List
@Description:Check_If_none_of_trials_are_available,_there_should_be_an_empty_state_for:</ul>,If_none_of_trials_are_available,_there_should_be_an_empty_state_for_Clinical_trial_options_in_the_Summary_dashboard.
Scenario: Empty state cards are displayed in 'Treatments' group with "No Clinical trial options found"
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the user submit GET request to get all configurations by configType trial
When the user submit DELETE request to delete all fetched configurations type of trial
And the user submit GET request to get all configurations by configType trial
Then the response body contains the following text: '"results":[]'
When the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient e0e1117e-8470-462b-b3f7-a5adc78ece44, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue e0e1117e-8470-462b-b3f7-a5adc78ece44
When the user waits 120000 milliseconds
And the following patient was uploaded to PDS: patients/OncoTestPatient_CteEmpty.json
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue e0e1117e-8470-462b-b3f7-a5adc78ece44
When the user waits 120000 milliseconds
And the user clicks on the browser's refresh button
Then the Patient page is loaded
When the user selects the "OncoTestPatient, CteEmpty" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
And there is a Treatments card with clinical trial options
And the "Treatments" group has 3 cards
And the 3rd card in "Treatments" group equals the following data:
| data_type   | data                            |
| EMPTY_STATE | No Clinical trial options found |

@srs
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68441,SysRs-68445,SysRS-69188,SysRS-68443,SysRS-69187,SysRS-68437,SysRS-68447,SysRS-68446
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Trial_Eligibility
@Description:Clinical_trial_ingestion_-_Check_ingestion_of_omitted_configuration_parameter_-_'studyCompletion'_date:</ul>,Check_for_treatment_eligibility_service_success_response_when_ingested_without_optional_studyCompletion_parameter.
Scenario: Check ingestion of omitted study completion date
Given [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Sigrid.json
When the user submits PUT request to update/add trial/s configuration to an existing patient 7cfe48ab-eb05-9ee1-84a4-f59c8a84e05a whether exists or not with omitted params:
| omitted_param_name |
| studyCompletion    |
Then the received HTTP status code is 202
And the response body contains the following text: '{"id":"trialDefaultId123456","status":"updated","type":"trial","name":"This is a default trial name","description":"This is a default trial description","phase":"1","link":"https://clinicaltrials.gov/study/NCT04428710?a=23"}'

@srs
@UR65654
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68661,SysRS-68663,SysRS-102471,SysRS-102472,SysRS-102527
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
@Description:Clinical_trial_ingestion_-_Check_the_CTE_data_status_notification_when_user_is_lack_of_awareness_about_the_data_update<br/><ul></ul>,Check_for_treatment_eligibility_service_sync_data_indications:<status><br/><ul><li>Bar_is_more_prominent_(larger)_then_in_earlier_version</li><li>Semantic_color_(yellow)_banner_and_warning_icon_shown_below_patient_data_status_bar</li><li>Banner_notification_indicates_that_CTE_data_update_is_still_processing</li></ul>
Scenario: Check the CTE data status notification when user is lack of awareness about the data update
When the user submit GET request to get all configurations by configType trial
And the user submit DELETE request to delete all fetched configurations type of trial
And the user submit GET request to get all configurations by configType trial
Then the response body contains the following text: '"results":[]'
When the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient 7cfe48ab-eb05-9ee1-84a4-f59c8a84e05a, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue 7cfe48ab-eb05-9ee1-84a4-f59c8a84e05a
When [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Sigrid.json
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue 7cfe48ab-eb05-9ee1-84a4-f59c8a84e05a
And the received HTTP status code is 200
When [API] the [DEFAULT-TEST-USER] user turns ON the CLP visualization
And the [DEFAULT-TEST-USER] user logs in to OncoCare
And the user selects the "OncoTestPatient, Sigrid" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
When the user submits PUT request to update the existing patientID:7cfe48ab-eb05-9ee1-84a4-f59c8a84e05a with following details:
| config_id    | config_type | trial_configuration_file             |
| NCT06044900  | trial       | NCT06044900-Trial_All_UsingAI_True   |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue trial/NCT06044900.json
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then the patient summary view is loaded
And the notification message:"Clinical trials are being processed and will be presented once calculation is complete." about the trials calculation process is displayed on "Summary" page and above the trials list
And there is a Treatments card with clinical trial options
When the user clicks "Clinical trials" button
And the user checks for trial/s display in trials list
Then the notification message:"Clinical trials are being processed and will be presented once calculation is complete." about the trials calculation process is displayed on "Trials" page and above the trials list

@srs
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68441,SysRs-68445,SysRS-69188,SysRS-68443,SysRS-69187,SysRS-68437,SysRS-68447,SysRS-68446
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Trial_Eligibility
@Description:Clinical_trial_ingestion_-_Check_ingestion_of_invalid_'studyCompletion'_date_configuration_parameter_format<br/><ul></ul>,Check_for_treatment_eligibility_service_responds_with:<status><br/><ul><li>HTTP_422_Error</li><li>Message:"Invalid_date_format"</li></ul>
Scenario: Check ingestion of invalid study completion date format
Given the user submits PUT request to update/add trial/s configuration whether exists or not with custom data:
| config_field_name | config_field_value    |
| studyCompletion   | invalidDate           |
Then the received HTTP status code is 422
And the response body contains the following text: '{"message": "[{'type': 'value_error', 'loc': ('body',), 'msg': \"Value error, Invalid date format. Please provide a valid date string like 'YYYY-MM' or 'YYYY-MM-DD'\"'
When the user submits PUT request to update/add trial/s configuration whether exists or not with custom data:
| config_field_name | config_field_value    |
| studyCompletion   | ""           |
Then the received HTTP status code is 422
And the response body contains the following text: '{"message": "[{'type': 'value_error', 'loc': ('body',), 'msg': \"Value error, Invalid date format. Please provide a valid date string like 'YYYY-MM' or 'YYYY-MM-DD'\"'

@srs
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68608
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Trial_Eligibility
@Description:Clinical_trial_ingestion_-_Check_ingestion_of_many_calculated_simultaneously_trials</ul>,Check_for_treatment_eligibility_service_success_response_when_ingested_many_calculated_simultaneously_trials.
Scenario: Check ingestion of many calculated simultaneously trials
Given the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient testPatientManyTrialsId, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue testPatientManyTrialsId
When the user submit GET request to get all trials for patient testPatientManyTrialsId
And the user submit DELETE request to delete all treatments by trial configType and by patient testPatientManyTrialsId
And the following patient was uploaded to PDS: trials/many_trials_patinet_bundle.json
And the user submits PUT request to add 100 trials configuration
And the user submit GET request to get all trials for patient testPatientManyTrialsId
Then the received HTTP status code is 200
When the user waits 15000 milliseconds
Then the response contains 100 trials