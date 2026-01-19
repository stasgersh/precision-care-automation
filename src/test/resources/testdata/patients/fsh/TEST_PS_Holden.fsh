Alias: $loinc = http://loinc.org
Alias: $sct = http://snomed.info/sct
Alias: $us-core-documentreference-category = http://hl7.org/fhir/us/core/CodeSystem/us-core-documentreference-category
Alias: $v3-ActCode = http://terminology.hl7.org/CodeSystem/v3-ActCode
Alias: $v3-ParticipationType = http://terminology.hl7.org/CodeSystem/v3-ParticipationType
Alias: $rxnorm = http://www.nlm.nih.gov/research/umls/rxnorm
Alias: $observation-category = http://terminology.hl7.org/CodeSystem/observation-category
Alias: $v2-0203 = http://terminology.hl7.org/CodeSystem/v2-0203

Instance: 60f42c69-2a79-4742-960a-2722d5ffcda6
InstanceOf: Patient
Usage: #inline
* name.use = #official
* name.family = "TEST_PS"
* name.given = "Holden"
* gender = #male
* birthDate = "1973-12-03"

/**************************************************************
 * DiagnosticReport before the cut date
 * AI summary of this report is present in 'Bried history'
 *************************************************************/
Instance: cd397164-4846-445e-9959-31d4e9fdbadc
InstanceOf: DiagnosticReport
Usage: #inline
* status = #final
* category = $loinc#LP29684-5 "Radiology"
* category.text = "Radiology"
* subject = Reference(60f42c69-2a79-4742-960a-2722d5ffcda6)
* effectiveDateTime = "2006-08-08T16:31:51.000Z"
* performer = Reference(Practitioner/oB5eYSINJa30) "Dr. Bianca Matrey"
* conclusion = "STABLE"
* presentedForm.contentType = #text/plain
* presentedForm.data = "Q1QgVGhvcmF4IEFiZG9tZW4gUGVsdmlzIHdpdGggQ29udHJhc3QKCkluZGljYXRpb246IFRoZSBwYXRpZW50IHByZXNlbnRlZCB3aXRoIHN5bXB0b21zIG9mIGNoZXN0IHBhaW4gYW5kIHNob3J0bmVzcyBvZiBicmVhdGguIEEgQ1QgVGhvcmF4IEFiZG9tZW4gUGVsdmlzIHdpdGggQ29udHJhc3Qgd2FzIG9yZGVyZWQgdG8gZXZhbHVhdGUgZm9yIHBvc3NpYmxlIHB1bG1vbmFyeSBlbWJvbGlzbSwgcG5ldW1vbmlhLCBhbmQvb3IgYWJkb21pbmFsIHBhdGhvbG9neS4KClByb2NlZHVyZTogVGhlIHBhdGllbnQgd2FzIGFkbWluaXN0ZXJlZCAxMjAgbUwgb2Ygbm9uLWlvbmljIGNvbnRyYXN0IGFnZW50IGludHJhdmVub3VzbHkgYW5kIHVuZGVyd2VudCBhIENUIHNjYW4gb2YgdGhlIHRob3JheCwgYWJkb21lbiwgYW5kIHBlbHZpcyB1c2luZyBhIDY0LXNsaWNlIENUIHNjYW5uZXIuCgpGaW5kaW5nczoKClRoZSBDVCBzY2FuIHJldmVhbGVkIHRoZSBmb2xsb3dpbmcgZmluZGluZ3M6CgpUaGUgdGhvcmF4OiBObyBldmlkZW5jZSBvZiBwdWxtb25hcnkgZW1ib2xpc20gb3IgcG5ldW1vbmlhIHdhcyBzZWVuLiBUaGUgbHVuZ3Mgd2VyZSBjbGVhciwgd2l0aCBubyBldmlkZW5jZSBvZiBjb25zb2xpZGF0aW9uIG9yIGVmZnVzaW9uLgpUaGUgYWJkb21lbjogVGhlIGxpdmVyLCBzcGxlZW4sIGFuZCBraWRuZXlzIHdlcmUgbm9ybWFsIGluIHNpemUgYW5kIHNoYXBlLiBUaGUgcGFuY3JlYXMgd2FzIG5vcm1hbCwgd2l0aCBubyBldmlkZW5jZSBvZiBwYW5jcmVhdGl0aXMgb3IgcGFuY3JlYXRpYyBtYXNzLiBUaGUgc21hbGwgYm93ZWwgd2FzIG5vcm1hbCwgd2l0aCBubyBldmlkZW5jZSBvZiBvYnN0cnVjdGlvbiBvciBpbmZsYW1tYXRvcnkgY2hhbmdlcy4gVGhlIGxhcmdlIGJvd2VsIHdhcyBub3JtYWwsIHdpdGggbm8gZXZpZGVuY2Ugb2YgZGl2ZXJ0aWN1bGl0aXMgb3IgaW5mbGFtbWF0b3J5IGNoYW5nZXMuClRoZSBwZWx2aXM6IFRoZSBwZWx2aXMgd2FzIG5vcm1hbCwgd2l0aCBubyBldmlkZW5jZSBvZiBmcmFjdHVyZSBvciBpbmZsYW1tYXRvcnkgY2hhbmdlcy4KQ29udHJhc3QgRW5oYW5jZW1lbnQ6CgpUaGUgY29udHJhc3QgYWdlbnQgZW5oYW5jZWQgdGhlIHZpc3VhbGl6YXRpb24gb2YgdGhlIHZhc2N1bGFyIHN0cnVjdHVyZXMsIGluY2x1ZGluZyB0aGUgYW9ydGEsIHB1bG1vbmFyeSBhcnRlcmllcywgYW5kIHJlbmFsIGFydGVyaWVzLiBObyBldmlkZW5jZSBvZiB0aHJvbWJ1cyBvciBlbWJvbHVzIHdhcyBzZWVuIGluIHRoZSBwdWxtb25hcnkgYXJ0ZXJpZXMuCgpJbXByZXNzaW9uOgoKQmFzZWQgb24gdGhlIENUIFRob3JheCBBYmRvbWVuIFBlbHZpcyB3aXRoIENvbnRyYXN0LCB0aGUgaW1wcmVzc2lvbiBpcyB0aGF0IHRoZSBwYXRpZW50IGhhcyBubyBldmlkZW5jZSBvZiBwdWxtb25hcnkgZW1ib2xpc20sIHBuZXVtb25pYSwgb3IgYWJkb21pbmFsIHBhdGhvbG9neS4gVGhlIHBhdGllbnQncyBzeW1wdG9tcyBhcmUgbGlrZWx5IGR1ZSB0byBhbm90aGVyIGNhdXNlLCBhbmQgZnVydGhlciBldmFsdWF0aW9uIGlzIG5lZWRlZCB0byBkZXRlcm1pbmUgdGhlIHVuZGVybHlpbmcgZGlhZ25vc2lzLgoKUmVjb21tZW5kYXRpb25zOgoKQ29udGludWUgd2l0aCBmdXJ0aGVyIGV2YWx1YXRpb24gYW5kIG1hbmFnZW1lbnQgYXMgZGlyZWN0ZWQgYnkgdGhlIHBhdGllbnQncyBwcmltYXJ5IGNhcmUgcGh5c2ljaWFuLgpDb25zaWRlciBmdXJ0aGVyIGltYWdpbmcgc3R1ZGllcywgc3VjaCBhcyBhIHZlbnRpbGF0aW9uLXBlcmZ1c2lvbiBzY2FuIG9yIGEgcHVsbW9uYXJ5IGZ1bmN0aW9uIHRlc3QsIHRvIGZ1cnRoZXIgZXZhbHVhdGUgZm9yIHB1bG1vbmFyeSBlbWJvbGlzbS4KU2lnbmF0dXJlOgoKRHIgWSBY"
* code = $sct#433761009 "CT THORAX ABDOMEN PELVIS WITH CONTRAST"
* code.text = "CT THORAX ABDOMEN PELVIS WITH CONTRAST"

