Feature: [SysRS-68404] Logout After Inactivity

Narrative: The system shall logout users after one hour inactivity.


@srs
@SRS-17.01
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68404
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Access_Control
Scenario: User is not logged out automatically if there is any interaction with the UI
Given the [DEFAULT-TEST-USER] user is logged in to CareIntellect for Oncology application
And the Patient page is loaded
When the user do not interact with the CareIntellect for Oncology UI (change to another tab in the browser)
And the user waits 55 minutes
Then the user is still logged in to CareIntellect for Oncology (check it on the proper tab in the browser)
And the Patient page is still displayed
When the user moves the mouse over the CareIntellect for Oncology UI
And the user do not interact with the CareIntellect for Oncology UI again (change to another tab in the browser)
And the user waits 10 minutes
Then the user is still logged in to CareIntellect for Oncology (check it on the proper tab in the browser)
And the Patient page is still displayed

@srs
@SRS-17.02
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68404
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Access_Control
Scenario: User is automatically logged out after one hour inactivity
Given the [DEFAULT-TEST-USER] user is logged in to CareIntellect for Oncology application
And the Patient page is loaded
When the user do not interact with the CareIntellect for Oncology UI (change to another tab in the browser)
And the user waits 61 minutes
Then the user is logged out from CareIntellect for Oncology automatically (check it on the proper tab in the browser)
And the user is navigated to the CareIntellect for Oncology splash screen
