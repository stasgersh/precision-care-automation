Feature: [SysRS-68417] Data Query

Narrative: The system shall support the following operations on patient's data: Querying and filtering data

Background:
When the following patient was deleted from PDS: fulfillment/dataQuery/data_query_bundle.json
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient testPatientIdAmit, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue testPatientIdAmit

@sanity
@aws
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68417
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Data_processing_and_Fulfillment
@Description:Execute_Query_for_particular_patient:<br/><ul><li>patientId</li><li>version</li></ul>,data_query_executed_successfully
Scenario: Execute query for a particular patients to retrieve any related entities
Given following data models are present in the system:
            | items         |
            | testPatient       |
And the optional ETL processes are finished within 3000 seconds with status Succeeded for dataModel testPatient
And the following patient was uploaded to PDS: fulfillment/dataQuery/data_query_bundle.json
When the user executes [testPatient] data query for single patient [testPatientIdAmit]
Then the received HTTP status code is 200
And the dataQuery response contains the following body content: data_query_patient
And the following query system log is present: testPatientIdAmit
And the following query system log 'Started get' timestamp is equal to the response timestamp
And the following query system log is present: Finished query_by_patient

@srs
@aws
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68417,SysRS-68600,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
@Description:Execute_API_request(Post)_and_check_DataQuery<br/>,DataQuery_successfully_presented_via_an_API
@skip
Scenario: Audit log about DataQuery requests
Given following data models are present in the system:
            | items         |
            | testComorbidity       |
And the optional ETL processes are finished within 3000 seconds with status Succeeded for dataModel testComorbidity
And the following patient was uploaded to PDS: fulfillment/dataQuery/data_query_bundle.json
When the user executes [testComorbidity] data query for single patient [testPatientIdAmit]
Then the received HTTP status code is 200
And audit log is found by the service user with below parameters:
| property_path                                                   | property_value                                          |
| $.type.display                                                  | Patient Record                                          |
| $.type.code                                                     | 110110                                                  |
| $.action                                                        | R                                                       |
| $.outcome                                                       | 0                                                       |
| $.objects[1].type                                               | PERSON                                                  |
| $.objects[1].role                                               | PATIENT                                                 |
| $.objects[1].id                                                 | testPatientIdAmit                                       |
| $.outcomeDesc                                       | patient testPatientIdAmit query                         |



@sanity
@aws
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68417
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Data_processing_and_Fulfillment
@Description:Execute_Queries_for_particular_patient_after_data_model_update:<br/><ul><li>patientId</li><li>version</li></ul><br/><strong>Show_attributes:</strong><ul><li>code</li><li>date</li></ul><ul><li>value</li><li>definitions_name</li></ul><ul><li>conclusion</li><li>title</li></ul><ul><li>status</li>,data_query_executed_successfully
Scenario: Execute query for a particular patient after updating data model
Given following data models are present in the system:
| items                |
| testMetastasis           |
And the optional ETL processes are finished within 3000 seconds with status Succeeded for dataModel testMetastasis
When [API] the following patient is uploaded to PDS: fulfillment/dataQuery/data_query_bundle.json
And the user executes [testMetastasis] data query for single patient [testPatientIdAmit] with different attributes in "show" component:
| Attributes                  |
| attributes.date             |
| attributes.code             |
| attributes.clinicalStatus   |
| attributes.definitions_name |
Then the received HTTP status code is 200
And the dataQuery response contains the following body content: data_query_with_show_metastasis

@srs
@aws
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68417
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Query
@Description:Execute_Query_with_multiple_queries_for_particular_patient:<br/><ul><li>patientId</li><li>version</li></ul>,data_query_executed_successfully
Scenario: Execute query for a particular patient with multiple queries
Given following data models are present in the system:
            | items                 |
            | testPrimaryCancerDisease  |
            | testComorbidity           |
And the optional ETL process is finished within 3000 seconds with status Succeeded
When the following patient was uploaded to PDS: fulfillment/dataQuery/data_query_bundle.json
And the user executes [testComorbidity, testPrimaryCancerDisease] data queries for single patient [testPatientIdAmit]
Then the received HTTP status code is 200
And the dataQuery response contains the following body content: data_query_comorbidity_primaryCancerDisease


@srs
@aws
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68417
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Query
@Description:Execute_Query_with_different_amount_of_entities_for_particular_patient:<br/><ul><li>patientId</li><li>version</li></ul>,data_query_executed_successfully
Scenario: Execute query for a particular patients with different amounts of entities
Given following data models are present in the system:
            | items                   |
            | testComorbidity             |
            | testMetastasis              |
            | testMedication              |
