#!/bin/sh
echo "Uploading test result stats to Digital Dashboard"

serenity_summary_report_path=$1

readJsonConfig() {
        jq $1 $serenity_summary_report_path
}

total_tests=$(readJsonConfig ".results.counts.total")
error=$(readJsonConfig ".results.counts.error")
aborted=$(readJsonConfig ".results.counts.aborted")
compromised=$(readJsonConfig ".results.counts.compromised")
total_errors=$((error + aborted + compromised))
skipped=$(readJsonConfig ".results.counts.skipped")
ignored=$(readJsonConfig ".results.counts.ignored")
pending=$(readJsonConfig ".results.counts.pending")
success=$(readJsonConfig ".results.counts.success")
failure=$(readJsonConfig ".results.counts.failure")
success_percentage=$(readJsonConfig ".results.percentages.success")
failure_percentage=$(readJsonConfig ".results.percentages.failure")
error_percentage=$(readJsonConfig ".results.percentages.error")
aborted_percentage=$(readJsonConfig ".results.percentages.aborted")
compromised_percentage=$(readJsonConfig ".results.percentages.compromised")
total_error_percentage=$((error_percentage + aborted_percentage + compromised_percentage))
skipped_percentage=$(readJsonConfig ".results.percentages.skipped")
ignored_percentage=$(readJsonConfig ".results.percentages.ignored")
pending_percentage=$(readJsonConfig ".results.percentages.pending")
total_test_duration=$(readJsonConfig ".results.totalTestDuration")

current_date=$(date +"%Y-%m-%d")
current_datetime=$(date +"%Y-%m-%dT%T")

reportStats=$( jq -n \
                  --arg runUID "$current_date" \
                  --argjson total_tests "$total_tests" \
                  --argjson total_errors "$total_errors" \
                  --argjson skipped "$skipped" \
                  --argjson ignored "$ignored" \
                  --argjson pending "$pending" \
                  --argjson success "$success" \
                  --argjson failure "$failure" \
                  --argjson success_percentage "$success_percentage" \
                  --argjson failure_percentage "$failure_percentage" \
                  --argjson total_error_percentage "$total_error_percentage" \
                  --argjson skipped_percentage "$skipped_percentage" \
                  --argjson ignored_percentage "$ignored_percentage" \
                  --argjson pending_percentage "$pending_percentage" \
                  --argjson total_test_duration "$total_test_duration" \
                  --arg current_datetime "$current_datetime" \
                  '{
                    jobName: "OncologyCI/onchron-e2e-tests_nightly",
                    org: "GE HealthCare",
                    team: "oncology-digital-budapest",
                    product: "OncoFLow",
                    pipelinePhase: "BDD",
                    component: "onchron-e2e-tests",
                    runUID: $runUID,
                    scope: "e2e",
                    scopeName: $runUID,
                    granularity: "test",
                    tests: $total_tests,
                    errors: $total_errors,
                    skipped: $skipped,
                    ignored: $ignored,
                    pending: $pending,
                    success: $success,
                    failures: $failure,
                    successPercentage: $success_percentage,
                    failuresPercentage: $failure_percentage,
                    errorsPercentage: $total_error_percentage,
                    skippedPercentage: $skipped_percentage,
                    ignoredPercentage: $ignored_percentage,
                    pendingPercentage: $pending_percentage,
                    elapsedTime: $total_test_duration,
                    eventTimestamp: $current_datetime
                  }' )

curl -X POST -H 'Content-Type: application/json' -d "$reportStats" http://es-digitaldashboard.cloud.health.ge.com/elastic/bddsummary/
printf "\n"

echo "Uploading test result stats to Digital Dashboard finished"
