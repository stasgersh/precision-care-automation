Feature: [SysRS-70299] Medical History Configuration

Narrative: The system shall provide GEHC servie user the ability to configure Medical history with the following:
           - Groups
           - Information to display in each group using unified building blocks (representation elements), except completed medication group dropdown).


@SRS-70299.01
@configChange
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70299
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/System_Configuration
Scenario: Service User can modify the displayed Medical history sections and their order via API
Given the following request was sent to Precision Insights by service user app:
| service | method | endpoint_path                                              | payload                                                                    | content_type     |
| core    | PUT    | /configs/fulfillment-query-set/medicalHistory.onco.general | configuration/modified_configs/queryset/medical-history-onco-modified.json | application/json |
And the received HTTP status code was 204
And the following request was sent to Precision Insights by service user app:
| service | method | endpoint_path                                       | payload                                                                          | content_type     |
| core    | PUT    | /configs/representation/medicalHistory.onco.general | configuration/modified_configs/representation/medical-history-onco-modified.json | application/json |
And the received HTTP status code was 204
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the Patient page is loaded
When the user navigates to the "Medical history" view
Then the Medical history view is displayed
And the Medical history sections are displayed in the following order:
| sections                   |
| Patient history            |
| Medications                |
| Allergies and intolerances |

@SRS-70299.02
@configChange
@querySetChange
@Platform:Rest_API
@api
@Category:Negative
@Traceability:SysRS-70299
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/System_Configuration
Scenario Outline: Service User cannot upload an invalid representation config for the Medical History view via API
When the following request is sent to Precision Insights by service user app:
| service | method | endpoint_path | payload       | content_type     |
| core    | PUT    | <endpoint>    | <config_file> | application/json |
Then the received HTTP status code is 400

Examples:
| endpoint                                                   | config_file                                                                    |
| /configs/representation/medicalHistory.onco.general        | configuration/invalid_configs/representation/medical-history-onco-invalid.json |
| /configs/representation/medicalHistory.onco.general        | configuration/invalid_configs/representation/medical-history-onco-invalid.json |
| /configs/fulfillment-query-set/medicalHistory.onco.general | configuration/invalid_configs/queryset/medical-history-onco-invalid.json       |
| /configs/fulfillment-query-set/medicalHistory.onco.general | configuration/invalid_configs/queryset/medical-history-onco-invalid.json       |
