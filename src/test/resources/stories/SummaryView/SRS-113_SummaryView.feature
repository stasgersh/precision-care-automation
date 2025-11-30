
Feature: [SysRS-68478] Summary View

Narrative: The system shall provide Summary view with patient history information (lab results including biomarkers, vitals, diagnosis, pathology, images, medications, allergies, treatment, and encounters) divided into the following categories:
             - Diagnosis
             - Molecular profile
             - Care team
             - Imaging - most recent
             - ER visit - most recent
             - MDT report - most recent
             - Comments - most recent
             - Treatment and Plan
             - Labs
             - Oncology note - most recent
             - Medical history see Medical History View section.


@sanity
@edge
@SRS-113.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68478
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
Scenario: Cards are grouped on Summary view
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Empty" patient is selected
When the user navigates to the "Summary" view
Then the Summary view contains the following groups:
| column_index | group_index_in_column | group_name        | badge_text  |
| 0            | 0                     | Diagnosis         | <N/A>       |
| 0            | 1                     | Molecular profile | <N/A>       |
| 0            | 2                     | Care team         | <N/A>       |
| 1            | 0                     | Imaging           | Most recent |
| 1            | 1                     | Oncology note     | Most recent |
| 1            | 2                     | ER visit          | Most recent |
| 1            | 3                     | MDT report        | Most recent |
| 2            | 0                     | Treatments        | <N/A>       |
| 2            | 1                     | Labs              | <N/A>       |
| 2            | 2                     | Medical history   | <N/A>       |
| 2            | 3                     | Comments          | Most recent |

@sanity
@edge
@SRS-113.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68478,SysRS-68488
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
Scenario: Empty state cards are displayed in 'Diagnosis' group if relevant data is not available
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Empty" patient is selected
When the user navigates to the "Summary" view
Then the "Diagnosis" group has 3 cards
And the 1st card in "Diagnosis" group equals the following data:
| data_type   | data               |
| EMPTY_STATE | No diagnosis found |
And the 2st card in "Diagnosis" group equals the following data:
| data_type   | data           |
| EMPTY_STATE | No stage found |
And the 3st card in "Diagnosis" group equals the following data:
| data_type   | data                |
| EMPTY_STATE | No key events found |
And the 'Show on timeline' button is not enabled in the footer of the below cards:
| group_name | card_index_in_group |
| Diagnosis  | 0                   |
| Diagnosis  | 1                   |
| Diagnosis  | 2                   |
And the 'Full report' button is not enabled in the footer of the below cards:
| group_name | card_index_in_group |
| Diagnosis  | 0                   |
| Diagnosis  | 1                   |
| Diagnosis  | 2                   |


@uFMEA
@sanity
@SRS-113.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68478,SysRS-68488
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Summary_View
Scenario: Empty state cards are displayed in grey color in 'Diagnosis','Molecular profile','Care team','Imaging','Oncology note','ER visit','MDT report','Labs','Treatments' groups if relevant data is not available
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Empty" patient is selected
When the user navigates to the "Summary" view
Then the "Diagnosis" group has 3 cards
And the 1st card in "Diagnosis" group equals the following data:
| data_type   | data               | color   |
| EMPTY_STATE | No diagnosis found | #929498 |
And the 2st card in "Diagnosis" group equals the following data:
| data_type   | data           | color   |
| EMPTY_STATE | No stage found | #929498 |
And the 3st card in "Diagnosis" group equals the following data:
| data_type   | data                | color   |
| EMPTY_STATE | No key events found | #929498 |
And the "Molecular profile" group has 1 card
And the 1st card in "Molecular profile" group equals the following data:
| data_type   | data                       |
| EMPTY_STATE | No molecular profile found |
And the "Care team" group has 1 card
And the 1st card in "Care team" group equals the following data:
| data_type   | data               | color   |
| EMPTY_STATE | No care team found | #929498 |
And the "Imaging" group has 2 cards
And the 1st card in "Imaging" group equals the following data:
| data_type   | data             | color   |
| EMPTY_STATE | No imaging found | #929498 |
And the 2st card in "Imaging" group equals the following data:
| data_type   | data                     | color   |
| EMPTY_STATE | No imaging history found | #929498 |
And the "Oncology note" group has 1 card
And the 1st card in "Oncology note" group equals the following data:
| data_type   | data                   | color   |
| EMPTY_STATE | No oncology note found | #929498 |
And the "ER visit" group has 1 card
And the 1st card in "ER visit" group equals the following data:
| data_type   | data              | color   |
| EMPTY_STATE | No ER visit found | #929498 |
And the "MDT report" group has 1 card
And the 1st card in "MDT report" group equals the following data:
| data_type   | data                | color   |
| EMPTY_STATE | No MDT report found | #929498 |
And the "Treatments" group has 4 cards
And the 1st card in "Treatments" group equals the following data:
| data_type   | data                      | color   |
| EMPTY_STATE | No systemic therapy found | #929498 |
And the 2st card in "Treatments" group equals the following data:
| data_type   | data                       | color   |
| EMPTY_STATE | No radiation therapy found | #929498 |
And the "Labs" group has 1 cards
And the 1st card in "Labs" group equals the following data:
| data_type   | data                 | color   |
| EMPTY_STATE | No lab history found | #929498 |
And the "Comments" group has 1 card
And the 1st card in "Comments" group equals the following data:
| data_type   | data              | color   |
| EMPTY_STATE | No comments found | #929498 |


