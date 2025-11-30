Feature: Large Attachment

Narrative: PLACEHOLDER

@LargeAttachment
@largeAttachment.01
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68651
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_View
Scenario Outline: System handles bundles with large attachments (>5MB)
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "<patient>" patient is selected
And the user navigated to the "Timeline" view
And the patient timeline is displayed
And the full time range is displayed on the timeline
When the user clicks on the following event point on the "<swimlane>" swimlane:
| event_type | name_on_label | date_on_timeline_axis |
| MARKER     | <event>       | <event_date>          |
And the user clicks on the value of the following sidebar item:
| data_type | title       | value           |
| KEY-VALUE | Full report | Download report |
Then a new browser tab titled "Genomic Report" is opened for the user
And the PDF report content is loaded in the new tab
And the PDF report tab header contains the report title: "Genomic Report"
When the user clicks on the Download button on the PDF report tab
Then a file is downloaded with name "Genomic Report.pdf"
And the downloaded file content is equal to: L3/largeattachments/genomic report.pdf
When the user closes the PDF report tab
And the user clicks on the following sidebar item:
| data_type | title                |
| LINK      | Open complete report |
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title          | creation_date | author    | status   |
| <report_title> | <event_date>  | No author | <status> |
And the full report modal body contains the following data: <report_file>
Examples:
| patient               | swimlane           | event                    | status | event_date   | report_title             | report_file                                    |
| Gen. TEST_LA, Small   | Imaging            | Sample Diagnostic Report | final  | Jul 07, 2003 | Sample Diagnostic Report | L3/largeattachments/TEST_LA_Small_Report.html  |
| Gen. TEST_LA, Medium2 | Pathology and Labs | Surgical pathology study | final  | Apr 08, 1972 | Surgical pathology study | L3/largeattachments/TEST_LA_Medium_Report.html |
| Gen. TEST_LA, Large2  | Imaging            | Sample Diagnostic Report | final  | Oct 09, 1981 | Sample Diagnostic Report | L3/largeattachments/TEST_LA_Large_Report.html  |

@LargeAttachment
@largeAttachment.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68651
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_View
Scenario Outline: User can open complete report of large attachments
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "Gen. Patient, Attachments #YPPmzQ5-" patient is selected
And the user navigated to the "Timeline" view
And the patient timeline is displayed
And the full time range is displayed on the timeline
When the user clicks on the 'Zoom in' button <zoom> times
And the user clicks on the following event point on the "<swimlane>" swimlane:
| event_type | name_on_label | date_on_timeline_axis |
| MARKER     | <event>       | <event_date>          |
And the user clicks on the following sidebar item:
| data_type | title                |
| LINK      | Open complete report |
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title          | creation_date | author    | status   |
| <report_title> | <event_date>  | No author | <status> |
And the full report modal body contains the following data: L3/largeAttachments/<report_file>
Examples:
| tc_name                                                                            | zoom | swimlane           | event                    | status  | event_date   | report_title             | report_file                                           |
| One formatted plain text HTML note                                                 | 1    | Encounters         | Office Visit             | current | Mar 03, 2020 | Progress Notes           | small progress note formatted plain text.html         |
| One small HTML note                                                                | 1    | Encounters         | Office Visit             | current | Feb 07, 2025 | Progress Notes           | small oncology clinical note.html                     |
| One large HTML note                                                                | 1    | Encounters         | Office Visit             | current | Dec 18, 2020 | Progress Notes           | large oncology clinical note.html                     |
| One large inline HTML note                                                         | 1    | Encounters         | Office Visit             | current | Jan 25, 2021 | Progress Notes           | large oncology clinical note.html                     |
| One small formatted plain HTML + large HTML (image) + one large PDF notes          | 6    | Encounters         | Office Visit             | current | Sep 23, 2025 | Progress Notes           | small progress note+large oncology clinical note.html |
| One large HTML + two large PDF notes                                               | 1    | Encounters         | Office Visit             | current | Oct 13, 2024 | Progress Notes           | large oncology clinical note.html                     |
| One small HTML report                                                              | 1    | Imaging            | Sample Diagnostic Report | final   | Dec 03, 2025 | Sample Diagnostic Report | small imaging report.html                             |
| One large HTML report                                                              | 1    | Imaging            | Sample Diagnostic Report | final   | Sep 17, 2024 | Sample Diagnostic Report | large imaging report.html                             |
| One large inline HTML report                                                       | 1    | Imaging            | Sample Diagnostic Report | final   | Dec 17, 2021 | Sample Diagnostic Report | large imaging report.html                             |
| Two large HTML report + one large PDF                                              | 1    | Pathology and Labs | Surgical pathology study | final   | Oct 15, 2022 | Surgical pathology study | 2large pathology report.html                          |
| One plain text report with section results + one large HTML + one large PDF report | 1    | Pathology and Labs | Surgical pathology study | final   | May 17, 2021 | Surgical pathology study | formatted plain text.html                             |

