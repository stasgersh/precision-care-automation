Feature: [SysRS-68535] CLP Info on Timeline, Patient Banner and Summary view

Narrative: The system shall be able to display AI/CLP based info on the following:
             - The event labels
             - Side panel
             - Patient banner
             - Summary view
             - Response tracking
             - Clinical trial eligibility
             - Treatment adherence


@SRS-37.01
@sanity
@edge
@ai_sanity
@CLP
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68535,SysRS-68383
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Timeline_section
Scenario: CLP info is displayed in the event label if it is available for the event
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And [API] the following events were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/e6266783-f448-4881-84c4-4b143599d533 |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                            |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/e6266783-f448-4881-84c4-4b143599d533 |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                            |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/e6266783-f448-4881-84c4-4b143599d533 |
When the user selects the "OncoTestPatient, Torvald" patient
And the user navigates to the "Timeline" view
Then the "Examination of joint under image intensifier (procedure)" event MARKER is available on the "Imaging" swimlane at "Sep 09, 2015" with the below badges:
| badge_type | badge_text                                             |
| CLP        | Right knee joint is normal, no abnormalities detected. |

@SRS-37.02
@SPR-4793
@sanity
@ai_sanity
@CLP
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68535,SysRS-68383
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Timeline_section
Scenario: L2 CLP info is displayed on the side panel
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And [API] the following events were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc |
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event is available on the "Imaging" swimlane:
| event_type | name_on_label                          |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST |
When the user clicks on the following event point on the "Imaging" swimlane:
| event_type | name_on_label                          |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST |
Then the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type     | badge_text                                                |
| CLASSIFICATION | CT                                                        |
| CLP            | No evidence of pulmonary embolism or abdominal pathology. |
And the sidebar content contains the following data:
| data_type | title                | value                                                                                                         |
| KEY-VALUE | Conclusion           | STABLE                                                                                                        |
| KEY-VALUE | Status               | final                                                                                                         |
| LINK      | Open complete report | <N/A>                                                                                                         |
| KEY-VALUE | Reason               | peritoneal cancer diagnosed in 2015, response to chemotherapy but no surgery, 6 monthly follow up scan please |
| KEY-VALUE | Body site            | Cross-sectional abdomen, Cross-sectional pelvis, Cross-sectional thorax                                       |
| CLP_INFO  | <N/A>                | [keywords]: CT Thorax Abdomen Pelvis with Contrast, no evidence                                               |
When the user clicks on the "Show more" button under the AI generated text on the sidebar
Then the sidebar content contains the following data:
| data_type | title                | value                                                                                                         |
| KEY-VALUE | Conclusion           | STABLE                                                                                                        |
| KEY-VALUE | Status               | final                                                                                                         |
| LINK      | Open complete report | <N/A>                                                                                                         |
| KEY-VALUE | Reason               | peritoneal cancer diagnosed in 2015, response to chemotherapy but no surgery, 6 monthly follow up scan please |
| KEY-VALUE | Body site            | Cross-sectional abdomen, Cross-sectional pelvis, Cross-sectional thorax                                       |
| CLP_INFO  | <N/A>                | [keywords]: Further evaluation, is recommended                                                                |
When the user clicks on the "Show less" button under the AI generated text on the sidebar
Then the sidebar content contains the following data:
| data_type | title                | value                                                                                                         |
| KEY-VALUE | Conclusion           | STABLE                                                                                                        |
| KEY-VALUE | Status               | final                                                                                                         |
| LINK      | Open complete report | <N/A>                                                                                                         |
| KEY-VALUE | Reason               | peritoneal cancer diagnosed in 2015, response to chemotherapy but no surgery, 6 monthly follow up scan please |
| KEY-VALUE | Body site            | Cross-sectional abdomen, Cross-sectional pelvis, Cross-sectional thorax                                       |
| CLP_INFO  | <N/A>                | [keywords]: CT Thorax Abdomen Pelvis with Contrast, no evidence                                               |

@SRS-37.03
@sanity
@ai_sanity
@CLP
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68535,SysRS-68383
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Timeline_section
Scenario: CLP info is displayed in patient banner
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Franklin" patient
Then the patient banner includes the following data:
| data_type | title           | value             |
| CLP_INFO  | Stage - initial | [keywords]: stage |

@SRS-37.04
@integration
@CLP
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68535,SysRS-68383
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Automated/Timeline_section
Scenario: CLP info is displayed on Summary view
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Franklin" patient
Then the patient summary view is loaded
And the 1st card in "Imaging" group equals the following data:
| data_type   | data                                                                                                                                              |
| CARD_TITLE  | Imaging - most recent                                                                                                                             |
| NORMAL_TEXT | CT THORAX ABDOMEN PELVIS WITH CONTRAST                                                                                                            |
| CLP_DATA    | [keywords]: stage 4, rising CA125 levels, abdominal pain, no enlarged thoracic,pleura are clear, cystic lesion, pelvic sidewall remains unchanged |
| KEY_VALUE   | [key]: Date            [value]: Sep 28, 1975                                                                                                      |
And the 2st card in "Diagnosis" group equals the following data:
| data_type  | data                                                |
| CARD_TITLE | Stage                                               |
| TABLE      | [source]: summary/table_on_stage_crd_Franklin.table |

@SRS-37.05
@CLP
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68535
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Automated/Timeline_section
Scenario: Long CLP info is displayed on Summary view
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Freya" patient
Then the patient summary view is loaded
And the 1st card in "Imaging" group equals the following data:
| data_type   | data                                        |
| CARD_TITLE  | Imaging - most recent                       |
| NORMAL_TEXT | CT THORAX ABDOMEN PELVIS WITH CONTRAST      |
| CLP_DATA    | [keywords]: peritoneal cancer, chemotherapy |
| KEY_VALUE   | [key]: Date       [value]: Sep 28, 2011     |
When the user clicks on the "Show more" button at the following data on the 1st card in "Imaging" group:
| data_type | data                                        |
| CLP_DATA  | [keywords]: peritoneal cancer, chemotherapy |
Then the 1st card in "Imaging" group equals the following data:
| data_type   | data                                                      |
| CARD_TITLE  | Imaging - most recent                                     |
| NORMAL_TEXT | CT THORAX ABDOMEN PELVIS WITH CONTRAST                    |
| CLP_DATA    | [keywords]: peritoneal cancer, no significant progression |
| KEY_VALUE   | [key]: Date       [value]: Sep 28, 2011                   |
When the user clicks on the "Show less" button at the following data on the 1st card in "Imaging" group:
| data_type | data                                                      |
| CLP_DATA  | [keywords]: peritoneal cancer, no significant progression |
Then the 1st card in "Imaging" group equals the following data:
| data_type   | data                                        |
| CARD_TITLE  | Imaging - most recent                       |
| NORMAL_TEXT | CT THORAX ABDOMEN PELVIS WITH CONTRAST      |
| CLP_DATA    | [keywords]: peritoneal cancer, chemotherapy |
| KEY_VALUE   | [key]: Date       [value]: Sep 28, 2011     |
