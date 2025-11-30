Feature: [SysRS-68456] Open Imaging Report

Narrative: The system shall provide the user Open imaging functionality whenever imaging study is available:
             - Summary dashboard view: Cards and Progressive summarization
             - Response tracking view
             - Timeline view


@SRS-177.01
@manual
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68456
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Treatment_Response_View
Scenario: Open images of the Imaging study by clicking on 'Open images'
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
And the patient summary view is loaded
And the "Patient status" button was clicked
And the Treatment and response view is loaded
And the following imaging study was selected for study A:
| name | date         |
| AP   | Nov 15, 1990 |
And the following imaging study was selected for study B:
| name                                                               | date         |
| Computed tomography, abdomen and pelvis; with contrast material(s) | Jan 16, 2000 |
When the user clicks "Open images" of "AP" at imaging study A
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title | creation_date |
| AP    | Nov 15, 1990  |
When the user clicks on the 'X' button on the full report modal
And the user clicks "Open images" of "Computed tomography, abdomen and pelvis;with contrast material(s)" at imaging study B
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title                                                              | creation_date |
| Computed tomography, abdomen and pelvis; with contrast material(s) | Jan 16, 2000  |
And the DICOM viewer is displayed in the modal