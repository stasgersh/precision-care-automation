@cte
Feature: [SysRS-102471,SysRS-102469,SysRS-102470,SysRS-102473] CTE AI Rest API

Narrative: The system shall trigger CTE-AI calculation for a determined list of patients and trials.

Background:
Given the user submits PUT request to update data-configuration: FullDataConfig
Then the received HTTP status code is 200
When the user submits PUT request to update treatments configuration:
| fileName         |
| NCT05568472_conf |
| NCT06044857_conf |
| NCT05929768_conf |
| NCT04457596_conf |
Then the received HTTP status code is 200
And the "message" property in the response matches the value: Success
When the user submits GET request to get all data-configuration
Then the received HTTP status code is 200
And the body of the response contains the following: all_data_configuration

@srs
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-102469,SysRS-102470
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/CTE-AI
@Description:submit_get_full_data_configuration
Scenario: Submit get full data configuration
When the user submits PUT request to update data-configuration: FullDataConfig
Then the received HTTP status code is 200
When the user submits PUT request to update treatments configuration:
| fileName         |
| NCT05568472_conf |
| NCT06044857_conf |
| NCT05929768_conf |
| NCT04457596_conf |
Then the received HTTP status code is 200
And the "message" property in the response matches the value: Success
When the user submits GET request to get all data-configuration
Then the received HTTP status code is 200
And the body of the response contains the following: all_data_configuration

