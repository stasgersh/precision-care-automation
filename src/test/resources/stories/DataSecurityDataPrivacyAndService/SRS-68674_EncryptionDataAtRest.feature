@safety
Feature: [SysRS-68674] Encryption - data at rest

Narrative: The system shall use encryption of data at rest (include audit trails).


@SRS-68674.01
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68674
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Encryption
Scenario: Verify data at rest is encrypted
Given the user has access to the AWS Management Console
And the user is logged in with appropriate permissions
When the user checks the following AWS services which stores data:
| service_name  |
| DynamoDB      |
| S3 bucket     |
| SQS           |
| SNS           |
Then the user sees that encryption at rest is enabled using Amazon managed keys
