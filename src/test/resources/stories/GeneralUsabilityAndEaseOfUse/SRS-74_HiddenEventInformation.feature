@safety
Feature: [SysRS-68648] Hidden Event Information

Narrative: The system users shall be informed when any events are hidden.


@sanity
@edge
@SRS-74.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68648
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use
Scenario: Filter counter and filter label are displayed if any event is hidden
Given [API] the following event was marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/effb5f0c-038b-453b-8a5b-555adff35547 |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
When the user set the following timeline filter option:
| filter_group | checkbox_name | selected |
| Labels       | Important     | false    |
Then the filter counter icon on the Filter button is not displayed
And filter badge is not available in the filter list on the timeline toolbar
When the user set the following timeline filter option:
| filter_group | checkbox_name | selected |
| Labels       | Important     | true     |
Then the filter counter icon on the Filter button is displayed with number: 1
And the following filter badge is available in the filter list on the timeline toolbar:
| filter    |
| Important |
When the user set the following timeline filter option:
| filter_group | checkbox_name | selected |
| Labels       | Important     | false    |
Then the filter counter icon on the Filter button is not displayed
And filter badge is not available in the filter list on the timeline toolbar

@SRS-74.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68648
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Hidden_Event_Information
Scenario: Filter counter is displayed if any event is hidden - Vertical toolbar
Given [API] the following event was marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/effb5f0c-038b-453b-8a5b-555adff35547 |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
And the browser size is set to: width=1680, height=768
When the user set the following timeline filter options on the vertical toolbar:
| filter_group | checkbox_name | selected |
| Labels       | Important     | true     |
Then the filter counter icon on the Filter button on the vertical toolbar is displayed with number: 1
And the filter counter color icon on the Filter button is displayed with color:purple
When the user set the following timeline filter options on the vertical toolbar:
| filter_group | checkbox_name | selected |
| Labels       | Important     | false    |
Then the filter counter icon on the Filter button is not displayed on the vertical toolbar

