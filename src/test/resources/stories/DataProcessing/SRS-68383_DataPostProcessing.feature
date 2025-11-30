Feature: [SysRS-68383] Data Post Processing

Narrative: The system shall be able to process and visualize enriched data provided by Data Fabric service or AI service.

@SRS-68383.01
@integration
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68383
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Artificial_Intelligence
Scenario: Post processing is triggered and CLP/Summarization data is present in the system
Given the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient test-patient-dpsa-id, triggering the ETL Step Function fulfill-etl-workflow
And the mandatory ETL process is finished within 120 seconds with status Succeeded
And [API] the following patient is uploaded to PDS with no fixed wait: patients/OncoTestPatient_DpsaTest.json
And [API] a mandatory ETL process is started in 180 seconds and finished within 300 seconds with status Succeeded for patient test-patient-dpsa-id
And [API] a mandatory ETL process is started in 180 seconds and finished within 300 seconds with status Succeeded for patient test-patient-dpsa-id
And [API] the AI data visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "OncoTestPatient, DpsaTest" patient
Then the patient summary view is loaded
And the patient banner includes the following data:
| data_type | title          | value                 |
| CLP_INFO  | Histology type | [keywords]: carcinoma |
And the 1st card in "Imaging" group equals the following data:
| data_type   | data                                                                      |
| CARD_TITLE  | Imaging - most recent                                                     |
| NORMAL_TEXT | Examination of joint under image intensifier (procedure)                  |
| CLP_DATA    | [source]: summary/CLP/Juno_examination_of_joint_summary_CLP_truncated.txt |
| KEY_VALUE   | [key]: Date            [value]: Sep 09, 2022                              |
And the "Imaging - most recent" card content is highlighted