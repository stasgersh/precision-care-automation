Feature: [SysRS-68499] Progressive Summarization - What Has Changed

Narrative: The system shall display the following information in a 'What has changed':
               - last oncology encounter  date of the patient (with the oncologist)..
               - list of events since the last encounter.
               - Hyperlinks to source documents for each event.
               - Open Imaging functionality whenever imaging study is available.
               - Summary of recent reports - L2 information.


@sanity
@SRS-68499.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68499
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: Date of last oncology encounter and the list of events is displayed in What has changed' section (less than 5 events)
Given [API] the AI data visualization is switched OFF for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "TEST_PS, Hank" patient
Then the Progressive Summarization Overview is displayed
And the 'What has changed' section is displayed in PS Overview
And the 'What has changed' section does not have 'AI is turned off' information
And the 'What has changed' section has the title: "Since last oncology encounter (Sep 09, 2007)"
And the 'What has changed' section has the following event list table:
| Event                                                    | Date         | Options                          |
| Pathology Report                                         | Apr 10, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Tissue Pathology biopsy report                           | Feb 04, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Examination of joint under image intensifier (procedure) | Oct 11, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| CT ABDOMEN AND PELVIS                                    | Oct 02, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |

@sanity
@SRS-68499.02
@SPR-4618
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68499
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: User can navigate to the source document of the event if report is available for the event
Given [API] the AI data visualization is switched OFF for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "TEST_PS, Hank" patient
Then the Progressive Summarization Overview is displayed
And the 'What has changed' section has the following event list table:
| Event                                                    | Date         | Options                          |
| Pathology Report                                         | Apr 10, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Tissue Pathology biopsy report                           | Feb 04, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Examination of joint under image intensifier (procedure) | Oct 11, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| CT ABDOMEN AND PELVIS                                    | Oct 02, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |
And the 'Full report' button is enabled in row: 0, column: "Options" in 'What has changed' table
And the 'Full report' button is disabled in row: 1, column: "Options" in 'What has changed' table
And the 'Full report' button is enabled in row: 2, column: "Options" in 'What has changed' table
And the 'Full report' button is enabled in row: 3, column: "Options" in 'What has changed' table
When the user clicks on the 'Full report' button in row: 0, column: "Options" in 'What has changed' table
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title            | creation_date | author             | status  |
| Pathology Report | Apr 10, 2008  | Dr. Matrey, Bianca | current |
And the full report modal body contains the following data: L3/TEST_PS_Hank_PathologyReport.txt
When the user clicks on the 'X' button on the full report modal
Then the full report modal is not displayed on the screen
When the user clicks on the 'Full report' button in row: 2, column: "Options" in 'What has changed' table
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title                                                    | creation_date | author    | status |
| Examination of joint under image intensifier (procedure) | Oct 11, 2007  | No author | final  |
And the full report modal body contains the following data: L3/TEST_PS_Hank_ExaminationOfJoint.txt

@manual
@sanity
@SRS-68499.03
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68499
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Manual/Summary_View/Progressive_summarization
Scenario: User can navigate to the images of the event if images are available for the event
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "TEST_PS, Hank" patient
Then the Progressive Summarization Overview is displayed
And the 'What has changed' section has the following event list table:
| Event                                                    | Date         | Options                          |
| Pathology Report                                         | Apr 10, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Tissue Pathology biopsy report                           | Feb 04, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Examination of joint under image intensifier (procedure) | Oct 11, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| CT ABDOMEN AND PELVIS                                    | Oct 02, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |
And the 'Open images' button is disabled in the 1st row in the "Options" column
And the 'Open images' button is disabled in the 2nd row in the "Options" column
And the 'Open images' button is disabled in the 3rd row in the "Options" column
And the 'Open images' button is enabled in the 4th row in the "Options" column
When I click on the 'Open images' button in the 4th row in the "Options" column
Then I see a modal open with the following title: "CT ABDOMEN AND PELVIS"
And I see the DICOM viewer on the modal

