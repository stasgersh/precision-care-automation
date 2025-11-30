Feature: [SysRS-68578] Commenter's Name And Date At Patient Comment

Narrative: The application shall automatically add the commenter’s name and the date to the saved Patient comments.


@sanity
@edge
@SRS-100.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68578
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Comment_view
Scenario: Commenter's name and the date is saved to the patient comments
Given [API] the below patient comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | comment_text                                        |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | initial patient comment: further examination needed |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
When the user navigates to the "Comments" view
Then the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time           | content                                             |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | initial patient comment: further examination needed |
When the user types the following text into the comment box on the Comments view: "this is a patient comment again to test it in E2E test"
And the user clicks on the send button next to the comment box on the Comments view
Then the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time           | content                                                |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | initial patient comment: further examination needed    |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | this is a patient comment again to test it in E2E test |
When the user navigates to the "Medical history" view
Then the Medical history view is displayed
When the user navigates to the "Comments" view
Then the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time           | content                                                |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | initial patient comment: further examination needed    |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | this is a patient comment again to test it in E2E test |
