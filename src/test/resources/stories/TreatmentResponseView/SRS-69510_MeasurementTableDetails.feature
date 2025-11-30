Feature: [SysRS-69510] Measurement Table Details

Narrative: The system shall display for each measurement the following information:
             - Name.
             - Value.
             - Unit of measurement (if available).
             - Date.
             - Trend direction (Increase/ Decrease - Point B compared/related to Point A).


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Sigrid" patient is selected
And the patient summary view is loaded
And the "Status and response" button was clicked
And the Treatment and response view is loaded

@SRS-192.01
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69510
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Treatment_Response_View
Scenario: Display the nearest value within 30 days following the selected imaging study, if none exists, show the closest value within 30 days preceding the selected imaging study. (if the selected imaging study is not the most recent)
When the user selects the following imaging study for study A:
| name                     | date         |
| Diagnostic imaging study | Apr 25, 2010 |
And the user selects the following imaging study for study B:
| name                     | date         |
| Diagnostic imaging study | Jan 24, 2013 |
Then the Measurements table is displayed with the following details:
| measure                                               | A_badge_value           | A_badge_date | B_badge_value           | B_badge_date | diff_badge |
| Body Weight                                           | No value within 30 days | <N/A>        | 64 kg                   | Feb 20, 2013 | <N/A>      |
| Prostate specific Ag [Mass/volume] in Pleural fluid   | No value within 30 days | <N/A>        | No value within 30 days | <N/A>        | <N/A>      |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | No value within 30 days | <N/A>        | 1.21 ng/mL              | Jan 12, 2013 | <N/A>      |
| Prostate specific Ag [Moles/volume] in Plasma         | No value within 30 days | <N/A>        | No value within 30 days | <N/A>        | <N/A>      |

@SRS-192.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69510
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Treatment_Response_View
Scenario: Display the most recent value after the latest imaging study (without threshold), if no data is available after the latest imaging study, show the most recent value before it
When the user selects the following imaging study for study A:
| name                     | date         |
| Diagnostic imaging study | Jan 24, 2013 |
And the user selects the following imaging study for study B:
| name                                   | date         |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Mar 11, 2016 |
Then the Measurements table is displayed with the following details:
| measure                                               | A_badge_value           | A_badge_date | B_badge_value           | B_badge_date | diff_badge          |
| Body Weight                                           | 64 kg                   | Feb 20, 2013 | 74.2 kg                 | Dec 21, 2013 | +15.9% +10.2 kg     |
| Prostate specific Ag [Mass/volume] in Pleural fluid   | No value within 30 days | <N/A>        | 17.4 %                  | Jul 19, 2017 | <N/A>               |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | 1.21 ng/mL              | Jan 12, 2013 | 1.632 ng/mL             | Apr 25, 2017 | +34.9% +0.422 ng/mL |
| Prostate specific Ag [Moles/volume] in Plasma         | No value within 30 days | <N/A>        | 27.8 umol/L             | Jun 18, 2017 | <N/A>               |

@SRS-192.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69510
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Treatment_Response_View
Scenario: 'No value within 30 days' is displayed when data is NOT available within 30 days before/after of the selected imaging study (earlier in time)
When the user selects the following imaging study for study A:
| name                     | date         |
| Diagnostic imaging study | Apr 25, 2010 |
And the user selects the following imaging study for study B:
| name                                   | date         |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Mar 11, 2016 |
Then the Measurements table is displayed with the following details:
| measure                                               | A_badge_value           | A_badge_date | B_badge_value           | B_badge_date | diff_badge |
| Body Weight                                           | No value within 30 days | <N/A>        | 74.2 kg                 | Dec 21, 2013 | <N/A>      |
| Prostate specific Ag [Mass/volume] in Pleural fluid   | No value within 30 days | <N/A>        | 17.4 %                  | Jul 19, 2017 | <N/A>      |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | No value within 30 days | <N/A>        | 1.632 ng/mL             | Apr 25, 2017 | <N/A>      |
| Prostate specific Ag [Moles/volume] in Plasma         | No value within 30 days | <N/A>        | 27.8 umol/L             | Jun 18, 2017 | <N/A>      |
When the user clicks on the Swap button on the Study selector module
Then the Measurements table is displayed with the following details:
| measure                                               | A_badge_value           | A_badge_date | B_badge_value           | B_badge_date | diff_badge |
| Body Weight                                           | 74.2 kg                 | Dec 21, 2013 | No value within 30 days | <N/A>        | <N/A>      |
| Prostate specific Ag [Mass/volume] in Pleural fluid   | 17.4 %                  | Jul 19, 2017 | No value within 30 days | <N/A>        | <N/A>      |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | 1.632 ng/mL             | Apr 25, 2017 | No value within 30 days | <N/A>        | <N/A>      |
| Prostate specific Ag [Moles/volume] in Plasma         | 27.8 umol/L             | Jun 18, 2017 | No value within 30 days | <N/A>        | <N/A>      |
