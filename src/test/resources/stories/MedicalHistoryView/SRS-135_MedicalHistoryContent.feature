Feature: [SysRS-68553] Medical History Content

Narrative: The system’s Medical History view shall contain:
             - Comorbidities.
             - Medications.
             - Allergies and Intolerances.
             - Patient History info.


@sanity
@edge
@SRS-135.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68553
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Medical_History_View
Scenario: OncoCare's Medical History view contains Comorbidities, Medications, Allergies and Intolerances and Patient History info
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the Patient page is loaded
When the user navigates to the "Medical history" view
Then the Medical history view is displayed
And the "Comorbidities" section is available in Medical history
And the "Comorbidities" section includes the following data in Medical history:
| data_type   | title | value                  | color |
| EMPTY_STATE | N/A   | No Comorbidities found | grey  |
And the "Medications" section is available in Medical history
And the active medications table contains the following sorted items:
| Medication name                                            | Dosage                             | Route           | Frequency                                      | Notes                                                | Prescribed   |
| PACLITAXEL (TAXOL) IN 0.9% NACL CHEMO INFUSION             | 43 MG                              | Intravenous use | <EMPTY>                                        | [active] again, The patient need this drug again     | Jun 19, 2018 |
| Acetaminophen 300 MG / Codeine Phosphate 15 MG Oral Tablet | Every four hours (qualifier value) | Oral use        | 4 times a day from Feb 20, 2018 - Feb 23, 2018 | [active] Note test 2, this is from MedicationRequest | Feb 21, 2018 |
And the Completed medications header is displayed in the Medications section
And the "Allergies and intolerances" section is available in Medical history
And the "Allergies and intolerances" section includes the following data in Medical history:
| data_type   | title | value                               | color |
| EMPTY_STATE | N/A   | No Allergies and intolerances found | grey  |
And the "Patient history" section is available in Medical history
And the "Patient history" section contains the following data in Medical history:
| data_type     | title            | value        |
| SECTION_TITLE | Patient history  | N/A          |
| TABLE         | Surgical history | <DATA_TABLE> |
| KEY-VALUE     | Marital status   | M            |
And the "Patient history" section contains the following "Surgical history" table:
| Surgery name                                | Date         |
| Hysteroscopy                                | Apr 04, 2021 |
| Excision of sentinel lymph node (procedure) | Jun 14, 2017 |

@SRS-135.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68558
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Medical_History_View
Scenario: OncoCare's Medical History view displays the info about not found data in grey color
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Empty" patient is selected
And the Patient page is loaded
When the user navigates to the "Medical history" view
Then the Medical history view is displayed
And the "Comorbidities" section includes the following data in Medical history:
| data_type   | title | value                  | color |
| EMPTY_STATE | N/A   | No Comorbidities found | grey  |
Then the "Medications" section includes the following data in Medical history:
| data_type   | title | value                | color |
| EMPTY_STATE | N/A   | No Medications found | grey  |
And the "Allergies and intolerances" section includes the following data in Medical history:
| data_type   | title | value                               | color |
| EMPTY_STATE | N/A   | No Allergies and intolerances found | grey  |
Then the "Patient history" section contains the following data in Medical history:
| data_type     | title           | value                    | color |
| SECTION_TITLE | Patient history | N/A                      | grey  |
| EMPTY_STATE   | N/A             | No Patient history found | grey  |
