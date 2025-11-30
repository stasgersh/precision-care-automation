Feature: [SysRS-68623] Zoom In And Out On Timeline

Narrative: The system users shall be able to zoom in and out on the timeline.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the browser size is set to: width=1920, height=1080

@sanity
@edge
@SRS-54.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68623
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use/Zoom_In_And_Out_On_Timeline
Scenario: The user can zoom in on the timeline
Given the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
When the user clicks on the 'Zoom in' button 5 times
And the user moves to the following event on "Treatment and Plan" swimlane:
| event_type | name_on_label                   | date_on_timeline_axis |
| MARKER     | cyclophosphamide 1 GM Injection | Feb 24, 2014          |
Then the below event on "Treatment and Plan" swimlane is visible in the viewport:
| event_type | name_on_label                   | date_on_timeline_axis |
| MARKER     | cyclophosphamide 1 GM Injection | Feb 24, 2014          |
And the below events on "Treatment and Plan" swimlane are not visible in the viewport:
| event_type | name_on_label                               | date_on_timeline_axis |
| MARKER     | Excision of sentinel lymph node (procedure) | Jun 14, 2017          |
| MARKER     | Hysteroscopy                                | Apr 04, 2021          |
And the following values are visible on the timeline axis:
| timeline_axis_value |
| 2014 Jan            |
And the following values are not visible on the timeline axis:
| timeline_axis_value |
| 2017 Jun            |
| 2021 Apr            |

@sanity
@edge
@SRS-54.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68623
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use/Zoom_In_And_Out_On_Timeline
Scenario: The user can zoom out on the timeline
Given the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the 'Zoom in' button is clicked by the user 10 times
And the timeline is moved to the following event on "Treatment and Plan" swimlane:
| event_type | name_on_label | date_on_timeline_axis |
| MARKER     | Hysteroscopy  | Apr 04, 2021          |
And the below event on "Treatment and Plan" swimlane is visible in the viewport:
| event_type | name_on_label | date_on_timeline_axis |
| MARKER     | Hysteroscopy  | Apr 04, 2021          |
And the below events on "Treatment and Plan" swimlane are not visible in the viewport:
| event_type | name_on_label                               | date_on_timeline_axis |
| MARKER     | cyclophosphamide 1 GM Injection             | Feb 24, 2014          |
| MARKER     | Excision of sentinel lymph node (procedure) | Jun 14, 2017          |
When the user clicks on the 'Zoom out' button 10 times
Then the below events on "Treatment and Plan" swimlane are visible in the viewport:
| event_type | name_on_label                               | date_on_timeline_axis |
| MARKER     | cyclophosphamide 1 GM Injection             | Feb 24, 2014          |
| MARKER     | Excision of sentinel lymph node (procedure) | Jun 14, 2017          |
| MARKER     | Hysteroscopy                                | Apr 04, 2021          |
And the following values are visible on the timeline axis:
| timeline_axis_value |
| 2011                |
| 2013                |
| 2015                |
| 2017                |
| 2019                |
| 2023                |
| 2025                |

@SRS-54.03
@srs
@edge
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68623
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Zoom_In_And_Out_On_Timeline
Scenario: The user can zoom in on the timeline using the Vertical Toolbar
Given the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the browser size is set to: width=1680, height=840
And the Vertical toolbar section is displayed
When the user clicks the 'Zoom in' button on the vertical toolbar 5 times
And the user moves to the following event on "Treatment and Plan" swimlane:
| event_type | name_on_label                   | date_on_timeline_axis |
| MARKER     | cyclophosphamide 1 GM Injection | Feb 24, 2014          |
Then the below event on "Treatment and Plan" swimlane is visible in the viewport:
| event_type | name_on_label                   | date_on_timeline_axis |
| MARKER     | cyclophosphamide 1 GM Injection | Feb 24, 2014          |
And the below events on "Treatment and Plan" swimlane are not visible in the viewport:
| event_type | name_on_label                               | date_on_timeline_axis |
| MARKER     | Excision of sentinel lymph node (procedure) | Jun 14, 2017          |
| MARKER     | Hysteroscopy                                | Apr 04, 2021          |
And the following values are visible on the timeline axis:
| timeline_axis_value |
| 2014 Jan            |
And the following values are not visible on the timeline axis:
| timeline_axis_value |
| 2017 Jun            |
| 2021 Apr            |

