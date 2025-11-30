Feature: [SysRS-70904] Obscured password

Narrative: The system shall obscure the password field in the login screen.


@SRS-70904.01
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70904
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Password_Validation
Scenario Outline: Password field obscures input on the login screen
Given the user is navigated to the Login screen
When the user enters <text> in to the password field
Then the password field obscures the entered text
When the user pastes <text> into the password field
Then the password field obscures the entered text
Examples:
| text  |
| Test  |
| 12345 |
| &@$%+ |
