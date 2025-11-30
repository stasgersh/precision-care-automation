@cte
Feature: [SysRS-68445] Complex Criteria

Narrative: The system shall be able to calculate and display match status for a complex criterion (having more than one condition) as follow:
           If the complex is AND between criteria, then the status is the lowest indication status of these criteria.
           If the complex is OR between criteria, then the status is the highest indication status of these criteria.

@srs
@calculator
@Platform:Rest_API
@Category:Positive
@Description:the_custom_configuration_files_are_uploaded_with_non_complex_criteria,Update_existing_configuration_of_a_specific_type_of_custom_for_10_-_20_conditions,user_can_update_configuration_having_10_-_20_conditions
@Traceability:SysRS-68445
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Complex_Criteria
Scenario Outline: Update existing configuration with complex criteria for 10 - 20 conditions
Given the custom configuration files are uploaded with non complex criteria for patient: 83a2a278-ff45-e749-4968-bd12345678cc
When the user sends a PUT request to update calculator configuration using stored generated configID,type custom, patient 83a2a278-ff45-e749-4968-bd12345678cc from file <file_path>
Then the received HTTP status code is 200
When the user submits GET request to retrieve calculator configuration by custom type, existing configID and patient 83a2a278-ff45-e749-4968-bd12345678cc
Then the received HTTP status code is 200
And the response contains recently created configuration with custom, generated configurationID and the configuration from <file_path>
Examples:
| file_path                                         |
| complex_12_criteria_with_20_OR_items(conditions)  |

@srs
@calculator
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68445
@Description:the_custom_configuration_files_are_uploaded_with_non_complex_criteria,Delete_existing_configuration_of_a_specific_type_of_custom_for_10_-_20_conditions,user_can_delete_configuration_having_10_-_20_conditions
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Complex_Criteria
Scenario: Delete existing configuration with complex criteria for 10 - 20 conditions
Given the custom configuration files are uploaded with complex criteria for patient: 83a2a278-ff45-e749-4968-bd12345678cc
When the user submits DELETE request to delete existing configuration
Then the received HTTP status code is 200
And the response has the id of the deleted configuration
And status field has 'deleted' value
When the user submits GET request to retrieve the created custom type configuration
Then the received HTTP status code is 404
And the response body contains the following text: 'not found in bucket'

@srs
@calculator
@Platform:Rest_API
@Category:Positive
@Description:the_custom_configuration_files_are_uploaded_with_non_complex_criteria,Read_configuration_contents_for_complex_criteria_between_10_-_20_conditions,user_can_fetch_configuration_having_10_-_20_conditions
@Traceability:SysRS-68445
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Complex_Criteria
Scenario Outline: Read configuration contents for complex criteria between 10 - 20 conditions
Given the custom configuration files are uploaded with complex criteria for patient: 83a2a278-ff45-e749-4968-bd12345678cc
When the user submits GET request to retrieve calculator configuration by custom type, existing configID and patient 83a2a278-ff45-e749-4968-bd12345678cc
Then the received HTTP status code is 200
And the response contains recently created configuration with custom, generated configurationID and the configuration from <file_path>
Examples:
| file_path                         |
| validComplexCriteriaCustomConfig  |

@sanityCalculator
@sanity
@calculator
@Platform:Rest_API
@Category:Positive
@Description:Create_a_configuration_of_a_specific_type_of_custom_for_10_-_20_conditions,Configuration_can_be_created_with<ul><li>complex_criteria_10_and_items</li><li>complex_criteria_15_and_items</li><li>complex_criteria_20_or_items</li><li>complex_criteria_17_and_items</li></ul>
@Traceability:SysRS-68445
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Complex_Criteria
Scenario Outline: Create a configuration of a specific type of custom for 10 - 20 conditions
Given [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTeslaPatient_ModelY.json
When the user submits POST request to create new configuration having complex criteria <file_path>
Then the received HTTP status code is 200
And the response has the id of the new configuration
And status field has 'created' value
When the user submits GET request to retrieve the list of configurations for patient 83a2a278-ff45-e749-4968-bd12345678cc
Then the received HTTP status code is 200
And the "results" property contains a list where all elements have "is_global" properties with value: False
And the "results" property contains a list where all elements have "metadata" property
Examples:
| file_path                                         |
| complex_12_criteria_with_17_AND_items(conditions) |
| complex_12_criteria_with_20_OR_items(conditions)  |

@sanityCalculator
@sanity
@calculator
@Platform:Rest_API
@Category:Positive
@Description:Check_different_expression_handling_in_configurations,Configuration_can_be_created_with<ul><li>reference_variables_expression_config</li><li>constants_expression_config</li><li>math_operation_expression_config</li><li>nested_objects_items</li><li>supported_functions_items</li><li>non_supported_functions_items</li><li>mixed_expressions_items</li></ul>
@Traceability:SysRS-68445
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Criteria_Calculator/Complex_Criteria
Scenario Outline: Check expression handling in configurations
When the user submits POST request to create new configuration having expression <file_path>
Then the received HTTP status code is 200
And the response has the id of the new configuration
And status field has 'created' value
When the user submits GET request to retrieve the list of configurations for patient 83a2a278-ff45-e749-4968-bd12345678cc
Then the received HTTP status code is 200
And the "results" property contains a list where all elements have "is_global" properties with value: False
And the "results" property contains a list where all elements have "metadata" property
When the user submits GET request to retrieve the summary of evaluated criteria for patient id 83a2a278-ff45-e749-4968-bd12345678cc
Then the received HTTP status code is 200
Examples:
| file_path                             |
| reference_variables_expression_config |
| constants_expression_config           |
| math_operation_expression_config      |
| nested_objects_items                  |
| supported_functions_items             |
| mixed_expressions_items               |