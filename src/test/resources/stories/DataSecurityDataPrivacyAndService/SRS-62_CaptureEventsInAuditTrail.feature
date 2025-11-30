Feature: [SysRS-68601] Capture Events In Audit Trail

Narrative: The system shall capture the following actions (made by users/ services) in the audit trail:
             - Access to the system (login, logout).
             - Create/Update/Delete configuration items.
             - Create/Update/Delete valuesets.
             - Create/Update/Delete data models (also called Data entities).
             - Add/Delete comments.
             - Add labels.
             - Display/Export patient data.


@srs
@SRS-62.01
@audit_log
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68682,SysRS-68686,SysRS-68603,SysRS-68600,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario: Audit log about creating patient comment
Given the "username" of [DEFAULT-TEST-USER] user is stored as "audit.test.username"
And the "organizationName" of [DEFAULT-TEST-USER] user is stored as "audit.test.organizationName"
When [API] the below patient comments are added by the user [DEFAULT-TEST-USER]:
| patientID                            | comment_text                   |
| a10e48ab-b927-9ee1-84a4-41638a84e05a  | audit test log patient comment |
And [API] the id of the below patient comment is stored as "audit.test.comment.id":
| patientID                            | comment_text                   |
| a10e48ab-b927-9ee1-84a4-41638a84e05a  | audit test log patient comment |
Then audit log is found by the service user with below parameters:
| property_path                               | property_value                                          |
| $.type.display                              | Patient Record                                          |
| $.type.code                                 | 110110                                                  |
| $.entity[0].detail[0].valueString           | PATIENT_IDENTITY_FEED                                   |
| $.agent[?(@.requestor==true)].who.reference | <<audit.test.username>>@<<audit.test.organizationName>> |
| $.action                                    | C                                                       |
| $.outcome                                   | 0                                                       |
| $.entity[0].what.reference                  | Patient/a10e48ab-b927-9ee1-84a4-41638a84e05a            |
| $.outcomeDesc                               | comment created: <<audit.test.comment.id>>              |

@srs
@SRS-62.02
@audit_log
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68682,SysRS-68686,SysRS-68603,SysRS-68600,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario: Audit log about creating event comment
Given the "username" of [DEFAULT-TEST-USER] user is stored as "audit.test.username"
And the "organizationName" of [DEFAULT-TEST-USER] user is stored as "audit.test.organizationName"
When [API] the below event comments are added by the user [DEFAULT-TEST-USER]:
| patientID                            | root_resource_of_the_event                            | comment_text                 |
| a10e48ab-b927-9ee1-84a4-41638a84e05a  | DiagnosticReport/ef98eb70-cb59-4cd3-bc70-97bb04bdb4df | audit test log event comment |
And [API] the id of the below event comment is stored as "audit.test.event.comment.id":
| patientID                            | rootResourceID                                        | comment_text                 |
| a10e48ab-b927-9ee1-84a4-41638a84e05a  | DiagnosticReport/ef98eb70-cb59-4cd3-bc70-97bb04bdb4df | audit test log event comment |
Then audit log is found by the service user with below parameters:
| property_path                               | property_value                                          |
| $.type.display                              | Patient Record                                          |
| $.type.code                                 | 110110                                                  |
| $.entity[0].detail[0].valueString           | PATIENT_IDENTITY_FEED                                   |
| $.agent[?(@.requestor==true)].who.reference | <<audit.test.username>>@<<audit.test.organizationName>> |
| $.action                                    | C                                                       |
| $.outcome                                   | 0                                                       |
| $.entity[0].what.reference                  | Patient/a10e48ab-b927-9ee1-84a4-41638a84e05a            |
| $.outcomeDesc                               | comment created: <<audit.test.event.comment.id>>        |


@srs
@SRS-62.03
@audit_log
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68682,SysRS-68686,SysRS-68603,SysRS-68600,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario: Audit log about deleting a patient comment
Given the "username" of [DEFAULT-TEST-USER] user is stored as "audit.test.username"
And the "organizationName" of [DEFAULT-TEST-USER] user is stored as "audit.test.organizationName"
And [API] the below patient comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | comment_text                   |
| a10e48ab-b927-9ee1-84a4-41638a84e05a  | audit test log patient comment |
And [API] the id of the below patient comment is stored as "audit.test.delete.patient.comment.id":
| patientID                            | comment_text                   |
| a10e48ab-b927-9ee1-84a4-41638a84e05a  | audit test log patient comment |
When [API] the [DEFAULT-TEST-USER] user deletes all patient and event comments for the following patient:
| patientID                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a  |
Then audit log is found by the service user with below parameters:
| property_path                               | property_value                                                                                                          |
| $.type.display                              | Patient Record                                                                                                          |
| $.type.code                                 | 110110                                                                                                                  |
| $.entity[0].detail[0].valueString           | PATIENT_IDENTITY_FEED                                                                                                   |
| $.agent[?(@.requestor==true)].who.reference | <<audit.test.username>>@<<audit.test.organizationName>>                                                                 |
| $.action                                    | D                                                                                                                       |
| $.outcome                                   | 0                                                                                                                       |
| $.entity[0].what.reference                  | Patient/a10e48ab-b927-9ee1-84a4-41638a84e05a                                                                            |
| $.outcomeDesc                               | Delete comment <<audit.test.delete.patient.comment.id>> by user <<audit.test.username>>@<<audit.test.organizationName>> |


