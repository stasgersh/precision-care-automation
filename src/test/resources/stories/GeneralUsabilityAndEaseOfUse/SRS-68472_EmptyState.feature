@safety
Feature: [SysRS-68472] Empty State

Narrative: The system shall provide empty state status whenever data is not available, missing or not found for the following:
             - patient Banner
             - Summary dashboard view: Measurement cards
             - Progressive summarization view
             - Clinical trial eligibility view
             - Response tracking view
             - Timeline view and Treatment adherence view
             - Trends view
             - Medical history view


@SRS-194.01
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68472
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Treatment_Response_View
Scenario: Response view displays empty state for badge if no study was selected
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Franklin" patient is selected
And the patient summary view is loaded
When the user clicks "Status and response" button
Then the Treatment and response view is loaded
And the following imaging study is displayed for B:
| name                                   | date         |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 1975 |
And no imaging study is selected for study A
And the Response column displays the following empty message:
| title                                   | message                                                                   | button          |
| Not enough information to show Response | This view will become active when there are at least 2 studies available. | Back to Summary |
And the Measures table is not visible on the Response view
And the Multiline chart is not visible on the Response view

@SRS-194.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68472
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Treatment_Response_View
Scenario: Measurement table displays empty state if no value exist within one month of the selected studies
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
And the patient summary view is loaded
When the user clicks "Status and response" button
Then the Treatment and response view is loaded
When the user selects the following imaging study for study A:
| name | date         |
| AP   | Nov 15, 1990 |
And the user selects the following imaging study for study B:
| name                                                               | date         |
| Computed tomography, abdomen and pelvis; with contrast material(s) | Jan 16, 2000 |
Then the Measurements table is displayed with the following details:
| measure                                                                  | A_badge_value           | A_badge_date | B_badge_value           | B_badge_date | diff_badge |
| Body Weight                                                              | No value within 30 days | <N/A>        | No value within 30 days | <N/A>        | <N/A>      |
| Chloride                                                                 | No value within 30 days | <N/A>        | No value within 30 days | <N/A>        | <N/A>      |
| Hemoglobin                                                               | No value within 30 days | <N/A>        | No value within 30 days | <N/A>        | <N/A>      |
| Low Density Lipoprotein Cholesterol                                      | No value within 30 days | <N/A>        | No value within 30 days | <N/A>        | <N/A>      |
| Platelet distribution width [Entitic volume] in Blood by Automated count | No value within 30 days | <N/A>        | No value within 30 days | <N/A>        | <N/A>      |
When the user selects the following imaging study for study A:
| name                                   | date         |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011 |
Then the Measurements table is displayed with the following details:
| measure                                                                  | A_badge_value | A_badge_date | B_badge_value           | B_badge_date | diff_badge |
| Body Weight                                                              | 61.8 kg       | Oct 23, 2006 | No value within 30 days | <N/A>        | <N/A>      |
| Chloride                                                                 | 106 mmol/L    | Mar 26, 2022 | No value within 30 days | <N/A>        | <N/A>      |
| Hemoglobin                                                               | 10.5 g/dL     | Mar 26, 2022 | No value within 30 days | <N/A>        | <N/A>      |
| Low Density Lipoprotein Cholesterol                                      | 237.2 mg/dL   | Mar 26, 2022 | No value within 30 days | <N/A>        | <N/A>      |
| Platelet distribution width [Entitic volume] in Blood by Automated count | 478.9 fL      | Mar 26, 2022 | No value within 30 days | <N/A>        | <N/A>      |

@SRS-194.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68472
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Treatment_Response_View
Scenario: Empty state is displayed when most recent imaging could not be identified
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Empty" patient is selected
And the patient summary view is loaded
When the user clicks "Status and response" button
Then the Treatment and response view is loaded
And no imaging study is selected for study A
And no imaging study is selected for study B
And the imaging study selector of A is disabled
And the imaging study selector of B is disabled
And the Response column displays the following empty message:
| title                                   | message                                                                   | button          |
| Not enough information to show Response | This view will become active when there are at least 2 studies available. | Back to Summary |
And the Measures table is not visible on the Response view
And the Multiline chart is not visible on the Response view