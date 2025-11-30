# Service Log Test Script

This script (`service-log-test.sh`) automates the generation and collection of service logs for manual tests. It interacts with AWS, CI4O endpoints, and generates result files for each test scenario.

## Usage

### 1. Prerequisites
**NOTE:** This script is intended to be run in Git Bash on Windows only, because UUID generation relies on PowerShell currently.
- Ensure you have Bash (or compatible shell), `curl`, `jq`, and AWS CLI installed and configured.
- Log in to the AWS account where the environment is located.
- Add "MSYS_NO_PATHCONV=1" to environment variables to system level.

### 2. Running the Script
Run the script from the terminal, providing the required parameters:

```sh
./service-log-test.sh \
  --idam-url <IDAM_URL> \
  --client-id <CLIENT_ID> \
  --client-secret <CLIENT_SECRET> \
  --scope <SCOPE> \
  --minutes <MINUTES> \
  --env-url <ENV_URL> \
  --env-name <ENV_NAME> \
  --patient-id <PATIENT_ID> \
  --event-id <EVENT_ID>
```

#### Example:
```sh
./service-log-test.sh \
  --idam-url https://example.com/auth/realms/abc/protocol/openid-connect/token \
  --client-id my-client-id \
  --client-secret my-secret \
  --scope openid \
  --minutes 5 \
  --env-url https://api.example.com \
  --env-name "dev-dev" \
  --patient-id "12345" \
  --event-id "medication:bf9760a6-6acb-4412-9bfd-6f01e0f77067"
```

### 3. Scenarios
The script runs the following scenarios in sequence:

- **App Config Scenario**: Updates and verifies application configuration, then collects logs (`updateAppConfigResult.json`).
- **Select Patient Scenario**: Selects a patient and fetches summary data, then collects logs (`selectPatientResult.json`).
- **Export Summary View Scenario**: Exports the summary view for a patient, then collects logs (`exportSummaryViewResult.json`).
- **Create Event Comment Scenario**: Creates a comment on a specific event, collects logs (`createEventCommentResult.json`), and cleans up by deleting the comment.
- **Create Patient Comment Scenario**: Creates a comment on a patient, collects logs (`createPatientCommentResult.json`), and cleans up by deleting the comment.
- **Delete Event Comment Scenario**: Creates and then deletes an event comment, collects logs (`deleteEventCommentResult.json`).
- **Delete Patient Comment Scenario**: Creates and then deletes a patient comment, collects logs (`deletePatientCommentResult.json`).
- **Create Label Scenario**: Creates a label, collects logs (`createLabelResult.json`), and cleans up by deleting the label.

### 4. Output
- Scenario results are saved as JSON files (e.g., `updateAppConfigResult.json`, `selectPatientResult.json`).
- Console output provides step-by-step status and error messages.

### 5. Customization
- Uncomment or add scenarios in the script as needed for your testing.
- Adjust parameters to match your environment and test cases.

## Troubleshooting
- Ensure all required parameters are provided.
- Check AWS CLI and credentials configuration if AWS commands fail.
- Review console output for error details.

