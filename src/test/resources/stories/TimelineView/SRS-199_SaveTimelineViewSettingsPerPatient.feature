Feature: [SysRS-68515] Save Timeline View Settings Per Patient

Narrative: The system shall save the following Timeline view settings on patient level:
             - Selected event (side panel open).
             - Data filters.
             - Show/hide Macro navigator.
             - Timeline zoom status.
             - Horizontal scroll position of the timeline.


@SRS-199.01
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68515,SysRS-68516,SysRS-103347
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_View
Scenario: Settings are kept when navigating between tabs
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
And the "Timeline" view is selected
When the user set the following timeline filter options:
| filter_group       | checkbox_name      | selected |
| Swimlanes          | Encounters         | true     |
| Swimlanes          | Imaging            | false    |
| Swimlanes          | Pathology and Labs | true     |
| Swimlanes          | Biomarkers         | false    |
| Swimlanes          | Treatment and Plan | true     |
| Swimlanes          | Uncategorized      | true     |
| Labels             | Has comments       | true     |
| Labels             | Adverse Event      | true     |
| Imaging modalities | CT                 | true     |
| Pathology and Labs | Labs               | true     |
Then the filter counter icon on the Filter button is displayed with number: 8
And the swimlanes are available on the timeline in the following order:
| items              |
| Encounters         |
| Pathology and Labs |
| Treatment and Plan |
| Uncategorized      |
When the user selects the "Hide Navigator" checkbox on the timeline toolbar
Then the macro-navigator is not displayed
When the user navigates to the "Summary" view
And the user navigates to the "Timeline" view
Then the patient timeline is loaded
And the macro-navigator is not displayed
And the filter counter icon on the Filter button is displayed with number: 8
And the swimlanes are available on the timeline in the following order:
| items              |
| Encounters         |
| Pathology and Labs |
| Treatment and Plan |
| Uncategorized      |
And the following filter badge is available in the filter list on the timeline toolbar:
| filter             |
| Encounters         |
| Pathology and Labs |
| Treatment and Plan |
| Uncategorized      |
| Has comments       |
| Adverse Event      |
| CT                 |
| Labs               |
When the user opens the timeline filter modal
Then the following timeline filter options are displayed on the filter modal:
| filter_group       | checkbox_name      | selected |
| Swimlanes          | Encounters         | true     |
| Swimlanes          | Imaging            | false    |
| Swimlanes          | Pathology and Labs | true     |
| Swimlanes          | Biomarkers         | false    |
| Swimlanes          | Treatment and Plan | true     |
| Swimlanes          | Uncategorized      | true     |
| Labels             | Has comments       | true     |
| Labels             | Adverse Event      | true     |
| Imaging modalities | CT                 | true     |
| Pathology and Labs | Labs               | true     |

@SRS-199.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68515,SysRS-68516,SysRS-103347
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_View
Scenario: Settings are saved per patient
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Torvald" patient is selected
And the user navigated to the "Timeline" view
And the patient timeline is loaded
And the user has selected the "Hide Navigator" checkbox on the timeline toolbar
And the macro-navigator is not displayed
And the user has set the following timeline filter options:
| filter_group       | checkbox_name      | selected |
| Swimlanes          | Encounters         | true     |
| Swimlanes          | Imaging            | false    |
| Swimlanes          | Pathology and Labs | true     |
| Swimlanes          | Biomarkers         | false    |
| Swimlanes          | Treatment and Plan | true     |
| Swimlanes          | Uncategorized      | true     |
| Labels             | Has comments       | true     |
| Labels             | Adverse Event      | true     |
| Imaging modalities | CT                 | true     |
| Pathology and Labs | Labs               | true     |
Then the filter counter icon on the Filter button is displayed with number: 8
And the following filter badge is available in the filter list on the timeline toolbar:
| filter             |
| Encounters         |
| Pathology and Labs |
| Treatment and Plan |
| Uncategorized      |
| Has comments       |
| Adverse Event      |
| CT                 |
| Labs               |
When the user selects the "OncoTestPatient, Freya" patient
And the user navigates to the "Timeline" view
Then the macro-navigator is displayed
And the filter counter icon on the Filter button is not displayed
And filter badge is not available in the filter list on the timeline toolbar
When the user opens the timeline filter modal
Then the following timeline filter options are displayed on the filter modal:
| filter_group       | checkbox_name      | selected |
| Swimlanes          | Encounters         | false    |
| Swimlanes          | Imaging            | false    |
| Swimlanes          | Pathology and Labs | false    |
| Swimlanes          | Biomarkers         | false    |
| Swimlanes          | Treatment and Plan | false    |
| Swimlanes          | Uncategorized      | false    |
| Labels             | Has comments       | false    |
| Labels             | Adverse Event      | false    |
| Imaging modalities | CT                 | false    |
| Pathology and Labs | Labs               | false    |
When the user set the following timeline filter options:
| filter_group       | checkbox_name      | selected |
| Swimlanes          | Encounters         | false    |
| Swimlanes          | Imaging            | true     |
| Swimlanes          | Pathology and Labs | false    |
| Swimlanes          | Biomarkers         | true     |
| Swimlanes          | Treatment and Plan | false    |
| Swimlanes          | Uncategorized      | false    |
| Labels             | Important          | true     |
| Labels             | Date of diagnosis  | true     |
| Imaging modalities | MR                 | true     |
| Pathology and Labs | Pathology          | true     |
Then the filter counter icon on the Filter button is displayed with number: 6
When the user selects the "OncoTestPatient, Torvald" patient (without reseting patient settings)
And the user navigates to the "Timeline" view
And the patient timeline is loaded
Then the macro-navigator is not displayed
When the user opens the timeline filter modal
Then the following timeline filter options are displayed on the filter modal:
| filter_group       | checkbox_name      | selected |
| Swimlanes          | Encounters         | true     |
| Swimlanes          | Imaging            | false    |
| Swimlanes          | Pathology and Labs | true     |
| Swimlanes          | Biomarkers         | false    |
| Swimlanes          | Treatment and Plan | true     |
| Swimlanes          | Uncategorized      | true     |
| Labels             | Has comments       | true     |
| Labels             | Adverse Event      | true     |
| Imaging modalities | CT                 | true     |
| Pathology and Labs | Labs               | true     |

