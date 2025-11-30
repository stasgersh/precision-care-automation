Feature: [SysRS-68586] Settings – store view state, patient level

Narrative: The system shall be able to save the state of a user’s session and the application’s view settings (at the patient level) so that the view settings can be restored the next time the user accesses the application:
           - Summary view (and sub-views)
           - Trend's view (and sub-views if available)
           - Medical history view (and sub-views if available)
           - Timeline view (and sub-views if available)
           - Clinical Trial Eligibility view (and sub-views if available)
           - Response tracking view (and sub-views if available)
           - Comment’s view (and sub-views if available)


@SRS-68586.01
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68586,SysRS-103347
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Store_View_State
Scenario: Timeline settings are saved for user on patient level (opened event, zoom level, view mode)
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_TA, Higgins" patient is selected
And the "Timeline" view is selected
When the user sets the following treatment protocol configuration for the patient:
| view_mode           | protocol                             | trial_arm                           | start_date   |
| Treatment adherence | CC-94676 for Prostate Cancer Patient | CC94676 for Prostate Cancer Patient | Jan 01, 2025 |
Then the patient timeline is loaded
When the user clicks on the 'All time' button on the timeline toolbar
And the user clicks on the following event point on the "Encounters" swimlane:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 2                   | 2 events      |
And the user selects the following event from the cluster pill:
| event_name  |
| Counselling |
Then the sidebar is displayed
And the sidebar header contains the following data:
| title       | creation_date | report_type |
| Counselling | Nov 15, 2025  | Encounters  |
When the user selects the "OncoTestPatient, Casper" patient
And the user navigates to the "Timeline" view
Then the patient timeline is loaded
And the sidebar is not displayed
And the "Tx" badge is not displayed on the "View mode" button
When the user scrolls the timeline horizontally to the left side
Then the below events on "Encounters" swimlane are visible in the viewport:
| event_type | name_on_label       | date_on_timeline_axis |
| MARKER     | Body Weight 48.8 kg | Mar 15, 1928          |
When the user selects the "TEST_TA, Higgins" patient (without reseting patient settings)
And the user navigates to the "Timeline" view
Then the patient timeline is loaded
And the "Tx" badge is displayed on the "View mode" button
And the sidebar is displayed
And the sidebar header contains the following data:
| title       | creation_date | report_type |
| Counselling | Nov 15, 2025  | Encounters  |
And the Zoom out button is not enabled
When the user selects the "OncoTestPatient, Casper" patient (without reseting patient settings)
And the user navigates to the "Timeline" view
Then the patient timeline is loaded
And the below events on "Encounters" swimlane are visible in the viewport:
| event_type | name_on_label       | date_on_timeline_axis |
| MARKER     | Body Weight 48.8 kg | Mar 15, 1928          |
When the user logs out from OncoCare
And the [TEST-USER-2] user logs in to OncoCare
And the user selects the "TEST_TA, Higgins" patient (without reseting patient settings)
And the user navigates to the "Timeline" view
Then the patient timeline is loaded
And the sidebar is not displayed
And the "Tx" badge is not displayed on the "View mode" button

