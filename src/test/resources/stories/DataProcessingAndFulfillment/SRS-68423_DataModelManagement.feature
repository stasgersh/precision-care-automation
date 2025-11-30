Feature: [SysRS-68423] Data Model Management

Narrative: The system shall allow GEHC Service User the ability to modify data models' content (see Data mapping configuration (SysRS-68422)) without the need to redeploy the system (hot reloading).


@sanity
@aws
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68423
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Data_processing_and_Fulfillment
@Description:Create_data_models:<br/><ul><li>testBmi</li><li>testMetastasis</li></ul>,Data_models_successfully_created_via_an_API
Scenario Outline: New Data model can be created via an API
Given '<modelName>' data model is not present in the system
And the optional ETL processes are finished within 180 seconds with status Succeeded for dataModel <modelName>
When the user creates new '<modelName>' data model
Then the received HTTP status code is 200
And the "status" property in the response matches the value: Success
And the mandatory ETL processes are finished within 3000 seconds with status Succeeded for dataModel <modelName>
When the user requests the data model '<modelName>' by name
Then the received HTTP status code is 200
And the "classId" property in the response matches the value: <modelName>
Examples:
| modelName |
| testBmi   |
| testMetastasis|

@srs
@aws
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68423
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Model_Management
@Description:Submit_get_data_models_list_request,Data_models'_names_and_metadata_can_be_retrieved_via_an_API_filtered_by_name.<br/>Metadata_contains:<br/><ul><li>created</li><li>usesResourceTypes</li><li>usesValueSets</li></ul>
Scenario: Non-filtered list of data models' names and metadata present can be retrieved via an API
Given following data models are present in the system:
| items         |
| testBloodPressure |
| testImagingStudyRequest |
And the optional ETL process is finished within 3000 seconds with status Succeeded
When the user requests the list of data models
Then the received HTTP status code is 200
And the "models" property contains a list where all elements have following properties:
| items               |
| classId            |
| created             |
| usesResourceTypes |
| usesValueSets     |
And the response body contains the following lines:
| items                        |
| "classId":"testBloodPressure"   |
| "classId":"testImagingStudyRequest"   |


@sanity
@aws
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68423
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Data_processing_and_Fulfillment
@Description:Submit_get_data_models_list_request_filtered_by:<ul>_<li>testImagingStudyRequest</li></ul>,Data_models'_names_and_metadata_can_be_retrieved_via_an_API_filtered_by_name.<br/>Metadata_contains:<br/><ul><li>created</li><li>usesResourceTypes</li><li>usesValueSets</li></ul>
Scenario: List of data models' names and metadata can be retrieved via an API filtered by name
Given following data models are present in the system:
| modelName     |
| testBloodPressure |
| testImagingStudyRequest |
And the optional ETL process is finished within 3000 seconds with status Succeeded
When the user requests the list of data models with filter 'testImagingStudyRequest'
Then the received HTTP status code is 200
And the "models" property contains a list where all elements have following properties:
| propertyName        |
| classId            |
| created             |
| usesResourceTypes |
| usesValueSets     |
And the "models[*]" property contains a list where the elements have "classId" properties with below values:
| modelName     |
| testImagingStudyRequest |

@srs
@aws
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68423
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Model_Management
Scenario Outline: Existing Data model can be deleted via an API
Given following data models are present in the system:
| modelName     |
| <modelName>   |
And the optional ETL process is finished within 3000 seconds with status Succeeded
When the user deletes '<modelName>' data model
Then the received HTTP status code is 200
And the "status" property in the response matches the value: Success
And the mandatory ETL processes are finished within 3000 seconds with status Succeeded for dataModel <modelName>
When the user requests the data model '<modelName>' by name
Then the received HTTP status code is 404
And the "message" property in the response matches the value: Entity model '<modelName>' not found.
Examples:
| modelName     |
| testBmi           |
| testBloodPressure |
| testImagingStudyRequest |

