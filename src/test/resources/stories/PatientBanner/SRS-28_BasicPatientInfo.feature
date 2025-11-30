@safety
Feature: [SysRS-68429] Basic Patient Info

Narrative: The system shall display the following basic information of the selected patient:
             - Patient ID
             - DOB
             - Gender
             - Weight
             - Age
             - ECOG grade
             - Diagnosis
             - Initial Stage: "Stage" + group stage (I, II, III, IV) with TNM staging (format example: Stage IV T4N3M1)).
             - Last Treatment (Tx)
             - BMI
             - BSA
             - Molecular Profile
             - Comorbidities
             - Allergies and intolerances
             - PSA (display in prostate cancer mode only)
             - Histology type
             - Gleason Score (display in prostate cancer mode only)


@sanity
@edge
@SRS-28.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68390,SysRS-68429,SysRS-68407
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Patient_Banner
Scenario: Display patient banner when all required parameters are available of the selected patient except CLP only data (Note: PID displayed based on predefined config)
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the Patient header section is displayed
When the user selects the "OncoTestPatient, Juno" patient
Then the patient summary view is loaded
And the following patient information displayed in the patient banner:
| data_type | title                      | value                                         |
| KEY-VALUE | MRN                        | 011-233-455                                   |
| KEY-VALUE | DOB                        | Mar 21, 1989 (<<ACTUAL_AGE>>y)                |
| KEY-VALUE | Gender                     | female                                        |
| KEY-VALUE | Weight                     | 47.3 kg                                       |
| KEY-VALUE | ECOG                       | 3                                             |
| KEY-VALUE | Diagnosis                  | Malignant neoplasm of left breast (CMS/HCC)   |
| NOT_FOUND | Histology type             | No Histology type found                       |
| KEY-VALUE | Gleason score              | 8 (5 + 3)                                     |
| KEY-VALUE | Stage - initial            | T2aN0M0                                       |
| KEY-VALUE | Most Recent Tx             | cyclophosphamide 1 GM Injection               |
| KEY-VALUE | BMI                        | 21.27 kg/m2                                   |
| KEY-VALUE | BSA                        | 1.37 m2                                       |
| KEY-VALUE | PSA                        | 2.3 ng/mL                                     |
| KEY-VALUE | Molecular profile          | Estrogen Receptor: Positive (qualifier value) |
| KEY-VALUE | ER                         | Positive (qualifier value)                    |
| KEY-VALUE | HER2                       | Negative (qualifier value)                    |
| LINK      | Comorbidities              | <N/A>                                         |
| LINK      | Allergies and intolerances | <N/A>                                         |

@CLP
@sanity
@ai_sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68429
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Patient_Banner
Scenario: Display patient banner when all required parameters are available of the selected patient including CLP only data (Histology type)
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the Patient header section is displayed
When the user selects the "OncoTestPatient, Juno" patient
Then the following patient information displayed in the patient banner:
| data_type | title                      | value                                         |
| KEY-VALUE | MRN                        | 011-233-455                                   |
| KEY-VALUE | DOB                        | Mar 21, 1989 (<<ACTUAL_AGE>>y)                |
| KEY-VALUE | Gender                     | female                                        |
| KEY-VALUE | Weight                     | 47.3 kg                                       |
| KEY-VALUE | ECOG                       | 3                                             |
| KEY-VALUE | Diagnosis                  | Malignant neoplasm of left breast (CMS/HCC)   |
| CLP_INFO  | Histology type             | [keywords]: carcinoma                         |
| KEY-VALUE | Gleason score              | 8 (5 + 3)                                     |
| KEY-VALUE | Stage - initial            | T2aN0M0                                       |
| KEY-VALUE | Most Recent Tx             | cyclophosphamide 1 GM Injection               |
| KEY-VALUE | BMI                        | 21.27 kg/m2                                   |
| KEY-VALUE | BSA                        | 1.37 m2                                       |
| KEY-VALUE | PSA                        | 2.3 ng/mL                                     |
| KEY-VALUE | Molecular profile          | Estrogen Receptor: Positive (qualifier value) |
| KEY-VALUE | ER                         | Positive (qualifier value)                    |
| KEY-VALUE | HER2                       | Negative (qualifier value)                    |
| LINK      | Comorbidities              | <N/A>                                         |
| LINK      | Allergies and intolerances | <N/A>                                         |

@sanity
@SRS-28.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68429,SysRS-70330
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Patient_Banner
Scenario: Display patient banner when NOT all required parameters are available (PSA, Gleason, BMI and BSA score not displayed with empty state)
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the Patient header section is displayed
When the user selects the "OncoTestPatient, Empty" patient
Then the following patient information displayed in the patient banner:
| data_type | title                      | value                                | color |
| KEY-VALUE | MRN                        | 05b46423-4e30-47a3-b30d-cd7e9ff31e94 | <N/A> |
| KEY-VALUE | DOB                        | Jul 21, 1990 (<<ACTUAL_AGE>>y)       | <N/A> |
| KEY-VALUE | Gender                     | female                               | <N/A> |
| NOT_FOUND | Weight                     | No Weight found                      | grey  |
| NOT_FOUND | ECOG                       | No ECOG found                        | grey  |
| NOT_FOUND | Diagnosis                  | No Diagnosis found                   | grey  |
| NOT_FOUND | Histology type             | No Histology type found              | grey  |
| NOT_FOUND | Stage - initial            | No Stage - initial found             | grey  |
| NOT_FOUND | Most Recent Tx             | No Most Recent Tx found              | grey  |
| NOT_FOUND | Molecular profile          | No Molecular profile found           | grey  |
| LINK      | Comorbidities              | <N/A>                                | <N/A> |
| LINK      | Allergies and intolerances | <N/A>                                | <N/A> |

