Feature: [SysRS-70902] HTTPS enforcement

Narrative: The system shall enforce the use of HTTPS on client browsers.


@SRS-70902.01
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70902
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario Outline: System enforces HTTPS for client browsers
Given the user opens <browser> on my local machine
And opened the developer tools of the browser
And navigated to "Network" tab in the developer tools
And the user logged in to the application
When the user opens the following link <link>
Then browser redirects the the URL automatically to use HTTPS protocol
And the <link> is loaded for "OncoTestPatient, Juno"
When inspecting the request headers
Then the "Strict-Transport-Security" header presents and configured with a max-age value
When navigating to "Security" tab in the developer tools
Then it states that the connection is secure and the certificate is valid and trusted

Examples:
| browser        | link                                                                                     |
| Google Chrome  | <app_base_url>/patient/a10e48ab-b927-9ee1-84a4-41638a84e05a/summary  |
| Microsoft Edge | <app_base_url>/patient/a10e48ab-b927-9ee1-84a4-41638a84e05a/comments |
