@cte
@theranostics
@Summary
Feature: [SysRS-68493,SysRS-103457] Theranostics Summary view

SysRS-68493  - Summary page
SysRS-103457 - Summary Dashboard - Theranostics treatment card view

Background:
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded

@SummaryTab
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68493,SysRS-103457
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_-_Automated/Theranostics
@Description:Check_If_none_of_treatments_are_available,_there_should_be_an_empty_state_for:</ul>,If_none_of_treatments_are_available,_there_should_be_an_empty_state_for_treatments_options_in_the_Summary_dashboard_and_card_of_'Radioligand_therapy_options'
Scenario: Empty state cards are displayed in 'Treatments' group with "No Clinical trial options found" and card of 'Radioligand therapy options'
When the user submit GET request to get all configurations by configType treatment
And the user submit DELETE request to delete all fetched configurations type of treatment
And the user submit GET request to get all configurations by configType treatment
Then the response body contains the following text: '"results":[]'
When the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient e0e1117e-8470-462b-b3f7-a5adc78ece44, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue e0e1117e-8470-462b-b3f7-a5adc78ece44
When the following patient was uploaded to PDS: patients/OncoTestPatient_CteEmpty.json
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue e0e1117e-8470-462b-b3f7-a5adc78ece44
When the user waits 90000 milliseconds
And the user clicks on the browser's refresh button
Then the Patient page is loaded
When the user clicks on the browser's refresh button
Then the Patient page is loaded
When the user selects the "OncoTestPatient, CteEmpty" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
And there is a Treatments card with clinical trial options
And the "Treatments" group has 3 cards
And the 2nd card in "Treatments" group equals the following data:
| data_type   | data                       |
| EMPTY_STATE | No radiation therapy found |

