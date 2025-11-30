Feature: [SysRS-70530] Summary View Download

Narrative: The system shall provide the following data once Download is triggered (for each downloaded PDF file):
             - File Header:
               - Date and time (when the download action is executed)
               - GE HealthCare LOGO
               - Software name
               - Patient Banner details as described in SysRS-68429
             - Content:
               - Summary dashboard – summary view (SysRS-68478) and Progressive summarization - Export and download (SysRS-68500)
               - Response Tracking – Download to PDF ** (SysRS-68476)
               - Content's Rules:
                 - General:
                   - Only show AI notification when AI is turned on
                   - No purple links in the PDF - all text is dark grey (grey-1200).
                   - Show complete expanded content on export (Show more)
                   - No truncation
                 - Patient banner
                   - Patient name appears as key-value pair (no caret, no icon by patient name).
                   - Key-value pairs are always in one line (no line break)
                 - Charts
                   - In case chart overflows screen, it should go to the next page.
                 - Tables:
                   - When table is cut on the bottom of the page show table header and footer on both pages
                 - Notification section
                   - Last synced notification card is included. Date, time (04:20 PM), time-zone (UTC).
             - File Footer:
               - Exporter name – User full name (first and last).
               - Patient Name
               - PID
               - Local host
               - Current Page Number (X) out of Total number of pages (Y): “X/Y”. (page size shall be by default A4)


@SRS-38.01
@sanity
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70530
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME3_-_Manual/Functional_Service/Summary_View
Scenario: Summary view is saved into a pdf file (patient last sync less than 1 hour ago, CLP turned off, trend chart on Labs card)
Given the test patient "patients/OncoTestPatient_Juno.json" was uploaded to PDS less than 30 mins ago
And I logged in to OncoCare application
And "AI summarization" is turned OFF in user settings
And I selected the "OncoTestPatient, Juno" patient
And the patient banner is collapsed
And the patient status banner displays the information that the patient was synced with the hospital systems less than 59 mins ago (e.g. "2 min ago")
When I click the "More" button on the Navigation toolbar
And I click on the "Download" option in the More menu
And I select the "Entire summary" option
And I save the file
Then the saved pdf file contains the download date and time
And the saved file contains the following header: GE HealthCare | CareIntellect for Oncology
And the patient status banner displays the date and time in the following format: dd MMM, yyyy | hh:mm GMT+x    (e.g. 23 Aug, 2023 | 12:42 GMT+2)
And the following patient banner data is displayed above the summary cards in the pdf file:
| parameter_in_patient_banner |
| Patient name                |
| MRN                         |
| DOB                         |
| Gender                      |
| Weight                      |
| ECOG                        |
| Diagnosis                   |
| Histology type              |
| Gleason score               |
| Stage - initial             |
| Most Recent Tx              |
| BMI                         |
| BSA                         |
| PSA                         |
| Molecular profile           |
| ER                          |
| HER2                        |
And the following info card is NOT available in the pdf file:
| title                | text                                                                                          |
| Patient data summary | This page displays key patient information but may not include all available medical records. |
And the following AI info is not available in the pdf file: "The data highlighted in blue or marked on this page is a summary produced by generative artificial intelligence and is intended to supplement the existing patient data, may be incomplete or incorrect, and is not intended to replace the provider's judgment or responsibility to review the source original clinical information."
And the following Summary groups are available in the pdf file and have the following icons:
| group_name        | reference_card_icon                                                          |
| Diagnosis         | testdata/card_iconography/Card_icon_library.svg: Diagnosis                   |
| Molecular profile | testdata/card_iconography/Card_icon_library.svg: Molecular profile           |
| Care team         | testdata/card_iconography/Card_icon_library.svg: Care team                   |
| Imaging           | testdata/card_iconography/Card_icon_library.svg: Imaging - most recent       |
| Oncology note     | testdata/card_iconography/Card_icon_library.svg: Oncology note - most recent |
| ER visit          | testdata/card_iconography/Card_icon_library.svg: ER visit- most recent       |
| MDT report        | testdata/card_iconography/Card_icon_library.svg: MDT report - most recent    |
| Treatments        | testdata/card_iconography/Card_icon_library.svg: Treatment                   |
| Labs              | testdata/card_iconography/Card_icon_library.svg: Labs                        |
And the following Summary groups are NOT available in the pdf file:
| group_name      | reference_card_icon                                                     |
| Comments        | testdata/card_iconography/Card_icon_library.svg: Comments - most recent |
| Medical history | testdata/card_iconography/Card_icon_library.svg: Medical history        |
And a trend chart is visible on the "PSA" card in the "Labs" group
And the pdf file contains the exporter name under the summary cards
And the pdf file contains the patient's name under the summary cards
And the pdf file contains the full URL of the downloaded summary page in the footer
And the pdf file contains exported date in the bottom of the page

