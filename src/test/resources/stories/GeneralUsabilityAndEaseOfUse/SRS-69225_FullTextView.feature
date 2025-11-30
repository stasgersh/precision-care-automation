@safety
Feature: [SysRS-69225] Full Text View

Narrative: The system shall provide the user a way to view the full text in a line in case text to display is bigger than the field allocated (Treatment eligibility view).


@edge
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69225
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use
Scenario: Tooltip is displayed for study with long title
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the patient summary view is loaded
And the "Status and response" button was clicked
And the Treatment and response view is loaded
And the following imaging study was selected for study A:
| name                                                                            | date         |
| Percutaneous mechanical thrombectomy of portal vein using fluoroscopic guidance | Mar 20, 2009 |
And the following imaging study was selected for study B:
| name                    | date         |
| Mammography (procedure) | Mar 26, 2023 |
When the user opens the imaging study selector for A
Then the open dropdown list displays the imaging studies in the following order:
| name                                                                            | date         | selected | disabled |
| Mammography (procedure)                                                         | Mar 26, 2023 | no       | yes      |
| Positron emission tomography with computed tomography (procedure)               | Oct 02, 2019 | no       | no       |
| Ultrasonography of bilateral breasts (procedure)                                | Dec 22, 2016 | no       | no       |
| Examination of joint under image intensifier (procedure)                        | Sep 09, 2015 | no       | no       |
| Magnetic resonance imaging of breast (procedure)                                | Nov 20, 2013 | no       | no       |
| XR ABDOMEN                                                                      | Mar 11, 2012 | no       | no       |
| Bone density scan (procedure)                                                   | Jan 06, 2011 | no       | no       |
| Percutaneous mechanical thrombectomy of portal vein using fluoroscopic guidance | Mar 20, 2009 | yes      | no       |
When the user hovers on the following study in the open list:
| name                                                                            | date         |
| Percutaneous mechanical thrombectomy of portal vein using fluoroscopic guidance | Mar 20, 2009 |
Then a tooltip is displayed with the full study name as: "Percutaneous mechanical thrombectomy of portal vein using fluoroscopic guidance"