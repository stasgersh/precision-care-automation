Feature: [SysRS-68634] Mark As Important

Narrative: The system users shall be able to mark important events.


Background:
Given [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                     |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Encounter/65b5d7bf-07b5-4b03-a5fa-765ff2b1d825 |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                     |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Encounter/65b5d7bf-07b5-4b03-a5fa-765ff2b1d825 |

@sanity
@edge
@SRS-26.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68634,SysRS-68413
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use/Mark_As_Important
Scenario: User can mark an event as important
Given [API] the following event was not marked as important for [TEST-USER-2] user:
| patientID                            | root_resource_of_the_event                     |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Encounter/65b5d7bf-07b5-4b03-a5fa-765ff2b1d825 |
And the [TEST-USER-2] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
And the following events are available on the "Encounters" swimlane:
| event_type | name_on_label                        |
| MARKER     | Emergency room admission (procedure) |
When the user clicks on the following event point on the "Encounters" swimlane:
| event_type | name_on_label                        |
| MARKER     | Emergency room admission (procedure) |
Then the sidebar is displayed
And the sidebar header contains the following data:
| title                                | creation_date | report_type |
| Emergency room admission (procedure) | Jun 08, 2018  | Encounters  |
And the 'Mark as important' section is displayed as:
| header_title_highlighted | star_filled | displayed_text    |
| no                       | no          | Mark as important |
And the sidebar header contains the following badges:
| badge_type     | badge_text |
| CLASSIFICATION | ER visit   |
When the user clicks 'Mark as important' button on the sidebar
Then the 'Mark as important' section is displayed as:
| header_title_highlighted | star_filled | displayed_text      |
| no                       | yes         | Marked as important |
And the sidebar header contains the following badges:
| badge_type      | badge_text |
| IMPORTANT_EVENT | Important  |
| CLASSIFICATION  | ER visit   |

@sanity
@SRS-26.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68634
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use/Mark_As_Important
Scenario: User can undo mark as important
Given [API] the following event was marked as important for [TEST-USER-2] user:
| patientID                            | root_resource_of_the_event                     |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Encounter/65b5d7bf-07b5-4b03-a5fa-765ff2b1d825 |
And the [TEST-USER-2] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
And the following events are available on the "Encounters" swimlane:
| event_type | name_on_label                        |
| MARKER     | Emergency room admission (procedure) |
And the following event point is selected on the "Encounters" swimlane:
| event_type | name_on_label                        |
| MARKER     | Emergency room admission (procedure) |
And the 'Mark as important' section is displayed as:
| header_title_highlighted | star_filled | displayed_text      |
| no                       | yes         | Marked as important |
And the sidebar header contains the following badges:
| badge_type      | badge_text |
| IMPORTANT_EVENT | Important  |
| CLASSIFICATION  | ER visit   |
When the user clicks 'Marked as important' button on the sidebar
Then the 'Mark as important' section is displayed as:
| header_title_highlighted | star_filled | displayed_text    |
| no                       | no          | Mark as important |
And the sidebar header contains the following badges:
| badge_type     | badge_text |
| CLASSIFICATION | ER visit   |

@sanity
@SRS-26.03
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68634
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use/Mark_As_Important
Scenario: Label of event marked as important has a badge - Single Event
Given [API] the following event was marked as important for [TEST-USER-2] user:
| patientID                            | root_resource_of_the_event                     |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Encounter/65b5d7bf-07b5-4b03-a5fa-765ff2b1d825 |
And the [TEST-USER-2] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Torvald" patient
And the user navigates to the "Timeline" view
Then the "Emergency room admission (procedure)" event MARKER is available on the "Encounters" swimlane with the below badges:
| badge_type      | badge_text |
| IMPORTANT_EVENT | Important  |
| CLASSIFICATION  | ER visit   |

@sanity
@SRS-26.04
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68634
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use/Mark_As_Important
Scenario: User can undo mark as important and then badge is removed from event label
Given [API] the following event was marked as important for [TEST-USER-2] user:
| patientID                            | root_resource_of_the_event                     |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Encounter/65b5d7bf-07b5-4b03-a5fa-765ff2b1d825 |
And the [TEST-USER-2] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Torvald" patient
And the user navigates to the "Timeline" view
Then the "Emergency room admission (procedure)" event MARKER is available on the "Encounters" swimlane with the below badges:
| badge_type      | badge_text |
| IMPORTANT_EVENT | Important  |
| CLASSIFICATION  | ER visit   |
When the user clicks on the following event point on the "Encounters" swimlane:
| event_type | name_on_label                        |
| MARKER     | Emergency room admission (procedure) |
And the user clicks 'Marked as important' button on the sidebar
And the user clicks on the Close button on the sidebar
Then the "Emergency room admission (procedure)" event MARKER is available on the "Encounters" swimlane with the below badges:
| badge_type     | badge_text |
| CLASSIFICATION | ER visit   |

@SRS-26.05
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68634
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Mark_As_Important
Scenario: Label suggests the number of important events if 2 events are concurrent
Given [API] the [DEFAULT-TEST-USER] user has no labels for the following events:
| patientID                            | root_resource_of_the_event                            |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/9e816b7f-037f-4d22-adbc-bae8af3cc3ad |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/52899193-5ff5-4323-82d5-a71772d5a33e |
And [API] the following event was not marked as important for [TEST-USER-2] user:
| patientID                            | root_resource_of_the_event                            |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/9e816b7f-037f-4d22-adbc-bae8af3cc3ad |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/52899193-5ff5-4323-82d5-a71772d5a33e |
And the [TEST-USER-2] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Torvald" patient
And the user navigates to the "Timeline" view
Then the "2 events" event CLUSTER is available on the "Pathology and Labs" swimlane with the below badges:
| badge_type     | badge_text |
| CLASSIFICATION | Pathology  |
When the user clicks on the following event point on the "Pathology and Labs" swimlane:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 2                   | 2 events      |
And the user selects the following event from the cluster pill:
| event_name                       |
| Tissue Pathology biopsy report 2 |
Then the sidebar header contains the following data:
| title                            | creation_date | report_type        |
| Tissue Pathology biopsy report 2 | Jan 15, 2020  | Pathology and Labs |
When the user clicks 'Mark as important' button on the sidebar
Then the 'Mark as important' section is displayed as:
| header_title_highlighted | star_filled | displayed_text      |
| no                       | yes         | Marked as important |
And the "Tissue Pathology biopsy report 2" event is available in the custer pill with the below badges:
| badge_type      | badge_text |
| IMPORTANT_EVENT | Important  |
| CLASSIFICATION  | Pathology  |
When the user clicks on the Close button on the sidebar
And the user closes the cluster pill
Then the "2 events" event CLUSTER is available on the "Pathology and Labs" swimlane with the below badges:
| badge_type      | badge_text |
| IMPORTANT_EVENT | Important  |
| CLASSIFICATION  | Pathology  |
When the user clicks on the following event point on the "Pathology and Labs" swimlane:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 2                   | 2 events      |
And the user selects the following event from the cluster pill:
| event_name                     |
| Tissue Pathology biopsy report |
And the user clicks 'Mark as important' button on the sidebar
Then the 'Mark as important' section is displayed as:
| header_title_highlighted | star_filled | displayed_text      |
| no                       | yes         | Marked as important |
And the "Tissue Pathology biopsy report" event is available in the custer pill with the below badges:
| badge_type      | badge_text |
| IMPORTANT_EVENT | Important  |
| CLASSIFICATION  | Pathology  |
When the user clicks on the Close button on the sidebar
And the user closes the cluster pill
Then the "2 events" event CLUSTER is available on the "Pathology and Labs" swimlane with the below badges:
| badge_type      | badge_text  |
| IMPORTANT_EVENT | 2 Important |
| CLASSIFICATION  | Pathology   |

@SRS-26.06
@CLP
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68634
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Mark_As_Important
Scenario: Important event badge is the first badge if event with CLP data is marked as important
Given [API] the CLP visualization is switched ON for [TEST-USER-2] user
And [API] labels are added to the following events by the [TEST-USER-2] user:
| patientID                            | root_resource_of_the_event                            | labels         |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc | To Be Reviewed |
And [API] the below event comments were added previously by the user [TEST-USER-2]:
| patientID                            | root_resource_of_the_event                            | comment_text     |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc | test badge order |
And [API] the following event was not marked as important for [TEST-USER-2] user:
| patientID                            | root_resource_of_the_event                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc |
And the [TEST-USER-2] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Juno" patient
And the user navigates to the "Timeline" view
Then the "CT THORAX ABDOMEN PELVIS WITH CONTRAST" event MARKER is available on the "Imaging" swimlane with 4 miniaturized badges:
| badge_types_in_order |
| CLASSIFICATION       |
| CLP                  |
| COMMENT              |
| LABEL                |
When the user clicks on the following event point on the "Imaging" swimlane:
| event_type | name_on_label                          |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST |
Then the sidebar header contains the following badges:
| badge_type     | badge_text                                  |
| CLASSIFICATION | CT                                          |
| CLP            | [keywords]: No evidence, pulmonary embolism |
| LABEL          | To Be Reviewed                              |
When the user clicks 'Mark as important' button on the sidebar
Then the sidebar header contains the following badges:
| badge_type      | badge_text                                  |
| IMPORTANT_EVENT | Important                                   |
| CLASSIFICATION  | CT                                          |
| CLP             | [keywords]: No evidence, pulmonary embolism |
| LABEL           | To Be Reviewed                              |
When the user clicks on the Close button on the sidebar
Then the "CT THORAX ABDOMEN PELVIS WITH CONTRAST" event MARKER is available on the "Imaging" swimlane with 5 miniaturized badges:
| badge_types_in_order |
| IMPORTANT_EVENT      |
| CLASSIFICATION       |
| CLP                  |
| COMMENT              |
| LABEL                |

@SRS-26.09
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68634
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Mark_As_Important
Scenario: Important event badge is the first badge if more badges are available on the event
Given [API] labels are added to the following events by the [TEST-USER-2] user:
| patientID                            | root_resource_of_the_event                             | labels        |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/0dbfc5f3-1427-45e0-a2b7-539ab0fc9f3c | Adverse Event |
And [API] the below event comments were added previously by the user [TEST-USER-2]:
| patientID                            | root_resource_of_the_event                             | comment_text       |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/0dbfc5f3-1427-45e0-a2b7-539ab0fc9f3c | test badge order 2 |
And [API] the following event was not marked as important for [TEST-USER-2] user:
| patientID                            | root_resource_of_the_event                             |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/0dbfc5f3-1427-45e0-a2b7-539ab0fc9f3c |
And the [TEST-USER-2] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Freya" patient
And the user navigates to the "Timeline" view
Then the "CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE" event MARKER is available on the "Treatment and Plan" swimlane with 4 miniaturized badges:
| badge_types_in_order |
| CYCLED_EVENT         |
| CLASSIFICATION       |
| COMMENT              |
| LABEL                |
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label                            |
| MARKER     | CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE |
Then the sidebar header contains the following badges:
| badge_type     | badge_text    |
| CYCLED_EVENT   | Cycle 0       |
| CLASSIFICATION | Systemic Tx   |
| LABEL          | Adverse Event |
When the user clicks 'Mark as important' button on the sidebar
Then the sidebar header contains the following badges:
| badge_type      | badge_text    |
| IMPORTANT_EVENT | Important     |
| CYCLED_EVENT    | Cycle 0       |
| CLASSIFICATION  | Systemic Tx   |
| LABEL           | Adverse Event |
When the user clicks on the Close button on the sidebar
Then the "CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE" event MARKER is available on the "Treatment and Plan" swimlane with 5 miniaturized badges:
| badge_types_in_order |
| IMPORTANT_EVENT      |
| CYCLED_EVENT         |
| CLASSIFICATION       |
| COMMENT              |
| LABEL                |

@SRS-26.07
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68634
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Mark_As_Important
Scenario: Event remains marked as important after logout
Given [API] the [TEST-USER-2] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                               |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | MedicationStatement/359ab4ca-4a30-46b8-8f68-8a1bfddc7056 |
And [API] the following event was not marked as important for [TEST-USER-2] user:
| patientID                            | root_resource_of_the_event                               |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | MedicationStatement/359ab4ca-4a30-46b8-8f68-8a1bfddc7056 |
And the [TEST-USER-2] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
And the following events are available on the "Treatment and Plan" swimlane:
| event_type | name_on_label                   | date_on_timeline_axis |
| MARKER     | cyclophosphamide 1 GM Injection | Feb 24, 2014          |
And the following event point is selected on the "Treatment and Plan" swimlane:
| event_type | name_on_label                   | date_on_timeline_axis |
| MARKER     | cyclophosphamide 1 GM Injection | Feb 24, 2014          |
When the user clicks 'Mark as important' button on the sidebar
Then the 'Mark as important' section is displayed as:
| header_title_highlighted | star_filled | displayed_text      |
| no                       | yes         | Marked as important |
When the user logs out from OncoCare
And the [TEST-USER-2] user logs in to OncoCare
And the user selects the "OncoTestPatient, Torvald" patient
And the user navigates to the "Timeline" view
Then the "cyclophosphamide 1 GM Injection" event MARKER is available on the "Treatment and Plan" swimlane at "Feb 24, 2014" with the below badges:
| badge_type      | badge_text  |
| IMPORTANT_EVENT | Important   |
| CLASSIFICATION  | Systemic Tx |
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label                   | date_on_timeline_axis |
| MARKER     | cyclophosphamide 1 GM Injection | Feb 24, 2014          |
Then the 'Mark as important' section is displayed as:
| header_title_highlighted | star_filled | displayed_text      |
| no                       | yes         | Marked as important |
And the sidebar header contains the following badges:
| badge_type      | badge_text  |
| IMPORTANT_EVENT | Important   |
| CLASSIFICATION  | Systemic Tx |

@SRS-26.08
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68634
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Mark_As_Important
Scenario: Event marked as important by a user do not affect the events of another user
Given [API] the [TEST-USER-2] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                               |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Encounter/65b5d7bf-07b5-4b03-a5fa-765ff2b1d825           |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | MedicationStatement/359ab4ca-4a30-46b8-8f68-8a1bfddc7056 |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                               |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Encounter/65b5d7bf-07b5-4b03-a5fa-765ff2b1d825           |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | MedicationStatement/359ab4ca-4a30-46b8-8f68-8a1bfddc7056 |
And [API] the following event was not marked as important for [TEST-USER-2] user:
| patientID                            | root_resource_of_the_event                               |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Encounter/65b5d7bf-07b5-4b03-a5fa-765ff2b1d825           |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | MedicationStatement/359ab4ca-4a30-46b8-8f68-8a1bfddc7056 |
And [API] the following event was not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                     |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Encounter/65b5d7bf-07b5-4b03-a5fa-765ff2b1d825 |
And [API] the following event was marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                               |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | MedicationStatement/359ab4ca-4a30-46b8-8f68-8a1bfddc7056 |
And the [TEST-USER-2] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
When the user clicks on the following event point on the "Encounters" swimlane:
| event_type | name_on_label                        |
| MARKER     | Emergency room admission (procedure) |
And the user clicks 'Mark as important' button on the sidebar
And the user clicks on the Close button on the sidebar
Then the "Emergency room admission (procedure)" event MARKER is available on the "Encounters" swimlane with the below badges:
| badge_type      | badge_text |
| IMPORTANT_EVENT | Important  |
| CLASSIFICATION  | ER visit   |
And the "cyclophosphamide 1 GM Injection" event MARKER is available on the "Treatment and Plan" swimlane with the below badges:
| badge_type     | badge_text  |
| CLASSIFICATION | Systemic Tx |
When the user logs out from OncoCare
And the [DEFAULT-TEST-USER] user logs in to OncoCare
And the user selects the "OncoTestPatient, Torvald" patient
And the user navigates to the "Timeline" view
Then the "Emergency room admission (procedure)" event MARKER is available on the "Encounters" swimlane with the below badges:
| badge_type     | badge_text |
| CLASSIFICATION | ER visit   |
And the "cyclophosphamide 1 GM Injection" event MARKER is available on the "Treatment and Plan" swimlane with the below badges:
| badge_type      | badge_text  |
| IMPORTANT_EVENT | Important   |
| CLASSIFICATION  | Systemic Tx |
