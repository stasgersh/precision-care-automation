Feature: [SysRS-69323] Progressive Summarization - Brief History

Narrative: The system shall display a brief history of patient summary since the diagnosis of a patient and until today by utilizing AI service.


@sanity
@SRS-69323.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69323,SysRS-68592,SysRS-68594,SysRS-68595
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: Navigate from 'Brief history' content to the original resource (single report)
Given [API] the AI data visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "TEST_PS, Hank" patient
Then the Progressive Summarization Overview is displayed
And the 'Brief history' section has the title: "Brief history"
And the 'Brief history' section contains the following key words:
| key_words                       |
| metastatic colon adenocarcinoma |
| no malignancy                   |
| Xelox                           |
And the 'Brief history' section lists the AI findings with bullet points
And a [source] link is available at the end of all AI findings in the 'Brief history' section
When the user hovers the 3rd source link in 'Brief history' section
Then the AI tooltip contains the following root resource items:
| report_title                      | date         | author             | status  |
| History and physical note - test1 | Sep 09, 2007 | Dr. Matrey, Bianca | current |
When the user clicks on the title of the following root resource item on the AI tooltip:
| report_title                      | date         | author             | status  |
| History and physical note - test1 | Sep 09, 2007 | Dr. Matrey, Bianca | current |
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title                             | creation_date | author             | status  |
| History and physical note - test1 | Sep 09, 2007  | Dr. Matrey, Bianca | current |
And the full report modal body contains the following data: L3/TEST_PS_Hank_history_test1.txt
When the user clicks on the 'X' button on the full report modal
Then the full report modal is not displayed on the screen
When the user clicks on the 3rd source link in 'Brief history' section
Then the full reports modal is displayed on the screen
And the full report modal header contains the following data:
| title                             | creation_date | author             | status  |
| History and physical note - test1 | Sep 09, 2007  | Dr. Matrey, Bianca | current |
And the full report modal body contains the following data: L3/TEST_PS_Hank_history_test1.txt


@sanity
@SRS-69323.02
@SPR-4618
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69323,SysRS-68592,SysRS-68594,SysRS-69557
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: Navigate from 'Brief history' content to the original resources (multiple reports opened from tooltip)
Given [API] the AI data visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "TEST_PS, MultiReport" patient
Then the Progressive Summarization Overview is displayed
And the 'Brief history' section has the title: "Brief history"
And the 'Brief history' section has an AI badge with text: "AI generated"
And the 'Brief history' section contains the following key words:
| key_words                       |
| metastatic colon adenocarcinoma |
| HIPEC                           |
| Xelox                           |
When the user hovers the 1st source link in 'Brief history' section
Then the AI tooltip contains the following root resource items:
| report_title       | date         | author              | status  |
| Radiology report   | Feb 24, 1999 | No author           | current |
| Radiology report 2 | Jul 15, 1999 | Dr. Burton, Charles | current |
When the user clicks on the title of the following root resource item on the AI tooltip:
| report_title       | date         | author              | status  |
| Radiology report 2 | Jul 15, 1999 | Dr. Burton, Charles | current |
Then the full reports modal is displayed on the screen
And the multiple report modal header contains the following data:
| title              | sub_title                                    |
| Source information | Information is found in 2 different reports. |
And the event list on the multiple report modal contains the following events:
| title              | creation_date | author              | status  | selected |
| Radiology report   | Feb 24, 1999  | No author           | current | false    |
| Radiology report 2 | Jul 15, 1999  | Dr. Burton, Charles | current | true     |
And selected report on the multiple report modal contains the following header data:
| title              | creation_date | author              | status  |
| Radiology report 2 | Jul 15, 1999  | Dr. Burton, Charles | current |
And selected report on the multiple report modal contains the following content: L3/TEST_PS_MultiRep_Radiology2.txt


@sanity
@SRS-69323.03
@SPR-4618
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69323,SysRS-68592,SysRS-68594,SysRS-69557
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Summary_View/Progressive_summarization
Scenario: Navigate from 'Brief history' content to the original resources (multiple reports opened from [source] link)
Given [API] the AI data visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "TEST_PS, MultiReport" patient
Then the Progressive Summarization Overview is displayed
And the 'Brief history' section has the title: "Brief history"
And the 'Brief history' section has an AI badge with text: "AI generated"
And the 'Brief history' section contains the following key words:
| key_words                       |
| metastatic colon adenocarcinoma |
| HIPEC                           |
| Xelox                           |
When the user clicks on the 1st source link in 'Brief history' section
Then the full reports modal is displayed on the screen
And the multiple report modal header contains the following data:
| title              | sub_title                                    |
| Source information | Information is found in 2 different reports. |
And the event list on the multiple report modal contains the following events:
| title              | creation_date | author              | status  | selected |
| Radiology report   | Feb 24, 1999  | No author           | current | true     |
| Radiology report 2 | Jul 15, 1999  | Dr. Burton, Charles | current | false    |
And selected report on the multiple report modal contains the following header data:
| title            | creation_date | author    | status  |
| Radiology report | Feb 24, 1999  | No author | current |
And selected report on the multiple report modal contains the following content: L3/TEST_PS_MultiRep_Radiology.txt
When the user clicks on the following event on the event list on the multiple report modal:
| title              | creation_date | author              | status  | selected |
| Radiology report 2 | Jul 15, 1999  | Dr. Burton, Charles | current | false    |
Then selected report on the multiple report modal contains the following header data:
| title              | creation_date | author              | status  |
| Radiology report 2 | Jul 15, 1999  | Dr. Burton, Charles | current |
And selected report on the multiple report modal contains the following content: L3/TEST_PS_MultiRep_Radiology2.txt
