Feature: [SysRS-68656] Colored Event Points On Macro-navigator

Narrative: The system shall display colored event points on the Macro navigator according to the corresponding event’s color on the timeline. In case of multiple classification show the first one.


@SRS-215.01
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68656
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Colored_Event_Points_On_Macro_Navigator
Scenario: Colored event points are displayed on macro-navigator based on their classification: Fluoroscopy, Genomic report, Bone scan, CT, Mammography, MR, Ultrasound, X-ray, Pathology, Labs, Surgery, Systemic Tx, ER visit, Outpatient visit
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
When the user opens the following event point on the "Imaging" swimlane:
| event_type | name_on_label                                           |
| MARKER     | Percutaneous mechanical thrombectomy of portal vein ... |
Then the selected event point on macro-navigator has the same color as the "Fluoroscopy" badge on the selected event tooltip
When the user closes the sidebar
Then the color of the previously checked event point on macro-navigator is not changed
When the user opens the following event point on the "Biomarkers" swimlane:
| event_type | name_on_label           |
| MARKER     | Genetic analysis report |
Then the selected event point on macro-navigator has the same color as the "Genomic report" badge on the selected event tooltip
When the user opens the following event point on the "Imaging" swimlane:
| event_type | name_on_label                 |
| MARKER     | Bone density scan (procedure) |
Then the selected event point on macro-navigator has the same color as the "Bone scan" badge on the selected event tooltip
When the user opens the following event point on the "Imaging" swimlane:
| event_type | name_on_label                                             |
| MARKER     | Positron emission tomography with computed tomography ... |
Then the selected event point on macro-navigator has the same color as the "CT" badge on the selected event tooltip
When the user opens the following event point on the "Imaging" swimlane:
| event_type | name_on_label           |
| MARKER     | Mammography (procedure) |
Then the selected event point on macro-navigator has the same color as the "Mammography" badge on the selected event tooltip
When the user opens the following event point on the "Imaging" swimlane:
| event_type | name_on_label                                    |
| MARKER     | Magnetic resonance imaging of breast (procedure) |
Then the selected event point on macro-navigator has the same color as the "MR" badge on the selected event tooltip
When the user opens the following event point on the "Imaging" swimlane:
| event_type | name_on_label                                    |
| MARKER     | Ultrasonography of bilateral breasts (procedure) |
Then the selected event point on macro-navigator has the same color as the "Ultrasound" badge on the selected event tooltip
When the user opens the following event point on the "Imaging" swimlane:
| event_type | name_on_label |
| MARKER     | XR ABDOMEN    |
Then the selected event point on macro-navigator has the same color as the "X-ray" badge on the selected event tooltip
When the user opens the following event point on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
Then the selected event point on macro-navigator has the same color as the "Pathology" badge on the selected event tooltip
When the user opens the following event point on the "Pathology and Labs" swimlane:
| event_type | name_on_label      |
| MARKER     | Cholesterol, total |
Then the selected event point on macro-navigator has the same color as the "Labs" badge on the selected event tooltip
When the user opens the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label |
| MARKER     | Hysteroscopy  |
Then the selected event point on macro-navigator has the same color as the "Surgery" badge on the selected event tooltip
When the user opens the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label                   |
| MARKER     | cyclophosphamide 1 GM Injection |
Then the selected event point on macro-navigator has the same color as the "Systemic Tx" badge on the selected event tooltip
When the user opens the following event point on the "Encounters" swimlane:
| event_type | name_on_label                        |
| MARKER     | Emergency room admission (procedure) |
Then the selected event point on macro-navigator has the same color as the "ER visit" badge on the selected event tooltip
When the user opens the following event point on the "Encounters" swimlane:
| event_type | name_on_label                 |
| MARKER     | History and physical note - 1 |
Then the selected event point on macro-navigator has the same color as the "Outpatient visit" badge on the selected event tooltip

@SRS-215.02
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68656
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Colored_Event_Points_On_Macro_Navigator
Scenario: Colored event points are displayed on macro-navigator based on their classification: PET, PET-CT, PET-MR, SPECT, Radiation, Inpatient visit
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Tobin" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
When the user opens the following event point on the "Imaging" swimlane:
| event_type | name_on_label |
| MARKER     | PET 001       |
Then the selected event point on macro-navigator has the same color as the "PET" badge on the selected event tooltip
When the user closes the sidebar
Then the color of the previously checked event point on macro-navigator is not changed
When the user opens the following event point on the "Imaging" swimlane:
| event_type | name_on_label |
| MARKER     | PET with CT   |
Then the selected event point on macro-navigator has the same color as the "PET-CT" badge on the selected event tooltip
When the user opens the following event point on the "Imaging" swimlane:
| event_type | name_on_label         |
| MARKER     | PET MRI of whole body |
Then the selected event point on macro-navigator has the same color as the "PET-MR" badge on the selected event tooltip
When the user opens the following event point on the "Imaging" swimlane:
| event_type | name_on_label                             |
| MARKER     | Radiopharmaceutical localization of tumor |
Then the selected event point on macro-navigator has the same color as the "SPECT" badge on the selected event tooltip
When the user opens the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label                   |
| MARKER     | Radiation oncology Summary note |
Then the selected event point on macro-navigator has the same color as the "Radiation" badge on the selected event tooltip
When the user opens the following event point on the "Encounters" swimlane:
| event_type | name_on_label     |
| MARKER     | Discharge summary |
Then the selected event point on macro-navigator has the same color as the "Inpatient visit" badge on the selected event tooltip
