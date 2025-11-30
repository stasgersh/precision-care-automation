# restore.sh

## Overview

`restore.sh` is a Bash script designed to automate the process of restoring an Amazon DynamoDB table from its latest backup. The script performs the following steps:
1. **Fetch Latest Backup**: The script retrieves the latest backup ARN for the specified table.
2. **Delete Original Table**: The script deletes the existing table to prepare for restoration.
3. **Restore Table**: The script restores the table from the latest backup. If `--restore-entire-table` is set to `false`, secondary indexes are excluded.
4. **Delete Backup**: After the restoration is complete, the script deletes the backup to save costs.

---

## Prerequisites

Before using the script, ensure the following:
- AWS CLI is installed and configured on your system.
- You have the necessary IAM permissions to perform the following actions:
  - `dynamodb:ListBackups`
  - `dynamodb:DeleteTable`
  - `dynamodb:RestoreTableFromBackup`
  - `dynamodb:DescribeTable`
  - `dynamodb:DeleteBackup`
- Bash shell is available on your system.

---

## Usage

```bash
./restore.sh --table-name <table-name> --aws-region <aws-region> [--aws-profile <aws-profile>] [--restore-entire-table <true|false>]
```

### Required Parameters
- `--table-name <table-name>`: The name of the DynamoDB table to restore.
- `--aws-region <aws-region>`: The AWS region where the table resides.

### Optional Parameters
- `--aws-profile <aws-profile>`: The AWS CLI profile to use for authentication. If not provided, the default profile is used.
- `--restore-entire-table <true|false>`: Whether to restore the entire table, including secondary indexes. Defaults to `false`.

---

## Example

### Restore a Table Using Default AWS Profile
```bash
./restore.sh --table-name MyTable --aws-region us-east-1
```

### Restore a Table Using a Specific AWS Profile
```bash
./restore.sh --table-name MyTable --aws-region us-east-1 --aws-profile my-profile
```

### Restore a Table With Secondary Indexes
```bash
./restore.sh --table-name MyTable --aws-region us-east-1 --restore-entire-table true
```

---