@SRS-28.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68429
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Patient_Banner
Scenario: Navigate to patient comorbidities from the patient banner when patient comorbidities are available
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the Patient header section is displayed
When the user selects the "Tart, Emilia" patient
Then the patient banner includes the following data:
| data_type | title         | value |
| LINK      | Comorbidities | <N/A> |
When the user clicks on the following link in the patient banner: "Comorbidities"
Then the Medical history view is displayed
And the "Comorbidities" section is available in Medical history
And the "Comorbidities" section contains the following single table:
| Name                                      | Effective date |
| Bipolar disorder (No current medications) | Mar 27, 2015   |
| Human herpes simplex virus type 2         | Mar 27, 2015   |
| Polycystic ovary syndrome                 | Jan 01, 2007   |

@SRS-28.04
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68429
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Patient_Banner
Scenario: Navigate to patient comorbidities from the patient banner when patient comorbidities are not available
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the Patient header section is displayed
When the user selects the "OncoTestPatient, Empty" patient
Then the patient banner includes the following data:
| data_type | title         | value |
| LINK      | Comorbidities | <N/A> |
When the user clicks on the following link in the patient banner: "Comorbidities"
Then the Medical history view is displayed
And the "Comorbidities" section is available in Medical history
And the "Comorbidities" section includes the following data in Medical history:
| data_type   | title | value                  | color |
| EMPTY_STATE | N/A   | No Comorbidities found | grey  |

@SRS-28.05
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68429
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Patient_Banner
Scenario: Navigate to patient allergies and intolerances from the patient banner when any patient allergies or intolerances are available
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the Patient header section is displayed
When the user selects the "OncoTestPatient, Juno" patient
Then the patient banner includes the following data:
| data_type | title                      | value |
| LINK      | Allergies and intolerances | <N/A> |
When the user clicks on the following link in the patient banner: "Allergies and intolerances"
Then the Medical history view is displayed
And the "Allergies and intolerances" section is available in Medical history
And the "Allergies and intolerances" section contains the following single table:
| Name                | Type        | Observation date |
| HYDROCHLOROTHIAZIDE | medication  | Apr 11, 2011     |
| Plant phenol oil    | food        | Jul 22, 2018     |
| amoxicillin         | <EMPTY>     | Jan 25, 2021     |
| Pollen              | environment | Nov 13, 2020     |

@SRS-28.06
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68429
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Patient_Banner
Scenario: Navigate to patient allergies and intolerances from the patient banner when patient allergies and intolerances are not available
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the Patient header section is displayed
When the user selects the "OncoTestPatient, Empty" patient
Then the patient banner includes the following data:
| data_type | title                      | value |
| LINK      | Allergies and intolerances | <N/A> |
When the user clicks on the following link in the patient banner: "Allergies and intolerances"
Then the Medical history view is displayed
And the "Allergies and intolerances" section is available in Medical history
And the "Allergies and intolerances" section includes the following data in Medical history:
| data_type   | title | value                               | color |
| EMPTY_STATE | N/A   | No Allergies and intolerances found | grey  |

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68429
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Patient_Banner
Scenario: Display patient banner tooltip when users hover on truncated banner text
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
When the user expands the patient banner
And the user moves the mouse over on the value of the following patient banner item:
| data_type | title     | value                                       |
| KEY-VALUE | Diagnosis | Malignant neoplasm of left breast (CMS/HCC) |
Then the banner tooltip displays the following text: "Malignant neoplasm of left breast (CMS/HCC)"
When the user moves the mouse over on the "more" link of the following patient banner item:
| data_type | title     | value                                       |
| KEY-VALUE | Diagnosis | Malignant neoplasm of left breast (CMS/HCC) |
Then the banner tooltip displays the following text: "Malignant neoplasm of left breast (CMS/HCC)"
When the user moves the mouse over on the value of the following patient banner item:
| data_type | title          | value                  |
| CLP_INFO  | Histology type | <HIGHLIGHTED_AI_VALUE> |
Then the banner tooltip displays the full value of the following patient banner item:
| data_type | title          | value                  |
| CLP_INFO  | Histology type | <HIGHLIGHTED_AI_VALUE> |
When the user moves the mouse over on the "more" link of the following patient banner item:
| data_type | title          | value                  |
| CLP_INFO  | Histology type | <HIGHLIGHTED_AI_VALUE> |
Then the banner tooltip displays the full value of the following patient banner item:
| data_type | title          | value                  |
| CLP_INFO  | Histology type | <HIGHLIGHTED_AI_VALUE> |
