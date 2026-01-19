#!/bin/bash

set -e -u -o pipefail

# Check if all required parameters are provided
if [ "$#" -ne 3 ]; then
  echo "Usage: $0 <API_URL> <JWT_ACCESS_TOKEN> <FOLDER>"
  exit 1
fi

# Assign parameters to variables
API_URL=$1
JWT_ACCESS_TOKEN=$2
FOLDER=$3

# Check if folder exists
if [ ! -d "$FOLDER" ]; then
  echo "Folder $FOLDER does not exist."
  exit 1
fi

# Iterate over JSON files in the folder
# Note that each iteration will overwrite the previously uploaded configuration.
for FILE in "$FOLDER"/*.json; do
  if [ ! -f "$FILE" ]; then
    echo "No JSON files found in the folder."
    exit 1
  fi

  URL="${API_URL}config/data"
  echo "Uploading $FILE"
  RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" -X PUT "$URL" \
    -H "Authorization: Bearer $JWT_ACCESS_TOKEN" \
    -H "Content-Type: application/json" \
    --data @"$FILE")

  # Log the response
  DATE=$(date '+%Y-%m-%d %H:%M:%S')
  echo "$DATE | HTTP $RESPONSE | $URL"
done