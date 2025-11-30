@cte
Feature: [SysRS-68608] Non-Functional - Error handling and retry

@integration
@manual
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68608,SysRS-68472,SysRS-68663,SysRS-68664
@Description:<ul><li>the_Trial_Eligibility_component_is_down</li></ul>,Check_server_throws_500_error_in_case_last_retry_failed,After_3_retries_the_following_happens:<ul><li>proper_error_message_sent_back_to_the_requester</li><li>log_error_is_printed_with_500_error_status</li></ul>
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Clinical_Trial_Eligibility
Scenario: Check server throws 500 error in case last retry failed (Trial Eligibility)
When the user submits PUT request to ingest new trial configuration to patient "OncoTestPatient, Calculator"
And the user submits GET request to retrieve the list of all criteria configurations for patient 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 and config type of trial
Then the received HTTP status code is 200
And the response contains recently created configuration with trial and generated configurationID
Given in AWS console,Lambda '<tested-stack>-criteria-ContentManagement-zip'
When the Clinical Calculator Lambda is 'Throttled' (i.e., 'Throttle' was changed to 0)
Then the message displayed "Function has been throttled."
When the user open the log of Lambda '/aws/lambda/<tested-stack>-eligibility-Configuration-zip'
And the user submits Eligbility service API GET request to "/config/{configType}/{configId}", to fetch configurations by:
| config_type | config_id                   | query_string |
| trial       | <generated configurationID> | patient=8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 |
Then the received HTTP status code is 500
And the response body contains the following text: '{"message":"Failed to get {configID}"}'
When the user submits PUT request to with new_configID to the existing patientID: 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 with following details:
| config_id   | config_type  | trial_configuration_file    |
| trial       | new_configID | patient=8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 |
Then the received HTTP status code is 500
And the response body contains the following text: '{"message":"Failed to update [new_configID]"}'
And a log error is printed with 500 error status in cloudWatch logs
And check 3 retries of 'Started api_call' with debug 'Executes queries against Criteria service' and 'Failed to run call'
When in AWS console,Lambda '<tested-stack>-criteria-ContentManagement-zip'
And the Clinical Calculator Lambda is turned back On by setting Concurrency to "Use unreserved account concurrency" and saved it
And user submits Eligbility service API GET request to "/config/{configType}/{configId}", to fetch configurations by:
| config_type | config_id                   | query_string |
| trial       | <generated configurationID> | patient=8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 |
Then the received HTTP status code is 200
And the response body contains the <generated configurationID>

@integration
@manual
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68608,SysRS-68472,SysRS-68663,SysRS-68664
@Description:<ul><li>the_Trial_Eligibility_component_is_down</li></ul>,Check_retry_with_less_than_3_attempts_of_failure,After_2_failed_retries_the_service_functions_as_expected_when_Trial_Eligibility_component_is_back_and_user_can_fetch_data_as_expected
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Clinical_Trial_Eligibility
Scenario: Check retry with less than 3 attempts of failure (Trial_Eligibility)
When the user submits PUT request to ingest new trial configuration to patient "OncoTestPatient, Calculator"
And the user submits GET request to retrieve the list of all criteria configurations for patient 8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 and config type of trial
Then the received HTTP status code is 200
And the response contains recently created configuration with trial and generated configurationID
When in AWS console,Lambda '<tested-stack>-criteria-ContentManagement-zip'
And the Clinical Calculator Lambda is 'Throttled' (i.e., 'Throttle' was changed to 0)
Then the message displayed "Function has been throttled."
When the user open the log of Lambda '/aws/lambda/<tested-stack>-eligibility-Configuration-zip'
And click 'Start tailing'
And the user submits Eligbility service API GET request to "/config/{configType}/{configId}", to fetch configurations by:
| config_type | config_id                   | query_string |
| trial       | <generated configurationID> | patient=8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 |
Then the received HTTP status code is 500
And the response body contains the following text: '{"message":"Internal Server Error"}'
And a log error is printed with 500 error status in cloudWatch logs
Then check 2 retries of 'Started api_call' with debug 'Executes queries against Criteria service' and 'Failed to run call'
When in AWS console,Lambda '<tested-stack>-criteria-ContentManagement-zip'
And the Clinical Calculator Lambda is turned back On by setting Concurrency to "Use unreserved account concurrency" and saved it
And user submits Eligbility service API GET request to "/config/{configType}/{configId}", to fetch configurations by:
| config_type | config_id                   | query_string |
| trial       | <generated configurationID> | patient=8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5 |
Then the received HTTP status code is 200
And the response body contains the <generated configurationID>