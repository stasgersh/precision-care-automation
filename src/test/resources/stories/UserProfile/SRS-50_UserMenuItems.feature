Feature: [SysRS-69611] Navigation bar**

Narrative: SRS-50 The system shall display Navigation bar to the user with the following functionalities:
           
            - GE HealthCare logo.
            - Application About information see Labeling (Functional)* (SysRS-68658)
            - Help User Documentation and Online Help* (SysRS-68659)
            - Application Settings.
            - User Name.
            - Logout.

@sanity
@SRS-50.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69611
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/User_Profile
Scenario: User menu contains the following items: About, Help, Settings, Profile and Sign out
When the [DEFAULT-TEST-USER] user logs in to OncoCare
Then the Patient page is loaded
And the following items are available in the user menu:
| menu_items    |
| About         |
| Help          |
| Settings      |
| User Onchron  |
| Sign out      |
