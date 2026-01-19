#!/bin/bash

set -e -u -o pipefail

# Check if all required parameters are provided
if [ "$#" -ne 4 ]; then
  echo "Usage: $0 <ACTION> <API_URL> <JWT_ACCESS_TOKEN> <FOLDER>"
  exit 1
fi

# Assign parameters to variables
ACTION=$1
API_URL=$2
JWT_ACCESS_TOKEN=$3
FOLDER=$4

# For create action, get first and second files
if [ "$ACTION" == "create" ]; then
  FIRST_FILE=uncategorized.json
  SECOND_FILE=patient.json
fi

# Validate action
if [[ "$ACTION" != "create" && "$ACTION" != "update" && "$ACTION" != "delete" ]]; then
  echo "Invalid action. Allowed actions are: create, update, delete."
  exit 1
fi

# Check if folder exists
if [ ! -d "$FOLDER" ]; then
  echo "Folder $FOLDER does not exist."
  exit 1
fi

# Function to poll until unlocked
poll_until_unlocked() {
  local poll_url=$1
  local wait_seconds=10
  local attempt=1
  
  echo "Polling $poll_url until unlocked..."
  
  while true; do
    local response=$(curl -s -X GET "$poll_url" \
      -H "Authorization: Bearer $JWT_ACCESS_TOKEN" \
      -H "Content-Type: application/json")
    
    local locked=$(echo "$response" | jq -r '.locked')
    
    if [[ "$locked" == "false" ]]; then
      echo "Resource unlocked after $attempt attempts."
      return 0
    fi
    
    echo "Attempt $attempt: Resource still locked. Waiting $wait_seconds seconds..."
    sleep $wait_seconds
    ((attempt++))
  done
}

if [ "$ACTION" == "create" ]; then
  # Step 1: Upload first specific file
  FIRST_FILE_PATH="$FOLDER/$FIRST_FILE"
  if [ ! -f "$FIRST_FILE_PATH" ]; then
    echo "First file $FIRST_FILE_PATH does not exist."
    exit 1
  fi
  UNCATEGORIZED_ENTITY_ID=$(jq -r '.classId' "$FIRST_FILE_PATH")
  
  URL="${API_URL}data-model"
  RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$URL" \
    -H "Authorization: Bearer $JWT_ACCESS_TOKEN" \
    -H "Content-Type: application/json" \
    --data @"$FIRST_FILE_PATH")
  
  DATE=$(date '+%Y-%m-%d %H:%M:%S')
  echo "$DATE | HTTP $RESPONSE | $URL | $FIRST_FILE_PATH"
  
  # Step 2: Poll until unlocked
  poll_until_unlocked "${API_URL}data-model/${UNCATEGORIZED_ENTITY_ID}"
  
  # Step 3: Upload second specific file
  SECOND_FILE_PATH="$FOLDER/$SECOND_FILE"

  if [ ! -f "$SECOND_FILE_PATH" ]; then
    echo "Second file $SECOND_FILE_PATH does not exist."
    exit 1
  fi
  PATIENT_ENTITY_ID=$(jq -r '.classId' "$SECOND_FILE_PATH")
  
  RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$URL" \
    -H "Authorization: Bearer $JWT_ACCESS_TOKEN" \
    -H "Content-Type: application/json" \
    --data @"$SECOND_FILE_PATH")
  
  DATE=$(date '+%Y-%m-%d %H:%M:%S')
  echo "$DATE | HTTP $RESPONSE | $URL | $SECOND_FILE_PATH"
  
  # Step 4: Poll until unlocked
  poll_until_unlocked "${URL}/${PATIENT_ENTITY_ID}"
  
  # Step 5: Upload all other files (excluding the first two)
  echo "Uploading remaining files..."
  for FILE in "$FOLDER"/*.json; do
    # Skip the first and second files
    if [ "$FILE" == "$FIRST_FILE_PATH" ] || [ "$FILE" == "$SECOND_FILE_PATH" ]; then
      continue
    fi
    
    if [ -f "$FILE" ]; then
      RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" -X POST "${URL}" \
        -H "Authorization: Bearer $JWT_ACCESS_TOKEN" \
        -H "Content-Type: application/json" \
        --data @"$FILE")
      
      DATE=$(date '+%Y-%m-%d %H:%M:%S')
      echo "$DATE | HTTP $RESPONSE | $URL | $FILE"
    fi
  done
else
  # Original logic for update and delete actions
  for FILE in "$FOLDER"/*.json; do
    if [ ! -f "$FILE" ]; then
      echo "No JSON files found in the folder."
      exit 1
    fi

    # Extract ID from JSON file if action is update or delete
    if [[ "$ACTION" == "update" || "$ACTION" == "delete" ]]; then
      ID=$(jq -r '.classId' "$FILE")
      if [ "$ID" == "null" ] || [ -z "$ID" ]; then
        echo "No valid ID found in $FILE. Skipping..."
        continue
      fi
      URL="${API_URL}data-model/$ID"
    else
      URL="${API_URL}data-model"
    fi

    # Perform the API call using cURL
    if [ "$ACTION" == "update" ]; then
      RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" -X PUT "$URL" \
        -H "Authorization: Bearer $JWT_ACCESS_TOKEN" \
        -H "Content-Type: application/json" \
        --data @"$FILE")
    elif [ "$ACTION" == "delete" ]; then
      RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" -X DELETE "$URL" \
        -H "Authorization: Bearer $JWT_ACCESS_TOKEN")
    fi

    # Log the response
    DATE=$(date '+%Y-%m-%d %H:%M:%S')
    echo "$DATE | HTTP $RESPONSE | $URL | $FILE"
  done
fi