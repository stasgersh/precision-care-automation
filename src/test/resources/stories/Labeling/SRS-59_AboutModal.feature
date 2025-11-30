Feature: [SysRS-68658] About modal

Narrative: The system shall have "About" info, which includes:
           - GE HealthCare logo
           - Product name
           - Part number (Device Identifier (Product or Catalog number))
           - Software version number
           - Electronic IFU icon and link to the eIFU
           - Consult instructions for use symbol
           - Read warnings
           - Copyright information and disclaimer
           - Manufacturer icon, name and address
           - Manufacturer date
           - NotifyMe catalog reference
           - Used Licences (Open source and commercial software disclosure)


@SRS-59.01
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68658,SysRS-69611
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Timeline_Time_Range_Options
Scenario: User can see the mandatory elements on the About modal
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
When the user clicks the "About" button on the user menu panel
Then the About modal is displayed
And the following labeling information is displayed on the modal:
| name                 | reference_image                                       | reference_txt               |
| GE Healthcare logo   | testdata/aboutModalRefs/ico-ge-healthcare-logo-32.svg | <N/A>                       |
| Product name         | <N/A>                                                 | CareIntellect for Oncology  |
| Version number       | <N/A>                                                 | 1.0.0.<build_number>        |
And information about the used licenses is displayed as: "This application uses third-party and open source components."
And copyright information is displayed at the bottom of the modal as: "© 2025 GE HealthCare. GE is a trademark of General Electric Company used under trademark license."

@SRS-59.02
@manual
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68658
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Functional/Functional_Service/General_Usability_And_Ease_Of_Use/Timeline_Time_Range_Options
Scenario: User can see the used 3rd party licenses on the About modal
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the Patient page is loaded
When the user clicks the "About" button on the user menu panel
Then the About modal is displayed
And the user sees the following information: "This application uses third-party and open source components."
And the "third-party and open source components." section is a link
When the user clicks on "third-party and open source components." section of the text
Then the user is navigated to a new browser tab or a new file download (depends on the browser settings of the device)
And the user sees the licenses listed in a txt file
When the user compares the content of the file with the reference file (/testdata/text_files/3rd_party_licenses.txt)
Then the content of the txt file is the same as the reference file's
