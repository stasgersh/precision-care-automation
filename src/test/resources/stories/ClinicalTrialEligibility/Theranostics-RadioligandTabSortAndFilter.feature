@cte
@theranostics
@RadioligandTabSortAndFilter
Feature: [SysRS-103451] Theranostics Criteria list Filter and Sort

Narrative: The system shall support Filter and Sort options on criteria list as defined in SysRS-69188.

Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
When the user selects the "Booker, FabianCopy7K_0" patient
And the user navigates to the "Summary" view

@RadioligandTab
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69188,SysRS-103450,SysRS-103451,SysRS-103454,SysRS-103455
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_-_Automated/Theranostics/FilterAndSort
Scenario: Display all criteria filter options in Radioligand tab
When the user submits PUT request to update the existing patientID:b5f3375a-11a8-4946-baa6-bb4d0ef3ade9 with following details:
| config_id | config_type   | trial_configuration_file      |
| PLUVICTO6 | treatment     | pluvicto/PLUVICTO6-All_Matching_Scores |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue treatment/PLUVICTO6.json
When the following treatments have been configured for the patients:
| patient                                | list_of_treatments   |
| b5f3375a-11a8-4946-baa6-bb4d0ef3ade9   | PLUVICTO6            |
Then the received HTTP status code is 200
And the "message" property in the response matches the value: Ingestion process started
And the mandatory cte-ai-ai-criteria-matching Sfn processes are finished within 900 seconds with status Succeeded for property patients[0].id and propertyValue b5f3375a-11a8-4946-baa6-bb4d0ef3ade9
And the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property eventType and propertyValue PATIENT_VERSION_COMPLETED
When the user submit GET request to get treatment by treatment ID: PLUVICTO6
And the user submit GET request to get Eligibility configuration by configType: treatment, configID: PLUVICTO6, patientId: b5f3375a-11a8-4946-baa6-bb4d0ef3ade9
Then the "eligibility" property in the response contains the value: NO_MATCH
And the following patient sync banner appears in 90000 milliseconds:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
When the user clicks on the button on the following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
Then there is a Treatments card with clinical trial options
And the "Treatments" group has 7 cards
When the user clicks on 'Radioligand therapy options' description :(ConfigType=treatment) A prescription medicine used to treat men with PSMA+ metastatic castration-resistant prostate cancer (mCRPC).
And the user navigates to the "Radioligand Tx" view
Then the Radioligand view is displayed
When the user selects the following configuration id: PLUVICTO6 from the list of treatments
And the user clicks on the filter icon in the 'Status' column
Then the 'Status' column's pop-up list view contains the following values:
| status            |
| Inclusion met     |
| Inclusion not met |
| Exclusion not met |
| Exclusion met     |
| Missing           |

@RadioligandTab
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69188,SysRS-103450,SysRS-103451,SysRS-103454,SysRS-103455
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_-_Automated/Theranostics/FilterAndSort
Scenario: Criteria is sortable by Status
When there is a Treatments card with clinical trial options
Then the "Treatments" group has 7 cards
When the user clicks on 'Radioligand therapy options' description :(ConfigType=treatment) A prescription medicine used to treat men with PSMA+ metastatic castration-resistant prostate cancer (mCRPC).
And the user navigates to the "Radioligand Tx" view
Then the Radioligand view is displayed
When the user selects the following configuration id: PLUVICTO6 from the list of treatments
And the user clicks on the sort arrow for the "Status" column
Then the criteria table is sorted by "Status" column which has an "up" pointing arrow
And the list of criteria for the "Status" column was sorted in ascending order:
| column_ordered_values |
| Missing |
| Exclusion met |
| Inclusion not met |
| Inclusion met |
| Inclusion met |
| Inclusion met |
| Exclusion not met |
| Exclusion not met |
When the user clicks on the sort arrow for the "Status" column
Then the criteria table is sorted by "Status" column which has an "down" pointing arrow
And the list of criteria for the "Status" column was sorted in descending order:
| column_ordered_values |
| Exclusion not met |
| Exclusion not met |
| Inclusion met     |
| Inclusion met     |
| Inclusion met     |
| Inclusion not met |
| Exclusion met     |
| Missing           |

