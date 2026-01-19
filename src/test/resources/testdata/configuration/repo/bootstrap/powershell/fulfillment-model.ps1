<#
.SYNOPSIS
Performs actions (create, update, delete) on data models using an API.

.DESCRIPTION
This script takes an action, API URL, JWT access token, and folder as parameters.
It then performs the specified action on the JSON files within the given folder.
For the 'create' action, it uploads specific files in a defined order and polls until unlocked.
For 'update' and 'delete' actions, it iterates through the JSON files, extracts the ID, and performs the corresponding API call.

.PARAMETER Action
The action to perform: create, update, or delete.

.PARAMETER ApiUrl
The base URL of the API.

.PARAMETER JwtAccessToken
The JWT access token for authentication.

.PARAMETER Folder
The folder containing the JSON files.
#>
[CmdletBinding()]
param(
    [Parameter(Mandatory=$true)]
    [ValidateSet('create', 'update', 'delete')]
    [string]$Action,

    [Parameter(Mandatory=$true)]
    [string]$ApiUrl,

    [Parameter(Mandatory=$true)]
    [string]$JwtAccessToken,

    [Parameter(Mandatory=$true)]
    [string]$Folder
)

# Set error action preference to stop on any error
$ErrorActionPreference = 'Stop'

# For create action, define first and second files
if ($Action -eq 'create') {
    $FirstFile = 'uncategorized.json'
    $SecondFile = 'patient.json'
}

# Check if folder exists
if (-not (Test-Path -Path $Folder -PathType Container)) {
    Write-Error "Folder $Folder does not exist."
    exit 1
}

function Poll-UntilUnlocked {
    param(
        [string]$PollUrl
    )
    
    $waitSeconds = 10
    $attempt = 1
    
    Write-Host "Polling $PollUrl until unlocked..."

    while ($true) {
        $response = Invoke-RestMethod -Uri $PollUrl -Method Get -Headers @{
            "Authorization" = "Bearer $($JwtAccessToken)"
            "Content-Type" = "application/json"
        } -ErrorAction Stop

        $locked = $response.locked

        if ($locked -eq $false) {
            Write-Host "Resource unlocked after $attempt attempts."
            return
        }

        Write-Host "Attempt ${attempt}: Resource still locked. Waiting $waitSeconds seconds..."
        Start-Sleep -Seconds $waitSeconds
        $attempt++
    }
}

