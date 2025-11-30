@safety
Feature: [SysRS-68594] Artificial Intelligence (AI) – information and hyperlink source

Narrative: The system shall provide a hyperlink to the user for the source of data whenever data is extracted from an AI algorithm.

@CLP
@sanity
@edge
@ai_sanity
@SRS-68594.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68594,SysRS-68591
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: CLP data source can be opened from Patient Banner
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Franklin" patient is selected
And the patient banner includes the following data:
| data_type | title          | value                 |
| CLP_INFO  | Histology type | [keywords]: carcinoma |
When the user moves the mouse over on the value of the following patient banner item:
| data_type | title          | value                  |
| CLP_INFO  | Histology type | <HIGHLIGHTED_AI_VALUE> |
Then the AI tooltip is displayed with "Sourced or produced by AI" disclaimer text
When the user clicks on the title of the following root resource item on the AI tooltip:
| report_title       | date         | author    | status  |
| Pathology Report 3 | Mar 23, 2004 | No author | current |
Then the full report modal is displayed on the screen
And the full report modal body contains the following data: L3/OncoTestPatient_Franklin_PathologyReport.txt

@SRS-68594.02
@srs
@CLP
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68594,SysRS-68591,SysRS-68593
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: CLP data source can be opened from Summary Dashboard
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Juno" patient is selected
And the patient summary view is loaded
And the 1st card in "Imaging" group equals the following data:
| data_type   | data                                                                      |
| CARD_TITLE  | Imaging - most recent                                                     |
| NORMAL_TEXT | Examination of joint under image intensifier (procedure)                  |
| CLP_DATA    | [source]: summary/CLP/Juno_examination_of_joint_summary_CLP_truncated.txt |
| KEY_VALUE   | [key]: Date            [value]: Sep 09, 2022                              |
When the user moves the mouse on the data on the 1st card in "Imaging" group:
| data_type | data                                                                      |
| CLP_DATA  | [source]: summary/CLP/Juno_examination_of_joint_summary_CLP_truncated.txt |
Then the AI tooltip is displayed with "Sourced or produced by AI" disclaimer text
When the user clicks on the title of the following root resource item on the AI tooltip:
| report_title                                             | date         | author    | status |
| Examination of joint under image intensifier (procedure) | Sep 09, 2022 | No author | final  |
Then the full report modal is displayed on the screen
And the full report modal body contains the following data: L3/OncoTestPatient_Juno_MostRecentImaging_FullReport.txt

@SRS-68594.04
@srs
@CLP
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68594,SysRS-68591,SysRS-68593
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: CLP data source can be opened from response data on Treatment and response view
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Juno" patient is selected
And the patient summary view is loaded
And the "Status and response" button was clicked
And the Treatment and response view is loaded
And the following imaging study was selected for study B:
| name                                                     | date         |
| Examination of joint under image intensifier (procedure) | Sep 09, 2022 |
When the user hovers on the AI extracted text of "Examination of joint under image intensifier (procedure)" on the Response view
Then the AI tooltip is displayed with "Sourced or produced by AI" disclaimer text
When the user clicks on the title of the following root resource item on the AI tooltip:
| report_title                                             | date         | author    | status |
| Examination of joint under image intensifier (procedure) | Sep 09, 2022 | No author | final  |
Then the full report modal is displayed on the screen
And the full report modal body contains the following data: L3/OncoTestPatient_Juno_MostRecentImaging_FullReport.txt

@SRS-68594.05
@srs
@CLP
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68594,SysRS-68591,SysRS-68593
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: CLP data source can be opened from Timeline view sidebar badge
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Juno" patient is selected
And the patient summary view is loaded
And the 'Show on timeline' button is enabled in the footer of the below card:
| group_name | card_index_in_group |
| Imaging    | 0                   |
When the user clicks on the 'Show on timeline' button in the footer of the below card:
| group_name | card_index_in_group |
| Imaging    | 0                   |
Then the patient timeline is loaded
And the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type | badge_text                                      |
| CLP        | Partial tear and fracture due to recent trauma. |
When the user moves the mouse over on the following sidebar badge:
| badge_type | badge_text                                      |
| CLP        | Partial tear and fracture due to recent trauma. |
Then the AI tooltip is displayed with "Sourced or produced by AI" disclaimer text
When the user clicks on the title of the following root resource item on the AI tooltip:
| report_title                                             | date         | author    | status |
| Examination of joint under image intensifier (procedure) | Sep 09, 2022 | No author | final  |
Then the full report modal is displayed on the screen
And the full report modal body contains the following data: L3/OncoTestPatient_Juno_MostRecentImaging_FullReport.txt

@SRS-68594.06
@srs
@CLP
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68594,SysRS-68591,SysRS-68593
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Product_Messaging_Alarms_Warnings_And_Notifications
Scenario: CLP data source can be opened from Timeline view sidebar text
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Juno" patient is selected
And the patient summary view is loaded
And the 'Show on timeline' button is enabled in the footer of the below card:
| group_name | card_index_in_group |
| Imaging    | 0                   |
When the user clicks on the 'Show on timeline' button in the footer of the below card:
| group_name | card_index_in_group |
| Imaging    | 0                   |
Then the patient timeline is loaded
And the sidebar is displayed
And the sidebar content contains the following data:
| data_type | title                | value  | value_from_file                                                |
| KEY-VALUE | Conclusion           | STABLE | <N/A>                                                          |
| KEY-VALUE | Status               | final  | <N/A>                                                          |
| LINK      | Open complete report | <N/A>  | <N/A>                                                          |
| CLP_INFO  | <N/A>                | <N/A>  | L2/CLP/OncoTestPatient_Juno_Examination_of_joint_truncated.txt |
When the user moves the mouse on the following data on the sidebar:
| data_type | title | value_from_file                                                |
| CLP_INFO  | <N/A> | L2/CLP/OncoTestPatient_Juno_Examination_of_joint_truncated.txt |
Then the AI tooltip is displayed with "Sourced or produced by AI" disclaimer text
When the user clicks on the title of the following root resource item on the AI tooltip:
| report_title                                             | date         | author    | status |
| Examination of joint under image intensifier (procedure) | Sep 09, 2022 | No author | final  |
Then the full report modal is displayed on the screen
And the full report modal body contains the following data: L3/OncoTestPatient_Juno_MostRecentImaging_FullReport.txt
