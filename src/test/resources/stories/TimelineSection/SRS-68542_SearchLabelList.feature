Feature: [SysRS-68542] Search label list

Narrative: The system labels in the label list shall be searchable.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application

@sanity
@edge
@SRS-133.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68542
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Timeline_section
Scenario Outline: User can search for labels on select label panel
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Biomarkers" swimlane:
| event_type | name_on_label                                |
| MARKER     | Estrogen Receptor Positive (qualifier value) |
And the sidebar is displayed
When the user clicks on the 'Label' button on the sidebar header
And the label selection panel is opened
Then the following labels are available in the label list: Date of diagnosis, Molecular testing, Adverse Event
When the user types the following text into the search field on select label panel: "<search_text>"
Then the following labels are available in the label list: <found_labels>
And the following labels are not available in the label list: <not_found_labels>
Examples:
| example_description                                        | search_text       | found_labels                                        | not_found_labels                     |
| At least 3 character needed for search but only 2 provided | ve                | Date of diagnosis, Molecular testing, Adverse Event | N/A                                  |
| At least 3 character needed for search and 3 provided      | veN               | Adverse Event                                       | Date of diagnosis, Molecular testing |
| Full match for label                                       | Molecular testing | Molecular testing                                   | Adverse Event, Date of diagnosis     |

@SRS-133.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68542
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_section
Scenario: Not found message is displayed on label panel if searched label is not available
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Biomarkers" swimlane:
| event_type | name_on_label                                |
| MARKER     | Estrogen Receptor Positive (qualifier value) |
And the sidebar is displayed
When the user clicks on the 'Label' button on the sidebar header
Then the label selection panel is opened
And the following labels are available in the label list:
| label_name     |
| MDT discussion |
When the user types the following text into the search field on select label panel: "N0t avai1ab13 lab3l"
Then not found message is displayed on the label panel: "No matching labels."