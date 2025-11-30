Feature: [SysRS-68498] Progressive Summarization - General View

Narrative: The system shall provide Progressive summarization view:
              - Brief history (Patient summary overview)
              - What has changed since last oncology encounter
              - Summary of recent reports and note
           by utilizing Ai service.


@sanity
@SRS-68498.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68498,SysRS-68590,SysRS-68591
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: 'Brief history' is displayed in Progressive Summarization if AI is turned on
Given [API] the AI data visualization is switched OFF for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "TEST_PS, Hank" patient
Then the Progressive Summarization Overview is displayed
And the 'Brief history' section has 'AI is turned off' information
When the user clicks on the "Turn on AI" button on 'Brief history' section
Then the 'Brief history' section does not have 'AI is turned off' information
And the 'Brief history' section has the title: "Brief history"
And the 'Brief history' section has an AI badge with text: "AI generated"
When the user hovers the 'i' symbol next to the AI badge on 'Brief history' section
Then tooltip is displayed with text: text_files/AI_info_tooltip_text_on_PS_Overview.txt

@sanity
@SRS-68498.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68498
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: 'What has changed' is displayed in Progressive Summarization even if AI is turned off
Given [API] the AI data visualization is switched OFF for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "TEST_PS, Hank" patient
Then the Progressive Summarization Overview is displayed
And the 'What has changed' section is displayed in PS Overview
And the 'What has changed' section does not have 'AI is turned off' information
And the 'What has changed' section has the title: "Since last oncology encounter (Sep 09, 2007)"
And the 'What has changed' section has the event list table

@sanity
@SRS-68498.03
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68498,SysRS-68590,SysRS-68591
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: 'Summary of recent reports' is displayed in Progressive Summarization if AI is turned on
Given [API] the AI data visualization is switched OFF for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "TEST_PS, Hank" patient
Then the Progressive Summarization Overview is displayed
And the 'Summary of recent reports' section is displayed in PS Overview
And the 'Summary of recent reports' section has 'AI is turned off' information
When the user clicks on the "Turn on AI" button on 'Summary of recent reports' section
Then the 'Summary of recent reports' section does not have 'AI is turned off' information
And the 'Summary of recent reports' section has the title: "Summary of recent reports and notes"
And the 'Summary of recent reports' section has an AI badge with text: "AI generated"
When the user hovers the 'i' symbol next to the AI badge on 'Summary of recent reports' section
Then tooltip is displayed with text: text_files/AI_info_tooltip_text_on_PS_Overview.txt

@integration
@SRS-68498.04
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68498,SysRS-68590,SysRS-68591
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Automated/Functional_Service/Summary_View/Progressive_summarization
Scenario: 'Brief history' and 'Summary of recent reports' is displayed in Progressive Summarization if AI is turned on
Given [API] the AI data visualization is switched OFF for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "TEST_PS, Hank" patient
Then the Progressive Summarization Overview is displayed
And the 'Brief history' section has 'AI is turned off' information
When the user clicks on the "Settings" menu item on the user menu panel
Then the Settings page is displayed
When the user clicks on 'Edit' button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user selects the "On" radio button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user clicks on the "Save" button on the Settings page
When the user clicks on the "Back" button in the Settings toolbar header
Then the patient summary view is loaded
And the Progressive Summarization Overview is displayed
And the 'Brief history' section does not have 'AI is turned off' information
And the 'Summary of recent reports' section does not have 'AI is turned off' information

@manual
@integration
@SRS-68498.05
@Platform:WEB
@Category:Negative
@Traceability:SysRS-68498
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Summary_View/Progressive_summarization
Scenario: Error message is displayed when PS data cannot be loaded
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And [DEVTOOLS] - the following text pattern was added to "Network request blocking" tool:
| text pattern |
| ai-overview  |
And [DEVTOOLS] - the "Enable network request blocking" checkbox is CHECKED in the "Network request blocking" tool
And [DEVTOOLS] - the "ai-overview" item is selected in the list of "Enable network request blocking"
When the user selects the "TEST_PS, 5e" patient
Then the following error message is displayed on the "Summary" view: "Failed to load patient data."
And "Try again" button is available on the page
When [DEVTOOLS] - the user unchecks "Enable network request blocking" checkbox
And the user clicks on the "Try again" button
Then the "Summary" view is displayed
And the Progressive Summarization panel is displayed
And the error message disappeared
