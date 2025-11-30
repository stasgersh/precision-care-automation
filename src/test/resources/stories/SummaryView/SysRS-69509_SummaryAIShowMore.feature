@safety
Feature: [SysRS-69509] Timepoints AI summary show more

Narrative: The system shall support Show more option to expand and display all summary content in case Summary of event contains more than lines than the default view setting as described in Response tracking – Timepoints AI summary (SysRS-69508).


@sanity
@ai
@ai_summary
@edge
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69509,SysRS-69508
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
Scenario: User can toggle the Show more/Show less button to display or truncate the summary text
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, AmitHaze" patient is selected
And the user navigated to the "Summary" view
When the user clicks on the "Settings" menu item on the user menu panel
Then the Settings page is displayed
And the following settings are available on the page:
| settings_title               | setting_value |
| Artificial Intelligence (AI) | Off           |
When the user clicks on 'Edit' button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user selects the "On" radio button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user clicks on the "Save" button on the Settings page
When the user clicks on the "Back" button in the Settings toolbar header
Then the Patient page is loaded
And the 1th card in "Imaging" group is not empty
And the "Imaging - most recent" card "Imaging" data truncated
And the "Show more" button presented
And the "Imaging - most recent" card content is highlighted
When the user click on "Show more" button in "Imaging" group on Summary view
Then the all text displayed on "Imaging - most recent" card and "Show less" button

@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69508,SysRS-68596
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
Scenario: AI produced content is highlighted on the selected event's summary if AI summarization is turned on
Given [API] the following patient is uploaded to PDS: summary/amitHaze_bundle.json
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, AmitHaze" patient is selected
And the user navigated to the "Summary" view
When the user clicks on the "Settings" menu item on the user menu panel
Then the Settings page is displayed
And the following settings are available on the page:
| settings_title               | setting_value |
| Artificial Intelligence (AI) | Off           |
When the user clicks on 'Edit' button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user selects the "On" radio button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user clicks on the "Save" button on the Settings page
When the user clicks on the "Back" button in the Settings toolbar header
Then the patient summary view is loaded
And the "Imaging - most recent" card content is highlighted
When the user click on "Show more" button in "Imaging" group on Summary view
Then the "Imaging - most recent" card content is highlighted