@srs
@aws
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68423
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Model_Management
Scenario Outline: Existing Data model can be updated via an API
Given following data models are present in the system:
| modelName     |
| <modelName>   |
And the optional ETL processes are finished within 3000 seconds with status Succeeded for dataModel <modelName>
When the user updates '<modelName>' data model
Then the received HTTP status code is 200
And the "status" property in the response matches the value: Success
And the mandatory ETL processes are finished within 3000 seconds with status Succeeded for dataModel <modelName>
When the user requests the data model '<modelName>' by name
Then the received HTTP status code is 200
And the "model.description" property in the response contains the value: updated
And the response contains the "updated" property
Examples:
| modelName     |
| testBmi           |
| testBloodPressure |
| testImagingStudyRequest |

@srs
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68423,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Model_Management
Scenario: Actions on Data models trigger ETL process and changes entities
Given 'testPatient' data model is not present in the system
Then the optional ETL process is finished within 180 seconds with status Succeeded
Given the following patient was uploaded to PDS: fulfillment/dataQuery/data_query_bundle.json
When the user creates new 'testPatient' data model
Then the received HTTP status code is 200
And the mandatory ETL process is started for testPatient dataModel
#And audit log is found by the service user with below parameters:
#| property_path                                                   | property_value                                         |
#| $.type.display                                                  | Generic API Test                                       |
#| $.type.code                                                     | 110011                                                 |
#| $.action                                                        | C                                                      |
#| $.outcome                                                       | 0                                                      |
#| $.objects[0].type                                               | SYSTEM OBJECT                                          |
#| $.objects[0].role                                               | REPORT                                                 |
#| $.objects[0].id                                                 | testPatient                                            |
#| $.outcomeDesc                                       | testPatient model creation                             |

And the mandatory ETL processes are finished within 3000 seconds with status Succeeded for dataModel testPatient
When the user executes [testPatient] data query for single patient [testPatientIdAmit]
Then the received HTTP status code is 200
And the dataQuery response contains the following body content: data_query_patient
Then the version stored
Given the following patient was uploaded to PDS: fulfillment/dataQuery/data_query_bundle_update.json
When the user updates 'testPatient' data model
Then the received HTTP status code is 200
#And audit log is found by the service user with below parameters:
#| property_path                                                   | property_value                                         |
#| $.type.display                                                  | Generic API Test                                       |
#| $.type.code                                                     | 110011                                                 |
#| $.action                                                        | U                                                      |
#| $.outcome                                                       | 0                                                      |
#| $.objects[0].type                                               | SYSTEM OBJECT                                          |
#| $.objects[0].role                                               | REPORT                                                 |
#| $.objects[0].id                                                 | testPatient                                            |
#| $.outcomeDesc                                       | testPatient model update                             |

And the mandatory ETL processes are finished within 3000 seconds with status Succeeded for dataModel testPatient
When the user executes [testPatient] data query for single patient [testPatientIdAmit]
Then the received HTTP status code is 200
And the dataQuery response contains the following body content: data_query_patient_updated
Then the version updated
When the user deletes 'testPatient' data model
Then the received HTTP status code is 200
#And audit log is found by the service user with below parameters:
#| property_path                                                   | property_value                                         |
#| $.type.display                                                  | Generic API Test                                       |
#| $.type.code                                                     | 110011                                                 |
#| $.action                                                        | D                                                      |
#| $.outcome                                                       | 0                                                      |
#| $.objects[0].type                                               | SYSTEM OBJECT                                          |
#| $.objects[0].role                                               | REPORT                                                 |
#| $.objects[0].id                                                 | testPatient                                            |
#| $.outcomeDesc                                       | testPatient model deletion                             |

And the mandatory ETL processes are finished within 3000 seconds with status Succeeded for dataModel testPatient
When the user executes [testPatient] data query for single patient [testPatientIdAmit]
Then the received HTTP status code is 404