@SummaryTab
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68493,SysRS-103457
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_-_Automated/Theranostics
@Description:Check_for_new_theranostics_configuration_on_Summary_Page_before_CTE_AI_trigger:<br/><ul></ul>,Check_statuses_indications:<br/><ul><li>The_trial_status_is_Pending</li></ul>
Scenario: Check that new theranostics are displayed on the Summary page with 'Pending' status when patient's gender is male, before CTE AI trigger
When the user submit GET request to get all configurations by configType treatment
And the user submit DELETE request to delete all fetched configurations type of treatment
And the user submit DELETE request to delete all treatments by treatment configType and by patient c024dded-f5e1-4b5f-9441-5eb840020d75
Then the response body contains the following text: '"results":[]'
When the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient c024dded-f5e1-4b5f-9441-5eb840020d75, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property eventType and propertyValue DATA_FABRIC_DELETE
And the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue c024dded-f5e1-4b5f-9441-5eb840020d75
When [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/Booker_FabianCopyReduced.json
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 1200 seconds with status Succeeded for property parameters.patientId and propertyValue c024dded-f5e1-4b5f-9441-5eb840020d75
And the received HTTP status code is 200
When the user waits 1 minutes
And the user clicks on the browser's refresh button
Then the Patient page is loaded
When the user selects the "Booker, FabianCopyReduced" patient
And the user navigates to the "Summary" view
And the user submits PUT request to update the existing patientID:c024dded-f5e1-4b5f-9441-5eb840020d75 with following details:
| config_id | config_type | trial_configuration_file                     |
| PLUVICTO4 | treatment   | pluvicto/PLUVICTO4-theranostics_as_treatment |
Then the received HTTP status code is 202
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then the "Booker, FabianCopyReduced" patient is selected
And there is a Treatments card with clinical trial options
And the "Treatments" group has 5 cards
And the 5th card in "Treatments" group contains the following data:
| data_type                           | data                         |
| CARD_TITLE                          | Radioligand therapy options  |
| CLINICAL_TRIAL_ROW_BADGE_DATA       | Pending, false |
| CLINICAL_TRIAL_DESCRIPTION          | (ConfigType=treatment) A prescription medicine used to treat men with PSMA+ metastatic castration-resistant prostate cancer (mCRPC). |

@SummaryTab
@SPR-5045,SPR-5055
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68493,SysRS-103457
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_-_Automated/Theranostics
Scenario: Check that theranostics are displayed on the Summary page with final status 'Probable match' after CTE AI trigger
When the user submits PUT request to update the existing patientID:c024dded-f5e1-4b5f-9441-5eb840020d75 with following details:
| config_id | config_type | trial_configuration_file                        |
| PLUVICTO4 | treatment   | pluvicto/PLUVICTO4-theranostics_as_treatment    |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue treatment/PLUVICTO4.json
When the following treatments have been configured for the patients:
| patient                                | list_of_treatments   |
| c024dded-f5e1-4b5f-9441-5eb840020d75   | PLUVICTO4            |
Then the received HTTP status code is 200
And the "message" property in the response matches the value: Ingestion process started
And the mandatory cte-ai-ai-criteria-matching Sfn processes are finished within 900 seconds with status Succeeded for property patients[0].id and propertyValue c024dded-f5e1-4b5f-9441-5eb840020d75
And the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property eventType and propertyValue PATIENT_VERSION_COMPLETED
When the user submit GET request to get treatment by treatment ID: PLUVICTO4
And the user submit GET request to get Eligibility configuration by configType: treatment, configID: PLUVICTO4, patientId: c024dded-f5e1-4b5f-9441-5eb840020d75
Then the "eligibility" property in the response contains the value: LIKELY_NO_MATCH

@SPR-5045
@SummaryTab
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68493,SysRS-103457
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_-_Automated/Theranostics
@Description:Check_that_theranostics_are_not_displayed_on_the_Summary_page_when_the_patient's_gender_is_female:<br/><ul></ul>,Check_for:<br/><ul><li>Theranostics_are_not_displayed_on_the_Summary_page</li></ul><ul>
Scenario: Check that theranostics are not displayed on the Summary page when the patient's gender is female
When the user submit GET request to get all configurations by configType treatment
And the user submit DELETE request to delete all fetched configurations type of treatment
Then the optional criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property patientId and propertyValue 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5
And the response body contains the following text: '"results":[]'
When the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5
And the received HTTP status code is 200
When [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Calculator.json
And the user submits PUT request to update/add trial/s configuration whether exists or not:
| trial_id  | config_type | trial_configuration_file                     |
| PLUVICTO4 | treatment   | pluvicto/PLUVICTO4-theranostics_as_treatment |
Then the received HTTP status code is 202
When the user clicks on the browser's refresh button
And the user selects the "OncoTestPatient, Calculator" patient
And the user navigates to the "Summary" view
Then the "Treatments" group has 3 cards
And the 2nd card in "Treatments" group equals the following data:
| data_type   | data                        |
| EMPTY_STATE | No radiation therapy found  |

@SummaryTab
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68493,SysRS-103457
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_-_Automated/Theranostics
@Description:Check_the_description_in_'Radioligand_therapy_options'_card_is_linkable_and_redirected_the_user_to_'Radioligand'_tab:<br/><ul></ul>,Check_for:<br/><ul><li>Radioligand_therapy_options_card_has_treatment_configuration_displayed</li></ul><ul><li>Clicking_on_treatment_description,_and_check_user_redirected_to_Radioligand_tab</li></ul>
Scenario: Check the description in 'Radioligand therapy options' card is linkable and redirected the user to 'Radioligand' tab
When [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/Booker_FabianCopyReduced.json
Then the optional fulfill-etl-workflow Sfn processes are finished within 1200 seconds with status Succeeded for property parameters.patientId and propertyValue c024dded-f5e1-4b5f-9441-5eb840020d75
And the received HTTP status code is 200
When the user clicks on the browser's refresh button
Then the Patient page is loaded
When the user selects the "Booker, FabianCopyReduced" patient
And the user navigates to the "Summary" view
And there is a Treatments card with clinical trial options
Then the "Treatments" group has 5 cards
And the 5th card in "Treatments" group contains the following data:
| data_type                           | data                         |
| CARD_TITLE                          | Radioligand therapy options  |
| CLINICAL_TRIAL_ROW_BADGE_DATA       | Likely no match, true |
| CLINICAL_TRIAL_DESCRIPTION          | (ConfigType=treatment) A prescription medicine used to treat men with PSMA+ metastatic castration-resistant prostate cancer (mCRPC). |
When the user clicks on 'Radioligand therapy options' description :(ConfigType=treatment) A prescription medicine used to treat men with PSMA+ metastatic castration-resistant prostate cancer (mCRPC).
And the user navigates to the "Radioligand Tx" view
Then the Radioligand view is displayed
When the user selects the following configuration id: PLUVICTO4 from the list of treatments
Then each treatment has its related matching confidence score:
|treatment_id   | Matching_Confidence_Score |
|PLUVICTO4      | Likely no match           |

@SummaryTab
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68493,SysRS-103457,SysRS-103333
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_-_Automated/Theranostics
@Description:Check_'See_all_radioligand_therapy_options'_link_visibility_and_clickability_when_multiple_PLUVICTO_configurations_exist_on_Summary_page:<br/><ul></ul>
Scenario: Check 'See all radioligand therapy options' link visibility and clickability when multiple PLUVICTO configurations exist on Summary page
When the user selects the "Booker, FabianCopy7K_1" patient
And the user navigates to the "Summary" view
And the user submits PUT request to update the existing patientID:f0727397-1a50-4ced-bef1-d2a6908eb3e3 with following details:
| config_id | config_type   | trial_configuration_file  |
| PLUVICTO1 | treatment     | pluvicto/PLUVICTO1        |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue treatment/PLUVICTO1.json
When the user submits PUT request to update the existing patientID:f0727397-1a50-4ced-bef1-d2a6908eb3e3 with following details:
| config_id | config_type   | trial_configuration_file  |
| PLUVICTO2 | treatment     | pluvicto/PLUVICTO2        |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue treatment/PLUVICTO2.json
When the user submits PUT request to update the existing patientID:f0727397-1a50-4ced-bef1-d2a6908eb3e3 with following details:
| config_id | config_type | trial_configuration_file    |
| PLUVICTO3 | treatment   | pluvicto/PLUVICTO3          |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue treatment/PLUVICTO3.json
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then there is a Treatments card with clinical trial options
And the "Treatments" group has 5 cards
When the button "See all radioligand therapy" is displayed and clicked for 'Radioligand therapy options'
Then the Radioligand view is displayed