Alias: $loinc = http://loinc.org
Alias: $sct = http://snomed.info/sct
Alias: $us-core-documentreference-category = http://hl7.org/fhir/us/core/CodeSystem/us-core-documentreference-category
Alias: $v3-ActCode = http://terminology.hl7.org/CodeSystem/v3-ActCode
Alias: $v3-ParticipationType = http://terminology.hl7.org/CodeSystem/v3-ParticipationType
Alias: $rxnorm = http://www.nlm.nih.gov/research/umls/rxnorm
Alias: $observation-category = http://terminology.hl7.org/CodeSystem/observation-category
Alias: $v2-0203 = http://terminology.hl7.org/CodeSystem/v2-0203

Instance: hvOaPTfNxUoH
InstanceOf: Patient
Usage: #inline
* name.use = #official
* name.family = "TEST_PS"
* name.given = "MultiReport"
* gender = #male
* birthDate = "1975-03-19"

/**************************************************************
 * DocumentReference before the cut date
 * AI summary of this report is present in 'Brief history'
 *************************************************************/
Instance: yHHdytiYXiys
InstanceOf: DocumentReference
Usage: #inline
* status = #current
* type = $loinc#34108-1 "Outpatient Note"
* type.text = "Outpatient note"
* category = $loinc#34108-1 "Outpatient Note"
* category.text = "Outpatient note"
* subject = Reference(hvOaPTfNxUoH)
* date = "1999-01-28T10:00:00.000Z"
* content.attachment.id = "q8oyuPJUpIzo"
* content.attachment.contentType = #text/plain
* content.attachment.data = "dGhpcyBpcyBhIHRlc3Q="
* content.attachment.title = "Urology New Patient History and Physical"
* content.attachment.creation = "1999-01-28T10:00:00.000Z"
* author = Reference(9ETWWv8Nlc79) "Dr. Charles Burton"

