Feature: [SysRS-68639] Labels with Icon in Filter Section

Narrative: The system shall display labels (except custom and predefined labels) with icon in the filter section (modal, navigator).


@SRS-222.01
@manual
@srs
@CLP
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68639
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Labels_With_Icon_In_Filter_Section
Scenario: Labels are available on Filter modal with icons (except custom and predefined labels)
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And [API] the following labels are created:
| label_name    |
| Filter test 1 |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
When the user opens the timeline filter modal
Then the items in "Swimlane" group do not have icons
And the following items in "Labels" group have the following icons:
| label_name   | reference_badge_icon                                   | icon_color_on_filter_modal |
| Important    | testdata/labels/Label_library.svg: Important           | yellow                     |
| Chemo cycle  | testdata/labels/Label_library.svg: Cycle 6             | black                      |
| Has comments | testdata/labels/Label_library.svg: 6 comments          | black                      |
| AI enriched  | testdata/labels/Label_library.svg: CLP text comes here | blue-green                 |
And the following items in "Labels" group do not have icons:
| label_name        |
| Adverse Event     |
| Date of diagnosis |
| MDT discussion    |
| Molecular testing |
| Progression       |
| Recurrence        |
| To Be Reviewed    |
| Filter test 1     |
And the items in "Imaging modalities" group have the following icons:
| label_name       | reference_badge_icon                                | icon_color_on_filter_modal |
| Bone scan        | testdata/labels/Label_library.svg: Bone scan        | black                      |
| CT               | testdata/labels/Label_library.svg: CT               | black                      |
| Fluoroscopy      | testdata/labels/Label_library.svg: Fluoroscopy      | black                      |
| Mammography      | testdata/labels/Label_library.svg: Mammography      | black                      |
| MR               | testdata/labels/Label_library.svg: MR               | black                      |
| PET              | testdata/labels/Label_library.svg: PET              | black                      |
| PET-CT           | testdata/labels/Label_library.svg: PET-CT           | black                      |
| PET-MR           | testdata/labels/Label_library.svg: PET-MR           | black                      |
| SPECT            | testdata/labels/Label_library.svg: SPECT            | black                      |
| Ultrasound       | testdata/labels/Label_library.svg: Ultrasound       | black                      |
| X-ray            | testdata/labels/Label_library.svg: X-ray            | black                      |
And the items in "Pathology and Labs" group have the following icons:
| label_name       | reference_badge_icon                                | icon_color_on_filter_modal |
| Labs             | testdata/labels/Label_library.svg: Labs             | black                      |
| Pathology        | testdata/labels/Label_library.svg: Pathology        | black                      |
And the items in "Encounter types" group have the following icons:
| label_name       | reference_badge_icon                                | icon_color_on_filter_modal |
| ER visit         | testdata/labels/Label_library.svg: ER visit         | black                      |
| Inpatient visit  | testdata/labels/Label_library.svg: Inpatient visit  | black                      |
| Outpatient visit | testdata/labels/Label_library.svg: Outpatient visit | black                      |
And the items in "Biomarkers" group have the following icons:
| label_name       | reference_badge_icon                                | icon_color_on_filter_modal |
| Genomic report   | testdata/labels/Label_library.svg: Genomic report   | black                      |
And the items in "Treatment types" group have the following icons:
| label_name       | reference_badge_icon                                | icon_color_on_filter_modal |
| Radiation        | testdata/labels/Label_library.svg: Radiation        | black                      |
| Surgery          | testdata/labels/Label_library.svg: Surgery          | black                      |
| Systemic Tx      | testdata/labels/Label_library.svg: Systemic Tx      | black                      |

@SRS-222.02
@manual
@srs
@CLP
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68639
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Labels_With_Icon_In_Filter_Section
Scenario: Label badges are available on timeline toolbar with icons (except custom and predefined labels)
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And [API] the following labels are created:
| label_name    |
| Filter test 1 |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
When the user opens the timeline filter modal
And the user selects the following items from "Swimlanes" group:
| label_name        |
| Encounters        |
| Imaging           |
And the user selects the following items from "Labels" group:
| label_name        |
| Important         |
| Chemo cycle       |
| Has comments      |
| AI enriched       |
| Adverse Event     |
| Date of diagnosis |
| MDT discussion    |
| Molecular testing |
| Progression       |
| Recurrence        |
| To Be Reviewed    |
| Filter test 1     |
And the user selects all items in "Imaging modalities" group
And the user selects all items in "Pathology and Labs" group
And the user selects all items in "Encounter types" group
And the user selects all items in "Biomarkers" group
And the user selects all items in "Treatment types" group
And the user closes the filter modal
And the user opens the filter badge list on the timeline toolbar
Then the following badges do not have icons:
| badge_text        |
| Encounters        |
| Imaging           |
| Adverse Event     |
| Date of diagnosis |
| MDT discussion    |
| Molecular testing |
| Progression       |
| Recurrence        |
| To Be Reviewed    |
| Filter test 1     |
And the following badges have icons:
| label_name       | reference_badge_icon                                   | icon_color_on_timeline_toolbar |
| Important        | testdata/labels/Label_library.svg: Important           | black                          |
| Chemo cycle      | testdata/labels/Label_library.svg: Cycle 6             | black                          |
| Has comments     | testdata/labels/Label_library.svg: 6 comments          | black                          |
| AI enriched      | testdata/labels/Label_library.svg: CLP text comes here | black                          |
| Bone scan        | testdata/labels/Label_library.svg: Bone scan           | black                          |
| CT               | testdata/labels/Label_library.svg: CT                  | black                          |
| Fluoroscopy      | testdata/labels/Label_library.svg: Fluoroscopy         | black                          |
| Mammography      | testdata/labels/Label_library.svg: Mammography         | black                          |
| MR               | testdata/labels/Label_library.svg: MR                  | black                          |
| PET              | testdata/labels/Label_library.svg: PET                 | black                          |
| PET-CT           | testdata/labels/Label_library.svg: PET-CT              | black                          |
| PET-MR           | testdata/labels/Label_library.svg: PET-MR              | black                          |
| SPECT            | testdata/labels/Label_library.svg: SPECT               | black                          |
| Ultrasound       | testdata/labels/Label_library.svg: Ultrasound          | black                          |
| X-ray            | testdata/labels/Label_library.svg: X-ray               | black                          |
| Labs             | testdata/labels/Label_library.svg: Labs                | black                          |
| Pathology        | testdata/labels/Label_library.svg: Pathology           | black                          |
| ER visit         | testdata/labels/Label_library.svg: ER visit            | black                          |
| Inpatient visit  | testdata/labels/Label_library.svg: Inpatient visit     | black                          |
| Outpatient visit | testdata/labels/Label_library.svg: Outpatient visit    | black                          |
| Genomic report   | testdata/labels/Label_library.svg: Genomic report      | black                          |
| Radiation        | testdata/labels/Label_library.svg: Radiation           | black                          |
| Surgery          | testdata/labels/Label_library.svg: Surgery             | black                          |
| Systemic Tx      | testdata/labels/Label_library.svg: Systemic Tx         | black                          |
