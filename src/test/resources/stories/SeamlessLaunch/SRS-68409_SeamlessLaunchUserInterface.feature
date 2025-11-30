Feature: [SysRS-68409] Seamless launch user interface (SMART launch)

Narrative: The system shall provide a seamless user interface integration with the EMR to allow users to launch the application directly from the EMR.

Background:
Given the test environment was configured for SMART app launch (see README.md for details)
And the following patient was uploaded to DPSA: patients/e0DsArtctOZjV.9hY3xp57w3.json
And the tester has access to "https://fhir.epic.com/"
And I navigated to "https://fhir.epic.com/Documentation?docId=launching" page
And I clicked "Try it" button
And I configured the modal as:
| field_title                                         | value                                                                                                                   |
| Choose an app to test with                          | Precision Insight Test                                                                                                  |
| Select a patient                                    | Optime, Omar                                                                                                            |
| Enter launch URL to receive the request to your app | <<UI_URL_OF_DEPLOYMENT>>/auth/smart/launch?redirect_uri=<<UI_URL_OF_DEPLOYMENT>>/                                       |
| Enter tokens used in OAuth 2.0 context              | epicuserid=%EPICUSERID%&syslogin=%SYSLOGIN%&userprovfhirid=%USERPROVFHIRID%&userfname=%USERFNAME%&userlname=%USERLNAME% |
And I clicked "Generate URL Only"
And I copied the generated URL to the Clipboard
And I opened the Hyperdrive Web Developer Test Harness tool
And the "Open in a separate window" checkbox is not set
And I pasted the copied URL into the URL field
And I clicked on the "Navigate" button
And the patient summary view of Optime, Omar was loaded


@SRS-68409.01
@manual
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68409,SysRS-68611,SysRS-102049
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Seamless_launch
Scenario: Patient banner is displayed in Hyperdrive (iframe)
Given "AI summarization" is turned ON in user settings
And the patient banner is collapsed
And patient data is displayed in 1 line in patient banner
When I click on the "Expand" button
Then the patient banner is expanded
And patient data is displayed in 3 lines in patient banner
When I hover the value of "Histology type" in patient banner
Then the following data is displayed on a popover:
"""
serous papillary carcinoma
Sourced or produced by AI
"""
And the following root source details are displayed on a popover:
| report_title     | date         | author    | status  |
| Pathology Report | Oct 13, 2012 | No author | current |
When I unhover the value of "Histology type"
Then the popover disappears

@SRS-68409.02
@manual
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68409,SysRS-68611,SysRS-102049
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Seamless_launch
Scenario: Add and remove patient comment in Hyperdrive (iframe)
When I navigate to the Comments view
And I type the following text into the comment box on the Comments view: "test comment in hyperdrive"
And I click on the send button next to the comment box on the Comments view
Then the following comment is available on the Comments view:
| author (firstname lastname) | date_and_time           | content                    |
| Interconnect User           | <CURRENT_DATE_AND_TIME> | test comment in hyperdrive |
When I click on the 'Delete' button in the menu of the following comment:
| author (firstname lastname) | date_and_time           | content                    |
| Interconnect User           | <CURRENT_DATE_AND_TIME> | test comment in hyperdrive |
Then the Comments view is refreshed
And the following comment is not available on the Comments view:
| author (firstname lastname) | date_and_time           | content                    |
| Interconnect User           | <CURRENT_DATE_AND_TIME> | test comment in hyperdrive |

@SRS-68409.03
@manual
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68409,SysRS-68611,SysRS-102049
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Seamless_launch
Scenario: Error notification is displayed when failed to save patient comment in Hyperdrive (iframe)
Given I navigated to the Comments view
And I typed the following text into the comment box on the Comments view: "test failed to send comment in hyperdrive"
And [DEVTOOLS] - the following text pattern was added to "Network request blocking" tool:
| text pattern |
| comments     |
And [DEVTOOLS] - the "Enable network request blocking" checkbox is CHECKED in the "Network request blocking" tool
And [DEVTOOLS] - the "comments" item is selected in the list of "Enable network request blocking"
When I click on the send button next to the comment box on the Comments view
Then an error popup is displayed with the following message:
| error_title            | detailed_error_text                     |
| Failed to save Comment | Sorry, something went wrong on our end. |

