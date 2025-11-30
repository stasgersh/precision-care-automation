@safety
Feature: [SysRS-68636] Event Classification Filter On Timeline

Narrative: The system users shall be able to filter on the Timeline for the following events:
             - Fluoroscopy
             - Genomic report
             - Bone scan
             - CT
             - Mammography
             - MR
             - PET
             - PET-CT
             - PET-MR
             - SPECT
             - Ultrasound
             - X-ray
             - Pathology
             - Labs
             - Radiation
             - Surgery
             - Systemic Tx
             - ER visit
             - Inpatient visit
             - Outpatient visit.


@sanity
@edge
@SRS-149.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68636,SysRS-68646,SysRS-68645,SysRS-68644,SysRS-68643
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use
Scenario: Event classification options are available on the timeline filter modal
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Empty" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
When the user opens the timeline filter modal
Then only the following timeline filter options are displayed on the filter modal in "Imaging modalities" group:
| checkbox_name    | selected | enabled |
| Bone scan        | false    | true    |
| CT               | false    | true    |
| Fluoroscopy      | false    | true    |
| Mammography      | false    | true    |
| MR               | false    | true    |
| PET              | false    | true    |
| PET-CT           | false    | true    |
| PET-MR           | false    | true    |
| SPECT            | false    | true    |
| Ultrasound       | false    | true    |
| X-ray            | false    | true    |
And only the following timeline filter options are displayed on the filter modal in "Pathology and Labs" group:
| checkbox_name    | selected | enabled |
| Labs             | false    | true    |
| Pathology        | false    | true    |
And only the following timeline filter options are displayed on the filter modal in "Encounter types" group:
| checkbox_name    | selected | enabled |
| ER visit         | false    | true    |
| Inpatient visit  | false    | true    |
| Outpatient visit | false    | true    |
And only the following timeline filter options are displayed on the filter modal in "Biomarkers" group:
| checkbox_name    | selected | enabled |
| Genomic report   | false    | true    |
And only the following timeline filter options are displayed on the filter modal in "Treatment types" group:
| checkbox_name    | selected | enabled |
| Radiation        | false    | true    |
| Surgery          | false    | true    |
| Systemic Tx      | false    | true    |


