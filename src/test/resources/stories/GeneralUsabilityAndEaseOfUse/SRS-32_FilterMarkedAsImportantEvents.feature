
@safety
Feature: [SysRS-68635] Filter Marked As Important Events

Narrative: The system shall be able to support the following filtering on the timeline:
             - events which were marked as important events. (SRS-32 The system users shall be able to filter on the Timeline for events which were marked as important events.)
             - events which have comments. (SRS-143 The system users shall be able to filter on the Timeline for events which has comments.)
             - Chemo cycle events.(SRS-147 The system users shall be able to filter on the Timeline for Chemo cycle events.)
             - events which have AI enriched data. (SRS-148 The system users shall be able to filter on the Timeline for events which has AI enriched data. )

@sanity
@edge
@SRS-32.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68635
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use/Filter_Mark_As_Important_Events
Scenario: Only the important events are displayed on the swimlanes if 'Important' filter is selected
Given [API] the following events were marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                             |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/effb5f0c-038b-453b-8a5b-555adff35547  |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/66aa4e29-daec-4cce-85bb-b1ef53609472         |
And [API] the following events were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                               |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/9e816b7f-037f-4d22-adbc-bae8af3cc3ad    |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/52899193-5ff5-4323-82d5-a71772d5a33e    |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | MedicationStatement/359ab4ca-4a30-46b8-8f68-8a1bfddc7056 |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the patient page is displayed
And the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
When the user set the following timeline filter option:
| filter_group | checkbox_name | selected |
| Labels       | Important     | true     |
Then the following events are available on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
And the following events are available on the "Treatment and Plan" swimlane:
| event_type | name_on_label                               | date_on_timeline_axis |
| MARKER     | Excision of sentinel lymph node (procedure) | Jun 14, 2017          |
And the following events are not available on the "Pathology and Labs" swimlane:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 2                   | 2 events      |
And the following events are not available on the "Treatment and Plan" swimlane:
| event_type | name_on_label                   | date_on_timeline_axis |
| MARKER     | cyclophosphamide 1 GM Injection | Feb 24, 2014          |


@SRS-32.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68635
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Filter_Mark_As_Important_Events
Scenario: All events appear on the swimlanes if user unselects 'Important' filter option
Given [API] the following events were marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                             |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/effb5f0c-038b-453b-8a5b-555adff35547  |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/66aa4e29-daec-4cce-85bb-b1ef53609472         |
And [API] the following events were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                               |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/9e816b7f-037f-4d22-adbc-bae8af3cc3ad    |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/52899193-5ff5-4323-82d5-a71772d5a33e    |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | MedicationStatement/359ab4ca-4a30-46b8-8f68-8a1bfddc7056 |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the patient page is displayed
And the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
And the user has set the following timeline filter option:
| filter_group | checkbox_name | selected |
| Labels       | Important     | true     |
And the following events are available on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
And the following events are available on the "Treatment and Plan" swimlane:
| event_type | name_on_label                               | date_on_timeline_axis |
| MARKER     | Excision of sentinel lymph node (procedure) | Jun 14, 2017          |
And the following events are not available on the "Pathology and Labs" swimlane:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 2                   | 2 events      |
And the following events are not available on the "Treatment and Plan" swimlane:
| event_type | name_on_label                   | date_on_timeline_axis |
| MARKER     | cyclophosphamide 1 GM Injection | Feb 24, 2014          |
When the user set the following timeline filter option:
| filter_group | checkbox_name | selected |
| Labels       | Important     | false    |
Then the following events are available on the "Pathology and Labs" swimlane:
| event_type | text_on_event_point | name_on_label          |
| MARKER     | <EMPTY>             | Test Diagnostic Report |
| CLUSTER    | 2                   | 2 events               |
And the following events are available on the "Treatment and Plan" swimlane:
| event_type | name_on_label                               | date_on_timeline_axis |
| MARKER     | cyclophosphamide 1 GM Injection             | Feb 24, 2014          |
| MARKER     | Excision of sentinel lymph node (procedure) | Jun 14, 2017          |

@SRS-32.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68635
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Filter_Mark_As_Important_Events
Scenario: Event disappears from the swimlane if user unmarks the event
Given [API] the following event was marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/effb5f0c-038b-453b-8a5b-555adff35547 |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/66aa4e29-daec-4cce-85bb-b1ef53609472        |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the patient page is displayed
And the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
And the user has set the following timeline filter option:
| filter_group | checkbox_name | selected |
| Labels       | Important     | true     |
When the user clicks on the following event point on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
Then the sidebar is displayed
When the user clicks 'Marked as important' button on the sidebar
Then the following events are not available on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
And the following events are available on the "Treatment and Plan" swimlane:
| event_type | name_on_label                               | date_on_timeline_axis |
| MARKER     | Excision of sentinel lymph node (procedure) | Jun 14, 2017          |

