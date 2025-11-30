@safety
Feature: [SysRS-68551] Full Fidelity Details (L3)

Narrative: The system shall provide access to the original report to see full fidelity details.

Remark: Level 3 info (L3) are full reports or DICOM studies made available through links along with the L2 information, alternatively, if the full report is available either in a binary or a text encoded format in the Data Fabric Storage Service, the application can provide L3 within its context.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the patient page is displayed

@sanity
@edge
@SRS-43.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68390,SysRS-68551
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Full_Fidelity_Info
Scenario: Full report is displayed in a new modal (value comes from 'presentedForm' parameter as plain text)
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Pathology and Labs" swimlane:
| event_type | name_on_label     |
| MARKER     | DiagReport Test 0 |
And the sidebar is displayed
And the sidebar content contains the following data:
| data_type | title                | value                              |
| KEY-VALUE | Conclusion           | To test content type: "text/plain" |
| KEY-VALUE | Status               | final                              |
| LINK      | Open complete report | <N/A>                              |
When the user clicks on the following sidebar item:
| data_type | title                |
| LINK      | Open complete report |
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title             | creation_date | author    | status |
| DiagReport Test 0 | May 11, 2007  | No author | final  |
And the full report modal body contains the following data: L3/OncoTestPatient_Juno_Pathology_DiagReportTest0.txt

@SRS-43.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68551
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Full_Fidelity_Info
Scenario: Full report is displayed in a new modal (value comes from 'presentedForm' parameter as plain text and charset is provided in 'contentType')
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Pathology and Labs" swimlane:
| event_type | name_on_label          |
| MARKER     | Test Diagnostic Report |
And the sidebar is displayed
And the sidebar content contains the following data:
| data_type | title                | value                                             |
| KEY-VALUE | Conclusion           | To test content type: "text/plain; charset=utf-8" |
| KEY-VALUE | Status               | <ANY_VALUE>                                       |
| LINK      | Open complete report | <N/A>                                             |
When the user clicks on the following sidebar item:
| data_type | title                |
| LINK      | Open complete report |
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title                  | creation_date | author    | status |
| Test Diagnostic Report | May 11, 2009  | No author | final  |
And the full report modal body contains the following data: L3/OncoTestPatient_Juno_Pathology_TestDiagnosticReport.txt

@SRS-43.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68551
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Full_Fidelity_Info
Scenario: Full report is displayed in a new modal (value comes from 'content.attachment' parameter as plain text)
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Encounters" swimlane:
| event_type | name_on_label    |
| MARKER     | Tumor board Note |
And the sidebar is displayed
And the sidebar content contains the following data:
| data_type | title                | value                         |
| KEY-VALUE | Status               | final                         |
| KEY-VALUE | Author               | Physician Family Medicine, MD |
| LINK      | Open complete report | <N/A>                         |
When the user clicks on the following sidebar item:
| data_type | title                |
| LINK      | Open complete report |
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title            | creation_date | author                        | status  |
| Tumor board Note | Jul 07, 2019  | Physician Family Medicine, MD | current |
And the full report modal body contains the following data: L3/OncoTestPatient_Juno_Encounter2_CompleteReport.txt

@SRS-43.04
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68551
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Full_Fidelity_Info
Scenario: Full report modal is closed by clicking on 'X' button
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Encounters" swimlane:
| event_type | name_on_label    |
| MARKER     | Tumor board Note |
And the sidebar is displayed
And the following sidebar item was clicked:
| data_type | title                |
| LINK      | Open complete report |
And the full report modal is displayed on the screen
When the user clicks on the 'X' button on the full report modal
Then the full report modal is not displayed on the screen

@SRS-43.05
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68551
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Full_Fidelity_Info
Scenario: Alert message is displayed before leaving OncoCare to see full report in a new browser tab (external content)
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Pathology and Labs" swimlane:
| event_type | name_on_label                  |
| MARKER     | Tissue Pathology biopsy report |
And the sidebar is displayed
And the sidebar content contains the following data:
| data_type | title                 | value                                    |
| KEY-VALUE | Conclusion            | Left axillary lymph node core biopsy: OK |
| KEY-VALUE | Status                | final                                    |
| LINK      | Open external content | <N/A>                                    |
When the user clicks on the following sidebar item:
| data_type | title                 |
| LINK      | Open external content |
Then an alert modal is displayed
And the alert modal contains the following title: "Leaving CareIntellect for Oncology"
And the alert modal contains the following message: L3/LeavingOncoCare_alert.txt

