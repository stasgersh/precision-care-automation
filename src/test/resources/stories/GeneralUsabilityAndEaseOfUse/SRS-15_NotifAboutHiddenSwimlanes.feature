@safety
Feature: [SysRS-68633] Notif About Hidden Swimlanes

Narrative: The system users shall be informed when any swimlanes are hidden.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application


@SRS-15.01
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68633
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Notify_About_Hidden_Swimlanes
Scenario: Filter counter and filter label is displayed if any swimlane is hidden
Given the "OncoTestPatient, Eleanor470" patient is selected
And the "Timeline" view is selected
When the user set the following timeline filter options:
| filter_group | checkbox_name             | selected |
| Swimlanes    | Encounters                | true     |
| Swimlanes    | Imaging                   | true     |
| Swimlanes    | Pathology and Labs        | false    |
| Swimlanes    | Biomarkers                | false    |
| Swimlanes    | Treatment and Plan        | true     |
| Swimlanes    | Uncategorized             | false    |
Then the filter counter icon on the Filter button is displayed with number: 3
And the following filter badge is available in the filter list on the timeline toolbar:
| filter             |
| Encounters         |
| Imaging            |
| Treatment and Plan |

@SRS-15.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68633
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Notify_About_Hidden_Swimlanes
Scenario: All swimlanes are displayed and the filter notifications are removed if all swimlanes are selected again on filter modal
Given the "OncoTestPatient, Eleanor470" patient is selected
And the "Timeline" view is selected
And the user has set the following timeline filter options:
| filter_group | checkbox_name             | selected |
| Swimlanes    | Encounters                | true     |
| Swimlanes    | Imaging                   | false    |
| Swimlanes    | Pathology and Labs        | false    |
| Swimlanes    | Biomarkers                | true     |
| Swimlanes    | Treatment and Plan        | true     |
| Swimlanes    | Uncategorized             | false    |
And the user has closed the timeline filter modal
And the filter counter icon on the Filter button is displayed
When the user opens the timeline filter modal
And the user set the following timeline filter options:
| filter_group | checkbox_name             | selected |
| Swimlanes    | Encounters                | true     |
| Swimlanes    | Imaging                   | true     |
| Swimlanes    | Pathology and Labs        | true     |
| Swimlanes    | Biomarkers                | true     |
| Swimlanes    | Treatment and Plan        | true     |
| Swimlanes    | Uncategorized             | true     |
Then the swimlanes are available on the timeline in the following order:
| items                     |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Biomarkers                |
| Treatment and Plan        |
| Uncategorized             |
And the filter counter icon on the Filter button is not displayed
And filter badge is not available in the filter list on the timeline toolbar

@SRS-15.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68633
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Notify_About_Hidden_Swimlanes
Scenario: Filter counter icon is displayed if any swimlane is hidden - Vertical toolbar
Given the "OncoTestPatient, Eleanor470" patient is selected
And the "Timeline" view is selected
And the browser size is set to: width=1680, height=768
When the user set the following timeline filter options on the vertical toolbar:
| filter_group | checkbox_name             | selected |
| Swimlanes    | Encounters                | true     |
| Swimlanes    | Imaging                   | true     |
| Swimlanes    | Pathology and Labs        | false    |
| Swimlanes    | Biomarkers                | false    |
| Swimlanes    | Treatment and Plan        | true     |
| Swimlanes    | Uncategorized             | false    |
Then the filter counter icon on the Filter button on the vertical toolbar is displayed with number: 3

@SRS-15.04
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68633
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Notify_About_Hidden_Swimlanes
Scenario: Filter counter icon is NOT displayed if all swimlane is visible - Vertical toolbar
Given the "OncoTestPatient, Eleanor470" patient is selected
And the "Timeline" view is selected
And the browser size is set to: width=1680, height=768
When the user set the following timeline filter options on the vertical toolbar:
| filter_group | checkbox_name             | selected |
| Swimlanes    | Encounters                | true     |
| Swimlanes    | Imaging                   | true     |
| Swimlanes    | Pathology and Labs        | false    |
| Swimlanes    | Biomarkers                | false    |
| Swimlanes    | Treatment and Plan        | true     |
| Swimlanes    | Uncategorized             | false    |
Then the filter counter icon on the Filter button on the vertical toolbar is displayed with number: 3
When the user set the following timeline filter options on the vertical toolbar:
| filter_group | checkbox_name             | selected |
| Swimlanes    | Encounters                | true     |
| Swimlanes    | Imaging                   | true     |
| Swimlanes    | Pathology and Labs        | true     |
| Swimlanes    | Biomarkers                | true     |
| Swimlanes    | Treatment and Plan        | true     |
| Swimlanes    | Uncategorized             | true     |
Then the filter counter icon on the Filter button is not displayed on the vertical toolbar
