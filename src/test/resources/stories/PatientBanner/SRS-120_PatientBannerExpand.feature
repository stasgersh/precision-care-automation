@safety
Feature: [SysRS-68431] Expand patient banner

Narrative: The system shall allow the users to Expand Patient Banner to see all the data in case data exceed one row.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Juno" patient is selected
And the patient banner is collapsed

@sanity
@edge
@SRS-120.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68431
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Patient_Banner
Scenario: Patient banner can be expanded
Given the following parameters are visible in the patient banner:
| parameter_name |
| MRN            |
| DOB            |
| Gender         |
| Weight         |
And the following parameters are not visible in the patient banner:
| parameter_name             |
| Gleason score              |
| Stage - initial            |
| Most Recent Tx             |
| BMI                        |
| BSA                        |
| PSA                        |
| Molecular profile          |
| Comorbidities              |
| Allergies and intolerances |
When the user expands the patient banner
Then the following parameters are visible in the patient banner:
| parameter_name             |
| MRN                        |
| DOB                        |
| Gender                     |
| Weight                     |
| ECOG                       |
| Diagnosis                  |
| Histology type             |
| Gleason score              |
| Stage - initial            |
| Most Recent Tx             |
| BMI                        |
| BSA                        |
| PSA                        |
| Molecular profile          |
| Comorbidities              |
| Allergies and intolerances |
And the patient banner button label was changed to "Collapse"

@SRS-120.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68431
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Patient_Banner
Scenario: Patient banner remains expanded for the patient
When the user expands the patient banner
Then the patient banner is expanded
When the "Trends" view is selected
Then the patient banner is expanded
When the user waits 2000 milliseconds
And the user clicks on the browser's refresh button
Then the Patient page is loaded
And the patient banner is expanded
When [API] the [DEFAULT-TEST-USER] user resets the patient banner settings for "OncoTestPatient, Freya"
And the user selects the "OncoTestPatient, Freya" patient
Then the patient banner is expanded
When the user clicks on the "Settings" menu item on the user menu panel
Then the Settings page is displayed
When the user clicks on the "Back" button in the Settings toolbar header
Then the Patient page is loaded
And the patient banner is expanded
