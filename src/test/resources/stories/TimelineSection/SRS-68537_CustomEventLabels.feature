Feature: [SysRS-68537] Custom event labels

Narrative: The system users shall be able to add new custom labels to the label list.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And [API] the following events were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/2caafd5d-053f-4a0c-85b4-9af5bc436dcc |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/21becb88-3a8c-4787-8b8d-53d6d3a3dcbf |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/2caafd5d-053f-4a0c-85b4-9af5bc436dcc |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/21becb88-3a8c-4787-8b8d-53d6d3a3dcbf |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f |

@sanity
@edge
@SRS-129.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68538,SysRS-68537
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Timeline_section
Scenario: Create custom label and add it to an event
Given [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                       |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f |
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Encounters" swimlane:
| event_type | name_on_label       |
| MARKER     | Body Weight 47.3 kg |
And the sidebar is displayed
And the sidebar header does not contain any badges
When the user clicks on the 'Label' button on the sidebar header
Then the label selection panel is opened
When the user clicks on the 'Create new' button on the label panel
Then the create new label panel is opened
When the user types the following text into the 'Label name' field: "Test label <<create_label_timestamp>>"
And the user clicks on the 4th color on the 'Color' palette
Then the 4th color is selected on the 'Color' palette
When the user saves the color of the 4th item on the 'Color' palette as "4th_color"
And the user clicks on the 'Create' button on the create new label panel
Then the label selection panel is displayed
And the "Test label <<create_label_timestamp>>" label is available with "4th_color" color in the label list
When the user saves the color of the following label on the label panel:
| label_name                            |
| Test label <<create_label_timestamp>> |
And the user clicks on the following label on the label panel:
| label_name                            |
| Test label <<create_label_timestamp>> |
Then the sidebar header contains the following labels with the previously saved color:
| labels                                |
| Test label <<create_label_timestamp>> |
And the "Body Weight 47.3 kg" event MARKER is available on the "Encounters" swimlane with the below badges:
| badge_type | badge_text                            | badge_color                    |
| LABEL      | Test label <<create_label_timestamp>> | <PREVIOUSLY_SAVED_BADGE_COLOR> |
When the user clicks on the Close button on the sidebar
Then the sidebar is not displayed
When the user clicks on the following event point on the "Encounters" swimlane:
| event_type | name_on_label       |
| MARKER     | Body Weight 47.3 kg |
Then the sidebar is displayed
And the sidebar header contains the following labels with the previously saved color:
| labels                                |
| Test label <<create_label_timestamp>> |
When the user clicks on the browser's refresh button
Then the patient timeline is loaded
And the "Body Weight 47.3 kg" event MARKER is available on the "Encounters" swimlane with the below badges:
| badge_type | badge_text                            | badge_color                    |
| LABEL      | Test label <<create_label_timestamp>> | <PREVIOUSLY_SAVED_BADGE_COLOR> |
When the user opens the following event point on the "Encounters" swimlane:
| event_type | name_on_label       |
| MARKER     | Body Weight 47.3 kg |
Then the sidebar is displayed
And the sidebar header contains the following labels with the previously saved color:
| labels                                |
| Test label <<create_label_timestamp>> |

@SRS-129.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68537
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_section
Scenario: Custom labels are available for all users
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Biomarkers" swimlane:
| event_type | name_on_label                            |
| MARKER     | HER2 Receptor Negative (qualifier value) |
And the sidebar is displayed
When the user clicks on the 'Label' button on the sidebar header
Then the label selection panel is opened
When the user clicks on the 'Create new' button on the label panel
And the user types the following text into the 'Label name' field: "Test label <<create_label_timestamp>>"
And the user clicks on the 4th color on the 'Color' palette
And the user saves the color of the 4th item on the 'Color' palette as "4th_color"
And the user clicks on the 'Create' button on the create new label panel
Then the label selection panel is displayed
And the "Test label <<create_label_timestamp>>" label is available with "4th_color" color in the label list
When the user logs out from OncoCare
And the [TEST-USER-2] user logs in to OncoCare
And the user selects the "OncoTestPatient, Juno" patient
And the user navigates to the "Timeline" view
And the user clicks on the following event point on the "Biomarkers" swimlane:
| event_type | name_on_label   |
| MARKER     | Report with pdf |
And the user clicks on the 'Label' button on the sidebar header
Then the label selection panel is opened
And the "Test label <<create_label_timestamp>>" label is available with "4th_color" color in the label list
When the user opens the timeline filter modal
Then the following timeline filter options are displayed on the filter modal:
| filter_group | checkbox_name                         | selected | enabled |
| Labels       | Test label <<create_label_timestamp>> | false    | true    |

@SRS-129.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68537
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_section
Scenario: Error message is displayed if user tries to create label without label name
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Biomarkers" swimlane:
| event_type | name_on_label                                |
| MARKER     | Estrogen Receptor Positive (qualifier value) |
And the sidebar is displayed
When the user clicks on the 'Label' button on the sidebar header
Then the label selection panel is opened
When the user clicks on the 'Create new' button on the label panel
Then the create new label panel is opened
When the user clicks on the 'Create' button on the create new label panel
Then the create new label panel is displayed
And the following error is displayed on the create label panel: "Please enter a label name."

@SRS-129.04
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68537
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_section
Scenario: Error message is displayed if user tries to create label with an already existing name
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Biomarkers" swimlane:
| event_type | name_on_label                                |
| MARKER     | Estrogen Receptor Positive (qualifier value) |
And the sidebar is displayed
When the user clicks on the 'Label' button on the sidebar header
Then the label selection panel is opened
When the user clicks on the 'Create new' button on the label panel
Then the create new label panel is opened
And the 'Create' button is enabled on the create new label panel
When the user types the following text into the 'Label name' field: "MDT discussion"
Then the following error is displayed on the create label panel: "A label with the same name already exists."
And the 'Create' button is not enabled on the create new label panel
When the user types the following text into the 'Label name' field: " mdt    DiscuSsion   "
Then the following error is displayed on the create label panel: "A label with the same name already exists."
And the 'Create' button is not enabled on the create new label panel
When the user clicks on the 4th color on the 'Color' palette
Then the 'Create' button is not enabled on the create new label panel

@SRS-129.05
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68537
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_section
Scenario: Error message is displayed if user tries to create label with an already existing name (with label name changes)
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Biomarkers" swimlane:
| event_type | name_on_label                                |
| MARKER     | Estrogen Receptor Positive (qualifier value) |
And the sidebar is displayed
When the user clicks on the 'Label' button on the sidebar header
Then the label selection panel is opened
When the user clicks on the 'Create new' button on the label panel
And the user types the following text into the 'Label name' field: "Test <<create_label_timestamp>> abc"
And the user clicks on the 'Create' button on the create new label panel
Then the following labels are available in the label list:
| label_name                          |
| Test <<create_label_timestamp>> abc |
When the user clicks on the 'Create new' button on the label panel
Then the create new label panel is displayed
And the 'Create' button is enabled on the create new label panel
When the user types the following text into the 'Label name' field: "Test <<previously_created_label_timestamp>> abc"
And the user removes the last character of the label name in the 'Label name' field
And the user adds the following text to the label name in the 'Label name' field: "c"
Then the following error is displayed on the create label panel: "A label with the same name already exists."
And the 'Create' button is not enabled on the create new label panel