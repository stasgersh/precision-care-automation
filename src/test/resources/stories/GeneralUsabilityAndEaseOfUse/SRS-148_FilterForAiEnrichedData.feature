@safety
Feature: [SysRS-68635] Filter For AI Enriched Data

Narrative: The system shall be able to support the following filtering on the timeline:
             - events which were marked as important events. (SRS-32 The system users shall be able to filter on the Timeline for events which were marked as important events.)
             - events which have comments. (SRS-143 The system users shall be able to filter on the Timeline for events which has comments.)
             - Chemo cycle events.(SRS-147 The system users shall be able to filter on the Timeline for Chemo cycle events.)
             - events which have AI enriched data. (SRS-148 The system users shall be able to filter on the Timeline for events which has AI enriched data. )


@SRS-148.01
@sanity
@edge
@ai_sanity
@CLP
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68635
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use
Scenario: User can filter for events with CLP data
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the following event is available on the "Encounters" swimlane:
| event_type | name_on_label  |
| MARKER     | Progress Notes |
And the following event is available on the "Imaging" swimlane:
| event_type | name_on_label                          |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST |
When the user set the following timeline filter option:
| filter_group | checkbox_name | selected |
| Labels       | AI enriched   | true     |
Then the following event is not available on the "Encounters" swimlane:
| event_type | name_on_label  |
| MARKER     | Progress Notes |
And the following event is available on the "Imaging" swimlane:
| event_type | name_on_label                          |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST |
