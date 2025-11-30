Feature: [SysRS-68587] Settings – store view state, user level

Narrative: When presenting data, the application shall apply the stored user settings and view state for the following views and their sub-views:
           - CLP AI service on/off
           - Timeline view (direction and swimlane order)

Background:
Given the user is logged out from OncoCare application

@sanity
@edge
@SRS-94.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68585,SysRS-68587
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use
Scenario: Swimlane order is saved as user preference and kept after the user logs out from the application
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the user navigated to the Settings page
And the "Swimlane order" setting in the "Timeline" section contains the following ordered list:
| swimlane_names            |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Biomarkers                |
| Treatment and Plan        |
| Uncategorized             |
When the user clicks on 'Edit' button at the following user setting:
| settings_title | setting_name   |
| Timeline       | Swimlane order |
And the user sets the following "Swimlane order" list in "Timeline" section:
| swimlane_names            |
| Imaging                   |
| Encounters                |
| Pathology and Labs        |
| Uncategorized             |
| Treatment and Plan        |
| Biomarkers                |
And the user clicks on the "Save" button on the Settings page
Then the "Swimlane order" setting in the "Timeline" section contains the following ordered list:
| swimlane_names            |
| Imaging                   |
| Encounters                |
| Pathology and Labs        |
| Uncategorized             |
| Treatment and Plan        |
| Biomarkers                |
When the user logs out from OncoCare
Then the user is navigated to the OncoCare splash screen
When the [DEFAULT-TEST-USER] user logs in to OncoCare
And the user navigates to the Settings page
Then the "Swimlane order" setting in the "Timeline" section contains the following ordered list:
| swimlane_names            |
| Imaging                   |
| Encounters                |
| Pathology and Labs        |
| Uncategorized             |
| Treatment and Plan        |
| Biomarkers                |

@SRS-94.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68585
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Save_Swimlanes_Order_As_User_Preference
Scenario: Cancel the editing of swimlane order user preference
Given [API] the following swimlane order was set by the [DEFAULT-TEST-USER] user:
| swimlane_names            |
| Treatment and Plan        |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Uncategorized             |
| Biomarkers                |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the swimlanes are available on the timeline in the following order:
| swimlane_names            |
| Treatment and Plan        |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Uncategorized             |
| Biomarkers                |
And the user navigated to the Settings page
And the "Swimlane order" setting in the "Timeline" section contains the following ordered list:
| swimlane_names            |
| Treatment and Plan        |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Uncategorized             |
| Biomarkers                |
When the user clicks on 'Edit' button at the following user setting:
| settings_title | setting_name   |
| Timeline       | Swimlane order |
And the user sets the following "Swimlane order" list in "Timeline" section:
| swimlane_names            |
| Imaging                   |
| Encounters                |
| Pathology and Labs        |
| Uncategorized             |
| Treatment and Plan        |
| Biomarkers                |
And the user clicks on the "Cancel" button on the Settings page
Then the "Swimlane order" setting in the "Timeline" section contains the following ordered list:
| swimlane_names            |
| Treatment and Plan        |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Uncategorized             |
| Biomarkers                |
When the user clicks on the "Back" button in the Settings toolbar header
Then the patient timeline is loaded
And the swimlanes are available on the timeline in the following order:
| swimlane_names            |
| Treatment and Plan        |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Uncategorized             |
| Biomarkers                |

@sanity
@SRS-94.03
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68585
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use/Save_Swimlanes_Order_As_User_Preference
Scenario: Different swimlane orders and timeline direction can be saved for different users
Given [API] the following swimlane order was set by the [DEFAULT-TEST-USER] user:
| swimlane_names            |
| Uncategorized             |
| Encounters                |
| Treatment and Plan        |
| Pathology and Labs        |
| Biomarkers                |
| Imaging                   |
And [API] the following swimlane order was set by the [TEST-USER-2] user:
| swimlane_names            |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Biomarkers                |
| Treatment and Plan        |
| Uncategorized             |
And [API] the timeline direction is set by the user [DEFAULT-TEST-USER] to show the newest event on right
And [API] the timeline direction is set by the user [TEST-USER-2] to show the newest event on left
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the swimlanes are available on the timeline in the following order:
| swimlane_names            |
| Uncategorized             |
| Encounters                |
| Treatment and Plan        |
| Pathology and Labs        |
| Biomarkers                |
| Imaging                   |
And the user navigated to the Settings page
And the "Swimlane order" setting in the "Timeline" section contains the following ordered list:
| swimlane_names            |
| Uncategorized             |
| Encounters                |
| Treatment and Plan        |
| Pathology and Labs        |
| Biomarkers                |
| Imaging                   |
And the following settings are available on the page:
| settings_title | setting_name | setting_value                   |
| Timeline       | Direction    | Newest event on right (default) |
When the user clicks on 'Edit' button at the following user setting:
| settings_title | setting_name   |
| Timeline       | Swimlane order |
And the user sets the following "Swimlane order" list in "Timeline" section:
| swimlane_names            |
| Imaging                   |
| Uncategorized             |
| Encounters                |
| Treatment and Plan        |
| Pathology and Labs        |
| Biomarkers                |
And the user clicks on the "Save" button on the Settings page
Then the "Swimlane order" setting in the "Timeline" section contains the following ordered list:
| swimlane_names            |
| Imaging                   |
| Uncategorized             |
| Encounters                |
| Treatment and Plan        |
| Pathology and Labs        |
| Biomarkers                |
When the user clicks on the "Back" button in the Settings toolbar header
Then the patient timeline is loaded
And the swimlanes are available on the timeline in the following order:
| swimlane_names            |
| Imaging                   |
| Uncategorized             |
| Encounters                |
| Treatment and Plan        |
| Pathology and Labs        |
| Biomarkers                |
When the user logs out from OncoCare
Then the user is navigated to the OncoCare splash screen
When the [TEST-USER-2] user logs in to OncoCare
And the user selects the "OncoTestPatient, Juno" patient
And the user navigates to the "Timeline" view
Then the swimlanes are available on the timeline in the following order:
| swimlane_names            |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Biomarkers                |
| Treatment and Plan        |
| Uncategorized             |
When the user navigates to the Settings page
Then the "Swimlane order" setting in the "Timeline" section contains the following ordered list:
| swimlane_names            |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Biomarkers                |
| Treatment and Plan        |
| Uncategorized             |
And the following settings are available on the page:
| settings_title | setting_name | setting_value        |
| Timeline       | Direction    | Newest event on left |

