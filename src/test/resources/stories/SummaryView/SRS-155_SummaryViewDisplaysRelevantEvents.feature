Feature: [SysRS-68484] Summary View Displays Relevant Events

Narrative: The system shall provide the users the ability to view the corresponding event/s on the timeline of the following cards:
             - Key events
             - Molecular profile
             - Most recent imaging
             - Imaging history
             - Most recent MDT report
             - Radiation therapy
             - Systemic therapy
             - Lab history
             - Most recent oncology note

@sanity
@edge
@SRS-155.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68484
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
Scenario: Navigate from Summary view to the timeline from 'Key events' card
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Torvald" patient is selected
And the patient summary view is loaded
Then the 'Show on timeline' button is enabled in the footer of the below card:
| group_name | card_index_in_group |
| Diagnosis  | 2                   |
When the user clicks on the 'Show on timeline' button in the footer of the below card:
| group_name | card_index_in_group |
| Diagnosis  | 2                   |
Then the 'Show on timeline' modal is displayed
And the following links are available on the 'Show on timeline' modal:
| link_text                                   | date         |
| Hysteroscopy                                | Apr 04, 2021 |
| Tissue Pathology biopsy report              | Jan 15, 2020 |
| Tissue Pathology biopsy report 2            | Jan 15, 2020 |
| Excision of sentinel lymph node (procedure) | Jun 14, 2017 |
| Cytology, pap smear (cervical)              | May 31, 2013 |
| Test Diagnostic Report                      | May 11, 2009 |
When the user clicks on the following link on the 'Show on timeline' modal:
| link_text                      | date         |
| Cytology, pap smear (cervical) | May 31, 2013 |
Then the patient timeline is loaded
And the sidebar is displayed
And the sidebar header contains the following data:
| title                          | creation_date | report_type        |
| Cytology, pap smear (cervical) | May 31, 2013  | Pathology and Labs |

@SPR-4891
@SRS-155.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68484
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Navigate from Summary view to the timeline from 'Molecular profile' card
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Summary" view
And the 'Show on timeline' button is enabled in the footer of the below card:
| group_name        | card_index_in_group |
| Molecular profile | 0                   |
When the user clicks on the 'Show on timeline' button in the footer of the below card:
| group_name        | card_index_in_group |
| Molecular profile | 0                   |
Then the 'Show on timeline' modal is displayed
And the following links are available on the 'Show on timeline' modal:
| link_text                                    | date         |
| Estrogen Receptor Positive (qualifier value) | Dec 21, 2016 |
| HER2 Receptor Negative (qualifier value)     | Nov 13, 2008 |
When the user clicks on the following link on the 'Show on timeline' modal:
| link_text                                | date         |
| HER2 Receptor Negative (qualifier value) | Nov 13, 2008 |
Then the patient timeline is loaded
And the sidebar is displayed
And the sidebar header contains the following data:
| title                                    | creation_date | report_type |
| HER2 Receptor Negative (qualifier value) | Nov 13, 2008  | Biomarkers  |

@SRS-155.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68484
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Navigate from Summary view to the timeline from 'Imaging - most recent' card
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Juno" patient is selected
When the user navigates to the "Summary" view
Then the 'Show on timeline' button is enabled in the footer of the below card:
| group_name | card_index_in_group |
| Imaging    | 0                   |
When the user clicks on the 'Show on timeline' button in the footer of the below card:
| group_name | card_index_in_group |
| Imaging    | 0                   |
Then the patient timeline is loaded
And the sidebar is displayed
And the sidebar header contains the following data:
| title                                                    | creation_date | report_type |
| Examination of joint under image intensifier (procedure) | Sep 09, 2022  | Imaging     |

@SRS-155.04
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68484
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Navigate from Summary view to the timeline from 'Imaging history' card
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Summary" view
And the patient summary view is loaded
And the 'Show on timeline' button is enabled in the footer of the below card:
| group_name | card_index_in_group |
| Imaging    | 1                   |
When the user clicks on the 'Show on timeline' button in the footer of the below card:
| group_name | card_index_in_group |
| Imaging    | 1                   |
Then the 'Show on timeline' modal is displayed
And the following links are available on the 'Show on timeline' modal:
| link_text                                                         | date         |
| Examination of joint under image intensifier (procedure)          | Sep 09, 2022 |
| Positron emission tomography with computed tomography (procedure) | Sep 08, 2022 |
| Diagnostic imaging study                                          | Aug 19, 2022 |
| XR CHEST PA OR AP Gerd (Gastroesophageal Reflux Disease)          | Aug 18, 2022 |
When the user clicks on the following link on the 'Show on timeline' modal:
| link_text                                                         | date         |
| Positron emission tomography with computed tomography (procedure) | Sep 08, 2022 |
Then the patient timeline is loaded
And the sidebar is displayed
And the sidebar header contains the following data:
| title                                                             | creation_date | report_type |
| Positron emission tomography with computed tomography (procedure) | Sep 08, 2022  | Imaging     |

@SRS-155.05
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68484
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Cancel to navigate from Summary view to the timeline if more timeline events are linked to the card
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Summary" view
And the 'Show on timeline' button is enabled in the footer of the below card:
| group_name | card_index_in_group |
| Imaging    | 1                   |
When the user clicks on the 'Show on timeline' button in the footer of the below card:
| group_name | card_index_in_group |
| Imaging    | 1                   |
Then the 'Show on timeline' modal is displayed
When the user clicks on the 'X' button on the 'Show on timeline' modal
Then the patient summary view is displayed

