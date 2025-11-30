@safety
Feature: [SysRS-68589] Artificial Intelligence (AI) – information and hyperlink source

Narrative: The system shall be able to provide AI enrichment support for documents summarization on the following data type formats: PDF, Txt, Doc(x), and rtf by utilizing AI service.

@CLP
@sanity
@ai_sanity
@edge
@SRS-68589.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68589
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Artificial_Intelligence
Scenario: AI-enriched summary generated from PDF, DOCX and RTF reports can be viewed on PS Overview
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "TEST_AI, MultiFileType" patient is selected
Then the Progressive Summarization Overview is displayed
And the 'What has changed' section has the following event list table:
| Event                                                    | Date         | Options                          |
| doc report                                               | Feb 13, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| pdf report                                               | Jan 11, 2008 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| rtf report                                               | Dec 09, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |
| Examination of joint under image intensifier (procedure) | Oct 11, 2007 | [REPORT_BUTTON] [IMAGING_BUTTON] |
And the 'Summary of recent reports' section has the title: "Summary of recent reports and notes"
And the 'Summary of recent reports' section has an AI badge with text: "AI generated"
When the user clicks "Show more" on 'Summary of recent reports' section
Then the 'Summary of recent reports' section contains the following key words:
| key_words      |
| adenocarcinoma |
| hemoptysis     |
When the user hovers the 2nd source link in 'Summary of recent reports' section
Then the AI tooltip contains the following root resource items:
| report_title | date         | author    | status  |
| rtf report   | Dec 09, 2007 | No author | current |
When the user hovers the 6th source link in 'Summary of recent reports' section
Then the AI tooltip contains the following root resource items:
| report_title | date         | author    | status |
| pdf report   | Jan 11, 2008 | No author | final  |
When the user hovers the 7th source link in 'Summary of recent reports' section
Then the AI tooltip contains the following root resource items:
| report_title | date         | author    | status  |
| doc report   | Feb 13, 2008 | No author | current |
