Feature: [SysRS-68697] Observability

Narrative: The system shall provide GEHC service person the ability to monitor alerts and metrics as defined in Observability Alerts and Metrics by utilizing the DHS monitoring service.

@SRS-68697.01
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68697
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Monitoring
Scenario: Metrics can be visualized in Kloudfuse application
Given the user logged in to Kloudfuse application
And the user selected the Metrics menu
When I filter for the following metrics:
| metrics                                                   |
| aws_wafv2_blocked_requests                                |
| aws_apigateway_count                                      |
| aws_apigateway_4xxerror                                   |
| aws_apigateway_5xx                                        |
| aws_lambda_throttles                                      |
| aws_lambda_duration                                       |
| aws_lambda_claimed_account_concurrency                    |
| aws_dynamodb_consumed_read_capacity_units                 |
| aws_dynamodb_consumed_write_capacity_units                |
| aws_firehose_delivery_to_http_endpoint_success            |
| aws_natgateway_error_port_allocation                      |
| aws_natgateway_packets_drop_count                         |
| aws_natgateway_peak_bytes_per_second                      |
| aws_natgateway_peak_packets_per_second                    |
| aws_sns_number_of_messages_published                      |
| aws_sns_number_of_notifications_delivered                 |
| aws_sns_number_of_notifications_failed                    |
| aws_sns_publish_size                                      |
| aws_sqs_approximate_age_of_oldest_message                 |
| aws_sqs_approximate_number_of_messages_not_visible        |
| aws_sqs_approximate_number_of_messages_visible            |
| aws_sqs_number_of_deduplicated_sent_messages              |
| aws_sqs_number_of_messages_deleted                        |
| aws_sqs_number_of_messages_received                       |
| aws_sqs_number_of_messages_sent                           |
| aws_sqs_sent_message_size                                 |
| aws_aoss_4xx                                              |
| aws_aoss_5xx                                              |
| aws_lambda_invocations                                    |
| aws_sns_number_of_notifications_filtered_out_message_body |
| aws_lambda_errors                                         |
| aws_dynamodb_successful_request_latency                   |
| aws_dynamodb_user_errors                                  |
| aws_dynamodb_throttled_requests                           |
| aws_aoss_storage_used_in_s3                               |
| aws_aoss_ingestion_document_errors                        |
| aws_aoss_ingestion_document_rate                          |
| aws_aoss_search_request_rate                              |
| aws_aoss_search_request_latency                           |
| aws_aoss_ingestion_request_latency                        |
| aws_aoss_ingestion_request_errors                         |
| aws_states_execution_time                                 |
| aws_states_executions_failed                              |
| aws_states_executions_timed_out                           |
| aws_states_execution_throttled                            |
Then I can see the chart displayed for selected metrics
When I change the time range to 'Last day'
Then the chart is displaying data from the last 24 hours

@SRS-68697.02
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68697
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Monitoring
Scenario Outline: Service alerts can be monitored in Kloudfuse application
Given the user downloaded the Observability Alerts and Metrics document from MyWorkshop (DOC3102317)
And the user logged in to Kloudfuse application
And the user selected Alerts menu
When I search for <alerts>
And open the filtered alert
Then I see details about the selected alert
When I check <alerts> alert 'Expression' in Kloudfuse
Then I see that the value matches with <alerts> 'Expression' in the document
Examples:
| alerts                                                                |
| <env_name>_wafv2_blocked_requests_alert                               |
| <env_name>_apigw_count_alert                                          |
| <env_name>_apigateway_4xxerror_alert                                  |
| <env_name>_apigateway_5xx_alert                                       |
| <env_name>_lambda_throttles_alert                                     |
| <env_name>_lambda_duration_alert                                      |
| <env_name>_lambda_claimed_account_concurrency_alert                   |
| <env_name>_dynamodb_consumed_read_capacity_units_alert                |
| <env_name>_dynamodb_consumed_write_capacity_units_alert               |
| <env_name>_firehose_delivery_to_http_endpoint_success_alert           |
| <env_name>_natgateway_error_port_allocation_alert                     |
| <env_name>_natgateway_packets_drop_count_alert                        |
| <env_name>_natgateway_peak_bytes_per_second_alert                     |
| <env_name>_natgateway_peak_packets_per_second_alert                   |
| <env_name>_sns_number_of_messages_published_alert                     |
| <env_name>_sns_number_of_notifications_delivered_alert                |
| <env_name>_sns_number_of_notifications_failed_alert                   |
| <env_name>_sns_publish_size_alert                                     |
| <env_name>_sqs_approximate_age_of_oldest_message_alert                |
| <env_name>_sqs_approximate_number_of_messages_not_visible_alert       |
| <env_name>_sqs_approximate_number_of_messages_visible_alert           |
| <env_name>_sqs_number_of_deduplicated_sent_messages_alert             |
| <env_name>_sqs_number_of_messages_deleted_alert                       |
| <env_name>_sqs_number_of_messages_received_alert                      |
| <env_name>_sqs_number_of_messages_sent_alert                          |
| <env_name>_sqs_sent_message_size_alert                                |
| <env_name>_aoss_4xx_alert                                             |
| <env_name>_aoss_5xx_alert                                             |

