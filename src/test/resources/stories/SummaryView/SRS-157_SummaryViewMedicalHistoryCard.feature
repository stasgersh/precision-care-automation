Feature: [SysRS-68486] Summary View Medical History Card

Narrative: Medical history card on the Summary view tab shall include links for:
             - Comorbidities
             - Medications
             - Allergies and intolerances
             - Patient history.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Juno" patient is selected
When the user navigates to the "Summary" view
Then the "Medical history" group has 1 card
And the 1st card in "Medical history" group equals the following data:
| data_type | data                                           |
| LINK      | [link]: Comorbidities              [date]: N/A |
| LINK      | [link]: Medications                [date]: N/A |
| LINK      | [link]: Allergies and intolerances [date]: N/A |
| LINK      | [link]: Patient history            [date]: N/A |


@SRS-157.01
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68486
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Navigate from Summary view to Comorbidities section on Medical History view
When the user clicks on the following data item on the 1st card in "Medical history" group:
| data_type | data                                           |
| LINK      | [link]: Comorbidities              [date]: N/A |
Then the Medical history view is displayed
And the "Comorbidities" navigation button is highlighted on Medical history view
And the "Medications" navigation button is not highlighted on Medical history view
And the "Allergies and intolerances" navigation button is not highlighted on Medical history view
And the "Patient history" navigation button is not highlighted on Medical history view
And the "Comorbidities" section is displayed in the viewport on Medical history view

@SRS-157.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68486
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Navigate from Summary view to Medications section on Medical History view
When the user clicks on the following data item on the 1st card in "Medical history" group:
| data_type | data                                           |
| LINK      | [link]: Medications                [date]: N/A |
Then the Medical history view is displayed
And the "Comorbidities" navigation button is not highlighted on Medical history view
And the "Medications" navigation button is highlighted on Medical history view
And the "Allergies and intolerances" navigation button is not highlighted on Medical history view
And the "Patient history" navigation button is not highlighted on Medical history view
And the "Medications" section is displayed in the viewport on Medical history view

@SRS-157.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68486
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Navigate from Summary view to Allergies and intolerances section on Medical History view
When the user clicks on the following data item on the 1st card in "Medical history" group:
| data_type | data                                           |
| LINK      | [link]: Allergies and intolerances [date]: N/A |
Then the Medical history view is displayed
And the "Comorbidities" navigation button is not highlighted on Medical history view
And the "Medications" navigation button is not highlighted on Medical history view
And the "Allergies and intolerances" navigation button is highlighted on Medical history view
And the "Patient history" navigation button is not highlighted on Medical history view
And the "Allergies and intolerances" section is displayed in the viewport on Medical history view

@SRS-157.04
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68486
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_Summary_View/Summary_View
Scenario: Navigate from Summary view to Patient history section on Medical History view
When the user clicks on the following data item on the 1st card in "Medical history" group:
| data_type | data                                           |
| LINK      | [link]: Patient history            [date]: N/A |
Then the Medical history view is displayed
And the "Comorbidities" navigation button is not highlighted on Medical history view
And the "Medications" navigation button is not highlighted on Medical history view
And the "Allergies and intolerances" navigation button is not highlighted on Medical history view
And the "Patient history" navigation button is highlighted on Medical history view
And the "Patient history" section is displayed in the viewport on Medical history view
