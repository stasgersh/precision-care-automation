Feature: [SysRS-68659] User Manual

Narrative: The system shall provide online User Manual for the users.
             - Name of Device
             - Name and Address of the Legal Manufacturer
             - Country of Origin
             - Warnings / Precautions (if necessary)
             - Directions [Instructions] for Use
             - Indications for Use
             - Contraindications
             - Revision/control


@SRS-60.01
@srs
@manual
@Category:Positive
@Platform:WEB
@Traceability:SysRS-68659
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Documentation
Scenario: User Manual can be downloaded by clicking Help in the User menu, contains the expected information and matches the approved version
Given the user has downloaded the "CareIntellect Oncology 1.0.0 User Guide" (DOC3098983) from MWS for reference purposes
And the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
When the user clicks on the "Help" menu item on the user menu panel
Then the user guide pdf file is opened in a new tab
And the following revision is present in the document: "Version 1.0"
And the following information is present in the Legal Notices section:
| information_type                           | information                                              |
| name of device                             | GE HealthCare CareIntellect for Oncology                 |
| country of origin                          | USA                                                      |
| name and address of the legal manufacturer | GE Healthcare 500 W. Monroe Street Chicago, IL 60661 USA |
And the document contains the following sections:
| section_title                              |
| Overview                                   |
| Launching CareIntellect for Oncology       |
| AI (Artificial Intelligence) Summarization |
| Empty State                                |
| Patient Banner                             |
| Summary View                               |
| Treatment Response                         |
| Clinical Trial Eligibility                 |
| Timeline View                              |
| Treatment Adherence                        |
| Medical History                            |
| Trends                                     |
| Comments                                   |
| Patient Data Status Bar                    |
| Navigation Pane                            |
And the following section contains "Important" subsection:
| section_title | important_text                                                                                                                                                                                                |
| Overview      | GE HealthCare CareIntellect for Oncology does not interpret, alter, or modify the source data, it can only be as accurate as the source data is. Always refer to primary data sources for clinical decisions. |
When the user compares the opened user guide with the reference file
Then the content of the opened user guide matches the reference file
