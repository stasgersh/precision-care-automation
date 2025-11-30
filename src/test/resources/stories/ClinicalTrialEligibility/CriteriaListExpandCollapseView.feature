@cte
Feature: [SysRS-68443] Clinical Trial eligibility - Criterion expand view. [SysRS-69187] - Clinical Trial eligibility - Collapse view
Narrative: Check the Clinical Trial's criteria list expand & collapse views

Background:
Given [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Sigrid.json
And [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application

@sanityTrial
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68441,SysRS-68444,SysRs-68445,SysRS-69188,SysRS-68443,SysRS-69187,SysRS-68437,SysRS-68447,SysRS-68446
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Trial_Eligibility
@Description:Check_patient_update_and_the_criteria_status_recalculation_when_patient_data_update_arrived:<ul></ul>,Criteria_results_recalculated_properly_upon_patient_data_update
Scenario:Check criteria status recalculated when patient update arrives
Given [API] the following patient is uploaded to PDS: patients/prepared/OncoTestPatient_Silman_female_Age_GT_18.json
And [API] a mandatory ETL process is started in 180 seconds and finished within 180 seconds with status Succeeded for patient cb5cf096-1b17-4139-b4bc-6aefdfc46b7b
And the Patient page is loaded
When the user selects the "OncoTestPatient, Silman" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
And there is a Treatments card with clinical trial options
When the user clicks "Clinical trials" button
And the user checks for trial/s display in trials list
Then the Trials view is displayed
When the user submits PUT request to update the existing patientID:cb5cf096-1b17-4139-b4bc-6aefdfc46b7b with following details:
| config_id   |config_type  |trial_configuration_file  |
| NCT04428713 |trial        |NCT04428713-Study_V       |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue trial/NCT04428713.json
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
And the user checks for trial/s display in trials list
Then the Trials view is displayed
When the user selects the following trial id:NCT04428713 from the list of trials
And the user expands criteria name starts with "Gender ?", to check the inner conditions
Then the user checks the inner content has the following data:
| header           | status             |
| Gender is female | Inclusion met      |
| Gender is male   | Inclusion not met  |
And the calculated status is Inclusion met for the criteria titled Age >= 18 years
When [API] the following patient is uploaded to PDS: patients/prepared/OncoTestPatient_Silman_Male_Age_LT_18.json
Then [API] a mandatory ETL process is started in 180 seconds and finished within 180 seconds with status Succeeded for patient cb5cf096-1b17-4139-b4bc-6aefdfc46b7b
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
And the user checks for trial/s display in trials list
Then the Trials view is displayed
When the user clicks on the browser's refresh button
And the user checks for trial/s display in trials list
And the user selects the following trial id:NCT04428713 from the list of trials
And the user expands criteria name starts with "Gender ?", to check the inner conditions
Then the user checks the inner content has the following data:
| header            | status             |
| Gender is female  | Inclusion not met  |
| Gender is male    | Inclusion met      |
When the user clicks on the browser's refresh button
And the user checks for trial/s display in trials list
Then the Trials view is displayed
And the calculated status is Inclusion not met for the criteria titled Age >= 18 years

@sanityTrial
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68441,SysRS-68444,SysRS-68445,SysRS-69188,SysRS-68443,SysRS-69187,SysRS-68437,SysRS-68447,SysRS-68446
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Trial_Eligibility
@Description:Check_Expand_functionality_-_SubCriteria_with_more_than_one_OR_operator_in_criteria_and_matching_status_display(e.g:5_different_conditions):<ul></ul>,Check_for_conditions_between_criteria_displayed_in_expanded_view
Scenario: Check Criteria Expand - 5 SubCriteria with OR operator
When the user selects the "OncoTestPatient, Sigrid" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
And there is a Treatments card with clinical trial options
When the user clicks "Clinical trials" button
And the user checks for trial/s display in trials list
Then the Trials view is displayed
When the user submits PUT request to update the existing patientID:7cfe48ab-eb05-9ee1-84a4-f59c8a84e05a with following details:
| config_id   |config_type  | trial_configuration_file  |
| NCT04428713 |trial        | NCT04428713-Expand_Comorbidity_with_5_OR_conditions  |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue trial/NCT04428713.json
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
And the user checks for trial/s display in trials list
And the user selects the following trial id:NCT04428713 from the list of trials
And the user expands criteria name starts with "Clinically significant venous thromboembolism within 3 months prior to the first dose of IP", to check the inner conditions
Then the user checks the inner content has the following data:
| header                | status            |
| Prediabetes           | Missing           |
| Anemia (disorder)     | Missing           |
| Alcoholism            | Missing           |
| Diabetes Mellitus     | Exclusion not met |
| Obesity               | Exclusion not met |
And the sub-criteria Prediabetes followed by logical operator - Or
And the sub-criteria Anemia (disorder) followed by logical operator - Or
And the sub-criteria Alcoholism followed by logical operator - Or
And the sub-criteria Diabetes Mellitus followed by logical operator - Or
When the user clicks collapse expended criteria name starts with:Clinically significant venous thromboembolism within 3
Then the criteria with title starts with "Clinically significant venous thromboembolism within 3", was collapsed into initial view
And the complex is OR between criteria, then the Exclusion not met is the highest indication status of criteria with title starts with Clinically significant venous thromboembolism within 3

@sanityTrial
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68441,SysRS-68444,SysRs-68445,SysRS-69188,SysRS-68443,SysRS-69187,SysRS-68437,SysRS-68447,SysRS-68446
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Trial_Eligibility
@Description:Check_Expand_functionality_-_SubCriteria_with_more_than_one_AND_operator_in_criteria_and_matching_status_display(e.g:5_different_conditions):<ul></ul>,Check_for_conditions_between_criteria_displayed_in_expanded_view
Scenario: Check Criteria Expand - 5 SubCriteria with AND operator
When the user submits PUT request to update the existing patientID:7cfe48ab-eb05-9ee1-84a4-f59c8a84e05a with following details:
| config_id   |config_type  | trial_configuration_file  |
| NCT04428789 |trial        | NCT04428789-Expand_Comorbidity_with_5_AND_conditions  |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue trial/NCT04428789.json
And the Patient page is loaded
When the user selects the "OncoTestPatient, Sigrid" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
And there is a Treatments card with clinical trial options
When the user clicks on any available description
And the user checks for trial/s display in trials list
Then the Trials view is displayed
When the user selects the following trial id:NCT04428789 from the list of trials
And the user expands criteria name starts with "Clinically significant venous thromboembolism within 3 months prior to the first dose of IP", to check the inner conditions
Then the user checks the inner content has the following data:
| header                 | status            |
| Prediabetes            | Missing           |
| Anemia (disorder)      | Missing           |
| Alcoholism             | Missing           |
| Diabetes Mellitus      | Exclusion not met |
| Obesity                | Exclusion not met |
And the sub-criteria Prediabetes followed by logical operator - And
And the sub-criteria Anemia (disorder) followed by logical operator - And
And the sub-criteria Alcoholism followed by logical operator - And
And the sub-criteria Diabetes Mellitus followed by logical operator - And
When the user clicks collapse expended criteria name starts with:Clinically significant venous thromboembolism within 3
Then the criteria with title starts with "Clinically significant venous thromboembolism within 3", was collapsed into initial view
And the complex is AND between criteria, then the Missing is the lowest indication status of criteria with title starts with Clinically significant venous thromboembolism within 3

@sanityTrial
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68441,SysRS-68444,SysRS-68445,SysRS-69188,SysRS-68443,SysRS-69187,SysRS-68437,SysRS-68447,SysRS-68446
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Trial_Eligibility
@Description:Collapse_expanded_criteria:<ul></ul>,Check_all_criteria_collapsed_into_initial_view
Scenario: Collapse expanded criteria
When the user selects the "OncoTestPatient, Silman" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
And there is a Treatments card with clinical trial options
When the user clicks on any available description
And the user checks for trial/s display in trials list
Then the Trials view is displayed
When the user selects the following trial id:NCT04428713 from the list of trials
And the user expands criteria name starts with "Clinically significant venous thromboembolism within 3 months prior to the first dose of IP", to check the inner conditions
Then the user checks the inner content has the following data:
| header            | status            |
| Prediabetes       | Missing           |
| Anemia (disorder) | Missing           |
| Alcoholism        | Missing           |
| Diabetes Mellitus | Exclusion not met |
| Obesity           | Exclusion not met |
When the user clicks collapse expended criteria name starts with:Clinically significant venous thromboembolism within 3 months prior to the first dose of IP
Then the criteria with title starts with "Clinically significant venous thromboembolism within 3 months prior to the first dose of IP", was collapsed into initial view

@srs
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68441,SysRs-68445,SysRS-69188,SysRS-68443,SysRS-69187,SysRS-68437,SysRS-68447,SysRS-68446
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Trial_Eligibility
@Description:Check_Expand_functionality_-_Combination_both_(OR_+_AND)_are_not_supported:<ul></ul>,Check_for_treatment_eligibility_service_response_with_appropriate_error_message
Scenario: Check Criteria Ingestion fails with and error for SubCriteria with combination of (OR + AND)
When the user submits PUT request to update/add trial/s configuration whether exists or not:
| trial_id    | config_type | trial_configuration_file            |
| NCT04428713 | trial       | NCT04428713-AND_with_OR_Comorbidity_Not_Supported  |
Then the received HTTP status code is 422
And the response body contains the following text: 'Value error, Cannot combine both `AND`and `OR` operators in the same expression.'

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68441,SysRS-68444,SysRS-69188,SysRS-68443,SysRS-69187,SysRS-68437,SysRS-68447,SysRS-68446
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Trial_Eligibility
@Description:No_additional_conditions_displayed_for_simple_criteria:<ul></ul>,Check_no_additional_conditions_are_displayed_for_simple_criteria
Scenario: Check no additional conditions are displayed for simple criteria
When the user submits PUT request to update the existing patientID:cb5cf096-1b17-4139-b4bc-6aefdfc46b7b with following details:
| config_id   |config_type  | trial_configuration_file  |
| NCT04428713 |trial        |NCT04428713-Study_V       |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue trial/NCT04428713.json
When the user selects the "OncoTestPatient, Silman" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
And there is a Treatments card with clinical trial options
When the user clicks on any available description
And the user checks for trial/s display in trials list
Then the Trials view is displayed
When the user selects the following trial id:NCT04428713 from the list of trials
Then the simple criteria name starts with:Age >= 18 years, is not expandable