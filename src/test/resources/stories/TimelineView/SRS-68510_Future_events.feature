Feature: [SysRS-68510] Future events

Narrative: The system shall display Patient’s Future events (Planned events) in a chronological order.


@sanity
@edge
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68510,SysRS-68509
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Timeline_View
@Description:Future_events_are_displayed_on_the_timeline_in_chronological_order_on_both_timeline_direction<br/>,Future_events_successfully_presented_on_timeline_view
Scenario: Future events are displayed on the timeline in chronological order on both timeline direction
Given [API] the following patient is uploaded to PDS: timeline/aviRon_bundle.json
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, AviRon" patient is selected
And the "Timeline" view is selected
And the full time range is displayed on the timeline
And the user navigated to the Settings page
And the following settings are available on the page:
| settings_title | setting_name | setting_value                   |
| Timeline       | Direction    | Newest event on right (default) |
When the user clicks on the "Back" button in the Settings toolbar header
Then the patient timeline is loaded
Then "Future" event marker is displayed on the "right" side of the timeline axis
When the user navigates to the Settings page
And the user clicks on 'Edit' button at the following user setting:
| settings_title | setting_name |
| Timeline       | Direction    |
And the user selects the "Newest event on left" radio button at the following user setting:
| settings_title | setting_name |
| Timeline       | Direction    |
And the user clicks on the "Save" button on the Settings page
Then the following settings are available on the page:
| settings_title | setting_name | setting_value        |
| Timeline       | Direction    | Newest event on left |
When the user clicks on the "Back" button in the Settings toolbar header
Then the patient timeline is loaded
And "Future" event marker is displayed on the "left" side of the timeline axis