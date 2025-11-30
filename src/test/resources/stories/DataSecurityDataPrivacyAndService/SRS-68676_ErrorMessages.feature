Feature: [SysRS-68676] Error messages - no data revealed

Narrative: The system shall generate error messages that do not reveal design nor implementation details


@SRS-68676.01
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68676
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario: Error messages do not provide sensitive information
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Franklin" patient is selected
And the "Timeline" view is selected
And the user opened Chrome Developer Tools
And the user navigated to the "Network" tab in Developer Tools
When the user adds the follow patterns to "Network request blocking" tool on Developer Tools:
| text_pattern    |
| summary         |
| timeline        |
| medical-history |
| trends          |
| comments        |
| banner          |
And reloads the page
Then the following error message is displayed on the page "Failed to load patient data."
And the error message does not reveal details like the database name, SQL queries, or server configurations
When the user disables all patterns in "Network request blocking" tool on Developer Tools
And navigates to Comments view
And the user types a comment to the field
And enables only the "comments" pattern in "Network request blocking" tool on Developer Tools
And clicks on the "Save" button
Then a failure notification is displayed
And the error message does not reveal details like the database name, SQL queries, or server configurations