@SRS-199.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68515,SysRS-68516,SysRS-68399,SysRS-103347
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Timeline_View
Scenario: Settings are kept at page reload
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the user has selected the "Hide Navigator" checkbox on the timeline toolbar
And the macro-navigator is not displayed
When the user set the following timeline filter options:
| filter_group | checkbox_name      | selected |
| Swimlanes    | Encounters         | true     |
| Swimlanes    | Imaging            | true     |
| Swimlanes    | Pathology and Labs | true     |
| Swimlanes    | Biomarkers         | false    |
| Swimlanes    | Treatment and Plan | false    |
| Swimlanes    | Uncategorized      | false    |
| Labels       | Chemo cycle        | true     |
| Labels       | Adverse Event      | true     |
| Biomarkers   | Genomic report     | true     |
Then the filter counter icon on the Filter button is displayed with number: 6
When the user clicks on the browser's refresh button
And the patient timeline is loaded
Then the macro-navigator is not displayed
And the filter counter icon on the Filter button is displayed with number: 6
And the following filter badge is available in the filter list on the timeline toolbar:
| filter             |
| Encounters         |
| Imaging            |
| Pathology and Labs |
| Chemo cycle        |
| Adverse Event      |
| Genomic report     |
When the user opens the timeline filter modal
Then the following timeline filter options are displayed on the filter modal:
| filter_group | checkbox_name      | selected |
| Swimlanes    | Encounters         | true     |
| Swimlanes    | Imaging            | true     |
| Swimlanes    | Pathology and Labs | true     |
| Swimlanes    | Biomarkers         | false    |
| Swimlanes    | Treatment and Plan | false    |
| Swimlanes    | Uncategorized      | false    |
| Labels       | Chemo cycle        | true     |
| Labels       | Adverse Event      | true     |
| Biomarkers   | Genomic report     | true     |

@SRS-199.04
@manual
@srs
@Platform:WEB
@Category:Negative
@Traceability:SysRS-68522,SysRS-103347
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Trends_View
Scenario: No error is displayed if the saving of the settings is unsuccessful
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
And the "Timeline" view is selected
And [DEVTOOLS] - the following text patterns were added to "Network request blocking" tool:
| text pattern  |
| settings      |
When [DEVTOOLS] - the user checks "Enable network request blocking" checkbox
And I set the following timeline filter options:
| filter_group       | checkbox_name      | selected |
| Swimlanes          | Encounters         | true     |
| Swimlanes          | Imaging            | true     |
| Swimlanes          | Pathology and Labs | false    |
| Swimlanes          | Biomarkers         | false    |
| Swimlanes          | Treatment and Plan | false    |
| Swimlanes          | Uncategorized      | true     |
| Labels             | Important          | true     |
| Pathology and Labs | Labs               | true     |
And I select "Hide Navigator" checkbox on the timeline toolbar
Then the filter counter icon on the Filter button is displayed with number: 5
And error message is not displayed on the screen
When [DEVTOOLS] - the user unchecks "Enable network request blocking" checkbox
And I refresh the page in the browser's toolbar
Then the page is reloaded
And the macro-navigator is displayed
And the filter counter icon on the Filter button is not displayed
And filter badge is not available in the filter list on the timeline toolbar
When the I open the timeline filter modal
Then no filter options are selected

@SRS-199.05
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68515,SysRS-68516,SysRS-103347
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Trends_View
Scenario: Saved settings are kept when the patient data is refreshed
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the "OncoTestPatient, Freya" patient is selected
And the "Timeline" view is selected
And the user has selected the "Hide Navigator" checkbox on the timeline toolbar
And the macro-navigator is not displayed
When I set the following timeline filter options:
| filter_group       | checkbox_name      | selected |
| Swimlanes          | Encounters         | true     |
| Swimlanes          | Imaging            | false    |
| Swimlanes          | Pathology and Labs | true     |
| Swimlanes          | Biomarkers         | true     |
| Swimlanes          | Treatment and Plan | false    |
| Swimlanes          | Uncategorized      | false    |
| Labels             | Chemo cycle        | true     |
| Imaging modalities | CT                 | true     |
Then the filter counter icon on the Filter button is displayed with number: 5
When [API] I upload the "OncoTestPatient, Freya" patient to PDS again ('testdata/patients/OncoTestPatient_Freya.json')
And I wait for following patient sync banner:
| banner_message_regex           | button_in_banner |
| Patient data update available. | Refresh          |
And I click on the "Refresh" button in the patient sync banner
Then the page is reloaded
And the macro-navigator is not displayed
And the filter counter icon on the Filter button is displayed with number: 5
When the user opens the timeline filter modal
Then the following timeline filter options are displayed on the filter modal:
| filter_group       | checkbox_name      | selected |
| Swimlanes          | Encounters         | true     |
| Swimlanes          | Imaging            | false    |
| Swimlanes          | Pathology and Labs | true     |
| Swimlanes          | Biomarkers         | true     |
| Swimlanes          | Treatment and Plan | false    |
| Swimlanes          | Uncategorized      | false    |
| Labels             | Chemo cycle        | true     |
| Imaging modalities | CT                 | true     |