@SRS-149.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68636,SysRS-68647
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Event_Classification_Filter_On_Timeline
Scenario: Events can be filtered on the timeline based on event classification (Fluoroscopy, Mammography, X-ray, Labs, ER visit, Genomic report, Bone scan, CT, MR, Ultrasound, Systemic Tx, Outpatient visit)
Given [API] the following events were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                               | event_name                                                                      |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Encounter/65b5d7bf-07b5-4b03-a5fa-765ff2b1d825           | Emergency room admission (procedure)                                            |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/3097da7f-2a1c-4950-924e-0edeb0ca9f21           | Percutaneous mechanical thrombectomy of portal vein using fluoroscopic guidance |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/d0bbc4c1-0768-4171-a8bb-fd94ac18336e           | Bone density scan (procedure)                                                   |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/790872e3-988a-4491-a9dc-4dd3d59626f9    | XR ABDOMEN                                                                      |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/c86c138d-06f2-464c-b681-61b6ce580306           | Magnetic resonance imaging of breast (procedure)                                |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/e6266783-f448-4881-84c4-4b143599d533    | Examination of joint under image intensifier (procedure)                        |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/dc317297-f381-4234-b543-6d62150bb097           | Ultrasonography of bilateral breasts (procedure)                                |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/e48dd5f6-cc10-43b8-8a9e-f93c5a085d91           | Positron emission tomography with computed tomography (procedure)               |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/5b205577-29e8-4c65-8384-8c6f7c75aac1           | Mammography (procedure)                                                         |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/effb5f0c-038b-453b-8a5b-555adff35547    | Test Diagnostic Report                                                          |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/bf563ff3-d493-466c-99f0-0571b22417b7    | Cytology, pap smear (cervical)                                                  |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/d0cef2d4-6b62-498e-8bb9-35a8bf1df2cb    | Cholesterol, total                                                              |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/e5a82084-0ccb-4325-bc67-52153b81bd06           | Hysteroscopy                                                                    |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/2a93feae-612f-4bfb-a4ba-d0f327286632    | Genetic analysis report                                                         |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | MedicationStatement/359ab4ca-4a30-46b8-8f68-8a1bfddc7056 | cyclophosphamide 1 GM Injection                                                 |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                               | event_name                                                                      |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Encounter/65b5d7bf-07b5-4b03-a5fa-765ff2b1d825           | Emergency room admission (procedure)                                            |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/3097da7f-2a1c-4950-924e-0edeb0ca9f21           | Percutaneous mechanical thrombectomy of portal vein using fluoroscopic guidance |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/d0bbc4c1-0768-4171-a8bb-fd94ac18336e           | Bone density scan (procedure)                                                   |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/790872e3-988a-4491-a9dc-4dd3d59626f9    | XR ABDOMEN                                                                      |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/c86c138d-06f2-464c-b681-61b6ce580306           | Magnetic resonance imaging of breast (procedure)                                |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/e6266783-f448-4881-84c4-4b143599d533    | Examination of joint under image intensifier (procedure)                        |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/dc317297-f381-4234-b543-6d62150bb097           | Ultrasonography of bilateral breasts (procedure)                                |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/e48dd5f6-cc10-43b8-8a9e-f93c5a085d91           | Positron emission tomography with computed tomography (procedure)               |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/5b205577-29e8-4c65-8384-8c6f7c75aac1           | Mammography (procedure)                                                         |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/effb5f0c-038b-453b-8a5b-555adff35547    | Test Diagnostic Report                                                          |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/bf563ff3-d493-466c-99f0-0571b22417b7    | Cytology, pap smear (cervical)                                                  |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/d0cef2d4-6b62-498e-8bb9-35a8bf1df2cb    | Cholesterol, total                                                              |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/e5a82084-0ccb-4325-bc67-52153b81bd06           | Hysteroscopy                                                                    |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/2a93feae-612f-4bfb-a4ba-d0f327286632    | Genetic analysis report                                                         |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | MedicationStatement/359ab4ca-4a30-46b8-8f68-8a1bfddc7056 | cyclophosphamide 1 GM Injection                                                 |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                               | event_name                                                                      |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Encounter/65b5d7bf-07b5-4b03-a5fa-765ff2b1d825           | Emergency room admission (procedure)                                            |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/3097da7f-2a1c-4950-924e-0edeb0ca9f21           | Percutaneous mechanical thrombectomy of portal vein using fluoroscopic guidance |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/d0bbc4c1-0768-4171-a8bb-fd94ac18336e           | Bone density scan (procedure)                                                   |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/790872e3-988a-4491-a9dc-4dd3d59626f9    | XR ABDOMEN                                                                      |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/c86c138d-06f2-464c-b681-61b6ce580306           | Magnetic resonance imaging of breast (procedure)                                |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/e6266783-f448-4881-84c4-4b143599d533    | Examination of joint under image intensifier (procedure)                        |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/dc317297-f381-4234-b543-6d62150bb097           | Ultrasonography of bilateral breasts (procedure)                                |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/e48dd5f6-cc10-43b8-8a9e-f93c5a085d91           | Positron emission tomography with computed tomography (procedure)               |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/5b205577-29e8-4c65-8384-8c6f7c75aac1           | Mammography (procedure)                                                         |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/effb5f0c-038b-453b-8a5b-555adff35547    | Test Diagnostic Report                                                          |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/bf563ff3-d493-466c-99f0-0571b22417b7    | Cytology, pap smear (cervical)                                                  |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/d0cef2d4-6b62-498e-8bb9-35a8bf1df2cb    | Cholesterol, total                                                              |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | Procedure/e5a82084-0ccb-4325-bc67-52153b81bd06           | Hysteroscopy                                                                    |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | DiagnosticReport/2a93feae-612f-4bfb-a4ba-d0f327286632    | Genetic analysis report                                                         |
| cee5f972-9531-4cd4-b1e4-599c9589a177 | MedicationStatement/359ab4ca-4a30-46b8-8f68-8a1bfddc7056 | cyclophosphamide 1 GM Injection                                                 |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event is available on the "Encounters" swimlane with the below badges:
| event_type | name_on_label                        | badges                     |
| MARKER     | Emergency room admission (procedure) | [CLASSIFICATION]: ER visit |
And the following events are available on the "Imaging" swimlane with the below badges:
| event_type | name_on_label                                                                   | badges                        |
| MARKER     | Percutaneous mechanical thrombectomy of portal vein using fluoroscopic guidance | [CLASSIFICATION]: Fluoroscopy |
| MARKER     | Bone density scan (procedure)                                                   | [CLASSIFICATION]: Bone scan   |
| MARKER     | XR ABDOMEN                                                                      | [CLASSIFICATION]: X-ray       |
| MARKER     | Magnetic resonance imaging of breast (procedure)                                | [CLASSIFICATION]: MR          |
| MARKER     | Ultrasonography of bilateral breasts (procedure)                                | [CLASSIFICATION]: Ultrasound  |
| MARKER     | Positron emission tomography with computed tomography (procedure)               | [CLASSIFICATION]: CT          |
| MARKER     | Mammography (procedure)                                                         | [CLASSIFICATION]: Mammography |
And the following events are available on the "Pathology and Labs" swimlane with the below badges:
| event_type | name_on_label                  | badges                      |
| MARKER     | Test Diagnostic Report         | [CLASSIFICATION]: Pathology |
| MARKER     | Cytology, pap smear (cervical) | [CLASSIFICATION]: Pathology |
| MARKER     | Cholesterol, total             | [CLASSIFICATION]: Labs      |
And the following events are available on the "Treatment and Plan" swimlane with the below badges:
| event_type | name_on_label                   | badges                        |
| MARKER     | cyclophosphamide 1 GM Injection | [CLASSIFICATION]: Systemic Tx |
| MARKER     | Hysteroscopy                    | [CLASSIFICATION]: Surgery     |
And the following event is available on the "Biomarkers" swimlane with the below badges:
| event_type | name_on_label           | badges                           |
| MARKER     | Genetic analysis report | [CLASSIFICATION]: Genomic report |
When the user set the following timeline filter options:
| filter_group       | checkbox_name    | selected |
| Encounter types    | ER visit         | true     |
| Imaging modalities | Fluoroscopy      | true     |
| Imaging modalities | X-ray            | true     |
| Pathology and Labs | Labs             | true     |
| Imaging modalities | Mammography      | false    |
| Encounter types    | Outpatient visit | false    |
| Biomarkers         | Genomic report   | false    |
| Imaging modalities | Bone scan        | false    |
| Imaging modalities | CT               | false    |
| Imaging modalities | MR               | false    |
| Imaging modalities | Ultrasound       | false    |
| Treatment types    | Systemic Tx      | false    |
Then the following event is available on the "Encounters" swimlane with the below badges:
| event_type | name_on_label                        | badges                     |
| MARKER     | Emergency room admission (procedure) | [CLASSIFICATION]: ER visit |
And the following events are available on the "Imaging" swimlane with the below badges:
| event_type | name_on_label                                                                   | badges                        |
| MARKER     | Percutaneous mechanical thrombectomy of portal vein using fluoroscopic guidance | [CLASSIFICATION]: Fluoroscopy |
| MARKER     | XR ABDOMEN                                                                      | [CLASSIFICATION]: X-ray       |
And the following events are not available on the "Imaging" swimlane with the below badges:
| event_type | name_on_label                                                     | badges                        |
| MARKER     | Mammography (procedure)                                           | [CLASSIFICATION]: Mammography |
| MARKER     | Bone density scan (procedure)                                     | [CLASSIFICATION]: Bone scan   |
| MARKER     | Magnetic resonance imaging of breast (procedure)                  | [CLASSIFICATION]: MR          |
| MARKER     | Ultrasonography of bilateral breasts (procedure)                  | [CLASSIFICATION]: Ultrasound  |
| MARKER     | Positron emission tomography with computed tomography (procedure) | [CLASSIFICATION]: CT          |
And the following event is available on the "Pathology and Labs" swimlane with the below badges:
| event_type | name_on_label      | badges                 |
| MARKER     | Cholesterol, total | [CLASSIFICATION]: Labs |
And the following events are not available on the "Pathology and Labs" swimlane with the below badges:
| event_type | name_on_label                  | badges                      |
| MARKER     | Test Diagnostic Report         | [CLASSIFICATION]: Pathology |
| MARKER     | Cytology, pap smear (cervical) | [CLASSIFICATION]: Pathology |
And the following events are not available on the "Treatment and Plan" swimlane with the below badges:
| event_type | name_on_label                   | badges                        |
| MARKER     | cyclophosphamide 1 GM Injection | [CLASSIFICATION]: Systemic Tx |
| MARKER     | Hysteroscopy                    | [CLASSIFICATION]: Surgery     |
And the following event is not available on the "Biomarkers" swimlane with the below badges:
| event_type | name_on_label           | badges                           |
| MARKER     | Genetic analysis report | [CLASSIFICATION]: Genomic report |
When the user set the following timeline filter options:
| filter_group       | checkbox_name    | selected |
| Encounter types    | ER visit         | false    |
| Imaging modalities | Fluoroscopy      | false    |
| Imaging modalities | X-ray            | false    |
| Pathology and Labs | Labs             | false    |
| Imaging modalities | Mammography      | true     |
| Encounter types    | Outpatient visit | true     |
| Biomarkers         | Genomic report   | true     |
| Imaging modalities | Bone scan        | true     |
| Imaging modalities | CT               | false    |
| Imaging modalities | MR               | false    |
| Imaging modalities | Ultrasound       | false    |
| Treatment types    | Systemic Tx      | false    |
Then the following event is not available on the "Encounters" swimlane with the below badges:
| event_type | name_on_label                        | badges                     |
| MARKER     | Emergency room admission (procedure) | [CLASSIFICATION]: ER visit |
And the following events are not available on the "Imaging" swimlane with the below badges:
| event_type | name_on_label                                                                   | badges                        |
| MARKER     | Percutaneous mechanical thrombectomy of portal vein using fluoroscopic guidance | [CLASSIFICATION]: Fluoroscopy |
| MARKER     | XR ABDOMEN                                                                      | [CLASSIFICATION]: X-ray       |
| MARKER     | Magnetic resonance imaging of breast (procedure)                                | [CLASSIFICATION]: MR         |
| MARKER     | Ultrasonography of bilateral breasts (procedure)                                | [CLASSIFICATION]: Ultrasound |
| MARKER     | Positron emission tomography with computed tomography (procedure)               | [CLASSIFICATION]: CT         |
And the following event is available on the "Imaging" swimlane with the below badges:
| event_type | name_on_label                 | badges                        |
| MARKER     | Mammography (procedure)       | [CLASSIFICATION]: Mammography |
And the following events are not available on the "Pathology and Labs" swimlane with the below badges:
| event_type | name_on_label                  | badges                      |
| MARKER     | Cholesterol, total             | [CLASSIFICATION]: Labs      |
| MARKER     | Test Diagnostic Report         | [CLASSIFICATION]: Pathology |
| MARKER     | Cytology, pap smear (cervical) | [CLASSIFICATION]: Pathology |
And the following events are not available on the "Treatment and Plan" swimlane with the below badges:
| event_type | name_on_label                   | badges                        |
| MARKER     | cyclophosphamide 1 GM Injection | [CLASSIFICATION]: Systemic Tx |
| MARKER     | Hysteroscopy                    | [CLASSIFICATION]: Surgery     |
And the following event is available on the "Biomarkers" swimlane with the below badges:
| event_type | name_on_label           | badges                           |
| MARKER     | Genetic analysis report | [CLASSIFICATION]: Genomic report |
When the user set the following timeline filter options:
| filter_group       | checkbox_name    | selected |
| Encounter types    | ER visit         | false    |
| Imaging modalities | Fluoroscopy      | false    |
| Imaging modalities | X-ray            | false    |
| Pathology and Labs | Labs             | false    |
| Imaging modalities | Mammography      | false    |
| Encounter types    | Outpatient visit | false    |
| Biomarkers         | Genomic report   | false    |
| Imaging modalities | Bone scan        | false    |
| Imaging modalities | CT               | true     |
| Imaging modalities | MR               | true     |
| Imaging modalities | Ultrasound       | true     |
| Treatment types    | Systemic Tx      | true     |
Then the following events are not available on the "Encounters" swimlane with the below badges:
| event_type | name_on_label                        | badges                             |
| MARKER     | Emergency room admission (procedure) | [CLASSIFICATION]: ER visit         |
And the following events are not available on the "Imaging" swimlane with the below badges:
| event_type | name_on_label                                                                   | badges                        |
| MARKER     | Percutaneous mechanical thrombectomy of portal vein using fluoroscopic guidance | [CLASSIFICATION]: Fluoroscopy |
| MARKER     | XR ABDOMEN                                                                      | [CLASSIFICATION]: X-ray       |
| MARKER     | Mammography (procedure)                                                         | [CLASSIFICATION]: Mammography |
| MARKER     | Bone density scan (procedure)                                                   | [CLASSIFICATION]: Bone scan   |
And the following events are available on the "Imaging" swimlane with the below badges:
| event_type | name_on_label                                                     | badges                       |
| MARKER     | Magnetic resonance imaging of breast (procedure)                  | [CLASSIFICATION]: MR         |
| MARKER     | Ultrasonography of bilateral breasts (procedure)                  | [CLASSIFICATION]: Ultrasound |
| MARKER     | Positron emission tomography with computed tomography (procedure) | [CLASSIFICATION]: CT         |
And the following events are not available on the "Pathology and Labs" swimlane with the below badges:
| event_type | name_on_label                  | badges                      |
| MARKER     | Cholesterol, total             | [CLASSIFICATION]: Labs      |
| MARKER     | Test Diagnostic Report         | [CLASSIFICATION]: Pathology |
| MARKER     | Cytology, pap smear (cervical) | [CLASSIFICATION]: Pathology |
And the following event is available on the "Treatment and Plan" swimlane with the below badges:
| event_type | name_on_label                   | badges                        |
| MARKER     | cyclophosphamide 1 GM Injection | [CLASSIFICATION]: Systemic Tx |
And the following event is not available on the "Treatment and Plan" swimlane with the below badges:
| event_type | name_on_label                   | badges                        |
| MARKER     | Hysteroscopy                    | [CLASSIFICATION]: Surgery     |
And the following event is not available on the "Biomarkers" swimlane with the below badges:
| event_type | name_on_label           | badges                           |
| MARKER     | Genetic analysis report | [CLASSIFICATION]: Genomic report |