/**************************************************************
 * DocumentReference refers to an Encounter (cut date)
 * AI summary of this report is present in 'Bried history'
 *************************************************************/
Instance: a390eba8-8497-47ea-a7d3-b56c3e119141
InstanceOf: DocumentReference
Usage: #inline
* status = #current
* type.coding[0] = $loinc#34117-2 "History and physical note - test1"
* category = $us-core-documentreference-category#clinical-note "Clinical Note"
* subject = Reference(60f42c69-2a79-4742-960a-2722d5ffcda6)
* date = "2006-09-09T17:31:51.000Z"
* author = Reference(oB5eYSINJa30) "Dr. Bianca Matrey"
* content.attachment.contentType = #text/plain
* content.attachment.language = #en-US
* content.attachment.data = "Q2xpbmljYWwgYW5kIFRyZWF0bWVudCBTdW1tYXJ5XG5cbk1ldCBjb2xvbiBhZGVub2NhcmNpbm9hbSB3aXRoIHNvbGl0YXJ5IG9tZW50YWwgbWV0IC0gYWxsIHZpc2libGUgZGlzZWFzZSByZXNlY3RlZCBNU1MgUkFTL1JBRiBXVCBzL3AgcHJvcGh5bGFjdGljIEhJUEVDIG9uIDEzLzMvMTggKG9uIHRyaWFsKVxuXG5cblxuSGlzdG9yeSwgRXhhbWluYXRpb24gYW5kIEludmVzdGlnYXRpb25zXG5cblVuZGVyd2VudCBwcm9waHlsYWN0aWMgSElQRUMgKHRyaWFsKSBvbiAxMy8zLzE4IER1ZSAjNiBYZWxveCAjNCBhbmQgNSBkb3NlIGRlbGF5IDIgd2Vla3MgZHVlIHRvIGxvdyBQTHQgYW5kIGxvdyBBTkMsIGdpdmVuIDJkYXlzIHMvYyBHQ1NGIChwcmlvciB0byAjNCBidXQgbm90ICM1KSBIaXN0bzogRElBR05PU0lTIChBKSBMZWZ0IHBlbHZpYyBwZXJpdG9uZXVtOyBleGNpc2lvbiBiaW9wc3k6IEZhdCBuZWNyb3NpcywgaW5mbGFtbWF0aW9uLCBmaWJyb3NpcyBhbmQgZm9yZWlnbiBib2R5LXR5cGUgbXVsdGludWNsZWF0ZSBnaWFudCBjZWxsIHJlYWN0aW9uOyAtIG5lZ2F0aXZlIGZvciBtYWxpZ25hbmN5LiAoQikgT21lbnR1bTsgb21lbnRlY3RvbXk6IE5vbi1zcGVjaWZpYyByZWFjdGl2ZSBjaGFuZ2VzOyAtIHR3byBiZW5pZ24gbHltcGggbm9kZXMgKDAvMiksIC0gbmVnYXRpdmUgZm9yIG1hbGlnbmFuY3kuIEJsZHMgdG9kYXkgQU5DIDAuOTYgUEx0IDEyMWsgQ0VBIDYuNCBmcm9tIDQgQ1QgVEFQIDkvNy8xODogQ29uY2x1c2lvbiBTaW5jZSAxMyBBcHIgMjAxOCwgMS4gU3RhdHVzIHBvc3QgaGlnaCBhbnRlcmlvciByZXNlY3Rpb24gd2l0aCBubyBldmlkZW5jZSBvZiBsb2NhbCByZWN1cnJlbmNlIG9yIGludGVydmFsIGNoYW5nZS4gMi4gVGhlIHB1bG1vbmFyeSBub2R1bGVzLCBzdXNwaWNpb3VzIG9mIG1ldGFzdGFzZXMsIGFyZSBzbWFsbGVyLiBUaGUgcmlnaHQgbG93ZXIgbG9iZSBub2R1bGUgaGFzIG5lYXJseSByZXNvbHZlZC4gMy4gVGhlIGNpcmN1bWZlcmVudGlhbCBtdXJhbCB0aGlja2VuaW5nIGluIHRoZSByZWN0dW0gaXMgbGVzcyBwcm9taW5lbnQgYW5kIHJlbWFpbnMgaW5kZXRlcm1pbmF0ZS4gNC4gTm8gbmV3IGRpc3RhbnQgbWV0YXN0YXNpcy4gU1VCSkVDVElWRSBUb2xlcmF0ZWQgd2VsbCBHMSBuZXVyb3BhdGh5IGluIGZpbmdlcnMgYW5kIHRvZXMgRWF0IGFuZCBCTyByZWd1bGFybHksIHdlbGwgTm8gUFIgYmxlZWQgRHJ5IHNraW4sIG5vIFBQRSwgbm90IHVzaW5nIG1vaXN0dXJpc2VycyBPQkVKQ1RJVkUgRUNPRyAxIC0gbGltcHMgYnV0IHRoaXMgaXMgYmFzZWxpZW4gZnJvbSBwcmV2aW91cyBhY2NpZGVudCBIdCAxNzFjbSBXdCA2MC45a2cgZnJvbSA2MC44a2cgZnJvbSA1Ny44a2cgZnJvbSA1Ni44a2cgZnJvbSA1NS40a2cgZnJvbSA1NWtnIGZyb20gNTQuNGtnIEgtUzFTMiBMIC0gTm8gY3JlcHMgQSAtIFNvZnQgTm9udGVuZGVyIFQgMzYuNCBIUiAxMzEvODYgU3BPMiAxMDAlIG9uIFJBIEhSIDcxIEhvbWUgQlAgbW9uaXRvcmluZyBTQlAgMTIwLTE1MG1tSEcgb24gYW1sb2RpcGluZSAyLjVtZyBPTSBJbXA6IE1ldCBjb2xvbiBhZGVub2NhcmNpbm9hbSB3aXRoIHNvbGl0YXJ5IG9tZW50YWwgbWV0IC0gYWxsIHZpc2libGUgZGlzZWFzZSByZXNlY3RlZCBNU1MgUkFTL1JBRiBXVCBzL3AgcHJvcGh5bGFjdGljIEhJUEVDIG9uIDEzLzMvMTggKG9uIHRyaWFsKSBQT3N0LW9wIENUIC0gaW5jcmVhc2UgaW4gb25lIGx1bmcgbW9kdWxlID9tZXQgLSByZXNwb25kaW5nIHRvIFhlbG94XG5cblxuTWFuYWdlbWVudCBGb3IgVGhpcyBWaXNpdFxuXG5QcmNvZWVkIHdpdGggIzYgWGVsb3ggb24gMy85LzE4IEZCQyBPQSBUQ1UgbWUgNCB3ZWVrcyBCbGRzIE9BIENUIFRBUCBiZWZvcmUgVENVIENvbnR0IGFtbG9kaXBpbmUgYXQgMi41bWcgT00sIGFkdmlzZWQgaG9tZSBCUCBtb25pdG9yaW5nLCBLSVYgZnVydGhlciBpbmNyZWFzZSB0byA1bWcgT00gQWR2aXNlZCB0byByZXR1cm4gZWFybHkgaWYgZmV2ZXIvdW53ZWxsIHdoaWxlIG9uIGNoZW1vIFMvVCBUb2xlcmF0aW5nIGNoZW1vIHdlbGwsIHdlIHdvdWxkIGNvbnRpbnVlIFhlbG94IExBc3QgQ1Qgc2hvd3MgbHVuZyBsZXNpb25zIGFyZSBzbWFsbGVyLCBsaWtlbHkgbHVuZyBtZXRzIGFuZCBzdGFnZSA0IGRpc2Vhc2UgV2Ugd291bGQgcmV2aWV3IENUIHJlc3VsdHMgYWdhaW4gd2l0aCByZXBlYXQgQ1QgYWZ0ZXIgdGhpcyBjeWNsZSBUaGV5IHVuZGVyc3RhbmQsIGFncmVlYWJsZSBBbGwgcXVlc3Rpb25zIGFuc3dlcmVkXG5cblxuTWFuYWdlbWVudCBGb3IgVGhpcyBWaXNpdFxuXG5QcmNvZWVkIHdpdGggIzYgWGVsb3ggb24gMy85LzE4IEZCQyBPQSBUQ1UgbWUgNCB3ZWVrcyBCbGRzIE9BIENUIFRBUCBiZWZvcmUgVENVIC0gcG9zdHBvbmUgY3VycmVudCBDVCBhcHB0IHRvIDE3LzkvMTggQ29udHQgYW1sb2RpcGluZSBhdCAyLjVtZyBPTSwgYWR2aXNlZCBob21lIEJQIG1vbml0b3JpbmcsIEtJViBmdXJ0aGVyIGluY3JlYXNlIHRvIDVtZyBPTSBBZHZpc2VkIHRvIHJldHVybiBlYXJseSBpZiBmZXZlci91bndlbGwgd2hpbGUgb24gY2hlbW8gUy9UIFRvbGVyYXRpbmcgY2hlbW8gd2VsbCwgd2Ugd291bGQgY29udGludWUgWGVsb3ggTEFzdCBDVCBzaG93cyBsdW5nIGxlc2lvbnMgYXJlIHNtYWxsZXIsIGxpa2VseSBsdW5nIG1ldHMgYW5kIHN0YWdlIDQgZGlzZWFzZSBXZSB3b3VsZCByZXZpZXcgQ1QgcmVzdWx0cyBhZ2FpbiB3aXRoIHJlcGVhdCBDVCBhZnRlciB0aGlzIGN5Y2xlIFRoZXkgdW5kZXJzdGFuZCwgYWdyZWVhYmxlIEFsbCBxdWVzdGlvbnMgYW5zd2VyZWQ="
* content.attachment.title = "Test attachment 1"
* context.encounter = Reference(1a2b674e-0bfc-4da2-91fe-67fa5db59677)

