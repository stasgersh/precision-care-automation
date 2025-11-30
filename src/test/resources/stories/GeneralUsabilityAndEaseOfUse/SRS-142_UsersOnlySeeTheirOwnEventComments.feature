Feature: [SysRS-68628] Users Only See Their Own Event Comments

Narrative: The system users shall only see their own Event comments.


@sanity
@SRS-142.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68628
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use
Scenario: Users can see only their own event comments
Given the user is logged out from OncoCare application
And [API] the below event comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | root_resource_of_the_event                            | comment_text                                                    |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/ef98eb70-cb59-4cd3-bc70-97bb04bdb4df | this is only an even tcomment text which was written previously |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/ef98eb70-cb59-4cd3-bc70-97bb04bdb4df | a simple comment                                                |
And [API] the below event comments were added previously by the user [TEST-USER-2]:
| patientID                            | root_resource_of_the_event                            | comment_text                                                    |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/ef98eb70-cb59-4cd3-bc70-97bb04bdb4df | and another comment by another \"test\" user!                   |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/ef98eb70-cb59-4cd3-bc70-97bb04bdb4df | a simple comment                                                |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
And the comment input box is available on the sidebar
When the user types the following text into the comment box  on the sidebar: "this is a test of event comment text in E2E test"
And the user clicks on the send button next to the comment box on the sidebar
Then the sidebar contains the following comments:
| author (firstname lastname) | date_and_time           | content                                                         |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | this is only an even tcomment text which was written previously |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | a simple comment                                                |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | this is a test of event comment text in E2E test                |
And the following comment information message is displayed on the sidebar: "Only you can see these comments. Comments are only available in CareIntellect for Oncology."
When the user logs out from OncoCare
And the [TEST-USER-2] user logs in to OncoCare
And the user selects the "OncoTestPatient, Juno" patient
And the user navigates to the "Timeline" view
And the user clicks on the following event point on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
Then the sidebar contains the following comments:
| author (firstname lastname) | date_and_time | content                                     |
| [TEST-USER-2]               | <ANY_VALUE>   | and another comment by another "test" user! |
| [TEST-USER-2]               | <ANY_VALUE>   | a simple comment                            |
