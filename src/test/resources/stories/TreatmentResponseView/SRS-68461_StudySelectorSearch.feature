Feature: [SysRS-68461] Study Selector Search

Narrative: The system shall provide search functionality for two different time-points (Imaging notes events and images events) to compare between (point A and point B).


@SRS-182.01
@sanity
@edge
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68461,SysRS-69190,SysRS-68459
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Treatment_Response_View
Scenario: User can search imaging studies by title
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juliane376" patient is selected
And the patient summary view is loaded
When the user clicks "Status and response" button
Then the Treatment and response view is loaded
When the user opens the imaging study selector for A
Then the open dropdown list displays the imaging studies in the following order:
| name                                             | date         | selected | disabled |
| Mammography (procedure)                          | Jan 04, 2020 | no       | yes      |
| Mammography (procedure)                          | Mar 09, 2019 | yes      | no       |
| Mammography (procedure)                          | Apr 28, 2018 | no       | no       |
| Ultrasonography of bilateral breasts (procedure) | May 10, 2016 | no       | no       |
| Screening mammography (procedure)                | May 10, 2016 | no       | no       |
| Fetal anatomy study                              | Apr 23, 1988 | no       | no       |
| Ultrasound scan for fetal viability              | Feb 27, 1988 | no       | no       |
| Fetal anatomy study                              | Feb 01, 1986 | no       | no       |
| Ultrasound scan for fetal viability              | Dec 07, 1985 | no       | no       |
When the user types "mam" into the Search field of the open imaging study selector
Then the open dropdown list displays the imaging studies in the following order:
| name                              | date         | selected | disabled |
| Mammography (procedure)           | Jan 04, 2020 | no       | yes      |
| Mammography (procedure)           | Mar 09, 2019 | yes      | no       |
| Mammography (procedure)           | Apr 28, 2018 | no       | no       |
| Screening mammography (procedure) | May 10, 2016 | no       | no       |
When the user types "FE" into the Search field of the open imaging study selector
Then the open dropdown list displays the imaging studies in the following order:
| name                                             | date         | selected | disabled |
| Ultrasonography of bilateral breasts (procedure) | May 10, 2016 | no       | no       |
| Fetal anatomy study                              | Apr 23, 1988 | no       | no       |
| Ultrasound scan for fetal viability              | Feb 27, 1988 | no       | no       |
| Fetal anatomy study                              | Feb 01, 1986 | no       | no       |
| Ultrasound scan for fetal viability              | Dec 07, 1985 | no       | no       |
When the user types "Ultrasonography of bilateral breasts (procedure)" into the Search field of the open imaging study selector
Then the open dropdown list displays the imaging studies in the following order:
| name                                             | date         | selected | disabled |
| Ultrasonography of bilateral breasts (procedure) | May 10, 2016 | no       | no       |

@SRS-182.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68461,SysRS-69190,SysRS-68459
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Treatment_Response_View
Scenario: User can select imaging study in the filtered list
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the patient summary view is loaded
And the "Status and response" button was clicked
And the Treatment and response view is loaded
And the following imaging study was selected for study A:
| name              | date         |
| XR CHEST PA OR AP | Aug 18, 2022 |
And the following imaging study was selected for study B:
| name                                                              | date         |
| Positron emission tomography with computed tomography (procedure) | Sep 08, 2022 |
When the user opens the imaging study selector for A
Then the open dropdown list displays the imaging studies in the following order:
| name                                                              | date         | selected | disabled |
| Examination of joint under image intensifier (procedure)          | Sep 09, 2022 | no       | no       |
| Positron emission tomography with computed tomography (procedure) | Sep 08, 2022 | no       | yes      |
| Diagnostic imaging study                                          | Aug 19, 2022 | no       | no       |
| XR CHEST PA OR AP                                                 | Aug 18, 2022 | yes      | no       |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST                            | Sep 28, 2011 | no       | no       |
When the user types "EXAMINATION of joint" into the Search field of the open imaging study selector
Then the open dropdown list displays the imaging studies in the following order:
| name                                                     | date         | selected | disabled |
| Examination of joint under image intensifier (procedure) | Sep 09, 2022 | no       | no       |
When the user clicks the following imaging study in the open list:
| name                                                     | date         |
| Examination of joint under image intensifier (procedure) | Sep 09, 2022 |
Then the following imaging study is displayed for A:
| name                                                     | date         |
| Examination of joint under image intensifier (procedure) | Sep 09, 2022 |

