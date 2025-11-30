Feature: [SysRS-68544] Full badges on event tooltips

Narrative: The system users shall be able to see detailed tooltips with full badges on the timeline for the mouse hover action.

@sanity
@edge
@SRS-208.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68544
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Timeline_View
Scenario: Full badges are displayed on event's L1 label if the event label is hovered
Given [API] the CLP visualization is switched OFF for [DEFAULT-TEST-USER] user
And [API] the following labels are created:
| label_name    |
| Filter test 1 |
And [API] labels are added to the following events by the [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            | labels        |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc | Filter test 1 |
And [API] the following event is marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            | event_name                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc | CT THORAX ABDOMEN PELVIS WITH CONTRAST |
And [API] the below event comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | root_resource_of_the_event                            | comment_text                | event_name                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc | Test event badge comment 1a | CT THORAX ABDOMEN PELVIS WITH CONTRAST |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc | Test event badge comment 1b | CT THORAX ABDOMEN PELVIS WITH CONTRAST |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Juno" patient
And the user navigates to the "Timeline" view
Then the patient timeline is loaded
And the "CT THORAX ABDOMEN PELVIS WITH CONTRAST" event MARKER is available on the "Imaging" swimlane at "Sep 28, 2011" with 4 miniaturized badges:
| badge_types_in_order |
| IMPORTANT_EVENT      |
| CLASSIFICATION       |
| COMMENT              |
| LABEL                |
When the user hovers the following event label on the "Imaging" swimlane:
| event_type | name_on_label                          | date_on_timeline_axis |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
Then the "CT THORAX ABDOMEN PELVIS WITH CONTRAST" event MARKER is available on the "Imaging" swimlane at "Sep 28, 2011" with the below badges:
| badge_type      | badge_text    |
| IMPORTANT_EVENT | Important     |
| CLASSIFICATION  | CT            |
| COMMENT         | 2 comments    |
| LABEL           | Filter test 1 |
When the user unhovers the following event label on the "Imaging" swimlane:
| event_type | name_on_label                          | date_on_timeline_axis |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
Then the "CT THORAX ABDOMEN PELVIS WITH CONTRAST" event MARKER is available on the "Imaging" swimlane at "Sep 28, 2011" with 4 miniaturized badges:
| badge_types_in_order |
| IMPORTANT_EVENT      |
| CLASSIFICATION       |
| COMMENT              |
| LABEL                |

@SRS-208.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68544
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_View
Scenario: Full badges are displayed on L1 label of the event cluster and on the event labels in the cluster pill if the event label is hovered
Given [API] the following events were marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                             | event_name                                    |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/3b43e0f4-6ba4-4d07-95d4-805fa982bd49 | RUCAPARIB 300MG TABLET (FOC)                  |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/523a692e-96c9-4ca9-9692-0d8e6373057a | DOXORUBICIN LIPOSOMAL (CAELYX) CHEMO INFUSION |
And [API] the following event was not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                             | event_name                               |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/744b63c9-fb4a-4a94-b5ce-cf44e7c67320 | CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE |
And [API] the below event comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | root_resource_of_the_event                             | comment_text          | event_name                               |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/3b43e0f4-6ba4-4d07-95d4-805fa982bd49 | Test badge comment 1  | RUCAPARIB 300MG TABLET (FOC)             |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/744b63c9-fb4a-4a94-b5ce-cf44e7c67320 | Test badge comment 2a | CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/744b63c9-fb4a-4a94-b5ce-cf44e7c67320 | Test badge comment 2b | CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                             | event_name                                    |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/523a692e-96c9-4ca9-9692-0d8e6373057a | DOXORUBICIN LIPOSOMAL (CAELYX) CHEMO INFUSION |
And [API] labels are added to the following events by the [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                             | labels                           | event_name                                    |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/3b43e0f4-6ba4-4d07-95d4-805fa982bd49 | Adverse Event                    | RUCAPARIB 300MG TABLET (FOC)                  |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/523a692e-96c9-4ca9-9692-0d8e6373057a | Adverse Event, Date of diagnosis | DOXORUBICIN LIPOSOMAL (CAELYX) CHEMO INFUSION |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                             | event_name                               |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 | MedicationRequest/744b63c9-fb4a-4a94-b5ce-cf44e7c67320 | CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Freya" patient
And the user navigates to the "Timeline" view
Then the "3 events" event CLUSTER is available on the "Treatment and Plan" swimlane at "Feb 11, 1995 - Feb 11, 1995" with 6 miniaturized badges:
| badge_types_in_order |
| IMPORTANT_EVENT      |
| CYCLED_EVENT         |
| CYCLED_EVENT         |
| CLASSIFICATION       |
| COMMENT              |
| LABEL                |
| LABEL                |
When the user hovers the following event label on the "Treatment and Plan" swimlane:
| event_type | name_on_label | text_on_event_point | date_on_timeline_axis       |
| CLUSTER    | 3 events      | 3                   | Feb 11, 1995 - Feb 11, 1995 |
Then the "3 events" event CLUSTER is available on the "Treatment and Plan" swimlane at "Feb 11, 1995 - Feb 11, 1995" with the below badges:
| badge_type      | badge_text        |
| IMPORTANT_EVENT | 2 Important       |
| CYCLED_EVENT    | Cycle 1           |
| CYCLED_EVENT    | Cycle 2           |
| CLASSIFICATION  | Systemic Tx       |
| COMMENT         | 3 comments        |
| LABEL           | Adverse Event     |
| LABEL           | Date of diagnosis |
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 3                   | 3 events      |
Then the "RUCAPARIB 300MG TABLET (FOC)" event is available in the custer pill with the below miniaturized badges:
| badge_type      |
| IMPORTANT_EVENT |
| CYCLED_EVENT    |
| CLASSIFICATION  |
| COMMENT         |
| LABEL           |
When the user hovers the "RUCAPARIB 300MG TABLET (FOC)" event in the cluster pill
Then the "RUCAPARIB 300MG TABLET (FOC)" event is available in the custer pill with the below badges:
| badge_type      | badge_text    |
| IMPORTANT_EVENT | Important     |
| CYCLED_EVENT    | Cycle 1       |
| CLASSIFICATION  | Systemic Tx   |
| COMMENT         | 1 comment     |
| LABEL           | Adverse Event |
When the user unhovers the "RUCAPARIB 300MG TABLET (FOC)" event in the cluster pill
Then the "RUCAPARIB 300MG TABLET (FOC)" event is available in the custer pill with the below miniaturized badges:
| badge_type      |
| IMPORTANT_EVENT |
| CYCLED_EVENT    |
| CLASSIFICATION  |
| COMMENT         |
| LABEL           |
And the "DOXORUBICIN LIPOSOMAL (CAELYX) CHEMO INFUSION" event is available in the custer pill with the below miniaturized badges:
| badge_type      |
| IMPORTANT_EVENT |
| CYCLED_EVENT    |
| CLASSIFICATION  |
| LABEL           |
| LABEL           |
When the user hovers the "DOXORUBICIN LIPOSOMAL (CAELYX) CHEMO INFUSION" event in the cluster pill
Then the "DOXORUBICIN LIPOSOMAL (CAELYX) CHEMO INFUSION" event is available in the custer pill with the below badges:
| badge_type      | badge_text        |
| IMPORTANT_EVENT | Important         |
| CYCLED_EVENT    | Cycle 2           |
| CLASSIFICATION  | Systemic Tx       |
| LABEL           | Adverse Event     |
| LABEL           | Date of diagnosis |
When the user unhovers the "DOXORUBICIN LIPOSOMAL (CAELYX) CHEMO INFUSION" event in the cluster pill
Then the "DOXORUBICIN LIPOSOMAL (CAELYX) CHEMO INFUSION" event is available in the custer pill with the below miniaturized badges:
| badge_type      |
| IMPORTANT_EVENT |
| CYCLED_EVENT    |
| CLASSIFICATION  |
| LABEL           |
| LABEL           |
And the "CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE" event is available in the custer pill with the below miniaturized badges:
| badge_type     |
| CYCLED_EVENT   |
| CLASSIFICATION |
| COMMENT        |

@SRS-200.03
@CLP
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68544,SysRS-68543
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_View
Scenario: Full CLP badges are displayed on event's L1 label if the event label is hovered
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And [API] the following labels are created:
| label_name    |
| Filter test 1 |
And [API] labels are added to the following events by the [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            | labels        |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc | Filter test 1 |
And [API] the following event is marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            | event_name                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc | CT THORAX ABDOMEN PELVIS WITH CONTRAST |
And [API] the below event comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | root_resource_of_the_event                            | comment_text                | event_name                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc | Test event badge comment 1a | CT THORAX ABDOMEN PELVIS WITH CONTRAST |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/2acc76c5-eda4-4076-80fa-bb263276d1fc | Test event badge comment 1b | CT THORAX ABDOMEN PELVIS WITH CONTRAST |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Juno" patient
And the user navigates to the "Timeline" view
Then the patient timeline is loaded
And the "CT THORAX ABDOMEN PELVIS WITH CONTRAST" event MARKER is available on the "Imaging" swimlane at "Sep 28, 2011" with 5 miniaturized badges:
| badge_types_in_order |
| IMPORTANT_EVENT      |
| CLASSIFICATION       |
| CLP                  |
| COMMENT              |
| LABEL                |
When the user hovers the following event label on the "Imaging" swimlane:
| event_type | name_on_label                          | date_on_timeline_axis |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
Then the "CT THORAX ABDOMEN PELVIS WITH CONTRAST" event MARKER is available on the "Imaging" swimlane at "Sep 28, 2011" with the below badges:
| badge_type      | badge_text                                     |
| IMPORTANT_EVENT | Important                                      |
| CLASSIFICATION  | CT                                             |
| CLP             | [keywords]: No evidence, of pulmonary embolism |
| COMMENT         | 2 comments                                     |
| LABEL           | Filter test 1                                  |
When the user unhovers the following event label on the "Imaging" swimlane:
| event_type | name_on_label                          | date_on_timeline_axis |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
Then the "CT THORAX ABDOMEN PELVIS WITH CONTRAST" event MARKER is available on the "Imaging" swimlane at "Sep 28, 2011" with 5 miniaturized badges:
| badge_types_in_order |
| IMPORTANT_EVENT      |
| CLASSIFICATION       |
| CLP                  |
| COMMENT              |
| LABEL                |
