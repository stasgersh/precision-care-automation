Feature: [SysRS-68529] Timeline Start And End

Narrative: The system patient timeline shall be able to display patient data from the actual patient’s first data point until the current day.

Remark: Level 1 Info (L1) are the most important attributes that are displayed on the timeline as the event label.

@sanity
@edge
@SRS-31.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68529
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Timeline_section
Scenario Outline: The first date on the timeline axis is the actual patient's first event date, the last date is the current or the next day
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "<patient>" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the timeline toolbar section is displayed
When the user zooms in the timeline to max resolution
When the user scrolls the timeline horizontally to the left side
Then the earliest date on the timeline is "<first_date_on_timeline>"
When the user scrolls the timeline horizontally to the end
Then the latest date on the timeline is the date of the current or the next day
Examples:
| patient                   | first_date_on_timeline |
| OncoTestPatient, Pablo44  | 1964 Jun 8             |
| OncoTestPatient, Hanna    | 2013 Jan 30            |
| OncoTestPatient, Columbus | 2010 Aug 17             |
