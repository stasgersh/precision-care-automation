Feature: [SysRS-68626] Timeline Time Range Options

Narrative: The system users shall be able to select to see last 30 days, last 90 days or all time data on the Timeline view.


Background:
Given the user is logged out from OncoCare application

@sanity
@edge
@SRS-145.01
@SPR-4303
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68626
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use
Scenario: Time range options when user has 28 days long timeline
Given the dates of the following patient's resources are prepared and uploaded to PDS: patients/prepared/OncoTestPatient_TimeRange28Days.json
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the browser size is set to: width=1920, height=1080
And the "OncoTestPatient, TimeRange28Days" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following events are available on the "Pathology and Labs" swimlane:
| event_type | name_on_label      |
| MARKER     | Report 3 days ago  |
| MARKER     | Report 11 days ago |
| MARKER     | Report 28 days ago |
When the user clicks on the 'All time' button on the timeline toolbar
Then the below events on "Pathology and Labs" swimlane are visible in the viewport:
| event_type | name_on_label      |
| MARKER     | Report 3 days ago  |
| MARKER     | Report 11 days ago |
| MARKER     | Report 28 days ago |
When the user clicks on the 'Last 30 days' button on the timeline toolbar
Then the below events on "Pathology and Labs" swimlane are visible in the viewport:
| event_type | name_on_label      |
| MARKER     | Report 3 days ago  |
| MARKER     | Report 11 days ago |
| MARKER     | Report 28 days ago |
When the user clicks on the 'Last 90 days' button on the timeline toolbar
Then the below events on "Pathology and Labs" swimlane are visible in the viewport:
| event_type | name_on_label      |
| MARKER     | Report 3 days ago  |
| MARKER     | Report 11 days ago |
| MARKER     | Report 28 days ago |

@SRS-145.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68626
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Timeline_Time_Range_Options
Scenario: Time range options when user has 85 days long timeline
Given the dates of the following patient's resources are prepared and uploaded to PDS: patients/prepared/OncoTestPatient_TimeRange85Days.json
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the browser size is set to: width=1920, height=1080
And the "OncoTestPatient, TimeRange85Days" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following events are available on the "Pathology and Labs" swimlane:
| event_type | name_on_label      |
| MARKER     | Report 30 days ago |
| MARKER     | Report 32 days ago |
| MARKER     | Report 85 days ago |
When the user clicks on the 'All time' button on the timeline toolbar
Then the below events on "Pathology and Labs" swimlane are visible in the viewport:
| event_type | name_on_label      |
| MARKER     | Report 30 days ago |
| MARKER     | Report 32 days ago |
| MARKER     | Report 85 days ago |
When the user clicks on the 'Last 30 days' button on the timeline toolbar
Then the below events on "Pathology and Labs" swimlane are visible in the viewport:
| event_type | name_on_label      |
| MARKER     | Report 30 days ago |
And the below events on "Pathology and Labs" swimlane are not visible in the viewport:
| event_type | name_on_label      |
| MARKER     | Report 32 days ago |
| MARKER     | Report 85 days ago |
When the user clicks on the 'Last 90 days' button on the timeline toolbar
Then the below events on "Pathology and Labs" swimlane are visible in the viewport:
| event_type | name_on_label      |
| MARKER     | Report 30 days ago |
| MARKER     | Report 32 days ago |
| MARKER     | Report 85 days ago |

@SRS-145.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68626
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Timeline_Time_Range_Options
Scenario: Time range options when user has 265 days long timeline
Given the dates of the following patient's resources are prepared and uploaded to PDS: patients/prepared/OncoTestPatient_TimeRange265Days.json
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the browser size is set to: width=1920, height=1080
And the "OncoTestPatient, TimeRange265Days" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the timeline is zoomed in to max resolution
And the following events are available on the "Pathology and Labs" swimlane:
| event_type | name_on_label       |
| MARKER     | Report 30 days ago  |
| MARKER     | Report 32 days ago  |
| MARKER     | Report 90 days ago  |
| MARKER     | Report 92 days ago  |
| MARKER     | Report 265 days ago |
When the user clicks on the 'All time' button on the timeline toolbar
Then the below events on "Pathology and Labs" swimlane are visible in the viewport:
| event_type | text_on_event_point | name_on_label       |
| CLUSTER    | 2                   | 2 events            |
| CLUSTER    | 2                   | 2 events            |
| MARKER     | <EMPTY>             | Report 265 days ago |
When the user clicks on the 'Last 30 days' button on the timeline toolbar
Then the below events on "Pathology and Labs" swimlane are visible in the viewport:
| event_type | name_on_label      |
| MARKER     | Report 30 days ago |
And the below events on "Pathology and Labs" swimlane are not visible in the viewport:
| event_type | name_on_label       |
| MARKER     | Report 32 days ago  |
| MARKER     | Report 90 days ago  |
| MARKER     | Report 92 days ago  |
| MARKER     | Report 265 days ago |
When the user clicks on the 'Last 90 days' button on the timeline toolbar
Then the below events on "Pathology and Labs" swimlane are visible in the viewport:
| event_type | name_on_label      |
| MARKER     | Report 30 days ago |
| MARKER     | Report 32 days ago  |
| MARKER     | Report 90 days ago  |
And the below events on "Pathology and Labs" swimlane are not visible in the viewport:
| event_type | name_on_label       |
| MARKER     | Report 92 days ago  |
| MARKER     | Report 265 days ago |

