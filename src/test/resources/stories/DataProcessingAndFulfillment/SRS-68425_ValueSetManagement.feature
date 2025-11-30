Feature: [SysRS-68425] Value Set Management

Narrative: The system shall provide the following capabilities for GEHC Service user:
             1. Create/update valueset.
             2. Delete valueset.
             3. Download (get and retieve) valueset.


@sanity
@aws
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68425,SysRS-68421,SysRS-68600
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Data_processing_and_Fulfillment
@Description:Get_List_of_existing_value_sets<br/>,Value_Sets_successfully_presented_via_an_API
Scenario: List of existing value sets can be retrieved via an API
Given the following value sets are present in the system:
| items               | names |
| create_value_sets_1 | vs_1  |
| create_value_sets_2 | vs_2  |
| create_value_sets_3 | vs_3  |
When the user requests the list of all value sets
Then the received HTTP status code is 200
And the value sets response contains the following body content: all_value_sets
And the following value-set system log is present: Started get_all_vs_meta_data
And the following value-set system log 'Started get' timestamp is equal to the response timestamp
And the following value-set system log is present: Finished get /value-set (all)

@srs
@aws
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68425,SysRS-68421,SysRS-68600,SysRS-68601
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
@Description:Execute_different_API_requests(Create_Update_Delete)_and_check_auditLogs<br/>,Value_Sets_successfully_presented_via_an_API
@skip
Scenario: Audit log about Value Sets requests
Given the user remove value set name vs_1 when it exist in value set management
When the user creates the following value sets:
| items               |
| create_value_sets_1 |
Then the received HTTP status code is 200
And the response content matches the content of jsonFile for single valueSet: create_value_sets_1
And audit log is found by the service user with below parameters:
| property_path                                                   | property_value                                          |
| $.type.display                                                  | Generic API Test                                          |
| $.type.code                                                     | 110011                                                  |
| $.entity[0].detail[0].valueString                               | Generic API Test                                   |
| $.action                                                        | C                                                       |
| $.outcome                                                       | 0                                                       |
| $.objects[0].type                                               | SYSTEM OBJECT                                                  |
| $.objects[0].role                                              | REPORT                                                 |
| $.objects[0].id                                                | vs_1                                                 |
| $.outcomeDesc                                       | vs_1 value-set creation              |

When the user update value set vs_1 with body: vs_1_update
Then the received HTTP status code is 200
And the response content matches the content of jsonFile for single valueSet: vs_1_update
And audit log is found by the service user with below parameters:
| property_path                                                   | property_value                                          |
| $.type.display                                                  | Generic API Test                                          |
| $.type.code                                                     | 110011                                                  |
| $.outcome                                                       | 0                                                       |
| $.entity[0].detail[0].valueString                               | Generic API Test                                   |
| $.action                                                        | U                                                       |
| $.objects[0].type                                               | SYSTEM OBJECT                                                  |
| $.objects[0].role                                              | REPORT                                                 |
| $.objects[0].id                                                | vs_1                                                 |
| $.outcomeDesc                                       | vs_1 value-set update              |

When the user remove value set name vs_1
Then the received HTTP status code is 200
And audit log is found by the service user with below parameters:
| property_path                                                   | property_value                                          |
| $.type.display                                                  | Generic API Test                                          |
| $.type.code                                                     | 110011                                                  |
| $.outcome                                                       | 0                                                       |
| $.entity[0].detail[0].valueString                               | Generic API Test                                   |
| $.action                                                        | D                                                       |
| $.objects[0].type                                               | SYSTEM OBJECT                                                  |
| $.objects[0].role                                              | REPORT                                                 |
| $.objects[0].id                                                | vs_1                                                 |
| $.outcomeDesc                                       | vs_1 value-set deletion              |


@srs
@aws
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68425,SysRS-68421,SysRS-68600,SysRS-68601,SysRS-68422
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
@Description:Execute_different_API_requests(Create_Update_Delete)_and_check_auditLogs<br/>,Value_Sets_Error_presented_via_an_API
@skip
Scenario: Error Audit log about Value Sets requests
Given the user remove value set name vs_1 when it exist in value set management
When the user creates the following value sets:
| items                                  |
| create_value_sets_1_without_name_value |
Then the received HTTP status code is 422
And audit log is found by the service user with below parameters:
| property_path                                                   | property_value      |
| $.type.display                                                  | Generic API Test                                          |
| $.type.code                                                     | 110011                                                  |
| $.entity[0].detail[0].valueString                               | Generic API Test                                   |
| $.action                                                        | C                                                       |
| $.outcome                                                       | 12                                                       |
| $.objects[0].type                                               | SYSTEM OBJECT      |
| $.objects[0].role                                               | REPORT             |
| $.objects[0].id                                                 | N/A                |

