Feature: [SysRS-102049] Seamless launch - login

Narrative: The system shall provide seamless login for the user and automatically login (if the application is launched from an EMR and the user has a valid session).


@SRS-102049.01
@manual
@integration
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68409,SysRS-68410,SysRS-102049,SysRS-68611
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Integration/VPR_List_-_Manual/Functional_Service/Seamless_launch
Scenario: The app can be started from EMR
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
When I click on the "Navigate" button
Then the patient summary view of "Optime, Omar" is loaded
When I try to change the patient
Then I don't see any option to change it
When I click on the GE Healthcare logo on the user menu panel
Then the patient page is loaded
And I can't select a patient
