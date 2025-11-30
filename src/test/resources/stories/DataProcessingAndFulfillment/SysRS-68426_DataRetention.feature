Feature: [SysRS-68426] Data configuration - Data retention period

Narrative: The system shall support configurable retention period time for the patient's data.


@srs
@fulfillment
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68426
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
@manual
Scenario: Old versions of data are deleted after a certain time
Given retention period is set to 3 minutes
When the following patient was uploaded to PDS: patients/OncoTestPatient_VersionTest.json
And the mandatory ETL process is finished within 300 seconds with status Succeeded
And the user executes [patient] data query for single patient [version-test-patient-id]
Then the received HTTP status code is 200
And the value of "results[0].entries[0].patient_version" property in the response is stored as "patient_patient_version"
When the user executes [bmi] data query for single patient [version-test-patient-id]
Then the received HTTP status code is 200
And the value of "results[0].entries[0].patient_version" property in the response is stored as "bmi_patient_version"
When the following patient was uploaded to PDS: patients/OncoTestPatient_VersionTest.json
And the mandatory ETL process is finished within 300 seconds with status Succeeded
And the user executes [patient] data query for single patient [version-test-patient-id]
Then the received HTTP status code is 200
And the value of "results[0].entries[0].patient_version" property is bigger than "patient_patient_version"
When the user executes [bmi] data query for single patient [version-test-patient-id]
Then the received HTTP status code is 200
And the value of "results[0].entries[0].patient_version" property is bigger than "bmi_patient_version"
And TTL can be seen for the "patient_patient_version"
When retention period is over
And "patient_patient_version" can't be observed
And the user executes [patient] data query for single patient [version-test-patient-id] with version "patient_patient_version"
Then the received HTTP status code is 200
And the "results[0].entries" property in the response contains 0 elements
When the user executes [bmi] data query for single patient [version-test-patient-id] with version "bmi_patient_version"
Then the received HTTP status code is 200
And the "results[0].entries" property in the response contains 0 elements


