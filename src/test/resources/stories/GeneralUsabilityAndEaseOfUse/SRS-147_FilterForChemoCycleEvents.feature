Feature: [SysRS-68635] Filter For Chemo Cycle Events

Narrative: he system shall be able to support the following filtering on the timeline:
             - events which were marked as important events. (SRS-32 The system users shall be able to filter on the Timeline for events which were marked as important events.)
             - events which have comments. (SRS-143 The system users shall be able to filter on the Timeline for events which has comments.)
             - Chemo cycle events.(SRS-147 The system users shall be able to filter on the Timeline for Chemo cycle events.)
             - events which have AI enriched data. (SRS-148 The system users shall be able to filter on the Timeline for events which has AI enriched data. )


@SRS-147.01
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68635
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use
Scenario: User can filter for chemo cycle events
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
And the user navigated to the "Timeline" view
And the patient timeline is loaded
And the following events are available on the timeline:
| swimlane           | event_type | text_on_event_point | name_on_label                            |
| Treatment and Plan | MARKER     | <EMPTY>             | CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE |
| Treatment and Plan | MARKER     | <EMPTY>             | CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE |
And the following events is available on the timeline:
| swimlane           | event_type | text_on_event_point | name_on_label                            |
| Encounters         | MARKER     | <EMPTY>             | Body Weight 56.2 kg                      |
When the user opens the timeline filter modal
And the user set the following timeline filter options:
| filter_group | checkbox_name | selected |
| Labels       | Chemo cycle   | true     |
Then the following events are available on the timeline:
| swimlane           | event_type | text_on_event_point | name_on_label                            |
| Treatment and Plan | MARKER     | <EMPTY>             | CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE |
| Treatment and Plan | MARKER     | <EMPTY>             | CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE |
And the following event is not available on the timeline:
| swimlane           | event_type | text_on_event_point | name_on_label                            |
| Encounters         | MARKER     | <EMPTY>             | Body Weight 56.2 kg                      |
When the user opens the timeline filter modal
Then the following timeline filter options are displayed on the filter modal:
| filter_group | checkbox_name | selected |
| Labels       | Chemo cycle   | true     |
When the user clicks on the "Reset filters" button on the filter modal
Then the following timeline filter options are displayed on the filter modal:
| filter_group | checkbox_name | selected |
| Labels       | Chemo cycle   | false    |
When the user closes the timeline filter modal
Then the following events are available on the timeline:
| swimlane           | event_type | text_on_event_point | name_on_label                            |
| Treatment and Plan | MARKER     | <EMPTY>             | CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE |
| Treatment and Plan | MARKER     | <EMPTY>             | CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE |
And the following event is available on the timeline:
| swimlane           | event_type | text_on_event_point | name_on_label                            |
| Encounters         | MARKER     | <EMPTY>             | Body Weight 56.2 kg                      |
