Feature: [SysRS-68539] Users See Only Event Tags Added by Themselves

Narrative: The system users shall only see the Event Labels added by themselves.


Background:
Given [API] the following events were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/8b554e91-4ccb-46b5-803f-1e9ccdfe3dbb |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/8b554e91-4ccb-46b5-803f-1e9ccdfe3dbb |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/8b554e91-4ccb-46b5-803f-1e9ccdfe3dbb |
And [API] the following events were not marked as important for [TEST-USER-2] user:
| patientID                            | root_resource_of_the_event                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/8b554e91-4ccb-46b5-803f-1e9ccdfe3dbb |
And [API] default labels are added to the following event by the [TEST-USER-2] user:
| patientID                            | root_resource_of_the_event                             | labels            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/8b554e91-4ccb-46b5-803f-1e9ccdfe3dbb | Molecular testing |
And [API] the [TEST-USER-2] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                             |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | DocumentReference/8b554e91-4ccb-46b5-803f-1e9ccdfe3dbb |

@sanity
@SRS-90.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68539
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use
Scenario: Users see only the event tags added by themselves
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the following event point is selected on the "Encounters" swimlane:
| event_type | name_on_label  |
| MARKER     | Progress Notes |
And the sidebar is displayed
And the sidebar header contains the following badges:
| badge_type     | badge_text       |
| CLASSIFICATION | Outpatient visit |
When the user adds the following labels to the opened event:
| label_name     |
| MDT discussion |
| Recurrence     |
Then the sidebar header contains the following badges:
| badge_type     | badge_text       |
| CLASSIFICATION | Outpatient visit |
| LABEL          | MDT discussion   |
| LABEL          | Recurrence       |
When the user logs out from OncoCare
And the [TEST-USER-2] user logs in to OncoCare
And the user selects the "OncoTestPatient, Juno" patient
And the user navigates to the "Timeline" view
And the user clicks on the following event point on the "Encounters" swimlane:
| event_type | name_on_label  |
| MARKER     | Progress Notes |
Then the sidebar header contains the following badges:
| badge_type     | badge_text        |
| CLASSIFICATION | Outpatient visit  |
| LABEL          | Molecular testing |
