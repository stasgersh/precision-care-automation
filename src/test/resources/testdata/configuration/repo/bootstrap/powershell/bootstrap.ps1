<#
.SYNOPSIS
Bootstraps an environment by calling scripts in the directory.
Currently only empty environment bootstrapping is supported.

.DESCRIPTION
This script automates the process of setting up an environment by retrieving
stack outputs from AWS CloudFormation, requesting a token from IDAM, and
then calling other PowerShell scripts to configure various services.

.PARAMETER StackName
The AWS CloudFormation stack name.

.PARAMETER IdamUrl
The IDAM URL.

.PARAMETER IdamClientId
The IDAM Client ID.

.PARAMETER IdamClientSecret
The IDAM Client Secret.

.PARAMETER Folder
The folder path containing configuration files.

.EXAMPLE
.\bootstrap.ps1 -StackName "MyStack" -IdamUrl "https://idam.example.com" -IdamClientId "client123" -IdamClientSecret "secret456" -Folder ".\config"
#>
[CmdletBinding()]
Param (
    [Parameter(Mandatory = $true, HelpMessage = "AWS Cloudformation stack name")]
    [string]$StackName,

    [Parameter(Mandatory = $true, HelpMessage = "IDAM URL")]
    [string]$IdamUrl,

    [Parameter(Mandatory = $true, HelpMessage = "IDAM Client ID")]
    [string]$IdamClientId,

    [Parameter(Mandatory = $true, HelpMessage = "IDAM Client Secret")]
    [string]$IdamClientSecret,

    [Parameter(Mandatory = $true, HelpMessage = "Folder path")]
    [string]$Folder
)

#region Helper functions
function Get-OutputValue {
    param (
        [array]$Outputs,
        [string]$Key
    )
    $Outputs | Where-Object { $_.OutputKey -eq $Key } | Select-Object -ExpandProperty OutputValue
}

function Get-IdamToken {
    param (
        [string]$IdamUrl,
        [string]$IdamClientId,
        [string]$IdamClientSecret
    )
    Write-Host "Requesting token from IDAM..."
    $body = @{
        grant_type    = "client_credentials"
        client_id     = $IdamClientId
        client_secret = $IdamClientSecret
        scope         = "pi.adherence.configuration.full pi.adherence.full pi.eligibility.configuration.full pi.fulfillment.configuration.full pi.summary.configuration.full pi.representation.configuration.full TODO_RANDOM"
    }

    try {
        $response = Invoke-RestMethod -Uri "$IdamUrl/oauth2/token" -Method Post -ContentType "application/x-www-form-urlencoded" -Body $body
        return $response.access_token
    }
    catch {
        Write-Error "Failed to retrieve token from IDAM: $($_.Exception.Message)"
        exit 1
    }
}
#endregion

Write-Host "Bootstrapping environment with stack name: $StackName"

# Get stack outputs
$StackOutputs = aws cloudformation describe-stacks --stack-name $StackName --query "Stacks[0].Outputs" --output json | ConvertFrom-Json

# Get IDAM token
$Token = Get-IdamToken -IdamUrl $IdamUrl -IdamClientId $IdamClientId -IdamClientSecret $IdamClientSecret

#region Fulfillment
# Get FULFILLMENT_URL from stack outputs
$FULFILLMENT_URL = ($StackOutputs | Where-Object { $_.OutputKey -eq "fulfillmentServiceUrl" }).OutputValue
if (-not [string]::IsNullOrEmpty($FULFILLMENT_URL)) {
    Write-Host "FULFILLMENT_URL is set to: $FULFILLMENT_URL"
    ./bootstrap/powershell/fulfillment-valueset.ps1  create $FULFILLMENT_URL $Token "$Folder/fulfillment/valuesets"
    ./bootstrap/powershell/fulfillment-model.ps1 create $FULFILLMENT_URL $Token "$Folder/fulfillment/models"
}
else {
    Write-Host "FULFILLMENT_URL is not set. Skipping ValueSet and Model upload."
}
#endregion

#region Eligibility
# Get ELIGIBILITY_URL from stack outputs
$ELIGIBILITY_URL = ($StackOutputs | Where-Object { $_.OutputKey -eq "treatmentEligibilityServiceUrl" }).OutputValue

