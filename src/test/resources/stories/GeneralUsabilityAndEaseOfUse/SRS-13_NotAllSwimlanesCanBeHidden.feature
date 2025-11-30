Feature: [SysRS-68632] Not All Swimlanes Can Be Hidden

Narrative: The system users shall not be able to hide all the swimlanes.


@SRS-13.01
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68632,SysRS-69226
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use
Scenario: It is not possible to hide all available swimlanes at the same time
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Al" patient is selected
And the "Timeline" view is selected
When the user set the following timeline filter options:
| filter_group | checkbox_name      | selected |
| Swimlanes    | Encounters         | false    |
| Swimlanes    | Imaging            | false    |
| Swimlanes    | Pathology and Labs | false    |
| Swimlanes    | Biomarkers         | false    |
| Swimlanes    | Treatment and Plan | true     |
| Swimlanes    | Uncategorized      | false    |
Then the filter counter icon on the Filter button is displayed with number: 1
And the following filter badge is available in the filter list on the timeline toolbar:
| filter             |
| Treatment and Plan |
And the following swimlanes are available on the timeline:
| items              |
| Treatment and Plan |
And the following swimlanes are not available on the timeline:
| items              |
| Encounters         |
| Imaging            |
| Pathology and Labs |
| Biomarkers         |
| Uncategorized      |
When the user set the following timeline filter options:
| filter_group | checkbox_name      | selected |
| Swimlanes    | Encounters         | true     |
| Swimlanes    | Imaging            | true     |
| Swimlanes    | Pathology and Labs | true     |
| Swimlanes    | Biomarkers         | true     |
| Swimlanes    | Treatment and Plan | true     |
| Swimlanes    | Uncategorized      | true     |
Then the filter counter icon on the Filter button is not displayed
And filter badge is not available in the filter list on the timeline toolbar
And the following swimlanes are available on the timeline:
| items              |
| Encounters         |
| Imaging            |
| Pathology and Labs |
| Biomarkers         |
| Treatment and Plan |
| Uncategorized      |
When the user set the following timeline filter options:
| filter_group | checkbox_name      | selected |
| Swimlanes    | Encounters         | false    |
| Swimlanes    | Imaging            | false    |
| Swimlanes    | Pathology and Labs | false    |
| Swimlanes    | Biomarkers         | false    |
| Swimlanes    | Treatment and Plan | false    |
| Swimlanes    | Uncategorized      | false    |
Then the filter counter icon on the Filter button is not displayed
And filter badge is not available in the filter list on the timeline toolbar
And the following swimlanes are available on the timeline:
| items              |
| Encounters         |
| Imaging            |
| Pathology and Labs |
| Biomarkers         |
| Treatment and Plan |
| Uncategorized      |
