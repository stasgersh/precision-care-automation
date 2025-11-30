Feature: [SysRS-68558] Medications With Unknown Time

Narrative: The system shall display medications with unknown time at the end of the medication list.


@SRS-86.01
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68558
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Medical_History_View
Scenario: Active medications table is sorted by prescription date by default: the first item is the newest medication, and medication with unknown time is at the end of the list
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Rory188" patient is selected
When the user navigates to the "Medical history" view
Then the "Medications" section is available in Medical history
And the active medications table is displayed
And the active medications table content is sorted by "Prescribed" column which has a "downward" pointing arrow

@sanity
@SRS-86.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68558
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Medical_History_View
Scenario: Completed medications table is sorted by prescription date by default: the first item is the newest medication, and medication with unknown time is at the end of the list
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
When the user navigates to the "Medical history" view
And the user opens the Completed medications section
Then the Completed medications table is displayed in the Medications section
And the Completed medications table is sorted by "Prescribed" column which has a "downward" pointing arrow
And the Completed medications table contains the following sorted items:
| Medication name                                                    | Dosage                             | Route           | Frequency                                      | Notes                                                     | Prescribed   |
| Acetaminophen 300 MG / Codeine Phosphate 15 MG Oral Tablet         | Every four hours (qualifier value) | Oral use        | 4 times a day from Jan 30, 2016 - Mar 15, 2016 | [completed] Note test 2, this is from MedicationRequest   | Jan 31, 2016 |
| PACLITAXEL (TAXOL) IN 0.9% NACL CHEMO INFUSION                     | 108 MG                             | Intravenous use | <EMPTY>                                        | [completed] Test note 1, this is from MedicationStatement | Nov 08, 2015 |
| NDA020503 200 ACTUAT Albuterol 0.09 MG/ACTUAT Metered Dose Inhaler | Every four hours                   | Subcutaneous    | 5 times a day from Jan 25, 2015 - Jan 28, 2015 | [completed] Another note                                  | Jan 29, 2015 |

@SRS-86.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68558
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Medical_History_View
Scenario: Sort order of prescription date can be changed to show the oldest medication first and in this case, medication with unknown time is the first item in the list
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Medical history" view
And the "Medications" section is available in Medical history
And the active medications table is displayed
When the user clicks on the "Prescribed" column header in the Medications table
Then the active medications table content is sorted by "Prescribed" column which has a "upward" pointing arrow