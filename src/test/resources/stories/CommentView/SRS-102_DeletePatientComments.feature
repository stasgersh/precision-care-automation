@safety
Feature: [SysRS-68580] Delete Patient Comments

Narrative: The system shall provide the user the ability to delete Patient comments.


Background:
Given [API] the below patient comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | comment_text                                                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | some test patient comment text is written to test delete functionality 1 |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | some test patient comment text is written to test delete functionality 2 |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application

@sanity
@edge
@SRS-102.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68580
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Comment_view
Scenario: Delete patient comment
Given the "OncoTestPatient, Juno" patient is selected
And the patient summary view is loaded
And the counter badge displays "2" on the "Comments" tab
And the user navigated to the "Comments" view
And the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time | content                                                                  |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | some test patient comment text is written to test delete functionality 1 |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | some test patient comment text is written to test delete functionality 2 |
When the user clicks on the 'Delete' button in the menu of the following comment on the Comments view:
| author (firstname lastname) | date_and_time | content                                                                  |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | some test patient comment text is written to test delete functionality 2 |
Then the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time | content                                                                  |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | some test patient comment text is written to test delete functionality 1 |
And the counter badge displays "1" on the "Comments" tab
When the user navigates to the "Medical history" view
Then the Medical history view is displayed
When the user navigates to the "Comments" view
Then the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time | content                                                                  |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | some test patient comment text is written to test delete functionality 1 |
