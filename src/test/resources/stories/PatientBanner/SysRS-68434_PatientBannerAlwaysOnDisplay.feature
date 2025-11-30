@safety
Feature: [SysRS-68434] Patient Banner Always On Display

Narrative: The system shall display patient banner at the following screens (always on display):
             - Summary
             - Timeline
             - Medical History
             - Trends
             - Comments
             - Trials
             - Response


@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68434
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Patient_Banner
Scenario: Patient banner is displayed at the following screens: Summary, Timeline, Medical History, Trends, Comments, Trials, Response
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Freya" patient is selected
When the user navigates to the Settings page
Then the Patient Banner is not displayed
When the user clicks on the "Back" button in the Settings toolbar header
Then the patient summary view is loaded
And the Patient Banner is displayed
When the user navigates to the "Timeline" view
Then the Patient Banner is displayed
When the user navigates to the "Medical history" view
Then the Patient Banner is displayed
When the user navigates to the "Trends" view
Then the Patient Banner is displayed
When the user navigates to the "Comments" view
Then the Patient Banner is displayed
When the user navigates to the "Summary" view
And the user clicks "Status and response" button
Then the Treatment and response view is loaded
And the Patient Banner is displayed
When the user selects the "huge-test-patient-9330-id" patient
And the user clicks "Clinical trials" button
Then the Patient Banner is displayed