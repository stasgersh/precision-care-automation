Feature: [SysRS-68490] Card Group Rearrangement And Visibility

Narrative: The system shall allow the User to configure Summary dashboard view with the following:
             - Card groups' view order - Columns content (which card shall be presented in which column).
             - Show/hide card groups.
             - Show/Hide cards with empty data.


@sanity
@edge
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68490,SysRS-69194
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
Scenario: User changes the position and visibility of summary card groups
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Summary" view
And the Summary view contains the following groups:
| column_index | group_index_in_column | group_name        | badge_text  |
| 0            | 0                     | Diagnosis         | <N/A>       |
| 0            | 1                     | Molecular profile | <N/A>       |
| 0            | 2                     | Care team         | <N/A>       |
| 1            | 0                     | Imaging           | Most recent |
| 1            | 1                     | Oncology note     | Most recent |
| 1            | 2                     | ER visit          | Most recent |
| 1            | 3                     | MDT report        | Most recent |
| 2            | 0                     | Treatments        | <N/A>       |
| 2            | 1                     | Labs              | <N/A>       |
| 2            | 2                     | Medical history   | <N/A>       |
| 2            | 3                     | Comments          | Most recent |
And no badge is displayed on the Navigation toolbar
When the user clicks the "More" button on the Navigation toolbar
And the user clicks on the "Edit order and visibility" option in the View options
Then the "Summary order and visibility" modal is displayed
And the following columns are available on the "Summary order and visibility" modal:
| column_name |
| Column 1    |
| Column 2    |
| Column 3    |
And the "Summary order and visibility" modal contains the following groups:
| column_index | group_index_in_column | group_name        | hidden |
| 0            | 0                     | Diagnosis         | no     |
| 0            | 1                     | Molecular profile | no     |
| 0            | 2                     | Care team         | no     |
| 1            | 0                     | Imaging           | no     |
| 1            | 1                     | Oncology note     | no     |
| 1            | 2                     | ER visit          | no     |
| 1            | 3                     | MDT report        | no     |
| 2            | 0                     | Treatments        | no     |
| 2            | 1                     | Labs              | no     |
| 2            | 2                     | Medical history   | no     |
| 2            | 3                     | Comments          | no     |
When the user sets the following group settings on the "Summary order and visibility" modal:
| column_index | group_index_in_column | group_name        | hidden |
| 0            | 0                     | Treatments        | no     |
| 0            | 1                     | ER visit          | yes    |
| 0            | 2                     | Care team         | no     |
| 1            | 0                     | Imaging           | yes    |
| 1            | 1                     | Comments          | no     |
| 1            | 2                     | Diagnosis         | no     |
| 1            | 3                     | Molecular profile | no     |
| 2            | 0                     | Medical history   | yes    |
| 2            | 1                     | Labs              | no     |
| 2            | 2                     | Oncology note     | no     |
| 2            | 3                     | MDT report        | yes    |
And the user clicks on the "Save" button on the "Summary order and visibility" modal
Then the "Summary order and visibility" modal disappears
And the patient summary view is loaded
And the Summary view contains the following groups:
| column_index | group_index_in_column | group_name        | badge_text  |
| 0            | 0                     | Treatments        | <N/A>       |
| 0            | 1                     | Care team         | <N/A>       |
| 1            | 0                     | Comments          | Most recent |
| 1            | 1                     | Diagnosis         | <N/A>       |
| 1            | 2                     | Molecular profile | <N/A>       |
| 2            | 0                     | Labs              | <N/A>       |
| 2            | 1                     | Oncology note     | Most recent |
And the following badge is displayed on the Navigation toolbar:
| badge_type  | badge_text        | badge_color     |
| INFORMATION | Some cards hidden | light blue-gray |

