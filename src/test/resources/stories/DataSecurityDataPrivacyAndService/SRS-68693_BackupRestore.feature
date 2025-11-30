Feature: [SysRS-68693] Backup & Restore

Narrative: The system shall support backup and restore functionality for the following:
           - Application configurations.
           - System's settings:
                - Labelled events, CLP on/off, marked as important events, timeline direction and swimlane order settings
           - Comments, Label list.
           - Valuesets.

@srs
@SRS-68693.01
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68693
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Backup_Restore
Scenario: Application configuration can be backed up and restored
Given the following request was sent to CareIntellect for Oncology by service user app:
| service | method | endpoint_path | payload                               | content_type     |
| core    | PUT    | /configs/app  | configuration/default_app_config.json | application/json |
And the received HTTP status code is 204
And following request was sent to CareIntellect for Oncology by service user app:
| service | method | endpoint_path                                              | payload                                                                                           | content_type     |
| core    | PUT    | /configs/fulfillment-query-set/medicalHistory.onco.general | configuration/repo/default/representation/fulfillment-query-set/medical-history-onco-general.json | application/json |
And the received HTTP status code is 204
And the following request was sent to CareIntellect for Oncology by service user app:
| service | method | endpoint_path                                          | payload                                                                                       | content_type     |
| core    | PUT    | /configs/representation/treatmentResponse.onco.general | configuration/repo/default/representation/representation/treatment-response-onco-general.json | application/json |
And following request was sent to CareIntellect for Oncology by service user app:
| service | method | endpoint_path                        | payload                                                                    | content_type     |
| core    | PUT    | /configs/app-preset/oncology.default | configuration/repo/default/representation/app-preset/oncology-default.json | application/json |
And the received HTTP status code is 204
When I create a backup for the representation-configs table as "<environment_name>-representation-configs-backup" (see Readme.md)
Then I see the backup is created
When the following request is sent to CareIntellect for Oncology by service user app:
| service | method | endpoint_path | payload                             | content_type     |
| core    | PUT    | /configs/app  | configuration/empty_app_config.json | application/json |
Then the received HTTP status code is 204
When the following request is sent to CareIntellect for Oncology by service user app:
| service | method | endpoint_path                                              | payload                                                                    | content_type     |
| core    | PUT    | /configs/fulfillment-query-set/medicalHistory.onco.general | configuration/modified_configs/queryset/medical-history-onco-modified.json | application/json |
Then the received HTTP status code is 204
When the following request is sent to CareIntellect for Oncology by service user app:
| service | method | endpoint_path                                          | payload                                                                        | content_type     |
| core    | PUT    | /configs/representation/treatmentResponse.onco.general | configuration/modified_configs/representation/treatment-response-modified.json | application/json |
Then the received HTTP status code is 204
When the following request is sent to CareIntellect for Oncology by service user app:
| service | method | endpoint_path                        | payload                                                                  | content_type     |
| core    | PUT    | /configs/app-preset/oncology.default | configuration/modified_configs/app-preset/oncology-default-modified.json | application/json |
Then the received HTTP status code is 204
When I restore the "<environment_name>-representation-configs-backup" backup (see Readme.md)
And the following request is sent to CareIntellect for Oncology by service user app:
| service | method | endpoint_path |
| core    | GET    | /configs/app  |
Then the received HTTP status code is 200
And the response body matches the content of the following configuration file: configuration/default_app_config.json
When the following request is sent to CareIntellect for Oncology by service user app:
| service | method | endpoint_path                                              |
| core    | GET    | /configs/fulfillment-query-set/medicalHistory.onco.general |
Then the received HTTP status code is 200
And the response body matches the content of the following configuration file: configuration/repo/default/representation/fulfillment-query-set/medical-history-onco-general.json
When the following request is sent to CareIntellect for Oncology by service user app:
| service | method | endpoint_path                                          |
| core    | GET    | /configs/representation/treatmentResponse.onco.general |
Then the received HTTP status code is 200
And the response body matches the content of the following configuration file: configuration/repo/default/representation/representation/treatment-response-onco-general.json
When the following request is sent to CareIntellect for Oncology by service user app:
| service | method | endpoint_path                        |
| core    | GET    | /configs/app-preset/oncology.default |
Then the received HTTP status code is 200
And the response body matches the content of the following configuration file: configuration/repo/default/representation/app-preset/oncology-default.json

