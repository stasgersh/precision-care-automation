@safety
Feature: [SysRS-68433] Save Patient Banner State Per Patient

Narrative: The system shall save the Patient Banner’s opened or closed view state on patient level.

@ToPlaywright
@SRS-171.01
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68433
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Patient_Banner
Scenario: Patient banner's opened or closed state is saved per user per patient.
Given the user is logged out from OncoCare application
And [API] the [DEFAULT-TEST-USER] user reset the patient banner settings for "OncoTestPatient, Juno"
And [API] the [DEFAULT-TEST-USER] user reset the patient banner settings for "OncoTestPatient, Freya"
And [API] the [TEST-USER-2] user reset the patient banner settings for "OncoTestPatient, Juno"
And [API] the [TEST-USER-2] user reset the patient banner settings for "OncoTestPatient, Freya"
When the [DEFAULT-TEST-USER] user logs in to OncoCare
And the user selects the "OncoTestPatient, Juno" patient (without reseting patient settings)
And the user collapses the patient banner
Then the patient banner is collapsed
When the user navigates to the "Timeline" view
Then the patient timeline is loaded
And the patient banner is collapsed
When the user selects the "OncoTestPatient, Freya" patient (without reseting patient settings)
Then the patient banner is expanded
When the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method | endpoint_path                                           |
| core    | GET    | /patients/6d2d9e07-4a24-7525-4661-f59ccabb02a7/settings |
Then the received HTTP status code is 200
And the "banner.open" property in the response matches the value: true
When the user selects the "OncoTestPatient, Juno" patient (without reseting patient settings)
Then the patient banner is collapsed
When the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method | endpoint_path                                           |
| core    | GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/settings |
Then the received HTTP status code is 200
And the "banner.open" property in the response matches the value: false
When the user logs out from OncoCare
And the [TEST-USER-2] user logs in to OncoCare
And the user selects the "OncoTestPatient, Juno" patient (without reseting patient settings)
Then the patient banner is expanded
When the following request is sent to OncoCare on behalf of [TEST-USER-2] user:
| service | method | endpoint_path                                           |
| core    | GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/settings |
Then the received HTTP status code is 200
And the "banner.open" property in the response matches the value: true