@sanity
@SRS-68499.04
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68499,SysRS-69559
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: List of events is displayed in What has changed' section (5 to 10 events)
Given [API] the AI data visualization is switched OFF for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "TEST_PS, 08e" patient
Then the Progressive Summarization Overview is displayed
And the 'What has changed' section is displayed in PS Overview
And the 'What has changed' section does not have 'AI is turned off' information
And the 'What has changed' section has the title: "Since last oncology encounter (Sep 09, 2007)"
And the 'What has changed' section has the following event list table:
| Event                           | Date         | Options                          |
| Radiation oncology Summary note | Oct 07, 2009 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Tumor board Note                | Jul 07, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Progress Notes                  | May 19, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| CT ABDOMEN AND PELVIS 2         | Apr 15, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
And the 'What has changed' section displays the following button "Show all 8 events"
When the user clicks "Show all 8 events" on 'What has changed' section
Then the 'What has changed' section has the following event list table:
| Event                                                    | Date         | Options                          |
| Radiation oncology Summary note                          | Oct 07, 2009 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Tumor board Note                                         | Jul 07, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Progress Notes                                           | May 19, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| CT ABDOMEN AND PELVIS 2                                  | Apr 15, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | Apr 10, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| CT ABDOMEN AND PELVIS                                    | Mar 15, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | Feb 03, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Examination of joint under image intensifier (procedure) | Oct 11, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |
And the 'What has changed' section displays the following button "Show less events"
When the user clicks "Show less events" on 'What has changed' section
Then the 'What has changed' section has the following event list table:
| Event                           | Date         | Options                          |
| Radiation oncology Summary note | Oct 07, 2009 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Tumor board Note                | Jul 07, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Progress Notes                  | May 19, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| CT ABDOMEN AND PELVIS 2         | Apr 15, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
And the 'What has changed' section displays the following button "Show all 8 events"

@sanity
@SRS-68499.05
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68499,SysRS-69559,SysRS-69563
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: List of events is displayed in What has changed' section (over 10 events)
Given [API] the AI data visualization is switched OFF for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "TEST_PS, 11e" patient
Then the Progressive Summarization Overview is displayed
And the 'What has changed' section is displayed in PS Overview
And the 'What has changed' section does not have 'AI is turned off' information
And the 'What has changed' section has the title: "Since last oncology encounter (Sep 09, 2007)"
And the 'What has changed' section has the following event list table:
| Event                           | Date         | Options                          |
| Radiation oncology Summary note | Oct 07, 2009 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Tumor board Note                | Jul 07, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Progress Notes                  | May 19, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| CT ABDOMEN AND PELVIS 2         | Apr 15, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
And the 'What has changed' section displays the following button "View all 11 events"
When the user clicks "View all 11 events" on 'What has changed' section
Then the 'What has changed' modal is displayed on the screen
And the 'What has changed' modal has the following title: "Since last oncology encounter (Sep 09, 2007)"
And the 'What has changed' modal has the following event list table:
| Event                                                    | Date         | Options                          |
| Radiation oncology Summary note                          | Oct 07, 2009 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Tumor board Note                                         | Jul 07, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Progress Notes                                           | May 19, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| CT ABDOMEN AND PELVIS 2                                  | Apr 15, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | Apr 10, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| CT ABDOMEN AND PELVIS                                    | Mar 15, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | Feb 03, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | Feb 02, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | Feb 01, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | Jan 31, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Examination of joint under image intensifier (procedure) | Oct 11, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |
When the user clicks on the 'Full report' button in row: 4, column: "Options" in Events list table on 'What has changed' modal
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title            | creation_date | author    | status  |
| Pathology Report | Apr 10, 2008  | No author | current |
And the full report modal body contains the following data: L3/TEST_PS_11e_Pathology_FullReport.txt
When the user clicks on the 'Back to Event list' button on the full report modal
Then the 'What has changed' modal is displayed on the screen
And the 'What has changed' modal has the following title: "Since last oncology encounter (Sep 09, 2007)"
And the 'What has changed' modal has the following event list table:
| Event                                                    | Date         | Options                          |
| Radiation oncology Summary note                          | Oct 07, 2009 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Tumor board Note                                         | Jul 07, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Progress Notes                                           | May 19, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| CT ABDOMEN AND PELVIS 2                                  | Apr 15, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | Apr 10, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| CT ABDOMEN AND PELVIS                                    | Mar 15, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | Feb 03, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | Feb 02, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | Feb 01, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | Jan 31, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Examination of joint under image intensifier (procedure) | Oct 11, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |
When the user clicks on the 'X' button on the 'What has changed' modal
Then the 'What has changed' modal is not displayed on the screen