@SRS-145.04
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68626
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Timeline_Time_Range_Options
Scenario: Time range options when user has 10 years long timeline
Given the dates of the following patient's resources are prepared and uploaded to PDS: patients/prepared/OncoTestPatient_TimeRange10Years.json
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the browser size is set to: width=1920, height=1080
And the "OncoTestPatient, TimeRange10Years" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the timeline is scrolled vertically to the "Biomarkers" swimlane
When the user clicks on the 'Last 30 days' button on the timeline toolbar
Then the below events on "Pathology and Labs" swimlane are visible in the viewport:
| event_type | name_on_label      |
| MARKER     | Report 30 days ago |
And the below events on "Pathology and Labs" swimlane are not visible in the viewport:
| event_type | name_on_label       |
| MARKER     | Report 32 days ago  |
| MARKER     | Report 90 days ago  |
| MARKER     | Report 92 days ago  |
| MARKER     | Report 265 days ago |
| MARKER     | Report 10 years ago |
When the user clicks on the 'Last 90 days' button on the timeline toolbar
Then the below events on "Pathology and Labs" swimlane are visible in the viewport:
| event_type | name_on_label      |
| MARKER     | Report 30 days ago |
| MARKER     | Report 32 days ago  |
| MARKER     | Report 90 days ago  |
And the below events on "Pathology and Labs" swimlane are not visible in the viewport:
| event_type | name_on_label       |
| MARKER     | Report 92 days ago  |
| MARKER     | Report 265 days ago |
| MARKER     | Report 10 years ago |

@SRS-145.05
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68626
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Timeline_Time_Range_Options
Scenario: Time range options when user has 85 days long timeline - Vertical toolbar
Given the dates of the following patient's resources are prepared and uploaded to PDS: patients/prepared/OncoTestPatient_TimeRange85Days.json
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the browser size is set to: width=1920, height=900
And the "OncoTestPatient, TimeRange85Days" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following events are available on the "Pathology and Labs" swimlane:
| event_type | name_on_label      |
| MARKER     | Report 30 days ago |
| MARKER     | Report 32 days ago |
| MARKER     | Report 85 days ago |
When the user clicks on the 'All time' button on the vertical timeline toolbar
Then the below events on "Pathology and Labs" swimlane are visible in the viewport:
| event_type | name_on_label      |
| MARKER     | Report 30 days ago |
| MARKER     | Report 32 days ago |
| MARKER     | Report 85 days ago |
When the user clicks on the 'Last 30 days' button on the vertical timeline toolbar
Then the below events on "Pathology and Labs" swimlane are visible in the viewport:
| event_type | name_on_label      |
| MARKER     | Report 30 days ago |
And the below events on "Pathology and Labs" swimlane are not visible in the viewport:
| event_type | name_on_label      |
| MARKER     | Report 32 days ago |
| MARKER     | Report 85 days ago |
When the user clicks on the 'Last 90 days' button on the vertical timeline toolbar
Then the below events on "Pathology and Labs" swimlane are visible in the viewport:
| event_type | name_on_label      |
| MARKER     | Report 30 days ago |
| MARKER     | Report 32 days ago |
| MARKER     | Report 85 days ago |

@SRS-145.06
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68626
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Timeline_Time_Range_Options
Scenario: Time range options when user has 265 days long timeline - Vertical toolbar
Given the dates of the following patient's resources are prepared and uploaded to PDS: patients/prepared/OncoTestPatient_TimeRange265Days.json
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the browser size is set to: width=1920, height=900
And the "OncoTestPatient, TimeRange265Days" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the 'Zoom in' button on the vertical toolbar is clicked by the user 4 times
And the following events are available on the "Pathology and Labs" swimlane:
| event_type | name_on_label       |
| MARKER     | Report 30 days ago  |
| MARKER     | Report 32 days ago  |
| MARKER     | Report 90 days ago  |
| MARKER     | Report 92 days ago  |
| MARKER     | Report 265 days ago |
When the user clicks on the 'All time' button on the vertical timeline toolbar
Then the below events on "Pathology and Labs" swimlane are visible in the viewport:
| event_type | text_on_event_point | name_on_label       |
| CLUSTER    | 2                   | 2 events            |
| CLUSTER    | 2                   | 2 events            |
| MARKER     | <EMPTY>             | Report 265 days ago |
When the user clicks on the 'Last 30 days' button on the vertical timeline toolbar
Then the below events on "Pathology and Labs" swimlane are visible in the viewport:
| event_type | name_on_label      |
| MARKER     | Report 30 days ago |
And the below events on "Pathology and Labs" swimlane are not visible in the viewport:
| event_type | name_on_label       |
| MARKER     | Report 32 days ago  |
| MARKER     | Report 90 days ago  |
| MARKER     | Report 92 days ago  |
| MARKER     | Report 265 days ago |
When the user clicks on the 'Last 90 days' button on the vertical timeline toolbar
Then the below events on "Pathology and Labs" swimlane are visible in the viewport:
| event_type | name_on_label      |
| MARKER     | Report 30 days ago |
| MARKER     | Report 32 days ago  |
| MARKER     | Report 90 days ago  |
And the below events on "Pathology and Labs" swimlane are not visible in the viewport:
| event_type | name_on_label       |
| MARKER     | Report 92 days ago  |
| MARKER     | Report 265 days ago |

