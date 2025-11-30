#!/bin/bash

#--------------------------------- Function definitions ----------------------------------
parse_input_parameters(){
  TEST="false"
  while [[ "$#" -gt 0 ]]; do
      case $1 in
          --test-type) TEST_TYPE="$2"; shift ;;
          --eat-url) EAT_URL="$2"; shift ;;
          --username) USERNAME="$2"; shift ;;
          --minutes) MINUTES="$2"; shift ;;
          --test) TEST="$2"; shift ;;
          --idam-url) IDAM_URL="$2"; shift ;;
          --client-id) CLIENT_ID="$2"; shift ;;
          --client-secret) CLIENT_SECRET="$2"; shift ;;
          --scope) SCOPE="$2"; shift ;;
          --audience) AUDIENCE="$2"; shift ;;
          --eat-version) EAT_VERSION="$2"; shift ;;
          --tenant-id) TENANT_ID="$2"; shift ;;
          *) echo "Unknown parameter passed: $1"; exit 1 ;;
      esac
      shift
  done
}

log_input_parameters(){
  echo "---------------------------------------------------------------------------------------------"
  echo "Run the script with the following parameters:"
  echo "- Test type: $TEST_TYPE"
  echo "- EAT URL: $EAT_URL"
  echo "- EAT version: $EAT_VERSION"
  echo "- Username: $USERNAME"
  echo "- Minutes: $MINUTES"
  echo "- Test mode: $TEST"
  echo "- IDAM URL: $IDAM_URL"
  echo "- Client ID: $CLIENT_ID"
  echo "- Client secret: $CLIENT_SECRET"
  echo "- Scope: $SCOPE"
  echo "- Audience: $AUDIENCE"
  echo "- Tenant ID: $TENANT_ID"
}

validate_input_parameters() {
    # Validate required parameters
    if [[ -z "$TEST_TYPE" || -z "$EAT_URL" || -z "$USERNAME" || -z "$MINUTES" || -z "$IDAM_URL" || -z "$CLIENT_ID" || -z "$CLIENT_SECRET" || -z "$SCOPE" || -z "$AUDIENCE" || -z "$EAT_VERSION" || -z "$TENANT_ID" ]]; then
        echo "Error: Missing required parameters."
        echo "Usage: $0 --test-type <type> --eat-url <url> --username <name> --minutes <minutes> --idam-url <url> --client-id <id> --client-secret <secret> --scope <scope> --audience <audience> --eat-version <v1|v2> --tenant-id <id> [--test <true|false>]"
        exit 1
    fi

    # Validate EAT version
    if [[ "$EAT_VERSION" != "v1" && "$EAT_VERSION" != "v2" ]]; then
        echo "Error: Invalid EAT version. Allowed values are: v1, v2."
        exit 1
    fi

    # Validate test type
    local VALID_TEST_TYPES=("CreateComment" "DeleteComment" "CreateLabel")
    local VALID=false
    for TYPE in "${VALID_TEST_TYPES[@]}"; do
        if [[ "$TYPE" == "$TEST_TYPE" ]]; then
            VALID=true
            break
        fi
    done

    if [[ "$VALID" == false ]]; then
        echo "Error: Invalid test-type. Allowed values are: ${VALID_TEST_TYPES[*]}."
        exit 1
    fi
}

request_token() {
    local idam_response=$(curl --silent --request POST \
        --url "$IDAM_URL" \
        --header 'Content-Type: application/x-www-form-urlencoded' \
        --data grant_type=client_credentials \
        --data client_id="$CLIENT_ID" \
        --data client_secret="$CLIENT_SECRET" \
        --data "scope=$SCOPE" \
        --data audience="$AUDIENCE")

    if [[ -z "$idam_response" ]]; then
        echo "Error: Failed to retrieve token."
        exit 1
    fi

    echo "$idam_response" | jq -r '.access_token'
}

