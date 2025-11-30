@cte
Feature: [SysRS-69188] Clinical Trials criteria list Filter

Narrative: The system shall support Filter and Sort options on criteria list table for Status and Type (of trial) fields.

Note:
Add this step after understand how to ingest new patient data relevant to Onco-Care

Background:
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
When the user selects the "OncoTestPatient, Calculator" patient
And the user navigates to the "Summary" view
Then the patient summary view is loaded


@sanityTrial
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68440,SysRS-68441,SysRS-68444,SysRS-69188,SysRS-68437,SysRS-68447,SysRS-102471,SysRS-102472,SysRS-102527
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Trial_Eligibility
@Description:Display_all_criteria_filter_options_in_popup:<ul><li>In_progress</li><li>Inclusion_met</li><li>Exclusion_met</li><li>Missing</li><li>Inclusion_not_met</li><li>Exclusion_not_met</li></ul>,Check_all_criteria_filter_options_displayed_in_popup
Scenario: Display all criteria filter options
When [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Calculator.json
And the user submits PUT request to update the existing patientID:8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 with following details:
| config_id   | config_type | trial_configuration_file                  |
| NCT04428789 | trial       | NCT04428789-Study_II_with_all_statuses    |
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
And the user checks for trial/s display in trials list
And the user selects the following trial id:NCT04428789 from the list of trials
And the user clicks on the filter icon in the 'Status' column
Then the 'Status' column's pop-up list view contains the following values:
| status            |
| In progress       |
| Inclusion met     |
| Inclusion not met |
| Missing           |
| Exclusion met     |
| Exclusion not met |

@sanityTrial
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68440,SysRS-68441,SysRS-68444,SysRS-69188,SysRS-68437,SysRS-68447
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Trial_Eligibility
@Description:Default_behaviour_when_none_of_filters_selected:<ul></ul>,Check_"Filter_By"_frame_doesn't_appears_on_page
Scenario: Default behaviour when none selected
When [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Calculator.json
And the user submits PUT request to update the existing patientID:8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 with following details:
| config_id   | config_type | trial_configuration_file                  |
| NCT04428711 | trial       | NCT04428711-Study_IV    |
Then the received HTTP status code is 202
And the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue trial/NCT04428711.json
And the following patient sync banner appears in 120000 milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
And there is a Treatments card with clinical trial options
And the user clicks "Clinical trials" button
And the user checks for trial/s display in trials list
And the user selects the following trial id:NCT04428711 from the list of trials
Then the user verify that "Filter By" frame doesn't appears on page
And the criteria list is not empty and displayed on view for trial id:"NCT04428711"

@sanityTrial
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68440,SysRS-68441,SysRS-68444,SysRS-69188,SysRS-68437,SysRS-68447,SysRS-102471,SysRS-102472,SysRS-102527
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Trial_Eligibility
@Description:Filter-out_criteria_by_status:<ul><li>In_progress</li><li>Inclusion_met</li><li>Exclusion_met</li><li>Missing</li><li>Inclusion_not_met</li><li>Exclusion_not_met</li></ul>,Check_the_pop_up_list_view_contains_the_status_values_stated_above
Scenario: Filter-out criteria by status
When [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Calculator.json
And the user submits PUT request to update the existing patientID:8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 with following details:
| config_id   | config_type | trial_configuration_file |
| NCT04428789 | trial       | NCT04428789-Study_II_with_all_statuses      |
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
And the user checks for trial/s display in trials list
And the user selects the following trial id:NCT04428789 from the list of trials
And the user clicks on the filter icon in the 'Status' column
Then the 'Status' column's pop-up list view contains the following values:
| status            |
| In progress       |
| Inclusion met     |
| Inclusion not met |
| Missing           |
| Exclusion met     |
| Exclusion not met |
When the user selects In progress status from the filter's list
Then the criteria view displays criteria with the following statuses:
| status   |
| In progress  |
When the user selects Inclusion met status from the filter's list
Then the criteria view displays criteria with the following statuses:
| status   |
| Inclusion met  |
When the user click on "Clear all" button on "Filter By" frame
And the user clicks on the filter icon in the 'Status' column
And the user selects Inclusion not met status from the filter's list
Then the criteria view displays criteria with the following statuses:
| status   |
| Inclusion not met  |
When the user click on "Clear all" button on "Filter By" frame
And the user clicks on the filter icon in the 'Status' column
And the user selects Missing status from the filter's list
Then the criteria view displays criteria with the following statuses:
| status   |
| Missing  |
When the user click on "Clear all" button on "Filter By" frame
And the user clicks on the filter icon in the 'Status' column
And the user selects Exclusion met status from the filter's list
Then the criteria view displays criteria with the following statuses:
| status   |
| Exclusion met  |
When the user click on "Clear all" button on "Filter By" frame
And the user clicks on the filter icon in the 'Status' column
And the user selects Inclusion met status from the filter's list
Then the criteria view displays criteria with the following statuses:
| status   |
| Inclusion met  |
When the user click on "Clear all" button on "Filter By" frame
And the user clicks on the filter icon in the 'Status' column
And the user selects Exclusion not met status from the filter's list
Then the criteria view displays criteria with the following statuses:
| status   |
| Exclusion not met  |
And the user click on "Clear all" button on "Filter By" frame

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68440,SysRS-68441,SysRS-68444,SysRS-69188,SysRS-68437,SysRS-68447,SysRS-68447,SysRS-102471,SysRS-102472,SysRS-102527
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Trial_Eligibility
@Description:Remove_filters_one_by_one_from_"Filter_By"_frame_and_check_all_criteria_were_displayed:<ul></ul>,Check_the_pop-up_list_view_contains_the_following_status_values_and_the_criteria_back_into_table_view_with_removed_<status>:<ul><li>In_progress</li><li>Inclusion_met</li><li>Exclusion_met</li><li>Missing</li><li>Inclusion_not_met</li><li>Exclusion_not_met</li></ul>
Scenario Outline: Remove filters one by one from "Filter By" frame and check all criteria were displayed
When there is a Treatments card with clinical trial options
And the user clicks "Clinical trials" button
And the user checks for trial/s display in trials list
And the user selects the following trial id:NCT04428789 from the list of trials
And the user clicks on the filter icon in the 'Status' column
Then the 'Status' column's pop-up list view contains the following values:
| status            |
| In progress       |
| Inclusion met     |
| Inclusion not met |
| Missing           |
| Exclusion met     |
| Exclusion not met |
When the user selects all filters at once the following filters:
| status            |
| In progress       |
| Inclusion met     |
| Inclusion not met |
| Missing           |
| Exclusion met     |
| Exclusion not met |
Then the user verify that "Filter By" frame contains only selected filters:
| status            |
| In progress       |
| Inclusion met     |
| Inclusion not met |
| Missing           |
| Exclusion met     |
| Exclusion not met |
When the user click on "Clear all" button on "Filter By" frame
Then the criteria view displays criteria with the following statuses:
| status            |
| In progress       |
| Inclusion met     |
| Inclusion not met |
| Missing           |
| Exclusion met     |
| Exclusion not met |

@sanityTrial
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68440,SysRS-68441,SysRS-68444,SysRS-69188,SysRS-68437,SysRS-68447
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Trial_Eligibility
@Description:Filter-out_criteria_by_type:<ul><li>Inclusion</li><li>Exclusion</li><li>Other_Inclusion</li></ul>,Check_the_pop_up_list_view_contains_the_type_values_stated_above
Scenario Outline: Filter-out criteria by type
When [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Calculator.json
And the user submits PUT request to update the existing patientID:8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 with following details:
| config_id   | config_type | trial_configuration_file |
| NCT04428713 | trial       | NCT04428713-Study_V      |
Then the received HTTP status code is 202
And the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue trial/NCT04428713.json
And the following patient sync banner appears in 120000 milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
And there is a Treatments card with clinical trial options
And the user clicks "Clinical trials" button
And the user checks for trial/s display in trials list
And the user selects the following trial id:NCT04428713 from the list of trials
And the user clicks on the filter icon in the 'Type' column
Then the 'Type' column's pop-up list view contains the following values:
| type      |
| Inclusion |
| Exclusion |
When the user selects each time only one <type> from the filter's list
Then the user verify that "Filter By" frame contains only selected <type>
And the criteria view displays criteria with filtered type - <type> only
When the user click on "Clear all" button on "Filter By" frame
Then the user verify that "Filter By" frame doesn't appears on page
Examples:
| type      |
| Inclusion |
| Exclusion |