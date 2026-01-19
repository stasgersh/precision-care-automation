Alias: $loinc = http://loinc.org
Alias: $sct = http://snomed.info/sct
Alias: $us-core-documentreference-category = http://hl7.org/fhir/us/core/CodeSystem/us-core-documentreference-category
Alias: $v3-ActCode = http://terminology.hl7.org/CodeSystem/v3-ActCode
Alias: $v3-ParticipationType = http://terminology.hl7.org/CodeSystem/v3-ParticipationType
Alias: $rxnorm = http://www.nlm.nih.gov/research/umls/rxnorm
Alias: $observation-category = http://terminology.hl7.org/CodeSystem/observation-category
Alias: $v2-0203 = http://terminology.hl7.org/CodeSystem/v2-0203

Instance: --PATIENT.ID.1--
InstanceOf: Patient
Usage: #inline
* name.use = #official
* name.family = "TEST_PS"
* name.given = "SmartSch1"
* gender = #male
* birthDate = "1971-09-11"

/**************************************************************
 * DocumentReference after the cut date
 *************************************************************/
Instance: --DIAGNOSTIC.REPORT.ID.2.2--
InstanceOf: DocumentReference
Usage: #inline
* status = #current
* category = $us-core-documentreference-category#clinical-note "Clinical Note"
* category.text = "Clinical Note"
* type = $loinc#LP18648-3 "Pathology Report"
* type.text = "Pathology Report"
* content.attachment.contentType = #text/plain
* content.attachment.data = "UHlsb3JpYyBzdHJpY3R1cmV+ICBiZW5pZ24gID9nYXN0cmljIHBvbHlwfiBmdW5kaWM/ICBOb2R1bGFyIGFwcGVhcmVuY2UgaW4gZGlzdGFsIG9lc29waGFndXM/IEEuIEJpb3BzeSB4IDIuICAgQi4gUHlsb3J1cyB4IDYuICBDLiBPZXNvcGhhZ3VzOiBCaW9wc3kgeCA0LiAgUHJlLWNhc3NldHRlZC4gWzNdbnIgQS4gU3BlY2lhbGlzZWQgZ2FzdHJpYyBtdWNvc2EgYmlvcHN5IHNob3dpbmcgbm9ybWFsIGFwcGVhcmFuY2VzIHdpdGggbm8gZXZpZGVuY2Ugb2YgYWN0aXZlIGluZmxhbW1hdGlvbjsgZ3JhbnVsb21hczsgYXRyb3BoeTsgaW50ZXN0aW5hbCBtZXRhcGxhc2lhOyBkeXNwbGFzaWEgbm9yIG1hbGlnbmFuY3kuIEhlbGljb2JhY3RlciBweWxvcmktbGlrZSBvcmdhbmlzbXMgYXJlIGFic2VudCBvbiByb3V0aW5lIHN0YWluaW5nLiBEaWFnbm9zdGljIGZlYXR1cmVzIG9mIGZ1bmRpYyBnbGFuZCBwb2x5cCBhcmUgYWJzZW50LiAgICBCLiBOb24tc3BlY2lhbGlzZWQgZ2FzdHJpYyBtdWNvc2FsIGJpb3BzaWVzIHNob3dpbmcgbW9kZXJhdGVseSBkaWZmZXJlbnRpYXRlZCBjYXJjaW5vbWEgd2l0aCBvY2Nhc2lvbmFsIHR1bW91ciBuZXN0cyB3aXRoaW4gdGhlIGxhbWluYSBwcm9wcmlhIGRpc3BsYXlpbmcgYSBtaWNyb3BhcGlsbGFyeSBhcmNoaXRlY3R1cmUuIEltbXVub3N0YWluaW5nIHJldmVhbHMgdHVtb3VyIGNlbGwgZXhwcmVzc2lvbiBvZiBDQS0xMjUgYW5kIFdULTEgKG9jY2FzaW9uYWwgY2VsbHMpIHdpdGggbmVnYXRpdmUgc3RhaW5pbmcgZm9yIENEWC0yLiBUaGVzZSBmZWF0dXJlcyBhcmUgaW4ga2VlcGluZyB3aXRoIG1ldGFzdGF0aWMgb3ZhcmlhbiBzZXJvdXMgcGFwaWxsYXJ5IGNhcmNpbm9tYS4gICAgIEMuIEJpb3BzaWVzIHNob3dpbmcgYSBwYXJ0aWFsbHkgY29sbGFwc2VkIG9lc29waGFnZWFsIGN5c3Qgd2l0aCBhIGJpbGF5ZXJlZCBsaW5pbmcuIFRoZXJlIGlzIGFsc28gYSBmcmFnbWVudCBvZiB1bGNlciBzbG91Z2ggbWF0ZXJpYWwuIEEuIEdBU1RSSUMgQklPUFNZOiBOTyBESUFHTk9TVElDIEZFQVRVUkVTIE9GIEZVTkRJQyBHTEFORCBQT0xZUCAgICBCLiBQWUxPUlVTIEJJT1BTSUVTOiBGRUFUVVJFUyBJTiBLRUVQSU5HIFdJVEggTUVUQVNUQVRJQyBPVkFSSUFOIFNFUk9VUyBQQVBJTExBUlkgQ0FSQ0lOT01BICAgIEMuIE9FU09QSEFHRUFMIEJJT1BTSUVTOiBPRVNPUEhBR0VBTCBDWVNUIFdJVEggRVZJREVOQ0UgT0YgVUxDRVJBVElPTg=="
* subject = Reference(Patient/--PATIENT.ID.1--)
* date = "2005-10-03T10:22:22.000Z"