@SRS-38.02
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70530
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/VPR_List_-_Manual/Functional_Service/Summary_View
Scenario: Summary view is saved into a pdf file (patient last sync more than 1 hour ago, CLP turned off)
Given the test patient "patients/OncoTestPatient_Freya.json" was uploaded to PDS more than 1 hour ago
And I logged in to OncoCare application
And "AI summarization" is turned OFF in user settings
And I selected the "OncoTestPatient, Freya" patient
And the patient banner is expanded
And the patient status banner displays the information that the patient was synced with the hospital systems more than 1 hour ago (e.g. 23 Aug, 2023 | 13:55 GMT+2)
When I click the "More" button on the Navigation toolbar
And I click on the "Download" option in the More menu
And I select the "Entire summary" option
And I save the file
Then the saved pdf file contains the download date and time
And the saved file contains the following header: GE HealthCare | CareIntellect for Oncology
| And the patient status banner displays the date and time in the following format: dd MMM, yyyy | hh:mm GMT+x    (e.g. 23 Aug, 2023 | 12:42 GMT+2) |
And the following patient banner data is displayed above the summary cards in the pdf file:
| parameter_in_patient_banner |
| Patient name                |
| MRN                         |
| DOB                         |
| Gender                      |
| ECOG                        |
| Diagnosis                   |
| Histology type              |
| Stage - initial             |
| Most Recent Tx              |
| Weight                      |
| BMI                         |
| Molecular profile           |

@SRS-38.03
@manual
@integration
@CLP
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70530,SysRS-68596
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Summary_View
Scenario: Summary view is saved into a pdf file with CLP data if CLP is turned on
Given I logged in to OncoCare application
And "AI summarization" is turned ON in user settings
And I selected the "OncoTestPatient, Juno" patient
When I click the "More" button on the Navigation toolbar
And I click on the "Download" option in the More menu
And I select the "Entire summary" option
And I select the colorized background option (Background graphics checkbox)
And I save the file
Then the following info card is NOT available in the pdf file:
| title                | text                                                                                          |
| Patient data summary | This page displays key patient information but may not include all available medical records. |
And the following AI info is available in the pdf file: "The data highlighted in blue or marked on this page is a summary produced by generative artificial intelligence and is intended to supplement the existing patient data, may be incomplete or incorrect, and is not intended to replace the provider's judgment or responsibility to review the source original clinical information."
And the 1st card in "Imaging" group in the pdf file equals the following data:
| data_type   | data                                                     |
| CARD_TITLE  | Imaging - most recent                                    |
| NORMAL_TEXT | Examination of joint under image intensifier (procedure) |
| CLP_DATA    | <Highlighted AI data>                                    |
| KEY_VALUE   | [key]: Date         [value]: Sep 09, 2022                |
And the CLP data on "Imaging" card is highlighted
When I select the "OncoTestPatient, Franklin" patient
And I click the "More" button on the Navigation toolbar
And I click on the "Download" option in the More menu
And I select the "Entire summary" option
And I select the colorized background option (Background graphics checkbox)
And I save the file
Then the "Stage" card in "Diagnosis" group in the pdf file contains the following table:
| Category | Value   | Date         |
| Stage    | Stage 4 | Mar 23, 2004 |
And the value "Stage 4" in "Value" column is highlighted
And the patient banner section in the pdf file includes the following data:
| data_type | name            | value   |
| CLP_INFO  | Stage - initial | Stage 4 |
And the "Stage - initial Stage 4" is highlighted in the patient banner
When I go to the "Settings" page
And I turn OFF the "AI summarization"
And I go back to the selected patient
And I click the "More" button on the Navigation toolbar
And I click on the "Download" option in the More menu
And I select the "Entire summary" option
And I select the colorized background option (Background graphics checkbox)
And I save the file
Then the following notification card is NOT available in the pdf file:
| title                | text                                                                                          |
| Patient data summary | This page displays key patient information but may not include all available medical records. |
And the following AI info is not available in the pdf file: "The data highlighted in blue or marked on this page is a summary produced by generative artificial intelligence and is intended to supplement the existing patient data, may be incomplete or incorrect, and is not intended to replace the provider's judgment or responsibility to review the source original clinical information."
And the following text is NOT available in the pdf file:
| title                             | text       |
| This page is best viewed with AI. | Turn on AI |
And the 2nd card in the "Diagnosis" group in the pdf file displays the message: "No stage found"
And the patient banner section in the pdf file includes the following data:
| data_type | name            | value                    |
| KEY-VALUE | Stage - initial | No Stage - initial found |
When I select the "OncoTestPatient, Juno" patient
And I click the "More" button on the Navigation toolbar
And I click on the "Download" option in the More menu
And I select the "Entire summary" option
And I select the colorized background option (Background graphics checkbox)
And I save the file
Then the 1st card in "Imaging" group in the pdf file equals the following data:
| data_type   | data                                                     |
| CARD_TITLE  | Imaging                                                  |
| NORMAL_TEXT | Examination of joint under image intensifier (procedure) |
| KEY_VALUE   | [key]: Date      [value]: Sep 09, 2022                   |

