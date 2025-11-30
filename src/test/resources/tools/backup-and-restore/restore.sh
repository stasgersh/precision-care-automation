#!/bin/bash

set -euo pipefail

usage() {
  echo "Usage: $0 --table-name <table-name> --aws-region <aws-region> [--aws-profile <aws-profile>] [--restore-entire-table <true|false>]"
  exit 1
}

error_exit() {
  echo "ERROR: $1" >&2
  read -p "Press Enter to exit..."
}

STARTTIME="$(date +%s)"

# Parse named parameters
TABLE_NAME=""
AWS_REGION=""
AWS_PROFILE=""
RESTORE_ENTIRE_TABLE="false"

while [[ "$#" -gt 0 ]]; do
  case $1 in
    --table-name) TABLE_NAME="$2"; shift ;;
    --aws-region) AWS_REGION="$2"; shift ;;
    --aws-profile) AWS_PROFILE="$2"; shift ;;
    --restore-entire-table) RESTORE_ENTIRE_TABLE="$2"; shift ;;
    *) error_exit "Unknown parameter: $1"; usage ;;
  esac
  shift
done

# Validate parameters
[[ -z "$TABLE_NAME" || -z "$AWS_REGION" ]] && error_exit "Missing required parameters." && usage

echo "---------------------------------------------------------------------------------------------"
echo "Run the script with the following parameters:"
echo "Table Name: $TABLE_NAME"
echo "AWS Region: $AWS_REGION"
echo "AWS Profile: $AWS_PROFILE"
echo "Restore Entire Table (with secondary indexes): $RESTORE_ENTIRE_TABLE"

# Step 1: List backups for the source table
echo "---------------------------------------------------------------------------------------------"
echo "1. Fetching the latest backup for the table: $TABLE_NAME"
BACKUP_ARN=$(aws dynamodb list-backups \
  --table-name "$TABLE_NAME" \
  --region "$AWS_REGION" \
  ${AWS_PROFILE:+--profile "$AWS_PROFILE"} \
  --query "sort_by(BackupSummaries, &BackupCreationDateTime)[-1].BackupArn" \
  --output text) || error_exit "Failed to list backups for the table: $TABLE_NAME"

if [[ "$BACKUP_ARN" == "None" ]]; then
  error_exit "No backups found for the table: $TABLE_NAME"
fi

echo "Latest backup ARN retrieved: $BACKUP_ARN"

# Step 2: Delete the original table
echo "---------------------------------------------------------------------------------------------"
echo "2. Deleting the original table: $TABLE_NAME"
aws dynamodb delete-table \
  --table-name "$TABLE_NAME" \
  --region "$AWS_REGION" \
  ${AWS_PROFILE:+--profile "$AWS_PROFILE"} || error_exit "Failed to initiate deletion of the table: $TABLE_NAME"

echo "Table deletion initiated: $TABLE_NAME"

# Check the status of the table deletion
echo "Checking the status of the table deletion: $TABLE_NAME"
while true; do
  TABLE_STATUS=$(aws dynamodb describe-table \
    --table-name "$TABLE_NAME" \
    --region "$AWS_REGION" \
    ${AWS_PROFILE:+--profile "$AWS_PROFILE"} \
    --query "Table.TableStatus" \
    --output text 2>/dev/null || true)

  if [[ "$TABLE_STATUS" == "DELETING" ]]; then
    echo "Table is still being deleted. Waiting for 30 seconds..."
    sleep 30
  elif [[ -z "$TABLE_STATUS" ]]; then
    echo "Table deletion completed successfully: $TABLE_NAME"
    break
  else
    error_exit "Unexpected table status: $TABLE_STATUS"
  fi
done

# Step 3: Restore the table from the backup
echo "---------------------------------------------------------------------------------------------"
echo "3. Restoring the original table from the backup..."
aws dynamodb restore-table-from-backup \
  --target-table-name "$TABLE_NAME" \
  --backup-arn "$BACKUP_ARN" \
  --region "$AWS_REGION" \
  ${AWS_PROFILE:+--profile "$AWS_PROFILE"} \
  $( [[ "$RESTORE_ENTIRE_TABLE" == "false" ]] && echo "--global-secondary-index-override [] --local-secondary-index-override []" ) || error_exit "Failed to start the restoration process for the original table."

echo "The original table restoration process started successfully for: $TABLE_NAME"

# Check the status of the restored original table
echo "Checking the status of the restored original table: $TABLE_NAME"
while true; do
  TABLE_STATUS=$(aws dynamodb describe-table \
    --table-name "$TABLE_NAME" \
    --region "$AWS_REGION" \
    ${AWS_PROFILE:+--profile "$AWS_PROFILE"} \
    --query "Table.TableStatus" \
    --output text) || error_exit "Failed to describe the table: $TABLE_NAME"

  if [[ "$TABLE_STATUS" == "CREATING" ]]; then
    echo "Table is still being created. Waiting for 30 seconds..."
    sleep 30
  else
    echo "Original table restored successfully! Status: $TABLE_STATUS"
    break
  fi
done

# Step 4: Delete backup
echo "---------------------------------------------------------------------------------------------"
echo "Step 4: Deleting the backup..."
aws dynamodb delete-backup \
  --backup-arn "$BACKUP_ARN" \
  --region "$AWS_REGION" \
  ${AWS_PROFILE:+--profile "$AWS_PROFILE"} || error_exit "Failed to delete the backup: $BACKUP_ARN"

ENDTIME="$(date +%s)"
RUNTIME=$((ENDTIME-STARTTIME))
echo "All operations completed successfully! - Total runtime: $RUNTIME seconds."
read -p "Press Enter to exit..."