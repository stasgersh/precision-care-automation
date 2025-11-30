@safety
Feature: [SysRS-68432] Default Patient Banner Visibility

Narrative: The system users shall be able to Collapse Patient Banner when it is displayed in more than one row.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Juno" patient is selected
And the Patient Banner is displayed
And the patient banner was expanded
And the following parameters are visible in the patient banner:
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

@sanity
@edge
@SRS-121.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68432
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Patient_Banner
Scenario: Patient banner is collapsed
When the user collapses the patient banner
Then the following parameters are visible in the patient banner:
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
And the patient banner button label was changed to "Expand"

@ToPlaywright
@SRS-121.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68432,SysRS-68399
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Patient_Banner
Scenario: Patient banner remains collapsed for the patient
Given the patient summary view is loaded
When the user collapses the patient banner
Then the patient banner is collapsed
When the "Timeline" view is selected
Then the patient timeline is loaded
And the patient banner is collapsed
When the user waits 2000 milliseconds
And the user clicks on the browser's refresh button
Then the patient timeline is loaded
And the patient banner is collapsed
When [API] the [DEFAULT-TEST-USER] user resets the patient banner settings for "OncoTestPatient, Freya"
And the user selects the "OncoTestPatient, Freya" patient
Then the patient banner is expanded
When the user selects the "OncoTestPatient, Juno" patient
Then the patient banner is collapsed
When the user clicks on the "Settings" menu item on the user menu panel
And the user clicks on the "Back" button in the Settings toolbar header
Then the patient summary view is loaded
And the patient banner is collapsed
