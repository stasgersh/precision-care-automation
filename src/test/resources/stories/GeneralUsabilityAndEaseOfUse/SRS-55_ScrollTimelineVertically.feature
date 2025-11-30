@safety
Feature: [SysRS-68624] Scroll Timeline Vertically

Narrative: The system users shall be able to scroll the timeline vertically.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the browser size is set to: width=1920, height=1080
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded


@sanity
@edge
@SRS-55.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68624
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use/Scroll_Timeline_Vertically
Scenario: The user can scroll down the timeline vertically
Given the full time range is displayed on the timeline
And the swimlanes are available on the timeline in the following order:
| swimlane_names            |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Biomarkers                |
| Treatment and Plan        |
| Uncategorized             |
And the below swimlanes are visible in the viewport:
| swimlane_names            |
| Encounters                |
| Imaging                   |
And the below swimlanes are not visible in the viewport:
| swimlane_names            |
| Treatment and Plan        |
| Uncategorized             |
When the user scrolls the timeline vertically to the bottom
Then the below swimlanes are visible in the viewport:
| swimlane_names            |
| Treatment and Plan        |
| Uncategorized             |
And the below swimlanes are not visible in the viewport:
| swimlane_names            |
| Encounters                |
| Imaging                   |

@sanity
@edge
@SRS-55.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68624
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use/Scroll_Timeline_Vertically
Scenario: The user can scroll up the timeline vertically
Given the full time range is displayed on the timeline
And the swimlanes are available on the timeline in the following order:
| swimlane_names            |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Biomarkers                |
| Treatment and Plan        |
| Uncategorized             |
And the timeline is scrolled vertically to the bottom
And the below swimlanes are visible in the viewport:
| swimlane_names            |
| Treatment and Plan        |
| Uncategorized             |
And the below swimlanes are not visible in the viewport:
| swimlane_names            |
| Encounters                |
| Imaging                   |
When the user scrolls the timeline vertically to the top
Then the below swimlanes are visible in the viewport:
| swimlane_names            |
| Encounters                |
| Imaging                   |
And the below swimlanes are not visible in the viewport:
| swimlane_names            |
| Treatment and Plan        |
| Uncategorized             |

@SRS-55.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68624
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Scroll_Timeline_Vertically
Scenario: The user can scroll the timeline when a side panel is opened
Given the full time range is displayed on the timeline
And the swimlanes are available on the timeline in the following order:
| swimlane_names            |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Biomarkers                |
| Treatment and Plan        |
| Uncategorized             |
And the below swimlanes are visible in the viewport:
| swimlane_names            |
| Encounters                |
| Imaging                   |
And the below swimlanes are not visible in the viewport:
| swimlane_names            |
| Treatment and Plan        |
| Uncategorized             |
When the user clicks on the following event point on the "Imaging" swimlane:
| event_type | text_on_event_point | name_on_label                          | date_on_timeline_axis |
| MARKER     | <EMPTY>             | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
And the user scrolls the timeline vertically to the bottom
Then the below swimlanes are visible in the viewport:
| swimlane_names            |
| Treatment and Plan        |
| Uncategorized             |
And the below swimlanes are not visible in the viewport:
| swimlane_names            |
| Encounters                |
| Imaging                   |

@SRS-55.04
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68624
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Scroll_Timeline_Vertically
Scenario: Notification appears on the sidepanel when timeline is scrolled vertically and the event is outside of the viewport, and notification disappears after the event is scrolled into the viewport
Given the full time range is displayed on the timeline
And the below swimlane is visible in the viewport:
| swimlane_names |
| Imaging        |
And the below event on "Imaging" swimlane is visible in the viewport:
| event_type | name_on_label                          |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST |
And the following event point is selected on the "Imaging" swimlane:
| event_type | name_on_label                          |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST |
And the sidebar is displayed
And the sidebar header contains the following data:
| title                                  | creation_date | report_type |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011  | Imaging     |
And notification banner is not available in the sidebar header
When the user scrolls the timeline vertically to the bottom
Then the sidebar header contains the following notification banner:
| banner_message_text                        | button_in_banner    |
| This event is outside of visible timeline. | Focus on this event |
And the below swimlane is not visible in the viewport:
| swimlane_names |
| Imaging        |
When the user scrolls the timeline vertically to the top
Then the below swimlane is visible in the viewport:
| swimlane_names |
| Imaging        |
And the below event on "Imaging" swimlane is visible in the viewport:
| event_type | name_on_label                          |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST |
And notification banner is not available in the sidebar header

@SRS-55.05
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68624
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Scroll_Timeline_Vertically
Scenario: Notification appears on the sidepanel when timeline is scrolled vertically and the event is outside of the viewport, and notification disappears after 'Focus on this event' is clicked
Given the full time range is displayed on the timeline
And the below swimlane is visible in the viewport:
| swimlane_names |
| Imaging        |
And the below event on "Imaging" swimlane is visible in the viewport:
| event_type | name_on_label                          |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST |
And the following event point is selected on the "Imaging" swimlane:
| event_type | name_on_label                          |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST |
And the sidebar is displayed
And the sidebar header contains the following data:
| title                                  | creation_date | report_type |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011  | Imaging     |
And notification banner is not available in the sidebar header
When the user scrolls the timeline vertically to the bottom
Then the sidebar header contains the following notification banner:
| banner_message_text                        | button_in_banner    |
| This event is outside of visible timeline. | Focus on this event |
And the below swimlane is not visible in the viewport:
| swimlane_names |
| Imaging        |
When the user clicks on the button on the following notification banner on the sidebar header:
| banner_message_text                        | button_in_banner    |
| This event is outside of visible timeline. | Focus on this event |
Then the below swimlane is visible in the viewport:
| swimlane_names |
| Imaging        |
And the below event on "Imaging" swimlane is visible in the viewport:
| event_type | name_on_label                          |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST |
And notification banner is not available in the sidebar header
