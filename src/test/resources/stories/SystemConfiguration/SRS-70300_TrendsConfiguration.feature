Feature: [SysRS-70300] Trends Configuration

Narrative: The system shall allow the user to configure parameters to display in trends view.


@SRS-70300.01
@configChange
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70300
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/System_Configuration
Scenario: Service User can modify the displayed Trends charts and their order via API
Given the following request was sent to Precision Insights by service user app:
| service | method | endpoint_path                                      | payload                                                          | content_type     |
| core    | PUT    | /configs/fulfillment-query-set/trend.onco.extended | configuration/modified_configs/queryset/trend-onco-modified.json | application/json |
And the received HTTP status code was 204
And the following request was sent to Precision Insights by service user app:
| service | method | endpoint_path                               | payload                                                                | content_type     |
| core    | PUT    | /configs/representation/trend.onco.extended | configuration/modified_configs/representation/trend-onco-modified.json | application/json |
And the received HTTP status code was 204
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Trendenza" patient is selected
When the user navigates to the "Trends" view
Then the Trends dashboard is displayed
And the trend graphs are displayed in the following order:
| trend_graph_title                                            |
| Cancer Ag 125 [Units/volume] in Serum or Plasma (kU/l)       |
| Leukocytes [#/volume] in Blood by Automated count (10*3/uL)  |
| Eosinophils [#/volume] in Blood by Automated count (10*3/uL) |
| Bilirubin.total [Mass/volume] in Serum or Plasma (mg/dL)     |
| Creatinine (mg/dL)                                           |
| Hemoglobin [Mass/volume] in Blood (g/dL)                     |

@SRS-70300.02
@configChange
@querySetChange
@Platform:Rest_API
@api
@Category:Negative
@Traceability:SysRS-70300
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/System_Configuration
Scenario Outline: Service User cannot upload an invalid representation config for the Trends view via API
When the following request is sent to Precision Insights by service user app:
| service | method | endpoint_path | payload       | content_type     |
| core    | PUT    | <endpoint>    | <config_file> | application/json |
Then the received HTTP status code is 400

Examples:
| endpoint                                           | config_file                                                          |
| /configs/representation/trend.onco.extended        | configuration/invalid_configs/representation/trend-onco-invalid.json |
| /configs/representation/trend.onco.extended        | configuration/invalid_configs/representation/trend-onco-invalid.json |
| /configs/fulfillment-query-set/trend.onco.extended | configuration/invalid_configs/queryset/trend-onco-invalid.json       |
| /configs/fulfillment-query-set/trend.onco.extended | configuration/invalid_configs/queryset/trend-onco-invalid.json       |
