Feature: [SysRS-68465] Saved Imaging Study Settings

Narrative: The system's users shall see their saved imaging study settings for the selected patient after:
           Patient selection
           Browser page refresh
           After back-and-forth navigation.


@sanity
@edge
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68465
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Treatment_Response_View
Scenario: Saved imaging study settings are displayed for the user
Given the [TEST-USER-2] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the patient summary view is loaded
And the "Status and response" button was clicked
And the Treatment and response view is loaded
And the following imaging study was selected for study A:
| name                                             | date         |
| Magnetic resonance imaging of breast (procedure) | Nov 20, 2013 |
And the following imaging study was selected for study B:
| name                    | date         |
| Mammography (procedure) | Mar 26, 2023 |
When the user navigates to the "Timeline" view
Then the patient timeline is loaded
When the user navigates to the "Response" view
Then the Treatment and response view is loaded
And the following imaging study is displayed for A:
| name                                             | date         |
| Magnetic resonance imaging of breast (procedure) | Nov 20, 2013 |
And the following imaging study is displayed for B:
| name                    | date         |
| Mammography (procedure) | Mar 26, 2023 |
When the user clicks on the browser's refresh button
Then the Treatment and response view is loaded
And the following imaging study is displayed for A:
| name                                             | date         |
| Magnetic resonance imaging of breast (procedure) | Nov 20, 2013 |
And the following imaging study is displayed for B:
| name                    | date         |
| Mammography (procedure) | Mar 26, 2023 |
When the user selects the following imaging study for study A:
| name       | date         |
| XR ABDOMEN | Mar 11, 2012 |
When the user selects the following imaging study for study B:
| name                          | date         |
| Bone density scan (procedure) | Jan 06, 2011 |
Then the following alert is displayed under the imaging study selectors: "A and B are in reverse chronological order."
When the user selects the "OncoTestPatient, Juno" patient (without reseting patient settings)
Then the Patient page is loaded
When the user selects the "OncoTestPatient, Torvald" patient (without reseting patient settings)
Then the Patient page is loaded
When the user clicks "Status and response" button
Then the Treatment and response view is loaded
And the following imaging study is displayed for A:
| name       | date         |
| XR ABDOMEN | Mar 11, 2012 |
And the following imaging study is displayed for B:
| name                          | date         |
| Bone density scan (procedure) | Jan 06, 2011 |
