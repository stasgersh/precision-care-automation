Feature: [SysRS-68696] Delete patient data

Narrative: The system shall delete all patient’s data stored in the system (comments as well) whenever the patient is deleted from PDSc.


Background:
Given the following patient was uploaded to PDS: patients/OncoTestPatient_Delete.json
And comments were added on the Timeline to the following events by [DEFAULT-TEST-USER] as:
| swimlane   | event_label                            | comment                    |
| Encounters | Body Weight 31 kg                      | This is an event comment 1 |
| Imaging    | CT THORAX ABDOMEN PELVIS WITH CONTRAST | This is an event comment 2 |
And "Test comment 3" comment was added on "Comments" view
And the following comments are available on the Comments view:
| author              | date_and_time | content                    | badge            | has_view_on_timeline_link |
| [DEFAULT-TEST-USER] | <ANY_VALUE>   | This is an event comment 1 | Timeline comment | yes                       |
| [DEFAULT-TEST-USER] | <ANY_VALUE>   | This is an event comment 2 | Timeline comment | yes                       |
| [DEFAULT-TEST-USER] | <ANY_VALUE>   | Test comment 3             | <N/A>            | no                        |
And labels were added to the following events as:
| swimlane   | event_label                            | labels                         |
| Encounters | Body Weight 41 kg                      | Progression; Molecular testing |
| Imaging    | CT THORAX ABDOMEN PELVIS WITH CONTRAST | To be reviewed                 |
And the following events were marked as important:
| swimlane   | event_label       |
| Encounters | Body Weight 35 kg |

@SRS-159.01
@integration
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68696
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Data_Security_Data_Privacy_And_Service
Scenario: Local data was restored after re-uploading the patient before the patient delete chron job has not run
Given I deleted the patient from DPSA via REST
And I triggered the deletion of "OncoTestPatient, Delete" patient in fulfillment via State machines (See 'Patient deletion in fulfillment' in README)
When the [DEFAULT-TEST-USER] user logs in to OncoCare application
And I try to find the "OncoTestPatient, Delete" in the patient list
Then patient list is empty
And the following text is displayed in the empty patient list: "No patient found"
When I upload the following patient to DPSA: patients/OncoTestPatient_Delete.json
Then the upload was finished with status code: 200
When I refresh the browser
And I open the "OncoTestPatient, Delete" patient
And I navigate to "Comments" view
Then the following comments are available on the Comments view:
| author              | date_and_time | content                    | badge            | has_view_on_timeline_link |
| [DEFAULT-TEST-USER] | <ANY_VALUE>   | This is an event comment 1 | Timeline comment | yes                       |
| [DEFAULT-TEST-USER] | <ANY_VALUE>   | This is an event comment 2 | Timeline comment | yes                       |
| [DEFAULT-TEST-USER] | <ANY_VALUE>   | Test comment 3             | <N/A>            | no                        |
When I navigate to "Timeline" view
Then labels are displayed at following events as:
| swimlane   | event_label                            | labels                         |
| Encounters | Body Weight 41 kg                      | Progression; Molecular testing |
| Imaging    | CT THORAX ABDOMEN PELVIS WITH CONTRAST | CT; 1 comment; To be reviewed  |
And the following events were marked as important:
| swimlane   | event_label       |
| Encounters | Body Weight 35 kg |

