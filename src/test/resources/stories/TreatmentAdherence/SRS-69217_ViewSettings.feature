Feature: [SysRS-69217] View Settings

Narrative: The system in treatment adherence mode shall have the following view settings:
           - Timeline Swimlanes to display as defined in treatment adherence configuration file.
           - Filters:
                - default filters to display as defined in treatment adherence configuration file.
                - Filters are disabled to edit (and Indication or message to the user that the filters are disabled from edit).


Background:
Given [API] the following treatment protocol was uploaded: treatment/cc-94676_adherence_config.json

@sanity
@edge
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69217
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Treatment_Adherence
Scenario: Timeline gets filtered based on the treatment protocol configuration
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_TA, Higgins" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
When the user clicks on "View mode" button on the Timeline toolbar
Then the "View mode" modal is displayed
When the user selects "Treatment adherence" checkbox on the View mode modal
And the user sets the following options on "View mode" modal:
| protocol                             | trial_arm                           | start_date   |
| CC-94676 for Prostate Cancer Patient | CC94676 for Prostate Cancer Patient | Jun 07, 2024 |
And the user clicks "Done" on the "View mode" modal
Then the "View mode" modal is not displayed
And the patient timeline is loaded
And the "Tx" badge is displayed on the "View mode" button
When the user opens the timeline filter modal
Then the following notification is displayed on the Timeline filter modal: "Filters disabled\nYou can’t edit filters in Treatment adherence mode.\nExit Treatment adherence mode"
And the following timeline filter options are displayed on the filter modal:
| filter_group | checkbox_name      | selected | enabled |
| Swimlanes    | Encounters         | true     | false   |
| Swimlanes    | Imaging            | true     | false   |
| Swimlanes    | Pathology and Labs | true     | false   |
| Swimlanes    | Biomarkers         | true     | false   |
| Swimlanes    | Treatment and Plan | true     | false   |
| Swimlanes    | Uncategorized      | false    | false   |
When the user closes the timeline filter modal
Then the patient timeline is loaded
And the following swimlanes are available on the timeline:
| items              |
| Encounters         |
| Imaging            |
| Pathology and Labs |
| Biomarkers         |
| Treatment and Plan |

