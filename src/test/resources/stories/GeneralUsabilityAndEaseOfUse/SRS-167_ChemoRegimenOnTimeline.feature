Feature: [SysRS-68649] Chemo Regimen on Timeline

Narrative: The system shall be able to display regimen for Chemotherapy on the timeline.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the browser size is set to: width=1920, height=1080


@SRS-167.01
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68649
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Regimen_for_Chemotherapy_on_timeline
Scenario: Chemotherapy regimen is displayed on the timeline based on the first and last event of the regimen
Given the "OncoTestPatient, Dolores" patient is selected
When I navigate to the "Timeline" view
And I zoom out the timeline
Then I see a chemo regimen on the "Treatment and Plan" swimlane with the following parameters:
| event_description    | event_name                              | event_date   |
| First included event | cyclophosphamide (CYTOXAN) chemo tablet | Dec 09, 2007 |
| Last included event  | Chemo regimen test event                | Mar 09, 2013 |

@SRS-167.02
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68649
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Regimen_for_Chemotherapy_on_timeline
Scenario: Chemo regimen is kept on the timeline when an event is opened from the regimen
Given the "OncoTestPatient, Dolores" patient is selected
And I navigated to the "Timeline" view
And I zoomed out the timeline
And I saw a chemo regimen on the "Treatment and Plan" swimlane with the following parameters:
| event_description    | event_name                              | event_date   |
| First included event | cyclophosphamide (CYTOXAN) chemo tablet | Dec 09, 2007 |
| Last included event  | Chemo regimen test event                | Mar 09, 2013 |
When I select the following event on the "Treatment and Plan" swimlane:
| event_name                              | event_date   |
| cyclophosphamide (CYTOXAN) chemo tablet | Dec 09, 2007 |
Then the sidepanel is opened
And the regimen on the "Treatment and Plan" swimlane is still displayed with the following parameters:
| event_description    | event_name                              | event_date   |
| First included event | cyclophosphamide (CYTOXAN) chemo tablet | Dec 09, 2007 |
| Last included event  | Chemo regimen test event                | Mar 09, 2013 |

@SRS-167.03
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68649
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Regimen_for_Chemotherapy_on_timeline
Scenario: Chemo regimen title is displayed when the chemo regimen is hovered
Given the "OncoTestPatient, Dolores" patient is selected
And I navigated to the "Timeline" view
And I zoomed out the timeline
And I saw a chemo regimen on the "Treatment and Plan" swimlane with the following parameters:
| event_description    | event_name                              | event_date   |
| First included event | cyclophosphamide (CYTOXAN) chemo tablet | Dec 09, 2007 |
| Last included event  | Chemo regimen test event                | Mar 09, 2013 |
When I hover above the described chemo regimen
Then a tooltip is displayed above the regimen with the text: "Test Treatment Plan"

@SRS-167.04
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68649
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Regimen_for_Chemotherapy_on_timeline
Scenario: Chemo regimen is highlighted when an event is hovered which is part of the regimen
Given the "OncoTestPatient, Dolores" patient is selected
And I navigated to the "Timeline" view
And I zoomed out the timeline
And I saw a chemo regimen on the "Treatment and Plan" swimlane with the following parameters:
| event_description    | event_name                              | event_date   |
| First included event | cyclophosphamide (CYTOXAN) chemo tablet | Dec 09, 2007 |
| Included event       | cyclophosphamide (CYTOXAN) chemo tablet | Dec 09, 2009 |
| Included event       | Suture open wound                       | Mar 22, 2011 |
| Last included event  | Chemo regimen test event                | Mar 09, 2013 |
When I hover the following event point on the "Treatment and Plan" swimlane:
| event_name                              | event_date   |
| cyclophosphamide (CYTOXAN) chemo tablet | Dec 09, 2007 |
Then the regimen which contains the hovered event becomes colorized
And a tooltip is displayed above the regimen with the text: "Test Treatment Plan"
When I hover the following event point on the "Treatment and Plan" swimlane:
| event_name                              | event_date   |
| cyclophosphamide (CYTOXAN) chemo tablet | Dec 09, 2009 |
Then the regimen which contains the hovered event becomes colorized
And a tooltip is displayed above the regimen with the text: "Test Treatment Plan"
When I hover the following event point on the "Treatment and Plan" swimlane:
| event_name               | event_date   |
| Chemo regimen test event | Mar 09, 2013 |
Then the regimen which contains the hovered event becomes colorized
And a tooltip is displayed above the regimen with the text: "Test Treatment Plan"
When I hover the following event point on the "Treatment and Plan" swimlane:
| event_name        | event_date   |
| Suture open wound | Mar 22, 2011 |
Then the regimen which contains the hovered event DOES NOT become colorized
And tooltip is NOT displayed above the regimen

