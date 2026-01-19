# configurations

The repository contains the configurations that are used within the CareIntellect Oncology application.

## Table of Contents
1. [Structure](#structure)
2. [Load configuration](#load-configuration)
   - [Shell](#shell)
     - [Bootstrap](#bootstrap)
     - [Fulfillment](#fulfillment)
       - [Valuesets](#valuesets)
       - [Models](#models)
     - [Eligibility](#eligibility)
     - [Adherence](#adherence)
     - [Patient Summary](#patient-summary)
     - [Representation](#representation)
       - [App preset](#app-preset)
       - [Fulfillment Query Set](#fulfillment-query-set)
       - [Representation](#representation-1)
       - [Labels](#labels)
   - [PowerShell](#powershell)
     - [Bootstrap](#bootstrap-1)
     - [Fulfillment](#fulfillment-1)
       - [Valuesets](#valuesets-1)
       - [Models](#models-1)
     - [Eligibility](#eligibility-1)
     - [Adherence](#adherence-1)
     - [Patient Summary](#patient-summary-1)
     - [Representation](#representation-2)
       - [App preset](#app-preset-1)
       - [Fulfillment Query Set](#fulfillment-query-set-1)
       - [Representation](#representation-3)
       - [Labels](#labels-1)

## Structure

The repository has the folder `default`, that contains the default configurations for the application.
```
default/
├── fulfillment/
│   ├── models/
│   └── valuesets/
├── patient-summary/
├── representation/
│   ├── breast/
│   ├── general/
│   │   ├── representation/
│   │   ├── fulfillment-query-set/
│   │   ├── app-preset/
│   │   └── labels/
│   └── prostate/
├── treatment-adherence/
│   └── prostate/
└── treatment-eligibility/
    ├── treatments/
    └── trials/
        ├── breast/
        └── prostate/
```

## Load configuration

### Shell

> **Requirements:**
> The scripts are using `jq` and `curl`. Make sure these tools are installed before executing the scripts.

The `bootstrap/shell` folder contains Shell scripts to `create`, `update` or `delete` configs from the services.

> **Note:**
> Not all services support the update or delete operation

#### Bootstrap

The bootstrap script can be used to upload all configurations to a fresh environment in the correct order.

The script collects the service URLs from the CloudFormation Stack's Output. It also requests a token from IDAM.

Usage:
```sh
./bootstrap/shell/bootstrap.sh <CFT_STACK_NAME> <IDAM_URL> <IDAM_CLIENT_ID> <IDAM_CLIENT_SECRET> <CONFIG_DIR>
```

Example:
```sh
./bootstrap/shell/bootstrap.sh dev-config-pi https://idam.gehealthcloud.io client_id client_secret ./default
```

#### Fulfillment

Fulfillment configs can be uploaded and removed using the scripts:
- `bootstrap/shell/fulfillment-valuesets.sh`
- `bootstrap/shell/fulfillment-models.sh`

##### Valuesets

The script can be used to `create` and `delete` valuesets. `update` currently is not working.

Usage:
```sh
./bootstrap/shell/fulfillment-valuesets.sh <ACTION> <API_URL> <JWT_ACCESS_TOKEN> <FOLDER>
```

Example:
```sh
./bootstrap/shell/fulfillment-valueset.sh create https://abcd.execute-api.us-east-1.amazonaws.com/v1/ <TOKEN> ./default/fulfillment/valuesets
```

##### Models

The script can be used to `create`, `update` and `delete` models.
During create the script first tries to upload the `uncategorized` entity, then it waits until the model gets unlocked. After that it tries to upload the `patient` entity and also waits until the model gets unlocked. Then uploads all the remaining entity model configs.

Usage:
```sh
./bootstrap/shell/fulfillment-models.sh <ACTION> <API_URL> <JWT_ACCESS_TOKEN> <FOLDER>
```

Example:
```sh
./bootstrap/shell/fulfillment-models.sh create https://abcd.execute-api.us-east-1.amazonaws.com/v1/ <TOKEN> ./default/fulfillment/models
```

#### Eligibility

The script can be used to `create`, `update` and `delete` configs. The script gets the `type` (`trial` or `treatment`) from the configurations.

Usage:
```sh
./bootstrap/shell/eligibility.sh <ACTION> <API_URL> <JWT_ACCESS_TOKEN> <FOLDER>
```

Example:
```sh
./bootstrap/shell/eligibility.sh create https://abcd.execute-api.us-east-1.amazonaws.com/v1/ <TOKEN> ./default/treatment-eligibility/treatments
./bootstrap/shell/eligibility.sh create https://abcd.execute-api.us-east-1.amazonaws.com/v1/ <TOKEN> ./default/treatment-eligibility/trials/breast
```

#### Adherence

The script can be used to `create` and `delete` adherence protocols. `update` currently is not working.

Usage:
```sh
./bootstrap/shell/adherence.sh <ACTION> <API_URL> <JWT_ACCESS_TOKEN> <FOLDER>
```

Example:
```sh
./bootstrap/shell/adherence.sh create https://abcd.execute-api.us-east-1.amazonaws.com/v1/ <TOKEN> ./default/treatment-adherence/prostate
```

#### Patient Summary

The script can be used to `create`, `update` and `delete` config.

Usage:
```sh
./bootstrap/shell/summary.sh <ACTION> <API_URL> <JWT_ACCESS_TOKEN> <FOLDER>
```

Example:
```sh
./bootstrap/shell/summary.sh create https://abcd.execute-api.us-east-1.amazonaws.com/v1/ <TOKEN> ./default/patient-summary
```

#### Representation

The `representation.sh` script calls other representation config scripts (`representation-app-preset.sh`, `representation-fulfillment-query-set.sh`, `representation-representation.sh`, `representation-labels.sh`) on a directory.
It currently only supports `create` and `update`. `delete` is not supported.

Usage:
```sh
./bootstrap/shell/representation.sh <ACTION> <API_URL> <JWT_ACCESS_TOKEN> <FOLDER>
```

Example:
```sh
./bootstrap/shell/representation.sh create https://abcd.execute-api.us-east-1.amazonaws.com/v1/ <TOKEN> ./default/representation/general
```

##### App preset

The script can be used to `create`, `update` and `delete` configs.

Usage:
```sh
./bootstrap/shell/representation-app-preset.sh <ACTION> <API_URL> <JWT_ACCESS_TOKEN> <FOLDER>
```

Example:
```sh
./bootstrap/shell/representation-app-preset.sh create https://abcd.execute-api.us-east-1.amazonaws.com/v1/ <TOKEN> ./default/representation/general/app-preset
```

##### Fulfillment Query Set

The script can be used to `create`, `update` and `delete` configs.

Usage:
```sh
./bootstrap/shell/representation-fulfillment-query-set.sh <ACTION> <API_URL> <JWT_ACCESS_TOKEN> <FOLDER>
```

Example:
```sh
./bootstrap/shell/representation-fulfillment-query-set.sh create https://abcd.execute-api.us-east-1.amazonaws.com/v1/ <TOKEN> ./default/representation/general/fulfillment-query-set
```

##### Representation

The script can be used to `create`, `update` and `delete` configs.

Usage:
```sh
./bootstrap/shell/representation-representation.sh <ACTION> <API_URL> <JWT_ACCESS_TOKEN> <FOLDER>
```

Example:
```sh
./bootstrap/shell/representation-representation.sh create https://abcd.execute-api.us-east-1.amazonaws.com/v1/ <TOKEN> ./default/representation/general/representation
```

##### Labels

The script can be used to `create` labels.

Usage:
```sh
./bootstrap/shell/representation-labels.sh <ACTION> <API_URL> <JWT_ACCESS_TOKEN> <FOLDER>
```

Example:
```sh
./bootstrap/shell/representation-labels.sh create https://abcd.execute-api.us-east-1.amazonaws.com/v1/ <TOKEN> ./default/representation/general/labels
```

### PowerShell

> **Requirements:**
> The scripts are using PowerShell v7 features, make sure you are using v7.

The `bootstrap/powershell` folder contains Shell scripts to `create`, `update` or `delete` configs from the services.

> **Note:**
> Not all services support the update or delete operation

#### Bootstrap

The bootstrap script can be used to upload all configurations to a fresh environment in the correct order.

The script collects the service URLs from the CloudFormation Stack's Output. It also requests a token from IDAM.

Usage:
```sh
./bootstrap/powershell/bootstrap.ps1 <CFT_STACK_NAME> <IDAM_URL> <IDAM_CLIENT_ID> <IDAM_CLIENT_SECRET> <CONFIG_DIR>
```

Example:
```sh
./bootstrap/powershell/bootstrap.ps1 dev-config-pi https://idam.gehealthcloud.io client_id client_secret ./default
```

#### Fulfillment

Fulfillment configs can be uploaded and removed using the scripts:
- `bootstrap/powershell/fulfillment-valuesets.ps1`
- `bootstrap/powershell/fulfillment-models.ps1`

##### Valuesets

The script can be used to `create` and `delete` valuesets. `update` currently is not working.

Usage:
```sh
./bootstrap/powershell/fulfillment-valuesets.ps1 <ACTION> <API_URL> <JWT_ACCESS_TOKEN> <FOLDER>
```

Example:
```sh
./bootstrap/powershell/fulfillment-valueset.ps1 create https://abcd.execute-api.us-east-1.amazonaws.com/v1/ <TOKEN> ./default/fulfillment/valuesets
```

##### Models

The script can be used to `create`, `update` and `delete` models.
During create the script first tries to upload the `uncategorized` entity, then it waits until the model gets unlocked. After that it tries to upload the `patient` entity and also waits until the model gets unlocked. Then uploads all the remaining entity model configs.

Usage:
```sh
./bootstrap/powershell/fulfillment-models.ps1 <ACTION> <API_URL> <JWT_ACCESS_TOKEN> <FOLDER>
```

Example:
```sh
./bootstrap/powershell/fulfillment-models.ps1 create https://abcd.execute-api.us-east-1.amazonaws.com/v1/ <TOKEN> ./default/fulfillment/models
```

#### Eligibility

The script can be used to `create`, `update` and `delete` configs. The script gets the `type` (`trial` or `treatment`) from the configurations.

Usage:
```sh
./bootstrap/powershell/eligibility.ps1 <ACTION> <API_URL> <JWT_ACCESS_TOKEN> <FOLDER>
```

Example:
```sh
./bootstrap/powershell/eligibility.ps1 create https://abcd.execute-api.us-east-1.amazonaws.com/v1/ <TOKEN> ./default/treatment-eligibility/treatments
./bootstrap/powershell/eligibility.ps1 create https://abcd.execute-api.us-east-1.amazonaws.com/v1/ <TOKEN> ./default/treatment-eligibility/trials/breast
```

#### Adherence

The script can be used to `create` and `delete` adherence protocols. `update` currently is not working.

Usage:
```sh
./bootstrap/powershell/adherence.ps1 <ACTION> <API_URL> <JWT_ACCESS_TOKEN> <FOLDER>
```

Example:
```sh
./bootstrap/powershell/adherence.ps1 create https://abcd.execute-api.us-east-1.amazonaws.com/v1/ <TOKEN> ./default/treatment-adherence/prostate
```

#### Patient Summary

The script can be used to `create`, `update` and `delete` config.

Usage:
```sh
./bootstrap/powershell/summary.ps1 <ACTION> <API_URL> <JWT_ACCESS_TOKEN> <FOLDER>
```

Example:
```sh
./bootstrap/powershell/summary.ps1 create https://abcd.execute-api.us-east-1.amazonaws.com/v1/ <TOKEN> ./default/patient-summary
```

#### Representation

The `representation.sh` script calls other representation config scripts (`representation-app-preset.sh`, `representation-fulfillment-query-set.sh`, `representation-representation.sh`, `representation-labels.sh`) on a directory.
It currently only supports `create` and `update`. `delete` is not supported.

Usage:
```sh
./bootstrap/powershell/representation.ps1 <ACTION> <API_URL> <JWT_ACCESS_TOKEN> <FOLDER>
```

Example:
```sh
./bootstrap/powershell/representation.ps1 create https://abcd.execute-api.us-east-1.amazonaws.com/v1/ <TOKEN> ./default/representation/general
```

##### App preset

The script can be used to `create`, `update` and `delete` configs.

Usage:
```sh
./bootstrap/powershell/representation-app-preset.ps1 <ACTION> <API_URL> <JWT_ACCESS_TOKEN> <FOLDER>
```

Example:
```sh
./bootstrap/powershell/representation-app-preset.ps1 create https://abcd.execute-api.us-east-1.amazonaws.com/v1/ <TOKEN> ./default/representation/general/app-preset
```

##### Fulfillment Query Set

The script can be used to `create`, `update` and `delete` configs.

Usage:
```sh
./bootstrap/powershell/representation-fulfillment-query-set.ps1 <ACTION> <API_URL> <JWT_ACCESS_TOKEN> <FOLDER>
```

Example:
```sh
./bootstrap/powershell/representation-fulfillment-query-set.ps1 create https://abcd.execute-api.us-east-1.amazonaws.com/v1/ <TOKEN> ./default/representation/general/fulfillment-query-set
```

##### Representation

The script can be used to `create`, `update` and `delete` configs.

Usage:
```sh
./bootstrap/powershell/representation-representation.ps1 <ACTION> <API_URL> <JWT_ACCESS_TOKEN> <FOLDER>
```

Example:
```sh
./bootstrap/powershell/representation-representation.ps1 create https://abcd.execute-api.us-east-1.amazonaws.com/v1/ <TOKEN> ./default/representation/general/representation
```

##### Labels

The script can be used to `create` labels.

Usage:
```sh
./bootstrap/powershell/representation-labels.ps1 <ACTION> <API_URL> <JWT_ACCESS_TOKEN> <FOLDER>
```

Example:
```sh
./bootstrap/powershell/representation-labels.ps1 create https://abcd.execute-api.us-east-1.amazonaws.com/v1/ <TOKEN> ./default/representation/general/labels
```