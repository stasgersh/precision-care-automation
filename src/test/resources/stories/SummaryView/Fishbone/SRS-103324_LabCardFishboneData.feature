Feature: [SysRS-103324] Lab Card Fishbone Data

Narrative: The system shall display the following information in the fishbone lab diagrams:
           - Indicator that lab result is for the most recent lab
           - Date of lab test
           - Lab parameter
           - Values of the lab result (in case a values is not available in a lab result, it shall be indicated with empty field. Information )
           - Lab result unit
           - Reference range (if available)
           - Warning in case value is out of reference range


@SRS-103324.01
@sanity
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-103324
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Summary_View
Scenario: Flag out-of-range lab values with warning icon
Given I logged in to CareIntellect for Oncology application as [DEFAULT-TEST-USER]
And I selected the "TEST_FISHBONE, Felix" patient
And the patient summary view was loaded
When I navigate to the "Chemistry" fishbone card in the "Labs" group
Then the following lab values are flagged with a yellow warning icon:
| lab_parameter | lab_value |
| Chloride      | 113       |
| Glucose       | 60.01     |
| Creatinine    | 3.1       |
When I hover over the "Creatinine" lab value in the fishbone
Then a static trend chart is displayed showing the out of reference range values
When I navigate to the "Hematology" fishbone card in the "Labs" group
Then the following lab values are flagged with a yellow warning icon:
| lab_parameter | lab_value |
| Auto WBC      | 11.7      |
| Hgb           | 18.3      |
| Platelets     | 543       |
When I hover over the "Platelets" lab value in the fishbone
Then a static trend chart is displayed showing the out of reference range values

@SRS-103324.02
@sanity
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-103324
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Summary_View
Scenario: Display popover with or without reference range on hover
Given I logged in to CareIntellect for Oncology application as [DEFAULT-TEST-USER]
And I selected the "TEST_FISHBONE, Felix" patient
And the patient summary view was loaded
When I navigate to the "Chemistry" fishbone card in the "Labs" group
And I hover over the "Potassium" lab value in the fishbone diagram
Then the popover displays "No reference range available"
When I hover over the "Creatinine" lab value in the fishbone diagram
Then the static trend chart displays the reference range "0.5 - 1.5 mg/dL" across the entire time axis.

@SRS-103324.03
@sanity
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-103324
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Summary_View
Scenario: Display static trend chart with missing unit
Given I logged in to CareIntellect for Oncology application as [DEFAULT-TEST-USER]
And I selected the "DEV, FISHBONE-Large" patient
And the patient summary view was loaded
And I navigated to the "Chemistry" fishbone card in the "Labs" group
And the "Chemistry" fishbone displays the "CO2" lab value as "26" with the following text below it: "(no unit found)"
When I hover over the "CO2" lab value in the fishbone diagram
Then the popover displays the label as "CO2 (no unit found)"
And the popover displays "No reference range available"
And a static trend chart is displayed
And the following data point is displayed on the chart:
| Date         | Value |
| Nov 21, 2016 | 26    |

@SRS-103324.04
@sanity
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-103324
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Summary_View
Scenario: Display empty cells for missing lab values
Given I logged in to CareIntellect for Oncology application as [DEFAULT-TEST-USER]
And I selected the "TEST_FISHBONE, Felix" patient
And the patient summary view was loaded
And I navigated to the "Chemistry" fishbone card in the "Labs" group
Then the first value in the fishbone diagram is empty
When I hover over the empty value in the fishbone diagram
Then no popover is displayed
And a caveat is shown explaining the missing data as: "Some fields may be empty due to unavailability of data or because repeat tests do not cover all lab values, resulting in an incomplete fishbone diagram."
When I navigate to the "Hematology" fishbone card in the "Labs" group
Then the "Hematology" fishbone does not have any empty values
And NO caveat is shown below the fishbone diagram as: "Some fields may be empty due to unavailability of data or because repeat tests do not cover all lab values, resulting in an incomplete fishbone diagram."

