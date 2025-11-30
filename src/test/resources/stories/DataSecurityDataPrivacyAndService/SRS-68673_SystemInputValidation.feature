@safety
Feature: [SysRS-68673] System input validation

Narrative: The system shall validate all inputs (User inputs, configuration files, Valuesets) against malicious content

@SRS-68673.01
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68673
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/System_Input_Validation
Scenario: Inputs are validated against malicious content in Comments view
Given the user is logged into the application
And the "OncoTestPatient, Franklin" patient is selected
And the user navigates to Comments view
And Dev tools is opened on the Network tab
When the user submits each entry as comment listed in: 'testdata/input_validation/input_validation_entries.txt'
Then the comment is not saved
And the following request has been sent for each entry:
| request_name  | http_response |
| comments      | 403           |

@SRS-68673.02
@srs
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68673
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/System_Input_Validation
Scenario Outline: Inputs are validated against malicious content for comment endpoint
When the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method   | endpoint_path   | payload   | content_type   |
| core    | <method> | <endpoint_path> | <payload> | <content_type> |
Then the received HTTP status code is <status_code>
Examples:
| method | endpoint_path                                           | payload                                            | content_type     | status_code |
| POST   | /patients/62812e9a-96ae-4b59-85fe-43872df6bd78/comments | { "content":"saa", "fake":"saa"}                   | application/json | 400         |
| POST   | /patients/62812e9a-96ae-4b59-85fe-43872df6bd78/comments | { "content": "patient comment added through api" } | text/html        | 500         |

@SRS-68673.05
@srs
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68673
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/System_Input_Validation
Scenario Outline: Inputs are validated against malicious content for label endpoint
When the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method   | endpoint_path   | payload   | content_type   |
| core    | <method> | <endpoint_path> | <payload> | <content_type> |
Then the received HTTP status code is <status_code>
Examples:
| method | endpoint_path | payload                                                     | content_type     | status_code |
| POST   | /labels       | { "text":"saa", "fake":"saa"}                               | application/json | 400         |
| POST   | /labels       | { "text": "label for input validation", "paletteColor": 0 } | text/html        | 500         |

@SRS-68673.06
@srs
@manual
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68673
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/System_Input_Validation
Scenario Outline: Valuesets are validated against malicious content
When the user sends the following request to upload valueset to the application:
| endpoint_path                   | method | payload   |
| <onco_fulfill_api_gw>/value-set | POST   | <payload> |
Then the received HTTP status code is 403
When the user try to retrieve the valueset by sending the following request:
| endpoint_path_retrieve   | method |
| <endpoint_path_retrieve> | GET    |
Then the received HTTP status code is 404
Examples:
| payload                                             | endpoint_path_retrieve                 |
| testdata/input_validation/input_validation_vs.json  | <onco_fulfill_api_gw>/value-set/iv_vs  |
| testdata/input_validation/input_validation_vs2.json | <onco_fulfill_api_gw>/value-set/iv_vs2 |
| testdata/input_validation/input_validation_vs3.json | <onco_fulfill_api_gw>/value-set/iv_vs3 |

@SRS-68673.07
@srs
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68673
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/System_Input_Validation
Scenario Outline: Verify configuration API is validated against malicious content
When the following request is sent to Precision Insights by service user app:
| service | method | endpoint_path   | payload   | content_type     |
| core    | PUT    | <endpoint_path> | <payload> | application/json |
Then the received HTTP status code is <status_code>
Examples:
| endpoint_path | payload                                                          | status_code |
| /configs/app  | configuration/malicious_configs/app-config/malicious-script.json | 403         |
| /configs/app  | configuration/malicious_configs/app-config/fake-property.json    | 400         |
| /configs/app  | configuration/malicious_configs/app-config/redirect-url.json     | 400         |
| /configs/app  | configuration/malicious_configs/app-config/non-web-link.json     | 400         |

