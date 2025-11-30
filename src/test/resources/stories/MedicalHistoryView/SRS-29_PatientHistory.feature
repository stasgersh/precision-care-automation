Feature: [SysRS-68560] Patient History

Narrative: The system shall display the following Patient History info of a patient:
             - Family history,
             - Social history,
             - Substance history and
             - Environmental/occupation history.


@SPR-4702
@sanity
@edge
@SRS-29.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68390,SysRS-68560
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Medical_History_View
Scenario: Patient history includes the family, social, substance and surgical history
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
When the user navigates to the "Medical history" view
Then the "Patient history" section is available in Medical history
And the "Patient history" section contains the following data in Medical history:
| data_type     | title             | value        |
| SECTION_TITLE | Patient history   | N/A          |
| TABLE         | Surgical history  | <DATA_TABLE> |
| TABLE         | Family history    | <DATA_TABLE> |
| TABLE         | Social history    | <DATA_TABLE> |
| KEY-VALUE     | Marital status    | M            |
| TABLE         | Substance history | <DATA_TABLE> |
And the "Patient history" section contains the following "Surgical history" table:
| Surgery name           | Date         |
| Suture open wound      | Mar 22, 2020 |
| APPENDECTOMY           | Jul 02, 2019 |
| CLEFT PALATE REPAIR    | May 02, 2019 |
| Hysteroscopy           | Apr 04, 2017 |
| Coronary artery bypass | Feb 11, 2017 |
And the "Patient history" section contains the following "Family history" table:
| Relationship | Sex    | Age     | Deceased | Condition                                       |
| wife         | female | 46      | true     | Does not attend dentist (finding)               |
| half-brother | male   | <EMPTY> | false    | Heavy chain disease, Acute tubotympanic catarrh |
And the "Patient history" section contains the following "Social history" table:
| Name                    | Value                | Date         |
| SHX Environment risks   | Animals in the house | Jul 19, 2016 |
| SHX Alcohol type        | Liquor               | Jul 23, 2016 |
| SHX Smoker in household | No                   | Jul 19, 2016 |
And the "Patient history" section contains the following "Substance history" table:
| Substance                    | Value                                    | Date         |
| Occasional drinker (finding) | Liquor                                   | Jul 24, 2016 |
| SHX Tobacco use              | Former smoker, quit more than 1 year ago | Jul 19, 2016 |
| SHX Alcohol type             | Liquor                                   | Jul 23, 2016 |
| SHX Environment risks        | Animals in the house                     | Jul 19, 2016 |
| SHX Smoker in household      | No                                       | Jul 19, 2016 |

@SRS-29.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68560
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Medical_History_View
Scenario: Patient history contains only substance history
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, McKesson" patient is selected
When the user navigates to the "Medical history" view
Then the "Patient history" section is available in Medical history
And the "Patient history" section contains the following data in Medical history:
| data_type     | title             | value        |
| SECTION_TITLE | Patient history   | N/A          |
| TABLE         | Substance history | <DATA_TABLE> |
And the "Patient history" section contains the following "Substance history" table:
| Substance       | Value          | Date         |
| Smoking History | Never Assessed | Apr 09, 2020 |

@SRS-29.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68560
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Medical_History_View
Scenario: Patient history contains only marital status
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
When the user navigates to the "Medical history" view
Then the "Patient history" section contains the following data in Medical history:
| data_type     | title           | value |
| SECTION_TITLE | Patient history | N/A   |
| KEY-VALUE     | Marital status  | S     |

@SRS-29.04
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68560
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Medical_History_View
Scenario: Information is displayed to the user if patient history is not available
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Empty" patient is selected
When the user navigates to the "Medical history" view
Then the "Patient history" section contains the following data in Medical history:
| data_type     | title           | value                    | color |
| SECTION_TITLE | Patient history | N/A                      | N/A   |
| EMPTY_STATE   | N/A             | No Patient history found | grey  |
