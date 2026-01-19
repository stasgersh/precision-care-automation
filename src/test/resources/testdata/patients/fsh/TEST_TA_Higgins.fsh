Alias: $allergyintolerance-clinical = http://terminology.hl7.org/CodeSystem/allergyintolerance-clinical
Alias: $allergyintolerance-verification = http://terminology.hl7.org/CodeSystem/allergyintolerance-verification
Alias: $sct = http://snomed.info/sct
Alias: $condition-clinical = http://terminology.hl7.org/CodeSystem/condition-clinical
Alias: $condition-ver-status = http://terminology.hl7.org/CodeSystem/condition-ver-status
Alias: $condition-category = http://terminology.hl7.org/CodeSystem/condition-category
Alias: $icd-10-cm = http://hl7.org/fhir/sid/icd-10-cm
Alias: $v2-0074 = http://terminology.hl7.org/CodeSystem/v2-0074
Alias: $loinc = http://loinc.org
Alias: $cpt = http://www.ama-assn.org/go/cpt
Alias: $v2-0203 = http://terminology.hl7.org/CodeSystem/v2-0203
Alias: $us-core-documentreference-category = http://hl7.org/fhir/us/core/CodeSystem/us-core-documentreference-category
Alias: $v3-ActCode = http://terminology.hl7.org/CodeSystem/v3-ActCode
Alias: $codesystem-service-type.html = http://hl7.org/fhir/R4/codesystem-service-type.html
Alias: $demo = http://gehealtcare.precision-insights/demo
Alias: $reason-medication-given = http://terminology.hl7.org/CodeSystem/reason-medication-given
Alias: $rxnorm = http://www.nlm.nih.gov/research/umls/rxnorm
Alias: $observation-category = http://terminology.hl7.org/CodeSystem/observation-category
Alias: $null_system = http://ge.com/oncoflow/null_system
Alias: $HCPCS-all-codes = http://terminology.hl7.org/CodeSystem/HCPCS-all-codes

Instance: d39ded02-ef1a-4998-ba5a-9d80e7325c93
InstanceOf: Encounter
Usage: #inline
* status = #finished
* class = $v3-ActCode#AMB "ambulatory"
* type = $codesystem-service-type.html#214 "Radiation Oncology"
* type.text = "Oncology Visit"
* subject = Reference(2d5d2b8e-24ba-4241-3f80-65e8d678298a)
* period.start = "2025-04-12T17:00:00+02:00"
* period.end = "2025-04-12T18:00:00+02:00"

Instance: 23de8b4c-81c2-44c9-bef9-84164338df4c
InstanceOf: ServiceRequest
Usage: #inline
* status = #active
* intent = #order
* category = $sct#409063005 "Counselling"
* code = $codesystem-service-type.html#214 "Radiation Oncology"
* subject = Reference(2d5d2b8e-24ba-4241-3f80-65e8d678298a)
* occurrenceDateTime = "2025-11-15T09:25:00.000Z"

Instance: 69d6db20-bcae-4b02-9fdc-1f454e8e6e44
InstanceOf: ServiceRequest
Usage: #inline
* status = #active
* intent = #order
* category = $sct#363679005 "Imaging"
* code = $demo#82542-0 "PSMA-PET scan"
* code.text = "PSMA-PET scan"
* subject = Reference(2d5d2b8e-24ba-4241-3f80-65e8d678298a)
* occurrenceDateTime = "2025-11-12T09:25:00.000Z"

Instance: 4d3b71dc-4b45-423f-97d6-7fe6e5abbece
InstanceOf: Encounter
Usage: #inline
* identifier.system = "https://ge.com/oncoflow/omop/visit_occurrence_id"
* identifier.value = "14193695"
* status = #finished
* class = $v3-ActCode#AMB "ambulatory"
* subject = Reference(2d5d2b8e-24ba-4241-3f80-65e8d678298a)
* period.start = "2017-06-11T10:16:00.000Z"
* period.end = "2017-06-11T23:59:00.000Z"

Instance: 7a5adbe4-bc2f-4e13-9be0-503f12fa7726
InstanceOf: ServiceRequest
Usage: #inline
* status = #active
* intent = #order
* category = $sct#363679005 "Imaging"
* code = $demo#82542-0 "PSMA-PET scan"
* code.text = "PSMA-PET scan"
* subject = Reference(2d5d2b8e-24ba-4241-3f80-65e8d678298a)
* occurrenceDateTime = "2025-05-12T09:25:00.000Z"

