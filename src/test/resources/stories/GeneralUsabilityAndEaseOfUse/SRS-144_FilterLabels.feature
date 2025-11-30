
@safety
Feature: [SysRS-68637] Filter labels

Narrative: The system users shall be able to filter on the Timeline for predefined Labels:
             - Adverse Event
             - Date of diagnosis
             - MDT discussion
             - Molecular testing
             - Progression
             - Recurrence
             - To Be Reviewed
             - custom event labels.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application

@sanity
@edge
@SRS-144.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68637
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use
Scenario: Filter events by using single custom label filter
Given [API] the following events were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/2caafd5d-053f-4a0c-85b4-9af5bc436dcc      |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/8407cbdb-1ae3-4a86-b4d6-6e1778a67519 |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f      |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Procedure/8bd96ca8-d605-44d6-a0c6-1e280aa2e1db        |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Procedure/115c3179-704d-41c5-8952-7074a65df0a2        |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/2caafd5d-053f-4a0c-85b4-9af5bc436dcc      |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/8407cbdb-1ae3-4a86-b4d6-6e1778a67519 |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f      |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Procedure/8bd96ca8-d605-44d6-a0c6-1e280aa2e1db        |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Procedure/115c3179-704d-41c5-8952-7074a65df0a2        |
And [API] the following labels are created:
| label_name    |
| Filter test 1 |
| Filter test 2 |
And [API] labels are added to the following events by the [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            | labels                                        | event_name                                   |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/2caafd5d-053f-4a0c-85b4-9af5bc436dcc      | Filter test 1, To Be Reviewed, MDT discussion | Estrogen Receptor Positive (qualifier value) |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc | Filter test 1, To Be Reviewed, Progression    | CT THORAX ABDOMEN PELVIS WITH CONTRAST       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/8407cbdb-1ae3-4a86-b4d6-6e1778a67519 | MDT discussion                                | Report with pdf                              |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f      | Filter test 2, To Be Reviewed                 | Body Weight 47.3 kg                          |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Procedure/115c3179-704d-41c5-8952-7074a65df0a2        | Filter test 2                                 | CLEFT PALATE REPAIR                          |
And the patient page is displayed
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the following events are available on the timeline:
| swimlane   | event_type | text_on_event_point | name_on_label                                | date_on_timeline_axis |
| Imaging    | MARKER     | <EMPTY>             | CT THORAX ABDOMEN PELVIS WITH CONTRAST       | Sep 28, 2011          |
| Biomarkers | MARKER     | <EMPTY>             | Estrogen Receptor Positive (qualifier value) | Dec 21, 2016          |
| Biomarkers | MARKER     | <EMPTY>             | Report with pdf                              | Dec 06, 2012          |
| Encounters | MARKER     | <EMPTY>             | Body Weight 47.3 kg                          | Jan 23, 2013          |
When the user set the following timeline filter options:
| filter_group | checkbox_name  | selected |
| Labels       | Filter test 1  | true     |
| Labels       | Filter test 2  | false    |
| Labels       | To Be Reviewed | false    |
| Labels       | MDT discussion | false    |
| Labels       | Progression    | false    |
Then the following events are available on the timeline:
| swimlane   | event_type | text_on_event_point | name_on_label                                | date_on_timeline_axis |
| Imaging    | MARKER     | <EMPTY>             | CT THORAX ABDOMEN PELVIS WITH CONTRAST       | Sep 28, 2011          |
| Biomarkers | MARKER     | <EMPTY>             | Estrogen Receptor Positive (qualifier value) | Dec 21, 2016          |
And the "CT THORAX ABDOMEN PELVIS WITH CONTRAST" event MARKER is available on the "Imaging" swimlane with 4 miniaturized badges:
| badge_types_in_order |
| CLASSIFICATION       |
| LABEL                |
| LABEL                |
| LABEL                |
And the "Estrogen Receptor Positive (qualifier value)" event MARKER is available on the "Biomarkers" swimlane with 4 miniaturized badges:
| badge_types_in_order |
| CLASSIFICATION       |
| LABEL                |
| LABEL                |
| LABEL                |
And the following events are not available on the timeline:
| swimlane           | event_type | text_on_event_point | name_on_label       | date_on_timeline_axis |
| Biomarkers         | MARKER     | <EMPTY>             | Report with pdf     | Dec 06, 2012          |
| Encounters         | MARKER     | <EMPTY>             | Body Weight 47.3 kg | Jan 23, 2013          |
| Treatment and Plan | MARKER     | <EMPTY>             | CLEFT PALATE REPAIR | May 02, 2019          |
And the filter counter icon on the Filter button is displayed with number: 1
And the following filter badge is available in the filter list on the timeline toolbar:
| filter        |
| Filter test 1 |
When the user set the following timeline filter options:
| filter_group | checkbox_name  | selected |
| Labels       | Filter test 1  | false    |
| Labels       | Filter test 2  | false    |
| Labels       | To Be Reviewed | false    |
| Labels       | MDT discussion | false    |
| Labels       | Progression    | false    |
Then the filter counter icon on the Filter button is not displayed
And filter badge is not available in the filter list on the timeline toolbar

@SRS-144.07
@edge
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68637
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use
Scenario: Filter events by using single default label filter (Progression, Recurrence)
Given [API] the following labels are created:
| label_name    |
| Filter test 1 |
| Filter test 2 |
And [API] labels are added to the following events by the [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                             | labels                                        | event_name                                   |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/2caafd5d-053f-4a0c-85b4-9af5bc436dcc       | Filter test 1, To Be Reviewed, MDT discussion | Estrogen Receptor Positive (qualifier value) |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc  | Filter test 1, To Be Reviewed, Progression    | CT THORAX ABDOMEN PELVIS WITH CONTRAST       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/8407cbdb-1ae3-4a86-b4d6-6e1778a67519  | MDT discussion                                | Report with pdf                              |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f       | Filter test 2, To Be Reviewed                 | Body Weight 47.3 kg                          |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/f45b1db7-7194-45c9-830a-e9cbed025778 | Filter test 2, Recurrence                     | Radiation oncology Summary note              |
And the patient page is displayed
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the following events are available on the timeline:
| swimlane   | event_type | text_on_event_point | name_on_label                                | date_on_timeline_axis |
| Imaging    | MARKER     | <EMPTY>             | CT THORAX ABDOMEN PELVIS WITH CONTRAST       | Sep 28, 2011          |
| Biomarkers | MARKER     | <EMPTY>             | Estrogen Receptor Positive (qualifier value) | Dec 21, 2016          |
| Biomarkers | MARKER     | <EMPTY>             | Report with pdf                              | Dec 06, 2012          |
| Encounters | MARKER     | <EMPTY>             | Body Weight 47.3 kg                          | Jan 23, 2013          |
And the following events are not available on the timeline:
| swimlane           | event_type | text_on_event_point | name_on_label                   | date_on_timeline_axis |
| Treatment and Plan | MARKER     | <EMPTY>             | Radiation oncology Summary note | Feb 04, 2020          |
When the user set the following timeline filter options:
| filter_group | checkbox_name  | selected |
| Labels       | Filter test 1  | false    |
| Labels       | Filter test 2  | false    |
| Labels       | To Be Reviewed | false    |
| Labels       | MDT discussion | false    |
| Labels       | Progression    | true     |
| Labels       | Recurrence     | false    |
Then the following events are available on the timeline:
| swimlane | event_type | text_on_event_point | name_on_label                          | date_on_timeline_axis |
| Imaging  | MARKER     | <EMPTY>             | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
And the following events are not available on the timeline:
| swimlane           | event_type | text_on_event_point | name_on_label                                | date_on_timeline_axis       |
| Biomarkers         | MARKER     | <EMPTY>             | Estrogen Receptor Positive (qualifier value) | Dec 21, 2016                |
| Biomarkers         | MARKER     | <EMPTY>             | Report with pdf                              | Dec 06, 2012                |
| Encounters         | MARKER     | <EMPTY>             | Body Weight 47.3 kg                          | Jan 23, 2013                |
| Treatment and Plan | CLUSTER    | 2                   | 2 events                                     | Feb 11, 2017 - Apr 04, 2017 |
| Treatment and Plan | MARKER     | <EMPTY>             | Radiation oncology Summary note              | Feb 04, 2020                |
And the filter counter icon on the Filter button is displayed with number: 1
And the following filter badge is available in the filter list on the timeline toolbar:
| filter      |
| Progression |
When the user set the following timeline filter options:
| filter_group | checkbox_name  | selected |
| Labels       | Filter test 1  | false    |
| Labels       | Filter test 2  | false    |
| Labels       | To Be Reviewed | false    |
| Labels       | MDT discussion | false    |
| Labels       | Progression    | false    |
| Labels       | Recurrence     | true     |
Then the following events are available on the timeline:
| swimlane           | event_type | text_on_event_point | name_on_label                   | date_on_timeline_axis |
| Treatment and Plan | MARKER     | <EMPTY>             | Radiation oncology Summary note | Feb 04, 2020          |
And the following events are not available on the timeline:
| swimlane   | event_type | text_on_event_point | name_on_label                                | date_on_timeline_axis |
| Imaging    | MARKER     | <EMPTY>             | CT THORAX ABDOMEN PELVIS WITH CONTRAST       | Sep 28, 2011          |
| Biomarkers | MARKER     | <EMPTY>             | Estrogen Receptor Positive (qualifier value) | Dec 21, 2016          |
| Biomarkers | MARKER     | <EMPTY>             | Report with pdf                              | Dec 06, 2012          |
| Encounters | MARKER     | <EMPTY>             | Body Weight 47.3 kg                          | Jan 23, 2013          |
And the filter counter icon on the Filter button is displayed with number: 1
And the following filter badge is available in the filter list on the timeline toolbar:
| filter     |
| Recurrence |
When the user set the following timeline filter options:
| filter_group | checkbox_name  | selected |
| Labels       | Filter test 1  | false    |
| Labels       | Filter test 2  | false    |
| Labels       | To Be Reviewed | false    |
| Labels       | MDT discussion | false    |
| Labels       | Progression    | false    |
| Labels       | Recurrence     | false    |
Then the filter counter icon on the Filter button is not displayed
And filter badge is not available in the filter list on the timeline toolbar

@SRS-144.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68637
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Filter_Labels
Scenario: Filter events by using multiple label filters ('or' connection custom label and To Be Reviewed)
Given [API] the following labels are created:
| label_name    |
| Filter test 1 |
| Filter test 2 |
And [API] labels are added to the following events by the [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            | labels                                        | event_name                                   |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/2caafd5d-053f-4a0c-85b4-9af5bc436dcc      | Filter test 1, To Be Reviewed, MDT discussion | Estrogen Receptor Positive (qualifier value) |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc | Filter test 1, To Be Reviewed, Progression    | CT THORAX ABDOMEN PELVIS WITH CONTRAST       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/8407cbdb-1ae3-4a86-b4d6-6e1778a67519 | MDT discussion                                | Report with pdf                              |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f      | Filter test 2, To Be Reviewed                 | Body Weight 47.3 kg                          |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Procedure/115c3179-704d-41c5-8952-7074a65df0a2        | Filter test 2                                 | CLEFT PALATE REPAIR                          |
And the patient page is displayed
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the following events are available on the timeline:
| swimlane   | event_type | text_on_event_point | name_on_label                                | date_on_timeline_axis |
| Imaging    | MARKER     | <EMPTY>             | CT THORAX ABDOMEN PELVIS WITH CONTRAST       | Sep 28, 2011          |
| Biomarkers | MARKER     | <EMPTY>             | Estrogen Receptor Positive (qualifier value) | Dec 21, 2016          |
| Biomarkers | MARKER     | <EMPTY>             | Report with pdf                              | Dec 06, 2012          |
| Encounters | MARKER     | <EMPTY>             | Body Weight 47.3 kg                          | Jan 23, 2013          |
When the user set the following timeline filter options:
| filter_group | checkbox_name  | selected |
| Labels       | Filter test 1  | true     |
| Labels       | To Be Reviewed | true     |
| Labels       | Filter test 2  | false    |
| Labels       | MDT discussion | false    |
| Labels       | Progression    | false    |
Then the following events are available on the timeline:
| swimlane   | event_type | name_on_label                                | date_on_timeline_axis |
| Imaging    | MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST       | Sep 28, 2011          |
| Biomarkers | MARKER     | Estrogen Receptor Positive (qualifier value) | Dec 21, 2016          |
| Encounters | MARKER     | Body Weight 47.3 kg                          | Jan 23, 2013          |
And the following events are not available on the timeline:
| swimlane           | event_type | name_on_label       | date_on_timeline_axis |
| Biomarkers         | MARKER     | Report with pdf     | Dec 06, 2012          |
| Treatment and Plan | MARKER     | CLEFT PALATE REPAIR | May 02, 2019          |
And the filter counter icon on the Filter button is displayed with number: 2
And the following filter badge is available in the filter list on the timeline toolbar:
| filter         |
| Filter test 1  |
| To Be Reviewed |
When the user set the following timeline filter options:
| filter_group | checkbox_name  | selected |
| Labels       | Filter test 1  | false    |
| Labels       | To Be Reviewed | false    |
| Labels       | Filter test 2  | false    |
| Labels       | MDT discussion | false    |
| Labels       | Progression    | false    |
Then the filter counter icon on the Filter button is not displayed
And filter badge is not available in the filter list on the timeline toolbar
And the following events are available on the timeline:
| swimlane   | event_type | text_on_event_point | name_on_label                                | date_on_timeline_axis |
| Imaging    | MARKER     | <EMPTY>             | CT THORAX ABDOMEN PELVIS WITH CONTRAST       | Sep 28, 2011          |
| Biomarkers | MARKER     | <EMPTY>             | Estrogen Receptor Positive (qualifier value) | Dec 21, 2016          |
| Biomarkers | MARKER     | <EMPTY>             | Report with pdf                              | Dec 06, 2012          |
| Encounters | MARKER     | <EMPTY>             | Body Weight 47.3 kg                          | Jan 23, 2013          |

@SRS-144.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68637
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Filter_Labels
Scenario: User can search for labels on the Filter modal
Given [API] the following labels are created:
| label_name    |
| Filter test 1 |
| Filter test 2 |
And the patient page is displayed
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the user has opened the timeline filter modal
And the following timeline filter options are displayed on the filter modal:
| filter_group | checkbox_name     | selected | enabled |
| Labels       | Filter test 1     | false    | true    |
| Labels       | Filter test 2     | false    | true    |
| Labels       | To Be Reviewed    | false    | true    |
| Labels       | MDT discussion    | false    | true    |
| Labels       | Progression       | false    | true    |
| Labels       | Molecular testing | false    | true    |
When the user set the following timeline filter options:
| filter_group | checkbox_name     | selected |
| Labels       | Filter test 1     | true     |
| Labels       | Filter test 2     | false    |
| Labels       | To Be Reviewed    | false    |
| Labels       | MDT discussion    | false    |
| Labels       | Progression       | false    |
| Labels       | Molecular testing | false    |
When the user opens the timeline filter modal
And the user types the following text into the search field on filter modal: "test"
Then the following timeline filter options are displayed on the filter modal:
| filter_group | checkbox_name     | selected | enabled |
| Labels       | Filter test 1     | true     | true    |
| Labels       | Filter test 2     | false    | true    |
| Labels       | Molecular testing | false    | true    |
And the following timeline filter options are not displayed on the filter modal:
| filter_group | checkbox_name  | selected | enabled |
| Labels       | To Be Reviewed | false    | true    |
| Labels       | MDT discussion | false    | true    |
| Labels       | Progression    | false    | true    |
When the user types the following text into the search field on filter modal: "N0t avai1ab13 lab3l"
Then the following message is displayed on the Filter modal in the "Labels" group: "No matches."

@SRS-144.04
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68637
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Filter_Labels
Scenario: Filter events by using multiple label filters on Vertical Toolbar (MDT discussion, Molecular testing)
Given [API] the following labels are created:
| label_name    |
| Filter test 1 |
| Filter test 2 |
And [API] labels are added to the following events by the [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            | labels                                        | event_name                                   |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/2caafd5d-053f-4a0c-85b4-9af5bc436dcc      | Filter test 1, To Be Reviewed, MDT discussion | Estrogen Receptor Positive (qualifier value) |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Procedure/8bd96ca8-d605-44d6-a0c6-1e280aa2e1db        | Filter test 2                                 | Coronary artery bypass                       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/8407cbdb-1ae3-4a86-b4d6-6e1778a67519 | MDT discussion                                | Report with pdf                              |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f      | Filter test 2, Molecular testing              | Body Weight 47.3 kg                          |
And the patient page is displayed
And the "OncoTestPatient, Juno" patient is selected
And the browser size is set to: width=1680, height=900
And the "Timeline" view is selected
And the patient timeline is loaded
And the following events are available on the timeline:
| swimlane           | event_type | name_on_label                                | date_on_timeline_axis |
| Biomarkers         | MARKER     | Estrogen Receptor Positive (qualifier value) | Dec 21, 2016          |
| Treatment and Plan | MARKER     | Coronary artery bypass                       | Feb 11, 2017          |
| Encounters         | MARKER     | Body Weight 47.3 kg                          | Jan 23, 2013          |
| Biomarkers         | MARKER     | Report with pdf                              | Dec 06, 2012          |
When the user set the following timeline filter options on the vertical toolbar:
| filter_group | checkbox_name     | selected |
| Labels       | Filter test 1     | true     |
| Labels       | MDT discussion    | true     |
| Labels       | Molecular testing | true     |
| Labels       | Filter test 2     | false    |
| Labels       | To Be Reviewed    | false    |
| Labels       | Progression       | false    |
Then the following events are available on the timeline:
| swimlane   | event_type | name_on_label                                | date_on_timeline_axis |
| Biomarkers | MARKER     | Estrogen Receptor Positive (qualifier value) | Dec 21, 2016          |
| Encounters | MARKER     | Body Weight 47.3 kg                          | Jan 23, 2013          |
| Biomarkers | MARKER     | Report with pdf                              | Dec 06, 2012          |
And the following events are not available on the timeline:
| swimlane           | event_type | name_on_label          | date_on_timeline_axis |
| Treatment and Plan | MARKER     | Coronary artery bypass | Feb 11, 2017          |
And the filter counter icon on the Filter button on the vertical toolbar is displayed with number: 3
When the user set the following timeline filter options on the vertical toolbar:
| filter_group | checkbox_name     | selected |
| Labels       | Filter test 1     | false    |
| Labels       | MDT discussion    | false    |
| Labels       | Molecular testing | false    |
| Labels       | Filter test 2     | false    |
| Labels       | To Be Reviewed    | false    |
| Labels       | Progression       | false    |
Then the filter counter icon on the Filter button is not displayed on the vertical toolbar

@SRS-144.05
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68637
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Filter_Labels
Scenario: Labels are miniaturized on event L1 label if more labels are added to the event
Given [API] the following events were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/2caafd5d-053f-4a0c-85b4-9af5bc436dcc |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/2caafd5d-053f-4a0c-85b4-9af5bc436dcc |
And [API] the following labels are created:
| label_name    |
| Filter test 1 |
| Filter test 2 |
And [API] labels are added to the following events by the [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                       | labels                                                       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/2caafd5d-053f-4a0c-85b4-9af5bc436dcc | Filter test 1, Filter test 2, To Be Reviewed, MDT discussion |
And the patient page is displayed
When the user selects the "OncoTestPatient, Juno" patient
And the user navigates to the "Timeline" view
Then the patient timeline is loaded
And the "Estrogen Receptor Positive (qualifier value)" event MARKER is available on the "Biomarkers" swimlane at "Dec 21, 2016" with 5 miniaturized badges:
| badge_types_in_order |
| CLASSIFICATION       |
| LABEL                |
| LABEL                |
| LABEL                |
| LABEL                |

@SRS-144.06
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68637,SysRS-68638
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Filter_Labels
Scenario: User can reset label filter settings
Given [API] the following labels are created:
| label_name    |
| Filter test 1 |
| Filter test 2 |
And [API] labels are added to the following events by the [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            | labels                                       | event_name                                   |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/2caafd5d-053f-4a0c-85b4-9af5bc436dcc      | Filter test 1, Adverse Event, MDT discussion | Estrogen Receptor Positive (qualifier value) |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/8407cbdb-1ae3-4a86-b4d6-6e1778a67519 | MDT discussion, Filter test 2                | Report with pdf                              |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f      | Date of diagnosis                            | Body Weight 47.3 kg                          |
And the patient page is displayed
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the following events are available on the timeline:
| swimlane   | event_type | text_on_event_point | name_on_label                                | date_on_timeline_axis |
| Biomarkers | MARKER     | <EMPTY>             | Estrogen Receptor Positive (qualifier value) | Dec 21, 2016          |
| Biomarkers | MARKER     | <EMPTY>             | Report with pdf                              | Dec 06, 2012          |
| Encounters | MARKER     | <EMPTY>             | Body Weight 47.3 kg                          | Jan 23, 2013          |
When the user set the following timeline filter options:
| filter_group | checkbox_name     | selected |
| Labels       | Adverse Event     | true     |
| Labels       | Date of diagnosis | true     |
| Labels       | Filter test 1     | false    |
| Labels       | Filter test 2     | false    |
| Labels       | MDT discussion    | false    |
Then the following events are available on the timeline:
| swimlane   | event_type | name_on_label                                | date_on_timeline_axis |
| Biomarkers | MARKER     | Estrogen Receptor Positive (qualifier value) | Dec 21, 2016          |
| Encounters | MARKER     | Body Weight 47.3 kg                          | Jan 23, 2013          |
And the following event is not available on the timeline:
| swimlane   | event_type | name_on_label   | date_on_timeline_axis |
| Biomarkers | MARKER     | Report with pdf | Dec 06, 2012          |
When the user opens the timeline filter modal
Then the following timeline filter options are displayed on the filter modal:
| filter_group | checkbox_name     | selected |
| Labels       | Adverse Event     | true     |
| Labels       | Date of diagnosis | true     |
| Labels       | Filter test 1     | false    |
| Labels       | Filter test 2     | false    |
| Labels       | MDT discussion    | false    |
When the user clicks on the "Reset filters" button on the filter modal
Then the following timeline filter options are displayed on the filter modal:
| filter_group | checkbox_name     | selected |
| Labels       | Adverse Event     | false    |
| Labels       | Date of diagnosis | false    |
| Labels       | Filter test 1     | false    |
| Labels       | Filter test 2     | false    |
| Labels       | MDT discussion    | false    |
When the user closes the timeline filter modal
Then the following events are available on the timeline:
| swimlane   | event_type | text_on_event_point | name_on_label                                | date_on_timeline_axis |
| Biomarkers | MARKER     | <EMPTY>             | Estrogen Receptor Positive (qualifier value) | Dec 21, 2016          |
| Biomarkers | MARKER     | <EMPTY>             | Report with pdf                              | Dec 06, 2012          |
| Encounters | MARKER     | <EMPTY>             | Body Weight 47.3 kg                          | Jan 23, 2013          |

@SRS-144.08
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68637,SysRS-68642
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Filter_Labels
Scenario: Labels are grouped on the filter modal
Given [API] the following labels are created:
| label_name    |
| Filter test 1 |
| Filter test 2 |
And the "OncoTestPatient, Empty" patient is selected
And the "Timeline" view is selected
When the user opens the timeline filter modal
Then the following timeline filter options are displayed on the filter modal:
| filter_group | checkbox_name     | selected | enabled |
| Labels       | Adverse Event     | false    | true    |
| Labels       | Date of diagnosis | false    | true    |
| Labels       | MDT discussion    | false    | true    |
| Labels       | Molecular testing | false    | true    |
| Labels       | Progression       | false    | true    |
| Labels       | Recurrence        | false    | true    |
| Labels       | To Be Reviewed    | false    | true    |
| Labels       | Filter test 1     | false    | true    |
| Labels       | Filter test 2     | false    | true    |
