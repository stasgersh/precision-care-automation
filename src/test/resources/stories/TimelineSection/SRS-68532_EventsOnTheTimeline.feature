Feature: [SysRS-68532] Events On The Timeline

Narrative: The system shall display events on the timeline.
           (For event definition see the “Definitions, Acronyms, Abbreviations” chapter.)


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application


@srs
@SRS-34.01
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68532
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Timeline_section
Scenario Outline: Event marker is displayed on the timeline based on the event date
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
When the user zoom in until there is no hidden <timeline_period> value on the timeline axis
And the user checks the following timeline event:
| swimlane           | event_type | name_on_label          |
| Pathology and Labs | MARKER     | Test Diagnostic Report |
Then the center of the event point is between the following timeline values: <timeline_values>
Examples:
| timeline_period | timeline_values   |
| year            | 2009 and 2010     |
| month           | May and Jun       |
| days            | May 11 and May 12 |

@srs
@SRS-34.02
@manual
@CLP
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68532
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Timeline_section
Scenario Outline: Event marker with CLP info is displayed on the timeline based on the event date
Given the "OncoTestPatient, Juno" patient is selected
And "AI summarization" is turned ON in user settings
And the "Timeline" view is selected
When the user zooms in until there is no hidden <timeline_period> value on the timeline axis
And the user checks the following timeline event:
| swimlane | event_type | name_on_label                          | date_on_timeline_vertical_line_label |
| Imaging  | MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011                         |
Then the "CT THORAX ABDOMEN PELVIS WITH CONTRAST" event has 1 miniaturized CLP badges
And the center of the event point is between the following timeline values: <timeline_values>
Examples:
| timeline_period | timeline_values   |
| year            | 2011 and 2012     |
| month           | Sep and Oct       |
| day             | Sep 28 and Sep 29 |

@srs
@SRS-34.03
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68532
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Timeline_section
Scenario: Event cluster is displayed on the timeline based on the date of the first and the last event in the cluster
Given the "OncoTestPatient, Casper" patient is selected
And the "Timeline" view is selected
And the following event is available on the "Encounters" swimlane:
| event_type | name_on_label | date_on_timeline_axis       |
| CLUSTER    | 2 events      | Dec 25, 1997 - Feb 25, 1998 |
When the user zoom in until there is no hidden year value on the timeline axis
And the user checks the following timeline event:
| swimlane   | event_type | name_on_label | date_on_timeline_vertical_line_label |
| Encounters | CLUSTER    | 2 events      | Dec 25, 1997 - Feb 25, 1998          |
Then the center of the event point is between the following timeline values: 1998 and 1999
And after the user zoom in until there is no hidden month value on the timeline axis
Then the event custer is splitted to 2 simple event markers
And these 2 events are between the following timeline values: 1997 Dec and 1998 Mar
