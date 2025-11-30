Feature: [SysRS-68579] Users Only See Their Own Patient Comments

Narrative: The application users shall only see their own Patient comments.


@sanity
@SRS-101.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68579
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Comment_view
Scenario: Users can see only their own patient comments
Given the user is logged out from OncoCare application
And [API] the [DEFAULT-TEST-USER] user has no comments for the following patients:
| patientID                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a |
And [API] the below patient comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | comment_text                                |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | some test patient comment text is written 1 |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | some test patient comment text is written 2 |
And [API] the below patient comments were added previously by the user [TEST-USER-2]:
| patientID                            | comment_text                         |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | some other comment by another user   |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | some other comment by another user 2 |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
When the user navigates to the "Comments" view
Then the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time | content                                     |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | some test patient comment text is written 1 |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | some test patient comment text is written 2 |
And the comment input box is available on the Comments view
When the user types the following text into the comment box on the Comments view: "new comment by this user"
And the user clicks on the send button next to the comment box on the Comments view
Then the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time           | content                                     |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>             | some test patient comment text is written 1 |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>             | some test patient comment text is written 2 |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | new comment by this user                    |
And the following information message is displayed on the Comments view: "Only you can see these comments. Comments are only available in CareIntellect for Oncology."
When the user logs out from OncoCare
And the [TEST-USER-2] user logs in to OncoCare
And the user selects the "OncoTestPatient, Juno" patient
And the user navigates to the "Comments" view
Then the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time | content                              |
| [TEST-USER-2]               | <ANY_VALUE>   | some other comment by another user   |
| [TEST-USER-2]               | <ANY_VALUE>   | some other comment by another user 2 |

