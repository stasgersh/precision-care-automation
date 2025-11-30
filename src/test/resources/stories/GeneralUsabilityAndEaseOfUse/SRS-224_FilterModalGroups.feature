Feature: [SysRS-68640] Filter Modal Groups

Narrative: The system shall display the labels in the filter modal by the following groups:
            - Swimlanes
            - Labels
            - Imaging modalities
            - Pathology and Labs
            - Encounter types
            - Biomarkers
            - Treatment types.


@SRS-224.01
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68640
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Filter_Modal_Groups
Scenario: Filter modal elements are grouped on Filter modal
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Empty" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
When the user opens the timeline filter modal
Then the following groups are available on the filter modal:
| groups             |
| Swimlanes          |
| Labels             |
| Imaging modalities |
| Pathology and Labs |
| Encounter types    |
| Biomarkers         |
| Treatment types    |
