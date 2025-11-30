@cte
@cleanupAllPatientCustomTypeConfigurations
Feature: [SysRS-68448] Configuration

Narrative: The system shall support the following configuration items:
             1. Clinical trials list:  to allow the user to add/remove trials to the list, trial name, trial conditions as described below . see Clinical trials (SysRS-68447).
             2. Clinical trial eligibility score - criteria match configuration for each trial. see SysRS-68446.
             3. Complex criteria configuration. see SysRS-68445
             4. Atomic criterion configuration. see SysRS-68444


@PERMISSION_ISSUE_WAITING_FOR_NEW_PIPELINE
@sanityCalculator
@sanity
@calculator
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68448,SysRS-68600
@Description:<ul><li>Patient_exists_in_the_system</li><li>custom_configuration_files_are_uploaded_and_configured</li></ul>,Check_returning_all_available_configurations_of_type_custom_for_patient,the_configurations_list_for_specific_patient_are_returned
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Criteria_Calculator/Configuration
Scenario: Check returning all available configurations of type custom for patient
Given [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Mbappe.json
And the new configuration uploaded with type of custom and configured for the patient 83a2a278-ff45-e749-4968-4321Mbappe
When the user submits GET request to retrieve the list of configurations for patient 83a2a278-ff45-e749-4968-4321Mbappe
Then the received HTTP status code is 200
And the "results" property contains a list where all elements have "is_global" properties with value: False
And the "results" property contains a list where all elements have "metadata" property
And the following criteria-ContentManagement system log 'Started' timestamp is equal to the response timestamp
And the following criteria-ContentManagement system log searched by recent correlationID, is contains: END RequestId


@sanityCalculator
@sanity
@calculator
@Platform:Rest_API
@Category:Negative
@Description:Read_non_existing_configuration_contents,user_gets_proper_error_message_when_trying_to_fetch_non_existing_configuration
@Traceability:SysRS-68448,SysRS-68600,SysRS-68662,SysRS-68608
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Criteria_Calculator/Configuration
Scenario: Read non existing configuration contents
When the user submits GET request to retrieve configuration with non-existing-id
Then the received HTTP status code is 404
And the response body contains the following text: 'not found'
And the following criteria-ContentManagement system log 'Started get' timestamp is equal to the response timestamp
And the following criteria-ContentManagement system log searched by recent correlationID, is contains: END RequestId

@sanityCalculator
@sanity
@calculator
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68448
@Description:the_custom_configuration_files_are_uploaded,Update_existing_configuration,user_can_successfully_update_existing_configuration
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Criteria_Calculator/Configuration
Scenario: Update existing configuration
Given the custom configuration files are uploaded
When the user submits PUT request to update existing configuration
Then the received HTTP status code is 200
And the response has the id of the updated configuration
And status field has 'updated' value
When the user submits GET request to retrieve the updated custom type configuration
Then the received HTTP status code is 200
And the response contains the relevant configuration custom

@sanityCalculator
@sanity
@calculator
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68448,SysRS-68662,SysRS-68608
@Description:the_custom_configuration_file_is_uploaded,Create_config_with_existing_configuration_id,user_gets_error_message_that_configuration_already_exists
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Criteria_Calculator/Configuration
Scenario: Check error message when creating configuration with existing id
Given the custom configuration files are uploaded
When the user submits POST request to create new configuration with existing id
Then the received HTTP status code is 409
And the response body contains the following text: 'already exists'

@sanityCalculator
@sanity
@calculator
@Platform:Rest_API
@Category:Positive
@Description:custom_configuration_files_are_uploaded,Check_returning_all_available_configurations_of_type_custom,the_configurations_list_of_custom_type_are_returned
@Traceability:SysRS-68448
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Criteria_Calculator/Configuration
Scenario: Check returning all available configurations of type custom
Given the custom configuration files are uploaded
When the user submits GET request to retrieve the list of configurations
Then the received HTTP status code is 200
And the "results" property contains a list where all elements have "is_global" properties with value: True
And the "results" property contains a list where all elements have "metadata" property

@PERMISSION_ISSUE_WAITING_FOR_NEW_PIPELINE
@sanityCalculator
@sanity
@calculator
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68448,SysRS-68600
@Description:Create_a_configuration_of_a_specific_type_of_custom,user_can_successfully_create_configuration_of_custom_type
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Criteria_Calculator/Configuration
Scenario: Create a configuration of a specific type of custom
When the user submits POST request to create new custom configuration
Then the received HTTP status code is 200
And the response has the id of the new configuration
And status field has 'created' value
And the following criteria-ContentManagement system log 'Started' timestamp is equal to the response timestamp
And the following criteria-ContentManagement system log searched by recent correlationID, is contains: END RequestId
When the user submits GET request to retrieve the created custom type configuration
Then the received HTTP status code is 200
And the response contains the newly created custom configuration

@sanityCalculator
@sanity
@calculator
@Platform:Rest_API
@Category:Positive
@Description:Create_a_configuration_of_a_specific_type_for_specific_patient,user_can_successfully_create_configuration_of_custom_type_for_specific_patient
@Traceability:SysRS-68448
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Criteria_Calculator/Configuration
Scenario: Create a configuration of a specific type for specific patient
When the user submits POST request to create new custom configuration for patient id 123
Then the received HTTP status code is 200
And the response has the id of the new configuration
And status field has 'created' value
When the user submits GET request to retrieve the created custom type configuration
Then the received HTTP status code is 200
And the response contains the newly created custom configuration
When the user submits GET request to retrieve the list of configurations for patient 123
Then the received HTTP status code is 200
And the "results" property contains a list where all elements have "is_global" properties with value: False

@sanityCalculator
@sanity
@calculator
@Platform:Rest_API
@Category:Positive
@Description:the_custom_configuration_files_are_uploaded,Delete_existing_configuration,user_can_successfully_delete_existing_configuration_by_id
@Traceability:SysRS-68448,SysRS-68662,SysRS-68608
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Criteria_Calculator/Configuration
Scenario: Delete existing configuration
Given the custom configuration files are uploaded
When the user submits DELETE request to delete existing configuration
Then the received HTTP status code is 200
And the response has the id of the deleted configuration
And status field has 'deleted' value
When the user submits GET request to retrieve configuration with non-existing-id
Then the received HTTP status code is 404
And the response body contains the following text: 'not found'

@sanityCalculator
@sanity
@calculator
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68448,SysRS-68449,SysRS-68662,SysRS-68608
@Description:Check_creating_invalid_configuration_with_missing_required_main_fields<ul><li>id</li><li>type</li><li>variables</li><li>outputs</li></ul>,user_gets_proper_error_message_When_trying_to_create_configuration_missing_required_main_fields
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Criteria_Calculator/Configuration
Scenario: Check creating invalid configuration with missing required main fields
When the user submits POST request to create new configuration missing or invalid required main fields
Then the received HTTP status code is 422
And the response body contains the following text: '{"message":"Invalid Argument(s)'

@srs
@calculator
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68448
@Description:Create_a_configuration_of_non_existing_patient,user_can_successfully_create_configuration_for_non_existing_patient_without_errors
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Configuration
Scenario: Create a configuration of non existing patient
When the user submits POST request to create new custom configuration for patient id non-existing-id
Then the received HTTP status code is 200
And the response has the id of the new configuration
And status field has 'created' value

@srs
@calculator
@Platform:Rest_API
@Category:Negative
@Description:Create_a_configuration_of_non_supported_type,user_gets_proper_error_message_when_trying_to_create_non_supported_type_of_configuration
@Traceability:SysRS-68448
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Configuration
Scenario: Create a configuration of non supported type
When the user submits POST request to create new nonSupportedType configuration
Then the received HTTP status code is 200
And status field has 'created' value

@srs
@calculator
@Platform:Rest_API
@Category:Negative
@Description:Update_non_existing_configuration,user_gets_proper_error_message_when_trying_to_update_non_existing_configuration
@Traceability:SysRS-68448,SysRS-68662,SysRS-68608
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Configuration
Scenario: Update non existing configuration
When the user submits PUT request to update non existing configuration
Then the received HTTP status code is 400
And the response body contains the following text: '{"message":"Bad request: The configuration id or type in the URL does not match the id or type provided in the body"}'

@srs
@calculator
@Platform:Rest_API
@Category:Negative
@Description:Delete_non_existing_configuration,user_gets_proper_error_message_When_trying_to_delete_non_existing_configuration
@Traceability:SysRS-68448,SysRS-68662,SysRS-68608
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Configuration
Scenario: Delete non existing configuration
When the user submits DELETE request to delete non existing configuration
Then the received HTTP status code is 404
And the response body contains the following text: '{"message":"Error: Config not found"}'

@srs
@calculator
@Platform:Rest_API
@Category:Negative
@Description:Check_creating_invalid_configuration_with_failed_fulfillment_query,user_gets_proper_error_message_When_trying_to_create_configuration_with_failed_fulfillment_query
@Traceability:SysRS-68448,SysRS-68662,SysRS-68608
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Configuration
Scenario: Check creating invalid configuration with failed fulfillment query
When the user submits POST request to create new configuration missing or invalid required fulfillment fields
Then the received HTTP status code is 422
And the response body contains the following text: '{"message":"Invalid Argument(s)'

@srs
@calculator
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68448,SysRS-68449,SysRS-68662,SysRS-68608
@Description:Check_creating_invalid_configuration_with_missing_required_fulfillment_field,user_gets_proper_error_message_When_trying_to_create_configuration_missing_required_fulfillment_field
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Configuration
Scenario: Check creating invalid configuration with missing required fulfillment field
When the user submits POST request to create new configuration missing or invalid required expression field
Then the received HTTP status code is 422
And the response body contains the following text: '{"message":"Invalid Argument(s)'

@srs
@calculator
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68448,SysRS-68449,SysRS-68662,SysRS-68608
@Description:Check_creating_invalid_configuration_with_missing_required_states_fields,user_gets_proper_error_message_When_trying_to_create_configuration_missing_required_states_field
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Configuration
Scenario: Check creating invalid configuration with missing required states fields
When the user submits POST request to create new configuration missing or invalid required states fields
Then the received HTTP status code is 422
And the response body contains the following text: '{"message":"Invalid Argument(s)'

@SPR-4774
@srs
@calculator
@Platform:Rest_API
@Category:Negative
@Description:Check_creating_invalid_configuration_with_missing_required_items_fields,user_gets_proper_error_message_When_trying_to_create_configuration_missing_required_items_fields
@Traceability:SysRS-68448,SysRS-68449,SysRS-68662,SysRS-68608
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Configuration
Scenario: Check creating invalid configuration with missing required items fields
When the user submits POST request to create new configuration missing or invalid required items fields
Then the received HTTP status code is 422
And the response body contains the following text: '{"message":"Invalid Argument(s)'

@sanityCalculator
@sanity
@calculator
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68448,SysRS-68449,SysRS-68662,SysRS-68608
@Description:Check_submitting_empty_configuration_file_(empty_json),user_gets_proper_error_message_when_trying_to_submit_empty_json_configuration_file
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Criteria_Calculator/Configuration
Scenario: Check submitting empty configuration file (empty json)
When the user submits POST request to create empty configuration file
Then the received HTTP status code is 422
And the response body contains the following text: '{"message":"Invalid Argument(s)'

@srs
@calculator
@Platform:Rest_API
@Category:Negative
@Description:Check_submitting_non_supported_configuration_file<ul><li>invalid_JSON_file</li><li>invalid_CSV_file</li><li>invalid_XML_file</li></ul>,user_gets_proper_error_message_when_trying_to_submit_invalid_configuration_file
@Traceability:SysRS-68448,SysRS-68449,SysRS-68662,SysRS-68608
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Configuration
Scenario Outline: Check submitting non supported configuration file (invalid json, csv, xml etc..)
When the user submits POST request to create configuration with <file_name> and extension of <file_extension>
Then the received HTTP status code is 422
And the response body contains the following text: '<response_body>'
Examples:
| file_name    |file_extension   | response_body |
| invalid_json |.json            | {"message":"Invalid Argument(s): json_invalid ('body' |
| invalid_csv  |.csv             | {"message":"Invalid Argument(s): json_invalid ('body' |
| invalid_xml  |.xml             | {"message":"Invalid Argument(s): json_invalid ('body' |