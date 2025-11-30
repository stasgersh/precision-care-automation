Feature: [SysRS-68689] Service manual

Narrative: The system service manual for GEHC Service user shall be provided in English language and include the following information for the system:
             - Product name and version
             - Deployment/Installation
             - Dependencies
             - Configuration (including valuesets and system's configuration)
             - Troubleshooting and Monitoring details (alerts and metrics)
             - Uninstallation


@SRS-71.01
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68689
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario: Service manual includes installation, configuration and troubleshooting details
Given the user downloaded the following document: "CareIntellect for Oncology 1.0 Provisional Install and Service Guide for ME4" from MyWorkshop (DOC3155360)
When the user opens the saved document
Then the document is written in English language
And the following sections are present and not empty in the document:
| section_title                                           |
| 1. Overview                                             |
| 1.1 Purpose                                             |
| 1.2 Revision history                                    |
| 1.3 Abbreviations                                       |
| 1.4 References                                          |
| 1.5 General information                                 |
| 1.5.1 Product information                               |
| 1.5.2 Architecture overview                             |
| 2. Preinstallation (Day 0)                              |
| 2.1 Data collection and configuration                   |
| 2.1.1 Technical requirements for the on premise DCC     |
| 2.1.2 SMART integration                                 |
| Gather information from customer                        |
| Information to be provided to the customer              |
| 2.1.3 DICOM Viewer                                      |
| 2.1.4 Patient Identifier                                |
| 2.2 Prerequisite for cloud deployment                   |
| 2.2.1 Increase EC2-VPC Elastic IPs quota                |
| 2.2.2 Maximum integration timeout in milliseconds quota |
| 3. Installation (Day 1)                                 |
| 3.1 Installation Prerequisite                           |
| 3.1.0 Context                                           |
| 3.1.1 IDAM Configuration                                |
| 3.1.1.1 Create Application                              |
| 3.1.1.2 DPSA Subscription                               |
| 3.1.1.3 DHS EAT v1 Subscription                         |
| 3.1.1.4 PS Subscription                                 |
| 3.1.2  DPSA integration                                 |
| 3.1.2.1 PreReq                                          |
| 3.1.2.2 SNS integration                                 |
| 3.1.3 Application domain                                |
| 3.1.3.1 Certificate creation                            |
| 3.1.3.1.1 Collect the domains                           |
| 3.1.3.1.2 Adding domain records                         |
| 3.1.3.1.3 Certificate creation                          |
| 3.1.3.1.4 Certificate request                           |
| 3.1.3.1.5 Certificate received                          |
| 3.2 Installation                                        |
| 3.2.1 Account on-boarding                               |
| 3.2.1.1 ECR                                             |
| 3.2.1.2 EAT v2                                          |
| 3.2.1.3 COF account level                               |
| 3.2.2 Account compatibility                             |
| 3.2.3. Installation with CDK                            |
| 3.3. Post-installation actions                          |
| 3.3.1 DPSA - API GW                                     |
| 3.3.2 PS - API GW                                       |
| 3.3.3 Domain setup                                      |
| 3.3.4 Kloudfuse setup                                   |
| 3.3.4.1 Steps                                           |
| 3.3.4.2 Alert setup                                     |
| 3.3.5 IDAM config                                       |
| 3.3.6 Applications config                               |
| 3.3.6.1 Get service user token from IDAM                |
| 3.3.6.2 Get Config URL                                  |
| 3.3.6.3 Upload Valueset configuration                   |
| 3.3.6.4 Upload Models configuration                     |
| 3.3.6.5 Upload Patient Summary configuration            |
| 3.3.6.6 Upload Treatment Adherence configuration        |
| 3.3.6.7 Upload Treatment Eligibility configuration      |
| 3.3.6.8 Upload AI Criteria Matching data configuration  |
| 3.3.6.9 Upload Labels                                   |
| 3.3.6.10 Upload Representation                          |
| 3.3.6.11 Upload Fulfillment-query-set                   |
| 3.3.6.12 Upload App-preset                              |
| 3.4. SMART configuration                                |
| 3.4.1 Prerequisite                                      |
| 3.4.2 IDAM configuration                                |
| 3.4.3 Application configuration                         |
| 3.5 App configuration                                   |
| Configuration options                                   |
| 4. Maintenance (Day 2)                                  |
| 4.1 Uninstall via AWS console                           |
| 4.2 Troubleshooting                                     |
| 4.2.1 Alerts and metrics                                |
| 4.2.2 Cannot execute powershell scripts                 |
| 4.2.3 SMART launch                                      |
| 4.3. Kill switch for ME                                 |
| 4.3.1 Application removal from network                  |