@sanity
@SRS-68499.06
@SPR-4618
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68499,SysRS-68592,SysRS-68594,SysRS-68595
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: Navigate from 'Summary of recent reports' content to the original resource (single report)
Given [API] the AI data visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "TEST_PS, Hank" patient
Then the Progressive Summarization Overview is displayed
And the 'Summary of recent reports' section has the title: "Summary of recent reports and notes"
When the user clicks "Show more" on 'Summary of recent reports' section
Then the 'Summary of recent reports' section contains the following key words:
| key_words                          |
| since the last visit on 09-09-2007 |
| serous papillary carcinoma         |
When the user hovers the 2nd source link in 'Summary of recent reports' section
Then the AI tooltip contains the following root resource items:
| report_title     | date         | author             | status  |
| Pathology Report | Apr 10, 2008 | Dr. Matrey, Bianca | current |
When the user clicks on the title of the following root resource item on the AI tooltip:
| report_title     | date         | author             | status  |
| Pathology Report | Apr 10, 2008 | Dr. Matrey, Bianca | current |
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title            | creation_date | author             | status  |
| Pathology Report | Apr 10, 2008  | Dr. Matrey, Bianca | current |
And the full report modal body contains the following data: L3/TEST_PS_Hank_PathologyReport.txt
When the user clicks on the 'X' button on the full report modal
Then the full report modal is not displayed on the screen

@sanity
@SRS-68499.07
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68499,SysRS-69559
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: User can expand the AI summarization in 'Summary of recent reports' to see the full AI finding
Given [API] the AI data visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "TEST_PS, LongSummary" patient
Then the Progressive Summarization Overview is displayed
And the 'Summary of recent reports' section has the title: "Summary of recent reports and notes"
And the 'Summary of recent reports' section contains the following key words:
| key_words            |
| Since the last visit |
And the 'Summary of recent reports' section does not contain the following key words:
| key_words                           |
| moderately differentiated carcinoma |
And the 'Summary of recent reports' section displays the following button "Show more"
And the 'Summary of recent reports' section lists the AI findings with bullet points
And a [source] link is available at the end of all AI findings in the 'Summary of recent reports' section
When the user clicks "Show more" on 'Summary of recent reports' section
Then the 'Summary of recent reports' section displays the following button "Show less"
And the 'Summary of recent reports' section contains the following key words:
| key_words                           |
| Since the last visit                |
| moderately differentiated carcinoma |
And the 'Summary of recent reports' section lists the AI findings with bullet points
And a [source] link is available at the end of all AI findings in the 'Summary of recent reports' section
When the user clicks "Show less" on 'Summary of recent reports' section
Then the 'Summary of recent reports' section displays the following button "Show more"
And the 'Summary of recent reports' section contains the following key words:
| key_words            |
| Since the last visit |
And the 'Summary of recent reports' section does not contain the following key words:
| key_words                           |
| moderately differentiated carcinoma |

@sanity
@SRS-68499.08
@SPR-4618
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68499,SysRS-69559,SysRS-68592,SysRS-68594,SysRS-69557
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: Navigate from 'Summary of recent reports' content to the original resources (multiple reports + Show more/show less)
Given [API] the AI data visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "TEST_PS, LongSummary" patient
Then the Progressive Summarization Overview is displayed
And the 'Summary of recent reports' section has the title: "Summary of recent reports and notes"
And the 'Summary of recent reports' section displays the following button "Show more"
When the user clicks "Show more" on 'Summary of recent reports' section
And the user clicks the last source link in 'Summary of recent reports' section
Then the full reports modal is displayed on the screen
And the multiple report modal header contains the following data:
| title              | sub_title                                    |
| Source information | Information is found in 2 different reports. |
And the event list on the multiple report modal contains the following events:
| title                  | creation_date | author              | status  | selected |
| Pathology Report test1 | Apr 12, 2000  | Dr. Burton, Charles | current | true     |
| Pathology Report test2 | Apr 13, 2000  | Dr. Burton, Charles | current | false    |
And selected report on the multiple report modal contains the following header data:
| title                  | creation_date | author              | status  |
| Pathology Report test1 | Apr 12, 2000  | Dr. Burton, Charles | current |
And selected report on the multiple report modal contains the following content: L3/TEST_PS_LongSummary_PathologyReportTest1.txt

