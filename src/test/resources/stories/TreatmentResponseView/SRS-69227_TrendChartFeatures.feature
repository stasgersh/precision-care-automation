Feature: [SysRS-69227] Measurement Table Details

Narrative: The system shall support the following features for Trend charts:
             - Display the Name of the graphs.
             - Display each trend graph line with a differentiator.
             - Differentiate selected graph once user select it.
             - More details on each event/timepoint: Provide the user more details if requested by the user:
               - Therapy details:
                 - Start date
                 - Most recent Therapy - if relevant
                 - End date - if relevant
                 - Pulse counts out of total number of pulses - if relevant
               - Parameter value and date (PSA and Weight)


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application


@sanity
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69227
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Manual/Functional_Service/Treatment_Response_view
Scenario Outline: Y axis values reflects the highlighted line's data values
Given the "OncoTestPatient, Sigrid" patient is selected
And the user navigated to the "Treatment and response" view
And the user selected the following custom date range on the chart slider:
| start        | end          |
| Mar 25, 2010 | Feb 02, 2021 |
And the following lines are present on the chart:
| name                                                  | unit   |
| Body Weight                                           | kg     |
| Prostate specific Ag [Mass/volume] in Pleural fluid   | %      |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | ng/mL  |
| Prostate specific Ag [Moles/volume] in Plasma         | umol/L |
And there is no values on the Y-axis
And the X-axis (date) is displaying values in the following range:
| start_date   | end_date     |
| Mar 25, 2010 | Feb 02, 2021 |
When the user hovers on the "<graph_name>" legend on the right side
Then the Y-axis is displaying values in the following range:
| min_value   | max_value   |
| <min_value> | <max_value> |
And the X-axis (date) is displaying values in the following range:
| start_date   | end_date     |
| Mar 25, 2010 | Feb 02, 2021 |
When the user hovers on the "<graph_name>" line on the chart area
Then the Y-axis is displaying values in the following range:
| min_value   | max_value   |
| <min_value> | <max_value> |
And the X-axis (date) is displaying values in the following range:
| start_date   | end_date     |
| Mar 25, 2010 | Feb 02, 2021 |
Examples:
| graph_name                                            | min_value | max_value |
| Body Weight                                           | 0         | 80        |
| Prostate specific Ag [Mass/volume] in Pleural fluid   | 0         | 20        |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | 0         | 2.0       |
| Prostate specific Ag [Moles/volume] in Plasma         | 0         | 35        |

@sanity
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69227
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Manual/Functional_Service/Treatment_Response_view
Scenario Outline: Colorings are unique and not hovered trend lines, vertical markers and legends and are displayed as gray
Given the "OncoTestPatient, Sigrid" patient is selected
And the user navigated to the "Treatment and response" view
And the user selected the following custom date range on the chart slider:
| start        | end          |
| Mar 25, 2010 | Feb 02, 2021 |
And the following lines are present on the chart:
| name                                                  | unit   |
| Body Weight                                           | kg     |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | ng/mL  |
| Prostate specific Ag [Moles/volume] in Plasma         | umol/L |
| Prostate specific Ag [Mass/volume] in Pleural fluid   | %      |
And each trend graph has unique coloring
When the user hovers on the "<graph_name>" legend on the right side
Then the "<graph_name>" line is displayed colored on the chart area
And all other lines are displayed gray on the chart area
And the "<graph_name>" legend is displayed colored on the legend area
And all other legends are displayed gray on the right side
Examples:
| graph_name                                            |
| Body Weight                                           |
| Prostate specific Ag [Mass/volume] in Serum or Plasma |
| Prostate specific Ag [Moles/volume] in Plasma         |
| Prostate specific Ag [Mass/volume] in Pleural fluid   |

