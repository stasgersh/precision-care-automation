@cte
Feature: [SysRS-102471,SysRS-102472,SysRS-102527,SysRS-102473] - CTE-AI Eligibility

SysRS-102527 - Supported FHIR resource types
SysRS-102472 - Eligibility calculation
SysRS-102471 - Triggering CTE AI calculation
The system shall trigger CTE-AI calculation for a determined list of patients and trials.
SysRS-102474 - The system shall support data extraction from the following file formats: pdf,txt, docx, rtf ,html

Narrative: The system shall be able to provide the following calculation for a given patient for a specific trial (by utilizing LLMaaS):
           - List of criterions and their matching result, including for each
           - Matching assessments score (Met, Not met, Couldn’t determine).
           - Reason: A rational for the matching assessment.
           - Source references: links from eligibility details to their corresponding source documents/reports.

Background:
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded

@SPR-5057
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-102471,SysRS-102472,SysRS-102527,SysRS-102528,SysRS-102469
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Trial_Eligibility/CTE-AI
Scenario: Check CTE AI with DocumentReference resources containing > 131072 characters
When [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Dursley.json
Then the optional fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue bfc64820-6c23-44e3-8122-1666431213c4
And the received HTTP status code is 200
When the user submits PUT request to update the existing patientID:bfc64820-6c23-44e3-8122-1666431213c4 with following details:
| config_id         | config_type | trial_configuration_file       |
| NCT06044857-real  | trial       | NCT06044857-real-trial-config  |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue trial/NCT06044857-real.json
When the user clicks on the browser's refresh button
Then the Patient page is loaded
When the user selects the "OncoTestPatient, Dursley" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
When there is a Treatments card with clinical trial options
And the user clicks "Clinical trials" button
And the user checks for trial/s display in trials list
And the following treatments have been configured for the patients:
| patient                                | list_of_treatments   |
| bfc64820-6c23-44e3-8122-1666431213c4   | NCT06044857-real     |
Then the received HTTP status code is 200
And the "message" property in the response matches the value: Ingestion process started
And the mandatory cte-ai-ai-criteria-matching Sfn processes are finished within 900 seconds with status Succeeded for property patients[0].id and propertyValue bfc64820-6c23-44e3-8122-1666431213c4
And the optional criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue trial/NCT06044857-real.json
When the user submit GET request to get treatment by treatment ID: NCT06044857-real
And the user submit GET request to get Eligibility configuration by configType: trial, configID: NCT06044857-real, patientId: bfc64820-6c23-44e3-8122-1666431213c4
Then the "eligibility" property in the response contains the value: LIKELY_NO_MATCH
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then each trial has its related matching confidence score:
|trial_id           |Matching_Confidence_Score|
|NCT06044857-real   |Likely no match          |

@SPR-5058
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-102471,SysRS-102472,SysRS-102527,SysRS-102528,SysRS-102469
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Trial_Eligibility/CTE-AI
Scenario: Check 'Eligibility criteria' in expanded view was not changed to "Unknown" in case CTE-AI couldn't determine eligibility
When the user submits PUT request to update the existing patientID:bfc64820-6c23-44e3-8122-1666431213c4 with following details:
| config_id   | config_type | trial_configuration_file  |
| NCT04457596 | trial       | NCT04457596-trial         |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue trial/NCT04457596.json
When the user submit GET request to get treatment by treatment ID: NCT04457596
Then the received HTTP status code is 200
When the user clicks on the browser's refresh button
Then the Patient page is loaded
When the user selects the "OncoTestPatient, Dursley" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
When there is a Treatments card with clinical trial options
And the user clicks "Clinical trials" button
Then the Trials view is displayed
When the user checks for trial/s display in trials list
And the following treatments have been configured for the patients:
| patient                                | list_of_treatments   |
| bfc64820-6c23-44e3-8122-1666431213c4   | NCT04457596          |
Then the received HTTP status code is 200
And the "message" property in the response matches the value: Ingestion process started
And the mandatory cte-ai-ai-criteria-matching Sfn processes are finished within 900 seconds with status Succeeded for property patients[0].id and propertyValue bfc64820-6c23-44e3-8122-1666431213c4
And the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property eventType and propertyValue PATIENT_VERSION_COMPLETED
When the user submit GET request to get treatment by treatment ID: NCT04457596
And the user submit GET request to get Eligibility configuration by configType: trial, configID: NCT04457596, patientId: bfc64820-6c23-44e3-8122-1666431213c4
Then the "eligibility" property in the response contains the value: LIKELY_NO_MATCH
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then each trial has its related matching confidence score:
|trial_id     |Matching_Confidence_Score|
|NCT04457596  |Likely no match          |
When the user selects the following trial id:NCT04457596 from the list of trials
And the user expands criteria name starts with "Use of a strong CYP3A4 or CYP2C8 inhibitor within 2 weeks, or use of a strong CYP3A4 or CYP2C8 inducer within 5 days prior to registration is prohibited.", to check the inner conditions
Then the user checks the inner content has the following data:
| header   | status  | type  | eligibility_value |
| Use of sensitive CYP3A substrates two weeks before registration and during study treatment. | Missing | Empty | The exclusion criterion specifies the use of CYP3A4 or CYP2C8 inducers as concomitant medications within 5 days following discontinuation of tucatinib treatment. The provided medical history does not include any information about the patient's current or recent medication list, nor does it mention the use of CYP3A4 or CYP2C8 inducers. Without specific details about the patient's medication history or plans, it is impossible to determine whether the patient meets this exclusion criterion. Given the lack of relevant information, the answer must be 'unknown'.  |
| CYP3A4 or CYP2C8 inducers as concomitant medications within 5 days following discontinuation of tucatinib treatment. | Missing | Empty | The exclusion criterion specifies the use of sensitive CYP3A substrates within two weeks before registration and during study treatment. The provided medical history does not include any information about the patient's medication history or use of CYP3A substrates. Without specific details about the patient's medication use, it is impossible to determine whether the patient meets this exclusion criterion.|
| Require medications that are known to be sensitive substrates of CYP3A4 with a narrow therapeutic window. | Missing | Empty | The exclusion criterion specifies the use of sensitive CYP3A substrates within two weeks before registration and during study treatment. The provided medical history does not include any information about the patient's medication history or use of CYP3A substrates. Without specific details about the patient's medication use, it is impossible to determine whether the patient meets this exclusion criterion.|


@sanityTrial
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-102471,SysRS-102472,SysRS-102527
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_-_Automated/Clinical_Trial_Eligibility
@Description:Check_for_status_Pending_during_CTE-AI_calculation_process:<br/><ul></ul>,Pending_status_for_configuration_and_criteria_in_progress
Scenario: Check for status "Pending" during CTE-AI calculation process for new configuration
When the user submit GET request to get all trials for patient c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu
And the user submit DELETE request to delete all fetched configurations type of trial
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property eventType and propertyValue DATA_FABRIC_DELETE
And the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu
When the user waits 30000 milliseconds
And [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Carlson.json
Then the optional fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu
And the received HTTP status code is 200
When the user clicks on the browser's refresh button
Then the Patient page is loaded
When the user selects the "OncoTestPatient, Carlson" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
When the user submits PUT request to update the existing patientID:c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu with following details:
| config_id   | config_type | trial_configuration_file      |
| NCT06044857 | trial       | NCT06044857-All_UsingAI_True  |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue trial/NCT06044857.json
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
And there is a Treatments card with clinical trial options
And the user clicks "Clinical trials" button
Then the Trials view is displayed
When the user checks for trial/s display in trials list
And the user selects the following trial id:NCT06044857 from the list of trials
Then each trial has its related matching confidence score:
|trial_id     |Matching_Confidence_Score|
|NCT06044857  |Pending                  |
And the patient's criteria basic data is available for NCT06044857 and check the following fields and data:
| Field             | Field content  |
| Trial name        | PSMA PET Response Guided SabR in High Risk Pca |
| Trial description | Sequential cohort evaluation of ideal timing of imaging and treatment spacing to discern maximal PSMA (Prostate specific membrane antigen) PET (Positron Emission Tomography) response (PSMA-11 68Ga, Illucix) for adaptation of dominant intra-prostatic lesion tumor boost dose. |
| Matching score    | Pending        |
| Phase             | 1              |
| Status            | Recruiting     |
| Trial_ID          | NCT06044857    |
When the user clicks on the filter icon in the 'Status' column
And the user selects In progress status from the filter's list
Then the criteria view displays criteria with the following statuses:
| status        |
| In progress   |
When the user clicks the X button to close the Trials tab
And the user submits PUT request to update the existing patientID:c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu with following details:
| config_id   | config_type | trial_configuration_file  |
| NCT04428710 | trial       | NCT04428710-Study_III     |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue trial/NCT04428710.json
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
And there is a Treatments card with clinical trial options
Then the "Treatments" group has 4 cards
And the 3rd card in "Treatments" group contains the following data:
| data_type   | data                                                    |
| CARD_TITLE  | Clinical trial options                                  |
| NORMAL_TEXT | Clinical trial options are being calculated and will be shared once the calculation is complete |
| CLINICAL_TRIAL_ROW_BADGE_DATA | No match, false |
| CLINICAL_TRIAL_DESCRIPTION    | Study III to Evaluate the Safety and Tolerability of CC-94676 in Participants With Metastatic Castration-Resistant Prostate Cancer |

@sanityTrial
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-102471,SysRS-102472,SysRS-102527
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_-_Automated/Clinical_Trial_Eligibility
@Description:Check_the_status_of_existing_trials_is_still_displayed_the_same_as_before_the_new_trials_were_added:<br/><ul></ul>,Pending_status_for_new_configuration_and_Strong_Match_for_the_existing_trial
Scenario: Check the status of existing trials is still displayed the same as before the new trials were added
When the user clicks on the browser's refresh button
Then the Patient page is loaded
When the user selects the "OncoTestPatient, Carlson" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
When the user submits PUT request to update the existing patientID:c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu with following details:
| config_id   | config_type | trial_configuration_file    |
| NCT04428712 | trial       | NCT04428712-Study_X         |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue trial/NCT04428712.json
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
And there is a Treatments card with clinical trial options
And the user clicks "Clinical trials" button
Then the Trials view is displayed
When the user checks for trial/s display in trials list
Then each trial has its related matching confidence score:
|trial_id     |Matching_Confidence_Score|
|NCT04428712  |Complete match             |
When the user clicks the X button to close the Trials tab
And the user navigates to the "Summary" view
And the user submits PUT request to update the existing patientID:c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu with following details:
| config_id   | config_type | trial_configuration_file      |
| NCT06044857 | trial       | NCT06044857-All_UsingAI_True  |
Then the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
And there is a Treatments card with clinical trial options
Then the "Treatments" group has 4 cards
And the 3rd card in "Treatments" group contains the following data:
| data_type   | data                                                    |
| CARD_TITLE  | Clinical trial options                                  |
| NORMAL_TEXT | Clinical trial options are being calculated and will be shared once the calculation is complete |
| CLINICAL_TRIAL_ROW_BADGE_DATA | Complete match, false |
| CLINICAL_TRIAL_DESCRIPTION    | Study_X to Evaluate the Safety and Tolerability of CC-94676 in Participants With Metastatic Castration-Resistant Prostate Cancer |
When the user clicks "Clinical trials" button
Then the Trials view is displayed
When the user checks for trial/s display in trials list
And the user selects the following trial id:NCT06044857 from the list of trials
Then each trial has its related matching confidence score:
|trial_id     |Matching_Confidence_Score|
|NCT06044857  |Pending                  |

@sanityTrial
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-102471,SysRS-102472,SysRS-102527
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_-_Automated/Clinical_Trial_Eligibility
@Description:Check_for_trials_statuses_while_criteria_is_calculated:<br/><ul></ul>,Check_statuses_indications:<br/><ul><li>The_trial_status_is_Pending</li><li>The_criteria_status_is_In-progress</li><li>The_eligibility_value_is_N/A</li></ul>
Scenario: Check for trials statuses while criteria is calculated. The trial status pending, criteria status in-progress and its eligibility value N/A
When the user clicks on the browser's refresh button
Then the Patient page is loaded
When the user selects the "OncoTestPatient, Carlson" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
And there is a Treatments card with clinical trial options
When the user clicks "Clinical trials" button
Then the Trials view is displayed
When the user checks for trial/s display in trials list
And the user selects the following trial id:NCT06044857 from the list of trials
Then each trial has its related matching confidence score:
|trial_id     |Matching_Confidence_Score|
|NCT06044857  |Pending                  |
When the user selects the following trial id:NCT06044857 from the list of trials
Then the criteria list view contains the following columns:
| Eligibility criteria |
| Status               |
| Type                 |
| Eligibility value(s) |
And the criteria list view contains the following values:
| Eligibility_criteria  | Status        | Type         | Eligibility_values |
| Gender is male        | In progress   | Inclusion    | N/A                |
When the user hover the eligibility value(s) of "Gender is male" eligibility criteria
Then the tooltip showed-up for criteria Gender is male, with the following source description: 'Sourced or produced by AI'

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-102471,SysRS-102472,SysRS-102527
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Trial_Eligibility/CTE-AI
@Description:Check_for_trials_statuses_while_criteria_is_calculated<br/><ul></ul>,Check_statuses_indications:<br/><ul><li>The_trial_status_is_Pending</li><li>The_criteria_status_In-progress</li><li>The_Type_is_empty</li><li>The_eligibility_value_is_N/A</li></ul>
Scenario: Check for In Progress status in expanded criteria view
Given [API] the following patient is uploaded to PDS: patients/OncoTestPatient_Carlson.json
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu
When the user submits PUT request to update the existing patientID:c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu with following details:
| config_id   | config_type | trial_configuration_file         |
| NCT05568777 | trial       | NCT05568777-with-expand-criteria |
Then the received HTTP status code is 202
And the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue trial/NCT05568777.json
When the user clicks on the browser's refresh button
Then the Patient page is loaded
When the user selects the "OncoTestPatient, Carlson" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
When the following treatments have been configured for the patients:
| patient                                | list_of_treatments   |
| c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu   | NCT05568777          |
Then the received HTTP status code is 200
And the "message" property in the response matches the value: Ingestion process started
And the mandatory cte-ai-ai-criteria-matching Sfn processes are finished within 900 seconds with status Succeeded for property patients[0].id and propertyValue c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu
Then the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then there is a Treatments card with clinical trial options
When the user clicks on any available description
Then the Trials view is displayed
When the user checks for trial/s display in trials list
And the user selects the following trial id:NCT05568777 from the list of trials
And the user expands criteria name starts with "Participants must have been pre- or peri-menopausal", to check the inner conditions
Then the user checks the inner content has the following data:
| header                                                         | status      | type  | eligibility_value |
| Pre- or peri-menopausal at diagnosis                           | In progress | Empty | N/A               |
| Serum or plasma estradiol and/or follicle stimulating hormone  | In progress | Empty | N/A               |
And the sub-criteria Pre- or peri-menopausal at diagnosis followed by logical operator - Or
When the user clicks collapse expended criteria name starts with:Participants must have been pre- or peri-menopausal
Then the criteria with title starts with "Participants must have been pre- or peri-menopausal", was collapsed into initial view

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-102471,SysRS-102472,SysRS-102527,SysRS-102528,SysRS-102469
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Trial_Eligibility/CTE-AI
@Description:Check_eligibility_results_evaluation_after_ingestion_for_new_trial_configuration:<br/><ul></ul>,Check_statuses_indications:<br/><ul><li>The_trial_status_is_Pending_changed_to_Likely_no_match</li><li>The_criteria_status_In-progress_changed_to_Inclusion_not_met</li></ul>
Scenario: Check eligibility results evaluation after ingestion for new trial configuration
When the user submit DELETE request to delete configuration by configType: trial, configID: NCT06044900, patientId: c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu
Then the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu, triggering the ETL Step Function fulfill-etl-workflow
And the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu
When [API] the following patient is uploaded to PDS: patients/OncoTestPatient_Carlson.json
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu
And the received HTTP status code is 200
When the user waits 60000 milliseconds
And the user clicks on the browser's refresh button
Then the Patient page is loaded
When the user selects the "OncoTestPatient, Carlson" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
When the user submits PUT request to update the existing patientID:c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu with following details:
| config_id   | config_type | trial_configuration_file           |
| NCT06044900 | trial       | NCT06044900-Trial_All_UsingAI_True |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue trial/NCT06044900.json
When the user submit GET request to get Eligibility configuration by configType: trial, configID: NCT06044900, patientId: c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu
Then the "eligibility" property in the response contains the value: PENDING
When there is a Treatments card with clinical trial options
And the user clicks on the browser's refresh button
Then the Patient page is loaded
When the user clicks "Clinical trials" button
Then the Trials view is displayed
When the user checks for trial/s display in trials list
Then each trial has its related matching confidence score:
|trial_id     |Matching_Confidence_Score|
|NCT06044900  |Pending                  |
When the user selects the following trial id:NCT06044900 from the list of trials
And the user expands criteria name starts with "Staging 68Ga PSMA-11 PET -CT or -MRI performed within 90 days", to check the inner conditions
Then the user checks the inner content has the following data:
| header                             | status      | type  | eligibility_value |
| Staging 68Ga PSMA-11 PET-CT date   | In progress | Empty | N/A               |
| Staging 68Ga PSMA-11 PET-MRI date  | In progress | Empty | N/A               |
When the following treatments have been configured for the patients:
| patient                                | list_of_treatments   |
| c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu   | NCT06044900          |
Then the received HTTP status code is 200
And the "message" property in the response matches the value: Ingestion process started
And the mandatory cte-ai-ai-criteria-matching Sfn processes are finished within 900 seconds with status Succeeded for property patients[0].id and propertyValue c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu
And the optional criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property eventType and propertyValue PATIENT_VERSION_COMPLETED
When the user submit GET request to get treatment by treatment ID: NCT06044900
And the user submit GET request to get Eligibility configuration by configType: trial, configID: NCT06044900, patientId: c27u6vw3-kc2g-ojvd-ltfv-dbxdc82s3ygu
Then the "eligibility" property in the response contains the value: LIKELY_NO_MATCH
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then each trial has its related matching confidence score:
|trial_id     |Matching_Confidence_Score|
|NCT06044900  |Likely no match          |
When the user selects the following trial id:NCT06044900 from the list of trials
And the user expands criteria name starts with "Staging 68Ga PSMA-11 PET -CT or -MRI performed within 90 days", to check the inner conditions
Then the user checks the inner content has the following data:
| header                             | status      | type  | eligibility_value |
| Staging 68Ga PSMA-11 PET-CT date   | Inclusion not met | Empty | regex:AI_ExpectedExtractionResponse_PET-CT.txt  |
| Staging 68Ga PSMA-11 PET-MRI date  | Inclusion not met | Empty | regex:AI_ExpectedExtractionResponse_PET-MRI.txt |

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-102471,SysRS-102472,SysRS-102527,SysRS-102528,SysRS-102469
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Trial_Eligibility/CTE-AI
@Description:Check_eligibility_results_evaluation_for_global_trial_configuration:<br/><ul></ul>,Check_statuses_indications:<br/><ul><li>The_trial_status_is_Pending_changed_to_Probable_match</li><li>The_criteria_status_In-progress_changed_to_Exclusion_not_met</li></ul>
Scenario: Check eligibility results evaluation for global trial configuration
When the user submit GET request to get all trials for patient testPatientIdCteAiDocRef
And the user submit DELETE request to delete all treatments by trial configType and by patient testPatientIdCteAiDocRef
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient testPatientIdCteAiDocRef, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property eventType and propertyValue DATA_FABRIC_DELETE
And the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue testPatientIdCteAiDocRef
When [API] the following patient is uploaded to PDS: cteAi/OncoTestPatient_Docref_pdf_txt_docx_rtf_html.json
Then [API] a mandatory ETL process is started in 180 seconds and finished within 180 seconds with status Succeeded for patient testPatientIdCteAiDocRef
When the user submits POST request to add the config type of trial, as global configuration from file name: GlobalConfigCTEAI
Then the optional criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property eventType and propertyValue PATIENT_VERSION_COMPLETED
When the user waits 30000 milliseconds
And the user submit GET request to get Eligibility configuration by configType: trial, configID: global-cteai, patientId: testPatientIdCteAiDocRef
Then the "eligibility" property in the response contains the value: PENDING
When the user clicks on the browser's refresh button
And  the user navigates to the initial patient page
Then the Patient page is loaded
When the user selects the "OncoTestPatient, CteAiDocRef" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
When there is a Treatments card with clinical trial options
And the user clicks "Clinical trials" button
Then the Trials view is displayed
When the user checks for trial/s display in trials list
Then each trial has its related matching confidence score:
|trial_id      | Matching_Confidence_Score|
|global-cteai  | Pending                  |
When the user selects the following trial id:global-cteai from the list of trials
And the user clicks on the filter icon in the 'Status' column
And the user selects In progress status from the filter's list
And the user clicks on criteria grid view to hide the selection list
Then the criteria view displays criteria with the following statuses:
| status   |
| In progress  |
When the following treatments have been configured for the patients:
| patient                    | list_of_treatments   |
| testPatientIdCteAiDocRef   | global-cteai          |
Then the received HTTP status code is 200
And the "message" property in the response matches the value: Ingestion process started
And the mandatory cte-ai-ai-criteria-matching Sfn processes are finished within 900 seconds with status Succeeded for property patients[0].id and propertyValue testPatientIdCteAiDocRef
And the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property eventType and propertyValue PATIENT_VERSION_COMPLETED
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then each trial has its related matching confidence score:
|trial_id     | Matching_Confidence_Score |
|global-cteai | Probable match            |
When the user selects the following trial id:global-cteai from the list of trials
Then the calculated status is Exclusion not met for the criteria titled Any significant medical condition, such as uncontrolled infection, laboratory abnormality, or psychiatric illness
When the user hover the eligibility value(s) of "The exclusion criterion" eligibility criteria
Then the tooltip showed-up for criteria Any significant medical condition, such as uncontrolled infection, laboratory abnormality, or psychiatric illness, with the following source description: 'Sourced or produced by AI'

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-102474,SysRS-102471,SysRS-102472,SysRS-102527,SysRS-102528,SysRS-102469
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Trial_Eligibility/CTE-AI
@Description:Check_the_criteria_was_evaluated_results_Support_data_extraction_from_the_following_file_formats:<br/><ul></ul>,1_._Check_for_support_data_extraction_from_the_folowing_file_formats:<br/><ul><li>PDF_/_TXT_/_DOCX_/_RTF_/_HTML</li></ul>2_._Check_for_final_statuses_and_its_evaluation_assesments_results_per_type_of_resoure</li></ul>
Scenario: Check the criteria was recalculated and assessment calculation considered the resource data
When the user submit GET request to get all trials for patient testPatientIdCteAiDocRef
And the user submit DELETE request to delete all treatments by trial configType and by patient testPatientIdCteAiDocRef
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient testPatientIdCteAiDocRef, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property eventType and propertyValue DATA_FABRIC_DELETE
And the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue testPatientIdCteAiDocRef
When [API] the following patient is uploaded to PDS: cteAi/OncoTestPatient_Docref_pdf_txt_docx_rtf_html.json
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue testPatientIdCteAiDocRef
When the user waits 60000 milliseconds
And the user clicks on the browser's refresh button
Then the Patient page is loaded
When the user selects the "OncoTestPatient, CteAiDocRef" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
When the user submits PUT request to update the existing patientID:testPatientIdCteAiDocRef with following details:
| config_id   | config_type | trial_configuration_file      |
| NCT05568903 | trial       | NCT05568903-CTE_AI_SupportedResourcesFormats_PDF_TXT_HTML_RTF_DOCX  |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue trial/NCT05568903.json
When the user waits 60000 milliseconds
Then the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
And there is a Treatments card with clinical trial options
And the user clicks "Clinical trials" button
Then the Trials view is displayed
When the user checks for trial/s display in trials list
And the user selects the following trial id:NCT05568903 from the list of trials
And the user submit GET request to get treatment by treatment ID: NCT05568903
And the user submit GET request to get Eligibility configuration by configType: trial, configID: NCT05568903, patientId: testPatientIdCteAiDocRef
Then the received HTTP status code is 200
And the user checks the json response ignoring properties [resultTimestamp, version] VS expected json file cteAi/NCT05568903_eligibility_response.json
And the "eligibility" property in the response contains the value: PENDING
When the user clicks on the browser's refresh button
Then the Patient page is loaded
When the user selects the "OncoTestPatient, CteAiDocRef" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
When there is a Treatments card with clinical trial options
And the user clicks "Clinical trials" button
Then the Trials view is displayed
When the user checks for trial/s display in trials list
Then each trial has its related matching confidence score:
|trial_id      | Matching_Confidence_Score|
|NCT05568903   | Pending                  |
When the user selects the following trial id:NCT05568903 from the list of trials
Then the criteria list view contains the following values:
| Eligibility_criteria                            | Status            | Type      | Eligibility_values |
| Constipation Finding of nocturia Text/Plain     | In progress       | Inclusion | N/A     |
| Cholestrol 6.3 Triglyceride 1.3 - PDF           | In progress       | Inclusion | N/A     |
| Document patient is fine text/RTF               | In progress       | Inclusion | N/A     |
| Breast Calcification Seen On Mammogram txt html | In progress       | Inclusion | N/A     |
| Age >= 18 years                                 | Inclusion met     | Inclusion | 83      |
| Gender is male                                  | Inclusion met     | Inclusion | male    |
When the following treatments have been configured for the patients:
| patient                    | list_of_treatments   |
| testPatientIdCteAiDocRef   | NCT05568903          |
Then the received HTTP status code is 200
And the "message" property in the response matches the value: Ingestion process started
And the mandatory cte-ai-ai-criteria-matching Sfn processes are finished within 900 seconds with status Succeeded for property patients[0].id and propertyValue testPatientIdCteAiDocRef
And the optional criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property eventType and propertyValue PATIENT_VERSION_COMPLETED
And the following patient sync banner appears in 90000 milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then the Trials view is displayed
When the user waits 15000 milliseconds
And the user selects the following trial id:NCT05568903 from the list of trials
Then each trial has its related matching confidence score:
|trial_id     | Matching_Confidence_Score   |
|NCT05568903  | Likely no match             |
And the criteria list view contains the following values:
| Eligibility_criteria                            | Status            | Type      | Eligibility_values |
| Age >= 18 years                                 | Inclusion met     | Inclusion | 83      |
| Gender is male                                  | Inclusion met     | Inclusion | male    |
And check the 'Eligibility values' matches the regex according the file - AI_ExpectedExtractionResponse.txt