@srs
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68423,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Model_Management
Scenario: Actions on Data models triggered ETL process
Given following data models are present in the system:
| modelName                 |
| testComorbidity           |
| testPrimaryCancerDisease  |
| testPatient               |
When the following patient was deleted from PDS: fulfillment/dataQuery/data_query_bundle.json
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient testPatientIdAmit, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue testPatientIdAmit
When the following patient was deleted from PDS: fulfillment/dataQuery/patientMary_bundle.json
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient MaryChrisID, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue MaryChrisID
When the following bundle was uploaded to PDS with no fixed wait: fulfillment/dataQuery/data_query_bundle_update.json
Then the received HTTP status code is 200
When the following bundle was uploaded to PDS with no fixed wait: fulfillment/dataQuery/patientMary_bundle.json
Then the mandatory ETL process is started for testPatientIdAmit patient
When the user updates 'testComorbidity' data model
And the user updates 'testPatient' data model
Then the mandatory ETL process is started for testPatient dataModel
When the following bundle was uploaded to PDS with no fixed wait: fulfillment/dataQuery/data_query_bundle.json
Then the mandatory ETL processes are finished within 3000 seconds with status Succeeded for dataModel testPatient
When the user executes [testPatient] data query for single patient [testPatientIdAmit]
Then the received HTTP status code is 200
And the dataQuery response contains the following body content: data_query_patient
When the user executes [testPatient] data query for single patient [MaryChrisID]
Then the received HTTP status code is 200
And the dataQuery response contains the following body content: data_query_patientMary
When the user executes [testComorbidity, testPrimaryCancerDisease] data queries for single patient [testPatientIdAmit]
Then the received HTTP status code is 200
And the dataQuery response contains the following body content: data_query_comorbidity_primaryCancerDisease

@srs
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68423,SysRS-68419
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Model_Management
Scenario: Changes to source data (PDS) trigger ETL process and changes entities
When following data models are present in the system:
            | items         |
            | testPatient       |
Then the optional ETL processes are finished within 3000 seconds with status Succeeded for dataModel testPatient
When the following patient was deleted from PDS: fulfillment/dataQuery/data_query_bundle.json
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient testPatientIdAmit, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue testPatientIdAmit
When [API] the following patient is uploaded to PDS: fulfillment/dataQuery/data_query_bundle.json
And the user executes [testPatient] data query for single patient [testPatientIdAmit]
Then the received HTTP status code is 200
And the dataQuery response contains the following body content: data_query_patient
When [API] the following patient is uploaded to PDS: fulfillment/dataQuery/data_query_bundle_update.json
And the user executes [testPatient] data query for single patient [testPatientIdAmit]
Then the received HTTP status code is 200
And the dataQuery response contains the following body content: data_query_patient_updated

@srs
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68423
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Model_Management
Scenario: Entity values match the source resources' data
Given the following patient was uploaded to PDS: summary/amitHaze_bundle_BMI_BSA.json
When the user executes [testBmi] data query for single patient [testPatientIdAmitHaze]
Then the received HTTP status code is 200
And the dataQuery response aligned to requested resource: amitHaze_bundle_BMI_BSA

@srs
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68423
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Model_Management
Scenario: ETL process does not block queries
When the following patient was deleted from PDS: fulfillment/dataQuery/data_query_bundle_update.json
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient testPatientIdAmit, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue testPatientIdAmit
Given following data models are present in the system:
            | items         |
            | testPatient       |
And the optional ETL processes are finished within 3000 seconds with status Succeeded for dataModel testPatient
And the following patient was uploaded to PDS: fulfillment/dataQuery/data_query_bundle.json
When the user executes [testPatient] data query for single patient [testPatientIdAmit]
Then the received HTTP status code is 200
And the dataQuery response contains the following body content: data_query_patient
Given [API] the following patient is uploaded to PDS with no fixed wait: fulfillment/dataQuery/data_query_bundle_update.json
When the mandatory ETL process is started
And the user executes [testPatient] data query for single patient [testPatientIdAmit]
Then the received HTTP status code is 200
And the dataQuery response contains the following body content: data_query_patient
And the mandatory ETL process is finished within 3000 seconds with status Succeeded
When the user executes [testPatient] data query for single patient [testPatientIdAmit]
Then the received HTTP status code is 200
And the dataQuery response contains the following body content: data_query_patient_updated

@srs
@fulfillment
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68423
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Model_Management/Error_handling
Scenario: System can handle errors while retrieving data model's definition by name via an API
When the user requests the data model 'kukuriku' by name
Then the received HTTP status code is 404
And the "message" property in the response matches the value: Entity model 'kukuriku' not found.
When the user requests the data model '/*' by name
Then the received HTTP status code is 403
And the response body contains the following text: 'Invalid key=value pair'