@ToPlaywright
@LargeAttachment
@largeAttachment.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68651
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_View
Scenario: User can download large rtf attachment
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "Gen. Patient, Attachments #YPPmzQ5-" patient is selected
And the user navigated to the "Timeline" view
And the patient timeline is displayed
And the full time range is displayed on the timeline
When the user clicks on the 'Zoom in' button 1 times
And the user clicks on the following event point on the "Encounters" swimlane:
| event_type | name_on_label | date_on_timeline_axis |
| MARKER     | Office Visit  | Sep 19, 2021          |
And the user clicks on the value of the following sidebar item:
| data_type | title       | value           |
| KEY-VALUE | Full report | Download report |
Then a file is downloaded with name "small nursing note (rtf).rtf"
And the downloaded file content is equal to: L3/largeattachments/documents/small nursing note (rtf).rtf

@ToPlaywright
@LargeAttachment
@largeAttachment.04
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68651
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_View
Scenario Outline: User can open and download large attachments PDF reports in embedded viewer
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "Gen. Patient, Attachments #YPPmzQ5-" patient is selected
And the user navigated to the "Timeline" view
And the patient timeline is displayed
And the full time range is displayed on the timeline
When the user clicks on the 'Zoom in' button <zoom> times
And the user clicks on the following event point on the "<swimlane>" swimlane:
| event_type | name_on_label | date_on_timeline_axis |
| MARKER     | <event>       | <event_date>          |
And the user clicks on the value of the following sidebar item:
| data_type | title       | value           |
| KEY-VALUE | Full report | Download report |
Then a new browser tab titled "<report_title>" is opened for the user
And the PDF report content is loaded in the new tab
And the PDF report tab header contains the report title: "<report_title>"
When the user clicks on the Download button on the PDF report tab
Then a file is downloaded with name "<report_file>"
And the downloaded file content is equal to: L3/largeattachments/documents/<report_file>
When the user closes the PDF report tab
Then the patient timeline is displayed
Examples:
| tc_name                                                                            | zoom | swimlane           | event                    | event_date   | report_title                       | report_file                            |
| One large PDF note                                                                 | 8    | Encounters         | Office Visit             | Sep 17, 2025 | Large Oncology Clinical Note (pdf) | large oncology clinical note (pdf).pdf |
| One small formatted plain HTML + large HTML (image) + one large PDF notes          | 6    | Encounters         | Office Visit             | Sep 23, 2025 | Large Oncology Clinical Note (pdf) | large oncology clinical note (pdf).pdf |
| One large PDF report                                                               | 1    | Imaging            | Sample Diagnostic Report | Jul 19, 2024 | Large Imaging Report (pdf)         | large imaging report (pdf).pdf         |
| Two large HTML report + one large PDF                                              | 1    | Pathology and Labs | Surgical pathology study | Oct 15, 2022 | Large Pathology Report (pdf)       | large pathology report (pdf).pdf       |
| One plain text report with section results + one large HTML + one large PDF report | 1    | Pathology and Labs | Surgical pathology study | May 17, 2021 | Large Pathology Report (pdf)       | large pathology report (pdf).pdf       |

@LargeAttachment
@largeAttachment.05
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68651
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_View
Scenario: User can download multiple attached attachments - RTF
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "Gen. Patient, Attachments #YPPmzQ5-" patient is selected
And the user navigated to the "Timeline" view
And the patient timeline is displayed
And the full time range is displayed on the timeline
When the user clicks on the 'Zoom in' button 1 times
And the user clicks on the following event point on the "Encounters" swimlane:
| event_type | name_on_label | date_on_timeline_axis |
| MARKER     | Office Visit  | Mar 08, 2024          |
And the user clicks on the value of the following sidebar item:
| data_type | title       | value           |
| KEY-VALUE | Full report | Download report |
Then files are downloaded with names "small nursing note (rtf).rtf+small nursing note (rtf) (1).rtf"
And the downloaded files content are equal to: L3/largeattachments/documents/small nursing note (rtf).rtf+L3/largeattachments/documents/small nursing note (rtf) (1).rtf