@srs
@SRS-62.04
@audit_log
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68682,SysRS-68686,SysRS-68600,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario: Audit log about deleting an event comment
Given the "username" of [DEFAULT-TEST-USER] user is stored as "audit.test.username"
And the "organizationName" of [DEFAULT-TEST-USER] user is stored as "audit.test.organizationName"
And [API] the below event comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | root_resource_of_the_event                            | comment_text                 |
| a10e48ab-b927-9ee1-84a4-41638a84e05a  | DiagnosticReport/ef98eb70-cb59-4cd3-bc70-97bb04bdb4df | audit test log event comment |
And [API] the id of the below event comment is stored as "audit.test.delete.event.comment.id":
| patientID                            | rootResourceID                                        | comment_text                 |
| a10e48ab-b927-9ee1-84a4-41638a84e05a  | DiagnosticReport/ef98eb70-cb59-4cd3-bc70-97bb04bdb4df | audit test log event comment |
When [API] the [DEFAULT-TEST-USER] user deletes all patient and event comments for the following patient:
| patientID                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a  |
Then audit log is found by the service user with below parameters:
| property_path                               | property_value                                                                                                        |
| $.type.display                              | Patient Record                                                                                                        |
| $.type.code                                 | 110110                                                                                                                |
| $.entity[0].detail[0].valueString           | PATIENT_IDENTITY_FEED                                                                                                 |
| $.agent[?(@.requestor==true)].who.reference | <<audit.test.username>>@<<audit.test.organizationName>>                                                               |
| $.action                                    | D                                                                                                                     |
| $.outcome                                   | 0                                                                                                                     |
| $.entity[0].what.reference                  | Patient/a10e48ab-b927-9ee1-84a4-41638a84e05a                                                                          |
| $.outcomeDesc                               | Delete comment <<audit.test.delete.event.comment.id>> by user <<audit.test.username>>@<<audit.test.organizationName>> |


@SRS-62.05
@srs
@audit_log
@configChange
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68682,SysRS-68686,SysRS-68603,SysRS-68600,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario: Audit log about viewing a patient
Given the following request was sent to Precision Insights by service user app:
| service | method | endpoint_path | payload                                  | content_type     |
| core    | PUT    | /configs/app  | configuration/app_config_cache_off.json  | application/json |
And the "username" of [DEFAULT-TEST-USER] user is stored as "audit.test.username"
And the "organizationName" of [DEFAULT-TEST-USER] user is stored as "audit.test.organizationName"
When the [DEFAULT-TEST-USER] user logs in to OncoCare
Then audit log is found by the service user with below parameters and no correlation id:
| property_path                               | property_value                                          |
| $.type.display                              | Patient Record                                          |
| $.type.code                                 | 110110                                                  |
| $.entity[0].detail[0].valueString           | PATIENT_IDENTITY_FEED                                   |
| $.agent[?(@.requestor==true)].who.reference | <<audit.test.username>>@<<audit.test.organizationName>> |
| $.action                                    | R                                                       |
| $.outcome                                   | 0                                                       |
| $.entity[0].what.reference                  | Patient/All Patients                                    |
| $.outcomeDesc                               | patient data successfully accessed by patient list      |
When the user selects the "OncoTestPatient, Torvald" patient
Then audit log is found by the service user with below parameters and no correlation id:
| property_path                               | property_value                                                    |
| $.type.display                              | Patient Record                                                    |
| $.type.code                                 | 110110                                                            |
| $.entity[0].detail[0].valueString           | PATIENT_IDENTITY_FEED                                             |
| $.agent[?(@.requestor==true)].who.reference | <<audit.test.username>>@<<audit.test.organizationName>>           |
| $.action                                    | R                                                                 |
| $.outcome                                   | 0                                                                 |
| $.entity[0].what.reference                  | Patient/cee5f972-9531-4cd4-b1e4-599c9589a177                      |
| $.outcomeDesc                               | [FROM_FILE]: auditLogs/details_of_patientProfile_with_queries.txt |
And audit log is found by the service user with below parameters and no correlation id:
| property_path                               | property_value                                             |
| $.type.display                              | Patient Record                                             |
| $.type.code                                 | 110110                                                     |
| $.entity[0].detail[0].valueString           | PATIENT_IDENTITY_FEED                                      |
| $.agent[?(@.requestor==true)].who.reference | <<audit.test.username>>@<<audit.test.organizationName>>    |
| $.action                                    | R                                                          |
| $.outcome                                   | 0                                                          |
| $.entity[0].what.reference                  | Patient/cee5f972-9531-4cd4-b1e4-599c9589a177               |
| $.outcomeDesc                               | [FROM_FILE]: auditLogs/details_of_summary_with_queries.txt |
When the user navigates to the "Timeline" view
And the user clicks on the browser's refresh button
Then audit log is found by the service user with below parameters and no correlation id:
| property_path                               | property_value                                              |
| $.type.display                              | Patient Record                                              |
| $.type.code                                 | 110110                                                      |
| $.entity[0].detail[0].valueString           | PATIENT_IDENTITY_FEED                                       |
| $.agent[?(@.requestor==true)].who.reference | <<audit.test.username>>@<<audit.test.organizationName>>     |
| $.action                                    | R                                                           |
| $.outcome                                   | 0                                                           |
| $.entity[0].what.reference                  | Patient/cee5f972-9531-4cd4-b1e4-599c9589a177                |
| $.outcomeDesc                               | [FROM_FILE]: auditLogs/details_of_timeline_with_queries.txt |
When the user navigates to the "Medical history" view
And the user clicks on the browser's refresh button
Then audit log is found by the service user with below parameters and no correlation id:
| property_path                               | property_value                                                |
| $.type.display                              | Patient Record                                                |
| $.type.code                                 | 110110                                                        |
| $.entity[0].detail[0].valueString           | PATIENT_IDENTITY_FEED                                         |
| $.agent[?(@.requestor==true)].who.reference | <<audit.test.username>>@<<audit.test.organizationName>>       |
| $.action                                    | R                                                             |
| $.outcome                                   | 0                                                             |
| $.entity[0].what.reference                  | Patient/cee5f972-9531-4cd4-b1e4-599c9589a177                  |
| $.outcomeDesc                               | [FROM_FILE]: auditLogs/details_of_medHistory_with_queries.txt |
When the user navigates to the "Trends" view
And the user clicks on the browser's refresh button
Then audit log is found by the service user with below parameters and no correlation id:
| property_path                               | property_value                                            |
| $.type.display                              | Patient Record                                            |
| $.type.code                                 | 110110                                                    |
| $.entity[0].detail[0].valueString           | PATIENT_IDENTITY_FEED                                     |
| $.agent[?(@.requestor==true)].who.reference | <<audit.test.username>>@<<audit.test.organizationName>>   |
| $.action                                    | R                                                         |
| $.outcome                                   | 0                                                         |
| $.entity[0].what.reference                  | Patient/cee5f972-9531-4cd4-b1e4-599c9589a177              |
| $.outcomeDesc                               | [FROM_FILE]: auditLogs/details_of_trends_with_queries.txt |