@SRS-68409.04
@manual
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68409,SysRS-68611,SysRS-102049
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Seamless_launch
Scenario: Full report is displayed in a new modal in Hyperdrive (iframe)
Given I navigated to the Timeline view
And I selected the following event point on the "Pathology and Labs" swimlane:
| event_type | name_on_label                |
| MARKER     | DiagnosticReport with binary |
And the sidebar is displayed
And the sidebar header contains the following data:
| title                        | creation_date | report_type        |
| DiagnosticReport with binary | May 11, 2015  | Pathology and Labs |
And the sidebar content contains the following data:
| data_type | title                | value                         |
| KEY-VALUE | Conclusion           | To test report type: "Binary" |
| KEY-VALUE | Status               | final                         |
| LINK      | Open complete report | <N/A>                         |
When I click on the following sidebar item:
| data_type | title                |
| LINK      | Open complete report |
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title                        | creation_date | author    | status |
| DiagnosticReport with binary | May 11, 2015  | No author | final  |
And the full report modal body contains the following data:
| report_title | report_content                                                                                                      |
| From binary  | This report text is coming from a binary report. (presentedForm.url: "Binary/ecb5474e-8a45-40df-b049-69bb76a2edc1") |
And a zoom slider is available under the L3 modal header with the following values:
| zoom_slider_values |
| 100%               |
| 120%               |
| 150%               |
And the zoom slider is at 100%
And the '-' zoom button is disabled
And the '+' zoom button is enabled
When I move the zoom slider to 120%
Then the font size of the report body is increased
And the '-' zoom button is enabled
And the '+' zoom button is enabled
When I click on the 'X' button on the full report modal
Then the full report modal is not displayed on the screen

@SRS-68409.05
@manual
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68409,SysRS-68611,SysRS-102049
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Seamless_launch
Scenario: Full report is displayed in a new browser tab (external content) in Hyperdrive (iframe)
Given I navigated to the Timeline view
And I selected the following event point on the "Pathology and Labs" swimlane:
| event_type | name_on_label                  |
| MARKER     | Tissue Pathology biopsy report |
And the sidebar is displayed
And the sidebar content contains the following data:
| data_type | title                 | value                                    |
| KEY-VALUE | Conclusion            | Left axillary lymph node core biopsy: OK |
| KEY-VALUE | Status                | final                                    |
| LINK      | Open external content | <N/A>                                    |
When I click on the following sidebar item:
| data_type | title                 |
| LINK      | Open external content |
Then an alert modal is displayed
When I click on the "Continue" button on the alert modal
Then the alert modal disappears
And the landing page of https://fhir.epic.com/ is loaded in a new window

@SRS-68409.06
@manual
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68409,SysRS-68611,SysRS-102049
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Seamless_launch
Scenario: Full report is downloaded in Hyperdrive (iframe) if it is available in pdf format
Given I navigated to the Timeline view
And I selected the following event point on the "Biomarkers" swimlane:
| event_type | name_on_label   |
| MARKER     | Report with pdf |
And the sidebar is displayed
And the sidebar content contains the following data:
| data_type | title           | value                |
| KEY-VALUE | Conclusion      | To test pdf download |
| KEY-VALUE | Status          | final                |
| LINK      | Download report | <N/A>                |
When I click on the following sidebar item:
| data_type | title           |
| LINK      | Download report |
Then the following report is opened in a new window: L3/Report with pdf.pdf

@SRS-68409.07
@manual
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68409,SysRS-68611,SysRS-102049
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Seamless_launch
Scenario: Trend chart is displayed on Timeline view in Hyperdrive (iframe)
Given I navigated to the Timeline view
And I selected the following event point on the "Biomarkers" swimlane:
| event_type | name_on_label                       | date_on_timeline_axis |
| MARKER     | Prostate specific antigen 2.5 ng/mL | Jan 23, 2007          |
And the sidebar is displayed
And trend chart is displayed on the sidebar
And the following data point has a halo on the chart:
| data_point_value | data_point_date |
| 2.5              | Jan 23, 2007    |
When I hover the following data point on the chart:
| data_point_value | data_point_date |
| 2.5              | Jan 23, 2007    |
Then a vertical line is displayed along with the hovered point
And a tooltip is displayed above the chart with data:
"""
2.5
Jan 23, 2007
"""
And a vertical line is displayed in the timeline at the following event:
| event_type | name_on_label                       | date_on_timeline_axis |
| MARKER     | Prostate specific antigen 2.5 ng/mL | Jan 23, 2007          |