@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69217,SysRS-69213,SysRS-69217
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Treatment_Adherence
Scenario: Saved Timeline filter settings are restored after exiting Treatment adherence mode
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_TA, Higgins" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
When the user opens the timeline filter modal
And the user set the following timeline filter options:
| filter_group       | checkbox_name      | selected |
| Swimlanes          | Encounters         | false    |
| Swimlanes          | Imaging            | true     |
| Swimlanes          | Pathology and Labs | true     |
| Swimlanes          | Biomarkers         | false    |
| Swimlanes          | Treatment and Plan | true     |
| Swimlanes          | Uncategorized      | true     |
| Labels             | Chemo cycle        | true     |
| Labels             | Has comments       | true     |
| Imaging modalities | MR                 | true     |
| Pathology and Labs | Labs               | true     |
| Treatment types    | Radiation          | true     |
| Treatment types    | Surgery            | true     |
Then the following events are available on the timeline:
| swimlane           | event_type | text_on_event_point | name_on_label                            | date_on_timeline_axis       |
| Pathology and Labs | MARKER     | <EMPTY>             | Hepatic function panel - Serum or Plasma | Apr 06, 2017                |
| Pathology and Labs | MARKER     | <EMPTY>             | PSA measurement                          | Sep 12, 2025                |
| Treatment and Plan | CLUSTER    | 2                   | 2 events                                 | Mar 10, 2014 - Mar 10, 2014 |
When the user clicks on "View mode" button on the Timeline toolbar
Then the "View mode" modal is displayed
When the user selects "Treatment adherence" checkbox on the View mode modal
And the user sets the following options on "View mode" modal:
| protocol                             | trial_arm                           | start_date   |
| CC-94676 for Prostate Cancer Patient | CC94676 for Prostate Cancer Patient | Jun 07, 2024 |
And the user clicks "Done" on the "View mode" modal
Then the "View mode" modal is not displayed
And the patient timeline is loaded
When the user opens the timeline filter modal
Then the following timeline filter options are displayed on the filter modal:
| filter_group       | checkbox_name      | selected | enabled |
| Swimlanes          | Encounters         | true     | false   |
| Swimlanes          | Imaging            | true     | false   |
| Swimlanes          | Pathology and Labs | true     | false   |
| Swimlanes          | Biomarkers         | true     | false   |
| Swimlanes          | Treatment and Plan | true     | false   |
| Swimlanes          | Uncategorized      | false    | false   |
| Labels             | Chemo cycle        | false    | false   |
| Labels             | Has comments       | false    | false   |
| Imaging modalities | MR                 | false    | false   |
| Pathology and Labs | Labs               | false    | false   |
| Treatment types    | Radiation          | false    | false   |
| Treatment types    | Surgery            | false    | false   |
When the user clicks "Exit Treatment adherence mode" on the Filter modal
Then the following timeline filter options are displayed on the filter modal:
| filter_group       | checkbox_name      | selected | enabled |
| Swimlanes          | Encounters         | false    | true    |
| Swimlanes          | Imaging            | true     | true    |
| Swimlanes          | Pathology and Labs | true     | true    |
| Swimlanes          | Biomarkers         | false    | true    |
| Swimlanes          | Treatment and Plan | true     | true    |
| Swimlanes          | Uncategorized      | true     | true    |
| Labels             | Chemo cycle        | true     | true    |
| Labels             | Has comments       | true     | true    |
| Imaging modalities | MR                 | true     | true    |
| Pathology and Labs | Labs               | true     | true    |
| Treatment types    | Radiation          | true     | true    |
| Treatment types    | Surgery            | true     | true    |
When the user closes the timeline filter modal
Then the following events are available on the timeline:
| swimlane           | event_type | text_on_event_point | name_on_label                            | date_on_timeline_axis       |
| Pathology and Labs | MARKER     | <EMPTY>             | Hepatic function panel - Serum or Plasma | Apr 06, 2017                |
| Pathology and Labs | MARKER     | <EMPTY>             | PSA measurement                          | Sep 12, 2025                |
| Treatment and Plan | CLUSTER    | 2                   | 2 events                                 | Mar 10, 2014 - Mar 10, 2014 |

