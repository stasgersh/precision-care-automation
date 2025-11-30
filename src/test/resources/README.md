# CareIntellect for Oncology E2E tests
End-to-end test for CareIntellect for Oncology app.

## Prerequisites ##

**Install tools** \
The following shall be downloaded and installed:

1. [Java: JDK 21](https://www.oracle.com/java/technologies/downloads/?er=221886#java21)
2. [Maven 3.6.3](https://archive.apache.org/dist/maven/maven-3/3.6.3/)

    - Install and configure maven with java: [Installing Apache Maven](https://maven.apache.org/install.html)
    - Configure proxy settings for Maven to be able to reach repositories outside of the GE network: [Configuring a proxy](https://maven.apache.org/guides/mini/guide-proxies.html)
        - a proxy which can be used: `PITC-Zscaler-EMEA-Amsterdam3PR.proxy.corporate.ge.com:80`

To check if you have succeeded type the `mvn -v` and
   `java --version` commands in to the CLI. The appropriate version numbers should appear.

Now, you should be able to execute automated tests from command prompt. See [Run Test](#run-test) chapter.

**Install IDE**

- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/other.html)
- [JBheave Support plugin](https://plugins.jetbrains.com/plugin/7268-jbehave-support) for IntelliJ
    - After plugin install, associate `.feature` file type to `JBeahave story files` in `Settings > File Types` menu


***Configure the project in IntelliJ***
1. Clone this repository.
2. Open the cloned repository in IntelliJ through `File > New > Project from existing sources`
3. In `Settings > Build,Execution,Deployment > Build Tools > Maven` change the `Maven home directory` to the `3.6.3`
   directory.
4. In `Settings > Build,Execution,Deployment > Compiler > Java Compiler` change the `Target bytecode version` to `21`.
5. In `Project Structure ...` change the Java version to `21` in `Modules` and `Project`.
6. In `Project Structure ...` > `Project` set language level to `21 (Preview) - String templates, unnamed classes ...`
7. Wait until Maven installs dependencies.

***!!Update application configuration sub-module!!***

1. Open a terminal in admin mode inside the `src/test/resources/testdata/configuration/scripts/` folder
2. Run the script `./update-configuration-repo.sh`
3. Note: The script downloads the latest configuration files from `configurations` repository.

## Run Test

### Preconditions ###

Before starting the E2E tests, please make sure that the users in below table are configured properly
- in`resources\testdata\users.json` file
- or in a new json file which is set in `user.data.config.file.path` env var ( e.g.: `user.data.config.file.path=testdata/custom-users.json`)

Before executing the tests, please make sure that the test users are not used by anybody else because
- one user can have only one valid session (token)
- changes in user settings can affect test results \
  (patient settings on user or patient level, e.g. store view state)

| Alias             | Initial User        | Description                                                                              |
|-------------------|---------------------|------------------------------------------------------------------------------------------|
| DEFAULT-TEST-USER | onchron-e2e-user-0  | the user has:<br/> - OncoCare app role<br /> - OncoDummyApp role (with cdip.write scope) |
| TEST-USER-1       | onchron-e2e-user-1  | the user does not have OncoCare app role                                                 |
| TEST-USER-2       | onchron-e2e-user-2  | the user has OncoCare app role                                                           |
| BACKUP-USER       | onchron_backup_user | the user has OncoCare access and used for backup and restore test cases                  |

User passwords are set in `/resources/testdata/users.json` file.

- test patients are uploaded to CDIP/EDA

- in the config service, PID configuration is the following:
  ```
      "patientIdentifier": {
        "system": "urn:oid:1.2.840.114350.1.13.0.1.7.5.737384.14",
        "type": {
            "code": "MR",
            "system": "http://terminology.hl7.org/CodeSystem/v2-0203"
        }
    }
  ```

#### Preconditions in Cloud ####

1. Test organization with test users is created in IDAM Cloud
2. Test organization subscribed to OncoCare which is under testing
3. Passwords are set for the users
4. OncoCare consent is approved for all test users (Approve for every sign in)
5. Onco Proxy service is deployed (for PDS upload)

### Run configurations ###

- set the target platform: `ehl.platform.type` (`onprem` or `cloud`)
    - in case of `cloud` platform, set `user.organization` (e.g. `user.organization=onchrontest.org`)
- set the required URL parameters in serenity.properties:
    - `ehl.box.address` (e.g. https://3.249.67.132)
    - `onchron.url.path` (e.g. `onco/onchron-main` or `/oncocare`)
    - `fhir.server.url`
- set the `webdriver.driver` property: `chrome | edge` 

Notes:
- webdrivers (Chrome/Edge) are downloaded automatically by the e2e framework based on the installed browser version
- run from IDE: run the `OncoCareTestSuite` class - in this case, all scenario with `@skip` and `@manual` tag will
  be skipped
- run with Maven:
    - `mvn clean install -Dmetafilter="-skip -manual"` \
      or
    - `mvn verify -Dmetafilter="-skip -manual"`
    - run only some selected scenarios: provide the required test IDs,
      e.g.: \
      `mvn verify -Dmetafilter="+SRS-29.01 +SRS-17.02"`
- run with headless Chrome/Edge: add `--headless=new` parameter to the `chrome.switches` property
  in `serenity.properties`
- run with custom window size: set the below parameters in `serenity.properties`
    - `serenity.browser.width`
    - `serenity.browser.height`

## Audit Log Tests ##

## Deployment configuration in AWS for Lambda ##

### EAT v1 ###
1. In AWS console navigate to Lambda service and filter for representation-representation lambda for tested environment
2. Under Configuration tab select Environment variables
3. Click on Edit button
4. Modify the required variable value (audit logger endpoint, DynamoDB table name)
   <img src="src/test/resources/doc/lambdaConfigChange.png" alt="drawing" width="600"/>
5. Click on Save button

### EAT v2 ###
1. In AWS console navigate to Lambda service and filter for representation-representation lambda for tested environment
2. Under Configuration tab select Permissions
3. Click on the Role name
4. Under Permissions policies open *LambdaRoleDefaultPolicy* policy
   <img src="src/test/resources/doc/auditlogPermissionEdit.png" alt="drawing" width="600"/> 
5. Edit policy by changing *PutLogEvents* action's effect to Deny and Save the changes

## Backup and Restore Tests ##

### How to restore a DB in DynamoDB

1. Navigate to DynamoDB service in AWS console
2. Open the required table and select the backup you want to restore
   <img src="src/test/resources/doc/dynamodb_select_backup_to_restore.png" alt="drawing" width="600"/>
3. Click on "Restore" button
4. Enter the required name for the restore table (e.g. dev-dhs-pi-representation-configs-restore)
5. Wait until the table is created
   <img src="src/test/resources/doc/dynamodb_restore_inprogress.png" alt="drawing" width="600"/>
6. Delete the original table (from step 2)
7. Create a backup for the newly created table (from step 5)
8. Click on "Restore" button for this backup
9. Enter the original table name and click on Restore
   <img src="src/test/resources/doc/dynamodb_restore_screen.png" alt="drawing" width="600"/>
10. Wait until the table is created

### After configuration table restore, the ECS Fargate task needs to be restarted to apply change #

1. Navigate to Elastic Container Service in AWS console
2. Select <env_name>-representation-RepresentationFargateCluster cluster from list
3. Restart running Task by selecting Stop option
4. Wait for Task to restart

<img src="src/test/resources/doc/fargate_cluster_restart.png" alt="drawing" width="600"/>

## TLS connection check ##

Prerequisite: OpenSSL is installed

How to check TLS connection for application:
1. Enter the following command to a terminal:

   `openssl s_client -connect <app_base_url>:443 -<tls_version>`

e.g. `openssl s_client -connect test-test1-oncology.careintellect.gehealthcare.com:443 -tls1_1`

If the command returns a certificate chain and completes the handshake, TLS version is supported.

If the command result in a handshake error, the versions is not supported

## Performance Tests ##

See [Performance tests](./tools/load-test/README.md)

## Run Quality Test ##

- Set `UI_PERFORMANCE_TEST` parameter True in `TestSystemParameters.java`
- Performance text file will automatically created at the end of the test run in `TestResults` folder

#### Jenkins Configuration ####

| Chapter                     | Parameter                | Description                                           | Required for Functional Test | Required for Perfomance Test |
|-----------------------------|--------------------------|-------------------------------------------------------|------------------------------|------------------------------|
| Test type                   | Test type                | Functional, Performance (JMeter) or Performance (k6)' | x                            | x                            |
| ENVIRONMENT                 | e2eBranch                | Default: master                                       | x                            | x                            |
| ENVIRONMENT                 | ehlBox                   | Default: OncoBox                                      | x                            | x                            |
| ENVIRONMENT                 | oncoNamespace            | Default: onco                                         | x                            | x                            |
| TEST USERS                  | oncoDeployment           | Default: onchron-main                                 | x                            | x                            |
| TEST USERS                  | user0_Name               |                                                       | x                            | x                            |
| TEST USERS                  | user0_Password           |                                                       | x                            | x                            |
| TEST USERS                  | user1_Name               |                                                       | x                            |                              |
| TEST USERS                  | user1_Password           |                                                       | x                            |                              |
| TEST USERS                  | user2_Name               |                                                       | x                            |                              |
| TEST USERS                  | user2_Password           |                                                       | x                            |                              |
| TEST FILTERS                | includedFeatures         |                                                       | x                            |                              |
| TEST FILTERS                | metafilter               |                                                       | x                            |                              |
| ADDITIONAL PARAMETERS       | chromeVersion            |                                                       | x                            |                              |
| ADDITIONAL PARAMETERS       | timeZone                 |                                                       | x                            |                              |
| PERFORMANCE TEST PARAMETERS | numOfUsers               |                                                       |                              | x                            |
| PERFORMANCE TEST PARAMETERS | rampupPeriod             | Currently, only for JMeter tests                      |                              | x                            |
| PERFORMANCE TEST PARAMETERS | loopCount                | 'Infinite' if it is set to '-1' ('-1' only in JMeter) |                              | x                            |
| PERFORMANCE TEST PARAMETERS | durationInSec            | Max duration of the load test                         |                              | x                            |
| PERFORMANCE TEST PARAMETERS | perfUsersBaseName        | Default: onchron-perf-users-                          |                              | x                            |
| PERFORMANCE TEST PARAMETERS | perfUsersPassword        | Password of performance test users                    |                              | x                            |
| PERFORMANCE TEST PARAMETERS | perfUsersInitialPassword | Initial password after user creation                  |                              | x                            |

## Notes to test cases ##

### Admin Console

**OncoCare URL** \

- ISO install: <ip_address>/oncocare
- Helm install <ip_address>/<kubernetes_namespace>/<deployment_name>

**Login to Admin Console** \
See the [Infrastructure](https://devcloud.swcoe.ge.com/devspace/display/RPYGZ/Infrastructure) page

**OncoCare app config editor** can be found in `Applications` menu:

- `OncoCare Config`: in case of OncoCare was installed from ISO
- `Onchron <deployment_name> Config`: in case of OncoCare was deployed by using Helm chart

**OncoCare valuesset editor** can be found in `Applications` menu:

- `OncoCare Terminology`: in case of OncoCare was installed from ISO
- `Onchron <deployment_name> Terminology`: in case of OncoCare was deployed by using Helm chart

***

### Patient deletion in fulfillment

Current fulfillment design doesn't remove patient entity and local DB right after when the patient was deleted from DPSA.
There are 3 ways to trigger patient deletion in AWS.

- SIMPLE NOTIFICATION SERVICE (SNS) - triggers target env's 'onco-fulfill-EventPatientsDeletionNotifier' lambda which removes the saved DB entries as well OR
- STEP FUNCTIONS - state machines - triggers patient delete but doesn't remove DB
- +1: wait for nightly fulfillment patient deletion rule to trigger deletion (Amazon EventBridge/Rules/<target_env-fulfill-patient-deletion) every day at midnight

#### To trigger patient deletion via SNS:

1. Log in to AWS with a deployer role
2. Navigate to Amazon SNS / Topics / <test_env>-fulfill-sns.fifo
3. Click 'Public message'
4. Fill the mandatory fields and publish the message:

> | *Message group ID*: DATA_FABRIC_DELETE
> | *Message deduplication ID*: <unique_UUID>
> | *Message body*:
`{
"eventType": "DATA_FABRIC_DELETE",
"parameters": {
"patientId": "<patientID>"
},
"x-correlation-id": "<uniqueID>"
}`
>
*Note: It might take several minutes before the patient related entries are deleted from the DynamoDB

#### To trigger patient deletion via STEP FUNCTIONS:

1. Log in to AWS with a deployer role
2. Navigate to Amazon Step functions / State machines / <test_env>-fulfill-etl-workflow
3. Click 'Start execution'
4. Fill the mandatory fields and publish the message:

> | *Name*: DATA_FABRIC_DELETE-<uniqueUUID>
> | *Input*: > `{
eventType": "DATA_FABRIC_DELETE",
"parameters": {
"patientId": "<patientID>"
},
"x-correlation-id": "<uniqueID>"
}`
>

5. Wait for the execution to be in 'Succeeded' state

***

### Queries and actions in DPSA

Delete resource: \
DELETE https://oncf-03.ehs.edison.gehealthcare.com/oncodev/pdsd-proxy/pds/fhir/r4/[resourceType]/[patientresourceID]

e.g. Delete patient resource: \
DELETE https://oncf-03.ehs.edison.gehealthcare.com/oncodev/pdsd-proxy/pds/fhir/r4/Patient/[patientresourceID]  \
Notes:
 - This delete request removes only the patient resource. Other resources of the patient will be kept.
 - On fulfillment side the patient entity is automatically deleted every 00:10:00 (UTC). Until then the patient will be visible in the patient list.

Search for all specific resource types of a patient: \
GET https://d08nwjz8hc.execute-api.us-east-1.amazonaws.com/v1/fhir/r4/[resourceType]?subject=Patient/[patientresourceID]

e.g. DiagnosticReport: \
https://d08nwjz8hc.execute-api.us-east-1.amazonaws.com/v1/fhir/r4/DiagnosticReport?subject=Patient/[patientresourceID]

*** 

### Smart launch

#### Seamless launch (https://fhir.epic.com/)

See details on Confluence [Seamless launch test](https://ge-hc.atlassian.net/wiki/x/rgWfMQ)

Short summary:

1. Login to https://fhir.epic.com/
2. Select "Launching Your App from Epic" in Documentation menu and click on "Try it" button
    - Fill the fields appropriately, e.g.: \
      <img src="src/test/resources/doc/smart_on_fhir_launch.png" alt="drawing" width="400"/>
3. Click on "Generate URL Only"
4. Copy and paste the generated URL into Hyperdrive

### DICOM Viewer

Set via app config: \
`PUT ${api.base.url}/representation/configs/app`

DICOM Viewer URL: `https://d16vr74iv12cb5.cloudfront.net/stone-webviewer/index.html?study={acsn}`

Example payload:
```
{
	"patientIdentifier": {
		"type": {
			"code": "MR",
			"system": "http://terminology.hl7.org/CodeSystem/v2-0203"
		},
		"system": "urn:oid:1.2.840.114350.1.13.0.1.7.5.737384.14"
	},
	"defaultPreset": "oncology.prostate",
	"dicomViewerUrl": "https://d16vr74iv12cb5.cloudfront.net/stone-webviewer/index.html?study={acsn}",
	"oncologyDepartmentIdentifier": {
		"type": {
			"code": "",
			"system": ""
		},
		"value": "",
		"system": ""
	}
}
```

---

## Deployment update from AWS CLI

#### Prerequisite: AWS CLI install and configuration:
1. Install AWS CLI on your operating system: https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html#getting-started-install-instructions
2. Open https://gehc-master.awsapps.com/start/#/?tab=accounts
3. Select the AWS account (e.g.: gehealthcloud357)
4. Click "Access keys" button at the deployer role name (gehc-deployer-v2)
5. Follow the description to set up the configration
6. Run 'aws configure sso' in CMD with GlobalProtect disabled:
    * sso_start_url = https://gehc-master.awsapps.com/start/#
    * sso_region = us-east-1
    * sso_registration_scopes = sso:account:access
7. Clone https://gitlab.apps.ge-healthcare.net/precision-care-framework/precision-care-infra project:
    * install dependencies "npm install -g typescript" and compile "tsc"

#### Update a Deployed environment's CDK stack

###### Note: When the target environment was built the deployer used a tagged CDK.json file as a baseline. This tag number and the CDK deploy script were added to the PI environments table on Confluence page https://ge-hc.atlassian.net/wiki/spaces/PI/pages/607521948/V+V+Testing

1. Navigate to the cloned precision-care-infra project
2. Checkout the tag that was shared on Confluence page
3. Modify the cdk.json for your needs
4. Run the shared cdk deploy script
5. The cdk.json will be synth-ed, and you should see UPDATE_IN_PROGRESS and UPDATE_COMPLETE for the target environment's AWS::CloudFormation::Stack stacks
6. The re-deploy is completed when the output is displayed (deployment time should take max. 5 mins)

###### Notes:

* The "deploy script" might have multiple -c <parameter> lines which overwrites the cdk.json's content
* Only parameters with simple datatypes e.g. strings can be overwritten in the deploy script. Complex parameters such as lists, arrays can only be edited in the cdk.json file
* A single CDK parameter cannot be overwritten by itself. All the parameters are needed as an argument in the 'cdk deploy' command.
* If the parameters were updated to an incorrect value (e.g. wrong service version) then the deployment starts a rollback which restores the deployment to the previous working version
  automatically

---
## Coding Conventions ##

Best practices for writing feature files/scenarios

- scenario description: Write what you are testing, not how you are testing that (for the „how” the GWT is used.). Write
  it in a short, clear sentence trying to highlight the requirement part for which the given scenario is created.
- Keep the GWT order in your tests (for this annotating existing steps is a technique). However, there are cases, where
  the WhenThenWhenThen makes sense.
- Use @Given to express the preconditions of a test execution. Use passive sentences, @Given is mostly not an "action".
  E.g "I push the button" --> "the button is pushed"
- Use @When only for the functionality you want to test in the scenario (use only 1 or 2 Whens in a scenario) For all
  other pre-requisites, use @Given  (alias the When steps if needed)
- Use @Then only for assertions. Use it to describe the actual outcome, not an expected. DO NOT use "shall happen", but
  DO use "happened". A statement instead of an expectation.
- Try to write reusable steps (make them parametrized)
- Do not save the words, step definitions shall be descriptive and clear, but not technical sentences.
- Do not use capital letters for the steps, since it will start anyways with Given, When or Then.
- Use @BeforeStories, @BeforeScenario to hide initialization which is irrelevant for the end user but needed for the
  test to be stable and independent (E.g Logout users, register projects, etc)
- Add quote for exact button names e.g: `user clicks on "Login" button` -> the button text is exactly `Login`
- When you have to values in connection use the same unit in the tests (e.g: do not set 1 minute timeout and execute
  10000 millisec algorithm, but: 2 minute timeout and 1 minute execution)
- When testing result, you can be code specific: e.g: when a json response shall contain a "myMessage" filled with "
  xyz": And I get the response with "xyz" message --> And I get the response with "myMessage" is "xyz".

Code formatting: In IntelliJ Idea please use the code format template `GEHCPFH.xml` what is in the in
the `\src\test\resources\code formatting template\` directory. Install it
via `File -> Settings -> Code Style -> Scheme -> Import Scheme` menu

## Building, pulling and running tests locally using docker ##

*Precondition*

* A working docker is needed to build or pull docker images and then to run the tests.

### The local build process ###

* In your preferred command line tool, where you can reach docker, navigate to the e2e test folder, then give out the
  following command:

```
docker build -t <IMAGE NAME> .
```

where in the IMAGE NAME field, you can
write any name you want for your image e.g. e2e-tests.

* Once the build process is finished, the image is ready to be used locally.

### Pulling the image from artifactory ###

* To pull an existing image from the artifactory, you need a command line tool, where you can give out the following
  command:

 ```
 docker pull hc-eu-west-aws-artifactory.cloud.health.ge.com/docker-oncotools-dev/onchron-e2e-tests:<IMAGE VERSION>
 ```

where the IMAGE VERSION is the version of the image you want to pull, e.g. latest, which will be the latest image built
from master branch.

* Once the pulling finishes, the image is ready to be used locally.

### Running the image locally ###

* Before running an image locally, an image is needed that could be ran, that could be either build locally or pulled
  from the artifactory.
* When there is an image ready, it can be ran, using the following commands:
  ```
  docker run <IMAGE NAME> mvn verify 
  ```

This is the most basic way of running the image, it starts the full e2e test on the default environment(usually
onchron-main), with logs displayed in the terminal where the command is given out.

  ```
  docker run -d <IMAGE NAME> mvn verify
  ```

Using the ` -d ` flag, enables us to use the terminal since it makes the container run in the background, but othervise
it is the same as the command before.

  ```
  docker run -d -v <RESULT_FOLDER>:/opt/onchron-e2e-tests/target <IMAGE NAME> mvn verify
  ```

This command helps us attach a volume to the container, which will copy all the results to our local machine, to the
preferred folder, which we can give by filling out the RESULT_FOLDER variable, it can for example be a relative
path `.\results `, or an absolute path `c:/result `. If the folder does not exists in the local machine, then it will be
created when the container is created.

  ```
  docker run -d -v <RESULT_FOLDER>:/opt/onchron-e2e-tests/target <IMAGE NAME> mvn verify [OPTIONS]
  ```

For the OPTIONS field, we can give a number of different options to the e2e tests:

* `-Dehl.box.address="<BOX_ADDRESS>"`: In which box does the environment we want to test is deployed,
  e.g. `-Dehl.box.address="https://10.214.154.134/"`
* `-Donchron.url.path="<URL_PATH>"`: The url in which the deployment can be accessed,
  e.g. `-Donchron.url.path="onco/onchron-local-core"`.
* `-Dfhir.server.url="<FHIR_SERVER_URL>"`: The url in which the FHIR server can be accessed,
  e.g. `-Dfhir.server.url=\"https://10.214.154.134/cdip/fhir/r4\"`.
* `-DincludedFeatures="<INCLUDED_FEATURES>"`: What feature files should be tested,
  e.g. `-DincludedFeatures="**/MedicalHistoryView/SRS-33_Medications.feature"`.
* `-Dmetafilter="<METAFILTER>"`: Filters which tests should run, if we want to only test specific test scenarios,
  e.g. `-Dmetafilter="+SRS-50.01"`.
* `-Duser.DEFAULT-TEST-USER.name="<USER_NAME>"`: Username of the default test user
* `-Duser.DEFAULT-TEST-USER.psw="<USER_PSW>"`: Password of the default test user
* `-Duser.TEST-USER-1.name="<USER_NAME>"`: Username of test-user-1
* `-Duser.TEST-USER-1.psw="<USER_PSW>"`: Password of test-user-1
* `-Duser.TEST-USER-2.name="<USER_NAME>"`: Username of the test-user-2
* `-Duser.TEST-USER-2.psw="<USER_PSW>"`: Password of the test-user-2
* `-Dchrome.switches="<CHROME_SWITCHES>"`: Chrome switches we would want the chrome driver to use,
  e.g. `-Dchrome.switches="--ignore-ssl-errors=yes;--ignore-certificate-errors;"`.