@srs
@SRS-68693.02
@manual
@CLP
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68693
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Backup_Restore
Scenario: Comments can be backed up and restored
Given the [DEFAULT-TEST-USER] user is logged in to CareIntellect for Oncology application
And the "OncoTestPatient, Juno" patient is selected
And comments were added on the Timeline to the following events as:
| swimlane   | event_label                            | comment          |
| Encounters | History and physical note              | Backup comment 1 |
| Imaging    | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Backup comment 2 |
And "Backup comment 3" comment was added on "Comments" view
And the following comments are available on the Comments view:
| author              | date_and_time          | content          | badge            | has_view_on_timeline_link |
| [DEFAULT-TEST-USER] | <dateAndTimeOfComment> | Backup comment 1 | Timeline comment | yes                       |
| [DEFAULT-TEST-USER] | <dateAndTimeOfComment> | Backup comment 2 | Timeline comment | yes                       |
| [DEFAULT-TEST-USER] | <dateAndTimeOfComment> | Backup comment 3 | <N/A>            | <N/A>                     |
When I create a backup for the representation-comments DB as "<environment_name>-representation-comments-backup" (see Readme.md)
Then I see the backup is created
When the [DEFAULT-TEST-USER] user logs in to CareIntellect for Oncology application
And the "OncoTestPatient, Juno" patient is selected
And I delete the following comments on "Comments" view:
| comment          |
| Backup comment 1 |
| Backup comment 2 |
| Backup comment 3 |
And I add the "Should not restore this comment 1" comment on "Comments" view
Then the following comments are available on the Comments view:
| author              | date_and_time          | content                           | badge | has_view_on_timeline_link |
| [DEFAULT-TEST-USER] | <dateAndTimeOfComment> | Should not restore this comment 1 | <N/A> | <N/A>                     |
When I restore the "<environment_name>-representation-comments-backup" backup (see Readme.md)
And the [DEFAULT-TEST-USER] user logs in to CareIntellect for Oncology application
And the "OncoTestPatient, Juno" patient is selected
Then the following comments are available on the Comments view:
| author              | date_and_time          | content          | badge            | has_view_on_timeline_link |
| [DEFAULT-TEST-USER] | <dateAndTimeOfComment> | Backup comment 1 | Timeline comment | yes                       |
| [DEFAULT-TEST-USER] | <dateAndTimeOfComment> | Backup comment 2 | Timeline comment | yes                       |
| [DEFAULT-TEST-USER] | <dateAndTimeOfComment> | Backup comment 3 | <N/A>            | <N/A>                     |
And the following comment is not available on the Comments view:
| author              | date_and_time          | content                           | badge | has_view_on_timeline_link |
| [DEFAULT-TEST-USER] | <dateAndTimeOfComment> | Should not restore this comment 1 | <N/A> | <N/A>                     |

@srs
@SRS-68693.03
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68693
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Backup_Restore
Scenario: Labels can be backed up and restored
Given the [DEFAULT-TEST-USER] user is logged in to CareIntellect for Oncology application
And the "OncoTestPatient, Juno" patient is selected
And the following labels were created:
| label          | color |
| backup label 1 | blue  |
| backup label 2 | green |
When I create a backup for the representation-labels as "<environment_name>-representation-labels-backup" (see Readme.md)
Then I see the backup is created
When the [DEFAULT-TEST-USER] user logs in to CareIntellect for Oncology application
And the "OncoTestPatient, Juno" patient is selected
And I create the following labels:
| label                         | color  |
| Should not restore this label | orange |
Then the following labels are available in the application:
| label                         | color  |
| backup label 1                | blue   |
| backup label 2                | green  |
| Should not restore this label | orange |
When I restore the "<environment_name>-representation-labels-backup" backup (see Readme.md)
And the [DEFAULT-TEST-USER] user logs in to CareIntellect for Oncology application
And the "OncoTestPatient, Juno" patient is selected
Then the following labels are available in the application:
| label          | color |
| backup label 1 | blue  |
| backup label 2 | green |
And the following labels are not available in the application:
| label                         | color  |
| Should not restore this label | orange |