@sanity
@SRS-74.03
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68648
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use
Scenario: Filter counter and filter label are displayed if more event filter options are applied at the same time
Given [API] the following events were marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/347b7a4b-ab75-4691-a8f5-d2f498bb6bed |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/8407cbdb-1ae3-4a86-b4d6-6e1778a67519 |
And [API] the below event comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | root_resource_of_the_event                            | comment_text                |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/ef98eb70-cb59-4cd3-bc70-97bb04bdb4df | filter union test comment   |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/8407cbdb-1ae3-4a86-b4d6-6e1778a67519 | filter union test comment 2 |
And [API] labels are added to the following events by the [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            | labels                      |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc | To Be Reviewed, Progression |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/8407cbdb-1ae3-4a86-b4d6-6e1778a67519 | To Be Reviewed              |
And [API] the following event was not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/ef98eb70-cb59-4cd3-bc70-97bb04bdb4df |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following event:
| patientID                            | root_resource_of_the_event                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/347b7a4b-ab75-4691-a8f5-d2f498bb6bed |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/347b7a4b-ab75-4691-a8f5-d2f498bb6bed |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/ef98eb70-cb59-4cd3-bc70-97bb04bdb4df |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the following events are available on the timeline:
| swimlane           | event_type | name_on_label                          | date_on_timeline_axis |
| Pathology and Labs | MARKER     | DiagReport Test 0                      | May 11, 2007          |
| Pathology and Labs | MARKER     | Test Diagnostic Report                 | May 11, 2009          |
| Imaging            | MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
| Biomarkers         | MARKER     | Report with pdf                        | Dec 06, 2012          |
When the user set the following timeline filter options:
| filter_group | checkbox_name | selected |
| Labels       | Important     | true     |
| Labels       | Has comments  | false    |
Then the filter counter icon on the Filter button is displayed with number: 1
And the following filter badge is available in the filter list on the timeline toolbar:
| filter    |
| Important |
And the following events are available on the timeline:
| swimlane           | event_type | name_on_label     | date_on_timeline_axis |
| Pathology and Labs | MARKER     | DiagReport Test 0 | May 11, 2007          |
| Biomarkers         | MARKER     | Report with pdf   | Dec 06, 2012          |
And the following events are not available on the timeline:
| swimlane           | event_type | name_on_label                          | date_on_timeline_axis |
| Pathology and Labs | MARKER     | Test Diagnostic Report                 | May 11, 2009          |
| Imaging            | MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
When the user set the following timeline filter options:
| filter_group | checkbox_name | selected |
| Labels       | Important     | true     |
| Labels       | Has comments  | true     |
Then the filter counter icon on the Filter button is displayed with number: 2
And the following filter badges are available in the filter list on the timeline toolbar:
| filter       |
| Important    |
| Has comments |
And the following events are available on the timeline:
| swimlane           | event_type | name_on_label          | date_on_timeline_axis |
| Pathology and Labs | MARKER     | DiagReport Test 0      | May 11, 2007          |
| Biomarkers         | MARKER     | Report with pdf        | Dec 06, 2012          |
| Pathology and Labs | MARKER     | Test Diagnostic Report | May 11, 2009          |
And the following events are not available on the timeline:
| swimlane | event_type | name_on_label                          | date_on_timeline_axis |
| Imaging  | MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
When the user set the following timeline filter options:
| filter_group | checkbox_name  | selected |
| Labels       | Important      | true     |
| Labels       | Has comments   | true     |
| Labels       | To Be Reviewed | false    |
| Labels       | Progression    | true     |
Then the following events are available on the timeline:
| swimlane           | event_type | name_on_label                          | date_on_timeline_axis |
| Pathology and Labs | MARKER     | DiagReport Test 0                      | May 11, 2007          |
| Pathology and Labs | MARKER     | Test Diagnostic Report                 | May 11, 2009          |
| Imaging            | MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
| Biomarkers         | MARKER     | Report with pdf                        | Dec 06, 2012          |

@SRS-74.04
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68648,SysRS-69226
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Hidden_Event_Information
Scenario: User can remove the applied filters from the timeline toolbar (remove filters one-by-one or reset all)
Given [API] the following events were marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/347b7a4b-ab75-4691-a8f5-d2f498bb6bed |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/8407cbdb-1ae3-4a86-b4d6-6e1778a67519 |
And [API] the below event comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | root_resource_of_the_event                            | comment_text                |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/ef98eb70-cb59-4cd3-bc70-97bb04bdb4df | filter union test comment   |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/8407cbdb-1ae3-4a86-b4d6-6e1778a67519 | filter union test comment 2 |
And [API] labels are added to the following events by the [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            | labels                      |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc | To Be Reviewed, Progression |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/8407cbdb-1ae3-4a86-b4d6-6e1778a67519 | To Be Reviewed              |
And [API] the following event was not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/ef98eb70-cb59-4cd3-bc70-97bb04bdb4df |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following event:
| patientID                            | root_resource_of_the_event                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/347b7a4b-ab75-4691-a8f5-d2f498bb6bed |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/347b7a4b-ab75-4691-a8f5-d2f498bb6bed |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/ef98eb70-cb59-4cd3-bc70-97bb04bdb4df |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the following events are available on the timeline:
| swimlane           | event_type | name_on_label                          | date_on_timeline_axis |
| Pathology and Labs | MARKER     | DiagReport Test 0                      | May 11, 2007          |
| Pathology and Labs | MARKER     | Test Diagnostic Report                 | May 11, 2009          |
| Imaging            | MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
| Biomarkers         | MARKER     | Report with pdf                        | Dec 06, 2012          |
When the user set the following timeline filter options:
| filter_group       | checkbox_name      | selected |
| Swimlanes          | Encounters         | true     |
| Swimlanes          | Imaging            | true     |
| Swimlanes          | Pathology and Labs | true     |
| Swimlanes          | Biomarkers         | true     |
| Swimlanes          | Treatment and Plan | true     |
| Swimlanes          | Uncategorized      | false    |
| Labels             | Important          | true     |
| Labels             | Has comments       | true     |
| Labels             | To Be Reviewed     | false    |
| Labels             | Progression        | true     |
| Pathology and Labs | Pathology          | true     |
| Imaging modalities | CT                 | true     |
Then the following events are available on the timeline:
| swimlane           | event_type | name_on_label                          | date_on_timeline_axis |
| Pathology and Labs | MARKER     | DiagReport Test 0                      | May 11, 2007          |
| Pathology and Labs | MARKER     | Test Diagnostic Report                 | May 11, 2009          |
| Imaging            | MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
| Biomarkers         | MARKER     | Report with pdf                        | Dec 06, 2012          |
And a button is displayed on the timeline toolbar to see all applied filters
When the user opens the more filters modal to see all applied filters
Then the following filter badges are available in the filter list on the timeline toolbar:
| filter             |
| Encounters         |
| Imaging            |
| Pathology and Labs |
| Biomarkers         |
| Treatment and Plan |
| Important          |
| Has comments       |
| Progression        |
| Pathology          |
| CT                 |
When the user clicks on the 'X' button on the below filter badges in the filter list on the timeline toolbar:
| filter    |
| Important |
| Pathology |
Then the following filter badges are available in the filter list on the timeline toolbar:
| filter             |
| Encounters         |
| Imaging            |
| Pathology and Labs |
| Biomarkers         |
| Treatment and Plan |
| Has comments       |
| Progression        |
| CT                 |
And the following events are available on the timeline:
| swimlane           | event_type | name_on_label                          | date_on_timeline_axis |
| Pathology and Labs | MARKER     | Test Diagnostic Report                 | May 11, 2009          |
| Imaging            | MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
| Biomarkers         | MARKER     | Report with pdf                        | Dec 06, 2012          |
And the following events are not available on the timeline:
| swimlane           | event_type | name_on_label     | date_on_timeline_axis |
| Pathology and Labs | MARKER     | DiagReport Test 0 | May 11, 2007          |
When the user clicks on the 'Reset filters' button in the filter list modal on the timeline toolbar
Then filter badge is not available in the filter list on the timeline toolbar
And the following events are available on the timeline:
| swimlane           | event_type | name_on_label                          | date_on_timeline_axis |
| Pathology and Labs | MARKER     | DiagReport Test 0                      | May 11, 2007          |
| Pathology and Labs | MARKER     | Test Diagnostic Report                 | May 11, 2009          |
| Imaging            | MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
| Biomarkers         | MARKER     | Report with pdf                        | Dec 06, 2012          |

@SRS-74.05
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68648
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Hidden_Event_Information
Scenario: Notification appears on the sidepanel when the swimlane is filtered where the open event is available
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the following event is available on the "Encounters" swimlane:
| event_type | name_on_label  |
| MARKER     | Progress Notes |
And the following event point is selected on the "Encounters" swimlane:
| event_type | name_on_label  |
| MARKER     | Progress Notes |
And the sidebar header contains the following data:
| title          | creation_date | report_type |
| Progress Notes | May 31, 2017  | Encounters  |
And notification banner is not available in the sidebar header
When the user set the following timeline filter options:
| filter_group | checkbox_name      | selected |
| Swimlanes    | Encounters         | false    |
| Swimlanes    | Imaging            | true     |
| Swimlanes    | Pathology and Labs | true     |
| Swimlanes    | Biomarkers         | true     |
| Swimlanes    | Treatment and Plan | true     |
| Swimlanes    | Uncategorized      | true     |
And the user closes the timeline filter modal
Then the following swimlanes are available on the timeline:
| items              |
| Imaging            |
| Pathology and Labs |
| Biomarkers         |
| Treatment and Plan |
| Uncategorized      |
And the following swimlanes are not available on the timeline:
| items      |
| Encounters |
And the sidebar is displayed
And the sidebar header contains the following data:
| title          | creation_date | report_type |
| Progress Notes | May 31, 2017  | Encounters  |
And the sidebar header contains the following notification banner:
| banner_message_regex                                            |
| This event is not visible on timeline due to filter(s) applied. |
When the user set the following timeline filter options:
| filter_group | checkbox_name      | selected |
| Swimlanes    | Encounters         | true     |
| Swimlanes    | Imaging            | false    |
| Swimlanes    | Pathology and Labs | true     |
| Swimlanes    | Biomarkers         | true     |
| Swimlanes    | Treatment and Plan | true     |
| Swimlanes    | Uncategorized      | true     |
And the user closes the timeline filter modal
Then the following swimlanes are available on the timeline:
| items              |
| Encounters         |
| Pathology and Labs |
| Biomarkers         |
| Treatment and Plan |
| Uncategorized      |
And the following swimlanes are not available on the timeline:
| items   |
| Imaging |
And notification banner is not available in the sidebar header

@SRS-74.06
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68648
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Hidden_Event_Information
Scenario: Notification appears on the sidepanel when the event is not visible based on the applied label filters
Given [API] the following event was marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f |
And [API] default labels are added to the following event by the [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                       | labels            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f | Molecular testing |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event is available on the "Encounters" swimlane:
| event_type | name_on_label       | date_on_timeline_axis |
| MARKER     | Body Weight 47.3 kg | Jan 23, 2013          |
And the following event point is selected on the "Encounters" swimlane:
| event_type | name_on_label       | date_on_timeline_axis |
| MARKER     | Body Weight 47.3 kg | Jan 23, 2013          |
And the sidebar header contains the following data:
| title               | creation_date | report_type |
| Body Weight 47.3 kg | Jan 23, 2013  | Encounters  |
And the sidebar header contains the following badges:
| badge_type      | badge_text        |
| IMPORTANT_EVENT | Important         |
| LABEL           | Molecular testing |
And notification banner is not available in the sidebar header
When the user set the following timeline filter options:
| filter_group | checkbox_name     | selected |
| Labels       | Important         | false    |
| Labels       | Molecular testing | false    |
| Labels       | To Be Reviewed    | true     |
Then the sidebar header contains the following notification banner:
| banner_message_regex                                            |
| This event is not visible on timeline due to filter(s) applied. |
When the user opens the timeline filter modal
And the user clicks on the "Reset filters" button on the filter modal
And the user closes the timeline filter modal
Then notification banner is not available in the sidebar header

@SRS-74.07
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68648
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Hidden_Event_Information
Scenario: Notification appears on the sidepanel when the event is not visible based on the applied event classification filters
Given [API] the following event were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following event:
| patientID                            | root_resource_of_the_event                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the below event on "Imaging" swimlane is visible in the viewport:
| event_type | name_on_label                          | date_on_timeline_axis |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
And the following event point is selected on the "Imaging" swimlane:
| event_type | name_on_label                          | date_on_timeline_axis |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
And the sidebar is displayed
And the sidebar header contains the following data:
| title                                  | creation_date | report_type |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011  | Imaging     |
And the sidebar header contains the following badges:
| badge_type     | badge_text |
| CLASSIFICATION | CT         |
And notification banner is not available in the sidebar header
When the user clicks on the 'Zoom in' button 5 times
Then the sidebar header contains the following notification banner:
| banner_message_text                        | button_in_banner    |
| This event is outside of visible timeline. | Focus on this event |
When the user set the following timeline filter options:
| filter_group       | checkbox_name | selected |
| Imaging modalities | X-ray         | true     |
Then the sidebar header contains the following notification banner:
| banner_message_regex                                            |
| This event is not visible on timeline due to filter(s) applied. |
When the user opens the timeline filter modal
And the user clicks on the "Reset filters" button on the filter modal
And the user closes the timeline filter modal
Then the sidebar header contains the following notification banner:
| banner_message_text                        | button_in_banner    |
| This event is outside of visible timeline. | Focus on this event |