@SRS-68409.08
@manual
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68409,SysRS-68611,SysRS-102049
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Seamless_launch
Scenario: Filter events on Timeline view in Hyperdrive (iframe)
Given I navigated to the Timeline view
And I marked the following event as important on "Biomarkers" swimlane:
| event_type | name_on_label                            | date_on_timeline_axis |
| MARKER     | HER2 Receptor Negative (qualifier value) | Nov 13, 2008          |
When I set filter options in Filter modal to see only the Important events
Then the following event is displayed on the timeline:
| event_type | name_on_label                            | date_on_timeline_axis |
| MARKER     | HER2 Receptor Negative (qualifier value) | Nov 13, 2008          |
And the corresponding event on the macro-navigator is filled in
And the not important events are not displayed on the timeline
And the not important event points on the macro-navigator are not filled in

@SRS-68409.09
@manual
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68409,SysRS-68611,SysRS-102049
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Seamless_launch
Scenario: Popover is displayed above truncated text in Hyperdrive (iframe)
Given [API] the following treatment protocol was uploaded: treatment/simple_treatment_multiple_protocols.json
And the "Timeline" view is selected
When I click on the "View mode" button on the Timeline toolbar
And I select "Treatment adherence" mode
And I select the following protocol: "Test treatment with multiple protocols"
And I open the "Trial arm" dropdown
And I hover over the item that starts with: "Phase 2/3 Adaptive Trial of [Vaccine Name]"
Then a popover is displayed with the following text: "Phase 2/3 Adaptive Trial of [Vaccine Name] for the Prevention of Influenza in Adults Aged 65 and Older"

@SRS-68409.11
@manual
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68409,SysRS-68611,SysRS-102049
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Seamless_launch
Scenario: DICOM images can be viewed in Hyperdrive (iframe)
Given the following DICOM viewer is configured: https://d16vr74iv12cb5.cloudfront.net/stone-webviewer/index.html?study={acsn} (see README.md for details)
When I click on the "View all 26 events" on the Progressive Summarization panel
And I click on the 'Images' button in the row of CT ABDOMEN AND PELVIS at Feb 02, 2008 on the events list modal
Then the DICOM Viewer is opened in a modal
When I click on the "Back to Event list" button in the header of the modal
Then I navigated back to the events list modal

@SRS-68409.12
@manual
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68409,SysRS-68611,SysRS-102049
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Seamless_launch
Scenario: Entire summary can be exported in Hyperdrive (iframe)
Given "AI summarization" is turned ON in user settings
And the 'What has changed' section has the title: "Since last oncology encounter (Sep 09, 2006)"
When I click the "More" button in the Summary view toolbar
And I click on the "Download" option in the More menu
And I click on the "Entire summary" option in the More menu
And I save the file
Then the saved pdf file contains the download date and time
And the saved pdf file contains the exporter name: "User, Interconnect"
And the saved file contains the following header: GE HealthCare | CareIntellect for Oncology
And the following patient banner data is displayed above the summary cards in the pdf file:
| parameter_in_patient_banner |
| Patient name                |
| MRN                         |
| DOB                         |
| Gender                      |
| Weight                      |
| ECOG                        |
| Diagnosis                   |
| Histology type              |
| Gleason score               |
| Stage - initial             |
| Most Recent Tx              |
| BMI                         |
| BSA                         |
| PSA                         |
| Molecular profile           |
| ER                          |
| HER2                        |
And the 'Overview' section is present in the pdf file with the following sub-sections:
| section                                      |
| Brief history                                |
| Since last oncology encounter (Sep 09, 2006) |
| Summary of recent reports and notes          |
And the following Summary groups are available in the pdf file and have the following icons:
| group_name        |
| Diagnosis         |
| Molecular profile |
| Care team         |
| Imaging           |
| Oncology note     |
| ER visit          |
| MDT report        |
| Treatments        |
| Labs              |
And the following Summary groups are NOT available in the pdf file:
| group_name      |
| Comments        |
| Medical history |