@srs
@SRS-62.06
@audit_log
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68682,SysRS-68686,SysRS-68603,SysRS-68600,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario: Audit log about creating a tag (label)
Given the "username" of [DEFAULT-TEST-USER] user is stored as "audit.test.username"
And the "organizationName" of [DEFAULT-TEST-USER] user is stored as "audit.test.organizationName"
When [API] the user creates the following labels:
| label_name                                      |
| audit log label test <<create_label_timestamp>> |
Then audit log is found by the service user with below parameters:
| property_path                               | property_value                                          |
| $.type.display                              | Software Configuration                                  |
| $.type.code                                 | 110131                                                  |
| $.entity[0].detail[0].valueString           | PATIENT_IDENTITY_FEED                                   |
| $.agent[?(@.requestor==true)].who.reference | <<audit.test.username>>@<<audit.test.organizationName>> |
| $.action                                    | C                                                       |
| $.outcome                                   | 0                                                       |
| $.outcomeDesc                               | label created                                           |


@srs
@SRS-62.07
@audit_log
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68682,SysRS-68686,SysRS-68603,SysRS-68600,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario: Audit log about exporting Summary view
Given the "username" of [DEFAULT-TEST-USER] user is stored as "audit.test.username"
And the "organizationName" of [DEFAULT-TEST-USER] user is stored as "audit.test.organizationName"
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the user navigated to the "Summary" view
When the user clicks the "More" button on the Navigation toolbar
And the user clicks on the "Download" option in the View options
And the user clicks on the "Entire summary" option in the sub-menu in the View options
Then audit log is found by the service user with below parameters:
| property_path                                   | property_value                                          |
| $.type.display                                  | Patient Record                                          |
| $.type.code                                     | 110110                                                  |
| $.entity[0].detail[0].valueString               | PATIENT_IDENTITY_FEED                                   |
| $.agent[?(@.requestor==true)].who.reference     | <<audit.test.username>>@<<audit.test.organizationName>> |
| $.action                                        | R                                                       |
| $.outcome                                       | 0                                                       |
| $.entity[0].what.reference                      | Patient/cee5f972-9531-4cd4-b1e4-599c9589a177           |
| $.outcomeDesc                                   | summary exported                                        |


