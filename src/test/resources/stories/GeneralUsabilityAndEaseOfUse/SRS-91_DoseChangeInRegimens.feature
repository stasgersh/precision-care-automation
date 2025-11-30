@safety
Feature: [SysRS-68650] Dose Change In Regimens

Narrative: The system shall be able to display on the timeline the changes of medication doses within a chemo regimen.


Background:
Given the [DEFAULT-TEST-USER] user is logged in to OncoCare application
And the browser size is set to: width=1920, height=1080

@sanity
@edge
@SRS-91.01
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68650
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_Sanity-Exploratory/VPR_List_ME1_-_Automated/Functional_Service/General_Usability_And_Ease_Of_Use
Scenario: Changes of medication doses are displayed if same medications are part of the same regimens and the dose quantity is changed
Given [API] the following events were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                             | # comment to this test step                                                                 |
| cd472bba-8069-4b24-aa8e-89b43c3ee154 | MedicationRequest/34847d54-3b8f-4eab-818a-d57ea91fd913 | # event: bortezomib (VELCADE) chemo injection - Feb 11, 2017; part of 'First test regimen'  |
| cd472bba-8069-4b24-aa8e-89b43c3ee154 | MedicationRequest/e3557258-1984-4625-a90e-5768b99bfe60 | # event: bortezomib (VELCADE) chemo injection - Jan 09, 2018; part of 'First test regimen'  |
| cd472bba-8069-4b24-aa8e-89b43c3ee154 | MedicationRequest/0db78ac1-45ed-4ab8-85f7-f9c719c8f551 | # event: bortezomib (VELCADE) chemo injection - Jan 09, 2020; part of 'First test regimen'  |
| cd472bba-8069-4b24-aa8e-89b43c3ee154 | MedicationRequest/c3893075-e4e5-429e-be0f-38b1f2c6e709 | # event: bortezomib (VELCADE) chemo injection - Jun 09, 2020; part of 'Second test regimen' |
| cd472bba-8069-4b24-aa8e-89b43c3ee154 | MedicationRequest/1a392d62-d507-4874-8869-1ba09284af9c | # event: bortezomib (VELCADE) chemo injection - Jan 09, 2021; part of 'First test regimen'  |
| cd472bba-8069-4b24-aa8e-89b43c3ee154 | MedicationRequest/64e31375-c69f-421f-86fe-201dc3e93f56 | # event: bortezomib (VELCADE) chemo injection - Mar 11, 2022; part of 'Second test regimen' |
| cd472bba-8069-4b24-aa8e-89b43c3ee154 | MedicationRequest/d248af31-775f-4739-9643-d1aaf7793ca5 | # event: bortezomib (VELCADE) chemo injection - Jan 09, 2023; part of 'Second test regimen' |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                             | # comment to this test step                                  |
| cd472bba-8069-4b24-aa8e-89b43c3ee154 | MedicationRequest/34847d54-3b8f-4eab-818a-d57ea91fd913 | # event: bortezomib (VELCADE) chemo injection - Feb 11, 2017 |
| cd472bba-8069-4b24-aa8e-89b43c3ee154 | MedicationRequest/e3557258-1984-4625-a90e-5768b99bfe60 | # event: bortezomib (VELCADE) chemo injection - Jan 09, 2018 |
| cd472bba-8069-4b24-aa8e-89b43c3ee154 | MedicationRequest/0db78ac1-45ed-4ab8-85f7-f9c719c8f551 | # event: bortezomib (VELCADE) chemo injection - Jan 09, 2020 |
| cd472bba-8069-4b24-aa8e-89b43c3ee154 | MedicationRequest/c3893075-e4e5-429e-be0f-38b1f2c6e709 | # event: bortezomib (VELCADE) chemo injection - Jun 09, 2020 |
| cd472bba-8069-4b24-aa8e-89b43c3ee154 | MedicationRequest/1a392d62-d507-4874-8869-1ba09284af9c | # event: bortezomib (VELCADE) chemo injection - Jan 09, 2021 |
| cd472bba-8069-4b24-aa8e-89b43c3ee154 | MedicationRequest/64e31375-c69f-421f-86fe-201dc3e93f56 | # event: bortezomib (VELCADE) chemo injection - Mar 11, 2022 |
| cd472bba-8069-4b24-aa8e-89b43c3ee154 | MedicationRequest/d248af31-775f-4739-9643-d1aaf7793ca5 | # event: bortezomib (VELCADE) chemo injection - Jan 09, 2023 |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                             | # comment to this test step                                  |
| cd472bba-8069-4b24-aa8e-89b43c3ee154 | MedicationRequest/34847d54-3b8f-4eab-818a-d57ea91fd913 | # event: bortezomib (VELCADE) chemo injection - Feb 11, 2017 |
| cd472bba-8069-4b24-aa8e-89b43c3ee154 | MedicationRequest/e3557258-1984-4625-a90e-5768b99bfe60 | # event: bortezomib (VELCADE) chemo injection - Jan 09, 2018 |
| cd472bba-8069-4b24-aa8e-89b43c3ee154 | MedicationRequest/0db78ac1-45ed-4ab8-85f7-f9c719c8f551 | # event: bortezomib (VELCADE) chemo injection - Jan 09, 2020 |
| cd472bba-8069-4b24-aa8e-89b43c3ee154 | MedicationRequest/c3893075-e4e5-429e-be0f-38b1f2c6e709 | # event: bortezomib (VELCADE) chemo injection - Jun 09, 2020 |
| cd472bba-8069-4b24-aa8e-89b43c3ee154 | MedicationRequest/1a392d62-d507-4874-8869-1ba09284af9c | # event: bortezomib (VELCADE) chemo injection - Jan 09, 2021 |
| cd472bba-8069-4b24-aa8e-89b43c3ee154 | MedicationRequest/64e31375-c69f-421f-86fe-201dc3e93f56 | # event: bortezomib (VELCADE) chemo injection - Mar 11, 2022 |
| cd472bba-8069-4b24-aa8e-89b43c3ee154 | MedicationRequest/d248af31-775f-4739-9643-d1aaf7793ca5 | # event: bortezomib (VELCADE) chemo injection - Jan 09, 2023 |
And the "OncoTestPatient, Dorrin" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the "bortezomib (VELCADE) chemo injection" event MARKER is available on the "Treatment and Plan" swimlane at "Feb 11, 2017" with the below badges:
| badge_type     | badge_text  |
| CLASSIFICATION | Systemic Tx |
And the "bortezomib (VELCADE) chemo injection" event MARKER is available on the "Treatment and Plan" swimlane at "Jan 09, 2018" with the below badges:
| badge_type     | badge_text  |
| CLASSIFICATION | Systemic Tx |
And the "bortezomib (VELCADE) chemo injection" event MARKER is available on the "Treatment and Plan" swimlane at "Jan 09, 2020" with 2 miniaturized badges:
| badge_types_in_order |
| CLASSIFICATION       |
| DOSE_CHANGED         |
When the user hovers the following event label on the "Treatment and Plan" swimlane:
| event_type | name_on_label                        | date_on_timeline_axis |
| MARKER     | bortezomib (VELCADE) chemo injection | Jan 09, 2020          |
Then the "bortezomib (VELCADE) chemo injection" event MARKER is available on the "Treatment and Plan" swimlane at "Jan 09, 2020" with the below badges:
| badge_type     | badge_text        |
| CLASSIFICATION | Systemic Tx       |
| DOSE_CHANGED   | -34.8% -0.8 mg/m2 |
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label                        | date_on_timeline_axis |
| MARKER     | bortezomib (VELCADE) chemo injection | Feb 11, 2017          |
Then the sidebar header contains the following badges:
| badge_type     | badge_text  |
| CLASSIFICATION | Systemic Tx |
And the sidebar content contains the following data:
| data_type | title  | value                                       |
| KEY-VALUE | Drug   | bortezomib (VELCADE) chemo injection        |
| KEY-VALUE | Dosage | 2.3 mg/m2                                   |
| KEY-VALUE | Route  | Subcutaneous (part of 'First test regimen') |
| KEY-VALUE | Status | completed                                   |
| KEY-VALUE | Intent | order                                       |
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label                        | date_on_timeline_axis |
| MARKER     | bortezomib (VELCADE) chemo injection | Jan 09, 2018          |
Then the sidebar header contains the following badges:
| badge_type     | badge_text  |
| CLASSIFICATION | Systemic Tx |
And the sidebar content contains the following data:
| data_type | title  | value                                       |
| KEY-VALUE | Drug   | bortezomib (VELCADE) chemo injection        |
| KEY-VALUE | Dosage | 2.3 mg/m2                                   |
| KEY-VALUE | Route  | Subcutaneous (part of 'First test regimen') |
| KEY-VALUE | Status | completed                                   |
| KEY-VALUE | Intent | order                                       |
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label                        | date_on_timeline_axis |
| MARKER     | bortezomib (VELCADE) chemo injection | Jan 09, 2020          |
Then the sidebar header contains the following badges:
| badge_type     | badge_text        |
| CLASSIFICATION | Systemic Tx       |
| DOSE_CHANGED   | -34.8% -0.8 mg/m2 |
And the sidebar content contains the following data:
| data_type | title  | value                                       |
| KEY-VALUE | Drug   | bortezomib (VELCADE) chemo injection        |
| KEY-VALUE | Dosage | 1.5 mg/m2                                   |
| BADGE     | <N/A>  | [DOSE_CHANGED]: -34.8% -0.8 mg/m2           |
| KEY-VALUE | Route  | Subcutaneous (part of 'First test regimen') |
| KEY-VALUE | Status | completed                                   |
| KEY-VALUE | Intent | order                                       |
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label                        | date_on_timeline_axis |
| MARKER     | bortezomib (VELCADE) chemo injection | Jan 09, 2021          |
Then the sidebar header contains the following badges:
| badge_type     | badge_text        |
| CLASSIFICATION | Systemic Tx       |
| DOSE_CHANGED   | +33.3% +0.5 mg/m2 |
And the sidebar content contains the following data:
| data_type | title  | value                                       |
| KEY-VALUE | Drug   | bortezomib (VELCADE) chemo injection        |
| KEY-VALUE | Dosage | 2 mg/m2                                     |
| BADGE     | <N/A>  | [DOSE_CHANGED]: +33.3% +0.5 mg/m2           |
| KEY-VALUE | Route  | Subcutaneous (part of 'First test regimen') |
| KEY-VALUE | Status | completed                                   |
| KEY-VALUE | Intent | order                                       |
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label                        | date_on_timeline_axis |
| MARKER     | bortezomib (VELCADE) chemo injection | Jun 09, 2020          |
Then the sidebar header contains the following badges:
| badge_type     | badge_text  |
| CLASSIFICATION | Systemic Tx |
And the sidebar content contains the following data:
| data_type | title  | value                                                         |
| KEY-VALUE | Drug   | bortezomib (VELCADE) chemo injection                          |
| KEY-VALUE | Dosage | 3 mg/m2                                                       |
| KEY-VALUE | Route  | Subcutaneous (part of 'Second test regimen'), epic.com system |
| KEY-VALUE | Status | completed                                                     |
| KEY-VALUE | Intent | order                                                         |
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label                        | date_on_timeline_axis |
| MARKER     | bortezomib (VELCADE) chemo injection | Mar 11, 2022          |
Then the sidebar header contains the following badges:
| badge_type     | badge_text       |
| CLASSIFICATION | Systemic Tx      |
| DOSE_CHANGED   | +100.0% +3 mg/m2 |
And the sidebar content contains the following data:
| data_type | title  | value                                                        |
| KEY-VALUE | Drug   | bortezomib (VELCADE) chemo injection                         |
| KEY-VALUE | Dosage | 6 mg/m2                                                      |
| BADGE     | <N/A>  | [DOSE_CHANGED]: +100.0% +3 mg/m2                             |
| KEY-VALUE | Route  | Subcutaneous (part of 'Second test regimen'), hl7.org system |
| KEY-VALUE | Status | completed                                                    |
| KEY-VALUE | Intent | order                                                        |
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label                        | date_on_timeline_axis |
| MARKER     | bortezomib (VELCADE) chemo injection | Jan 09, 2023          |
Then the sidebar content contains the following data:
| data_type | title  | value                                                         |
| KEY-VALUE | Drug   | bortezomib (VELCADE) chemo injection                          |
| KEY-VALUE | Dosage | 1 mg/m2                                                       |
| BADGE     | <N/A>  | [DOSE_CHANGED]: -83.3% -5 mg/m2                               |
| KEY-VALUE | Route  | Subcutaneous (part of 'Second test regimen'), epic.com system |
| KEY-VALUE | Status | completed                                                     |
| KEY-VALUE | Intent | order                                                         |

