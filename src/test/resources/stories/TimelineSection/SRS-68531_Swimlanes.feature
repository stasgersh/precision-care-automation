Feature: [SysRS-68531] Swimlanes

Narrative: The system shall be able to display the Patient data points on a timeline, grouped by the following swimlanes:
             - Encounters,
             - Imaging,
             - Pathology and Labs,
             - Biomarkers,
             - Treatment and Plan and
             - Uncategorized.


@sanity
@SRS-30.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68531
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Timeline_section
Scenario Outline: The predefined swimlanes are displayed for all patients (even if there is no data on the swimlane)
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "<patient>" patient is selected
When the user navigates to the "Timeline" view
Then the patient timeline is loaded
And the swimlanes are available on the timeline in the following order:
| items              |
| Encounters         |
| Imaging            |
| Pathology and Labs |
| Biomarkers         |
| Treatment and Plan |
| Uncategorized      |
Examples:
| example_description        | patient                     |
| Patient has several events | OncoTestPatient, Juno       |
| Patient has only one event | OncoTestPatient, MaryHuanna |