@SRS-94.04
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68585
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Save_Swimlanes_Order_As_User_Preference
Scenario: Cancel to set back the swimlane order user preference to default
Given [API] the following swimlane order was set by the [DEFAULT-TEST-USER] user:
| swimlane_names            |
| Pathology and Labs        |
| Imaging                   |
| Uncategorized             |
| Biomarkers                |
| Encounters                |
| Treatment and Plan        |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the swimlanes are available on the timeline in the following order:
| swimlane_names            |
| Pathology and Labs        |
| Imaging                   |
| Uncategorized             |
| Biomarkers                |
| Encounters                |
| Treatment and Plan        |
And the user navigated to the Settings page
And the "Swimlane order" setting in the "Timeline" section contains the following ordered list:
| swimlane_names            |
| Pathology and Labs        |
| Imaging                   |
| Uncategorized             |
| Biomarkers                |
| Encounters                |
| Treatment and Plan        |
When the user clicks on 'Edit' button at the following user setting:
| settings_title | setting_name   |
| Timeline       | Swimlane order |
And the user clicks on the "Reset to default" button on the Settings page
Then the "Swimlane order" setting in the "Timeline" section contains the following ordered list:
| swimlane_names            |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Biomarkers                |
| Treatment and Plan        |
| Uncategorized             |
When the user clicks on the "Cancel" button on the Settings page
Then the "Swimlane order" setting in the "Timeline" section contains the following ordered list:
| swimlane_names            |
| Pathology and Labs        |
| Imaging                   |
| Uncategorized             |
| Biomarkers                |
| Encounters                |
| Treatment and Plan        |
When the user clicks on the "Back" button in the Settings toolbar header
Then the patient timeline is loaded
And the swimlanes are available on the timeline in the following order:
| swimlane_names            |
| Pathology and Labs        |
| Imaging                   |
| Uncategorized             |
| Biomarkers                |
| Encounters                |
| Treatment and Plan        |

@SRS-94.05
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68585
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Save_Swimlanes_Order_As_User_Preference
Scenario: Alert modal is displayed to the user when trying to leave Settings page while editing swimlane order (Leave page)
Given [API] the following swimlane order was set by the [DEFAULT-TEST-USER] user:
| swimlane_names            |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Uncategorized             |
| Biomarkers                |
| Treatment and Plan        |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the swimlanes are available on the timeline in the following order:
| swimlane_names            |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Uncategorized             |
| Biomarkers                |
| Treatment and Plan        |
And the user navigated to the Settings page
When the user clicks on 'Edit' button at the following user setting:
| settings_title | setting_name   |
| Timeline       | Swimlane order |
And the user sets the following "Swimlane order" list in "Timeline" section:
| swimlane_names            |
| Imaging                   |
| Pathology and Labs        |
| Encounters                |
| Uncategorized             |
| Biomarkers                |
| Treatment and Plan        |
And the user clicks on the "Back" button in the Settings toolbar header
Then an alert modal is displayed
And the alert modal contains the following title: "Leave page?"
And the alert modal contains the following message: text_files/Leave_settings_alert.txt
When the user clicks on the "Leave page" button on the alert modal
Then the alert modal disappears
And the patient timeline is loaded
And the swimlanes are available on the timeline in the following order:
| swimlane_names            |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Uncategorized             |
| Biomarkers                |
| Treatment and Plan        |

