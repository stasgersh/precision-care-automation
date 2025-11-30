Feature: [SysRS-68692] Hospital IT Administrators configuration

Narrative: The system shall provide a way for Hospital IT Administrators to configure the following parameters in EHS Admin Console UI:
             - EMR smart integration
             - DICOM viewer URL
             - Patient identifier
             - Oncology Department Identifier.


@ToPlaywright
@sanity
@configChange
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68692,SysRS-68691
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/General_Usability_And_Ease_Of_Use
Scenario Outline: Patient ID is changed in the system based on configuration
Given the following configuration uploaded into the App: <configuration>
And the following patient was uploaded to PDS: <patient_file>
And the mandatory ETL process is finished within 180 seconds with status Succeeded
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient header section is displayed
And the page is reloaded
And the Patient page is loaded
And the user opened the patient select menu
And the search field is empty
When the user types into the patient search field: <patient_id>
Then the following user is present in the patient list:
| name                             | mrn          |
| Ms. OncoTestPatient, Config Test | <patient_id> |
Examples:
| configuration                         | patient_id       | patient_file                                    |
| configuration/default_app_config.json | mr-type-based-id | patients/OncoTestPatient_ConfigTest_Type.json   |
| configuration/ss_type_app_config.json | ss-type-based-id | patients/OncoTestPatient_ConfigTest_Type.json   |
| configuration/default_app_config.json | system-based-id  | patients/OncoTestPatient_ConfigTest_System.json |


@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68692,SysRS-68691
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Manual/General_Usability_And_Ease_Of_Use
Scenario: DICOM url is changed based on configuration
Given the following configuration uploaded into the App: configuration/empty_app_config.json
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
When the user clicks on the following event point on the "Imaging" swimlane:
| event_type | name_on_label                                                      |
| MARKER     | Computed tomography, abdomen and pelvis; with contrast material(s) |
And following sidebar item is not present:
| data_type | title       |
| LINK      | Open images |
Given the following configuration uploaded into the App: configuration/default_app_config.json
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
When the user clicks on the following event point on the "Imaging" swimlane:
| event_type | name_on_label                                                      |
| MARKER     | Computed tomography, abdomen and pelvis; with contrast material(s) |
And I click on the following sidebar item:
| data_type | title       |
| LINK      | Open images |
Then the DICOM Viewer is opened in a modal
And the opened modal has the following title: "Computed tomography, abdomen and pelvis; with contrast material(s)"

@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68692,SysRS-68691,SysRS-70905
@NotImplemented
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/General_Usability_And_Ease_Of_Use
Scenario: System prevents uploading of invalid configuration

@srs
@manual
@aws
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68608
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
@Description:Check_if_fargate_getting_in_use_after_etl_is_finished<br/>,ETL_Process_successfully_finished
Scenario: Check if fargate getting in use after etl is finished
Given the following patient was uploaded to PDS: fulfillment/etlProcess/casey.json
And the mandatory ETL process is started successfully
When i go to lambda/Functions/<tested-stack>-fulfill-EtlManagement-zip in aws
Then i tap on Throttle button
And the following etl-workflow system log is present message:
| items             |
| Build ETL Request |
| ETL Lambda        |
| ETL Fargate       |
When i tap on configuration in lambda/Functions/<tested-stack>-fulfill-EtlManagement-zip at aws
And select  "Concurrency and recursion detection"
And Tap on Edit
Then i tap on Reserve concurrency
Given the following patient was uploaded to PDS: summary/amitHaze_bundle.json
And the mandatory ETL process is finished within 1500 seconds with status Succeeded
Then the following etl-workflow system log is not present message:
| items       |
| ETL Fargate |