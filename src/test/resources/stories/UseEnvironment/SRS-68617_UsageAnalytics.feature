Feature: [SysRS-68617] Usage Analytics

Narrative: The system shall collect usage analytics through integration with Gainsight when enabled, to support product enhancements and assist in resolving customer issues.

@SRS-68617.01
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68617,SysRS-103331,SysRS-103343,SysRS-103320
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Usage_Analytics
Scenario: User actions are tracked on Progressive Summary section
Given the user opened CareIntellect for Oncology application
And the user opened the Network panel in DevTools
When the [DEFAULT-TEST-USER] user logs in to CareIntellect for Oncology application
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | ep_query_parameter_detail |
| identify            | <environment_url>   | "userHash"                |
And the "userHash" parameter value does not contain sensitive information
When the user turns ON the "Artificial Intelligence (AI)" in user settings
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | ep_query_parameter_detail |
| custom              | <environment_url>   | "value":"On"              |
When the user selects "TEST_PS, 08e" patient
And the user checks '/command' requests in the Network tab
Then the request url does not contain the following Patient ID: 'QDvEOlUwS6BO'
When the user clicks on "Show all 8 events" button in 'Since last oncology encounter' section
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | ep_query_parameter_detail                         |
| click               | <environment_url>   | "since-last-oncology-encounter-show-more-button"  |
When the user clicks on the 'Open images' button for 'CT ABDOMEN AND PELVIS' event
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | payload_detail              |
| click               | <environment_url>   | "recent-reports-and-notes"  |
When the user closes the report modal
And the user clicks on the 'Full report' button for 'Progress Notes' event
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | payload_detail              |
| click               | <environment_url>   | "recent-reports-and-notes"  |
When the user closes the report modal
And the user clicks on "Show more" button on 'Brief history' section
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | payload_detail                  |
| click               | <environment_url>   |"brief-history-show-more-button" |
When the user hovers on a "[source]" link on 'Brief history' section
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | cet_query_parameter                   |
| custom              | <environment_url>   | "hover_history_summarization_linkcta" |
When the user clicks on a "[source]" link on 'Summary of recent reports and notes' section
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | payload_detail              |
| click               | <environment_url>   | "recent-reports-and-notes"  |
When the user closes the report modal
And the user turns OFF the "Artificial Intelligence (AI)" in user settings
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | ep_query_parameter_detail |
| custom              | <environment_url>   | "value":"Off"             |

@SRS-68617.02
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68617,SysRS-103331,SysRS-103343,SysRS-103320
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Usage_Analytics
Scenario: User actions are tracked on Summary page cards
Given the user opened CareIntellect for Oncology application
And the user opened the Network panel in DevTools
When the [DEFAULT-TEST-USER] user logs in to CareIntellect for Oncology application
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | ep_query_parameter_detail |
| identify            | <environment_url>   | "userHash"                |
And the "userHash" parameter value does not contain sensitive information
When the user selects "TEST_PS, 11e" patient
And the user checks '/command' requests in the Network tab
Then the request url does not contain the following Patient ID: 'ZKvEIUw532324'
When the user clicks on the following link on 'Imaging history' card:
| event                 | date          |
| CT ABDOMEN AND PELVIS | Mar 15, 2008  |
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | payload_detail          |
| click               | <environment_url>   | "imaging-history-card"  |
When the user closes the report modal
And the user clicks on the following link on 'Key events' card:
| event             | date          |
| Pathology Report  | Apr 10, 2008  |
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | payload_detail    |
| click               | <environment_url>   | "key-events-card" |
When the user closes the report modal
And the user clicks on the "Full report" button on 'Oncology note - most recent' card
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | payload_detail                          |
| click               | <environment_url>   | "oncology-note---most-recent-icon-cta"  |

