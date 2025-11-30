Feature: [SysRS-68475] Chart Date Range

Narrative: The application users shall be able to select to see last 6 months, full date range according to Response tracking - Select time point to compare (SysRS-69191)


@manual
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68475
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Manual/Functional_Service/Treatment_Response_view
Scenario: The 'Last 6 months' is selected by default
Given the dates of the following patient's resources are prepared and uploaded to PDS: patients/prepared/multiline/OncoTestPatient_Multiline6Month.json
And [API] the [DEFAULT-TEST-USER] user reset the treatment response settings for "OncoTestPatient, Multiline6Month"
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Multiline6Month" patient is selected
When the user clicks "Patient status" button
Then the Treatment and response view is loaded
When the user clicks "Last 6 months" button on the multiline chart
And the X-axis (date) is displaying values in the following range:
| start_date     | end_date |
| <184_DAYS_AGO> | Today    |
And the following data points are displayed on the "Body Weight (kg)" trend graph:
| data_value_on_point_label | date_on_point_label |
| 78.9                      | <184_DAYS_AGO>      |
| 78.8                      | <183_DAYS_AGO>      |
| 76.8                      | <182_DAYS_AGO>      |
| 90.8                      | <1_DAY_AGO>         |
| 91                        | <TODAY>             |
And the following data points are not displayed on the "Body Weight (kg)" trend graph:
| data_value_on_point_label | date_on_point_label |
| 80.2                      | <185_DAYS_AGO>      |
When the user clicks "Date range" button on the multiline chart
Then the range selector slide has the following date selection:
| start_date     | end_date |
| <184_DAYS_AGO> | <TODAY>  |
And the X-axis (date) is displaying values in the following range:
| start_date     | end_date |
| <184_DAYS_AGO> | <TODAY>  |

@manual
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68475
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Manual/Functional_Service/Treatment_Response_view
Scenario: Chart updates when the start or end date is adjusted using the slider
Given [API] the Treatment Response settings were reset for "OncoTestPatient, Sigrid" by the [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Sigrid" patient is selected
And the user navigated to the "Treatment and response" view
And the user selected the following custom date range on the chart slider:
| start_date   | end_date     |
| Jul 02, 2013 | May 25, 2014 |
And the following lines are present on the chart area:
| name                                                  | unit  |
| Body Weight                                           | kg    |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | ng/mL |
| Prostate specific Ag [Mass/volume] in Pleural fluid   | %     |
And the X-axis (date) is displaying values in the following range:
| start_date   | end_date     |
| Jul 02, 2013 | May 25, 2014 |
When the user hovers on the "Body Weight (kg)" legend
Then the following data points are present on the "Body Weight (kg)" trend graph:
| data_value_on_point_label | date_on_point_label |
| 71.8                      | Aug 20, 2013        |
| 74.2                      | Dec 21, 2013        |
When the user selects the following custom date range on the chart slider:
| start_date   | end_date     |
| Jan 29, 2013 | Jul 16, 2013 |
Then the X-axis (date) is displaying values in the following range:
| start_date   | end_date     |
| Jan 29, 2013 | Jul 16, 2013 |
And the following data points are present on the "Body Weight (kg)" trend graph:
| data_value_on_point_label | date_on_point_label |
| 64                        | Feb 20, 2013        |
| 65.3                      | Feb 23, 2013        |
| 69                        | Jun 24, 2013        |
When the user hovers on the left thumb of the date range selector slider
Then the following tooltips are displayed over both slider thumbs:
| tooltip | date         |
| left    | Jan 29, 2013 |
| right   | Jul 16, 2013 |
When the user hovers on the right thumb of the range selector slider
Then the following tooltips are displayed over both slider thumbs:
| tooltip | date         |
| left    | Jan 29, 2013 |
| right   | Jul 16, 2013 |