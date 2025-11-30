@safety
Feature: [SysRS-70905] URL validation

Narrative: The system shall validate URLs against a valid scheme.


@SRS-70905.01
@srs
@manual
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-70905
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario: System validates URLs against a valid scheme
Given the user logged in to the application
When the user opens the following URL: <app_base_url>/patient/6d2d9e07-4a24-7525-4661-f59ccabb02a7/timeline/weight:cfd91524-c9e5-4877-a114-a6a448f0f0e2
Then the patient timeline of "OncoTestPatient, Freya" is loaded
And the sidebar header contains the following data:
| title               |
| Body Weight 61.8 kg |

@SRS-70905.02
@srs
@manual
@Platform:Rest_API
@Category:Negative
@Traceability:SysRS-70905
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario Outline: System rejects URLs with unsupported schemes
Given the user logged in to the application
When the user opens the following URL: <url>
Then the application does not open
Examples:
| url                                                                                                                         |
| htp://<app_base_url>/patient/a10e48ab-b927-9ee1-84a4-41638a84e05a/summary                                                   |
| http$://<app_base_url>/patient/a10e48ab-b927-9ee1-84a4-41638a84e05a/summary                                                 |
| https://<app_base_url>/patient/a10e48ab-b927-9ee1-84a4-41638a84e05a/summary?param=%3Cjavascript%3Aalert(document.cookie%3E) |
| https://<app_base_url>:8080                                                                                                 |