@sanity
@fulfillment
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68423,SysRS-68424
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Data_processing_and_Fulfillment
@Description:Send_invalid_data_model_creation_requests_with:<ul><li>empty_body</li><li>empty_json_body_(_"{}"_)</li><li>invalid_json</li><li>missing_field_recordType</li><li>filters_containing_non-existent_value_set</li><li>wrong_value_in_definitions[0].resourceType</li><li>already_existing_name</li></ul>,<ul><li>System_can_handle_above_errors.</li><li>System_returns_proper_error_messages.</li></ul>
Scenario: System can handle errors while creating a new data model via an API
Given 'testBmi' data model is not present in the system
And the optional ETL processes are finished within 180 seconds with status Succeeded for dataModel testBmi
When the user creates new 'testBmi' data model with empty body
Then the received HTTP status code is 422
And the "message" property in the response matches the value: Invalid Argument(s): ('body',) Field required
When the user creates new 'testBmi' data model with empty json body
Then the received HTTP status code is 422
When the user creates new 'testBmi' data model with invalid json
Then the received HTTP status code is 422
And the response body contains the following lines: JSON decode error
When the user creates new 'testBmi' data model with missing field classId
Then the received HTTP status code is 422
And the response body contains the following lines:
|items                              |
|('body', 'classId') Field required |
When the user creates new 'testBmi' data model with filters containing non-existent value set
Then the received HTTP status code is 412
And the response body contains the following lines: Missing value sets
When the user creates new 'testBmi' data model with wrong value in definitions[0].resourceType
Then the received HTTP status code is 412
And the response body contains the following lines: Invalid FHIR resource types
When the user creates new 'testBmi' data model
Then the received HTTP status code is 200
And the mandatory ETL processes are finished within 3000 seconds with status Succeeded for dataModel testBmi
When the user creates new 'testBmi' data model
Then the received HTTP status code is 409
And the response body contains the following lines: already exists

@srs
@fulfillment
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68423,SysRS-68424
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Model_Management/Error_handling
@Description:Send_invalid_data_model_update_requests_with:<ul><li>empty_body</li><li>empty_json_body_(_&quot;{}&quot;_)</li><li>invalid_json</li><li>missing_field_recordType</li><li>filters_containing_non-existent_value_set</li><li>wrong_value_in_definitions[0].resourceType</li><li>non_existant_model_name</li><li>model_name_in_url_not_matching_name_in_body</li></ul>,<ul><li>System_can_handle_above_errors.</li><li>System_returns_proper_error_messages.</li></ul>
Scenario: System can handle errors while updating an existing data model via an API
Given following data models are present in the system:
| items         |
| testBmi       |
And the optional ETL processes are finished within 3000 seconds with status Succeeded for dataModel testBmi
When the user updates 'testBmi' data model with empty body
Then the received HTTP status code is 422
And the "message" property in the response matches the value: Invalid Argument(s): ('body',) Field required
When the user updates 'testBmi' data model with empty json body
Then the received HTTP status code is 422
When the user updates 'testBmi' data model with invalid json
Then the received HTTP status code is 422
And the response body contains the following lines: JSON decode error
When the user updates 'testBmi' data model with missing field classId
Then the received HTTP status code is 422
And the response body contains the following lines:
|items                              |
|('body', 'classId') Field required |
When the user updates 'testBmi' data model with filters containing non-existent value set
Then the received HTTP status code is 412
And the response body contains the following lines: Missing value sets
When the user updates 'testBmi' data model with wrong value in definitions[0].resourceType
Then the received HTTP status code is 412
And the response body contains the following lines: Invalid FHIR resource types
Given following data models are updated in the system:
| items               |
| nonExistentModel    |
Then the received HTTP status code is 404
And the response body contains the following lines: Entity model 'nonExistentModel' not found
When the user updates 'testBmi' data model with wrong value in classId
Then the received HTTP status code is 400
And the response body contains the following lines: provided in path is not equal to


@srs
@fulfillment
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68423
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Model_Management/Error_handling
Scenario: System can handle errors while deleting a data model via an API
When the user deletes 'testDoctor' data model
Then the received HTTP status code is 404
And the response body contains the following lines: Entity model 'testDoctor' not found.
When the user creates new 'testBmi' data model
Then the optional ETL processes are finished within 3000 seconds with status Succeeded for dataModel testBmi
When the user updates 'testBmi' data model
And the mandatory ETL process is started
And the user deletes 'testBmi' data model
Then the received HTTP status code is 423
And the response body contains the following lines: Entity model 'testBmi' is locked.