And the optional ETL process is finished within 3000 seconds with status Succeeded
When [API] the following patient is uploaded to PDS: fulfillment/dataQuery/data_query_bundle.json
And the user executes [testComorbidity] data query for single patient [testPatientIdAmit]
Then the received HTTP status code is 200
And the "results[0].entries.id" property in the response contains 2 elements
When the user executes [testMetastasis] data query for single patient [testPatientIdAmit]
Then the received HTTP status code is 200
And the "results[0].entries.id" property in the response contains 2 elements
When the user executes [testMedication] data query for single patient [testPatientIdAmit]
Then the received HTTP status code is 200
And the "results[0].entries.id" property in the response contains 2 elements

@srs
@aws
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68417
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Query
@Description:Execute_Query_with_different_values_in_"show"_component_for_particular_patient:<br/><ul><li>patientId</li><li>version</li></ul><br/><strong>Show_attributes:</strong><ul><li>code</li><li>date</li></ul><ul><li>value</li><li>definitions_name</li></ul><ul><li>clinicalStatus</li>,data_query_executed_successfully
Scenario: Execute query for a particular patient with different values in "show" component
Given following data models are present in the system:
            | items                |
            | testMetastasis           |
            | testPrimaryCancerDisease |
And the optional ETL process is finished within 3000 seconds with status Succeeded
When the following patient was uploaded to PDS: fulfillment/dataQuery/data_query_bundle.json
And the user executes [testMetastasis] data query for single patient [testPatientIdAmit] with different attributes in "show" component:
| Attributes                  |
| attributes.date             |
| attributes.code             |
| attributes.clinicalStatus   |
| attributes.definitions_name |
Then the received HTTP status code is 200
And the dataQuery response contains the following body content: data_query_with_show_metastasis
When the user executes [testPrimaryCancerDisease] data query for single patient [testPatientIdAmit] with different attributes in "show" component:
| Attributes                  |
| attributes.date             |
| attributes.stage            |
| attributes.definitions_name |
| attributes.clinicalStatus   |
Then the received HTTP status code is 200
And the dataQuery response contains the following body content: data_query_with_show_primaryCancerDisease

@srs
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68417
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Query
@Description:Execute_Query_with_different_values_in_"from"_component_for_particular_patient:<br/><ul><li>patientId</li><li>version</li></ul><br/><strong>From_attributes:</strong><ul><li>testPatient</li><li>testComorbidity</li></ul>,data_query_executed_successfully
Scenario: Execute query for a particular patient with different values in "from" component
Given following data models are present in the system:
      | items         |
      | testPatient       |
      | testComorbidity    |
And the optional ETL process is finished within 3000 seconds with status Succeeded
When [API] the following patient is uploaded to PDS: fulfillment/dataQuery/data_query_bundle.json
And the user executes [testPatient] data query for single patient [testPatientIdAmit] with [testPatient] parameter in "from" component
Then the received HTTP status code is 200
And the dataQuery response contains the following body content: data_query_patient
When the user executes [testComorbidity] data query for single patient [testPatientIdAmit] with [testComorbidity] parameter in "from" component
Then the received HTTP status code is 200
And the dataQuery response contains the following body content: data_query_comorbidity

@srs
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68417
@Description:Execute_Query_with_different_json_values_in_&quot;filter&quot;_component_for_particular_patient:<p><strong>Data_models:</strong></p><ul><li>testMetastasis</li><li>testComorbidity</li></ul><p><br_/><strong>json_attributes_filters:</strong></p><ul><li>date_equal</li><li>provenance_equal</li></ul>,data_query_executed_successfully
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Query
Scenario: Execute query for a particular patient with different json values in "filter" component
Given following data models are present in the system:
      | items         |
      | testMetastasis    |
      | testComorbidity |
And the optional ETL process is finished within 3000 seconds with status Succeeded
When the following patient was uploaded to PDS: fulfillment/dataQuery/data_query_bundle.json
And the user executes [testMetastasis] data query from [testMetastasis] for single patient [testPatientIdAmit] with data_query_filter_date values in "filter" component
Then the received HTTP status code is 200
And the dataQuery response contains the following body content: data_query_metastasis_with_filter
When the user executes [testComorbidity] data query from [testComorbidity] for single patient [testPatientIdAmit] with data_query_filter_provenance values in "filter" component
Then the received HTTP status code is 200
And the dataQuery response contains the following body content: data_query_comorbidity_with_filter

