Feature: [SysRS-103446] Response Tracking - Trend chart configurability

Narrative: The system shall provide functionality to configure the list of parameters available on the trend chart.


@SRS-103446.01
@sanity
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-103446
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Manual/Functional_Service/Treatment_Response_view
Scenario: Visible trend charts can be customized in Response view
Given [API] the Treatment Response settings are reset for "OncoTestPatient, Multiline" by the [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to CareIntellect for Oncology application
And the "OncoTestPatient, Multiline" patient is selected
And the Treatment and response view is loaded
And the following imaging study was selected for study A:
| name                      | date         |
| Mammography and US Breast | May 21, 2025 |
And the following imaging study was selected for study B:
| name                      | date         |
| Mammography and US Breast | Jun 04, 2025 |
When the user selects the following custom date range on the chart slider:
| start        | end          |
| Jan 01, 2025 | Aug 17, 2025 |
Then the following lines are present on the chart area:
| name                                                  | unit   |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | ng/mL  |
| Weight                                                | g      |
| Weight                                                | kg     |
| Cancer Ag 15-3 [Presence] in Serum or Plasma          | ng/mL  |
| Chromogranin A [Moles/volume] in Serum or Plasma      | ng/mL  |
And the "Trends" button is displayed with number '5' badge
When the user clicks on the "Trends" button
Then Trends modal opens with available trends listed:
| name                                                  | unit    | selected  |
| Auto WBC                                              | 10^3/mL | no        |
| Cancer Ag 15-3 [Presence] in Serum or Plasma          | ng/mL   | yes       |
| Chromogranin A [Moles/volume] in Serum or Plasma      | ng/mL   | yes       |
| Creatinine                                            | mg/dL   | no        |
| HCT                                                   | %       | no        |
| Hgb                                                   | g/dL    | no        |
| Plt                                                   | 10*3/uL | no        |
| Potassium                                             | mEq/L   | no        |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | ng/mL   | yes       |
| Weight (2)                                            | kg, g   | yes       |
And the recommendation message is displayed: "For best view, select no more than 5 trends."
When the user sets the following trend visibility:
| name                                                  | unit    | selected  |
| Auto WBC                                              | 10^3/mL | yes       |
| Cancer Ag 15-3 [Presence] in Serum or Plasma          | ng/mL   | no        |
| Chromogranin A [Moles/volume] in Serum or Plasma      | ng/mL   | yes       |
| Creatinine                                            | mg/dL   | no        |
| HCT                                                   | %       | no        |
| Hgb                                                   | g/dL    | yes       |
| Plt                                                   | 10*3/uL | yes       |
| Potassium                                             | mEq/L   | no        |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | ng/mL   | no        |
| Weight (2)                                            | kg, g   | no        |
Then the recommendation message is not displayed: "For best view, select no more than 5 trends."
When the user closes the Trends modal
Then the following lines are present on the chart area:
| name                                              | unit    |
| Auto WBC                                          | 10^3/mL |
| Chromogranin A [Moles/volume] in Serum or Plasma  | ng/mL   |
| Hgb                                               | g/dL    |
| Plt                                               | 10*3/uL |
And the horizontal axis is in the following range:
| start        | end          |
| Jan 01, 2025 | Aug 17, 2025 |
And the "Trends" button is displayed with number '4' badge

@SRS-103446.02
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-103446
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Treatment_Response_View
Scenario: User can reset visible trend charts to default
Given [API] the Treatment Response settings are reset for "OncoTestPatient, Multiline" by the [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to CareIntellect for Oncology application
And the "OncoTestPatient, Multiline" patient is selected
And the Treatment and response view is loaded
And the following trend visibility was set:
| name                                                  | unit    | selected  |
| Auto WBC                                              | 10^3/mL | yes       |
| Cancer Ag 15-3 [Presence] in Serum or Plasma          | ng/mL   | no        |
| Chromogranin A [Moles/volume] in Serum or Plasma      | ng/mL   | yes       |
| Creatinine                                            | mg/dL   | no        |
| HCT                                                   | %       | no        |
| Hgb                                                   | g/dL    | yes       |
| Plt                                                   | 10*3/uL | no        |
| Potassium                                             | mEq/L   | yes       |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | ng/mL   | no        |
| Weight (2)                                            | kg, g   | no        |
And the following lines were present on the chart area:
| name                                                  | unit    |
| Auto WBC                                              | 10^3/mL |
| Chromogranin A [Moles/volume] in Serum or Plasma      | ng/mL   |
| Hgb                                                   | g/dL    |
| Potassium                                             | mEq/L   |
When the user clicks on the "Trends" button
And the user clicks on the "Reset to default" button
Then the trend visibility is set as the following:
| name                                                  | unit    | selected  |
| Auto WBC                                              | 10^3/mL | no        |
| Cancer Ag 15-3 [Presence] in Serum or Plasma          | ng/mL   | yes       |
| Chromogranin A [Moles/volume] in Serum or Plasma      | ng/mL   | yes       |
| Creatinine                                            | mg/dL   | no        |
| HCT                                                   | %       | no        |
| Hgb                                                   | g/dL    | no        |
| Plt                                                   | 10*3/uL | no        |
| Potassium                                             | mEq/L   | no        |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | ng/mL   | yes       |
| Weight (2)                                            | kg, g   | yes       |
When the user closes the Trends modal
Then the following lines are present on the chart area:
| name                                                  | unit   |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | ng/mL  |
| Weight                                                | g      |
| Weight                                                | kg     |
| Cancer Ag 15-3 [Presence] in Serum or Plasma          | ng/mL  |
| Chromogranin A [Moles/volume] in Serum or Plasma      | ng/mL  |
And the "Trends" button is displayed with number '5' badge

@SRS-103446.03
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-103446,SysRS-103448
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Treatment_Response_View
Scenario: Empty state is displayed when no trends are selected
Given the [DEFAULT-TEST-USER] user is logged in to CareIntellect for Oncology application
And the "OncoTestPatient, Multiline" patient is selected
And the Treatment and response view is loaded
When the user clicks on the "Trends" button
And the user sets the following trend visibility:
| name                                                  | unit    | selected  |
| Auto WBC                                              | 10^3/mL | no        |
| Cancer Ag 15-3 [Presence] in Serum or Plasma          | ng/mL   | no        |
| Chromogranin A [Moles/volume] in Serum or Plasma      | ng/mL   | no        |
| Creatinine                                            | mg/dL   | no        |
| HCT                                                   | %       | no        |
| Hgb                                                   | g/dL    | no        |
| Plt                                                   | 10*3/uL | no        |
| Potassium                                             | mEq/L   | no        |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | ng/mL   | no        |
| Weight (2)                                            | kg, g   | no        |
And the user closes the Trends modal
Then the following info message present in the chart area:
| message_header        | message_body                                |
| Please select trends  | To see trends, select one or more options.  |
And the "Trends" button is displayed with number '0' badge
And "Last 6 months" and "Date range" button is disabled

@SRS-103446.04
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-103446,SysRS-103447
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Treatment_Response_View
Scenario: Separate trend charts are displayed for trends with multiple units of measure
Given [API] the Treatment Response settings are reset for "OncoTestPatient, Multiline" by the [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to CareIntellect for Oncology application
And the "OncoTestPatient, Multiline" patient is selected
And the Treatment and response view is loaded
When the following imaging study is selected for study A:
| name                      | date         |
| Mammography and US Breast | May 21, 2025 |
And the following imaging study is selected for study B:
| name                      | date         |
| Mammography and US Breast | Jun 04, 2025 |
And the user selects the following custom date range on the chart slider:
| start        | end          |
| Jan 01, 2025 | Aug 17, 2025 |
Then the following lines are present on the chart area:
| name                                                  | unit   |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | ng/mL  |
| Weight                                                | g      |
| Weight                                                | kg     |
| Cancer Ag 15-3 [Presence] in Serum or Plasma          | ng/mL  |
| Chromogranin A [Moles/volume] in Serum or Plasma      | ng/mL  |
And the following charts have matching colored lines and legends:
| name    | unit   |
| Weight  | g      |
| Weight  | kg     |
When the user hovers on the "Weight (g)" line on the chart area
Then the following lines are present on the chart area:
| name                                                  | unit   |  colored |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | ng/mL  |  no      |
| Weight                                                | g      |  yes     |
| Weight                                                | kg     |  no      |
| Cancer Ag 15-3 [Presence] in Serum or Plasma          | ng/mL  |  no      |
| Chromogranin A [Moles/volume] in Serum or Plasma      | ng/mL  |  no      |
And the Y-axis is displaying values in the following range:
| min_value | max_value |
| 0         | 80000     |
When the user hovers on the "Weight (kg)" legend on the right side
Then the following lines are present on the chart area:
| name                                                  | unit   |  colored |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | ng/mL  |  no      |
| Weight                                                | g      |  no      |
| Weight                                                | kg     |  yes     |
| Cancer Ag 15-3 [Presence] in Serum or Plasma          | ng/mL  |  no      |
| Chromogranin A [Moles/volume] in Serum or Plasma      | ng/mL  |  no      |
And the Y-axis is displaying values in the following range:
| min_value | max_value |
| 0         | 80        |

@SRS-103446.05
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-103446
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Treatment_Response_View
Scenario: User can search for trend on the Trends modal
Given the [DEFAULT-TEST-USER] user is logged in to CareIntellect for Oncology application
And the "OncoTestPatient, Multiline" patient is selected
And the Treatment and response view is loaded
When the user clicks on the "Trends" button
And the user types the following text into the search field: "Weight"
Then the following trend is present in the trend list:
| name    | unit  |
| Weight  | kg, g |
And there is no any other trend listed
When the user types the following text into the search field: "volume"
Then the following trend is present in the trend list:
| name                                                  | unit  |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | ng/ml |
| Chromogranin A [Moles/volume] in Serum or Plasma      | ng/ml |
And there is no any other trend listed


