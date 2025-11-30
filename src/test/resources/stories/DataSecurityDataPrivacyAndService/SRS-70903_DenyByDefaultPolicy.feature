Feature: [SysRS-70903] - Deny by Default/Allow by Exception

Narrative: The system shall employ a policy of deny by default and allow by exception for all connections.

@SRS-70903.01
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70903
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario: Review IAM policies to ensure system employs 'deny by default and allow by exception' policy
Given the 'precision-care-infra' project is opened
When I review IAM policies with a SME as follows:
| file path                         |
| adherence\authorization.ts        |
| ai\authorization.ts               |
| criteria\authorization.ts         |
| eligibility\authorization.ts      |
| fulfillment\authorization.ts      |
| representation\api.ts             |
| summary\authorization.ts          |
| token-mediating\authorization.ts  |
Then system employs 'deny by default and allow by exception' policy based on the source code review
