Feature: [SysRS-XXXX] Breast Configuration

Narrative: Placeholder


Background:
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application

@SRS-BREAST.01
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68691
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/System_Configuration
Scenario: Breast cancer related elements are displayed on the patient banner
Given the "Talia, Ember" patient is selected
And the patient summary view is loaded
When the user expands the patient banner
Then the patient banner is expanded
And the following patient information displayed in the patient banner:
| data_type | title                      | value                                           |
| KEY-VALUE | MRN                        | 5c635b8f-ebf8-45cb-bb36-90a3c6a02269            |
| KEY-VALUE | DOB                        | Oct 03, 1965 (59y)                              |
| KEY-VALUE | Gender                     | female                                          |
| KEY-VALUE | Weight                     | 76.9 kg                                         |
| CLP_INFO  | ECOG                       | 1                                               |
| KEY-VALUE | Diagnosis                  | Invasive Mammary Carcinoma                      |
| CLP_INFO  | Histology type             | [keywords]: no special type                     |
| KEY-VALUE | Stage - initial            | Stage IIIC IMC                                  |
| KEY-VALUE | Most Recent Tx             | Tamoxifen 20 mg po daily for a total of 5 years |
| KEY-VALUE | BMI                        | 30 kg/m2                                        |
| KEY-VALUE | BSA                        | 1.8 m2                                          |
| KEY-VALUE | Molecular profile          | ER: positive                                    |
| KEY-VALUE | ER                         | positive                                        |
| KEY-VALUE | PR                         | positive                                        |
| KEY-VALUE | HER2                       | negative                                        |
| KEY-VALUE | CA 125                     | 35.2 [U]/mL                                     |
| CLP_INFO  | Menopausal status          | [keywords]: Menopausal                          |
| LINK      | Comorbidities              | <N/A>                                           |
| LINK      | Allergies and intolerances | <N/A>                                           |

@SRS-BREAST.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68691
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/System_Configuration
Scenario: Breast cancer related elements are displayed on the Summary view
When the user selects the "Talia, Ember" patient
Then the patient summary view is loaded
And the 1st card in "Diagnosis" group equals the following data:
| data_type  | data                                             |
| CARD_TITLE | Diagnosis                                        |
| TABLE      | [source]: summary/table_on_diagnosis_Talia.table |
And the 1st card in "Molecular profile" group equals the following data:
| data_type  | data                                                     |
| CARD_TITLE | Molecular profile                                        |
| TABLE      | [source]: summary/table_on_molecular_profile_Talia.table |
And the 2nd card in "Treatments" group equals the following data:
| data_type   | data                                                         |
| CARD_TITLE  | Radiation therapy                                            |
| NORMAL_TEXT | Procedure - most recent                                      |
| KEY_VALUE   | [key]: Post-mastectomy radiotherapy    [value]: Jun 07, 2010 |
| NORMAL_TEXT | Start of therapy                                             |
| KEY_VALUE   | [key]: Post-mastectomy radiotherapy    [value]: Jun 07, 2010 |
And the 1st card in "Labs" group equals the following data:
| data_type  | data                                    |
| CARD_TITLE | CA-125                                  |
| KEY_VALUE  | [key]: 35.2[U]/mL [value]: Mar 21, 2024 |

@SRS-BREAST.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68691
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/System_Configuration
Scenario: Breast cancer related elements are displayed on the Trends view
Given the "Tart, Emilia" patient is selected
When the user navigates to the "Trends" view
Then the trend graphs are displayed in the following order:
| trend_graph_title                                            |
| Body weight (kg)                                             |
| Carcinoembryonic Ag [Mass/volume] in Serum or Plasma (ng/mL) |
| Cancer Ag 19-9 [Units/volume] in Serum or Plasma (U/mL)      |
| Cancer Ag 15-3 [Units/volume] in Serum or Plasma (U/mL)      |
| Cancer Ag 125 [Units/volume] in Serum or Plasma (U/mL)       |
| Cancer Ag 27-29 [Units/volume] in Serum or Plasma (U/mL)     |