@SRS-149.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68636
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Event_Classification_Filter_On_Timeline
Scenario: Events can be filtered on the timeline based on classification when events are in clusters (Pathology, Radiation, Surgery)
Given [API] the following events were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                             | event_name                      |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/f45b1db7-7194-45c9-830a-e9cbed025778 | Radiation oncology Summary note |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Procedure/fcd9f025-9f19-4aeb-a426-e3bda16547d5         | Suture open wound               |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/178b6d6a-39f7-4a06-b15f-d67702e84208  | Tissue Pathology biopsy report  |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                             | event_name                      |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/f45b1db7-7194-45c9-830a-e9cbed025778 | Radiation oncology Summary note |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Procedure/fcd9f025-9f19-4aeb-a426-e3bda16547d5         | Suture open wound               |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/178b6d6a-39f7-4a06-b15f-d67702e84208  | Tissue Pathology biopsy report  |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                             | event_name                      |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/f45b1db7-7194-45c9-830a-e9cbed025778 | Radiation oncology Summary note |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | Procedure/fcd9f025-9f19-4aeb-a426-e3bda16547d5         | Suture open wound               |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DiagnosticReport/178b6d6a-39f7-4a06-b15f-d67702e84208  | Tissue Pathology biopsy report  |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the "2 events" event CLUSTER is available on the "Treatment and Plan" swimlane at "Feb 04, 2020 - Mar 22, 2020" with the below badges:
| badge_type     | badge_text |
| CLASSIFICATION | Radiation  |
| CLASSIFICATION | Surgery    |
And the following event is not available on the "Imaging" swimlane:
| event_type | name_on_label                   |
| MARKER     | Radiation oncology Summary note |
And the following event is available on the "Pathology and Labs" swimlane:
| event_type | name_on_label                  |
| MARKER     | Tissue Pathology biopsy report |
And the "Tissue Pathology biopsy report" event MARKER is available on the "Pathology and Labs" swimlane with the below badges:
| badge_type     | badge_text |
| CLASSIFICATION | Pathology  |
When the user set the following timeline filter options:
| filter_group       | checkbox_name | selected |
| Pathology and Labs | Pathology     | true     |
| Treatment types    | Radiation     | true     |
| Treatment types    | Surgery       | false    |
Then the filter counter icon on the Filter button is displayed with number: 2
And the following filter badge is available in the filter list on the timeline toolbar:
| filter    |
| Pathology |
| Radiation |
And the following event is not available on the "Treatment and Plan" swimlane:
| event_type | name_on_label     |
| MARKER     | Suture open wound |
And the following event is available on the "Treatment and Plan" swimlane:
| event_type | name_on_label                   |
| MARKER     | Radiation oncology Summary note |
And the "Radiation oncology Summary note" event MARKER is available on the "Treatment and Plan" swimlane with the below badges:
| badge_type     | badge_text |
| CLASSIFICATION | Radiation  |
And the following event is available on the "Pathology and Labs" swimlane:
| event_type | name_on_label                  |
| MARKER     | Tissue Pathology biopsy report |
And the "Tissue Pathology biopsy report" event MARKER is available on the "Pathology and Labs" swimlane with the below badges:
| badge_type     | badge_text |
| CLASSIFICATION | Pathology  |
When the user set the following timeline filter options:
| filter_group       | checkbox_name | selected |
| Pathology and Labs | Pathology     | false    |
| Treatment types    | Radiation     | false    |
| Treatment types    | Surgery       | true     |
Then the filter counter icon on the Filter button is displayed with number: 1
And the following filter badge is available in the filter list on the timeline toolbar:
| filter  |
| Surgery |
And the following event is available on the "Treatment and Plan" swimlane:
| event_type | name_on_label     |
| MARKER     | Suture open wound |
And the "Suture open wound" event MARKER is available on the "Treatment and Plan" swimlane with the below badges:
| badge_type     | badge_text |
| CLASSIFICATION | Surgery    |
And the following event is not available on the "Treatment and Plan" swimlane:
| event_type | name_on_label                   |
| MARKER     | Radiation oncology Summary note |
And the following event is not available on the "Pathology and Labs" swimlane:
| event_type | name_on_label                  |
| MARKER     | Tissue Pathology biopsy report |

