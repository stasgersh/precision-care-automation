Feature: [SysRS-68576] Add Patient Comment

Narrative: The application users shall be able to add comments to each Patient.


Background:
Given [API] the [DEFAULT-TEST-USER] user has no comments for the following patients:
| patientID                            |
| a10e48ab-b927-9ee1-84a4-41638a84e05a |
| 6d2d9e07-4a24-7525-4661-f59ccabb02a7 |
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application

@sanity
@edge
@SRS-98.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68576,SysRS-68581
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Comment_view
Scenario: Add comment to a patient
Given the "OncoTestPatient, Juno" patient is selected
And the patient summary view is loaded
And the counter badge displays "0" on the "Comments" tab
When the user navigates to the "Comments" view
Then the comment input box is available on the Comments view
And comment is not available on the Comments view
When the user types the following text into the comment box on the Comments view: "this is a patient comment test text in E2E test"
And the user clicks on the send button next to the comment box on the Comments view
Then the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time           | content                                         |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | this is a patient comment test text in E2E test |
And the counter badge displays "1" on the "Comments" tab
When the user selects the "OncoTestPatient, Freya" patient
And the user navigates to the "Comments" view
Then the comment input box is available on the Comments view
And comment is not available on the Comments view
And the counter badge displays "0" on the "Comments" tab

@sanity
@SRS-98.02
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68576,SysRS-68581
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/Comment_view
Scenario: Patient comment is kept after the user logs out from the application
Given the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Comments" view
And comment is not available on the Comments view
When the user types the following text into the comment box on the Comments view: "this is another patient comment test text in E2E test"
And the user clicks on the send button next to the comment box on the Comments view
Then the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time           | content                                               |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | this is another patient comment test text in E2E test |
When the user logs out from OncoCare
And the [DEFAULT-TEST-USER] user logs in to OncoCare
And the user selects the "OncoTestPatient, Juno" patient
Then the patient summary view is loaded
And the counter badge displays "1" on the "Comments" tab
When the user navigates to the "Comments" view
Then the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time           | content                                               |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | this is another patient comment test text in E2E test |

