Feature: [SysRS-68611] Support browsers

Narrative: The system shall provide web-based access to the users with the following browsers:
           - Google Chrome
           - Microsoft Edge
           - Hyperdrive when launched from Epic

@SRS-45.01
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68611
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Support_browsers
Scenario: OncoCare supports Google Chrome browser
Given all existing automated and manual tests were executed on Chrome browser
When I check the results of the test runs
Then no browser specific issues were found

@SRS-45.02
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68611
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Support_browsers
Scenario: OncoCare supports Microsoft Edge browser
Given all automated test cases tagged with @edge were executed on Edge browser
When I check the results of the test runs
Then the results contain no browser related issue

@SRS-68611.03
@manual
@integration
@hyperdrive
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68409,SysRS-68611
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Seamless_launch
Scenario: Navigate to Treatment and response view from summary card in Hyperdrive (separate window)
Given [API] the following treatment protocol was uploaded: treatment/simple_treatment_multiple_protocols.json
And I opened the Hyperdrive Web Developer Test Harness tool
And the "Open in a separate window" checkbox is set
And I typed the URL of tested environment into the "URL" field
And I clicked on the "Navigate" button
And the splash screen was opened in a separate window
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_TA, Higgins" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
When I click on "View mode" button on the Timeline toolbar
And I set the following options on "View mode" modal:
| protocol                               | trial_arm                                                                                              | start_date   |
| Test treatment with multiple protocols | Phase 2/3 Adaptive Trial of [Vaccine Name] for the Prevention of Influenza in Adults Aged 65 and Older | Aug 01, 2024 |
And I click "Done" on the "View mode" modal
Then the "View mode" modal is not displayed
And the patient timeline is loaded
And "Protocol start" label is displayed at the following event on the "Treatment and Plan" swimlane:
| event_type | name_on_label                    | date_on_timeline_axis  |
| MARKER     | Androgen Receptor Degrader Test2 | Aug 01, 2024           |
And "Missed" badge is available on the label of the following event:
| event_type | name_on_label                    | date_on_timeline_axis  |
| MARKER     | Androgen Receptor Degrader Test2 | Aug 01, 2024           |
When I click on the "Filter" button in the timeline toolbar
Then the Filter modal is displayed
And the filter options are grayed out
