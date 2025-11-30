@safety
Feature: [SysRS-70330] Mandatory Fields In Patient Banner

Narrative: The system shall always display the following parameters to the user:
               - Patient name
               - Patient_id (PID)
               - DOB
               - Gender
               - Weight

@sanity
@SPR-4930
@configChange
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-70330
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Patient_Banner
Scenario Outline: Following parameters cannot be removed via patient banner configuration: PID, DOB, Gender, Weight
When the following request is sent to Precision Insights by service user app:
| service | method | endpoint_path                                       |
| core    | GET    | /configs/representation/patientBanner.onco.extended |
And the user removes the element "<json_path_to_property>" from the response json and stores as "property_removed_from_config"
And the following request is sent to Precision Insights by service user app:
| service | method | endpoint_path                                       | payload                          | content_type     |
| core    | PUT    | /configs/representation/patientBanner.onco.extended | <<property_removed_from_config>> | application/json |
Then the received HTTP status code is 400
Examples:
| json_path_to_property                            |
| $.items[0].elements[?(@.key.constant=='MRN')]    |
| $.items[0].elements[?(@.key.constant=='DOB')]    |
| $.items[0].elements[?(@.key.constant=='Gender')] |
| $.items[0].elements[?(@.key.constant=='Weight')] |

@sanity
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-70286
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Patient_Banner
Scenario Outline: Hide/Show parameters with empty state
When the following request is sent to Precision Insights by service user app:
| service | method | endpoint_path                                       |
| core    | GET    | /configs/representation/patientBanner.onco.extended |
And the user set false value to the element "<json_path_to_property>" from the response json and stores as "set_value_to_config"
And the following request is sent to Precision Insights by service user app:
| service | method | endpoint_path                                       | payload                 | content_type     |
| core    | PUT    | /configs/representation/patientBanner.onco.extended | <<set_value_to_config>> | application/json |
Then the received HTTP status code is 204
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Empty" patient is selected
When the user expands the patient banner
Then the following parameters are not visible in the patient banner:
| parameter_name |
| Histology type |
When the following request is sent to Precision Insights by service user app:
| service | method | endpoint_path                                       |
| core    | GET    | /configs/representation/patientBanner.onco.extended |
And the user set true value to the element "<json_path_to_property>" from the response json and stores as "set_value_to_config"
And the following request is sent to Precision Insights by service user app:
| service | method | endpoint_path                                       | payload                 | content_type     |
| core    | PUT    | /configs/representation/patientBanner.onco.extended | <<set_value_to_config>> | application/json |
Then the received HTTP status code is 204
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Empty" patient is selected
When the user expands the patient banner
Then the following parameters are visible in the patient banner:
| parameter_name |
| Histology type |
Examples:
| json_path_to_property                                           |
| $.items[0].elements[?(@.key.string=='Histology type')].required |

@sanity
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-70286
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Patient_Banner
Scenario: Hide/Show parameters with different states
When the following patient was deleted from PDS: summary/amitHaze_bundle_BMI_BSA.json
Then the received HTTP status code is 200
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient testPatientIdAmitHaze, triggering the ETL Step Function fulfill-etl-workflow
Given the following bundle was uploaded to PDS: summary/amitHaze_bundle.json
And the received HTTP status code was 200
And the mandatory ETL process is finished within 900 seconds with status Succeeded
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, AmitHaze" patient is selected
When the user expands the patient banner
Then the following parameters are not visible in the patient banner:
| parameter_name |
| BMI            |
| BSA            |
Given the following bundle was uploaded to PDS: summary/amitHaze_bundle_BMI_BSA.json
Then the received HTTP status code is 200
Then the mandatory ETL process is finished within 900 seconds with status Succeeded
When the user clicks on the browser's refresh button
Then the patient summary view is loaded
When the user expands the patient banner
Then the following parameters are visible in the patient banner:
| parameter_name |
| BMI            |
| BSA            |
When the following patient was deleted from PDS: summary/amitHaze_bundle_BMI_BSA.json
Then the received HTTP status code is 200
And the DATA_FABRIC_DELETE SNS event from file deleteDataFabricMessage was published with start time offset 90 sec to delete patient testPatientIdAmitHaze, triggering the ETL Step Function fulfill-etl-workflow
And the mandatory ETL process is finished within 300 seconds with status Succeeded
When the user clicks on the browser's refresh button
Given the following bundle was uploaded to PDS: summary/amitHaze_bundle.json
And the mandatory ETL process is finished within 900 seconds with status Succeeded
When the user clicks on the browser's refresh button
Given the Patient page is loaded
And the "OncoTestPatient, AmitHaze" patient is selected
When the user expands the patient banner
Then the following parameters are not visible in the patient banner:
| parameter_name |
| BMI            |
| BSA            |


@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70330
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Patient_Banner
Scenario: Patient name is always displayed when navigating between tabs
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
When the user selects the "OncoTestPatient, Freya" patient
Then the patient summary view is loaded
And the patient name is displayed in the patient header: "OncoTestPatient, Freya"
When the user navigates to the "Timeline" view
Then the patient name is displayed in the patient header: "OncoTestPatient, Freya"
When the user navigates to the "Medical history" view
Then the patient name is displayed in the patient header: "OncoTestPatient, Freya"
When the user navigates to the "Trends" view
Then the patient name is displayed in the patient header: "OncoTestPatient, Freya"
When the user navigates to the "Comments" view
Then the patient name is displayed in the patient header: "OncoTestPatient, Freya"


@srs
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-70330
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Patient_Banner
Scenario: Non-service user has no access to change the patient banner configuration
When the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method | endpoint_path                                  | payload                                                | content_type     |
| core    | PUT    | /configs/representation/patientBanner/prostate | applicationConfiguration/patientBanner/missingPID.json | application/json |
Then the received HTTP status code is 403
