@safety
Feature: [SysRS-68384] LDAP integration

Narrative: The application shall be able to leverage the federation between the hospital’s LDAP server and Cloud IDAM service for account management.

@SRS-4.01
@sanity
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68384
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Manual/Functional_Service/Data_processing_and_Fulfillment
Scenario: User can log in with SSO
Given I'm on OncoCare splash screen
When I click the Sign in button
Then the Login page is loaded
When I log in with my SSO credentials
Then the patient page is loaded
When I expand the user menu panel
Then my name is displayed on the panel (in firstname lastname order)

@SRS-4.02
@integration
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68384
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Data_processing_and_Fulfillment
Scenario: Name of the user is displayed on Summary report
Given I logged in to OncoCare application with my SSO credentials
And the "OncoTestPatient, Torvald" patient is selected
When I click on the "Download" option in the More menu
And I select the "Entire summary" option
And I save the file
Then the saved pdf file contains my name (in "Lastname, Firstname" format)

@SRS-4.03
@integration
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68384
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Data_processing_and_Fulfillment
Scenario: Name of the user is displayed on comments
Given I logged in to OncoCare application with my SSO credentials
And the "OncoTestPatient, Torvald" patient is selected
And the following event point was selected on the "Encounters" swimlane:
| event_type | name_on_label                 |
| MARKER     | History and physical note - 2 |
When the user types the following text into the comment box on the sidebar: "this is an event test comment"
And the user clicks on the send button next to the comment box on the sidebar
Then the sidebar contains the following comments:
| author               | date_and_time           | content                       |
| <firstname lastname> | <CURRENT_DATE_AND_TIME> | this is an event test comment |
When I add the following comment on "Comments" view: "this is a test comment"
Then the following comments are available on the Comments view:
| author               | date_and_time | content                       |
| <firstname lastname> | <ANY_VALUE>   | this is an event test comment |
| <firstname lastname> | <ANY_VALUE>   | this is a test comment        |

@SRS-4.04
@integration
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68384
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Data_processing_and_Fulfillment
Scenario: Users can't see other users' comment
Given I logged in to OncoCare application with my SSO credentials
And the "OncoTestPatient, Torvald" patient was selected
And I added a comment to the following event on the "Encounters" swimlane:
| event_type | name_on_label                 | comment                       |
| MARKER     | History and physical note - 2 | this is an event test comment |
And the following comment was added on the Comments view:
| author               | date_and_time | content                |
| <firstname lastname> | <ANY_VALUE>   | this is a test comment |
And the following comments are available on the Comments view:
| author               | date_and_time | content                       |
| <firstname lastname> | <ANY_VALUE>   | this is an event test comment |
| <firstname lastname> | <ANY_VALUE>   | this is a test comment        |
When I log out
And I log in with a different SSO credential
And I select "OncoTestPatient, Torvald" patient
And I navigate to "Comments" view
Then comment is not available on the Comments view
And the following message is displayed on the empty Comments view: "This patient has no comments yet."