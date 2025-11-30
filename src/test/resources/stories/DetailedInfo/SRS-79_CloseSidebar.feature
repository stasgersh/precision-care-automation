Feature: [SysRS-68548] Close Sidebar

Narrative: The system users shall be able to close the side panel.

Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application

@sanity
@edge
@SRS-79.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68548
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Detailed_Info
Scenario: The side panel which contains event details is closed by clicking on the close button
Given the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following events are available on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
And the following event point is selected on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
And the sidebar is displayed
When the user clicks on the Close button on the sidebar
Then the sidebar is not displayed

@SRS-79.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68548
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Detailed_Info
Scenario: The side panel is closed by clicking the selected event again in the cluster pill and the cluster pill is closed when clicking on the cluster pill
Given the "OncoTestPatient, Casper" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the following event point is selected on the "Encounters" swimlane:
| event_type | text_on_event_point | name_on_label | date_on_timeline_axis       |
| CLUSTER    | 2                   | 2 events      | Dec 25, 1997 - Feb 25, 1998 |
Then the cluster pill is opened
And the following events are displayed in the cluster pill:
| event_name          | date_on_tooltip |
| Body Weight 52.7 kg | Dec 25, 1997    |
| Body Weight 55.8 kg | Feb 25, 1998    |
And the cluster pill displays the following text: "Dec 25, 1997 - Feb 25, 1998"
When the user selects the following event from the cluster pill:
| event_name          |
| Body Weight 52.7 kg |
Then the sidebar is displayed
When the user clicks on the following event on the cluster pill:
| event_name          |
| Body Weight 52.7 kg |
Then the sidebar is not displayed
And the cluster pill is still opened
When the user hovers over the cluster pill
Then the cluster pill displays the following text: "Close"
When the user clicks on the cluster pill
Then the cluster pill is closed

@SRS-79.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68548
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Detailed_Info
Scenario: The side panel is closed by clicking the selected event marker again
Given the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following events are available on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
And the following event point is selected on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
And the sidebar is displayed
When the user clicks on the following event point on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
Then the sidebar is not displayed
