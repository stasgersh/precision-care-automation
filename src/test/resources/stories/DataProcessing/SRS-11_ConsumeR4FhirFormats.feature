Feature: [SysRS-68416] Consume R4 FHIR Formats

Narrative: The system shall be able to consume data in HL7 FHIR r4 format from different data sources available at the hospital


@SRS-11.01
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68382,SysRS-68416
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Automated/Functional_Service/Data_processing_and_Fulfillment
Scenario: The fulfillment service can process data in HL7 FHIR R4 standard and categorize it
Given [API] the "latestVersion" timestamp of the patient with fhir id "899a9796-cf42-4b55-b6ab-5e991cb0e30a" is saved
When the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method | endpoint_path                                                                     |
| core    | GET    | /patients/899a9796-cf42-4b55-b6ab-5e991cb0e30a/timeline?version=<<latestVersion>> |
Then the response contains the "items[0].trace" property
And the "items[*]" property contains a list where elements have "trace.classId" properties includes the following values:
| resourceTypes   |
| vitalSigns      |
| labResult       |
| clinicalNote    |
| imagingReport   |
| pathologyReport |
| uncategorized   |
| medication      |
When [API] the "latestVersion" timestamp of the patient with fhir id "426a3c11-fce2-1113-8a72-de0b43301a3b" is saved
And the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method | endpoint_path                                                                     |
| core    | GET    | /patients/426a3c11-fce2-1113-8a72-de0b43301a3b/timeline?version=<<latestVersion>> |
Then the response contains the "items[0].trace" property
And the "items[*]" property contains a list where elements have "trace.classId" properties includes the following values:
| resourceTypes     |
| clinicalNote      |
| patientVisit      |
| vitalSigns        |
| surgicalProcedure |
| labReport         |
| imagingReport     |
| uncategorized     |