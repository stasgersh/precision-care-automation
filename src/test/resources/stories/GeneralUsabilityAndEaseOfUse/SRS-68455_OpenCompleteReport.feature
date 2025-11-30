Feature: [SysRS-68455] Open Complete Report

Narrative: The system shall provide Open complete report functionality whenever report is available and for the following:
             - Summary dashboard view: Cards and Progressive summarization
             - Response tracking view
             - Clinical trial eligibility view
             - Treatment adherence view


@SRS-176.01
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68455
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Treatment_Response_View
Scenario: Open full report modal of the most recent Oncology note on Response view
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the patient summary view is loaded
And the "Status and response" button was clicked
And the Treatment and response view is loaded
And the "Oncology note - most recent" treatment card contains the following data:
| data_type   | data                        |
| CARD_TITLE  | Oncology note - most recent |
| DATE        | May 31, 2017                |
| NORMAL_TEXT | Progress Notes              |
When the user clicks "Open complete report" of "Oncology note - most recent" treatment card
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title          | creation_date | author                        | status  |
| Progress Notes | May 31, 2017  | Physician Family Medicine, MD | current |
And the full report modal body contains the following data: L3/OncoTestPatient_Juno_Encounter_ProgressNotes_fromBinary.txt

@SRS-176.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68455
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Treatment_Response_View
Scenario: Open full report modal of an Imaging study on Response view
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the patient summary view is loaded
And the "Status and response" button was clicked
And the Treatment and response view is loaded
And the following imaging study was selected for study A:
| name              | date         |
| XR CHEST PA OR AP | Aug 18, 2022 |
When the user clicks "Open complete report" of "XR CHEST PA OR AP" at imaging study A
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title             | creation_date | author    | status |
| XR CHEST PA OR AP | Aug 18, 2022  | No author | final  |
And the full report modal body contains the following data: L3/OncoTestPatient_Juno_XR_CHEST_PA_OR_AP_FullReport.txt
When the user clicks on the 'X' button on the full report modal
And the user selects the following imaging study for study B:
| name                                   | date         |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011 |
And the user clicks "Open complete report" of "CT THORAX ABDOMEN PELVIS WITH CONTRAST" at imaging study B
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title                                  | creation_date | author    | status |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011  | No author | final  |
And the full report modal body contains the following data: L3/OncoTestPatient_Juno_CT_THORAX_ABDOMEN_FullReport.txt
