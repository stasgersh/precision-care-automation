
@safety
Feature: [SysRS-68483] Summary View Displays Full Report

Narrative: The system shall provide the users the ability to view the corresponding full report/s and historical patient’s information of the following cards:
             - Key events
             - Most recent imaging
             - Imaging history
             - Most recent MDT report
             - Most recent oncology note


@sanity
@edge
@SRS-154.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68483,SysRS-68485
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
Scenario: Open full report modal of 'Key events' card by clicking on the report title on the card
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the patient summary view is loaded
And the 3rd card in "Diagnosis" group contains the following data:
| data_type  | data                                               |
| CARD_TITLE | Key events                                         |
| TABLE      | [source]: summary/table_on_diagnosis_Torvald.table |
When the user clicks on the "Tissue Pathology biopsy report 2" report link in "Key events" in the "Diagnosis" group
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title                            | creation_date | author    | status |
| Tissue Pathology biopsy report 2 | Jan 15, 2020  | No author | final  |
And the full report modal body contains the following data: L3/OncoTestPatient_Torvald_Tissue_Path_biopsy_report_2.html
When the user clicks on the 'X' button on the full report modal
Then the full report modal is not displayed on the screen
When the user clicks on the "Cytology, pap smear (cervical)" report link in "Key events" in the "Diagnosis" group
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title                          | creation_date | author    | status |
| Cytology, pap smear (cervical) | May 31, 2013  | No author | final  |
And the full report modal body contains the following data: L3/OncoTestPatient_Torvald_Cytology_pap_smear.html

@SRS-154.02
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70530
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/VPR_List_-_Manual/Functional_Service/Summary_View
Scenario: Open full report modal of 'Key events' card from card footer if the card is linked to more reports
Given I logged in to OncoCare application
And I selected the "OncoTestPatient, Juno" patient
And the "Summary" view is displayed
And the "Key events" card in "Diagnosis" group equals the following data:
| data_type | data                                                        |
| TEXT      | Suture open wound                      [date]: Mar 22, 2020 |
| TEXT      | Tissue Pathology biopsy report         [date]: Jan 15, 2020 |
| TEXT      | APPENDECTOMY                           [date]: Jul 02, 2019 |
| TEXT      | CLEFT PALATE REPAIR                    [date]: May 02, 2019 |
| TEXT      | Hysteroscopy                           [date]: Apr 04, 2017 |
| TEXT      | Coronary artery bypass                 [date]: Feb 11, 2017 |
| LINK      | [link]: DiagnosticReport with binary   [date]: May 11, 2015 |
| LINK      | [link]: Pathology Report               [date]: Oct 13, 2012 |
| LINK      | [link]: Test Diagnostic Report         [date]: May 11, 2009 |
| LINK      | [link]: DiagReport Test 0              [date]: May 11, 2007 |
| LINK      | [link]: Doc ref test                   [date]: Date Unknown |
When I click on the 'Full report' button in the footer of the below card:
| group_name | card_title_in_group |
| Key events | Key events          |
Then the report modal is displayed
And the 'Full report' has scrollbar to view the full report
And the report modal header contains the following data:
| title       | info_text                                    |
| Full report | Key events is linked to 5 different reports. |
And the report modal contains an event list on the left side with the following events:
| event_name                   | event_date   |
| DiagnosticReport with binary | May 11, 2015 |
| Pathology Report             | Oct 13, 2012 |
| Test Diagnostic Report       | May 11, 2009 |
| DiagReport Test 0            | May 11, 2007 |
| Doc ref test                 | Date Unknown |
And the first report is selected from the event list by default
And the following data is displayed in the report content area:
| date         | report_title                 | report_content                                                                                                                                           |
| May 11, 2015 | DiagnosticReport with binary | [SUB_TITLE]: From binary [CONTENT]: This report text is comming from a binary report. (presentedForm.url: "Binary/76bf4510-6868-4b7b-8093-55c446dc8aed") |
And a zoom slider is available in the report content area under the report title with the following values:
| zoom_slider_values |
| 100%               |
| 120%               |
| 150%               |
And the zoom slider is at 100%
When I move the zoom slider to 120%
Then the font size of the report body is increased
When I move the zoom slider to 150%
Then the font size of the report body is increased
When I move the zoom slider to 120%
Then the font size of the report body is decreased
When I select the following event from the list:
| event_name             | event_date   |
| Test Diagnostic Report | May 11, 2009 |
Then the following data is displayed in the report content area:
| date         | report_title           | report_content                                                                                                                                                                                              |
| May 11, 2009 | Test Diagnostic Report | XR Chest Clinical indication : Left sided chest pain going down to left arm ?unstable angina The lungs and pleural spaces are clear. Normal heart size and mediastinal contour. Single RV lead PPM in situ. |
And the zoom slider is still at 120%
When I move the zoom slider to 100%
Then the font size of the report body is decreased

@SRS-154.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68483,SysRS-68485,SysRS-69564
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Summary_View
Scenario: Open full report modal of 'Imaging - most recent' card from card footer
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the patient summary view is loaded
And the 1st card in "Imaging" group equals the following data:
| data_type   | data                                                     |
| CARD_TITLE  | Imaging - most recent                                    |
| NORMAL_TEXT | Examination of joint under image intensifier (procedure) |
| KEY_VALUE   | [key]: Date                 [value]: Sep 09, 2022        |
When the user clicks on the 'Full report' button in the footer of the below card:
| group_name | card_index_in_group |
| Imaging    | 0                   |
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title                                                    | creation_date | author    | status |
| Examination of joint under image intensifier (procedure) | Sep 09, 2022  | No author | final  |
And the full report modal body contains the following data: L3/OncoTestPatient_Juno_MostRecentImaging_FullReport.txt