@srs
@fulfillment
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68423
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Model_Management/Error_handling
Scenario: Failed operations with data models do not trigger ETL process
Given following data models are present in the system:
| items         |
| testBmi       |
And the optional ETL processes are finished within 3000 seconds with status Succeeded for dataModel testBmi
When the user updates 'testBmi' data model with empty body
Then the received HTTP status code is 422
And the optional ETL process is not started for testBmi dataModel
When the user updates 'testBmi' data model with filters containing non-existent value set
Then the received HTTP status code is 412
And the optional ETL process is not started for testBmi dataModel
When the user creates new 'testBmi' data model
Then the received HTTP status code is 409
And the optional ETL process is not started for testBmi dataModel
Given following data models are updated in the system:
| items               |
| nonExistentModel    |
Then the received HTTP status code is 404
And the optional ETL process is not started for nonExistentModel dataModel
When the user deletes 'testDoctor' data model
Then the received HTTP status code is 404
And the optional ETL process is not started for testBmi dataModel

@srs
@manual
@fulfillment
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68423
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Data_processing_and_Fulfillment
@Description:<p>Trigger_ETL_process_by:</p><ul>_<li>Ingesting&nbsp;new_data_model</li>_<li>Updating_existing_data_model</li>_<li>Deleting_existing_data_model</li></ul><p>Make_ETL_process_fail_by_changing_DPSA_url_in_lambda</p>,<ul>_<li>ETL_process_stops_executing_after_3_retries.</li>_<li>No_entities_are_changed_when_querying_the_fulfillment_service.</li></ul>
Scenario: Failed ETL process does not update existing entities
Given following data models are present in the system:
| modelName     |
| testPatient   |
And the optional ETL processes are finished within 3000 seconds with status Succeeded for dataModel testPatient
When the user executes [testPatient] data query for single patient [testPatientIdAmit]
Then the received HTTP status code is 200
And the dataQuery response contains the following body content: data_query_patient
Given user changes DPSA url in fulfillment lambada to invalid
When the user updates 'testPatient' data model
Then the mandatory ETL processes are finished within 3000 seconds with status Failed for dataModel testPatient
And the ETL process failed after 2 retries
When user changes DPSA url in fulfillment lambada to valid
And the user executes [testPatient] data query for single patient [testPatientIdAmit]
Then the received HTTP status code is 200
And the dataQuery response contains the following body content: data_query_patient

@srs
@manual
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68423
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Data_processing_and_Fulfillment
@Description:<p>Review_ETL_code_to_check_that_it_will_try_to_recover_after_failure_up_to_3_times</p>,<ul>_<li>ETL_process_should_restart&nbsp;after_the_the_failure_up_to_3_times</li></ul>
Scenario: ETL service can recover after fail
Given the code review was performed
Then ETL code shows the retry mechanism that can try to recover after failure up to 3 times

@srs
@aws
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68423,SysRS-68415
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Model_Management
Scenario: Original content is not changed by ETL process
Given following data models are present in the system:
| items                 |
| testComorbidity       |
And the optional ETL processes are finished within 3000 seconds with status Succeeded for dataModel testComorbidity
Given the following patient was uploaded to PDS: patients/OncoTestPatient_PatientDataSync.json
Then the following patient's data was not changed in DPSA: patients/OncoTestPatient_PatientDataSync.json
When the user deletes 'testComorbidity' data model
Then the mandatory ETL processes are finished within 180 seconds with status Succeeded for dataModel testComorbidity
And the following patient's data was not changed in DPSA: patients/OncoTestPatient_PatientDataSync.json

@srs
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68427
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Data_processing_and_Fulfillment
@manual
Scenario: The system notifies the user when data source that is configured is unavailable or unreachable
Given PDS service is unreachable
When the following patient was uploaded to PDS: summary/amitHaze_bundle.json
And the mandatory ETL process is finished within 180 seconds with status Failed
Then user is notified about the error