@sanity
@edge
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68490,SysRS-69202
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
Scenario: User can reset the summary card group order and visibility settings to the default one
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Juno" patient is selected
And [API] the following summary layout was set for "OncoTestPatient, Juno" by [DEFAULT-TEST-USER]: summary/layout/layout_01.json
And the user navigated to the "Summary" view
And the Summary view contains the following groups:
| column_index | group_index_in_column | group_name        | badge_text  |
| 0            | 0                     | Imaging           | Most recent |
| 0            | 1                     | Care team         | <N/A>       |
| 1            | 0                     | Comments          | Most recent |
| 1            | 1                     | Treatments        | <N/A>       |
| 1            | 2                     | Labs              | <N/A>       |
| 2            | 0                     | ER visit          | Most recent |
| 2            | 1                     | Diagnosis         | <N/A>       |
| 2            | 2                     | Medical history   | <N/A>       |
And the following badge is displayed on the Navigation toolbar:
| badge_type  | badge_text        | badge_color     |
| INFORMATION | Some cards hidden | light blue-gray |
When the user navigates to the "Edit order and visibility" from the "More" menu
Then the "Summary order and visibility" modal contains the following groups:
| column_index | group_index_in_column | group_name        | hidden |
| 0            | 0                     | Imaging           | no     |
| 0            | 1                     | Care team         | no     |
| 1            | 0                     | Comments          | no     |
| 1            | 1                     | Treatments        | no     |
| 1            | 2                     | Oncology note     | yes    |
| 1            | 3                     | Labs              | no     |
| 2            | 0                     | ER visit          | no     |
| 2            | 1                     | Diagnosis         | no     |
| 2            | 2                     | Molecular profile | yes    |
| 2            | 3                     | MDT report        | yes    |
| 2            | 4                     | Medical history   | no     |
When the user clicks on the "Reset to default" button on the "Summary order and visibility" modal
Then the "Summary order and visibility" modal contains the following groups:
| column_index | group_index_in_column | group_name        | hidden |
| 0            | 0                     | Diagnosis         | no     |
| 0            | 1                     | Molecular profile | no     |
| 0            | 2                     | Care team         | no     |
| 1            | 0                     | Imaging           | no     |
| 1            | 1                     | Oncology note     | no     |
| 1            | 2                     | ER visit          | no     |
| 1            | 3                     | MDT report        | no     |
| 2            | 0                     | Treatments        | no     |
| 2            | 1                     | Labs              | no     |
| 2            | 2                     | Medical history   | no     |
| 2            | 3                     | Comments          | no     |
When the user clicks on the "Save" button on the "Summary order and visibility" modal
Then the "Summary order and visibility" modal disappears
And the patient summary view is loaded
And the Summary view contains the following groups:
| column_index | group_index_in_column | group_name        | badge_text  |
| 0            | 0                     | Diagnosis         | <N/A>       |
| 0            | 1                     | Molecular profile | <N/A>       |
| 0            | 2                     | Care team         | <N/A>       |
| 1            | 0                     | Imaging           | Most recent |
| 1            | 1                     | Oncology note     | Most recent |
| 1            | 2                     | ER visit          | Most recent |
| 1            | 3                     | MDT report        | Most recent |
| 2            | 0                     | Treatments        | <N/A>       |
| 2            | 1                     | Labs              | <N/A>       |
| 2            | 2                     | Medical history   | <N/A>       |
| 2            | 3                     | Comments          | Most recent |
And no badge is displayed on the Navigation toolbar

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68490,SysRS-69202
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: User can reset the summary card group order and visibility settings while editing them
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Juno" patient is selected
And [API] the following summary layout was set for "OncoTestPatient, Juno" by [DEFAULT-TEST-USER]: summary/layout/layout_02.json
And the user navigated to the "Summary" view
And the Summary view contains the following groups:
| column_index | group_index_in_column | group_name        | badge_text  |
| 0            | 0                     | Diagnosis         | <N/A>       |
| 0            | 1                     | Imaging           | Most recent |
| 0            | 2                     | Oncology note     | Most recent |
| 0            | 3                     | ER visit          | Most recent |
| 0            | 4                     | Molecular profile | <N/A>       |
| 0            | 5                     | Care team         | <N/A>       |
| 2            | 0                     | Medical history   | <N/A>       |
| 2            | 1                     | Treatments        | <N/A>       |
| 2            | 2                     | Labs              | <N/A>       |
When the user navigates to the "Edit order and visibility" from the "More" menu
Then the "Summary order and visibility" modal contains the following groups:
| column_index | group_index_in_column | group_name        | hidden |
| 0            | 0                     | Diagnosis         | no     |
| 0            | 1                     | Imaging           | no     |
| 0            | 2                     | Oncology note     | no     |
| 0            | 3                     | ER visit          | no     |
| 0            | 4                     | Molecular profile | no     |
| 0            | 5                     | Care team         | no     |
| 1            | 0                     | MDT report        | yes    |
| 2            | 0                     | Medical history   | no     |
| 2            | 1                     | Treatments        | no     |
| 2            | 2                     | Labs              | no     |
| 2            | 3                     | Comments          | yes    |
When the user sets the following group settings on the "Summary order and visibility" modal:
| column_index | group_index_in_column | group_name        | hidden |
| 0            | 0                     | Imaging           | no     |
| 0            | 1                     | ER visit          | no     |
| 0            | 2                     | Oncology note     | no     |
| 0            | 3                     | Diagnosis         | yes    |
| 1            | 0                     | Labs              | no     |
| 1            | 1                     | Molecular profile | no     |
| 2            | 0                     | Care team         | no     |
| 2            | 1                     | MDT report        | yes    |
| 2            | 2                     | Medical history   | no     |
| 2            | 3                     | Treatments        | yes    |
| 2            | 4                     | Comments          | no     |
And the user clicks on the "Reset to default" button on the "Summary order and visibility" modal
Then the "Summary order and visibility" modal contains the following groups:
| column_index | group_index_in_column | group_name        | hidden |
| 0            | 0                     | Diagnosis         | no     |
| 0            | 1                     | Molecular profile | no     |
| 0            | 2                     | Care team         | no     |
| 1            | 0                     | Imaging           | no     |
| 1            | 1                     | Oncology note     | no     |
| 1            | 2                     | ER visit          | no     |
| 1            | 3                     | MDT report        | no     |
| 2            | 0                     | Treatments        | no     |
| 2            | 1                     | Labs              | no     |
| 2            | 2                     | Medical history   | no     |
| 2            | 3                     | Comments          | no     |
When the user sets the following group settings on the "Summary order and visibility" modal:
| column_index | group_index_in_column | group_name        | hidden |
| 0            | 0                     | Diagnosis         | no     |
| 0            | 1                     | Treatments        | no     |
| 1            | 0                     | Molecular profile | no     |
| 1            | 1                     | Care team         | yes    |
| 1            | 2                     | Imaging           | no     |
| 1            | 3                     | Oncology note     | no     |
| 2            | 0                     | Labs              | no     |
| 2            | 1                     | Medical history   | no     |
| 2            | 2                     | Comments          | no     |
| 2            | 3                     | ER visit          | no     |
| 2            | 4                     | MDT report        | no     |
And the user clicks on the "Reset to default" button on the "Summary order and visibility" modal
Then the "Summary order and visibility" modal contains the following groups:
| column_index | group_index_in_column | group_name        | hidden |
| 0            | 0                     | Diagnosis         | no     |
| 0            | 1                     | Molecular profile | no     |
| 0            | 2                     | Care team         | no     |
| 1            | 0                     | Imaging           | no     |
| 1            | 1                     | Oncology note     | no     |
| 1            | 2                     | ER visit          | no     |
| 1            | 3                     | MDT report        | no     |
| 2            | 0                     | Treatments        | no     |
| 2            | 1                     | Labs              | no     |
| 2            | 2                     | Medical history   | no     |
| 2            | 3                     | Comments          | no     |

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68490
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Cancel editing the summary card group order and visibility settings
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Juno" patient is selected
And [API] the following summary layout was set for "OncoTestPatient, Juno" by [DEFAULT-TEST-USER]: summary/layout/layout_01.json
And the user navigated to the "Summary" view
And the Summary view contains the following groups:
| column_index | group_index_in_column | group_name        | badge_text  |
| 0            | 0                     | Imaging           | Most recent |
| 0            | 1                     | Care team         | <N/A>       |
| 1            | 0                     | Comments          | Most recent |
| 1            | 1                     | Treatments        | <N/A>       |
| 1            | 2                     | Labs              | <N/A>       |
| 2            | 0                     | ER visit          | Most recent |
| 2            | 1                     | Diagnosis         | <N/A>       |
| 2            | 2                     | Medical history   | <N/A>       |
And the following badge is displayed on the Navigation toolbar:
| badge_type  | badge_text        | badge_color     |
| INFORMATION | Some cards hidden | light blue-gray |
When the user navigates to the "Edit order and visibility" from the "More" menu
Then the "Summary order and visibility" modal contains the following groups:
| column_index | group_index_in_column | group_name        | hidden |
| 0            | 0                     | Imaging           | no     |
| 0            | 1                     | Care team         | no     |
| 1            | 0                     | Comments          | no     |
| 1            | 1                     | Treatments        | no     |
| 1            | 2                     | Oncology note     | yes    |
| 1            | 3                     | Labs              | no     |
| 2            | 0                     | ER visit          | no     |
| 2            | 1                     | Diagnosis         | no     |
| 2            | 2                     | Molecular profile | yes    |
| 2            | 3                     | MDT report        | yes    |
| 2            | 4                     | Medical history   | no     |
When the user clicks on the "Reset to default" button on the "Summary order and visibility" modal
Then the "Summary order and visibility" modal contains the following groups:
| column_index | group_index_in_column | group_name        | hidden |
| 0            | 0                     | Diagnosis         | no     |
| 0            | 1                     | Molecular profile | no     |
| 0            | 2                     | Care team         | no     |
| 1            | 0                     | Imaging           | no     |
| 1            | 1                     | Oncology note     | no     |
| 1            | 2                     | ER visit          | no     |
| 1            | 3                     | MDT report        | no     |
| 2            | 0                     | Treatments        | no     |
| 2            | 1                     | Labs              | no     |
| 2            | 2                     | Medical history   | no     |
| 2            | 3                     | Comments          | no     |
When the user clicks on the "Cancel" button on the "Summary order and visibility" modal
Then the "Summary order and visibility" modal disappears
And the patient summary view is loaded
And the Summary view contains the following groups:
| column_index | group_index_in_column | group_name        | badge_text  |
| 0            | 0                     | Imaging           | Most recent |
| 0            | 1                     | Care team         | <N/A>       |
| 1            | 0                     | Comments          | Most recent |
| 1            | 1                     | Treatments        | <N/A>       |
| 1            | 2                     | Labs              | <N/A>       |
| 2            | 0                     | ER visit          | Most recent |
| 2            | 1                     | Diagnosis         | <N/A>       |
| 2            | 2                     | Medical history   | <N/A>       |
And the following badge is displayed on the Navigation toolbar:
| badge_type  | badge_text        | badge_color     |
| INFORMATION | Some cards hidden | light blue-gray |

