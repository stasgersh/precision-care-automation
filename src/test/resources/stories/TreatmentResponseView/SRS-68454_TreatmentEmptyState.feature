Feature: [SysRS-68454] Treatment Empty state

Narrative: The system shall provide empty state if there is no data for the Systemic therapy,  Radiation therapy, Most recent oncology note events or for the selected imaging study.


@sanity
@edge
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68454
@SRS-175.01
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Treatment_Response_View
Scenario: OncoCare displays an empty state if no data is available for Treatment cards
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Empty" patient is selected
And the patient summary view is loaded
When the user clicks "Status and response" button
Then the Treatment and response view is loaded
And the "Systemic therapy" treatment card contains the following data:
| data_type  | data                      |
| CARD_TITLE | Systemic therapy          |
| EMPTY_TEXT | No systemic therapy found |
And the "Radiation therapy" treatment card contains the following data:
| data_type  | data                       |
| CARD_TITLE | Radiation therapy          |
| EMPTY_TEXT | No radiation therapy found |
And the "Oncology note - most recent" treatment card contains the following data:
| data_type  | data                        |
| CARD_TITLE | Oncology note - most recent |
| EMPTY_TEXT | No oncology note found      |

@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68454,SysRS-68458
@SRS-175.02
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Treatment_Response_View
Scenario: OncoCare displays an empty state if no Imaging study is available
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Empty" patient is selected
And the patient summary view is loaded
When the user clicks "Patient status" button
Then the Treatment and response view is loaded
And the Response column displays the following message:
| title                                   | message                                                                   | button          |
| Not enough information to show Response | This view will become active when there are at least 2 studies available. | Back to Summary |
And the imaging study selector of A is disabled
And the imaging study selector of B is disabled

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68454
@SRS-175.03
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Treatment_Response_View/Treatment_Empty_state
Scenario: OncoCare displays an empty state if no Biomarker or Weight trends are available
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the patient summary view is loaded
When the user clicks "Status and response" button
Then the Treatment and response view is loaded
And the Response column displays the following text for "Trends" section: "No biomarker and weight trends found"
