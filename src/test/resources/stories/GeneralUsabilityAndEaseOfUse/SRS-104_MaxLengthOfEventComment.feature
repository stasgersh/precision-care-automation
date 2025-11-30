Feature: [SysRS-68629] Max Length Of Event Comment

Narrative: The system shall only accept maximum 2000-character long Event comments.


Background:
Given [API] the [DEFAULT-TEST-USER] user has no comments for the following event:
| patientID                            | root_resource_of_the_event                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/ef98eb70-cb59-4cd3-bc70-97bb04bdb4df |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded


@SRS-104.01
@srs
@Platform:WEB
@Category:Negative
@Traceability:SysRS-68629
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use
Scenario: It is not allowed to send an event comment with more than 2000 characters
Given the following event point is selected on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
And the comment input box is available on the sidebar
And comment is not available on the sidebar
And the send comment button is not enabled on the sidebar
When the user types the text from /comments/Comment_with_1499_characters.txt file into the comment input box on the sidebar
Then the comment character counter is not visible on the sidebar
And the send comment button is enabled on the sidebar
When the user appends the following text to the comment in the comment input box on the sidebar: "h"
Then the comment character counter is visible on the sidebar
And the comment character counter has te following text on the sidebar: "Character available: 500/2000"
And the send comment button is enabled on the sidebar
When the user types the text from /comments/Comment_with_1999_characters.txt file into the comment input box on the sidebar
Then the comment character counter is visible on the sidebar
And the comment character counter has te following text on the sidebar: "Character available: 1/2000"
And the send comment button is enabled on the sidebar
When the user appends the following text to the comment in the comment input box on the sidebar: "i"
Then the comment character counter is visible on the sidebar
And the comment character counter has te following text on the sidebar: "Character available: 0/2000"
And the send comment button is enabled on the sidebar
When the user appends the following text to the comment in the comment input box on the sidebar: "b"
Then the comment character counter is visible on the sidebar
And the comment character counter has te following text on the sidebar: "Character available: -1/2000"
And the send comment button is not enabled on the sidebar
And a warning is displayed for the comment with the following message on the sidebar: "Exceeded character limit. Reduce characters to 2000."
