Feature: [SysRS-69191] Select Time Points To Compare

Narrative: The system shall provide the user ability to select two different time-points (events) to compare between (point A and point B).


@sanity
@edge
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69191,SysRS-68459
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Treatment_Response_View
Scenario Outline: Selector dropdown is scrollable if there are more than 8 options is displayed in the list
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "<patient>" patient is selected
And the patient summary view is loaded
And the "Status and response" button was clicked
And the Treatment and response view is loaded
When the user opens the imaging study selector for A
Then <number_of_studies> studies are available in the open dropdown list
And the open dropdown list <dropdown_scrollable> scrollable
Examples:
| patient                       | number_of_studies | dropdown_scrollable |
| OncoTestPatient, Franklin     | 1                 | is not              |
| OncoTestPatient, Sigrid       | 7                 | is not              |
| OncoTestPatient, Torvald      | 8                 | is                  |
| OncoTestPatient, Juliane376   | 9                 | is                  |
| Ms. OncoTestPatient, Nancy265 | 13                | is                  |

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69191
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Treatment_Response_View
Scenario: Swap, Reset and Dropdown of A is disabled if only one event is present for the patient
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Franklin" patient is selected
And the patient summary view is loaded
And the "Status and response" button was clicked
And the Treatment and response view is loaded
And no imaging study was selected for study A
And the following imaging study was selected for study B:
| name                                   | date         |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 1975 |
When the user opens the imaging study selector for B
Then 1 studies are available in the open dropdown list
When the user opens the imaging study selector for A
Then the open dropdown list displays the imaging studies in the following order:
| name                                   | date         | selected | disabled |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 1975 | no       | yes      |
And the Swap button is disabled on Response view
And the Reset button is disabled on Response view

@me3hotfix3
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69191
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Treatment_Response_View
Scenario: Selector is disabled when there is no event present for the patient
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Empty" patient is selected
And the patient summary view is loaded
When the user clicks "Status and response" button
Then the Treatment and response view is loaded
And the imaging study selector of A is disabled
And the imaging study selector of B is disabled


@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69191
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Treatment_Response_View
Scenario: Already selected events are disabled in the selector list
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the patient summary view is loaded
And the "Status and response" button was clicked
And the Treatment and response view is loaded
And the following imaging study was selected for study A:
| name              | date         |
| XR CHEST PA OR AP | Aug 18, 2022 |
And the following imaging study was selected for study B:
| name                                                     | date         |
| Examination of joint under image intensifier (procedure) | Sep 09, 2022 |
When the user opens the imaging study selector for A
Then the open dropdown list displays the imaging studies in the following order:
| name                                                              | date         | selected | disabled |
| Examination of joint under image intensifier (procedure)          | Sep 09, 2022 | no       | yes      |
| Positron emission tomography with computed tomography (procedure) | Sep 08, 2022 | no       | no       |
| Diagnostic imaging study                                          | Aug 19, 2022 | no       | no       |
| XR CHEST PA OR AP                                                 | Aug 18, 2022 | yes      | no       |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST                            | Sep 28, 2011 | no       | no       |
When the user opens the imaging study selector for B
Then the open dropdown list displays the imaging studies in the following order:
| name                                                              | date         | selected | disabled |
| Examination of joint under image intensifier (procedure)          | Sep 09, 2022 | yes      | no       |
| Positron emission tomography with computed tomography (procedure) | Sep 08, 2022 | no       | no       |
| Diagnostic imaging study                                          | Aug 19, 2022 | no       | no       |
| XR CHEST PA OR AP                                                 | Aug 18, 2022 | no       | yes      |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST                            | Sep 28, 2011 | no       | no       |
When the user selects the following imaging study for study A:
| name                                   | date         |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011 |
When the user selects the following imaging study for study B:
| name                                                              | date         |
| Positron emission tomography with computed tomography (procedure) | Sep 08, 2022 |
When the user opens the imaging study selector for B
Then the open dropdown list displays the imaging studies in the following order:
| name                                                              | date         | selected | disabled |
| Examination of joint under image intensifier (procedure)          | Sep 09, 2022 | no       | no       |
| Positron emission tomography with computed tomography (procedure) | Sep 08, 2022 | yes      | no       |
| Diagnostic imaging study                                          | Aug 19, 2022 | no       | no       |
| XR CHEST PA OR AP                                                 | Aug 18, 2022 | no       | no       |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST                            | Sep 28, 2011 | no       | yes      |
When the user closes the imaging study selector for B
And the user opens the imaging study selector for A
Then the open dropdown list displays the imaging studies in the following order:
| name                                                              | date         | selected | disabled |
| Examination of joint under image intensifier (procedure)          | Sep 09, 2022 | no       | no       |
| Positron emission tomography with computed tomography (procedure) | Sep 08, 2022 | no       | yes      |
| Diagnostic imaging study                                          | Aug 19, 2022 | no       | no       |
| XR CHEST PA OR AP                                                 | Aug 18, 2022 | no       | no       |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST                            | Sep 28, 2011 | yes      | no       |