@SRS-68673.08
@srs
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68673
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/System_Input_Validation
Scenario Outline: Representation API is validated against malicious content
When the following request is sent to Precision Insights by service user app:
| service | method | endpoint_path   | payload   | content_type     |
| core    | PUT    | <endpoint_path> | <payload> | application/json |
Then the received HTTP status code is <status_code>
Examples:
| endpoint_path                                                      | payload                                                                                                          | status_code |
| /configs/representation/imagingStudy.onco.general                  | configuration/malicious_configs/representation/representation/imaging-study-onco-general.json                    | 403         |
| /configs/representation/labReportResults.onco.general              | configuration/malicious_configs/representation/representation/lab-report-results-onco-general.json               | 403         |
| /configs/representation/medicalHistory.onco.general                | configuration/malicious_configs/representation/representation/medical-history-onco-general.json                  | 403         |
| /configs/representation/patientBanner.onco.extended                | configuration/malicious_configs/representation/representation/patient-banner-onco-extended.json                  | 403         |
| /configs/representation/summary.onco.extended                      | configuration/malicious_configs/representation/representation/summary-onco-extended.json                         | 403         |
| /configs/representation/timeline.onco.extended                     | configuration/malicious_configs/representation/representation/timeline-onco-extended.json                        | 403         |
| /configs/representation/adherence.onco.extended                    | configuration/malicious_configs/representation/representation/treatment-adherence-onco-extended.json             | 403         |
| /configs/representation/treatmentResponseChartMarkers.onco.general | configuration/malicious_configs/representation/representation/treatment-response-chart-markers-onco-general.json | 403         |
| /configs/representation/treatmentResponse.onco.general             | configuration/malicious_configs/representation/representation/treatment-response-onco-general.json               | 403         |
| /configs/representation/trend.onco.extended                        | configuration/malicious_configs/representation/representation/trend-onco-extended.json                           | 403         |

@SRS-68673.09
@srs
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68673
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/System_Input_Validation
Scenario Outline: Fulfillment query set API is validated against malicious content
When the following request is sent to Precision Insights by service user app:
| service | method | endpoint_path   | payload   | content_type     |
| core    | PUT    | <endpoint_path> | <payload> | application/json |
Then the received HTTP status code is <status_code>
Examples:
| endpoint_path                                                  | payload                                                                                                    | status_code |
| /configs/fulfillment-query-set/imagingReports.onco.general     | configuration/malicious_configs/representation/fulfillment-query-set/imaging-reports-onco-general.json     | 403         |
| /configs/fulfillment-query-set/medicalHistory.onco.general     | configuration/malicious_configs/representation/fulfillment-query-set/medical-history-onco-general.json     | 403         |
| /configs/fulfillment-query-set/patientProfile.onco.extended    | configuration/malicious_configs/representation/fulfillment-query-set/patient-profile-onco-extended.json    | 403         |
| /configs/fulfillment-query-set/recentEvents.onco.general       | configuration/malicious_configs/representation/fulfillment-query-set/recent-events-onco-general.json       | 403         |
| /configs/fulfillment-query-set/summary.onco.extended           | configuration/malicious_configs/representation/fulfillment-query-set/summary-onco-extended.json            | 403         |
| /configs/fulfillment-query-set/timeline.onco.extended          | configuration/malicious_configs/representation/fulfillment-query-set/timeline-onco-extended.json           | 403         |
| /configs/fulfillment-query-set/treatmentResponse.onco.extended | configuration/malicious_configs/representation/fulfillment-query-set/treatment-response-onco-extended.json | 403         |
| /configs/fulfillment-query-set/trend.onco.extended             | configuration/malicious_configs/representation/fulfillment-query-set/trend-onco-extended.json              | 403         |

@SRS-68673.11
@srs
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68673
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/System_Input_Validation
Scenario: Treatment Adherence config input is validated against malicious content
When the following request is sent to Precision Insights by service user app:
| service   | method | endpoint_path | payload                                                                        | content_type     |
| adherence | POST   | /treatment    | configuration/malicious_configs/treatment-adherence/NCT04428788-malicious.json | application/json |
Then the received HTTP status code is 403