@SRS-103324.05
@sanity
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-103324
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Summary_View
Scenario: User can navigate to the patient timeline from fishbone card and see the data in fishbone structure on the sidebar
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_FISHBONE, Felix" patient is selected
And the user navigated to the "Summary" view
And I navigated to the "Chemistry" fishbone card in the "Labs" group
And the fishbone was displayed with the following lab values:
| parameter  | value | unit   | warning_icon |
| Sodium     | <N/A> | <N/A>  | No           |
| Chloride   | 113   | mmol/L | Yes          |
| BUN        | 9.8   | mg/dL  | No           |
| Potassium  | 6.6   | mEq/L  | No           |
| CO2        | 28.7  | mEq/L  | No           |
| Creatinine | 3.1   | mg/dL  | Yes          |
| Glucose    | 60.01 | mg/dL  | Yes          |
And the 'Show on timeline' button is enabled in the footer of the below card:
| group_name | card_index_in_group |
| Labs       | 0                   |
| Labs       | 1                   |
When the user clicks on the 'Show on timeline' button in the footer of the below card:
| group_name | card_index_in_group |
| Labs       | 0                   |
Then the patient timeline is loaded
And the sidebar is displayed
And the sidebar header contains the following data:
| title                 | creation_date | report_type        |
| Basic metabolic panel | Nov 16, 2015  | Pathology and Labs |
And the sidebar contains the data in following fishbone structure: testdata/fishbone/Chemistry.png
And the fishbone is displayed with the following lab values on the sidebar:
| parameter  | value | unit   | warning_icon |
| Sodium     | <N/A> | <N/A>  | No           |
| Chloride   | 113   | mmol/L | Yes          |
| BUN        | 9.8   | mg/dL  | No           |
| Potassium  | 6.6   | mEq/L  | No           |
| CO2        | 28.7  | mEq/L  | No           |
| Creatinine | 3.1   | mg/dL  | Yes          |
| Glucose    | 60.01 | mg/dL  | Yes          |
And the sidebar contains the following caveat below the fishbone diagram: "Some fields may be empty due to unavailability of data or because repeat tests do not cover all lab values, resulting in an incomplete fishbone diagram."

@SRS-103324.06
@sanity
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-103324
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Summary_View
Scenario: Include abbreviated labels in PDF export
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_FISHBONE, Felix" patient is selected
And the user navigated to the "Summary" view
When the user clicks the "More" button on the Navigation toolbar
And the user clicks on the "Download" option in the View options
And the user clicks on the "Entire summary" option in the sub-menu in the View options
When I open the saved PDF
Then the PDF contains the following fishbone cards in the "Labs" group:
| card_title | card_date             | badge       | information_text                                                                                                                                         |
| Chemistry  | Nov 16, 2015 01:00 AM | Most recent | Some fields may be empty due to unavailability of data or because repeat tests do not cover all lab values, resulting in an incomplete fishbone diagram. |
| Hematology | Nov 16, 2015 01:00 AM | Most recent | <N/A>                                                                                                                                                    |
And the "Chemistry" fishbone card in PDF matches the following structure: testdata/fishbone/Chemistry.png
And the "Chemistry" fishbone card in PDF contains the following values with abbreviated labels:
| abbreviated_label | lab_value | unit  | warning_icon |
| <N/A>             | <N/A>     | <N/A> | No           |
| Cl                | 113       | mEq   | Yes          |
| BUN               | 9.8       | mg/dL | No           |
| K                 | 6.6       | mEq/L | No           |
| CO2               | 28.7      | mEq/L | No           |
| Cr                | 3.1       | mg/dL | Yes          |
| Glu               | 60.01     | mg/dL | Yes          |
And the "Hematology" fishbone card in PDF matches the following structure: testdata/fishbone/Hematology.png
And the "Hematology" fishbone card in PDF contains the following values with abbreviated labels:
| abbreviated_label | lab_value | unit     | warning_icon |
| WBC               | 11.7      | x10E3/mL | Yes          |
| Hb                | 18.3      | g/dL     | Yes          |
| HCT               | 35.4      | %        | No           |
| Plt               | 543       | x10E3/uL | Yes          |
And the PDF does not contain any caveat below the fishbone diagrams
And no popover or static trend chart is displayed in the PDF

