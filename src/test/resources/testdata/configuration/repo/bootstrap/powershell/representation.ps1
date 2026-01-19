param (
    [string]$ACTION,
    [string]$API_URL,
    [string]$JWT_ACCESS_TOKEN,
    [string]$SUB_FOLDER
)

# Exit on error
$ErrorActionPreference = "Stop"

# Validate parameters
if (-not $ACTION -or -not $API_URL -or -not $JWT_ACCESS_TOKEN -or -not $SUB_FOLDER) {
    Write-Host "Usage: ./representation.ps1 -ACTION <ACTION> -API_URL <API_URL> -JWT_ACCESS_TOKEN <JWT_ACCESS_TOKEN> -SUB_FOLDER <SUB_FOLDER>"
    exit 1
}

# Validate action
if ($ACTION -notin @("create", "update", "delete")) {
    Write-Host "Invalid action. Allowed actions are: create, update, delete."
    exit 1
}

# Validate if the subfolder exists
if (-not (Test-Path -Path $SUB_FOLDER -PathType Container)) {
    Write-Host "Subfolder $SUB_FOLDER does not exist."
    exit 1
}

# Check and call the corresponding scripts for each subfolder
if (Test-Path -Path "$SUB_FOLDER/app-preset" -PathType Container) {
    Write-Host "Processing app-preset..."
    ./bootstrap/powershell/representation-app-preset.ps1 $ACTION $API_URL $JWT_ACCESS_TOKEN "$SUB_FOLDER/app-preset"
}

if (Test-Path -Path "$SUB_FOLDER/labels" -PathType Container) {
    Write-Host "Processing labels..."
    ./bootstrap/powershell/representation-labels.ps1 $ACTION $API_URL $JWT_ACCESS_TOKEN "$SUB_FOLDER/labels"
}

if (Test-Path -Path "$SUB_FOLDER/fulfillment-query-set" -PathType Container) {
    Write-Host "Processing fulfillment-query-set..."
    ./bootstrap/powershell/representation-fulfillment-query-set.ps1 $ACTION $API_URL $JWT_ACCESS_TOKEN "$SUB_FOLDER/fulfillment-query-set"
}

if (Test-Path -Path "$SUB_FOLDER/representation" -PathType Container) {
    Write-Host "Processing representation..."
    ./bootstrap/powershell/representation-representation.ps1 $ACTION $API_URL $JWT_ACCESS_TOKEN "$SUB_FOLDER/representation"
}

Write-Host "Processing completed for $SUB_FOLDER."