@srs
@audit_log
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68682,SysRS-68686,SysRS-68603,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario: Audit log about updating user settings
Given the "username" of [DEFAULT-TEST-USER] user is stored as "audit.test.username"
And the "organizationName" of [DEFAULT-TEST-USER] user is stored as "audit.test.organizationName"
When [API] the [DEFAULT-TEST-USER] user turns OFF the CLP visualization
Then audit log is found by the service user with below parameters:
| property_path                               | property_value                                          |
| $.type.display                              | Software Configuration                                  |
| $.type.code                                 | 110131                                                  |
| $.entity[0].detail[0].valueString           | PATIENT_IDENTITY_FEED                                   |
| $.agent[?(@.requestor==true)].who.reference | <<audit.test.username>>@<<audit.test.organizationName>> |
| $.action                                    | U                                                       |
| $.outcome                                   | 0                                                       |
| $.outcomeDesc                               | user settings successfully updated                      |


@srs
@audit_log
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68682,SysRS-68686,SysRS-68603,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario: Audit log about updating patient banner configuration
When the following request is sent to Precision Insights by service user app:
| service | method | endpoint_path                                       |
| core    | GET    | /configs/representation/patientBanner.onco.extended |
And the user stores response json as "patient_banner_config"
And the following request is sent to Precision Insights by service user app:
| service | method | endpoint_path                                       | payload                   | content_type     |
| core    | PUT    | /configs/representation/patientBanner.onco.extended | <<patient_banner_config>> | application/json |
Then the received HTTP status code is 204
And audit log is found by the service user with below parameters and no correlation id:
| property_path                               | property_value                                                                    |
| $.type.display                              | Software Configuration                                                            |
| $.type.code                                 | 110131                                                                            |
| $.entity[0].detail[0].valueString           | PATIENT_IDENTITY_FEED                                                             |
| $.agent[?(@.requestor==true)].who.reference | <<service_user_credentials>>                                                      |
| $.action                                    | U                                                                                 |
| $.outcome                                   | 0                                                                                 |
| $.outcomeDesc                               | Successful config operation (UPDATE) for configID of patientBanner.onco.extended. |


@srs
@SRS-62.08
@manual
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68682,SysRS-68686,SysRS-68603
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Audit_log
Scenario: Audit log about retrieving CareIntellect for Oncology configuration through config API
Given the "client_id" of service user app is stored as audit.test.clientid
And the "application_group" of service user is stored as "audit.test.applicationgroup"
When the following request is sent to Precision Insights by service user app:
| service | method | endpoint_path | payload  | content_type     |
| core    | GET    | /configs/app  | N/A      | application/json |
Then the received HTTP status code is 200
And audit log is found by the service user with below parameters and no correlation id:
| property_path                               | property_value                                           |
| $.type.display                              | Software Configuration                                           |
| $.type.code                                 | 110131                                                   |
| $.entity[0].detail[0].valueString           | PATIENT_IDENTITY_FEED                                    |
| $.agent[?(@.requestor==true)].who.reference | <<audit.test.clientid>>@<<audit.test.applicationgroup>>  |
| $.action                                    | R                                                        |
| $.outcome                                   | 0                                                        |
| $.outcomeDesc                               | Successful config operation (READ) for configID of app.  |

@srs
@SRS-62.09
@manual
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68682,SysRS-68686,SysRS-68603
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Audit_log
Scenario: Audit log about changing CareIntellect for Oncology configuration through config API
Given the "client_id" of service user app is stored as audit.test.clientid
And the "application_group" of service user is stored as "audit.test.applicationgroup"
When the following request is sent to Precision Insights by service user app:
| service | method | endpoint_path | payload                               | content_type     |
| core    | PUT    | /configs/app  | configuration/default_app_config.json | application/json |
Then the received HTTP status code is 204
And audit log is found by the service user with below parameters:
| property_path                               | property_value                                           |
| $.type.display                              | Software Configuration                                           |
| $.type.code                                 | 110131                                                   |
| $.entity[0].detail[0].valueString           | PATIENT_IDENTITY_FEED                                    |
| $.agent[?(@.requestor==true)].who.reference | <<audit.test.clientid>>@<<audit.test.applicationgroup>>  |
| $.action                                    | U                                                        |
| $.outcome                                   | 0                                                        |
| $.outcomeDesc                               | Successful config operation (UPDATE) for configID of app |

@srs
@SRS-62.12
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68682,SysRS-68686,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Error_Message
Scenario: Error message is logged in service logs if audit logging fails during creating patient comment
When I send the following request to CareIntellect for Oncology application to create patient comment while audit logging is unavailable (see READ.me):
| method  | patientID                             | comment_text                   |
| POST    | a10e48ab-b927-9ee1-84a4-41638a84e05a  | audit test log patient comment |
Then the following new error messages are available in the service logs:
| service_log                                                                                                                                            |
| "Store comment for patient [a10e48ab-b927-9ee1-84a4-41638a84e05a] event [undefined] by user [<<audit.test.username>>@<<audit.test.organizationName>>]" |
| "res":{"statusCode":201},"responseTime":<ANY_VALUE>,"message":"request completed"                                                                      |
| "Failed to send audit log. Reason: CloudWatchException: explicit deny in an identity-based policy"                                                     |