When the user update value set vs_1 with body: create_value_sets_1_without_name_value
Then the received HTTP status code is 422
And audit log is found by the service user with below parameters:
| property_path                                                   | property_value      |
| $.type.display                                                  | Generic API Test    |
| $.type.code                                                     | 110011              |
| $.outcome                                                       | 12                  |
| $.entity[0].detail[0].valueString                               | Generic API Test    |
| $.action                                                        | U                   |
| $.objects[0].type                                               | SYSTEM OBJECT       |
| $.objects[0].role                                               | REPORT              |
| $.objects[0].id                                                 | N/A                 |

When the user remove value set name vs_10
Then the received HTTP status code is 404
And audit log is found by the service user with below parameters:
| property_path                                                   | property_value                                          |
| $.type.display                                                  | Generic API Test                                          |
| $.type.code                                                     | 110011                                                  |
| $.outcome                                                       | 12                                                       |
| $.entity[0].detail[0].valueString                               | Generic API Test                                   |
| $.action                                                        | D                                                       |
| $.objects[0].type                                               | SYSTEM OBJECT                                                  |
| $.objects[0].role                                              | REPORT                                                 |
| $.objects[0].id                                                | vs_10                                                 |
| $.outcomeDesc                                       | vs_10 value-set deletion              |


@srs
@aws
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68425,SysRS-68421,SysRS-68600
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Value_Set_Management
@Description:Get_for_particular_existing_value_sets<br/>,Value_Set_successfully_presented_via_an_API
Scenario: Specific value set can be retrieved by name via an API
Given the following value sets are present in the system:
| items               | names |
| create_value_sets_1 | vs_1  |
| create_value_sets_2 | vs_2  |
| create_value_sets_3 | vs_3  |
When the user requests for single value set: vs_2
Then the received HTTP status code is 200
And The response content matches the content of jsonFile: create_value_sets_2
And the following value-set system log is present: get_value_set_metadata vs_name='vs_2'
And the following value-set system log 'Started get' timestamp is equal to the response timestamp
And the following value-set system log is present: Finished

@srs
@aws
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68425,SysRS-68421,SysRS-68600,SysRS-68422
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Value_Set_Management
@Description:Update_existing_value_sets<br/>,Value_Set_successfully_Updated
Scenario: Existing value set can be updated via an API
Given the user remove value set name vs_2 when it exist in value set management
When the user creates new value set create_value_sets_2
And the user update value set vs_2 with body: vs_2_update
Then the received HTTP status code is 200
And the response content matches the content of jsonFile for single valueSet: vs_2_update
And the following value-set system log is present: response name='vs_2' description='value set 2 update' version='version_2'
And the following value-set system log is present: Finished put_object ${stack.name}-fulfill
And the following value-set system log 'Started update_vs with vs_name vs_2' timestamp is equal to the response timestamp
And the following value-set system log is present: Finished


@srs
@aws
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68425,SysRS-68421,SysRS-68600,SysRS-68422
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Value_Set_Management
@Description:Delete_particular_existing_value_sets<br/>,Value_Set_successfully_Deleted_via_an_API
Scenario: Existing value set can be deleted via an API
Given the following value sets are present in the system:
| items               | names |
| create_value_sets_1 | vs_1  |
| create_value_sets_2 | vs_2  |
| create_value_sets_3 | vs_3  |
When the user remove value set name vs_3
Then the received HTTP status code is 200
And the response content matches the content of jsonFile for single valueSet: create_value_sets_3
And the following value-set system log is present: finished delete_value_set vs_name='vs_3' success True
And the following value-set system log 'Started delete_vs_by_name vs_3' timestamp is equal to the response timestamp
And the following value-set system log is present: Finished delete /value-set/vs_3

@sanity
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68425,SysRS-68421,SysRS-68422
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Data_processing_and_Fulfillment
@Description:Create_Value_Set:<br/><ul><li>Json_File</li></ul>,Value_Set_successfully_created_via_an_API
Scenario: New value set can be created via an API
Given the user remove value set name vs_1 when it exist in value set management
When the user creates new value set create_value_sets_1
Then the received HTTP status code is 200
And the response content matches the content of jsonFile for single valueSet: create_value_sets_1