@SRS-167.05
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68649
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Regimen_for_Chemotherapy_on_timeline
Scenario: Chemo regimen time range is kept when events in chemo regimen are filtered
Given the "OncoTestPatient, Dolores" patient is selected
And I navigated to the "Timeline" view
And I zoomed out the timeline
And only the following event was marked as important on the "Treatment and Plan" swimlane:
| event_name                              | event_date   |
| cyclophosphamide (CYTOXAN) chemo tablet | Dec 09, 2009 |
And I saw a chemo regimen on the "Treatment and Plan" swimlane with the following parameters:
| event_description    | event_name                              | event_date   |
| First included event | cyclophosphamide (CYTOXAN) chemo tablet | Dec 09, 2007 |
| Included event       | cyclophosphamide (CYTOXAN) chemo tablet | Dec 09, 2009 |
| Included event       | Suture open wound                       | Mar 22, 2011 |
| Last included event  | Chemo regimen test event                | Mar 09, 2013 |
When I filter for the important events in the timeline toolbar
Then the only visible event on the "Treatment and Plan" swimlane is the following:
| event_name                              | event_date   |
| cyclophosphamide (CYTOXAN) chemo tablet | Dec 09, 2009 |
And the original time range was kept of the regimen which contains the important event

@SRS-167.06
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68649
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Regimen_for_Chemotherapy_on_timeline
Scenario: Chemo regimens can be connected in event cluster (Multiple partial regimens clustered)
Given the "OncoTestPatient, Dolores" patient is selected
When I navigate to the "Timeline" view
And I zoom out the timeline
Then I see that the right side of the "First test regimen" chemo regimen is connected to the following event on the "Treatment and Plan" swimlane:
| event_type | event_name | event_date                  |
| CLUSTER    | 2 events   | Jan 09, 2021 - Feb 01, 2021 |
And I see that the left side of the "Another regimen for E2E tests" chemo regimen is connected to the following event on the "Treatment and Plan" swimlane:
| event_type | event_name | event_date                  |
| CLUSTER    | 2 events   | Jan 09, 2021 - Feb 01, 2021 |
When I zoom in the timeline until the following cluster is ungrouped:
| event_type | event_name | event_date                  |
| CLUSTER    | 2 events   | Jan 09, 2021 - Feb 01, 2021 |
Then I see that the "First test regimen" chemo regimen ends at "Jan 09, 2021" and contains the following event:
| event_type | event_name                           | event_date   |
| MARKER     | bortezomib (VELCADE) chemo injection | Jan 09, 2021 |
And I see that the "Another regimen for E2E tests" chemo regimen starts at "Feb 01, 2021" and contains the following event:
| event_type | event_name                              | event_date   |
| MARKER     | cyclophosphamide (CYTOXAN) chemo tablet | Feb 01, 2021 |

@SRS-167.07
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68649
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Regimen_for_Chemotherapy_on_timeline
Scenario: Chemo regimens can be connected in event cluster (full + partial)
Given the "OncoTestPatient, Dolores" patient is selected
When I navigate to the "Timeline" view
And I zoom out the timeline
Then I see that the following event is surrounded by the "Plan: OP MYELOMA CYBORDEX PO" chemo regimen on the "Treatment and Plan" swimlane:
| event_type | event_name | event_date                  |
| CLUSTER    | 3 events   | Oct 11, 2004 - Dec 01, 2004 |
And I see that the right side of the "Onco plan" chemo regimen is connected to the following event on the "Treatment and Plan" swimlane:
| event_type | event_name | event_date                  |
| CLUSTER    | 3 events   | Oct 11, 2004 - Dec 01, 2004 |
When I zoom in the timeline until the following cluster is ungrouped:
| event_type | event_name | event_date                  |
| CLUSTER    | 3 events   | Oct 11, 2004 - Dec 01, 2004 |
Then I see that the "Onco plan" chemo regimen ends at "Oct 11, 2004" and contains the following event:
| event_type | event_name               | event_date   |
| MARKER     | Chemo regimen test event | Oct 11, 2004 |
And I see that the following event is surrounded by the "Plan: OP MYELOMA CYBORDEX PO" chemo regimen on the "Treatment and Plan" swimlane:
| event_type | event_name | event_date                  |
| CLUSTER    | 2 events   | Dec 01, 2004 - Dec 01, 2004 |
