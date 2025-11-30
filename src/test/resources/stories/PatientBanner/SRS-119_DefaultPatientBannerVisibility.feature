@safety
Feature: [SysRS-68430] Default Patient Banner Visibility

Narrative: Patient banner shall be open by default.


Background:
Given the user is logged out from OncoCare application
And [API] the [TEST-USER-2] user reset the patient banner settings for "OncoTestPatient, Juno"
When the following request is sent to OncoCare on behalf of [TEST-USER-2] user:
| service | method | endpoint_path                                           |
| core    | GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/settings |
Then the received HTTP status code is 200
And the "banner.open" property in the response matches the value: true

@sanity
@SRS-119.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68430
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Patient_Banner
Scenario: Patient banner is expanded by default
When the [TEST-USER-2] user logs in to OncoCare
And the user selects the "OncoTestPatient, Juno" patient (without reseting patient settings)
Then the patient summary view is loaded
And the patient banner is expanded

@SRS-119.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68430
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Patient_Banner
Scenario: Patient banner does NOT collapse when navigating between tabs
When the [TEST-USER-2] user logs in to OncoCare
And the user selects the "OncoTestPatient, Juno" patient (without reseting patient settings)
Then the patient summary view is loaded
When the "Trends" view is selected
Then the patient banner is expanded
When the "Summary" view is selected
Then the patient banner is expanded

@SRS-119.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68430,SysRS-68399
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Patient_Banner
Scenario: Patient banner remains open when the page is refreshed or reopened
When the [TEST-USER-2] user logs in to OncoCare
And the user selects the "OncoTestPatient, Juno" patient
Then the patient summary view is loaded
When the "Medical history" view is selected
Then the patient banner is expanded
When the user clicks on the browser's refresh button
Then the Patient page is loaded
And the patient banner is expanded
When the "Comments" view is selected
Then the patient banner is expanded
When the user opens a new browser tab
And the user navigates to the initial patient page
And the user selects the "OncoTestPatient, Juno" patient
Then the patient banner is expanded