@SRS-68409.13
@manual
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68409,SysRS-68611,SysRS-102049
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Seamless_launch
Scenario: Open full report from summary card in Hyperdrive (iframe)
When I click on the "Show more" button on the "Key events" card on the Summary view
And I click on the following item on the "Key events" card:
| report_title     | report_date  |
| Pathology Report | Jan 12, 2008 |
Then the full report modal is displayed on the screen
And the full report modal header contains the following data:
| title            | creation_date | author    | status  |
| Pathology Report | Jan 12, 2008  | No author | current |
And the full report modal body contains the following data: "this is just a test"
When I click on the 'X' button on the full report modal
Then the full report modal is not displayed on the screen
When I click on the "Full report" button in the footer of the "Key events" card
Then a multi-report modal is displayed with title: "Full report"

@SRS-68409.14
@manual
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68409,SysRS-68611,SysRS-102049
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Seamless_launch
Scenario: Navigate to Timeline view from summary card in Hyperdrive (iframe)
When I click on the "Show on timeline" button in the footer of "Imaging history" card on the Summary view
Then I am navigated to the Timeline view
And the sidebar is displayed
And the sidebar header contains the following data:
| title                                                    | creation_date | report_type |
| Examination of joint under image intensifier (procedure) | Sep 09, 2022  | Imaging     |

@SRS-68409.15
@manual
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68409,SysRS-68611,SysRS-102049
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Seamless_launch
Scenario: Navigate to Treatment and response view from summary card in Hyperdrive (iframe)
When I click on the "Patient status" button in the "Treatments" group on the Summary view
Then the Treatment and response view is loaded
When I select the following imaging study for study A:
| name                                                              | date         |
| Positron emission tomography with computed tomography (procedure) | Sep 08, 2022 |
And I select the following imaging study for study B:
| name                                                     | date         |
| Examination of joint under image intensifier (procedure) | Sep 09, 2022 |
And I set the following time range above the multiple trend chart:
| start_date   | end_date |
| May 04, 2005 | <TODAY>  |
And I hover the "Body Weight" badge next to the trend chart
Then the "Body Weight" line is colorized on the timeline
And the "Prostate specific antigen" line is grayed out
When I hover the Radiation Therapy badge above the chart
Then the following data is displayed on a popover:
"""
Start of therapy: Aug 15, 2018
Most recent therapy: Aug 15, 2018
Radiation
"""

@SRS-68409.16
@manual
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68409,SysRS-68611,SysRS-102049
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Seamless_launch
Scenario: Treatment eligibility is displayed in Hyperdrive (iframe)
Given the "testdata/configuration/repo/default/treatment-eligibility/prophet.json" trial config was uploaded:
| service                     | method | endpoint_path                                             | payload                   | content_type     |
| treatmentEligibilityService | PUT    | <treatmentEligibilityServiceUrl>/config/trial/NCT06044857 | <content_of_prophet.json> | application/json |
And "AI summarization" is turned ON in user settings
When I click on the "Clinical trials" button in the "Treatments" group
Then the "Trials" view is displayed
When I select the "PSMA PET Response Guided SabR in High Risk Pca" trial card on the left side panel
Then the "PSMA PET Response Guided SabR in High Risk Pca" trial is displayed
When I click on the Trial ID "NCT06044857" in the header
Then "PSMA PET Response Guided SabR in High Risk Pca" trial is opened in a new window
When I click on the 'opposite arrows' icon next to the "Type" header name in the table
Then the criteria table is sorted by "Type" column which has a "upward" pointing arrow
When I click on the 'filter' icon next to the "Type" header name in the table
Then "Filters" list is displayed with the following items:
| filters   |
| inclusion |
| exclusion |
When I select the "inclusion" filter option
Then the table contains only the items where Type equals to "inclusion"
When I click on the down arrow on the left side of the row starts with: "Staging 68Ga PMSA-11 PET -CT or -MRI performed"
Then the details of the criteria are expanded