/**************************************************************
 * Encounter (cut date)
 *************************************************************/
Instance: 1a2b674e-0bfc-4da2-91fe-67fa5db59677
InstanceOf: Encounter
Usage: #inline
* status = #finished
* class = $v3-ActCode#AMB
* type = $sct#162673000 "General examination of patient (procedure)"
* type.text = "General examination of patient (procedure)"
* subject = Reference(60f42c69-2a79-4742-960a-2722d5ffcda6)
* participant.type = $v3-ParticipationType#PPRF "primary performer"
* participant.type.text = "primary performer"
* participant.period.start = "2006-09-09T17:31:51.000Z"
* participant.period.end = "2006-09-09T17:51:51.000Z"
* participant.individual = Reference(oB5eYSINJa30) "Dr. Bianca Matrey"
* period.start = "2006-09-09T17:31:51.000Z"
* period.end = "2006-09-09T17:51:51.000Z"

/**************************************************************
 * DiagnosticReport after the cut date
 *************************************************************/
Instance: 73af2a8f-d022-40c6-b25f-93b58abd3947
InstanceOf: DiagnosticReport
Usage: #inline
* meta.profile = "http://hl7.org/fhir/us/core/StructureDefinition/us-core-diagnosticreport-note"
* status = #final
* category = $loinc#LP29684-5 "Radiology"
* category.text = "Radiology"
* subject = Reference(60f42c69-2a79-4742-960a-2722d5ffcda6)
* effectiveDateTime = "2006-10-11T11:22:22.000Z"
* conclusion = "STABLE"
* presentedForm.contentType = #text/plain
* presentedForm.data = "RXhhbWluYXRpb24gb2YgSm9pbnQgdW5kZXIgSW1hZ2UgSW50ZW5zaWZpZXI6CgpQcm9jZWR1cmU6IEV4YW1pbmF0aW9uIG9mIHRoZSBsZWZ0IHNob3VsZGVyIGpvaW50IHVuZGVyIGltYWdlIGludGVuc2lmaWVyIChYLXJheSkgd2FzIHBlcmZvcm1lZCB0byBldmFsdWF0ZSB0aGUgc3VzcGVjdGVkIHJvdGF0b3IgY3VmZiB0ZWFyLgoKRmluZGluZ3M6CgpUaGUgZXhhbWluYXRpb24gcmV2ZWFsZWQgdGhlIGZvbGxvd2luZyBmaW5kaW5nczoKClRoZSBqb2ludCBzcGFjZSB3YXMgbWFpbnRhaW5lZCwgd2l0aCBubyBldmlkZW5jZSBvZiBzaWduaWZpY2FudCBqb2ludCBlZmZ1c2lvbiBvciBzd2VsbGluZy4KVGhlIGh1bWVyYWwgaGVhZCBhbmQgZ2xlbm9pZCBjYXZpdHkgd2VyZSB2aXN1YWxpemVkLCBzaG93aW5nIGEgc21hbGwgZnJhY3R1cmUgb2YgdGhlIGdyZWF0ZXIgdHViZXJvc2l0eSBjb25zaXN0ZW50IHdpdGggYSByZWNlbnQgdHJhdW1hLgpUaGUgcm90YXRvciBjdWZmIHRlbmRvbnMgd2VyZSB2aXN1YWxpemVkLCBzaG93aW5nIHNpZ25zIG9mIGRlZ2VuZXJhdGl2ZSBjaGFuZ2VzIGFuZCBwYXJ0aWFsIHRlYXIgb2YgdGhlIHN1cHJhc3BpbmF0dXMgdGVuZG9uLgpUaGUgYWNyb21pb24gYW5kIGNvcmFjb2lkIHByb2Nlc3NlcyB3ZXJlIG5vcm1hbCwgd2l0aCBubyBldmlkZW5jZSBvZiBzaWduaWZpY2FudCBkZWdlbmVyYXRpdmUgY2hhbmdlcyBvciBmcmFjdHVyZXMuClRoZSBib25lIGRlbnNpdHkgd2FzIG5vcm1hbCwgd2l0aCBubyBldmlkZW5jZSBvZiBvc3Rlb3Bvcm9zaXMgb3Igb3RoZXIgYm9ueSBhYm5vcm1hbGl0aWVzLgpJbXByZXNzaW9uOgoKQmFzZWQgb24gdGhlIGV4YW1pbmF0aW9uIHVuZGVyIGltYWdlIGludGVuc2lmaWVyLCB0aGUgaW1wcmVzc2lvbiBpcyB0aGF0IHRoZSBwYXRpZW50IGhhcyBhIHBhcnRpYWwgdGVhciBvZiB0aGUgc3VwcmFzcGluYXR1cyB0ZW5kb24gYW5kIGEgc21hbGwgZnJhY3R1cmUgb2YgdGhlIGdyZWF0ZXIgdHViZXJvc2l0eSBvZiB0aGUgaHVtZXJ1cywgbGlrZWx5IGR1ZSB0byBhIHJlY2VudCB0cmF1bWEuIFRoZSBkZWdlbmVyYXRpdmUgY2hhbmdlcyBpbiB0aGUgcm90YXRvciBjdWZmIHRlbmRvbnMgYXJlIGNvbnNpc3RlbnQgd2l0aCBhZ2UtcmVsYXRlZCB3ZWFyIGFuZCB0ZWFyLgoKUmVjb21tZW5kYXRpb25zOgoKQ29udGludWUgd2l0aCBwaHlzaWNhbCB0aGVyYXB5IHRvIGltcHJvdmUgcmFuZ2Ugb2YgbW90aW9uIGFuZCBzdHJlbmd0aCBpbiB0aGUgbGVmdCBzaG91bGRlci4KQ29uc2lkZXIgZnVydGhlciBpbWFnaW5nIHN0dWRpZXMsIHN1Y2ggYXMgYW4gTVJJIG9yIENUIHNjYW4sIHRvIGZ1cnRoZXIgZXZhbHVhdGUgdGhlIGV4dGVudCBvZiB0aGUgcm90YXRvciBjdWZmIHRlYXIgYW5kIHRoZSBmcmFjdHVyZS4KQ29uc2lkZXIgcmVmZXJyYWwgdG8gYW4gb3J0aG9wZWRpYyBzcGVjaWFsaXN0IGZvciBmdXJ0aGVyIGV2YWx1YXRpb24gYW5kIHRyZWF0bWVudC4KU2lnbmF0dXJlOgoKRHIgWAo="
* code = $sct#179929004 "Examination of joint under image intensifier (procedure)"
* code.text = "Examination of joint under image intensifier (procedure)"