@SRS-68586.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68586,SysRS-103350
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Store_View_State
Scenario: Comment settings are saved for user on patient level
Given [API] the [DEFAULT-TEST-USER] user has neither patient nor event comments for the following patients:
| patientID                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a |
| 5cf7faf6-41c0-459e-84ff-fd57a2626666 |
And [API] the comment settings are reset for "OncoTestPatient, Juno" by the [DEFAULT-TEST-USER] user
And [API] the comment settings are reset for "OncoTestPatient, Casper" by the [DEFAULT-TEST-USER] user
And [API] the below patient comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | comment_text                |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | this is a patient comment 1 |
| 5cf7faf6-41c0-459e-84ff-fd57a2626666 | this is a patient comment 2 |
And [API] the below event comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | root_resource_of_the_event                       | comment_text               |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f | This is an event comment 1 |
| 5cf7faf6-41c0-459e-84ff-fd57a2626666 | Observation/e00ec944-dd24-4ff2-879e-c9ba44c93c16 | This is an event comment 2 |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
When the user navigates to the "Comments" view
Then the counter badge displays "2" on the "Comments" tab
Then the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time | content                     | badge            | has_view_on_timeline_link |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | this is a patient comment 1 | <N/A>            | <N/A>                     |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | This is an event comment 1  | Timeline comment | yes                       |
When the user clicks on the 'Hide Timeline comments' on the Comments view
Then the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time           | content                     |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | this is a patient comment 1 |
And the following comments are not available on the Comments view:
| author (firstname lastname) | date_and_time           | content                    |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | This is an event comment 1 |
When the user selects the "OncoTestPatient, Casper" patient
And the user navigates to the "Comments" view
Then the counter badge displays "2" on the "Comments" tab
And the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time | content                     | badge            | has_view_on_timeline_link |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | this is a patient comment 2 | <N/A>            | <N/A>                     |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | This is an event comment 2  | Timeline comment | yes                       |
When the user selects the "OncoTestPatient, Juno" patient
And the user navigates to the "Comments" view
Then the counter badge displays "2" on the "Comments" tab
Then the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time | content                     | badge | has_view_on_timeline_link |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | this is a patient comment 1 | <N/A> | <N/A>                     |
And the following comments are not available on the Comments view:
| author (firstname lastname) | date_and_time | content                    | badge            | has_view_on_timeline_link |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | This is an event comment 1 | Timeline comment | yes                       |

