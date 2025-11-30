@cte
Feature: Summary View Treatments, Clinical trial options card
Narrative:
The system shall display clinical trial option card with top three highest ranking clinical trials and their respective matching confidence score (See SysRS-68446).

Trials in Summary dashboard are shown if there is at least one trial available.
If none are available there should be an empty state for Clinical trial options in the Summary dashboard.

Background:
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded

@sanity
@edge
@sanityTrial
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68494,SysRS-68438
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View
@Description:Check_matching_confidence_badges_on_Clinical_trial_options_card_on_Summary_view:<br/></ul>,All_trial_statuses_appears_correctly
Scenario: Check matching confidence badges on Clinical trial options card on Summary view
When the user clicks on the browser's refresh button
Then the Patient page is loaded
When the user selects the "huge-test-patient-9330-id" patient
And the user navigates to the "Summary" view
And there is a Treatments card with clinical trial options
Then the "Treatments" group has 5 cards
And the 5th card in "Treatments" group contains the following data:
| data_type                           | data                                              |
| CARD_TITLE                          | Clinical trial options                            |
| CLINICAL_TRIAL_ROW_BADGE_DATA       | Strong match, true |
| CLINICAL_TRIAL_DESCRIPTION          | Study V to Evaluate the Safety and Tolerability of CC-94676 in Participants With Metastatic Castration-Resistant Prostate Cancer |

@sanity
@sanityTrial
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68494,SysRS-68438,SysRS-68441
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View
@Description:Check_See_all_clinical_trial_options_button_displays_on_Clinical_trial_card_on_Summary_view_and_redirects_to_trials_tab_view:<br/></ul>,Trials_tab_view_is_displayed
Scenario: Check "See all clinical trial options" button displays in Summary view and redirects to Trials tab view
Given the "OncoTestPatient, Calculator" patient is selected
And the user navigated to the "Summary" view
And there is a Treatments card with clinical trial options
Then the "Treatments" group has 4 cards
When the button "All clinical trial options" is displayed and clicked for 'Clinical trial options'
Then the Trials view is displayed

@sanity
@sanityTrial
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68494,SysRS-68438,SysRS-68441
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View
@Description:Check_Clinical_trials_button_displays_on_Clinical_trial_card_on_Summary_view_and_redirects_to_trials_tab_view:<br/></ul>,Trials_tab_view_is_displayed
Scenario: Check "Clinical trials" button displays in Summary view and redirects to Trials tab view
When the user clicks on the browser's refresh button
Then the Patient page is loaded
When the user selects the "huge-test-patient-9330-id" patient
And the user navigates to the "Summary" view
And there is a Treatments card with clinical trial options
Then the "Treatments" group has 5 cards
When the user clicks "Clinical trials" button
Then the Trials view is displayed

@sanity
@sanityTrial
@Platform:WEB
@Category:Positive
@Traceability:SysRS-102471,SysRS-102472,SysRS-102527
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_-_Automated/Summary_View
@Description:Check_for_the_message_of_Clinical_trial_options_are_being_calculated...:<br/></ul>,in_case_trials_are_being_processed
Scenario: Check for the message in of "Clinical trial options are being calculated..." in case trials in process
Given the user submits PUT request to update the existing patientID:huge-test-patient-9330-id with following details:
| config_id    | config_type | trial_configuration_file  |
| NCT06044857  | trial       | NCT06044857-All_UsingAI_True       |
And [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/huge-test-patient-9330-id.json
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue huge-test-patient-9330-id
When the user clicks on the browser's refresh button
Then the Patient page is loaded
When the user selects the "huge-test-patient-9330-id" patient
And the user navigates to the "Summary" view
And there is a Treatments card with clinical trial options
Then the "Treatments" group has 5 cards
And the 5th card in "Treatments" group contains the following data:
| data_type   | data                                                    |
| CARD_TITLE  | Clinical trial options                                  |
| NORMAL_TEXT | Clinical trial options are being calculated and will be shared once the calculation is complete |