@SRS-68697.03
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68697
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Monitoring
Scenario Outline: Fulfillment alerts can be monitored in Kloudfuse application
Given the user downloaded the Observability Alerts and Metrics document from MyWorkshop (DOC3102317)
And the user logged in to Kloudfuse application
And the user selected Alerts menu
When I search for <alerts>
And open the filtered alert
Then I see details about the selected alert
When I check <alerts> alert 'Expression' in Kloudfuse
Then I see that the value matches with <alerts> 'Expression' in the document
Examples:
| alerts                                           |
| <env_name>-ff_sns_failure_rate                   |
| <env_name>-ff_sns_invalid_message_body           |
| <env_name>-ff_sqs_high_visibility                |
| <env_name>-ff_sqs_high_message_age               |
| <env_name>-ff_lambda_high_error_rate             |
| <env_name>-ff_ddb_high_write_capacity            |
| <env_name>-ff_ddb_high_read_capacity             |
| <env_name>-ff_ddb_high_request_latency           |
| <env_name>-ff_ddb_high_user_errors               |
| <env_name>-ff_ddb_high_throttled_requests        |
| <env_name>-ff_aoss_4xx_errors                    |
| <env_name>-ff_aoss_high_storage_usage            |
| <env_name>-ff_aoss_high_ingestion_errors         |
| <env_name>-ff_aoss_low_ingestion_rate            |
| <env_name>-ff_aoss_low_search_rate               |
| <env_name>-ff_aoss_high_search_latency           |
| <env_name>-ff_aoss_high_ingestion_latency        |
| <env_name>-ff_aoss_high_ingestion_request_errors |
| <env_name>-ff_sfn_high_execution_time            |
| <env_name>-ff_sfn_execution_failures             |
| <env_name>-ff_sfn_execution_timeouts             |
| <env_name>-ff_sfn_execution_throttles            |
| <env_name>-ff_query_lambda_high_latency          |

@SRS-68697.04
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68697
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Monitoring
Scenario Outline: DynamoDB alerts can be monitored in Kloudfuse application
Given the user downloaded the Observability Alerts and Metrics document from MyWorkshop (DOC3102317)
And the user logged in to Kloudfuse application
And the user selected Alerts menu
When I search for <alerts>
And open the filtered alert
Then I see details about the selected alert
When I check <alerts> alert 'Expression' in Kloudfuse
Then I see that the value matches with <alerts> 'Expression' in the document
Examples:
| alerts                                                             |
| <env_name>-adherence-adherence-table-throttled-requests            |
| <env_name>-adherence-protocol-throttled-requests                   |
| <env_name>-criteria-configurations-throttled-requests              |
| <env_name>-criteria-results-throttled-requests                     |
| <env_name>-eligibility-results-throttled-requests                  |
| <env_name>-fulfill-entity-model-definitions-throttled-requests     |
| <env_name>-fulfill-patient-versions-definitions-throttled-requests |
| <env_name>-representation-comments-throttled-requests              |
| <env_name>-representation-configs-throttled-requests               |
| <env_name>-representation-labels-throttled-requests                |
| <env_name>-representation-settings-throttled-requests              |
| <env_name>-summary-configuration-throttled-requests                |

@SRS-68697.05
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68697
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Monitoring
Scenario Outline: Patient Summary alerts can be monitored in Kloudfuse application
Given the user downloaded the Observability Alerts and Metrics document from MyWorkshop (DOC3102317)
And the user logged in to Kloudfuse application
And the user selected Alerts menu
When I search for <alerts>
And open the filtered alert
Then I see details about the selected alert
When I check <alerts> alert 'Expression' in Kloudfuse
Then I see that the value matches with <alerts> 'Expression' in the document
Examples:
| alerts                                         |
| <env_name>-summary-high-frequency-invocations  |
| <env_name>-summary-execution-timeout           |
| <env_name>-summary-config-execution-timeout    |