@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68490,SysRS-69194
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
Scenario Outline: All summary view columns can contain zero or more card groups
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Summary" view
And the Summary view contains the following groups:
| column_index | group_index_in_column | group_name        | badge_text  |
| 0            | 0                     | Diagnosis         | <N/A>       |
| 0            | 1                     | Molecular profile | <N/A>       |
| 0            | 2                     | Care team         | <N/A>       |
| 1            | 0                     | Imaging           | Most recent |
| 1            | 1                     | Oncology note     | Most recent |
| 1            | 2                     | ER visit          | Most recent |
| 1            | 3                     | MDT report        | Most recent |
| 2            | 0                     | Treatments        | <N/A>       |
| 2            | 1                     | Labs              | <N/A>       |
| 2            | 2                     | Medical history   | <N/A>       |
| 2            | 3                     | Comments          | Most recent |
And no badge is displayed on the Navigation toolbar
When the user clicks the "More" button on the Navigation toolbar
And the user clicks on the "Edit order and visibility" option in the View options
Then the "Summary order and visibility" modal is displayed
When the user sets the following group settings on the "Summary order and visibility" modal:
| column_index     | group_index_in_column | group_name        | hidden |
| <column_index_0> | 0                     | ER visit          | no     |
| <column_index_0> | 1                     | Care team         | yes    |
| <column_index_0> | 2                     | Imaging           | no     |
| <column_index_0> | 3                     | Comments          | no     |
| <column_index_1> | 0                     | Diagnosis         | no     |
| <column_index_1> | 1                     | Treatments        | no     |
| <column_index_1> | 2                     | Molecular profile | no     |
| <column_index_1> | 3                     | Medical history   | no     |
| <column_index_1> | 4                     | Labs              | no     |
| <column_index_1> | 5                     | Oncology note     | no     |
| <column_index_1> | 6                     | MDT report        | yes    |
And the user clicks on the "Save" button on the "Summary order and visibility" modal
Then the "Summary order and visibility" modal disappears
And the patient summary view is loaded
And the Summary view contains the following groups:
| column_index     | group_index_in_column | group_name        | badge_text  |
| <column_index_0> | 0                     | ER visit          | Most recent |
| <column_index_0> | 1                     | Imaging           | Most recent |
| <column_index_0> | 2                     | Comments          | Most recent |
| <column_index_1> | 0                     | Diagnosis         | <N/A>       |
| <column_index_1> | 1                     | Treatments        | <N/A>       |
| <column_index_1> | 2                     | Molecular profile | <N/A>       |
| <column_index_1> | 3                     | Medical history   | <N/A>       |
| <column_index_1> | 4                     | Labs              | <N/A>       |
| <column_index_1> | 5                     | Oncology note     | Most recent |
And the following badge is displayed on the Navigation toolbar:
| badge_type  | badge_text        | badge_color     |
| INFORMATION | Some cards hidden | light blue-gray |
Examples:
| example_description              | column_index_0 | column_index_1 |
| Summary groups in column 0 and 2 | 0              | 2              |
| Summary groups in column 0 and 1 | 0              | 1              |
| Summary groups in column 1 and 2 | 1              | 2              |

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68490,SysRS-69194
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario Outline: User can move all summary card groups into one column
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Summary" view
And the Summary view contains the following groups:
| column_index | group_index_in_column | group_name        | badge_text  |
| 0            | 0                     | Diagnosis         | <N/A>       |
| 0            | 1                     | Molecular profile | <N/A>       |
| 0            | 2                     | Care team         | <N/A>       |
| 1            | 0                     | Imaging           | Most recent |
| 1            | 1                     | Oncology note     | Most recent |
| 1            | 2                     | ER visit          | Most recent |
| 1            | 3                     | MDT report        | Most recent |
| 2            | 0                     | Treatments        | <N/A>       |
| 2            | 1                     | Labs              | <N/A>       |
| 2            | 2                     | Medical history   | <N/A>       |
| 2            | 3                     | Comments          | Most recent |
And no badge is displayed on the Navigation toolbar
When the user clicks the "More" button on the Navigation toolbar
And the user clicks on the "Edit order and visibility" option in the View options
Then the "Summary order and visibility" modal is displayed
When the user sets the following group settings on the "Summary order and visibility" modal:
| column_index | group_index_in_column | group_name        | hidden |
| 1            | 0                     | Molecular profile | no     |
| 1            | 1                     | Care team         | no     |
| 1            | 2                     | Imaging           | no     |
| 1            | 3                     | Treatments        | no     |
| 1            | 4                     | ER visit          | no     |
| 1            | 5                     | Medical history   | no     |
| 1            | 6                     | Comments          | no     |
| 1            | 7                     | Diagnosis         | no     |
| 1            | 8                     | Labs              | no     |
| 1            | 9                     | Oncology note     | no     |
| 1            | 10                    | MDT report        | no     |
And the user clicks on the "Save" button on the "Summary order and visibility" modal
Then the "Summary order and visibility" modal disappears
And the patient summary view is loaded
And the Summary view contains the following groups:
| column_index | group_index_in_column | group_name        | badge_text  |
| 1            | 0                     | Molecular profile | <N/A>       |
| 1            | 1                     | Care team         | <N/A>       |
| 1            | 2                     | Imaging           | Most recent |
| 1            | 3                     | Treatments        | <N/A>       |
| 1            | 4                     | ER visit          | Most recent |
| 1            | 5                     | Medical history   | <N/A>       |
| 1            | 6                     | Comments          | Most recent |
| 1            | 7                     | Diagnosis         | <N/A>       |
| 1            | 8                     | Labs              | <N/A>       |
| 1            | 9                     | Oncology note     | Most recent |
| 1            | 10                    | MDT report        | Most recent |
And no badge is displayed on the Navigation toolbar

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68490,SysRS-68480
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Treatments group and buttons in it remains visible when user hides all summary card groups
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Summary" view
And no badge is displayed on the Navigation toolbar
When the user navigates to the "Edit order and visibility" from the "More" menu
Then the "Summary order and visibility" modal is displayed
When the user sets the following group settings on the "Summary order and visibility" modal:
| column_index | group_index_in_column | group_name        | hidden |
| 0            | 0                     | Diagnosis         | yes    |
| 0            | 1                     | Molecular profile | yes    |
| 0            | 2                     | Care team         | yes    |
| 1            | 0                     | Imaging           | yes    |
| 1            | 1                     | Oncology note     | yes    |
| 1            | 2                     | ER visit          | yes    |
| 1            | 3                     | MDT report        | yes    |
| 2            | 0                     | Treatments        | yes    |
| 2            | 1                     | Labs              | yes    |
| 2            | 2                     | Medical history   | yes    |
| 2            | 3                     | Comments          | yes    |
And the user clicks on the "Save" button on the "Summary order and visibility" modal
Then the "Summary order and visibility" modal disappears
And the patient summary view is loaded
And the following badge is displayed on the Navigation toolbar:
| badge_type  | badge_text        | badge_color     |
| INFORMATION | Some cards hidden | light blue-gray |
Then the Summary view contains the following groups:
| column_index | group_index_in_column | group_name | badge_text  |
| 2            | 0                     | Treatments | <N/A>       |
When the user clicks "Status and response" button
Then the Treatment and response view is loaded
When the user navigates to the "Summary" view
And the user clicks "Clinical trials" button
Then the Trials view is displayed