@srs
@SRS-68693.04
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68693
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Backup_Restore
Scenario: Valuesets can be backed up and restored
Given the user sends the following request to upload valueset to the application:
| endpoint_path                   | method | payload                                                |
| <onco_fulfill_api_gw>/value-set | POST   | testdata/backup_and_restore/backup_and_restore_vs.json |
And the received HTTP status code is 200
When I create a backup for the fulfill-value-sets as "<environment_name>-fulfill-value-sets-backup" (see Readme.md)
Then I see the backup is created
When the user sends the following request to upload valueset to the application:
| endpoint_path                   | method | payload                                                |
| <onco_fulfill_api_gw>/value-set | POST   | testdata/backup_and_restore/should_not_restore_vs.json |
And the received HTTP status code is 200
And the user sends the following request to retrieve valuesets:
| endpoint_path                   | method | payload |
| <onco_fulfill_api_gw>/value-set | GET    | N/A     |
Then the following valuesets are available in the response body:
| name                  | description           |
| backup_and_restore_vs | backup and restore vs |
| should_not_restore_vs | should not restore vs |
When I restore the "<environment_name>-fulfill-value-sets-backup" backup (see Readme.md)
And the user sends the following request to retrieve valuesets:
| endpoint_path                   | method | payload |
| <onco_fulfill_api_gw>/value-set | GET    | N/A     |
Then the following valuesets are available in the response body:
| name                  | description           |
| backup_and_restore_vs | backup and restore vs |
And the following valuesets are not available in the response body:
| name                  | description           |
| should_not_restore_vs | should not restore vs |

@srs
@SRS-68693.05
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68693,SysRS-68466
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Backup_Restore
Scenario: User settings can be backed up and restored
Given the [DEFAULT-TEST-USER] user is logged in to CareIntellect for Oncology application
And the "OncoTestPatient, Juno" patient is selected
And the user sets the following group settings on the "Summary order and visibility" modal:
| column_index | group_index_in_column | group_name        | hidden |
| 0            | 0                     | Treatments        | no     |
| 0            | 1                     | ER visit          | yes    |
| 0            | 2                     | Care team         | no     |
| 1            | 0                     | Imaging           | no     |
| 1            | 1                     | Patient           | yes    |
| 1            | 2                     | Comments          | yes    |
| 1            | 3                     | Diagnosis         | no     |
| 1            | 4                     | Molecular profile | no     |
| 2            | 0                     | Medical history   | yes    |
| 2            | 1                     | Labs              | no     |
| 2            | 2                     | Oncology note     | no     |
| 2            | 3                     | MDT report        | yes    |
And the user turns on "Artificial Intelligence (AI)" in user settings
And the user sets the timeline direction to show the newest event on left in user settings
And the user sets the following order in "Swimlane order" list in user settings:
| swimlane_names     |
| Biomarkers         |
| Encounters         |
| Imaging            |
| Pathology and Labs |
| Uncategorized      |
| Treatment and Plan |
When I create a backup for the representation-settings as "<environment_name>-representation-settings-backup" (see Readme.md)
Then I see the backup is created
When the [DEFAULT-TEST-USER] user logs in to CareIntellect for Oncology application
And the "OncoTestPatient, Juno" patient is selected
And the user selects "Hide empty cards" checkbox in the View options
And the user clicks on the "Reset to default" button on the "Summary order and visibility" modal
And the timeline direction is set to show the newest event on right in user settings
And the user turns off "Artificial Intelligence (AI)" in user settings
And the user sets the following order in "Swimlane order" list in user settings:
| swimlane_names     |
| Treatment and Plan |
| Biomarkers         |
| Encounters         |
| Uncategorized      |
| Imaging            |
| Pathology and Labs |
When I restore the "<environment_name>-representation-settings-backup" backup (see Readme.md)
And the [DEFAULT-TEST-USER] user logs in to CareIntellect for Oncology application
And the "OncoTestPatient, Juno" patient is selected
Then the following group settings displayed on the "Summary order and visibility" modal:
| column_index | group_index_in_column | group_name        | hidden |
| 0            | 0                     | Treatments        | no     |
| 0            | 1                     | ER visit          | yes    |
| 0            | 2                     | Care team         | no     |
| 1            | 0                     | Imaging           | no     |
| 1            | 1                     | Patient           | yes    |
| 1            | 2                     | Comments          | yes    |
| 1            | 3                     | Diagnosis         | no     |
| 1            | 4                     | Molecular profile | no     |
| 2            | 0                     | Medical history   | yes    |
| 2            | 1                     | Labs              | no     |
| 2            | 2                     | Oncology note     | no     |
| 2            | 3                     | MDT report        | yes    |
And the "Artificial Intelligence (AI)" turned on in user settings
And the timeline direction is set to show the newest event on left
And the following order is set in "Swimlane order" list in user settings:
| swimlane_names     |
| Biomarkers         |
| Encounters         |
| Imaging            |
| Pathology and Labs |
| Uncategorized      |
| Treatment and Plan |
And the "Hide empty cards" checkbox in the View options is unselected

