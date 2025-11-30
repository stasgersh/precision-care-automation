Feature: [SysRS-68677] Least privilege access control

Narrative: The system shall employ the principle of least privilege across all sub-components/services/flows


@SRS-68677.01
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68677
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Access_Control
Scenario: Review policies and role mappings to ensure system employs least privilege access for sub-components/services/flows
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
And I review API access policies (AVP) with a SME as follows:
| file path                    |
| lib\authorization-policy.ts  |
And I review User role/scope mappings with a SME as follows:
| file path                         |
| adherence\authorization.ts        |
| ai\authorization.ts               |
| criteria\authorization.ts         |
| eligibility\authorization.ts      |
| fulfillment\authorization.ts      |
| representation\api.ts             |
| summary\authorization.ts          |
| token-mediating\authorization.ts  |
And I review Scope lists for token requests with a SME as follows:
| file path                    |
| lib\authorization-policy.ts  |
Then system employs least privilege access for sub-components/services/flows based on the source code review