@SRS-159.02
@integration
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68696,SysRS-68572
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Data_Security_Data_Privacy_And_Service
Scenario: Local data was not restored after the patient deletion chron job ran at midnight
Given I deleted the patient from DPSA via REST
When I navigate to DynamoDB / Explore items / "<target_env>-representation-comments" table
And I run a filter with the following settings:
| attribute_name      | condition | value                                |
| identifier (String) | Contains  | 66812e9a-96ae-4b59-85fe-43872df6bd78 |
Then I following items are displayed in the returned list:
| identifier (String)                                            | commentId (String) | content             | created                   | eventId                                            |
| <DEFAULT_TEST_USER_EMAIL>#66812e9a-96ae-4b59-85fe-43872df6bd78 | <unique_ID>        | <content_in_BASE64> | <timestamp_creation_date> | weight:dd4e5141-fc1c-4da0-66e7-9fa5098ea8c5        |
| <DEFAULT_TEST_USER_EMAIL>#66812e9a-96ae-4b59-85fe-43872df6bd78 | <unique_ID>        | <content_in_BASE64> | <timestamp_creation_date> | imagingReport:4aeddf73-0851-4d79-b9ed-8dbb48487922 |
| <DEFAULT_TEST_USER_EMAIL>#66812e9a-96ae-4b59-85fe-43872df6bd78 | <unique_ID>        | <content_in_BASE64> | <timestamp_creation_date> | <EMPTY>                                            |
When I open the "<target_env>-representation-settings" table
And I run a filter with the following settings:
| attribute_name        | condition | value                                |
| settingsType (String) | Contains  | 66812e9a-96ae-4b59-85fe-43872df6bd78 |
Then I following items are displayed in the returned list:
| userId (String)           | settingsType (String)                                                                         | settings                                                                                                                                                                                                                            |
| <DEFAULT_TEST_USER_EMAIL> | EVENT#66812e9a-96ae-4b59-85fe-43872df6bd78#imagingReport:4aeddf73-0851-4d79-b9ed-8dbb48487922 | { "important" : { "BOOL" : false }, "labels" : { "L" : [ { "M" : { "paletteColor" : { "N" : "7" }, "text" : { "S" : "To Be Reviewed" } } } ] } }                                                                                    |
| <DEFAULT_TEST_USER_EMAIL> | EVENT#66812e9a-96ae-4b59-85fe-43872df6bd78#weight:66222011-5c80-4a0b-b3f0-b8a434e61481        | { "important" : { "BOOL" : false }, "labels" : { "L" : [ { "M" : { "paletteColor" : { "N" : "4" }, "text" : { "S" : "Progression" } } }, { "M" : { "paletteColor" : { "N" : "3" }, "text" : { "S" : "Molecular testing" } } } ] } } |
| <DEFAULT_TEST_USER_EMAIL> | EVENT#66812e9a-96ae-4b59-85fe-43872df6bd78#weight:66ae8b8d-9c3f-4317-9db2-e268d71bcd1c        | { "important" : { "BOOL" : true }, "labels" : { "L" : [  ] } }                                                                                                                                                                      |
When I wait for <target_env>-fulfill-patient-deletion to trigger the patient deletion at midnight
And the [DEFAULT-TEST-USER] user logs in to OncoCare application
And I try to find the "OncoTestPatient, Delete" in the patient list
Then patient list is empty
And the following text is displayed in the empty patient list: "No patient found"
When I navigate to DynamoDB / Explore items / "<target_env>-representation-comments" table
And I run a filter with the following settings:
| attribute_name      | condition | value                                |
| identifier (String) | Contains  | 66812e9a-96ae-4b59-85fe-43872df6bd78 |
Then the query returned with 0 items and the following text: "The query did not return any results."
When I open the "<target_env>-representation-settings" table
And I run a filter with the following settings:
| attribute_name        | condition | value                                |
| settingsType (String) | Contains  | 66812e9a-96ae-4b59-85fe-43872df6bd78 |
Then the query returned with 0 items and the following text: "The query did not return any results."
When I open the "<target_env>-criteria-results" table
And I run a filter with the following settings:
| attribute_name | condition | value                                |
| patient        | Contains  | 66812e9a-96ae-4b59-85fe-43872df6bd78 |
Then the query returned with 0 items and the following text: "The query did not return any results."
When I open the "<target_env>-eligibility-results" table
And I run a filter with the following settings:
| attribute_name | condition | value                                |
| patientId      | Contains  | 66812e9a-96ae-4b59-85fe-43872df6bd78 |
Then the query returned with 0 items and the following text: "The query did not return any results."
When I open the "<target_env>-fulfill-patient-versions-definitions" table
And I run a filter with the following settings:
| attribute_name | condition | value                                |
| patientId      | Contains  | 66812e9a-96ae-4b59-85fe-43872df6bd78 |
Then the query returned with 0 items and the following text: "The query did not return any results."
When I open the "<target_env>-representation-settings" table
And I run a filter with the following settings:
| attribute_name | condition | value                                |
| settingsType   | Contains  | 66812e9a-96ae-4b59-85fe-43872df6bd78 |
Then the query returned with 0 items and the following text: "The query did not return any results."
When I open the "<target_env>-summary-configuration" table
And I run a filter with the following settings:
| attribute_name | condition | value                                |
| settingsType   | Contains  | 66812e9a-96ae-4b59-85fe-43872df6bd78 |
Then the query returned with 0 items and the following text: "The query did not return any results."