@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69227
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Treatment_Response_view
Scenario: Exported Response view with Warning banner on top of the PDF file(vertical markers and legends and are displayed as gray)
Given [API] the Treatment Response settings were reset for "OncoTestPatient, Sigrid" by the [DEFAULT-TEST-USER] user
And the "OncoTestPatient, Sigrid" patient is selected
And the "Artificial Intelligence (AI)" turned on in user settings
And the user navigated to the "Treatment and response" view
And the user selected the following custom date range on the chart slider:
| start        | end          |
| Mar 25, 2010 | Feb 02, 2021 |
When I click on the "Download" button in the bottom of the page in the Response view
And I save the file with black and white coloring
Then the saved pdf file contains the download date and time
And the saved file contains the following header: GE HealthCare | CareIntellect for Oncology
| And the patient status banner displays the date and time in the following format: dd MMM, yyyy | hh:mm GMT+x    (e.g. 23 Aug, 2023 | 12:42 GMT+2) |
And the following lines are present on the chart in gray color since color information is lost in printing:
| name                                                  | unit   | color |
| Body Weight                                           | kg     | gray  |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | ng/mL  | gray  |
| Prostate specific Ag [Moles/volume] in Plasma         | umol/L | gray  |
| Prostate specific Ag [Mass/volume] in Pleural fluid   | %      | gray  |
And the following legends are displayed on the chart in gray color since color information is lost in printing:
| legend_name                                           | color |
| Body Weight                                           | gray  |
| Prostate specific Ag [Mass/volume] in Serum or Plasma | gray  |
| Prostate specific Ag [Moles/volume] in Plasma         | gray  |
| Prostate specific Ag [Mass/volume] in Pleural fluid   | gray  |
And the pdf exported file contains warning banner on top of the page:"Do not make clinical decisions based on this document alone Information may be diminished or degraded in PDF or printed."
And the pdf exported file contains AI generated data message:"The data highlighted in blue or marked on this page is a summary produced by generative artificial intelligence and is intended to supplement the existing patient data, may be incomplete or incorrect, and is not intended to replace the provider's judgment or responsibility to review the source original clinical information."

@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69227
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Treatment_Response_View
Scenario Outline: User can see the complete title of a truncated label in a popover when hovered
Given the "OncoTestPatient, Sigrid" patient is selected
And the "Status and response" button was clicked
And the Treatment and response view is loaded
When the user hovers on the "<full_text>" on the side legend area
Then a popover is displayed with the following full text: "<full_text>"
Examples:
| graph_name     | full_text                                                     |
| Prostate sp... | Prostate specific Ag [Mass/volume] in Pleural fluid (%)       |
| Prostate sp... | Prostate specific Ag [Mass/volume] in Serum or Plasma (ng/mL) |
| Prostate sp... | Prostate specific Ag [Moles/volume] in Plasma (umol/L)        |


@sanity
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69227
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Manual/Functional_Service/Treatment_Response_view
Scenario: Badges of the selected studies are displayed on the chart with vertical markers
Given the "OncoTestPatient, Rory188" patient is selected
And the Treatment and response view is loaded
When the user selects the following imaging study for study A:
| name            | date         |
| Upper arm X-ray | Jul 13, 1962 |
And the user selects the following imaging study for study B:
| name        | date         |
| Chest X-ray | Oct 08, 1974 |
And the user selects the following custom date range on the chart slider:
| start        | end     |
| Feb 24, 1958 | <TODAY> |
Then the following badges are displayed with vertical markers on the multiline chart:
| badge_name       | reference_badge_icon                                     |
| A                | N/A                                                      |
| B                | N/A                                                      |
| Systemic therapy | testdata/labels/Label_library.svg: Systemic Tx (compact) |
And the following badge is not displayed on the multiline chart:
| badge_name        | reference_badge_icon                                   |
| Radiation therapy | testdata/labels/Label_library.svg: Radiation (compact) |
When the user hovers on the "A" badge on the multiline chart
Then a tooltip is displayed over the badge with date "Jul 13, 1962" as:
| label_name | reference_badge_icon                     |
| A          | N/A                                      |
| X-ray      | testdata/labels/Label_library.svg: X-ray |
When the user hovers on the "B" badge on the multiline chart
Then a tooltip is displayed over the badge with date "Oct 08, 1974" as:
| label_name | reference_badge_icon                     |
| B          | N/A                                      |
| X-ray      | testdata/labels/Label_library.svg: X-ray |
When the user hovers on the "Systemic Therapy" badge on the multiline chart
Then a tooltip is displayed over the badge with date "Sep 21, 2016" as:
| label_name  | reference_badge_icon                           | text_on_tooltip |
| Systemic Tx | testdata/labels/Label_library.svg: Systemic Tx | N/A             |
And the "Systemic Therapy" badge became colored as the reference icon: "testdata/labels/Label_library.svg: Systemic Tx (compact)"