@SRS-54.04
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68623
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Zoom_In_And_Out_On_Timeline
Scenario: The user can zoom out on the timeline using the Vertical Toolbar
Given the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the browser size is set to: width=1680, height=840
And the Vertical toolbar section is displayed
And the 'Zoom in' button on the vertical toolbar is clicked by the user 10 times
And the timeline is moved to the following event on "Treatment and Plan" swimlane:
| event_type | name_on_label | date_on_timeline_axis |
| MARKER     | Hysteroscopy  | Apr 04, 2021          |
And the below event on "Treatment and Plan" swimlane is visible in the viewport:
| event_type | name_on_label | date_on_timeline_axis |
| MARKER     | Hysteroscopy  | Apr 04, 2021          |
And the below events on "Treatment and Plan" swimlane are not visible in the viewport:
| event_type | name_on_label                               | date_on_timeline_axis |
| MARKER     | cyclophosphamide 1 GM Injection             | Feb 24, 2014          |
| MARKER     | Excision of sentinel lymph node (procedure) | Jun 14, 2017          |
When the user clicks the 'Zoom out' button on the vertical toolbar 10 times
Then the below events on "Treatment and Plan" swimlane are visible in the viewport:
| event_type | name_on_label                               | date_on_timeline_axis |
| MARKER     | cyclophosphamide 1 GM Injection             | Feb 24, 2014          |
| MARKER     | Excision of sentinel lymph node (procedure) | Jun 14, 2017          |
| MARKER     | Hysteroscopy                                | Apr 04, 2021          |
And the following values are visible on the timeline axis:
| timeline_axis_value |
| 2011                |
| 2013                |
| 2015                |
| 2017                |
| 2019                |
| 2023                |
| 2025                |

@SRS-54.05
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68623
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Zoom_In_And_Out_On_Timeline
Scenario: The user can zoom in and out on the timeline by using the zoom slider
Given the "OncoTestPatient, Freya" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the below events on "Encounters" swimlane are visible in the viewport:
| event_type | name_on_label       | date_on_timeline_axis |
| MARKER     | Body Weight 15.3 kg | May 22, 1975          |
| MARKER     | Body Weight 56.2 kg | Mar 23, 2004          |
| MARKER     | Body Weight 61.8 kg | Oct 23, 2006          |
When the user moves the zoom slider by mouse to the right side
Then the below event on "Encounters" swimlane is visible in the viewport:
| event_type | name_on_label       | date_on_timeline_axis |
| MARKER     | Body Weight 15.3 kg | May 22, 1975          |
And the below events on "Encounters" swimlane are not visible in the viewport:
| event_type | name_on_label       | date_on_timeline_axis |
| MARKER     | Body Weight 56.2 kg | Mar 23, 2004          |
| MARKER     | Body Weight 61.8 kg | Oct 23, 2006          |
When the user moves the zoom slider by mouse to the left side
Then the below events on "Encounters" swimlane are visible in the viewport:
| event_type | name_on_label       | date_on_timeline_axis |
| MARKER     | Body Weight 15.3 kg | May 22, 1975          |
| MARKER     | Body Weight 56.2 kg | Mar 23, 2004          |
| MARKER     | Body Weight 61.8 kg | Oct 23, 2006          |

@SRS-54.06
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68623
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Zoom_In_And_Out_On_Timeline
Scenario: Notification appears on the sidepanel when timeline is zoomed in and the event is outside of the viewport, and notification disappears after the timeline is zoomed out
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the below event on "Imaging" swimlane is visible in the viewport:
| event_type | name_on_label                          | date_on_timeline_axis |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
And the following event point is selected on the "Imaging" swimlane:
| event_type | name_on_label                          | date_on_timeline_axis |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
And the sidebar is displayed
And the sidebar header contains the following data:
| title                                  | creation_date | report_type |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011  | Imaging     |
And notification banner is not available in the sidebar header
When the user clicks on the 'Zoom in' button 5 times
Then the sidebar header contains the following notification banner:
| banner_message_text                        | button_in_banner    |
| This event is outside of visible timeline. | Focus on this event |
And the below event on "Imaging" swimlane is not visible in the viewport:
| event_type | name_on_label                          | date_on_timeline_axis |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
When the user clicks on the 'All time' button on the timeline toolbar
Then the below event on "Imaging" swimlane is visible in the viewport:
| event_type | name_on_label                          | date_on_timeline_axis |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
And notification banner is not available in the sidebar header

@SRS-54.07
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68623
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Zoom_In_And_Out_On_Timeline
Scenario: Notification appears on the sidepanel when timeline is zoomed in and the event is outside of the viewport, and notification disappears after 'Focus on this event' is clicked
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the below event on "Imaging" swimlane is visible in the viewport:
| event_type | name_on_label                          | date_on_timeline_axis |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
And the following event point is selected on the "Imaging" swimlane:
| event_type | name_on_label                          | date_on_timeline_axis |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
And the sidebar is displayed
And the sidebar header contains the following data:
| title                                  | creation_date | report_type |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011  | Imaging     |
And notification banner is not available in the sidebar header
When the user clicks on the 'Zoom in' button 5 times
Then the sidebar header contains the following notification banner:
| banner_message_text                        | button_in_banner    |
| This event is outside of visible timeline. | Focus on this event |
And the below event on "Imaging" swimlane is not visible in the viewport:
| event_type | name_on_label                          | date_on_timeline_axis |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
When the user clicks on the button on the following notification banner on the sidebar header:
| banner_message_text                        | button_in_banner    |
| This event is outside of visible timeline. | Focus on this event |
Then the below event on "Imaging" swimlane is visible in the viewport:
| event_type | name_on_label                          | date_on_timeline_axis |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
And notification banner is not available in the sidebar header
