Feature: [SysRS-68546] Lab Results In Tabular Format

Narrative: The system shall be able to display lab results in tabular format on the side panel.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application

@sanity
@edge
@SRS-40.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68546
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Detailed_Info
Scenario: Lab results can be displayed in tabular format on the side panel
Given the "OncoTestPatient, Al" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the "Pathology and Labs" swimlane contains the following events:
| event_type | text_on_event_point | name_on_label                                                    |
| MARKER     | <EMPTY>             | Complete blood count (hemogram) panel - Blood by Automated count |
| CLUSTER    | 2                   | 2 events                                                         |
When the user clicks on the following event point on the "Pathology and Labs" swimlane:
| event_type | name_on_label                                                    |
| MARKER     | Complete blood count (hemogram) panel - Blood by Automated count |
Then the sidebar is displayed
And the sidebar header contains the following data:
| title                                                            | creation_date | report_type        |
| Complete blood count (hemogram) panel - Blood by Automated count | Nov 10, 2014  | Pathology and Labs |
And the sidebar content contains the following data:
| data_type | title                | value        |
| TABLE     | Results              | <DATA_TABLE> |
And the sidebar contains the following "Results" table:
| Test name                                                                | Result | Unit    | Flag    | Reference range |
| Hemoglobin [Mass/volume] in Blood                                        | 16.526 | g/dL    | <EMPTY> | <EMPTY>         |
| MCV [Entitic volume] by Automated count                                  | 81.809 | fL      | <EMPTY> | <EMPTY>         |
| Platelets [#/volume] in Blood by Automated count                         | 213.75 | 10*3/uL | <EMPTY> | <EMPTY>         |
| Hematocrit [Volume Fraction] of Blood by Automated count                 | 48.439 | %       | <EMPTY> | <EMPTY>         |
| Erythrocyte distribution width [Entitic volume] by Automated count       | 42.3   | fL      | <EMPTY> | <EMPTY>         |
| Platelet mean volume [Entitic volume] in Blood by Automated count        | 11.128 | fL      | <EMPTY> | <EMPTY>         |
| MCHC [Mass/volume] by Automated count                                    | 34.022 | g/dL    | <EMPTY> | <EMPTY>         |
| MCH [Entitic mass] by Automated count                                    | 30.109 | pg      | <EMPTY> | <EMPTY>         |
| Leukocytes [#/volume] in Blood by Automated count                        | 6.3928 | 10*3/uL | <EMPTY> | <EMPTY>         |
| Erythrocytes [#/volume] in Blood by Automated count                      | 5.2367 | 10*6/uL | <EMPTY> | <EMPTY>         |
| Platelet distribution width [Entitic volume] in Blood by Automated count | 302.57 | fL      | <EMPTY> | <EMPTY>         |