@SRS-149.04
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68636
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Event_Classification_Filter_On_Timeline
Scenario: Search for events based on classification on the timeline filter modal (search when at least 3 characters are typed)
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Empty" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the user has opened the timeline filter modal
And the following timeline filter options are displayed on the filter modal:
| filter_group       | checkbox_name    | selected | enabled |
| Imaging modalities | Bone scan        | false    | true    |
| Imaging modalities | CT               | false    | true    |
| Imaging modalities | Fluoroscopy      | false    | true    |
| Imaging modalities | Mammography      | false    | true    |
| Imaging modalities | MR               | false    | true    |
| Imaging modalities | PET              | false    | true    |
| Imaging modalities | PET-CT           | false    | true    |
| Imaging modalities | PET-MR           | false    | true    |
| Imaging modalities | SPECT            | false    | true    |
| Imaging modalities | Ultrasound       | false    | true    |
| Imaging modalities | X-ray            | false    | true    |
| Pathology and Labs | Labs             | false    | true    |
| Pathology and Labs | Pathology        | false    | true    |
| Encounter types    | ER visit         | false    | true    |
| Encounter types    | Inpatient visit  | false    | true    |
| Encounter types    | Outpatient visit | false    | true    |
| Biomarkers         | Genomic report   | false    | true    |
| Treatment types    | Radiation        | false    | true    |
| Treatment types    | Surgery          | false    | true    |
| Treatment types    | Systemic Tx      | false    | true    |
When the user types the following text into the search field on filter modal: "su"
Then only the following timeline filter options are displayed on the filter modal in "Treatment types" group:
| checkbox_name | selected | enabled |
| Surgery       | false    | true    |
And only the following timeline filter options are displayed on the filter modal in "Imaging modalities" group:
| checkbox_name | selected | enabled |
| Ultrasound    | false    | true    |
And the following message is displayed on the Filter modal in the "Swimlanes" group: "No matches."
And the following message is displayed on the Filter modal in the "Pathology and Labs" group: "No matches."
And the following message is displayed on the Filter modal in the "Encounter types" group: "No matches."
And the following message is displayed on the Filter modal in the "Biomarkers" group: "No matches."
When the user types the following text into the search field on filter modal: "sur"
Then only the following timeline filter options are displayed on the filter modal in "Treatment types" group:
| checkbox_name | selected | enabled |
| Surgery       | false    | true    |
And the following message is displayed on the Filter modal in the "Swimlanes" group: "No matches."
And the following message is displayed on the Filter modal in the "Labels" group: "No matches."
And the following message is displayed on the Filter modal in the "Imaging modalities" group: "No matches."
And the following message is displayed on the Filter modal in the "Pathology and Labs" group: "No matches."
And the following message is displayed on the Filter modal in the "Encounter types" group: "No matches."
And the following message is displayed on the Filter modal in the "Biomarkers" group: "No matches."
When the user types the following text into the search field on filter modal: "tholo"
Then only the following timeline filter options are displayed on the filter modal in "Pathology and Labs" group:
| checkbox_name | selected | enabled |
| Pathology     | false    | true    |
And only the following timeline filter options are displayed on the filter modal in "Swimlanes" group:
| checkbox_name      | selected | enabled |
| Pathology and Labs | false    | true    |
And the following message is displayed on the Filter modal in the "Labels" group: "No matches."
And the following message is displayed on the Filter modal in the "Imaging modalities" group: "No matches."
And the following message is displayed on the Filter modal in the "Encounter types" group: "No matches."
And the following message is displayed on the Filter modal in the "Biomarkers" group: "No matches."
And the following message is displayed on the Filter modal in the "Treatment types" group: "No matches."
When the user types the following text into the search field on filter modal: "N0t avai1ab13 note"
Then the following message is displayed on the Filter modal in the "Swimlanes" group: "No matches."
And the following message is displayed on the Filter modal in the "Labels" group: "No matches."
And the following message is displayed on the Filter modal in the "Imaging modalities" group: "No matches."
And the following message is displayed on the Filter modal in the "Pathology and Labs" group: "No matches."
And the following message is displayed on the Filter modal in the "Encounter types" group: "No matches."
And the following message is displayed on the Filter modal in the "Biomarkers" group: "No matches."
And the following message is displayed on the Filter modal in the "Treatment types" group: "No matches."

