Feature: [SysRS-68577] Max Length Of Patient Comment

Narrative: The application shall only accept maximum 2000 character long Patient comments.


Background:
Given [API] the [DEFAULT-TEST-USER] user has no comments for the following patients:
| patientID                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected


@srs
@SRS-99.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68577
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Comment_view
Scenario: It is not allowed to send a patient comment with more than 2000 characters
Given the user navigated to the "Comments" view
Then the comment input box is available on the Comments view
And comment is not available on the Comments view
And the send comment button is not enabled on the Comments view
When the user types the text from /comments/Comment_with_1499_characters.txt file into the comment input box on the Comments view
Then the comment character counter is not visible on the Comments view
And the send comment button is enabled on the Comments view
When the user appends the following text to the comment in the comment input box on the Comments view: "h"
Then the comment character counter is visible on the Comments view
And the comment character counter has te following text on the Comments view: "Character available: 500/2000"
And the send comment button is enabled on the Comments view
When the user types the text from /comments/Comment_with_1999_characters.txt file into the comment input box on the Comments view
Then the comment character counter is visible on the Comments view
And the comment character counter has te following text on the Comments view: "Character available: 1/2000"
And the send comment button is enabled on the Comments view
When the user appends the following text to the comment in the comment input box on the Comments view: "i"
Then the comment character counter is visible on the Comments view
And the comment character counter has te following text on the Comments view: "Character available: 0/2000"
And the send comment button is enabled on the Comments view
When the user appends the following text to the comment in the comment input box on the Comments view: "b"
Then the comment character counter is visible on the Comments view
And the comment character counter has te following text on the Comments view: "Character available: -1/2000"
And the send comment button is not enabled on the Comments view
And a warning is displayed for the comment with the following message on the Comments view: "Exceeded character limit. Reduce characters to 2000."
