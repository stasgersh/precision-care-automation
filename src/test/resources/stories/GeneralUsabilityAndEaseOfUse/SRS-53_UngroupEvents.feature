@safety
Feature: [SysRS-68622] Ungroup Events

Narrative: The system users shall be able to ungroup the Event Groups when the user clicks on the grouped event on the timeline.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application


@SRS-53.01
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68622
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Ungroup_Events
Scenario: Event group is ungrouped when the browser window size is increased
Given the "OncoTestPatient, Rory188" patient is selected
And the patient banner is collapsed
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
When the browser size is set to: width=600, height=1080
Then the "Biomarkers" swimlane contains the following events:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 8                   | 8 events      |
When the browser size is set to: width=1200, height=1080
Then the "Biomarkers" swimlane contains the following events:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 3                   | 3 events      |
| CLUSTER    | 5                   | 5 events      |
When the browser size is set to: width=1920, height=1080
Then the "Biomarkers" swimlane contains the following events:
| event_type | text_on_event_point | name_on_label           |
| CLUSTER    | 2                   | 2 events                |
| CLUSTER    | 2                   | 2 events                |
| CLUSTER    | 2                   | 2 events                |
| CLUSTER    | 2                   | 2 events                |

@sanity
@edge
@SRS-53.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68622
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use
Scenario: Event group is ungrouped when the timeline is zoomed in
Given the browser size is set to: width=1920, height=1080
And the "OncoTestPatient, Franklin" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the "Encounters" swimlane contains the following events from left to the right:
| event_type | text_on_event_point | name_on_label     | date_on_timeline_axis       |
| MARKER     | <EMPTY>             | Body Weight 41 kg | May 22, 1991                |
| MARKER     | <EMPTY>             | Body Weight 35 kg | Apr 23, 1996                |
| CLUSTER    | 3                   | 3 events          | Aug 26, 2001 - Jul 15, 2002 |
| MARKER     | <EMPTY>             | Body Weight 31 kg | Oct 23, 2006                |
| MARKER     | <EMPTY>             | Body Weight 39 kg | Aug 13, 2011                |
When the user clicks on the 'Zoom in' button 2 times
Then the "Encounters" swimlane contains the following events from left to the right:
| event_type | text_on_event_point | name_on_label     | date_on_timeline_axis       |
| MARKER     | <EMPTY>             | Body Weight 41 kg | May 22, 1991                |
| MARKER     | <EMPTY>             | Body Weight 35 kg | Apr 23, 1996                |
| MARKER     | <EMPTY>             | Body Weight 43 kg | Aug 26, 2001                |
| CLUSTER    | 2                   | 2 events          | Apr 11, 2002 - Jul 15, 2002 |
| MARKER     | <EMPTY>             | Body Weight 31 kg | Oct 23, 2006                |
| MARKER     | <EMPTY>             | Body Weight 39 kg | Aug 13, 2011                |
When the user zooms in the timeline to max resolution
Then the "Encounters" swimlane contains the following events:
| event_type | text_on_event_point | name_on_label     | date_on_timeline_axis       |
| MARKER     | <EMPTY>             | Body Weight 41 kg | May 22, 1991                |
| MARKER     | <EMPTY>             | Body Weight 35 kg | Apr 23, 1996                |
| MARKER     | <EMPTY>             | Body Weight 43 kg | Aug 26, 2001                |
| MARKER     | <EMPTY>             | Body Weight 38 kg | Apr 11, 2002                |
| MARKER     | <EMPTY>             | Body Weight 44 kg | Jul 15, 2002                |
| MARKER     | <EMPTY>             | Body Weight 31 kg | Oct 23, 2006                |
| MARKER     | <EMPTY>             | Body Weight 39 kg | Aug 13, 2011                |

@SRS-53.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68622
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Ungroup_Events
Scenario: Event group is ungrouped (i.e. opened in cluster pill) when the user clicks on an event group which contains events with different date
Given the "OncoTestPatient, Franklin" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the "Encounters" swimlane contains the following events from left to the right:
| event_type | text_on_event_point | name_on_label     | date_on_timeline_axis       |
| MARKER     | <EMPTY>             | Body Weight 41 kg | May 22, 1991                |
| MARKER     | <EMPTY>             | Body Weight 35 kg | Apr 23, 1996                |
| CLUSTER    | 3                   | 3 events          | Aug 26, 2001 - Jul 15, 2002 |
| MARKER     | <EMPTY>             | Body Weight 31 kg | Oct 23, 2006                |
| MARKER     | <EMPTY>             | Body Weight 39 kg | Aug 13, 2011                |
When the user clicks on the following event point on the "Encounters" swimlane:
| event_type | text_on_event_point | name_on_label     | date_on_timeline_axis       |
| CLUSTER    | 3                   | 3 events          | Aug 26, 2001 - Jul 15, 2002 |
Then the following events are displayed in the cluster pill:
| event_name        | date_on_tooltip |
| Body Weight 43 kg | Aug 26, 2001    |
| Body Weight 38 kg | Apr 11, 2002    |
| Body Weight 44 kg | Jul 15, 2002    |