@srs
@SRS-62.13
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68682,SysRS-68686,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Error_Message
Scenario: Error message is logged in service logs if audit logging fails during creating event comment
When I send the following request to CareIntellect for Oncology application to create event comment while audit logging is unavailable (see READ.me):
| method  | patientID                             | root_resource_of_the_event                            | comment_text                 |
| POST    | a10e48ab-b927-9ee1-84a4-41638a84e05a  | DiagnosticReport/ef98eb70-cb59-4cd3-bc70-97bb04bdb4df | audit test log event comment |
Then the following new error messages are available in the service logs:
| service_log                                                                                                                                                                                       |
| "Store comment for patient [a10e48ab-b927-9ee1-84a4-41638a84e05a] event [pathologyReport:ef98eb70-cb59-4cd3-bc70-97bb04bdb4df] by user [<<audit.test.username>>@<<audit.test.organizationName>>]" |
| "res":{"statusCode":201},"responseTime":<ANY_VALUE>,"message":"request completed"                                                                                                                 |
| "Failed to send audit log. Reason: CloudWatchException: explicit deny in an identity-based policy"                                                                                                |

@srs
@SRS-62.14
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68682,SysRS-68686,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Error_Message
Scenario: Error message is logged in service logs if audit logging fails during deleting patient comment
When I send the following request to CareIntellect for Oncology application to delete patient comment while audit logging is unavailable (see READ.me):
| method  | patientID                             | comment_text                   |
| DELETE  | a10e48ab-b927-9ee1-84a4-41638a84e05a  | audit test log patient comment |
Then the following new error messages are available in the service logs:
| service_log                                                                                             |
| "Delete comment <<DELETED_COMMENT_ID>> by user <<audit.test.username>>@<<audit.test.organizationName>>" |
| "res":{"statusCode":204},"responseTime":<ANY_VALUE>,"message":"request completed"                       |
| "Failed to send audit log. Reason: CloudWatchException: explicit deny in an identity-based policy"      |

@srs
@SRS-62.15
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68682,SysRS-68686,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Error_Message
Scenario: Error message is logged in service logs if audit logging fails during deleting event comment
When I send the following request to CareIntellect for Oncology application to delete event comment while audit logging is unavailable (see READ.me):
| method  | patientID                             | root_resource_of_the_event                            | comment_text                 |
| POST    | a10e48ab-b927-9ee1-84a4-41638a84e05a  | DiagnosticReport/ef98eb70-cb59-4cd3-bc70-97bb04bdb4df | audit test log event comment |
Then the following new error messages are available in the service logs:
| service_log                                                                                             |
| "Delete comment <<DELETED_COMMENT_ID>> by user <<audit.test.username>>@<<audit.test.organizationName>>" |
| "res":{"statusCode":204},"responseTime":<ANY_VALUE>,"message":"request completed"                       |
| "Failed to send audit log. Reason: CloudWatchException: explicit deny in an identity-based policy"      |

@srs
@SRS-62.16
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68682,SysRS-68686,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Error_Message
Scenario: Error message is logged in service logs if audit logging fails when selecting a patient
When I send the following request to CareIntellect for Oncology application to select a patient while audit logging is unavailable (see READ.me):
| method  | patientID                             |
| GET     | a10e48ab-b927-9ee1-84a4-41638a84e05a  |
Then the following new error messages are available in the service logs:
| service_log                                                                                                                                         |
| "method":"GET","url":"/patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/summary?usingAi=<ANY_VALUE>&eligibilityVersion=<ANY_VALUE>&version=<ANY_VALUE> |
| "res":{"statusCode":200},"responseTime":<ANY_VALUE>,"message":"request completed"                                                                   |
| "Failed to send audit log. Reason: CloudWatchException: explicit deny in an identity-based policy"                                                  |

@srs
@SRS-62.17
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68682,SysRS-68686,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Error_Message
Scenario: Error message is logged in service logs if audit logging fails during creating a tag (label)
When I send the following request to CareIntellect for Oncology application to create a label while audit logging is unavailable (see READ.me):
| method  | label_name                                      |
| POST    | audit log label test <<create_label_timestamp>> |
Then the following new error messages are available in the service logs:
| service_log                                                                                        |
| "Create label by user [<<audit.test.username>>@<<audit.test.organizationName>>]"                   |
| "res":{"statusCode":201},"responseTime":<ANY_VALUE>,"message":"request completed"                  |
| "Failed to send audit log. Reason: CloudWatchException: explicit deny in an identity-based policy" |

@srs
@SRS-62.18
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68682,SysRS-68686,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Error_Message
Scenario: Error message is logged in service logs if audit logging fails during exporting Summary view
When I send the following request to CareIntellect for Oncology application to export Summary view while audit logging is unavailable (see READ.me):
| method  | patientID                             |
| POST    | cee5f972-9531-4cd4-b1e4-599c9589a177  |
Then the following new error messages are available in the service logs:
| service_log                                                                                         |
| "method":"POST","url":"/patients/cee5f972-9531-4cd4-b1e4-599c9589a177/exports/summary/"             |
| "res":{"statusCode":204},"responseTime":<ANY_VALUE>,"message":"request completed"                   |
| "Failed to send audit log. Reason: CloudWatchException: explicit deny in an identity-based policy"  |