@srs
@SRS-68693.06
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68693
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Backup_Restore
Scenario: Marked as important events, labelled events can be backed up and restored
Given the [DEFAULT-TEST-USER] user is logged in to CareIntellect for Oncology application
And the "OncoTestPatient, Juno" patient is selected
And labels were added to the following events as:
| swimlane           | event                    | labels                         |
| Encounters         | Tumor board Note         | Progression; Molecular testing |
| Imaging            | Diagnostic imaging study | To be reviewed                 |
| Treatment and Plan | Suture open wound        | Date of diagnosis              |
And the following events were marked as important:
| swimlane           | event_label          |
| Encounters         | Body Weight 47.3 kg  |
| Pathology and Labs | Pathology Report     |
When I create a backup for the representation-settings as "<environment_name>-representation-settings-backup" (see Readme.md)
Then I see the backup is created
When the [DEFAULT-TEST-USER] user logs in to CareIntellect for Oncology application
And the "OncoTestPatient, Juno" patient is selected
And labels were added to the following events as:
| swimlane           | event                           | labels            |
| Encounters         | History and physical note - 1   | To be reviewed    |
| Pathology and Labs | Tissue Pathology biopsy report  | Molecular testing |
| Treatment and Plan | Radiation oncology Summary note | Date of diagnosis |
And the following events were marked as important:
| swimlane           | event                        |
| Treatment and Plan | Coronary artery bypass       |
| Pathology and Labs | DiagnosticReport with binary |
And the user unmarks the following events as important:
| swimlane   | event               |
| Encounters | Body Weight 47.3 kg |
Then the following events are available in the Timeline:
| swimlane           | event                           | labels                         |
| Encounters         | Tumor board Note                | Progression; Molecular testing |
| Imaging            | Diagnostic imaging study        | To be reviewed                 |
| Treatment and Plan | Suture open wound               | Date of diagnosis              |
| Encounters         | History and physical note - 1   | To be reviewed                 |
| Pathology and Labs | Tissue Pathology biopsy report  | Molecular testing              |
| Treatment and Plan | Radiation oncology Summary note | Date of diagnosis              |
| Treatment and Plan | Coronary artery bypass          | Important                      |
| Pathology and Labs | DiagnosticReport with binary    | Important                      |
| Pathology and Labs | Pathology Report                | Important                      |
| Encounters         | Body Weight 47.3 kg             | N/A                            |
When I restore the "<environment_name>-representation-settings-backup" backup (see Readme.md)
And the [DEFAULT-TEST-USER] user logs in to CareIntellect for Oncology application
And the "OncoTestPatient, Juno" patient is selected
Then the following labels are set for the following events:
| swimlane           | event                    | labels                         |
| Encounters         | Tumor board Note         | Progression; Molecular testing |
| Imaging            | Diagnostic imaging study | To be reviewed                 |
| Treatment and Plan | Suture open wound        | Date of diagnosis              |
And the following labels are not set for the following events:
| swimlane           | event                           | labels            |
| Encounters         | History and physical note - 1   | To be reviewed    |
| Pathology and Labs | Tissue Pathology biopsy report  | Molecular testing |
| Treatment and Plan | Radiation oncology Summary note | Date of diagnosis |
And the following events are marked as important:
| swimlane           | event                |
| Encounters         | Body Weight 47.3 kg  |
| Pathology and Labs | Pathology Report     |
And the following events are not marked as important:
| swimlane           | event                        |
| Treatment and Plan | Coronary artery bypass       |
| Pathology and Labs | DiagnosticReport with binary |

