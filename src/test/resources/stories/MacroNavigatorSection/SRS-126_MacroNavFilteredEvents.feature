Feature: [SysRS-68527] Macro navigator filtered events

Narrative: The system Macro navigator shall reflect to the filtered events.


Background:
Given [API] the following events were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            | event_name                                                                      |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Encounter/65b5d7bf-07b5-4b03-a5fa-765ff2b1d825        | Emergency room admission (procedure)                                            |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/3097da7f-2a1c-4950-924e-0edeb0ca9f21        | Percutaneous mechanical thrombectomy of portal vein using fluoroscopic guidance |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/d0bbc4c1-0768-4171-a8bb-fd94ac18336e        | Bone density scan (procedure)                                                   |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/790872e3-988a-4491-a9dc-4dd3d59626f9 | XR ABDOMEN                                                                      |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/c86c138d-06f2-464c-b681-61b6ce580306        | Magnetic resonance imaging of breast (procedure)                                |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/e6266783-f448-4881-84c4-4b143599d533 | Examination of joint under image intensifier (procedure)                        |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/dc317297-f381-4234-b543-6d62150bb097        | Ultrasonography of bilateral breasts (procedure)                                |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/e48dd5f6-cc10-43b8-8a9e-f93c5a085d91        | Positron emission tomography with computed tomography (procedure)               |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/5b205577-29e8-4c65-8384-8c6f7c75aac1        | Mammography (procedure)                                                         |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/effb5f0c-038b-453b-8a5b-555adff35547 | Test Diagnostic Report                                                          |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/bf563ff3-d493-466c-99f0-0571b22417b7 | Cytology, pap smear (cervical)                                                  |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/d0cef2d4-6b62-498e-8bb9-35a8bf1df2cb | Cholesterol, total                                                              |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/52899193-5ff5-4323-82d5-a71772d5a33e | Tissue Pathology biopsy report                                                  |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/9e816b7f-037f-4d22-adbc-bae8af3cc3ad | Tissue Pathology biopsy report 2                                                |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                            | event_name                                                                      |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Encounter/65b5d7bf-07b5-4b03-a5fa-765ff2b1d825        | Emergency room admission (procedure)                                            |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/3097da7f-2a1c-4950-924e-0edeb0ca9f21        | Percutaneous mechanical thrombectomy of portal vein using fluoroscopic guidance |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/d0bbc4c1-0768-4171-a8bb-fd94ac18336e        | Bone density scan (procedure)                                                   |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/790872e3-988a-4491-a9dc-4dd3d59626f9 | XR ABDOMEN                                                                      |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/c86c138d-06f2-464c-b681-61b6ce580306        | Magnetic resonance imaging of breast (procedure)                                |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/e6266783-f448-4881-84c4-4b143599d533 | Examination of joint under image intensifier (procedure)                        |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/dc317297-f381-4234-b543-6d62150bb097        | Ultrasonography of bilateral breasts (procedure)                                |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/e48dd5f6-cc10-43b8-8a9e-f93c5a085d91        | Positron emission tomography with computed tomography (procedure)               |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/5b205577-29e8-4c65-8384-8c6f7c75aac1        | Mammography (procedure)                                                         |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/effb5f0c-038b-453b-8a5b-555adff35547 | Test Diagnostic Report                                                          |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/bf563ff3-d493-466c-99f0-0571b22417b7 | Cytology, pap smear (cervical)                                                  |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/d0cef2d4-6b62-498e-8bb9-35a8bf1df2cb | Cholesterol, total                                                              |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/52899193-5ff5-4323-82d5-a71772d5a33e | Tissue Pathology biopsy report                                                  |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/9e816b7f-037f-4d22-adbc-bae8af3cc3ad | Tissue Pathology biopsy report 2                                                |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                            | event_name                                                                      |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Encounter/65b5d7bf-07b5-4b03-a5fa-765ff2b1d825        | Emergency room admission (procedure)                                            |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/3097da7f-2a1c-4950-924e-0edeb0ca9f21        | Percutaneous mechanical thrombectomy of portal vein using fluoroscopic guidance |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/d0bbc4c1-0768-4171-a8bb-fd94ac18336e        | Bone density scan (procedure)                                                   |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/790872e3-988a-4491-a9dc-4dd3d59626f9 | XR ABDOMEN                                                                      |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/c86c138d-06f2-464c-b681-61b6ce580306        | Magnetic resonance imaging of breast (procedure)                                |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/e6266783-f448-4881-84c4-4b143599d533 | Examination of joint under image intensifier (procedure)                        |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/dc317297-f381-4234-b543-6d62150bb097        | Ultrasonography of bilateral breasts (procedure)                                |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/e48dd5f6-cc10-43b8-8a9e-f93c5a085d91        | Positron emission tomography with computed tomography (procedure)               |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/5b205577-29e8-4c65-8384-8c6f7c75aac1        | Mammography (procedure)                                                         |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/effb5f0c-038b-453b-8a5b-555adff35547 | Test Diagnostic Report                                                          |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/bf563ff3-d493-466c-99f0-0571b22417b7 | Cytology, pap smear (cervical)                                                  |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/d0cef2d4-6b62-498e-8bb9-35a8bf1df2cb | Cholesterol, total                                                              |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/52899193-5ff5-4323-82d5-a71772d5a33e | Tissue Pathology biopsy report                                                  |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/9e816b7f-037f-4d22-adbc-bae8af3cc3ad | Tissue Pathology biopsy report 2                                                |


