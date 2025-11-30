@safety
Feature: [SysRS-68473] Response Tracking – Trend chart

Narrative: The application shall be able to display a Trend chart(s) - Multiline chart for the following the parameters:
           Default parameters - that shall be displayed by default:
           - Weight
           - Biomarkers (general, prostate, breast)


@sanity
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68473
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Manual/Functional_Service/Treatment_Response_view
Scenario: Body Weight and Prostate specific tumor marker lines are displayed in the Response group
Given [API] the Treatment Response settings were reset for "OncoTestPatient, Sigrid" by the [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Sigrid" patient is selected
And the user navigated to the "Treatment and response" view
And the Treatment and response view is loaded
And the following imaging study was selected for study A:
| name                     | date         |
| Diagnostic imaging study | Jan 28, 2016 |
And the following imaging study was selected for study B:
| name                     | date         |
| Diagnostic imaging study | Jan 30, 2016 |
When the user selects the following custom date range on the chart slider:
| start        | end          |
| Mar 25, 2010 | Feb 02, 2021 |
Then the following lines are present on the chart:
| name                                                  | unit   |
| Body Weight                                           | kg     |
| Prostate specific Ag [Mass/volume] in Pleural fluid   | %      |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | ng/mL  |
| Prostate specific Ag [Moles/volume] in Plasma         | umol/L |
And the multiline chart is displayed as "resources/testdata/responseView/multilineChart/Sigrid_MAR_25_2010_FEB_02_2021.png"
When the user selects the following custom date range on the chart slider:
| start        | end          |
| Mar 25, 2010 | Jul 04, 2014 |
And the user hovers on the "Body Weight" legend on the right side
Then the multiline chart is displayed as "resources/testdata/responseView/multilineChart/Sigrid_MAR_25_2010_JUL_04_2014.png"
When the user hovers on the "Body Weight" line on the chart area
Then 8 data points became displayed on the "Body Weight" trend graph
And the following data points are present on the "Body Weight (kg)" trend graph:
| data_value_on_point_label | date_on_point_label |
| 60                        | Mar 25, 2010        |
| 65                        | May 26, 2010        |
| 69.9                      | Jan 19, 2013        |
| 64                        | Feb 20, 2013        |
| 65.3                      | Feb 23, 2013        |
| 69                        | Jun 24, 2013        |
| 71.8                      | Aug 20, 2013        |
| 74.2                      | Dec 21, 2013        |