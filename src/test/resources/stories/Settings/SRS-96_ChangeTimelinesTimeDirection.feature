Feature: [SysRS-68584] Change Timeline's Time Direction

Narrative: The application users shall be able to change the timeline's time direction to see newest event on the right or on the left.


Background:
Given the user is logged out from OncoCare application

@sanity
@edge
@SRS-96.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68584
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Settings
Scenario: Change timeline direction to show the newest event on left
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Franklin" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the "Encounters" swimlane contains the following events from left to the right:
| event_type | text_on_event_point | name_on_label     | date_on_timeline_axis       |
| MARKER     | <EMPTY>             | Body Weight 41 kg | May 22, 1991                |
| MARKER     | <EMPTY>             | Body Weight 35 kg | Apr 23, 1996                |
| CLUSTER    | 3                   | 3 events          | Aug 26, 2001 - Jul 15, 2002 |
| MARKER     | <EMPTY>             | Body Weight 31 kg | Oct 23, 2006                |
| MARKER     | <EMPTY>             | Body Weight 39 kg | Aug 13, 2011                |
And the timeline axis values are ascending from left to the right
And the user navigated to the Settings page
And the following settings are available on the page:
| settings_title | setting_name | setting_value                   |
| Timeline       | Direction    | Newest event on right (default) |
When the user clicks on 'Edit' button at the following user setting:
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
And the timeline axis values are descending from left to the right
When the user clicks on the 'All time' button on the timeline toolbar
Then the "Encounters" swimlane contains the following events from left to the right:
| event_type | text_on_event_point | name_on_label     | date_on_timeline_axis       |
| MARKER     | <EMPTY>             | Body Weight 39 kg | Aug 13, 2011                |
| MARKER     | <EMPTY>             | Body Weight 31 kg | Oct 23, 2006                |
| CLUSTER    | 3                   | 3 events          | Jul 15, 2002 - Aug 26, 2001 |
| MARKER     | <EMPTY>             | Body Weight 35 kg | Apr 23, 1996                |
| MARKER     | <EMPTY>             | Body Weight 41 kg | May 22, 1991                |

@SRS-96.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68584
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Settings
Scenario: Cancel to change timeline direction to show the newest event on left
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Franklin" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the "Encounters" swimlane contains the following events from left to the right:
| event_type | text_on_event_point | name_on_label     | date_on_timeline_axis       |
| MARKER     | <EMPTY>             | Body Weight 41 kg | May 22, 1991                |
| MARKER     | <EMPTY>             | Body Weight 35 kg | Apr 23, 1996                |
| CLUSTER    | 3                   | 3 events          | Aug 26, 2001 - Jul 15, 2002 |
| MARKER     | <EMPTY>             | Body Weight 31 kg | Oct 23, 2006                |
| MARKER     | <EMPTY>             | Body Weight 39 kg | Aug 13, 2011                |
And the timeline axis values are ascending from left to the right
And the user navigated to the Settings page
And the following settings are available on the page:
| settings_title | setting_name | setting_value                   |
| Timeline       | Direction    | Newest event on right (default) |
When the user clicks on 'Edit' button at the following user setting:
| settings_title | setting_name |
| Timeline       | Direction    |
And the user selects the "Newest event on left" radio button at the following user setting:
| settings_title | setting_name |
| Timeline       | Direction    |
And the user clicks on the "Cancel" button on the Settings page
Then the following settings are available on the page:
| settings_title | setting_name | setting_value                   |
| Timeline       | Direction    | Newest event on right (default) |
When the user clicks on the "Back" button in the Settings toolbar header
Then the patient timeline is loaded
When the user clicks on the 'All time' button on the timeline toolbar
Then the timeline axis values are ascending from left to the right
And the "Encounters" swimlane contains the following events from left to the right:
| event_type | text_on_event_point | name_on_label     | date_on_timeline_axis       |
| MARKER     | <EMPTY>             | Body Weight 41 kg | May 22, 1991                |
| MARKER     | <EMPTY>             | Body Weight 35 kg | Apr 23, 1996                |
| CLUSTER    | 3                   | 3 events          | Aug 26, 2001 - Jul 15, 2002 |
| MARKER     | <EMPTY>             | Body Weight 31 kg | Oct 23, 2006                |
| MARKER     | <EMPTY>             | Body Weight 39 kg | Aug 13, 2011                |

@SRS-96.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68584
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Settings
Scenario: Change back the timeline direction to show the newest event on right
Given [API] the timeline direction is set by the user [DEFAULT-TEST-USER] to show the newest event on left
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Franklin" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the "Encounters" swimlane contains the following events from left to the right:
| event_type | text_on_event_point | name_on_label     | date_on_timeline_axis       |
| MARKER     | <EMPTY>             | Body Weight 39 kg | Aug 13, 2011                |
| MARKER     | <EMPTY>             | Body Weight 31 kg | Oct 23, 2006                |
| CLUSTER    | 3                   | 3 events          | Jul 15, 2002 - Aug 26, 2001 |
| MARKER     | <EMPTY>             | Body Weight 35 kg | Apr 23, 1996                |
| MARKER     | <EMPTY>             | Body Weight 41 kg | May 22, 1991                |
And the timeline axis values are descending from left to the right
And the user navigated to the Settings page
And the following settings are available on the page:
| settings_title | setting_name | setting_value        |
| Timeline       | Direction    | Newest event on left |
When the user clicks on 'Edit' button at the following user setting:
| settings_title | setting_name |
| Timeline       | Direction    |
And the user selects the "Newest event on right (default)" radio button at the following user setting:
| settings_title | setting_name |
| Timeline       | Direction    |
And the user clicks on the "Save" button on the Settings page
Then the following settings are available on the page:
| settings_title | setting_name | setting_value                   |
| Timeline       | Direction    | Newest event on right (default) |
When the user clicks on the "Back" button in the Settings toolbar header
Then the patient timeline is loaded
When the user clicks on the 'All time' button on the timeline toolbar
Then the timeline axis values are ascending from left to the right
And the "Encounters" swimlane contains the following events from left to the right:
| event_type | text_on_event_point | name_on_label     | date_on_timeline_axis       |
| MARKER     | <EMPTY>             | Body Weight 41 kg | May 22, 1991                |
| MARKER     | <EMPTY>             | Body Weight 35 kg | Apr 23, 1996                |
| CLUSTER    | 3                   | 3 events          | Aug 26, 2001 - Jul 15, 2002 |
| MARKER     | <EMPTY>             | Body Weight 31 kg | Oct 23, 2006                |
| MARKER     | <EMPTY>             | Body Weight 39 kg | Aug 13, 2011                |

@SRS-96.04
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68584
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Settings
Scenario: "Today" marker is displayed on the left side of the timeline axis if timeline direction is set to display the newest event on right
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
And the "Timeline" view is selected
And the full time range is displayed on the timeline
And the timeline axis values are ascending from left to the right
Then "Now" event marker is displayed on the "right" side of the timeline axis
Given the user navigated to the Settings page
When the user clicks on 'Edit' button at the following user setting:
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
When the user clicks on the 'All time' button on the timeline toolbar
Then the timeline axis values are descending from left to the right
And "Now" event marker is displayed on the "left" side of the timeline axis
