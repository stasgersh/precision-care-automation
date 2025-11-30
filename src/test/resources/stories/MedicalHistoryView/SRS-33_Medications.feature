Feature: [SysRS-68555] Medications

Narrative: The system shall be able to display patients active and completed medications sorted by time with the following info:
             - Medication Name
             - Dosage
             - Route
             - Frequency
             - Notes
             - Prescribed


@sanity
@edge
@SRS-33.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68555,SysRS-68560
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Medical_History_View
Scenario: All active medications are visible for the user by default
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
When the user navigates to the "Medical history" view
Then the "Medications" section is available in Medical history
And the active medications table contains the following sorted items:
| Medication name                                            | Dosage                             | Route           | Frequency                                      | Notes                                                | Prescribed   |
| PACLITAXEL (TAXOL) IN 0.9% NACL CHEMO INFUSION             | 43 MG                              | Intravenous use | <EMPTY>                                        | [active] again, The patient need this drug again     | Jun 19, 2018 |
| Acetaminophen 300 MG / Codeine Phosphate 15 MG Oral Tablet | Every four hours (qualifier value) | Oral use        | 4 times a day from Feb 20, 2018 - Feb 23, 2018 | [active] Note test 2, this is from MedicationRequest | Feb 21, 2018 |
And the Completed medications header is displayed in the Medications section
And the Completed medications table is not displayed in the Medications section

@SRS-33.02
@SPR-4292
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68555
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Medical_History_View
Scenario: Information is displayed in grey color to the user if active medication is not available
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Empty" patient is selected
When the user navigates to the "Medical history" view
Then the "Medications" section includes the following data in Medical history:
| data_type   | title       | value                | color |
| EMPTY_STATE | Medications | No Medications found | grey  |

@SRS-33.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68555
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Medical_History_View
Scenario: The patient's completed medications are available for the user
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
When the user navigates to the "Medical history" view
Then the Completed medications header is displayed in the Medications section
And the Completed medications header contains a badge with the following number: 2
When the user opens the Completed medications section
Then the Completed medications table contains the following sorted items:
| Medication name                          | Dosage            | Route       | Frequency                                      | Notes                               | Prescribed   |
| Simvastatin 10 MG Oral Tablet            | Every eight hours | Oral use    | 4 times a day from Apr 12, 2003 - Sep 19, 2003 | [completed]                         | Apr 12, 2003 |
| CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE | 3 mg              | Intravenous | from Aug 30, 1981                              | [completed] continue in next cycle! | Aug 30, 1981 |

@SRS-33.04
@SPR-4292
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68555
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Medical_History_View
Scenario: Information about not available Completed medications is displayed to the user
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Walter473" patient is selected
When the user navigates to the "Medical history" view
Then the "Medications" section is available in Medical history
And the Completed medications header is displayed in the Medications section
And the Completed medications header contains a badge with the following number: 0
And the "Medications" section includes the following data in Medical history:
| data_type   | title | value                          | color |
| EMPTY_STATE | N/A   | No Completed medications found | grey  |

@SRS-33.05
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68555
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Medical_History_View
Scenario: Information about not available medications is displayed in grey color to the user if neither active nor completed medications exist
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, McKesson" patient is selected
When the user navigates to the "Medical history" view
Then the "Medications" section includes the following data in Medical history:
| data_type   | title | value                | color |
| EMPTY_STATE | N/A   | No Medications found | grey  |

@SRS-33.06
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68555
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Medical_History_View
Scenario: Medications table is sortable by any of the table columns (active medications, sorted by "Notes", Medications with "entered-in-error" status are not displayed)
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Medical history" view
And the Medical history view is displayed
And the active medications table is displayed
And the active medications table content is sorted by "Prescribed" column which has a "downward" pointing arrow
When the user clicks on the "Notes" column header in the Medications table
Then the active medications table content is sorted by "Notes" column which has a "upward" pointing arrow
When the user clicks on the "Notes" column header in the Medications table
Then the active medications table content is sorted by "Notes" column which has a "downward" pointing arrow

@SRS-33.07
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68555
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Medical_History_View
Scenario: Medications table is sortable by any of the table columns (active medications, sorted by "Medication name")
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Medical history" view
And the Medical history view is displayed
And the active medications table is displayed
And the active medications table content is sorted by "Prescribed" column which has a "downward" pointing arrow
When the user clicks on the "Medication name" column header in the Medications table
Then the active medications table content is sorted by "Medication name" column which has a "upward" pointing arrow
When the user clicks on the "Medication name" column header in the Medications table
Then the active medications table content is sorted by "Medication name" column which has a "downward" pointing arrow

@SRS-33.08
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68555
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Medical_History_View
Scenario: Medications table is sortable by any of the table columns (active medications, sorted by "Route")
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Medical history" view
And the Medical history view is displayed
And the active medications table is displayed
And the active medications table content is sorted by "Prescribed" column which has a "downward" pointing arrow
When the user clicks on the "Route" column header in the Medications table
Then the active medications table content is sorted by "Route" column which has a "upward" pointing arrow
When the user clicks on the "Route" column header in the Medications table
Then the active medications table content is sorted by "Route" column which has a "downward" pointing arrow

