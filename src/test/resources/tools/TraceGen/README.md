# Traceability and Metrics Generator Tool

## How to run ##

`mvn compile exec:java`

or from base directory:

`mvn -f .\tools\TraceGen\ compile exec:java`

or from base directory with parameters:

`mvn -f .\tools\TraceGen\ compile exec:java -DinitialTraceabilityFilePath="tools/TraceGen/OncoSRS_rev4_with_initial_traceability.csv" -DfeatureFilesBaseDirPath="src/test/resources/stories"`

## Features ##

After execution, the following files are generated in `target/traceabilityAndMetrics` directory:
- `generatedTraceability.csv`
- `testCases.csv`
- `metrics.json`: used in the E2E Test Jenkins pipeline to provide data to Digital Dashboard

Error returns when
- an obsolete requirement is available in the feature files (obsolete: not available in the initial traceability file)
- a requirement ID was found more times in the initial traceability (one SRS ID should only be present once)
- safety flag mismatch in the initial traceability and the related feature file
- a requirement ID was found in more feature files (all feature files should contain a unique SRS ID)
- a feature file was found without any scenarios
- the requirement has linked test cases in the initial traceability and the same requirement has a feature file too (*future improvement to allow both*)
- the provided test ID in the initial traceability is not available in the observed feature files (probably, it is a removed scenario)
- if the feature file syntax is not as expected and the requirement ID is not found in the feature title between `[` and `]` characters
