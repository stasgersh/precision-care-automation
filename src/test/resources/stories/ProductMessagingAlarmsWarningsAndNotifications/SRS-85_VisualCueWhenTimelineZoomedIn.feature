@safety
Feature: [SysRS-68666] Visual Cue When Timeline Zoomed In

Narrative: The system shall have visual cue when the timeline is zoomed in.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the browser size is set to: width=1920, height=1080
And the "OncoTestPatient, Pablo44" patient is selected

@srs
@SRS-85.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68666
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Zoom slider is not on the left side by default if the whole patient timeline is longer than 6 months
When the user navigates to the "Timeline" view
Then the patient timeline is loaded
And the zoom slider is not moved to the left side

@srs
@SRS-85.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68666
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Zoom slider is moved on the zoom slider axis when the user zooms in the timeline
Given the "Timeline" view is selected
And the patient timeline is loaded
When the user clicks on the 'All time' button on the timeline toolbar
Then the zoom slider is moved to the left side
When the user clicks on the 'Zoom in' button 8 times
Then the zoom slider is in the middle of the zoom slider axis

@srs
@SRS-85.03
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68666
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Zoom slider is on the right side when the timeline is fully zoomed in
Given the "Timeline" view is selected
And the patient timeline is loaded
When the user zooms in the timeline to max resolution
Then the zoom slider is moved to the right side

@srs
@SRS-85.04
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68666
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: Zoom slider is moved to the left side when the user sets 'All time' time range on the timeline toolbar
Given the "Timeline" view is selected
And the patient timeline is loaded
And the zoom slider is not on the left side
When the user clicks on the 'All time' button on the timeline toolbar
Then the zoom slider is moved to the left side
