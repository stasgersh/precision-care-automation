@cte
@ai_sanity
@safety
Feature: [SysRS-68595] Artificial Intelligence (AI) – View single source details (Modal)

Narrative: The system shall display full data of the source (single source) to the user with the following information, once hyperlink is triggered (as described in Artificial Intelligence (AI) – information and hyperlink source (SysRS-68594)) :
             - Report/document name.
             - Date.
             - Doctor/Author name.
             - Report status.
             - Source data (as is) - full report/document.

@sanityTrial
@sanity
@edge
@CLP
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68590,SysRS-68591,SysRS-68594,SysRS-68595,SysRS-68596
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Trial_Eligibility
@Description:Artificial_Intelligence_AI_-_Check_AI_extracted_value_is_highlighted_in_patient_banner<ul></ul>,AI_extracted_value_is_highlighted_in_patient_banner
Scenario: Check AI extracted value is highlighted in patient banner
Given [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Calculator.json
And the user submits PUT request to update the existing patientID:8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 with following details:
| config_id   | config_type | trial_configuration_file    |
| NCT04428710 | trial       | NCT04428710-Study_III       |
| NCT04428711 | trial       | NCT04428711-Study_IV        |
| NCT04428713 | trial       | NCT04428713-Study_V         |
| NCT04428719 | trial       | NCT04428719-Study_Expand_2  |
| NCT04428788 | trial       | NCT04428788-Study_to_Evaluate_the_Safety_and_Tolerability    |
| NCT04428789 | trial       | NCT04428789-Study_II_to_Evaluate_the_Safety_and_Tolerability    |
And [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Calculator" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
And there is a Treatments card with clinical trial options
When the user clicks on any available description
When the Patient Banner is displayed
Then the following patient information displayed in the patient banner and highlighted:
| data_type | title             | value                         |
| CLP_INFO  | Histology type    | serous papillary carcinoma    |

@sanityTrial
@sanity
@CLP
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68590,SysRS-68591,SysRS-68594,SysRS-68595
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Trial_Eligibility
@Description:Artificial_Intelligence_AI_-_Check_AI_indication_for_trial_in_trials_card_list_view<ul></ul>,Check_Artificial_Intelligence_indications:<status><ul><li>Visual_cue</li></li><li>Inform_the_user</li></ul>,AI_Icon_Indication_displays_on_trial
Scenario: Check AI indications are displayed for trial in trial cards
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Calculator" patient is selected
And the user navigated to the "Summary" view
And there is a Treatments card with clinical trial options
And the user clicks on any available description
When the user selects the following trial id:NCT04428713 from the list of trials
Then the AI indication isDisplayed true, next to trial id:NCT04428713

@sanityTrial
@sanity
@edge
@CLP
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68590,SysRS-68591,SysRS-68594,SysRS-68595,SysRS-68596
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Trial_Eligibility
@Description:Artificial_Intelligence_AI_-_Check_different_AI_indications_for_trial_in_Main_Grid_View<ul></ul>,Check_various_Artificial_Intelligence_indications:<status><ul><li>Visual_cue</li><li>Inform_the_user</li><li>information_and_hyperlink_source</li><li>View_single_source_details_(Modal)</li></ul>,AI_Indications_display_on_main_grid_view
Scenario: Check AI indications are displayed for trial in main grid view
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Calculator" patient is selected
And the user navigated to the "Summary" view
And there is a Treatments card with clinical trial options
And the user clicks on any available description
When the user selects the following trial id:NCT04428713 from the list of trials
Then the trial:NCT04428713 has the following expected AI data in Grid Details of main section:
|grid_detail_section                    |expected_data  |
|isOverallMatchingScoreAiIconDisplayed  |true           |
|isAIHighlighted                        |true           |
|isDisclaimerIconDisplayed              |true           |
|AiDisclaimerDescription                |The data highlighted in blue or marked on this page is produced by generative artificial intelligence and is intended to supplement the existing patient data, may be incomplete or incorrect, and is not intended to replace the provider's judgment or responsibility to review the source original clinical information. |

@sanityTrial
@sanity
@CLP
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68590,SysRS-68591,SysRS-68594,SysRS-68595,SysRS-68596
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Trial_Eligibility
@Description:Artificial_Intelligence_AI_-_Check_AI_indications_are_displayed_for_expended_criteria_in_table_view<ul></ul>,Check_various_Artificial_Intelligence_indications_In_Expanded_Criteria_view:<status><ul><li>Patient_Value_highlighted_if_extracted_by_AI</li><li>Patient_Value_-NOT_highlighted_if_not_extracted_by_AI</li><li>AI_Icon_appears_next_to_Status_that_extracted_by_AI</li></ul>,AI_Indications_display_on_expanded_criteria_view
Scenario: Check AI indications are displayed for expended criteria in table view
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Calculator" patient is selected
And the user navigated to the "Summary" view
And there is a Treatments card with clinical trial options
And the user clicks on any available description
When the user selects the following trial id:NCT04428713 from the list of trials
And the user expands criteria name starts with "Gender ?", to check the inner conditions
Then the user checks the inner content has the following data:
| header           | status              |
| Gender is female | Inclusion met       |
| Gender is male   | Inclusion not met    |
And the user checks that patient value highlighted per condition as indication of AI extraction:
| patient_value     | isHighlighted  |
| female            | true           |
And the user checks that sub-criteria for the trial NCT04428713, the AI indicator next to each status as following:
|criteria_status   | ai_icon_displayed   |
|Inclusion met     | true                |
|Inclusion not met | true                |
Then the user checks the card menu, the AI indicator next to each card item as following:
|criteria_status     | ai_icon_displayed   |
|Likely no match     | true                |
|No match            | true                |