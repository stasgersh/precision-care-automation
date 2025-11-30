Feature: [SysRS-68610] Product Operating Environment

Narrative: The system shall provide web-based access to the users in US English language.


@SRS-44.01
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68610
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Product_Operating_Environment
Scenario: All elements of patient history, medications, trends and comments are displayed in English
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
When the user navigates to the "Medical history" view
Then all text elements are in English language
When the user navigates to the "Comments" view
Then all text elements are in English language
When the user selects the "OncoTestPatient, Empty" patient
And the user navigates to the "Trends" view
Then all text elements are in English language

@SRS-44.02
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68610
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Product_Operating_Environment
Scenario: Confirmation modal of unsaved settings is displayed in English
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
And the "Settings" menu item is selected on the user menu panel
And the Settings page is displayed
When the user clicks on 'Edit' button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user clicks on the "Back" button in the Settings toolbar header
Then all text elements are in English language on the "Leave page?" modal

@SRS-44.03
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68610
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Product_Operating_Environment
Scenario: All elements of the patient banner, the timeline and the sidebar are displayed in English
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
When the user expands the patient banner
Then all text elements are in English language on the the patient banner
When the user clicks on the following event point on the "Encounters" swimlane:
| event_type | name_on_label                 |
| MARKER     | History and physical note - 1 |
Then all text elements are in English language on the sidebar
When the user opens the timeline filter modal
Then all text elements are in English language on the filter modal
When the user hovers the main user menu on the left side
Then all text elements are in English language in the main data menu
