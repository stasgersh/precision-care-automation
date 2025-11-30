Feature: [SysRS-68405] Logout

Narrative: The system users shall be able to logout from the application.


@srs
@SRS-18.01
@edge
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68405,SysRS-68413,SysRS-68565
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control
Scenario: User can logout from the application
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
When the user clicks on the "Sign out" button on the user menu panel
Then the user is navigated to the OncoCare splash screen

@srs
@SRS-18.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68405
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control
Scenario: OncoCare splash screen is displayed if the user tries to navigate to an OncoCare page which requires authorization
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
When the user logs out from OncoCare
Then the user is navigated to the OncoCare splash screen
When the user goes to the following URL: ${webdriver.base.url}/patient/a10e48ab-b927-9ee1-84a4-41638a84e05a
Then the user is navigated to the OncoCare splash screen
And the user navigates to the Login screen

@token_validation
@srs
@SRS-18.03
@SPR-4346
@edge
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68405
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control
Scenario Outline: The user's access token is invalidated after logout from the application
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And token validation is set to "<mode>" mode
And the Patient page is loaded
And the user access token is stored from the browser's cookie
And the user access token is valid for no more than 15 minutes
And the following request was sent to OncoCare by using the stored access token from cookie:
| service | method | endpoint_path |
| core    | GET    | /patients     |
And the received HTTP status code was 200
And the user logged out from OncoCare
And the user was navigated to the OncoCare splash screen
When the user waits <wait_mins> minutes
And the following request is sent to OncoCare by using the stored access token from cookie:
| service | method | endpoint_path |
| core    | GET    | /patients     |
Then the received HTTP status code is 403
Examples:
| mode    | wait_mins |
| online  | 1         |
| offline | 16        |
