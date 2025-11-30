Feature: [SysRS-68480] Jump links

Narrative: The system shall provide navigation toolbar (e.g jump links) at the top of the page with hyperlink to all the sections (as described inSummary dashboard – summary view (SysRS-68478)) for jump and focus-on the section once user request to.


Background:
Given the [TEST-USER-2] user is logged in to OncoCare application
And the Patient page is loaded


@sanity
@edge
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68480
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
Scenario Outline: By default the following jump links should be displayed: Diagnosis, Imaging, Treatments, Molecular profile, Oncology note, Labs, ER visit, MDT report, Care team
Given the "<patient>" patient is selected
When the user navigates to the "Summary" view
Then the patient summary view is loaded
And the navigation toolbar is displayed
When the user clicks the "More" button on the Navigation toolbar
Then the "More" menu is opened
And the "Hide empty cards" checkbox is not selected in the View options
And the navigation toolbar contains links to the following sections:
| section_name      |
| Diagnosis         |
| Imaging           |
| Treatments        |
| Molecular profile |
| Oncology note     |
| Labs              |
| ER visit          |
| MDT report        |
| Care team         |
Examples:
| patient                |
| OncoTestPatient, Freya |
| OncoTestPatient, Empty |


@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68480
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario Outline: User can jump to sections on Summary view by clicking on a jump link in the navigation toolbar
Given the "OncoTestPatient, Freya" patient is selected
And the user navigated to the "Summary" view
And the patient summary view is loaded
And the navigation toolbar is displayed
And the "Hide empty cards" checkbox was not selected in the View options
When the user clicks on "<section_name>" link on the navigation toolbar
Then "<section_name>" section is in the viewport
And the URL contains the "<URL_tag>" tag

Examples:
| section_name      | URL_tag                         |
| Diagnosis         | /summary#diagnosis              |
| Imaging           | /summary#mostRecentImaging      |
| Treatments        | /summary#treatments             |
| Molecular profile | /summary#molecularProfile       |
| Oncology note     | /summary#mostRecentOncologyNote |
| Labs              | /summary#labs                   |
| ER visit          | /summary#mostRecentErVisit      |
| MDT report        | /summary#mostRecentMdtReport    |
| Care team         | /summary#careTeam               |

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68480
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Jump links should not display the hidden Summary cards after selecting Hide empty cards checkbox
Given the "OncoTestPatient, Empty" patient is selected
And the user navigated to the "Summary" view
And the patient summary view is loaded
And the navigation toolbar is displayed
And no badge is displayed on the Navigation toolbar
And the navigation toolbar contains links to the following sections:
| section_name      |
| Diagnosis         |
| Imaging           |
| Treatments        |
| Molecular profile |
| Oncology note     |
| Labs              |
| ER visit          |
| MDT report        |
| Care team         |
When the user selects the "Hide empty cards" checkbox in View options
Then the following badge is displayed on the Navigation toolbar:
| badge_type  | badge_text        | badge_color     |
| INFORMATION | Some cards hidden | light blue-gray |
And the navigation toolbar contains links to the following sections:
| section_name |
| Treatments   |
And the Summary view contains the following groups:
| column_index | group_index_in_column | group_name      | badge_text |
| 2            | 0                     | Treatments      | <N/A>      |
| 2            | 1                     | Medical history | <N/A>      |
When the user clicks on "Treatments" link on the navigation toolbar
Then "Treatments" section is in the viewport
And the URL contains the "/summary#treatments" tag

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68480
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Jump links should not display the hidden Summary cards when the user hides them on Summary order and visibility modal
When the user selects the "OncoTestPatient, Juno" patient
Then the patient summary view is loaded
And no badge is displayed on the Navigation toolbar
And the navigation toolbar contains links to the following sections:
| section_name      |
| Diagnosis         |
| Imaging           |
| Treatments        |
| Molecular profile |
| Oncology note     |
| Labs              |
| ER visit          |
| MDT report        |
| Care team         |
When the user navigates to the "Edit order and visibility" from the "More" menu
And the user sets the following group settings on the "Summary order and visibility" modal:
| column_index | group_index_in_column | group_name        | hidden |
| 0            | 0                     | Diagnosis         | yes    |
| 0            | 1                     | Molecular profile | no     |
| 0            | 2                     | Care team         | no     |
| 1            | 0                     | Imaging           | yes    |
| 1            | 1                     | Oncology note     | yes    |
| 1            | 2                     | ER visit          | no     |
| 1            | 3                     | MDT report        | no     |
| 2            | 0                     | Treatments        | no     |
| 2            | 1                     | Labs              | no     |
| 2            | 2                     | Medical history   | no     |
| 2            | 3                     | Comments          | no     |
And the user clicks on the "Save" button on the "Summary order and visibility" modal
Then the "Summary order and visibility" modal disappears
And the patient summary view is loaded
And the following badge is displayed on the Navigation toolbar:
| badge_type  | badge_text        | badge_color     |
| INFORMATION | Some cards hidden | light blue-gray |
And the navigation toolbar contains links to the following sections:
| section_name      |
| Treatments        |
| Molecular profile |
| Labs              |
| ER visit          |
| MDT report        |
| Care team         |
When the user clicks on "Molecular profile" link on the navigation toolbar
Then "Molecular profile" section is in the viewport
And the URL contains the "/summary#molecularProfile" tag
When the user navigates to the "Edit order and visibility" from the "More" menu
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
When the user clicks on the "Save" button on the "Summary order and visibility" modal
Then the "Summary order and visibility" modal disappears
And the patient summary view is loaded
And the navigation toolbar contains links to the following sections:
| section_name      |
| Diagnosis         |
| Imaging           |
| Treatments        |
| Molecular profile |
| Oncology note     |
| Labs              |
| ER visit          |
| MDT report        |
| Care team         |
When the user clicks on "Care team" link on the navigation toolbar
Then "Care team" section is in the viewport
And the URL contains the "/summary#careTeam" tag
When the user clicks on "Care team" link on the navigation toolbar
Then "Care team" section is in the viewport
And the URL contains the "/summary#careTeam" tag