@srs
@SRS-68693.07
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68693,SysRS-68571
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Backup_Restore
Scenario: Trends settings can be backed up and restored
Given the [DEFAULT-TEST-USER] user is logged in to CareIntellect for Oncology application
And the "OncoTestPatient, Juno" patient is selected
And the user navigates to Trends view
And the "List View" option is selected in the Trends toolbar
And the "Reference Range" was turned on in the Trends toolbar
And the "Last 30 days" option selected
When I create a backup for the representation-settings as "<environment_name>-representation-settings-backup" (see Readme.md)
Then I see the backup is created
When the [DEFAULT-TEST-USER] user logs in to CareIntellect for Oncology application
And the "OncoTestPatient, Juno" patient is selected
And the user select "Grid View" option in the Trends toolbar
And the user unselects the "Reference Range" in the Trends toolbar
And the user select "Last 90 days" option
Then the following charts displayed in Grid view without reference range:
| chart_title                       | x_axis_start  | x_axis_end |
| Body Weight (kg)                  | <90_DAYS_AGO> | <TODAY>    |
| Prostate specific antigen (ng/mL) | <90_DAYS_AGO> | <TODAY>    |
When I restore the "<environment_name>-representation-settings-backup" backup (see Readme.md)
And the [DEFAULT-TEST-USER] user logs in to CareIntellect for Oncology application
And the "OncoTestPatient, Juno" patient is selected
And the user navigates to Trends view
Then the following charts displayed in List view with reference range:
| chart_title                       | x_axis_start  | x_axis_end |
| Body Weight (kg)                  | <30_DAYS_AGO> | <TODAY>    |
| Prostate specific antigen (ng/mL) | <30_DAYS_AGO> | <TODAY>    |

@srs
@SRS-68693.08
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68693
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Backup_Restore
Scenario: Response settings can be backed up and restored
Given the [DEFAULT-TEST-USER] user is logged in to CareIntellect for Oncology application
And the "OncoTestPatient, Juno" patient is selected
And the Treatment and response view is loaded
And the following imaging studies are selected in Response view:
| study | imaging_study_name       | date         |
| A     | XR CHEST PA OR AP        | Aug 18, 2022 |
| B     | Diagnostic imaging study | Aug 19, 2022 |
And the following custom date range is selected on the chart slider:
| start_date   | end_date |
| Sep 28, 2011 | <TODAY>  |
When I create a backup for the representation-settings as "<environment_name>-representation-settings-backup" (see Readme.md)
Then I see the backup is created
When the [DEFAULT-TEST-USER] user logs in to CareIntellect for Oncology application
And the "OncoTestPatient, Juno" patient is selected
And the user opens Treatment and response view
And the user clicks on 'Reset to default' button in the Imaging study selector
And the user clicks "Last 6 months" button on the multiline chart
Then the following imaging studies are selected:
| study | imaging_study_name                                       | date         |
| A     | Diagnostic imaging study                                 | Aug 19, 2022 |
| B     | Examination of joint under image intensifier (procedure) | Sep 09, 2022 |
And the following date range is displayed in the x axis:
| start_date     | end_date |
| <6_MONTHS_AGO> | <TODAY>  |
When I restore the "<environment_name>-representation-settings-backup" backup (see Readme.md)
And the [DEFAULT-TEST-USER] user logs in to CareIntellect for Oncology application
And the "OncoTestPatient, Juno" patient is selected
And the user opens Treatment and response view
Then the following imaging studies are selected in Response view:
| study | imaging_study_name       | date         |
| A     | XR CHEST PA OR AP        | Aug 18, 2022 |
| B     | Diagnostic imaging study | Aug 19, 2022 |
And the following custom date range is selected on the chart slider:
| start_date   | end_date |
| Sep 28, 2011 | <TODAY>  |