Instance: d2319370-b4e8-4cf1-8537-16d0044fb9fb
InstanceOf: ServiceRequest
Usage: #inline
* status = #active
* intent = #order
* category = $sct#108252007 "Laboratory procedure"
* code = $sct#63476009 "Prostate specific antigen measurement"
* code.text = "PSA measurement"
* subject = Reference(2d5d2b8e-24ba-4241-3f80-65e8d678298a)
* occurrenceDateTime = "2025-09-12T09:25:00.000Z"

Instance: d1729548-1795-4068-a0d8-df5575c7d68f
InstanceOf: Observation
Usage: #inline
* identifier.system = "https://ge.com/oncoflow/omop/measurement_id"
* identifier.value = "836919506"
* status = #final
* category = $observation-category#laboratory "laboratory"
* code = $loinc#2857-1 "Prostate specific Ag [Mass/volume] in Serum or Plasma"
* code.text = "Prostate specific Ag [Mass/volume] in Serum or Plasma"
* subject = Reference(2d5d2b8e-24ba-4241-3f80-65e8d678298a)
* effectiveDateTime = "2024-10-23T10:04:00.000Z"
* valueQuantity = 196.19 'ng/mL' "ng/mL"
* referenceRange.low = 0 'ng/mL' "ng/mL"
* referenceRange.high = 0 'ng/mL' "ng/mL"

Instance: b6814260-63cc-4ca9-aee4-a1d2a9ff047c
InstanceOf: Observation
Usage: #inline
* identifier.system = "https://ge.com/oncoflow/omop/measurement_id"
* identifier.value = "836919506"
* status = #final
* category = $observation-category#laboratory "laboratory"
* code = $loinc#2857-1 "Prostate specific Ag [Mass/volume] in Serum or Plasma"
* code.text = "Prostate specific Ag [Mass/volume] in Serum or Plasma"
* subject = Reference(2d5d2b8e-24ba-4241-3f80-65e8d678298a)
* effectiveDateTime = "2025-04-25T08:00:00.000Z"
* valueQuantity = 2.43 'ng/mL' "ng/mL"
* referenceRange.low = 0 'ng/mL' "ng/mL"
* referenceRange.high = 0 'ng/mL' "ng/mL"

Instance: a2d9077d-9446-44a3-8ce2-fd057178058b
InstanceOf: Observation
Usage: #inline
* identifier.system = "https://ge.com/oncoflow/omop/measurement_id"
* identifier.value = "855743555"
* status = #registered
* category = $observation-category#vital-signs "Vital Signs"
* code = $loinc#29463-7 "Body weight"
* code.text = "Body weight"
* subject = Reference(2d5d2b8e-24ba-4241-3f80-65e8d678298a)
* encounter = Reference(4d3b71dc-4b45-423f-97d6-7fe6e5abbece)
* effectiveDateTime = "2017-06-11T10:30:00.000Z"
* valueQuantity = 101.15 'kg' "kg"
* referenceRange.low = 0 'kg' "kg"
* referenceRange.high = 24000 'kg' "kg"

Instance: 1acc994e-8e0d-4dcf-a04d-bd69ddfa995b
InstanceOf: DiagnosticReport
Usage: #inline
* status = #final
* category = $v2-0074#LAB "Laboratory"
* category.text = "Laboratory"
* code = $loinc#24324-6 "Hepatic function panel - Serum or Plasma"
* subject = Reference(2d5d2b8e-24ba-4241-3f80-65e8d678298a)
* effectiveDateTime = "2017-04-06T00:00:00.000Z"
* issued = "2017-04-06T00:00:00.000Z"
* result = Reference(cd7c73f6-92e9-4a36-adc4-157b032f6e20) "Alanine aminotransferase [Enzymatic activity/volume] in Serum or Plasma"

Instance: dab8a2dc-ba8e-4170-9087-0bdcee48ea0e
InstanceOf: Observation
Usage: #inline
* identifier.system = "https://ge.com/oncoflow/omop/measurement_id"
* identifier.value = "1373400479"
* status = #registered
* category = $observation-category#laboratory "laboratory"
* code = $loinc#2857-1 "Prostate specific Ag [Mass/volume] in Serum or Plasma"
* code.text = "Prostate specific Ag [Mass/volume] in Serum or Plasma"
* subject = Reference(2d5d2b8e-24ba-4241-3f80-65e8d678298a)
* effectiveDateTime = "2017-08-13T12:18:00.000Z"
* valueQuantity = 0.22 'ng/mL' "ng/mL"
* referenceRange.low = 0 'ng/mL' "ng/mL"
* referenceRange.high = 0 'ng/mL' "ng/mL"