@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68480
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Jump links should NOT reflect the order of the Summary cards
Given the "OncoTestPatient, Sigrid" patient is selected
When the user navigates to the "Summary" view
Then the patient summary view is loaded
And the navigation toolbar is displayed
And no badge is displayed on the Navigation toolbar
And the navigation toolbar contains links to the following sections:
| section_name      |
| Diagnosis         |
| Imaging           |
| Treatments        |
| Molecular profile |
| Oncology note     |
| Labs              |
| ER visit          |
| MDT report        |
| Care team         |
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
When the user navigates to the "Edit order and visibility" from the "More" menu
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
| 0            | 1                     | Care team         | yes    |
| 0            | 2                     | Imaging           | no     |
| 0            | 3                     | Oncology note     | no     |
| 0            | 4                     | MDT report        | yes    |
| 1            | 0                     | ER visit          | no     |
| 1            | 1                     | Molecular profile | no     |
| 1            | 2                     | Labs              | no     |
| 2            | 0                     | Comments          | yes    |
| 2            | 1                     | Treatments        | no     |
| 2            | 2                     | Medical history   | no     |
And the user clicks on the "Save" button on the "Summary order and visibility" modal
Then the "Summary order and visibility" modal disappears
And the patient summary view is loaded
And the following badge is displayed on the Navigation toolbar:
| badge_type  | badge_text        | badge_color     |
| INFORMATION | Some cards hidden | light blue-gray |
And the navigation toolbar contains links to the following sections:
| section_name      |
| Diagnosis         |
| Imaging           |
| Treatments        |
| Molecular profile |
| Oncology note     |
| Labs              |
| ER visit          |
And the Summary view contains the following groups:
| column_index | group_index_in_column | group_name        | badge_text  |
| 0            | 0                     | Diagnosis         | <N/A>       |
| 0            | 1                     | Imaging           | Most recent |
| 0            | 2                     | Oncology note     | Most recent |
| 1            | 0                     | ER visit          | Most recent |
| 1            | 1                     | Molecular profile | <N/A>       |
| 1            | 2                     | Labs              | <N/A>       |
| 2            | 0                     | Treatments        | <N/A>       |
| 2            | 1                     | Medical history   | <N/A>       |
When the user clicks on "Labs" link on the navigation toolbar
Then "Labs" section is in the viewport
And the URL contains the "/summary#labs" tag