@SRS-38.04
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70530
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/VPR_List_-_Manual/Functional_Service/Summary_View
Scenario: Summary view is saved into a pdf file with "Do not make clinical decisions" notification
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
When the user clicks the "More" button on the Navigation toolbar
And the user clicks on the "Download" option in the More menu
And the user clicks on the "Entire summary" option in the More menu
And I save the file
Then the saved pdf file contains the following notification:
| icon | notification_title                                          | description                                                  |
| Info | Do not make clinical decisions based on this document alone | Information may be diminished or degraded in PDF or printed. |

@SPR-4794
@SRS-38.04
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70530,SysRS-68476
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Treatment_Response_View
Scenario: Response is saved into a PDF file (trend chart: custom date, CLP off )
Given [API] the CLP visualization is switched OFF for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Patient status" button was clicked
And the Treatment and response view is loaded
When the user selects the following imaging study for study A:
| name                     | date         |
| Diagnostic imaging study | Aug 19, 2022 |
And the user selects the following imaging study for study B:
| name                                                     | date         |
| Examination of joint under image intensifier (procedure) | Sep 09, 2022 |
And the user selects the following custom date range on the chart slider:
| start        | end          |
| May 04, 2005 | Mar 14, 2019 |
And the user clicks on the "Download" button
And the user saves the PDF file
Then the saved PDF file contains the download date and time
And the saved file contains the following header: GE HealthCare | CareIntellect for Oncology
| And the patient status banner displays the date and time in the following format: dd MMM, yyyy | hh:mm GMT+x | (e.g. 23 Aug, 2023 | 12:42 GMT+2) |
And the following patient banner data is displayed above the summary cards in the PDF file:
| parameter_in_patient_banner |
| Patient name                |
| MRN                         |
| DOB                         |
| Gender                      |
| Weight                      |
| ECOG                        |
| Diagnosis                   |
| Histology type              |
| Gleason score               |
| Stage - initial             |
| Most Recent Tx              |
| BMI                         |
| BSA                         |
| PSA                         |
| Molecular profile           |
| ER                          |
| HER2                        |
And the following sections are displayed in the PDF file:
| section   | sub_section                 | section_date | section_content                                     |
| Treatment | Systemic therapy            | <N/A>        | No systemic therapy found                           |
| Treatment | Radiation therapy           | Aug 15, 2018 | [source]: responseView/radiation_therapy_Juno.table |
| Treatment | Oncology note - most recent | May 31, 2017 | Progress Notes                                      |
And the following AI info is not available in the PDF file: "The data highlighted in blue or marked on this page is a summary produced by generative artificial intelligence and is intended to supplement the existing patient data, may be incomplete or incorrect, and is not intended to replace the provider's judgment or responsibility to review the source original clinical information."
And the Imaging selector section is displayed in the PDF file as:
| title                                       | studyA_title             | studyA_date  | studyB_title                                             | studyB_date  |
| A and B time points selected for comparison | Diagnostic imaging study | Aug 19, 2022 | Examination of joint under image intensifier (procedure) | Sep 09, 2022 |
And the Multiline chart is displayed in the PDF file as:
| top_legend                                           | selected_date_range               | right_legend                 |
| Body Weight (kg) · Prostate specific antigen (ng/mL) | From May 04, 2005 to Mar 14, 2019 | Body Weight ; Prostate sp... |
And the Multiline chart's chart area is displayed in the PDF file
And the Chart time buttons are NOT displayed in the PDF file
And the Multiline chart displays the "Prostate specific antigen (ng/mL)" trend line with red in the PDF file
And the Multiline chart displays the following elements in the PDF file:
| element         | description        |
| surgery badge   | with vertical line |
| surgery badge   | with vertical line |
| radiation badge | with vertical line |
And the Study details section is displayed in the PDF file as:
| studyA_title             | studyA_date  | studyB_title                                             | studyB_date  |
| Diagnostic imaging study | Aug 19, 2022 | Examination of joint under image intensifier (procedure) | Sep 09, 2022 |
And the Measures table is displayed in the PDF file as:
| measures                  | studyA_value            | studyA_date | studyA_badge | studyB_value            | studyB_date  | studyB_badge |
| Body Weight               | No value within 30 days | <N/A>       | <N/A>        | 47.3 kg                 | Jan 23, 2013 | <N/A>        |
| Prostate specific antigen | No value within 30 days | <N/A>       | <N/A>        | 2.3 ng/mL               | Jul 19, 2017 | <N/A>        |
And the following information text is displayed in the PDF file under the Measures table: "Tables contain values closest to the date of points A and B"
And the "Download" button is NOT displayed in the PDF file
And the "Open full report" button is NOT displayed in the PDF file
And the "Open images" button is NOT displayed in the PDF file
And the Exporter name, patient name, PI, date information is displayed at the end of the file

