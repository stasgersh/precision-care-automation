@cte
@AuditLogs
Feature: [SysRS-68448] CTE Audit Logs

Narrative: The system shall support the following configuration items:
             1. Clinical trials list:  to allow the user to add/remove trials to the list, trial name, trial conditions as described below . see Clinical trials (SysRS-68447).
             2. Clinical trial eligibility score - criteria match configuration for each trial. see SysRS-68446.
             3. Complex criteria configuration. see SysRS-68445
             4. Atomic criterion configuration. see SysRS-68444

Background:
Given [API] the following patient is uploaded to PDS: patients/OncoTestPatient_Calculator.json

@sanityCalculator
@sanity
@calculator
@Platform:Rest_API
@NotImplemented
@Category:Positive
@Description:Check_audit_log_is_triggered_for_creating_a_new_configuration_of_type_custom,Audit_log_is_triggered_for_configuration_creation
@Traceability:SysRS-68448,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Criteria_Calculator/Audit_Logs
Scenario: Check audit log is triggered for creating a new configuration of type custom
Given the "username" of [DEFAULT-TEST-USER] user is stored as "audit.test.username"
And the "organizationName" of [DEFAULT-TEST-USER] user is stored as "audit.test.organizationName"
When the user submits POST request to create new custom configuration for patient id 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5
And the generated ID is stored as parameter from new custom configuration creation
Then the received HTTP status code is 200
And audit log is found by the service user and by custom/%s configuration creation pattern, and by generatedUUID, and with below parameters:
| property_path                          | property_value       |
| $.eventId.name                         | Generic API Test     |
| $.eventId.code                         | 110011               |
| $.eventActionCode                      | C                    |
| $.eventOutcome                         | 0                    |
| $.humanRequestor[0].id                 | pi-cc-config-admin   |
| $.humanRequestor[0].name               | cc config admin, N/A |
| $.objectDetails[?(@.type)].type        | Detail               |
| $.objectDetails[?(@.value)].value      | ${ID_VALUE}          |
And the found audit log item contains "eventDate" property in the following format: "yyyy-MM-dd HH:mm:ss.S"

@srs
@calculator
@Platform:Rest_API
@Category:Positive
@NotImplemented
@Description:Check_audit_log_is_triggered_for_creating_a_new_configuration_of_a_specific_type_for_specific_patient,Audit_log_is_triggered_for_configuration_creation_for_specific_patient
@Traceability:SysRS-68448,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Audit_Logs
Scenario: Check audit log is triggered for creating a new configuration of a specific type for specific patient
Given the "username" of [DEFAULT-TEST-USER] user is stored as "audit.test.username"
And the "organizationName" of [DEFAULT-TEST-USER] user is stored as "audit.test.organizationName"
When the user submits POST request to create new custom configuration for patient id 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5
And the generated ID is stored as parameter from new custom configuration creation
Then the received HTTP status code is 200
And audit log is found by the service user and by custom/%s configuration creation pattern, and by generatedUUID, and with below parameters:
| property_path                          | property_value       |
| $.eventId.name                         | Generic API Test     |
| $.eventId.code                         | 110011               |
| $.eventActionCode                      | C                    |
| $.eventOutcome                         | 0                    |
| $.humanRequestor[0].id                 | pi-cc-config-admin   |
| $.humanRequestor[0].name               | cc config admin, N/A |
| $.objectDetails[?(@.type)].type        | Detail               |
| $.objectDetails[?(@.value)].value      | ${ID_VALUE}          |
And the found audit log item contains "eventDate" property in the following format: "yyyy-MM-dd HH:mm:ss.S"

@srs
@calculator
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68448,SysRS-68601
@Description:Check_audit_log_is_triggered_for_updating_existing_configuration,Audit_log_is_triggered_for_configuration_update_request
@NotImplemented
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Audit_Logs
Scenario: Check audit log is triggered for updating existing configuration
Given the "username" of [DEFAULT-TEST-USER] user is stored as "audit.test.username"
And the "organizationName" of [DEFAULT-TEST-USER] user is stored as "audit.test.organizationName"
And the new configuration uploaded with type of custom and configured for the patient 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5
When the generated ID is stored as parameter from new custom configuration creation
And the user sends a PUT request to update calculator configuration using stored generated configID,type custom, patient 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 from file criteria-calculator-first-met
Then the received HTTP status code is 200
And audit log is found by the service user and by custom/%s configuration update pattern, and by generatedUUID, and with below parameters:
| property_path                          | property_value       |
| $.eventId.name                         | Generic API Test     |
| $.eventId.code                         | 110011               |
| $.eventActionCode                      | U                    |
| $.eventOutcome                         | 0                    |
| $.humanRequestor[0].id                 | pi-cc-config-admin   |
| $.humanRequestor[0].name               | cc config admin, N/A |
| $.objectDetails[?(@.type)].type        | Detail               |
| $.objectDetails[?(@.value)].value      | ${ID_VALUE}          |
And the found audit log item contains "eventDate" property in the following format: "yyyy-MM-dd HH:mm:ss.S"

@srs
@calculator
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68448,SysRS-68601
@Description:Check_audit_log_is_triggered_for_deleting_existing_configuration,Audit_log_is_triggered_for_configuration_deletion_request
@NotImplemented
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Audit_Logs
Scenario: Check audit log is triggered for deleting existing configuration
Given the "username" of [DEFAULT-TEST-USER] user is stored as "audit.test.username"
And the "organizationName" of [DEFAULT-TEST-USER] user is stored as "audit.test.organizationName"
When the user submits POST request to create new custom configuration for patient id 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5
When the user submits DELETE request to delete existing configuration
Then the received HTTP status code is 200
And audit log is found by the service user and by custom/%s configuration deletion pattern, and by generatedUUID, and with below parameters:
| property_path                          | property_value       |
| $.eventId.name                         | Generic API Test     |
| $.eventId.code                         | 110011               |
| $.eventActionCode                      | D                    |
| $.eventOutcome                         | 0                   |
| $.humanRequestor[0].id                 | pi-cc-config-admin   |
| $.humanRequestor[0].name               | cc config admin, N/A |
| $.objectDetails[?(@.type)].type        | Detail               |
| $.objectDetails[?(@.value)].value      | ${ID_VALUE}          |
And the found audit log item contains "eventDate" property in the following format: "yyyy-MM-dd HH:mm:ss.S"
