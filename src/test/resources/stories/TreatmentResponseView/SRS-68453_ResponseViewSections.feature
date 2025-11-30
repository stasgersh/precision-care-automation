
Feature: [SysRS-68453] Response view sections

Narrative: The system shall provide “Response” view page with the following sections:
             - Most recent Treatment
               - Treatment card.
               - Response tracking
               - Select points to compare.
               - Adverse events
               - Trend charts
               - Measurements
               -  EPIC-26 QOL


@sanity
@edge
@ai_sanity
@manual
@CLP
@SRS-174.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68453,SysRS-69190,SysRS-69508
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Manual/Functional_Service/Treatment_Response_view
Scenario: User can see the Treatment and response view in two columns
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And [API] the Treatment Response settings were reset for "OncoTestPatient, Juno" by the [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the browser size is set to: width=1920, height=1080
When the "OncoTestPatient, Juno" patient is selected
And the patient summary view is loaded
And the "Patient status" button was clicked
And the Treatment and response view is loaded
And the following imaging study was selected for study A:
| name                     | date         |
| Diagnostic imaging study | Aug 19, 2022 |
And the following imaging study was selected for study B:
| name                                                     | date         |
| Examination of joint under image intensifier (procedure) | Sep 09, 2022 |
Then I see the following treatment cards in "Treatment" group on the left side as:
| card_title                  | card_date    | card_content                                                     | link                 |
| Systemic therapy            | Feb 24, 2019 | [source]: responseView/table_on_systemic_therapy_card_Juno.table | N/A                  |
| Radiation therapy           | Aug 15, 2018 | [source]: responseView/radiation_therapy_Juno.table              | N/A                  |
| Oncology note - most recent | May 31, 2017 | Progress Notes                                                   | Open complete report |
And I see the "Response" group on the right side as:
| study_badge     | study_date   | study_name                                               | link                         |
| Imaging study A | Aug 19, 2022 | Diagnostic imaging study                                 | [LINK]: Open complete report |
| Imaging study B | Sep 09, 2022 | Examination of joint under image intensifier (procedure) | [LINK]: Open complete report |
And the study content contains the following key words:
| study_badge     | key_words                                              |
| Imaging study A | N/A                                                    |
| Imaging study B | partial tear, greater tuberosity, degenerative changes |
And the Measurements table is displayed with the following details:
| measure                   | A_badge_value           | A_badge_date | B_badge_value | B_badge_date | diff_badge |
| Body Weight               | No value within 30 days | <N/A>        | 47.3 kg       | Jan 23, 2013 | <N/A>      |
| Prostate specific antigen | No value within 30 days | <N/A>        | 2.3 ng/mL     | Jul 19, 2017 | <N/A>      |
And the following lines are present on the chart:
| name                      | unit  |
| Body Weight               | kg    |
| Prostate specific antigen | ng/mL |

@sanity
@manual
@SRS-174.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68453,SysRS-69505
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Manual/Functional_Service/Treatment_Response_view
Scenario: Most recent Treatment cards are displaying the same content as on the Summary page
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the patient summary view is loaded
And the 1st card in "Treatments" group equals the following data:
| data_type  | data                                                           |
| CARD_TITLE | Systemic therapy                                               |
| TABLE      | [source]: summary/table_on_systemic_therapy_card_Torvald.table |
| KEY_VALUE  | [key]: Date            [value]: Feb 24, 2014                   |
And the 1st card in "Oncology note" group equals the following data:
| data_type   | data                                        |
| CARD_TITLE  | Oncology note - most recent                 |
| NORMAL_TEXT | History and physical note                   |
| KEY_VALUE   | [key]: Date           [value]: Apr 22, 2021 |
And the 2st card in "Treatments" group equals the following data:
| data_type   | data                       |
| EMPTY_STATE | No radiation therapy found |
When the user clicks "Patient status" button
Then the Treatment and response view is loaded
And the "Systemic therapy" treatment card contains the following data:
| data_type  | data                                                           |
| CARD_TITLE | Systemic therapy                                               |
| DATE       | Feb 24, 2014                                                   |
| TABLE      | [source]: summary/table_on_systemic_therapy_card_Torvald.table |
And the "Radiation therapy" treatment card contains the following data:
| data_type   | data                       |
| EMPTY_STATE | No radiation therapy found |
And the "Oncology note - most recent" treatment card contains the following data:
| data_type   | data                        |
| CARD_TITLE  | Oncology note - most recent |
| DATE        | Apr 22, 2021                |
| NORMAL_TEXT | History and physical note   |

@srs
@SRS-174.03
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68453,SysRS-69509
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Treatment_Response_View
Scenario: View patient truncated information in Response tracking option
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the Patient page is loaded
And the "Status and response" button was clicked
And the Treatment and response view is loaded
And the following imaging study was selected for study A:
| name                                   | date         |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011 |
And the following imaging study was selected for study B:
| name              | date         |
| XR CHEST PA OR AP | Aug 18, 2022 |
Then the study A card has truncated text
When the user clicks "Show more" of "XR CHEST PA OR AP" at imaging study A
Then the study A card does not have truncated text
When the user clicks "Show less" of "XR CHEST PA OR AP" at imaging study A
Then the study A card has truncated text

@srs
@SRS-174.05
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68453,SysRS-69509
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Treatment_Response_View
Scenario: Check in response view hidden content
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
When the user clicks "Status and response" button
Then the Treatment and response view is loaded
When the user shrinks browser window
And the user clicks on Expand button at TreatmentCard
Then the TreatmentCard state changed

@srs
@SRS-174.06
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68453,SysRS-69506,SysRS-69191
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Treatment_Response_View
Scenario: Check in response view reverse alert
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
When the user clicks "Status and response" button
Then the Treatment and response view is loaded
When the user clicks on the Swap button on the Study selector module
Then the alert message presented: A and B are in reverse chronological order