@srs
@SRS-98.03
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68576
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Comment_view
Scenario: Add additional comments to a patient (Latest comment is at the bottom of the page)
Given [API] the below patient comments were added previously by the user [DEFAULT-TEST-USER]:
| patientID                            | comment_text                                          |
| a10e48ab-b927-9ee1-84a4-41638a84e05a | some test patient comment text was written previously |
And the "OncoTestPatient, Juno" patient is selected
And the patient summary view is loaded
And the counter badge displays "1" on the "Comments" tab
And the user navigated to the "Comments" view
Then the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time           | content                                               |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | some test patient comment text was written previously |
When the user types the following text into the comment box on the Comments view: "the patient Mr. (@#-%+!) is getting: better and better?"
And the user clicks on the send button next to the comment box on the Comments view
Then the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time           | content                                                 |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | some test patient comment text was written previously   |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | the patient Mr. (@#-%+!) is getting: better and better? |
And the counter badge displays "2" on the "Comments" tab
When the user types the following text into the comment box on the Comments view: "repeat exam in 2 weeks!!"
And the user clicks on the send button next to the comment box on the Comments view
Then the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time           | content                                                 |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | some test patient comment text was written previously   |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | the patient Mr. (@#-%+!) is getting: better and better? |
| [DEFAULT-TEST-USER]         | <CURRENT_DATE_AND_TIME> | repeat exam in 2 weeks!!                                |
And the counter badge displays "3" on the "Comments" tab

@srs
@edge
@SRS-98.04
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68576
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Comment_view
Scenario: Information message is displayed if patient comment is not available
Given the "OncoTestPatient, Juno" patient is selected
And the patient summary view is loaded
And the counter badge displays "0" on the "Comments" tab
When the user navigates to the "Comments" view
Then comment is not available on the Comments view
And the following message is displayed on the empty Comments view: "This patient has no comments yet."

@sanity
@edge
@Category:Positive
@Platform:WEB
@Traceability:SysRS-68576
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME2_-_Automated/Comment_view
Scenario: Patient comment box keeps text when users select "Keep editing" alert modal option
Given the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Comments" view
When the user types the following text into the comment box on the Comments view: "This message should be kept in the box."
And the user clicks on the "Summary" tab
Then an alert modal is displayed
And the alert modal contains the following title: "Leave page?"
And the alert modal contains the following message: text_files/Leave_comment_alert.txt
When the user clicks on the "Keep editing" button on the alert modal
Then the alert modal disappears
And the comment input box on the Comments view contains the following text: "This message should be kept in the box."
When the user selects the "OncoTestPatient, Freya" patient
Then an alert modal is displayed
And the alert modal contains the following title: "Leave page?"
And the alert modal contains the following message: text_files/Leave_comment_alert.txt
When the user clicks on the 'X' button on the alert modal
Then the alert modal disappears
And the comment input box on the Comments view contains the following text: "This message should be kept in the box."
When the user clicks on the "Settings" menu item on the user menu panel
Then an alert modal is displayed
And the alert modal contains the following title: "Leave page?"
And the alert modal contains the following message: text_files/Leave_comment_alert.txt
When the user clicks on the "Keep editing" button on the alert modal
Then the alert modal disappears
And comment is not available on the Comments view
And the comment input box on the Comments view contains the following text: "This message should be kept in the box."
When the user clicks on the send button next to the comment box on the Comments view
Then comment is available on the Comments view
And the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time           | content                                 |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>             | This message should be kept in the box. |

@srs
@Category:Positive
@Platform:WEB
@Traceability:SysRS-68576
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional/Functional_Service/Comment_view
Scenario: Patient comment box does not keep text when users select "Leave page" alert modal option
Given the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Comments" view
When the user types the following text into the comment box on the Comments view: "This message should not be kept in the box."
And the user clicks on the "Summary" tab
Then an alert modal is displayed
And the alert modal contains the following title: "Leave page?"
And the alert modal contains the following message: text_files/Leave_comment_alert.txt
When the user clicks on the "Leave page" button on the alert modal
Then the alert modal disappears
When the user navigates to the "Comments" view
Then comment is not available on the Comments view
And the comment input box on the Comments view contains the following text: ""

@srs
@manual
@Category:Positive
@Platform:WEB
@Traceability:SysRS-68576
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Comment_view
Scenario: Patient comment box keeps text when users select "Cancel" browser alert box option
Given the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Comments" view
When the user types the following text into the comment box on the Comments view: "This message should be kept in the box."
And the user clicks on the "GE HealthCare logo" item on the user menu panel
Then a browser alert box is displayed
And the browser alert box contains the following title: "Leave site?"
And the browser alert box contains the following message: Changes you made may not be saved.
When the user clicks on the "Cancel" button on the browser alert box
Then the browser alert box disappears
When the user clicks on the browser's refresh button
Then a browser alert box is displayed
And the browser alert box contains the following title: "Reload site?"
And the browser alert box contains the following message: Changes you made may not be saved.
When the user clicks on the "Cancel" button on the browser alert box
Then the browser alert box disappears
When the user clicks on the "Sign out" menu item on the user menu panel
Then a browser alert box is displayed
And the browser alert box contains the following title: "Leave site?"
And the browser alert box contains the following message: Changes you made may not be saved.
When the user clicks on the "Cancel" button on the browser alert box
Then the browser alert box disappears
And comment is not available on the Comments view
And the comment input box on the Comments view contains the following text: "This message should be kept in the box."
When the user clicks on the send button next to the comment box on the Comments view
Then comment is available on the Comments view
And the following comments are available on the Comments view:
| author (firstname lastname) | date_and_time           | content                                 |
| [DEFAULT-TEST-USER]         | <ANY_VALUE>             | This message should be kept in the box. |

@srs
@manual
@Category:Positive
@Platform:WEB
@Traceability:SysRS-68576
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/Comment_view
Scenario: Patient comment box does not keep text when users select "Reload" browser alert box option
Given the "OncoTestPatient, Juno" patient is selected
And the user navigated to the "Comments" view
When the user types the following text into the comment box on the Comments view: "This message should not be kept in the box."
And the user clicks on the browser's refresh button
Then a browser alert box is displayed
And the browser alert box contains the following title: "Reload site?"
And the browser alert box contains the following message: Changes you made may not be saved.
When the user clicks on the "Reload" button on the browser alert
Then the browser alert box disappears
And comment is not available on the Comments view
And the comment input box on the Comments view contains the following text: ""