@SRS-103324.07
@sanity
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-103324
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Summary_View
Scenario: Fishbone card is updated when new lab data is available for the patient
Given [API] the following resource are not available in PDS:
| resourceFilePath                             |
| patients/resource/Felix_Metabolic_Panel.json |
| patients/resource/Felix_CBC.json             |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the page is reloaded
And the "TEST_FISHBONE, Felix" patient is selected
And the summary view is loaded
When I navigate to the "Labs" group
Then the "Chemistry" fishbone is displayed with the following lab values:
| parameter  | value | unit   | warning_icon |
| Sodium     | <N/A> | <N/A>  | No           |
| Chloride   | 113   | mmol/L | Yes          |
| BUN        | 9.8   | mg/dL  | No           |
| Potassium  | 6.6   | mEq/L  | No           |
| CO2        | 28.7  | mEq/L  | No           |
| Creatinine | 3.1   | mg/dL  | Yes          |
| Glucose    | 60.01 | mg/dL  | Yes          |
And the "Hematology" fishbone is displayed with the following lab values:
| parameter | value | unit     | warning_icon |
| WBC       | 11.7  | x10E3/mL | Yes          |
| Hb        | 18.3  | g/dL     | Yes          |
| HCT       | 35.4  | %        | No           |
| Plt       | 543   | x10E3/uL | Yes          |
When [API] the following resources are uploaded to PDS:
| patient_resource                     | resource                                     |
| patients/resource/Felix_Patient.json | patients/resource/Felix_Metabolic_Panel.json |
| patients/resource/Felix_Patient.json | patients/resource/Felix_CBC.json             |
Then [API] an optional ETL process is started in 60 seconds and finished within 180 seconds with status Succeeded for patient a001cdff-d9ca-41fa-rem1-a2222340a000
And the following patient sync banner appears in ${change.detection.timeout.in.millisec} milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then the summary view is reloaded
And the following card is present in the "Labs" group:
| card_title | card_date             | badge       | information_text |
| Chemistry  | Apr 18, 2016 04:00 PM | Most recent | <N/A>            |
| Hematology | Jan 01, 2023 01:00 AM | Most recent | <N/A>            |
And the "Chemistry" fishbone is displayed with the following lab values:
| parameter  | value | unit   | warning_icon |
| Sodium     | 142   | mEq/L  | No           |
| Chloride   | 102   | mmol/L | No           |
| BUN        | 15    | mg/dL  | No           |
| Potassium  | 4     | mEq/L  | No           |
| CO2        | 25    | mEq/L  | No           |
| Creatinine | 1     | mg/dL  | No           |
| Glucose    | 65    | mg/dL  | No           |
And the "Hematology" fishbone is displayed with the following lab values:
| parameter | value | unit     | warning_icon |
| WBC       | 10.9  | x10E3/mL | No           |
| Hb        | 17.5  | g/dL     | No           |
| HCT       | 37    | %        | No           |
| Plt       | 451   | x10E3/uL | Yes          |

@SRS-103324.08
@sanity
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-103324
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Summary_View
Scenario: Static trend chart displays previous data points when hovering over a fishbone value - timeline view- same unit
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_FISHBONE, Felix" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the following event is available on the "Pathology and Labs" swimlane:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 6                   | 6 events      |
When the user clicks on the following event point on the "Pathology and Labs" swimlane:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 6                   | 6 events      |
And the user selects the following event from the cluster pill:
| event_name            | event_date   |
| Basic metabolic panel | Nov 02, 2015 |
Then the sidebar is displayed
When the user hovers on the "CO2" lab value in the fishbone diagram on the sidebar
Then a static trend chart is displayed showing the following data points:
| Date         | Value |
| Nov 02, 2015 | 26.7  |
When the user selects the following event from the cluster pill:
| event_name            | event_date   |
| Basic metabolic panel | Nov 09, 2015 |
And the user hovers on the "CO2" lab value in the fishbone diagram on the sidebar
Then a static trend chart is displayed showing the following data points:
| Date         | Value |
| Nov 02, 2015 | 26.7  |
| Nov 09, 2015 | 27.7  |
When the user selects the following event from the cluster pill:
| event_name            | event_date   |
| Basic metabolic panel | Nov 16, 2015 |
And the user hovers on the "CO2" lab value in the fishbone diagram on the sidebar
Then a static trend chart is displayed showing the following data points:
| Date         | Value |
| Nov 02, 2015 | 26.7  |
| Nov 09, 2015 | 27.7  |
| Nov 16, 2015 | 28.7  |

