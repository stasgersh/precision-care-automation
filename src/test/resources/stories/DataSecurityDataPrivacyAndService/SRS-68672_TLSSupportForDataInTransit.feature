@safety
Feature: [SysRS-68672] Encryption - data in transit (TLS 1.2)

Narrative: The system shall ensure authentication and encryption of data in transit by supporting TLS 1.2 and above to all communication (Internal and external).


@SRS-68672.01
@srs
@manual
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68672
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Encryption
Scenario: Internal and external communication supports TLS 1.2 and above
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the user opens browsers Developer Tools
And the user navigates to the "Network" tab in Developer Tools
When the user captures network requests by navigating on tabs and loading patient data
And the user checks the "Scheme" column for all captured requests
Then all network requests use the "https" scheme
When the user navigates to the "Security" tab in Developer Tools
Then the page displays a message indicating "This page is secure (valid HTTPS)"
And the connection details shows the TLS version as "TLS 1.2" or "TLS 1.3"

@SRS-68672.02
@srs
@manual
@Platform:WEB
@Category:Negative
@Traceability:SysRS-68672
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Manual_-_Non-Functional/Non_Functional_Service/Data_Security_Data_Privacy_And_Service/Encryption
Scenario: Application does not support communication with TLS 1.0 and TLS 1.1
When a connection attempt is made to the application using TLS 1.0 (see README.md for details)
Then the connection attempt fails with a handshake error
When a connection attempt is made to the application using TLS 1.1 (see README.md for details)
Then the connection attempt fails with a handshake error