if (-not [string]::IsNullOrEmpty($ELIGIBILITY_URL)) {
    Write-Host "ELIGIBILITY_URL is set to: $ELIGIBILITY_URL"
    # First find all top-level directories in treatment-eligibility
    Get-ChildItem -Path "$Folder/treatment-eligibility/*" -Directory | ForEach-Object {
        $TOP_DIR = $_.FullName
        Write-Host "Processing top-level directory: $TOP_DIR"
        ./bootstrap/powershell/eligibility.ps1 create $ELIGIBILITY_URL $Token $TOP_DIR
        # Now find and process all subdirectories
        Get-ChildItem -Path "$TOP_DIR/*" -Directory | ForEach-Object {
            $SUB_DIR = $_.FullName
            Write-Host "Processing subdirectory: $SUB_DIR"
            ./bootstrap/powershell/eligibility.ps1 create $ELIGIBILITY_URL $Token $SUB_DIR
        }
    }
}
else {
    Write-Host "ELIGIBILITY_URL is not set. Skipping Trials upload."
}
#endregion

#region AI Criteria Matching
# Get AI_CRITERIA_MATCHING_URL from stack outputs
$AI_CRITERIA_MATCHING_URL = ($StackOutputs | Where-Object { $_.OutputKey -eq "aiCriteriaMatchingServiceUrl" }).OutputValue

if (-not [string]::IsNullOrEmpty($AI_CRITERIA_MATCHING_URL)) {
    Write-Host "AI_CRITERIA_MATCHING_URL is set to: $AI_CRITERIA_MATCHING_URL"
    ./bootstrap/powershell/ai-criteria-matching.ps1 $AI_CRITERIA_MATCHING_URL $Token "$Folder/ai-criteria-matching"
}
else {
    Write-Host "AI_CRITERIA_MATCHING_URL is not set. Skipping AI Criteria Matching configuration upload."
}
#endregion

#region Summary
# Get SUMMARY_URL from stack outputs
$SUMMARY_URL = ($StackOutputs | Where-Object { $_.OutputKey -eq "patientSummaryServiceUrl" }).OutputValue

if (-not [string]::IsNullOrEmpty($SUMMARY_URL)) {
    Write-Host "SUMMARY_URL is set to: $SUMMARY_URL"
    ./bootstrap/powershell/summary.ps1 create $SUMMARY_URL $Token "$Folder/patient-summary"
}
else {
    Write-Host "SUMMARY_URL is not set. Skipping Summary upload."
}
#endregion

#region Adherence
# Get ADHERENCE_URL from stack outputs
$ADHERENCE_URL = ($StackOutputs | Where-Object { $_.OutputKey -eq "treatmentAdherenceServiceUrl" }).OutputValue

if (-not [string]::IsNullOrEmpty($ADHERENCE_URL)) {
    Write-Host "ADHERENCE_URL is set to: $ADHERENCE_URL"
    # Iterate through all subdirectories in the treatment-adherence folder
    Get-ChildItem -Path "$Folder/treatment-adherence/*" -Directory | ForEach-Object {
        $DIR = $_.FullName
        Write-Host "Processing adherence configuration in directory: $DIR"
        ./bootstrap/powershell/adherence.ps1 create $ADHERENCE_URL $Token $DIR
    }
}
else {
    Write-Host "ADHERENCE_URL is not set. Skipping Adherence upload."
}
#endregion

#region Representation
# Get REPRESENTATION_URL from stack outputs
$REPRESENTATION_URL = ($StackOutputs | Where-Object { $_.OutputKey -eq "representationServiceUrl" }).OutputValue

if (-not [string]::IsNullOrEmpty($REPRESENTATION_URL)) {
    Write-Host "REPRESENTATION_URL is set to: $REPRESENTATION_URL"
    # Iterate through all subdirectories in the representation folder
    Get-ChildItem -Path "$Folder/representation/*" -Directory | ForEach-Object {
        $DIR = $_.FullName
        Write-Host "Processing representation configuration in directory: $DIR"
        # Check and process labels folder
        if (Test-Path -Path "$($DIR)/labels" -PathType Container) {
            ./bootstrap/powershell/representation-labels.ps1 create $REPRESENTATION_URL $Token "$($DIR)/labels"
        }
        # Check and process representation folder
        if (Test-Path -Path "$($DIR)/representation" -PathType Container) {
            ./bootstrap/powershell/representation-representation.ps1 create $REPRESENTATION_URL $Token "$($DIR)/representation"
        }
        # Check and process fulfillment-query-set folder
        if (Test-Path -Path "$($DIR)/fulfillment-query-set" -PathType Container) {
            ./bootstrap/powershell/representation-fulfillment-query-set.ps1 create $REPRESENTATION_URL $Token "$($DIR)/fulfillment-query-set"
        }
        # Check and process app-preset folder
        if (Test-Path -Path "$($DIR)/app-preset" -PathType Container) {
            ./bootstrap/powershell/representation-app-preset.ps1 create $REPRESENTATION_URL $Token "$($DIR)/app-preset"
        }
    }
}
else {
    Write-Host "REPRESENTATION_URL is not set. Skipping Representation upload."
}
#endregion