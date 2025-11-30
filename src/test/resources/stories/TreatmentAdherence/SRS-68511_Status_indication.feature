Feature: [SysRS-68511] Status indication

Narrative: The system shall provide a status indication for every event according to the following:
           - Planned
           - Scheduled
           - Done
           - Missed
           - Unscheduled
           - Each status shall be represented with its own differentiator.


Background:
Given [API] the following treatment protocol was uploaded: treatment/cc-94676_adherence_config.json


@sanity
@edge
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68511,SysRS-69218
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Treatment_Adherence
Scenario: Event status calculated based on the treatment protocol configuration is displayed
Given the dates of the following patient's resources are prepared and uploaded to PDS: patients/prepared/TEST_TA_Mortimer.json
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_TA, Mortimer" patient is selected
And the "Timeline" view is selected
And the timeline toolbar section is displayed
When the user sets the following treatment protocol configuration for the patient:
| view_mode           | protocol                             | trial_arm                           | start_date     |
| Treatment adherence | CC-94676 for Prostate Cancer Patient | CC94676 for Prostate Cancer Patient | <100_DAYS_AGO> |
Then the patient timeline is loaded
When the user clicks on the 'All time' button on the timeline toolbar
Then the following events are available on the timeline:
| swimlane           | event_type | name_on_label                                                                                       | badges                      | date_on_timeline_axis |
| Encounters         | MARKER     | Oncology Visit 1 - 3 +/-2 days after PSA-2                                                          | [STATUS_BADGE]: Deviation   | <6_DAYS_AGO>          |
| Encounters         | MARKER     | Oncology visit                                                                                      | [STATUS_BADGE]: Unscheduled | <272_DAYS_FROM_NOW>   |
| Imaging            | MARKER     | PSMA-PET 1 - 21 +/-10 days before ARD                                                               | [STATUS_BADGE]: Missed      | <112_DAYS_AGO>        |
| Imaging            | MARKER     | PSMA-PET scan 2 - 90 +/-14 days after PSA-2                                                         | [STATUS_BADGE]: Scheduled   | <80_DAYS_FROM_NOW>    |
| Imaging            | MARKER     | PSMA PET scan                                                                                       | [STATUS_BADGE]: Unscheduled | <267_DAYS_FROM_NOW>   |
| Pathology and Labs | MARKER     | PSA measurement                                                                                     | [STATUS_BADGE]: Unscheduled | <175_DAYS_FROM_NOW>   |
| Pathology and Labs | MARKER     | PSA measurement                                                                                     | [STATUS_BADGE]: Unscheduled | <267_DAYS_FROM_NOW>   |
| Biomarkers         | MARKER     | Prostate specific Ag [Mass/volume] in Serum or Plasma - PSA-1 21 +/-10 days before ARD 196.19 ng/mL | [STATUS_BADGE]: Done        | <121_DAYS_AGO>        |
| Biomarkers         | MARKER     | Prostate specific Ag [Mass/volume] in Serum or Plasma - PSA-2 92 +/-14 days after ARD 2.43 ng/mL    | [STATUS_BADGE]: Done        | <10_DAYS_AGO>         |
| Biomarkers         | MARKER     | Prostate specific Ag [Mass/volume] in Serum or Plasma - PSA-3 90 +/-14 days after PSA-2 0.22 ng/mL  | [STATUS_BADGE]: Deviation   | <82_DAYS_FROM_NOW>    |
| Treatment and Plan | MARKER     | Androgen Receptor Degrade                                                                           | [STATUS_BADGE]: Done        | <100_DAYS_AGO>        |
When the user clicks on the following event point on the "Encounters" swimlane:
| event_type | name_on_label                              | date_on_timeline_axis |
| MARKER     | Oncology Visit 1 - 3 +/-2 days after PSA-2 | <6_DAYS_AGO>          |
Then the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type   | badge_text |
| STATUS_BADGE | Deviation  |
And the sidebar contains the following adherence dates:
| date_type      | value        |
| Planned date   | <3_DAYS_AGO> |
| Scheduled date | Not found    |
| Actual date    | <6_DAYS_AGO> |
When the user clicks on the following event point on the "Imaging" swimlane:
| event_type | name_on_label                               | date_on_timeline_axis |
| MARKER     | PSMA-PET scan 2 - 90 +/-14 days after PSA-2 | <80_DAYS_FROM_NOW>    |
Then the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type   | badge_text |
| STATUS_BADGE | Scheduled  |
And the sidebar contains the following adherence dates:
| date_type      | value              |
| Planned date   | <86_DAYS_FROM_NOW> |
| Scheduled date | <80_DAYS_FROM_NOW> |
When the user clicks on the following event point on the "Pathology and Labs" swimlane:
| event_type | name_on_label   | date_on_timeline_axis |
| MARKER     | PSA measurement | <175_DAYS_FROM_NOW>   |
Then the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type   | badge_text  |
| STATUS_BADGE | Unscheduled |
And the sidebar contains the following adherence dates:
| date_type      | value               |
| Planned date   | <175_DAYS_FROM_NOW> |
| Scheduled date | Not found           |
When the user clicks on the Close button on the sidebar
And the user clicks on the following event point on the "Biomarkers" swimlane:
| event_type | name_on_label                                                                                      | date_on_timeline_axis |
| MARKER     | Prostate specific Ag [Mass/volume] in Serum or Plasma - PSA-3 90 +/-14 days after PSA-2 0.22 ng/mL | <82_DAYS_FROM_NOW>    |
Then the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type   | badge_text |
| STATUS_BADGE | Deviation  |
And the sidebar contains the following adherence dates:
| date_type      | value              |
| Planned date   | <86_DAYS_FROM_NOW> |
| Scheduled date | Not found          |
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label             | date_on_timeline_axis |
| MARKER     | Androgen Receptor Degrade | <100_DAYS_AGO>        |
Then the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type   | badge_text |
| STATUS_BADGE | Done       |
And the sidebar contains the following adherence dates:
| date_type      | value          |
| Planned date   | <100_DAYS_AGO> |
| Scheduled date | <100_DAYS_AGO> |
| Actual date    | <100_DAYS_AGO> |

