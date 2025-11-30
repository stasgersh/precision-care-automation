@safety
Feature: [SysRS-68388] Patient Name and ID

Narrative: The application shall ensure that whenever Patient name is displayed, then Patient ID is also displayed.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded

@SRS-7.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68388
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Data_processing_and_Fulfillment/Data_processing_and_Fulfillment
Scenario Outline: Patient Name and ID displayed together on Patient banner
Given the "<patient>" patient is selected
When the user navigates to the "Summary" view
Then the patient summary view is loaded
And the Patient banner contains both the <patient> and <MRN_VALUE> together

Examples:
| patient                   | MRN_VALUE                            |
| OncoTestPatient, Juno     | 011-233-455                          |
| OncoTestPatient, Al       | 9bb6cace-aae0-3332-d0b8-0254ab0e9903 |
| OncoTestPatient, McKesson | 204074                               |
| OncoTestPatient, SANDY A  | 9006143802                           |

@sanity
@SRS-7.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68388
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Data_processing_and_Fulfillment
Scenario Outline: Patient Name and ID displayed together in the Patient list
When the user opens the patient select menu
And the user types into the patient search field: <patient>
Then the following user is present in the patient list:
| name      | mrn         |
| <patient> | <mrn_value> |

Examples:
| patient                       | mrn_value                            |
| OncoTestPatient, Juno         | 011-233-455                          |
| OncoTestPatient, Al           | 9bb6cace-aae0-3332-d0b8-0254ab0e9903 |
| OncoTestPatient, McKesson     | 204074                               |
| Mr. OncoTeslaPatient, Janneta | 83a2a278                             |