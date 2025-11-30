Feature: [SysRS-103323] Lab Card Fishbone Types

Narrative: The system shall display the following fishbone lab diagrams types in case any relevant value is present in the patient's most recent lab result:
            - Chemistry
            - Hematology


@SRS-103323.01
@sanity
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-103323
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Summary_View
Scenario Outline: Fishbone cards displays the most recent lab panel and caveat only displayed when there are empty values
Given I logged in to CareIntellect for Oncology application as [DEFAULT-TEST-USER]
And I selected the "TEST_FISHBONE, Felix" patient
And the patient summary view was loaded
When I navigate to the "Labs" group
Then the following card is present in the "Labs" group:
| card_title   | card_date             | badge       | information_text   |
| <card_title> | Nov 16, 2015 01:00 AM | Most recent | <information_text> |
And the card displays data in the following structure: testdata/fishbone/<card_title>.png
And the following parameters are displayed on the fishbone: <parameters>
When I hover on each value in the <card_title> fishbone
And each fishbone value is shown with its label and unit
Then last date and value displayed on the static trends chart is "Nov 16, 2015"
Examples:
| card_title | information_text                                                                                                                                         | parameters                   |
| Chemistry  | Some fields may be empty due to unavailability of data or because repeat tests do not cover all lab values, resulting in an incomplete fishbone diagram. | K, Cl, CO2, BUN, Cr, and Glu |
| Hematology | <N/A>                                                                                                                                                    | WBC, Hb, HCT, and Plt        |