fetch_audit_logs() {
    local eat_version="$1"
    local audit_logs="[]"
    
    echo "---------------------------------------------------------------------------------------------" >&2
    echo "Requesting token..." >&2
    local token=$(request_token)
    echo "Token: $token" >&2
    
    echo "---------------------------------------------------------------------------------------------" >&2
    echo "Request audit logs from EAT $eat_version..." >&2
    if [[ $eat_version == "v1" ]]; then
        local current_time=$(date -u +%s)  # Get current time in UTC
        local x_minutes=$((current_time - (MINUTES * 60)))

        echo "CURRENT_TIME (UTC): $current_time - $(date -u -d @"$current_time")" >&2
        echo "X_MINUTES (UTC): $x_minutes - $(date -u -d @"$x_minutes")" >&2

        for PAGE in {0..100}; do
            echo "Fetching page $PAGE..." >&2
            local PAGE_LOGS=$(curl --silent --request GET --ssl-no-revoke \
                --url "$EAT_URL?page=$PAGE&startDate=$(date -u +%Y-%m-%d)&endDate=$(date -u +%Y-%m-%d)" \
                --header "Authorization: Bearer $token" \
                --header "x-tenant-id: $TENANT_ID")

            if [[ -n "$PAGE_LOGS" ]]; then
                local PAGE_ITEMS=$(echo "$PAGE_LOGS" | jq '.items')
                audit_logs=$(echo "$audit_logs" "$PAGE_ITEMS" | jq -s 'add')
            fi

            if [[ -z "$PAGE_ITEMS" || "$PAGE_ITEMS" == "null" ]]; then
                echo "Error: PAGE_ITEMS is empty or invalid." >&2
                break
            fi

            local last_event_date=$(echo "$PAGE_ITEMS" | jq -r '.[-1].eventDate' | xargs -I {} date -u -d {} +%s)
            if [[ $? -ne 0 || -z "$last_event_date" || ! "$last_event_date" =~ ^[0-9]+$ ]]; then
                echo "Error: last_event_date is invalid or not a number." >&2
                exit 1
            fi

            echo "Debug: last_event_date=$last_event_date - $(date -u -d @"$last_event_date"), x_minutes=$x_minutes - $(date -u -d @"$x_minutes")" >&2

            if [[ "$last_event_date" -lt "$x_minutes" ]]; then
                echo "Reached events older than $MINUTES minutes. Stopping pagination." >&2
                break
            else
                echo "Debug: LAST_EVENT_DATE is within the last $MINUTES minutes." >&2
            fi
        done
    else
        local page_logs=$(curl --silent --request GET --ssl-no-revoke \
            --url "$EAT_URL?outcome=4&date=$(date +%Y-%m-%d)&agent=$USERNAME" \
            --header "Authorization: Bearer $token" \
            --header "x-tenant-id: $TENANT_ID")

        if [[ -n "$page_logs" ]]; then
            echo "First page fetched successfully." >&2
            local page_items=$(echo "$page_logs" | jq '.entry')
            audit_logs=$(echo "$audit_logs" "$page_items" | jq -s 'add')
            local next_url=$(echo "$page_logs" | jq -r '.link[]? | select(.relation=="next") | .url')
            while [[ -n "$next_url" && "$next_url" != "null" ]]; do
                echo "Fetching next page..." >&2
                local next_logs=$(curl --silent --request GET --ssl-no-revoke \
                    --url "$next_url" \
                    --header "Authorization: Bearer $token" \
                    --header "x-tenant-id: $TENANT_ID")

                if [[ -z "$next_logs" ]]; then
                    echo "No response or invalid JSON from next page. Stopping pagination." >&2
                    break
                fi

                page_items=$(echo "$next_logs" | jq '.entry')
                if [[ -z "$page_items" || "$page_items" == "null" || "$page_items" == "[]" ]]; then
                    echo "No more entries found in next page. Stopping pagination." >&2
                    break
                fi

                audit_logs=$(echo "$audit_logs" "$page_items" | jq -s 'add')
                next_url=$(echo "$next_logs" | jq -r '.link[]? | select(.relation=="next") | .url')
          
                if [[ -z "$next_url" || "$next_url" == "null" || "$next_url" == "" ]]; then
                    echo "No further next page. Stopping pagination." >&2
                    break
                fi
            done
        fi
    fi
    
    echo "$audit_logs"
}

filter_logs() {
    local eat_version="$1"
    echo "---------------------------------------------------------------------------------------------"
    echo "Filtering audit logs for EAT $eat_version..."
    local FILTER_KEY=""
    case $TEST_TYPE in
        CreateComment)
            FILTER_KEY="failed to create comment"
            ;;
        DeleteComment)
            FILTER_KEY="failed to delete comment"
            ;;
        CreateLabel)
            FILTER_KEY="failed to create label"
            ;;
    esac

    local FILTERED_LOGS
    if [[ "$eat_version" == "v1" ]]; then
        FILTERED_LOGS=$(echo "$AUDIT_LOGS" | jq --arg USERNAME "$USERNAME" --arg FILTER_KEY "$FILTER_KEY" '
            map(select(
                (.sources[0].id // "" | contains($USERNAME)) and
                (.objectDetails[0].value // "" | contains($FILTER_KEY))
            ))')
    else
        local current_time=$(date -u +%s)
        echo "CURRENT_TIME (UTC): $(date -u -d @"$current_time" +"%Y-%m-%dT%H:%M:%SZ")"
        
        local x_minutes=$((current_time - (MINUTES * 60)))
        x_minutes=$(date -u -d @"$x_minutes" +"%Y-%m-%dT%H:%M:%SZ")
        echo "X_MINUTES (UTC): $x_minutes"
        
        FILTERED_LOGS=$(echo "$AUDIT_LOGS" | jq --arg X_MINUTES "$x_minutes" --arg FILTER_KEY "$FILTER_KEY" '
            map(select(
                (.resource.outcomeDesc // "" | contains($FILTER_KEY)) and
                ((.resource.recorded[0:19] + "Z" | fromdateiso8601) >= ($X_MINUTES | fromdateiso8601))
            ))')
    fi
        
    if [[ -z "$FILTERED_LOGS" || "$FILTERED_LOGS" == "[]" ]]; then
        echo "No logs found containing USERNAME: $USERNAME and TEST_TYPE: $TEST_TYPE"
        echo "$FILTERED_LOGS" > result.json
    else
        echo "Filtered Logs: $FILTERED_LOGS"
        echo "$FILTERED_LOGS" > result.json
    fi
}

read_audit_logs_from_file(){
  echo "Test mode is enabled. Using test_audit_logs_$EAT_VERSION.json instead of making API calls." >&2
  if [ -f "test_audit_logs_$EAT_VERSION.json" ]; then
      local file_content
      file_content=$(cat test_audit_logs_$EAT_VERSION.json)
      echo "Loaded audit logs from test file." >&2
      echo "$file_content"
  else
      echo "Error: test_audit_logs.json not found." >&2
      exit 1
  fi
}

#--------------------------------- Main script execution ----------------------------------
parse_input_parameters "$@"
validate_input_parameters
log_input_parameters

AUDIT_LOGS="[]"
if [[ "$TEST" == true ]]; then
    AUDIT_LOGS=$(read_audit_logs_from_file)
else
    AUDIT_LOGS=$(fetch_audit_logs "$EAT_VERSION")
fi

echo "$AUDIT_LOGS" > audit_logs.json
filter_logs "$EAT_VERSION"