@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68511
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Treatment_Adherence
Scenario: Synthetic and future event's planned date updates if the protocol start date was changed
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_TA, Higgins" patient is selected
And the "Timeline" view is selected
And the timeline toolbar section is displayed
And the user has set the following treatment protocol configuration for the patient:
| view_mode           | protocol                             | trial_arm                           | start_date   |
| Treatment adherence | CC-94676 for Prostate Cancer Patient | CC94676 for Prostate Cancer Patient | Nov 07, 2024 |
And the patient timeline is loaded
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label             | date_on_timeline_axis |
| MARKER     | Androgen Receptor Degrade | Nov 07, 2024          |
Then the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type   | badge_text |
| STATUS_BADGE | Done       |
And the sidebar contains the following adherence dates:
| date_type      | value        |
| Planned date   | Nov 07, 2024 |
| Scheduled date | Nov 07, 2024 |
| Actual date    | Nov 07, 2024 |
When the user clicks on the following event point on the "Imaging" swimlane:
| event_type | name_on_label | date_on_timeline_axis |
| MARKER     | PSMA-PET scan | May 12, 2025          |
Then the sidebar is displayed
And the sidebar contains the following adherence dates:
| date_type      | value        |
| Planned date   | May 09, 2025 |
| Scheduled date | May 12, 2025 |
| Actual date    | Not found    |
When the user sets the following treatment protocol configuration for the patient:
| start_date   |
| Nov 08, 2024 |
Then the patient timeline is loaded
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label             | date_on_timeline_axis |
| MARKER     | Androgen Receptor Degrade | Nov 07, 2024          |
Then the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type   | badge_text |
| STATUS_BADGE | Done       |
And the sidebar contains the following adherence dates:
| date_type      | value        |
| Planned date   | Nov 08, 2024 |
| Scheduled date | Nov 07, 2024 |
| Actual date    | Nov 07, 2024 |
When the user clicks on the following event point on the "Imaging" swimlane:
| event_type | name_on_label | date_on_timeline_axis |
| MARKER     | PSMA-PET scan | May 12, 2025          |
Then the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type   | badge_text |
| STATUS_BADGE | Missed     |
And the sidebar contains the following adherence dates:
| date_type      | value        |
| Planned date   | May 10, 2025 |
| Scheduled date | May 12, 2025 |
| Actual date    | Not found    |
When the user sets the following treatment protocol configuration for the patient:
| start_date   |
| Nov 25, 2024 |
Then the patient timeline is loaded
When the user clicks on the following event point on the "Imaging" swimlane:
| event_type | name_on_label | date_on_timeline_axis |
| MARKER     | PSMA PET scan | May 27, 2025          |
Then the sidebar is displayed
And the sidebar contains the following adherence dates:
| date_type      | value        |
| Planned date   | May 27, 2025 |
| Scheduled date | Not found    |