@sanity
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69227
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Manual/Functional_Service/Treatment_Response_view
Scenario: Badges of radiation and systematic therapy are displayed on the chart with vertical markers
Given the "OncoTestPatient, Walter473" patient is selected
And the Treatment and response view is loaded
And the "Systemic therapy" treatment card contains the following data:
| data_type  | data                                                             |
| CARD_TITLE | Systemic therapy                                                 |
| DATE       | Nov 06, 2017                                                     |
| TABLE      | [source]: summary/table_on_systemic_therapy_card_Walter473.table |
And the "Radiation therapy" treatment card contains the following data:
| data_type  | data                                                                                 |
| CARD_TITLE | Radiation therapy                                                                    |
| DATE       | Apr 04, 2018                                                                         |
| Text       | Procedure - most recent                                                              |
| KEY_VALUE  | [key]: Combined chemotherapy and radiation therapy (procedure) [value]: Apr 04, 2018 |
| TEXT       | Start of therapy                                                                     |
| KEY_VALUE  | [key]: Combined chemotherapy and radiation therapy (procedure) [value]: Nov 06, 2017 |
And the user has set the full date range on the multiline chart
And vertical markers are intersecting the "Body Weight (kg)" and "Prostate specific Ag [Mass/volume] in Serum or Plasma (ng/mL)" chart lines starting from the following compact badges:
| badge_name       | reference_badge_icon                                     |
| Systemic therapy | testdata/labels/Label_library.svg: Systemic Tx (compact) |
| Radiation        | testdata/labels/Label_library.svg: Radiation (compact)   |
| Surgery          | testdata/labels/Label_library.svg: Surgery (compact)     |
When the user hovers on the "Systemic Therapy" badge on the multiline chart
Then a tooltip is displayed over the badge with date "Nov 06, 2017" as:
| label_name  | reference_badge_icon                           | text_on_tooltip |
| Systemic Tx | testdata/labels/Label_library.svg: Systemic Tx | N/A             |
And the "Systemic Therapy" badge became colored as the reference icon: "testdata/labels/Label_library.svg: Systemic Tx (compact)"
When the user hovers on the "Radiation" badge on the multiline chart
Then a tooltip is displayed over the badge with date as:
| label_name | reference_badge_icon                         | start_of_therapy               | most_recent_therapy               |
| Radiation  | testdata/labels/Label_library.svg: Radiation | Start of therapy: Nov 06, 2017 | Most recent therapy: Apr 04, 2018 |
And the "Radiation" badge became colored as the reference icon: "testdata/labels/Label_library.svg: Radiation (compact)"
When the user hovers on the "Surgery" badge on the multiline chart
Then a tooltip is displayed over the badge with date "Feb 11, 2017" as:
| label_name | reference_badge_icon                       | text_on_tooltip |
| Surgery    | testdata/labels/Label_library.svg: Surgery | N/A             |
And the "Surgery" badge became colored as the reference icon: "testdata/labels/Label_library.svg: Surgery (compact)"

@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69227
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Treatment_Response_View
Scenario: Badges of radiation and systematic therapy are NOT displayed on the chart if no data is available for the patient
Given the "OncoTestPatient, Sigrid" patient is selected
And the patient summary view is loaded
When the user clicks "Patient status" button
Then the Treatment and response view is loaded
And the "Systemic therapy" treatment card contains the following data:
| data_type   | data                      |
| EMPTY_STATE | No systemic therapy found |
And the "Radiation therapy" treatment card contains the following data:
| data_type   | data                       |
| EMPTY_STATE | No radiation therapy found |
When the user sets the full date range on the multiline chart
Then the following badges are NOT displayed on the multiline chart:
| badge_name       | reference_badge_icon                                     |
| Systemic therapy | testdata/labels/Label_library.svg: Systemic Tx (compact) |
| Radiation        | testdata/labels/Label_library.svg: Radiation (compact)   |
