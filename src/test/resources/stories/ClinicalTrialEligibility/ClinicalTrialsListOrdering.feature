@cte
Feature: [SysRS-68439] Clinical Trials List Ordering

Narrative: The system shall display trials list in descending order of match confidence score (from highest match confidence score to the lowest).

@sanityTrial
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68439,SysRS-68437,SysRS-68447,SysRS-68446
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Trial_Eligibility/Trials_List_Ordering
@Description:Check_the_trials_list_in_descending_order_per_match_confidence_score:<ul><li>Complete_match</li><li>Strong_match</li><li>Probable_match</li><li>Likely_no_match</li><li>No_match</li></ul>,Trials_sorted_in_descending_order
Scenario: Check the trials list appears in the list according to descending order of match confidence score
Given [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Calculator.json
When the user submits PUT request to update the existing patientID:8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 with following details:
| config_id   | config_type | trial_configuration_file    |
| NCT04428710 | trial       | NCT04428710-Study_III       |
| NCT04428711 | trial       | NCT04428711-Study_IV        |
| NCT04428713 | trial       | NCT04428713-Study_V         |
| NCT04428719 | trial       | NCT04428719-Study_Expand_2  |
| NCT04428788 | trial       | NCT04428788-Study_to_Evaluate_the_Safety_and_Tolerability    |
| NCT04428789 | trial       | NCT04428789-Study_II_to_Evaluate_the_Safety_and_Tolerability    |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue trial/NCT04428789.json
When [API] the [DEFAULT-TEST-USER] user turns ON the CLP visualization
And the [DEFAULT-TEST-USER] user logs in to OncoCare
And the user selects the "OncoTestPatient, Calculator" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
And there is a Treatments card with clinical trial options
When the user clicks on any available description
And the user checks for trial/s display in trials list
Then each trial has its related matching confidence score:
|trial_id     |Matching_Confidence_Score|
|NCT04428711  |Complete match           |
|NCT04428719  |Complete match           |
|NCT04428710  |Strong match             |
|NCT04428788  |Probable match           |
|NCT04428713  |Likely no match          |
|NCT04428789  |No match                 |

@sanityTrial
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68439,SysRS-68438,SysRS-68437,SysRS-68447,SysRS-68446,SysRS-102471,SysRS-102472,SysRS-102527
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Trial_Eligibility/Trials_List_Ordering
@Description:Check_the_trials_in_descending_order_of_match_confidence_order_with_same_match_confidence_-_Different_trials_with_same_match:</ul>,Trials_sorted_in_descending_order
Scenario: Check different trials with same matching score appears in descending order
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
When the [DEFAULT-TEST-USER] user logs in to OncoCare
And the user submits PUT request to update the existing patientID:8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 with following details:
| config_id    | config_type | trial_configuration_file            |
| NCT04428719 | trial       | NCT04428719-Study_Expand_2_Updated  |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue trial/NCT04428719.json
When the user selects the "OncoTestPatient, Calculator" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
And there is a Treatments card with clinical trial options
When the user clicks on any available description
And the user checks for trial/s display in trials list
Then each trial has its related matching confidence score:
|trial_id     |Matching_Confidence_Score|
|NCT04428711  |Complete match           |
|NCT04428719  |Complete match           |
|NCT04428710  |Strong match             |
|NCT04428788  |Probable match           |
|NCT04428713  |Likely no match          |
|NCT04428789  |No match                 |
When the user submits PUT request to update the existing patientID:8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 with following details:
| config_id    | config_type | trial_configuration_file      |
|NCT06044857   | trial       | NCT06044857-All_UsingAI_True  |
Then the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then each trial has its related matching confidence score:
|trial_id     |Matching_Confidence_Score|
|NCT06044857  |Pending                  |
|NCT04428719  |Complete match           |
|NCT04428711  |Complete match           |
|NCT04428710  |Strong match             |
|NCT04428788  |Probable match           |
|NCT04428713  |Likely no match          |
|NCT04428789  |No match                 |