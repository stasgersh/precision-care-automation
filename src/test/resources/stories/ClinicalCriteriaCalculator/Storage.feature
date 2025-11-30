@cte
Feature: [SysRS-68696] Storage

Narrative: The system shall delete all patient’s data stored in the system (comments as well) whenever the patient is deleted from PDSc.

@manual
@integration
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68696
@Description:The_patient_exists_in_the_system,Check_that_all_criteria_results_are_deleted_when_deletion_event_received_for_patient,The_criteria_results_in_the_system_are_deleted_for_the_patient
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Clinical_Criteria_Calculator
Scenario: Check that all criteria results are deleted when deletion event received for patient
Given [API] the following patient is uploaded to PDS: patients/OncoTestPatient_Mbappe.json
And the new configuration uploaded with type of custom and configured for the patient 83a2a278-ff45-e749-4968-4321Mbappe
When the generated ID is stored as parameter from new custom configuration creation
When the user submits GET request to retrieve the list of all criteria configurations for patient 83a2a278-ff45-e749-4968-4321Mbappe and config type of custom
Then the received HTTP status code is 200
And the response contains at least one new configuration that was created in previous steps
When the user trigger SNS with DATA_FABRIC_DELETE event in '<tested-stack>-fulfill-sns.fifo':'{"default": "{\"eventType\": \"DATA_FABRIC_DELETE\", \"parameters\": {\"patientId\": \"83a2a278-ff45-e749-4968-4321Mbappe\"}, \"x-correlation-id\": \"f0955be5-bd4e-4b1d-88a5-cfc83a198620\"}"}'
When user wait for ETL process to finish
Then check the logs in '/aws/lambda/<tested-stack>-fulfill-EventHandler-zip' for the 'Processing record' has message with same correlation-id='f0955be5-bd4e-4b1d-88a5-cfc83a198620' and the process has completed
And check the patient 'OncoTestPatient, Mbappe' is not listed in the patients' list anymore
When the user submits GET request to retrieve the summary evaluation for patient '83a2a278-ff45-e749-4968-4321Mbappe' and 'custom' config type
Then the response contains empty list: '{"patient":"83a2a278-ff45-e749-4968-4321Mbappe","evaluated":[]}'
When the user query Clinical Calculator DynamoDB table '<tested-stack>-criteria-results' with parameters:
|patientID                              | configType | configID          |
|83a2a278-ff45-e749-4968-4321Mbappe     | custom     | recently-created  |
Then the list of objects contains patient '83a2a278-ff45-e749-4968-4321Mbappe', configTypeId in this format 'custom/<recently-created>
When the TTL was set to one minute expiration for the entry that returned from "<tested-stack>-criteria-results" DynamoDB table
And the time threshold is reached (TTL)
Then the criteria results in the system are deleted

@srs
@manual
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68696
@Description:The_patient_exists_in_the_system,Check_that_criteria_results_are_deleted_after_certain_amount_of_time_(TTL),Criteria_results_are_deleted_according_to_the_defined_TTL
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Clinical_Criteria_Calculator
Scenario: Check that criteria results are deleted after certain amount of time (TTL)
Given the following patient was uploaded to PDS: patients/OncoTeslaPatient_ModelY.json
And the new configuration uploaded with type of trial and configured for the patient 83a2a278-ff45-e749-4968-bd12345678cc
When the user submits GET request to retrieve the list of all criteria configurations for patient 83a2a278-ff45-e749-4968-bd12345678cc and config type of trial
Then the response contains the 'recently-created' configuration id
When the user query Clinical Calculator DynamoDB table '<tested-stack>-criteria-configurations' with parameters:
|patientID                              | configType | configID          |
|83a2a278-ff45-e749-4968-bd12345678cc   | trial      | recently-created  |
Then the query responds with configuration id 'recently-created'
When the user search in S3 Buckets for '<tested-stack>-criteriacalcul-criteriaconfigurationbu' bucket
And the user search for objects' folder with name of 'trial' that represents configuration type and then select it
Then the list of objects contains 'recently-created'.json
When the user query Clinical Calculator DynamoDB table '<tested-stack>-criteria-results' with parameters:
|patientID                              | configType | configID          |
|83a2a278-ff45-e749-4968-bd12345678cc   | trial      | recently-created  |
Then the list of objects contains patient '83a2a278-ff45-e749-4968-bd12345678cc', configTypeId in this format 'trial/<recently-created>
When the user submits DELETE request to delete configuration by 'recently-created' configID, 'trial' configType and patient '83a2a278-ff45-e749-4968-bd12345678cc'
Then the received HTTP status code is 200
And the response body contains the following text: '{"id":"<trialID-recently-created>","status":"deleted"}'
When the user submits GET request to retrieve 'recently-created' configID, 'trial' configType and patient '83a2a278-ff45-e749-4968-bd12345678cc'
Then the received HTTP status code is 404
And the response body contains the following text: '{"message":"File \"trial/<trialID-recently-created>.json\" not found"}'
When the user query Clinical Calculator DynamoDB table "<tested-stack>-criteria-configurations" with parameters:
|patientID                              | configType | configID          |
|83a2a278-ff45-e749-4968-bd12345678cc   | trial      | recently-created  |
Then the query respond with "Items returned (0)"
When the user search in S3 Buckets for '<tested-stack>-criteriacalcul-criteriaconfigurationbuc' bucket
And the user search for objects' folder with name of 'trial' that represents configuration type and then select it
Then the list with type of 'trial' configurations are displayed
When the user search in '<tested-stack>-criteriacalcul-criteriaconfigurationbuc' bucket, for json configuration of the 'recently-created'.json
Then no results were returned for the 'recently-created'.json
When the user query Clinical Calculator DynamoDB table '<tested-stack>-criteria-results' with parameters:
|patientID                              | configType | configID          |
|83a2a278-ff45-e749-4968-bd12345678cc   | trial      | recently-created  |
Then the list of objects contains patient '83a2a278-ff45-e749-4968-bd12345678cc', configTypeId in this format 'trial/<recently-created>
When the TTL was set to one minute expiration for the entry that returned from "<tested-stack>-criteria-results" DynamoDB table
And the time threshold is reached (TTL)
Then the criteria results in the system are deleted