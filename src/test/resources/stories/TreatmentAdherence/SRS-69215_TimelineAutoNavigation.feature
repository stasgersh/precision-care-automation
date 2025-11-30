Feature: [SysRS-69215] Timeline Auto Navigation

Narrative: The system shall set sliding window time between start date (see Treatment adherence - Select treatment and its parameters (SysRS-68508)) and end date (last future event) and focus timeline view on that period.


@manual
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69215
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Manual/Treatment_Adherence
Scenario: The timeline and macro-navigator automatically adjust to ensure that the protocol start date and the last event are within the viewport
Given [API] the following treatment protocol was uploaded: treatment/cc-94676_adherence_config.json
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_TA, Higgins" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
When the user sets the following treatment protocol configuration for the patient:
| view_mode           | protocol                             | trial_arm                           | start_date   |
| Treatment adherence | CC-94676 for Prostate Cancer Patient | CC94676 for Prostate Cancer Patient | Jan 01, 2025 |
Then the patient timeline is loaded
And the time-box on the macro-navigator contains all treatment event points since the protocol start:
| swimlane           | name_on_label                                                    |
| Encounters         | Counselling                                                      |
| Encounters         | Oncology Visit                                                   |
| Imaging            | PSMA-PET scan                                                    |
| Imaging            | PSMA-PET scan                                                    |
| Imaging            | PSMA PET scan                                                    |
| Pathology and Labs | PSA measurement                                                  |
| Pathology and Labs | PSA measurement                                                  |
| Pathology and Labs | PSA measurement                                                  |
| Biomarkers         | Prostate specific Ag [Mass/volume] in Serum or Plasma 2.43 ng/mL |
| Treatment and Plan | Androgen Receptor Degrader CC-94676                              |
And the view port of the timeline contains all treatment events since the protocol start date:
| swimlane           | name_on_label                                                    |
| Encounters         | Counselling                                                      |
| Encounters         | Oncology Visit                                                   |
| Imaging            | PSMA-PET scan                                                    |
| Imaging            | PSMA-PET scan                                                    |
| Imaging            | PSMA PET scan                                                    |
| Pathology and Labs | PSA measurement                                                  |
| Pathology and Labs | PSA measurement                                                  |
| Pathology and Labs | PSA measurement                                                  |
| Biomarkers         | Prostate specific Ag [Mass/volume] in Serum or Plasma 2.43 ng/mL |
| Treatment and Plan | Androgen Receptor Degrader CC-94676                              |
When the user sets the following treatment protocol configuration for the patient:
| view_mode           | protocol                             | trial_arm                           | start_date   |
| Treatment adherence | CC-94676 for Prostate Cancer Patient | CC94676 for Prostate Cancer Patient | Mar 01, 2023 |
And the user clicks on "Protocol start" label on the macro-navigator
And the macro-navigator updated so that the protocol start date is in the middle of the time-box
And the timeline updated so that the protocol start date is in the middle of viewport
