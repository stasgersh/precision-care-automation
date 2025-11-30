Feature: [SysRS-68651] Indicate Chemo Cycle Numbers

Narrative: The system shall be able to visually indicate the Chemo cycle numbers on the timeline and on the Side panel.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application


@sanity
@edge
@SRS-92.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68651
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use
Scenario: Chemo cycle number is displayed on L1 event marker label and on the event sidebar header
Given [API] the following event was not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                             |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/0dbfc5f3-1427-45e0-a2b7-539ab0fc9f3c |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                             |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/0dbfc5f3-1427-45e0-a2b7-539ab0fc9f3c |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                             |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/0dbfc5f3-1427-45e0-a2b7-539ab0fc9f3c |
And the "OncoTestPatient, Freya" patient is selected
When the user navigates to the "Timeline" view
Then the patient timeline is loaded
And the "CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE" event MARKER is available on the "Treatment and Plan" swimlane with the below badges:
| badge_type     | badge_text  |
| CYCLED_EVENT   | Cycle 0     |
| CLASSIFICATION | Systemic Tx |
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label                            |
| MARKER     | CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE |
Then the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type     | badge_text  |
| CYCLED_EVENT   | Cycle 0     |
| CLASSIFICATION | Systemic Tx |

@SRS-92.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68651
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use
Scenario: Chemo cycle number is displayed on L1 event cluster label, on event label in the cluster and on the sidebar headers of the events
Given [API] the following events were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                             |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/3b43e0f4-6ba4-4d07-95d4-805fa982bd49 |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/523a692e-96c9-4ca9-9692-0d8e6373057a |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/744b63c9-fb4a-4a94-b5ce-cf44e7c67320 |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following events:
| patientID                            | root_resource_of_the_event                             |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/3b43e0f4-6ba4-4d07-95d4-805fa982bd49 |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/523a692e-96c9-4ca9-9692-0d8e6373057a |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/744b63c9-fb4a-4a94-b5ce-cf44e7c67320 |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                             |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/3b43e0f4-6ba4-4d07-95d4-805fa982bd49 |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/523a692e-96c9-4ca9-9692-0d8e6373057a |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/744b63c9-fb4a-4a94-b5ce-cf44e7c67320 |
And the "OncoTestPatient, Freya" patient is selected
When the user navigates to the "Timeline" view
Then the patient timeline is loaded
And the "3 events" event CLUSTER is available on the "Treatment and Plan" swimlane with 3 miniaturized badges:
| badge_types_in_order |
| CYCLED_EVENT         |
| CYCLED_EVENT         |
| CLASSIFICATION       |
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 3                   | 3 events      |
Then the cluster pill is opened
And the following events are displayed in the cluster pill:
| event_name                                    | date_on_tooltip |
| RUCAPARIB 300MG TABLET (FOC)                  | Feb 11, 1995    |
| CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE      | Feb 11, 1995    |
| DOXORUBICIN LIPOSOMAL (CAELYX) CHEMO INFUSION | Feb 11, 1995    |
And the "RUCAPARIB 300MG TABLET (FOC)" event is available in the custer pill with the below badges:
| badge_type     | badge_text  |
| CYCLED_EVENT   | Cycle 1     |
| CLASSIFICATION | Systemic Tx |
When the user selects the following event from the cluster pill:
| event_name                   |
| RUCAPARIB 300MG TABLET (FOC) |
Then the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type     | badge_text  |
| CYCLED_EVENT   | Cycle 1     |
| CLASSIFICATION | Systemic Tx |
When the user selects the following event from the cluster pill:
| event_name                                    |
| DOXORUBICIN LIPOSOMAL (CAELYX) CHEMO INFUSION |
Then the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type     | badge_text  |
| CYCLED_EVENT   | Cycle 2     |
| CLASSIFICATION | Systemic Tx |
When the user selects the following event from the cluster pill:
| event_name                               |
| CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE |
Then the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type     | badge_text  |
| CYCLED_EVENT   | Cycle 1     |
| CLASSIFICATION | Systemic Tx |