@SRS-154.04
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70530
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/VPR_List_-_Manual/Functional_Service/Summary_View
Scenario: Open full report modal of 'Imaging history' card from card footer
Given I logged in to OncoCare application
And I selected the "OncoTestPatient, Juno" patient
And the "Summary" view is displayed
And the "Imaging history" card in "Imaging" group equals the following data:
| data_type | data                                                                                           |
| KEY_VALUE | [key]: Examination of joint under image intensifier (procedure)          [value]: Sep 09, 2022 |
| KEY_VALUE | [key]: Positron emission tomography with computed tomography (procedure) [value]: Sep 08, 2022 |
| KEY_VALUE | [key]: Diagnostic imaging study                                          [value]: Aug 19, 2022 |
| KEY_VALUE | [key]: XR CHEST PA OR AP                                                 [value]: Aug 18, 2022 |
When I click on the 'Full report' button in the footer of the below card:
| group_name | card_title_in_group |
| Imaging    | Imaging history     |
Then the report modal is displayed
And the report modal header contains the following data:
| title       | info_text                                         |
| Full report | Imaging history is linked to 3 different reports. |
And the report modal contains an event list on the left side with the following events:
| event_name                                               | event_date   |
| Examination of joint under image intensifier (procedure) | Sep 09, 2022 |
| Diagnostic imaging study                                 | Aug 19, 2022 |
| XR CHEST PA OR AP                                        | Aug 18, 2022 |
And the first report is selected from the event list by default
And the following data is displayed in the report content area:
| date         | report_title                                             | report_content                                         |
| Sep 09, 2022 | Examination of joint under image intensifier (procedure) | testdata/L3/OncoTestPatientJuno_ExaminationOfJoint.txt |
And a zoom slider is available in the report content area under the reprot title with the following values:
| zoom_slider_values |
| 100%               |
| 120%               |
| 150%               |
And the zoom slider is at 100%
When I move the zoom slider to 120%
Then the font size of the report body is increased
When I move the zoom slider to 150%
Then the font size of the report body is increased
When I move the zoom slider to 120%
Then the font size of the report body is decreased
When I select the following event from the list:
| event_name        | event_date   |
| XR CHEST PA OR AP | Aug 18, 2022 |
Then the following data is displayed in the report content area:
| date         | report_title      | report_content                                                                                                              |
| Aug 18, 2022 | XR CHEST PA OR AP | Narrative Heart normal size and configuration. Lungs clear. (from observation) Impression Negative chest (from observation) |
And the zoom slider is still at 120%
When I move the zoom slider to 100%
Then the font size of the report body is decreased

@SRS-154.06
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68483,SysRS-68485
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Summary_View
Scenario: Open full report modal of 'MDT report - most recent' card from card footer
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the patient summary view is loaded
And the 1st card in "MDT report" group equals the following data:
| data_type   | data                                               |
| CARD_TITLE  | MDT report - most recent                           |
| NORMAL_TEXT | Tumor board Note                                   |
| KEY_VALUE   | [key]: Date                  [value]: Jul 07, 2019 |
When the user clicks on the 'Full report' button in the footer of the below card:
| group_name | card_index_in_group |
| MDT report | 0                   |
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title            | creation_date | author                        | status  |
| Tumor board Note | Jul 07, 2019  | Physician Family Medicine, MD | current |
And the full report modal body contains the following data: L3/OncoTestPatient_Juno_MostRecentMDTReport_FullReport.txt

@SRS-154.07
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68483,SysRS-68485
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Summary_View
Scenario: Open full report modal of 'Oncology note - most recent' card from card footer if the card is linked to one report
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Eleanor470" patient is selected
And the patient summary view is loaded
And the 1st card in "Oncology note" group equals the following data:
| data_type   | data                                              |
| CARD_TITLE  | Oncology note - most recent                       |
| NORMAL_TEXT | History and physical note                         |
| KEY_VALUE   | [key]: Date                 [value]: Mar 30, 2020 |
When the user clicks on the 'Full report' button in the footer of the below card:
| group_name    | card_index_in_group |
| Oncology note | 0                   |
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title                     | creation_date | author                  | status  |
| History and physical note | Mar 30, 2020  | Dr. Borer986, Marvin195 | current |
And the full report modal body contains the following data: L3/OncoTestPatient_Eleanor470_MostRecentOncoNote_FullReport.txt

@SRS-154.08
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68483,SysRS-68485
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Summary_View
Scenario: Open full report modal of 'Radiation therapy' card by clicking on the report title on the card
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the patient summary view is loaded
And the 4rd card in "Treatments" group equals the following data:
| data_type  | data                                                          |
| CARD_TITLE | Radiation therapy                                             |
| LINK       | [link]: Radiation oncology Summary note  [date]: Feb 04, 2020 |
When the user clicks on the following data item on the 4rd card in "Treatments" group:
| data_type | data                                                          |
| LINK      | [link]: Radiation oncology Summary note  [date]: Feb 04, 2020 |
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title                           | creation_date | author                        | status  |
| Radiation oncology Summary note | Feb 04, 2020  | Physician Family Medicine, MD | current |
And the full report modal body contains the following data: L3/OncoTestPatient_Juno_RadiationOncologySummary_note.txt
When the user clicks on the 'X' button on the full report modal
Then the full report modal is not displayed on the screen
