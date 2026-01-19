#!/bin/bash

set -e -u -o pipefail

# Check if all required parameters are provided
if [ "$#" -ne 4 ]; then
  echo "Usage: $0 <ACTION> <API_URL> <JWT_ACCESS_TOKEN> <SUB_FOLDER>"
  exit 1
fi

# Assign parameters to variables
ACTION=$1
API_URL=$2
JWT_ACCESS_TOKEN=$3
SUB_FOLDER=$4

# Validate action
if [[ "$ACTION" != "create" && "$ACTION" != "update" && "$ACTION" != "delete" ]]; then
  echo "Invalid action. Allowed actions are: create, update, delete."
  exit 1
fi

# Validate if the subfolder exists
if [ ! -d "$SUB_FOLDER" ]; then
  echo "Subfolder $SUB_FOLDER does not exist."
  exit 1
fi

# Check and call the corresponding scripts for each subfolder
if [ -d "$SUB_FOLDER/app-preset" ]; then
  echo "Processing app-preset..."
  ./bootstrap/shell/representation-app-preset.sh "$ACTION" "$API_URL" "$JWT_ACCESS_TOKEN" "$SUB_FOLDER/app-preset"
fi

if [ -d "$SUB_FOLDER/labels" ]; then
  echo "Processing labels..."
  ./bootstrap/shell/representation-labels.sh "$ACTION" "$API_URL" "$JWT_ACCESS_TOKEN" "$SUB_FOLDER/labels"
fi

if [ -d "$SUB_FOLDER/fulfillment-query-set" ]; then
  echo "Processing fulfillment-query-set..."
  ./bootstrap/shell/representation-fulfillment-query-set.sh "$ACTION" "$API_URL" "$JWT_ACCESS_TOKEN" "$SUB_FOLDER/fulfillment-query-set"
fi

if [ -d "$SUB_FOLDER/representation" ]; then
  echo "Processing representation..."
  ./bootstrap/shell/representation-representation.sh "$ACTION" "$API_URL" "$JWT_ACCESS_TOKEN" "$SUB_FOLDER/representation"
fi

echo "Processing completed for $SUB_FOLDER."