@SRS-94.06
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68585
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Save_Swimlanes_Order_As_User_Preference
Scenario: Alert modal is displayed to the user when trying to leave Settings page while editing swimlane order (Keep editing)
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the user navigated to the Settings page
When the user clicks on 'Edit' button at the following user setting:
| settings_title | setting_name   |
| Timeline       | Swimlane order |
And the user sets the following "Swimlane order" list in "Timeline" section:
| swimlane_names            |
| Imaging                   |
| Pathology and Labs        |
| Encounters                |
| Uncategorized             |
| Biomarkers                |
| Treatment and Plan        |
And the user clicks on the "Back" button in the Settings toolbar header
Then an alert modal is displayed
And the alert modal contains the following title: "Leave page?"
And the alert modal contains the following message: text_files/Leave_settings_alert.txt
When the user clicks on the "Keep editing" button on the alert modal
Then the alert modal disappears
And the Settings page is displayed
And the "Swimlane order" setting in the "Timeline" section contains the following ordered list:
| swimlane_names            |
| Imaging                   |
| Pathology and Labs        |
| Encounters                |
| Uncategorized             |
| Biomarkers                |
| Treatment and Plan        |

@SRS-94.07
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68584,SysRS-68587
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Save_Swimlanes_Order_As_User_Preference
Scenario: Timeline direction is saved as a user preference and kept after logout
Given [API] the timeline direction is set by the user [DEFAULT-TEST-USER] to show the newest event on right
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
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
When the user clicks on the "Settings" menu item on the user menu panel
Then the Settings page is displayed
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
When the user clicks on the 'All time' button on the timeline toolbar
Then the "Encounters" swimlane contains the following events from left to the right:
| event_type | text_on_event_point | name_on_label     | date_on_timeline_axis       |
| MARKER     | <EMPTY>             | Body Weight 39 kg | Aug 13, 2011                |
| MARKER     | <EMPTY>             | Body Weight 31 kg | Oct 23, 2006                |
| CLUSTER    | 3                   | 3 events          | Jul 15, 2002 - Aug 26, 2001 |
| MARKER     | <EMPTY>             | Body Weight 35 kg | Apr 23, 1996                |
| MARKER     | <EMPTY>             | Body Weight 41 kg | May 22, 1991                |
When the user logs out from OncoCare
Then the user is navigated to the OncoCare splash screen
When the [DEFAULT-TEST-USER] user logs in to OncoCare
And the user selects the "OncoTestPatient, Franklin" patient
And the user navigates to the "Timeline" view
And the patient timeline is loaded
And the user clicks on the 'All time' button on the timeline toolbar
Then the "Encounters" swimlane contains the following events from left to the right:
| event_type | text_on_event_point | name_on_label     | date_on_timeline_axis       |
| MARKER     | <EMPTY>             | Body Weight 39 kg | Aug 13, 2011                |
| MARKER     | <EMPTY>             | Body Weight 31 kg | Oct 23, 2006                |
| CLUSTER    | 3                   | 3 events          | Jul 15, 2002 - Aug 26, 2001 |
| MARKER     | <EMPTY>             | Body Weight 35 kg | Apr 23, 1996                |
| MARKER     | <EMPTY>             | Body Weight 41 kg | May 22, 1991                |
And the timeline axis values are descending from left to the right
When the user clicks on the "Settings" menu item on the user menu panel
Then the Settings page is displayed
And the following settings are available on the page:
| settings_title | setting_name | setting_value        |
| Timeline       | Direction    | Newest event on left |

@SRS-94.08
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68584,SysRS-68587
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Save_Swimlanes_Order_As_User_Preference
Scenario: Alert modal is displayed to the user when trying to leave Settings page while editing timeline direction (Leave page)
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
And the user clicks on the "Back" button in the Settings toolbar header
Then an alert modal is displayed
And the alert modal contains the following title: "Leave page?"
And the alert modal contains the following message: text_files/Leave_settings_alert.txt
When the user clicks on the "Leave page" button on the alert modal
Then the alert modal disappears
And the patient timeline is loaded
When the user clicks on the 'All time' button on the timeline toolbar
Then the timeline axis values are ascending from left to the right
And the "Encounters" swimlane contains the following events from left to the right:
| event_type | text_on_event_point | name_on_label     | date_on_timeline_axis       |
| MARKER     | <EMPTY>             | Body Weight 41 kg | May 22, 1991                |
| MARKER     | <EMPTY>             | Body Weight 35 kg | Apr 23, 1996                |
| CLUSTER    | 3                   | 3 events          | Aug 26, 2001 - Jul 15, 2002 |
| MARKER     | <EMPTY>             | Body Weight 31 kg | Oct 23, 2006                |
| MARKER     | <EMPTY>             | Body Weight 39 kg | Aug 13, 2011                |

@SRS-94.09
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68584,SysRS-68587
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Save_Swimlanes_Order_As_User_Preference
Scenario: Alert modal is displayed to the user when trying to leave Settings page while editing timeline direction (Keep editing)
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
And the user clicks on the "Back" button in the Settings toolbar header
Then an alert modal is displayed
And the alert modal contains the following title: "Leave page?"
And the alert modal contains the following message: text_files/Leave_settings_alert.txt
When the user clicks on the "Keep editing" button on the alert modal
Then the alert modal disappears
And the Settings page is displayed