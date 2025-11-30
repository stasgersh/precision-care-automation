Feature: [SysRS-68452] Treatment Response Page Navigation

Narrative: The system shall provide an option to close “Response” view page and navigate back to the summary page.


@sanity
@edge
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68452,SysRS-69203
@SRS-173.01
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Treatment_Response_View
Scenario: User can navigate back and forth between "Treatment and Response" view and "Summary" page
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the patient summary view is loaded
When the user clicks "Status and response" button
Then the Treatment and response view is loaded
And the "Response" tab is visible on the navigation bar
And the "Oncology note - most recent" treatment card contains the following data:
| data_type   | data                        |
| CARD_TITLE  | Oncology note - most recent |
| DATE        | May 31, 2017                |
| NORMAL_TEXT | Progress Notes              |
When the user clicks the "X" icon of the Response tab on the navigation bar
Then the patient summary view is loaded
And the "Response" tab is not visible on the navigation bar
