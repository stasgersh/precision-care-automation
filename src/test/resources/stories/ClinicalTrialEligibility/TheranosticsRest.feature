@cte
@theranostics
@theranosticsRest
Feature: [Theranostics Rest API]


Narrative:
    - add nerative
    - write correct Description
    - define appropriate path in orcanos
    - add appropriate SysRS into Traceability

@srs
@eligibility
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68441,SysRs-68445,SysRS-69188,SysRS-68443,SysRS-69187,SysRS-68437,SysRS-68447,SysRS-68446
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Theranostics
@Description:Clinical_treatment_ingestion_-_Check_for_treatment_eligibility_service_response_'Success'_when_configuration_'Update'_is_ingested_without_optional_parameters:<br/><ul><li>Study_completion_date</li><li>Filter</li></ul>
Scenario: Check for treatment eligibility service response 'Success' when configuration 'Update' is ingested without optional parameters: study completion date and filter
Given [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Sigrid.json
When the user submits PUT request to update/add treatment configuration type of treatment to an existing patient 7cfe48ab-eb05-9ee1-84a4-f59c8a84e05a whether exists or not with omitted params:
| omitted_param_name |
| studyCompletion    |
Then the received HTTP status code is 202
And the response body contains the following text: '{"id":"trialDefaultId123456","status":"updated","type":"treatment","name":"This is a default trial name","description":"This is a default trial description","phase":"1","link":"https://clinicaltrials.gov/study/NCT04428710?a=23"}'
When the user submits PUT request to update/add treatment configuration type of treatment to an existing patient 7cfe48ab-eb05-9ee1-84a4-f59c8a84e05a whether exists or not with omitted params:
| omitted_param_name |
| patientsFilter    |
Then the received HTTP status code is 202
And the response body contains the following text: '{"id":"trialDefaultId123456","status":"updated","type":"treatment","name":"This is a default trial name","description":"This is a default trial description","phase":"1","link":"https://clinicaltrials.gov/study/NCT04428710?a=23"}'

@fails_due_to_an_error_502_when_posting_treatment_request_without_mandatory_field_VARIABLES
@SPR-5047
@srs
@eligibility
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68441,SysRs-68445,SysRS-69188,SysRS-68443,SysRS-69187,SysRS-68437,SysRS-68447,SysRS-68446
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Theranostics
@Description:Clinical_treatment_ingestion_-_Check_for_treatment_eligibility_service_response_'Failure'_when_configuration_'Update'_is_ingested_without_Mandatory_parameters:<br/><ul><li>Variables</li><li>Criteria</li></ul>
Scenario:Check for treatment eligibility service response 'Failure' when configuration 'Update' is ingested without 'Mandatory' parameters: variables and criteria
Given [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Sigrid.json
When the user submits PUT request to update/add treatment configuration type of treatment to an existing patient 7cfe48ab-eb05-9ee1-84a4-f59c8a84e05a whether exists or not with omitted params:
| omitted_param_name    |
| status                |
| phase                 |
| variables             |
Then the received HTTP status code is 422
And the response body contains the following text: '{"message": "[{'type': 'missing', 'loc': ('body', 'variables'), 'msg': 'Field required''
When the user submits PUT request to update/add treatment configuration type of treatment to an existing patient 7cfe48ab-eb05-9ee1-84a4-f59c8a84e05a whether exists or not with omitted params:
| omitted_param_name    |
| status                |
| phase                 |
| criteria             |
Then the received HTTP status code is 422
And the response body contains the following text: '{"message": "[{'type': 'missing', 'loc': ('body', 'criteria'), 'msg': 'Field required''

@fails_due_to_an_error_502_when_posting_treatment_request_without_mandatory_field_CRITEREA
@SPR-5047
@srs
@eligibility
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68441,SysRs-68445,SysRS-69188,SysRS-68443,SysRS-69187,SysRS-68437,SysRS-68447,SysRS-68446
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Theranostics
@Description:Clinical_treatment_ingestion_-_Check_for_treatment_eligibility_service_response_'Failure'_when_'New'_configuration_is_ingested_without_'Mandatory'_parameters:<br/><ul><li>Variables</li><li>Criteria</li></ul>
Scenario: Check for treatment eligibility service response 'Failure' when 'New' configuration is ingested without 'Mandatory' parameters: variables and criteria
Given [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Sigrid.json
When the user sends POST to add treatment as global config, with omitted params:
| omitted_param_name    |
| status                |
| phase                 |
| variables              |
Then the received HTTP status code is 422
And the response body contains the following text: '{"message": "[{'type': 'missing', 'loc': ('body', 'variables'), 'msg': 'Field required''
When the user sends POST to add treatment as global config, with omitted params:
| omitted_param_name    |
| status                |
| phase                 |
| criteria             |
Then the received HTTP status code is 422
And the response body contains the following text: '{"message": "[{'type': 'missing', 'loc': ('body', 'criteria'), 'msg': 'Field required''

@srs
@eligibility
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68441,SysRs-68445,SysRS-69188,SysRS-68443,SysRS-69187,SysRS-68437,SysRS-68447,SysRS-68446
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Theranostics
@Description:Clinical_treatment_ingestion_-_Check_ingestion_of_invalid_'studyCompletion'_date_configuration_parameter_format<br/><ul></ul>,Check_for_treatment_eligibility_service_responds_with:<status><br/><ul><li>HTTP_422_Error</li><li>Message:"Invalid_date_format"</li></ul>
Scenario: Check ingestion of invalid studyCompletion date format
Given the user submits PUT request to update/add configuration type of treatment, whether exists or not with custom data:
| config_field_name | config_field_value    |
| studyCompletion   | invalidDate           |
Then the received HTTP status code is 422
And the response body contains the following text: '{"message": "[{'type': 'value_error', 'loc': ('body',), 'msg': \"Value error, Invalid date format. Please provide a valid date string like 'YYYY-MM' or 'YYYY-MM-DD'\"'
When the user submits PUT request to update/add configuration type of treatment, whether exists or not with custom data:
| config_field_name | config_field_value |
| variables          |                   |
Then the received HTTP status code is 422
And the response body contains the following text: '{"message": "[{'type': 'string_type', 'loc': ('body', 'variables', 0, 'id'), 'msg': 'Input should be a valid string', 'input': None, 'url': 'https://errors.pydantic.dev/2.10/v/string_type'}]"}'
When the user submits PUT request to update/add configuration type of treatment, whether exists or not with custom data:
| config_field_name | config_field_value |
| criteria          |                    |
Then the received HTTP status code is 422
And the response body contains the following text: '{"message": "[{'type': 'enum', 'loc': ('body', 'criteria', 0, 'type'), 'msg': \"Input should be 'inclusion' or 'exclusion'\", 'input': None, 'ctx': {'expected': \"'inclusion' or 'exclusion'\"}'

@SPR-5045
@srs
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68441
@Description:Clinical_treatment_ingestion_-_Check_ingestion_and_fetch_of_a_new_PLUVICTO-treatment_configuration:<status><br/><ul><li>Fetch_configuration_check_the_configuration_in_response</li><li>Delete_configuration_and_check_for_empty_results</li></ul>
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Theranostics
Scenario: Check ingestion of a new PLUVICTO-treatment configuration, fetch it and then DELETE, and check for empty results
When the user submits PUT request to update/add trial/s configuration whether exists or not:
| trial_id  | config_type | trial_configuration_file                         |
| PLUVICTO9 | treatment   | pluvicto/PLUVICTO9-GlobalConfigPluvictoTreatment |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue treatment/PLUVICTO9.json
And the received HTTP status code is 200
When the user waits 1 minutes
And the user submit GET request to get all configurations by configType treatment
Then the received HTTP status code is 200
When the user submit DELETE request to delete all fetched configurations type of treatment
Then the received HTTP status code is 404
And the response body contains the following text: 'file not found'
When the user submits POST request to add the config type of treatment, as global configuration from file name: pluvicto/PLUVICTO9-GlobalConfigPluvictoTreatment
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue treatment/PLUVICTO9.json
And the received HTTP status code is 200
When the user submit GET request to get all configurations by configType treatment
Then the received HTTP status code is 200
When the user submit GET request to get configuration by configType treatment and configId PLUVICTO9
Then the received HTTP status code is 200
When the user submit DELETE request to delete all fetched configurations type of treatment
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue null
And the received HTTP status code is 204
And the response body contains the following text: ''