@srs
@TC-70933
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68508,SysRS-69213,SysRS-69217
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Treatment_Adherence
Scenario: User can switch to Default view mode for the patient
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_TA, Higgins" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the following events are available on the "Encounters" swimlane:
| event_type | name_on_label         |
| MARKER     | Body weight 101.15 kg |
| MARKER     | Oncology Visit        |
| MARKER     | Counselling           |
And the following events are available on the "Imaging" swimlane:
| event_type | name_on_label |
| MARKER     | PSMA-PET scan |
| MARKER     | PSMA-PET scan |
And the following events are available on the "Pathology and Labs" swimlane:
| event_type | name_on_label                            |
| MARKER     | Hepatic function panel - Serum or Plasma |
| MARKER     | PSA measurement                          |
And the following events are available on the "Biomarkers" swimlane:
| event_type | name_on_label                                                      |
| MARKER     | Prostate specific Ag [Mass/volume] in Serum or Plasma 0.22 ng/mL   |
| MARKER     | Prostate specific Ag [Mass/volume] in Serum or Plasma 196.19 ng/mL |
| MARKER     | Prostate specific Ag [Mass/volume] in Serum or Plasma 2.43 ng/mL   |
And the following events are available on the "Treatment and Plan" swimlane:
| event_type | text_on_event_point | name_on_label             |
| CLUSTER    | 2                   | 2 events                  |
| MARKER     | <EMPTY>             | docetaxel                 |
| MARKER     | <EMPTY>             | Androgen Receptor Degrade |
And the following events are not available on the "Uncategorized" swimlane:
| event_type | name_on_label                                                           |
| MARKER     | Alanine aminotransferase [Enzymatic activity/volume] in Serum or Plasma |
When the user clicks on "View mode" button on the Timeline toolbar
Then the "View mode" modal is displayed
When the user selects "Treatment adherence" checkbox on the View mode modal
And the user sets the following options on "View mode" modal:
| protocol                             | trial_arm                           | start_date   |
| CC-94676 for Prostate Cancer Patient | CC94676 for Prostate Cancer Patient | Nov 07, 2024 |
And the user clicks "Done" on the "View mode" modal
Then the "View mode" modal is not displayed
And the patient timeline is loaded
And the following events are available on the timeline:
| swimlane           | event_type | name_on_label                                                      | badges                      | date_on_timeline_axis |
| Encounters         | MARKER     | Oncology Visit                                                     | [STATUS_BADGE]: Deviation   | Apr 12, 2025          |
| Encounters         | MARKER     | Counselling                                                        | [STATUS_BADGE]: Scheduled   | Nov 15, 2025          |
| Imaging            | MARKER     | PSMA PET scan                                                      | [STATUS_BADGE]: Missed      | Oct 17, 2024          |
| Imaging            | MARKER     | PSMA-PET scan                                                      | [STATUS_BADGE]: Missed      | May 12, 2025          |
| Imaging            | MARKER     | PSMA-PET scan                                                      | [STATUS_BADGE]: Scheduled   | Nov 12, 2025          |
| Pathology and Labs | MARKER     | PSA measurement                                                    | [STATUS_BADGE]: Deviation   | Sep 12, 2025          |
| Pathology and Labs | MARKER     | PSA measurement                                                    | [STATUS_BADGE]: Unscheduled | Nov 09, 2025          |
| Biomarkers         | MARKER     | Prostate specific Ag [Mass/volume] in Serum or Plasma 196.19 ng/mL | [STATUS_BADGE]: Done        | Oct 23, 2024          |
| Biomarkers         | MARKER     | PSA                                                                | [STATUS_BADGE]: Missed      | Feb 09, 2025          |
| Biomarkers         | MARKER     | Prostate specific Ag [Mass/volume] in Serum or Plasma 2.43 ng/mL   | [STATUS_BADGE]: Done        | Apr 25, 2025          |
| Treatment and Plan | MARKER     | Androgen Receptor Degrade                                          | [STATUS_BADGE]: Missed      | Nov 07, 2024          |
When the user opens the timeline filter modal
Then the following notification is displayed on the Timeline filter modal: "Filters disabled\nYou can’t edit filters in Treatment adherence mode.\nExit Treatment adherence mode"
When the user closes the timeline filter modal
And the user clicks on the following event point on the "Encounters" swimlane:
| event_type | name_on_label  |
| MARKER     | Oncology Visit |
Then the "Mark as important" button is disabled on the Sidebar header
And the "Label" button is disabled on the Sidebar header
And the "0 comments" button is disabled on the Sidebar header
When the user clicks on "View mode" button on the Timeline toolbar
Then the "View mode" modal is displayed
When the user selects "Default" checkbox on the View mode modal
And the user clicks "Done" on the "View mode" modal
Then the "View mode" modal is not displayed
And the patient timeline is loaded
And the following events are available on the "Encounters" swimlane:
| event_type | name_on_label         |
| MARKER     | Body weight 101.15 kg |
| MARKER     | Oncology Visit        |
| MARKER     | Counselling           |
And the following events are available on the "Imaging" swimlane:
| event_type | name_on_label |
| MARKER     | PSMA-PET scan |
| MARKER     | PSMA-PET scan |
And the following events are available on the "Pathology and Labs" swimlane:
| event_type | name_on_label                            |
| MARKER     | Hepatic function panel - Serum or Plasma |
| MARKER     | PSA measurement                          |
And the following events are available on the "Biomarkers" swimlane:
| event_type | name_on_label                                                      |
| MARKER     | Prostate specific Ag [Mass/volume] in Serum or Plasma 0.22 ng/mL   |
| MARKER     | Prostate specific Ag [Mass/volume] in Serum or Plasma 196.19 ng/mL |
| MARKER     | Prostate specific Ag [Mass/volume] in Serum or Plasma 2.43 ng/mL   |
And the following events are available on the "Treatment and Plan" swimlane:
| event_type | text_on_event_point | name_on_label             |
| CLUSTER    | 2                   | 2 events                  |
| MARKER     | <EMPTY>             | docetaxel                 |
| MARKER     | <EMPTY>             | Androgen Receptor Degrade |
And the following events are not available on the "Uncategorized" swimlane:
| event_type | name_on_label                                                           |
| MARKER     | Alanine aminotransferase [Enzymatic activity/volume] in Serum or Plasma |