@SRS-113.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68478,SysRS-68488
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Summary_View
Scenario: Empty state card is displayed in 'Molecular profile' group if relevant data is not available
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Empty" patient is selected
When the user navigates to the "Summary" view
Then the "Molecular profile" group has 1 card
And the 1st card in "Molecular profile" group equals the following data:
| data_type   | data                       |
| EMPTY_STATE | No molecular profile found |
And the 'Show on timeline' button is not enabled in the footer of the below cards:
| group_name        | card_index_in_group |
| Molecular profile | 0                   |
And the 'Full report' button is not enabled in the footer of the below cards:
| group_name        | card_index_in_group |
| Molecular profile | 0                   |

@SRS-113.04
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68478,SysRS-68488
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Summary_View
Scenario: Empty state card is displayed in 'Care team' group if relevant data is not available
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Empty" patient is selected
When the user navigates to the "Summary" view
Then the "Care team" group has 1 card
And the 1st card in "Care team" group equals the following data:
| data_type   | data               |
| EMPTY_STATE | No care team found |
And footer is not available on the 1st card in "Care team" group

@SRS-113.06
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68478,SysRS-68488
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Summary_View
Scenario: Empty state cards are displayed in 'Imaging - most recent' group if relevant data is not available
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Empty" patient is selected
When the user navigates to the "Summary" view
Then the "Imaging" group has 2 cards
And the 1st card in "Imaging" group equals the following data:
| data_type   | data             |
| EMPTY_STATE | No imaging found |
And the 2st card in "Imaging" group equals the following data:
| data_type   | data                     |
| EMPTY_STATE | No imaging history found |
And the 'Show on timeline' button is not enabled in the footer of the below cards:
| group_name | card_index_in_group |
| Imaging    | 0                   |
| Imaging    | 1                   |
And the 'Full report' button is not enabled in the footer of the below cards:
| group_name | card_index_in_group |
| Imaging    | 0                   |
| Imaging    | 1                   |

@SRS-113.08
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68478,SysRS-68488
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Summary_View
Scenario: Empty state cards are displayed in 'ER visit - most recent' group if relevant data is not available
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Empty" patient is selected
When the user navigates to the "Summary" view
Then the "ER visit" group has 1 card
And the 1st card in "ER visit" group equals the following data:
| data_type   | data              |
| EMPTY_STATE | No ER visit found |
And the 'Show on timeline' button is not enabled in the footer of the below cards:
| group_name | card_index_in_group |
| ER visit   | 0                   |
And the 'Full report' button is not enabled in the footer of the below cards:
| group_name | card_index_in_group |
| ER visit   | 0                   |

@SRS-113.09
@SPR-4839
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68478,SysRS-68488
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Summary_View
Scenario: Empty state cards are displayed in 'MDT report - most recent' group if relevant data is not available
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Empty" patient is selected
When the user navigates to the "Summary" view
Then the "MDT report" group has 1 card
And the 1st card in "MDT report" group equals the following data:
| data_type   | data                |
| EMPTY_STATE | No MDT report found |
When the user hovers the 'Full report' button in the footer of the below card:
| group_name | card_index_in_group |
| MDT report | 0                   |
Then tooltip is displayed with text: Report could not be found. It may exist somewhere outside the application, like in another hospital or system this application is not connected to.