@SRS-68586.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68586,SysRS-103348
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Store_View_State
Scenario: Clinical Trial Eligibility settings are saved for user on patient level
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And [API] the trials settings are reset for "OncoTestPatient, Juno" by the [DEFAULT-TEST-USER] user
And [API] the trials settings are reset for "OncoTestPatient, Al" by the [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
When the user clicks "Clinical trials" button
Then the Trials view is displayed
When the user selects the following trial id:NCT04428789 from the list of trials
Then the patient's criteria basic data is available for NCT04428789 and check the following fields and data:
| Field             | Field content                                                                                                                                                                 |
| Trial name        | Study II to Evaluate the Safety and Tolerability of CC-94676 in Participants With Metastatic Castration-Resistant Prostate Cancer                                             |
| Trial description | The purpose of this study is to assess the safety, tolerability and preliminary efficacy of CC-94676 in men with progressive metastatic castration resistant prostate cancer. |
| Matching score    | No match                                                                                                                                                                      |
| Phase             | 1                                                                                                                                                                             |
| Status            | Recruiting                                                                                                                                                                    |
| Trial_ID          | NCT04428789                                                                                                                                                                   |
When the user clicks on the filter icon in the 'Type' column
And the user selects Inclusion status from the filter's list
Then the "Filter By" frame contains only selected "Inclusion"
When the user clicks on the sort arrow for the "Status" column
Then the criteria table is sorted by "Status" column which has an "up" pointing arrow
When the user selects the "OncoTestPatient, Al" patient
And the user clicks "Clinical trials" button
Then the Trials view is displayed
When the user selects the following trial id:NCT04428788 from the list of trials
Then the patient's criteria basic data is available for NCT04428788 and check the following fields and data:
| Field             | Field content                                                                                                                                                                 |
| Trial name        | Study to Evaluate the Safety and Tolerability of CC-94676 in Participants With Metastatic Castration-Resistant Prostate Cancer                                                |
| Trial description | The purpose of this study is to assess the safety, tolerability and preliminary efficacy of CC-94676 in men with progressive metastatic castration resistant prostate cancer. |
| Matching score    | No match                                                                                                                                                                      |
| Phase             | 1                                                                                                                                                                             |
| Status            | Recruiting                                                                                                                                                                    |
| Trial_ID          | NCT04428788                                                                                                                                                                   |
When the user clicks on the filter icon in the 'Status' column
And the user selects Inclusion met status from the filter's list
Then the "Filter By" frame contains only selected "Inclusion met"
When the user clicks on the sort arrow for the "Type" column
Then the criteria table is sorted by "Type" column which has an "up" pointing arrow
When the user selects the "OncoTestPatient, Juno" patient
Then the patient summary view is loaded
And the "Trials" tab is visible on the navigation bar
When the user clicks "Clinical trials" button
Then the Trials view is displayed
And the patient's criteria basic data is available for NCT04428789 and check the following fields and data:
| Field             | Field content                                                                                                                                                                 |
| Trial name        | Study II to Evaluate the Safety and Tolerability of CC-94676 in Participants With Metastatic Castration-Resistant Prostate Cancer                                             |
| Trial description | The purpose of this study is to assess the safety, tolerability and preliminary efficacy of CC-94676 in men with progressive metastatic castration resistant prostate cancer. |
| Matching score    | No match                                                                                                                                                                      |
| Phase             | 1                                                                                                                                                                             |
| Status            | Recruiting                                                                                                                                                                    |
| Trial_ID          | NCT04428789                                                                                                                                                                   |
And the "Filter By" frame contains only selected "Inclusion"
And the criteria table is sorted by "Status" column which has an "up" pointing arrow

@SRS-68586.04
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68586,SysRS-103346
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Store_View_State
Scenario: Medication history settings are saved for user on patient level
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
When the user navigates to the "Medical history" view
Then the "Medications" section is available in Medical history
And the active medications table content is sorted by "Prescribed" column which has a "downward" pointing arrow
When the user clicks on the "Medication name" column header in the Medications table
Then the active medications table content is sorted by "Medication name" column which has a "upward" pointing arrow
When the user clicks on the "Type" column header in the Allergies and intolerances table
Then the Allergies and intolerances table content is sorted by "Type" column which has a "upward" pointing arrow
When the user clicks on the "Type" column header in the Allergies and intolerances table
Then the Allergies and intolerances table content is sorted by "Type" column which has a "downward" pointing arrow
When the user clicks on the "Date" column header in the Surgical History table
Then the Surgical history table content in Patient History is sorted by "Date" column which has an "upward" pointing arrow
When the user selects the "OncoTestPatient, Torvald" patient
And the user navigates to the "Medical history" view
Then the "Medications" section is available in Medical history
And the active medications table content is sorted by "Prescribed" column which has a "downward" pointing arrow
When the user clicks on the "Dosage" column header in the Medications table
Then the active medications table content is sorted by "Dosage" column which has a "upward" pointing arrow
When the user clicks on the "Surgery name" column header in the Surgical History table
Then the Surgical history table content in Patient History is sorted by "Surgery name" column which has an "upward" pointing arrow
When the user selects the "OncoTestPatient, Juno" patient (without reseting patient settings)
And the user navigates to the "Medical history" view
Then the "Medications" section is available in Medical history
And the active medications table content is sorted by "Medication name" column which has a "upward" pointing arrow
And the Allergies and intolerances table content is sorted by "Type" column which has a "downward" pointing arrow
And the Surgical history table content in Patient History is sorted by "Date" column which has an "upward" pointing arrow
When the user logs out from OncoCare
And the [TEST-USER-2] user logs in to OncoCare
And the user selects the "OncoTestPatient, Juno" patient (without reseting patient settings)
And the user navigates to the "Medical history" view
Then the active medications table content is sorted by "Prescribed" column which has a "downward" pointing arrow
And the Allergies and intolerances table content is sorted by "Observation date" column which has a "downward" pointing arrow
And the Surgical history table content in Patient History is sorted by "Date" column which has an "downward" pointing arrow

@SRS-68586.05
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68586,SysRS-103349
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Store_View_State
Scenario: Response view settings are saved for user on patient level
Given [API] the Treatment Response settings were reset for "OncoTestPatient, Sigrid" by the [DEFAULT-TEST-USER] user
And [API] the Treatment Response settings were reset for "OncoTestPatient, Juno" by the [DEFAULT-TEST-USER] user
And [API] the Treatment Response settings were reset for "OncoTestPatient, Sigrid" by the [TEST-USER-2] user
And the [DEFAULT-TEST-USER] user is logged in to CareIntellect for Oncology application
And the "OncoTestPatient, Sigrid" patient was selected
When the user clicks on the "Patient status" button
Then the Treatment and response view is loaded
When the user selects the following imaging study for study A:
| name                      | date         |
| Diagnostic imaging study  | Apr 25, 2010 |
And the user selects the following imaging study for study B:
| name                      | date         |
| Diagnostic imaging study  | Jan 28, 2016 |
And the user selects the following custom date range on the chart slider:
| start        | end          |
| Mar 25, 2010 | Jun 30, 2018 |
Then the X-axis (date) is displaying values in the following range:
| start_date    | end_date      |
| Mar 25, 2010  | Jun 30, 2018  |
When the user clicks on the "Trends" button
And the user unselects the following trend from the list: "Body Weight (kg)"
And the user closes the Trends modal
Then the following lines are present on the chart area:
| name                                                  | unit    |
| Prostate specific Ag [Mass/volume] in Pleural fluid   |  %      |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | ng/mL   |
| Prostate specific Ag [Moles/volume] in Plasma         | umol/L  |
When the user selects the "OncoTestPatient, Juno" patient
And the user clicks on the "Patient status" button
Then the Treatment and response view is loaded
When the user selects the following imaging study for study A:
| name                      | date         |
| Diagnostic imaging study  | Aug 19, 2022 |
And the user clicks "Last 6 months" button on the multiline chart
Then the X-axis (date) is displaying values in the following range:
| start_date     | end_date |
| <184_DAYS_AGO> | Today    |
When the user clicks on the "Trends" button
And the user unselects the following trend from the list: "Prostate specific antigen (ng/mL)"
And the user closes the Trends modal
Then the following legend is present on the chart area:
| name        | unit  |
| Body Weight | kg    |
When the user selects the "OncoTestPatient, Sigrid" patient
And the user clicks on the "Patient status" button
Then the Treatment and response view is loaded
And the following imaging study is displayed for A:
| name                      | date         |
| Diagnostic imaging study  | Apr 25, 2010 |
And the following imaging study is displayed for B:
| name                      | date         |
| Diagnostic imaging study  | Jan 28, 2016 |
And the X-axis (date) is displaying values in the following range:
| start_date    | end_date      |
| Mar 25, 2010  | Jun 30, 2018  |
And the following lines are present on the chart area:
| name                                                  | unit    |
| Prostate specific Ag [Mass/volume] in Pleural fluid   |  %      |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | ng/mL   |
| Prostate specific Ag [Moles/volume] in Plasma         | umol/L  |
When the user logs out from CareIntellect for Oncology application
And the [TEST-USER-2] user logs in to CareIntellect for Oncology application
And the user selects the "OncoTestPatient, Sigrid" patient
And the user clicks on the "Patient status" button
Then the Treatment and response view is loaded
And the following imaging study is displayed for A:
| name                      | date         |
| Diagnostic imaging study  | Jan 30, 2016 |
And the following imaging study is displayed for B:
| name                                    | date         |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST  | Mar 11, 2016 |
And the X-axis (date) is displaying values in the following range:
| start_date     | end_date |
| <184_DAYS_AGO> | Today    |
And the following lines are present on the chart area:
| name                                                  | unit    |
| Body Weight                                           | kg      |
| Prostate specific Ag [Mass/volume] in Pleural fluid   |  %      |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | ng/mL   |
| Prostate specific Ag [Moles/volume] in Plasma         | umol/L  |