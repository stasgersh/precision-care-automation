@safety
Feature: [SysRS-68518] Open event by URL

Narrative: The system’s users shall see the linked (URL routed) timeline event rather than the saved selected event.


@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68518
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_View
Scenario Outline: User can open an event by its URL
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the "Treatment and Plan" swimlane contains the following events:
| event_type | text_on_event_point | name_on_label                            | date_on_timeline_axis       |
| MARKER     | <EMPTY>             | CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE | Aug 30, 1981                |
| CLUSTER    | 3                   | 3 events                                 | Feb 11, 1995 - Feb 11, 1995 |
When the user clicks on the following event point on the "Encounters" swimlane:
| event_type | name_on_label       |
| MARKER     | Body Weight 15.3 kg |
Then the sidebar is displayed
And the sidebar header contains the following data:
| title               | creation_date | report_type |
| Body Weight 15.3 kg | May 22, 1975  | Encounters  |
When the user navigates to the "<tab>" view
And the user goes to the following URL: ${webdriver.base.url}patient/6d2d9e07-4a24-7525-4661-f59ccabb02a7/timeline/medication:0dbfc5f3-1427-45e0-a2b7-539ab0fc9f3c
Then the patient timeline is loaded
And the macro-navigator is displayed
And the "Treatment and Plan" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | false       | true     |
| 1     | false       | false    |
| 2     | false       | false    |
| 3     | false       | false    |
And the "Encounters" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | false       | false    |
| 1     | false       | false    |
| 2     | false       | false    |
And the sidebar is displayed
And the sidebar header contains the following data:
| title                                    | creation_date | report_type        |
| CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE | Aug 30, 1981  | Treatment and Plan |
Examples:
| tab             |
| Summary         |
| Medical history |

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68518
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_View
Scenario Outline: User can open an event in an event cluster by its URL
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | text_on_event_point | name_on_label | date_on_timeline_axis       |
| CLUSTER    | 3                   | 3 events      | Feb 11, 1995 - Feb 11, 1995 |
Then the cluster pill is opened
When the user selects the following event from the cluster pill:
| event_name                                    |
| DOXORUBICIN LIPOSOMAL (CAELYX) CHEMO INFUSION |
Then the sidebar is displayed
And the sidebar header contains the following data:
| title                                         | creation_date | report_type        |
| DOXORUBICIN LIPOSOMAL (CAELYX) CHEMO INFUSION | Feb 11, 1995  | Treatment and Plan |
When the user navigates to the "<tab>" view
And the user goes to the following URL: ${webdriver.base.url}patient/6d2d9e07-4a24-7525-4661-f59ccabb02a7/timeline/medication:744b63c9-fb4a-4a94-b5ce-cf44e7c67320
Then the patient timeline is loaded
And the macro-navigator is displayed
And the "Treatment and Plan" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | false       | false    |
| 1     | false       | false    |
| 2     | false       | false    |
| 3     | false       | true     |
And the sidebar is displayed
And the sidebar header contains the following data:
| title                                    | creation_date | report_type        |
| CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE | Feb 11, 1995  | Treatment and Plan |
Examples:
| tab      |
| Trends   |
| Comments |