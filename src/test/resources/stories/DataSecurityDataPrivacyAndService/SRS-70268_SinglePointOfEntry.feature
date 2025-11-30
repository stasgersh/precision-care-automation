Feature: [SysRS-70268] Single point of entry

Narrative: All external traffic coming into the system (Precision Insights) shall be controlled, filtered, and monitored.

@SRS-70268.01
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70268
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario: Review authorizer configurations to ensure external traffic is regulated
Given the 'precision-care-infra' project is opened
When I review WAF configuration with a SME as follows:
|file path                           |
| lib\observability.ts               |
| lib\services\representation\waf.ts |
| lib\base\waf-rules.ts              |
And I review API access configurations with a SME as follows:
|file path                     |
| lib\authorization-policy.ts  |
And I review API authorizer configurations with a SME as follows:
|file path                  |
| lib\authorization-policy.ts  |
Then incoming external traffic is regulated based on the source code review