@LargeAttachment
@largeAttachment.06
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68651
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_View
Scenario: User can download multiple attached attachments - PDF
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "Gen. Patient, Attachments #YPPmzQ5-" patient is selected
And the user navigated to the "Timeline" view
And the patient timeline is displayed
And the full time range is displayed on the timeline
When the user clicks on the 'Zoom in' button 4 times
And the user clicks on the following event point on the "Encounters" swimlane:
| event_type | name_on_label | date_on_timeline_axis |
| MARKER     | Office Visit  | Oct 13, 2024          |
And the user clicks on the value of the following sidebar item:
| data_type | title       | value           |
| KEY-VALUE | Full report | Download report |
Then 2 new browser tabs is opened for the user
When the user navigates to the 1st PDF report tab
Then the PDF report tab header contains the report title: "Large Oncology Clinical Note (pdf)"
When the user clicks on the Download button on the PDF report tab
Then files are downloaded with names "large oncology clinical note (pdf).pdf"
And the downloaded files content are equal to: L3/largeattachments/documents/large oncology clinical note (pdf).pdf
When the user navigates to the 2nd PDF report tab
Then the PDF report tab header contains the report title: "Large Oncology Clinical Note (pdf)"
When the user clicks on the Download button on the PDF report tab
Then files are downloaded with names "large oncology clinical note (pdf) (1).pdf"
And the downloaded files content are equal to: L3/largeattachments/documents/large oncology clinical note (pdf) (1).pdf

@LargeAttachment
@largeAttachment.07
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68651
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_View
Scenario Outline: AI summary is generated from large attachments - DocumentReference
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "Gen. Patient, Attachments #YPPmzQ5-" patient is selected
And the user navigated to the "Timeline" view
And the patient timeline is displayed
And the full time range is displayed on the timeline
When the user clicks on the 'Zoom in' button <zoom> times
And the user clicks on the following event point on the "<swimlane>" swimlane:
| event_type | name_on_label | date_on_timeline_axis |
| MARKER     | <event>       | <event_date>          |
Then the following data was displayed on the sidebar:
| data_type | title | value                  |
| CLP_INFO  | <N/A> | [keywords]: <keywords> |
Examples:
| tc_name                                                                            | zoom | swimlane           | event                    | event_date   | keywords                                                                                   |
| One small HTML note                                                                | 3    | Encounters         | Office Visit             | Feb 07, 2025 | Ms. Jane Doe, adenocarcinoma, hepatic metastases, Stage IV                                 |
| One large HTML note                                                                | 8    | Encounters         | Office Visit             | Dec 18, 2020 | Ms. Jane Doe, adenocarcinoma, hepatic metastases, Stage IV                                 |
| One large inline HTML note                                                         | 4    | Encounters         | Office Visit             | Jan 25, 2021 | Ms. Jane Doe, adenocarcinoma, hepatic metastases, Stage IV                                 |
| One large PDF note                                                                 | 6    | Encounters         | Office Visit             | Sep 17, 2025 | Ms. Jane Doe, adenocarcinoma, hepatic metastases, Stage IV                                 |
| One small formatted plain HTML + large HTML (image) + one large PDF notes          | 7    | Encounters         | Office Visit             | Sep 23, 2025 | Ms. Jane Doe, adenocarcinoma, hepatic metastases, Stage IV                                 |
| One large HTML + two large PDF notes                                               | 2    | Encounters         | Office Visit             | Oct 13, 2024 | Ms. Jane Doe, adenocarcinoma, hepatic metastases, Stage IV                                 |
| One small HTML report                                                              | 3    | Imaging            | Sample Diagnostic Report | Dec 03, 2025 | CT Thorax Abdomen Pelvis, pulmonary embolism, clear lungs and no consolidation or effusion |
| One large HTML report                                                              | 8    | Imaging            | Sample Diagnostic Report | Sep 17, 2024 | CT Thorax Abdomen Pelvis, pulmonary embolism, clear lungs and no consolidation or effusion |
| One large inline HTML report                                                       | 4    | Imaging            | Sample Diagnostic Report | Dec 17, 2021 | CT Thorax Abdomen Pelvis, pulmonary embolism, clear lungs and no consolidation or effusion |
| Two large HTML report + one large PDF                                              | 7    | Pathology and Labs | Surgical pathology study | Oct 15, 2022 | Stage 4 high-grade, serous ovarian cancer, infiltration by high-grade serous carcinoma     |
| One plain text report with section results + one large HTML + one large PDF report | 2    | Pathology and Labs | Surgical pathology study | May 17, 2021 | Stage 4 high-grade, serous ovarian carcinoma, infiltration by high-grade serous carcinoma  |