@SRS-103324.09
@sanity
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-103324
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Summary_View
Scenario: Static trend chart can only display trends for lab values with same unit - timeline view - different unit
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_FISHBONE, Felix" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the following event is available on the "Pathology and Labs" swimlane:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 6                   | 6 events      |
When the user clicks on the following event point on the "Pathology and Labs" swimlane:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 6                   | 6 events      |
And the user selects the following event from the cluster pill:
| event_name            | event_date   |
| Basic metabolic panel | Nov 09, 2015 |
Then the sidebar is displayed
And the sidebar contains the following fishbone:
| parameter  | value | unit    | warning_icon |
| Sodium     | 141.8 | mEq/L   | No           |
| Chloride   | 10    | mmol/dL | Yes          |
| BUN        | 8.8   | mg/dL   | No           |
| Potassium  | 5.6   | mEq/L   | No           |
| CO2        | 27.7  | mEq/L   | No           |
| Creatinine | 2.1   | mg/dL   | No           |
| Glucose    | 89.2  | mg/dL   | No           |
When the user hovers on the "Chloride" lab value in the fishbone diagram on the sidebar
Then the popover displays the label as "Chloride (mmol/L)"
And a static trend chart is displayed showing the following data points:
| Date         | Value |
| Nov 09, 2015 | 10    |
When the user selects the following event from the cluster pill:
| event_name            | event_date   |
| Basic metabolic panel | Nov 16, 2015 |
Then the sidebar is displayed
And the sidebar contains the following fishbone:
| parameter  | value | unit   | warning_icon |
| Sodium     | <N/A> | <N/A>  | No           |
| Chloride   | 113   | mmol/L | Yes          |
| BUN        | 9.8   | mg/dL  | No           |
| Potassium  | 6.6   | mEq/L  | No           |
| CO2        | 28.7  | mEq/L  | No           |
| Creatinine | 3.1   | mg/dL  | Yes          |
| Glucose    | 60.01 | mg/dL  | Yes          |
When the user hovers on the "Chloride" lab value in the fishbone diagram on the sidebar
Then a static trend chart is displayed showing the following data points:
| Date         | Value |
| Nov 02, 2015 | 99    |
| Nov 16, 2015 | 113   |
And the following data points are NOT displayed on the static trend chart:
| Date         | Value |
| Nov 09, 2015 | 10    |

@SRS-103324.10
@sanity
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-103324
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Summary_View
Scenario: Static trend chart displays the same data points as the Trend view
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "TEST_FISHBONE, Felix" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the full time range is displayed on the timeline
And the following event is available on the "Pathology and Labs" swimlane:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 6                   | 6 events      |
When the user clicks on the following event point on the "Pathology and Labs" swimlane:
| event_type | text_on_event_point | name_on_label |
| CLUSTER    | 6                   | 6 events      |
And the user selects the following event from the cluster pill:
| event_name | event_date   |
| CBC        | Nov 16, 2015 |
Then the sidebar is displayed
And the sidebar contains the following fishbone:
| parameter | value | unit    | warning_icon |
| Auto WBC  | 11.7  | 10^3/mL | Yes          |
| Hgb       | 18.3  | g/dL    | Yes          |
| Platelets | 543   | 10*3/uL | Yes          |
| HCT       | 35.4  | %       | No           |
When the user hovers on the "Auto WBC" lab value in the fishbone diagram on the sidebar
Then the popover displays the label as "Auto WBC (10^3/mL)"
And a static trend chart is displayed showing the following data points:
| Date         | Value |
| Nov 02, 2015 | 9.7   |
| Nov 09, 2015 | 10.7  |
| Nov 16, 2015 | 11.7  |
When the user navigates to the "Trend" view
Then the trend view is loaded
And the trend view contains a chart for "Auto WBC"
And the "Auto WBC" chart displays the following data points:
| Date         | Value |
| Nov 02, 2015 | 9.7   |
| Nov 09, 2015 | 10.7  |
| Nov 16, 2015 | 11.7  |