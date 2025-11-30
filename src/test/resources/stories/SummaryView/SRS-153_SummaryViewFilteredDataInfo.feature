@safety
Feature: [SysRS-68482] Summary View Filtered Data Info

Narrative: The system shall inform the users when the Summary view page presents filtered data.


@SRS-153.01
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68482
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Info card is displayed on the Summary view about that the page presents filtered data
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Juno" patient
Then the patient summary view is loaded
And the Filtered data info on the Summary View contains the following text: "Patient data summary This page displays key patient information but may not include all available medical records. Hide for 30 days"
