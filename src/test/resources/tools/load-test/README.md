# Performance tests

Prerequisites:
1. Required number of users is created:
    * Create users helm chart: [test-users](https://gitlab-gxp.cloud.health.ge.com/oncology-digital-budapest/tools/test-users)
    * User name format: `<users.base.name>1`, so counter starts from 1
2. User-role mappings are created by
    * [onchron-helm-chart](https://gitlab-gxp.cloud.health.ge.com/oncology-digital-budapest/onchron-helm-chart) or
    * manually in Admin Console
3. `<users.base.name>1` requires `oncodummyapp` role
4. Test patients are uploaded: from `/patients/load-tests` directory
5. clear core cache before starting load-tests

## JMeter

There are 3 ways to execute the JMeter tests:

1. Execute pure .jmx file from command prompt (please replace the path parameters properly):
   ```
   jmeter -n -t C:\git\OncoCare\onchron-e2e-tests\tools\load-test\onchron-workflow.jmx -Jehl.box.ip=10.214.154.134 -Jonchron.url.path=oncocare -Jonchron.users.base.name=onchron-perf-user- -Jonchron.user.password=User!321 -Jonchron.user.password.initial=User!123 -Jnum.of.users=30 -Jrampup.period=60 -Jloop.count=20 -Jresults.folder=onchron-results -l onchron-results/result.jtl -e -o C:\dev\OncoCare\load-test\jmeter-report
   ```
   The result files will be generated into `/onchron-results` folder and a formatted html report will be generated into `/onchron-dashboard-report` folder. 


2. jmeter-maven-plugin **[OUTDATED]**:
   ```
   mvn -Pperformance clean verify "-Dloadtest.ehl.box.ip=3.249.67.132" "-Dloadtest.onchron.namespace=onco" "-Dloadtest.onchron.deployment.name=onchron-main" "-Dloadtest.onchron.users.base.name=onchron-perf-user-" "-Dloadtest.onchron.user.password=User!321" "-Dloadtest.onchron.user.password.initial=User!123" "-Dloadtest.num.of.users=2" "-Dloadtest.rampup.period=2" "-Dloadtest.loop.count=2" "-Dloadtest.duration.in.sec=10"
   ```
   The result files will be generated into `/target/load-test-results` folder and a formatted html report will be generated into `/target/jmeter/reports` folder.


3. Jenkins **[OUTDATED]**: \
see [Jenkins Configuration](../../README.md#Jenkins-Configuration)


## K6

There are 2 ways to execute the K6 tests:

1. Execute k6 test locally:
   * Base execution command: \
      ```
     k6 run --env EHL_BOX_ADDRESS="https://3.249.67.132" --env NAMESPACE="onco" --env DEPLOYMENT_NAME="onchron-main" --env NUMBER_OF_USERS="2" --env NUMBER_OF_ITERATIONS="2" --env DURATION_IN_SEC="60" --env USERS_BASE_NAME="onchron-perf-user-" --env USERS_PASSWORD="User!321" --env USERS_INITIAL_PASSWORD="User!123" --env HTML_REPORT_FILE_PATH="target/k6-onchron-load-test-result-summary.html" --out csv=onchron_result.csv .\tools\load-test\k6-onchron-load-test.js
      ```
   * Output into csv: \
     &rarr; user the following option: `--out csv=onchron_result.csv`
   * With debug logs: \
     &rarr; user the following option: `--http-debug="full"`
   HTML report is generated to the file provided in `HTML_REPORT_FILE_PATH` env var (if target directory is available)
2. Jenkins: see [Jenkins Configuration](../../README.md#Jenkins-Configuration)
