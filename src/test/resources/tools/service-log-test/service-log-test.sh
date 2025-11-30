#!/bin/bash

#--------------------------------- Function definitions ----------------------------------
parse_input_parameters(){
  TEST="false"
  while [[ "$#" -gt 0 ]]; do
      case $1 in
          --idam-url) IDAM_URL="$2"; shift ;;
          --client-id) CLIENT_ID="$2"; shift ;;
          --client-secret) CLIENT_SECRET="$2"; shift ;;
          --scope) SCOPE="$2"; shift ;;
          --minutes) MINUTES="$2"; shift ;;
          --env-url) ENV_URL="$2"; shift ;;
          --env-name) ENV_NAME="$2"; shift ;;
          --patient-id) PATIENT_ID="$2"; shift ;;
          --event-id) EVENT_ID="$2"; shift ;;
          *) echo "Unknown parameter passed: $1"; exit 1 ;;
      esac
      shift
  done
}

log_input_parameters(){
  echo "---------------------------------------------------------------------------------------------"
  echo "Run the script with the following parameters:"
  echo "- IDAM URL: $IDAM_URL"
  echo "- Client ID: $CLIENT_ID"
  echo "- Client secret: $CLIENT_SECRET"
  echo "- Scope: $SCOPE"
  echo "- Minutes: $MINUTES"
  echo "- Env URL: $ENV_URL"
  echo "- Env name: $ENV_NAME"
  echo "- Patient ID: $PATIENT_ID"
  echo "- Event ID: $EVENT_ID"
}