@SRS-43.06
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68551
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Full_Fidelity_Info
Scenario Outline: Full report is displayed in a new browser tab (external content)
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "<swimlane>" swimlane:
| event_type | name_on_label |
| MARKER     | <event_name>  |
And the sidebar is displayed
And the following sidebar item was clicked:
| data_type | title                 |
| LINK      | Open external content |
And an alert modal is displayed
When the user clicks on the "Continue" button on the alert modal
Then the alert modal disappears
And a new browser tab is opened with the following url: <url>
Examples:
| example_description                           | swimlane           | event_name                     | url                                                                    |
| url comes from 'presentedForm' parameter      | Pathology and Labs | Tissue Pathology biopsy report | https://onchron-test.com/resource/9aa11c95-9f25-4630-a8b3-e37764edcca9 |
| url comes from 'content.attachment' parameter | Uncategorized      | Office Visit                   | https://onchron-test.com/resource/6ab6cfdb-d937-4518-a9af-79aa47e6c5cb |

@SRS-43.07
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68551
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Full_Fidelity_Info
Scenario: 'Leaving OncoCare' alert popup is closed by clicking on Cancel button (external content)
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Pathology and Labs" swimlane:
| event_type | name_on_label                  |
| MARKER     | Tissue Pathology biopsy report |
And the sidebar is displayed
And the following sidebar item was clicked:
| data_type | title                 |
| LINK      | Open external content |
And an alert modal is displayed
When the user clicks on the "Cancel" button on the alert modal
Then the alert modal disappears
And new browser tab is not opened

@SRS-43.08
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68551
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Full_Fidelity_Info
Scenario: 'Leaving OncoCare' alert popup is closed by clicking on the 'X' button (external content)
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Pathology and Labs" swimlane:
| event_type | name_on_label                  |
| MARKER     | Tissue Pathology biopsy report |
And the sidebar is displayed
And the following sidebar item was clicked:
| data_type | title                 |
| LINK      | Open external content |
And an alert modal is displayed
When the user clicks on the 'X' button on the alert modal
Then the alert modal disappears
And new browser tab is not opened

@SRS-43.09
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68551
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Full_Fidelity_Info
Scenario: Full report is displayed in a new modal if it is coming from binary resource in DiagnosticReport
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Pathology and Labs" swimlane:
| event_type | text_on_event_point | name_on_label                |
| MARKER     | <EMPTY>             | DiagnosticReport with binary |
And the sidebar is displayed
And the sidebar header contains the following data:
| title                        | creation_date | report_type        |
| DiagnosticReport with binary | May 11, 2015  | Pathology and Labs |
And the sidebar content contains the following data:
| data_type | title                | value                         |
| KEY-VALUE | Conclusion           | To test report type: "Binary" |
| KEY-VALUE | Status               | final                         |
| LINK      | Open complete report | <N/A>                         |
When the user clicks on the following sidebar item:
| data_type | title                |
| LINK      | Open complete report |
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title                        | creation_date | author    | status |
| DiagnosticReport with binary | May 11, 2015  | No author | final  |
And the full report modal body contains the following data: L3/OncoTestPatient_Juno_Pathology_DiagReport_fromBinary.txt

@SRS-43.10
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68551
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Full_Fidelity_Info
Scenario: Full report is displayed in a new modal if it is coming from binary resource in DocumentReference
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Encounters" swimlane:
| event_type | text_on_event_point | name_on_label  |
| MARKER     | <EMPTY>             | Progress Notes |
And the sidebar is displayed
Then the sidebar header contains the following data:
| title          | creation_date | report_type |
| Progress Notes | May 31, 2017  | Encounters  |
And the sidebar content contains the following data:
| data_type | title                | value                         |
| KEY-VALUE | Document Status      | final                         |
| KEY-VALUE | Status               | current                       |
| KEY-VALUE | Author               | Physician Family Medicine, MD |
| KEY-VALUE | Provider Type        | Physician                     |
| LINK      | Open complete report | <N/A>                         |
When the user clicks on the following sidebar item:
| data_type | title                |
| LINK      | Open complete report |
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title          | creation_date | author                        | status  |
| Progress Notes | May 31, 2017  | Physician Family Medicine, MD | current |
And the full report modal body contains the following data: L3/OncoTestPatient_Juno_Encounter_ProgressNotes_fromBinary.txt

