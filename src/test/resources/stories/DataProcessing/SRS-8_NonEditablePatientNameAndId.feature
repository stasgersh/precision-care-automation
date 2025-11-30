@safety
Feature: [SysRS-68389] Non-Editable Patient Name, Id

Narrative: The application shall ensure that Patient's name, ID are non-editable through the application.


@SRS-8.01
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68389,SysRS-68687
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Data_processing_and_Fulfillment
Scenario: Patient's name and ID are not editable on OncoCare UI
Given I logged in to OncoCare application
When I select the "OncoTestPatient, Juno" patient
Then there isn't any option to change the patient's name in the patient list
And there isn't any option to change the patient's name in the patient banner
And there isn't any option to change the patient's ID in the patient list
And there isn't any option to change the patient's ID in the patient banner

@SRS-8.03
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68389
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Data_processing_and_Fulfillment
Scenario: Patient's name and ID are not editable based on source code review
Given the 'oncology-digital-budapest/onchron-core' project is opened
When I review the source code
Then I confirm that patient's name and ID are not editable
