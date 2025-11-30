Feature: [SysRS-68536] Predefined event labels

Narrative: The system users shall be able to add the following predefined labels to the events:
             - Adverse Event
             - Date of diagnosis
             - MDT discussion
             - Molecular testing
             - Progression
             - Recurrence
             - To Be Reviewed.


@sanity
@edge
@SRS-128.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68536
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Timeline_section
Scenario: Default label is added to an event
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And [API] the following events were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/21becb88-3a8c-4787-8b8d-53d6d3a3dcbf |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/21becb88-3a8c-4787-8b8d-53d6d3a3dcbf |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/21becb88-3a8c-4787-8b8d-53d6d3a3dcbf |
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Biomarkers" swimlane:
| event_type | name_on_label                            |
| MARKER     | HER2 Receptor Negative (qualifier value) |
And the sidebar is displayed
And the sidebar header does not contain any badges
When the user clicks on the 'Label' button on the sidebar header
Then the label selection panel is opened
When the user saves the color of the following label on the label panel:
| label_name     |
| MDT discussion |
And the user clicks on the following label on the label panel:
| label_name     |
| MDT discussion |
Then the sidebar header contains the following labels with the previously saved color:
| labels         |
| MDT discussion |
And the "HER2 Receptor Negative (qualifier value)" event MARKER is available on the "Biomarkers" swimlane with the below badges:
| badge_type | badge_text     | badge_color                    |
| LABEL      | MDT discussion | <PREVIOUSLY_SAVED_BADGE_COLOR> |
When the user clicks on the Close button on the sidebar
Then the sidebar is not displayed
When the user clicks on the following event point on the "Biomarkers" swimlane:
| event_type | name_on_label                            |
| MARKER     | HER2 Receptor Negative (qualifier value) |
Then the sidebar is displayed
And the sidebar header contains the following labels with the previously saved color:
| labels         |
| MDT discussion |
When the user clicks on the browser's refresh button
Then the patient timeline is loaded
And the "HER2 Receptor Negative (qualifier value)" event MARKER is available on the "Biomarkers" swimlane with the below badges:
| badge_type | badge_text     | badge_color                    |
| LABEL      | MDT discussion | <PREVIOUSLY_SAVED_BADGE_COLOR> |
When the user opens the following event point on the "Biomarkers" swimlane:
| event_type | name_on_label                            |
| MARKER     | HER2 Receptor Negative (qualifier value) |
Then the sidebar is displayed
And the sidebar header contains the following labels with the previously saved color:
| labels         |
| MDT discussion |
When the user clicks on the following event point on the "Encounters" swimlane:
| event_type | name_on_label    |
| MARKER     | Tumor board Note |
Then the sidebar is displayed
And the sidebar header does not contain any badges

@SRS-128.02
@sanity
@edge
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68536
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Timeline_section
Scenario: More default labels are added to an event
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And [API] the following events were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/c6e08dfd-fd75-4a37-9f50-eceff502a8d2 |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/c6e08dfd-fd75-4a37-9f50-eceff502a8d2 |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/c6e08dfd-fd75-4a37-9f50-eceff502a8d2 |
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Encounters" swimlane:
| event_type | name_on_label    |
| MARKER     | Tumor board Note |
And the sidebar is displayed
And the sidebar header does not contain any badges
When the user clicks on the 'Label' button on the sidebar header
Then the label selection panel is opened
When the user clicks on the following labels on the label panel:
| label_name        |
| Adverse Event     |
| Date of diagnosis |
| Molecular testing |
| Progression       |
| Recurrence        |
| To Be Reviewed    |
Then the sidebar header contains the following badges:
| badge_type | badge_text        |
| LABEL      | Adverse Event     |
| LABEL      | Date of diagnosis |
| LABEL      | Molecular testing |
| LABEL      | Progression       |
| LABEL      | Recurrence        |
| LABEL      | To Be Reviewed    |
When the user clicks on the Close button on the sidebar
Then the sidebar is not displayed
When the user clicks on the following event point on the "Encounters" swimlane:
| event_type | name_on_label    |
| MARKER     | Tumor board Note |
Then the sidebar is displayed
Then the sidebar header contains the following badges:
| badge_type | badge_text        |
| LABEL      | Adverse Event     |
| LABEL      | Date of diagnosis |
| LABEL      | Molecular testing |
| LABEL      | Progression       |
| LABEL      | Recurrence        |
| LABEL      | To Be Reviewed    |
