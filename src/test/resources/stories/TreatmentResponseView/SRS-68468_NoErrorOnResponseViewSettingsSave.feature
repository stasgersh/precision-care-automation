Feature: [SysRS-68468] No Error On Response View Settings Save

Narrative: The system shall not show any error message in case of unsuccessful imaging study settings save, and let the user continue his/her workflow.


@manual
@SRS-68468.01
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68468
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME3_-_Manual/Treatment_Response_View
Scenario: No error is displayed if the saving of the imaging study settings is unsuccessful
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Sigrid" patient is selected
And the "Patient status" button was clicked
And the Treatment and response view is loaded
And the following imaging study was selected for study A:
| name                     | date         |
| Diagnostic imaging study | Apr 25, 2010 |
And the following imaging study was selected for study B:
| name                     | date         |
| Diagnostic imaging study | Jan 24, 2013 |
Given [DEVTOOLS] - the following text patterns were added to "Network request blocking" tool:
| text pattern |
| settings     |
When [DEVTOOLS] - the user checks "Enable network request blocking" checkbox
And the user selects the following imaging study for study A:
| name                     | date         |
| Diagnostic imaging study | Jan 30, 2016 |
And the user selects the following imaging study for study B:
| name                                   | date         |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Mar 11, 2016 |
Then no error is displayed on the UI
When [DEVTOOLS] - the user unchecks "Enable network request blocking" checkbox
And the user refreshes the page
Then the Response view is reloaded
And the following imaging study is displayed for A:
| name                     | date         |
| Diagnostic imaging study | Apr 25, 2010 |
And the following imaging study is displayed for B:
| name                     | date         |
| Diagnostic imaging study | Jan 24, 2013 |