@SRS-68409.17
@manual
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68409,SysRS-68611,SysRS-102049
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Seamless_launch
Scenario: Medical history view is displayed in Hyperdrive (iframe)
Given I navigated to the "Medical history" view
When I click on the "Allergies and intolerances" menu item on the left side
Then the "Allergies and intolerances" section is displayed on the screen
When I click on the "Expand" button next to "Completed medications"
Then the Completed medications section is opened
And the Completed medications table is sorted by "Prescribed" column which has a "downward" pointing arrow
When I click on the "Prescribed" column header in the Medications table
Then the Completed medications table is sorted by "Prescribed" column which has a "upward" pointing arrow
And the Completed medications table contains the following sorted items:
| Medication name                                                    | Dosage                             | Route           | Frequency                                      | Notes                                                     | Prescribed   |
| NDA020503 200 ACTUAT Albuterol 0.09 MG/ACTUAT Metered Dose Inhaler | Every four hours                   | Subcutaneous    | 5 times a day from Jan 25, 2015 - Jan 28, 2015 | [completed] Another note                                  | Jan 29, 2015 |
| PACLITAXEL (TAXOL) IN 0.9% NACL CHEMO INFUSION                     | 108 MG                             | Intravenous use | <EMPTY>                                        | [completed] Test note 1, this is from MedicationStatement | Nov 08, 2015 |
| Acetaminophen 300 MG / Codeine Phosphate 15 MG Oral Tablet         | Every four hours (qualifier value) | Oral use        | 4 times a day from Jan 30, 2016 - Mar 15, 2016 | [completed] Note test 2, this is from MedicationRequest   | Jan 31, 2016 |

@SRS-68409.18
@manual
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68409,SysRS-68611,SysRS-102049
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Seamless_launch
Scenario: Trends view is displayed in Hyperdrive (iframe)
Given I navigated to the "Trends" view
And the "Grid view" is selected
And the following trend charts are displayed in grid view:
| chart_name                        |
| Body Weight (kg)                  |
| Prostate specific antigen (ng/mL) |
When I select "List view"
Then the following trend charts are displayed in list view:
| chart_name                        |
| Body Weight (kg)                  |
| Prostate specific antigen (ng/mL) |
When I hover the following data point on the "Prostate specific antigen (ng/mL)" chart:
| data_point_value | data_point_date |
| 2.5              | Jan 23, 2007    |
Then a vertical line is displayed along with the hovered point
And a tooltip is displayed above the chart with data:
"""
2.5
Jan 23, 2007
"""
And a vertical line is displayed at the same position on "Body Weight (kg)" chart as on the "Prostate specific antigen (ng/mL)" chart

@SRS-68409.19
@manual
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68409,SysRS-68611,SysRS-102049
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Seamless_launch
Scenario: User can see the used 3rd party licenses on the About modal in Hyperdrive (iframe)
When I click the "About" button on the user menu panel
Then the About modal is displayed
And I see the following information: "This application uses third-party and open source components."
And the "third-party and open source components" section is a link
When I click on "third-party and open source components." section of the text
Then I am navigated to a new window
And I see the licenses listed in a txt file

@SRS-68409.20
@manual
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68409,SysRS-68611,SysRS-102049
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Seamless_launch
Scenario: User menu contains the following items: About, Help, Settings, Profile and Sign out in Hyperdrive (iframe)
When I check the user menu items on the left side
And the following items are available in the user menu:
| menu_items        |
| About             |
| Help              |
| Settings          |
| Interconnect User |
| Sign out          |

@SRS-68409.21
@manual
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68409,SysRS-68611,SysRS-102049
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Seamless_launch
Scenario: User settings are stored when the application is started from EMR
Given I collapsed the Progressive summarization panel
And I navigated to the Timeline view
And I selected the following event on "Imaging" swimlane:
| title                                  | date_on_timeline_axis |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011          |
And I created the following comment on Comments view: "saved comment test"
And I navigated to the user settings view
And I changed the swimlane order to:
| swimlane_names     |
| Imaging            |
| Encounters         |
| Pathology and Labs |
| Treatment and Plan |
| Biomarkers         |
| Uncategorized      |
And I signed out from the app
When I sign in again using the seamless launch flow via https://fhir.epic.com/
Then the patient summary view of Optime, Omar is loaded
And the Progressive summarization panel is collapsed
When I navigate to the Timeline view
Then the swimlanes are displayed in the following order:
| swimlane_names     |
| Imaging            |
| Encounters         |
| Pathology and Labs |
| Treatment and Plan |
| Biomarkers         |
| Uncategorized      |
And the sidebar is displayed on the view
And the sidebar header contains the following data:
| title                                  | creation_date | report_type |
| CT THORAX ABDOMEN PELVIS WITH CONTRAST | Sep 28, 2011  | Imaging     |
When I navigate to the Comments view
Then the following comment is available on Comments view: "saved comment test"

@SRS-68409.22
@manual
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68409,SysRS-68611,SysRS-102049
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Seamless_launch
Scenario: User can sign out from the app in Hyperdrive (iframe)
When I click on the "Sign out" button on the user menu panel
Then I am navigated to the 'CareIntellect for Oncology' splash screen