@SRS-32.04
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68635
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Filter_Mark_As_Important_Events
Scenario: 'Important' checkbox remains in selected state and events are not visible on the timeline if all important events are unmarked after filtering
Given [API] the following event was marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/effb5f0c-038b-453b-8a5b-555adff35547 |
And [API] the following events were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                               |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/9e816b7f-037f-4d22-adbc-bae8af3cc3ad    |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/52899193-5ff5-4323-82d5-a71772d5a33e    |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/66aa4e29-daec-4cce-85bb-b1ef53609472           |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | MedicationStatement/359ab4ca-4a30-46b8-8f68-8a1bfddc7056 |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the patient page is displayed
And the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
And the user has set the following timeline filter option:
| filter_group | checkbox_name | selected |
| Labels       | Important     | true     |
And the filter counter icon on the Filter button is displayed with number: 1
And the following filter badge is available in the filter list on the timeline toolbar:
| filter    |
| Important |
And the following events are available on the "Pathology and Labs" swimlane:
| event_type | text_on_event_point | name_on_label          |
| MARKER     | <EMPTY>             | Test Diagnostic Report |
And the following events are not available on the "Pathology and Labs" swimlane:
| event_type | text_on_event_point | name_on_label          |
| CLUSTER    | 2                   | 2 events               |
And the following events are not available on the "Treatment and Plan" swimlane:
| event_type | name_on_label                               | date_on_timeline_axis |
| MARKER     | Excision of sentinel lymph node (procedure) | Jun 14, 2017          |
| MARKER     | cyclophosphamide 1 GM Injection             | Feb 24, 2014          |
When the user opens the timeline filter modal
Then the following timeline filter options are displayed on the filter modal:
| filter_group | checkbox_name | selected | enabled |
| Labels       | Important     | true     | true    |

@SRS-32.05
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68635
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Filter_Mark_As_Important_Events
Scenario: Only the marked event is displayed on the swimlane - concurrent events
Given [API] the following events were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/9e816b7f-037f-4d22-adbc-bae8af3cc3ad |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/52899193-5ff5-4323-82d5-a71772d5a33e |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the patient page is displayed
And the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
And the following event point is selected on the "Pathology and Labs" swimlane:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 2                   | 2 events      |
When the user selects the following event from the cluster pill:
| event_name                       |
| Tissue Pathology biopsy report 2 |
Then the sidebar header contains the following data:
| title                            | creation_date | report_type        |
| Tissue Pathology biopsy report 2 | Jan 15, 2020  | Pathology and Labs |
When the user clicks 'Mark as important' button on the sidebar
And the user clicks on the Close button on the sidebar
And the user set the following timeline filter option:
| filter_group | checkbox_name | selected |
| Labels       | Important     | true     |
Then the following events are available on the "Pathology and Labs" swimlane:
| event_type | name_on_label                    |
| MARKER     | Tissue Pathology biopsy report 2 |
Then the following events are not available on the "Pathology and Labs" swimlane:
| event_type | text_on_event_point | name_on_label                    |
| CLUSTER    | 2                   | 2 events                         |
| MARKER     | <EMPTY>             | Tissue Pathology biopsy report 1 |

@SRS-32.06
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68635
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Filter_Mark_As_Important_Events
Scenario: 'Important' filter option is always enabled even if there is no important event on the timeline
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the patient page is displayed
And the "OncoTestPatient, Empty" patient is selected
And the "Timeline" view is selected
When the user opens the timeline filter modal
Then the following timeline filter options are displayed on the filter modal:
| filter_group | checkbox_name | selected | enabled |
| Labels       | Important     | false    | true    |

@SRS-32.07
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68635
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Filter_Mark_As_Important_Events
Scenario Outline: 'Important' filter option is not selected by default and setting is not saved after logout
Given [API] the following events were marked as important for [<user>] user:
| patientID                            | root_resource_of_the_event                            |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/effb5f0c-038b-453b-8a5b-555adff35547 |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/347b7a4b-ab75-4691-a8f5-d2f498bb6bed |
And the [<user>] user is logged in to OncoCare application
And the patient page is displayed
And the "<patient>" patient is selected
And the "Timeline" view is selected
And the user has opened the timeline filter modal
And the following timeline filter options are displayed on the filter modal:
| filter_group | checkbox_name | selected | enabled |
| Labels       | Important     | false    | true    |
When the user set the following timeline filter option:
| filter_group | checkbox_name | selected |
| Labels       | Important     | true     |
And the user logs out from OncoCare
And the [<user>] user logs in to OncoCare
And the user selects the "<patient>" patient
And the user navigates to the "Timeline" view
And the user opens the timeline filter modal
Then the following timeline filter options are displayed on the filter modal:
| filter_group | checkbox_name | selected | enabled |
| Labels       | Important     | false    | true    |
Examples:
| user              | patient                  |
| DEFAULT-TEST-USER | OncoTestPatient, Torvald |
| TEST-USER-2       | OncoTestPatient, Torvald |