if ($Action -eq 'create') {
    # Step 1: Upload first specific file
    $FirstFilePath = Join-Path -Path $Folder -ChildPath $FirstFile
    if (-not (Test-Path -Path $FirstFilePath -PathType Leaf)) {
        Write-Error "First file $FirstFilePath does not exist."
        exit 1
    }
  
    try {
        $UncategorizedEntityId = (Get-Content -Path $FirstFilePath -Raw | ConvertFrom-Json).classId
    } catch {
        Write-Error "Failed to extract classId from ${FirstFilePath}: $($_.Exception.Message)"
        exit 1
    }
    
    $Url = "$($ApiUrl)data-model"
    
    try {
        $response = Invoke-RestMethod -Uri $Url -Method Post -Headers @{
            "Authorization" = "Bearer $($JwtAccessToken)"
            "Content-Type" = "application/json"
        } -Body (Get-Content -Path $FirstFilePath) -ErrorAction Stop -StatusCodeVariable StatusCode
    } catch {
        Write-Error "Failed to upload ${FirstFilePath}: $($_.Exception.Message)"
        exit 1
    }
    
    $date = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    Write-Host "$date | HTTP $StatusCode | $Url | $FirstFilePath"
    
    # Step 2: Poll until unlocked
    Poll-UntilUnlocked "$($ApiUrl)data-model/$UncategorizedEntityId"
    
    # Step 3: Upload second specific file
    $SecondFilePath = Join-Path -Path $Folder -ChildPath $SecondFile
    if (-not (Test-Path -Path $SecondFilePath -PathType Leaf)) {
        Write-Error "Second file $SecondFilePath does not exist."
        exit 1
    }

    try {
        $PatientEntityId = (Get-Content -Path $SecondFilePath -Raw | ConvertFrom-Json).classId
    } catch {
        Write-Error "Failed to extract classId from ${SecondFilePath}: $($_.Exception.Message)"
        exit 1
    }
    
    try {
        $response = Invoke-RestMethod -Uri $Url -Method Post -Headers @{
            "Authorization" = "Bearer $($JwtAccessToken)"
            "Content-Type" = "application/json"
        } -Body (Get-Content -Path $SecondFilePath) -ErrorAction Stop -StatusCodeVariable StatusCode
    } catch {
        Write-Error "Failed to upload ${SecondFilePath}: $($_.Exception.Message)"
        exit 1
    }
    
    $date = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    Write-Host "$date | HTTP $StatusCode | $Url | $SecondFilePath"
    
    # Step 4: Poll until unlocked
    Poll-UntilUnlocked "$($Url)/$PatientEntityId"
    
    # Step 5: Upload all other files (excluding the first two)
    Write-Host "Uploading remaining files..."
    
    Get-ChildItem -Path $Folder -Filter *.json | ForEach-Object {
        $file = $_.FullName
        $fileName = Split-Path -Path $file -Leaf
        
        Write-Host "Processing file: $fileName"
        # Skip the first and second files
        if ($fileName -ne $FirstFile -and $fileName -ne $SecondFile) {
            if (Test-Path -Path $file -PathType Leaf) {
                try {
                    $response = Invoke-RestMethod -Uri $Url -Method Post -Headers @{
                        "Authorization" = "Bearer $($JwtAccessToken)"
                        "Content-Type" = "application/json"
                    } -Body (Get-Content -Path $file) -ErrorAction Stop -StatusCodeVariable StatusCode

                    $httpStatus = $response.StatusCode #Might need to adjust based on actual response
                } catch {
                    Write-Error "Failed to upload ${file}: $($_.Exception.Message)"
                }
                
                $date = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
                Write-Host "$date | HTTP $StatusCode | $Url | $file"
            }
        }
    }
} else {
    # Original logic for update and delete actions
    $jsonFiles = Get-ChildItem -Path $Folder -Filter *.json
    
    if ($jsonFiles.Count -eq 0) {
        Write-Error "No JSON files found in the folder."
        exit 1
    }
    
    foreach ($file in $jsonFiles) {
        $Content = Get-Content -Path $File.FullName | ConvertFrom-Json
        # Extract ID from JSON file if action is update or delete
        if ($Action -eq "update" -or $Action -eq "delete") {
            $ID = $Content.classId

            if (-not $ID) {
                Write-Host "No valid ID found in $($File.Name). Skipping..."
                continue
            }
            $Url = "${ApiUrl}data-model/$ID"
        } else {
            $Url = "${ApiUrl}data-model"
        }
        
        # Perform the API call using Invoke-RestMethod
        try {
            if ($Action -eq 'update') {
                $response = Invoke-RestMethod -Uri $Url -Method Put -Headers @{
                    "Authorization" = "Bearer $($JwtAccessToken)"
                    "Content-Type" = "application/json"
                } -Body (Get-Content -Path $file.FullName) -ErrorAction Stop -StatusCodeVariable StatusCode
            } elseif ($Action -eq 'delete') {
                $response = Invoke-RestMethod -Uri $Url -Method Delete -Headers @{
                    "Authorization" = "Bearer $($JwtAccessToken)"
                } -ErrorAction Stop -StatusCodeVariable StatusCode
            }
        }
        catch [Microsoft.PowerShell.Commands.HttpResponseException] {
            $StatusCode = $_.Exception.Response.StatusCode.value__
            if ($StatusCode -eq 404) {
                Write-Warning "Resource not found for $($file.Name) (Status: 404). Skipping..."
                continue
            }
            else {
                Write-Error "HTTP Error $StatusCode processing $($file.Name): $($_.Exception.Message)"
                continue
            }
        }
        catch {
            Write-Error "Error processing $($file.Name): $($_.Exception.Message)"
            continue
        }
        
        # Log the response
        $date = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
        Write-Host "$date | HTTP $StatusCode | $Url | $($file.FullName)"
    }
}