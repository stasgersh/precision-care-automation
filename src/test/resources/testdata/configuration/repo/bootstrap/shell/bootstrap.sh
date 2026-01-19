#!/bin/bash

# Bootstrapping an environment by calling the scripts in the directory
# Currently only empty environment bootstrapping is supported
#
# Parameters:
# 1. Deployment AWS Cloudformation stack name
# 2. IDAM URL
# 3. IDAM Client ID
# 4. IDAM Client Secret
# 5. Folder
#
# Requires:
# 1. AWS CLI
# 2. jq
# 3. curl

STACK_NAME=$1
IDAM_URL=$2
IDAM_CLIENT_ID=$3
IDAM_CLIENT_SECRET=$4
FOLDER=$5

if [ -z "$STACK_NAME" ] || [ -z "$IDAM_URL" ] || [ -z "$IDAM_CLIENT_ID" ] || [ -z "$IDAM_CLIENT_SECRET" ] || [ -z "$FOLDER" ]; then
    echo "Error: Missing required parameters"
    echo "Usage: $0 <stack_name> <idam_url> <client_id> <client_secret> <folder>"
    echo "Parameters:"
    echo "  stack_name        - AWS Cloudformation stack name"
    echo "  idam_url          - IDAM URL"
    echo "  client_id         - IDAM Client ID"
    echo "  client_secret     - IDAM Client Secret"
    echo "  folder            - Folder path"
    exit 1
fi

echo "Bootstrapping environment with stack name: $STACK_NAME"
echo "Getting stack outputs from AWS CloudFormation..."
STACK_OUTPUTS=$(aws cloudformation describe-stacks --stack-name $STACK_NAME --query "Stacks[0].Outputs" --output json | jq -r '.[] | {OutputKey, OutputValue}')

echo "Requesting token from IDAM..."
TOKEN=$(curl -s --request POST --url $IDAM_URL/oauth2/token --header 'Content-Type: application/x-www-form-urlencoded' --data grant_type=client_credentials --data client_id=$IDAM_CLIENT_ID --data client_secret=$IDAM_CLIENT_SECRET --data 'scope=pi.adherence.configuration.full pi.adherence.full pi.eligibility.configuration.full pi.fulfillment.configuration.full pi.summary.configuration.full pi.representation.configuration.full TODO_RANDOM' | jq -r '.access_token')

echo "Getting FULFILLMENT_URL from stack outputs..."
FULFILLMENT_URL=$(echo $STACK_OUTPUTS | jq -r '. | select(.OutputKey=="fulfillmentServiceUrl") | .OutputValue')
if [[ -n "$FULFILLMENT_URL" ]]; then
    echo "FULFILLMENT_URL is set to: $FULFILLMENT_URL"
    sh ./bootstrap/shell/fulfillment-valueset.sh create $FULFILLMENT_URL $TOKEN $FOLDER/fulfillment/valuesets
    sh ./bootstrap/shell/fulfillment-model.sh create $FULFILLMENT_URL $TOKEN $FOLDER/fulfillment/models
else
    echo "FULFILLMENT_URL is not set. Skipping ValueSet and Model upload."
fi

echo "Getting ELIGIBILITY_URL from stack outputs..."
ELIGIBILITY_URL=$(echo $STACK_OUTPUTS | jq -r '. | select(.OutputKey=="treatmentEligibilityServiceUrl") | .OutputValue')
if [[ -n "$ELIGIBILITY_URL" ]]; then
    echo "ELIGIBILITY_URL is set to: $ELIGIBILITY_URL"
    # First find all top-level directories in treatment-eligibility
    for TOP_DIR in "$FOLDER"/treatment-eligibility/*/; do
        if [ -d "$TOP_DIR" ]; then
            echo "Processing top-level directory: $TOP_DIR"
            sh ./bootstrap/shell/eligibility.sh create $ELIGIBILITY_URL $TOKEN "$TOP_DIR"
            # Now find and process all subdirectories
            for SUB_DIR in "$TOP_DIR"*/; do
                if [ -d "$SUB_DIR" ]; then
                    echo "Processing subdirectory: $SUB_DIR"
                    sh ./bootstrap/shell/eligibility.sh create $ELIGIBILITY_URL $TOKEN "$SUB_DIR"
                fi
            done
        fi
    done
