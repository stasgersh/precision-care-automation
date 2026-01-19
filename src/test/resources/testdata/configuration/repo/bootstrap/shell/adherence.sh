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

# Iterate over JSON files in the folder
for FILE in "$FOLDER"/*.json; do
  if [ ! -f "$FILE" ]; then
    echo "No JSON files found in the folder."
    exit 1
  fi

  URL="${API_URL}treatment"

  # Perform the API call using cURL
  if [ "$ACTION" == "create" ]; then
    RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$URL" \
      -H "Authorization: Bearer $JWT_ACCESS_TOKEN" \
      -H "Content-Type: application/json" \
      --data @"$FILE")
    # Log the response
    DATE=$(date '+%Y-%m-%d %H:%M:%S')
    echo "$DATE | HTTP $RESPONSE | $URL"
  fi
done