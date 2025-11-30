Feature: [SysRS-70308] Response Tracking Configuration

Narrative: The system shall allow the GEHC serivce user to configure Response tracking with the following:
           Left panel shall be configurable as summary dashboard card (See SysRS-68490).
           Right panel shall have configurable fixed blocks: , block order and block functionality shall be configurable with the following:
           Event selection (side-by-side): must have, type of events configurable (one type at a time)
           multi-line chart: optional block. parameters to display in the chart are configurable
           Event details (side-by-side): optional block , content is configurable (see SysRS-68490)
           Measurement table: optional block , data and parameters are configurable.


@SRS-70308.01
@configChange
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70308
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/System_Configuration
Scenario: Service User can modify the representation of the Response tracking view
Given the following request was sent to Precision Insights by service user app:
| service | method | endpoint_path                                          | payload                                                                        | content_type     |
| core    | PUT    | /configs/representation/treatmentResponse.onco.general | configuration/modified_configs/representation/treatment-response-modified.json | application/json |
And the received HTTP status code was 204
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the patient summary view is loaded
When the user clicks "Status and response" button
Then the Treatment and response view is loaded
And the "Most recent Oncology note details" treatment card contains the following data:
| data_type   | data                              |
| CARD_TITLE  | Most recent Oncology note details |
| DATE        | May 31, 2017                      |
| NORMAL_TEXT | Progress Notes                    |
And the "Click here to open the complete report" link is visible at imaging study B

@manual
@SRS-70308.02
@configChange
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70308
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/System_Configuration
Scenario: Service User can modify the chart markers on the Response tracking view
Given the following request was sent to Precision Insights by service user app:
| service | method | endpoint_path                                                      | payload                                                                                      | content_type     |
| core    | PUT    | /configs/representation/treatmentResponseChartMarkers.onco.general | configuration/modified_configs/representation/treatment-response-chart-markers-modified.json | application/json |
And the received HTTP status code was 204
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the patient summary view is loaded
And the "Status and response" button was clicked
And the Treatment and response view is loaded
When the user selects the following imaging study for study A:
| name              | date         |
| XR CHEST PA OR AP | Aug 18, 2022 |
And the user selects the following imaging study for study B:
| name                                   | date         |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011 |
When the user sets the full date range on the multiline chart
Then the icon of the "B" marker on the multiline chart is orange
When the user hovers on the "Radiation" badge on the multiline chart
Then a tooltip is displayed over the badge with date as:
| label_name | reference_badge_icon                         | start_of_therapy            | most_recent_therapy          |
| Radiation  | testdata/labels/Label_library.svg: Radiation | Therapy start: Aug 15, 2018 | Newest therapy: Aug 15, 2018 |
When the following request is sent to Precision Insights by service user app:
| service | method | endpoint_path                                                      | payload                                                                              | content_type     |
| core    | PUT    | /configs/representation/treatmentResponseChartMarkers.onco.general | configuration/repo/representation/treatment-response-chart-markers-onco-general.json | application/json |
Then the received HTTP status code is 204
When the user refreshes the page
Then the page is reloaded
And the icon of the "B" marker on the multiline chart is teal
When the user hovers on the "Radiation" badge on the multiline chart
Then a tooltip is displayed over the badge with date as:
| label_name | reference_badge_icon                         | start_of_therapy               | most_recent_therapy               |
| Radiation  | testdata/labels/Label_library.svg: Radiation | Start of therapy: Aug 15, 2018 | Most recent therapy: Aug 15, 2018 |

@SRS-70308.03
@configChange
@Platform:Rest_API
@api
@Category:Negative
@Traceability:SysRS-70308
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/System_Configuration
Scenario Outline: Service User cannot upload an invalid representation config for the Response tracking view via API
When the following request is sent to Precision Insights by service user app:
| service | method | endpoint_path | payload       | content_type     |
| core    | PUT    | <endpoint>    | <config_file> | application/json |
Then the received HTTP status code is 400

Examples:
| endpoint                                                           | config_file                                                                                  |
| /configs/representation/treatmentResponse.onco.general             | configuration/invalid_configs/representation/treatment-response-invalid_1.json               |
| /configs/representation/treatmentResponse.onco.general             | configuration/invalid_configs/representation/treatment-response-invalid_2.json               |
| /configs/representation/treatmentResponseChartMarkers.onco.general | configuration/invalid_configs/representation/treatment-response-chart-markers-invalid_1.json |
| /configs/representation/treatmentResponseChartMarkers.onco.general | configuration/invalid_configs/representation/treatment-response-chart-markers-invalid_2.json |
