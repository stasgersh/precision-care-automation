@cte
@theranostics
@RadioligandTabAfterCTE
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
@Traceability:SysRS-102472,SysRS-103333,SysRS-103334,SysRS-103455,SysRS-103450,SysRS-103452
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_-_Automated/Theranostics/Radioligand_tab
Scenario: Check 'Radioligand' tab after CTE AI trigger for Final status 'Likely no match', criteria/subcriteria also 'Exclusion not met' and proper card details
When [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/Booker_FabianCopyReduced.json
Then the optional fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue c024dded-f5e1-4b5f-9441-5eb840020d75
And the received HTTP status code is 200
When the user selects the "Booker, FabianCopyReduced" patient
Then the patient summary view is loaded
When the user submits PUT request to update the existing patientID:c024dded-f5e1-4b5f-9441-5eb840020d75 with following details:
| config_id | config_type | trial_configuration_file      |
| PLUVICTO1 | treatment   | pluvicto/PLUVICTO1   |
| PLUVICTO2 | treatment   | pluvicto/PLUVICTO2   |
| PLUVICTO3 | treatment   | pluvicto/PLUVICTO3   |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue treatment/PLUVICTO3.json
And the following patient sync banner appears in 120000 milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
And the user navigates to the "Summary" view
Then the patient summary view is loaded
And there is a Radioligand card with clinical therapy options
And the "Treatments" group has 5 cards
When the button "See all radioligand therapy options" is displayed and clicked for 'Radioligand therapy options'
And the following treatments have been configured for the patients:
| patient                                | list_of_treatments   |
| c024dded-f5e1-4b5f-9441-5eb840020d75   | PLUVICTO1            |
Then the received HTTP status code is 200
And the "message" property in the response matches the value: Ingestion process started
And the mandatory cte-ai-ai-criteria-matching Sfn processes are finished within 900 seconds with status Succeeded for property patients[0].id and propertyValue c024dded-f5e1-4b5f-9441-5eb840020d75
And the mandatory criteria-criteria-calculator Sfn processes are finished within 1500 seconds with status Succeeded for property eventType and propertyValue PATIENT_VERSION_COMPLETED
When the user submit GET request to get treatment by treatment ID: PLUVICTO1
And the user submit GET request to get Eligibility configuration by configType: treatment, configID: PLUVICTO1, patientId: c024dded-f5e1-4b5f-9441-5eb840020d75
Then the "eligibility" property in the response contains the value: LIKELY_NO_MATCH
And the following patient sync banner appears in 90000 milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
And the user selects the following configuration id: PLUVICTO1 from the list of treatments
Then each treatment has its related matching confidence score:
|treatment_id  |Matching_Confidence_Score |
|PLUVICTO1     |Likely no match           |
And the criteria list view contains the following values:
| Eligibility_criteria                                                                        | Status            | Type      | Eligibility_values      |
| Member has a current Eastern Cooperative Oncology Group (ECOG) performance status of 0 to 2 | Missing           | Inclusion | N/A                     |
| Disease is prostate-specific membrane antigen (PSMA)-positive, as confirmed on positive emission tomography (PET) or computed tomography (CT) scan    | Missing | Inclusion | The inclusion criterion |
| Previously treated with androgen receptor pathway inhibitor (ARPI) therapy                  | Inclusion not met | Inclusion | The inclusion criterion |
| Diagnosis of metastatic castration-resistant prostate cancer (mCRPC)                        | Inclusion not met | Inclusion | The patient has a confirmed |
| Patient meets ONE of the following:                                                         | Inclusion met     | Inclusion | The patient has metastatic |
| Gender is male                                                                              | Inclusion met     | Inclusion | male    |
| Age >= 18 years                                                                             | Inclusion met     | Inclusion | 67      |
| Member does not have severe renal impairment (CrCl 29 mL/min or less) or end-stage renal disease (ESRD)  | Exclusion met | Exclusion | The exclusion criterion |
When the user expands criteria name starts with "Member does not have severe renal impairment (CrCl 29 mL/min or less)", to check the inner conditions
Then the user checks the inner content has the following data:
| header            | status            | type  | eligibility_value |
| CrCl <= 29 mL/min | Exclusion met     | Empty | The exclusion criterion |
| Patient has ESRD  | Exclusion not met | Empty | The exclusion criterion |
And the sub-criteria CrCl <= 29 mL/min followed by logical operator - And
Then the tooltip showed-up for criteria CrCl <= 29 mL/min, with the following source description: 'Sourced or produced by AI'
When the user clicks on the title of the following root resource item on the AI tooltip:
| report_title     | date         | author    | status  |
| Outpatient Note  | Apr 13, 2014 | No author | current |
Then the full report modal is displayed on the screen
And the full report modal body contains the following data: trials/source_info_ai/Outpatient_note_APR_13_2014.txt

@RadioligandTab
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-102472,SysRS-103333,SysRS-103334,SysRS-103455,SysRS-103450
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_-_Automated/Theranostics/Radioligand_tab
Scenario: Check 'Radioligand' tab after CTE AI trigger for Final status 'Strong match'
When the user clicks on the browser's refresh button
Then the Patient page is loaded
When the user selects the "Booker, FabianCopy7K_0" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
And there is a Radioligand card with clinical therapy options
When the user clicks on any Radioligand's available description
And the user navigates to the "Radioligand Tx" view
Then the Radioligand view is displayed
When the user submits PUT request to update the existing patientID:b5f3375a-11a8-4946-baa6-bb4d0ef3ade9 with following details:
| config_id | config_type   | trial_configuration_file                      |
| PLUVICTO4 | treatment     | pluvicto/PLUVICTO4-theranostics_as_treatment  |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue treatment/PLUVICTO4.json
When the following treatments have been configured for the patients:
| patient                                | list_of_treatments   |
| b5f3375a-11a8-4946-baa6-bb4d0ef3ade9   | PLUVICTO4            |
Then the received HTTP status code is 200
And the "message" property in the response matches the value: Ingestion process started
And the mandatory cte-ai-ai-criteria-matching Sfn processes are finished within 900 seconds with status Succeeded for property patients[0].id and propertyValue b5f3375a-11a8-4946-baa6-bb4d0ef3ade9
And the optional criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property eventType and propertyValue PATIENT_VERSION_COMPLETED
When the user submit GET request to get treatment by treatment ID: PLUVICTO4
And the user submit GET request to get Eligibility configuration by configType: treatment, configID: PLUVICTO4, patientId: b5f3375a-11a8-4946-baa6-bb4d0ef3ade9
Then the "eligibility" property in the response contains the value: STRONG_MATCH
And the following patient sync banner appears in 120000 milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
And the user selects the following configuration id: PLUVICTO4 from the list of treatments
Then each treatment has its related matching confidence score:
|treatment_id | Matching_Confidence_Score |
|PLUVICTO4    | Strong match              |
And the patient's treatment basic data is available for PLUVICTO4 and check the following fields and data:
| Field                 | Field content  |
| Treatment name        | (ConfigType=treatment) A prescription medicine used to treat men with PSMA+ metastatic castration-resistant prostate cancer (mCRPC). |
| Treatment description | PLUVICTO is a radioligand therapy (RLT). RLT is a different type of radiation therapy that is injected and that targets a biomarker called PSMA, which is present on many prostate cancer cells and also some healthy cells. This means it is designed to find and attack PSMA+ cells, including cancer cells. |
| Matching score        | Strong match |
And the criteria list view contains the following values:
| Eligibility_criteria                                                          | Status        | Type          | Eligibility_values |
| Disease is prostate-specific membrane antigen (PSMA)-positive, as confirmed on positive emission tomography (PET) or computed tomography (CT) scan | Missing | Inclusion | The inclusion criterion  |
| Diagnosis of metastatic castration-resistant prostate cancer (mCRPC)          | Inclusion met | Inclusion     | The inclusion criterion   |
| Previously treated with androgen receptor pathway inhibitor (ARPI) therapy    | Inclusion met | Inclusion     | The inclusion criterion   |
| Patient meets ONE of the following:                                           | Inclusion met | Inclusion     | The inclusion criterion   |
| Gender is male                                                                | Inclusion met | Inclusion     | male                      |
| Age >= 18 years                                                               | Inclusion met | Inclusion     | 67                        |
| Member has a current Eastern Cooperative Oncology Group (ECOG) performance status of 0 to 2   | Inclusion met | Inclusion | 1             |
| Member does not have severe renal impairment (CrCl 29 mL/min or less) or end-stage renal disease (ESRD) | Exclusion not met   | Exclusion | The exclusion criterion specifies  |

@RadioligandTab
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-102472,SysRS-103333,SysRS-103334,SysRS-103455,SysRS-103450
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_-_Automated/Theranostics/Radioligand_tab
Scenario: Check 'Radioligand' tab after CTE AI trigger for Final status 'Complete match'
When [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/Booker_FabianCopy7K_0.json
Then the optional fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue b5f3375a-11a8-4946-baa6-bb4d0ef3ade9
And the received HTTP status code is 200
When the user submit GET request to get treatment by treatment ID: PLUVICTO7
And the user submit GET request to get Eligibility configuration by configType: treatment, configID: PLUVICTO7, patientId: b5f3375a-11a8-4946-baa6-bb4d0ef3ade9
Then the "eligibility" property in the response contains the value: COMPLETE_MATCH
When the user clicks on the browser's refresh button
Then the Patient page is loaded
When the user selects the "Booker, FabianCopy7K_0" patient
Then the patient summary view is loaded
And there is a Radioligand card with clinical therapy options
And the "Treatments" group has 7 cards
When the user clicks on any Radioligand's available description
And the user navigates to the "Radioligand Tx" view
Then the Radioligand view is displayed
When the user selects the following configuration id: PLUVICTO7 from the list of treatments
Then each treatment has its related matching confidence score:
|treatment_id   | Matching_Confidence_Score |
|PLUVICTO7      | Complete match            |
And the patient's treatment basic data is available for PLUVICTO7 and check the following fields and data:
| Field                 | Field content  |
| Treatment name        | Complete match - test |
| Treatment description | PLUVICTO is a radioligand therapy (RLT). RLT is a different type of radiation therapy that is injected and that targets a biomarker called PSMA, which is present on many prostate cancer cells and also some healthy cells. This means it is designed to find and attack PSMA+ cells, including cancer cells. |
| Matching score        | Complete match |