@srs
@SRS-62.19
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68682,SysRS-68686,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Error_Message
Scenario: Error message is logged in service logs if audit logging fails during changing application configuration via API
When I send the following request to CareIntellect for Oncology application to change application configuration while audit logging is unavailable (see READ.me):
| method  | payload                               |
| PUT     | configuration/default_app_config.json |
Then the following new error messages are available in the service logs:
| service_log                                                                                         |
| "method":"PUT","url":"/configs/app"                                                                 |
| "res":{"statusCode":204},"responseTime":<ANY_VALUE>,"message":"request completed"                   |
| "Failed to send audit log. Reason: CloudWatchException: explicit deny in an identity-based policy"  |

@srs
@SRS-62.23
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68682,SysRS-68686,SysRS-68603,SysRS-68600,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Audit_log
Scenario: Audit log is created about the failed patient comment creation
Given I logged in to CareIntellect for Oncology application as [DEFAULT-TEST-USER] in the browser
And I noted the "username" of [DEFAULT-TEST-USER] user as "audit.test.username"
And I noted the "organizationName" of [DEFAULT-TEST-USER] user as "audit.test.organizationName"
And I selected the "OncoTestPatient, Juno" patient
And I navigated to the "Comments" view
And I logged in to AWS console
And I modified the table name to the following (see READ.me):
| parameter                   | old_value                          |  new_value                               |
| COMMENTS_DYNAMO_TABLE_NAME  | <env_name>-representation-comments |  <env_name>-representation-comments-test |
And I waited until the representation service was updated
When I send the following patient comment: "This is a patient comment to test failed audit logging"
And I note down the actual date and time
And I wait for sending the comment
Then an error popup is displayed with the following message:
| error_title            | detailed_error_text                     |
| Failed to save Comment | Sorry, something went wrong on our end. |
And audit log is found as the service user with below parameters:
| property_path                               | property_value                                          |
| $.type.display                              | Patient Record                                          |
| $.type.code                                 | 110110                                                  |
| $.entity[0].detail[0].valueString           | PATIENT_IDENTITY_FEED                                   |
| $.recorded                                  | <<the_noted_actual_date_and_time_in_UTC_timezone>>      |
| $.agent[?(@.requestor==true)].who.reference | <<audit.test.username>>@<<audit.test.organizationName>> |
| $.action                                    | C                                                       |
| $.outcome                                   | 4                                                       |
| $.entity[0].what.reference                  | Patient/a10e48ab-b927-9ee1-84a4-41638a84e05a            |
| $.outcomeDesc                               | failed to create comment                                |


@srs
@SRS-62.24
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68682,SysRS-68686,SysRS-68603,SysRS-68600,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Audit_log
Scenario: Audit log is created about the failed patient comment deletion
Given I logged in to CareIntellect for Oncology application as [DEFAULT-TEST-USER] in the browser
And I noted the "username" of [DEFAULT-TEST-USER] user as "audit.test.username"
And I noted the "organizationName" of [DEFAULT-TEST-USER] user as "audit.test.organizationName"
And I selected the "OncoTestPatient, Juno" patient
And I navigated to the "Comments" view
And I created the following patient comment for the "OncoTestPatient, Juno" patient:
| patient_comment                                 |
| This is a patient comment to test audit logging |
And I logged in to AWS console
And I modified the table name to the following (see READ.me):
| parameter                   | old_value                          |  new_value                               |
| COMMENTS_DYNAMO_TABLE_NAME  | <env_name>-representation-comments |  <env_name>-representation-comments-test |
And I waited until the representation service was updated
When I try to delete the already existing patient comment at "OncoTestPatient, Juno" patient: "This is a patient comment to test audit logging"
And I note down the actual date and time
And I note down the "commentId" param from the url of the following request: DELETE /patients/<patient_id>/comments/<commentId>
And I wait for deleting the comment
Then an error popup is displayed with the following message:
| error_title              | detailed_error_text                     |
| Failed to delete Comment | Sorry, something went wrong on our end. |
And audit log is found as the service user with below parameters:
| property_path                               | property_value                                          |
| $.type.display                              | Patient Record                                          |
| $.type.code                                 | 110110                                                  |
| $.entity[0].detail[0].valueString           | PATIENT_IDENTITY_FEED                                   |
| $.recorded                                  | <<the_noted_actual_date_and_time_in_UTC_timezone>>      |
| $.agent[?(@.requestor==true)].who.reference | <<audit.test.username>>@<<audit.test.organizationName>> |
| $.action                                    | D                                                       |
| $.outcome                                   | 4                                                       |
| $.entity[0].what.reference                  | Patient/a10e48ab-b927-9ee1-84a4-41638a84e05a            |
| $.outcomeDesc                               | failed to delete comment <DELETED_COMMENT_ID>           |


