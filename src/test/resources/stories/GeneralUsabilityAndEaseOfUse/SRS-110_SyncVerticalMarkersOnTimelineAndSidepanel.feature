Feature: [SysRS-68653] Sync Vertical Markers On Timeline And Sidepanel

Narrative: The system shall keep in sync the vertical markers on the timeline and the Side panel chart. (In the cases when the Side panel has chart.)


Background:
Given I logged in to OncoCare application
And I selected the "OncoTestPatient, Franklin" patient
And I navigated to the "Timeline" view
And I zoomed out the timeline
And I see the following events on "Encounters" swimlane:
| event_type | text_on_event_point | name_on_label     |
| MARKER     | <EMPTY>             | Body Weight 41 kg |
| MARKER     | <EMPTY>             | Body Weight 35 kg |
| CLUSTER    | 3                   | 3 events          |
| MARKER     | <EMPTY>             | Body Weight 31 kg |
| MARKER     | <EMPTY>             | Body Weight 39 kg |
And I selected the following event on the "Encounters" swimlane:
| event_type | text_on_event_point | name_on_label     |
| MARKER     | <EMPTY>             | Body Weight 35 kg |
And the side panel is opened
And a trend graph is displayed on the side panel with the following data points:
| data_value_on_point_label | date_on_point_label |
| 41                        | May 22, 1991        |
| 35                        | Apr 23, 1996        |
| 43                        | Aug 26, 2001        |
| 38                        | Apr 11, 2002        |
| 44                        | Jul 15, 2002        |
| 31                        | Oct 23, 2006        |
| 39                        | Aug 13, 2011        |
And the following event point is highlighted on the trend graph:
| data_value_on_point_label | date_on_point_label |
| 35                        | Apr 23, 1996        |

@SRS-110.01
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68653
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Sync_the_vertical_markers
Scenario: Vertical line is displayed both on the timeline and the trend graph if the mouse is moved over an event on the timeline
When I move the mouse over the following event point on the "Encounters" swimlane:
| event_type | text_on_event_point | name_on_label     |
| MARKER     | <EMPTY>             | Body Weight 35 kg |
Then a vertical line is displayed on the timeline along with the hovered event
And a vertical line is displayed on the trend graph on the side panel at the following data point:
| data_value_on_point_label | date_on_point_label |
| 35                        | Apr 23, 1996        |
When I move the mouse over the following event point on the "Encounters" swimlane:
| event_type | text_on_event_point | name_on_label     |
| MARKER     | <EMPTY>             | Body Weight 31 kg |
Then a vertical line is displayed on the timeline along with the hovered event
And a vertical line is displayed on the trend graph on the side panel at the following data point:
| data_value_on_point_label | date_on_point_label |
| 31                        | Oct 23, 2006        |
When I move the mouse over the following event point on the "Encounters" swimlane:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 3                   | 3 events      |
Then a vertical line is displayed on the timeline along with the hovered event
And a vertical lane is displayed on the trend graph on the side panel which covers the following data points:
| data_value_on_point_label | date_on_point_label |
| 43                        | Aug 26, 2001        |
| 38                        | Apr 11, 2002        |
| 44                        | Jul 15, 2002        |
When I move the mouse over the following event point on the "Pathology and Labs" swimlane:
| event_type | text_on_event_point | name_on_label      |
| MARKER     | <EMPTY>             | Pathology Report 3 |
Then a vertical line is displayed on the timeline along with the hovered event
And a vertical line is displayed on the trend graph on the side panel between the following 2 data points:
| description_for_the_step                          | data_value_on_point_label | date_on_point_label |
| vertical line is displayed after this data point  | 44                        | Jul 15, 2002        |
| vertical line is displayed before this data point | 31                        | Oct 23, 2006        |

@SRS-110.02
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68653
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Sync_the_vertical_markers
Scenario: Vertical line is displayed both on the trend graph and the timeline if the mouse is moved over a data point on the trend graph
When I move the mouse over the following data point on the trend graph:
| data_value_on_point_label | date_on_point_label |
| 35                        | Apr 23, 1996        |
Then a vertical line is displayed on the trend graph along with the hovered data point
And a vertical line is displayed on the timeline at the following event on the "Encounters" swimlane:
| event_type | text_on_event_point | name_on_label     |
| MARKER     | <EMPTY>             | Body Weight 35 kg |
When I move the mouse over the following data point on the trend graph:
| data_value_on_point_label | date_on_point_label |
| 31                        | Oct 23, 2006        |
Then a vertical line is displayed on the trend graph along with the hovered data point
And a vertical line is displayed on the timeline at the following event on the "Encounters" swimlane:
| event_type | text_on_event_point | name_on_label     |
| MARKER     | <EMPTY>             | Body Weight 31 kg |
When I move the mouse over the following data point on the trend graph:
| data_value_on_point_label | date_on_point_label |
| 43                        | Aug 26, 2001        |
Then a vertical line is displayed on the trend graph along with the hovered data point
And a vertical line is displayed on the timeline at the following event on the "Encounters" swimlane:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 3                   | 3 events      |