@SRS-33.09
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68555
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Medical_History_View
Scenario: Medications table is sortable by any of the table columns ( Completed medications, sorted by "Frequency")
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Medical history" view
And the Medical history view is displayed
And the Completed medications section is opened
And the Completed medications table is sorted by "Prescribed" column which has a "downward" pointing arrow
When the user clicks on the "Frequency" column header in the Completed medications table
Then the Completed medications table is sorted by "Frequency" column which has a "upward" pointing arrow
And the Completed medications table contains the following sorted items:
| Medication name                                                    | Dosage                             | Route           | Frequency                                      | Notes                                                     | Prescribed   |
| PACLITAXEL (TAXOL) IN 0.9% NACL CHEMO INFUSION                     | 108 MG                             | Intravenous use | <EMPTY>                                        | [completed] Test note 1, this is from MedicationStatement | Nov 08, 2015 |
| Acetaminophen 300 MG / Codeine Phosphate 15 MG Oral Tablet         | Every four hours (qualifier value) | Oral use        | 4 times a day from Jan 30, 2016 - Mar 15, 2016 | [completed] Note test 2, this is from MedicationRequest   | Jan 31, 2016 |
| NDA020503 200 ACTUAT Albuterol 0.09 MG/ACTUAT Metered Dose Inhaler | Every four hours                   | Subcutaneous    | 5 times a day from Jan 25, 2015 - Jan 28, 2015 | [completed] Another note                                  | Jan 29, 2015 |
When the user clicks on the "Frequency" column header in the Completed medications table
Then the Completed medications table is sorted by "Frequency" column which has a "downward" pointing arrow
And the Completed medications table contains the following sorted items:
| Medication name                                                    | Dosage                             | Route           | Frequency                                      | Notes                                                     | Prescribed   |
| NDA020503 200 ACTUAT Albuterol 0.09 MG/ACTUAT Metered Dose Inhaler | Every four hours                   | Subcutaneous    | 5 times a day from Jan 25, 2015 - Jan 28, 2015 | [completed] Another note                                  | Jan 29, 2015 |
| Acetaminophen 300 MG / Codeine Phosphate 15 MG Oral Tablet         | Every four hours (qualifier value) | Oral use        | 4 times a day from Jan 30, 2016 - Mar 15, 2016 | [completed] Note test 2, this is from MedicationRequest   | Jan 31, 2016 |
| PACLITAXEL (TAXOL) IN 0.9% NACL CHEMO INFUSION                     | 108 MG                             | Intravenous use | <EMPTY>                                        | [completed] Test note 1, this is from MedicationStatement | Nov 08, 2015 |

@SRS-33.10
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68555
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Medical_History_View
Scenario: Medications table is sortable by any of the table columns (Completed medications, sorted by "Dosage")
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Medical history" view
And the Medical history view is displayed
And the Completed medications section is opened
And the Completed medications table is sorted by "Prescribed" column which has a "downward" pointing arrow
When the user clicks on the "Dosage" column header in the Completed medications table
Then the Completed medications table is sorted by "Dosage" column which has a "upward" pointing arrow
And the Completed medications table contains the following sorted items:
| Route           | Medication name                                                    | Dosage                             | Frequency                                      | Notes                                                     | Prescribed   |
| Intravenous use | PACLITAXEL (TAXOL) IN 0.9% NACL CHEMO INFUSION                     | 108 MG                             | <EMPTY>                                        | [completed] Test note 1, this is from MedicationStatement | Nov 08, 2015 |
| Subcutaneous    | NDA020503 200 ACTUAT Albuterol 0.09 MG/ACTUAT Metered Dose Inhaler | Every four hours                   | 5 times a day from Jan 25, 2015 - Jan 28, 2015 | [completed] Another note                                  | Jan 29, 2015 |
| Oral use        | Acetaminophen 300 MG / Codeine Phosphate 15 MG Oral Tablet         | Every four hours (qualifier value) | 4 times a day from Jan 30, 2016 - Mar 15, 2016 | [completed] Note test 2, this is from MedicationRequest   | Jan 31, 2016 |
When the user clicks on the "Dosage" column header in the Completed medications table
Then the Completed medications table is sorted by "Dosage" column which has a "downward" pointing arrow
And the Completed medications table contains the following sorted items:
| Route           | Medication name                                                    | Dosage                             | Frequency                                      | Notes                                                     | Prescribed   |
| Oral use        | Acetaminophen 300 MG / Codeine Phosphate 15 MG Oral Tablet         | Every four hours (qualifier value) | 4 times a day from Jan 30, 2016 - Mar 15, 2016 | [completed] Note test 2, this is from MedicationRequest   | Jan 31, 2016 |
| Subcutaneous    | NDA020503 200 ACTUAT Albuterol 0.09 MG/ACTUAT Metered Dose Inhaler | Every four hours                   | 5 times a day from Jan 25, 2015 - Jan 28, 2015 | [completed] Another note                                  | Jan 29, 2015 |
| Intravenous use | PACLITAXEL (TAXOL) IN 0.9% NACL CHEMO INFUSION                     | 108 MG                             | <EMPTY>                                        | [completed] Test note 1, this is from MedicationStatement | Nov 08, 2015 |
