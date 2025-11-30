Feature: [SysRS-70907] No hardcoded passwords

Narrative: The system shall not store unencrypted static authenticators (passwords).


@SRS-70907.01
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70907
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Password_Validation
Scenario: No hard-coded credentials stored in system code
When I open the "CWE Top 25" tab of "Security Reports" of the following portfolio in SonarQube:
| portfolio_name    |
| Onchron-portfolio |
Then the found "CWE-798 - Use of Hard-coded Credentials" vulnerability CWE is 0
