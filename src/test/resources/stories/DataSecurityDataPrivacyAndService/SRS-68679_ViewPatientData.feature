Feature: [SysRS-68679] View patient data

Narrative: The system's user role shall be the only role who can use the application to view patient data.


@SRS-68679.01
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68679,SysRS-68678
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Access_Control
Scenario: Review role definitions and permissions in IDAM
Given I log in to IDAM application
And I select 'Application Groups' menu
And I click on 'Details' link for the Precision Insights deployment currently being tested (e.g. precision-insights)
And I click on 'Details' link for required Application from the list (e.g. precision-insights-test-1)
When I select 'Roles' tab
Then application displays Role table containing the following roles:
|Role name  |API scopes                                                                                                                                                                                                 |
|user       |pi.adherence.full, pi.representation.full                                                                                                                                                                  |
|service    |pi.adherence.configuration.full, pi.criteria.configuration.full, pi.eligibility.configuration.full, pi.fulfillment.configuration.full, pi.representation.configuration.full, pi.summary.configuration.full |

@SRS-68679.02
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68679,SysRS-68678,SysRS-68680
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Access_Control
Scenario: Verify application user role cannot access Kloudfuse
When the application user tries to log into Kloudfuse application
Then the following message shall be displayed: "Authentication error! Something went wrong during the authentication process. Please try signing in again."
And the user cannot access any menu in Kloudfuse

@SRS-68679.03
@srs
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68679,SysRS-68678,SysRS-68680
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Access_Control
Scenario Outline: Verify application user role cannot access configuration API
When the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method   | endpoint_path   | payload   | content_type   |
| core    | <method> | <endpoint_path> | <payload> | <content_type> |
Then the received HTTP status code is <status_code>
And the [DEFAULT-TEST-USER] token is reset
Examples:
| method | endpoint_path                                        | payload                                                                                                                                                                                                          | content_type     | status_code |
| GET    | /configs/app                                         | N/A                                                                                                                                                                                                              | application/json | 403         |
| PUT    | /configs/app                                         | {"dicomViewerUrl": "https://10.177.217.216", "patientIdentifier": {"system": "","type": {"code": "","system": ""}},"oncologyDepartmentIdentifier": {"system": "","type": {"code": "","system": ""},"value": ""}} | application/json | 403         |
| GET    | /configs/representation/patientBanner.onco.extended  | N/A                                                                                                                                                                                                              | application/json | 403         |
| PUT    | /configs/representation/summary.onco.extended        | configuration/modified_configs/queryset/summary-onco-modified.json                                                                                                                                               | application/json | 403         |
| GET    | /configs/fulfillment-query-set/summary.onco.extended | N/A                                                                                                                                                                                                              | application/json | 403         |
| PUT    | /configs/fulfillment-query-set/trend.onco.extended   | configuration/modified_configs/queryset/trend-onco-modified.json                                                                                                                                                 | application/json | 403         |
| GET    | /configs/app-preset/oncology.default                 | N/A                                                                                                                                                                                                              | application/json | 403         |
| PUT    | /configs/app-preset/oncology.default                 | configuration/modified_configs/app-preset/oncology-default-modified.json                                                                                                                                        | application/json | 403         |