@integration
@change_detection
@SRS-68499.09
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68499
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Automated/Functional_Service/Summary_View/Progressive_summarization
Scenario: Progressive summarization data can be refreshed after the user is notified about new patient summary data availability
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And [API] the patient with ID "sLpokXQEPjo8AfP9CxDgyy" is deleted from PS monitoring list
And [API] the following resources are not available in PDS:
| resourceFilePath                                  |
| patients/resource/TEST_PS_Charlie_Encounter2.json |
| patients/resource/TEST_PS_Charlie_DocRef2.json    |
| patients/resource/TEST_PS_Charlie_DocRef3.json    |
And the following patient was deleted from PDS: patients/TEST_PS_Charlie.json
And [API] all additional "DocumentReference" resources were deleted from DPSA with patient reference ID: "sLpokXQEPjo8AfP9CxDgyy"
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient sLpokXQEPjo8AfP9CxDgyy, triggering the ETL Step Function fulfill-etl-workflow
Then the mandatory fulfill-etl-workflow Sfn processes are finished within 900 seconds with status Succeeded for property parameters.patientId and propertyValue sLpokXQEPjo8AfP9CxDgyy
Given [API] the following patient is uploaded to PDS with no fixed wait: patients/TEST_PS_Charlie.json
And [API] the "/summarization" post-processing metadata is generated into the following resources:
| resource                                 |
| DiagnosticReport/aJ4dbYhBxyo8Fy2CXYJXv4  |
| DiagnosticReport/2xLzcJas2NXQsCZYgTGNLF  |
| DocumentReference/9gk39bByqAN1FtcFW8xJq6 |
And [API] a mandatory ETL process is started in 180 seconds and finished within 180 seconds with status Succeeded for patient sLpokXQEPjo8AfP9CxDgyy
And [API] an optional ETL process is started in 60 seconds and finished within 180 seconds with status Succeeded for patient sLpokXQEPjo8AfP9CxDgyy
And [API] an optional ETL process is started in 60 seconds and finished within 180 seconds with status Succeeded for patient sLpokXQEPjo8AfP9CxDgyy
When the [DEFAULT-TEST-USER] user logs in to OncoCare
And the user selects the "TEST_PS, Charlie" patient
Then the patient summary view is loaded
And the Progressive Summarization Overview is displayed
And the 'What has changed' section has the title: "Since last oncology encounter"
And [API] 1 "DocumentReference" resource with category "http://loinc.org|60591-5" is generated in DPSA for patient "sLpokXQEPjo8AfP9CxDgyy"
And [API] a mandatory ETL process is started in 180 seconds and finished within 180 seconds with status Succeeded for patient sLpokXQEPjo8AfP9CxDgyy
And [API] an optional ETL process is started in 60 seconds and finished within 180 seconds with status Succeeded for patient sLpokXQEPjo8AfP9CxDgyy
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then the patient summary view is loaded
And the following patient sync banner appears in 60000 milliseconds, regex: "Patient data last updated: (0|1) min ago"
And the Progressive Summarization Overview is displayed
And the 'What has changed' section has the title: "Since last oncology encounter (Sep 09, 2003)"
When [API] the following resources are uploaded to PDS with no fixed wait:
| patient_resource                               | resource                                          |
| patients/resource/TEST_PS_Charlie_Patient.json | patients/resource/TEST_PS_Charlie_Encounter2.json |
| patients/resource/TEST_PS_Charlie_Patient.json | patients/resource/TEST_PS_Charlie_DocRef2.json    |
| patients/resource/TEST_PS_Charlie_Patient.json | patients/resource/TEST_PS_Charlie_DocRef3.json    |
Then [API] the "/summarization" post-processing metadata is generated into the following resources:
| resource                                 |
| DocumentReference/qRqiWy4iJ5WyY98dFTRZgm |
| DocumentReference/uQ57rEYfhxm2WjAaPWi7NL |
And [API] a mandatory ETL process is started in 180 seconds and finished within 180 seconds with status Succeeded for patient sLpokXQEPjo8AfP9CxDgyy
And [API] an optional ETL process is started in 60 seconds and finished within 180 seconds with status Succeeded for patient sLpokXQEPjo8AfP9CxDgyy
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then the patient summary view is loaded
And the following patient sync banner appears in 60000 milliseconds, regex: "Patient data last updated: (0|1) min ago"
And the Progressive Summarization Overview is displayed
And the 'What has changed' section has the title: "Since last oncology encounter (Sep 09, 2003)"
When [API] the patient with ID "sLpokXQEPjo8AfP9CxDgyy" is deleted from PS monitoring list
And the user clicks on the browser's refresh button
Then the patient summary view is loaded
And the Progressive Summarization Overview is displayed
And the 'What has changed' section has the title: "Since last oncology encounter (Sep 09, 2003)"
And [API] 2 "DocumentReference" resources with category "http://loinc.org|60591-5" are generated in DPSA for patient "sLpokXQEPjo8AfP9CxDgyy"
And [API] a mandatory ETL process is started in 180 seconds and finished within 180 seconds with status Succeeded for patient sLpokXQEPjo8AfP9CxDgyy
And [API] an optional ETL process is started in 60 seconds and finished within 180 seconds with status Succeeded for patient sLpokXQEPjo8AfP9CxDgyy
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then the patient summary view is loaded
And the following patient sync banner appears in 60000 milliseconds, regex: "Patient data last updated: (0|1) min ago"
And the Progressive Summarization Overview is displayed
And the 'What has changed' section has the title: "Since last oncology encounter (Nov 09, 2003)"
And the 'What has changed' section has the following event list table:
| Event            | Date         | Options                          |
| Pathology Report | Dec 02, 2003 | [REPORT_BUTTON] [IMAGING_BUTTON] |
When the user hovers the 1st source link in 'Summary of recent reports' section
Then the AI tooltip contains the following root resource items:
| report_title     | date         | author    | status  |
| Pathology Report | Dec 02, 2003 | No author | current |

