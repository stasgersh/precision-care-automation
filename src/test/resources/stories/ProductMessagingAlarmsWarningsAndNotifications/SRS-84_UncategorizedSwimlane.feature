@safety
Feature: [SysRS-68665] Uncategorized swimlane

Narrative: The system shall display events on the Uncategorized swimlane in the cases when:
             - Date information is not available for the record
             - The system fails to determine timeline event domain group.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the patient page is displayed

@deprecated_US167644
@sanity
@SRS-84.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68665
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Event without date information is displayed on the Uncategorized swimlane
Given the "OncoTestPatient, Freya" patient is selected
When the user navigates to the "Timeline" view
And the patient timeline is loaded
And the user clicks on the 'All time' button on the timeline toolbar
And the user clicks on the 'Zoom in' button 1 times
And the user scrolls the timeline vertically to the bottom
And the user scrolls the timeline horizontally to the left side
Then the below event on "Uncategorized" swimlane is not visible in the viewport:
| event_type | name_on_label       | date_on_timeline_axis |
| MARKER     | Body Weight 61.8 kg | Date unknown          |
When the user scrolls the timeline vertically to the bottom
And the user scrolls the timeline horizontally to the end
Then the below event on "Uncategorized" swimlane is visible in the viewport:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 4                   | 4 events      |
When the user clicks on the following event point on the "Uncategorized" swimlane:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 4                   | 4 events      |
And the user selects the following event from the cluster pill:
| event_name          |
| Body Weight 61.8 kg |
Then the sidebar is displayed
And the sidebar header contains the following data:
| title               | creation_date | report_type   |
| Body Weight 61.8 kg | Date unknown  | Uncategorized |
And the sidebar header contains the following notification banners:
| banner_message_text                        | button_in_banner    |
| Date of event is unknown.                  | N/A                 |
| This event is outside of visible timeline. | Focus on this event |

@deprecated_US167644
@srs
@SRS-84.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68665
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Event cluster without date information is displayed on the Uncategorized swimlane
Given [API] the following patient is uploaded to PDS: patients/OncoTestPatient_Hanna.json
And the dates of the following patient's resources are prepared and uploaded to PDS: patients/prepared/OncoTestPatient_Hanna_Uncategorized_HistoryNote.json
And the page is reloaded
And the "OncoTestPatient, Hanna" patient is selected
When the user navigates to the "Timeline" view
And the patient timeline is loaded
And the user clicks on the 'All time' button on the timeline toolbar
Then the following events are available on the "Uncategorized" swimlane:
| event_type | name_on_label | text_on_event_point | date_on_timeline_axis             |
| CLUSTER    | 3 events      | 3                   | <1_DAYS_AGO> - Some dates unknown |
When the user clicks on the following event point on the "Uncategorized" swimlane:
| event_type | name_on_label | text_on_event_point | date_on_timeline_axis             |
| CLUSTER    | 3 events      | 3                   | <1_DAYS_AGO> - Some dates unknown |
Then the cluster pill is opened
And the following events are displayed in the cluster pill:
| event_name                | date_on_tooltip |
| History and physical note | <1_DAYS_AGO>    |
| Body Weight 80.4 kg       | Date unknown    |
| Body Weight 80.6 kg       | Date unknown    |
And the cluster pill displays the following text: "<1_DAYS_AGO> - Some dates unknown"
When the user zooms in the timeline to max resolution
Then the following events are ordered from left to the right on the "Uncategorized" swimlane:
| event_type | name_on_label             | text_on_event_point | date_on_timeline_axis |
| MARKER     | History and physical note | <EMPTY>             | <1_DAYS_AGO>          |
| CLUSTER    | 2 events                  | 2                   | All dates unknown     |
When the user clicks on the following event point on the "Uncategorized" swimlane:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 2                   | 2 events      |
Then the cluster pill is opened
And the following events are displayed in the cluster pill:
| event_name          | date_on_tooltip |
| Body Weight 80.4 kg | Date unknown    |
| Body Weight 80.6 kg | Date unknown    |
And the cluster pill displays the following text: "All dates unknown"
When the user selects the following event from the cluster pill:
| event_name          |
| Body Weight 80.4 kg |
Then the sidebar is displayed
And the sidebar header contains the following data:
| title               | creation_date | report_type   |
| Body Weight 80.4 kg | Date unknown  | Uncategorized |
And the sidebar header contains the following notification banners:
| banner_message_text                        | button_in_banner    |
| Date of event is unknown.                  | N/A                 |
| This event is outside of visible timeline. | Focus on this event |
When the user selects the following event from the cluster pill:
| event_name          |
| Body Weight 80.6 kg |
Then the sidebar is displayed
And the sidebar header contains the following data:
| title               | creation_date | report_type   |
| Body Weight 80.6 kg | Date unknown  | Uncategorized |
And the sidebar header contains the following notification banners:
| banner_message_text                        | button_in_banner    |
| Date of event is unknown.                  | N/A                 |
| This event is outside of visible timeline. | Focus on this event |

@srs
@SRS-84.03
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68665
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Event with not recognized domain group is displayed on the Uncategorized swimlane
Given the "OncoTestPatient, Juno" patient is selected
When the user navigates to the "Timeline" view
And the patient timeline is loaded
And the user zooms in the timeline to max resolution
Then the following event is available on the "Uncategorized" swimlane:
| event_type | name_on_label                                        | date_on_timeline_axis |
| MARKER     | Gleason grade finding for prostatic cancer (finding) | Dec 17, 2006          |
When the user clicks on the following event point on the "Uncategorized" swimlane:
| event_type | name_on_label                                        | date_on_timeline_axis |
| MARKER     | Gleason grade finding for prostatic cancer (finding) | Dec 17, 2006          |
Then the sidebar is displayed
And the sidebar header contains the following data:
| title                                                | creation_date | report_type   |
| Gleason grade finding for prostatic cancer (finding) | Dec 17, 2006  | Uncategorized |
