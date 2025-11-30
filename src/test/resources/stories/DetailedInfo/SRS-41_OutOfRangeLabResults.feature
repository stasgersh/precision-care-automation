Feature: [SysRS-68547] Out Of Range Lab Results

Narrative: The system shall be able to visualize out of range lab results on the side panel.

Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded


@SRS-41.01
@edge
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68547
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Detailed_Info
Scenario: Out of range lab results are highlighted on the side panel
When the user clicks on the following event point on the "Pathology and Labs" swimlane:
| event_type | name_on_label      |
| MARKER     | Cholesterol, total |
Then the sidebar is displayed
And the sidebar header contains the following data:
| title              | creation_date | report_type        |
| Cholesterol, total | Jun 07, 2017  | Pathology and Labs |
And the sidebar contains the following "Results" table:
| Test name       | Result | Unit     | Flag     | Reference range |
| Cholesterol     | 220    | mg/dL    | Abnormal | 0 - 200 mg/dL   |
| HDL             | 38     | <EMPTY>  | <EMPTY>  | 35 - 70         |
| LDL Cholesterol | 135    | <EMPTY>  | <EMPTY>  | <EMPTY>         |
| Triglycerides   | 145    | mg/dL    | <EMPTY>  | 40 - 160 mg/dL  |
And the following rows are highlighted with "yellow" color in the "Results" table:
| Test name       | Result | Unit     | Flag     | Reference range |
| Cholesterol     | 220    | mg/dL    | Abnormal | 0 - 200 mg/dL   |
And the following texts are "bold" in the "Results" table:
| Strings                        |
| Cholesterol;220;mg/dL;Abnormal |

@SRS-41.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68547
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Detailed_Info
Scenario: In range lab results and Reference range values are not highlighted on the side panel
When the user clicks on the following event point on the "Pathology and Labs" swimlane:
| event_type | name_on_label      |
| MARKER     | Cholesterol, total |
Then the sidebar is displayed
And the sidebar header contains the following data:
| title              | creation_date | report_type        |
| Cholesterol, total | Jun 07, 2017  | Pathology and Labs |
And the sidebar contains the following "Results" table:
| Test name       | Result | Unit     | Flag     | Reference range |
| Cholesterol     | 220    | mg/dL    | Abnormal | 0 - 200 mg/dL   |
| HDL             | 38     | <EMPTY>  | <EMPTY>  | 35 - 70         |
| LDL Cholesterol | 135    | <EMPTY>  | <EMPTY>  | <EMPTY>         |
| Triglycerides   | 145    | mg/dL    | <EMPTY>  | 40 - 160 mg/dL  |
And the following rows are not highlighted with "yellow" color in the "Results" table:
| Test name       | Result | Unit     | Flag     | Reference range |
| HDL             | 38     | <EMPTY>  | <EMPTY>  | 35 - 70         |
| LDL Cholesterol | 135    | <EMPTY>  | <EMPTY>  | <EMPTY>         |
| Triglycerides   | 145    | mg/dL    | <EMPTY>  | 40 - 160 mg/dL  |
And the following texts are not "bold" in the "Results" table:
| Strings                                |
| 0 - 200 mg/dL                          |
| HDL;38;35 - 70                         |
| LDL Cholesterol;135                    |
| Triglycerides;145;mg/dL;40 - 160 mg/dL |