@SRS-68617.03
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68617,SysRS-103331,SysRS-103343,SysRS-103320
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Usage_Analytics
Scenario: User actions are tracked on Trials view
Given [API] the AI data visualization is switched ON for [DEFAULT-TEST-USER] user
And the user opened CareIntellect for Oncology application
And the user opened the Network panel in DevTools
When the [DEFAULT-TEST-USER] user logs in to CareIntellect for Oncology application
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | ep_query_parameter_detail |
| identify            | <environment_url>   | "userHash"                |
And the "userHash" parameter value does not contain sensitive information
When the user selects "OncoTestPatient, ManyTrials" patient
And the user checks '/command' requests in the Network tab
Then the request url does not contain the following Patient ID: 'testPatientManyTrialsId'
When the user clicks on the "Clinical trials" button
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | cet_query_parameter |
| custom              | <environment_url>   | "open_trials_view"  |
When the user closes the Trials view
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | ep_query_parameter_detail |
| click               | <environment_url>   | "trials-tab-nav-item"     |
When the user clicks on the first trial option on 'Clinical trial options' card
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | cet_query_parameter |
| custom              | <environment_url>   | "open_trials_view"  |
When the user closes the Trials view
And the user clicks on the "All clinical trial options" button on 'Clinical trial options' card
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | cet_query_parameter |
| custom              | <environment_url>   | "open_trials_view"  |
When the user clicks on the Status filter icon
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | payload_detail                  |
| click               | <environment_url>   | "trials-header-filter-section"  |
When the user hovers on an eligibility value
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | cet_query_parameter |
| custom              | <environment_url>   | "hover_nlp_value"   |

@SRS-68617.04
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68617,SysRS-103331,SysRS-103343,SysRS-103320
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Usage_Analytics
Scenario: User actions are tracked on Timeline view
Given the user opened CareIntellect for Oncology application
And the user opened the Network panel in DevTools
When the [DEFAULT-TEST-USER] user logs in to CareIntellect for Oncology application
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | ep_query_parameter_detail |
| identify            | <environment_url>   | "userHash"                |
And the "userHash" parameter value does not contain sensitive information
When the user selects "TEST_TA, Higgins" patient
And the user checks '/command' requests in the Network tab
Then the request url does not contain the following Patient ID: '2d5d2b8e-24ba-4241-3f80-65e8d678298a'
When the user navigates to "Timeline" view
And the user selects the following event on the "Encounters" swimlane:
| event_type | name_on_label  |
| MARKER     | Oncology visit |
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | cet_query_parameter |
| custom              | <environment_url>   | "open_event"        |
When the user clicks on the 'Label' button on the sidebar header
And the user types 'test' into the label search field
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | cet_query_parameter |
| custom              | <environment_url>   | "search_for_label"  |
When the user selects 'Molecular testing' label from the results
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | cet_query_parameter   |
| custom              | <environment_url>   | "add_label_to_event"  |
When the user clicks on the "Create new" button on the label panel
And the user types the following text into the 'Label name' field: "Test label <<create_label_timestamp>>"
And the user clicks on "Create" button on the label panel
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | cet_query_parameter |
| custom              | <environment_url>   | "create_label"      |
When the user clicks on the "Filter" button above Timeline
And the user selects previously created label: "Test label <<create_label_timestamp>>"
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | cet_query_parameter | ep_query_parameter_detail                       |
| custom              | <environment_url>   | "filter_timeline"   | "text":"Test label <<create_label_timestamp>>"  |
When the user closes the filter modal
And the user clicks on 'View mode' button
And the user sets the following treatment protocol configuration for the patient:
| view_mode           | protocol                             | trial_arm                           | start_date   |
| Treatment adherence | CC-94676 for Prostate Cancer Patient | CC94676 for Prostate Cancer Patient | Jan 01, 2025 |
And the user clicks on "Done" button
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | ep_query_parameter_detail |
| click               | <environment_url>   | "view-mode-modal-save"    |
When the user clicks on the "Protocol start" link
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | payload_detail        |
| click               | <environment_url>   | "protocol-start-tick" |
When the user clicks on 'View mode' button
And the user selects the 'Default' option
And the user clicks on "Done" button
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | payload_detail          |
| click               | <environment_url>   | "view-mode-modal-save"  |

