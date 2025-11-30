Feature: [SysRS-68485] Summary View Displays Report Date

Narrative: The system shall display the date of the report on the following Summary view cards:
             - Key events
             - Most recent imaging
             - Imaging history
             - Most recent MDT report
             - Radiation therapy
             - Systemic therapy
             - Lab history (list of lab tests performed)
             - Most recent oncology note


@SRS-156.01
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68485,SysRS-68493
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Date is displayed on 'Radiation therapy' card on Summary view
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Juno" patient
Then the patient summary view is loaded
And the 2nd card in "Treatments" group equals the following data:
| data_type   | data                                                             |
| CARD_TITLE  | Radiation therapy                                                |
| NORMAL_TEXT | Procedure - most recent                                          |
| KEY_VALUE   | [key]: Radiotherapy course of treatment    [value]: Aug 15, 2018 |
| NORMAL_TEXT | Start of therapy                                                 |
| KEY_VALUE   | [key]: Radiotherapy course of treatment    [value]: Aug 15, 2018 |

@sanity
@SRS-156.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68485,SysRS-68493
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View
Scenario: Date is displayed on 'Radiation therapy' card on Summary view (start and end date of therapy)
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Juno" patient
Then the patient summary view is loaded
And the 3st card in "Treatments" group equals the following data:
| data_type  | data                                                                                                                             |
| CARD_TITLE | Radiation therapy                                                                                                                |
| KEY_VALUE  | [key]: Site of therapy     [value]: Chest Wall Structure (body structure)                                                        |
| KEY_VALUE  | [key]: Phases              [value]: 2                                                                                            |
| KEY_VALUE  | [key]: Modalities          [value]: External beam radiation therapy using photons (procedure) • Teleradiotherapy using electrons |
| TABLE      | [source]: summary/table_on_radiation_therapy_card_Juno.table                                                                     |
| KEY_VALUE  | [key]: Start of therapy    [value]: Aug 15, 2018                                                                                 |
| KEY_VALUE  | [key]: End of therapy      [value]: Oct 25, 2018                                                                                 |

@SRS-156.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68485
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Date is displayed on 'Systemic therapy' card on Summary view
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Torvald" patient
Then the patient summary view is loaded
And the 1st card in "Treatments" group equals the following data:
| data_type  | data                                                           |
| CARD_TITLE | Systemic therapy                                               |
| TABLE      | [source]: summary/table_on_systemic_therapy_card_Torvald.table |
| KEY_VALUE  | [key]: Date            [value]: Feb 24, 2014                   |

@SRS-156.04
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68485
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Date is displayed on 'Lab history' card on Summary view
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Torvald" patient
Then the patient summary view is loaded
And the 1st card in "Labs" group equals the following data:
| data_type   | data                                              |
| CARD_TITLE  | Lab history                                       |
| NORMAL_TEXT | From Mar 07, 2017 through Jun 07, 2017            |
| LINK        | [link]: Cholesterol, total   [date]: Jun 07, 2017 |

@SRS-156.05
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68479
@Description:Check_data_in_'Lab_History'_card_are_displayed_in_descending_chronological_order<br/><ul><li>Labs</li><li>Lab_history_card</li></ul>,card_are_displayed_in_descending_chronological_order_successfully
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
Scenario: Check data in 'Lab History' card are displayed in descending chronological order
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Torvald" patient
Then the patient summary view is loaded
And the "Lab history" are displayed in descending chronological order in "Labs" group

@SRS-156.06
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68479
@Description:Check_data_in_'ER_visit'_card_are_displayed_in_descending_chronological_order<br/><ul><li>ER_visit</li></ul>,card_are_displayed_in_descending_chronological_order_successfully
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
Scenario: Check data in 'ER visit' cards are displayed in descending chronological order
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Torvald"  patient
Then the patient summary view is loaded
And the "ER visit - most recent" are displayed in descending chronological order in "ER visit" group

@SRS-156.07
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68479
@Description:Check_data_in_'MDT_report'_card_are_displayed_in_descending_chronological_order<br/><ul><li>MDT_report</li></ul>,card_are_displayed_in_descending_chronological_order_successfully
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
Scenario: Check data in 'MDT report' cards are displayed in descending chronological order
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Juno"  patient
Then the patient summary view is loaded
And the "MDT report - most recent" are displayed in descending chronological order in "MDT report" group

@SRS-156.08
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68479
@Description:Check_data_in_'Oncology_note'_card_are_displayed_in_descending_chronological_order<br/><ul><li>Oncology_note</li></ul>,card_are_displayed_in_descending_chronological_order_successfully
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
Scenario: Check data in 'Oncology note' cards are displayed in descending chronological order
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Juno"  patient
Then the patient summary view is loaded
And the "Oncology note - most recent" are displayed in descending chronological order in "Oncology note" group

@SRS-156.09
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68479
@Description:Check_data_in_'Imaging'_card_are_displayed_in_descending_chronological_order<br/><ul><li>Imaging_-_most_recent</li><li>Imaging_history</li></ul>,card_are_displayed_in_descending_chronological_order_successfully
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
Scenario: Check data in 'Imaging' cards are displayed in descending chronological order
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Juno"  patient
Then the patient summary view is loaded
And the "Imaging - most recent" are displayed in descending chronological order in "Imaging" group
And the "Imaging history" are displayed in descending chronological order in "Imaging" group

@SRS-156.10
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68479
@Description:Check_data_in_'Diagnosis'_card_are_displayed_in_descending_chronological_order<br/><ul><li>Diagnosis</li><li>Key_events</li></ul>,card_are_displayed_in_descending_chronological_order_successfully
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
Scenario: Check data in 'Diagnosis' cards are displayed in descending chronological order
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Juno"  patient
Then the patient summary view is loaded
And the "Diagnosis" are displayed in descending chronological order in "Diagnosis" group
And the "Key events" are displayed in descending chronological order in "Diagnosis" group

@SRS-156.11
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68479
@Description:Check_data_in_'Molecular_profile'_card_are_displayed_in_descending_chronological_order<br/><ul><li>Molecular_profile</li></ul>,card_are_displayed_in_descending_chronological_order_successfully
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
Scenario: Check data in 'Molecular profile' cards are displayed in descending chronological order
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Juno"  patient
Then the patient summary view is loaded
And the "Molecular profile" are displayed in descending chronological order in "Molecular profile" group