@SRS-68697.06
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68697
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Monitoring
Scenario Outline: Log based alerts can be monitored in Kloudfuse application
Given the user downloaded the Observability Alerts and Metrics document from MyWorkshop (DOC3102317)
And the user logged in to Kloudfuse application
And the user selected Alerts menu
When I search for <alerts>
And open the filtered alert
Then I see details about the selected alert
When I check <alerts> alert 'Expression' in Kloudfuse
Then I see that the value matches with <alerts> 'Expression' in the document
Examples:
| alerts                                                |
| <env_name>-audit_log_failed                           |
| <env_name>-authorizer_lambda_failed_to_call_IDAM      |
| <env_name>_patient_summary_ttl_above_default          |
| <env_name>-ff_log_Query_payload_size                  |
| <env_name>-ff_log_dpsa_is_unavailable                 |
| <env_name>-ff_log_delete_patient_data_failed          |
| <env_name>-ff_log_delete_patient_index_failed         |
| <env_name>-ff_log_unsupported_sqs_event               |
| <env_name>-ff_log_unsupported_dynamodb_streams_event  |
| <env_name>-ff_log_sfn_is_unavailable                  |
| <env_name>-ff_log_oss_is_unavailable                  |
| <env_name>-ff_log_sqs_is_unavailable                  |
| <env_name>-cc_log_sqs_event_is_invalid                |
| <env_name>-ce_log_sqs_or_dynamodb_event_is_invalid    |
| <env_name>-ff_log_idam_is_unavailable                 |

@SRS-68697.07
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68697
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Monitoring
Scenario: Service Alert is triggered when alerting threshold reached
Given I logged in to Kloudfuse application
And I opened "<env_name>_wafv2_blocked_requests_alert" alert from 'Alerts' menu
And I see that the selected alert status is 'OK' in Kloudfuse
When the following request is sent to OncoCare repeatedly for 6 minutes:
| method  | endpoint_path                                                                                                          |
| GET     | <app_base_url>/patients/62812e9a-96ae-4b59-85fe-43872df6bd78/comments/?param=%3Cjavascript%3Aalert(document.cookie%3E) |
And the received HTTP status code is 403
Then I see that the selected alert status changed to 'ALERTING' in Kloudfuse
And the metric value reached the alerting threshold
And the datapoint of the current time is in the red highlighted area on the graph

@SRS-68697.08
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68697
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Monitoring
Scenario: Patient Summary alert is triggered when alerting threshold reached
Given I logged in to Kloudfuse application
And I opened "<env_name>-summary-high-frequency-invocations" alert from 'Alerts' menu
And I see that the selected alert status is 'OK' in Kloudfuse
When I update the following parameter in "testdata/configuration/repo/default/patient-summary/patient-summary-config.json":
| parameter_name          | updated_parameter_value |
| monitoredListUpdateRule |  * * * * ? *            |
And I update the Patient Summary configuration by sending the following request:
| method | endpoint_path           | payload                                                                         |
| PUT    | <summary_api_gw>/config | testdata/configuration/repo/default/patient-summary/patient-summary-config.json |
And the received HTTP status code is 201
Then I see that the selected alert status changed to 'ALERTING' in Kloudfuse after 30 minutes
And the metric value reached the alerting threshold
And the datapoint of the current time is in the red highlighted area on the graph

@SRS-68697.09
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68697
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Monitoring
Scenario: Log based alert is triggered when alerting threshold reached
Given I logged in to Kloudfuse application
And I opened "<env_name>-audit_log_failed" alert from 'Alerts' menu
And I see that the selected alert status is 'OK' in Kloudfuse
When I disable audit logging (see READ.me):
And I wait until the representation service finished updating
And the following request is sent to OncoCare repeatedly for 6 minutes:
| method  | endpoint_path                                                         | payload                       |
| POST    | <app_base_url>/patients/62812e9a-96ae-4b59-85fe-43872df6bd78/comments | { "content":"alert comment" } |
And the received HTTP status code is 201
Then I see that the selected alert status changed to 'ALERTING' in Kloudfuse after 5 minutes
And the metric value reached the alerting threshold
And the datapoint of the current time is in the red highlighted area on the graph

@SRS-68697.10
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68697
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario: Failure ingestion in DPSA
When the following patient was deleted from PDS: dpsaReq/partial_req_bundle.json
Given the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient YudaId, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue YudaId
When the following bundle was uploaded to DCC: dpsaReq/bad_req_bundle.json
Then the received HTTP status code is 200
And the "entry.[*]" property contains a list where the elements have "response.status" properties with below values: 400, 400
And the following event-handler system log is present with no correlation id: failure
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the user opened the patient select menu
And the user typed into the patient search field: OncoTestPatien BadReq
Then patient list is empty
When the following bundle was uploaded to DCC: dpsaReq/partial_req_bundle.json
Then the received HTTP status code is 200
And the "entry.[*]" property contains a list where the elements have "response.status" properties with below values: 201, 400, 201
And the following event-handler system log is present with no correlation id: partial-failure
And 1 mandatory ETL processes are finished within 180 seconds with status Succeeded for patient YudaId
When the user clicks on the browser's refresh button
And the user selects the "OncoTestPatient, Yuda" patient
Then the patient summary view is loaded
And the "Diagnosis" group has 3 cards
And the 1st card in "Diagnosis" group is empty
And the 2nd card in "Diagnosis" group is empty
And the 3rd card in "Diagnosis" group is empty
