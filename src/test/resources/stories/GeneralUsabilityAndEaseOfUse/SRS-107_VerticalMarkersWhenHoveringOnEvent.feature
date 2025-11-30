@safety
Feature: [SysRS-68652] Vertical Markers When Hovering On Event

Narrative: The system shall display vertical markers on the timeline along the swimlanes by hovering on an event.

@SRS-107.01
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68652
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Vertical_markers_along_the_swimlanes_on_timeline
Scenario: Vertical line is displayed along with an event marker if the mouse is moved over it
Given I logged in to OncoCare application
And I selected the "OncoTestPatient, Juno" patient
And I navigated to the "Timeline" view
And I see the following event on the "Biomarkers" swimlane:
| event_type | text_on_event_point | name_on_label   |
| MARKER     | <EMPTY>             | Report with pdf |
When I move the mouse over the following event point on the "Biomarkers" swimlane:
| event_type | text_on_event_point | name_on_label   |
| MARKER     | <EMPTY>             | Report with pdf |
Then a vertical line is displayed on the timeline along with the hovered event
And a label is displayed on the top of the vertical line with the following text: "Dec 06, 2012"
When I move the mouse away from the hovered event
Then the vertical line disappears

@SRS-107.02
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68652
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Vertical_markers_along_the_swimlanes_on_timeline
Scenario: Vertical line is displayed along with an event cluster if the mouse is moved over it
Given I logged in to OncoCare application
And I selected the "OncoTestPatient, Juno" patient
And I navigated to the "Timeline" view
And I fully zoomed out the timeline
And I see the following event on the "Imaging" swimlane:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 4                   | 4 events      |
When I move the mouse over the following event point on the "Imaging" swimlane:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 4                   | 4 events      |
Then a vertical line is displayed on the timeline along with the hovered event cluster
And a label is displayed on the top of the vertical line with the following text: "Aug 18, 2022 - Sep 09, 2022"
When I move the mouse away from the hovered event
Then the vertical line disappears
