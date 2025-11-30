Feature: [SysRS-68487] Summary dashboard – Expend and Collapse card view

Narrative: The system shall provide the users the ability to Expend/Collapse card view in order to provide all data related to the card for a patient in case data in the card exceeds the default size.
           Default state shall be Collapsed.


@sanity
@edge
@SysRS-68487.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68487
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
Scenario: Long Comment is displayed on Summary view
Given [API] the [DEFAULT-TEST-USER] user has no comments for the following patients:
| patientID                            |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "OncoTestPatient, Freya" patient is selected
When the user navigates to the "Comments" view
Then the comment input box is available on the Comments view
And comment is not available on the Comments view
When the user types the text from /comments/Comment_with_1999_characters.txt file into the comment input box on the Comments view
And the user clicks on the send button next to the comment box on the Comments view
When the user navigates to the "Summary" view
Then the "Comments" group has 1 card
And the 1st card in "Comments" group equals the following data:
| data_type | data                                                              |
| COMMENTS  | [source]: summary/comments_on_card_collapsed_SysRS-68487.01.table |
| LINK      | [link]: Open all comments     [date]: N/A                         |
When the user clicks on the "Show more" link at the following data on the 1st card in "Comments" group:
| data_type | data                                                              |
| COMMENTS  | [source]: summary/comments_on_card_collapsed_SysRS-68487.01.table |
Then the 1st card in "Comments" group equals the following data:
| data_type | data                                                             |
| COMMENTS  | [source]: summary/comments_on_card_expanded_SysRS-68487.01.table |
| LINK      | [link]: Open all comments     [date]: N/A                        |

@sanity
@ai_sanity
@CLP
@SysRS-68487.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68487
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Summary_View
Scenario: Long Oncology note is displayed on Summary view
Given [API] the CLP visualization is switched ON for [DEFAULT-TEST-USER] user
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
And the "Tart, Emilia" patient is selected
When the user navigates to the "Summary" view
Then the "Oncology note" group has 1 card
And the 1st card in "Oncology note" group equals the following data:
| data_type   | data                                    |
| CARD_TITLE  | Oncology note - most recent             |
| NORMAL_TEXT | Ancillary note                          |
| CLP_DATA    | [keywords]: female patient, anesthesia  |
| KEY_VALUE   | [key]: Date       [value]: Jan 14, 2016 |
When the user clicks on the "Show more" button at the following data on the 1st card in "Oncology note" group:
| data_type | data                                   |
| CLP_DATA  | [keywords]: female patient, anesthesia |
Then the 1st card in "Oncology note" group equals the following data:
| data_type   | data                                    |
| CARD_TITLE  | Oncology note - most recent             |
| NORMAL_TEXT | Ancillary note                          |
| CLP_DATA    | [keywords]: oral intake                 |
| KEY_VALUE   | [key]: Date       [value]: Jan 14, 2016 |
When the user clicks on the "Show less" button at the following data on the 1st card in "Oncology note" group:
| data_type | data                    |
| CLP_DATA  | [keywords]: oral intake |
Then the 1st card in "Oncology note" group equals the following data:
| data_type   | data                                    |
| CARD_TITLE  | Oncology note - most recent             |
| NORMAL_TEXT | Ancillary note                          |
| CLP_DATA    | [keywords]: female patient, anesthesia  |
| KEY_VALUE   | [key]: Date       [value]: Jan 14, 2016 |