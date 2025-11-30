@safety
Feature: [SysRS-68631] Delete Event Comments

Narrative: The system users shall be able to delete Event comments.


@sanity
@edge
@SRS-106.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68631
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use
Scenario: Delete event comment
Given [API] the below event comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | root_resource_of_the_event                            | comment_text                                                                   |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/ef98eb70-cb59-4cd3-bc70-97bb04bdb4df | Test comment text is written here to test delete comment on side panel.... Ok? |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/ef98eb70-cb59-4cd3-bc70-97bb04bdb4df | Patient status seems to be ok.                                                 |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the following event point is selected on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
And the sidebar contains the following comments:
| author (firstname lastname) | date_and_time | content                                                                        |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | Test comment text is written here to test delete comment on side panel.... Ok? |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | Patient status seems to be ok.                                                 |
And the sidebar header contains the following comments information: "2 comments"
When the user clicks on the 'Delete' button in the menu of the following comment on the sidebar:
| author (firstname lastname) | date_and_time | content                                                                        |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | Test comment text is written here to test delete comment on side panel.... Ok? |
Then the sidebar contains the following comments:
| author (firstname lastname) | date_and_time | content                        |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | Patient status seems to be ok. |
When the user clicks on the Close button on the sidebar
Then the sidebar is not displayed
When the user clicks on the following event point on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
Then the sidebar is displayed
And the sidebar contains the following comments:
| author (firstname lastname) | date_and_time | content                        |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | Patient status seems to be ok. |
And the sidebar header contains the following comments information: "1 comment"

@SRS-106.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68631
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use
Scenario: Comment badge is removed from the event label if all comments removed from the event
Given [API] the following events were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f |
And [API] the below event comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | root_resource_of_the_event                       | comment_text         |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f | test event comment 1 |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f | test event comment 2 |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the "Body Weight 47.3 kg" event MARKER is available on the "Encounters" swimlane with the below badges:
| badge_type | badge_text |
| COMMENT    | 2 comments |
When the user clicks on the following event point on the "Encounters" swimlane:
| event_type | name_on_label        |
| MARKER     | Body Weight 47.3 kg  |
Then the sidebar header contains the following comments information: "2 comments"
When the user clicks on the 'Delete' button in the menu of the following comment on the sidebar:
| author (firstname lastname) | date_and_time | content              |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | test event comment 1 |
Then the sidebar header contains the following comments information: "1 comment"
And the "Body Weight 47.3 kg" event MARKER is available on the "Encounters" swimlane with the below badges:
| badge_type | badge_text |
| COMMENT    | 1 comment  |
When the user clicks on the 'Delete' button in the menu of the following comment on the sidebar:
| author (firstname lastname) | date_and_time | content              |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | test event comment 2 |
Then the sidebar header contains the following comments information: "0 comments"
And the "Body Weight 47.3 kg" event MARKER is available on the "Encounters" swimlane without any badges

@SRS-106.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68631
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use
Scenario: Event comments can be deleted on the sidepanel and on the Comments view too
Given [API] the [DEFAULT-TEST-USER] user has neither patient nor event comments for the following patients:
| patientID                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a |
And [API] the below patient comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | comment_text                |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | this is a patient comment 1 |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | this is a patient comment 2 |
And [API] the below event comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | root_resource_of_the_event                       | comment_text               |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f | This is an event comment 1 |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f | This is an event comment 2 |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f | This is an event comment 3 |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the counter badge displays "5" on the "Comments" tab
When the user clicks on the following event point on the "Encounters" swimlane:
| event_type | name_on_label        |
| MARKER     | Body Weight 47.3 kg  |
Then the sidebar header contains the following comments information: "3 comments"
When the user clicks on the 'Delete' button in the menu of the following comment on the sidebar:
| author (firstname lastname) | date_and_time | content                    |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | This is an event comment 1 |
Then the sidebar contains the following comments:
| author (firstname lastname) | date_and_time | content                    |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | This is an event comment 2 |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | This is an event comment 3 |
And the sidebar header contains the following comments information: "2 comments"
And the counter badge displays "4" on the "Comments" tab
When the user navigates to the "Comments" view
Then the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time | content                     | badge            | has_view_on_timeline_link |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | this is a patient comment 1 | <N/A>            | <N/A>                     |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | this is a patient comment 2 | <N/A>            | <N/A>                     |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | This is an event comment 2  | Timeline comment | yes                       |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | This is an event comment 3  | Timeline comment | yes                       |
When the user clicks on the 'Delete' button in the menu of the following comment on the Comments view:
| author (firstname lastname) | date_and_time | content                     | badge            | has_view_on_timeline_link |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | This is an event comment 2  | Timeline comment | yes                       |
Then the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time | content                     | badge            | has_view_on_timeline_link |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | this is a patient comment 1 | <N/A>            | <N/A>                     |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | this is a patient comment 2 | <N/A>            | <N/A>                     |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | This is an event comment 3  | Timeline comment | yes                       |
And the counter badge displays "3" on the "Comments" tab
When the user navigates to the "Timeline" view
And the patient timeline is loaded
And the user opens the following event point on the "Encounters" swimlane:
| event_type | name_on_label        |
| MARKER     | Body Weight 47.3 kg  |
Then the sidebar header contains the following comments information: "1 comment"
And the sidebar contains the following comments:
| author (firstname lastname) | date_and_time | content                    |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | This is an event comment 3 |
