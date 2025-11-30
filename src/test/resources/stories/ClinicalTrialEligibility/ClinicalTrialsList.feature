@cte
Feature: [SysRS-68438] Clinical Trials List

Narrative: The system shall provide the user list of available trials for a patient, each trial on the list shall have the following information:
            - Trial name
            - Trial identifier (if relevant, otherwise: No trial identifier found)
            - Matching confidence score. see SysRS-68446
            - Short description on the trial

Background:
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Calculator" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
And there is a Treatments card with clinical trial options
When the user clicks on any available description

@sanityTrial
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68438,SysRS-68437,SysRS-68447
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Trial_Eligibility/Trials_List
@Description:Check_each_trial_has_its_related_matching_confidence_score:<ul><li>Complete_match</li><li>Strong_match</li><li>Probable_match</li><li>Likely_not_a_match</li><li>No_match</li></ul>,All_trial_statuses_appears_correctly
Scenario: Check Trials with all possible Matching confidence scores
Given [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Calculator.json
When the user submits PUT request to update the existing patientID:8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 with following details:
| config_id   | config_type | trial_configuration_file    |
| NCT04428710 | trial       | NCT04428710-Study_III       |
| NCT04428711 | trial       | NCT04428711-Study_IV        |
| NCT04428713 | trial       | NCT04428713-Study_V         |
| NCT04428719 | trial       | NCT04428719-Study_Expand_2  |
| NCT04428788 | trial       | NCT04428788-Study_to_Evaluate_the_Safety_and_Tolerability       |
| NCT04428789 | trial       | NCT04428789-Study_II_to_Evaluate_the_Safety_and_Tolerability    |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue trial/NCT04428789.json
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
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
@Traceability:SysRS-68438,SysRS-68437,SysRS-68447
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Trial_Eligibility/Trials_List
@Description:Check_each_trial_has_the_following_information:<ul><li>Trial_name</li><li>Trial_identifier</li><li>Matching_confidence_score</li><li>Short_description_on_the_trial</li></ul>,Data_appears_for_each_one_of_fields
Scenario: Check system provides the user the following information for each trials
Given the Trials view is displayed
When the user checks for trial/s display in trials list
Then each trial has the following information:
|trial_information_fields       |
|Trial name                     |
|Trial identifier               |
|Matching confidence score      |
|Short description on the trial |

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68438,SysRS-68437,SysRS-68447
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Trial_Eligibility/Trials_List
@Description:Check_trial_long_name:</ul>,Long_trial_name_has_three_dots_as_an_indication_for_a_long_name
Scenario: Check one of trials with long string name(100 chars)
When the user selects the following trial id:NCT04428710 from the list of trials
Then the trial card titled 'Study III to Evaluate the Safe' has a truncated title and its screenshot size matches the expected bytes in range of 2500 and 5000

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68438,SysRS-68437,SysRS-68447
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Trial_Eligibility/Trials_List
@Description:Check_trial_long_description:</ul>,Long_trial_name_has_three_dots_as_an_indication_for_a_long_description
Scenario: Check one of trials has long description ingested and displayed (100 chars)
When the user selects the following trial id:NCT04428710 from the list of trials
Then the trial card 'Study III to Evaluate the Safe' has a truncated description starting with 'The purpose of this study is to assess the safety' and its screenshot size matches the expected bytes in range of 4500 and 5700

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68438,SysRS-68437,SysRS-68447
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Trial_Eligibility/Trials_List
@Description:Check_Trials_scrolling_functionality:<ul></ul>,The_scroll_bar_was_scrolled_to_button_and_last_trial_is_displayed
Scenario: Check Trials scrolling functionality
Given the Trials view is displayed
When the user checks for trial/s display in trials list
And scrolls down to the bottom of the list and last configID:NCT04428788 is displayed