@SRS-91.02
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68650
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use
Scenario: Changes of medication doses are not calculated if medications are not part of any regimen
Given [API] the following events were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                             | # comment to this test step                                  |
| 23bc6174-78a1-42f8-bbff-e56a93324f9c | MedicationRequest/77129f62-0b69-4c1b-b0a1-dbdb6728ef0d | # event: bortezomib (VELCADE) chemo injection - Jan 09, 2020 |
| 23bc6174-78a1-42f8-bbff-e56a93324f9c | MedicationRequest/96c625f9-4704-4036-b22a-21f5b309670c | # event: bortezomib (VELCADE) chemo injection - Jan 09, 2021 |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                             | # comment to this test step                                  |
| 23bc6174-78a1-42f8-bbff-e56a93324f9c | MedicationRequest/77129f62-0b69-4c1b-b0a1-dbdb6728ef0d | # event: bortezomib (VELCADE) chemo injection - Jan 09, 2020 |
| 23bc6174-78a1-42f8-bbff-e56a93324f9c | MedicationRequest/96c625f9-4704-4036-b22a-21f5b309670c | # event: bortezomib (VELCADE) chemo injection - Jan 09, 2021 |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                             | # comment to this test step                                  |
| 23bc6174-78a1-42f8-bbff-e56a93324f9c | MedicationRequest/77129f62-0b69-4c1b-b0a1-dbdb6728ef0d | # event: bortezomib (VELCADE) chemo injection - Jan 09, 2020 |
| 23bc6174-78a1-42f8-bbff-e56a93324f9c | MedicationRequest/96c625f9-4704-4036-b22a-21f5b309670c | # event: bortezomib (VELCADE) chemo injection - Jan 09, 2021 |
And the "OncoTestPatient, Dylen" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the "bortezomib (VELCADE) chemo injection" event MARKER is available on the "Treatment and Plan" swimlane at "Jan 09, 2020" with the below badges:
| badge_type     | badge_text  |
| CLASSIFICATION | Systemic Tx |
And the "bortezomib (VELCADE) chemo injection" event MARKER is available on the "Treatment and Plan" swimlane at "Jan 09, 2021" with the below badges:
| badge_type     | badge_text  |
| CLASSIFICATION | Systemic Tx |
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label                        | date_on_timeline_axis |
| MARKER     | bortezomib (VELCADE) chemo injection | Jan 09, 2020          |
Then the sidebar content contains the following data:
| data_type | title  | value                                  |
| KEY-VALUE | Drug   | bortezomib (VELCADE) chemo injection   |
| KEY-VALUE | Dosage | 1.5 mg/m2                              |
| KEY-VALUE | Route  | Subcutaneous (not part of any regimen) |
| KEY-VALUE | Status | completed                              |
| KEY-VALUE | Intent | order                                  |
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label                        | date_on_timeline_axis |
| MARKER     | bortezomib (VELCADE) chemo injection | Jan 09, 2021          |
Then the sidebar content contains the following data:
| data_type | title  | value                                  |
| KEY-VALUE | Drug   | bortezomib (VELCADE) chemo injection   |
| KEY-VALUE | Dosage | 2 mg/m2                                |
| KEY-VALUE | Route  | Subcutaneous (not part of any regimen) |
| KEY-VALUE | Status | completed                              |
| KEY-VALUE | Intent | order                                  |