@srs
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-102469,SysRS-102471,SysRS-102470
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/CTE-AI
@Description:Submit_get_for_treatment_of_particular_patient
Scenario: Submit get for treatment of particular patient
When [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: cteAi/bundle_patient_moti.json
Then the optional fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue testPatientIdMoti
When the following treatments have been configured for the patients:
| patient           | list_of_treatments |
| testPatientIdMoti | NCT05568472        |
Then the mandatory cte-ai-ai-criteria-matching Sfn processes are finished within 900 seconds with status Succeeded for property patients[0].id and propertyValue testPatientIdMoti
When the user submit GET request to get treatment by treatment ID: NCT05568472
Then the received HTTP status code is 200
And the treatment response contains the following body content NCT05568472_conf in properties:
| properties         |
| criteria_text      |
| criteria_type      |
When the user submit GET request to get testPatientIdMoti patient treatment by config ID: NCT05568472
Then the received HTTP status code is 200
And the body property criteria.reasoning of the response contains the following keyWords: NCT05568472_keyWords

@srs
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-102469,SysRS-102471,SysRS-102470
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/CTE-AI
@Description:Submit_get_for_non-existing_treatment_of_particular_patient
Scenario: Submit get for non-existing treatment of particular patient
When [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: cteAi/bundle_patient_moti.json
Then the optional fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue testPatientIdMoti
When the user submit GET request to get testPatientIdMoti patient treatment by config ID: kuku
Then the received HTTP status code is 404
And the "message" property in the response matches the value: No results found for the patient
When the user submit GET request to get treatment by treatment ID: kuku
Then the received HTTP status code is 404
And the "message" property in the response matches the value: No treatments found

@srs
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-102469,SysRS-102471,SysRS-102470
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/CTE-AI
@Description:Submit_post_treatments_request_for_particular_patients
Scenario: Submit post treatments request for particular patients
When [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: cteAi/bundle_patient_mary.json
Then the optional fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue testPatientIdMary
When [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: cteAi/bundle_patient_moti.json
Then the optional fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue testPatientIdMoti
When the following treatments have been configured for the patients:
| patient              | list_of_treatments                    |
| testPatientIdMoti    | NCT05568472, NCT06044857              |
| testPatientIdMary    | NCT05929768, NCT04457596              |
Then the received HTTP status code is 200
And the "message" property in the response matches the value: Ingestion process started
And the mandatory cte-ai-ai-criteria-matching Sfn processes are finished within 900 seconds with status Succeeded for property patients[0].id and propertyValue testPatientIdMoti
When the user submit GET request to get testPatientIdMoti patient treatment by config ID: NCT05568472
Then the received HTTP status code is 200
And the treatment response contains the following body content NCT05568472_conf in properties:
| properties         |
| criteria.statement |
| criteria.type      |
When the user submit GET request to get testPatientIdMoti patient treatment by config ID: NCT06044857
Then the received HTTP status code is 200
And the treatment response contains the following body content NCT06044857_conf in properties:
| properties         |
| criteria.statement |
| criteria.type      |
When the user submit GET request to get testPatientIdMary patient treatment by config ID: NCT05929768
Then the received HTTP status code is 200
And the treatment response contains the following body content NCT05929768_conf in properties:
| properties         |
| criteria.statement |
| criteria.type      |
When the user submit GET request to get testPatientIdMary patient treatment by config ID: NCT04457596
Then the received HTTP status code is 200
And the treatment response contains the following body content NCT04457596_conf in properties:
| properties         |
| criteria.statement |
| criteria.type      |

@srs
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-102469,SysRS-102471
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/CTE-AI
@Description:Submit_post_invalid_treatments_request_for_particular_patients
Scenario: Submit post invalid treatments request for particular patients
When [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: cteAi/bundle_patient_moti.json
Then the optional fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue testPatientIdMoti
When the following treatments have been configured for the patients:
| patient           | list_of_treatments |
| testPatientIdMoti | NCT05568472, kuku  |
Then the received HTTP status code is 404
And the "message" property in the response matches the value: Treatment ID(s) not found: kuku

@srs
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-102469,SysRS-102471
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/CTE-AI
@Description:Submit_post_treatments_request_for_invalid_patients
Scenario: Submit post treatments request for invalid patients
Given the Following treatment are configure to patient:
| patient           | list_of_treatments |
| testPatientId     | NCT05568472        |
Then the received HTTP status code is 200
And the "message" property in the response matches the value: Ingestion process started
And the mandatory cte-ai-ai-criteria-matching Sfn processes are finished implicit within 900 seconds with status Succeeded for patient testPatientId
And the cte-ai-ai-criteria-matching Sfn processes response contains the value "failed" for patient testPatientId

@srs
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-102469
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/CTE-AI
@Description:Submit_get_with_valid_ingestId
Scenario: Submit get with valid ingestId
When [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: cteAi/bundle_patient_moti.json
Then the optional fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue testPatientIdMoti
When the following treatments have been configured for the patients:
| patient              | list_of_treatments                    |
| testPatientIdMoti    | NCT05568472, NCT06044857              |
Then the mandatory cte-ai-ai-criteria-matching Sfn processes are finished within 900 seconds with status Succeeded for property patients[0].id and propertyValue testPatientIdMoti
When the user submits GET request to fetch latest Ingestion process status
Then the received HTTP status code is 200
And the "response[0].status" property in the response matches the value: success
And the "response[0].patient" property in the response matches the value: testPatientIdMoti

@srs
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-102469
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/CTE-AI
@Description:Submit_get_with_invalid_ingestId
Scenario: Submit get with invalid ingestId
Given the user submit GET request for getting Ingestion process by ingestion_id kuku
Then the received HTTP status code is 404
And the "message" property in the response contains the value: process not found


@srs
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-102469
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/CTE-AI
@Description:Submit_put_request_of_data_configuration
Scenario: Submit put request of data configuration
When the user submits PUT request to update data-configuration: Procedure_conf
Then the received HTTP status code is 200
And the "message" property in the response matches the value: Success

@srs
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-102469
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/CTE-AI
@Description:Submit_put_request_of_invalid_data_configuration
Scenario: Submit put request of invalid data configuration
When the user submits PUT request to update data-configuration: invalid_data_conf
Then the received HTTP status code is 400
And the "message" property in the response contains the value: Error: Invalid configuration received

@srs
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-102469
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/CTE-AI
@Description:Submit_Delete_patient_by_patient_id
Scenario: Submit Delete patient by patient id
When [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: cteAi/bundle_patient_moti.json
Then the optional fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue testPatientIdMoti
When the following treatments have been configured for the patients:
| patient              | list_of_treatments                    |
| testPatientIdMoti    | NCT05568472                           |
Then the mandatory cte-ai-ai-criteria-matching Sfn processes are finished within 900 seconds with status Succeeded for property patients[0].id and propertyValue testPatientIdMoti
Given the user submits DELETE patient request from CTE by patientID: testPatientIdMoti
Then the received HTTP status code is 200
And the "message" property in the response matches the value: Success
And the "patientId" property in the response matches the value: testPatientIdMoti

@srs
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-102469
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/CTE-AI
@Description:Submit_Delete_patient_by_invalid_patient_id
Scenario: Submit Delete patient by invalid patient id
Given the user submits DELETE patient request from CTE by patientID: kuku
Then the received HTTP status code is 404
And the "message" property in the response contains the value: Error: kuku not found

@srs
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-102469
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/CTE-AI
@Description:Submit_PUT_treatment_by_invalid_treatment_configuration
Scenario: Submit PUT treatment by invalid treatment configuration
When the user submits PUT request to update treatments configuration:
| fileName             |
| empty_treatment_conf |
Then the received HTTP status code is 422
And the "message" property in the response contains the value: A list of states that must have at least one item

@srs
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-102469
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/CTE-AI
@Description:Submit_Delete_treatment_by_treatmentId
Scenario: Submit Delete treatment by treatmentId
When the user submit DELETE request to delete treatment by treatment ID: NCT06044857
Then the received HTTP status code is 200
And the body of the response contains the following: NCT06044857_delete_res

@srs
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-102469
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/CTE-AI
@Description:Submit_Delete_treatment_by_invalid_treatmentId
Scenario: Submit Delete treatment by invalid treatmentId
When the user submit DELETE request to delete treatment by treatment ID: ""
Then the received HTTP status code is 404
And the "message" property in the response contains the value: not found for the deletion

@srs
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-102469
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/CTE-AI
@Description:Get_type_of_criteria
Scenario: Get type of criteria (Inclusion, Exclusion)
When [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: cteAi/bundle_patient_moti.json
Then the optional fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue testPatientIdMoti
When the following treatments have been configured for the patients:
| patient              | list_of_treatments       |
| testPatientIdMoti    | NCT06044857              |
Then the mandatory cte-ai-ai-criteria-matching Sfn processes are finished within 900 seconds with status Succeeded for property patients[0].id and propertyValue testPatientIdMoti
When the user submit GET request to get testPatientIdMoti patient treatment by config ID: NCT06044857
Then the received HTTP status code is 200
And the treatment response contains the following body content NCT06044857_conf in properties:
| properties         |
| criteria.statement |
| criteria.type      |

@srs
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-102469,SysRS-102472
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/CTE-AI
@Description:Check_eligibility_calculation_for_a_given_patient_for_specific_trial
Scenario: Check eligibility calculation for a given patient for specific trial
When [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: cteAi/bundle_patient_moti.json
Then the optional fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue testPatientIdMoti
When the user submits PUT request to update the existing patientID:testPatientIdMoti with following details:
| config_id   | config_type | trial_configuration_file    |
| NCT05568472 | trial       | NCT05568472-trial           |
Then the optional criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property patientId and propertyValue testPatientIdMoti
When the following treatments have been configured for the patients:
| patient              | list_of_treatments                    |
| testPatientIdMoti    | NCT05568472                           |
Then the mandatory cte-ai-ai-criteria-matching Sfn processes are finished within 900 seconds with status Succeeded for property patients[0].id and propertyValue testPatientIdMoti
And the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property patientId and propertyValue testPatientIdMoti
And the following score results are aligned for testPatientIdMoti patient with NCT05568472 trial and config file: NCT05568472-trial

@srs
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-102473
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/CTE-AI
@Description:Check_provided_calculation_status_update_for_a_patient_per_request
Scenario: Check provided calculation status update for a patient per request
When [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: cteAi/bundle_patient_moti.json
Then the optional fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue testPatientIdMoti
When the following treatments have been configured for the patients:
| patient              | list_of_treatments                    |
| testPatientIdMoti    | NCT05568472                           |
And the mandatory cte-ai-ai-criteria-matching sfn process is started
When the user submits GET request to fetch latest Ingestion process status
Then the "status" property in the response matches the value: RUNNING
And the mandatory cte-ai-ai-criteria-matching Sfn processes are finished within 900 seconds with status Succeeded for property patients[0].id and propertyValue testPatientIdMoti
When the user submits GET request to fetch latest Ingestion process status
Then the "response[0].status" property in the response matches the value: success
When the following treatments have been configured for the patients:
| patient               | list_of_treatments                    |
| testPatientIdMotke    | NCT05568472                           |
And the mandatory cte-ai-ai-criteria-matching sfn process is started
And the user submits GET request to fetch latest Ingestion process status
Then the "status" property in the response matches the value: RUNNING
And the mandatory cte-ai-ai-criteria-matching Sfn processes are finished within 900 seconds with status Succeeded for property patients[0].id and propertyValue testPatientIdMotke
When the user submits GET request to fetch latest Ingestion process status
Then the "response[0].status" property in the response matches the value: failed

@srs
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-102531
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/CTE-AI
@Description:Check_provided_calculation_status_update_for_a_patient_per_request
Scenario: The system shall meet the following Latency
When the following bundle was uploaded to DCC: cteAi/testPatientChiburashka.json
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue 1302dbb1-cefc-445a-a19e-97b09e2e75fa
When the following treatments have been configured for the patients:
| patient                                 | list_of_treatments       |
| 1302dbb1-cefc-445a-a19e-97b09e2e75fa    | NCT06044857              |
Then the received HTTP status code is 200
And the following ingestion time for 1302dbb1-cefc-445a-a19e-97b09e2e75fa
And the mandatory cte-ai-ai-criteria-matching Sfn processes are finished within 900 seconds with status Succeeded for property patients[0].id and propertyValue 1302dbb1-cefc-445a-a19e-97b09e2e75fa
And the following Latency ingestion time for patient is less then 20 minutes
When the user waits 2 minutes
Then the following criterion eligibility assessment take less than 30 Seconds for 1302dbb1-cefc-445a-a19e-97b09e2e75fa patient
