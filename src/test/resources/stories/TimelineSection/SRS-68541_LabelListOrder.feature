Feature: [SysRS-68541] Label list order

Narrative: The system label list shall be ordered in alphabetic order.


@srs
@SRS-132.01
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68541
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Timeline_View
Scenario: New custom labels are added to the list in alphabetical order
Given the following labels are not present in the label list:
| label_name          |
| *LabelTestSpecCh    |
| ZLabelTest          |
| ÉLabelTest          |
| AiLabelTest         |
| 00LabelTestNum      |
| DLabelTest          |
| AcLabelTest         |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Casper" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
When the user clicks on the following event point on the "Encounters" swimlane:
| event_type | name_on_label    |
| MARKER     | Body Weight 51.2 |
And the user clicks on the 'Label' button on the sidebar header
Then the label selection panel is opened
When the user creates the following labels:
| label_name          |
| AiLabelTest         |
| ZLabelTest          |
| ÉLabelTest          |
| 00LabelTestNum      |
Then the labels are in the following order:
| label_name          |
| 00LabelTestNum      |
| AiLabelTest         |
| ÉLabelTest          |
| ZLabelTest          |
When the user creates the following label:
| label_name          |
| *LabelTestSpecCh    |
| DLabelTest          |
| AcLabelTest         |
Then the labels are in the following order:
| label_name          |
| *LabelTestSpecCh    |
| 00LabelTestNum      |
| AcLabelTest         |
| AiLabelTest         |
| DLabelTest          |
| ÉLabelTest          |
| ZLabelTest          |
When the user closes and reopens the label selection panel
Then the labels are in the following order:
| label_name          |
| *LabelTestSpecCh    |
| 00LabelTestNum      |
| AcLabelTest         |
| AiLabelTest         |
| DLabelTest          |
| ÉLabelTest          |
| ZLabelTest          |