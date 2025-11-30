Feature: [SysRS-68690] Download Logs

Narrative: The system shall provide a way to access and download logs from the system for investigation purposes (by using Central Observability Framework).


@SRS-68690.01
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68690
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Monitoring
Scenario: Download CareIntellect for Oncology application logs from the system for investigation purposes
Given I logged in to CareIntellect for Oncology application as [DEFAULT-TEST-USER]
And I selected the "OncoTestPatient, Juno" patient
And I navigated to the "Timeline" view
And I turn on Treatment adherence mode
And I switch back to Default mode
And I logged in to Kloudfuse application
And I navigated to the "Logs" menu
And I filtered for the CareIntellect for Oncology deployment which is currently being tested
When I click on the "Download" button
And I select 'Download as JSON' option
Then a .json file is downloaded
When I open the file
Then I can find logs about the following actions:
| action                           | log_partial_detail                                                                                                                                   |
| get patient status               | "method":"GET","url":"/patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/status"                                                                         |
| get patient banner               | "method":"GET","url":"/patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/banner?version=<ANY_VALUE>"                                                     |
| get patient summary              | "method":"GET","url":"/patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/summary?version=<ANY_VALUE>&usingAi=<ANY_VALUE>&eligibilityVersion=<ANY_VALUE>" |
| get ai overview                  | "method":"GET","url":"/patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/ai-overview?version=<ANY_VALUE>                                                 |
| get patient timeline             | "method":"GET","url":"/patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/timeline?version=<ANY_VALUE>"                                                   |
| get treatment adherence timeline | "method":"GET","url":"/patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/timeline?version=<ANY_VALUE>&adherenceId=<ANY_VALUE>"                           |
