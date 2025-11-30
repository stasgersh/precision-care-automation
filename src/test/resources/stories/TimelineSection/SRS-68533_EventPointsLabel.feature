Feature: [SysRS-68533] Event Points Label

Narrative: The system shall be able to display the label of the event points.


@srs
@SRS-35.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68533
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_section
Scenario Outline: All events have a label on the timeline
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
When the user selects the "<patient_name>" patient
And the user navigates to the "Timeline" view
Then all events in all swimlanes have a label
Examples:
| patient_name           |
| OncoTestPatient, Freya |
| OncoTestPatient, Eleanor470   |
