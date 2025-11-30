Feature: [SysRS-68528] Macro navigator filtered swimlanes

Narrative: The system Macro navigator shall reflect to the filtered swimlanes.


@sanity
@edge
@SRS-127.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68528
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Macro_Navigator_Section
Scenario: Swimlane filters are applied on macro-navigator
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the user navigated to the "Timeline" view
And the patient timeline is displayed
And the macro-navigator is displayed
When the user set the following timeline filter options:
| filter_group | checkbox_name             | selected |
| Swimlanes    | Encounters                | true     |
| Swimlanes    | Imaging                   | true     |
| Swimlanes    | Pathology and Labs        | false    |
| Swimlanes    | Biomarkers                | true     |
| Swimlanes    | Treatment and Plan        | true     |
| Swimlanes    | Uncategorized             | true     |
Then the swimlanes are available on the macro-navigator in the following order:
| swimlane_name      | is_filtered |
| Encounters         | false       |
| Imaging            | false       |
| Pathology and Labs | true        |
| Biomarkers         | false       |
| Treatment and Plan | false       |
| Uncategorized      | false       |
And the "Encounters" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | false       | false    |
| 1     | false       | false    |
| 2     | false       | false    |
| 3     | false       | false    |
And the "Imaging" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | false       | false    |
| 1     | false       | false    |
| 2     | false       | false    |
| 3     | false       | false    |
| 4     | false       | false    |
| 5     | false       | false    |
| 6     | false       | false    |
| 7     | false       | false    |
And the "Pathology and Labs" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | true        | false    |
| 1     | true        | false    |
| 2     | true        | false    |
| 3     | true        | false    |
| 4     | true        | false    |
And the "Biomarkers" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | false       | false    |
And the "Treatment and Plan" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | false       | false    |
| 1     | false       | false    |
| 2     | false       | false    |
And the "Uncategorized" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | false       | false    |
| 1     | false       | false    |
| 2     | false       | false    |
When the user selects the "Hide Navigator" checkbox on the timeline toolbar
Then the macro-navigator is not displayed
When the user unselects the "Hide Navigator" checkbox on the timeline toolbar
Then the macro-navigator is displayed
And the swimlanes are available on the macro-navigator in the following order:
| swimlane_name      | is_filtered |
| Encounters         | false       |
| Imaging            | false       |
| Pathology and Labs | true        |
| Biomarkers         | false       |
| Treatment and Plan | false       |
| Uncategorized      | false       |
And the "Encounters" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | false       | false    |
| 1     | false       | false    |
| 2     | false       | false    |
| 3     | false       | false    |
And the "Imaging" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | false       | false    |
| 1     | false       | false    |
| 2     | false       | false    |
| 3     | false       | false    |
| 4     | false       | false    |
| 5     | false       | false    |
| 6     | false       | false    |
| 7     | false       | false    |
And the "Pathology and Labs" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | true        | false    |
| 1     | true        | false    |
| 2     | true        | false    |
| 3     | true        | false    |
| 4     | true        | false    |
And the "Biomarkers" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | false       | false    |
And the "Treatment and Plan" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | false       | false    |
| 1     | false       | false    |
| 2     | false       | false    |
And the "Uncategorized" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | false       | false    |
| 1     | false       | false    |
| 2     | false       | false    |