@SRS-182.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68461,SysRS-68459
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Treatment_Response_View
Scenario: User can cancel the search
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the patient summary view is loaded
And the "Status and response" button was clicked
And the Treatment and response view is loaded
And the following imaging study was selected for study A:
| name              | date         |
| XR CHEST PA OR AP | Aug 18, 2022 |
And the following imaging study was selected for study B:
| name                                                              | date         |
| Positron emission tomography with computed tomography (procedure) | Sep 08, 2022 |
When the user opens the imaging study selector for B
Then the open dropdown list displays the imaging studies in the following order:
| name                                                              | date         | selected | disabled |
| Examination of joint under image intensifier (procedure)          | Sep 09, 2022 | no       | no       |
| Positron emission tomography with computed tomography (procedure) | Sep 08, 2022 | yes      | no       |
| Diagnostic imaging study                                          | Aug 19, 2022 | no       | no       |
| XR CHEST PA OR AP                                                 | Aug 18, 2022 | no       | yes      |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST                            | Sep 28, 2011 | no       | no       |
When the user types "pos" into the Search field of the open imaging study selector
Then the open dropdown list displays the imaging studies in the following order:
| name                                                              | date         | selected | disabled |
| Positron emission tomography with computed tomography (procedure) | Sep 08, 2022 | yes      | no       |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST                            | Sep 28, 2011 | no       | no       |
When the user cancels the search by clicking 'X' icon on the Search field
Then the open dropdown list displays the imaging studies in the following order:
| name                                                              | date         | selected | disabled |
| Examination of joint under image intensifier (procedure)          | Sep 09, 2022 | no       | no       |
| Positron emission tomography with computed tomography (procedure) | Sep 08, 2022 | yes      | no       |
| Diagnostic imaging study                                          | Aug 19, 2022 | no       | no       |
| XR CHEST PA OR AP                                                 | Aug 18, 2022 | no       | yes      |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST                            | Sep 28, 2011 | no       | no       |

@SRS-181.01
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68461,SysRS-68459
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Treatment_Response_View
Scenario: Imaging selector panel displays the studies ordered by time
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juliane376" patient is selected
And the patient summary view is loaded
When the user clicks "Status and response" button
Then the Treatment and response view is loaded
When the user opens the imaging study selector for A
Then the open dropdown list displays the imaging studies in the following order:
| name                                             | date         | selected | disabled |
| Mammography (procedure)                          | Jan 04, 2020 | no       | yes      |
| Mammography (procedure)                          | Mar 09, 2019 | yes      | no       |
| Mammography (procedure)                          | Apr 28, 2018 | no       | no       |
| Ultrasonography of bilateral breasts (procedure) | May 10, 2016 | no       | no       |
| Screening mammography (procedure)                | May 10, 2016 | no       | no       |
| Fetal anatomy study                              | Apr 23, 1988 | no       | no       |
| Ultrasound scan for fetal viability              | Feb 27, 1988 | no       | no       |
| Fetal anatomy study                              | Feb 01, 1986 | no       | no       |
| Ultrasound scan for fetal viability              | Dec 07, 1985 | no       | no       |
When the user closes the imaging study selector for A
And the user opens the imaging study selector for B
Then the open dropdown list displays the imaging studies in the following order:
| name                                             | date         | selected | disabled |
| Mammography (procedure)                          | Jan 04, 2020 | yes      | no       |
| Mammography (procedure)                          | Mar 09, 2019 | no       | yes      |
| Mammography (procedure)                          | Apr 28, 2018 | no       | no       |
| Ultrasonography of bilateral breasts (procedure) | May 10, 2016 | no       | no       |
| Screening mammography (procedure)                | May 10, 2016 | no       | no       |
| Fetal anatomy study                              | Apr 23, 1988 | no       | no       |
| Ultrasound scan for fetal viability              | Feb 27, 1988 | no       | no       |
| Fetal anatomy study                              | Feb 01, 1986 | no       | no       |
| Ultrasound scan for fetal viability              | Dec 07, 1985 | no       | no       |