@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68490,SysRS-68491,SysRS-103344
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
Scenario: Summary card group order and visibility is applied for user on patient level
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Juno" patient is selected
And [API] the following summary layout was set for "OncoTestPatient, Juno" by [DEFAULT-TEST-USER]: summary/layout/layout_01.json
And the user navigated to the "Summary" view
And the Summary view contains the following groups:
| column_index | group_index_in_column | group_name        | badge_text  |
| 0            | 0                     | Imaging           | Most recent |
| 0            | 1                     | Care team         | <N/A>       |
| 1            | 0                     | Comments          | Most recent |
| 1            | 1                     | Treatments        | <N/A>       |
| 1            | 2                     | Labs              | <N/A>       |
| 2            | 0                     | ER visit          | Most recent |
| 2            | 1                     | Diagnosis         | <N/A>       |
| 2            | 2                     | Medical history   | <N/A>       |
When the user navigates to the "Edit order and visibility" from the "More" menu
Then the "Summary order and visibility" modal contains the following groups:
| column_index | group_index_in_column | group_name        | hidden |
| 0            | 0                     | Imaging           | no     |
| 0            | 1                     | Care team         | no     |
| 1            | 0                     | Comments          | no     |
| 1            | 1                     | Treatments        | no     |
| 1            | 2                     | Oncology note     | yes    |
| 1            | 3                     | Labs              | no     |
| 2            | 0                     | ER visit          | no     |
| 2            | 1                     | Diagnosis         | no     |
| 2            | 2                     | Molecular profile | yes    |
| 2            | 3                     | MDT report        | yes    |
| 2            | 4                     | Medical history   | no     |
When the user sets the following group settings on the "Summary order and visibility" modal:
| column_index | group_index_in_column | group_name        | hidden |
| 0            | 0                     | Imaging           | no     |
| 0            | 1                     | Care team         | yes    |
| 0            | 2                     | Comments          | no     |
| 1            | 0                     | Labs              | no     |
| 1            | 1                     | ER visit          | no     |
| 1            | 2                     | Medical history   | no     |
| 1            | 3                     | Treatments        | no     |
| 1            | 4                     | Diagnosis         | no     |
| 2            | 0                     | Molecular profile | yes    |
| 2            | 1                     | Oncology note     | yes    |
| 2            | 2                     | MDT report        | yes    |
And the user clicks on the "Save" button on the "Summary order and visibility" modal
Then the "Summary order and visibility" modal disappears
And the patient summary view is loaded
And the Summary view contains the following groups:
| column_index | group_index_in_column | group_name        | badge_text  |
| 0            | 0                     | Imaging           | Most recent |
| 0            | 1                     | Comments          | Most recent |
| 1            | 0                     | Labs              | <N/A>       |
| 1            | 1                     | ER visit          | Most recent |
| 1            | 2                     | Medical history   | <N/A>       |
| 1            | 3                     | Treatments        | <N/A>       |
| 1            | 4                     | Diagnosis         | <N/A>       |
When the user selects the "OncoTestPatient, Freya" patient (without reseting patient settings)
And [API] the following summary layout is set for "OncoTestPatient, Freya" by [DEFAULT-TEST-USER]: summary/layout/layout_02.json
And the user navigates to the "Summary" view
Then the Summary view contains the following groups:
| column_index | group_index_in_column | group_name        | badge_text  |
| 0            | 0                     | Diagnosis         | <N/A>       |
| 0            | 1                     | Imaging           | Most recent |
| 0            | 2                     | Oncology note     | Most recent |
| 0            | 3                     | ER visit          | Most recent |
| 0            | 4                     | Molecular profile | <N/A>       |
| 0            | 5                     | Care team         | <N/A>       |
| 2            | 0                     | Medical history   | <N/A>       |
| 2            | 1                     | Treatments        | <N/A>       |
| 2            | 2                     | Labs              | <N/A>       |
When the user selects the "OncoTestPatient, Juno" patient (without reseting patient settings)
Then the Summary view contains the following groups:
| column_index | group_index_in_column | group_name        | badge_text  |
| 0            | 0                     | Imaging           | Most recent |
| 0            | 1                     | Comments          | Most recent |
| 1            | 0                     | Labs              | <N/A>       |
| 1            | 1                     | ER visit          | Most recent |
| 1            | 2                     | Medical history   | <N/A>       |
| 1            | 3                     | Treatments        | <N/A>       |
| 1            | 4                     | Diagnosis         | <N/A>       |
When the user logs out from OncoCare
And the [TEST-USER-2] user logs in to OncoCare
And the user selects the "OncoTestPatient, Freya" patient (without reseting patient settings)
Then the Summary view contains the following groups:
| column_index | group_index_in_column | group_name        | badge_text  |
| 0            | 0                     | Diagnosis         | <N/A>       |
| 0            | 1                     | Molecular profile | <N/A>       |
| 0            | 2                     | Care team         | <N/A>       |
| 1            | 0                     | Imaging           | Most recent |
| 1            | 1                     | Oncology note     | Most recent |
| 1            | 2                     | ER visit          | Most recent |
| 1            | 3                     | MDT report        | Most recent |
| 2            | 0                     | Treatments        | <N/A>       |
| 2            | 1                     | Labs              | <N/A>       |
| 2            | 2                     | Medical history   | <N/A>       |
| 2            | 3                     | Comments          | Most recent |

