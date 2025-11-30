@safety
Feature: [SysRS-68662] Service & Network Error Messages

Narrative: The system shall display error message to the user whenever user action has failed (unless otherwise specified) see SysRS-68608. (user interaction, Service/ network issues, loading configuration file etc.)

Note: In case automatic retry mechanism (if available) has successfully finished no user error message shall be displayed.
      User error message content (at minimum):
        - User error message name – indicating the user action that has failed.
        - Additional information to the user and recommended action.


@srs
@SRS-76.01
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68662
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario Outline: Temporary service failure - Failed to load patient data in Views
Given [DEVTOOLS] - the following text patterns were added to "Network request blocking" tool:
| text pattern    |
| summary         |
| timeline        |
| medical-history |
| trends          |
And [DEVTOOLS] - the "Enable network request blocking" checkbox is UNCHECKED in the "Network request blocking" tool
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And no error is displayed on the UI
When [DEVTOOLS] - the user checks "Enable network request blocking" checkbox for <view> view
And the "OncoTestPatient, Freya" patient is selected
And the user navigates to the <view> view
Then a red alert icon is displayed
And the following error message is displayed in the <view> view: "Failed to load patient data."
And "Try again" button is displayed
When [DEVTOOLS] - the user unchecks "Enable network request blocking" checkbox
And clicks "Try again"
Then the <view> view is loaded
And no error is displayed on the UI
Examples:
| view            |
| Summary         |
| Timeline        |
| Medical history |
| Trends          |

@srs
@SRS-76.02
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68662
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Temporary service failure - Failed to load patient list
Given [DEVTOOLS] - the following text patterns were added to "Network request blocking" tool:
| text pattern |
| patients     |
And [DEVTOOLS] - the "Enable network request blocking" checkbox is UNCHECKED in the "Network request blocking" tool
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient list is loaded and not empty
When [DEVTOOLS] - the user checks "Enable network request blocking" checkbox
And refreshes the page
Then the "Select a patient" dropbox is not displayed
And a red alert icon is displayed
And the following error message is displayed: "Sorry, something went wrong on our end."
And "Try again" button is displayed
When [DEVTOOLS] - the user unchecks "Enable network request blocking" checkbox
And clicks "Try again"
Then the patient page is loaded
And the Patient list is loaded and not empty

@srs
@SRS-76.03
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68662
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Failed to load patient details - Patient banner
Given [DEVTOOLS] - the following text patterns were added to "Network request blocking" tool:
| text pattern |
| banner       |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And [DEVTOOLS] - the "Enable network request blocking" checkbox is CHECKED in the "Network request blocking" tool
When the user selects "OncoTestPatient, Freya" patient
Then a red alert icon is displayed on the screen
And the following error message is displayed: "Failed to load patient data."
And "Try again" button is displayed
When [DEVTOOLS] - the user unchecks "Enable network request blocking" checkbox
And clicks "Try again"
Then the Summary page is loaded
And the patient banner is displayed

@srs
@SRS-76.04
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68662
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Failed to load patient details - Side panel trend
Given [DEVTOOLS] - the following text patterns were added to "Network request blocking" tool:
| text pattern |
| trends       |
And [DEVTOOLS] - the "Enable network request blocking" checkbox is UNCHECKED in the "Network request blocking" tool
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Freya" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
When [DEVTOOLS] - the user checks "Enable network request blocking" checkbox
And selects the following event on "Encounters" swimlane:
| event_name          |
| Body Weight 56.2 kg |
Then the side panel is opened
And a red alert icon is displayed on the Side panel
And the following error message is displayed on the Side panel: "Failed to load details."
And "Try again" button is displayed on the Side panel
When [DEVTOOLS] - the user unchecks "Enable network request blocking" checkbox
And clicks "Try again"
Then the side panel is loaded
And the "Body Weight (kg)" trend is displayed

@srs
@SRS-76.05
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68662
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Failed to load labels
Given [DEVTOOLS] - the following text patterns were added to "Network request blocking" tool:
| text pattern |
| labels       |
And [DEVTOOLS] - the "Enable network request blocking" checkbox is UNCHECKED in the "Network request blocking" tool
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And I navigated to the following event at "OncoTestPatient, Freya" patient:
| swimlane   | event_name          |
| Encounters | Body Weight 56.2 kg |
And the side panel is opened
When [DEVTOOLS] - the user checks "Enable network request blocking" checkbox
And clicks on "Label"
Then a red alert icon is displayed
And the following error message is displayed: "Failed to load labels."
And "Try again" button is displayed
When [DEVTOOLS] - the user unchecks "Enable network request blocking" checkbox
And clicks "Try again"
Then the Labels list is loaded

@srs
@SRS-76.06
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68662
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Failed to create label
Given [DEVTOOLS] - the following text patterns were added to "Network request blocking" tool:
| text pattern |
| labels       |
And [DEVTOOLS] - the "Enable network request blocking" checkbox is UNCHECKED in the "Network request blocking" tool
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And I navigated to the following event at "OncoTestPatient, Freya" patient:
| swimlane   | event_name          |
| Encounters | Body Weight 15.3 kg |
And I navigated to the 'Create new label' modal
When the user types the following text into the 'Label name' field: "@SRS-76.07 test label"
And [DEVTOOLS] - the user checks "Enable network request blocking" checkbox
And clicks on "Create" button
Then a notification with red background is displayed as: "Failed to save Label Sorry, something went wrong on our end."