Instance: 1117bd59-214c-435e-8401-0ac7c2f03a4d
InstanceOf: MedicationAdministration
Usage: #inline
* status = #in-progress
* medicationCodeableConcept = $demo#CC-94676 "Androgen Receptor Degrade"
* medicationCodeableConcept.text = "Androgen Receptor Degrade"
* subject = Reference(2d5d2b8e-24ba-4241-3f80-65e8d678298a)
* effectiveDateTime = "2024-11-07T11:30:00.000Z"
* reasonCode = $reason-medication-given#b "Given as Ordered"
* dosage.text = "50mg x 1 days x 3 months"
* dosage.route = $sct#26643006 "Oral route"
* dosage.dose = 50 'mg' "mg"

Instance: cd7c73f6-92e9-4a36-adc4-157b032f6e20
InstanceOf: Observation
Usage: #inline
* identifier.system = "https://ge.com/oncoflow/omop/measurement_id"
* identifier.value = "1281293844"
* status = #registered
* category = $observation-category#laboratory "laboratory"
* code = $loinc#1742-6 "Alanine aminotransferase [Enzymatic activity/volume] in Serum or Plasma"
* code.text = "Alanine aminotransferase [Enzymatic activity/volume] in Serum or Plasma"
* subject = Reference(2d5d2b8e-24ba-4241-3f80-65e8d678298a)
* effectiveDateTime = "2017-04-06T10:35:00.000Z"
* valueQuantity = 32 '[U]/L' "[U]/L"
* referenceRange.low = 4 '[U]/L' "[U]/L"
* referenceRange.high = 40 '[U]/L' "[U]/L"

Instance: a41b2a5a-1183-4123-a6ba-c3556f15f8cb
InstanceOf: Procedure
Usage: #inline
* extension[0].url = "http://hl7.org/fhir/us/mcode/StructureDefinition/mcode-procedure-intent"
* extension[=].valueCodeableConcept = $sct#373808002 "Curative - procedure intent"
* extension[+].url = "http://hl7.org/fhir/us/mcode/StructureDefinition/mcode-radiotherapy-modality-and-technique"
* extension[=].extension[0].url = "http://hl7.org/fhir/us/mcode/StructureDefinition/mcode-radiotherapy-modality"
* extension[=].extension[=].valueCodeableConcept = $sct#168528006 "Palliative course of radiotherapy"
* extension[=].extension[=].valueCodeableConcept.text = "Palliative radiation to right inferior pubic ramus"
* extension[=].extension[+].url = "http://hl7.org/fhir/us/mcode/StructureDefinition/mcode-radiotherapy-technique"
* extension[=].extension[=].valueCodeableConcept = $sct#1156530009 "Volumetric Modulated Arc Therapy (procedure)"
* extension[+].url = "http://hl7.org/fhir/us/mcode/StructureDefinition/mcode-radiotherapy-dose-delivered-to-volume"
* extension[=].extension[0].url = "totalDoseDelivered"
* extension[=].extension[=].valueQuantity = 2500 'cGy'
* extension[=].extension[+].url = "fractionsDelivered"
* extension[=].extension[=].valueUnsignedInt = 14
* status = #completed
* category = $sct#108290001 "Radiation oncology AND/OR radiotherapy (procedure)"
* code = $sct#1217123003 "Radiotherapy course of treatment"
* subject = Reference(2d5d2b8e-24ba-4241-3f80-65e8d678298a)
* performedPeriod.start = "2014-03-10T15:32:12.778Z"
* performedPeriod.end = "2014-03-30T15:32:12.778Z"
* reasonCode = $sct#93974005 "Primary malignant neoplasm of prostate"
* bodySite = $sct#78904004 "Pelvic structure (body structure)"

Instance: a968e1fd-6ea4-5fa6-b30a-84c56ee9f735
InstanceOf: MedicationStatement
Usage: #inline
* identifier.system = "https://ge.com/oncoflow/omop/drug_exposure_id"
* identifier.value = "509303904"
* status = #completed
* medicationCodeableConcept = $rxnorm#72962 "docetaxel"
* medicationCodeableConcept.text = "docetaxel"
* subject = Reference(2d5d2b8e-24ba-4241-3f80-65e8d678298a)
* effectiveDateTime = "2018-01-14T16:06:00.000Z"
* dosage.route = $sct#47625008 "Intravenous route"
* dosage.route.text = "Intravenous route"

Instance: 2d5d2b8e-24ba-4241-3f80-65e8d678298a
InstanceOf: Patient
Usage: #inline
* name.use = #official
* name.family = "TEST_TA"
* name.given = "Higgins"
* gender = #male
* birthDate = "1958-05-28"