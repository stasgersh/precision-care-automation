Feature: [SysRS-68554] Comorbidities

Narrative: The system shall display Comorbidities with Name and Effectivity date.

@sanity
@edge
@SRS-136.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68554
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Medical_History_View
Scenario: The Medical History view displays Comorbidities with Name and Effectivity date
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "Tart, Emilia" patient is selected
And the Patient page is loaded
When the user navigates to the "Medical history" view
Then the Medical history view is displayed
And the "Comorbidities" section is available in Medical history
And the "Comorbidities" section contains the following single table:
| Name                                      | Effective date |
| Bipolar disorder (No current medications) | Mar 27, 2015   |
| Human herpes simplex virus type 2         | Mar 27, 2015   |
| Polycystic ovary syndrome                 | Jan 01, 2007   |

@SRS-136.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68558
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Medical_History_View
Scenario: The Medical History view displays info when Comorbidities were not found
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Empty" patient is selected
And the Patient page is loaded
When the user navigates to the "Medical history" view
Then the Medical history view is displayed
And the "Comorbidities" section is available in Medical history
And the "Comorbidities" section includes the following data in Medical history:
| data_type   | title | value                  | color |
| EMPTY_STATE | N/A   | No Comorbidities found | grey  |