/**************************************************************
 * Observation after the cut date
 *************************************************************/
Instance: d8040fb3-6413-4839-bc3c-531bf0fa11c7
InstanceOf: Observation
Usage: #inline
* status = #final
* category = $observation-category#vital-signs "vital-signs"
* code = $loinc#29463-7 "Body Weight"
* code.text = "Body Weight"
* subject = Reference(60f42c69-2a79-4742-960a-2722d5ffcda6)
* effectiveDateTime = "2006-11-28T10:55:12.000Z"
* issued = "2006-11-28T10:55:12.000Z"
* valueQuantity = 47.3 'kg' "kg"
* note.text = "Body Weight 47.3 kg"

/**************************************************************
 * DocumentReference after the cut date
 *************************************************************/
Instance: 51db2319-059c-43a0-b835-f8da1646d950
InstanceOf: DocumentReference
Usage: #inline
* status = #current
* category = $us-core-documentreference-category#clinical-note "Clinical Note"
* category.text = "Clinical Note"
* type = $loinc#LP18648-3 "Pathology Report"
* type.text = "Pathology Report"
* content.attachment.contentType = #text/plain
* content.attachment.data = "UHlsb3JpYyBzdHJpY3R1cmV+ICBiZW5pZ24gID9nYXN0cmljIHBvbHlwfiBmdW5kaWM/ICBOb2R1bGFyIGFwcGVhcmVuY2UgaW4gZGlzdGFsIG9lc29waGFndXM/IEEuIEJpb3BzeSB4IDIuICAgQi4gUHlsb3J1cyB4IDYuICBDLiBPZXNvcGhhZ3VzOiBCaW9wc3kgeCA0LiAgUHJlLWNhc3NldHRlZC4gWzNdbnIgQS4gU3BlY2lhbGlzZWQgZ2FzdHJpYyBtdWNvc2EgYmlvcHN5IHNob3dpbmcgbm9ybWFsIGFwcGVhcmFuY2VzIHdpdGggbm8gZXZpZGVuY2Ugb2YgYWN0aXZlIGluZmxhbW1hdGlvbjsgZ3JhbnVsb21hczsgYXRyb3BoeTsgaW50ZXN0aW5hbCBtZXRhcGxhc2lhOyBkeXNwbGFzaWEgbm9yIG1hbGlnbmFuY3kuIEhlbGljb2JhY3RlciBweWxvcmktbGlrZSBvcmdhbmlzbXMgYXJlIGFic2VudCBvbiByb3V0aW5lIHN0YWluaW5nLiBEaWFnbm9zdGljIGZlYXR1cmVzIG9mIGZ1bmRpYyBnbGFuZCBwb2x5cCBhcmUgYWJzZW50LiAgICBCLiBOb24tc3BlY2lhbGlzZWQgZ2FzdHJpYyBtdWNvc2FsIGJpb3BzaWVzIHNob3dpbmcgbW9kZXJhdGVseSBkaWZmZXJlbnRpYXRlZCBjYXJjaW5vbWEgd2l0aCBvY2Nhc2lvbmFsIHR1bW91ciBuZXN0cyB3aXRoaW4gdGhlIGxhbWluYSBwcm9wcmlhIGRpc3BsYXlpbmcgYSBtaWNyb3BhcGlsbGFyeSBhcmNoaXRlY3R1cmUuIEltbXVub3N0YWluaW5nIHJldmVhbHMgdHVtb3VyIGNlbGwgZXhwcmVzc2lvbiBvZiBDQS0xMjUgYW5kIFdULTEgKG9jY2FzaW9uYWwgY2VsbHMpIHdpdGggbmVnYXRpdmUgc3RhaW5pbmcgZm9yIENEWC0yLiBUaGVzZSBmZWF0dXJlcyBhcmUgaW4ga2VlcGluZyB3aXRoIG1ldGFzdGF0aWMgb3ZhcmlhbiBzZXJvdXMgcGFwaWxsYXJ5IGNhcmNpbm9tYS4gICAgIEMuIEJpb3BzaWVzIHNob3dpbmcgYSBwYXJ0aWFsbHkgY29sbGFwc2VkIG9lc29waGFnZWFsIGN5c3Qgd2l0aCBhIGJpbGF5ZXJlZCBsaW5pbmcuIFRoZXJlIGlzIGFsc28gYSBmcmFnbWVudCBvZiB1bGNlciBzbG91Z2ggbWF0ZXJpYWwuIEEuIEdBU1RSSUMgQklPUFNZOiBOTyBESUFHTk9TVElDIEZFQVRVUkVTIE9GIEZVTkRJQyBHTEFORCBQT0xZUCAgICBCLiBQWUxPUlVTIEJJT1BTSUVTOiBGRUFUVVJFUyBJTiBLRUVQSU5HIFdJVEggTUVUQVNUQVRJQyBPVkFSSUFOIFNFUk9VUyBQQVBJTExBUlkgQ0FSQ0lOT01BICAgIEMuIE9FU09QSEFHRUFMIEJJT1BTSUVTOiBPRVNPUEhBR0VBTCBDWVNUIFdJVEggRVZJREVOQ0UgT0YgVUxDRVJBVElPTg=="
* author = Reference(Practitioner/oB5eYSINJa30) "Dr. Bianca Matrey"
* author.type = "Practitioner"
* subject = Reference(60f42c69-2a79-4742-960a-2722d5ffcda6)
* date = "2007-04-10T10:55:12.000Z"

