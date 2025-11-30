Feature: [SysRS-68534] Event Name On Label

Narrative: The system shall be able to display event names on the event labels.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application


@SRS-36.01
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68534
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_section
Scenario Outline: All events have a name on the event label
When the user selects the "<patient_name>" patient
And the user navigates to the "Timeline" view
Then all event labels in all swimlanes contain a name
Examples:
| patient_name           |
| OncoTestPatient, Freya |
| OncoTestPatient, Eleanor470   |

@sanity
@SRS-36.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68534
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Timeline_section
Scenario: The event name is displayed on the event label
When the user selects the "OncoTestPatient, Al" patient
And the user navigates to the "Timeline" view
Then the patient timeline is loaded
When the user clicks on the 'All time' button on the timeline toolbar
Then the "Pathology and Labs" swimlane contains the following events:
| event_type | text_on_event_point | name_on_label                                                    |
| MARKER     | <EMPTY>             | Complete blood count (hemogram) panel - Blood by Automated count |
| CLUSTER    | 2                   | 2 events                                                         |

@sanity
@SRS-36.03
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68534
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Timeline_section
Scenario: The event name is truncated on the label if it is too long ('hemogram' in this example)
When the user selects the "OncoTestPatient, Columbus" patient
And the user navigates to the "Timeline" view
Then the following events are available on the "Pathology and Labs" swimlane:
| event_type | name_on_label                                                    | date_on_timeline_axis |
| MARKER     | Complete blood count (hemogram) panel - Blood by Automated count | Dec 13, 2012          |
| MARKER     | Complete blood count (hemogram) panel - Blood by Automated count | Jan 11, 2018          |

@SRS-36.04
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68534
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_section
Scenario: In case of event cluster, the event name is the number of the included events
When the user selects the "OncoTestPatient, Eleanor470" patient
And the user navigates to the "Timeline" view
Then the "Biomarkers" swimlane contains the following events:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 5                   | 5 events      |