@srs
@manual
@Platform:WEB
@Category:Negative
@Traceability:SysRS-68490
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Summary_View
Scenario: Error popup is displayed if summary card group order and visibility cannot be saved
Given the following summary layout was set previously for the user [DEFAULT-TEST-USER]:
| column_index | group_index_in_column | group_name        | badge_text  |
| 0            | 0                     | Comments          | Most recent |
| 0            | 1                     | Imaging           | Most recent |
| 1            | 0                     | Diagnosis         | <N/A>       |
| 1            | 1                     | Molecular profile | <N/A>       |
| 1            | 2                     | Care team         | <N/A>       |
| 2            | 0                     | Treatments        | <N/A>       |
| 2            | 1                     | Oncology note     | Most recent |
| 2            | 2                     | ER visit          | Most recent |
| 2            | 3                     | MDT report        | Most recent |
| 2            | 4                     | Labs              | <N/A>       |
| 2            | 5                     | Medical history   | <N/A>       |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Torvald" patient is selected
And the user navigated to the "Summary" view
And the user clicked on the "More" button on the Navigation toolbar
And the user clicked on the "Edit order and visibility" option in the View options
And the "Summary order and visibility" modal is displayed
When the user clicks on the "Reset to default" button on the "Summary order and visibility" modal
And the user sets the following group settings on the "Summary order and visibility" modal:
| column_index | group_index_in_column | group_name        | hidden |
| 0            | 0                     | Treatments        | no     |
| 0            | 1                     | Diagnosis         | no     |
| 0            | 2                     | Molecular profile | no     |
| 0            | 3                     | Care team         | no     |
| 1            | 0                     | Imaging           | no     |
| 1            | 1                     | Oncology note     | no     |
| 1            | 2                     | ER visit          | no     |
| 1            | 3                     | MDT report        | no     |
| 2            | 0                     | Labs              | no     |
| 2            | 1                     | Medical history   | no     |
| 2            | 2                     | Comments          | yes    |
And the user opens the Dev tools and navigate to the Network tab
And [DEVTOOLS] - the following text pattern was added to "Network request blocking" tool:
| text pattern |
| settings     |
And [DEVTOOLS] - the "Enable network request blocking" checkbox is CHECKED in the "Network request blocking" tool
And the user clicks on the "Save" button on the "Summary order and visibility" modal
Then the "Summary order and visibility" modal disappears
And an error popup is displayed with the following message:
| error_title             | detailed_error_text                     |
| Failed to save Settings | Sorry, something went wrong on our end. |
And [DEVTOOLS] - the user unchecks "Enable network request blocking" checkbox
And the Summary view contains the following groups:
| column_index | group_index_in_column | group_name        | badge_text  |
| 0            | 0                     | Comments          | Most recent |
| 0            | 1                     | Imaging           | Most recent |
| 1            | 0                     | Diagnosis         | <N/A>       |
| 1            | 1                     | Molecular profile | <N/A>       |
| 1            | 2                     | Care team         | <N/A>       |
| 2            | 0                     | Treatments        | <N/A>       |
| 2            | 1                     | Oncology note     | Most recent |
| 2            | 2                     | ER visit          | Most recent |
| 2            | 3                     | MDT report        | Most recent |
| 2            | 4                     | Labs              | <N/A>       |
| 2            | 5                     | Medical history   | <N/A>       |

