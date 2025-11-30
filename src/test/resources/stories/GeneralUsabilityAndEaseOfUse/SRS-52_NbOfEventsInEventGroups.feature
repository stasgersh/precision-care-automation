@safety
Feature: [SysRS-68621] Nb Of Events In Event Groups

Narrative: The system shall display the number of Events in each Event Group on the timeline.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application

@SRS-52.01
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68621
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use
Scenario Outline: The number of included events is dsplayed on all event groups
When the user selects the "<patient_name>" patient
And the user navigates to the "Timeline" view
And the user clicks on the 'All time' button on the timeline toolbar
Then all event clusters in all swimlanes displays a number
Examples:
| patient_name           |
| OncoTestPatient, Freya |
| OncoTestPatient, Eleanor470   |

@sanity
@SRS-52.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68621
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use
Scenario: The event groups and the displayed number on them is updated after the timeline is zoomed in
Given the browser size is set to: width=1920, height=1080
And the "Ms. OncoTestPatient, Nancy265" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the "Pathology and Labs" swimlane contains the following events:
| event_type | text_on_event_point | name_on_label                 |
| CLUSTER    | 2                   | 2 events                      |
| CLUSTER    | 4                   | 4 events                      |
| MARKER     | <EMPTY>             | Basic metabolic panel - Blood |
| CLUSTER    | 4                   | 4 events                      |
When the user clicks on the 'Zoom in' button 2 times
Then the "Pathology and Labs" swimlane contains the following events:
| event_type | text_on_event_point | name_on_label                 |
| CLUSTER    | 2                   | 2 events                      |
| CLUSTER    | 2                   | 2 events                      |
| CLUSTER    | 2                   | 2 events                      |
| MARKER     | <EMPTY>             | Basic metabolic panel - Blood |
| CLUSTER    | 3                   | 3 events                      |
| MARKER     | <EMPTY>             | Basic metabolic panel - Blood |