/**************************************************************
 * DocumentReference after the cut date
 *************************************************************/
Instance: 67c08483-de82-4149-aaf6-13b79ffc9757
InstanceOf: DocumentReference
Usage: #inline
* status = #current
* category = $us-core-documentreference-category#clinical-note "Clinical Note"
* category.text = "Clinical Note"
* type = $loinc#LP18648-3 "Pathology Report"
* type.text = "Pathology Report"
* content.attachment.contentType = #text/plain
* content.attachment.data = "dGhpcyBpcyBqdXN0IGEgdGVzdA=="
* author.type = "Practitioner"
* subject = Reference(60f42c69-2a79-4742-960a-2722d5ffcda6)
* date = "2007-04-11T10:55:12.000Z"

/**************************************************************
 * DocumentReference after the cut date
 *************************************************************/
Instance: 9919ebca-7853-4d6b-a88c-671a137ef63b
InstanceOf: DocumentReference
Usage: #inline
* status = #current
* category = $us-core-documentreference-category#clinical-note "Clinical Note"
* category.text = "Clinical Note"
* type = $loinc#LP18648-3 "Pathology Report"
* type.text = "Pathology Report"
* content.attachment.contentType = #text/plain
* content.attachment.data = "dGhpcyBpcyBqdXN0IGEgdGVzdA=="
* author.type = "Practitioner"
* subject = Reference(60f42c69-2a79-4742-960a-2722d5ffcda6)
* date = "2007-04-12T10:55:12.000Z"

