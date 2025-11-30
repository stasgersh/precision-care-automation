Feature: [SysRS-68569] Trends Reference Range

Narrative: The system shall be able to visualize the reference ranges on the Trend cards to easily notice the outlier datapoints.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded

@SRS-146.01
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68569
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Trends_View
Scenario: Trends dashboard can display the data points with reference range
Given I selected the "Marker, Patient" patient
And I navigated to the "Trends" dashboard
When I open the "More" menu
Then I see the following options:
| option_title | option_name           |
| View options | Reference Range       |
| Tools        | Reset to default view |
When I select the "Reference range" option
Then I see that Reference Range area greyed out for all the graph
And I see the "Reference range" legend on the bottom right side of each graph

@SRS-146.02
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68569
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Trends_View
Scenario: Trends dashboard can display the data points without reference range
Given I selected the "Marker, Patient" patient
And I navigated to the "Trends" dashboard
And the "Reference Range" was turned on
When I open the "More" menu
And I de-selects the "Reference range" option
Then the reference range areas disappeared from the charts
And the reference range legends disappeared from the charts

@SRS-146.03
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68569
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Trends_View
Scenario: Trends dashboard can display the data points with Normal/Minimum values/Maximum values/Smallest size of reference ranges
Given I selected the "Marker, Patient" patient
And I navigated to the "Trends" dashboard
When I open the "More" menu
And I select on "Reference range" option
Then I see the graphs with following reference ranges:
| chart_title          | reference_range                     | reference_range_sample_image (example only, dates can be changed) |
| WBC (x10E3/uL)       | ref range with min and max values   | testdata\trends\ReferenceRange\Normal.png                         |
| Hemoglobin (g/dL)    | ref range range with only min value | testdata\trends\ReferenceRange\Min.png                            |
| Creatinine (mg/dL)   | ref range range with only max value | testdata\trends\ReferenceRange\Max.png                            |
| Platelets (x10E3/uL) | smallest vizualized ref range       | testdata\trends\ReferenceRange\Small.png                          |

@SRS-146.04
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68569
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Trends_View
Scenario: Information message is displayed on the chart when reference range is turned on but reference data is not available
Given I selected the "OncoTestPatient, Trendenza" patient
And I navigated to the "Trends" dashboard
When I open the "More" menu
And I selects the "Reference range" option
Then I see the following message under the "Body Weight (kg)" graph: "No reference range available"

@SRS-146.05
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68569
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Trends_View
Scenario: Trends dashboard can display the data points with reference range in List view
Given I selected the "Marker, Patient" patient
And I navigated to the "Trends" dashboard
And I selected the "List view" option in the Trends toolbar
When I open the "More" menu
And I select the "Reference range" option
Then I see that Reference Range area greyed out for all the graph
And I see the "Reference range" legend on the left side of each graph