@srs
@SRS-62.25
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68682,SysRS-68686,SysRS-68603,SysRS-68600,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Audit_log
Scenario: Audit log is created about the failed event comment creation
Given I logged in to CareIntellect for Oncology application as [DEFAULT-TEST-USER] in the browser
And I selected the "OncoTestPatient, Juno" patient
And I navigated to the "Timeline" view
And I selected the following event:
| swimlane           | event_name             | event_date   |
| Pathology and Labs | Test Diagnostic Report | May 11, 2009 |
And I logged in to AWS console
And I modified the table name to the following (see READ.me):
| parameter                   | old_value                          |  new_value                               |
| COMMENTS_DYNAMO_TABLE_NAME  | <env_name>-representation-comments |  <env_name>-representation-comments-test |
And I waited until the representation service was updated
When I try to send the following event comment: "This is an event comment to test audit logging"
And I note down the actual date and time
And I wait for sending the comment
Then an error popup is displayed with the following message:
| error_title            | detailed_error_text                     |
| Failed to save Comment | Sorry, something went wrong on our end. |
And audit log is found as the service user with below parameters:
| property_path                               | property_value                                          |
| $.type.display                              | Patient Record                                          |
| $.type.code                                 | 110110                                                  |
| $.entity[0].detail[0].valueString           | PATIENT_IDENTITY_FEED                                   |
| $.recorded                                  | <<the_noted_actual_date_and_time_in_UTC_timezone>>      |
| $.agent[?(@.requestor==true)].who.reference | <<audit.test.username>>@<<audit.test.organizationName>> |
| $.action                                    | C                                                       |
| $.outcome                                   | 4                                                       |
| $.entity[0].what.reference                  | Patient/a10e48ab-b927-9ee1-84a4-41638a84e05a            |
| $.outcomeDesc                               | failed to create comment                                |

@srs
@SRS-62.26
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68682,SysRS-68686,SysRS-68603,SysRS-68600,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Audit_log
Scenario: Audit log is created about the failed event comment deletion
Given I logged in to CareIntellect for Oncology application as [DEFAULT-TEST-USER] in the browser
And I selected the "OncoTestPatient, Juno" patient
And I navigated to the "Timeline" view
And I created the following event comment for the "OncoTestPatient, Juno" patient:
| swimlane           | event_name             | event_date   | event_comment                                  |
| Pathology and Labs | Test Diagnostic Report | May 11, 2009 | This is an event comment to test audit logging |
And I open the "Network" tab in DEV Tools
And I logged in to AWS console
And I modified the table name to the following (see READ.me):
| parameter                   | old_value                          |  new_value                               |
| COMMENTS_DYNAMO_TABLE_NAME  | <env_name>-representation-comments |  <env_name>-representation-comments-test |
And I waited until the representation service was updated
When I try to delete the already existing event comment at "OncoTestPatient, Juno" patient:
| swimlane           | event_name             | event_date   | event_comment                                  |
| Pathology and Labs | Test Diagnostic Report | May 11, 2009 | This is an event comment to test audit logging |
And I note down the actual date and time
And I note down the "commentId" param from the url of the following request: DELETE /patients/<patient_id>/events/<event_id>/comments/<commentId>
And I wait for deleting the comment
Then an error popup is displayed with the following message:
| error_title              | detailed_error_text                     |
| Failed to delete Comment | Sorry, something went wrong on our end. |
And audit log is found as the service user with below parameters:
| property_path                               | property_value                                          |
| $.type.display                              | Patient Record                                          |
| $.type.code                                 | 110110                                                  |
| $.entity[0].detail[0].valueString           | PATIENT_IDENTITY_FEED                                   |
| $.recorded                                  | <<the_noted_actual_date_and_time_in_UTC_timezone>>      |
| $.agent[?(@.requestor==true)].who.reference | <<audit.test.username>>@<<audit.test.organizationName>> |
| $.action                                    | D                                                       |
| $.outcome                                   | 4                                                       |
| $.entity[0].what.reference                  | Patient/a10e48ab-b927-9ee1-84a4-41638a84e05a            |
| $.outcomeDesc                               | failed to delete comment <DELETED_COMMENT_ID>           |

@srs
@SRS-62.27
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68682,SysRS-68686,SysRS-68603,SysRS-68600,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Audit_log
Scenario: Audit log is created about the failed tag (label creation)
Given I logged in to CareIntellect for Oncology application as [DEFAULT-TEST-USER] in the browser
And I navigated to the following event at "OncoTestPatient, Juno" patient:
| swimlane           | event_name             | event_date   |
| Pathology and Labs | Test Diagnostic Report | May 11, 2009 |
And I navigated to the 'Create new label' modal
And I logged in to AWS console
And I modified the table name to the following (see READ.me):
| parameter                 | old_value                         |  new_value                             |
| LABELS_DYNAMO_TABLE_NAME  | <env_name>-representation-labels  |  <env_name>-representation-labels-test |
And I waited until the representation service was updated
And I create a new label on the 'Create new label' modal
And I note down the actual date and time
And I wait for creating the label
Then an error popup is displayed with the following message:
| error_title          | detailed_error_text                     |
| Failed to save Label | Sorry, something went wrong on our end. |
And audit log is found as the service user with below parameters:
| property_path                               | property_value                                          |
| $.type.display                              | Software Configuration                                  |
| $.type.code                                 | 110131                                                  |
| $.entity[0].detail[0].valueString           | PATIENT_IDENTITY_FEED                                   |
| $.recorded                                  | <<the_noted_actual_date_and_time_in_UTC_timezone>>      |
| $.agent[?(@.requestor==true)].who.reference | <<audit.test.username>>@<<audit.test.organizationName>> |
| $.action                                    | C                                                       |
| $.outcome                                   | 4                                                       |
| $.outcomeDesc                               | failed to create label                                  |