@SRS-149.05
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68636,SysRS-68638
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Event_Classification_Filter_On_Timeline
Scenario: User can reset event classification filter settings
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event is available on the "Encounters" swimlane with the below badges:
| event_type | name_on_label                        | badges                     |
| MARKER     | Emergency room admission (procedure) | [CLASSIFICATION]: ER visit |
And the following events are available on the "Imaging" swimlane with the below badges:
| event_type | name_on_label                                                                   | badges                        |
| MARKER     | Percutaneous mechanical thrombectomy of portal vein using fluoroscopic guidance | [CLASSIFICATION]: Fluoroscopy |
| MARKER     | Bone density scan (procedure)                                                   | [CLASSIFICATION]: Bone scan   |
| MARKER     | XR ABDOMEN                                                                      | [CLASSIFICATION]: X-ray       |
| MARKER     | Positron emission tomography with computed tomography (procedure)               | [CLASSIFICATION]: CT          |
| MARKER     | Mammography (procedure)                                                         | [CLASSIFICATION]: Mammography |
And the following event is available on the "Biomarkers" swimlane with the below badges:
| event_type | name_on_label           | badges                           |
| MARKER     | Genetic analysis report | [CLASSIFICATION]: Genomic report |
When the user set the following timeline filter option:
| filter_group       | checkbox_name    | selected |
| Encounter types    | ER visit         | true     |
| Imaging modalities | Fluoroscopy      | true     |
| Biomarkers         | Genomic report   | true     |
| Imaging modalities | Mammography      | true     |
| Imaging modalities | X-ray            | true     |
Then the following event is available on the "Encounters" swimlane with the below badges:
| event_type | name_on_label                        | badges                     |
| MARKER     | Emergency room admission (procedure) | [CLASSIFICATION]: ER visit |
And the following events are available on the "Imaging" swimlane with the below badges:
| event_type | name_on_label                                                                   | badges                        |
| MARKER     | Percutaneous mechanical thrombectomy of portal vein using fluoroscopic guidance | [CLASSIFICATION]: Fluoroscopy |
| MARKER     | XR ABDOMEN                                                                      | [CLASSIFICATION]: X-ray       |
| MARKER     | Mammography (procedure)                                                         | [CLASSIFICATION]: Mammography |
And the following events are not available on the "Imaging" swimlane with the below badges:
| event_type | name_on_label                                                     | badges                       |
| MARKER     | Bone density scan (procedure)                                     | [CLASSIFICATION]: Bone scan  |
| MARKER     | Magnetic resonance imaging of breast (procedure)                  | [CLASSIFICATION]: MR         |
| MARKER     | Ultrasonography of bilateral breasts (procedure)                  | [CLASSIFICATION]: Ultrasound |
| MARKER     | Positron emission tomography with computed tomography (procedure) | [CLASSIFICATION]: CT         |
And the following event is available on the "Biomarkers" swimlane with the below badges:
| event_type | name_on_label           | badges                           |
| MARKER     | Genetic analysis report | [CLASSIFICATION]: Genomic report |
When the user opens the timeline filter modal
Then the following timeline filter options are displayed on the filter modal:
| filter_group       | checkbox_name    | selected | enabled |
| Imaging modalities | Bone scan        | false    | true    |
| Imaging modalities | CT               | false    | true    |
| Imaging modalities | Fluoroscopy      | true     | true    |
| Imaging modalities | Mammography      | true     | true    |
| Imaging modalities | MR               | false    | true    |
| Imaging modalities | PET              | false    | true    |
| Imaging modalities | PET-CT           | false    | true    |
| Imaging modalities | PET-MR           | false    | true    |
| Imaging modalities | SPECT            | false    | true    |
| Imaging modalities | Ultrasound       | false    | true    |
| Imaging modalities | X-ray            | true     | true    |
| Pathology and Labs | Labs             | false    | true    |
| Pathology and Labs | Pathology        | false    | true    |
| Encounter types    | ER visit         | true     | true    |
| Encounter types    | Inpatient visit  | false    | true    |
| Encounter types    | Outpatient visit | false    | true    |
| Biomarkers         | Genomic report   | true     | true    |
| Treatment types    | Radiation        | false    | true    |
| Treatment types    | Surgery          | false    | true    |
| Treatment types    | Systemic Tx      | false    | true    |
When the user clicks on the "Reset filters" button on the filter modal
Then the following timeline filter options are displayed on the filter modal:
| filter_group       | checkbox_name    | selected | enabled |
| Imaging modalities | Bone scan        | false    | true    |
| Imaging modalities | CT               | false    | true    |
| Imaging modalities | Fluoroscopy      | false    | true    |
| Imaging modalities | Mammography      | false    | true    |
| Imaging modalities | MR               | false    | true    |
| Imaging modalities | PET              | false    | true    |
| Imaging modalities | PET-CT           | false    | true    |
| Imaging modalities | PET-MR           | false    | true    |
| Imaging modalities | SPECT            | false    | true    |
| Imaging modalities | Ultrasound       | false    | true    |
| Imaging modalities | X-ray            | false    | true    |
| Pathology and Labs | Labs             | false    | true    |
| Pathology and Labs | Pathology        | false    | true    |
| Encounter types    | ER visit         | false    | true    |
| Encounter types    | Inpatient visit  | false    | true    |
| Encounter types    | Outpatient visit | false    | true    |
| Biomarkers         | Genomic report   | false    | true    |
| Treatment types    | Radiation        | false    | true    |
| Treatment types    | Surgery          | false    | true    |
| Treatment types    | Systemic Tx      | false    | true    |
When the user closes the timeline filter modal
Then the following event is available on the "Encounters" swimlane with the below badges:
| event_type | name_on_label                        | badges                     |
| MARKER     | Emergency room admission (procedure) | [CLASSIFICATION]: ER visit |
And the following events are available on the "Imaging" swimlane with the below badges:
| event_type | name_on_label                                                                   | badges                        |
| MARKER     | Percutaneous mechanical thrombectomy of portal vein using fluoroscopic guidance | [CLASSIFICATION]: Fluoroscopy |
| MARKER     | Bone density scan (procedure)                                                   | [CLASSIFICATION]: Bone scan   |
| MARKER     | XR ABDOMEN                                                                      | [CLASSIFICATION]: X-ray       |
| MARKER     | Magnetic resonance imaging of breast (procedure)                                | [CLASSIFICATION]: MR          |
| MARKER     | Ultrasonography of bilateral breasts (procedure)                                | [CLASSIFICATION]: Ultrasound  |
| MARKER     | Positron emission tomography with computed tomography (procedure)               | [CLASSIFICATION]: CT          |
| MARKER     | Mammography (procedure)                                                         | [CLASSIFICATION]: Mammography |
And the following event is available on the "Biomarkers" swimlane with the below badges:
| event_type | name_on_label           | badges                           |
| MARKER     | Genetic analysis report | [CLASSIFICATION]: Genomic report |

