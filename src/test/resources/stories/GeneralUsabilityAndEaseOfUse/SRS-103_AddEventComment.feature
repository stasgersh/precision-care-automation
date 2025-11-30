Feature: [SysRS-68627] Add Event Comment

Narrative: The system users shall be able to add comments to the Events.


Background:
Given [API] the [DEFAULT-TEST-USER] user has no comments for the following event:
| patientID                            | root_resource_of_the_event                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/ef98eb70-cb59-4cd3-bc70-97bb04bdb4df |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/178b6d6a-39f7-4a06-b15f-d67702e84208 |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application

@sanity
@edge
@SRS-103.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68627
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use
Scenario: Add comment to an event
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
And the sidebar content contains the following data:
| data_type | title                | value                                             |
| KEY-VALUE | Conclusion           | To test content type: "text/plain; charset=utf-8" |
| KEY-VALUE | Status               | <ANY_VALUE>                                       |
| LINK      | Open complete report | <N/A>                                             |
And the sidebar header contains the following comments information: "0 comments"
And the comment input box is available on the sidebar
And comment is not available on the sidebar
When the user types the following text into the comment box on the sidebar: "this is an event comment test text in E2E test"
And the user clicks on the send button next to the comment box on the sidebar
Then the sidebar contains the following comments:
| author (firstname lastname) | date_and_time           | content                                        |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | this is an event comment test text in E2E test |
And the sidebar header contains the following comments information: "1 comment"
When the user clicks on the following event point on the "Pathology and Labs" swimlane:
| event_type | name_on_label                  |
| MARKER     | Tissue Pathology biopsy report |
Then comment is not available on the sidebar
When the user clicks on the following event point on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
Then the sidebar contains the following comments:
| author (firstname lastname) | date_and_time           | content                                        |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | this is an event comment test text in E2E test |

@sanity
@SRS-103.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68627
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use
Scenario: Event comment is kept after the user logs out from the application
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
And comment is not available on the sidebar
When the user types the following text into the comment box on the sidebar: "This is an event comment in E2E test. Is kept after logout?"
And the user clicks on the send button next to the comment box on the sidebar
Then the sidebar contains the following comments:
| author (firstname lastname) | date_and_time           | content                                                     |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | This is an event comment in E2E test. Is kept after logout? |
When the user logs out from OncoCare
And the [DEFAULT-TEST-USER] user logs in to OncoCare
And the user selects the "OncoTestPatient, Juno" patient
And the user navigates to the "Timeline" view
And the user clicks on the following event point on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
Then the sidebar contains the following comments:
| author (firstname lastname) | date_and_time           | content                                                     |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | This is an event comment in E2E test. Is kept after logout? |

@SRS-103.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68627
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Add_Event_Comment
Scenario: Add additional comments to an event (Latest comment is at the bottom of the page)
Given [API] the below event comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | root_resource_of_the_event                            | comment_text                                   |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/ef98eb70-cb59-4cd3-bc70-97bb04bdb4df | test event comment text was written previously |
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
And the sidebar contains the following comments:
| author (firstname lastname) | date_and_time           | content                                        |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | test event comment text was written previously |
And the sidebar header contains the following comments information: "1 comment"
When the user types the following text into the comment box on the sidebar: "I have read the report, please check it again"
And the user clicks on the send button next to the comment box on the sidebar
Then the sidebar contains the comments in the following order:
| author (firstname lastname) | date_and_time           | content                                        |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | test event comment text was written previously |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | I have read the report, please check it again  |
When the user types the following text into the comment box on the sidebar: "repeat x-ray in 4 weeks!"
And the user clicks on the send button next to the comment box on the sidebar
Then the sidebar contains the comments in the following order:
| author (firstname lastname) | date_and_time           | content                                        |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | test event comment text was written previously |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | I have read the report, please check it again  |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | repeat x-ray in 4 weeks!                       |
And the sidebar header contains the following comments information: "3 comments"

@SRS-103.04
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68627
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Add_Event_Comment
Scenario: Information message is displayed if event comment is not available
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
Then the comment input box is available on the sidebar
And comment is not available on the sidebar
And the following message about not available comments is displayed on the sidebar: "This event has no comments yet."