/**************************************************************
 * DocumentReference after the cut date
 *************************************************************/
Instance: 5ba1996b-3b04-4479-8b8b-72722a789b3d
InstanceOf: DocumentReference
Usage: #inline
* status = #current
* category = $us-core-documentreference-category#clinical-note "Clinical Note"
* category.text = "Clinical Note"
* type = $loinc#LP18648-3 "Pathology Report"
* type.text = "Pathology Report"
* content.attachment.contentType = #text/plain
* content.attachment.data = "dGhpcyBpcyBqdXN0IGEgdGVzdA=="
* author.type = "Practitioner"
* subject = Reference(60f42c69-2a79-4742-960a-2722d5ffcda6)
* date = "2007-05-10T10:55:12.000Z"

/**************************************************************
 * DocumentReference after the cut date
 *************************************************************/
Instance: a19d6391-0500-4f1d-82e4-4a2f05051055
InstanceOf: DocumentReference
Usage: #inline
* status = #current
* category = $us-core-documentreference-category#clinical-note "Clinical Note"
* category.text = "Clinical Note"
* type = $loinc#LP18648-3 "Pathology Report"
* type.text = "Pathology Report"
* content.attachment.contentType = #text/plain
* content.attachment.data = "dGhpcyBpcyBqdXN0IGEgdGVzdA=="
* author.type = "Practitioner"
* subject = Reference(60f42c69-2a79-4742-960a-2722d5ffcda6)
* date = "2007-05-22T10:55:12.000Z"

/**************************************************************
 * DocumentReference after the cut date
 *************************************************************/
Instance: 7d4c8c16-8003-40dc-bf0c-48045a7996bd
InstanceOf: DocumentReference
Usage: #inline
* status = #current
* category = $us-core-documentreference-category#clinical-note "Clinical Note"
* category.text = "Clinical Note"
* type = $loinc#LP18648-3 "Pathology Report"
* type.text = "Pathology Report"
* content.attachment.contentType = #text/plain
* content.attachment.data = "dGhpcyBpcyBqdXN0IGEgdGVzdA=="
* author.type = "Practitioner"
* subject = Reference(60f42c69-2a79-4742-960a-2722d5ffcda6)
* date = "2007-05-22T15:55:12.000Z"

/**************************************************************
 * DocumentReference after the cut date
 *************************************************************/
Instance: f01d0424-1ddc-4b2b-a455-317063ecb9df
InstanceOf: DocumentReference
Usage: #inline
* status = #current
* category = $us-core-documentreference-category#clinical-note "Clinical Note"
* category.text = "Clinical Note"
* type = $loinc#LP18648-3 "Pathology Report"
* type.text = "Pathology Report"
* content.attachment.contentType = #text/plain
* content.attachment.data = "dGhpcyBpcyBqdXN0IGEgdGVzdA=="
* author.type = "Practitioner"
* subject = Reference(60f42c69-2a79-4742-960a-2722d5ffcda6)
* date = "2007-06-10T10:55:12.000Z"

