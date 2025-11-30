@safety
Feature: [SysRS-68403] Access With Authenticated Users

Narrative: The system shall allow access only the securely authenticated users to the system.


@srs
@api
@SRS-16.01
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68403,SysRS-68413
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control
Scenario Outline: All GET endpoints are accessible by authenticated and authorized users
Given [API] the "latestVersion" timestamp of the patient with fhir id "a10e48ab-b927-9ee1-84a4-41638a84e05a" is saved
When the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method | endpoint_path   |
| core    | GET    | <endpoint_path> |
Then the received HTTP status code is 200
And the response contains the following content type: "<response_content_type>"
And the response contains the following headers:
| key                       | value                                        |
| Strict-Transport-Security | max-age=31536000; includeSubDomains; preload |
| X-Content-Type-Options    | nosniff                                      |
| Referrer-Policy           | strict-origin                                |
| Cache-Control             | no-cache, no-store, must-revalidate          |
| Pragma                    | no-cache                                     |
Examples:
| endpoint_path                                                                                                                                                                          | response_content_type |
| /settings                                                                                                                                                                              | application/json      |
| /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/status                                                                                                                                  | application/json      |
| /patients                                                                                                                                                                              | application/json      |
| /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/timeline?version=<<latestVersion>>                                                                                                      | application/json      |
| /patients/swimlanes                                                                                                                                                                    | application/json      |
| /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/banner?version=<<latestVersion>>                                                                                                        | application/json      |
| /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/reports/clinicalNote/clinicalNote:9fe5f5cb-11b1-4c7c-aa88-a86a46460c0c?representation=reportHtml.onco.general&version=<<latestVersion>> | application/json      |
| /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/trends?version=<<latestVersion>>                                                                                                        | application/json      |
| /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/medical-history?version=<<latestVersion>>                                                                                               | application/json      |
| /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/comments                                                                                                                                | application/json      |
| /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/events/DiagnosticReport:347b7a4b-ab75-4691-a8f5-d2f498bb6bed/comments                                                                   | application/json      |
| /labels                                                                                                                                                                                | application/json      |
| /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/summary?version=<<latestVersion>>                                                                                                       | application/json      |

@srs
@api
@SRS-16.02
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68403
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control
Scenario Outline: All PUT endpoints are accessible by authenticated and authorized users
When the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method | endpoint_path   | payload   | content_type     |
| core    | PUT    | <endpoint_path> | <payload> | application/json |
Then the received HTTP status code is 204
And the response contains the following headers:
| key                       | value                                        |
| Strict-Transport-Security | max-age=31536000; includeSubDomains; preload |
| X-Content-Type-Options    | nosniff                                      |
| Referrer-Policy           | strict-origin                                |
| Cache-Control             | no-cache, no-store, must-revalidate          |
| Pragma                    | no-cache                                     |
Examples:
| endpoint_path                                                                                                        | payload                             |
| /settings                                                                                                            | api/userSettings_example.json       |
| /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/events/DiagnosticReport:347b7a4b-ab75-4691-a8f5-d2f498bb6bed/settings | { "important": true, "labels": [] } |

@srs
@api
@SRS-16.03
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68403
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control
Scenario Outline: Error returns when trying to send invalid request to the PUT endpoints
When the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method | endpoint_path   | payload   | content_type     |
| core    | PUT    | <endpoint_path> | <payload> | application/json |
Then the received HTTP status code is 400
And the response contains the following headers:
| key                       | value                                        |
| Strict-Transport-Security | max-age=31536000; includeSubDomains; preload |
| X-Content-Type-Options    | nosniff                                      |
| Referrer-Policy           | strict-origin                                |
| Cache-Control             | no-cache, no-store, must-revalidate          |
| Pragma                    | no-cache                                     |
Examples:
| endpoint_path                                                                                                        | payload                                           |
| /settings                                                                                                            | { "showNlp": true }                               |
| /settings                                                                                                            | { "showNlpResults": "yes" }                       |
| /settings                                                                                                            | { "showNlpResults": true, "testParam": "testxx" } |
| /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/events/DiagnosticReport:347b7a4b-ab75-4691-a8f5-d2f498bb6bed/settings | { "important": true }                             |
| /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/events/DiagnosticReport:347b7a4b-ab75-4691-a8f5-d2f498bb6bed/settings | { "thisIsImportant": "maybe" }                    |
| /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/events/DiagnosticReport:347b7a4b-ab75-4691-a8f5-d2f498bb6bed/settings | { "important": true, "testParam": "testyy" }      |

