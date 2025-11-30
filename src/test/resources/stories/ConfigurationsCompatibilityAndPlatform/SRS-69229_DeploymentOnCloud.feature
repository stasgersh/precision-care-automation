Feature: [SysRS-69229] Deployment on cloud

Narrative: The system shall be deployable remotely on AWS cloud services and GEHC Platform services with the following pre-requisites:
             - EHS Fenwood v3 (and above) cluster cloud version.
             - DHS services (EAT, Central Observability Framework, LCM - optional).
             - IDAM available.
             - Alerts set up as defined in Reference #5 with default values as defined in Cloud Formation Template.


@SRS-69229.01
@installation
@sanity
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69229
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Manual/System_Installation
Scenario: Fresh install Precision Insights deployment
Given the latest version of Precision Insights deployed on the cloud environment according to the Service manual
And the following patient was uploaded to PDS: patients/OncoTestPatient_Juno.json
When I check the version of the installation in AWS console
Then I see that the Precision Insights deployment was successfully installed with the correct version
When I login with the [DEFAULT-TEST-USER] user to Precision Insights application
And I click the “About” button on the user menu panel
Then the About modal is opened with the correct version displayed
When the "OncoTestPatient, Juno" patient is selected
Then the Patient page is loaded
And the patient banner includes the following data:
| title | value       |
| MRN   | 011-233-455 |
And the Summary view contains the following groups:
| group_name        | badge_text  |
| Diagnosis         | <N/A>       |
| Molecular profile | <N/A>       |
| Care team         | <N/A>       |
| Imaging           | Most recent |
| Oncology note     | Most recent |
| ER visit          | Most recent |
| MDT report        | Most recent |
| Treatments        | <N/A>       |
| Labs              | <N/A>       |
| Medical history   | <N/A>       |
| Comments          | Most recent |
When I navigate to the "Timeline" view
Then the patient timeline is displayed

@SRS-69229.02
@installation
@sanity
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69230
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Manual/System_Installation
Scenario: Update Precision Insights deployment
Given an older version of Precision Insights deployment has been already installed
And the older version of Precision Insights deployment gets updated to the latest version on the cloud environment according to the Service manual
And the following patient was uploaded to PDS: patients/OncoTestPatient_Juno.json
When I check the version of the installation in AWS console
Then I see that the Precision Insights deployment was successfully installed with the correct version
When I login with the [DEFAULT-TEST-USER] user to Precision Insights application
And I click the “About” button on the user menu panel
Then the About modal is opened with the correct version displayed
When the "OncoTestPatient, Juno" patient is selected
Then the Patient page is loaded
And the patient banner includes the following data:
| title | value       |
| MRN   | 011-233-455 |
And the Summary view contains the following groups:
| group_name        | badge_text  |
| Diagnosis         | <N/A>       |
| Molecular profile | <N/A>       |
| Care team         | <N/A>       |
| Imaging           | Most recent |
| Oncology note     | Most recent |
| ER visit          | Most recent |
| MDT report        | Most recent |
| Treatments        | <N/A>       |
| Labs              | <N/A>       |
| Medical history   | <N/A>       |
| Comments          | Most recent |
When I navigate to the "Timeline" view
Then the patient timeline is displayed

@SRS-69229.03
@installation
@sanity
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-69232,SysRS-68685
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Manual/System_Installation
Scenario: Uninstall Precision Insights deployment
Given a Precision Insights deployment has been already installed
And the Precision Insights deployment gets uninstalled from the cloud environment according to the Service manual
When I try to open Precision Insights application
Then the application does not load

@SRS-69229.04
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-70270
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service
Scenario: Application can be deployed to multiple availability zones
Given the precision-care-infra project is opened
When I open 'cdk.json' file
Then I see that under "account_availability_zone_names" parameter has multiple availability zones listed
When I open AWS console
And I select 'Your VPCs' menu under VPC service
And I filter for the environment being tested
Then I see multiple availability zones are listed under Resource map section