/**************************************************************
 * DocumentReference after the cut date
 *************************************************************/
Instance: 06ba852b-da40-435c-aea2-58a98eab33f7
InstanceOf: DocumentReference
Usage: #inline
* status = #current
* category = $us-core-documentreference-category#clinical-note "Clinical Note"
* category.text = "Clinical Note"
* type = $loinc#LP18648-3 "Pathology Report"
* type.text = "Pathology Report"
* content.attachment.contentType = #text/plain
* content.attachment.data = "dGhpcyBpcyBqdXN0IGEgdGVzdA=="
* author.type = "Practitioner"
* subject = Reference(60f42c69-2a79-4742-960a-2722d5ffcda6)
* date = "2007-06-17T10:55:12.000Z"

/**************************************************************
 * DocumentReference after the cut date
 *************************************************************/
Instance: 3a9028b8-04ca-47c0-9d97-cb0902cb65fc
InstanceOf: DocumentReference
Usage: #inline
* status = #current
* category = $us-core-documentreference-category#clinical-note "Clinical Note"
* category.text = "Clinical Note"
* type = $loinc#LP18648-3 "Pathology Report"
* type.text = "Pathology Report"
* content.attachment.contentType = #text/plain
* content.attachment.data = "dGhpcyBpcyBqdXN0IGEgdGVzdA=="
* author.type = "Practitioner"
* subject = Reference(60f42c69-2a79-4742-960a-2722d5ffcda6)
* date = "2007-06-23T10:55:12.000Z"

/**************************************************************
 * DocumentReference after the cut date
 *************************************************************/
Instance: 2d9b906a-ec15-464e-8e54-46cfc979b74f
InstanceOf: DocumentReference
Usage: #inline
* status = #current
* category = $us-core-documentreference-category#clinical-note "Clinical Note"
* category.text = "Clinical Note"
* type = $loinc#LP18648-3 "Pathology Report"
* type.text = "Pathology Report"
* content.attachment.contentType = #text/plain
* content.attachment.data = "dGhpcyBpcyBqdXN0IGEgdGVzdA=="
* author.type = "Practitioner"
* subject = Reference(60f42c69-2a79-4742-960a-2722d5ffcda6)
* date = "2007-07-07T10:55:12.000Z"

/**************************************************************
 * DocumentReference after the cut date
 *************************************************************/
Instance: a6ff03da-e7b7-49a5-8ea3-ce4ac596f94e
InstanceOf: DocumentReference
Usage: #inline
* status = #current
* category = $us-core-documentreference-category#clinical-note "Clinical Note"
* category.text = "Clinical Note"
* type = $loinc#LP18648-3 "Pathology Report"
* type.text = "Pathology Report"
* content.attachment.contentType = #text/plain
* content.attachment.data = "dGhpcyBpcyBqdXN0IGEgdGVzdA=="
* author.type = "Practitioner"
* subject = Reference(60f42c69-2a79-4742-960a-2722d5ffcda6)
* date = "2007-07-17T10:55:12.000Z"

/**************************************************************
 * DocumentReference after the cut date
 *************************************************************/
Instance: fd90a9c5-b6e9-4ab0-a566-0cd89a061a17
InstanceOf: DocumentReference
Usage: #inline
* status = #current
* category = $us-core-documentreference-category#clinical-note "Clinical Note"
* category.text = "Clinical Note"
* type = $loinc#LP18648-3 "Pathology Report"
* type.text = "Pathology Report"
* content.attachment.contentType = #text/plain
* content.attachment.data = "dGhpcyBpcyBqdXN0IGEgdGVzdA=="
* author.type = "Practitioner"
* subject = Reference(60f42c69-2a79-4742-960a-2722d5ffcda6)
* date = "2007-07-22T10:55:12.000Z"

/**************************************************************
 * DocumentReference after the cut date
 *************************************************************/
Instance: a78f5323-b164-4ceb-a411-fe5f6ab2cde5
InstanceOf: DocumentReference
Usage: #inline
* status = #current
* category = $us-core-documentreference-category#clinical-note "Clinical Note"
* category.text = "Clinical Note"
* type = $loinc#LP18648-3 "Pathology Report"
* type.text = "Pathology Report"
* content.attachment.contentType = #text/plain
* content.attachment.data = "dGhpcyBpcyBqdXN0IGEgdGVzdA=="
* author.type = "Practitioner"
* subject = Reference(60f42c69-2a79-4742-960a-2722d5ffcda6)
* date = "2008-01-12T10:55:12.000Z"

/**************************************************************
 * DiagnosticReport after the cut date
 *************************************************************/
Instance: 39b6fbe2-f5c8-4af8-8dcf-e996b592175c
InstanceOf: DiagnosticReport
Usage: #inline
* status = #final
* subject = Reference(60f42c69-2a79-4742-960a-2722d5ffcda6)
* effectiveDateTime = "2008-01-23T10:55:12.000Z"
* conclusion = "Left axillary lymph node core biopsy: OK"
* code = $loinc#66121-5 "Tissue Pathology biopsy report"
* code.text = "Tissue Pathology biopsy report"

/**************************************************************
 * DiagnosticReport with images after the cut date
 *************************************************************/