@SRS-113.10
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68478,SysRS-68488
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Summary_View
Scenario: Empty state card is displayed in 'Comments - most recent' group if relevant data is not available
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Empty" patient is selected
When the user navigates to the "Summary" view
Then the "Comments" group has 1 card
And the 1st card in "Comments" group equals the following data:
| data_type   | data              |
| EMPTY_STATE | No comments found |
And footer is not available on the 1st card in "Comments" group

@SRS-113.11
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68478,SysRS-68488,SysRS-69205
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Summary_View
Scenario: Empty state cards are displayed in 'Treatments' group if relevant data is not available
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Empty" patient is selected
When the user navigates to the "Summary" view
Then the "Treatments" group has 4 cards
And the 1st card in "Treatments" group equals the following data:
| data_type   | data                      |
| EMPTY_STATE | No systemic therapy found |
And the 2st card in "Treatments" group equals the following data:
| data_type   | data                       |
| EMPTY_STATE | No radiation therapy found |
And the 'Show on timeline' button is not enabled in the footer of the below cards:
| group_name | card_index_in_group |
| Treatments | 0                   |
| Treatments | 1                   |
And the 'Full report' button is not enabled in the footer of the below cards:
| group_name | card_index_in_group |
| Treatments | 0                   |
| Treatments | 1                   |

@SRS-113.13
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68478,SysRS-68488
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Summary_View
Scenario: Empty state cards are displayed in 'Labs' group if relevant data is not available
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Empty" patient is selected
When the user navigates to the "Summary" view
Then the "Labs" group has 1 cards
And the 1st card in "Labs" group equals the following data:
| data_type   | data                 |
| EMPTY_STATE | No lab history found |
And the 'Show on timeline' button is not enabled in the footer of the below cards:
| group_name | card_index_in_group |
| Labs       | 0                   |
And the 'Full report' button is not enabled in the footer of the below cards:
| group_name | card_index_in_group |
| Labs       | 0                   |

@SRS-113.14
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68478,SysRS-68488
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Summary_View
Scenario: Empty state cards are displayed in 'Oncology note - most recent' group if relevant data is not available
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Empty" patient is selected
When the user navigates to the "Summary" view
Then the "Oncology note" group has 1 card
And the 1st card in "Oncology note" group equals the following data:
| data_type   | data                   |
| EMPTY_STATE | No oncology note found |
And the 'Show on timeline' button is not enabled in the footer of the below cards:
| group_name    | card_index_in_group |
| Oncology note | 0                   |
And the 'Full report' button is not enabled in the footer of the below cards:
| group_name    | card_index_in_group |
| Oncology note | 0                   |

@SRS-113.15
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68478
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Summary_View
Scenario: Medical history links are available on the 'Medical history' card even if relevant data is not available
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Empty" patient is selected
When the user navigates to the "Summary" view
Then the "Medical history" group has 1 card
And the 1st card in "Medical history" group equals the following data:
| data_type | data                                           |
| LINK      | [link]: Comorbidities              [date]: N/A |
| LINK      | [link]: Medications                [date]: N/A |
| LINK      | [link]: Allergies and intolerances [date]: N/A |
| LINK      | [link]: Patient history            [date]: N/A |
And footer is not available on the 1st card in "Care team" group

@SRS-113.20
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68478
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Summary_View
Scenario: Users see only their own comments on 'Comments - most recent
Given the user is logged out from OncoCare application
And [API] the [DEFAULT-TEST-USER] user has neither patient nor event comments for the following patients:
| patientID                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a |
And [API] the [TEST-USER-2] user has neither patient nor event comments for the following patients:
| patientID                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a |
And [API] the below patient comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | comment_text                          |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | this is a patient comment 1 - default |
And [API] the below patient comments were added previously by the user [TEST-USER-2]:
| patientID                            | comment_text                         |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | this is a patient comment 1 - user-2 |
And [API] the below event comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | root_resource_of_the_event                       | comment_text                       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f | This is an event comment - default |
And [API] the below event comments were added previously by the user [TEST-USER-2]:
| patientID                            | root_resource_of_the_event                       | comment_text                      |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f | This is an event comment - user-2 |
And the [TEST-USER-2] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
When the user navigates to the "Summary" view
Then the "Comments" group has 1 card
And the 1st card in "Comments" group equals the following data:
| data_type | data                                                |
| COMMENTS  | [source]: summary/comments_on_card_SRS-113.20.table |
| LINK      | [link]: Open all comments     [date]: N/A           |

