@safety
Feature: [SysRS-68687] Data integrity

Narrative: The system users shall not be able to edit any data that have been aggregated from the hospital systems


@SRS-69.02
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68687
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario: Patient's data is not editable based on source code review
Given the 'precision-care-framework/fulfillment-service-handler' project is opened
When I review the source code
Then I confirm that patient's data is not editable
