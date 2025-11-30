Feature: [SysRS-68655] Colored Event Badges On Timeline

Narrative: The system shall display colored event badges on the Timeline based on event types.


@SRS-214.01
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68655,SysRS-68657
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Colored_Event_Badges_On_Timeline
Scenario: Classification badges are colorized and have icons: Fluoroscopy, Genomic report, Bone scan, CT, Mammography, MR, Ultrasound, X-ray, Pathology, Labs, Surgery, Systemic Tx, ER visit, Outpatient visit
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
When the user navigates to the "Timeline" view
Then the patient timeline is loaded
And the following badge is available on the "Percutaneous mechanical thrombectomy of portal vein ..." event tooltip on "Imaging" swimlane:
| badge_text  | reference_badge_icon_and_color                 |
| Fluoroscopy | testdata/labels/Label_library.svg: Fluoroscopy |
And the following badge is available on the "Genetic analysis report" event tooltip on "Biomarkers" swimlane:
| badge_text     | reference_badge_icon_and_color                    |
| Genomic report | testdata/labels/Label_library.svg: Genomic report |
And the following badge is available on the "Bone density scan (procedure)" event tooltip on "Imaging" swimlane:
| badge_text | reference_badge_icon_and_color               |
| Bone scan  | testdata/labels/Label_library.svg: Bone scan |
And the following badge is available on the "Positron emission tomography with computed tomography (procedure)" event tooltip on "Imaging" swimlane:
| badge_text | reference_badge_icon_and_color        |
| CT         | testdata/labels/Label_library.svg: CT |
And the following badge is available on the "Mammography (procedure)" event tooltip on "Imaging" swimlane:
| badge_text  | reference_badge_icon_and_color                 |
| Mammography | testdata/labels/Label_library.svg: Mammography |
And the following badge is available on the "Magnetic resonance imaging of breast (procedure)" event tooltip on "Imaging" swimlane:
| badge_text | reference_badge_icon_and_color        |
| MR         | testdata/labels/Label_library.svg: MR |
And the following badge is available on the "Ultrasonography of bilateral breasts (procedure)" event tooltip on "Imaging" swimlane:
| badge_text | reference_badge_icon_and_color                |
| Ultrasound | testdata/labels/Label_library.svg: Ultrasound |
And the following badge is available on the "XR ABDOMEN" event tooltip on "Imaging" swimlane:
| badge_text | reference_badge_icon_and_color           |
| X-ray      | testdata/labels/Label_library.svg: X-ray |
And the following badge is available on the "Test Diagnostic Report" event tooltip on "Pathology and Labs" swimlane:
| badge_text | reference_badge_icon_and_color               |
| Pathology  | testdata/labels/Label_library.svg: Pathology |
And the following badge is available on the "Cholesterol, total" event tooltip on "Pathology and Labs" swimlane:
| badge_text | reference_badge_icon_and_color          |
| Labs       | testdata/labels/Label_library.svg: Labs |
And the following badge is available on the "Hysteroscopy" event tooltip on "Treatment and Plan" swimlane:
| badge_text | reference_badge_icon_and_color             |
| Surgery    | testdata/labels/Label_library.svg: Surgery |
And the following badge is available on the "cyclophosphamide 1 GM Injection" event tooltip on "Treatment and Plan" swimlane:
| badge_text  | reference_badge_icon_and_color                 |
| Systemic Tx | testdata/labels/Label_library.svg: Systemic Tx |
And the following badge is available on the "Emergency room admission (procedure)" event tooltip on "Encounters" swimlane:
| badge_text | reference_badge_icon_and_color              |
| ER visit   | testdata/labels/Label_library.svg: ER visit |
And the following badge is available on the "History and physical note - 1" event tooltip on "Encounters" swimlane:
| badge_text       | reference_badge_color                               |
| Outpatient visit | testdata/labels/Label_library.svg: Outpatient visit |

