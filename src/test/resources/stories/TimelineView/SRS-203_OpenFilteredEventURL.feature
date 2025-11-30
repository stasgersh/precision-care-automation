@safety
Feature: [SysRS-68519] Open filtered event by URL

Narrative: The system’s users shall see the linked (URL routed) timeline event’s panel info even if it is filtered out with the following notification: “This event is not visible on timeline due to filter(s) applied."


@SRS-203.01
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68519
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_View
Scenario: User can see filtered out event by opening its URL
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the "Treatment and Plan" swimlane contains the following events:
| event_type | text_on_event_point | name_on_label                            | date_on_timeline_axis       |
| MARKER     | <EMPTY>             | CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE | Aug 30, 1981                |
| CLUSTER    | 3                   | 3 events                                 | Feb 11, 1995 - Feb 11, 1995 |
When the user set the following timeline filter options:
| filter_group | checkbox_name      | selected |
| Swimlanes    | Encounters         | false    |
| Swimlanes    | Imaging            | false    |
| Swimlanes    | Pathology and Labs | true     |
| Swimlanes    | Biomarkers         | false    |
| Swimlanes    | Treatment and Plan | false    |
| Swimlanes    | Uncategorized      | false    |
Then the following swimlanes are available on the timeline:
| items              |
| Pathology and Labs |
And the following swimlanes are not available on the timeline:
| items              |
| Encounters         |
| Imaging            |
| Biomarkers         |
| Treatment and Plan |
| Uncategorized      |
And the "Treatment and Plan" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | true        | false    |
| 1     | true        | false    |
| 2     | true        | false    |
| 3     | true        | false    |
When the user goes to the following URL: CURRENT_URL/medication:0dbfc5f3-1427-45e0-a2b7-539ab0fc9f3c
Then the patient timeline is loaded
And the sidebar is displayed
And the sidebar header contains the following data:
| title                                    | creation_date | report_type        |
| CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE | Aug 30, 1981  | Treatment and Plan |
And the sidebar header contains the following notification banner:
| banner_message_regex                                            |
| This event is not visible on timeline due to filter(s) applied. |
And the "Treatment and Plan" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | true        | true     |
| 1     | true        | false    |
| 2     | true        | false    |
| 3     | true        | false    |
When the user clicks on the Close button on the sidebar
And the user goes to the following URL: CURRENT_URL/medication:744b63c9-fb4a-4a94-b5ce-cf44e7c67320
Then the patient timeline is loaded
And the sidebar is displayed
And the sidebar header contains the following data:
| title                                    | creation_date | report_type        |
| CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE | Feb 11, 1995  | Treatment and Plan |
And the sidebar header contains the following notification banner:
| banner_message_regex                                            |
| This event is not visible on timeline due to filter(s) applied. |
And the "Treatment and Plan" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | true        | false    |
| 1     | true        | false    |
| 2     | true        | false    |
| 3     | true        | true     |
When the user clicks on the Close button on the sidebar
And the user set the following timeline filter options:
| filter_group | checkbox_name      | selected |
| Swimlanes    | Encounters         | false    |
| Swimlanes    | Imaging            | false    |
| Swimlanes    | Pathology and Labs | true     |
| Swimlanes    | Biomarkers         | false    |
| Swimlanes    | Treatment and Plan | false    |
| Swimlanes    | Uncategorized      | true     |
| Labels       | Has comments       | true     |
And the user goes to the following URL: CURRENT_URL/vitalSigns:cfd91524-c9e5-4877-a114-a6a448f0f0e2
Then the patient timeline is loaded
And the sidebar is displayed
And the sidebar header contains the following data:
| title               | creation_date | report_type |
| Body Weight 61.8 kg | Oct 23, 2006  | Encounters  |
And the sidebar header contains the following notification banner:
| banner_message_regex                                            |
| This event is not visible on timeline due to filter(s) applied. |
And the "Encounters" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | true        | false    |
| 1     | true        | false    |
| 2     | true        | true     |