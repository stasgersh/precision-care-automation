# Audit Log Tests Tool

This tool helps test and verify the audit logging capability of CIO by searching in EAT for specific audit log entries based on given criteria.

## Overview

The script fetches audit logs from the EAT service (either v1 or v2), filters them by specific failure messages, and outputs the filtered results to a JSON file. It can help identify if specific actions are being properly logged in the audit system.

## Prerequisites

- `bash` shell environment
- `curl` for making HTTP requests
- `jq` for JSON processing

## Usage

```bash
./audit-log-tests.sh \
  --test-type <type> \
  --eat-url <url> \
  --username <name> \
  --minutes <minutes> \
  --idam-url <url> \
  --client-id <id> \
  --client-secret <secret> \
  --scope <scope> \
  --audience <audience> \
  --eat-version <v1|v2> \
  --tenant-id <id> \
  [--test <true|false>]
```

## Parameters

| Parameter | Description |
|-----------|-------------|
| `--test-type` | Type of test to filter logs for. Allowed values: `CreateComment`, `DeleteComment`, `CreateLabel` |
| `--eat-url` | URL of the EAT service |
| `--eat-version` | Version of the EAT service (v1 or v2) |
| `--tenant-id` | Tenant ID for the request |
| `--username` | Username to filter logs by |
| `--minutes` | Time window in minutes to search logs (e.g., 5 for logs from the past 5 minutes) |
| `--idam-url` | URL of the IDAM service for authentication |
| `--client-id` | Client ID for IDAM authentication |
| `--client-secret` | Client secret for IDAM authentication |
| `--scope` | API scope for authentication |
| `--audience` | API audience for authentication |
| `--test` | Optional. Run in test mode using local files instead of API calls (true/false, default: false) |

## Output

The script generates two output files:

1. `audit_logs.json` - Contains all raw audit logs retrieved from the API
2. `result.json` - Contains the filtered logs based on the specified test type

## Example

```bash
./audit-log-tests.sh \
  --test-type "CreateLabel" \
  --eat-url "https://eat.example.com/eat/v2/fhir-search" \
  --eat-version "v2" \
  --username "user@example.com" \
  --minutes 5 \
  --idam-url "https://idam.example.com/oauth2/token" \
  --client-id "client-id" \
  --client-secret "client-secret" \
  --scope "write:eat:audit:event:log" \
  --audience "audience-id" \
  --tenant-id "tenant-id"
```

## Test Mode

You can run the script in test mode by setting `--test true`. In this mode, instead of making API calls, the script reads logs from a local test file named `test_audit_logs_<EAT_VERSION>.json`.

Make sure this file exists in the same directory as the script before running in test mode.

## Filter Logic

The script filters logs based on the test type:
- For `CreateComment`: Searches for "failed to create comment"
- For `DeleteComment`: Searches for "failed to delete comment"
- For `CreateLabel`: Searches for "failed to create label"

Additionally, it filters by:
- Username
- Timestamp within the specified minutes window (using UTC)