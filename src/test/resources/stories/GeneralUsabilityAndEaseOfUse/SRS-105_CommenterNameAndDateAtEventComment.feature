Feature: [SysRS-68630] Commenter's Name And Date At Event Comment

Narrative: The system shall automatically add the commenter’s name and the date to the saved Event comments.


@SRS-105.01
@edge
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68630
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use
Scenario: Commenter's name and the date is saved to the event comments
Given [API] the below event comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | root_resource_of_the_event                            | comment_text                                           |
| a0747980-7e02-4318-9ef7-0569643aed5a | DiagnosticReport/71daa363-6e8f-4c81-a0da-e2d6f6d8412b | This is a test event comment for Al. He has fever!? |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Eleanor470" patient is selected
And the "Timeline" view is selected
And the following event point is selected on the "Pathology and Labs" swimlane:
| event_type | name_on_label                                 |
| MARKER     | Lipid panel with direct LDL - Serum or Plasma |
And the sidebar contains the following comments:
| author (firstname lastname) | date_and_time           | content                                                |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | This is a test event comment for Al. He has fever!? |
When the user types the following text into the comment box on the sidebar: "Yes, low fever in 2016 but high fever in 2020. Long time fever, unknown disease."
And the user clicks on the send button next to the comment box on the sidebar
Then the sidebar contains the following comments:
| author (firstname lastname) | date_and_time           | content                                                                          |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | This is a test event comment for Al. He has fever!?                           |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | Yes, low fever in 2016 but high fever in 2020. Long time fever, unknown disease. |
When the user clicks on the Close button on the sidebar
Then the sidebar is not displayed
When the user clicks on the following event point on the "Pathology and Labs" swimlane:
| event_type | name_on_label                                 |
| MARKER     | Lipid panel with direct LDL - Serum or Plasma |
Then the sidebar is displayed
And the sidebar contains the following comments:
| author (firstname lastname) | date_and_time           | content                                                                          |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | This is a test event comment for Al. He has fever!?                           |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | Yes, low fever in 2016 but high fever in 2020. Long time fever, unknown disease. |
