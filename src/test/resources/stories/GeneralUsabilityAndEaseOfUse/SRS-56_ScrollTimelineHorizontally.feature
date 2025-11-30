@safety
Feature: [SysRS-68625] Scroll Timeline Horizontally

Narrative: The system users shall be able to pan the timeline horizontally.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the browser size is set to: width=1920, height=1080

@ToPlaywright
@sanity
@edge
@SRS-56.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68625
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use
Scenario: The timeline is scrollable horizontally after the timeline is zoomed in
Given the "OncoTestPatient, Al" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the 'Zoom in' button is clicked by the user 2 times
And the timeline is scrolled vertically to the "Biomarkers" swimlane
And the below event on "Pathology and Labs" swimlane is visible in the viewport:
| event_type | text_on_event_point | name_on_label                                                    | date_on_timeline_axis |
| MARKER     | <EMPTY>             | Complete blood count (hemogram) panel - Blood by Automated count | Nov 10, 2014          |
And the below event on "Pathology and Labs" swimlane is not visible in the viewport:
| event_type | text_on_event_point | name_on_label | date_on_timeline_axis       |
| CLUSTER    | 2                   | 2 events      | Mar 08, 2020 - Mar 08, 2020 |
When the user scrolls the timeline horizontally to the end
Then the below event on "Pathology and Labs" swimlane is visible in the viewport:
| event_type | text_on_event_point | name_on_label | date_on_timeline_axis       |
| CLUSTER    | 2                   | 2 events      | Mar 08, 2020 - Mar 08, 2020 |
And the below event on "Pathology and Labs" swimlane is not visible in the viewport:
| event_type | text_on_event_point | name_on_label                                                    | date_on_timeline_axis |
| MARKER     | <EMPTY>             | Complete blood count (hemogram) panel - Blood by Automated count | Nov 10, 2014          |

@SRS-56.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68625
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Scroll_Timeline_Horizontally
Scenario: The timeline is scrollable horizontally after a side panel is opened
Given the "OncoTestPatient, Eleanor470" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the timeline toolbar section is displayed
And the 'Zoom in' button is clicked by the user 1 times
When the user scrolls the timeline horizontally to the end
Then the below events on "Imaging" swimlane are visible in the viewport:
| event_type | name_on_label           | text_on_event_point | date_on_timeline_axis |
| MARKER     | Mammography (procedure) | <EMPTY>             | Oct 08, 2018          |
| MARKER     | Mammography (procedure) | <EMPTY>             | Aug 26, 2019          |
And the below events on "Uncategorized" swimlane are not visible in the viewport:
| event_type | name_on_label              | text_on_event_point | date_on_timeline_axis |
| MARKER     | History and physical note  | <EMPTY>             | Jan 30, 1989          |
When the user clicks on the following event point on the "Imaging" swimlane:
| event_type | name_on_label           | date_on_timeline_axis |
| MARKER     | Mammography (procedure) | Oct 08, 2018          |
Then the sidebar is displayed
And the sidebar header contains the following data:
| title                   | creation_date | report_type |
| Mammography (procedure) | Oct 08, 2018  | Imaging     |
When the user scrolls the timeline horizontally to the left side
And the user scrolls the timeline vertically to the "Uncategorized" swimlane
Then the below events on "Uncategorized" swimlane are visible in the viewport:
| event_type | name_on_label              | text_on_event_point | date_on_timeline_axis |
| MARKER     | History and physical note  | <EMPTY>             | Jan 30, 1989          |

@SRS-56.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68625
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Scroll_Timeline_Horizontally
Scenario: The timeline is scrollable horizontally after the timeline is zoomed in and a side panel is opened
Given the "OncoTestPatient, Casper" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the 'Zoom in' button is clicked by the user 2 times
And the following event point is selected on the "Encounters" swimlane:
| event_type | name_on_label       | date_on_timeline_axis |
| MARKER     | Body Weight 48.8 kg | Mar 15, 1928          |
And the below events on "Encounters" swimlane is not visible in the viewport:
| event_type | name_on_label       | date_on_timeline_axis |
| MARKER     | Body Weight 51.2 kg | Mar 15, 2021          |
And the below event on "Encounters" swimlane is visible in the viewport:
| event_type | name_on_label       | date_on_timeline_axis |
| MARKER     | Body Weight 48.8 kg | Mar 15, 1928          |
When the user scrolls the timeline horizontally to the end
Then the below event on "Encounters" swimlane is visible in the viewport:
| event_type | name_on_label       | date_on_timeline_axis |
| MARKER     | Body Weight 51.2 kg | Mar 15, 2021          |
And the below event on "Encounters" swimlane is not visible in the viewport:
| event_type | name_on_label       | date_on_timeline_axis |
| MARKER     | Body Weight 48.8 kg | Mar 15, 1928          |

@SRS-56.04
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68625
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Scroll_Timeline_Horizontally
Scenario: Notification appears on the sidepanel when timeline is scrolled horizontally and the event is outside of the viewport, and notification disappears after the event is scrolled into the viewport
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the 'Zoom in' button is clicked by the user 1 times
And the below event on "Encounters" swimlane is visible in the viewport:
| event_type | name_on_label       |
| MARKER     | Body Weight 47.3 kg |
And the following event point is selected on the "Encounters" swimlane:
| event_type | name_on_label       |
| MARKER     | Body Weight 47.3 kg |
And the sidebar is displayed
And the sidebar header contains the following data:
| title               | creation_date | report_type |
| Body Weight 47.3 kg | Jan 23, 2013  | Encounters  |
And notification banner is not available in the sidebar header
When the user scrolls the timeline horizontally to the end
Then the sidebar header contains the following notification banner:
| banner_message_text                        | button_in_banner    |
| This event is outside of visible timeline. | Focus on this event |
And the below event on "Encounters" swimlane is not visible in the viewport:
| event_type | name_on_label       |
| MARKER     | Body Weight 47.3 kg |
When the user scrolls the timeline horizontally to the left side
Then the below event on "Encounters" swimlane is visible in the viewport:
| event_type | name_on_label       |
| MARKER     | Body Weight 47.3 kg |
And notification banner is not available in the sidebar header

@SRS-56.05
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68625
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Scroll_Timeline_Horizontally
Scenario: Notification appears on the sidepanel when timeline is scrolled horizontally and the event is outside of the viewport, and notification disappears after 'Focus on this event' is clicked
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the 'Zoom in' button is clicked by the user 1 times
And the below event on "Encounters" swimlane is visible in the viewport:
| event_type | name_on_label       |
| MARKER     | Body Weight 47.3 kg |
And the following event point is selected on the "Encounters" swimlane:
| event_type | name_on_label       |
| MARKER     | Body Weight 47.3 kg |
And the sidebar is displayed
And the sidebar header contains the following data:
| title               | creation_date | report_type |
| Body Weight 47.3 kg | Jan 23, 2013  | Encounters  |
And notification banner is not available in the sidebar header
When the user scrolls the timeline horizontally to the end
Then the sidebar header contains the following notification banner:
| banner_message_text                        | button_in_banner    |
| This event is outside of visible timeline. | Focus on this event |
And the below event on "Encounters" swimlane is not visible in the viewport:
| event_type | name_on_label       |
| MARKER     | Body Weight 47.3 kg |
When the user clicks on the button on the following notification banner on the sidebar header:
| banner_message_text                        | button_in_banner    |
| This event is outside of visible timeline. | Focus on this event |
Then the below event on "Encounters" swimlane is visible in the viewport:
| event_type | name_on_label       |
| MARKER     | Body Weight 47.3 kg |
And notification banner is not available in the sidebar header