@RadioligandTab
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69188,SysRS-103450,SysRS-103451,SysRS-103454,SysRS-103455
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_-_Automated/Theranostics/FilterAndSort
Scenario: Criteria is sortable by Type
When there is a Treatments card with clinical trial options
Then the "Treatments" group has 7 cards
When the user clicks on 'Radioligand therapy options' description :(ConfigType=treatment) A prescription medicine used to treat men with PSMA+ metastatic castration-resistant prostate cancer (mCRPC).
And the user navigates to the "Radioligand Tx" view
Then the Radioligand view is displayed
When the user selects the following configuration id: PLUVICTO6 from the list of treatments
And the user clicks on the sort arrow for the "Type" column
Then the criteria table is sorted by "Type" column which has an "up" pointing arrow
And the list of criteria for the "Type" column was sorted in ascending order:
| column_ordered_values |
| Exclusion |
| Exclusion |
| Exclusion |
| Inclusion |
| Inclusion |
| Inclusion |
| Inclusion |
| Inclusion |
When the user clicks on the sort arrow for the "Type" column
Then the criteria table is sorted by "Type" column which has an "down" pointing arrow
And the list of criteria for the "Type" column was sorted in descending order:
| column_ordered_values |
| Inclusion |
| Inclusion |
| Inclusion |
| Inclusion |
| Inclusion |
| Exclusion |
| Exclusion |
| Exclusion |

@RadioligandTab
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69188,SysRS-103450,SysRS-103451,SysRS-103454,SysRS-103455
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_-_Automated/Theranostics/FilterAndSort
Scenario: Criteria is sortable by "Eligibility value(s)"
When there is a Treatments card with clinical trial options
Then the "Treatments" group has 7 cards
When the user clicks on 'Radioligand therapy options' description :(ConfigType=treatment) A prescription medicine used to treat men with PSMA+ metastatic castration-resistant prostate cancer (mCRPC).
And the user navigates to the "Radioligand Tx" view
Then the Radioligand view is displayed
When the user selects the following configuration id: PLUVICTO6 from the list of treatments
And the user clicks on the sort arrow for the "Eligibility value(s)" column
Then the criteria table is sorted by "Eligibility value(s)" column which has an "up" pointing arrow
And the list of criteria for the "Eligibility value(s)" column was sorted in ascending order:
| column_ordered_values |
| 1     |
| 67    |
| male  |
| The exclusion criterion |
| The exclusion criterion specifies |
| The inclusion criterion requires  |
| The inclusion criterion requires  |
| The patient has a confirmed diagnosis of metastatic  |
When the user clicks on the sort arrow for the "Eligibility value(s)" column
Then the criteria table is sorted by "Eligibility value(s)" column which has an "down" pointing arrow
And the list of criteria for the "Eligibility value(s)" column was sorted in descending order:
| column_ordered_values |
| The patient has a confirmed diagnosis of metastatic  |
| The inclusion criterion requires  |
| The inclusion criterion requires  |
| The exclusion criterion specifies |
| The exclusion criterion |
| male  |
| 67    |
| 1     |



@RadioligandTab
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69188,SysRS-103450,SysRS-103451,SysRS-103454,SysRS-103455
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_-_Automated/Theranostics/FilterAndSort
Scenario: Default behaviour when none selected
When the user submits PUT request to update the existing patientID:b5f3375a-11a8-4946-baa6-bb4d0ef3ade9 with following details:
| config_id | config_type   | trial_configuration_file      |
| PLUVICTO6 | treatment     | pluvicto/PLUVICTO6-All_Matching_Scores |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue treatment/PLUVICTO6.json
And there is a Treatments card with clinical trial options
And the "Treatments" group has 7 cards
When the user clicks on 'Radioligand therapy options' description :(ConfigType=treatment) A prescription medicine used to treat men with PSMA+ metastatic castration-resistant prostate cancer (mCRPC).
And the user navigates to the "Radioligand Tx" view
Then the Radioligand view is displayed
When the user selects the following configuration id: PLUVICTO6 from the list of treatments
Then the user verify that "Filter By" frame doesn't appears on page