@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Audit_log
Scenario: Audit log about login and logout is handled by IDAM (evidence review)
Given the user opens IDAM project in ALM application
When I filter for the following Requirement ID: 18581
Then the content of "Requirement Name" refers to Login/Logout functionality
And the "Test Status" of this requirement is "Passed"
When I open this requirement
Then I see the the following Document ID in the Details tab:
| doc_id                        |
| 6938272-17814-20200317133641  |
When I open the 'Test Coverage' section
Then the "Coverage Status" of the test cases linked to this requirement are "Passed"

@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Audit_log
Scenario: Audit logs about Patient summary successfully accessed by AI overview service
Given the "username" of [DEFAULT-TEST-USER] user is stored as "audit.test.username"
And the "organizationName" of [DEFAULT-TEST-USER] user is stored as "audit.test.organizationName"
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_AI, MultiFileType" patient is selected
When the user navigates to the "Summary" view
Then audit log is found by the service user with below parameters and no correlation id:
| property_path                               | property_value                                                                                  |
| $.type.display                              | Patient Record                                                                                  |
| $.type.code                                 | 110110                                                                                          |
| $.entity[0].detail[0].valueString           | PATIENT_IDENTITY_FEED                                                                           |
| $.recorded                                  | <<the_noted_actual_date_and_time_in_UTC_timezone>>                                              |
| $.agent[?(@.requestor==true)].who.reference | <<audit.test.username>>@<<audit.test.organizationName>>                                         |
| $.action                                    | R                                                                                               |
| $.outcome                                   | 0                                                                                               |
| $.entity[0].what.reference                  | Patient/0og3DVsUqDph                                                                                    |
| $.outcomeDesc                               | Patient summary data successfully accessed by AI overview service. Patient version: <ANY_VALUE> |

@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Audit_log
Scenario: Audit logs about Patient summary generation successfully triggered by AI overview service
Given the following request is sent to Precision Insights by service user app:
| service         | method | endpoint_path          |
| patient-summary | DELETE | /${ps.monitoring.path} |
And the received HTTP status code is 204
And the "username" of [DEFAULT-TEST-USER] user is stored as "audit.test.username"
And the "organizationName" of [DEFAULT-TEST-USER] user is stored as "audit.test.organizationName"
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_AI, MultiFileType" patient is selected
When the user navigates to the "Summary" view
And I send the following request to CareIntellect for Oncology application on behalf of the service user:
| service         | method | endpoint_path          |
| patient-summary | GET    | /${ps.monitoring.path} |
Then the received HTTP status code is 200
And the "patients" list in the response contains the item with id "0og3DVsUqDph"
When I send the following request is sent to DHS-EAT by service user app:
| service | method | endpoint_path                                                                                                                         |
| DHS-EAT | GET    | /auditlogconfig/repositoryconfiguration/alv/messages?startDate=<<CURRENT_DATE_YYYY-MM-DD>>&endDate=<<CURRENT_DATE_YYYY-MM-DD>>&page=0 |
Then audit log is found by the service user with below parameters:
| property_path                               | property_value                                            |
| $.type.display                              | Patient Record                                            |
| $.type.code                                 | 110110                                                    |
| $.entity[0].detail[0].valueString           | PATIENT_IDENTITY_FEED                                     |
| $.recorded                                  | <<the_noted_actual_date_and_time_in_UTC_timezone>>        |
| $.agent[?(@.requestor==true)].who.reference | <<audit.test.username>>@<<audit.test.organizationName>>   |
| $.action                                    | E                                                         |
| $.outcome                                   | 0                                                         |
| $.entity[0].what.reference                  | Patient/0og3DVsUqDph                                      |
| $.outcomeDesc                               | Succeeded in triggering the patient summary AI generation |

@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70269
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario: Audit log tampering protection is handled by DHS EAT (evidence review)
Given I downloaded DHS EAT Privacy & Security Manual documentation from MyWorkshop (DOC3138079)
When I open the document and check the following chapter:
| chapter_name            |
| Information Protection  |
Then the document contains the following section: "All the traffic is encrypted in-transit at API Gateway. Data is encrypted at rest using AWS CMKs created which is unique per tenant deployment."
When I download DHS EAT Evaluation TDR documentation from MyWorkshop (DOC3156230)
And I open the document and check the following chapter:
| chapter_name                           |
| Compliance Standards and Requirements  |
Then the document contains the following section: "EAT will be compliant to the HIPAA Standards and regulations for hosting PHI data in US regions"
And the document contains the following section: "EAT will be compliant to the GDPR Standards and regulations for hosting PHI data in EU region"
