param (
    [string]$ACTION,
    [string]$API_URL,
    [string]$JWT_ACCESS_TOKEN,
    [string]$FOLDER
)

# Exit on error
$ErrorActionPreference = "Stop"

# Validate parameters
if (-not $ACTION -or -not $API_URL -or -not $JWT_ACCESS_TOKEN -or -not $FOLDER) {
    Write-Host "Usage: .\representation-app-preset.ps1 -ACTION <ACTION> -API_URL <API_URL> -JWT_ACCESS_TOKEN <JWT_ACCESS_TOKEN> -FOLDER <FOLDER>"
    exit 1
}

# Validate action
if ($ACTION -notin @("create", "update", "delete")) {
    Write-Host "Invalid action. Allowed actions are: create, update, delete."
    exit 1
}

# Check if folder exists
if (-not (Test-Path -Path $FOLDER -PathType Container)) {
    Write-Host "Folder $FOLDER does not exist."
    exit 1
}

# Iterate over JSON files in the folder
$JSONFiles = Get-ChildItem -Path $FOLDER -Filter *.json
if ($JSONFiles.Count -eq 0) {
    Write-Host "No JSON files found in the folder."
    exit 1
}

foreach ($File in $JSONFiles) {
    # Extract ID from JSON file
    $Content = Get-Content -Path $File.FullName | ConvertFrom-Json
    $ID = $Content.id

    if (-not $ID) {
        Write-Host "No valid ID found in $($File.Name). Skipping..."
        continue
    }

    # Set the URL for the API call
    $URL = "${API_URL}configs/app-preset/$ID"

    # Perform the API call using Invoke-RestMethod
    if ($ACTION -eq "create" -or $ACTION -eq "update") {
        $Response = Invoke-RestMethod -Uri $URL -Method Put -Headers @{
            Authorization = "Bearer $JWT_ACCESS_TOKEN"
            "Content-Type" = "application/json"
        } -Body (Get-Content -Path $File.FullName -Raw) -ErrorAction SilentlyContinue -StatusCodeVariable StatusCode
    } elseif ($ACTION -eq "delete") {
        $Response = Invoke-RestMethod -Uri $URL -Method Delete -Headers @{
            Authorization = "Bearer $JWT_ACCESS_TOKEN"
        } -ErrorAction SilentlyContinue -StatusCodeVariable StatusCode
    }

    # Log the response
    $Date = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    Write-Host "$Date | HTTP $StatusCode | $URL"
}