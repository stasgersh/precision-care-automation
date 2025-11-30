@cte
@safety
Feature: [SysRS-68608] Retry

Narrative: The system shall provide the following, In case of a failure:
             1. Provide an error message in the log according to the REST API compliance requirement.
             2. Send back an error message to the requestor.
             3. Retry mechanism (3 times by default) shall be produced, in case of the following activities:
               - Service API call.
               - Network/ Interaction(request-response).
               - DHS-EAT (Audit logging).
           In case the last retry was not successful, the system shall create an error log, return internal server error code (500), and create an alert for the given error.
           The system shall not disclose the detailed stack trace of where the error occurred, and no PHI data shall be included in the error message.
@integration
@manual
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68608,SysRS-68449,SysRS-68662
@Description:<ul><li>the_criteria_evaluator_component_is_down</li><li>the_patient_has_criteria_results_in_the_system</li></ul>,Check_retry_with_less_than_3_attempts_of_failure,After_2_failed_retries_the_service_functions_as_expected_when_criteria_component_is_back_and_user_can_fetch_data_as_expected
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Clinical_Criteria_Calculator
Scenario: Check retry with 3 attempts of failure
Given the user opens AWS Console and opens Lambda Functions to find the '<tested-stack>-criteria-Evaluator-zip'
And the user updates the 'Environment variables' parameter 'FULFILLMENT_SERVICE_URL' to bad URL:'https://ukhoe4etmj-vpce-051fc6c3e1cd9e908.execute-api.us-east-1.amazonaws.com/v1/bad'
And save the changes
When the user open the log of Lambda '/aws/lambda/<tested-stack>-criteria-Evaluator-zip'
And click 'Start tailing'
And the user submits PUT request to ingest new criteria configuration to patient "8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5" and 'trial' config type
Then the received HTTP status code is 200
And the logs contains 3 attempts to send request to bad URL: "Executes queries against Fulfillment service located "https://ukhoe4etmj-vpce-051fc6c3e1cd9e908.execute-api.us-east-1.amazonaws.com/v1/bad/"
And the printed "Started run Fulfillment query" message 3 times
When the user updates the 'Environment variables' parameter 'FULFILLMENT_SERVICE_URL' to correct URL:'https://ukhoe4etmj-vpce-051fc6c3e1cd9e908.execute-api.us-east-1.amazonaws.com/v1/'
And save the changes
And the user open the log of Lambda '/aws/lambda/<tested-stack>-criteria-Evaluator-zip'
And click 'Start tailing'
When the user submits PUT request to ingest new criteria configuration to patient "8c6974e7-c101-4b6c-bcb3-fa2d33e26bf5" and 'trial' config type
Then the received HTTP status code is 200
And the logs no such message appeared: "Executes queries against Fulfillment service located "https://ukhoe4etmj-vpce-051fc6c3e1cd9e908.execute-api.us-east-1.amazonaws.com/v1/bad/"