@SRS-155.06
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68484
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Navigate from Summary view to the timeline from 'MDT report - most recent' card
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Summary" view
And the 'Show on timeline' button is enabled in the footer of the below card:
| group_name | card_index_in_group |
| MDT report | 0                   |
When the user clicks on the 'Show on timeline' button in the footer of the below card:
| group_name | card_index_in_group |
| MDT report | 0                   |
Then the patient timeline is loaded
And the sidebar is displayed
And the sidebar header contains the following data:
| title            | creation_date | report_type |
| Tumor board Note | Jul 07, 2019  | Encounters  |

@SRS-155.07
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68484
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Navigate from Summary view to the timeline from 'Radiation therapy' card
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Summary" view
And the 'Show on timeline' button is enabled in the footer of the below card:
| group_name | card_index_in_group |
| Treatments | 2                   |
When the user clicks on the 'Show on timeline' button in the footer of the below card:
| group_name | card_index_in_group |
| Treatments | 2                   |
Then the patient timeline is loaded
And the sidebar is displayed
And the sidebar header contains the following data:
| title                            | creation_date | report_type        |
| Radiotherapy course of treatment | Aug 15, 2018  | Treatment and Plan |

@SRS-155.12
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68484
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Navigate from Summary view to the timeline from 'Radiation therapy' card - multiple events
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Walter473" patient is selected
And the user navigated to the "Summary" view
When the user clicks on the 'Show on timeline' button in the footer of the below card:
| group_name | card_index_in_group |
| Treatments | 1                   |
Then the 'Show on timeline' modal is displayed
And the following text is displayed on the 'Show on timeline' modal: Radiation therapy is composed of various medical data. Select one of the events to show on the timeline. (Only one can be displayed at a time.)
And the following links are available on the 'Show on timeline' modal:
| link_text                                               | date         |
| Combined chemotherapy and radiation therapy (procedure) | Nov 06, 2017 |
| Combined chemotherapy and radiation therapy (procedure) | Apr 04, 2018 |
When the user clicks on the following link on the 'Show on timeline' modal:
| link_text                                               | date         |
| Combined chemotherapy and radiation therapy (procedure) | Nov 06, 2017 |
Then the patient timeline is loaded
And the sidebar is displayed
And the sidebar header contains the following data:
| title                                                   | creation_date | report_type        |
| Combined chemotherapy and radiation therapy (procedure) | Nov 06, 2017  | Treatment and Plan |

@SRS-155.08
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68484
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Navigate from Summary view to the timeline from 'Systemic therapy' card
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the user navigated to the "Summary" view
And the 'Show on timeline' button is enabled in the footer of the below card:
| group_name | card_index_in_group |
| Treatments | 0                   |
When the user clicks on the 'Show on timeline' button in the footer of the below card:
| group_name | card_index_in_group |
| Treatments | 0                   |
Then the patient timeline is loaded
And the sidebar is displayed
And the sidebar header contains the following data:
| title                           | creation_date | report_type        |
| cyclophosphamide 1 GM Injection | Feb 24, 2014  | Treatment and Plan |

@SRS-155.09
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68484
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Navigate from Summary view to the timeline from 'Blood test trend' card
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Rory188" patient is selected
And the user navigated to the "Summary" view
And the patient summary view is loaded
And the 'Show on timeline' button is enabled in the footer of the below card:
| group_name | card_index_in_group |
| Labs       | 0                   |
When the user clicks on the 'Show on timeline' button in the footer of the below card:
| group_name | card_index_in_group |
| Labs       | 0                   |
Then the patient timeline is loaded
And the sidebar is displayed
And the sidebar header contains the following data:
| title                                                              | creation_date | report_type |
| Prostate specific Ag [Mass/volume] in Serum or Plasma 6.5733 ng/mL | Sep 21, 2016  | Biomarkers  |

@SRS-155.10
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68484
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Navigate from Summary view to the timeline from 'Lab history' card
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Rory188" patient is selected
And the user navigated to the "Summary" view
And the patient summary view is loaded
And the 'Show on timeline' button is enabled in the footer of the below card:
| group_name | card_index_in_group |
| Labs       | 1                   |
When the user clicks on the 'Show on timeline' button in the footer of the below card:
| group_name | card_index_in_group |
| Labs       | 1                   |
Then the patient timeline is loaded
And the sidebar is displayed
And the sidebar header contains the following data:
| title                         | creation_date | report_type        |
| Basic metabolic panel - Blood | Oct 15, 2019  | Pathology and Labs |

@SRS-155.11
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68484
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Navigate from Summary view to the timeline from 'Oncology note - most recent' card
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Summary" view
And the 'Show on timeline' button is enabled in the footer of the below card:
| group_name    | card_index_in_group |
| Oncology note | 0                   |
When the user clicks on the 'Show on timeline' button in the footer of the below card:
| group_name    | card_index_in_group |
| Oncology note | 0                   |
Then the patient timeline is loaded
And the sidebar is displayed
And the sidebar header contains the following data:
| title          | creation_date | report_type |
| Progress Notes | May 31, 2017  | Encounters  |
