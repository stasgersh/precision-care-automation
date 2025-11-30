@cte
@safety
Feature: [SysRS-68441] Clinical Trials criteria list view

Narrative: For each trial option, the system shall provide detailed criteria list view with the following information for each criterion:
             - Criteria description
             - Status (patient meets the criteria) - see SysRS-68444
             - Type (Inclusion/Exclusion)
             - Patient value - See AI information and hyperlink source (SysRS-68594)

Background:
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
When the user selects the "OncoTestPatient, Calculator" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded
When the user submit GET request to get all trials for patient 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5
And the user submit DELETE request to delete all fetched configurations type of trial
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property eventType and propertyValue DATA_FABRIC_DELETE
And the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5
When [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Calculator.json
And the user submits PUT request to update the existing patientID: 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 with following details:
| config_id   | config_type | trial_configuration_file    |
| NCT04428710 | trial       | NCT04428710-Study_III       |
| NCT04428711 | trial       | NCT04428711-Study_IV        |
| NCT04428713 | trial       | NCT04428713-Study_V         |
| NCT04428719 | trial       | NCT04428719-Study_Expand_2  |
| NCT04428788 | trial       | NCT04428788-Study_to_Evaluate_the_Safety_and_Tolerability    |
| NCT04428789 | trial       | NCT04428789-Study_II_to_Evaluate_the_Safety_and_Tolerability |
Then the received HTTP status code is 202
And the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue trial/NCT04428789.json
And the following patient sync banner appears in 120000 milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
And there is a Treatments card with clinical trial options
And the user clicks "Clinical trials" button

@sanityTrial
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68440,SysRS-68441,SysRS-68444,SysRS-68437,SysRS-68447,SysRS-68446
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Trial_Eligibility
@Description:Check_the_basic_criteria_list_view_contains_the_following_columns:<ul><li>Eligibility_criteria</li><li>Status</li><li>Type</li><li>Eligibility_value</li></ul>,Check_all_columns_are_displayed_correctly_on_criteria_view_table
Scenario: Check the basic criteria list view(table)
Given the page is reloaded
And the Trials view is displayed
When the user checks for trial/s display in trials list
And the user selects the following trial id:NCT04428788 from the list of trials
Then the criteria list view contains the following columns:
| Eligibility criteria |
| Status               |
| Type                 |
| Eligibility value(s) |

@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68440,SysRS-68441,SysRS-68437,SysRS-68447,SysRS-68446
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Clinical_Trial_Eligibility
@Description:The_list_of_criteria_is_displayed_on_table_and_can_be_scrolled_Up_and_Down:<ul><li>1.Check_that_each_criteria_in_the_list_has_all_required_fields_when_screen_resolution(1920x1200)_&_text_size_(100%):</li><li>2.Can_be_scrolled_Up_and_Down_in_case_of_long_list</li>The_list_of_criteria_is_displayed_on_table_and_can_be_scrolled_Up_and_Down:<ul><li>1.Check_that_each_criteria_in_the_list_has_all_required_fields_when_screen_resolution(1920x1200)_&_text_size_(100%):</li><li>2.Can_be_scrolled_Up_and_Down_in_case_of_long_list</li>
Scenario: Check when screen resolution(1920x1200) & text size (100%), each criteria in the list with has all required fields and can be scrolled Up/Down
Given the Trials view is displayed
When the user checks for trial/s display in trials list
And the user selects the following trial id:NCT04428710 from the list of trials
And set screen's resolution to (1920 x 1200) & text size (100%)
Then check all criteria displayed within the page and is scrollable Up/Down