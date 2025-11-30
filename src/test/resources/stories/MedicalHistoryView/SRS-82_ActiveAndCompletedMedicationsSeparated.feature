@safety
Feature: [SysRS-68556] Active And Completed Medications Separated

Narrative: The system shall populate active and completed medications in separate groups.


@SRS-82.01
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68556
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Medical_History_View
Scenario: The patient's active and completed medications are present in separate sections
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
When the user navigates to the "Medical history" view
Then the active medications table is displayed
And the Completed medications table is not displayed in the Medications section
When the user opens the Completed medications section
Then the Completed medications table is displayed in the Medications section