@SRS-113.21
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68478,SysRS-68479
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
Scenario: 'Comments - most recent' card contains the latest 3 comments (latest on the top)
Given the user is logged out from OncoCare application
And [API] the [DEFAULT-TEST-USER] user has neither patient nor event comments for the following patients:
| patientID                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a |
And [API] the comment settings are reset for "OncoTestPatient, Juno" by the [DEFAULT-TEST-USER] user
And [API] the below patient comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | comment_text                |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | this is a patient comment 1 |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | this is a patient comment 2 |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | this is a patient comment 3 |
And [API] the below event comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | root_resource_of_the_event                       | comment_text               |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f | This is an event comment 1 |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f | This is an event comment 2 |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
When the user navigates to the "Summary" view
Then the "Comments" group has 1 card
And the 1st card in "Comments" group equals the following data:
| data_type | data                                                |
| COMMENTS  | [source]: summary/comments_on_card_SRS-113.21.table |
| LINK      | [link]: Open all comments     [date]: N/A           |
When the user clicks on the following data item on the 1st card in "Comments" group:
| data_type | data                                      |
| LINK      | [link]: Open all comments     [date]: N/A |
Then the Comments view is displayed
And the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time | content                     | badge            | has_view_on_timeline_link |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | this is a patient comment 1 | <N/A>            | <N/A>                     |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | this is a patient comment 2 | <N/A>            | <N/A>                     |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | this is a patient comment 3 | <N/A>            | <N/A>                     |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | This is an event comment 1  | Timeline comment | yes                       |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | This is an event comment 2  | Timeline comment | yes                       |

@SRS-113.22
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68478
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Summary_View
Scenario: User can navigate to the timeline event from event comment at 'Comments - most recent'
Given the user is logged out from OncoCare application
And [API] the [DEFAULT-TEST-USER] user has neither patient nor event comments for the following patients:
| patientID                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a |
And [API] the below patient comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | comment_text                |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | this is a patient comment 1 |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | this is a patient comment 2 |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | this is a patient comment 3 |
And [API] the below event comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | root_resource_of_the_event                       | comment_text               |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f | This is an event comment 1 |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f | This is an event comment 2 |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
When the user navigates to the "Summary" view
Then the "Comments" group has 1 card
And the 1st card in "Comments" group equals the following data:
| data_type | data                                                |
| COMMENTS  | [source]: summary/comments_on_card_SRS-113.21.table |
| LINK      | [link]: Open all comments     [date]: N/A           |
When the user clicks on the 'View on timeline' button on the following comment on the 1st card in "Comments" group:
| author (firstname lastname) | date_and_time | content                    | badge            | has_view_on_timeline_link |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>   | This is an event comment 1 | Timeline comment | yes                       |
Then the patient timeline is loaded
And the sidebar is displayed
And the sidebar header contains the following data:
| title               | creation_date | report_type |
| Body Weight 47.3 kg | Jan 23, 2013  | Encounters  |
And the sidebar contains the following comments:
| author (firstname lastname) | date_and_time           | content                    |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | This is an event comment 1 |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | This is an event comment 2 |

