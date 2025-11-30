@cte
@theranostics
@RadioligandTabBeforeCTE
Feature: [SysRS-103333,SysRS-103334, SysRS-103455 - Theranostics tab, treatment view, matching confidence badges]

Narrative: The application users shall be able to view Theranostics in 'Radioligand Tx" tab.

Background:
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded

@RadioligandTab
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-102472,SysRS-103333,SysRS-103334,SysRS-103450,SysRS-68493,SysRS-103452,SysRS-103454,SysRS-103453
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_-_Automated/Theranostics/Radioligand_tab
Scenario: Check 'Radioligand' tab before CTE AI trigger Temporary status 'Pending' criteria/subcriteria also 'In-Progress'
When the user submit GET request to get all trials for patient c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu
And the user submit DELETE request to delete all fetched configurations type of treatment
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property eventType and propertyValue DATA_FABRIC_DELETE
And the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu
When the user waits 30000 milliseconds
And [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Carlson.json
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu
And the received HTTP status code is 200
When the user waits 40000 milliseconds
And the user clicks on the browser's refresh button
And the user selects the "OncoTestPatient, Carlson" patient
Then the patient summary view is loaded
When the user submits PUT request to update the existing patientID:c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu with following details:
| config_id   | config_type   | trial_configuration_file  |
| PLUVICTO1   | treatment     | pluvicto/PLUVICTO1        |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue treatment/PLUVICTO1.json
And the following patient sync banner appears in 120000 milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then the patient summary view is loaded
And there is a Radioligand card with clinical therapy options
And the "Treatments" group has 4 cards
And the 4th card in "Treatments" group contains the following data:
| data_type                       | data                        |
| CARD_TITLE                      | Radioligand therapy options |
| CLINICAL_TRIAL_ROW_BADGE_DATA   | Pending, false              |
| CLINICAL_TRIAL_DESCRIPTION      | Pluvicto treatment1         |
When the user clicks on 'Radioligand therapy options' description :Pluvicto treatment1
And the user selects the following configuration id: PLUVICTO1 from the list of treatments
Then each treatment has its related matching confidence score:
|treatment_id | Matching_Confidence_Score   |
|PLUVICTO1    | Pending                     |
When the user selects the following configuration id: PLUVICTO1 from the list of treatments
And the user expands criteria name starts with "Patient meets ONE of the following:", to check the inner conditions
Then the user checks the inner content has the following data:
| header                                                                | status      | type  | eligibility_value |
| Patient is considered appropriate to delay taxane-based chemotherapy  | In progress | Empty | N/A               |
| Previous treatment with taxane-based chemotherapy                     | In progress | Empty | N/A               |
And the sub-criteria Patient is considered appropriate to delay taxane-based chemotherapy followed by logical operator - Or
When the user clicks collapse expended criteria name starts with:Patient meets ONE of the following
Then the criteria with title starts with "Patient meets ONE of the following:", was collapsed into initial view
When the user expands criteria name starts with "Member does not have severe renal impairment (CrCl 29 mL/min or less)", to check the inner conditions
Then the user checks the inner content has the following data:
| header            | status      | type  | eligibility_value   |
| CrCl <= 29 mL/min | In progress | Empty | N/A                 |
|Patient has ESRD   | In progress | Empty | N/A                 |
And the sub-criteria CrCl <= 29 mL/min followed by logical operator - And

@RadioligandTab
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-103333,SysRS-103334,SysRS-68493
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_-_Automated/Theranostics/Radioligand_tab
Scenario: Check 'Radioligand' tab closed by clicking 'X' and user redirected to 'Summary' page
When [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Carlson.json
Then the optional fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu
And the received HTTP status code is 200
When the user selects the "OncoTestPatient, Carlson" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
When there is a Radioligand card with clinical therapy options
And the user clicks on 'Radioligand therapy options' description :Pluvicto treatment1
And the user selects the following configuration id: PLUVICTO1 from the list of treatments
And the user clicks the 'X' button to close the 'Radioligand' tab
And the user navigates to the "Summary" view
Then the patient summary view is loaded

@SummaryTab
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68590,SysRS-68591,SysRS-68594,SysRS-68595,SysRS-103450
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_-_Automated/Theranostics/Radioligand_tab
Scenario: Check AI indications are displayed for treatment in main grid view
When the user submits PUT request to update the existing patientID:b5f3375a-11a8-4946-baa6-bb4d0ef3ade9 with following details:
| config_id | config_type   | trial_configuration_file  |
| PLUVICTO5 | treatment     | pluvicto/PLUVICTO5        |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue treatment/PLUVICTO5.json
When the following treatments have been configured for the patients:
| patient                                | list_of_treatments   |
| b5f3375a-11a8-4946-baa6-bb4d0ef3ade9   | PLUVICTO5            |
Then the received HTTP status code is 200
And the "message" property in the response matches the value: Ingestion process started
And the mandatory cte-ai-ai-criteria-matching Sfn processes are finished within 900 seconds with status Succeeded for property patients[0].id and propertyValue b5f3375a-11a8-4946-baa6-bb4d0ef3ade9
And the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property eventType and propertyValue PATIENT_VERSION_COMPLETED
When the user submit GET request to get treatment by treatment ID: PLUVICTO5
And the user selects the "Booker, FabianCopy7K_0" patient
Then the patient summary view is loaded
And there is a Radioligand card with clinical therapy options
When the user clicks on any Radioligand's available description
And the user navigates to the "Radioligand Tx" view
Then the Radioligand view is displayed
When the user selects the following configuration id: PLUVICTO5 from the list of treatments
Then the trial:PLUVICTO5 has the following expected AI data in Grid Details of main section:
|grid_detail_section                    |expected_data  |
|isOverallMatchingScoreAiIconDisplayed  |true           |
|isAIHighlighted                        |true           |
|isDisclaimerIconDisplayed              |true           |
|AiDisclaimerDescription                |The data highlighted in blue or marked on this page is produced by generative artificial intelligence and is intended to supplement the existing patient data, may be incomplete or incorrect, and is not intended to replace the provider's judgment or responsibility to review the source original clinical information. |