else
    echo "ELIGIBILITY_URL is not set. Skipping Trials upload."
fi

echo "Getting AI_CRITERIA_MATCHING_URL from stack outputs..."
AI_CRITERIA_MATCHING_URL=$(echo $STACK_OUTPUTS | jq -r '. | select(.OutputKey=="aiCriteriaMatchingServiceUrl") | .OutputValue')
if [[ -n "$AI_CRITERIA_MATCHING_URL" ]]; then
    echo "AI_CRITERIA_MATCHING_URL is set to: $AI_CRITERIA_MATCHING_URL"
    sh ./bootstrap/shell/ai-criteria-matching.sh $AI_CRITERIA_MATCHING_URL $TOKEN $FOLDER/ai-criteria-matching
else
    echo "AI_CRITERIA_MATCHING_URL is not set. Skipping AI Criteria Matching configuration upload."
fi

echo "Getting SUMMARY_URL from stack outputs..."
SUMMARY_URL=$(echo $STACK_OUTPUTS | jq -r '. | select(.OutputKey=="patientSummaryServiceUrl") | .OutputValue')
if [[ -n "$SUMMARY_URL" ]]; then
    echo "SUMMARY_URL is set to: $SUMMARY_URL"
    sh ./bootstrap/shell/summary.sh create $SUMMARY_URL $TOKEN $FOLDER/patient-summary
else
    echo "SUMMARY_URL is not set. Skipping Summary upload."
fi

echo "Getting ADHERENCE_URL from stack outputs..."
ADHERENCE_URL=$(echo $STACK_OUTPUTS | jq -r '. | select(.OutputKey=="treatmentAdherenceServiceUrl") | .OutputValue')
if [[ -n "$ADHERENCE_URL" ]]; then
    echo "ADHERENCE_URL is set to: $ADHERENCE_URL"
    # Iterate through all subdirectories in the treatment-adherence folder
    for DIR in "$FOLDER"/treatment-adherence/*/; do
        if [ -d "$DIR" ]; then
            echo "Processing adherence configuration in directory: $DIR"
            sh ./bootstrap/shell/adherence.sh create $ADHERENCE_URL $TOKEN "$DIR"
        fi
    done
else
    echo "ADHERENCE_URL is not set. Skipping Adherence upload."
fi

echo "Getting REPRESENTATION_URL from stack outputs..."
REPRESENTATION_URL=$(echo $STACK_OUTPUTS | jq -r '. | select(.OutputKey=="representationServiceUrl") | .OutputValue')
if [[ -n "$REPRESENTATION_URL" ]]; then
    echo "REPRESENTATION_URL is set to: $REPRESENTATION_URL"
    # Iterate through all subdirectories in the representation folder
    for DIR in "$FOLDER"/representation/*/; do
        if [ -d "$DIR" ]; then
            echo "Processing representation configuration in directory: $DIR"
            # Check and process labels folder
            if [ -d "${DIR}labels" ]; then
                sh ./bootstrap/shell/representation-labels.sh create ${REPRESENTATION_URL} $TOKEN "${DIR}labels"
            fi
            # Check and process representation folder
            if [ -d "${DIR}representation" ]; then
                sh ./bootstrap/shell/representation-representation.sh create ${REPRESENTATION_URL} $TOKEN "${DIR}representation"
            fi
            # Check and process fulfillment-query-set folder
            if [ -d "${DIR}fulfillment-query-set" ]; then
                sh ./bootstrap/shell/representation-fulfillment-query-set.sh create ${REPRESENTATION_URL} $TOKEN "${DIR}fulfillment-query-set"
            fi
            # Check and process app-preset folder
            if [ -d "${DIR}app-preset" ]; then
                sh ./bootstrap/shell/representation-app-preset.sh create ${REPRESENTATION_URL} $TOKEN "${DIR}app-preset"
            fi
        fi
    done
else
    echo "REPRESENTATION_URL is not set. Skipping Representation upload."
fi