@srs
@api
@SRS-16.08
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68403
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control
Scenario Outline: All POST endpoints are accessible by authenticated and authorized users
When the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method | endpoint_path   | payload   | content_type     |
| core    | POST   | <endpoint_path> | <payload> | application/json |
Then the received HTTP status code is <status_code>
And the response contains the following headers:
| key                       | value                                        |
| Strict-Transport-Security | max-age=31536000; includeSubDomains; preload |
| X-Content-Type-Options    | nosniff                                      |
| Referrer-Policy           | strict-origin                                |
| Cache-Control             | no-cache, no-store, must-revalidate          |
| Pragma                    | no-cache                                     |
Examples:
| endpoint_path                                                                                              | payload                                                                             | status_code |
| /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/comments                                                    | { "content": "patient comment added through api" }                                  | 201         |
| /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/events/timeline:2:915ff71064419634e3398f5fdd1b8fab/comments | { "content": "event comment added through api" }                                    | 201         |
| /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/exports/summary                                             | {}                                                                                  | 204         |
| /labels                                                                                                    | { "text": "label added through api <<create_label_timestamp>>", "paletteColor": 0 } | 201         |

@srs
@api
@SRS-16.09
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68403
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control
Scenario Outline: Error returns when trying to send invalid request to the POST endpoints
When the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method | endpoint_path   | payload   | content_type     |
| core    | POST   | <endpoint_path> | <payload> | application/json |
Then the received HTTP status code is 400
And the response contains the following headers:
| key                       | value                                        |
| Strict-Transport-Security | max-age=31536000; includeSubDomains; preload |
| X-Content-Type-Options    | nosniff                                      |
| Referrer-Policy           | strict-origin                                |
| Cache-Control             | no-cache, no-store, must-revalidate          |
| Pragma                    | no-cache                                     |
Examples:
| endpoint_path                                                                                              | payload                                                               |
| /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/comments                                                    | { "content": "patient comment added through api", "type": "patient" } |
| /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/events/timeline:2:915ff71064419634e3398f5fdd1b8fab/comments | { "eventComment": "event comment added through api" }                 |
| /labels                                                                                                    | { "text": "label added through api <<create_label_timestamp>>" }      |

@srs
@api
@SRS-16.10
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68403
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control
Scenario: DELETE patient comment endpoint is accessible by authenticated and authorized users
When the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method | endpoint_path                                           | payload                                         | content_type     |
| core    | POST   | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/comments | { "content": "test of delete patient comment" } | application/json |
Then the received HTTP status code is 201
Given [API] the id of the below patient comment is stored as "delete_comment_id":
| patientID                            | comment_text                   |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | test of delete patient comment |
When the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method | endpoint_path                                                                 |
| core    | DELETE | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/comments/<<delete_comment_id>> |
Then the received HTTP status code is 204
And the response contains the following headers:
| key                       | value                                        |
| Strict-Transport-Security | max-age=31536000; includeSubDomains; preload |
| X-Content-Type-Options    | nosniff                                      |
| Referrer-Policy           | strict-origin                                |
| Cache-Control             | no-cache, no-store, must-revalidate          |
| Pragma                    | no-cache                                     |

@srs
@api
@SRS-16.11
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68403
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control
Scenario: DELETE event comment endpoint is accessible by authenticated and authorized users
Given the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method | endpoint_path                                                                                                       | payload                                       | content_type     |
| core    | POST   | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/events/pathologyReport:ef98eb70-cb59-4cd3-bc70-97bb04bdb4df/comments | { "content": "test of delete event comment" } | application/json |
And [API] the id of the below event comment is stored as "delete_comment_id":
| patientID                            | rootResourceID                                        | comment_text                 |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/ef98eb70-cb59-4cd3-bc70-97bb04bdb4df | test of delete event comment |
When the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method | endpoint_path                                                                                                                             |
| core    | DELETE | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/events/pathologyReport:ef98eb70-cb59-4cd3-bc70-97bb04bdb4df/comments/<<delete_comment_id>> |
Then the received HTTP status code is 204
And the response contains the following headers:
| key                       | value                                        |
| Strict-Transport-Security | max-age=31536000; includeSubDomains; preload |
| X-Content-Type-Options    | nosniff                                      |
| Referrer-Policy           | strict-origin                                |
| Cache-Control             | no-cache, no-store, must-revalidate          |
| Pragma                    | no-cache                                     |