@SRS-182.02
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68461
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Treatment_Response_View
Scenario Outline: Most recent imaging is selected for B and previous to most recent imaging is selected for A by default if there are at least 2 with the same modality
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "<patient>" patient is selected
And the patient summary view is loaded
And the "Status and response" button was clicked
And the Treatment and response view is loaded
When the user clicks on the Reset button on the Study selector module
Then the Treatment and response view is loaded
Then the following imaging study is displayed for A:
| name     | date     |
| <a_name> | <a_date> |
And the following imaging study is displayed for B:
| name     | date     |
| <b_name> | <b_date> |
Examples:
| patient                | a_name                                                             | a_date       | b_name                                                   | b_date       |
| OncoTestPatient, Freya | Computed tomography, abdomen and pelvis; with contrast material(s) | Jan 16, 2000 | CT THORAX ABDOMEN PELVIS WITH CONTRAST                   | Sep 28, 2011 |
| OncoTestPatient, Juno  | XR CHEST PA OR AP                                                  | Aug 18, 2022 | Examination of joint under image intensifier (procedure) | Sep 09, 2022 |

@SRS-68461.01
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68461
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME3_-_Automated/Treatment_Response_View
Scenario: User can search imaging studies by date
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juliane376" patient is selected
And the patient summary view is loaded
When the user clicks "Status and response" button
Then the Treatment and response view is loaded
When the user opens the imaging study selector for A
Then the open dropdown list displays the imaging studies in the following order:
| name                                             | date         | selected | disabled |
| Mammography (procedure)                          | Jan 04, 2020 | no       | yes      |
| Mammography (procedure)                          | Mar 09, 2019 | yes      | no       |
| Mammography (procedure)                          | Apr 28, 2018 | no       | no       |
| Ultrasonography of bilateral breasts (procedure) | May 10, 2016 | no       | no       |
| Screening mammography (procedure)                | May 10, 2016 | no       | no       |
| Fetal anatomy study                              | Apr 23, 1988 | no       | no       |
| Ultrasound scan for fetal viability              | Feb 27, 1988 | no       | no       |
| Fetal anatomy study                              | Feb 01, 1986 | no       | no       |
| Ultrasound scan for fetal viability              | Dec 07, 1985 | no       | no       |
When the user types "1988" into the Search field of the open imaging study selector
Then the open dropdown list displays the imaging studies in the following order:
| name                                | date         | selected | disabled |
| Fetal anatomy study                 | Apr 23, 1988 | no       | no       |
| Ultrasound scan for fetal viability | Feb 27, 1988 | no       | no       |
When the user types "May 10" into the Search field of the open imaging study selector
Then the open dropdown list displays the imaging studies in the following order:
| name                                             | date         | selected | disabled |
| Screening mammography (procedure)                | May 10, 2016 | no       | no       |
| Ultrasonography of bilateral breasts (procedure) | May 10, 2016 | no       | no       |
When the user types "Apr 28, 2018" into the Search field of the open imaging study selector
Then the open dropdown list displays the imaging studies in the following order:
| name                    | date         | selected | disabled |
| Mammography (procedure) | Apr 28, 2018 | no       | no       |

@SRS-68461.02
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68461,SysRS-68464
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME3_-_Automated/Treatment_Response_View
Scenario: Empty state is displayed if there is no result was found in the list - search by title and date
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juliane376" patient is selected
And the patient summary view is loaded
When the user clicks "Status and response" button
Then the Treatment and response view is loaded
When the user opens the imaging study selector for A
Then the open dropdown list displays the imaging studies in the following order:
| name                                             | date         | selected | disabled |
| Mammography (procedure)                          | Jan 04, 2020 | no       | yes      |
| Mammography (procedure)                          | Mar 09, 2019 | yes      | no       |
| Mammography (procedure)                          | Apr 28, 2018 | no       | no       |
| Ultrasonography of bilateral breasts (procedure) | May 10, 2016 | no       | no       |
| Screening mammography (procedure)                | May 10, 2016 | no       | no       |
| Fetal anatomy study                              | Apr 23, 1988 | no       | no       |
| Ultrasound scan for fetal viability              | Feb 27, 1988 | no       | no       |
| Fetal anatomy study                              | Feb 01, 1986 | no       | no       |
| Ultrasound scan for fetal viability              | Dec 07, 1985 | no       | no       |
When the user types "2024" into the Search field of the open imaging study selector
Then the open dropdown displays "No result found"
When the user types "Jun" into the Search field of the open imaging study selector
Then the open dropdown displays "No result found"
When the user types "Spectrography" into the Search field of the open imaging study selector
Then the open dropdown displays "No result found"
When the user types "Scans" into the Search field of the open imaging study selector
Then the open dropdown displays "No result found"