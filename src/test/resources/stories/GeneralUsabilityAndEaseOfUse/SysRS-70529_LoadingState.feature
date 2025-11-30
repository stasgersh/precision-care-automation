Feature: [SysRS-70529] Loading state

Narrative: The system shall support Loading state to inform the user data relevant for one or more sections in the selected view is being processed (data processing activity is ongoing and not completed yet).


@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70529
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/General_Usability_And_Ease_Of_Use
Scenario: Loading state is displayed for the user if one or more sections of Timeline view are still loading
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juliane376" patient is selected
When the download bitrate is set to 1 kbit/s
And the user clicks on the "Timeline" view without waiting for it to load
Then the timeline loading skeleton is visible
When the download bitrate is set to default
Then the patient timeline is loaded
And the timeline loading skeleton is not visible

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70529
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use
Scenario: Loading state is displayed for the user if one or more sections of Summary view are still loading
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the Patient header section is displayed
And the user opened the patient select menu
When the user types into the patient search field: OncoTestPatient, Juliane376
And the download bitrate is set to 1 kbit/s
And the user selects the "OncoTestPatient, Juliane376" patient from the already opened patient list
Then the summary loading skeleton is visible
And the progressive summarization loading skeleton is visible
And the patient banner loading skeleton is visible
When the download bitrate is set to default
Then the patient summary view is loaded
And the summary loading skeleton is not visible
And the progressive summarization loading skeleton is not visible
And the patient banner loading skeleton is not visible