@integration
@SRS-68499.10
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68499,SysRS-69323
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Automated/Functional_Service/Summary_View/Progressive_summarization
Scenario: Empty state messages are displayed on Progressive summarization panel if PS report not available
Given [API] the AI data visualization is switched OFF for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, Empty" patient
Then the Progressive Summarization Overview is displayed
And the 'Brief history' section has 'AI is turned off' information
And the 'Summary of recent reports' section has 'AI is turned off' information
And the 'What has changed' section does not have 'AI is turned off' information
And the 'What has changed' section has the title: "Since last oncology encounter"
And the 'What has changed' section has the following empty state message: "No new events found"
When the user clicks on the "Turn on AI" button on 'Brief history' section
Then the 'Brief history' section has the following empty state message: "No reports found"
And the 'Summary of recent reports' section has the following empty state message: "No reports found"

@integration
@SRS-68499.11
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68499
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Automated/Functional_Service/Summary_View/Progressive_summarization
Scenario: Explanatory note is displayed when hovering report and/or imaging buttons in the table of events since last oncology encounter (PS panel)
Given [API] the AI data visualization is switched OFF for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "TEST_PS, Hank" patient
Then the Progressive Summarization Overview is displayed
And the 'What has changed' section has the following event list table:
| Event                                                    | Date         | Options                          |
| Pathology Report                                         | Apr 10, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Tissue Pathology biopsy report                           | Feb 04, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Examination of joint under image intensifier (procedure) | Oct 11, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| CT ABDOMEN AND PELVIS                                    | Oct 02, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |
And the 'Full report' button is enabled in row: 0, column: "Options" in 'What has changed' table
And the 'Full report' button is disabled in row: 1, column: "Options" in 'What has changed' table
And the 'Images' button is disabled in row: 0, column: "Options" in 'What has changed' table
And the 'Images' button is enabled in row: 3, column: "Options" in 'What has changed' table
When the user hovers the 'Full report' button in row: 0, column: "Options" in 'What has changed' table
Then tooltip is displayed with text: Full report
When the user hovers the 'Full report' button in row: 1, column: "Options" in 'What has changed' table
Then tooltip is displayed with text: Report could not be found. It may exist somewhere outside the application, like in another hospital or system this application is not connected to.
When the user hovers the 'Images' button in row: 0, column: "Options" in 'What has changed' table
Then tooltip is displayed with text: Images could not be found. It may exist somewhere outside the application, like in another hospital or system this application is not connected to.
When the user hovers the 'Images' button in row: 3, column: "Options" in 'What has changed' table
Then tooltip is displayed with text: Images

