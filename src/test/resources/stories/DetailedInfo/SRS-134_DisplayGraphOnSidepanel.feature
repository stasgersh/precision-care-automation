Feature: [SysRS-68549] Display Graph On Sidepanel

Narrative: The system shall be able to display graph on the side panel for the events which values are changing in time.


@SRS-134.01
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68549
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Detailed_Info
Scenario: Single trend graph is displayed on the side panel
Given I logged in to OncoCare application
And I selected the "OncoTestPatient, Trendell" patient
And I navigated to the "Timeline" view
When I select the following event point on the "Encounters" swimlane:
| event_type | name_on_label                           | date_on_timeline_axis |
| MARKER     | Pain severity 0-10 - Reported 4 {score} | Dec 19, 2013          |
Then the sidebar is displayed
And the sidebar header contains the following data:
| title                                   | creation_date | report_type |
| Pain severity 0-10 - Reported 4 {score} | Dec 19, 2013  | Encounters  |
And the sidebar content contains the following data:
| data_type   | title                                   | value                                                       |
| TREND_GRAPH | Pain severity 0-10 - Reported ({score}) | trends/${os.type}/OncoTestPatient_Trendell_PainSeverity.png |
And the following event point is highlighted on the trend graph:
| data_point_value | data_point_date |
| 4                | Dec 19, 2013    |
When I click on the below data point on the "Pain severity 0-10 - Reported ({score})" trend graph:
| data_point_value | data_point_date |
| 1                | Dec 25, 2014    |
Then the following event is selected on "Encounters" swimlane:
| event_type | name_on_label                           | date_on_timeline_axis |
| MARKER     | Pain severity 0-10 - Reported 1 {score} | Dec 25, 2014          |
And the sidebar header contains the following data:
| title                                   | creation_date | report_type |
| Pain severity 0-10 - Reported 1 {score} | Dec 25, 2014  | Encounters  |
And the following data point has a halo on the graph:
| data_point_value | data_point_date |
| 1                | Dec 25, 2014    |