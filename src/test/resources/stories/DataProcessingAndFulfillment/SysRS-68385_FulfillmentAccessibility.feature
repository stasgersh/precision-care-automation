@safety
Feature: [SysRS-68385] Data processing – Accessibility

Narrative: The application shall only be accessible with a valid token issued by Cloud IDAM.


@sanity
@fulfillment
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68423,SysRS-68385
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Access_Control/Fulfillment
Scenario: The data model API is only accessible with a valid token issued by Cloud IDAM
Given the backend authentication token is valid
When the user requests the list of data models
Then the received HTTP status code is 200
Given the backend authentication token is invalidated
When the user creates new 'testBmi' data model
Then the received HTTP status code is 403
And the "Message" property in the response matches the value: User is not authorized to access this resource with an explicit deny in an identity-based policy
When the user requests the data model 'testBmi' by name
Then the received HTTP status code is 403
And the "Message" property in the response matches the value: User is not authorized to access this resource with an explicit deny in an identity-based policy
When the user requests the list of data models
Then the received HTTP status code is 403
And the "Message" property in the response matches the value: User is not authorized to access this resource with an explicit deny in an identity-based policy
When the user updates 'testBmi' data model
Then the received HTTP status code is 403
And the "Message" property in the response matches the value: User is not authorized to access this resource with an explicit deny in an identity-based policy
When the user deletes 'testBmi' data model
Then the received HTTP status code is 403
And the "Message" property in the response matches the value: User is not authorized to access this resource with an explicit deny in an identity-based policy


@srs
@fulfillment
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68417,SysRS-68385
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control/Fulfillment
Scenario: The data query API is only accessible with a valid token issued by Cloud IDAM
Given the backend authentication token is valid
When the user executes [testBmi] data query for single patient [testPatientIdAmit]
Then the received HTTP status code is 200
Given the backend authentication token is invalidated
When the user executes [testBmi] data query for single patient [testPatientIdAmit]
Then the received HTTP status code is 403
And the "Message" property in the response matches the value: User is not authorized to access this resource with an explicit deny in an identity-based policy
When the user executes data query for list of patients
Then the received HTTP status code is 403
And the "Message" property in the response matches the value: User is not authorized to access this resource with an explicit deny in an identity-based policy

@srs
@fulfillment
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-68417,SysRS-68385
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control/Fulfillment
Scenario: The value sets API is only accessible with a valid token issued by Cloud IDAM
Given the backend authentication token is valid
And the user remove value set name vs_1 when it exist in value set management
When the user creates the following value sets:
| items               |
| create_value_sets_1 |
Then the received HTTP status code is 200
When the user remove value set name vs_1
Then the received HTTP status code is 200
Given the backend authentication token is invalidated
When the user creates the following value sets:
| items               |
| create_value_sets_1 |
Then the received HTTP status code is 403
And the "Message" property in the response matches the value: User is not authorized to access this resource with an explicit deny in an identity-based policy
When the user requests for single value set: vs_1
Then the received HTTP status code is 403
And the "Message" property in the response matches the value: User is not authorized to access this resource with an explicit deny in an identity-based policy
When the user requests the list of all value sets
Then the received HTTP status code is 403
And the "Message" property in the response matches the value: User is not authorized to access this resource with an explicit deny in an identity-based policy
When the user update value set vs_1 with body: vs_1_update
Then the received HTTP status code is 403
And the "Message" property in the response matches the value: User is not authorized to access this resource with an explicit deny in an identity-based policy
When the user remove value set name vs_1
Then the received HTTP status code is 403
And the "Message" property in the response matches the value: User is not authorized to access this resource with an explicit deny in an identity-based policy

@srs
@fulfillment
@Platform:WEB
@Category:Negative
@Traceability:SysRS-68418
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Data_processing_and_Fulfillment
@manual
Scenario: Session terminates after a defined timeout
Given a session timeout set for 3 minutes
When the user executes [data_query_bmi] data query for single patient [temp_patientID]
Then the received HTTP status code is 200
When session times out
And the user executes [data_query_bmi] data query for single patient [temp_patientID]
Then the received HTTP status code is 403
And the "Message" property in the response matches the value: User is not authorized to access this resource with an explicit deny in an identity-based policy