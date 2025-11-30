Feature: [SysRS-68604] Audit Log Exception

Narrative: The system shall not include operational secrets in the audit log:
             - passwords
             - private keys


@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68604
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Logging
Scenario: Check audit log has no passwords or private keys
When the following request is sent to DHS-EAT by service user app:
| service | method | endpoint_path                                      |
| DHS-EAT | GET    | <eat.service.url>/<eat.service.path>?date=<<CURRENT_DATE_YYYY-MM-DD>> |
And the received HTTP status code is 200
Then the response body does not contain passwords
And the response body does not contain private keys