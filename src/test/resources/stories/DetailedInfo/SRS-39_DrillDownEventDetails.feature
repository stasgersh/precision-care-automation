Feature: [SysRS-68545] Drill Down Event Details

Narrative: The system users shall be able to drill down the details of each event by clicking on the event to see more details.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application

@sanity
@edge
@SRS-39.01
@SPR-4284
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68545
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Detailed_Info
Scenario: Event details are displayed on the side panel when user clicks on the event marker
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event is available on the "Encounters" swimlane:
| event_type | name_on_label  |
| MARKER     | Progress Notes |
When the user clicks on the following event point on the "Encounters" swimlane:
| event_type | name_on_label  |
| MARKER     | Progress Notes |
Then the sidebar is displayed
And the sidebar header contains the following data:
| title          | creation_date | report_type |
| Progress Notes | May 31, 2017  | Encounters  |
And the sidebar content contains the following data:
| data_type | title                | value                         |
| KEY-VALUE | Document Status      | final                         |
| KEY-VALUE | Status               | current                       |
| KEY-VALUE | Author               | Physician Family Medicine, MD |
| KEY-VALUE | Provider Type        | Physician                     |
| LINK      | Open complete report | <N/A>                         |

@sanity
@edge
@SRS-39.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68545
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Detailed_Info
Scenario: Details of an event in an event group are displayed after the cluster is ungrouped and the event is selected
Given the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the following event is available on the "Pathology and Labs" swimlane:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 2                   | 2 events      |
When the user clicks on the following event point on the "Pathology and Labs" swimlane:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 2                   | 2 events      |
And the user selects the following event from the cluster pill:
| event_name                       |
| Tissue Pathology biopsy report 2 |
Then the sidebar is displayed
And the sidebar header contains the following data:
| title                            | creation_date | report_type        |
| Tissue Pathology biopsy report 2 | Jan 15, 2020  | Pathology and Labs |
And the sidebar content contains the following data:
| data_type | title                | value                                      |
| KEY-VALUE | Conclusion           | Left axillary lymph node core biopsy 2: OK |
| KEY-VALUE | Status               | final                                      |
| LINK      | Open complete report | <N/A>                                      |

@sanity
@edge
@SRS-39.03
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68545
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Detailed_Info
Scenario: In case of concurrent events (events on same date), an event can be selected after the event cluster is ungrouped
Given the "OncoTestPatient, Al" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the "Pathology and Labs" swimlane contains the following events:
| event_type | text_on_event_point | name_on_label                                                    |
| MARKER     | <EMPTY>             | Complete blood count (hemogram) panel - Blood by Automated count |
| CLUSTER    | 2                   | 2 events                                                         |
When the user clicks on the following event point on the "Pathology and Labs" swimlane:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 2                   | 2 events      |
Then the cluster pill is opened
And the following events are displayed in the cluster pill:
| event_name                                                          | date_on_tooltip |
| Influenza virus A and B Ag panel - Nasopharynx by Rapid immunoassay | Mar 08, 2020    |
| SARS-CoV-2 RNA Pnl Resp NAA+probe                                   | Mar 08, 2020    |
And the sidebar is not displayed
When the user selects the following event from the cluster pill:
| event_name                                                          |
| Influenza virus A and B Ag panel - Nasopharynx by Rapid immunoassay |
Then the sidebar is displayed
And the sidebar header contains the following data:
| title                                                               | creation_date | report_type        |
| Influenza virus A and B Ag panel - Nasopharynx by Rapid immunoassay | Mar 08, 2020  | Pathology and Labs |
And the sidebar content contains the following data:
| data_type | title   | value        |
| TABLE     | Results | <DATA_TABLE> |
And the sidebar contains the following "Results" table:
| Test name                                                           | Result                         | Unit    | Flag    | Reference range |
| Influenza virus A Ag [Presence] in Nasopharynx by Rapid immunoassay | Not detected (qualifier value) | <EMPTY> | <EMPTY> | <EMPTY>         |
| Influenza virus B Ag [Presence] in Nasopharynx by Rapid immunoassay | Not detected (qualifier value) | <EMPTY> | <EMPTY> | <EMPTY>         |
When the user selects the following event from the cluster pill:
| event_name                        |
| SARS-CoV-2 RNA Pnl Resp NAA+probe |
Then the sidebar is displayed
And the sidebar header contains the following data:
| title                             | creation_date | report_type        |
| SARS-CoV-2 RNA Pnl Resp NAA+probe | Mar 08, 2020  | Pathology and Labs |
And the sidebar content contains the following data:
| data_type | title   | value        |
| TABLE     | Results | <DATA_TABLE> |
And the sidebar contains the following "Results" table:
| Test name                         | Result                     | Unit    | Flag    | Reference range |
| SARS-CoV-2 RNA Pnl Resp NAA+probe | Detected (qualifier value) | <EMPTY> | <EMPTY> | <EMPTY>         |

@SRS-39.05
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68545
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Detailed_Info
Scenario: Only the event comment section is displayed for the user if details are not available for the selected event
Given the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
When the user clicks on the following event point on the "Encounters" swimlane:
| event_type | name_on_label                        |
| MARKER     | Emergency room admission (procedure) |
Then the sidebar is displayed
And the sidebar header contains the following data:
| title                                | creation_date | report_type |
| Emergency room admission (procedure) | Jun 08, 2018  | Encounters  |
And the comment input box is available on the sidebar
And there is no any other patient data on the sidebar
