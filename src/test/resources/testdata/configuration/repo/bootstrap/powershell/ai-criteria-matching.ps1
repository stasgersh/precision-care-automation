param (
    [string]$API_URL,
    [string]$JWT_ACCESS_TOKEN,
    [string]$FOLDER
)

# Exit on error
$ErrorActionPreference = "Stop"

# Validate parameters
if (-not $API_URL -or -not $JWT_ACCESS_TOKEN -or -not $FOLDER) {
    Write-Host "Usage: .\ai-criteria-matching.ps1 -API_URL <API_URL> -JWT_ACCESS_TOKEN <JWT_ACCESS_TOKEN> -FOLDER <FOLDER>"
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
    $URL = "${API_URL}config/data"

    # Perform the API call using Invoke-RestMethod
    Write-Host "Processing $($File.Name)"
    $Response = Invoke-RestMethod -Uri $URL -Method Put -Headers @{
        Authorization = "Bearer $JWT_ACCESS_TOKEN"
        "Content-Type" = "application/json"
    } -Body (Get-Content -Path $File.FullName -Raw) -ErrorAction SilentlyContinue -StatusCodeVariable StatusCode

    # Log the response
    $Date = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    Write-Host "$Date | HTTP $StatusCode | $URL"
}