@srs
@SRS-68693.09
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68693,SysRS-68517
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Backup_Restore
Scenario: Summary and Timeline settings can be backed up and restored
Given the [DEFAULT-TEST-USER] user is logged in to CareIntellect for Oncology application
And the "TEST_TA, Higgins" patient is selected
And the patient banner is collapsed
And the Progressive Summarization Overview is not collapsed
And the "Timeline" view is selected
And the following event is opened:
| swimlane   | event_name            | event_date   |
| Encounters | Body weight 101.15 kg | Jun 11, 2017 |
And the user clicks on the 'All time' button on the Timeline toolbar
And the following timeline filter options are selected:
| filter_group       | checkbox_name      |
| Swimlanes          | Encounters         |
| Swimlanes          | Treatment and Plan |
| Swimlanes          | Uncategorized      |
| Labels             | Important          |
| Labels             | AI enriched        |
| Pathology and Labs | Pathology          |
| Pathology and Labs | Labs               |
When I create a backup for the representation-settings as "<environment_name>-representation-settings-backup" (see Readme.md)
Then I see the backup is created
When the [DEFAULT-TEST-USER] user logs in to CareIntellect for Oncology application
And the "TEST_TA, Higgins" patient is selected
And the user expands the patient banner
And the user collapses the Progressive Summarization Overview
And the user navigates to "Timeline" view
And the user opens the following event:
| swimlane           | event_name                               | event_date   |
| Pathology and Labs | Hepatic function panel - Serum or Plasma | Apr 06, 2017 |
And the user selects 'Hide navigator' checkbox on the Timeline toolbar
And the user clicks on the 'Last 30 days' button on the Timeline toolbar
And the following timeline filter options are selected:
| filter_group       | checkbox_name |
| Swimlanes          | Imaging       |
| Swimlanes          | Biomarkers    |
| Swimlanes          | Uncategorized |
| Labels             | Has comments  |
| Labels             | Chemo cycle   |
| Pathology and Labs | Pathology     |
When I restore the "<environment_name>-representation-settings-backup" backup (see Readme.md)
And the [DEFAULT-TEST-USER] user logs in to CareIntellect for Oncology application
And the "TEST_TA, Higgins" patient is selected
Then I see that patient banner is collapsed
And the Progressive Summarization Overview is not collapsed
When the user navigates to "Timeline" view
Then I see that the following event is opened:
| swimlane   | event_name            | event_date   |
| Encounters | Body weight 101.15 kg | Jun 11, 2017 |
And the Timeline displayed between the following range:
| start_date | end_date |
| 2014       | 2025     |
And the 'Hide navigator' checkbox is not selected on the Timeline toolbar
And the following timeline filter options are selected:
| filter_group       | checkbox_name      |
| Swimlanes          | Encounters         |
| Swimlanes          | Treatment and Plan |
| Swimlanes          | Uncategorized      |
| Labels             | Important          |
| Labels             | AI enriched        |
| Pathology and Labs | Pathology          |
| Pathology and Labs | Labs               |
And the following timeline filter option are not selected:
| filter_group | checkbox_name |
| Swimlanes    | Imaging       |
| Swimlanes    | Biomarkers    |
| Labels       | Has comments  |
| Labels       | Chemo cycle   |

@srs
@SRS-68693.10
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68693
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Backup_Restore
Scenario: Timeline view mode can be backed up and restored
Given [API] the following treatment protocol was uploaded: treatment/cc-94676_adherence_config.json
And the [DEFAULT-TEST-USER] user is logged in to CareIntellect for Oncology application
And the "TEST_TA, Higgins" patient is selected
And the "Timeline" view is selected
And the user sets the following treatment protocol configuration for the patient:
| view_mode           | protocol                             | trial_arm                           | start_date   |
| Treatment adherence | CC-94676 for Prostate Cancer Patient | CC94676 for Prostate Cancer Patient | Nov 07, 2024 |
And the timeline is zoomed in to max resolution
And the timeline is displayed between the following range:
| start_date  | end_date    |
| 2025 Apr 10 | 2025 Apr 24 |
And the following event is visible on the Timeline:
| swimlane   | event_name     | event_date   |
| Encounters | Oncology Visit | Apr 12, 2025 |
When I create a backup for the representation-settings as "<environment_name>-representation-settings-backup" (see Readme.md)
Then I see the backup is created
When the [DEFAULT-TEST-USER] user logs in to CareIntellect for Oncology application
And the "TEST_TA, Higgins" patient is selected
And the user resets the timeline settings using "Reset to default view" in the More menu
Then the patient timeline is loaded in Default view mode
When I restore the "<environment_name>-representation-settings-backup" backup (see Readme.md)
And the [DEFAULT-TEST-USER] user logs in to CareIntellect for Oncology application
And the "TEST_TA, Higgins" patient is selected
And the "Timeline" view is selected
Then the patient timeline is loaded in Treatment adherence view mode
And the timeline is zoomed in to max resolution
And the timeline is displayed between the following range:
| start_date  | end_date    |
| 2025 Apr 10 | 2025 Apr 24 |
And the following event is visible on the Timeline:
| swimlane   | event_name     | event_date   |
| Encounters | Oncology Visit | Apr 12, 2025 |
