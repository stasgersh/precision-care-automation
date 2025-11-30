@safety
Feature: [SysRS-68557] Collapsed Completed Medications

Narrative: The system shall collapse completed medication info by default to avoid mixing up active and completed medications.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected


@SRS-83.01
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68557
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Medical_History_View
Scenario: The patient's completed medications are not visible for the user by default
When the user navigates to the "Medical history" view
Then the Completed medications header is displayed in the Medications section
And the Completed medications table is not displayed in the Medications section

@SRS-83.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68557
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Medical_History_View
Scenario: The user has the possibility to open patient's completed medications section
When the user navigates to the "Medical history" view
Then the Completed medications section has a button to expand the data

@SRS-83.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68557
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Medical_History_View
Scenario: Completed medications are not visible after closing the completed medications section
Given the user navigated to the "Medical history" view
And the Completed medications section is opened
And the Completed medications table contains the following sorted items:
| Medication name                                                    | Dosage                             | Route           | Frequency                                      | Notes                                                     | Prescribed   |
| Acetaminophen 300 MG / Codeine Phosphate 15 MG Oral Tablet         | Every four hours (qualifier value) | Oral use        | 4 times a day from Jan 30, 2016 - Mar 15, 2016 | [completed] Note test 2, this is from MedicationRequest   | Jan 31, 2016 |
| PACLITAXEL (TAXOL) IN 0.9% NACL CHEMO INFUSION                     | 108 MG                             | Intravenous use | <EMPTY>                                        | [completed] Test note 1, this is from MedicationStatement | Nov 08, 2015 |
| NDA020503 200 ACTUAT Albuterol 0.09 MG/ACTUAT Metered Dose Inhaler | Every four hours                   | Subcutaneous    | 5 times a day from Jan 25, 2015 - Jan 28, 2015 | [completed] Another note                                  | Jan 29, 2015 |
And the Completed medications section has a button to collapse the data
When the user closes the Completed medications section
Then the Completed medications table is not displayed in the Medications section
And the Completed medications section has a button to expand the data
