Feature: [SysRS-69506] Swap between time points

Narrative: The system shall provide the user the ability to Swap between selected time point as described in Response tracking - Select time point to compare (SysRS-69191).


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Sigrid" patient is selected
And the patient summary view is loaded
And the "Status and response" button was clicked
And the Treatment and response view is loaded


@sanity
@edge
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69506
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Treatment_Response_View
Scenario: User can swap the selected A and B events
When the user selects the following imaging study for study A:
| name                     | date         |
| Diagnostic imaging study | Jan 24, 2013 |
And the user selects the following imaging study for study B:
| name                                   | date         |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Mar 11, 2016 |
Then the selected study for A is displayed in the Response group with the following details:
| study_badge | study_date   | study_name               | link |
| A           | Jan 24, 2013 | Diagnostic imaging study | N/A  |
And the selected study for B is displayed in the Response group with the following details:
| study_badge | study_date   | study_name                             | link                 |
| B           | Mar 11, 2016 | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Open complete report |
And the Measurements table is displayed with the following details:
| measure                                               | A_badge_value           | A_badge_date | B_badge_value           | B_badge_date | diff_badge          |
| Body Weight                                           | 64 kg                   | Feb 20, 2013 | 74.2 kg                 | Dec 21, 2013 | +15.9% +10.2 kg     |
| Prostate specific Ag [Mass/volume] in Pleural fluid   | No value within 30 days | <N/A>        | 17.4 %                  | Jul 19, 2017 | <N/A>               |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | 1.21 ng/mL              | Jan 12, 2013 | 1.632 ng/mL             | Apr 25, 2017 | +34.9% +0.422 ng/mL |
| Prostate specific Ag [Moles/volume] in Plasma         | No value within 30 days | <N/A>        | 27.8 umol/L             | Jun 18, 2017 | <N/A>               |
When the user clicks on the Swap button on the Study selector module
Then the Measurements table is displayed with the following details:
| measure                                               | A_badge_value           | A_badge_date | B_badge_value           | B_badge_date | diff_badge          |
| Body Weight                                           | 74.2 kg                 | Dec 21, 2013 | 64 kg                   | Feb 20, 2013 | +15.9% +10.2 kg     |
| Prostate specific Ag [Mass/volume] in Pleural fluid   | 17.4 %                  | Jul 19, 2017 | No value within 30 days | <N/A>        | <N/A>               |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | 1.632 ng/mL             | Apr 25, 2017 | 1.21 ng/mL              | Jan 12, 2013 | +34.9% +0.422 ng/mL |
| Prostate specific Ag [Moles/volume] in Plasma         | 27.8 umol/L             | Jun 18, 2017 | No value within 30 days | <N/A>        | <N/A>               |
And the selected study for A is displayed in the Response group with the following details:
| study_badge | study_date   | study_name                             | link                 |
| A           | Mar 11, 2016 | CT THORAX ABDOMEN PELVIS WITH CONTRAST | Open complete report |
And the selected study for B is displayed in the Response group with the following details:
| study_badge | study_date   | study_name               | link |
| B           | Jan 24, 2013 | Diagnostic imaging study | N/A  |
When the user opens the imaging study selector for A
Then the open dropdown list displays the imaging studies in the following order:
| name                                   | date         | selected | disabled |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Mar 11, 2016 | yes      | no       |
| Diagnostic imaging study               | Jan 30, 2016 | no       | no       |
| Diagnostic imaging study               | Jan 29, 2016 | no       | no       |
| Diagnostic imaging study               | Jan 28, 2016 | no       | no       |
| Diagnostic imaging study               | Jan 25, 2014 | no       | no       |
| Diagnostic imaging study               | Jan 24, 2013 | no       | yes      |
| Diagnostic imaging study               | Apr 25, 2010 | no       | no       |
When the user opens the imaging study selector for B
Then the open dropdown list displays the imaging studies in the following order:
| name                                   | date         | selected | disabled |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Mar 11, 2016 | no       | yes      |
| Diagnostic imaging study               | Jan 30, 2016 | no       | no       |
| Diagnostic imaging study               | Jan 29, 2016 | no       | no       |
| Diagnostic imaging study               | Jan 28, 2016 | no       | no       |
| Diagnostic imaging study               | Jan 25, 2014 | no       | no       |
| Diagnostic imaging study               | Jan 24, 2013 | yes      | no       |
| Diagnostic imaging study               | Apr 25, 2010 | no       | no       |

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69191,SysRS-69190
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Treatment_Response_View
Scenario: User can reset the selection to default
When the user selects the following imaging study for study A:
| name                     | date         |
| Diagnostic imaging study | Jan 25, 2014 |
And the user selects the following imaging study for study B:
| name                     | date         |
| Diagnostic imaging study | Jan 24, 2013 |
Then the selected study for A is displayed in the Response group with the following details:
| study_badge | study_date   | study_name               | link |
| A           | Jan 25, 2014 | Diagnostic imaging study | N/A  |
And the selected study for B is displayed in the Response group with the following details:
| study_badge | study_date   | study_name               | link |
| B           | Jan 24, 2013 | Diagnostic imaging study | N/A  |
When the user clicks on the Reset button on the Study selector module
Then the selected study for A is displayed in the Response group with the following details:
| study_badge | study_date   | study_name               | link |
| A           | Jan 30, 2016 | Diagnostic imaging study | N/A  |
And the following imaging study is displayed for B:
| name                                   | date         |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Mar 11, 2016 |
When the user opens the imaging study selector for A
Then the open dropdown list displays the imaging studies in the following order:
| name                                   | date         | selected | disabled |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Mar 11, 2016 | no       | yes      |
| Diagnostic imaging study               | Jan 30, 2016 | yes      | no       |
| Diagnostic imaging study               | Jan 29, 2016 | no       | no       |
| Diagnostic imaging study               | Jan 28, 2016 | no       | no       |
| Diagnostic imaging study               | Jan 25, 2014 | no       | no       |
| Diagnostic imaging study               | Jan 24, 2013 | no       | no       |
| Diagnostic imaging study               | Apr 25, 2010 | no       | no       |
When the user opens the imaging study selector for B
Then the open dropdown list displays the imaging studies in the following order:
| name                                   | date         | selected | disabled |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Mar 11, 2016 | yes      | no       |
| Diagnostic imaging study               | Jan 30, 2016 | no       | yes      |
| Diagnostic imaging study               | Jan 29, 2016 | no       | no       |
| Diagnostic imaging study               | Jan 28, 2016 | no       | no       |
| Diagnostic imaging study               | Jan 25, 2014 | no       | no       |
| Diagnostic imaging study               | Jan 24, 2013 | no       | no       |
| Diagnostic imaging study               | Apr 25, 2010 | no       | no       |
Then the Measurements table is displayed with the following details:
| measure                                               | A_badge_value           | A_badge_date | B_badge_value           | B_badge_date | diff_badge |
| Body Weight                                           | No value within 30 days | <N/A>        | 74.2 kg                 | Dec 21, 2013 | <N/A>      |
| Prostate specific Ag [Mass/volume] in Pleural fluid   | No value within 30 days | <N/A>        | 17.4 %                  | Jul 19, 2017 | <N/A>      |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | No value within 30 days | <N/A>        | 1.632 ng/mL             | Apr 25, 2017 | <N/A>      |
| Prostate specific Ag [Moles/volume] in Plasma         | No value within 30 days | <N/A>        | 27.8 umol/L             | Jun 18, 2017 | <N/A>      |
