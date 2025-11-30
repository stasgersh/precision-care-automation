Feature: [SysRS-69558] Progressive Summarization - Expand and Collapse

Narrative: The system shall provide the user the ability to Expand and Collapse Progressive summarization view. Default state shall be Expanded.


@sanity
@SRS-69558.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69558
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: Progressive summarization default state is Expanded
Given [API] the AI data visualization is switched ON for [DEFAULT-TEST-USER] user
And [API] the [DEFAULT-TEST-USER] user reset the summary settings for "TEST_PS, Hank"
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "TEST_PS, Hank" patient (without reseting patient settings)
Then the patient summary view is loaded
And the Progressive Summarization Overview is displayed
And the Progressive Summarization Overview is not collapsed
And the 'Brief history' section is displayed in PS Overview
And the 'What has changed' section is displayed in PS Overview
And the 'Summary of recent reports' section is displayed in PS Overview

@sanity
@SRS-69558.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69558
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: Progressive summarization expanded or collapsed state is saved per user per patient
Given [API] the AI data visualization is switched ON for [DEFAULT-TEST-USER] user
And [API] the [DEFAULT-TEST-USER] user reset the summary settings for "TEST_PS, Hank"
And [API] the [DEFAULT-TEST-USER] user reset the summary settings for "OncoTestPatient, Freya"
And [API] the [TEST-USER-2] user reset the summary settings for "TEST_PS, Hank"
And [API] the [TEST-USER-2] user reset the summary settings for "OncoTestPatient, Freya"
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "TEST_PS, Hank" patient (without reseting patient settings)
Then the patient summary view is loaded
And the Progressive Summarization Overview is displayed
And the Progressive Summarization Overview is not collapsed
When the user collapses the PS Overview
Then the Progressive Summarization Overview is collapsed
And the 'Brief history' section is not displayed in PS Overview
And the 'What has changed' section is not displayed in PS Overview
When the user navigates to the "Timeline" view
And the user navigates to the "Summary" view
Then the Progressive Summarization Overview is displayed
And the Progressive Summarization Overview is collapsed
When the user selects the "OncoTestPatient, Freya" patient (without reseting patient settings)
Then the patient summary view is loaded
And the Progressive Summarization Overview is displayed
And the Progressive Summarization Overview is not collapsed
When the user selects the "TEST_PS, Hank" patient (without reseting patient settings)
Then the patient summary view is loaded
And the Progressive Summarization Overview is displayed
And the Progressive Summarization Overview is collapsed
When the user logs out from OncoCare
And the [TEST-USER-2] user logs in to OncoCare
And the user selects the "TEST_PS, Hank" patient (without reseting patient settings)
Then the patient summary view is loaded
And the Progressive Summarization Overview is displayed
And the Progressive Summarization Overview is not collapsed

@integration
@SRS-69558.03
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69558
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Automated/Functional_Service/Summary_View/Progressive_summarization
Scenario: Progressive summarization state is kept after the page is refreshed
Given [API] the AI data visualization is switched ON for [DEFAULT-TEST-USER] user
And [API] the [DEFAULT-TEST-USER] user reset the summary settings for "TEST_PS, Hank"
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "TEST_PS, Hank" patient (without reseting patient settings)
Then the patient summary view is loaded
And the Progressive Summarization Overview is displayed
And the Progressive Summarization Overview is not collapsed
When the user collapses the PS Overview
Then the Progressive Summarization Overview is collapsed
And the 'Brief history' section is not displayed in PS Overview
And the 'What has changed' section is not displayed in PS Overview
And the 'Summary of recent reports' section is not displayed in PS Overview
When the user clicks on the browser's refresh button
Then the patient summary view is loaded
And the Progressive Summarization Overview is displayed
And the Progressive Summarization Overview is collapsed
And the 'Brief history' section is not displayed in PS Overview
And the 'What has changed' section is not displayed in PS Overview
And the 'Summary of recent reports' section is not displayed in PS Overview

@manual
@integration
@SRS-69558.04
@Platform:WEB
@Category:Negative
@Traceability:SysRS-69558
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Summary_View/Progressive_summarization
Scenario: Error message is not displayed when PS panel state cannot be saved
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the user selected the "TEST_PS, 5e" patient
And the Progressive Summarization panel is displayed
And the Progressive Summarization panel is expanded
And [DEVTOOLS] - the following text pattern was added to "Network request blocking" tool:
| text pattern |
| settings     |
And [DEVTOOLS] - the "Enable network request blocking" checkbox is CHECKED in the "Network request blocking" tool
And [DEVTOOLS] - the "settings" item is selected in the list of "Enable network request blocking"
When the user collapse the Progressive Summarization panel
Then [DEVTOOLS] - the "settings" request is blocked in the Network tab
And the Progressive Summarization panel is collapsed
And error message or error popup is not displayed in the application
When the user navigates to the Timeline view
And [DEVTOOLS] - the user unchecks "Enable network request blocking" checkbox
And the user navigates back to the Summary view
Then the Progressive Summarization panel is collapsed
When the user refreshes the page
Then the Summary view is reloaded
And the Progressive Summarization panel is expanded