@srs
@api
@SRS-16.12
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68403
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control
Scenario Outline: Error returns when trying to send invalid request to the DELETE endpoints
When the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method | endpoint_path   |
| core    | DELETE | <endpoint_path> |
Then the received HTTP status code is <response_status_code>
And the response contains the following headers:
| key                       | value                                        |
| Strict-Transport-Security | max-age=31536000; includeSubDomains; preload |
| X-Content-Type-Options    | nosniff                                      |
| Referrer-Policy           | strict-origin                                |
| Cache-Control             | no-cache, no-store, must-revalidate          |
| Pragma                    | no-cache                                     |
Examples:
| example_description               | endpoint_path                                                                                                                                   | response_status_code |
| Not existing patient comment id   | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/comments/11111111-1111-1111-1111-111111111111                                                    | 404                  |
| Invalid patient comment id format | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/comments/comment001                                                                              | 400                  |
| Not existing event comment id     | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/events/timeline:2:915ff71064419634e3398f5fdd1b8fab/comments/11111111-1111-1111-1111-111111111111 | 404                  |
| Invalid event comment id format   | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/events/timeline:2:915ff71064419634e3398f5fdd1b8fab/comments/1234                                 | 400                  |

@srs
@api
@SRS-16.04
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68403,SysRS-68413
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control
Scenario Outline: Error returns when trying to access restricted endpoints by not authorized users
When the following request is sent to OncoCare on behalf of [TEST-USER-1] user:
| service | method   | endpoint_path   | payload   | content_type     |
| core    | <method> | <endpoint_path> | <payload> | application/json |
Then the received HTTP status code is 403
And the response contains the following body: "{"Message":"User is not authorized to access this resource with an explicit deny in an identity-based policy"}"
And the response contains the following headers:
| key                       | value                                        |
| Strict-Transport-Security | max-age=31536000; includeSubDomains; preload |
| X-Content-Type-Options    | nosniff                                      |
| Referrer-Policy           | strict-origin                                |
| Cache-Control             | no-cache, no-store, must-revalidate          |
| Pragma                    | no-cache                                     |
Examples:
| method | endpoint_path                                                                                                                                   | payload                                                                             |
| GET    | /settings                                                                                                                                       | N/A                                                                                 |
| GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/status                                                                                           | N/A                                                                                 |
| GET    | /patients                                                                                                                                       | N/A                                                                                 |
| GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/timeline                                                                                         | N/A                                                                                 |
| GET    | /patients/swimlanes                                                                                                                             | N/A                                                                                 |
| GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/banner                                                                                           | N/A                                                                                 |
| GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/reports/timeline:2:915ff71064419634e3398f5fdd1b8fab                                              | N/A                                                                                 |
| GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/reports/DocumentReference:9fe5f5cb-11b1-4c7c-aa88-a86a46460c0c?representation=reportHtml         | N/A                                                                                 |
| GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/trends                                                                                           | N/A                                                                                 |
| GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/medical-history                                                                                  | N/A                                                                                 |
| GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/comments                                                                                         | N/A                                                                                 |
| GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/events/DiagnosticReport:347b7a4b-ab75-4691-a8f5-d2f498bb6bed/comments                            | N/A                                                                                 |
| GET    | /labels                                                                                                                                         | N/A                                                                                 |
| GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/summary                                                                                          | N/A                                                                                 |
| PUT    | /settings                                                                                                                                       | { "showNlpResults": true, "timelineDirection": 0  }                                 |
| PUT    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/events/DiagnosticReport:347b7a4b-ab75-4691-a8f5-d2f498bb6bed/settings                            | { "important": true, "labels": [] }                                                 |
| POST   | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/comments                                                                                         | { "content": "patient comment added through api" }                                  |
| POST   | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/events/timeline:2:915ff71064419634e3398f5fdd1b8fab/comments                                      | { "content": "event comment added through api" }                                    |
| POST   | /labels                                                                                                                                         | { "text": "label added through api <<create_label_timestamp>>", "paletteColor": 0 } |
| POST   | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/exports/summary                                                                                  | {}                                                                                  |
| DELETE | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/comments/11111111-1111-1111-1111-111111111111                                                    | N/A                                                                                 |
| DELETE | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/events/timeline:2:915ff71064419634e3398f5fdd1b8fab/comments/11111111-1111-1111-1111-111111111111 | N/A                                                                                 |

