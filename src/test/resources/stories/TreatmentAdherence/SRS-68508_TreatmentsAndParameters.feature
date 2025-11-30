Feature: [SysRS-68508] Treatments And Parameters

Narrative: The system shall provide the user the ability to select from a list the following parameters in Treatment adherence mode:
           - Protocol/Treatment Name
           - Protocol/Treatment starts date.
           - Trial arm - When relevant - If the selected protocol include trials.
           - note: Above parameters are mandatory.


Background:
Given [API] the following treatment protocol was uploaded: treatment/simple_treatment_multiple_protocols.json
And [API] the following treatment protocol was uploaded: treatment/cc-94676_adherence_config.json


@sanity
@edge
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68508,SysRS-70309
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Treatment_Adherence
Scenario: User can select from a list of protocols and trial arms on the Adherence view
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_TA, Higgins" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
When the user clicks on "View mode" button on the Timeline toolbar
Then the "View mode" modal is displayed
When the user selects "Treatment adherence" checkbox on the View mode modal
And the user opens the "Protocol" dropdown on "View mode" modal
Then the following protocols are present in the open dropdown on "View mode" modal:
| options                                |
| Test treatment with multiple protocols |
| CC-94676 for Prostate Cancer Patient   |
When the user closes the open dropdown on "View mode" modal
And the user sets the following options on "View mode" modal:
| protocol                               |
| Test treatment with multiple protocols |
And the user opens the "Trial arm" dropdown on "View mode" modal
Then the following trial arms are present in the open dropdown on "View mode" modal:
| options                                                                                                                                           |
| A Multicenter, Open-Label Study to Evaluate the Long-Term Safety and Efficacy of [Drug Name] in Patients with Moderate to Severe Plaque Psoriasis |
| Phase 2/3 Adaptive Trial of [Vaccine Name] for the Prevention of Influenza in Adults Aged 65 and Older                                            |
| A Phase 1/2 Study of [Gene Therapy] in Pediatric Patients with Spinal Muscular Atrophy                                                            |
When the user closes the open dropdown on "View mode" modal
And the user sets the following options on "View mode" modal:
| protocol                               | trial_arm                                                                                              | start_date   |
| Test treatment with multiple protocols | Phase 2/3 Adaptive Trial of [Vaccine Name] for the Prevention of Influenza in Adults Aged 65 and Older | Aug 01, 2024 |
And the user clicks "Done" on the "View mode" modal
Then the "View mode" modal is not displayed
And the patient timeline is loaded
And the timeline banner displays the following notification: "Treatment adherence mode | Protocol: Test treatment with multiple protocols | Trial arm: Phase 2/3 Adaptive Trial of [Vaccine Name] for the Prevention of Influenza in Adults Aged 65 and Older | Protocol start date: Aug 01, 2024"

@sanity
@edge
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68508
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Treatment_Adherence
Scenario: User can not set Adherence mode if mandatory fields are not filled
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_TA, Higgins" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
When the user clicks on "View mode" button on the Timeline toolbar
Then the "View mode" modal is displayed
When the user selects "Treatment adherence" checkbox on the View mode modal
And the user clicks "Done" on the "View mode" modal
Then the following error message is displayed under "Protocol" field on the View mode modal: "Please select Protocol"
And the following error message is displayed under "Protocol start date" field on the View mode modal: "Please select Protocol start date"
When the user sets the following options on "View mode" modal:
| protocol                             |
| CC-94676 for Prostate Cancer Patient |
And the user clicks "Done" on the "View mode" modal
Then the following error message is displayed under "Trial arm" field on the View mode modal: "Please select Trial arm"
And the following error message is displayed under "Protocol start date" field on the View mode modal: "Please select Protocol start date"
When the user sets the following options on "View mode" modal:
| start_date   |
| Jun 07, 2024 |
And the user clicks "Done" on the "View mode" modal
Then the following error message is displayed under "Trial arm" field on the View mode modal: "Please select Trial arm"

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68508
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Treatment_Adherence
Scenario: View mode is saved on patient level
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_TA, Higgins" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the timeline toolbar section is displayed
When the user sets the following treatment protocol configuration for the patient:
| view_mode           | protocol                             | trial_arm                           | start_date   |
| Treatment adherence | CC-94676 for Prostate Cancer Patient | CC94676 for Prostate Cancer Patient | Jun 07, 2024 |
Then the patient timeline is loaded
And the "Tx" badge is displayed on the "View mode" button
When the user opens the timeline filter modal
Then the following notification is displayed on the Timeline filter modal: "Filters disabled\nYou can’t edit filters in Treatment adherence mode.\nExit Treatment adherence mode"
When the user closes the timeline filter modal
And the user selects the "OncoTestPatient, Sigrid" patient
And the user navigates to the "Timeline" view
Then the patient timeline is loaded
And the "Tx" badge is not displayed on the "View mode" button
When the user sets the following treatment protocol configuration for the patient:
| view_mode           | protocol                               | trial_arm                                                                                                                                         | start_date   |
| Treatment adherence | Test treatment with multiple protocols | A Multicenter, Open-Label Study to Evaluate the Long-Term Safety and Efficacy of [Drug Name] in Patients with Moderate to Severe Plaque Psoriasis | Jun 26, 2024 |
Then the patient timeline is loaded
And the "Tx" badge is displayed on the "View mode" button
When the user selects the "TEST_TA, Higgins" patient (without reseting patient settings)
And the user navigates to the "Timeline" view
Then the patient timeline is loaded
When the user clicks on "View mode" button on the Timeline toolbar
Then the "View mode" modal is displayed
And the following treatment configuration is automatically filled on the View mode modal:
| view_mode           | protocol                             | trial_arm                           | start_date   |
| Treatment adherence | CC-94676 for Prostate Cancer Patient | CC94676 for Prostate Cancer Patient | Jun 07, 2024 |
When the user selects "Default" checkbox on the View mode modal
And the user clicks "Done" on the "View mode" modal
Then the "View mode" modal is not displayed
And the patient timeline is loaded
When the user selects the "OncoTestPatient, Sigrid" patient (without reseting patient settings)
And the user navigates to the "Timeline" view
Then the patient timeline is loaded
When the user clicks on "View mode" button on the Timeline toolbar
Then the "View mode" modal is displayed
And the following treatment configuration is automatically filled on the View mode modal:
| view_mode           | protocol                               | trial_arm                                                                                                                                         | start_date   |
| Treatment adherence | Test treatment with multiple protocols | A Multicenter, Open-Label Study to Evaluate the Long-Term Safety and Efficacy of [Drug Name] in Patients with Moderate to Severe Plaque Psoriasis | Jun 26, 2024 |