Feature: [SysRS-70298] Timeline configuration

Narrative: The system shall provide GEHC service user the ability to configure Timeline view with the following:
           Swimlanes Name.
           Event types on each swimlane.
           Information to display for each event type on the label and on the sidebar.
           Swimlanes Order

@SRS-70298.01
@SPR-4437
@configChange
@querySetChange
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70298
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/System_Configuration
Scenario: Service User to modify the configuration of Timeline view via API - pathology swimlane and weight events removed with order changes
Given the following request was sent to Precision Insights by service user app:
| service | method | endpoint_path                                         | payload                                                             | content_type     |
| core    | PUT    | /configs/fulfillment-query-set/timeline.onco.extended | configuration/modified_configs/queryset/timeline-onco-modified.json | application/json |
And the received HTTP status code was 204
And the following request was sent to Precision Insights by service user app:
| service | method | endpoint_path                                  | payload                                                                   | content_type     |
| core    | PUT    | /configs/representation/timeline.onco.extended | configuration/modified_configs/representation/timeline-onco-modified.json | application/json |
And the received HTTP status code was 204
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Juno" patient is selected
When the user navigates to the "Timeline" view
Then the patient timeline is loaded
When the user clicks on the 'All time' button on the timeline toolbar
Then the swimlanes are available on the timeline in the following order:
| swimlane_names   |
| Biomarkers       |
| Imaging          |
| Encounters       |
| Miscellaneous    |
| Therapy strategy |
And the following swimlanes are not available on the timeline:
| items              |
| Pathology and Labs |
| Uncategorized      |
And the following events are not available on the "Encounters" swimlane:
| event_type | name_on_label       |
| MARKER     | Body Weight 47.3 kg |

@SRS-70298.02
@configChange
@querySetChange
@Platform:Rest_API
@api
@Category:Negative
@Traceability:SysRS-70298
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Non-Functional/Non_Functional_Service/System_Configuration
Scenario Outline: Service User cannot upload an invalid timeline config via API
When the following request is sent to Precision Insights by service user app:
| service | method | endpoint_path | payload       | content_type     |
| core    | PUT    | <endpoint>    | <config_file> | application/json |
Then the received HTTP status code is <response>
Examples:
| endpoint                                              | config_file                                                             | response |
| /configs/representation/timeline.onco.extended        | configuration/invalid_configs/representation/timeline-onco-invalid.json | 500 |
| /configs/representation/timeline.onco.extended        | configuration/invalid_configs/representation/timeline-onco-invalid.json | 500 |
| /configs/fulfillment-query-set/timeline.onco.extended | configuration/invalid_configs/queryset/timeline-onco-invalid.json       | 400 |
| /configs/fulfillment-query-set/timeline.onco.extended | configuration/invalid_configs/queryset/timeline-onco-invalid.json       | 400 |