@srs
@api
@SRS-16.05
@SPR-4300
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68403
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control
Scenario Outline: Error returns when trying to access restricted endpoints by not authenticated users
When the following request is sent to OncoCare without authenticated user:
| service | method   | endpoint_path   | payload   | content_type     |
| core    | <method> | <endpoint_path> | <payload> | application/json |
Then the received HTTP status code is 403
And the response contains the following headers:
| key                       | value                                        |
| Strict-Transport-Security | max-age=31536000; includeSubDomains; preload |
| X-Content-Type-Options    | nosniff                                      |
| Referrer-Policy           | strict-origin                                |
| Cache-Control             | no-cache, no-store, must-revalidate          |
| Pragma                    | no-cache                                     |
Examples:
| method | endpoint_path                                                                                                                                   | payload                                                                             |
| GET    | /settings                                                                                                                                       | N/A                                                                                 |
| GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/status                                                                                           | N/A                                                                                 |
| GET    | /patients                                                                                                                                       | N/A                                                                                 |
| GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/timeline                                                                                         | N/A                                                                                 |
| GET    | /patients/swimlanes                                                                                                                             | N/A                                                                                 |
| GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/banner                                                                                           | N/A                                                                                 |
| GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/reports/timeline:2:915ff71064419634e3398f5fdd1b8fab                                              | N/A                                                                                 |
| GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/reports/DocumentReference:9fe5f5cb-11b1-4c7c-aa88-a86a46460c0c?representation=reportHtml         | N/A                                                                                 |
| GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/trends                                                                                           | N/A                                                                                 |
| GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/medical-history                                                                                  | N/A                                                                                 |
| GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/comments                                                                                         | N/A                                                                                 |
| GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/events/DiagnosticReport:347b7a4b-ab75-4691-a8f5-d2f498bb6bed/comments                            | N/A                                                                                 |
| GET    | /labels                                                                                                                                         | N/A                                                                                 |
| GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/summary                                                                                          | N/A                                                                                 |
| PUT    | /settings                                                                                                                                       | { "showNlpResults": true, "timelineDirection": 0  }                                 |
| PUT    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/events/DiagnosticReport:347b7a4b-ab75-4691-a8f5-d2f498bb6bed/settings                            | { "important": true, "labels": [] }                                                 |
| POST   | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/comments                                                                                         | { "content": "patient comment added through api" }                                  |
| POST   | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/events/timeline:2:915ff71064419634e3398f5fdd1b8fab/comments                                      | { "content": "event comment added through api" }                                    |
| POST   | /labels                                                                                                                                         | { "text": "label added through api <<create_label_timestamp>>", "paletteColor": 0 } |
| POST   | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/exports/summary                                                                                  | {}                                                                                  |
| DELETE | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/comments/11111111-1111-1111-1111-111111111111                                                    | N/A                                                                                 |
| DELETE | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/events/timeline:2:915ff71064419634e3398f5fdd1b8fab/comments/11111111-1111-1111-1111-111111111111 | N/A                                                                                 |

