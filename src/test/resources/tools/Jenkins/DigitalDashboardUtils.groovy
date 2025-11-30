def sendE2ETestResultsStats(resultJson, hcddSettings) {

    runUID = (new Date(System.currentTimeMillis()).format('yyyy-MM-dd'))

    hcdd.BddSummaryEvent testSummaryEvent = new hcdd.BddSummaryEvent(
            jobURL: env.JOB_URL,
            jobName: env.JOB_NAME,
            org: hcddSettings.org,
            team: hcddSettings.team,
            product: hcddSettings.product,
            program: hcddSettings.program,
            project: hcddSettings.project,
            branch: hcddSettings.branch,
            release: hcddSettings.release,
            pipelinePhase: hcddSettings.pipelinePhase,
            component: hcddSettings.component,
            source: '',
            testSuite: '',
            runUID: runUID,
            scope: 'e2e',
            scopeName: runUID,
            granularity: 'test',
            tests: resultJson.results.counts.total,
            errors: resultJson.results.counts.error + resultJson.results.counts.aborted + resultJson.results.counts.compromised,
            skipped: resultJson.results.counts.skipped,
            ignored: resultJson.results.counts.ignored,
            pending: resultJson.results.counts.pending,
            success: resultJson.results.counts.success,
            failures: resultJson.results.counts.failure,
            successPercentage: resultJson.results.percentages.success,
            failuresPercentage: resultJson.results.percentages.failure,
            errorsPercentage: resultJson.results.percentages.error + resultJson.results.percentages.aborted + resultJson.results.percentages.compromised,
            skippedPercentage: resultJson.results.percentages.skipped,
            ignoredPercentage: resultJson.results.percentages.ignored,
            pendingPercentage: resultJson.results.percentages.pending,
            elapsedTime: resultJson.results.totalTestDuration,
            eventTime: System.currentTimeMillis()
    )
    echo "testSummaryEvent dump: ${testSummaryEvent.dump()}"
    if (env.JOB_URL != null) {
        new hcdd.ElasticSearchUtil().send('bddsummary', testSummaryEvent, hcddSettings.elasticSearchURL)
    }
}

def sendRequirementCoverageStats(metricsJson, hcddSettings) {
    hcdd.SonarEvent reqCoverageEvent = new hcdd.SonarEvent(
            jobURL: env.JOB_URL,
            jobName: env.JOB_NAME,
            org: hcddSettings.org,
            team: hcddSettings.team,
            product: 'OncoCare',
            scope: 'requirement',
            program: hcddSettings.program,
            project: hcddSettings.project,
            branch: hcddSettings.branch,
            release: hcddSettings.release,
            pipelinePhase: hcddSettings.pipelinePhase,
            component: hcddSettings.component,
            elapsedTime: 0,
            created: System.currentTimeMillis(),
            description: '',
            source: 'onchron-e2e',
            createdBy: 'onchron-e2e',
            projectKey: env.JOB_NAME,
            coverage: metricsJson.coveredRequirementsPercentage,
            conditions_to_cover: metricsJson.totalRequirements,
            uncovered_conditions: metricsJson.uncoveredRequirements
    )
    echo "reqCoverageEvent dump: ${reqCoverageEvent.dump()}"
    if (env.JOB_URL != null) {
        new hcdd.ElasticSearchUtil().send('sonar', reqCoverageEvent, hcddSettings.elasticSearchURL)
    }
}

def sendAutoManualTestNumStats(metricsJson, hcddSettings) {
    Calendar calendar = Calendar.getInstance()
    // Calculate week number according to the ISO-8601 standard (https://www.epochconverter.com/weeknumbers)
    calendar.setFirstDayOfWeek(Calendar.MONDAY)
    calendar.setMinimalDaysInFirstWeek(4)
    calendar.setTime(new Date())
    int year = calendar.get(Calendar.YEAR)
    int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)

    String correctedYear;
    if (weekOfYear == 1 && calendar.get(Calendar.MONTH) == 11) {
        correctedYear = year + 1
    } else if (weekOfYear >= 52 && calendar.get(Calendar.MONTH) == 0) {
        correctedYear = year - 1
    } else {
        correctedYear = year
    }

    // To use 2 digits format for weeks (i.e. 01, 02, ... 10, 11, ...)
    String zeroPaddedWeekOfYear = weekOfYear.toString().padLeft(2, "0")

    runUID = correctedYear + "FW" + zeroPaddedWeekOfYear

    AutoManualTestStatsEvent autoManualTestNumStats = new AutoManualTestStatsEvent(
            jobURL: env.JOB_URL,
            jobName: env.JOB_NAME,
            org: hcddSettings.org,
            team: hcddSettings.team,
            product: hcddSettings.product,
            program: hcddSettings.program,
            project: hcddSettings.project,
            branch: hcddSettings.branch,
            release: hcddSettings.release,
            pipelinePhase: hcddSettings.pipelinePhase,
            component: hcddSettings.component,
            source: '',
            createdBy: 'onchron-e2e',
            runUID: runUID,
            scope: 'auto-man',
            automated: metricsJson.automatedTestCases,
            manual: metricsJson.manualTestCases,
            automated_percent: metricsJson.automatedTestCasesPercentage,
            eventTime: System.currentTimeMillis()
    )
    echo "autoManualTestNumStats dump: ${autoManualTestNumStats.dump()}"
    if (env.JOB_URL != null) {
        new hcdd.ElasticSearchUtil().send('bddsummary', autoManualTestNumStats, hcddSettings.elasticSearchURL)
    }
}

class AutoManualTestStatsEvent extends hcdd.GeneralEvent {
    String scope
    String runUID
    int automated               // number of automated test cases
    int manual                  // number of manual test cases
    double automated_percent    // percentage of automated tests
}

return this