@srs
@SRS-76.07
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68662
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Failed to save comments - Event comment
Given [DEVTOOLS] - the following text patterns were added to "Network request blocking" tool:
| text pattern |
| comments     |
And [DEVTOOLS] - the "Enable network request blocking" checkbox is UNCHECKED in the "Network request blocking" tool
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And I navigated to the following event at "OncoTestPatient, Freya" patient:
| swimlane   | event_name          |
| Encounters | Body Weight 56.2 kg |
And the side panel is opened
And the "Body Weight (kg)" trend is displayed
When the user types the following text into the comment box on the sidebar: "this is a test comment."
And [DEVTOOLS] - the user checks "Enable network request blocking" checkbox
And the user clicks on the send button next to the comment box on the sidebar
Then a notification with red background is displayed as: "Failed to save Comment Sorry, something went wrong on our end."
When [DEVTOOLS] - the user unchecks "Enable network request blocking" checkbox
And the user clicks on the send button next to the comment box on the sidebar
Then the comment "this is a test comment." was successfully added on the sidebar

@srs
@SRS-76.08
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68662
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Failed to delete comments - Event comment
Given [DEVTOOLS] - the following text patterns were added to "Network request blocking" tool:
| text pattern |
| comments     |
And [DEVTOOLS] - the "Enable network request blocking" checkbox is UNCHECKED in the "Network request blocking" tool
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And I navigated to the following event at "OncoTestPatient, Freya" patient:
| swimlane   | event_name          |
| Encounters | Body Weight 56.2 kg |
And the following comment was added to the event: "this is a test comment."
When [DEVTOOLS] - the user checks "Enable network request blocking" checkbox
And the user clicks on the 'Delete' button in the menu of the comment
Then a notification with red background is displayed as: "Failed to delete Comment Sorry, something went wrong on our end."
When [DEVTOOLS] - the user unchecks "Enable network request blocking" checkbox
And the user clicks on the 'Delete' button in the menu of the comment
Then the comment "this is a test comment." were successfully deleted

@SRS-76.09
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68662
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Failed to save comments - Patient comment
Given [DEVTOOLS] - the following text patterns were added to "Network request blocking" tool:
| text pattern |
| comments     |
And [DEVTOOLS] - the "Enable network request blocking" checkbox is UNCHECKED in the "Network request blocking" tool
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
And the "Comments" view is selected
And no comments are present on the "Comments" view
When the user types the following text into the comment box: "this is a test comment."
And [DEVTOOLS] - the user checks "Enable network request blocking" checkbox
And the user clicks on the send button next to the comment box
Then a notification with red background is displayed as: "Failed to save Comment Sorry, something went wrong on our end."
And no comments are present on the "Comments" view
When [DEVTOOLS] - the user unchecks "Enable network request blocking" checkbox
And the user clicks on the send button next to the comment box
Then the comment "this is a test comment." was successfully added

@SRS-76.10
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68662
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Failed to delete comments - Patient comment
Given [DEVTOOLS] - the following text patterns were added to "Network request blocking" tool:
| text pattern |
| comments     |
And [DEVTOOLS] - the "Enable network request blocking" checkbox is UNCHECKED in the "Network request blocking" tool
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
And the "Comments" view is selected
And the following comment were added to "Comments" view: "this is a test comment."
When [DEVTOOLS] - the user checks "Enable network request blocking" checkbox
And the user clicks on the 'Delete' button in the menu of the comment
Then a notification with red background is displayed as: "Failed to delete Comment Sorry, something went wrong on our end."
When [DEVTOOLS] - the user unchecks "Enable network request blocking" checkbox
And the user clicks on the 'Delete' button in the menu of the comment
Then the comment "this is a test comment." were successfully deleted

@SRS-76.11
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68662
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Failed to Save settings
Given [DEVTOOLS] - the following text patterns were added to "Network request blocking" tool:
| text pattern |
| settings     |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the user navigated to the Settings page
When [DEVTOOLS] - the user checks "Enable network request blocking" checkbox
And the user selects the "On" radio button at the following user setting:
| settings_title               | setting_name     |
| Artificial Intelligence (AI) | AI summarization |
And the user clicks on the "Save" button on the Settings page
Then a notification with red background is displayed as: "Failed to save Settings Sorry, something went wrong on our end."
When the user clicks on the "Cancel" button on the Settings page
And the user clicks on 'Edit' button at the following user setting:
| settings_title | setting_name |
| Timeline       | Direction    |
And the user selects the "Newest event on left" radio button at the following user setting:
| settings_title | setting_name |
| Timeline       | Direction    |
And the user clicks on the "Save" button on the Settings page
Then a notification with red background is displayed as: "Failed to save Settings Sorry, something went wrong on our end."
When the user clicks on the "Cancel" button on the Settings page
And the user clicks on 'Edit' button at the following user setting:
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
Then a notification with red background is displayed as: "Failed to save Settings Sorry, something went wrong on our end."
