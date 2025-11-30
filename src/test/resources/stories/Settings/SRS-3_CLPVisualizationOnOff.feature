Feature: [SysRS-68583] CLP Visualization On-Off

Narrative: The application users shall be able to turn On/Off CLP visualization.


@SRS-3.01
@sanity
@edge
@ai_sanity
@CLP
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68583,SysRS-68383
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Settings
Scenario: Switch off CLP visualization for the user (Timeline view)
Given [API] the following events were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/00862568-e2b2-41a7-9eeb-5fff4163621d  |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/1b46f42e-1f4c-48d4-b9f8-225ff72d510a |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/00862568-e2b2-41a7-9eeb-5fff4163621d  |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/1b46f42e-1f4c-48d4-b9f8-225ff72d510a |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/00862568-e2b2-41a7-9eeb-5fff4163621d  |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/1b46f42e-1f4c-48d4-b9f8-225ff72d510a |
And [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
When the user clicks on the 'Zoom out' button 5 times
When the user clicks on the following event point on the "Imaging" swimlane:
| event_type | text_on_event_point | name_on_label | date_on_timeline_axis       |
| CLUSTER    | 4                   | 4 events      | Aug 18, 2022 - Sep 09, 2022 |
Then the cluster pill is opened
And the "Examination of joint under image intensifier (procedure)" event is available in the custer pill with the below badges:
| badge_type | badge_text                                      |
| CLP        | Partial tear and fracture due to recent trauma. |
Given the "History and physical note - 3" event MARKER is available on the "Encounters" swimlane with 2 miniaturized badges:
| badge_types_in_order |
| CLASSIFICATION       |
| CLP                  |
When the user zooms out the timeline until it is possible
Given the following event point is selected on the "Encounters" swimlane:
| event_type | name_on_label                 |
| MARKER     | History and physical note - 3 |
And the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type     | badge_text                |
| CLASSIFICATION | Outpatient visit          |
| CLP            | [keywords]: Xelox ongoing |
When the user clicks on the following event point on the "Imaging" swimlane:
| event_type | text_on_event_point | name_on_label | date_on_timeline_axis       |
| CLUSTER    | 4                   | 4 events      | Aug 18, 2022 - Sep 09, 2022 |
And the user selects the following event from the cluster pill:
| event_name                                               |
| Examination of joint under image intensifier (procedure) |
Then the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type | badge_text                                      |
| CLP        | Partial tear and fracture due to recent trauma. |
And the sidebar content contains the following data:
| data_type | title                | value  | value_from_file                                                |
| KEY-VALUE | Conclusion           | STABLE | <N/A>                                                          |
| KEY-VALUE | Status               | final  | <N/A>                                                          |
| LINK      | Open complete report | <N/A>  | <N/A>                                                          |
| CLP_INFO  | <N/A>                | <N/A>  | L2/CLP/OncoTestPatient_Juno_Examination_of_joint_truncated.txt |
When the user clicks on the "Settings" menu item on the user menu panel
Then the Settings page is displayed
And the following settings are available on the page:
| settings_title               | setting_value |
| Artificial Intelligence (AI) | On            |
When the user clicks on 'Edit' button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user selects the "Off" radio button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user clicks on the "Save" button on the Settings page
Then the following settings are available on the page:
| settings_title               | setting_value |
| Artificial Intelligence (AI) | Off           |
When the user clicks on the "Back" button in the Settings toolbar header
Then the patient timeline is loaded
When the user clicks on the following event point on the "Imaging" swimlane:
| event_type | text_on_event_point | name_on_label | date_on_timeline_axis       |
| CLUSTER    | 4                   | 4 events      | Aug 18, 2022 - Sep 09, 2022 |
Then the "Examination of joint under image intensifier (procedure)" event is available in the custer pill without any badges
When the user closes the cluster pill
Then the "History and physical note - 3" event MARKER is available on the "Encounters" swimlane at "Apr 22, 2011" with the below badges:
| badge_type     | badge_text       |
| CLASSIFICATION | Outpatient visit |
And the sidebar is displayed
And the sidebar header does not contain any badges
And the sidebar content contains the following data:
| data_type | title                | value  |
| KEY-VALUE | Conclusion           | STABLE |
| KEY-VALUE | Status               | final  |
| LINK      | Open complete report | <N/A>  |
When the user clicks on the following event point on the "Encounters" swimlane:
| event_type | name_on_label                 |
| MARKER     | History and physical note - 3 |
Then the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type     | badge_text       |
| CLASSIFICATION | Outpatient visit |

@SRS-3.02
@sanity
@ai_sanity
@CLP
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68583,SysRS-68383
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Settings
Scenario: Switch on CLP visualization for the user
Given [API] the following events were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/00862568-e2b2-41a7-9eeb-5fff4163621d  |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/1b46f42e-1f4c-48d4-b9f8-225ff72d510a |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/00862568-e2b2-41a7-9eeb-5fff4163621d  |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/1b46f42e-1f4c-48d4-b9f8-225ff72d510a |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/00862568-e2b2-41a7-9eeb-5fff4163621d  |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/1b46f42e-1f4c-48d4-b9f8-225ff72d510a |
And [API] the CLP visualization is switched OFF for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the timeline is zoomed in to max resolution
And the "Examination of joint under image intensifier (procedure)" event MARKER is available on the "Imaging" swimlane at "Sep 09, 2022" without any badges
And the "History and physical note - 3" event MARKER is available on the "Encounters" swimlane at "Apr 22, 2011" with the below badges:
| badge_type     | badge_text       |
| CLASSIFICATION | Outpatient visit |
And the following event point is selected on the "Encounters" swimlane:
| event_type | name_on_label                 |
| MARKER     | History and physical note - 3 |
And the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type     | badge_text       |
| CLASSIFICATION | Outpatient visit |
And the following event point is selected on the "Imaging" swimlane:
| event_type | name_on_label                                            |
| MARKER     | Examination of joint under image intensifier (procedure) |
And the sidebar is displayed
And the sidebar header does not contain any badges
And the sidebar content contains the following data:
| data_type | title                | value  |
| KEY-VALUE | Conclusion           | STABLE |
| KEY-VALUE | Status               | final  |
| LINK      | Open complete report | <N/A>  |
And the "Settings" menu item is selected on the user menu panel
And the Settings page is displayed
And the following settings are available on the page:
| settings_title               | setting_value |
| Artificial Intelligence (AI) | Off           |
When the user clicks on 'Edit' button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user selects the "On" radio button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user clicks on the "Save" button on the Settings page
Then the following settings are available on the page:
| settings_title               | setting_value |
| Artificial Intelligence (AI) | On            |
When the user clicks on the "Back" button in the Settings toolbar header
Then the patient timeline is loaded
And the "Examination of joint under image intensifier (procedure)" event MARKER is available on the "Imaging" swimlane with the below badges:
| badge_type | badge_text                                      |
| CLP        | Partial tear and fracture due to recent trauma. |
And the "History and physical note - 3" event MARKER is available on the "Encounters" swimlane with 2 miniaturized badges:
| badge_types_in_order |
| CLASSIFICATION       |
| CLP                  |
And the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type | badge_text                                      |
| CLP        | Partial tear and fracture due to recent trauma. |
And the sidebar content contains the following data:
| data_type | title                | value  | value_from_file                                                |
| KEY-VALUE | Conclusion           | STABLE | <N/A>                                                          |
| KEY-VALUE | Status               | final  | <N/A>                                                          |
| LINK      | Open complete report | <N/A>  | <N/A>                                                          |
| CLP_INFO  | <N/A>                | <N/A>  | L2/CLP/OncoTestPatient_Juno_Examination_of_joint_truncated.txt |
When the user clicks on the following event point on the "Encounters" swimlane:
| event_type | name_on_label                 |
| MARKER     | History and physical note - 3 |
Then the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type     | badge_text                |
| CLASSIFICATION | Outpatient visit          |
| CLP            | [keywords]: Xelox ongoing |

@SRS-3.03
@CLP
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68583,SysRS-68587
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Settings
Scenario: CLP visualization setting is saved and it is kept when the user logs in to OncoCare again
Given [API] the following events were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/1b46f42e-1f4c-48d4-b9f8-225ff72d510a |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/1b46f42e-1f4c-48d4-b9f8-225ff72d510a |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/1b46f42e-1f4c-48d4-b9f8-225ff72d510a |
And [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the "History and physical note - 3" event MARKER is available on the "Encounters" swimlane with 2 miniaturized badges:
| badge_types_in_order |
| CLASSIFICATION       |
| CLP                  |
And the "Settings" menu item is selected on the user menu panel
And the Settings page is displayed
And the following settings are available on the page:
| settings_title               | setting_value |
| Artificial Intelligence (AI) | On            |
When the user clicks on 'Edit' button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user selects the "Off" radio button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user clicks on the "Save" button on the Settings page
Then the following settings are available on the page:
| settings_title               | setting_value |
| Artificial Intelligence (AI) | Off           |
When the user logs out from OncoCare
Then the user is navigated to the OncoCare splash screen
When the [DEFAULT-TEST-USER] user logs in to OncoCare
And the user selects the "OncoTestPatient, Juno" patient
And the user navigates to the "Timeline" view
Then the patient timeline is loaded
And the "History and physical note - 3" event MARKER is available on the "Encounters" swimlane at "Apr 22, 2011" with the below badges:
| badge_type     | badge_text       |
| CLASSIFICATION | Outpatient visit |

@SRS-3.04
@sanity
@ai_sanity
@CLP
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68583,SysRS-68587
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Settings
Scenario: CLP visualization setting changes by a user do not affect the settings of another user
Given [API] the following events were not marked as important for [TEST-USER-2] user:
| patientID                            | root_resource_of_the_event                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/1b46f42e-1f4c-48d4-b9f8-225ff72d510a |
And [API] the [TEST-USER-2] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/1b46f42e-1f4c-48d4-b9f8-225ff72d510a |
And [API] the [TEST-USER-2] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/1b46f42e-1f4c-48d4-b9f8-225ff72d510a |
And [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And [API] the CLP visualization is switched ON for [TEST-USER-2] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the "History and physical note - 3" event MARKER is available on the "Encounters" swimlane with 2 miniaturized badges:
| badge_types_in_order |
| CLASSIFICATION       |
| CLP                  |
And the "Settings" menu item is selected on the user menu panel
And the Settings page is displayed
And the following settings are available on the page:
| settings_title               | setting_value |
| Artificial Intelligence (AI) | On            |
When the user clicks on 'Edit' button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user selects the "Off" radio button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user clicks on the "Save" button on the Settings page
Then the following settings are available on the page:
| settings_title               | setting_value |
| Artificial Intelligence (AI) | Off           |
When the user logs out from OncoCare
Then the user is navigated to the OncoCare splash screen
When the [TEST-USER-2] user logs in to OncoCare
And the user selects the "OncoTestPatient, Juno" patient
And the user navigates to the "Timeline" view
Then the patient timeline is loaded
And the "History and physical note - 3" event MARKER is available on the "Encounters" swimlane with 2 miniaturized badges:
| badge_types_in_order |
| CLASSIFICATION       |
| CLP                  |

@SRS-3.05
@CLP
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68583
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Settings
Scenario: CLP visualization setting changes are not saved if editing CLP visualization process is cancelled
Given [API] the following events were not marked as important for [TEST-USER-2] user:
| patientID                            | root_resource_of_the_event                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/1b46f42e-1f4c-48d4-b9f8-225ff72d510a |
And [API] the [TEST-USER-2] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/1b46f42e-1f4c-48d4-b9f8-225ff72d510a |
And [API] the [TEST-USER-2] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/1b46f42e-1f4c-48d4-b9f8-225ff72d510a |
And [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the "History and physical note - 3" event MARKER is available on the "Encounters" swimlane with 2 miniaturized badges:
| badge_types_in_order |
| CLASSIFICATION       |
| CLP                  |
And the "Settings" menu item is selected on the user menu panel
And the Settings page is displayed
And the following settings are available on the page:
| settings_title               | setting_value |
| Artificial Intelligence (AI) | On            |
When the user clicks on 'Edit' button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user selects the "Off" radio button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user clicks on the "Cancel" button on the Settings page
Then the following settings are available on the page:
| settings_title               | setting_value |
| Artificial Intelligence (AI) | On            |
When the user clicks on the "Back" button in the Settings toolbar header
Then the patient timeline is loaded
And the "History and physical note - 3" event MARKER is available on the "Encounters" swimlane with 2 miniaturized badges:
| badge_types_in_order |
| CLASSIFICATION       |
| CLP                  |

@SRS-3.06
@CLP
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68583
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Settings
Scenario: CLP filter option is not displayed if CLP visualization is switched off
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
When the user opens the timeline filter modal
Then the following timeline filter option is displayed on the filter modal:
| filter_group | checkbox_name | selected |
| Labels       | AI enriched   | false    |
When the user closes the timeline filter modal
And the user clicks on the "Settings" menu item on the user menu panel
Then the Settings page is displayed
And the following settings are available on the page:
| settings_title               | setting_value |
| Artificial Intelligence (AI) | On            |
When the user clicks on 'Edit' button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user selects the "Off" radio button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user clicks on the "Save" button on the Settings page
And the user clicks on the "Back" button in the Settings toolbar header
Then the patient timeline is loaded
When the user opens the timeline filter modal
Then the following timeline filter option is not displayed on the filter modal:
| filter_group | checkbox_name | selected |
| Labels       | AI enriched   | false    |

@SRS-3.07
@CLP
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68583
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Settings
Scenario: Switch on/off CLP visualization for the user (Patient banner)
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Franklin" patient
Then the patient banner includes the following data:
| data_type | title           | value             |
| CLP_INFO  | Stage - initial | [keywords]: stage |
When the user clicks on the "Settings" menu item on the user menu panel
Then the Settings page is displayed
When the user clicks on 'Edit' button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user selects the "Off" radio button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user clicks on the "Save" button on the Settings page
And the user clicks on the "Back" button in the Settings toolbar header
Then the patient summary view is loaded
And the patient banner includes the following data:
| data_type | title           | value                    |
| KEY-VALUE | Stage - initial | No Stage - initial found |
When the user clicks on the "Settings" menu item on the user menu panel
Then the Settings page is displayed
When the user clicks on 'Edit' button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user selects the "On" radio button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user clicks on the "Save" button on the Settings page
And the user clicks on the "Back" button in the Settings toolbar header
Then the patient summary view is loaded
And the patient banner includes the following data:
| data_type | title           | value             |
| CLP_INFO  | Stage - initial | [keywords]: stage |

@SRS-3.08
@CLP
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68583,SysRS-68596
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Settings
Scenario: Switch on/off CLP visualization for the user (Summary view)
Given [API] the CLP visualization is switched OFF for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Franklin" patient
Then the patient summary view is loaded
And the AI info on the Summary View contains the following text: "This page is best viewed with AI.Turn on AI"
And the 2st card in "Diagnosis" group equals the following data:
| data_type   | data           |
| EMPTY_STATE | No stage found |
When the user clicks on the "Turn on AI" button on the AI info
Then the AI info on the Summary View contains the following text: "The data highlighted in blue or marked on this page is a summary produced by generative artificial intelligence and is intended to supplement the existing patient data, may be incomplete or incorrect, and is not intended to replace the provider's judgment or responsibility to review the source original clinical information."
And the 2st card in "Diagnosis" group equals the following data:
| data_type  | data                                                |
| CARD_TITLE | Stage                                               |
| TABLE      | [source]: summary/table_on_stage_crd_Franklin.table |
When the user clicks on the "Settings" menu item on the user menu panel
Then the Settings page is displayed
When the user clicks on 'Edit' button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user selects the "Off" radio button at the following user setting:
| settings_title               |
| Artificial Intelligence (AI) |
And the user clicks on the "Save" button on the Settings page
And the user clicks on the "Back" button in the Settings toolbar header
Then the patient summary view is loaded
And the AI info on the Summary View contains the following text: "This page is best viewed with AI.Turn on AI"
And the 2st card in "Diagnosis" group equals the following data:
| data_type   | data           |
| EMPTY_STATE | No stage found |