Instance: sg9unska0Q21
InstanceOf: DocumentReference
Usage: #inline
* identifier.use = #usual
* identifier.type = $v2-0203#ACSN
* identifier.value = "765756F9DE6BA392"
* status = #current
* type = $loinc#LP29684-5 "Radiology Note"
* type.text = "Radiology report"
* category = $loinc#LP29684-5 "Radiology Note"
* category.text = "Radiology report"
* subject = Reference(hvOaPTfNxUoH)
* date = "1999-02-24T10:00:00.000Z"
* content.attachment.id = "IPIyrK9hVRe4"
* content.attachment.contentType = #text/plain
* content.attachment.data = "SElTVE9SWTogIDxicj5NZXRhc3RhdGljIHByb3N0YXRpYyBhZGVub2NhcmNpbm9tYSAgPGJyPjxicj5FWEFNSU5BVElPTjogIDxicj5DVCBjaGVzdCwgYWJkb21lbiBhbmQgcGVsdmlzICA8YnI+PGJyPlRFQ0hOSVFVRTogIDxicj5NdWx0aWRldGVjdG9yIGF4aWFsIGRhdGEgc2V0IHRocm91Z2ggdGhlIGNoZXN0LCBhYmRvbWVuLCBhbmQgcGVsdmlzIHBvc3QgSVYgY29udHJhc3QgKDEwMCBtTCBPbW5pcGFxdWUgMzUwKS4gU2FnaXR0YWwgYW5kIGNvcm9uYWwgMi1EIHJlZm9ybWF0cyBmcm9tIHRoZSBkYXRhIHNldC4gIDxicj48YnI+SU5UUkFWRU5PVVMgQ09OVFJBU1QgQURNSU5JU1RSQVRJT046ICA8YnI+MTAwIG1MIE9tbmlwYXF1ZSAzNTAgIDxicj48YnI+RE9TRSBSRVBPUlQ6ICA8YnI+VG90YWwgRExQIChTdXJ2ZXkrSGVsaWNhbCk6IDIwNTAuNzUgbUd5KmNtICA8YnI+PGJyPkNPTVBBUklTT046ICA8YnI+Q1QgKipEQVRFW0p1biAyNSAyMDE3XSwgTVJJICoqREFURVtKdW4gMjIgMjAxNl0sIENUICoqREFURVtOb3YgMzAgMjAxNV0sICoqREFURVtNYXkgMDQgMjAxNV0gIDxicj48YnI+RklORElOR1M6ICA8YnI+PGJyPkNUIFRIT1JBWDogIDxicj4xLiBUaG9yYWNpYyBpbmxldCwgaGVhcnQgc2l6ZSwgY2VudHJhbCBwdWxtb25hcnkgYXJ0ZXJpZXMgYXBwZWFyIG5vcm1hbC4gQ2FsY2lmaWMgcGxhcXVlIG5vdGVkIGluIHRoZSB0cmFuc3ZlcnNlIGFvcnRpYyBhcmNoLiAgPGJyPjIuIE5vIHBhdGhvbG9naWMgaW50cmF0aG9yYWNpYywgYXhpbGxhcnkgb3Igc3VwcmFjbGF2aWN1bGFyIGx5bXBoYWRlbm9wYXRoeS4gIDxicj4zLiBObyBzdXNwaWNpb3VzIHB1bG1vbmFyeSBub2R1bGVzLCBpbmZpbHRyYXRlcywgb3IgcGxldXJhbCBlZmZ1c2lvbnMuIFRyYWNoZWEgYW5kIG1haW5zdGVtIGJyb25jaGkgYXBwZWFyIG5vcm1hbC4gIDxicj40LiBUaGVyZSBpcyBhIHNtYWxsIHNsaWRpbmcgaGlhdGFsIGhlcm5pYS4gIDxicj48YnI+Q1QgQUJET01FTi9QRUxWSVM6ICA8YnI+MS4gU3RhYmxlIHJpZ2h0IGhlcGF0aWMgbG9iZSAyLjEgY20gY3lzdCBhbmQgaGV0ZXJvZ2VuZW91c2x5IGVuaGFuY2luZyAxOCBtbSBmbGFzaCBmaWxsIGhlbWFuZ2lvbWEuICA8YnI+Mi4gU3BsZWVuLCBwYW5jcmVhcywgZ2FsbGJsYWRkZXIsIGludHJhaGVwYXRpYyBhbmQgZXh0cmFoZXBhdGljIGJpbGUgZHVjdHMsIHBvcnRhbCB2ZW5vdXMgc3lzdGVtLCBhbmQgYWRyZW5hbCBnbGFuZHMgYXBwZWFyIG5vcm1hbC4gIDxicj4zLiBLaWRuZXlzIGZ1bmN0aW9uIHN5bW1ldHJpY2FsbHkuIFBhcmVuY2h5bWEsIGNvbGxlY3Rpbmcgc3lzdGVtcywgYW5kIHVyaW5hcnkgYmxhZGRlciBhcHBlYXIgbm9ybWFsLiBEeXN0cm9waGljIGNhbGNpZmljYXRpb25zIG5vdGVkIHdpdGhpbiBhIHNocnVua2VuIHByb3N0YXRlLiAgPGJyPjQuIFRoZSBwcmV2aW91c2x5IGRvY3VtZW50ZWQgcGVyaXBoZXJhbGx5IGVuaGFuY2luZyBjZW50cmFsbHkgaHlwb2F0dGVudWF0aW5nIDIzIHggMTkgbW0gcmlnaHQgaW50ZXJuYWwgaWxpYWMgbHltcGggbm9kZSBoYXMgZ3Jvd24gYW5kIG5vdyBtZWFzdXJlcyA0NSB4IDM0IG1tLiBBIDYgbW0gc2hvcnQgYXhpcyBwb3J0YWNhdmFsIGx5bXBoIG5vZGUgaXMgc3RhYmxlLiAgPGJyPjUuIEdJIHRyYWN0IGlzIG5vbm9ic3RydWN0ZWQgYW5kIG5vbmlzY2hlbWljLiBCaWxhdGVyYWwgZmF0LWNvbnRhaW5pbmcgaW5ndWluYWwgaGVybmlhcyBhcmUgcHJlc2VudC4gIDxicj42LiBBb3J0YSwgSVZDLCBhbmQgYnJhbmNoIHZlc3NlbHMgYXJlIHBhdGVudCBhbmQgb2Ygbm9ybWFsIGNhbGliZXIuIEFuIGFjY2Vzc29yeSBsZWZ0IHJlbmFsIGFydGVyeSBpcyBwcmVzZW50LiAgPGJyPjcuIFNtYWxsIGx1Y2VuY3kgaW4gdGhlIHBvc3RlcmlvciByaWdodCBpbmZlcmlvciBwdWJpYyByYW11cyBpcyB1bmNoYW5nZWQuIFNjbGVyb3RpYyBmb2NpIGluIHRoZUlEKioqIGxlZnQgY2xhdmljbGUgYXJlIHN0YWJsZSBhbmQgbGlrZWx5IHJlcHJlc2VudCBib25lIGlzbGFuZHMuICA8YnI+PGJyPkNPTkNMVVNJT046ICA8YnI+MS4gSW50ZXJ2YWwgZ3Jvd3RoIG9mIGEgbWV0YXN0YXRpYyByaWdodCBpbnRlcm5hbCBpbGlhYyBseW1waCBub2RlIGFzIGRldGFpbGVkIGFib3ZlLiAgPGJyPjIuIFN0YWJsZSBsdWNlbmN5IGluIHRoZSByaWdodCBwb3N0ZXJpb3IgaW5mZXJpb3IgcHViaWMgcmFtdXMuICA8YnI+My4gU3RhYmxlIHJpZ2h0IGhlcGF0aWMgbG9iZSBjeXN0IGFuZCBmbGFzaCBmaWxsIGhlbWFuZ2lvbWEuICA8YnI+PGJyPkVsZWN0cm9uaWNhbGx5IFNpZ25lZCBCeTogKipbTkFNRSBYWFhdLiAg"
* content.attachment.title = "IMAGING"
* content.attachment.creation = "1999-02-24T10:00:00.000Z"

