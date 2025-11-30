Feature: [SysRS-70948] Summary Dashboard Configuration

Narrative: The system shall allow GEHC service user to configure summary dashboard with the following:
           - Cards data content (data card, measurement card/lab result card, chart) and define cards within each group.
           - Navigator toolbar appearance (Jump-link) - on/off and view order in the Navigator toolbar (according to card groups order configuration).
           - HyperLink layout (enable/disable):
              - Right and Left panel
                Left panel: name (currently “treatment”) and tab link.
                Right panel: name (currently “response”) and tab link.


@SRS-70948.01
@SPR-4437
@configChange
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70948
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/System_Configuration
Scenario: Service User can modify the order of the elements on the Summary dashboard
Given the following request was sent to Precision Insights by service user app:
| service | method | endpoint_path                                 | payload                                                                        | content_type     |
| core    | PUT    | /configs/representation/summary.onco.extended | configuration/modified_configs/representation/summary-onco-modified-order.json | application/json |
And the received HTTP status code was 204
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Juno" patient
Then the patient summary view is loaded
And the Summary view contains the following groups:
| column_index | group_index_in_column | group_name        | badge_text  |
| 0            | 0                     | Medical history   | <N/A>       |
| 0            | 1                     | Comments          | Most recent |
| 0            | 2                     | Molecular profile | <N/A>       |
| 1            | 0                     | Oncology note     | Most recent |
| 1            | 1                     | Imaging           | Most recent |
| 1            | 2                     | ER visit          | Most recent |
| 1            | 3                     | MDT report        | Most recent |
| 2            | 0                     | Labs              | <N/A>       |
| 2            | 1                     | Treatments        | <N/A>       |
| 2            | 2                     | Diagnosis         | <N/A>       |
| 2            | 3                     | Care team         | <N/A>       |

@SRS-70948.02
@configChange
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70948
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/System_Configuration
Scenario: Service User can turn off or modify sections on the Summary dashboard
Given the following request was sent to Precision Insights by service user app:
| service | method | endpoint_path                                        | payload                                                            | content_type     |
| core    | PUT    | /configs/fulfillment-query-set/summary.onco.extended | configuration/modified_configs/queryset/summary-onco-modified.json | application/json |
And the following request was sent to Precision Insights by service user app:
| service | method | endpoint_path                                 | payload                                                                           | content_type     |
| core    | PUT    | /configs/representation/summary.onco.extended | configuration/modified_configs/representation/summary-onco-modified-sections.json | application/json |
And the received HTTP status code was 204
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Juno" patient
Then the patient summary view is loaded
And the navigation toolbar does not contain any section link
And the Summary view contains the following groups:
| column_index | group_index_in_column | group_name        | badge_text  |
| 0            | 0                     | Diagnosis         | <N/A>       |
| 0            | 1                     | Molecular profile | <N/A>       |
| 1            | 0                     | Imaging           | Most recent |
| 1            | 1                     | Oncology note     | Most recent |
| 1            | 2                     | MDT report        | Most recent |
| 2            | 0                     | Treatments        | <N/A>       |
| 2            | 1                     | Labs              | <N/A>       |
| 2            | 2                     | Medical history   | <N/A>       |
And the 2nd card in "Treatments" group equals the following data:
| data_type   | data                                                             |
| CARD_TITLE  | Radiation                                                        |
| NORMAL_TEXT | Most recent procedure                                            |
| KEY_VALUE   | [key]: Radiotherapy course of treatment    [value]: Aug 15, 2018 |
| NORMAL_TEXT | Start of therapy                                                 |
| KEY_VALUE   | [key]: Radiotherapy course of treatment    [value]: Aug 15, 2018 |
And the 3rd card in "Diagnosis" group equals the following data:
| data_type  | data                                            |
| CARD_TITLE | Key events                                      |
| TABLE      | [source]: summary/table_on_diagnosis_Juno.table |

@SRS-70948.03
@configChange
@querySetChange
@Platform:Rest_API
@api
@Category:Negative
@Traceability:SysRS-70948
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/System_Configuration
Scenario Outline: Service User cannot upload an invalid representation config for the Summary dashboard view via API
When the following request is sent to Precision Insights by service user app:
| service | method | endpoint_path | payload       | content_type     |
| core    | PUT    | <endpoint>    | <config_file> | application/json |
Then the received HTTP status code is 400

Examples:
| endpoint                                             | config_file                                                            |
| /configs/representation/summary.onco.extended        | configuration/invalid_configs/representation/summary-onco-invalid.json |
| /configs/representation/summary.onco.extended        | configuration/invalid_configs/representation/summary-onco-invalid.json |
| /configs/fulfillment-query-set/summary.onco.extended | configuration/invalid_configs/queryset/summary-onco-invalid.json       |
| /configs/fulfillment-query-set/summary.onco.extended | configuration/invalid_configs/queryset/summary-onco-invalid.json       |