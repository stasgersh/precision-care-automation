@cte
Feature: Clinical Criteria Calculator
Narrative:
The system should evaluate clinical criteria for each patient according to configuration

@PERMISSION_ISSUE_WAITING_FOR_NEW_PIPELINE
@sanityCalculator
@sanity
@calculator
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68438,SysRS-68600
@Description:Patient_exists_in_the_system,Get_summary_of_evaluated_criteria_of_custom_type_for_patient,User_can_fetch_the_summary_of_evaluated_criteria_of_custom_type_for_patient
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Criteria_Calculator/Evaluate_Criteria
Scenario: Get summary of evaluated criteria of custom type for patient
Given [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTeslaPatient_ModelY.json
And the new configuration uploaded with type of custom and configured for the patient 83a2a278-ff45-e749-4968-bd12345678cc
When the user submits GET request to retrieve the summary of evaluated criteria for patient id 83a2a278-ff45-e749-4968-bd12345678cc
Then the received HTTP status code is 200
And the "evaluated" property contains a list where all elements have "id" property
And the "evaluated" property contains a list where all elements have "metadata" property
And the "evaluated" property contains a list where all elements have "timestamp" property
And the following criteria-Query system log 'Started' timestamp is equal to the response timestamp
And the following criteria-Query system log searched by recent correlationID, is contains: END RequestId

@srs
@calculator
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68438,SysRS-68607
@Description:Patient_exists_in_the_system,Get_summary_of_unsupported_criteria_type,User_gets_error_when_trying_to_fetch_summary_of_unsupported_criteria_type
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Evaluate_Criteria
Scenario: Get summary of unsupported criteria type
When the user submits GET request to retrieve the summary of unsupported evaluated criteria type for non-existing patient
Then the received HTTP status code is 200
And the response body contains the following text: '{"patient":"non-existing","evaluated":[]}'

@srs
@calculator
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68438,SysRS-68607
@Description:custom_configuration_files_are_uploaded_and_configured_for_non_existing_patient,Get_summary_of_criteria_type_for_non_existing_patient,User_gets_error_when_trying_to_fetch_summary_of_non_existing_patient
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Evaluate_Criteria
Scenario: Get summary of criteria type for non existing patient
When the user submits GET request to retrieve the summary of unsupported evaluated criteria type for non-existing patient
Then the received HTTP status code is 200
And the response body contains the following text: '{"patient":"non-existing","evaluated":[]}'

@PERMISSION_ISSUE_WAITING_FOR_NEW_PIPELINE
@sanityCalculator
@sanity
@calculator
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68442,SysRS-68444,SysRS-68445,SysRS-68600
@Description:<ul><li>Patient_exists_in_the_system</li><li>custom_configuration_files_are_uploaded_and_configured</li></ul>,Get_output_of_a_custom_criteria_config_for_patient,User_can_fetch_output_of_custom_criteria_for_patient_successfully
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Clinical_Criteria_Calculator/Evaluate_Criteria
Scenario: Get output of a custom criteria config for patient
Given [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoSummerEvalPatient_Eval.json
When the user submits POST request to create new custom configuration for patient id 83a2a278-ff45-e749-4968-bd11111111cc
And the user submits GET request to retrieve config evaluation for patient 83a2a278-ff45-e749-4968-bd11111111cc by type of custom and dynamically created configId and fetched from recent transaction
Then the received HTTP status code is 200
And the response contains the expected output config criteria
And the following criteria-Query system log 'Started' timestamp is equal to the response timestamp
And the following criteria-Query system log searched by recent correlationID, is contains: END RequestId

@srs
@calculator
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68442,SysRS-68444,SysRS-68445,SysRS-68607
@Description:Patient_exists_in_the_system,Get_output_of_unsupported_criteria_type,User_gets_error_when_trying_to_fetch_output_of_unsupported_criteria_type
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Evaluate_Criteria
Scenario: Get output of unsupported criteria type
When the user submits GET request to retrieve the summary of unsupported evaluated criteria type for non-existing patient
Then the received HTTP status code is 200
And the response body contains the following text: '{"patient":"non-existing","evaluated":[]}'

@srs
@calculator
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68442,SysRS-68444,SysRS-68445,SysRS-68607
@Description:Patient_exists_in_the_system,Get_output_of_non_existing_configuration_id,User_gets_error_when_trying_to_fetch_output_of_non_existing_configuration
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Evaluate_Criteria
Scenario: Get output of non existing configuration id
When the user submits GET request to retrieve configuration with non-existing-id
Then the received HTTP status code is 404
And the response body contains the following text: '{"message":"File \"custom/non-existing-id.json\" not found in bucket'

@srs
@calculator
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68442,SysRS-68444,SysRS-68445,SysRS-68607
@Description:Get_output_of_non_existing_patient,User_gets_empty_evaluation_response_when_trying_to_fetch_output_of_non_existing_patient
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Evaluate_Criteria
Scenario: Get output of non existing patient
When the user submits GET request to retrieve the summary of evaluated criteria for patient id non-existing-patient-id
Then the received HTTP status code is 200
And the response body contains the following text: '{"patient":"non-existing-patient-id","evaluated":[]}'

@srs
@calculator
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68438,SysRS-68607
@Description:Patient_exists_in_the_system,Get_output_for_patient_with_no_configured_clinical_custom,Check_output_endpoint_when_patient_has_no_configured_custom_returns_proper_error
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Evaluate_Criteria
Scenario: Get output for patient with no configured clinical custom
When the user submits GET request to retrieve the output of created configuration for patientId 345 and configId 123456
Then the received HTTP status code is 404
And the response body contains the following text: 'Result not found'

@srs
@calculator
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68438,SysRS-68607
@Description:Patient_exists_in_the_system,Get_output_for_patient_with_no_configured_clinical_trial,Check_output_endpoint_when_patient_has_no_configured_trial_returns_proper_error
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Evaluate_Criteria
Scenario: Get output for patient with no configured clinical trial
When the user submits GET request to retrieve the output of created configuration for patientId 345 and configId 123456
Then the received HTTP status code is 404
And the response body contains the following text: 'Result not found'

@srs
@calculator
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68442,SysRS-68444,SysRS-68445
@Description:<ul><li>Patient_exists_in_the_system</li><li>Custom_configuration_files_are_uploaded_and_configured</li></ul>,Get_output_of_a_custom_criteria_config_for_patient_having_multiple_configurations,Check_output_response_has_multiple_results_for_multiple_configurations_per_patient
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Evaluate_Criteria
Scenario: Get output of a custom criteria config for patient having multiple configurations
Given [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Calculator.json
When the user submits POST request to create new custom1 configuration for patient id 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5
And the user submits GET request to retrieve the created custom1 type configuration
Then the received HTTP status code is 200
When the user submits GET request to retrieve config evaluation for patient 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 by type of custom1 and dynamically created configId and fetched from recent transaction
Then the ".attributes.*" property contains a list where the elements have "value" properties with below values: NOT_MET, MET, MISSING, NOT_MET,MET
When the user submits POST request to create new custom2 configuration for patient id 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5
And the user submits GET request to retrieve the created custom2 type configuration
Then the received HTTP status code is 200
When the user submits GET request to retrieve config evaluation for patient 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 by type of custom2 and dynamically created configId and fetched from recent transaction
Then the ".attributes.*" property contains a list where the elements have "value" properties with below values: NOT_MET, MET, MISSING, NOT_MET,MET

@srs
@calculator
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68696
@Description:<ul><li>Patient_exists_in_the_system</li><li>Trial_configuration_files_are_uploaded_and_configured</li></ul>,Check_states_order_-_First_state_is_matched,Check_matched_state_order_in_the_evaluated_criteria_output_is_first
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Evaluate_Criteria
Scenario: Check states order - First state is matched
Given [API] the following patient is uploaded to PDS: patients/OncoTestPatient_Mbappe.json
And [API] a mandatory ETL process is started in 180 seconds and finished within 180 seconds with status Succeeded for patient 83a2a278-ff45-e749-4968-4321Mbappe
And the user sends a PUT request to create/update a trial configuration with config ID NCT04428788 and file criteria-calculator-first-met for patient ID 83a2a278-ff45-e749-4968-4321Mbappe
When the user submits GET request to retrieve config evaluation for patient 83a2a278-ff45-e749-4968-4321Mbappe by type of trial and configID NCT04428788
Then the received HTTP status code is 200
And the ".attributes.*" property contains a list where the elements have "value" properties with below values: MET, MET, MISSING

@srs
@calculator
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68696
@Description:<ul><li>Patient_exists_in_the_system</li><li>Trial_configuration_files_are_uploaded_and_configured</li></ul>,Check_states_order_-_middle_state_is_matched,Check_matched_state_in_the_middle_order_in_the_evaluated_criteria_output
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Evaluate_Criteria
Scenario: Check states order - State in the middle is matched
Given [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Janneta.json
And the user sends a PUT request to create/update a trial configuration with config ID NCT12345678 and file criteria-calculator-med-met for patient ID 83a2a278-ff45-e749-4968-4321Janneta
When the user submits GET request to retrieve config evaluation for patient 83a2a278-ff45-e749-4968-4321Janneta by type of trial and configID NCT12345678
Then the received HTTP status code is 200
And the ".attributes.*" property contains a list where the elements have "value" properties with below values: NOT_MET, MET, MISSING

@srs
@calculator
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68696
@Description:<ul><li>Patient_exists_in_the_system</li><li>Trial_configuration_files_are_uploaded_and_configured</li></ul>,Check_states_order_-_last_state_is_matched,Check_matched_last_state_in_the_evaluated_criteria_output
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Evaluate_Criteria
Scenario: Check states order - Last state is matched
Given [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Janneta.json
And the user sends a PUT request to create/update a trial configuration with config ID NCT12121212 and file criteria-calculator-last-met for patient ID 83a2a278-ff45-e749-4968-4321Janneta
When the user submits GET request to retrieve config evaluation for patient 83a2a278-ff45-e749-4968-4321Janneta by type of trial and configID NCT12121212
Then the received HTTP status code is 200
And the ".attributes.*" property contains a list where the elements have "value" properties with below values: MET, NOT_MET, MET

@srs
@calculator
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68438,SysRS-68607
@Description:Patient_exists_in_the_system,Get_summary_for_patient_with_no_configured_clinical_custom,Check_summary_endpoint_when_patient_has_no_configured_custom_returns_proper_error
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Evaluate_Criteria
Scenario: Get summary for patient with no configured clinical trial
When the user submits GET request to retrieve the summary of evaluated criteria for patient id 29591
Then the received HTTP status code is 200
And the response body contains the following text: '{"patient":"29591","evaluated":[]}'

@srs
@calculator
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68438
@Description:<ul><li>Patient_exists_in_the_system</li><li>custom_configuration_files_are_uploaded_and_configured</li></ul>,Get_summary_of_evaluated_criteria_of_custom_type_for_patient_having_multiple_configurations,Check_summary_response_has_multiple_results_for_multiple_configurations_per_patient
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Evaluate_Criteria
Scenario: Get summary of evaluated criteria of custom type for patient having multiple configurations
Given [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Calculator.json
When the new configuration uploaded with type of custom1 and configured for the patient 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5
Then the received HTTP status code is 200
When the user submits GET request to retrieve the summary of evaluated criteria for patientId 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 and configuration type custom1
Then the response body contains the following text: '{"patient":"8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5","evaluated":[{"id":'
When the new configuration uploaded with type of custom2 and configured for the patient 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5
Then the received HTTP status code is 200
When the user submits GET request to retrieve the summary of evaluated criteria for patientId 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 and configuration type custom1
Then the response body contains the following text: '{"patient":"8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5","evaluated":[{"id":'

@srs
@calculator
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68438
@Description:<ul><li>Patient_exists_in_the_system</li><li>Trial_configuration_files_are_uploaded_and_configured</li></ul>,Get_summary_of_evaluated_criteria_of_custom_type_for_patient_having_multiple_configurations,Check_summary_response_has_multiple_results_for_multiple_configurations_per_patient
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Clinical_Criteria_Calculator/Evaluate_Criteria
Scenario: Get summary of evaluated criteria of trial type for patient having multiple configurations
Given [API] the following bundle is re-uploaded to DPSA if missing in fulfillment, by using [patient] by: patients/OncoTestPatient_Calculator.json
When the new configuration uploaded with type of trial and configured for the patient 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5
Then the received HTTP status code is 200
When the user submits GET request to retrieve the summary of evaluated criteria for patientId 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 and configuration type trial
Then the response body contains the following text: '{"patient":"8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5","evaluated":[{"id":'
When the new configuration uploaded with type of trial and configured for the patient 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5
Then the received HTTP status code is 200
When the user submits GET request to retrieve the summary of evaluated criteria for patientId 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 and configuration type trial
Then the response body contains the following text: '{"patient":"8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5","evaluated":[{"id":'