@SPR-4794
@SRS-38.05
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70530,SysRS-68476
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Treatment_Response_View
Scenario: Response is saved into a PDF file (trend chart: custom date, CLP on )
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "Tart, Emilia" patient is selected
And the "Patient status" button was clicked
And the Treatment and response view is loaded
When the user selects the following imaging study for study A:
| name                   | date         |
| CHEST CT WITH CONTRAST | May 03, 2015 |
And the user selects the following imaging study for study B:
| name                                        | date         |
| CT Chest, Abdomen, and Pelvis with Contrast | Mar 17, 2016 |
And the user selects the following custom date range on the chart slider:
| start        | end          |
| Apr 21, 2015 | Aug 12, 2016 |
And the user clicks on the "Download" button
And the user saves the PDF file
And the user opens the saved PDF file
Then the saved PDF file contains the download date and time
And the saved file contains the following header: GE HealthCare | CareIntellect for Oncology
And the patient status banner displays the date and time in the following format: dd MMM, yyyy | hh:mm GMT+x | (e.g. 23 Aug, 2023 | 12:42 GMT+2)
And the following patient banner data is displayed above the summary cards in the PDF file:
| parameter_in_patient_banner | clp_highlighted |
| Patient name                | no              |
| MRN                         | no              |
| DOB                         | no              |
| Gender                      | no              |
| Weight                      | no              |
| ECOG                        | no              |
| Diagnosis                   | no              |
| Histology type              | yes             |
| Stage - initial             | no              |
| Most Recent Tx              | no              |
| BMI                         | no              |
| Molecular profile           | no              |
| ER                          | no              |
| PR                          | no              |
| HER2                        | no              |
| CA 15-3                     | no              |
| CA 125                      | no              |
| CA 27-29                    | no              |
| Menopausal status           | yes             |
And the following sections are displayed in the PDF file:
| section   | sub_section       | section_date | section_content                                      |
| Treatment | Systemic therapy  | Apr 21, 2016 | [source]: responseView/systemic_therapy_Emilia.table |
| Treatment | Radiation therapy | <N/A>        | No radiation therapy found                           |
And the 'Oncology note - most recent' section contains the following key words:
| key_words                                 |
| Stage IV invasive mammary carcinoma (IMC) |
| HER2 positive                             |
| musculoskeletal stiffness                 |
And the following AI info is available in the PDF file: "The data highlighted in blue or marked on this page is a summary produced by generative artificial intelligence and is intended to supplement the existing patient data, may be incomplete or incorrect, and is not intended to replace the provider's judgment or responsibility to review the source original clinical information."
And the Imaging selector section is displayed in the PDF file as:
| title                                       | studyA_title           | studyA_date  | studyB_title                                | studyB_date  |
| A and B time points selected for comparison | CHEST CT WITH CONTRAST | May 03, 2015 | CT Chest, Abdomen, and Pelvis with Contrast | Mar 17, 2016 |
And the Multiline chart is displayed in the PDF file as:
| top_legend                                                                                                                                                                                                                                                                                                        | selected_date_range               | right_legend                                                                         |
| Body weight(kg) · Cancer Ag 125 [Units/volume] in Serum or Plasma(U/mL) · Cancer Ag 15-3 [Units/volume] in Serum or Plasma(U/mL) · Cancer Ag 19-9 [Units/volume] in Serum or Plasma(U/mL) · Cancer Ag 27-29 [Units/volume] in Serum or Plasma(U/mL) · Carcinoembryonic Ag [Mass/volume] in Serum or Plasma(ng/mL) | From Apr 21, 2015 to Aug 12, 2016 | Body Weight ; Cancer Ag 1… ; Cancer Ag 1… ; Cancer Ag 2… ; Cancer Ag 1… ; Carcinoem… |
And the Multiline chart's chart area is displayed in the PDF file
And the Chart time buttons are NOT displayed in the PDF file
And the Multiline chart displays the following elements in the PDF file:
| element                | description        |
| study A badge          | with vertical line |
| study B badge          | with vertical line |
| systemic therapy badge | with vertical line |
And the Study details section is displayed in the PDF file as:
| studyA_title           | studyA_date  | studyA_badge | studyB_title                                | studyB_date  | studyB_badge |
| CHEST CT WITH CONTRAST | May 03, 2015 | AI generated | CT Chest, Abdomen, and Pelvis with Contrast | Mar 17, 2016 | AI generated |
And the Study A content contains the following key words:
| key_words                    |
| secondary malignant neoplasm |
| pulmonary nodules            |
| axillary lymph nodes         |
And the Study B content contains the following key words:
| key_words                |
| metastatic breast cancer |
| hepatic metastases       |
| lumbar spine             |
And the Measures table is displayed in the PDF file as:
| measures                                             | studyA_value            | studyA_date  | studyB_value            | studyB_date  | diff_badge        |
| Body Weight                                          | No value within 30 days | <N/A>        | 79.8 kg                 | Mar 21. 2016 | <N/A>             |
| Cancer Ag 125 [Units/volume] in Serum or Plasma      | 15.5 U/mL               | Apr 21, 2015 | 10 U/mL                 | Dec 29, 2015 | -35.5% +-5.5 U/mL |
| Cancer Ag 15-3 [Units/volume] in Serum or Plasma     | 39 U/mL                 | Apr 21, 2015 | 31 U/mL                 | Dec 29, 2015 | -20.5% -8 U/mL    |
| Cancer Ag 19-9 [Units/volume] in Serum or Plasma     | 17 U/mL                 | Apr 21, 2015 | 15 U/mL                 | Dec 29, 2015 | -11.8% -2 U/mL    |
| Cancer Ag 27-29 [Units/volume] in Serum or Plasma    | 42.5 U/mL               | Apr 21, 2015 | 30.4 U/mL               | Dec 29, 2015 | -28.5% -12.1 U/mL |
| Carcinoembryonic Ag [Mass/volume] in Serum or Plasma | 5.8 ng/mL               | Apr 21, 2015 | 3.3 ng/mL               | Dec 29, 2015 | -43.1% -2.5 ng/mL |
And the following information text is displayed in the PDF file under the Measures table: "Tables contain values closest to the date of points A and B"
And the "Download" button is NOT displayed in the PDF file
And the "Open full report" button is NOT displayed in the PDF file
And the "Open images" button is NOT displayed in the PDF file
And the Exporter name, patient name, PI, date information is displayed at the end of the file
