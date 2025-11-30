@safety
Feature: [SysRS-68402] Login Screen

Narrative: The system shall integrate with Cloud IDAM Login page, which provides place for the user to enter their username and password.


@sanity
@SRS-14.01
@edge
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68402,SysRS-68411
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Access_Control
Scenario: Username and password fields are available on the Login screen
Given the user is on the OncoCare splash screen
When the user navigates to the Login screen
Then the Login page is loaded
And the following input fields are visible: Username, Password


@sanity
@manual
@SRS-14.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68402,SysRS-68411
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Manual/Functional_Service/Access_Control
Scenario: Revoke token after closing application
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the user reopen browser
When the user goes to the following URL: ${webdriver.base.url}/patient
Given the user is on the OncoCare splash screen
When the user navigates to the Login screen
Then the Login page is loaded
And the following input fields are visible: Username, Password