@sanity
@edge
@SRS-126.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68527
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Macro_Navigator_Section
Scenario: Event filters are applied on macro-navigator
Given [API] the following labels are created:
| label_name     |
| test-macro-nav |
And [API] labels are added to the following events by the [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            | labels            | event_name                                                                      |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/effb5f0c-038b-453b-8a5b-555adff35547 | Date of diagnosis | Test Diagnostic Report                                                          |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/3097da7f-2a1c-4950-924e-0edeb0ca9f21        | Adverse Event     | Percutaneous mechanical thrombectomy of portal vein using fluoroscopic guidance |
And [API] the following events were marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                            | event_name |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/790872e3-988a-4491-a9dc-4dd3d59626f9 | XR ABDOMEN |
And [API] the below event comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | root_resource_of_the_event                            | comment_text | event_name         |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/d0cef2d4-6b62-498e-8bb9-35a8bf1df2cb | test comment | Cholesterol, total |
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the user navigated to the "Timeline" view
And the patient timeline is displayed
And the macro-navigator is displayed
And the "Magnetic resonance imaging of breast (procedure)" event MARKER is available on the "Imaging" swimlane with the below badges:
| badge_type     | badge_text |
| CLASSIFICATION | MR         |
And the "Ultrasonography of bilateral breasts (procedure)" event MARKER is available on the "Imaging" swimlane with the below badges:
| badge_type     | badge_text |
| CLASSIFICATION | Ultrasound |
And the "Encounters" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | false       | false    |
| 1     | false       | false    |
| 2     | false       | false    |
| 3     | false       | false    |
And the "Imaging" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | false       | false    |
| 1     | false       | false    |
| 2     | false       | false    |
| 3     | false       | false    |
| 4     | false       | false    |
| 5     | false       | false    |
| 6     | false       | false    |
| 7     | false       | false    |
And the "Pathology and Labs" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | false       | false    |
| 1     | false       | false    |
| 2     | false       | false    |
| 3     | false       | false    |
| 4     | false       | false    |
When the user set the following timeline filter options:
| filter_group | checkbox_name  | selected |
| Labels       | test-macro-nav | true     |
Then the "Encounters" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | true        | false    |
| 1     | true        | false    |
| 2     | true        | false    |
| 3     | true        | false    |
And the "Imaging" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | true        | false    |
| 1     | true        | false    |
| 2     | true        | false    |
| 3     | true        | false    |
| 4     | true        | false    |
| 5     | true        | false    |
| 6     | true        | false    |
| 7     | true        | false    |
And the "Pathology and Labs" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | true        | false    |
| 1     | true        | false    |
| 2     | true        | false    |
| 3     | true        | false    |
| 4     | true        | false    |
When the user set the following timeline filter options:
| filter_group | checkbox_name | selected |
| Labels       | Important     | true     |
Then the "Imaging" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | true        | false    |
| 1     | true        | false    |
| 2     | false       | false    |
| 3     | true        | false    |
| 4     | true        | false    |
| 5     | true        | false    |
| 6     | true        | false    |
| 7     | true        | false    |
When the user set the following timeline filter options:
| filter_group | checkbox_name  | selected |
| Labels       | To Be Reviewed | true     |
| Labels       | Adverse Event  | true     |
Then the "Encounters" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | true        | false    |
| 1     | true        | false    |
| 2     | true        | false    |
| 3     | true        | false    |
When the user set the following timeline filter options:
| filter_group       | checkbox_name | selected |
| Imaging modalities | MR            | true     |
Then the "Imaging" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | false       | false    |
| 1     | true        | false    |
| 2     | false       | false    |
| 3     | false       | false    |
| 4     | true        | false    |
| 5     | true        | false    |
| 6     | true        | false    |
| 7     | true        | false    |
When the user set the following timeline filter options:
| filter_group | checkbox_name | selected |
| Labels       | Has comments  | true     |
Then the "Pathology and Labs" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | true        | false    |
| 1     | true        | false    |
| 2     | false       | false    |
| 3     | true        | false    |
| 4     | true        | false    |
When the user opens the timeline filter modal
And the user clicks on the "Reset filters" button on the filter modal
And the user set the following timeline filter options:
| filter_group       | checkbox_name     | selected |
| Labels             | Important         | true     |
| Labels             | Has comments      | true     |
| Labels             | Date of diagnosis | true     |
| Labels             | To Be Reviewed    | true     |
| Imaging modalities | Ultrasound        | true     |
Then the "Encounters" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | true        | false    |
| 1     | true        | false    |
| 2     | true        | false    |
| 3     | true        | false    |
And the "Imaging" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | true        | false    |
| 1     | true        | false    |
| 2     | false       | false    |
| 3     | true        | false    |
| 4     | true        | false    |
| 5     | false       | false    |
| 6     | true        | false    |
| 7     | true        | false    |
And the "Pathology and Labs" swimlane on macro-navigator contains the below event points:
| index | is_filtered | selected |
| 0     | false       | false    |
| 1     | true        | false    |
| 2     | false       | false    |
| 3     | true        | false    |
| 4     | true        | false    |
