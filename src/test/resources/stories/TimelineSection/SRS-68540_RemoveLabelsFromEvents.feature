Feature: [SysRS-68540] Remove labels from events

Narrative: The system users shall be able to remove labels from the events.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And [API] the following event was not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/2caafd5d-053f-4a0c-85b4-9af5bc436dcc |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/2caafd5d-053f-4a0c-85b4-9af5bc436dcc |

@sanity
@edge
@SRS-131.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68540
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Timeline_section
Scenario: Remove single label from an event
Given [API] default labels are added to the following event by the [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                       | labels         |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/2caafd5d-053f-4a0c-85b4-9af5bc436dcc | MDT discussion |
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the "Estrogen Receptor Positive (qualifier value)" event MARKER is available on the "Biomarkers" swimlane with 2 miniaturized badges:
| badge_types_in_order |
| CLASSIFICATION       |
| LABEL                |
And the following event point is selected on the "Biomarkers" swimlane:
| event_type | name_on_label                                |
| MARKER     | Estrogen Receptor Positive (qualifier value) |
And the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type     | badge_text     |
| CLASSIFICATION | Genomic report |
| LABEL          | MDT discussion |
When the user clicks on the 'Label' button on the sidebar header
Then the label selection panel is opened
When the user clicks on the following labels on the label panel:
| labels         |
| MDT discussion |
Then the sidebar header contains the following badges:
| badge_type     | badge_text     |
| CLASSIFICATION | Genomic report |
When the user clicks on the Close button on the sidebar
Then the sidebar is not displayed
And the "Estrogen Receptor Positive (qualifier value)" event MARKER is available on the "Biomarkers" swimlane with the below badges:
| badge_type     | badge_text     |
| CLASSIFICATION | Genomic report |

@SRS-131.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68540
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_section
Scenario: Remove more default labels from an event
Given [API] default labels are added to the following event by the [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                       | labels                                                         |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/2caafd5d-053f-4a0c-85b4-9af5bc436dcc | MDT discussion, Progression, To Be Reviewed, Molecular testing |
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the "Estrogen Receptor Positive (qualifier value)" event MARKER is available on the "Biomarkers" swimlane with 5 miniaturized badges:
| badge_types_in_order |
| CLASSIFICATION       |
| LABEL                |
| LABEL                |
| LABEL                |
| LABEL                |
And the following event point is selected on the "Biomarkers" swimlane:
| event_type | name_on_label                                |
| MARKER     | Estrogen Receptor Positive (qualifier value) |
And the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type     | badge_text        |
| CLASSIFICATION | Genomic report    |
| LABEL          | MDT discussion    |
| LABEL          | Progression       |
| LABEL          | To Be Reviewed    |
| LABEL          | Molecular testing |
When the user clicks on the 'Label' button on the sidebar header
Then the label selection panel is opened
When the user clicks on the following labels on the label panel:
| labels            |
| MDT discussion    |
| Molecular testing |
Then the sidebar header contains the following badges:
| badge_type     | badge_text     |
| CLASSIFICATION | Genomic report |
| LABEL          | Progression    |
| LABEL          | To Be Reviewed |
When the user clicks on the Close button on the sidebar
Then the sidebar is not displayed
And the "Estrogen Receptor Positive (qualifier value)" event MARKER is available on the "Biomarkers" swimlane with 3 miniaturized badges:
| badge_types_in_order |
| CLASSIFICATION       |
| LABEL                |
| LABEL                |
When the user clicks on the following event point on the "Biomarkers" swimlane:
| event_type | name_on_label                                |
| MARKER     | Estrogen Receptor Positive (qualifier value) |
Then the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type     | badge_text     |
| CLASSIFICATION | Genomic report |
| LABEL          | Progression    |
| LABEL          | To Be Reviewed |
When the user clicks on the browser's refresh button
And the patient timeline is loaded
And the user opens the following event point on the "Biomarkers" swimlane:
| event_type | name_on_label                                |
| MARKER     | Estrogen Receptor Positive (qualifier value) |
Then the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type     | badge_text     |
| CLASSIFICATION | Genomic report |
| LABEL          | Progression    |
| LABEL          | To Be Reviewed |

@SRS-131.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68540
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_section
Scenario: Add and remove default labels to/from an event at the same time
Given [API] default labels are added to the following event by the [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                       | labels                                      |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/2caafd5d-053f-4a0c-85b4-9af5bc436dcc | MDT discussion, Progression, To Be Reviewed |
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Biomarkers" swimlane:
| event_type | name_on_label                                |
| MARKER     | Estrogen Receptor Positive (qualifier value) |
And the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type     | badge_text     |
| CLASSIFICATION | Genomic report |
| LABEL          | MDT discussion |
| LABEL          | Progression    |
| LABEL          | To Be Reviewed |
When the user clicks on the 'Label' button on the sidebar header
Then the label selection panel is opened
When the user saves the color of the following labels on the label panel:
| label_name        |
| MDT discussion    |
| Molecular testing |
| Recurrence        |
And the user clicks on the following labels on the label panel:
| label_name        |
| Progression       |
| To Be Reviewed    |
| Molecular testing |
| Recurrence        |
Then the sidebar header contains the following labels with the previously saved color:
| labels            |
| MDT discussion    |
| Molecular testing |
| Recurrence        |
When the user clicks on the Close button on the sidebar
Then the sidebar is not displayed
When the user clicks on the following event point on the "Biomarkers" swimlane:
| event_type | name_on_label                                |
| MARKER     | Estrogen Receptor Positive (qualifier value) |
Then the sidebar is displayed
And the sidebar header contains the following labels with the previously saved color:
| labels            |
| MDT discussion    |
| Molecular testing |
| Recurrence        |
When the user clicks on the browser's refresh button
Then the patient timeline is loaded
When the user opens the following event point on the "Biomarkers" swimlane:
| event_type | name_on_label                                |
| MARKER     | Estrogen Receptor Positive (qualifier value) |
Then the sidebar is displayed
And the sidebar header contains the following labels with the previously saved color:
| labels            |
| MDT discussion    |
| Molecular testing |
| Recurrence        |