@RadioligandTab
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69188,SysRS-103450,SysRS-103451,SysRS-103454,SysRS-103455
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_-_Automated/Theranostics/FilterAndSort
Scenario: Filter-out criteria by status
When the user submits PUT request to update the existing patientID:b5f3375a-11a8-4946-baa6-bb4d0ef3ade9 with following details:
| config_id | config_type   | trial_configuration_file      |
| PLUVICTO6 | treatment     | pluvicto/PLUVICTO6-All_Matching_Scores |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue treatment/PLUVICTO6.json
And there is a Treatments card with clinical trial options
And the "Treatments" group has 7 cards
When the user clicks on 'Radioligand therapy options' description :(ConfigType=treatment) A prescription medicine used to treat men with PSMA+ metastatic castration-resistant prostate cancer (mCRPC).
And the user navigates to the "Radioligand Tx" view
Then the Radioligand view is displayed
When the user selects the following configuration id: PLUVICTO6 from the list of treatments
And the user clicks on the filter icon in the 'Status' column
And the user selects Inclusion met status from the filter's list
Then the criteria view displays criteria with the following statuses:
| status   |
| Inclusion met  |
When the user click on "Clear all" button on "Filter By" frame
And the user clicks on the filter icon in the 'Status' column
And the user selects Inclusion not met status from the filter's list
Then the criteria view displays criteria with the following statuses:
| status   |
| Inclusion not met  |
When the user click on "Clear all" button on "Filter By" frame
And the user clicks on the filter icon in the 'Status' column
And the user selects Missing status from the filter's list
Then the criteria view displays criteria with the following statuses:
| status   |
| Missing  |
When the user click on "Clear all" button on "Filter By" frame
And the user clicks on the filter icon in the 'Status' column
And the user selects Exclusion met status from the filter's list
Then the criteria view displays criteria with the following statuses:
| status   |
| Exclusion met  |
When the user click on "Clear all" button on "Filter By" frame
And the user clicks on the filter icon in the 'Status' column
And the user selects Inclusion met status from the filter's list
Then the criteria view displays criteria with the following statuses:
| status   |
| Inclusion met  |
When the user click on "Clear all" button on "Filter By" frame
And the user clicks on the filter icon in the 'Status' column
And the user selects Exclusion not met status from the filter's list
Then the criteria view displays criteria with the following statuses:
| status   |
| Exclusion not met  |

@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69188,SysRS-103450,SysRS-103451,SysRS-103454,SysRS-103455
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_-_Automated/Theranostics/FilterAndSort
Scenario Outline: Remove filters one by one from "Filter By" frame and check all criteria were displayed
When the user submits PUT request to update the existing patientID:b5f3375a-11a8-4946-baa6-bb4d0ef3ade9 with following details:
| config_id | config_type   | trial_configuration_file      |
| PLUVICTO6 | treatment     | pluvicto/PLUVICTO6-All_Matching_Scores |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue treatment/PLUVICTO6.json
And there is a Treatments card with clinical trial options
And the "Treatments" group has 7 cards
When the user clicks on 'Radioligand therapy options' description :(ConfigType=treatment) A prescription medicine used to treat men with PSMA+ metastatic castration-resistant prostate cancer (mCRPC).
And the user navigates to the "Radioligand Tx" view
Then the Radioligand view is displayed
When the user selects the following configuration id: PLUVICTO6 from the list of treatments
And the user clicks on the filter icon in the 'Status' column
Then the 'Status' column's pop-up list view contains the following values:
| status            |
| Inclusion met     |
| Inclusion not met |
| Missing           |
| Exclusion met     |
| Exclusion not met |
When the user selects all filters at once the following filters:
| status            |
| Inclusion met     |
| Inclusion not met |
| Missing           |
| Exclusion met     |
| Exclusion not met |
Then the user verify that "Filter By" frame contains only selected filters:
| status            |
| Inclusion met     |
| Inclusion not met |
| Missing           |
| Exclusion met     |
| Exclusion not met |
When the user click on "Clear all" button on "Filter By" frame
Then the criteria view displays criteria with the following statuses:
| status            |
| Inclusion met     |
| Inclusion not met |
| Missing           |
| Exclusion met     |
| Exclusion not met |

@RadioligandTab
@sanity
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69188,SysRS-103450,SysRS-103451,SysRS-103454,SysRS-103455
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_-_Automated/Theranostics/FilterAndSort
Scenario Outline: Filter-out criteria by type
When the user submits PUT request to update the existing patientID:b5f3375a-11a8-4946-baa6-bb4d0ef3ade9 with following details:
| config_id | config_type   | trial_configuration_file      |
| PLUVICTO6 | treatment     | pluvicto/PLUVICTO6-All_Matching_Scores |
Then the mandatory criteria-criteria-calculator Sfn processes are finished within 900 seconds with status Succeeded for property key and propertyValue treatment/PLUVICTO6.json
And there is a Treatments card with clinical trial options
And the "Treatments" group has 7 cards
When the user clicks on 'Radioligand therapy options' description :(ConfigType=treatment) A prescription medicine used to treat men with PSMA+ metastatic castration-resistant prostate cancer (mCRPC).
And the user navigates to the "Radioligand Tx" view
Then the Radioligand view is displayed
When the user selects the following configuration id: PLUVICTO6 from the list of treatments
Then each treatment has its related matching confidence score:
|treatment_id   | Matching_Confidence_Score   |
|PLUVICTO6      | No match                    |
When the user clicks on the filter icon in the 'Type' column
Then the 'Type' column's pop-up list view contains the following values:
| type      |
| Inclusion |
| Exclusion |
When the user selects each time only one <type> from the filter's list
Then the criteria view displays criteria with filtered type - <type> only
When the user click on "Clear all" button on "Filter By" frame
Then the user verify that "Filter By" frame doesn't appears on page
Examples:
| type      |
| Inclusion |
| Exclusion |