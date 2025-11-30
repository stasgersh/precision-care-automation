Feature: [SysRS-68688] Trace To Source Data Health Record

Narrative: The system shall be able to trace The system data to the source data health record.


@srs
@CLP
@api
@SRS-70.01
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68688
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario: Trace to the root resource is available in the patient banner api response
Given [API] the "latestVersion" timestamp of the patient with fhir id "a10e48ab-b927-9ee1-84a4-41638a84e05a" is saved
When the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method | endpoint_path                                                                   |
| core    | GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/banner?version=<<latestVersion>> |
Then the response contains the "items[0].trace" property
And the "items[0].trace[*]" property contains a list where the elements have "resources[*]" properties with below values:
| rootResource                                             |
| Patient/a10e48ab-b927-9ee1-84a4-41638a84e05a             |
| Observation/e6274b64-2d9b-4789-a0be-00b681fcec7f         |
| Observation/70ffbbcd-4f3e-4d0f-8967-fc309b28cd57         |
| DocumentReference/1b46f42e-1f4c-48d4-b9f8-225ff72d510a   |
| Practitioner/08108467-0d68-4c9d-bffd-69f68da8529c        |
| MedicationStatement/adf27827-6468-4476-bd88-71ff28174ce2 |
| Condition/7d52ec36-5827-41bb-a3d5-01a636c8845c           |
| Condition/645c0d39-8c72-4f49-aa95-39ae119818a6           |
| Condition/500c107a-5510-4d0f-a5d7-ebcfc6ce237c           |
| Condition/4a9bc424-d1af-4e85-9081-3ae099d2a5b3           |
| DocumentReference/205c1168-e8a9-4402-8160-7b91ee120be9   |
| Observation/2164be93-ad28-4766-93e8-8859f4251827         |
| Observation/54105f89-eac9-4893-a2e8-8054ad164385         |
| Observation/e2377444-1c96-4b99-ac98-c054caeef186         |
| Observation/1f0b41fe-c75c-4039-9abf-2e6e791753b8         |
| Observation/2caafd5d-053f-4a0c-85b4-9af5bc436dcc         |
| Observation/2caafd5d-053f-4a0c-85b4-9af5bc436dcc         |
| Observation/21becb88-3a8c-4787-8b8d-53d6d3a3dcbf         |

@srs
@api
@SRS-70.02
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68688
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario: Trace to the root resource is available in the summary view api response
Given [API] the "latestVersion" timestamp of the patient with fhir id "a10e48ab-b927-9ee1-84a4-41638a84e05a" is saved
Given [API] the "latestEligibilityVersion" timestamp of the patient with fhir id "a10e48ab-b927-9ee1-84a4-41638a84e05a" is saved
When the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method | endpoint_path                                                                    |
| core    | GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/summary?version=<<latestVersion>> |
Then the response contains the "items" property
And the "items" property contains a list where all elements have "trace" property

@srs
@api
@SRS-70.03
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68688
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario: Trace to the root resource is available in the timeline view api response
Given [API] the "latestVersion" timestamp of the patient with fhir id "a10e48ab-b927-9ee1-84a4-41638a84e05a" is saved
When the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method | endpoint_path                                                                     |
| core    | GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/timeline?version=<<latestVersion>> |
Then the response contains the "items" property
And the "items" property contains a list where all elements have "trace" property

@srs
@api
@SRS-70.04
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68688
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario: Trace to the root resource is available in the L3 report api response
Given [API] the "latestVersion" timestamp of the patient with fhir id "a10e48ab-b927-9ee1-84a4-41638a84e05a" is saved
When the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method | endpoint_path                                                                                                                                                                                              |
| core    | GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/reports/clinicalNote/clinicalNote:9fe5f5cb-11b1-4c7c-aa88-a86a46460c0c?representation=reportHtml.onco.general&version=<<latestVersion>> |
Then the response contains the "items[0].trace" property
And the "items[*].trace[*]" property contains a list where the elements have "resources[*]" properties with below values:
| rootResource                                           |
| DocumentReference/9fe5f5cb-11b1-4c7c-aa88-a86a46460c0c |
| Practitioner/9fe5f5cb-11b1-4c7c-aa88-a68a64460c0c      |

@srs
@api
@SRS-70.05
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68688
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario: Trace to the root resource is available in the medical history view api response
Given [API] the "latestVersion" timestamp of the patient with fhir id "a10e48ab-b927-9ee1-84a4-41638a84e05a" is saved
When the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method | endpoint_path                                                                            |
| core    | GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/medical-history?version=<<latestVersion>> |
Then the response contains the "items" property
And the "items" property contains a list where all elements have "trace" property

@srs
@api
@SRS-70.06
@Platform:Rest_API
@Category:Positive
@Traceability:SysRS-68688
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario: Trace to the root resource is available in the trends api response
Given [API] the "latestVersion" timestamp of the patient with fhir id "a10e48ab-b927-9ee1-84a4-41638a84e05a" is saved
When the following request is sent to OncoCare on behalf of [DEFAULT-TEST-USER] user:
| service | method | endpoint_path                                                                   |
| core    | GET    | /patients/a10e48ab-b927-9ee1-84a4-41638a84e05a/trends?version=<<latestVersion>> |
Then the response contains the "items[0].elements[0].value.curves[0].dataPoints[0].id" property
And the "items[0].elements[1].value.curves[0].dataPoints[0].id" property in the response matches the value: vitalSigns:e6274b64-2d9b-4789-a0be-00b681fcec7f
