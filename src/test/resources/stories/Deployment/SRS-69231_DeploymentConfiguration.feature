Feature: [SysRS-69231] Deployment Configuration

Narrative: The system shall be configurable as documented in Service Guide with the following parameters as part of the deployment or during maintenance.

@manual
@SRS-69231.01
@non-functional
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69231
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Deployment
Scenario: System configuration is available at deployment time
Given the deployer tagged the latest version of 'precision-care-infra' branch before starting the deployment as <LATEST_TAG>
And I downloaded the "CareIntellect for Oncology 1.0 Provisional Install and Service Guide for ME4" from MyWorkshop (DOC3155360)
And the deployer deployed the system to the <TARGET_ENV>
And the deployer has logged the CDK version at accessible location (e.g: Confluence)
And the deployer has logged the CDK deploy command at accessible location (e.g: Confluence)
When I check the shared deployment information
Then I can see that the used CDK.json file or the deployment command contains the following parameters with configured data:
| parameter                      |
| security_oauth2_endpoint       |
| security_oauth2_client_id      |
| IdamClientSecret               |
| tenant_id                      |
| env_name                       |
| env_type                       |
| kloudfuse_segment              |
| kloudfuse_application          |
| kloudfuse_tenant               |
| kloudfuse_endpoint_logs        |
| kloudfuse_endpoint_metrics     |
| kloudfuse_endpoint_access_key  |
| kloudfuse_scraper_role_arn     |
| pds_sns_topic_arn              |
| pds_rest_api_id                |
| pds_client_id                  |
| app_domain_enabled             |
| app_domain                     |

@manual
@SRS-69231.02
@non-functional
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69231
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Deployment
Scenario: System is configurable during maintenance
Given the target system has been deployed to an environment
And the AWS CLI was installed and configured (see AWS CLI install in README.MD)
And the CDK deployment information (tag number of cdk.json, CDK deploy command with additional parameters) was shared
And the notification banner displays the "Not for clinical use" message
When I redeploy the cdk stack with the following modification (see "Deployment update from AWS CLI" in README)
| parameter_to_modify           | new value                                                                         |
| frontend_system_notifications | [{ "message": "Configuration test banner message", "type": "high-severity-alt" }] |
Then the CDK stack is successfully redeployed
When I log in to the application
Then the notification banner displays the "Configuration test banner message" message
When I revert the changes in the cdk.json file
And I redeploy the cdk stack (see "Deployment update from AWS CLI" in README)
Then the CDK stack is successfully redeployed
When I log in to the application
Then the notification banner displays the "Not for clinical use" message

@manual
@SRS-69231.03
@non-functional
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69231,SysRS-68691,SysRS-103330
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Deployment
Scenario: Analytics log collection can be enabled/disabled with predefined version in deployment configuration
Given the target system has been deployed to an environment
And the AWS CLI was installed and configured (see AWS CLI install in README.MD)
And the CDK deployment information (tag number of cdk.json, CDK deploy command with additional parameters) was shared
And analytics log collection is enabled for the environment being tested
And the Gainsight script version is noted as <deployed_version> from CDK deployment information
When I redeploy the cdk stack with the following modification (see "Deployment update from AWS CLI" in README)
| parameter_to_modify           | new value |
| CDK_CONTEXT_GAINSIGHT_ENABLED | false     |
Then the CDK stack is successfully redeployed
When I open CareIntellect for Oncology application
And I open the Network panel in DevTools
And I log in with [DEFAULT-TEST-USER] user
Then the following '/command' request is NOT available on the Network panel:
| et_query_parameter  | ho_query_parameter  | ep_query_parameter_detail |
| identify            | <environment_url>   | "userHash"                |
When I select "OncoTestPatient, Juno" patient
And I navigate to the Timeline
Then I see no '/command' request has been sent to Gainsight
When I revert the changes in the cdk.json file
And I redeploy the cdk stack (see "Deployment update from AWS CLI" in README)
Then the CDK stack is successfully redeployed
When I open CareIntellect for Oncology application
And I open the Network panel in DevTools
And I log in with [DEFAULT-TEST-USER] user
Then the following '/command' request is available on the Network panel:
| et_query_parameter  | ho_query_parameter  | ep_query_parameter_detail |
| identify            | <environment_url>   | "userHash"                |
Then the following '/aptrinsic.js' request is available on the Network panel:
| request_url_detail                          |
| /web-sdk/<actual_version>/api/aptrinsic.js  |
And the previously noted <deployed_version> is equal to the <actual_version>