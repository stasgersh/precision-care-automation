Feature: [SysRS-68500] Export Progressive Summarization as PDF

Narrative: The system shall provide the user the capability to export and download to PDF file type the following data (as content):
           - Brief History (SysRS-69323)
           - What has changed since last oncology encounter (SysRS-68499)
           - Summary of recent reports
           - Summary dashboard – summary view (SysRS-68478)
           see Download file format (SysRS-70530)


@manual
@sanity
@SRS-68500.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68500
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Manual/Summary_View/Progressive_summarization
Scenario: Overview section can be exported separately with more than 11 events in 'what has changed' section
Given [API] the AI data visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_PS, 11e" patient is selected
And the 'What has changed' section has the title: "Since last oncology encounter(Sep 09, 2007)"
And the 'What has changed' section has the following event list table:
| Event                           | Date         | Options                          |
| Radiation oncology Summary note | Oct 07, 2009 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Tumor board Note                | Jul 07, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Progress Notes                  | May 19, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| CT ABDOMEN AND PELVIS 2         | Apr 15, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
And the 'What has changed' section displays the following button "View all 11 events"
When the user clicks the "More" button on the Navigation toolbar
And the user clicks on the "Download" option in the More menu
And the user clicks on the "Overview only" option in the More menu
And I save the file
And I open the saved PDF file
Then the 'Overview' section is present in the pdf file with the following sub-sections:
| section                                      |
| Brief history                                |
| Since last oncology encounter (Sep 09, 2007) |
| Summary of recent reports and notes          |
And the 'What has changed' section has the following event list table:
| Event                                                    | Date         |
| Radiation oncology Summary note                          | Oct 07, 2009 |
| Tumor board Note                                         | Jul 07, 2008 |
| Progress Notes                                           | May 19, 2008 |
| CT ABDOMEN AND PELVIS 2                                  | Apr 15, 2008 |
| Pathology Report                                         | Apr 10, 2008 |
| CT ABDOMEN AND PELVIS                                    | Mar 15, 2008 |
| Pathology Report                                         | Feb 03, 2008 |
| Pathology Report                                         | Feb 02, 2008 |
| Pathology Report                                         | Feb 01, 2008 |
| Pathology Report                                         | Jan 31, 2008 |
| Examination of joint under image intensifier (procedure) | Oct 11, 2007 |


@manual
@sanity
@SRS-68500.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68500
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Manual/Summary_View/Progressive_summarization
Scenario: Long texts in the Overview section are fully displayed in the exported file
Given [API] the AI data visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_PS, LongSummary" patient is selected
And the 'What has changed' section has the title: "Since last oncology encounter (Jan 09, 2000)"
And the 'Summary of recent reports' section has the title: "Summary of recent reports and notes"
And the 'Brief history' section is displayed in PS Overview
And the 'What has changed' section is displayed in PS Overview
And the 'Summary of recent reports' section has a button: "Show more"
And the 'Summary of recent reports' section is not expanded
When the user clicks the "More" button on the Navigation toolbar
And the user clicks on the "Download" option in the More menu
And the user clicks on the "Overview only" option in the More menu
And I save the file
And I open the saved PDF file
Then the 'Overview' section is present in the pdf file with the following sub-sections:
| section                                      |
| Brief history                                |
| Since last oncology encounter (Jan 09, 2000) |
| Summary of recent reports and notes          |
And the 'AI generated' badge is displayed at 'Brief history' section
And the 'Brief history' section is expanded in the pdf file (no hidden AI finding)
And the AI findings are displayed with bullet points in 'Brief history' section
And the [source] link button is not displayed at the end of AI findings in 'Brief history' section
And the 'AI generated' badge is displayed at 'Summary of recent reports' section
And the 'Summary of recent reports' section is expanded in the pdf file (no hidden AI finding)
And the AI findings are displayed with bullet points in 'Summary of recent reports' section
And the [source] link button is not displayed at the end of AI findings in 'Summary of recent reports' section