validate_input_parameters() {
  local missing_params=()

  [[ -z "$IDAM_URL" ]] && missing_params+=("--idam-url")
  [[ -z "$CLIENT_ID" ]] && missing_params+=("--client-id")
  [[ -z "$CLIENT_SECRET" ]] && missing_params+=("--client-secret")
  [[ -z "$SCOPE" ]] && missing_params+=("--scope")
  [[ -z "$MINUTES" ]] && missing_params+=("--minutes")
  [[ -z "$ENV_URL" ]] && missing_params+=("--env-url")
  [[ -z "$ENV_NAME" ]] && missing_params+=("--env-name")
  [[ -z "$PATIENT_ID" ]] && missing_params+=("--patient-id")
  [[ -z "$EVENT_ID" ]] && missing_params+=("--event-id")

  if (( ${#missing_params[@]} )); then
    echo "Error: Missing required parameter(s): ${missing_params[*]}" >&2
    echo "Usage: $0 --idam-url <url> --client-id <id> --client-secret <secret> --scope <scope> --minutes <minutes> --env-url <url> --env-name <name> --patient-id <id> --event-id <id>" >&2
    exit 1
  fi

  local valid_event_id=$(isValidEventId)
  if [[ "$valid_event_id" == "false" ]]; then
    echo "Can't find event for patient ID: $PATIENT_ID and event ID: $EVENT_ID" >&2
    exit 1
  fi
}

update_putlogevents_policy() {
  echo "---------------------------------------------------------------------------------------------"
  echo "Updating logs:PutLogEvents action Effect to: $1"
  local effect="$1"

  ROLE_NAME="$ENV_NAME-pi-representation-representation-role"
  echo "ROLE_NAME: $ROLE_NAME"

  POLICY_JSON=$(aws iam list-role-policies --role-name $ROLE_NAME)
  echo "Policy JSON: $POLICY_JSON"
  
  POLICY_NAME=$(echo $POLICY_JSON | jq -r '.PolicyNames[0]')
  echo "Policy name: $POLICY_NAME"
  
  INLINE_POLICY_CONTENT=$(aws iam get-role-policy \
   --role-name $ROLE_NAME \
   --policy-name "$POLICY_NAME")
  
  echo "Inline policy content: $INLINE_POLICY_CONTENT"
  echo "Updating logs:PutLogEvents action Effect to: $effect"

  # Parse the policy document and update the Effect for logs:PutLogEvents
  local updated_policy=$(echo "$INLINE_POLICY_CONTENT" | jq --arg effect "$effect" '
    .PolicyDocument.Statement = (.PolicyDocument.Statement | map(
      if .Action == "logs:PutLogEvents" then
        .Effect = $effect
      else
        .
      end
    ))
  ')

  echo "Updated policy document:"
  echo "$updated_policy" | jq .

  aws iam put-role-policy \
    --role-name "$ROLE_NAME" \
    --policy-name "$POLICY_NAME" \
    --policy-document "$(echo "$updated_policy" | jq -c .PolicyDocument)"

  return 0
}

getLogsAndWriteToFile() {
  echo "---------------------------------------------------------------------------------------------" >&2
  local correlation_id="$1"
  local file_name="$2"
  local log_group_name="/aws/lambda/$ENV_NAME-pi-representation-representation-zip"
  local start_time=$(($(date -u +%s) - (MINUTES * 60)))
  local start_time_ms=$((start_time * 1000))

  if [[ "$correlation_id" =~ ^[0-9a-fA-F-]{36}$ ]]; then
    filter_pattern="{ $.requestId = \"$correlation_id\" }"
  else
    filter_pattern="$correlation_id"
  fi

  local logs_by_correlation_id=$(aws logs filter-log-events \
    --log-group-name "$log_group_name" \
    --start-time "$start_time_ms" \
    --filter-pattern "$filter_pattern")

  local logs_by_failed_audit_msg=$(aws logs filter-log-events \
    --log-group-name "$log_group_name" \
    --start-time "$start_time_ms" \
    --filter-pattern '"Failed to send audit log. Reason: CloudWatchException"')

  echo "Logs matching correlation id: $logs_by_correlation_id"
  echo "Logs matching CloudWatchException: $logs_by_failed_audit_msg"

  local events_by_correlation_id=$(echo "$logs_by_correlation_id" | jq '.events')
  local events_by_failed_audit_msg=$(echo "$logs_by_failed_audit_msg" | jq '[.events[0]]')

  # It is necessary to use temporary files, because of jq
  local tmp1="tmp_events_correlation_$$_$(date +%s).json"
  local tmp2="tmp_events_audit_$$_$(date +%s).json"
  
  # Write JSON to temp files, ensuring they have valid content
  if [[ -n "$events_by_correlation_id" && "$events_by_correlation_id" != "null" ]]; then
    printf '%s' "$events_by_correlation_id" > "$tmp1"
  else
    echo "[]" > "$tmp1"
  fi
  
  if [[ -n "$events_by_failed_audit_msg" && "$events_by_failed_audit_msg" != "null" ]]; then
    printf '%s' "$events_by_failed_audit_msg" > "$tmp2"
  else
    echo "[]" > "$tmp2"
  fi

  # Verify files exist before running jq
  if [[ ! -f "$tmp1" || ! -f "$tmp2" ]]; then
    echo "Error: Could not create temporary files" >&2
    return 1
  fi

  echo "Combining events into a single JSON array..."
  jq -s '.[0] + .[1] | map(
    if type == "object" and has("message") then
      ( .message | fromjson )
    else . end
  ) | map(
    if type == "object" and has("level") and (.level == "info" or .level == "error") then . else empty end
  ) | unique_by(.message)' "$tmp1" "$tmp2" > "$file_name"

  rm -f "$tmp1" "$tmp2"
  echo "Combined events written to $file_name"
}

request_token() {
    echo "Requesting token..." >&2
    local idam_response=$(curl --silent --request POST \
        --url "$IDAM_URL" \
        --header 'Content-Type: application/x-www-form-urlencoded' \
        --data grant_type=client_credentials \
        --data client_id="$CLIENT_ID" \
        --data client_secret="$CLIENT_SECRET" \
        --data "scope=$SCOPE")

    if [[ -z "$idam_response" ]]; then
        echo "Error: Failed to retrieve token."
        exit 1
    fi
    token=$(echo "$idam_response" | jq -r '.access_token')
    echo "Token: $token" >&2
    echo "$token"
}

getAppConfig() {
  echo "---------------------------------------------------------------------------------------------" >&2
  echo "Getting app config..." >&2
  local token="$1"
  local url="$2/representation/configs/app"
  echo "URL: $url" >&2
  local response=$(curl --silent --request GET --ssl-no-revoke \
    --url "$url" \
    --header "Authorization: Bearer $token")
  
  if [[ -z "$response" ]]; then
        echo "Error: Failed to app config."
        exit 1
  fi

  echo "getAppConfig: $response" >&2
  echo "$response"
}

updateAppConfig() {
  echo "---------------------------------------------------------------------------------------------" >&2
  echo "Updating app config..." >&2
  local token="$1"
  local url="$2/representation/configs/app"
  local config=$3
  local uuid=$4
  echo "URL: $url"
  echo "Config: $config"

  local response
  local http_code
  response=$(curl --silent --write-out "HTTPSTATUS:%{http_code}" --request PUT --ssl-no-revoke \
    --url "$url" \
    --header "Authorization: Bearer $token" \
    --header 'Content-Type: application/json' \
    --header "x-correlation-id: $uuid" \
    --data "$config")
  http_code=$(echo "$response" | tr -d '\n' | sed -e 's/.*HTTPSTATUS://')

  echo "Response HTTP status code: $http_code" >&2

  if [[ "$http_code" -ne 204 ]]; then
        echo "Error: Failed to update app config. HTTP status code: $http_code"
        exit 1
  fi
}

# This is necessary because the logs will only be available in CloudWatch after another request.
getStatus() {
  echo "---------------------------------------------------------------------------------------------" >&2
  echo "Getting status..." >&2
  sleep 2
  local token="$1"
  local url="$2/representation/patients/$PATIENT_ID/status"
  echo "URL: $url" >&2
  local response=$(curl --silent --request GET --ssl-no-revoke \
    --url "$url" \
    --header "Authorization: Bearer $token")

  if [[ -z "$response" ]]; then
        echo "Error: Failed to get status."
        exit 1
  fi

  echo "getStatus: $response" >&2
  echo "$response"
}

getSummary() {
  echo "---------------------------------------------------------------------------------------------" >&2
  echo "Getting summary..." >&2
  local token=$1
  local url=$2
  local status_response=$3
  local uuid=$4
  echo "status_response: $status_response" >&2
  local status_json=$(echo "$status_response" | tail -1 | tr -d '\r')
  local latest_version=$(echo "$status_json" | jq -r '.latestVersion')
  local latest_eligibility_version=$(echo "$status_json" | jq -r '.latestEligibilityVersion')
  local url="$url/representation/patients/$PATIENT_ID/summary"
  echo "URL: $url" >&2
  echo "latestVersion: $latest_version" >&2
  echo "latestEligibilityVersion: $latest_eligibility_version" >&2

  local response=$(curl --silent --request GET --ssl-no-revoke \
    --url "$url" \
    --get \
    --data-urlencode "version=$latest_version" \
    --data-urlencode "showOncologyRelated=false" \
    --data-urlencode "usingAi=false" \
    --data-urlencode "eligibilityVersion=$latest_eligibility_version" \
    --header "Authorization: Bearer $token" \
    --header "x-correlation-id: $uuid")

  if [[ -z "$response" ]]; then
        echo "Error: Failed to select patient." >&2
        exit 1
  fi
}

exportSummary() {
  echo "---------------------------------------------------------------------------------------------" >&2
  echo "Exporting summary..." >&2
  local token=$1
  local url=$2
  local uuid=$3

  local url="$url/representation/patients/$PATIENT_ID/exports/summary/"
  echo "URL: $url" >&2
  
  local response=$(curl --silent --write-out "HTTPSTATUS:%{http_code}" --request POST --ssl-no-revoke \
    --url "$url" \
    --header "Authorization: Bearer $token" \
    --header "x-correlation-id: $uuid")
  local http_code=$(echo "$response" | tr -d '\n' | sed -e 's/.*HTTPSTATUS://')

  echo "HTTP STATUS: $http_code"
}

isValidEventId(){
  echo "---------------------------------------------------------------------------------------------" >&2
  echo "Validate event ID..." >&2
  local token=$(request_token)
  local url="$ENV_URL/representation/patients/$PATIENT_ID/timeline"
  local status_response=$(getStatus $token $ENV_URL)
  echo "URL: $url" >&2
  echo "status_response: $status_response" >&2

  local status_json=$(echo "$status_response" | tail -1 | tr -d '\r')
  local latest_version=$(echo "$status_json" | jq -r '.latestVersion')

  local response=$(curl --silent --request GET --ssl-no-revoke \
    --url "$url" \
    --get \
    --data-urlencode "version=$latest_version" \
    --data-urlencode "showOncologyRelated=false" \
    --header "Authorization: Bearer $token")
  
  if [[ -z "$response" ]]; then
        echo "Error: Failed to get timeline event." >&2
        exit 1
  fi

  local found=$(echo "$response" | jq --arg event_id "$EVENT_ID" '[.items[] | select(.id == $event_id)] | length')
  if [[ "$found" -gt 0 ]]; then
    echo true
  else
    echo false
  fi
}

create_event_comment() {
  echo "---------------------------------------------------------------------------------------------" >&2
  echo "Create event comment..." >&2
  local token=$1
  local event_id=$3
  local url="$2/representation/patients/$PATIENT_ID/events/$event_id/comments"
  local uuid=$4
  echo "URL: $url" >&2

  local response=$(curl --silent --write-out "HTTPSTATUS:%{http_code}" --request POST --ssl-no-revoke \
    --url "$url" \
    --header "Authorization: Bearer $token" \
    --header "x-correlation-id: $uuid" \
    --header "Content-Type: application/json" \
    --data '{"content": "manual test"}')

  local http_code=$(echo "$response" | tr -d '\n' | sed -e 's/.*HTTPSTATUS://')
  local body=$(echo "$response" | sed -e 's/HTTPSTATUS:.*//')

  echo "Response status: $http_code" >&2
  echo "Response body: $body" >&2
  echo "$body"
}

delete_event_comment() {
  echo "---------------------------------------------------------------------------------------------" >&2
  echo "Delete event comment..." >&2
  local token=$1
  local event_id=$3
  local comment_id=$4
  local uuid=$5
  local url="$2/representation/patients/$PATIENT_ID/events/$event_id/comments/$comment_id"

  echo "URL: $url" >&2
  echo "UUID: $uuid" >&2
  echo "Comment ID: $comment_id" >&2

  local response=$(curl --silent --write-out "HTTPSTATUS:%{http_code}" --request DELETE --ssl-no-revoke \
    --url "$url" \
    --header "Authorization: Bearer $token" \
    --header "x-correlation-id: $uuid")

  local http_code=$(echo "$response" | tr -d '\n' | sed -e 's/.*HTTPSTATUS://')
  local body=$(echo "$response" | sed -e 's/HTTPSTATUS:.*//')
  echo "Response status: $http_code" >&2
  echo "Response body: $body" >&2

  if [[ "$http_code" -ne 204 ]]; then
        echo "Error: Failed to delete event comment. HTTP status code: $http_code" >&2
        exit 1
  fi
  echo "Event comment deleted successfully." >&2
}

create_patient_comment() {
  echo "---------------------------------------------------------------------------------------------" >&2
  echo "Create patient comment..." >&2
  local token=$1
  local url="$2/representation/patients/$PATIENT_ID/comments"
  local uuid=$3
  echo "URL: $url" >&2
  echo "UUID: $uuid" >&2

  local response=$(curl --silent --write-out "HTTPSTATUS:%{http_code}" --request POST --ssl-no-revoke \
    --url "$url" \
    --header "Authorization: Bearer $token" \
    --header "x-correlation-id: $uuid" \
    --header "Content-Type: application/json" \
    --data '{"content": "manual test"}')

  local http_code=$(echo "$response" | tr -d '\n' | sed -e 's/.*HTTPSTATUS://')
  local body=$(echo "$response" | sed -e 's/HTTPSTATUS:.*//')
  echo "Response status: $http_code" >&2
  echo "Response body: $body" >&2
  echo "$body"
}

delete_patient_comment() {
  echo "---------------------------------------------------------------------------------------------" >&2
  echo "Delete patient comment..." >&2
  local token=$1
  local comment_id=$3
  local url="$2/representation/patients/$PATIENT_ID/comments/$comment_id"
  local uuid=$4

  echo "UUID: $uuid" >&2

  local response=$(curl --silent --write-out "HTTPSTATUS:%{http_code}" --request DELETE --ssl-no-revoke \
    --url "$url" \
    --header "Authorization: Bearer $token" \
    --header "x-correlation-id: $uuid")

  local http_code=$(echo "$response" | tr -d '\n' | sed -e 's/.*HTTPSTATUS://')
  local body=$(echo "$response" | sed -e 's/HTTPSTATUS:.*//')
  echo "Response status: $http_code"
  echo "Response body: $body"

  if [[ "$http_code" -ne 204 ]]; then
        echo "Error: Failed to delete patient comment. HTTP status code: $http_code" >&2
        exit 1
  fi
  echo "Patient comment deleted successfully." >&2
}

create_label() {
  local token=$1
  local url="$2/representation/labels"
  local uuid=$3
  echo "UUID: $uuid" >&2

  local response=$(curl --silent --write-out "HTTPSTATUS:%{http_code}" --request POST --ssl-no-revoke \
    --url "$url" \
    --header "Authorization: Bearer $token" \
    --header "x-correlation-id: $uuid" \
    --header "Content-Type: application/json" \
    --data '{"text": "Test Label","paletteColor": 0}'
  )

  local http_code=$(echo "$response" | tr -d '\n' | sed -e 's/.*HTTPSTATUS://')
  local body=$(echo "$response" | sed -e 's/HTTPSTATUS:.*//')
  echo "Response status: $http_code" >&2

  if [[ "$http_code" -ne 201 ]]; then
        echo "Error: Failed to create label. HTTP status code: $http_code" >&2
        exit 1
  fi

  echo "Label created successfully. ID: $body" >&2
  echo "$body"
}

delete_label() {
  local label_name=$1
  local table_name="$ENV_NAME-pi-representation-labels"
  local label_id=$(aws dynamodb scan --table-name "$table_name" --filter-expression "#t = :val" --expression-attribute-names '{"#t": "text"}' --expression-attribute-values '{":val": {"S": "Test Label"}}' --query "Items[0].identifier.S" --output text)
  aws dynamodb delete-item --table-name "$table_name" --key "{\"identifier\": {\"S\": \"$label_id\"}}"
  echo "Label deleted successfully. ID: $label_id"
}

push_logs_to_cloudwatch() {
  local token=$1
  local env_url=$2
  
  getStatus $token $env_url
  getStatus $token $env_url
  getStatus $token $env_url
}
#------------------------------------ Scenarios -------------------------------------------
app_config_scenario() {
  echo -e "\n---------------------------------------------------------------------------------------------"
  echo "App Config Scenario"
  echo "---------------------------------------------------------------------------------------------"
  local token=$(request_token)
  local app_config=$(getAppConfig $token $ENV_URL)
  local uuid=$(powershell -Command "[guid]::NewGuid().ToString()")
  updateAppConfig $token $ENV_URL $app_config $uuid
  push_logs_to_cloudwatch $token $ENV_URL
  getLogsAndWriteToFile $uuid "updateAppConfigResult.json"
}

select_patient_scenario() {
  echo -e "\n---------------------------------------------------------------------------------------------"
  echo "Select Patient Scenario"
  echo "---------------------------------------------------------------------------------------------"
  local token=$(request_token)
  local uuid=$(powershell -Command "[guid]::NewGuid().ToString()")
  local status_response=$(getStatus $token $ENV_URL)

  echo "Status response: $status_response"
  getSummary $token $ENV_URL $status_response $uuid
  push_logs_to_cloudwatch $token $ENV_URL
  getLogsAndWriteToFile $uuid "selectPatientResult.json"
}

export_summary_view_scenario() {
  echo -e "\n---------------------------------------------------------------------------------------------"
  echo "Export Summary View Scenario"
  echo "---------------------------------------------------------------------------------------------"
  local token=$(request_token)
  local uuid=$(powershell -Command "[guid]::NewGuid().ToString()")

  exportSummary $token $ENV_URL $uuid
  push_logs_to_cloudwatch $token $ENV_URL
  getLogsAndWriteToFile $uuid "exportSummaryViewResult.json"
}

create_event_comment_scenario() {
  echo -e "\n---------------------------------------------------------------------------------------------"
  echo "Create Event Comment Scenario"
  echo "---------------------------------------------------------------------------------------------"
  local token=$(request_token)
  local uuid=$(powershell -Command "[guid]::NewGuid().ToString()")
  local comment_id=$(create_event_comment $token $ENV_URL $EVENT_ID $uuid)

  push_logs_to_cloudwatch $token $ENV_URL
  getLogsAndWriteToFile $uuid "createEventCommentResult.json"

  echo "Cleaning up after the test..."
  delete_event_comment $token $ENV_URL $EVENT_ID $comment_id $uuid
}

create_patient_comment_scenario() {
  echo -e "\n---------------------------------------------------------------------------------------------"
  echo "Create Patient Comment Scenario"
  echo "---------------------------------------------------------------------------------------------"
  local token=$(request_token)
  local uuid=$(powershell -Command "[guid]::NewGuid().ToString()")
  local comment_id=$(create_patient_comment $token $ENV_URL $uuid)

  push_logs_to_cloudwatch $token $ENV_URL
  getLogsAndWriteToFile $uuid "createPatientCommentResult.json"

  echo "Cleaning up after the test..."
  delete_patient_comment $token $ENV_URL $comment_id $uuid
}

delete_event_comment_scenario() {
  echo -e "\n---------------------------------------------------------------------------------------------"
  echo "Delete Event Comment Scenario"
  echo "---------------------------------------------------------------------------------------------"
  local token=$(request_token)
  local uuid_create=$(powershell -Command "[guid]::NewGuid().ToString()")
  local uuid_delete=$(powershell -Command "[guid]::NewGuid().ToString()")

  comment_id=$(create_event_comment $token $ENV_URL $EVENT_ID $uuid_create)
  echo "Comment ID: $comment_id"

  delete_event_comment $token $ENV_URL $EVENT_ID $comment_id $uuid_delete
  push_logs_to_cloudwatch $token $ENV_URL
  getLogsAndWriteToFile $uuid_delete "deleteEventCommentResult.json"
}

delete_patient_comment_scenario() {
  echo -e "\n---------------------------------------------------------------------------------------------"
  echo "Delete Patient Comment Scenario"
  echo "---------------------------------------------------------------------------------------------"
  local token=$(request_token)
  local status_response=$(getStatus $token $ENV_URL)
  local uuid_create=$(powershell -Command "[guid]::NewGuid().ToString()")
  local uuid_delete=$(powershell -Command "[guid]::NewGuid().ToString()")

  comment_id=$(create_patient_comment $token $ENV_URL $uuid_create)
  echo "Comment ID to be deleted: $comment_id"

  delete_patient_comment $token $ENV_URL $comment_id $uuid_delete
  push_logs_to_cloudwatch $token $ENV_URL
  getLogsAndWriteToFile $uuid_delete "deletePatientCommentResult.json"
}

create_label_scenario() {
  echo -e "\n---------------------------------------------------------------------------------------------"
  echo "Create Label Scenario"
  echo "---------------------------------------------------------------------------------------------"
  local token=$(request_token)
  local uuid=$(powershell -Command "[guid]::NewGuid().ToString()")

  local label_id=$(create_label $token $ENV_URL $uuid)
  push_logs_to_cloudwatch $token $ENV_URL
  getLogsAndWriteToFile $uuid "createLabelResult.json"

  echo "Cleaning up after the test..."
  delete_label $label_id
}

#--------------------------------- Main script execution ----------------------------------
SCRIPT_START_TIME=$(date +%s)

parse_input_parameters "$@"
validate_input_parameters
log_input_parameters
update_putlogevents_policy "Deny"
app_config_scenario
select_patient_scenario
export_summary_view_scenario
create_event_comment_scenario
create_patient_comment_scenario
delete_event_comment_scenario
delete_patient_comment_scenario
create_label_scenario
update_putlogevents_policy "Allow"

SCRIPT_END_TIME=$(date +%s)
SCRIPT_DURATION=$(($SCRIPT_END_TIME - $SCRIPT_START_TIME))
echo "All scenarios executed successfully."
echo "Total script duration: ${SCRIPT_DURATION} seconds"
