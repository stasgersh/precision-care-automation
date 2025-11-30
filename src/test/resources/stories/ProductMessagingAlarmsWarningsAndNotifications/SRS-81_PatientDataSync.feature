@safety
Feature: [SysRS-68663] Patient data status synchronization

Narrative: The system users shall be informed about the status of the data synchronization.


@srs
@SRS-81.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68663,SysRS-68608,SysRS-68661
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Patient Data Status Banner is not displayed if a patient is not selected
When the [DEFAULT-TEST-USER] user logs in to OncoCare
Then the Patient page is loaded
And the patient data sync banner is not displayed

@sanity
@SRS-81.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68663
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Patient Data Status Banner is displayed for a patient with relative Datetime (e.g. n mins ago)
Given the user is logged out from OncoCare application
And [API] the following patient is uploaded to PDS: patients/OncoTestPatient_PatientDataSync.json
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
When the user selects the "OncoTestPatient, PatientDataSync" patient
Then the patient data sync banner is displayed
And the message displayed in the banner matches to regex: "Patient data last updated: \d{1,2} min ago"

@srs
@SRS-81.03
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68663,SysRS-68608,SysRS-68661
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Patient Data Status Banner is displayed when a user navigates between the tabs in OncoCare
Given [API] the following patient is uploaded to PDS: patients/OncoTestPatient_PatientDataSync.json
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
When the user selects the "OncoTestPatient, PatientDataSync" patient
And the "Timeline" view is selected
Then the patient data sync banner is displayed
And the message displayed in the banner matches to regex: "Patient data last updated: \d{1,2} min ago"
When the "Medical history" view is selected
Then the patient data sync banner is displayed
And the message displayed in the banner matches to regex: "Patient data last updated: \d{1,2} min ago"
When the "Trends" view is selected
Then the patient data sync banner is displayed
And the message displayed in the banner matches to regex: "Patient data last updated: \d{1,2} min ago"
When the "Comments" view is selected
Then the patient data sync banner is displayed
And the message displayed in the banner matches to regex: "Patient data last updated: \d{1,2} min ago"

@srs
@SRS-81.04
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68663,SysRS-68661
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Patient Data Status Banner is displayed specific to the patient selected
Given [API] the following patient is uploaded to PDS: patients/OncoTestPatient_PatientDataSync.json
When the user waits 2 minutes
Given [API] the following patient is uploaded to PDS: patients/OncoTestPatient_PatientDataSync_1.json
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, PatientDataSync" patient
Then the Patient page is loaded
When the user selects the "OncoTestPatient, PatientDataSync_1" patient
Then the Patient page is loaded
And the last sync time is different for the patients:
| patients                           |
| OncoTestPatient, PatientDataSync   |
| OncoTestPatient, PatientDataSync_1 |
And The patients were last synced in following order:
| patients                           |
| OncoTestPatient, PatientDataSync_1 |
| OncoTestPatient, PatientDataSync   |

@SRS-81.05
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68663,SysRS-68661
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Patient Data Status Banner is displayed for a patient with absolute Datetime (e.g. Last sync with hospital systems: 23 Jul, 2023 | 12:34 GMT+5:30)
Given the user is logged out from OncoCare application
And [API] the following patient is uploaded to PDS: patients/OncoTestPatient_PatientDataSync.json
And the user has waited at least one hour
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
When the user selects the "OncoTestPatient, PatientDataSync" patient
Then the patient data sync banner is displayed
And the message displayed in the banner matches to regex: "Patient data last updated: \d{1,2} (?=.*[A-Za-z]).{3}, \d{4} | \d{2}:\d{2} GMT+\d{1,2}" (e.g. "6 Sep, 2023 | 19:30 GMT+2")

@SRS-81.06
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68663,SysRS-68661
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Patient Data Sync Banner when synchronization fails between OncoCare UI and backend
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, PatientDataSync" patient is selected
And the "Timeline" view was selected
When I open the DevTool's Network tab
And I wait for the next 'status' message on the Network tab
And I override the content of the 'status' message with the following changes:
| parameter | new_value |
| status    | failure   |
And I reload the page
Then the patient data sync banner displays text: "Sync with hospital systems failed. Please contact the IT support team in your hospital."
And the background semantic color is red and alert icon shown on status bar, and prominent bar size of 40 pixels
When I override the content of the 'status' message with the following changes:
| parameter | new_value |
| timestamp | <EMPTY>   |
Then the patient data sync banner displays text: "Last sync with hospital systems: unknown."
And the background semantic color is red and alert icon shown on status bar, and prominent bar size of 40 pixels
When I delete the overwritten 'status'
And I reload the page
Then the patient data sync banner displays: "Patient data last updated: "<ANY_DATE_AND_TIME>"