Feature: [SysRS-68521] Reset timeline settings to the default

Narrative: The system’s users shall be able to reset the timeline settings to the default Timeline view.


@sanity
@edge
@SRS-205.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68521
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Timeline_View
Scenario: User can reset the timeline filter settings to default - no filter
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
And the user navigated to the "Timeline" view
And the patient timeline is displayed
When the user set the following timeline filter options:
| filter_group       | checkbox_name      | selected |
| Swimlanes          | Encounters         | true     |
| Swimlanes          | Imaging            | false    |
| Swimlanes          | Pathology and Labs | true     |
| Swimlanes          | Biomarkers         | false    |
| Swimlanes          | Treatment and Plan | true     |
| Swimlanes          | Uncategorized      | true     |
| Labels             | Has comments       | true     |
| Labels             | Adverse Event      | true     |
| Imaging modalities | CT                 | true     |
| Pathology and Labs | Labs               | true     |
Then the filter counter icon on the Filter button is displayed with number: 8
And the swimlanes are available on the macro-navigator in the following order:
| swimlane_name      | is_filtered |
| Encounters         | false       |
| Imaging            | true        |
| Pathology and Labs | false       |
| Biomarkers         | true        |
| Treatment and Plan | false       |
| Uncategorized      | false       |
And the swimlanes are available on the timeline in the following order:
| items              |
| Encounters         |
| Pathology and Labs |
| Treatment and Plan |
| Uncategorized      |
And the "Encounters" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | true        | false    |
| 1     | true        | false    |
| 2     | true        | false    |
And the "Imaging" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | true        | false    |
| 1     | true        | false    |
| 2     | true        | false    |
When the user resets the timeline settings using "Reset to default view" in the More menu
Then the filter counter icon on the Filter button is not displayed
And filter badge is not available in the filter list on the timeline toolbar
And the swimlanes are available on the macro-navigator in the following order:
| swimlane_name      | is_filtered |
| Encounters         | false       |
| Imaging            | false       |
| Pathology and Labs | false       |
| Biomarkers         | false       |
| Treatment and Plan | false       |
| Uncategorized      | false       |
And the swimlanes are available on the timeline in the following order:
| items              |
| Encounters         |
| Imaging            |
| Pathology and Labs |
| Biomarkers         |
| Treatment and Plan |
| Uncategorized      |
And the "Encounters" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | false       | false    |
| 1     | false       | false    |
| 2     | false       | false    |
And the "Imaging" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | false       | false    |
| 1     | false       | false    |
| 2     | false       | false    |

@SRS-205.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68521
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_View
Scenario: User can reset the selected event to default - none selected
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
And the user navigated to the "Timeline" view
And the patient timeline is displayed
When the user clicks on the following event point on the "Imaging" swimlane:
| event_type | name_on_label                          |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST |
Then the "Imaging" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | false       | false    |
| 1     | false       | false    |
| 2     | false       | true     |
And the sidebar is displayed
When the user resets the timeline settings using "Reset to default view" in the More menu
Then the "Imaging" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | false       | false    |
| 1     | false       | false    |
| 2     | false       | false    |
And the sidebar is not displayed

@SRS-205.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68521
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_View
Scenario: User can reset the navigator state setting to default - open
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
And the user navigated to the "Timeline" view
And the patient timeline is displayed
When the user selects the "Hide Navigator" checkbox on the timeline toolbar
Then the macro-navigator is not displayed
And the "Hide navigator" checkbox is selected on the timeline toolbar
When the user resets the timeline settings using "Reset to default view" in the More menu
Then the macro-navigator is displayed
And the "Hide navigator" checkbox is not selected on the timeline toolbar

@SRS-205.04
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68521
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_View
Scenario: Resetting the timeline view settings does not change the saved state of the patient banner
Given [API] the [DEFAULT-TEST-USER] user reset the patient banner settings for "OncoTestPatient, Juno"
When the [DEFAULT-TEST-USER] user logs in to OncoCare
And the user selects the "OncoTestPatient, Juno" patient (without reseting patient settings)
And the user navigates to the "Timeline" view
Then the patient timeline is loaded
When the user collapses the patient banner
Then the patient banner is collapsed
When the user resets the timeline settings using "Reset to default view" in the More menu
Then the patient timeline is loaded
And the patient banner is collapsed

