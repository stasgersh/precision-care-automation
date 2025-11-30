@safety
Feature: [SysRS-68671] TTL on PHI

Narrative: The system shall not hold PHI data longer than the time necessary for the purpose of the use by the system. (Temporary use scenarios only)

Background:
Given the user downloaded the CareIntellect Security dTDR (DOC2968229) from MyWorkshop
And the user opens the dTDR document at the 'Deletion of data' section
And the user logs into the AWS Management Console

@SRS-68671.01
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68671
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/TTL
Scenario: Review TTL configuration for DynamoDB service
When the user navigates to the DynamoDB service
And review the TTL configuration for the following tables for the environment being tested:
| table_name                            |
| fulfill-patient-versions-definitions  |
| eligibility-results                   |
| criteria-results                      |
| summary-configuration                 |
| tokenmed-configuration                |
Then the TTL is enabled for the DynamoDB tables
And the TTL configuration in AWS console matches the configuration listed in the dTDR document

@SRS-68671.02
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68671
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/TTL
Scenario: Review TTL configuration for SQS service
When the user navigates to the SQS service
And review the TTL configuration for the following queues for the environment being tested:
| queue_name                   |
| adherence-queue              |
| criteria-events-queue        |
| cte-ai-requests              |
| fulfill-events-queue         |
| eligibility-events-queue     |
| representation-cache-events  |
| representation-events-queue  |
| summary-events               |
Then TTL is enabled for the SQS queues
And the TTL configuration in AWS console matches the configuration listed in the dTDR document

@SRS-68671.03
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68671
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/TTL
Scenario: Review TTL configuration for SNS service
When the user navigates to the SNS service
And review the TTL configuration for the following topics for the environment being tested:
| topic_name  |
| criteria    |
| fulfill     |
| cte-ai      |
| eligibility |
Then TTL is enabled for the SNS topics
And the TTL configuration in AWS console matches the configuration listed in the dTDR document

@SRS-68671.04
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68671
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/TTL
Scenario: Review TTL configuration for S3 service
When the user navigates to the S3 service
And review the TTL configuration for the following S3 buckets for the environment being tested:
| bucket_name            |
| evaluatedresultsbucket |
Then the TTL is enabled for the S3 buckets
And the TTL configuration in AWS console matches the configuration listed in the dTDR document