@srs
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68417
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Query
@Description:Execute_Query_for_list_of_patients:<br/><ul><li>patient-list</li></ul>,data_query_executed_successfully
Scenario: Execute query for list of patients with different amounts of filtered entities
When [API] the following patient is uploaded to PDS: fulfillment/dataQuery/data_query_bundle.json
And the user executes data query for list of patients with different values in "filter" component: patient_list_query_filter_date
Then the received HTTP status code is 200
And the "entries" property in the response contains 1 elements
When the user executes data query for list of patients with different values in "filter" component: patient_list_query_filter_code
Then the received HTTP status code is 200
And the "entries" property in the response contains 26 elements
When the user executes data query for list of patients with different values in "filter" component: patient_list_query_filter_martialStatus
Then the received HTTP status code is 200
And the "entries" property in the response contains 8 elements


@sanity
@aws
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68417
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Data_processing_and_Fulfillment
@Description:Execute_Query_for_list_of_patients:<br/><ul><li>patient-list</li></ul>,data_query_executed_successfully
Scenario: Execute query for list of patients without filters
Given following data models are present in the system:
            | items         |
            | patient       |
And the optional ETL processes are finished within 3000 seconds with status Succeeded for dataModel patient
And the following patient was uploaded to PDS: fulfillment/dataQuery/patient_bundle.json
When the user executes data query for list of patients
Then the received HTTP status code is 200
And the dataQuery for patient-list response contains the following body content: data_query_patient_list
And the following query system log is present: Finished query_entities
And the following query system log 'Started get' timestamp is equal to the response timestamp
And the following query system log is present: Finished query_patient_list

@srs
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68417
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Query
@Description:Execute_Query_with_different_json_values_in_"filter"_component_for_list_of_patients:<br/><ul><li>patient-list</li></ul><br/><strong>json_attributes_filters:</strong><ul><li>date_equal</li><li>code_equal</li></ul>,data_query_executed_successfully
Scenario: Execute query for list of patients with different json values in "filter" component
When the user executes data query for list of patients with different values in "filter" component: patient_list_query_filter_date
Then the received HTTP status code is 200
And the dataQuery for patient-list response contains the following body content: data_query_patient_list_with_date_filter
When the user executes data query for list of patients with different values in "filter" component: patient_list_query_filter_code
Then the received HTTP status code is 200
And the dataQuery for patient-list response contains the following body content: data_query_patient_list_with_code_filter

@srs
@fulfillment
@NotImplemented
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68417
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Query
@Description:Execute_Query_with_different_json_values_in_"sort"_component_for_list_of_patients:<br/><ul><li>patient-list</li></ul><br/><strong>json_attributes_sort:</strong><ul><li>attribute.field.name.1</li></ul>,data_query_executed_successfully
Scenario Outline: Execute query for list of patients for all entities with different values in sort component
Given following data models are present in the system:
            | items         |
            | testPatient           |
And the optional ETL processes are finished within 3000 seconds with status Succeeded for dataModel testPatient
When the user executes dataQuery for list of patients with different attributes <attributes> in "sort" component by desc order
Then the received HTTP status code is 200
And the dataQuery for patient-list sorted by desc order with <attributes> attribute
Examples:
| attributes        |
| latest_patient_version |


@srs
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68417
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Query
@Description:Execute_Query_with_different_values_in_"show"_component_for_list_of_patient:<br/><ul><li>patient-list</li><li>version</li></ul><br/><strong>Show_attributes:</strong><ul><li>patient_id</li><li>attribute.gender</li></ul>,data_query_executed_successfully
Scenario: Execute query for list of patients with different values in "show" component
When the user executes data query for list of patients with different attributes in "show" components:
| Attributes   |
| attributes.id   |
| attributes.gender |
Then the received HTTP status code is 200
And the dataQuery for patient-list with different attributes response contains the following body content: data_query_patient_list_with_show_comp

@srs
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68417
@Description:Check_basic_pagination_functionality,data_query_executed_successfully
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Query
Scenario: Check basic pagination functionality
When the user executes data query for list of patients
Then the received HTTP status code is 200
And the "entries" property in the response contains 30 elements
When the user moving to next page
Then the "entries" property in the response contains 30 elements


@srs
@fulfillment
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68417
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Query/Error_handling
@Description:Execute_Query_for_particular_patient_with_empty_patientID:<ul><li>testBmi_model</li></ul><p>data_query_sent_successfully</p>
Scenario: Execute query for particular patient with empty patient id
Given following data models are present in the system:
            | items         |
            | testBmi           |