@SRS-205.05
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68521
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_View
Scenario: Reset of the timeline view settings persist after navigating between tabs or refreshing the page
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
And the user navigated to the "Timeline" view
And the patient timeline is displayed
And the user has set the following timeline filter options:
| filter_group       | checkbox_name      | selected |
| Swimlanes          | Encounters         | true     |
| Swimlanes          | Imaging            | true     |
| Swimlanes          | Pathology and Labs | true     |
| Swimlanes          | Biomarkers         | false    |
| Swimlanes          | Treatment and Plan | true     |
| Swimlanes          | Uncategorized      | true     |
| Labels             | Has comments       | true     |
| Labels             | Adverse Event      | true     |
| Imaging modalities | CT                 | true     |
| Pathology and Labs | Labs               | true     |
And the following event point is selected on the "Imaging" swimlane:
| event_type | name_on_label                          |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST |
And the "Imaging" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | false       | false    |
| 1     | false       | false    |
| 2     | false       | true     |
And the user has selected the "Hide Navigator" checkbox on the timeline toolbar
When the user resets the timeline settings using "Reset to default view" in the More menu
Then the filter counter icon on the Filter button is not displayed
And filter badge is not available in the filter list on the timeline toolbar
And the macro-navigator is displayed
And the swimlanes are available on the macro-navigator in the following order:
| swimlane_name      | is_filtered |
| Encounters         | false       |
| Imaging            | false       |
| Pathology and Labs | false       |
| Biomarkers         | false       |
| Treatment and Plan | false       |
| Uncategorized      | false       |
And the "Hide navigator" checkbox is not selected on the timeline toolbar
And the sidebar is not displayed
When the user clicks on the browser's refresh button
Then the patient timeline is loaded
Then the filter counter icon on the Filter button is not displayed
And filter badge is not available in the filter list on the timeline toolbar
And the macro-navigator is displayed
And the swimlanes are available on the macro-navigator in the following order:
| swimlane_name      | is_filtered |
| Encounters         | false       |
| Imaging            | false       |
| Pathology and Labs | false       |
| Biomarkers         | false       |
| Treatment and Plan | false       |
| Uncategorized      | false       |
And the macro-navigator is displayed
And the "Hide navigator" checkbox is not selected on the timeline toolbar
And the sidebar is not displayed
When the user navigates to the "Comments" view
And the user navigates to the "Timeline" view
Then the filter counter icon on the Filter button is not displayed
And filter badge is not available in the filter list on the timeline toolbar
And the swimlanes are available on the macro-navigator in the following order:
| swimlane_name      | is_filtered |
| Encounters         | false       |
| Imaging            | false       |
| Pathology and Labs | false       |
| Biomarkers         | false       |
| Treatment and Plan | false       |
| Uncategorized      | false       |
And the macro-navigator is displayed
And the "Hide navigator" checkbox is not selected on the timeline toolbar
And the sidebar is not displayed

@SRS-205.06
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68521
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Timeline_View
Scenario: User can reset the zoom level to default - 6 months
Given test patients are copied from '\testdata\patients\prepared\macronav\' to '\tools\TestDataGeneration\basePatientData\'
And the test patients are generated by the '\tools\TestDataGeneration\prepareTestData.sh' tool today
And the generated test patients are uploaded to PDS
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, MacroNavMonth" patient is selected
When I navigate to the "Timeline" view
Then the patient timeline is displayed
And the time-box on the macro-navigator includes the last 6 months of the patient's timeline
And the patient's last 6 months are visible on the timeline
And the 'Report 6 months ago' event is in the time-box on the macro-navigator
And the 'Report 6 month ago' event is visible on the timeline
When I zoom in the timeline to the max zoom level
And I scroll the timeline to the left side
Then the 'Report 365 days ago' event is visible on the timeline
And the time-box on the macro-navigator is moved to the left side
When I reset the timeline settings using "Reset to default view" in the More menu
Then the patient's last 6 months are visible on the timeline
And the 'Report 6 months ago' event is in the time-box on the macro-navigator
And the 'Report 6 month ago' event is visible on the timeline
When I refresh the page
Then the patient's last 6 months are visible on the timeline
And the 'Report 6 months ago' event is in the time-box on the macro-navigator
And the 'Report 6 month ago' event is visible on the timeline
When I navigate to the "Summary" view
And I navigate back to the "Timeline" view
Then the patient's last 6 months are visible on the timeline
And the 'Report 6 months ago' event is in the time-box on the macro-navigator
And the 'Report 6 month ago' event is visible on the timeline