Instance: 73YtPCdJOPig
InstanceOf: DocumentReference
Usage: #inline
* status = #current
* type = $loinc#LP29684-5 "Radiology Note"
* type.text = "Radiology report 2"
* category = $loinc#LP29684-5 "Radiology Note"
* category.text = "Radiology report"
* subject = Reference(hvOaPTfNxUoH)
* date = "1999-07-15T10:31:51.000Z"
* content.attachment.id = "zammUCcU205B"
* content.attachment.contentType = #text/plain
* content.attachment.data = "SU1BR0lORwpISVNUT1JZOgpNZXRhc3RhdGljIHByb3N0YXRpYyBhZGVub2NhcmNpbm9tYQoKRVhBTUlOQVRJT046CkNUIGNoZXN0LCBhYmRvbWVuIGFuZCBwZWx2aXMKClRFQ0hOSVFVRToKTXVsdGlkZXRlY3RvciBheGlhbCBkYXRhIHNldCB0aHJvdWdoIHRoZSBjaGVzdCwgYWJkb21lbiwgYW5kIHBlbHZpcyBwb3N0IElWIGNvbnRyYXN0ICgxMDAgbUwgT21uaXBhcXVlIDM1MCkuIFNhZ2l0dGFsIGFuZCBjb3JvbmFsIDItRCByZWZvcm1hdHMgZnJvbSB0aGUgZGF0YSBzZXQuCgpJTlRSQVZFTk9VUyBDT05UUkFTVCBBRE1JTklTVFJBVElPTjoKMTAwIG1MIE9tbmlwYXF1ZSAzNTAKCkRPU0UgUkVQT1JUOgpUb3RhbCBETFAgKFN1cnZleStIZWxpY2FsKTogMjA1MC43NSBtR3kqY20KCkNPTVBBUklTT046CgpGSU5ESU5HUzoKCkNUIFRIT1JBWDoKMS4gVGhvcmFjaWMgaW5sZXQsIGhlYXJ0IHNpemUsIGNlbnRyYWwgcHVsbW9uYXJ5IGFydGVyaWVzIGFwcGVhciBub3JtYWwuIENhbGNpZmljIHBsYXF1ZSBub3RlZCBpbiB0aGUgdHJhbnN2ZXJzZSBhb3J0aWMgYXJjaC4KMi4gTm8gcGF0aG9sb2dpYyBpbnRyYXRob3JhY2ljLCBheGlsbGFyeSBvciBzdXByYWNsYXZpY3VsYXIgbHltcGhhZGVub3BhdGh5LgozLiBObyBzdXNwaWNpb3VzIHB1bG1vbmFyeSBub2R1bGVzLCBpbmZpbHRyYXRlcywgb3IgcGxldXJhbCBlZmZ1c2lvbnMuIFRyYWNoZWEgYW5kIG1haW5zdGVtIGJyb25jaGkgYXBwZWFyIG5vcm1hbC4KNC4gVGhlcmUgaXMgYSBzbWFsbCBzbGlkaW5nIGhpYXRhbCBoZXJuaWEuCgpDVCBBQkRPTUVOL1BFTFZJUzoKMS4gU3RhYmxlIHJpZ2h0IGhlcGF0aWMgbG9iZSAyLjEgY20gY3lzdCBhbmQgaGV0ZXJvZ2VuZW91c2x5IGVuaGFuY2luZyAxOCBtbSBmbGFzaCBmaWxsIGhlbWFuZ2lvbWEuCjIuIFNwbGVlbiwgcGFuY3JlYXMsIGdhbGxibGFkZGVyLCBpbnRyYWhlcGF0aWMgYW5kIGV4dHJhaGVwYXRpYyBiaWxlIGR1Y3RzLCBwb3J0YWwgdmVub3VzIHN5c3RlbSwgYW5kIGFkcmVuYWwgZ2xhbmRzIGFwcGVhciBub3JtYWwuCjMuIEtpZG5leXMgZnVuY3Rpb24gc3ltbWV0cmljYWxseS4gUGFyZW5jaHltYSwgY29sbGVjdGluZyBzeXN0ZW1zLCBhbmQgdXJpbmFyeSBibGFkZGVyIGFwcGVhciBub3JtYWwuIER5c3Ryb3BoaWMgY2FsY2lmaWNhdGlvbnMgbm90ZWQgd2l0aGluIGEgc2hydW5rZW4gcHJvc3RhdGUuCjQuIFRoZSBwcmV2aW91c2x5IGRvY3VtZW50ZWQgcGVyaXBoZXJhbGx5IGVuaGFuY2luZyBjZW50cmFsbHkgaHlwb2F0dGVudWF0aW5nIDIzIHggMTkgbW0gcmlnaHQgaW50ZXJuYWwgaWxpYWMgbHltcGggbm9kZSBoYXMgZ3Jvd24gYW5kIG5vdyBtZWFzdXJlcyA0NSB4IDM0IG1tLiBBIDYgbW0gc2hvcnQgYXhpcyBwb3J0YWNhdmFsIGx5bXBoIG5vZGUgaXMgc3RhYmxlLgo1LiBHSSB0cmFjdCBpcyBub25vYnN0cnVjdGVkIGFuZCBub25pc2NoZW1pYy4gQmlsYXRlcmFsIGZhdC1jb250YWluaW5nIGluZ3VpbmFsIGhlcm5pYXMgYXJlIHByZXNlbnQuCjYuIEFvcnRhLCBJVkMsIGFuZCBicmFuY2ggdmVzc2VscyBhcmUgcGF0ZW50IGFuZCBvZiBub3JtYWwgY2FsaWJlci4gQW4gYWNjZXNzb3J5IGxlZnQgcmVuYWwgYXJ0ZXJ5IGlzIHByZXNlbnQuCjcuIFNtYWxsIGx1Y2VuY3kgaW4gdGhlIHBvc3RlcmlvciByaWdodCBpbmZlcmlvciBwdWJpYyByYW11cyBpcyB1bmNoYW5nZWQuIFNjbGVyb3RpYyBmb2NpIGluIHRoZUlEKioqIGxlZnQgY2xhdmljbGUgYXJlIHN0YWJsZSBhbmQgbGlrZWx5IHJlcHJlc2VudCBib25lIGlzbGFuZHMuCgpDT05DTFVTSU9OOgoxLiBJbnRlcnZhbCBncm93dGggb2YgYSBtZXRhc3RhdGljIHJpZ2h0IGludGVybmFsIGlsaWFjIGx5bXBoIG5vZGUgYXMgZGV0YWlsZWQgYWJvdmUuCjIuIFN0YWJsZSBsdWNlbmN5IGluIHRoZSByaWdodCBwb3N0ZXJpb3IgaW5mZXJpb3IgcHViaWMgcmFtdXMuCjMuIFN0YWJsZSByaWdodCBoZXBhdGljIGxvYmUgY3lzdCBhbmQgZmxhc2ggZmlsbCBoZW1hbmdpb21hLgoKRWxlY3Ryb25pY2FsbHkgU2lnbmVkIEJ5OiBEciBYIFo="
* content.attachment.title = "Test report"
* content.attachment.creation = "1999-07-15T10:31:51.000Z"
* author = Reference(9ETWWv8Nlc79) "Dr. Charles Burton"