@srs
@api
@SRS-16.06
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68403
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control
Scenario Outline: Error returns when trying to access not existing resource or path
When the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method   | endpoint_path   | payload   | content_type     |
| core    | <method> | <endpoint_path> | <payload> | application/json |
Then the received HTTP status code is <response_status_code>
And the response contains the following headers:
| key                       | value                                        |
| Strict-Transport-Security | max-age=31536000; includeSubDomains; preload |
| X-Content-Type-Options    | nosniff                                      |
| Referrer-Policy           | strict-origin                                |
| Cache-Control             | no-cache, no-store, must-revalidate          |
| Pragma                    | no-cache                                     |
Examples:
| example_description               | method | endpoint_path                                                                                     | payload                  | response_status_code |
| not existing endpoint             | GET    | /patientsList                                                                                     | N/A                      | 403                  |
| not existing endpoint (PUT)       | PUT    | /labels                                                                                           | { "label": "some text" } | 403                  |
| not existing path                 | GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a                                                    | N/A                      | 404                  |
| not existing path (event details) | GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/events/timeline:2:12345678901123456789abc123456789 | N/A                      | 404                  |
| not existing path (PUT)           | PUT    | /settings/label                                                                                   | { "label": "some text" } | 403                  |
| not existing version              | GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/timeline?1740000098736                             | N/A                      | 400                  |
| missing version                   | GET    | /patients/AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA/summary                                            | N/A                      | 400                  |
| not existing event 1              | GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/events/abcd                                        | N/A                      | 404                  |
| not existing event 2              | GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/events/Procedure:abcd                              | N/A                      | 404                  |

@srs
@api
@SRS-16.07
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68403
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control
Scenario Outline: Error returns when trying to access restricted service endpoints by not authorized users
When the following request is sent to OncoCare on behalf of [TEST-USER-1] user:
| service   | method   | endpoint_path   | payload   | content_type     |
| <service> | <method> | <endpoint_path> | <payload> | application/json |
Then the received HTTP status code is 403
And the response contains the following body: "{"Message":"User is not authorized to access this resource with an explicit deny in an identity-based policy"}"
Examples:
| method | service  | endpoint_path                         | payload |
| GET    | core     | /metrics                              | N/A     |
| POST   | core     | /config/infra                         | {}      |
| POST   | core     | /config/valuesets                     | {}      |
| GET    | valueset | /ui                                   | N/A     |
| GET    | valueset | /csvdownload                          | N/A     |
| GET    | valueset | /csvdownload/active-allergy-status-vs | N/A     |
| PUT    | valueset | /csvdownload                          | {}      |

@srs
@SRS-16.13
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68403
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control
Scenario: Patient details are visible after providing user credentials who has access to OncoCare
Given the user is logged out from OncoCare application
When the user goes to the following URL: ${webdriver.base.url}/patient/a10e48ab-b927-9ee1-84a4-41638a84e05a/summary
Then the user is navigated to the OncoCare splash screen
When the user navigates to the Login screen
Then the Login page is loaded
When the [DEFAULT-TEST-USER] user logs in to OncoCare from the Login page
Then the Patient page is loaded
And the patient banner includes the following data:
| data_type | title | value       |
| KEY-VALUE | MRN   | 011-233-455 |
And the "OncoTestPatient, Juno" patient is selected

@srs
@SRS-16.14
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68403
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control
@Description:Execute_Query_for_particular_patient:<br/><ul><li>patientId</li><li>version</li></ul>,data_query_executed_successfully
Scenario: Patient details are not visible after providing user credentials who has no access to OncoCare
Given the user is logged out from OncoCare application
When the user goes to the following URL: ${webdriver.base.url}/patient/a10e48ab-b927-9ee1-84a4-41638a84e05a/summary
Then the user is navigated to the OncoCare splash screen
When the user navigates to the Login screen
Then the Login page is loaded
When the [TEST-USER-1] user logs in to OncoCare from the Login page
Then the following error is displayed on the screen: "Authorization error"
When the user clicks the Back to Sign in button
Then the user is navigated to the OncoCare splash screen

@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68401,SysRS-68679
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Access_Control
@Description:Access_to_app_with_different_users:<br/><ul><li>OncoCare_user<ul></li></ul><li>GE_user</li></ul>,OncoCare_user_can_access_to_OncoCare_app_but_GE_user_not
Scenario: Access to app with different users
When the user goes to the following URL: ${webdriver.base.url}/patient
And the user clicks the Sign in button
And the ge_user user logs in to CIO from the Login page with password User!321 and organization ge.com
Then the following error message is displayed on the login screen: "Wrong username and/or password."
When the user goes to the following URL: ${webdriver.base.url}/patient
And the user clicks the Sign in button
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
Then the Patient page is loaded