@SRS-145.07
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68626
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Timeline_Time_Range_Options
Scenario: Time range options when user has 10 years long timeline - Vertical toolbar
Given the dates of the following patient's resources are prepared and uploaded to PDS: patients/prepared/OncoTestPatient_TimeRange10Years.json
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the browser size is set to: width=1920, height=900
And the "OncoTestPatient, TimeRange10Years" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the timeline is scrolled vertically to the "Biomarkers" swimlane
When the user clicks on the 'Last 30 days' button on the vertical timeline toolbar
Then the below events on "Pathology and Labs" swimlane are visible in the viewport:
| event_type | name_on_label      |
| MARKER     | Report 30 days ago |
And the below events on "Pathology and Labs" swimlane are not visible in the viewport:
| event_type | name_on_label       |
| MARKER     | Report 32 days ago  |
| MARKER     | Report 90 days ago  |
| MARKER     | Report 92 days ago  |
| MARKER     | Report 265 days ago |
| MARKER     | Report 10 years ago |
When the user clicks on the 'Last 90 days' button on the vertical timeline toolbar
Then the below events on "Pathology and Labs" swimlane are visible in the viewport:
| event_type | name_on_label      |
| MARKER     | Report 30 days ago |
| MARKER     | Report 32 days ago  |
| MARKER     | Report 90 days ago  |
And the below events on "Pathology and Labs" swimlane are not visible in the viewport:
| event_type | name_on_label       |
| MARKER     | Report 92 days ago  |
| MARKER     | Report 265 days ago |
| MARKER     | Report 10 years ago |

@SRS-145.08
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68626
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Timeline_Time_Range_Options
Scenario: Time-box on macro-navigator is displayed according to the time range set on the timeline toolbar
Given the 'OncoTestPatient_TimeRange265Days.json' file is copied 'from src\test\resources\testdata\patients\prepared\' directory to '\tools\TestDataGeneration\basePatientData\' directory
And the "OncoTestPatient, TimeRange265Days" patient test data is generated by '\tools\TestDataGeneration\prepareTestData.sh' tool
And the generated test data ('\tools\TestDataGeneration\generated\OncoTestPatient_TimeRange265Days.json') is uploaded to Edison PDS
And I logged in to OncoCare application
And I selected the "OncoTestPatient, TimeRange265Days" patient
And I navigated to the "Timeline" view
And I zoomed in the timeline to max resolution
And the following events are available on the "Pathology and Labs" swimlane:
| event_type | name_on_label       |
| MARKER     | Report 30 days ago  |
| MARKER     | Report 32 days ago  |
| MARKER     | Report 90 days ago  |
| MARKER     | Report 92 days ago  |
| MARKER     | Report 265 days ago |
When I click on the 'Last 30 days' button on the timeline toolbar
Then the time-box on the macro-navigator contains the points related to the below events:
| event_type | name_on_label       |
| MARKER     | Report 30 days ago  |
And the time-box on the macro-navigator does NOT contain the points related to the below events:
| event_type | name_on_label       |
| MARKER     | Report 32 days ago  |
| MARKER     | Report 90 days ago  |
| MARKER     | Report 92 days ago  |
| MARKER     | Report 265 days ago |
When I click on the 'Last 90 days' button on the timeline toolbar
Then the time-box on the macro-navigator contains the points related to the below events:
| event_type | name_on_label       |
| MARKER     | Report 30 days ago  |
| MARKER     | Report 32 days ago  |
| MARKER     | Report 90 days ago  |
And the time-box on the macro-navigator does NOT contain the points related to the below events:
| event_type | name_on_label       |
| MARKER     | Report 92 days ago  |
| MARKER     | Report 265 days ago |
When I click on the 'All time' button on the timeline toolbar
Then the time-box on the macro-navigator covers the whole patient timeline