@SRS-103.15
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68627
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Add_Event_Comment
Scenario: Comment badge is displayed on event label if the event has a comment
Given [API] the following event were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following event:
| patientID                            | root_resource_of_the_event                       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f |
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the "Body Weight 47.3 kg" event MARKER is available on the "Encounters" swimlane without any badges
When the user clicks on the following event point on the "Encounters" swimlane:
| event_type | name_on_label       |
| MARKER     | Body Weight 47.3 kg |
Then the sidebar header contains the following comments information: "0 comments"
When the user types the following text into the comment box on the sidebar: "this is an event comment test text in E2E test to test comment badges"
And the user clicks on the send button next to the comment box on the sidebar
Then the sidebar header does not contain any badges
And the sidebar header contains the following comments information: "1 comment"
And the "Body Weight 47.3 kg" event MARKER is available on the "Encounters" swimlane with the below badges:
| badge_type | badge_text |
| COMMENT    | 1 comment  |
When the user types the following text into the comment box on the sidebar: "second comment"
And the user clicks on the send button next to the comment box on the sidebar
Then the sidebar header does not contain any badges
And the sidebar header contains the following comments information: "2 comments"
And the "Body Weight 47.3 kg" event MARKER is available on the "Encounters" swimlane with the below badges:
| badge_type | badge_text |
| COMMENT    | 2 comments |

