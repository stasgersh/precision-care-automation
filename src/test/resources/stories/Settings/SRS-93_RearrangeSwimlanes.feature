Feature: [SysRS-68585] Rearrange Swimlanes

Narrative: The application users shall be able to rearrange swim lanes.


Background:
Given the user is logged out from OncoCare application


@sanity
@edge
@SRS-93.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68585
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Settings
Scenario: User can set custom swimlane order on the timeline
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the swimlanes are available on the timeline in the following order:
| swimlane_names            |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Biomarkers                |
| Treatment and Plan        |
| Uncategorized             |
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
| Biomarkers                |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Uncategorized             |
| Treatment and Plan        |
And the user clicks on the "Save" button on the Settings page
Then the "Swimlane order" setting in the "Timeline" section contains the following ordered list:
| swimlane_names            |
| Biomarkers                |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Uncategorized             |
| Treatment and Plan        |
When the user clicks on the "Back" button in the Settings toolbar header
Then the patient timeline is loaded
And the swimlanes are available on the timeline in the following order:
| swimlane_names            |
| Biomarkers                |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Uncategorized             |
| Treatment and Plan        |
When the user selects the "OncoTestPatient, Torvald" patient
And the user navigates to the "Timeline" view
Then the swimlanes are available on the timeline in the following order:
| swimlane_names            |
| Biomarkers                |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Uncategorized             |
| Treatment and Plan        |

@SRS-93.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68585
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Settings
Scenario: User can set back the swimlane order to default
Given [API] the following swimlane order was set by the [DEFAULT-TEST-USER] user:
| swimlane_names            |
| Imaging                   |
| Encounters                |
| Pathology and Labs        |
| Uncategorized             |
| Biomarkers                |
| Treatment and Plan        |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the swimlanes are available on the timeline in the following order:
| swimlane_names            |
| Imaging                   |
| Encounters                |
| Pathology and Labs        |
| Uncategorized             |
| Biomarkers                |
| Treatment and Plan        |
And the user navigated to the Settings page
And the "Swimlane order" setting in the "Timeline" section contains the following ordered list:
| swimlane_names            |
| Imaging                   |
| Encounters                |
| Pathology and Labs        |
| Uncategorized             |
| Biomarkers                |
| Treatment and Plan        |
When the user clicks on 'Edit' button at the following user setting:
| settings_title | setting_name   |
| Timeline       | Swimlane order |
And the user clicks on the "Reset to default" button on the Settings page
And the user clicks on the "Save" button on the Settings page
Then the "Swimlane order" setting in the "Timeline" section contains the following ordered list:
| swimlane_names            |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Biomarkers                |
| Treatment and Plan        |
| Uncategorized             |
When the user clicks on the "Back" button in the Settings toolbar header
Then the patient timeline is loaded
And the swimlanes are available on the timeline in the following order:
| swimlane_names            |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Biomarkers                |
| Treatment and Plan        |
| Uncategorized             |
When the user selects the "OncoTestPatient, Torvald" patient
And the user navigates to the "Timeline" view
Then the swimlanes are available on the timeline in the following order:
| swimlane_names            |
| Encounters                |
| Imaging                   |
| Pathology and Labs        |
| Biomarkers                |
| Treatment and Plan        |
| Uncategorized             |

@manual
@SRS-93.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68585
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Settings
Scenario: Default swimlane order is set by sending payload without swimlane order property
Given the following swimlane order was set by the [DEFAULT-TEST-USER] user:
| swimlane_names            |
| Imaging                   |
| Encounters                |
| Pathology and Labs        |
| Uncategorized             |
| Biomarkers                |
| Treatment and Plan        |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the user navigated to the Settings page
And the user opened the Network panel in DevTools
When the user clicks on 'Edit' button at the following user setting:
| settings_title | setting_name   |
| Timeline       | Swimlane order |
And the user clicks on the "Reset to default" button on the Settings page
And the user clicks on the "Save" button on the Settings page
Then the body of the PUT request to the /settings endpoint does not contain the following parameter:
| param_in_request_body |
| "swimlaneOrder": []   |
