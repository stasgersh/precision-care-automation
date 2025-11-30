Feature: [SysRS-68559] Allergies And Intolerances

Narrative: The system shall display Allergies and Intolerances with name, type, criticality and observation date.


@SRS-137.01
@sanity
@edge
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68559
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Medical_History_View
Scenario: The Medical History view displays Allergies and Intolerances with name, type, criticality and observation date
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the Patient page is loaded
And the Patient header section is displayed
When the user navigates to the "Medical history" view
Then the Medical history view is displayed
And the "Allergies and intolerances" section is available in Medical history
And the "Allergies and intolerances" section contains the following single table:
| Name                | Type        | Observation date |
| HYDROCHLOROTHIAZIDE | medication  | Apr 11, 2011     |
| Plant phenol oil    | food        | Jul 22, 2018     |
| amoxicillin         | <EMPTY>     | Jan 25, 2021     |
| Pollen              | environment | Nov 13, 2020     |

@SRS-137.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68559
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Medical_History_View
Scenario: The Medical History view displays info when Allergies and Intolerances were not found
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Empty" patient is selected
And the Patient page is loaded
When the user navigates to the "Medical history" view
Then the Medical history view is displayed
And the "Allergies and intolerances" section is available in Medical history
And the "Allergies and intolerances" section includes the following data in Medical history:
| data_type   | title | value                               | color |
| EMPTY_STATE | N/A   | No Allergies and intolerances found | grey  |