@SRS-68617.05
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68617,SysRS-103331,SysRS-103343,SysRS-103320
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Usage_Analytics
Scenario: User actions are tracked on Treatment and response view
Given the user opened CareIntellect for Oncology application
And the user opened the Network panel in DevTools
When the [DEFAULT-TEST-USER] user logs in to CareIntellect for Oncology application
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | ep_query_parameter_detail |
| identify            | <environment_url>   | "userHash"                |
And the "userHash" parameter value does not contain sensitive information
When the user selects "OncoTestPatient, Juno" patient
And the user checks '/command' requests in the Network tab
Then the request url does not contain the following Patient ID: 'a10e48ab-b927-9ee1-84a4-41638a84e05a'
When the user clicks on the "Patient status" button
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | cet_query_parameter   |
| custom              | <environment_url>   | "open_treatment_view" |
When the following imaging study was selected for study A:
| name                                    | date         |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST  | Sep 28, 2011 |
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | cet_query_parameter     | ep_query_parameter_detail |
| custom              | <environment_url>   | "change_imaging_study"  | "study_type":"studyA"     |
When the following imaging study was selected for study B:
| name                     | date         |
| Diagnostic imaging study | Aug 19, 2022 |
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | cet_query_parameter     | ep_query_parameter_detail |
| custom              | <environment_url>   | "change_imaging_study"  | "study_type":"studyB"     |
When the user selects the following custom date range on the chart slider:
| start_date   | end_date |
| May 04, 2005 | <TODAY>  |
And the user hovers on the "A" badge on the multiline chart
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | cet_query_parameter     |
| custom              | <environment_url>   | "hover_vertical_marker" |
When the user hovers on the "Radiation" badge on the multiline chart
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | cet_query_parameter     |
| custom              | <environment_url>   | "hover_vertical_marker" |
When the user clicks on the "Open complete report" button for Study A
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | payload_detail                      |
| click               | <environment_url>   | "treatment-response-internal-link"  |
When the user closes the report modal
And the user clicks on the "Open complete report" button for Study B
Then the following '/command' request is available on the Network panel with the following details:
| et_query_parameter  | ho_query_parameter  | payload_detail                      |
| click               | <environment_url>   | "treatment-response-internal-link"  |

@SRS-68617.06
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-103321
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Usage_Analytics
Scenario: User actions can be mapped and visualized in Gainsight application
Given the user logged in to Gainsight application
And the user selected the 'Product Mapper' menu from sidebar
When the user opens the product feature tree for the environment being tested
And the user selects an available mapping from the list
Then the following mapping details are displayed on the screen for the selected event:
| mapping_details |
| name            |
| type            |
| rule            |
| selector        |
When the user selects the 'Dashboard' menu from sidebar
And the user selects the dashboard for the environment being tested
Then the application displays customized charts on the dashboard
And the charts are populated with data
When the user changes the time interval for a displayed chart
Then the chart is refreshed with corresponding data

@SRS-68617.07
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-103322
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Usage_Analytics
Scenario: No error is displayed if application fails to send user analytics logs
Given the user opened the Network panel in DevTools
And the following text pattern was added to "Network request blocking" tool:
| text pattern |
| command      |
And the "Enable network request blocking" checkbox is CHECKED in the "Network request blocking" tool
When the [DEFAULT-TEST-USER] user logs in to CareIntellect for Oncology application
And the user selects "OncoTestPatient, Juno" patient
And the user clicks on "Pathology Report" link on 'Key events' card
Then the full report modal is displayed on the screen
And no error is displayed on the UI
When the user closes the report modal
And the user clicks on the "Patient status" button
Then the Treatment and response view is loaded
And no error is displayed on the UI
When the user navigates to Timeline view
And the user selects the following event on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
Then the sidebar is displayed
And no error is displayed on the UI