@SRS-214.02
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68655,SysRS-68657
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Colored_Event_Badges_On_Timeline
Scenario: Classification badges are colorized and have icons: PET, PET-CT, PET-MR, SPECT, Radiation, Inpatient visit
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Tobin" patient is selected
When the user navigates to the "Timeline" view
Then the patient timeline is loaded
And the following badge is available on the "PET 001" event tooltip on "Imaging" swimlane:
| badge_text | reference_badge_icon_and_color         |
| PET        | testdata/labels/Label_library.svg: PET |
And the following badge is available on the "PET with CT" event tooltip on "Imaging" swimlane:
| badge_text | reference_badge_icon_and_color            |
| PET-CT     | testdata/labels/Label_library.svg: PET-CT |
And the following badge is available on the "PET MRI of whole body" event tooltip on "Imaging" swimlane:
| badge_text | reference_badge_icon_and_color            |
| PET-MR     | testdata/labels/Label_library.svg: PET-MR |
And the following badge is available on the "Radiopharmaceutical localization of tumor" event tooltip on "Imaging" swimlane:
| badge_text | reference_badge_icon_and_color           |
| SPECT      | testdata/labels/Label_library.svg: SPECT |
And the following badge is available on the "Radiation oncology Summary note" event tooltip on "Treatment and Plan" swimlane:
| badge_text | reference_badge_icon_and_color               |
| Radiation  | testdata/labels/Label_library.svg: Radiation |
And the following badge is available on the "Discharge summary" event tooltip on "Encounters" swimlane:
| badge_text      | reference_badge_icon_and_color                     |
| Inpatient visit | testdata/labels/Label_library.svg: Inpatient visit |

@SRS-214.03
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68655,SysRS-68657
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Colored_Event_Badges_On_Timeline
Scenario: Important badge is colorized and have icon
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Tobin" patient is selected
And the user navigated to the "Timeline" view
And the patient timeline is loaded
When the user marks the following event as important on "Encounters" swimlane:
| event_type | name_on_label     |
| MARKER     | Discharge summary |
Then the following badge is available on the "Discharge summary" event tooltip on "Encounters" swimlane:
| badge_text | reference_badge_icon_and_color               |
| Important  | testdata/labels/Label_library.svg: Important |

@SRS-214.04
@manual
@CLP
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68655,SysRS-68657
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/General_Usability_And_Ease_Of_Use
Scenario: CLP badge is colorized and have icon
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And "AI summarization" is turned ON in user settings
And the "OncoTestPatient, Torvald" patient is selected
When the user navigates to the "Timeline" view
Then the patient timeline is loaded
And the following badges are available on the "Examination of joint under image intensifier (procedure)" event tooltip on "Imaging" swimlane:
| badge_text                          | reference_badge_icon_and_color                         |
| Right knee joint is normal, no a... | testdata/labels/Label_library.svg: CLP text comes here |

@SRS-214.05
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68655,SysRS-68657
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Colored_Event_Badges_On_Timeline
Scenario: Cycle badge is colorized and have icon
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
When the user navigates to the "Timeline" view
Then the patient timeline is loaded
And the following badge is available on the "CARBOPLATIN INFUSION (BY AUC) IN GLUCOSE" event tooltip on "Treatment and Plan" swimlane:
| badge_text | reference_badge_icon_and_color             |
| Cycle 0    | testdata/labels/Label_library.svg: Cycle 6 |

@SRS-214.06
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68655,SysRS-68657
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Colored_Event_Badges_On_Timeline
Scenario: Comment badge is colorized and have icon
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Timeline" view
And the patient timeline is loaded
When the user add the following comment to "History and physical note - 1" event on "Encounters" swimlane: "test comment"
Then the following badge is available on the "History and physical note - 1" event tooltip on "Encounters" swimlane:
| badge_text | reference_badge_icon_and_color                |
| 1 comment  | testdata/labels/Label_library.svg: 6 comments |

@SRS-214.07
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68655,SysRS-68657
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Colored_Event_Badges_On_Timeline
Scenario: Dose change indicator is colorized and have icon
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Dyna" patient is selected
When the user navigates to the "Timeline" view
Then the patient timeline is loaded
And the following badge is available on the "This is a Medication without codings" event tooltip on "Treatment and Plan" swimlane:
| badge_text       | reference_badge_icon_and_color             |
| +100.0% +3 mg/m2 | testdata/labels/Label_library.svg: +/- +15 |
