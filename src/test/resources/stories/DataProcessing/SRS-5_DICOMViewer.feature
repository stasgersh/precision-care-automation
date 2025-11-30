@safety
Feature: [SysRS-68386] DICOM Viewer

Narrative: The application shall be able to open DICOM viewer URL.


@SRS-5.01
@integration
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68386
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Data_processing_and_Fulfillment
Scenario: DICOM images can be viewed
Given the following DICOM viewer is configured: https://d16vr74iv12cb5.cloudfront.net/stone-webviewer/index.html?study={acsn} (see README.md for details)
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
Then I see a modal open with the following title: "Computed tomography, abdomen and pelvis; with contrast material(s)"
And the DICOM Viewer is opened in a modal