@integration
@SRS-68499.12
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68499
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Automated/Functional_Service/Summary_View/Progressive_summarization
Scenario: Explanatory note is displayed when hovering report and/or imaging buttons in the table of events since last oncology encounter (Events list modal)
Given [API] the AI data visualization is switched OFF for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "TEST_PS, Holden" patient
Then the Progressive Summarization Overview is displayed
And the 'What has changed' section is displayed in PS Overview
And the 'What has changed' section has the title: "Since last oncology encounter (Sep 09, 2006)"
And the 'What has changed' section displays the following button "View all 16 events"
When the user clicks "View all 16 events" on 'What has changed' section
Then the 'What has changed' modal is displayed on the screen
And the 'What has changed' modal has the following title: "Since last oncology encounter (Sep 09, 2006)"
And the 'What has changed' modal has the following event list table:
| Event                                                    | Date         | Options                          |
| CT ABDOMEN AND PELVIS                                    | Feb 02, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Tissue Pathology biopsy report                           | Jan 23, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | Jan 12, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | Jul 22, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | Jul 17, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | Jul 07, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | Jun 23, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | Jun 17, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | Jun 10, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | May 22, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | May 22, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | May 10, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | Apr 12, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | Apr 11, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Pathology Report                                         | Apr 10, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Examination of joint under image intensifier (procedure) | Oct 11, 2006 | [REPORT_BUTTON] [IMAGING_BUTTON] |
When the user hovers the 'Full report' button in row: 0, column: "Options" in Events list table on 'What has changed' modal
Then tooltip is displayed with text: Full report
When the user hovers the 'Images' button in row: 0, column: "Options" in Events list table on 'What has changed' modal
Then tooltip is displayed with text: Images
When the user hovers the 'Full report' button in row: 1, column: "Options" in Events list table on 'What has changed' modal
Then tooltip is displayed with text: Report could not be found. It may exist somewhere outside the application, like in another hospital or system this application is not connected to.
When the user hovers the 'Images' button in row: 1, column: "Options" in Events list table on 'What has changed' modal
Then tooltip is displayed with text: Images could not be found. It may exist somewhere outside the application, like in another hospital or system this application is not connected to.

@manual
@integration
@SRS-68499.13
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68499,SysRS-69559,SysRS-69563
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Summary_View/Progressive_summarization
Scenario: User can navigate to the images of the event if images are available for the event (over 10 events)
Given [API] the AI data visualization is switched OFF for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "TEST_PS, Holden" patient
Then the Progressive Summarization Overview is displayed
And the 'Since last oncology encounter' section is displayed in PS Overview
And the 'Since last oncology encounter' section does not have 'AI is turned off' information
And the 'Since last oncology encounter' section has the title: "Since last oncology encounter (Sep 09, 2006)"
And the 'Since last oncology encounter' section displays the following button "View all 16 events"
When the user clicks "View all 16 events" on 'Since last oncology encounter' section
Then the 'Since last oncology encounter' modal is displayed on the screen
And the 'Since last oncology encounter' modal has the following title: "Since last oncology encounter (Sep 09, 2006)"
And the 'Open images' button is enabled in the 0th row in the "Options" column
And the 'Open images' button is disabled in the 1st row in the "Options" column
When I click on the 'Open images' button in the 0th row in the "Options" column
Then I see a modal open with the following header:
| title                 | creation_date |
| CT ABDOMEN AND PELVIS | Feb 02, 2008  |
And I see the DICOM viewer on the modal
And I see the 'Back to Event list' button on the top of the modal
When the user clicks on the 'Back to Event list' button on the modal
Then the 'Since last oncology encounter' modal is displayed on the screen
And the 'Since last oncology encounter' modal has the following title: "Since last oncology encounter (Sep 09, 2006)"

@integration
@SRS-68499.14
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68499
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Automated/Functional_Service/Summary_View/Progressive_summarization
Scenario: User is informed when there are no summaries since last oncology encounter to present
Given [API] the AI data visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "TEST_PS, MultiReport" patient
Then the Progressive Summarization Overview is displayed
And the 'What has changed' section has the title: "Since last oncology encounter (Jan 09, 2000)"
And the 'What has changed' section has the following empty state message: "No new events found"
And the 'Summary of recent reports' section has the title: "Summary of recent reports and notes"
And the 'Summary of recent reports' section has an AI badge with text: "AI generated"
And the 'Summary of recent reports' section contains the following text: "No summaries since last oncology encounter to present"