@srs
@TC-70932
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68508
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Treatment_Adherence
Scenario: User can cancel the Treatment protocol selection for the patient
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_TA, Higgins" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the following events are available on the "Encounters" swimlane:
| event_type | name_on_label         |
| MARKER     | Body weight 101.15 kg |
| MARKER     | Oncology Visit        |
| MARKER     | Counselling           |
And the following events are available on the "Imaging" swimlane:
| event_type | name_on_label |
| MARKER     | PSMA-PET scan |
| MARKER     | PSMA-PET scan |
And the following events are available on the "Pathology and Labs" swimlane:
| event_type | name_on_label                            |
| MARKER     | Hepatic function panel - Serum or Plasma |
| MARKER     | PSA measurement                          |
And the following events are available on the "Biomarkers" swimlane:
| event_type | name_on_label                                                      |
| MARKER     | Prostate specific Ag [Mass/volume] in Serum or Plasma 0.22 ng/mL   |
| MARKER     | Prostate specific Ag [Mass/volume] in Serum or Plasma 196.19 ng/mL |
| MARKER     | Prostate specific Ag [Mass/volume] in Serum or Plasma 2.43 ng/mL   |
And the following events are available on the "Treatment and Plan" swimlane:
| event_type | text_on_event_point | name_on_label             |
| CLUSTER    | 2                   | 2 events                  |
| MARKER     | <EMPTY>             | docetaxel                 |
| MARKER     | <EMPTY>             | Androgen Receptor Degrade |
And the following events are not available on the "Uncategorized" swimlane:
| event_type | name_on_label                                                           |
| MARKER     | Alanine aminotransferase [Enzymatic activity/volume] in Serum or Plasma |
When the user clicks on "View mode" button on the Timeline toolbar
Then the "View mode" modal is displayed
When the user selects "Treatment adherence" checkbox on the View mode modal
And the user sets the following options on "View mode" modal:
| protocol                             | trial_arm                           | start_date   |
| CC-94676 for Prostate Cancer Patient | CC94676 for Prostate Cancer Patient | Jun 07, 2024 |
And the user clicks "Cancel" on the "View mode" modal
Then the "View mode" modal is not displayed
And the "Tx" badge is not displayed on the "View mode" button
And the patient timeline is loaded
And the following events are available on the "Encounters" swimlane:
| event_type | name_on_label         |
| MARKER     | Body weight 101.15 kg |
| MARKER     | Oncology Visit        |
| MARKER     | Counselling           |
And the following events are available on the "Imaging" swimlane:
| event_type | name_on_label |
| MARKER     | PSMA-PET scan |
| MARKER     | PSMA-PET scan |
And the following events are available on the "Pathology and Labs" swimlane:
| event_type | name_on_label                            |
| MARKER     | Hepatic function panel - Serum or Plasma |
| MARKER     | PSA measurement                          |
And the following events are available on the "Biomarkers" swimlane:
| event_type | name_on_label                                                      |
| MARKER     | Prostate specific Ag [Mass/volume] in Serum or Plasma 0.22 ng/mL   |
| MARKER     | Prostate specific Ag [Mass/volume] in Serum or Plasma 196.19 ng/mL |
| MARKER     | Prostate specific Ag [Mass/volume] in Serum or Plasma 2.43 ng/mL   |
And the following events are available on the "Treatment and Plan" swimlane:
| event_type | text_on_event_point | name_on_label             |
| CLUSTER    | 2                   | 2 events                  |
| MARKER     | <EMPTY>             | docetaxel                 |
| MARKER     | <EMPTY>             | Androgen Receptor Degrade |
And the following events are not available on the "Uncategorized" swimlane:
| event_type | name_on_label                                                           |
| MARKER     | Alanine aminotransferase [Enzymatic activity/volume] in Serum or Plasma |
When the user clicks on "View mode" button on the Timeline toolbar
Then the "View mode" modal is displayed