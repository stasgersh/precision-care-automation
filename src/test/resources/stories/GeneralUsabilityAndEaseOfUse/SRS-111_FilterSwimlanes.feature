@safety
Feature: [SysRS-68654] Filter Swimlanes

Narrative: The system users shall be able to filter for swimlanes.

Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application


@sanity
@SRS-111.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68654,SysRS-68641
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use/Filter_Swimlanes
Scenario: Swimlanes are listed on the filter modal
Given the "OncoTestPatient, Pablo44" patient is selected
And the "Timeline" view is selected
When the user opens the timeline filter modal
Then only the following timeline filter options are displayed on the filter modal in "Swimlanes" group:
| checkbox_name             | selected | enabled |
| Encounters                | false    | true    |
| Imaging                   | false    | true    |
| Pathology and Labs        | false    | true    |
| Biomarkers                | false    | true    |
| Treatment and Plan        | false    | true    |
| Uncategorized             | false    | true    |

@sanity
@SRS-111.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68654
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use/Filter_Swimlanes
Scenario: Unselected swimlanes are removed from the timeline
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
When the user opens the timeline filter modal
And the user set the following timeline filter options:
| filter_group | checkbox_name             | selected |
| Swimlanes    | Encounters                | false    |
| Swimlanes    | Imaging                   | true     |
| Swimlanes    | Pathology and Labs        | true     |
| Swimlanes    | Biomarkers                | false    |
| Swimlanes    | Treatment and Plan        | true     |
| Swimlanes    | Uncategorized             | false    |
Then the swimlanes are available on the timeline in the following order:
| items              |
| Imaging            |
| Pathology and Labs |
| Treatment and Plan |
And the following swimlanes are not available on the timeline:
| items                     |
| Encounters                |
| Biomarkers                |
| Uncategorized             |

@SRS-111.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68654,SysRS-69226
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Filter_Swimlanes
Scenario: Swimlane filter options are kept after closing the Filter modal
Given the "OncoTestPatient, Eleanor470" patient is selected
And the "Timeline" view is selected
And the user has set the following timeline filter options:
| filter_group | checkbox_name             | selected |
| Swimlanes    | Encounters                | true     |
| Swimlanes    | Imaging                   | false    |
| Swimlanes    | Pathology and Labs        | true     |
| Swimlanes    | Biomarkers                | false    |
| Swimlanes    | Treatment and Plan        | true     |
| Swimlanes    | Uncategorized             | true     |
When the user closes the timeline filter modal
Then the swimlanes are available on the timeline in the following order:
| items              |
| Encounters         |
| Pathology and Labs |
| Treatment and Plan |
| Uncategorized      |
And the following swimlanes are not available on the timeline:
| items      |
| Imaging    |
| Biomarkers |
And the filter counter icon on the Filter button is displayed with number: 4
And the following filter badge is available in the filter list on the timeline toolbar:
| filter             |
| Encounters         |
| Pathology and Labs |
| Treatment and Plan |
| Uncategorized      |
When the user opens the timeline filter modal again
Then the following timeline filter options are displayed on the filter modal:
| filter_group | checkbox_name             | selected |
| Swimlanes    | Encounters                | true     |
| Swimlanes    | Imaging                   | false    |
| Swimlanes    | Pathology and Labs        | true     |
| Swimlanes    | Biomarkers                | false    |
| Swimlanes    | Treatment and Plan        | true     |
| Swimlanes    | Uncategorized             | true     |

@sanity
@SRS-111.04
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68654
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use/Filter_Swimlanes
Scenario: User can reset swimlane filter settings
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
When the user opens the timeline filter modal
And the user set the following timeline filter options:
| filter_group | checkbox_name             | selected |
| Swimlanes    | Encounters                | false    |
| Swimlanes    | Imaging                   | true     |
| Swimlanes    | Pathology and Labs        | true     |
| Swimlanes    | Biomarkers                | false    |
| Swimlanes    | Treatment and Plan        | true     |
| Swimlanes    | Uncategorized             | false    |
Then the swimlanes are available on the timeline in the following order:
| items              |
| Imaging            |
| Pathology and Labs |
| Treatment and Plan |
And the following swimlanes are not available on the timeline:
| items                     |
| Encounters                |
| Biomarkers                |
| Uncategorized             |
When the user opens the timeline filter modal
Then the following timeline filter options are displayed on the filter modal:
| filter_group | checkbox_name             | selected |
| Swimlanes    | Encounters                | false    |
| Swimlanes    | Imaging                   | true     |
| Swimlanes    | Pathology and Labs        | true     |
| Swimlanes    | Biomarkers                | false    |
| Swimlanes    | Treatment and Plan        | true     |
| Swimlanes    | Uncategorized             | false    |
When the user clicks on the "Reset filters" button on the filter modal
Then the following timeline filter options are displayed on the filter modal:
| filter_group | checkbox_name             | selected |
| Swimlanes    | Encounters                | false    |
| Swimlanes    | Imaging                   | false    |
| Swimlanes    | Pathology and Labs        | false    |
| Swimlanes    | Biomarkers                | false    |
| Swimlanes    | Treatment and Plan        | false    |
| Swimlanes    | Uncategorized             | false    |
When the user closes the timeline filter modal
Then the swimlanes are available on the timeline in the following order:
| swimlane_names            |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Biomarkers                |
| Treatment and Plan        |
| Uncategorized             |

@SRS-111.05
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68654
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Filter_Swimlanes
Scenario: Search for swimlanes on the timeline filter modal (search when at least 3 characters are typed)
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the user has opened the timeline filter modal
And only the following timeline filter options are displayed on the filter modal in "Swimlanes" group:
| filter_group | checkbox_name             | selected |
| Swimlanes    | Encounters                | false    |
| Swimlanes    | Imaging                   | false    |
| Swimlanes    | Pathology and Labs        | false    |
| Swimlanes    | Biomarkers                | false    |
| Swimlanes    | Treatment and Plan        | false    |
| Swimlanes    | Uncategorized             | false    |
When the user types the following text into the search field on filter modal: "er"
Then only the following timeline filter options are displayed on the filter modal in "Swimlanes" group:
| filter_group | checkbox_name             | selected |
| Swimlanes    | Encounters                | false    |
| Swimlanes    | Biomarkers                | false    |
| Swimlanes    | Uncategorized             | false    |
When the user types the following text into the search field on filter modal: "ers"
Then only the following timeline filter options are displayed on the filter modal in "Swimlanes" group:
| filter_group | checkbox_name             | selected |
| Swimlanes    | Encounters                | false    |
| Swimlanes    | Biomarkers                | false    |
When the user types the following text into the search field on filter modal: "mark"
Then only the following timeline filter options are displayed on the filter modal in "Swimlanes" group:
| filter_group | checkbox_name             | selected |
| Swimlanes    | Biomarkers                | false    |
When the user types the following text into the search field on filter modal: "not existing swimlane"
Then the following message is displayed on the Filter modal in the "Swimlanes" group: "No matches."