@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68490
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
Scenario: Hide empty cards on summary view - if the group contains only empty card(s) then the group title also hidden
Given [API] the [DEFAULT-TEST-USER] user has no comments for the following patients:
| patientID                            |
| 34e06220-11fd-a9ce-606d-98f572481f3e |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Pablo44" patient is selected
And the user navigated to the "Summary" view
And the patient summary view is loaded
And no badge is displayed on the Navigation toolbar
When the user selects the "Hide empty cards" checkbox in View options
Then the following badge is displayed on the Navigation toolbar:
| badge_type  | badge_text        | badge_color     |
| INFORMATION | Some cards hidden | light blue-gray |
And the Summary view contains the following groups:
| column_index | group_index_in_column | group_name      | badge_text  |
| 0            | 0                     | Diagnosis       | <N/A>       |
| 1            | 0                     | Imaging         | Most recent |
| 1            | 1                     | Oncology note   | Most recent |
| 1            | 2                     | ER visit        | Most recent |
| 2            | 0                     | Treatments      | <N/A>       |
| 2            | 1                     | Labs            | Lab history |
| 2            | 2                     | Medical history | <N/A>       |
And the visible cards are not empty on the Summary view

@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68490,SysRS-69202
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
Scenario: Show empty cards on summary view
Given [API] the [DEFAULT-TEST-USER] user has no comments for the following patients:
| patientID                            |
| 34e06220-11fd-a9ce-606d-98f572481f3e |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Pablo44" patient is selected
And [API] the empty cards are hidden on Summary view for "OncoTestPatient, Pablo44" by the [DEFAULT-TEST-USER] user
And the user navigated to the "Summary" view
And the patient summary view is loaded
And the following badge is displayed on the Navigation toolbar:
| badge_type  | badge_text        | badge_color     |
| INFORMATION | Some cards hidden | light blue-gray |
When the user unselects the "Hide empty cards" checkbox in View options
Then the patient summary view is loaded
And the Summary view contains the following groups:
| column_index | group_index_in_column | group_name        | badge_text  |
| 0            | 0                     | Diagnosis         | <N/A>       |
| 0            | 1                     | Molecular profile | <N/A>       |
| 0            | 2                     | Care team         | <N/A>       |
| 1            | 0                     | Imaging           | Most recent |
| 1            | 1                     | Oncology note     | Most recent |
| 1            | 2                     | ER visit          | Most recent |
| 1            | 3                     | MDT report        | Most recent |
| 2            | 0                     | Treatments        | <N/A>       |
| 2            | 1                     | Labs              | <N/A>       |
| 2            | 2                     | Medical history   | <N/A>       |
| 2            | 3                     | Comments          | Most recent |
And no badge is displayed on the Navigation toolbar
And the "Diagnosis" group has 3 cards
And the 1st card in "Diagnosis" group is empty
And the 2nd card in "Diagnosis" group is empty
And the 3rd card in "Diagnosis" group is not empty
And the "Molecular profile" group has 1 card
And the 1st card in "Molecular profile" group is empty
And the "Care team" group has 1 card
And the 1st card in "Care team" group is empty
And the "Imaging" group has 2 cards
And the 1st card in "Imaging" group is not empty
And the 2nd card in "Imaging" group is not empty
And the "Oncology note" group has 1 card
And the 1st card in "Oncology note" group is not empty
And the "MDT report" group has 1 card
And the 1st card in "MDT report" group is empty
And the "Treatments" group has 4 cards
And the 1st card in "Treatments" group is not empty
And the 2nd card in "Treatments" group is empty
And the 3rd card in "Treatments" group is not empty
And the "Labs" group has 1 cards
And the 1st card in "Labs" group is not empty
And the "Medical history" group has 1 card
And the 1st card in "Medical history" group is not empty
And the "Comments" group has 1 card
And the 1st card in "Comments" group is empty

