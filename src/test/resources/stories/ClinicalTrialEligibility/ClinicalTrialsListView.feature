@cte
Feature: [SysRS-68440] Clinical Trials List View

Narrative: For each trial option the system shall provide detailed view with the following information:
             - Trial bar
               - Trial study name
               - Matching confidence - Calculated.
               - Phase (if avialable)
               - Overall Status ((Met/likely Met/Likely not met/Not met/Missing)
               - Completion Date
               - Trial ID
             - Criteria list – as described in trials eligibility - criteria view (SysRS-68441)

Background:
Given [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Calculator.json
And the user submits PUT request to update the existing patientID:8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 with following details:
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

@sanityTrial
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68438,SysRS-68439,SysRS-68440,SysRS-68437,SysRS-68447,SysRS-65729
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME3_-_Automated/Clinical_Trial_Eligibility
@Description:For_each_trial_option_the_system_shall_provide_detailed_view_with_the_following_information:<ul><li>Trial_study_name</li><li>Calculated_matching_confidence</li><li>Status</li><li>Phase</li><li>Completion_Date</li><li>config_id</li></ul></ul>,Check_all_fields_are_displayed_correctly_on_Trial_view_and_updated_once_new_data_arrives
Scenario: Check the trial view with all required fields and updated when new data arrives
Given the Trials view is displayed
When the user checks for trial/s display in trials list
And the user selects the following trial id:NCT04428711 from the list of trials
Then the patient's criteria basic data is available for NCT04428711 and check the following fields and data:
| Field             | Field content  |
| Trial name        | Study_IV to Evaluate the Safety and Tolerability of CC-94676 in Participants With Metastatic Castration-Resistant Prostate Cancer |
| Trial description | The purpose of this study is to assess the safety, tolerability and preliminary efficacy of CC-94676 in men with progressive metastatic castration resistant prostate cancer. |
| Matching score    | Complete match |
| Phase             | 1              |
| Status            | Recruiting     |
| Trial_ID          | NCT04428711    |
When the user submits PUT request to update the existing patientID:8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 with following details:
| config_id   | config_type | trial_configuration_file    |
| NCT04428711 | trial       | trial_NCT04428711_update  |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue trial/NCT04428711.json
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then the patient's criteria basic data is available for NCT04428711 and check the following fields and data:
| Field             | Field content |
| Trial name        | Updated - Study_IV to Evaluate the Safety and Tolerability of CC-94676 in Participants With Metastatic Castration-Resistant Prostate Cancer |
| Trial description | Updated - The purpose of this study is to assess the safety, tolerability and preliminary efficacy of CC-94676 in men with progressive metastatic castration resistant prostate cancer. |
| Matching score    | No match |
| Phase             | 2 |
| Status            | Active, not recruiting  |
| Trial_ID          | NCT04428711 |