@srs
@aws
@fulfillment
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68425,SysRS-68421,SysRS-68422
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Value_Set_Management
@Description:Actions_on_value_sets_trigger_ETL_process_and_creates_new_entities<br/>,New_entities_successfully_created
Scenario: Actions on value sets trigger ETL process and creates new entities
Given 'create_data_model_1' data model is not present in the system
And the optional ETL processes are finished within 900 seconds with status Succeeded for dataModel create_data_model_1
And the user remove value set name vs_4 when it exist in value set management
When the user creates new value set create_value_sets_4
And the user creates new 'create_data_model_1' data model
Then the mandatory ETL processes are finished within 3000 seconds with status Succeeded for dataModel create_data_model_1
When the user update value set vs_4 with body: create_value_sets_4
Then the received HTTP status code is 200
And the mandatory ETL processes are finished within 900 seconds with status Succeeded for valueSet vs_4
And the mandatory ETL processes are finished within 3000 seconds with status Succeeded for dataModel create_data_model_1

@srs
@aws
@fulfillment
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68425,SysRS-68421,SysRS-68600,SysRS-68422
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Value_Set_Management/Error_handling
@Description:System_can_handle_errors_while_Getting_particular_value_set_via_an_API<br/>,System_present_relevant_error_status_code
Scenario: System can handle errors while retrieving specific value set by id via an API
Given the following value sets are present in the system:
| items               | names |
| create_value_sets_1 | vs_1  |
| create_value_sets_2 | vs_2  |
| create_value_sets_3 | vs_3  |
When the user requests for single value set: vs_test
Then the received HTTP status code is 404
And the response body contains the following text: 'Value set 'vs_test' not found.'
And the following value-set system log is present: Value set metadata not found
And the following value-set system log 'Started get_value_set' timestamp is equal to the response timestamp

@sanity
@aws
@fulfillment
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68425,SysRS-68421,SysRS-68600,SysRS-68422
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Data_processing_and_Fulfillment
@Description:System_can_handle_errors_while_creating_new_value_set_via_an_API:<br/><ul><li>Json_File</li></ul>,System_present_relevant_error_status_code
Scenario: System can handle errors while creating new value set via an API
Given the user remove value set name vs_1 when it exist in value set management
When the user creates new value set create_value_sets_1
Then the received HTTP status code is 200
And the following value-set system log is present: response name='vs_1' description='value set 1' version='version_1'
And the following value-set system log 'Started post_vs' timestamp is equal to the response timestamp
And the following value-set system log is present: Finished post_vs
When the user creates new value set create_value_sets_1
Then the received HTTP status code is 409
And the response body contains the following text: 'Value set 'vs_1' already exists'
And the following value-set system log is present: Value set vs_1 already exists
And the following value-set system log 'Started post_vs' timestamp is equal to the response timestamp
And the following value-set system log is present: Finished
When the user creates new value set create_value_sets_1_without_name_value
Then the received HTTP status code is 422
And the response body contains the following text: 'String should have at least 1 character'
When the user creates new value set create_value_sets_1_without_version_num
Then the received HTTP status code is 422
And the response body contains the following text: 'Field required'



@srs
@fulfillment
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68425,SysRS-68421,SysRS-68421,SysRS-68422
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Value_Set_Management/Error_handling
@Description:System_can_handle_errors_while_Updating_particular_value_set_via_an_API:<br/><ul><li>Json_File</li></ul>,System_present_relevant_error_status_code
Scenario: System can handle errors while updating an existing value set via an API
Given the following value sets are present in the system:
| items               | names |
| create_value_sets_1 | vs_1  |
| create_value_sets_2 | vs_2  |
| create_value_sets_3 | vs_3  |
When the user update value set vs_5 with body: create_value_sets_2
Then the received HTTP status code is 400
And the response body contains the following text: 'vs_5' provided in path is not equal to value set name provided in body 'vs_2'
And the following value-set system log is present: Value set name 'vs_5' provided in path is not equal to value set name provided in body 'vs_2'
When the user update value set vs_1 with body: create_value_sets_1_without_name_value
Then the received HTTP status code is 422
And the response body contains the following text: 'String should have at least 1 character'
When the user update value set vs_1 with body: create_value_sets_1_without_version_num
Then the received HTTP status code is 422
And the response body contains the following text: 'Field required'


@srs
@aws
@fulfillment
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68425,SysRS-68421,SysRS-68600,SysRS-68422
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment/Value_Set_Management/Error_handling
@Description:System_can_handle_errors_while_deleting_value_set_via_an_API:<br/><ul><li>Json_File</li></ul>,System_present_relevant_error_status_code
Scenario: System can handle errors while deleting a value set via an API
Given the user remove value set name vs_1 when it exist in value set management
When the user remove value set name vs_1
Then the received HTTP status code is 404
And the response body contains the following text: 'Value set 'vs_1' not found.'
And the following value-set system log is present: Value set vs_1 does not exists
And the following value-set system log 'Started delete_vs_by_name vs_1' timestamp is equal to the response timestamp