Instance: 33e032e3-4f71-40ba-8c13-37e32f928495
InstanceOf: DiagnosticReport
Usage: #inline
* status = #final
* category = $loinc#LP29684-5 "Radiology"
* category.text = "Radiology"
* subject = Reference(Patient/60f42c69-2a79-4742-960a-2722d5ffcda6)
* effectiveDateTime = "2008-02-02T10:22:22.000Z"
* conclusion = "STABLE"
* presentedForm.contentType = #text/plain
* presentedForm.data = "b3ZhcmlhbiBjYW5jZXIgd2l0aCBwZXJpdG9uZWFsIGRpc2Vhc2UuIGZhZWNhbCB2b21pdGluZywgZW1wdHkgcmVjdHVtLiA/ZGlzZWFzZSBwcm9ncmVzc2lvbiA/c3ViYWN1dGUgYm93ZWwgb2JzdHJ1Y3Rpb24gdW5lbmhhbmNlZCBpbWFnZXMgYWNxdWlyZWQgZHVlIHRvIHBvb3IgcmVuYWwgZnVuY3Rpb24uIGNvbXBhcmlzb24gbWFkZSB3aXRoIHByZXZpb3VzIGN0IGRhdGVkIDI0IDExIDE0LiBubyBzaWduaWZpY2FudCBpbnRlcnZhbCBjaGFuZ2UgaW4gdGhlIGxvY3VsYXRlZCBhc2NpdGVzIHdpdGhpbiB0aGUgcGVsdmlzLiByaWdodCBoeWRyb25lcGhyb3NpcyB1bmNoYW5nZWQuIGN5c3RzIHdpdGhpbiB0aGUgbGl2ZXIgYW5kIGxlZnQga2lkbmV5IG5vdGVkLiB0aGVyZSBoYXMgYmVlbiBubyBzaWduaWZpY2FudCBpbnRlcnZhbCBjaGFuZ2UgaW4gdGhlIHBlcml0b25lYWwgZGlzZWFzZSBzZWVuIHRocm91Z2hvdXQgdGhlIGFiZG9tZW4gYW5kIHBlbHZpcy4gMTAgbW0gYW9ydG9jYXZhbCBseW1waCBub2RlIGlzIHVuY2hhbmdlZC4gbm8gb3RoZXIgbmV3IGVubGFyZ2VkIGFiZG9taW5hbCBvciBwZWx2aWMgbHltcGggbm9kZXMuIHRoZSBzdG9tYWNoIGlzIGZsdWlkLS1maWxsZWQgYW5kIHNsaWdodGx5IGRpc3RvcnRlZC4gcGFydGlhbGx5IGNhbGNpZmllZCBzZXJvc2FsIGRpc2Vhc2UgaXMgc2VlbiB3aXRoaW4gdGhlIGxlc3NlciBzYWMuIHRoZSBweWxvcnVzIGFwcGVhcnMgYnVsa3ksIHdoaWNoIHh4eCByZXByZXNlbnQgZnVydGhlciBzZXJvc2FsIGRpc2Vhc2UgYXQgdGhpcyBzaXRlLCBidXQgdGhpcyBpcyBhZ2FpbiB1bmNoYW5nZWQgc2luY2Ugbm92ZW1iZXIuIHRoZSBzbWFsbCBhbmQgbGFyZ2UgYm93ZWwgYXJlIG9mIG5vcm1hbCBjYWxpYnJlLiBtaW5vciBhdGVsZWN0YXNpcyBpbiB0aGUgbHVuZyBiYXNlcy4gbm8gcGxldXJhbCBlZmZ1c2lvbi4gbm8gc3VzcGljaW91cyBmb2NhbCBib25lIGxlc2lvbi4gQ09OQ0xVU0lPTiAgc3RhYmxlIGRpc2Vhc2UuIGZsdWlkLS1maWxsZWQgc3RvbWFjaCB3aXRoIHNlcm9zYWwgZGlzZWFzZS4gdGhpY2tlbmVkIHB5bG9ydXMgeHh4IHJlcHJlc2VudCBmdXJ0aGVyIGRpc2Vhc2UgYXQgdGhpcyBzaXRlLiBubyBzbWFsbC0tYm93ZWwgb2JzdHJ1Y3Rpb24u"
* code = $loinc#16457501000119102 "CT ABDOMEN AND PELVIS"
* code.text = "CT ABDOMEN AND PELVIS"
* imagingStudy = Reference(4291e805-c774-4915-b08a-b5777c2320c1)
* result = Reference(c8712549-b6cd-45ac-922b-086b8f15f40c)

/*** Imaging study for DiagnosticReport 33e032e3-4f71-40ba-8c13-37e32f928495 ***/
Instance: 4291e805-c774-4915-b08a-b5777c2320c1
InstanceOf: ImagingStudy
Usage: #inline
* identifier.use = #usual
* identifier.type = $v2-0203#ACSN
* identifier.value = "11567660_img"
* status = #available
* reasonCode.text = "Ovarian cancer with peritoneal disease. Faecal vomiting, empty rectum. ?Disease progression ?subacute bowel obstruction"
* subject = Reference(Patient/60f42c69-2a79-4742-960a-2722d5ffcda6)

/*** Observation for DiagnosticReport 33e032e3-4f71-40ba-8c13-37e32f928495 ***/
Instance: c8712549-b6cd-45ac-922b-086b8f15f40c
InstanceOf: Observation
Usage: #inline
* status = #final
* code = $loinc#18782-3 "Radiology Study observation (narrative)"
* code.text = "Findings"
* subject = Reference(Patient/60f42c69-2a79-4742-960a-2722d5ffcda6)
* bodySite = $sct#818983003 "Abdomen+Pelvis:Doc:CT"
* valueString = "STABLE"

/**************************************************************
 * Practitioner for events in Progressive Summary tests
 *************************************************************/
Instance: oB5eYSINJa30
InstanceOf: Practitioner
Usage: #inline
* active = true
* name.family = "Matrey"
* name.given = "Bianca"
* name.prefix = "Dr."
* telecom.system = #email
* telecom.value = "Bianca.Matrey@testhopsital.com"
* address.line = "421 MERRIMACK ST"
* address.city = "METHUEN"
* address.state = "MA"
* address.postalCode = "01844-5865"
* address.country = "US"
* gender = #female