@SRS-43.11
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68551
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Full_Fidelity_Info
Scenario: Full report is downloaded if it is available in pdf format
Given the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Biomarkers" swimlane:
| event_type | name_on_label   |
| MARKER     | Report with pdf |
And the sidebar is displayed
And the sidebar content contains the following data:
| data_type | title       | value                |
| KEY-VALUE | Conclusion  | To test pdf download |
| KEY-VALUE | Status      | final                |
| KEY-VALUE | Full report | Download report      |
When the user clicks on the value of the following sidebar item:
| data_type | title       | value           |
| KEY-VALUE | Full report | Download report |
Then a new browser tab titled "Report with pdf" is opened for the user
And the PDF report content is loaded in the new tab
And the PDF report tab header contains the report title: "Report with pdf"
When the user clicks on the Download button on the PDF report tab
Then a file is downloaded with name "Report with pdf.pdf"
And the downloaded file content is equal to: L3/Report with pdf.pdf

@SRS-43.12
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68551,SysRS-69564
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Full_Fidelity_Info
Scenario Outline: User can increase the font size of L3 report
Given the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "<swimlane>" swimlane:
| event_type | name_on_label |
| MARKER     | <event_name>  |
When I click on the following sidebar item:
| data_type | title                |
| LINK      | Open complete report |
Then the full report modal is displayed on the screen
And a zoom slider is available under the L3 modal header with the following values:
| zoom_slider_values |
| 100%               |
| 120%               |
| 150%               |
And the zoom slider is at 100%
And the '-' zoom button is disabled
And the '+' zoom button is enabled
When I move the zoom slider to 120%
Then the font size of the report body is increased
And the '-' zoom button is enabled
And the '+' zoom button is enabled
When I move the zoom slider to 150%
Then the font size of the report body is increased
And the '-' zoom button is enabled
And the '+' zoom button is disabled
When I move the zoom slider to 100%
Then the font size of the report body is decreased
And the '-' zoom button is disabled
And the '+' zoom button is enabled
Examples:
| example_description           | swimlane           | event_name             |
| Report coming from plain text | Encounters         | Tumor board Note - 1   |
| Report coming from html       | Pathology and Labs | Test Diagnostic Report |

@SRS-43.13
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68551,SysRS-69564
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Full_Fidelity_Info
Scenario: Image displayed on report modal if html report contains image
Given the "OncoTestPatient, Torvald" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Pathology and Labs" swimlane:
| event_type | name_on_label                  |
| MARKER     | Cytology, pap smear (cervical) |
When I click on the following sidebar item:
| data_type | title                |
| LINK      | Open complete report |
Then the full report modal is displayed on the screen
And an image is displayed under the "Image" title on the modal
And the zoom slider is at 100%
When I move the zoom slider to 150%
Then the font size of the report body is increased
And the size of the image is increased

@manual
@SRS-43.14
@srs
@Platform:WEB
@Category:Negative
@Traceability:SysRS-68551
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Full_Fidelity_Info
Scenario: Error message is displayed if report cannot be downloaded and displayed on the report modal
Given [DEVTOOLS] - the following text pattern was added to "Network request blocking" tool:
| text pattern |
| s3link       |
And [DEVTOOLS] - the "Enable network request blocking" checkbox is CHECKED in the "Network request blocking" tool
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Imaging" swimlane:
| event_type | name_on_label                          |
| MARKER     | CT THORAX ABDOMEN PELVIS WITH CONTRAST |
When the user clicks on the value of the following sidebar item:
| data_type | value                |
| KEY-VALUE | Open complete report |
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title                                  | creation_date | author    | status |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011  | No author | final  |
And the full report modal body contains the following message: "Failed to load content"

@manual
@srs
@SRS-43.15
@Platform:WEB
@Category:Negative
@Traceability:SysRS-68551
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Full_Fidelity_Info
Scenario: Error message is displayed if report pdf cannot be downloaded
Given [DEVTOOLS] - the following text pattern was added to "Network request blocking" tool:
| text pattern |
| s3link       |
And [DEVTOOLS] - the "Enable network request blocking" checkbox is CHECKED in the "Network request blocking" tool
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Biomarkers" swimlane:
| event_type | name_on_label   |
| MARKER     | Report with pdf |
When the user clicks on the value of the following sidebar item:
| data_type | title       | value           |
| KEY-VALUE | Full report | Download report |
Then the following notification popup is visible on the screen:
| popup_header   | popup_content_message                                           |
| Download error | Some of the downloads have been failed. Please try again later. |
And the notification popup disappears in 5 seconds