/**************************************************************
 * DocumentReference refers to an Encounter (cut date)
 * AI summary of this report is present in 'Brief history'
 *************************************************************/
Instance: BkQgCxSKvK1M
InstanceOf: DocumentReference
Usage: #inline
* status = #current
* type.coding[0] = $loinc#34117-2 "History and physical note - test1"
* category = $us-core-documentreference-category#clinical-note "Clinical Note"
* subject = Reference(hvOaPTfNxUoH)
* date = "2000-01-09T13:31:51.000Z"
* author = Reference(9ETWWv8Nlc79) "Dr. Charles Burton"
* content.attachment.contentType = #text/plain
* content.attachment.language = #en-US
* content.attachment.data = "Q2xpbmljYWwgYW5kIFRyZWF0bWVudCBTdW1tYXJ5XG5cbk1ldCBjb2xvbiBhZGVub2NhcmNpbm9hbSB3aXRoIHNvbGl0YXJ5IG9tZW50YWwgbWV0IC0gYWxsIHZpc2libGUgZGlzZWFzZSByZXNlY3RlZCBNU1MgUkFTL1JBRiBXVCBzL3AgcHJvcGh5bGFjdGljIEhJUEVDIG9uIDEzLzMvMTggKG9uIHRyaWFsKVxuXG5cblxuSGlzdG9yeSwgRXhhbWluYXRpb24gYW5kIEludmVzdGlnYXRpb25zXG5cblVuZGVyd2VudCBwcm9waHlsYWN0aWMgSElQRUMgKHRyaWFsKSBvbiAxMy8zLzE4IER1ZSAjNiBYZWxveCAjNCBhbmQgNSBkb3NlIGRlbGF5IDIgd2Vla3MgZHVlIHRvIGxvdyBQTHQgYW5kIGxvdyBBTkMsIGdpdmVuIDJkYXlzIHMvYyBHQ1NGIChwcmlvciB0byAjNCBidXQgbm90ICM1KSBIaXN0bzogRElBR05PU0lTIChBKSBMZWZ0IHBlbHZpYyBwZXJpdG9uZXVtOyBleGNpc2lvbiBiaW9wc3k6IEZhdCBuZWNyb3NpcywgaW5mbGFtbWF0aW9uLCBmaWJyb3NpcyBhbmQgZm9yZWlnbiBib2R5LXR5cGUgbXVsdGludWNsZWF0ZSBnaWFudCBjZWxsIHJlYWN0aW9uOyAtIG5lZ2F0aXZlIGZvciBtYWxpZ25hbmN5LiAoQikgT21lbnR1bTsgb21lbnRlY3RvbXk6IE5vbi1zcGVjaWZpYyByZWFjdGl2ZSBjaGFuZ2VzOyAtIHR3byBiZW5pZ24gbHltcGggbm9kZXMgKDAvMiksIC0gbmVnYXRpdmUgZm9yIG1hbGlnbmFuY3kuIEJsZHMgdG9kYXkgQU5DIDAuOTYgUEx0IDEyMWsgQ0VBIDYuNCBmcm9tIDQgQ1QgVEFQIDkvNy8xODogQ29uY2x1c2lvbiBTaW5jZSAxMyBBcHIgMjAxOCwgMS4gU3RhdHVzIHBvc3QgaGlnaCBhbnRlcmlvciByZXNlY3Rpb24gd2l0aCBubyBldmlkZW5jZSBvZiBsb2NhbCByZWN1cnJlbmNlIG9yIGludGVydmFsIGNoYW5nZS4gMi4gVGhlIHB1bG1vbmFyeSBub2R1bGVzLCBzdXNwaWNpb3VzIG9mIG1ldGFzdGFzZXMsIGFyZSBzbWFsbGVyLiBUaGUgcmlnaHQgbG93ZXIgbG9iZSBub2R1bGUgaGFzIG5lYXJseSByZXNvbHZlZC4gMy4gVGhlIGNpcmN1bWZlcmVudGlhbCBtdXJhbCB0aGlja2VuaW5nIGluIHRoZSByZWN0dW0gaXMgbGVzcyBwcm9taW5lbnQgYW5kIHJlbWFpbnMgaW5kZXRlcm1pbmF0ZS4gNC4gTm8gbmV3IGRpc3RhbnQgbWV0YXN0YXNpcy4gU1VCSkVDVElWRSBUb2xlcmF0ZWQgd2VsbCBHMSBuZXVyb3BhdGh5IGluIGZpbmdlcnMgYW5kIHRvZXMgRWF0IGFuZCBCTyByZWd1bGFybHksIHdlbGwgTm8gUFIgYmxlZWQgRHJ5IHNraW4sIG5vIFBQRSwgbm90IHVzaW5nIG1vaXN0dXJpc2VycyBPQkVKQ1RJVkUgRUNPRyAxIC0gbGltcHMgYnV0IHRoaXMgaXMgYmFzZWxpZW4gZnJvbSBwcmV2aW91cyBhY2NpZGVudCBIdCAxNzFjbSBXdCA2MC45a2cgZnJvbSA2MC44a2cgZnJvbSA1Ny44a2cgZnJvbSA1Ni44a2cgZnJvbSA1NS40a2cgZnJvbSA1NWtnIGZyb20gNTQuNGtnIEgtUzFTMiBMIC0gTm8gY3JlcHMgQSAtIFNvZnQgTm9udGVuZGVyIFQgMzYuNCBIUiAxMzEvODYgU3BPMiAxMDAlIG9uIFJBIEhSIDcxIEhvbWUgQlAgbW9uaXRvcmluZyBTQlAgMTIwLTE1MG1tSEcgb24gYW1sb2RpcGluZSAyLjVtZyBPTSBJbXA6IE1ldCBjb2xvbiBhZGVub2NhcmNpbm9hbSB3aXRoIHNvbGl0YXJ5IG9tZW50YWwgbWV0IC0gYWxsIHZpc2libGUgZGlzZWFzZSByZXNlY3RlZCBNU1MgUkFTL1JBRiBXVCBzL3AgcHJvcGh5bGFjdGljIEhJUEVDIG9uIDEzLzMvMTggKG9uIHRyaWFsKSBQT3N0LW9wIENUIC0gaW5jcmVhc2UgaW4gb25lIGx1bmcgbW9kdWxlID9tZXQgLSByZXNwb25kaW5nIHRvIFhlbG94XG5cblxuTWFuYWdlbWVudCBGb3IgVGhpcyBWaXNpdFxuXG5QcmNvZWVkIHdpdGggIzYgWGVsb3ggb24gMy85LzE4IEZCQyBPQSBUQ1UgbWUgNCB3ZWVrcyBCbGRzIE9BIENUIFRBUCBiZWZvcmUgVENVIENvbnR0IGFtbG9kaXBpbmUgYXQgMi41bWcgT00sIGFkdmlzZWQgaG9tZSBCUCBtb25pdG9yaW5nLCBLSVYgZnVydGhlciBpbmNyZWFzZSB0byA1bWcgT00gQWR2aXNlZCB0byByZXR1cm4gZWFybHkgaWYgZmV2ZXIvdW53ZWxsIHdoaWxlIG9uIGNoZW1vIFMvVCBUb2xlcmF0aW5nIGNoZW1vIHdlbGwsIHdlIHdvdWxkIGNvbnRpbnVlIFhlbG94IExBc3QgQ1Qgc2hvd3MgbHVuZyBsZXNpb25zIGFyZSBzbWFsbGVyLCBsaWtlbHkgbHVuZyBtZXRzIGFuZCBzdGFnZSA0IGRpc2Vhc2UgV2Ugd291bGQgcmV2aWV3IENUIHJlc3VsdHMgYWdhaW4gd2l0aCByZXBlYXQgQ1QgYWZ0ZXIgdGhpcyBjeWNsZSBUaGV5IHVuZGVyc3RhbmQsIGFncmVlYWJsZSBBbGwgcXVlc3Rpb25zIGFuc3dlcmVkXG5cblxuTWFuYWdlbWVudCBGb3IgVGhpcyBWaXNpdFxuXG5QcmNvZWVkIHdpdGggIzYgWGVsb3ggb24gMy85LzE4IEZCQyBPQSBUQ1UgbWUgNCB3ZWVrcyBCbGRzIE9BIENUIFRBUCBiZWZvcmUgVENVIC0gcG9zdHBvbmUgY3VycmVudCBDVCBhcHB0IHRvIDE3LzkvMTggQ29udHQgYW1sb2RpcGluZSBhdCAyLjVtZyBPTSwgYWR2aXNlZCBob21lIEJQIG1vbml0b3JpbmcsIEtJViBmdXJ0aGVyIGluY3JlYXNlIHRvIDVtZyBPTSBBZHZpc2VkIHRvIHJldHVybiBlYXJseSBpZiBmZXZlci91bndlbGwgd2hpbGUgb24gY2hlbW8gUy9UIFRvbGVyYXRpbmcgY2hlbW8gd2VsbCwgd2Ugd291bGQgY29udGludWUgWGVsb3ggTEFzdCBDVCBzaG93cyBsdW5nIGxlc2lvbnMgYXJlIHNtYWxsZXIsIGxpa2VseSBsdW5nIG1ldHMgYW5kIHN0YWdlIDQgZGlzZWFzZSBXZSB3b3VsZCByZXZpZXcgQ1QgcmVzdWx0cyBhZ2FpbiB3aXRoIHJlcGVhdCBDVCBhZnRlciB0aGlzIGN5Y2xlIFRoZXkgdW5kZXJzdGFuZCwgYWdyZWVhYmxlIEFsbCBxdWVzdGlvbnMgYW5zd2VyZWQ="
* content.attachment.title = "Test attachment 1"
* context.encounter = Reference(QZbm476ZuUew)