@SRS-113.23
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68478
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/VPR_List_-_Manual/Functional_Service/Summary_View
Scenario: Icons are visible for card group titles on Summary Dashboard with different view
Given I logged in to OncoCare application
When I selected the "OncoTestPatient, Juno" patient
And the "Summary" view is displayed
Then the following Summary groups are available in the Summary dashboard and have the following icons:
| group_name                  | reference_card_icon                                                          |
| Diagnosis                   | testdata/card_iconography/Card_icon_library.svg: Diagnosis                   |
| Molecular profile           | testdata/card_iconography/Card_icon_library.svg: Molecular profile           |
| Care team                   | testdata/card_iconography/Card_icon_library.svg: Care team                   |
| Imaging - most recent       | testdata/card_iconography/Card_icon_library.svg: Imaging - most recent       |
| Oncology note - most recent | testdata/card_iconography/Card_icon_library.svg: Oncology note - most recent |
| ER visit - most recent      | testdata/card_iconography/Card_icon_library.svg: ER visit - most recent      |
| MDT report - most recent    | testdata/card_iconography/Card_icon_library.svg: MDT report - most recent    |
| Treatments                  | testdata/card_iconography/Card_icon_library.svg: Treatments                  |
| Labs                        | testdata/card_iconography/Card_icon_library.svg: Labs                        |
| Comments - most recent      | testdata/card_iconography/Card_icon_library.svg: Comments - most recent      |
| Medical history             | testdata/card_iconography/Card_icon_library.svg: Medical history             |
When I selected "Edit order and visibility" option from "More" menu
And set the following order for the Summary cards:
| Column1           | Column2       | Column3         |
| Molecular profile | Imaging       | Diagnosis       |
| Care team         | Oncology note | Treatments      |
| ER visit          | Labs          | Medical history |
|                   | MDT report    | Comments        |
Then the following Summary groups are available in the Summary dashboard and have the following icons:
| group_name                  | reference_card_icon                                                          |
| Diagnosis                   | testdata/card_iconography/Card_icon_library.svg: Diagnosis                   |
| Molecular profile           | testdata/card_iconography/Card_icon_library.svg: Molecular profile           |
| Care team                   | testdata/card_iconography/Card_icon_library.svg: Care team                   |
| Imaging - most recent       | testdata/card_iconography/Card_icon_library.svg: Imaging - most recent       |
| Oncology note - most recent | testdata/card_iconography/Card_icon_library.svg: Oncology note - most recent |
| ER visit - most recent      | testdata/card_iconography/Card_icon_library.svg: ER visit - most recent      |
| MDT report - most recent    | testdata/card_iconography/Card_icon_library.svg: MDT report - most recent    |
| Treatments                  | testdata/card_iconography/Card_icon_library.svg: Treatments                  |
| Labs                        | testdata/card_iconography/Card_icon_library.svg: Labs                        |
| Comments - most recent      | testdata/card_iconography/Card_icon_library.svg: Comments - most recent      |
| Medical history             | testdata/card_iconography/Card_icon_library.svg: Medical history             |
When I selected "Hide empty cards" option from "More" menu
Then the following Summary groups are available in the Summary dashboard and have the following icons:
| group_name                  | reference_card_icon                                                          |
| Diagnosis                   | testdata/card_iconography/Card_icon_library.svg: Diagnosis                   |
| Molecular profile           | testdata/card_iconography/Card_icon_library.svg: Molecular profile           |
| Imaging - most recent       | testdata/card_iconography/Card_icon_library.svg: Imaging - most recent       |
| Oncology note - most recent | testdata/card_iconography/Card_icon_library.svg: Oncology note - most recent |
| MDT report - most recent    | testdata/card_iconography/Card_icon_library.svg: MDT report - most recent    |
| Treatments                  | testdata/card_iconography/Card_icon_library.svg: Treatments                  |
| Labs                        | testdata/card_iconography/Card_icon_library.svg: Labs                        |
| Medical history             | testdata/card_iconography/Card_icon_library.svg: Medical history             |
And the following Summary groups and its icons are NOT available in the Summary dashboard:
| group_name             | reference_card_icon                                                     |
| Care team              | testdata/card_iconography/Card_icon_library.svg: Care team              |
| ER visit - most recent | testdata/card_iconography/Card_icon_library.svg: ER visit - most recent |
| Comments - most recent | testdata/card_iconography/Card_icon_library.svg: Comments - most recent |


@SRS-113.24
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69212
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
@Description:<p>Lab_results_card_contains_following_information:</p><ul><li>Lab_parameter_(result_report_+_hyperlink_to_the_report)</li><li>Value_of_lab_result</li><li>Lab_result_unit_-_if_exist</li><li>Date_of_lab_test</li></ul>
Scenario: Labs card displays the most recent available value with all required information
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the patient summary view is loaded
When the user clicks on the "Cholesterol, total" link at the following data on the 1st card in "Labs" group:
| data_type | data                                            |
| LINK      | [link]: Cholesterol, total [date]: Jun 07, 2017 |
Then the Summary Modal Viewer contains the following "Results" table:
| Test name       | Result | Unit    | Flag     | Reference range |
| Cholesterol     | 220    | mg/dL   | Abnormal | 0 - 200 mg/dL   |
| HDL             | 38     | <EMPTY> | <EMPTY>  | 35 - 70         |
| LDL Cholesterol | 135    | <EMPTY> | <EMPTY>  | <EMPTY>         |
| Triglycerides   | 145    | mg/dL   | <EMPTY>  | 40 - 160 mg/dL  |

