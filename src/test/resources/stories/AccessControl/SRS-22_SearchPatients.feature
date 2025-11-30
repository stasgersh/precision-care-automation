@safety
Feature: [SysRS-68408] Search Patients

Narrative: The system shall allow the users to search patients from the patient list based on patient name or patient ID.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the Patient header section is displayed
And the user opened the patient select menu
And the search field is empty

@sanity
@SRS-22.01
@edge
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68408
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Access_Control
Scenario Outline: Patient can be searched based on patient name
When the user types into the patient search field: <search_param>
Then the following user is the first item in the patient list:
| name           | mrn          |
| <patient_name> | <patient_id> |
Examples:
| search_param                | patient_name                | patient_id                           |
| OncoTestPatient, Freya      | OncoTestPatient, Freya      | 6d2d9e07-4a24-7525-4661-f59ccabb02a7 |
| OncOTestPatiEnt, EleANor470 | OncoTestPatient, Eleanor470 | a0747980-7e02-4318-9ef7-0569643aed5a |

@sanity
@SRS-22.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68408
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Access_Control
Scenario Outline: Patient can be searched based on patient id
When the user types into the patient search field: <search_param>
Then the following user is present in the patient list:
| name           | mrn          |
| <patient_name> | <patient_id> |
And the following user is not present in the patient list:
| name                   | mrn                                  |
| OncoTestPatient, Freya | 6d2d9e07-4a24-7525-4661-f59ccabb02a7 |
Examples:
| example_description                                    | search_param                         | patient_name             | patient_id                           |
| PID available based on predefined config               | 011-233-455                          | OncoTestPatient, Juno    | 011-233-455                          |
| PID available based on fallback mechanism: resource id | 34e06220-11fd-a9ce-606d-98f572481f3e | OncoTestPatient, Pablo44 | 34e06220-11fd-a9ce-606d-98f572481f3e |

@srs
@SRS-22.03
@edge
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68408
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control
Scenario Outline: Patient list is empty if patient cannot be found based on the provided search parameters
When the user types into the patient search field: <search_param>
Then patient list is empty
And the following text is displayed in the empty patient list: "No patient found"
Examples:
| search_param            |
| wweerrttbbccxxxxxxxxxxx |
| ****                    |
| %!/+"                   |

@srs
@SRS-22.04
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68408
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control
Scenario Outline: Patient can be found by providing partial patient name
When the user types into the patient search field: <search_param>
Then the following user is present in the patient list:
| name                 | mrn    |
| DR. Ross, Linda Jane | 203712 |
And the following user is not present in the patient list:
| name                     | mrn                                  |
| OncoTestPatient, Rory188 | a6f4b943-96fe-4136-b011-bd9fe5956a0a |
Examples:
| search_param |
| Ross         |
| Linda        |
| Li ja        |

@srs
@SRS-22.05
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68408
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control
Scenario: Patients can be found by providing partial patient name or id
When the user types into the patient search field: torv
Then the following users are present in the patient list:
| name                     | mrn                                  |
| OncoTestPatient, Dolores | 012-torv-999                         |
| OncoTestPatient, Torvald | cee5f972-9531-4cd4-b1e4-599c9589a177 |
And the following user is not present in the patient list:
| name                  | mrn         |
| OncoTestPatient, Juno | 011-233-455 |

@srs
@SRS-22.06
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68408
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control
Scenario Outline: Patients list is not filtered if less than three characters are provided as searching parameters
Given the number of the patients in the patient list is saved
When the user types into the patient search field: <search_param>
Then the number of patients is equal to the saved number
Examples:
| search_param |
| o            |
| on           |

@srs
@SRS-22.07
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68408
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control
Scenario Outline: Patients list is filtered if at least three characters are provided as searching parameters
Given the number of the patients in the patient list is saved
When the user types into the patient search field: <search_param>
Then the number of patients is less than the saved number
Examples:
| search_param |
| onc          |
| onco         |

@srs
@SRS-22.08
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68408
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control
Scenario: Search parameters can be cleared by 'X' button in the search field
Given the number of the patients in the patient list is saved
And the user typed into the patient search field: onc
And the number of patients was less than the saved number
When the user clicks on the 'X' button in the searching window
Then the number of patients is equal to the saved number

@srs
@SRS-22.09
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68408,SysRS-68407
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Access_Control/Access_Control
Scenario: Patient can be selected after search
Given the user typed into the patient search field: reya
When the user selects the "OncoTestPatient, Freya" patient from the already opened patient list
Then the patient summary view is loaded