@SRS-103.16
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68627
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Add_Event_Comment
Scenario: Comment badge is miniaturized on event label if the event has more badges
Given [API] the following event was marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f |
And [API] default labels are added to the following event by the [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                       | labels         |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f | MDT discussion |
And [API] the below event comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | root_resource_of_the_event                       | comment_text               |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f | another test event comment |
And the "OncoTestPatient, Juno" patient is selected
When the user navigates to the "Timeline" view
Then the patient timeline is loaded
And the "Body Weight 47.3 kg" event MARKER is available on the "Encounters" swimlane with 3 miniaturized badges:
| badge_types_in_order |
| IMPORTANT_EVENT      |
| COMMENT              |
| LABEL                |

@SRS-103.17
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68627,SysRS-68635
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Add_Event_Comment
Scenario: Events can be filtered on timeline based on event comment availability
Given [API] the below event comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | root_resource_of_the_event                            | comment_text        |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/ef98eb70-cb59-4cd3-bc70-97bb04bdb4df | filter comment test |
And the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Timeline" view
And the following events are available on the "Pathology and Labs" swimlane:
| event_type | name_on_label                  | date_on_timeline_axis |
| MARKER     | Test Diagnostic Report         | May 11, 2009          |
| MARKER     | Tissue Pathology biopsy report | Jan 15, 2020          |
When the user set the following timeline filter option:
| filter_group | checkbox_name | selected |
| Labels       | Has comments  | true     |
Then the following events are available on the "Pathology and Labs" swimlane:
| event_type | name_on_label          | date_on_timeline_axis |
| MARKER     | Test Diagnostic Report | May 11, 2009          |
And the following events are not available on the "Pathology and Labs" swimlane:
| event_type | name_on_label                  | date_on_timeline_axis |
| MARKER     | Tissue Pathology biopsy report | Jan 15, 2020          |
Then the filter counter icon on the Filter button is displayed with number: 1
And the following filter badge is available in the filter list on the timeline toolbar:
| filter        |
| Has comments  |
When the user set the following timeline filter option:
| filter_group | checkbox_name | selected |
| Labels       | Has comments  | false    |
Then the following events are available on the "Pathology and Labs" swimlane:
| event_type | name_on_label                  | date_on_timeline_axis |
| MARKER     | Test Diagnostic Report         | May 11, 2009          |
| MARKER     | Tissue Pathology biopsy report | Jan 15, 2020          |
And the filter counter icon on the Filter button is not displayed
And filter badge is not available in the filter list on the timeline toolbar

@SRS-103.18
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68627
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Add_Event_Comment
Scenario: Event comments are available on the Comments view and the user can navigate to the timline event from the Comments view
Given [API] the [DEFAULT-TEST-USER] user has neither patient nor event comments for the following patients:
| patientID                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a |
And [API] the comment settings are reset for "OncoTestPatient, Juno" by the [DEFAULT-TEST-USER] user
And [API] the below patient comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | comment_text              |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | this is a patient comment |
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the counter badge displays "1" on the "Comments" tab
When the user clicks on the following event point on the "Encounters" swimlane:
| event_type | name_on_label       |
| MARKER     | Body Weight 47.3 kg |
Then the sidebar header contains the following comments information: "0 comments"
When the user types the following text into the comment box on the sidebar: "this is an event comment"
And the user clicks on the send button next to the comment box on the sidebar
Then the sidebar contains the following comments:
| author (firstname lastname) | date_and_time           | content                  |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | this is an event comment |
And the sidebar header contains the following comments information: "1 comment"
And the counter badge displays "2" on the "Comments" tab
When the user navigates to the "Comments" view
Then the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time | content                   | badge            | has_view_on_timeline_link |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | this is a patient comment | <N/A>            | <N/A>                     |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | this is an event comment  | Timeline comment | yes                       |
When the user clicks on the 'View on timeline' button in the footer of the following comment on the Comments view:
| author (firstname lastname) | date_and_time | content                   | badge            | has_view_on_timeline_link |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | this is an event comment  | Timeline comment | yes                       |
Then the patient timeline is loaded
And the sidebar is displayed
And the sidebar header contains the following data:
| title               | creation_date | report_type |
| Body Weight 47.3 kg | Jan 23, 2013  | Encounters  |
And the sidebar contains the following comments:
| author (firstname lastname) | date_and_time           | content                  |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | this is an event comment |

@SRS-103.19
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68627
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Add_Event_Comment
Scenario: User can hide event comments on the Comments view
Given [API] the [DEFAULT-TEST-USER] user has neither patient nor event comments for the following patients:
| patientID                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a |
And [API] the comment settings are reset for "OncoTestPatient, Juno" by the [DEFAULT-TEST-USER] user
And [API] the below patient comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | comment_text                |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | this is a patient comment 1 |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | this is a patient comment 2 |
And [API] the below event comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | root_resource_of_the_event                       | comment_text               |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f | This is an event comment 1 |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f | This is an event comment 2 |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the patient summary view is loaded
And the counter badge displays "4" on the "Comments" tab
When the user navigates to the "Comments" view
Then the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time | content                     | badge            | has_view_on_timeline_link |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | this is a patient comment 1 | <N/A>            | <N/A>                     |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | this is a patient comment 2 | <N/A>            | <N/A>                     |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | This is an event comment 1  | Timeline comment | yes                       |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | This is an event comment 2  | Timeline comment | yes                       |
When the user clicks on the 'Hide Timeline comments' on the Comments view
Then the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time | content                     | badge            | has_view_on_timeline_link |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | this is a patient comment 1 | <N/A>            | <N/A>                     |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | this is a patient comment 2 | <N/A>            | <N/A>                     |
And the following comments are not available on the Comments view:
| author (firstname lastname) | date_and_time | content                     | badge            | has_view_on_timeline_link |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | This is an event comment 1  | Timeline comment | yes                       |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | This is an event comment 2  | Timeline comment | yes                       |
And the counter badge displays "4" on the "Comments" tab
When the user clicks on the 'Hide Timeline comments' on the Comments view again
Then the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time | content                     | badge            | has_view_on_timeline_link |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | this is a patient comment 1 | <N/A>            | <N/A>                     |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | this is a patient comment 2 | <N/A>            | <N/A>                     |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | This is an event comment 1  | Timeline comment | yes                       |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | This is an event comment 2  | Timeline comment | yes                       |
And the counter badge displays "4" on the "Comments" tab

@sanity
@Category:Positive
@Platform:WEB
@Traceability:SysRS-68627
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/General_Usability_And_Ease_Of_Use
Scenario: Event comment box keeps text when users select "Keep editing" alert modal option
Given the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Timeline" view
And the following event point is selected on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
And the comment input box is available on the sidebar
When the user types the following text into the comment box on the sidebar: "This message should be kept in the box."
And the user clicks on the Close button on the sidebar
Then an alert modal is displayed
And the alert modal contains the following title: "Leave page?"
And the alert modal contains the following message: text_files/Leave_comment_alert.txt
When the user clicks on the "Keep editing" button on the alert modal
Then the alert modal disappears
And the comment input box on the sidebar contains the following text: "This message should be kept in the box."
When the user clicks on the following event point on the "Pathology and Labs" swimlane:
| event_type | name_on_label    |
| MARKER     | Pathology Report |
Then an alert modal is displayed
And the alert modal contains the following title: "Leave page?"
And the alert modal contains the following message: text_files/Leave_comment_alert.txt
When the user clicks on the 'X' button on the alert modal
Then the alert modal disappears
And the comment input box on the sidebar contains the following text: "This message should be kept in the box."
When the user clicks on the "Summary" tab
Then an alert modal is displayed
And the alert modal contains the following title: "Leave page?"
And the alert modal contains the following message: text_files/Leave_comment_alert.txt
When the user clicks on the "Keep editing" button on the alert modal
Then the alert modal disappears
And the comment input box on the sidebar contains the following text: "This message should be kept in the box."
When the user selects the "OncoTestPatient, Freya" patient
Then an alert modal is displayed
And the alert modal contains the following title: "Leave page?"
And the alert modal contains the following message: text_files/Leave_comment_alert.txt
When the user clicks on the "Keep editing" button on the alert modal
Then the alert modal disappears
And the comment input box on the sidebar contains the following text: "This message should be kept in the box."
When the user clicks on the "Settings" menu item on the user menu panel
Then an alert modal is displayed
And the alert modal contains the following title: "Leave page?"
And the alert modal contains the following message: text_files/Leave_comment_alert.txt
When the user clicks on the "Keep editing" button on the alert modal
Then the alert modal disappears
And comment is not available on the sidebar
And the comment input box on the sidebar contains the following text: "This message should be kept in the box."
When the user clicks on the send button next to the comment box on the sidebar
Then comment is available on the sidebar
And the sidebar contains the following comments:
| author (firstname lastname) | date_and_time | content                                 |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | This message should be kept in the box. |

@srs
@Category:Positive
@Platform:WEB
@Traceability:SysRS-68627
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Add_Event_Comment
Scenario: Event comment box does not keep text when users select "Leave page" alert modal option
Given the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Timeline" view
And the following event point is selected on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
And the comment input box is available on the sidebar
When the user types the following text into the comment box on the sidebar: "This message should not be kept in the box."
And the user clicks on the Close button on the sidebar
Then an alert modal is displayed
And the alert modal contains the following title: "Leave page?"
And the alert modal contains the following message: text_files/Leave_comment_alert.txt
When the user clicks on the "Leave page" button on the alert modal
Then the alert modal disappears
When the user clicks on the following event point on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
Then comment is not available on the sidebar
And the comment input box on the sidebar contains the following text: ""

@srs
@manual
@Category:Positive
@Platform:WEB
@Traceability:SysRS-68627
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Add_Event_Comment
Scenario: Event comment box keeps text when users select "Cancel" browser alert box option
Given the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Timeline" view
And the following event point is selected on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
And the comment input box is available on the sidebar
When the user types the following text into the comment box on the sidebar: "This message should be kept in the box."
And the user clicks on the "GE HealthCare logo" item on the user menu panel
Then a browser alert box is displayed
And the browser alert box contains the following title: "Leave site?"
And the browser alert box contains the following message: Changes you made may not be saved.
When the user clicks on the "Cancel" button on the browser alert box
Then the browser alert box disappears
When the user clicks on the browser's refresh button
Then a browser alert box is displayed
And the browser alert box contains the following title: "Reload site?"
And the browser alert box contains the following message: Changes you made may not be saved.
When the user clicks on the "Cancel" button on the browser alert box
Then the browser alert box disappears
When the user clicks on the "Sign out" menu item on the user menu panel
Then a browser alert box is displayed
And the browser alert box contains the following title: "Leave site?"
And the browser alert box contains the following message: Changes you made may not be saved.
When the user clicks on the "Cancel" button on the browser alert box
Then the browser alert box disappears
And comment is not available on the Comments view
And the comment input box on the sidebar contains the following text: "This message should be kept in the box."
When the user clicks on the send button next to the comment box on the sidebar
Then comment is available on the sidebar
And the sidebar contains the following comments:
| author (firstname lastname) | date_and_time | content                                 |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | This message should be kept in the box. |

@srs
@manual
@Category:Positive
@Platform:WEB
@Traceability:SysRS-68627
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Add_Event_Comment
Scenario: Event comment box does not keep text when users select "Leave" browser alert box option
Given the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Timeline" view
And the following event point is selected on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
And the comment input box is available on the sidebar
And comment is not available on the sidebar
When the user types the following text into the comment box on the sidebar: "This message should not be kept in the box."
And the user clicks on the "GE HealthCare logo" item on the user menu panel
Then a browser alert box is displayed
And the browser alert box contains the following title: "Leave site?"
And the browser alert box contains the following message: Changes you made may not be saved.
When the user clicks on the "Leave" button on the browser alert
Then the browser alert box disappears
And the Patient page is loaded
When the user selects the "OncoTestPatient, Juno" patient
And the user navigates to the "Timeline" view
And the user clicks on the following event point on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
Then comment is not available on the sidebar
And the comment input box on the sidebar contains the following text: ""
