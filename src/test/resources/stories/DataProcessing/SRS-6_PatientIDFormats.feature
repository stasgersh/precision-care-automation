Feature: [SysRS-68387] Patient ID Formats

Narrative: The application shall handle different Patient ID formats available in the hospital.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the Patient header section is displayed
And the user opened the patient select menu
And the search field is empty

@sanity
@edge
@SRS-6.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68387
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Data_processing_and_Fulfillment
Scenario Outline: Patient ID on patient banner and on search box match
When the user opens the patient select menu
And the user types into the patient search field: <patient>
Then the following user is present in the patient list:
| name      | mrn         |
| <patient> | <MRN_VALUE> |
When the user selects the "<patient>" patient from the already opened patient list
Then the patient banner includes the following data:
| data_type | title | value       |
| KEY-VALUE | MRN   | <MRN_VALUE> |
Examples:
| patient                   | MRN_VALUE                            |
| OncoTestPatient, Juno     | 011-233-455                          |
| OncoTestPatient, Al       | 9bb6cace-aae0-3332-d0b8-0254ab0e9903 |
| OncoTestPatient, McKesson | 204074                               |
| OncoTestPatient, SANDY A  | 9006143802                           |
