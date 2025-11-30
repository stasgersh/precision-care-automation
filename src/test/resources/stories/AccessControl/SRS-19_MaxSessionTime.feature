Feature: [SysRS-68406] Max Session Time

Narrative: A user session shall be limited up to 6 hours.


@srs
@SRS-19.01
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68406
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Access_Control
Scenario: Refresh token expires in 6 hours
Given the [DEFAULT-TEST-USER] user is logged in to CareIntellect for Oncology application
And started to measure the time
When the user selects the "OncoTestPatient, Juno" patient
Then the patient summary view is loaded
When the user stays logged in by using the UI every hour (to avoid inactivity logout) for 6 hours
Then the user is still logged in after 5 hours and 58 minutes
When the user waits for 6 hours to pass
Then the user is logged out from CareIntellect for Oncology automatically
And the user is navigated to the CareIntellect for Oncology splash screen