@SRS-91.03
@srs
@Platform:WEB
@Category:Positive
@Traceability:SysRS-68650
@Path:CareIntellect_for_Oncology_1.0.0_VPR_for_System_SRS/Automated_-_Functional_-_General_Usability_And_Ease_Of_Use/General_Usability_And_Ease_Of_Use
Scenario: Changes of medication doses are calculated if medications haven't got medication coding but have the same ingredients
Given [API] the following events were not marked as important for [DEFAULT-TEST-USER] user:
| patientID                            | root_resource_of_the_event                             | # comment to this test step                                                                                                            |
| 04d1c1e4-eb05-4d80-be01-f487d0599ac8 | MedicationRequest/71872fd1-2109-4a48-b160-a26df6ead9ce | # event: This is a Medication without codings - Jun 09, 2020                      ; no medication coding but has ingredients           |
| 04d1c1e4-eb05-4d80-be01-f487d0599ac8 | MedicationRequest/7bc6eb0a-53d0-4f03-b895-372979481a44 | # event: This is a Medication without codings - Mar 11, 2022                      ; no medication coding but has the same ingredients  |
| 04d1c1e4-eb05-4d80-be01-f487d0599ac8 | MedicationRequest/ad711337-c4d8-41f0-8a9f-7b77dedb5214 | # event: This is a Medication without codings but other ingredients - Jan 11, 2023; no medication coding but has different ingredients |
| 04d1c1e4-eb05-4d80-be01-f487d0599ac8 | MedicationRequest/4bb1fe67-aabf-4e6b-8b2f-f39deee905d5 | # event: bortezomib (VELCADE) chemo injection - Jul 09, 2023                      ; has medication coding and has the same ingredients |
And [API] the [DEFAULT-TEST-USER] user has no labels for the following event:
| patientID                            | root_resource_of_the_event                             | # comment to this test step                                                        |
| 04d1c1e4-eb05-4d80-be01-f487d0599ac8 | MedicationRequest/71872fd1-2109-4a48-b160-a26df6ead9ce | # event: This is a Medication without codings - Jun 09, 2020                       |
| 04d1c1e4-eb05-4d80-be01-f487d0599ac8 | MedicationRequest/7bc6eb0a-53d0-4f03-b895-372979481a44 | # event: This is a Medication without codings - Mar 11, 2022                       |
| 04d1c1e4-eb05-4d80-be01-f487d0599ac8 | MedicationRequest/ad711337-c4d8-41f0-8a9f-7b77dedb5214 | # event: This is a Medication without codings but other ingredients - Jan 11, 2023 |
| 04d1c1e4-eb05-4d80-be01-f487d0599ac8 | MedicationRequest/4bb1fe67-aabf-4e6b-8b2f-f39deee905d5 | # event: bortezomib (VELCADE) chemo injection - Jul 09, 2023                       |
And [API] the [DEFAULT-TEST-USER] user has no comments for the following events:
| patientID                            | root_resource_of_the_event                             | # comment to this test step                                                        |
| 04d1c1e4-eb05-4d80-be01-f487d0599ac8 | MedicationRequest/71872fd1-2109-4a48-b160-a26df6ead9ce | # event: This is a Medication without codings - Jun 09, 2020                       |
| 04d1c1e4-eb05-4d80-be01-f487d0599ac8 | MedicationRequest/7bc6eb0a-53d0-4f03-b895-372979481a44 | # event: This is a Medication without codings - Mar 11, 2022                       |
| 04d1c1e4-eb05-4d80-be01-f487d0599ac8 | MedicationRequest/ad711337-c4d8-41f0-8a9f-7b77dedb5214 | # event: This is a Medication without codings but other ingredients - Jan 11, 2023 |
| 04d1c1e4-eb05-4d80-be01-f487d0599ac8 | MedicationRequest/4bb1fe67-aabf-4e6b-8b2f-f39deee905d5 | # event: bortezomib (VELCADE) chemo injection - Jul 09, 2023                       |
And the "OncoTestPatient, Dyna" patient is selected
And the "Timeline" view is selected
And the patient timeline is loaded
And the "This is a Medication without codings" event MARKER is available on the "Treatment and Plan" swimlane at "Jun 09, 2020" with the below badges:
| badge_type     | badge_text  |
| CLASSIFICATION | Systemic Tx |
And the "This is a Medication without codings but other ingredients" event MARKER is available on the "Treatment and Plan" swimlane at "Jan 11, 2023" with the below badges:
| badge_type     | badge_text  |
| CLASSIFICATION | Systemic Tx |
And the "bortezomib (VELCADE) chemo injection" event MARKER is available on the "Treatment and Plan" swimlane at "Jul 09, 2023" with the below badges:
| badge_type     | badge_text  |
| CLASSIFICATION | Systemic Tx |
And the "This is a Medication without codings" event MARKER is available on the "Treatment and Plan" swimlane at "Mar 11, 2022" with 2 miniaturized badges:
| badge_types_in_order |
| CLASSIFICATION       |
| DOSE_CHANGED         |
When the user hovers the following event label on the "Treatment and Plan" swimlane:
| event_type | name_on_label                        | date_on_timeline_axis |
| MARKER     | This is a Medication without codings | Mar 11, 2022          |
Then the "This is a Medication without codings" event MARKER is available on the "Treatment and Plan" swimlane at "Mar 11, 2022" with the below badges:
| badge_type     | badge_text       |
| CLASSIFICATION | Systemic Tx      |
| DOSE_CHANGED   | +100.0% +3 mg/m2 |
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label                        | date_on_timeline_axis |
| MARKER     | This is a Medication without codings | Jun 09, 2020          |
Then the sidebar header contains the following badges:
| badge_type     | badge_text  |
| CLASSIFICATION | Systemic Tx |
And the sidebar content contains the following data:
| data_type | title  | value                                                         |
| KEY-VALUE | Drug   | This is a Medication without codings                          |
| KEY-VALUE | Dosage | 3 mg/m2                                                       |
| KEY-VALUE | Route  | Subcutaneous (part of 'Second test regimen'), epic.com system |
| KEY-VALUE | Status | completed                                                     |
| KEY-VALUE | Intent | order                                                         |
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label                        | date_on_timeline_axis |
| MARKER     | This is a Medication without codings | Mar 11, 2022          |
Then the sidebar header contains the following badges:
| badge_type     | badge_text       |
| CLASSIFICATION | Systemic Tx      |
| DOSE_CHANGED   | +100.0% +3 mg/m2 |
And the sidebar content contains the following data:
| data_type | title  | value                                                                                                                                |
| KEY-VALUE | Drug   | This is a Medication without codings                                                                                                 |
| KEY-VALUE | Dosage | 6 mg/m2                                                                                                                              |
| BADGE     | <N/A>  | [DOSE_CHANGED]: +100.0% +3 mg/m2                                                                                                     |
| KEY-VALUE | Route  | Subcutaneous (part of 'Second test regimen'), hl7.org system. This Med Request has a Medication without coding but with ingredients. |
| KEY-VALUE | Status | completed                                                                                                                            |
| KEY-VALUE | Intent | order                                                                                                                                |
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label                                              | date_on_timeline_axis |
| MARKER     | This is a Medication without codings but other ingredients | Jan 11, 2023          |
Then the sidebar header contains the following badges:
| badge_type     | badge_text  |
| CLASSIFICATION | Systemic Tx |
And the sidebar content contains the following data:
| data_type | title  | value                                                                                                  |
| KEY-VALUE | Drug   | This is a Medication without codings but other ingredients                                             |
| KEY-VALUE | Dosage | 3 mg/m2                                                                                                |
| KEY-VALUE | Route  | Subcutaneous (part of 'Second test regimen'), hl7.org system. Other Medication with other ingredients. |
| KEY-VALUE | Status | completed                                                                                              |
| KEY-VALUE | Intent | order                                                                                                  |
When the user clicks on the following event point on the "Treatment and Plan" swimlane:
| event_type | name_on_label                        | date_on_timeline_axis |
| MARKER     | bortezomib (VELCADE) chemo injection | Jul 09, 2023          |
Then the sidebar content contains the following data:
| data_type | title  | value                                                         |
| KEY-VALUE | Drug   | bortezomib (VELCADE) chemo injection                          |
| KEY-VALUE | Dosage | 1 mg/m2                                                       |
| KEY-VALUE | Route  | Subcutaneous (part of 'Second test regimen'), epic.com system |
| KEY-VALUE | Status | completed                                                     |
| KEY-VALUE | Intent | order                                                         |
