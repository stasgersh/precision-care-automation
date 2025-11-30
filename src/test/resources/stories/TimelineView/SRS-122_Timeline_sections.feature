@safety
Feature: [SysRS-68514] Timeline sections

Narrative: The system Timeline view shall contain: Macro navigator, Timeline and Timeline Toolbar.


@sanity
@edge
@SRS-122.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68514
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Timeline_View
Scenario: Timeline consists of Macro navigator, Timeline and toolbar
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
When the user navigates to the "Timeline" view
Then the patient timeline is displayed
And the macro-navigator is displayed
And the timeline toolbar section is displayed