@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68490,SysRS-68491,SysRS-103344
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
Scenario: Hide empty cards setting saved for user on patient level
Given [API] the [DEFAULT-TEST-USER] user has no comments for the following patients:
| patientID                            |
| 34e06220-11fd-a9ce-606d-98f572481f3e |
| 05b46423-4e30-47a3-b30d-cd7e9ff31e94 |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Empty" patient
And [API] the empty cards are hidden on Summary view for "OncoTestPatient, Empty" by the [DEFAULT-TEST-USER] user
And the user navigates to the "Summary" view
Then the patient summary view is loaded
And the following badge is displayed on the Navigation toolbar:
| badge_type  | badge_text        | badge_color     |
| INFORMATION | Some cards hidden | light blue-gray |
And the Summary view contains the following groups:
| column_index | group_index_in_column | group_name      | badge_text  |
| 2            | 0                     | Treatments      | <N/A>       |
| 2            | 1                     | Medical history | <N/A>       |
And the visible cards are not empty on the Summary view
When the user clicks the "More" button on the Navigation toolbar
Then the "More" menu is opened
And the "Hide empty cards" checkbox is selected in the View options
When the user selects the "OncoTestPatient, Pablo44" patient (without reseting patient settings)
And [API] the empty cards are not hidden on Summary view for "OncoTestPatient, Pablo44" by the [DEFAULT-TEST-USER] user
And the user navigates to the "Summary" view
Then the patient summary view is loaded
And the Summary view contains the following groups:
| column_index | group_index_in_column | group_name        | badge_text  |
| 0            | 0                     | Diagnosis         | <N/A>       |
| 0            | 1                     | Molecular profile | <N/A>       |
| 0            | 2                     | Care team         | <N/A>       |
| 1            | 0                     | Imaging           | Most recent |
| 1            | 1                     | Oncology note     | Most recent |
| 1            | 2                     | ER visit          | Most recent |
| 1            | 3                     | MDT report        | Most recent |
| 2            | 0                     | Treatments        | <N/A>       |
| 2            | 1                     | Labs              | <N/A>       |
| 2            | 2                     | Medical history   | <N/A>       |
| 2            | 3                     | Comments          | Most recent |
And no badge is displayed on the Navigation toolbar
When the user clicks the "More" button on the Navigation toolbar
Then the "More" menu is opened
And the "Hide empty cards" checkbox is not selected in the View options
When the user selects the "OncoTestPatient, Empty" patient (without reseting patient settings)
Then the following badge is displayed on the Navigation toolbar:
| badge_type  | badge_text        | badge_color     |
| INFORMATION | Some cards hidden | light blue-gray |
And the Summary view contains the following groups:
| column_index | group_index_in_column | group_name      | badge_text  |
| 2            | 0                     | Treatments      | <N/A>       |
| 2            | 1                     | Medical history | <N/A>       |
And the visible cards are not empty on the Summary view
When the user clicks the "More" button on the Navigation toolbar
Then the "More" menu is opened
And the "Hide empty cards" checkbox is selected in the View options
When the user logs out from OncoCare
And the [TEST-USER-2] user logs in to OncoCare
And the user selects the "OncoTestPatient, Empty" patient (without reseting patient settings)
Then the Summary view contains the following groups:
| column_index | group_index_in_column | group_name        | badge_text  |
| 0            | 0                     | Diagnosis         | <N/A>       |
| 0            | 1                     | Molecular profile | <N/A>       |
| 0            | 2                     | Care team         | <N/A>       |
| 1            | 0                     | Imaging           | Most recent |
| 1            | 1                     | Oncology note     | Most recent |
| 1            | 2                     | ER visit          | Most recent |
| 1            | 3                     | MDT report        | Most recent |
| 2            | 0                     | Treatments        | <N/A>       |
| 2            | 1                     | Labs              | <N/A>       |
| 2            | 2                     | Medical history   | <N/A>       |
| 2            | 3                     | Comments          | Most recent |

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68490
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Treatments group and buttons in it remains visible if all cards are empty and hidden
Given [API] the [DEFAULT-TEST-USER] user has no comments for the following patients:
| patientID                            |
| 05b46423-4e30-47a3-b30d-cd7e9ff31e94 |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Empty" patient is selected
And the user navigated to the "Summary" view
And the patient summary view is loaded
And no badge is displayed on the Navigation toolbar
When the user selects the "Hide empty cards" checkbox in View options
Then the following badge is displayed on the Navigation toolbar:
| badge_type  | badge_text        | badge_color     |
| INFORMATION | Some cards hidden | light blue-gray |
And the Summary view contains the following groups:
| column_index | group_index_in_column | group_name      | badge_text  |
| 2            | 0                     | Treatments      | <N/A>       |
| 2            | 1                     | Medical history | <N/A>       |
And the visible cards are not empty on the Summary view
And the "Status and response" button is visible

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68490
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Comments card is not hidden if comment available for the patient
Given [API] the below patient comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | comment_text                  |
| 05b46423-4e30-47a3-b30d-cd7e9ff31e94 | comments card remains visible |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Empty" patient is selected
And the user navigated to the "Summary" view
And the patient summary view is loaded
And no badge is displayed on the Navigation toolbar
When the user selects the "Hide empty cards" checkbox in View options
Then the following badge is displayed on the Navigation toolbar:
| badge_type  | badge_text        | badge_color     |
| INFORMATION | Some cards hidden | light blue-gray |
And the Summary view contains the following groups:
| column_index | group_index_in_column | group_name      | badge_text  |
| 2            | 0                     | Treatments      | <N/A>       |
| 2            | 1                     | Medical history | <N/A>       |
| 2            | 2                     | Comments        | Most recent |
And the "Comments" group has 1 card
And the 1st card in "Comments" group equals the following data:
| data_type | data                                                 |
| COMMENTS  | [source]: summary/comments_on_card_SysRS-68490.table |
| LINK      | [link]: Open all comments     [date]: N/A            |
When the user navigates to the "Comments" view
And the user clicks on the 'Delete' button in the menu of the following comment on the Comments view:
| author (firstname lastname) | date_and_time | content                       |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | comments card remains visible |
Then the following comments are not available on the Comments view:
| author (firstname lastname) | date_and_time | content                       |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | comments card remains visible |
When the user navigates to the "Summary" view
Then the Summary view contains the following groups:
| column_index | group_index_in_column | group_name      | badge_text  |
| 2            | 0                     | Treatments      | <N/A>       |
| 2            | 1                     | Medical history | <N/A>       |

@srs
@manual
@Platform:WEB
@Category:Negative
@Traceability:SysRS-68490
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Summary_View
Scenario: Error popup is displayed if empty cards view state cannot be saved
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Empty" patient is selected
And the user navigated to the "Summary" view
And the patient summary view is loaded
And [DEVTOOLS] - the following text patterns were added to "Network request blocking" tool:
| text pattern  |
| settings      |
When [DEVTOOLS] - the user checks "Enable network request blocking" checkbox
And the user selects the "Hide empty cards" checkbox in View options
Then an error popup is displayed with the following message:
| error_title             | detailed_error_text                     |
| Failed to save Settings | Sorry, something went wrong on our end. |