/**************************************************************
 * Encounter (cut date)
 *************************************************************/
Instance: QZbm476ZuUew
InstanceOf: Encounter
Usage: #inline
* status = #finished
* class = $v3-ActCode#AMB
* type = $sct#162673000 "General examination of patient (procedure)"
* type.text = "General examination of patient (procedure)"
* subject = Reference(hvOaPTfNxUoH)
* participant.type = $v3-ParticipationType#PPRF "primary performer"
* participant.type.text = "primary performer"
* participant.period.start = "2000-01-09T12:31:51.000Z"
* participant.period.end = "2000-01-09T12:51:51.000Z"
* participant.individual = Reference(9ETWWv8Nlc79) "Dr. Charles Burton"
* period.start = "2000-01-09T12:31:51.000Z"
* period.end = "2000-01-09T12:51:51.000Z"

/**************************************************************
 * Practitioner for events in Progressive Summary tests
 *************************************************************/
Instance: 9ETWWv8Nlc79
InstanceOf: Practitioner
Usage: #inline
* active = true
* name.family = "Burton"
* name.given = "Charles"
* name.prefix = "Dr."
* telecom.system = #email
* telecom.value = "Charles.Burton@testhopsital2.com"
* address.line = "23 Hospital Street"
* address.city = "London"
* address.state = "MA"
* address.postalCode = "46326"
* address.country = "UK"
* gender = #male