And the optional ETL processes are finished within 3000 seconds with status Succeeded for dataModel testBmi
When the user executes [testBmi] data query for single patient []
Then the received HTTP status code is 403
And the response body contains the following text: 'Invalid key=value pair'

@sanity
@aws
@fulfillment
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68417
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Data_processing_and_Fulfillment
@Description:Execute_Query_for_particular_patient_with_invalid_patientID:<br/><ul><li>patientId</li><li>version</li></ul>,data_query_sent_successfully
Scenario: Execute query for particular patient with invalid patient id
Given following data models are present in the system:
            | items         |
            | testPatient       |
And the optional ETL processes are finished within 3000 seconds with status Succeeded for dataModel testPatient
And the following patient was uploaded to PDS: fulfillment/dataQuery/patient_bundle.json
When the user executes [testPatient] data query for single patient [1111]
Then the received HTTP status code is 200
And the dataQuery response contains the following body content: patient_with_unexisting_id

@srs
@aws
@fulfillment
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68417
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Query/Error_handling
@Description:Execute_Query_with_different_unsupported_body_values_for_particular_patient:</p><ul><li>testPatient</li></ul><p><br/><strong>Show_attributes:</strong></p><ul><li>10</li></ul><p><br/><strong>From_attributes:</strong></p><ul><li>10</li></ul><p><strong>Filter_attributes:</strong></p><ul><li>Empty_range</li></ul>,data_query_executed_successfully
Scenario: Execute query for particular patient with unsupported body parameters values
Given following data models are present in the system:
            | items         |
            | testPatient           |
And the optional ETL processes are finished within 3000 seconds with status Succeeded for dataModel testPatient
When [API] the following patient is uploaded to PDS with no fixed wait: fulfillment/dataQuery/data_query_bundle.json
And the user executes [testPatient] data query for single patient [testPatientIdAmit] with different invalid attributes in "show" component: 10
Then the received HTTP status code is 422
And the response body contains the following text: 'Invalid Argument(s)'
When the user executes data query [testPatient] for single patient [testPatientIdAmit] with invalid 10 parameter in "from" component
Then the received HTTP status code is 422
And the response body contains the following text: 'Invalid Argument(s)'
When the user executes [testPatient] data query from [testPatient] for single patient [testPatientIdAmit] with data_query_invalid_filter values in "filter" component
Then the received HTTP status code is 422
And the response body contains the following text: 'Invalid Argument(s)'

@srs
@fulfillment
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68417
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Query/Error_handling
@Description:Execute_query_for_specific_patient_with_unsupported_component_parameter:<br/><ul><li>kuku</li></ul>,data_query_sent_successfully
Scenario: Execute query for specific patient with unsupported component parameters
Given following data models are present in the system:
            | items         |
            | testBmi           |
And the optional ETL processes are finished within 3000 seconds with status Succeeded for dataModel testBmi
When the user executes [testBmi] data query for single patient [testPatientIdAmit] with "kuku" component: data_query_invalid_component
Then the received HTTP status code is 422
And the response body contains the following text: 'Invalid Argument(s)'

@srs
@fulfillment
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68417
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Query/Error_handling
@Description:Execute_Query_with_different_unsupported_body_values_for_list_of_patients</p><p><strong>Show_attributes:</strong></p><ul><li>10</li></ul><p>&nbsp;</p><p><strong>Filter_attributes:</strong></p><ul<li>Empty_range</li></ul<p>&nbsp;</p><p><strong>Sort_attributes:</strong></p><ul<li>10</li></ul><p>&nbsp;</p>,data_query_executed_successfully</p>
Scenario: Execute query for list of patients with unsupported body parameters values
When the user executes data query for list of patients with different invalid attributes in "show" components: 10
Then the received HTTP status code is 422
And the response body contains the following text: 'Invalid Argument(s)'
When the user executes data query for list of patients with different invalid values in "filter" component: patient_list_query_invaild_filter
Then the received HTTP status code is 422
And the response body contains the following text: 'Invalid Argument(s)'
When the user executes data query for list of patients with different invalid values in "sort" component: patient_list_query_invaild_sort
Then the received HTTP status code is 422
And the response body contains the following text: 'Invalid Argument(s)'

@srs
@fulfillment
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68417
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Data_Query/Error_handling
@Description:Execute_query_for_list_of_patients_with_unsupported_component_parameters:<br/><ul><li>patient-list</li></ul>,data_query_sent_successfully
Scenario: Execute query for list of patients with unsupported component parameters
When the user executes data query for list of patients with "kuku" component: data_query_patient_list_invalid_component
Then the received HTTP status code is 422
And the response body contains the following text: 'Invalid Argument(s)'