@manual
@srs
@SRS-68500.03
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68500
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Summary_View
Scenario: Overview section is still visible in the exported file if the whole section was collapsed
Given [API] the AI data visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_PS, 11e" patient is selected
When the user collapses the PS Overview
And the user clicks the "More" button on the Navigation toolbar
And the user clicks on the "Download" option in the More menu
And the user clicks on the "Overview only" option in the More menu
And I save the file
And I open the saved PDF file
Then the 'Overview' section is present in the pdf file with the following sub-sections:
| section                                      |
| Brief history                                |
| Since last oncology encounter (Sep 09, 2007) |
| Summary of recent reports                    |
And the 'AI generated' badge is displayed at 'Brief history' section
And the 'AI generated' badge is displayed at 'Summary of recent reports' section
And the 'Brief history' section contains the following key words:
| key_words                       |
| metastatic colon adenocarcinoma |
| HIPEC                           |
| Xelox                           |
And the 'Summary of recent reports' section contains the following key words:
| key_words                                     |
| metastatic ovarian serous papillary carcinoma |
| pylorus biopsies                              |
| esophageal cyst                               |
And the 'What has changed' section has the following event list table:
| Event                                                    | Date         |
| Radiation oncology Summary note                          | Oct 07, 2009 |
| Tumor board Note                                         | Jul 07, 2008 |
| Progress Notes                                           | May 19, 2008 |
| CT ABDOMEN AND PELVIS 2                                  | Apr 15, 2008 |
| Pathology Report                                         | Apr 10, 2008 |
| CT ABDOMEN AND PELVIS                                    | Mar 15, 2008 |
| Pathology Report                                         | Feb 03, 2008 |
| Pathology Report                                         | Feb 02, 2008 |
| Pathology Report                                         | Feb 01, 2008 |
| Pathology Report                                         | Jan 31, 2008 |
| Examination of joint under image intensifier (procedure) | Oct 11, 2007 |


@manual
@srs
@SRS-68500.04
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68500
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Summary_View
Scenario: Overview is not exported if the AI was turned off (event list shall be still visible)
Given [API] the AI data visualization is switched OFF for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_PS, LongSummary" patient is selected
And the 'What has changed' section has the title: "Since last oncology encounter (Jan 09, 2000)"
And the 'Brief history' section is not displayed in PS Overview
And the 'Summary of recent reports' section is not displayed in PS Overview
And the 'What has changed' section is displayed in PS Overview
And the 'What has changed' section has the following event list table:
| Event                                                    | Date         | Options                          |
| Pathology Report test2                                   | Apr 13, 2000 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report test1                                   | Apr 12, 2000 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report	                                       | Mar 01, 2000 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Examination of joint under image intensifier (procedure) | Feb 04, 2000 | [REPORT_BUTTON] [IMAGING_BUTTON] |
When the user clicks the "More" button on the Navigation toolbar
And the user clicks on the "Download" option in the More menu
And the user clicks on the "Overview only" option in the More menu
And I save the file
And I open the saved PDF file
Then the 'Overview' section is present in the pdf file with the following sub-sections:
| section                                      |
| Brief history                                |
| Since last oncology encounter (Jan 09, 2000) |
| Summary of recent reports and notes          |
And the 'AI generated' badge is not displayed at 'Brief history' section
And the 'AI generated' badge is not displayed at 'Summary of recent reports' section
And the 'Brief history' section has the following content: summary/PS/AI_is_turned_off.txt
And the 'Summary of recent reports' section has the following content: summary/PS/AI_is_turned_off.txt
And the 'What has changed' section has the following event list table:
| Event                                                    | Date         |
| Pathology Report test2                                   | Apr 13, 2000 |
| Pathology Report test1                                   | Apr 12, 2000 |
| Pathology Report	                                       | Mar 01, 2000 |
| Examination of joint under image intensifier (procedure) | Feb 04, 2000 |
| Emergency department note                                | Feb 02, 2000 |
| Emergency department note                                | Feb 02, 2000 |


@manual
@srs
@SRS-68500.05
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68500
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Summary_View
Scenario: Overview and summary dashboard can be extracted as one file
Given [API] the AI data visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_PS, LongSummary" patient is selected
When the user clicks the "More" button on the Navigation toolbar
And the user clicks on the "Download" option in the More menu
And the user clicks on the "Entire summary" option in the More menu
And I save the file
And I open the saved PDF file
Then the 'Overview' section is present in the pdf file with the following sub-sections:
| section                                      |
| Brief history                                |
| Since last oncology encounter (Jan 09, 2000) |
| Summary of recent reports and notes          |
And the following Summary groups are available in the pdf file and have the following icons:
| group_name                  | reference_card_icon                                                             |
| Diagnosis                   | testdata/card_iconography/Card_icon_library.svg: Diagnosis                      |
| Molecular profile           | testdata/card_iconography/Card_icon_library.svg: Molecular profile              |
| Care team                   | testdata/card_iconography/Card_icon_library.svg: Care team                      |
| Imaging                     | testdata/card_iconography/Card_icon_library.svg: Imaging - most recent          |
| Oncology note               | testdata/card_iconography/Card_icon_library.svg: Oncology note - most recent    |
| ER visit                    | testdata/card_iconography/Card_icon_library.svg: ER visit- most recent          |
| MDT report                  | testdata/card_iconography/Card_icon_library.svg: MDT report - most recent       |
| Treatments                  | testdata/card_iconography/Card_icon_library.svg: Treatment                      |
| Labs                        | testdata/card_iconography/Card_icon_library.svg: Labs                           |