@SRS-149.06
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68636
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use/Event_Classification_Filter_On_Timeline
Scenario: Events can be filtered on the timeline based on event classification (PET, PET-CT, PET-MR, SPECT, Inpatient visit)
Given [API] the following events were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                             | event_name                                |
| 18c9b2ac-0b07-4e57-a0be-b6297a552a69 | Procedure/78266e21-b028-4a06-9ec6-75570dcb548c         | Radiopharmaceutical localization of tumor |
| 18c9b2ac-0b07-4e57-a0be-b6297a552a69 | DiagnosticReport/e3182891-aea7-4ba0-907d-bb7e548212f6  | PET MRI of whole body                     |
| 18c9b2ac-0b07-4e57-a0be-b6297a552a69 | Procedure/22d45d4a-8f0c-4e38-9ec3-bdd47d50ec29         | PET 001                                   |
| 18c9b2ac-0b07-4e57-a0be-b6297a552a69 | DiagnosticReport/3ee03b18-b21b-438e-bfb7-617d8b795148  | PET with CT                               |
| 18c9b2ac-0b07-4e57-a0be-b6297a552a69 | Procedure/2ad58a1c-bbb8-4a7e-a658-c775cf804f5f         | Positron emission tomography (PET)        |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                             | event_name                                |
| 18c9b2ac-0b07-4e57-a0be-b6297a552a69 | Procedure/78266e21-b028-4a06-9ec6-75570dcb548c         | Radiopharmaceutical localization of tumor |
| 18c9b2ac-0b07-4e57-a0be-b6297a552a69 | DiagnosticReport/e3182891-aea7-4ba0-907d-bb7e548212f6  | PET MRI of whole body                     |
| 18c9b2ac-0b07-4e57-a0be-b6297a552a69 | Procedure/22d45d4a-8f0c-4e38-9ec3-bdd47d50ec29         | PET 001                                   |
| 18c9b2ac-0b07-4e57-a0be-b6297a552a69 | DiagnosticReport/3ee03b18-b21b-438e-bfb7-617d8b795148  | PET with CT                               |
| 18c9b2ac-0b07-4e57-a0be-b6297a552a69 | Procedure/2ad58a1c-bbb8-4a7e-a658-c775cf804f5f         | Positron emission tomography (PET)        |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                             | event_name                                |
| 18c9b2ac-0b07-4e57-a0be-b6297a552a69 | Procedure/78266e21-b028-4a06-9ec6-75570dcb548c         | Radiopharmaceutical localization of tumor |
| 18c9b2ac-0b07-4e57-a0be-b6297a552a69 | DiagnosticReport/e3182891-aea7-4ba0-907d-bb7e548212f6  | PET MRI of whole body                     |
| 18c9b2ac-0b07-4e57-a0be-b6297a552a69 | Procedure/22d45d4a-8f0c-4e38-9ec3-bdd47d50ec29         | PET 001                                   |
| 18c9b2ac-0b07-4e57-a0be-b6297a552a69 | DiagnosticReport/3ee03b18-b21b-438e-bfb7-617d8b795148  | PET with CT                               |
| 18c9b2ac-0b07-4e57-a0be-b6297a552a69 | Procedure/2ad58a1c-bbb8-4a7e-a658-c775cf804f5f         | Positron emission tomography (PET)        |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Tobin" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following events are available on the "Imaging" swimlane with the below badges:
| event_type | name_on_label                             | badges                    |
| MARKER     | Radiopharmaceutical localization of tumor | [CLASSIFICATION]: SPECT   |
| MARKER     | PET MRI of whole body                     | [CLASSIFICATION]: PET-MR  |
| MARKER     | PET 001                                   | [CLASSIFICATION]: PET     |
| MARKER     | PET with CT                               | [CLASSIFICATION]: PET-CT  |
| MARKER     | Positron emission tomography (PET)        | [CLASSIFICATION]: PET     |
When the user set the following timeline filter options:
| filter_group       | checkbox_name    | selected |
| Encounter types    | Inpatient visit  | true     |
| Imaging modalities | PET              | true     |
| Imaging modalities | PET-CT           | false    |
| Imaging modalities | PET-MR           | false    |
| Imaging modalities | SPECT            | false    |
Then the following events are available on the "Imaging" swimlane with the below badges:
| event_type | name_on_label                             | badges                    |
| MARKER     | PET 001                                   | [CLASSIFICATION]: PET     |
| MARKER     | Positron emission tomography (PET)        | [CLASSIFICATION]: PET     |
And the following events are not available on the "Imaging" swimlane with the below badges:
| event_type | name_on_label                             | badges                    |
| MARKER     | Radiopharmaceutical localization of tumor | [CLASSIFICATION]: SPECT   |
| MARKER     | PET MRI of whole body                     | [CLASSIFICATION]: PET-MR  |
| MARKER     | PET with CT                               | [CLASSIFICATION]: PET-CT  |
When the user set the following timeline filter options:
| filter_group       | checkbox_name    | selected |
| Encounter types    | Inpatient visit  | false    |
| Imaging modalities | PET              | false    |
| Imaging modalities | PET-CT           | true     |
| Imaging modalities | PET-MR           | false    |
| Imaging modalities | SPECT            | false    |
Then the following event is available on the "Imaging" swimlane with the below badges:
| event_type | name_on_label                             | badges                    |
| MARKER     | PET with CT                               | [CLASSIFICATION]: PET-CT  |
And the following events are not available on the "Imaging" swimlane with the below badges:
| event_type | name_on_label                             | badges                    |
| MARKER     | PET 001                                   | [CLASSIFICATION]: PET     |
| MARKER     | Positron emission tomography (PET)        | [CLASSIFICATION]: PET     |
| MARKER     | Radiopharmaceutical localization of tumor | [CLASSIFICATION]: SPECT   |
| MARKER     | PET MRI of whole body                     | [CLASSIFICATION]: PET-MR  |
When the user set the following timeline filter options:
| filter_group       | checkbox_name    | selected |
| Encounter types    | Inpatient visit  | false    |
| Imaging modalities | PET              | false    |
| Imaging modalities | PET-CT           | false    |
| Imaging modalities | PET-MR           | true     |
| Imaging modalities | SPECT            | true     |
Then the following events are available on the "Imaging" swimlane with the below badges:
| event_type | name_on_label                             | badges                    |
| MARKER     | Radiopharmaceutical localization of tumor | [CLASSIFICATION]: SPECT   |
| MARKER     | PET MRI of whole body                     | [CLASSIFICATION]: PET-MR  |
And the following events are not available on the "Imaging" swimlane with the below badges:
| event_type | name_on_label                             | badges                    |
| MARKER     | PET with CT                               | [CLASSIFICATION]: PET-CT  |
| MARKER     | PET 001                                   | [CLASSIFICATION]: PET     |
| MARKER     | Positron emission tomography (PET)        | [CLASSIFICATION]: PET     |
