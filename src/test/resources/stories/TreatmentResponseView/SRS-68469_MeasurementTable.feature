Feature: [SysRS-68469] Measurement table

Narrative: The system shall display the following Measurements in table format:
             - Tumor markers
             - Weight
             - PSA
             - PSMA


@SRS-190.01
@srs
@edge
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68469
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Treatment_Response_View
Scenario: Measurements table is displayed in the Response group
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Rory188" patient is selected
And the patient summary view is loaded
And the "Status and response" button was clicked
And the Treatment and response view is loaded
When the user selects the following imaging study for study A:
| name            | date         |
| Upper arm X-ray | Jul 13, 1962 |
And the user selects the following imaging study for study B:
| name        | date         |
| Chest X-ray | Oct 08, 1974 |
Then the Measurements table is displayed with the following details:
| measure                                                                   | A_badge_value           | A_badge_date | B_badge_value  | B_badge_date | diff_badge       |
| Alanine aminotransferase [Enzymatic activity/volume] in Serum or Plasma   | No value within 30 days | <N/A>        | 54.383 U/L     | Dec 20, 2016 | <N/A>            |
| Albumin [Mass/volume] in Serum or Plasma                                  | No value within 30 days | <N/A>        | 4.3713 g/dL    | Dec 20, 2016 | <N/A>            |
| Alkaline phosphatase [Enzymatic activity/volume] in Serum or Plasma       | No value within 30 days | <N/A>        | 42.725 U/L     | Dec 20, 2016 | <N/A>            |
| Aspartate aminotransferase [Enzymatic activity/volume] in Serum or Plasma | No value within 30 days | <N/A>        | 28.691 U/L     | Dec 20, 2016 | <N/A>            |
| Bilirubin.total [Mass/volume] in Serum or Plasma                          | No value within 30 days | <N/A>        | 0.57402 mg/dL  | Dec 20, 2016 | <N/A>            |
| Body Weight                                                               | 29.3 kg                 | Jul 17, 1962 | 65.5 kg        | Oct 15, 2019 | +123.5% +36.2 kg |
| Creatinine Mass/volume in Serum or Plasma                                 | No value within 30 days | <N/A>        | 0.64 mg/dL     | Oct 15, 2019 | <N/A>            |
| Hematocrit [Volume Fraction] of Blood by Automated count                  | No value within 30 days | <N/A>        | 48.275 %       | Dec 20, 2016 | <N/A>            |
| Hemoglobin [Mass/volume] in Blood                                         | No value within 30 days | <N/A>        | 12.668 g/dL    | Dec 20, 2016 | <N/A>            |
| Leukocytes [#/volume] in Blood by Automated count                         | No value within 30 days | <N/A>        | 4.6939 10*3/uL | Dec 20, 2016 | <N/A>            |
| Platelets [#/volume] in Blood by Automated count                          | No value within 30 days | <N/A>        | 281.37 10*3/uL | Dec 20, 2016 | <N/A>            |
| Prostate specific Ag [Mass/volume] in Serum or Plasma                     | No value within 30 days | <N/A>        | 6.5733 ng/mL   | Sep 21, 2016 | <N/A>            |