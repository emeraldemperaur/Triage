package iot.empiaurhouse.triage.network

import io.reactivex.Single
import iot.empiaurhouse.triage.model.*
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ChironAPIEndpoints {

    @Headers("Accept: application/json")
    @GET("http://chiron-cloudapp.herokuapp.com/api/whatsup")
    fun getChironAPIStatus(): Single<List<APIStatus>>

    @Headers("Accept: application/json")
    @GET("http://chiron-cloudapp.herokuapp.com/api/records")
    fun getChironRecords(): Single<List<ChironRecords>>

    @GET("api/patients")
    fun getChironPatients(): Single<List<Patient>>

    @GET("api/patients/l/{lastName}")
    fun getChironPatientsByLastName(@Path("lastName") lastName: String?): Single<List<Patient>>

    @GET("api/patients/l/{firstName}")
    fun getChironPatientsByFirstName(@Path("firstName") firstName: String?): Single<List<Patient>>

    @GET("api/patients/blood/{bloodGroup}")
    fun getChironPatientsByBloodGroup(@Path("bloodGroup") bloodGroup: String?): Single<List<Patient>>


    @GET("api/patients/insurancevendor/{insuranceVendor}")
    fun getChironPatientsByInsuranceVendor(@Path("insuranceVendor") insuranceVendor: String?): Single<List<Patient>>

    @GET("api/patients/insurancevendorid/{insuranceVendorID}")
    fun getChironPatientsByInsuranceVendorID(@Path("insuranceVendorID") insuranceVendorID: String?): Single<List<Patient>>

    @GET("api/patients/birthdate/on/{birthDate}")
    fun getChironPatientsByBirthDate(@Path("birthDate") birthDate: String?): Single<List<Patient>>

    @GET("api/patients/birthdate/before/{birthDate}")
    fun getChironPatientsByBirthDateBefore(@Path("birthDate") birthDate: String?): Single<List<Patient>>

    @GET("api/patients/birthdate/after/{birthDate}")
    fun getChironPatientsByBirthDateAfter(@Path("birthDate") birthDate: String?): Single<List<Patient>>

    @GET("api/patients/birthdate/between/{birthDate}/{birthDate2}")
    fun getChironPatientsByBirthDateBetween(@Path("birthDate") birthDate: String?,
                                            @Path("birthDate2") birthDate2: String?): Single<List<Patient>>

    @GET("http://chiron-cloudapp.herokuapp.com/api/diagnoses")
    fun getChironDiagnoses(): Single<List<Diagnosis>>

    @GET("api/diagnoses/on/{visitDate}")
    fun getChironDiagnosesByVisitDate(@Path("visitDate") visitDate: String?): Single<List<Diagnosis>>

    @GET("api/diagnoses/before/{visitDate}")
    fun getChironDiagnosesByVisitDateBefore(@Path("visitDate") visitDate: String?): Single<List<Diagnosis>>

    @GET("api/diagnoses/after/{visitDate}")
    fun getChironDiagnosesByVisitDateAfter(@Path("visitDate") visitDate: String?): Single<List<Diagnosis>>

    @GET("api/diagnoses/between/{visitDate}/{visitDate2}")
    fun getChironDiagnosesByVisitDateBetween(@Path("visitDate") visitDate: String?, @Path("visitDate2") visitDate2: String?): Single<List<Diagnosis>>

    @GET("api/diagnoses/insurancevendorid/{insuranceVendorID}")
    fun getChironDiagnosesByInsuranceVendorID(@Path("insuranceVendorID") insuranceVendorID: String?): Single<List<Diagnosis>>

    @GET("api/diagnoses/diagnosislevel/{diagnosisLevelName}")
    fun getChironDiagnosesByDiagnosisLevelName(@Path("diagnosisLevelName") diagnosisLevelName: String?): Single<List<Diagnosis>>

    @GET("api/diagnoses/synopsis/{diagnosisSynopsis}")
    fun getChironDiagnosesByDiagnosisSynopsis(@Path("diagnosisSynopsis") diagnosisSynopsis: String?): Single<List<Diagnosis>>

    @GET("api/prescriptions")
    fun getChironPrescriptions(): Single<List<Prescription>>

    @GET("api/prescriptions/name/{prescriptionName}")
    fun getChironPrescriptionsByPrescriptionName(@Path("prescriptionName") prescriptionName: String?): Single<List<Prescription>>

    @GET("api/prescriptions/prescriber/{prescribedBy}")
    fun getChironPrescriptionsByPrescriber(@Path("prescribedBy") prescribedBy: String?): Single<List<Prescription>>

    @GET("prescriptions/prescriberid/{prescriptionPractitionerID}")
    fun getChironPrescriptionsByPrescriberID(@Path("prescriptionPractitionerID") prescriptionPractitionerID: String?): Single<List<Prescription>>

    @GET("api/prescriptions/on/{prescriptionDate}")
    fun getChironPrescriptionsByPrescriptionDate(@Path("prescriptionDate") prescriptionDate: String?): Single<List<Prescription>>

    @GET("api/prescriptions/before/{prescriptionDate}")
    fun getChironPrescriptionsByPrescriptionDateBefore(@Path("prescriptionDate") prescriptionDate: String?): Single<List<Prescription>>

    @GET("api/prescriptions/after/{prescriptionDate}")
    fun getChironPrescriptionsByPrescriptionDateAfter(@Path("prescriptionDate") prescriptionDate: String?): Single<List<Prescription>>

    @GET("api/prescriptions/between/{prescriptionDate}/{prescriptionDate2}")
    fun getChironPrescriptionsByPrescriptionDateBetween(@Path("prescriptionDate") prescriptionDate: String?,
                                                        @Path("prescriptionDate2") prescriptionDate2: String?): Single<List<Prescription>>

    @GET("api/prescriptions/insurancevendorid/{insuranceVendorID}")
    fun getChironPrescriptionsByInsuranceVendorID(@Path("insuranceVendorID") insuranceVendorID: String?): Single<List<Prescription>>

    @GET("api/visits")
    fun getChironVisits(): Single<List<Visit>>

    @GET("api/visits/host/{hostPractitioner}")
    fun getChironVisitsByHostPractitioner(@Path("hostPractitioner") hostPractitioner: String?): Single<List<Visit>>

    @GET("api/visits/hostid/{hostPractitionerID}")
    fun getChironVisitsByHostPractitionerID(@Path("hostPractitionerID") hostPractitionerID: String?): Single<List<Visit>>

    @GET("api/visits/time/{visitTime}")
    fun getChironVisitsByVisitTime(@Path("visitTime") visitTime: String?): Single<List<Visit>>

    @GET("api/visits/description/{visitDescription}")
    fun getChironVisitsByVisitDescription(@Path("visitDescription") visitDescription: String?): Single<List<Visit>>

    @GET("api/visits/on/{visitDate}")
    fun getChironVisitsByVisitDate(@Path("visitDate") visitDate: String?): Single<List<Visit>>

    @GET("api/visits/before/{visitDate}")
    fun getChironVisitsByVisitDateBefore(@Path("visitDate") visitDate: String?): Single<List<Visit>>

    @GET("api/visits/after/{visitDate}")
    fun getChironVisitsByVisitDateAfter(@Path("visitDate") visitDate: String?): Single<List<Visit>>

    @GET("api/visits/between/{visitDate}/{visitDate2}")
    fun getChironVisitsByVisitDateBetween(@Path("visitDate") visitDate: String?,
                                          @Path("visitDate2") visitDate2: String?): Single<List<Visit>>

    @GET("api/visits/insurancevendorid/{insuranceVendorID}")
    fun getChironVisitsByInsuranceVendorID(@Path("insuranceVendorID") insuranceVendorID: String?): Single<List<Visit>>

    @GET("api/practitioners")
    fun getChironPractitioners(): Single<List<Practitioner>>

    @GET("api/practitioners/l/{lastName}")
    fun getChironPractitionersByLastName(@Path("lastName") lastName: String?): Single<List<Practitioner>>

    @GET("api/practitioners/f/{firstName}")
    fun getChironPractitionersByFirstName(@Path("firstName") firstName: String?): Single<List<Practitioner>>

    @GET("api/practitioners/id/{practitionerId}")
    fun getChironPractitionersByPractitionerID(@Path("practitionerId") practitionerId: String?): Single<List<Practitioner>>

    @GET("api/doctors")
    fun getChironDoctors(): Single<List<Doctor>>

    @GET("api/doctors/l/{lastName}")
    fun getChironDoctorsByLastName(@Path("lastName") lastName: String?): Single<List<Doctor>>

    @GET("api/doctors/f/{firstName}")
    fun getChironDoctorsByFirstName(@Path("firstName") firstName: String?): Single<List<Doctor>>

    @GET("api/doctors/id/{practitionerId}")
    fun getChironDoctorsByPractitionerID(@Path("practitionerId") practitionerId: String?): Single<List<Doctor>>

    @GET("api/nursepractitioners")
    fun getChironNursePractitioners(): Single<List<NursePractitioner>>

    @GET("api/nursepractitioners/l/{lastName}")
    fun getChironNursePractitionersByLastName(@Path("lastName") lastName: String?): Single<List<NursePractitioner>>

    @GET("api/nursepractitioners/f/{firstName}")
    fun getChironNursePractitionersByFirstName(@Path("firstName") firstName: String?): Single<List<NursePractitioner>>

    @GET("api/nursepractitioners/id/{practitionerId}")
    fun getChironNursePractitionersByPractitionerID(@Path("practitionerId") practitionerId: String?): Single<List<NursePractitioner>>


    @GET("api/registerednurses")
    fun getChironRegisteredNurses(): Single<List<RegisteredNurse>>

    @GET("api/registerednurses/l/{lastName}")
    fun getChironRegisteredNursesByLastName(@Path("lastName") lastName: String?): Single<List<RegisteredNurse>>

    @GET("api/registerednurses/f/{firstName}")
    fun getChironRegisteredNursesByFirstName(@Path("firstName") firstName: String?): Single<List<RegisteredNurse>>

    @GET("api/registerednurses/id/{practitionerId}")
    fun getChironRegisteredNursesByPractitionerID(@Path("practitionerId") practitionerId: String?): Single<List<RegisteredNurse>>

    @GET("api/pharmaceuticals")
    fun getChironPharmaceuticals(): Single<List<Pharmaceuticals>>

    @GET("api/pharmaceuticals/brand/{brandName}")
    fun getChironPharmaceuticalsByBrandName(@Path("brandName") brandName: String?): Single<List<Pharmaceuticals>>

    @GET("api/pharmaceuticals/generic/{genericName}")
    fun getChironPharmaceuticalsByGenericName(@Path("genericName") genericName: String?): Single<List<Pharmaceuticals>>

    @GET("api/pharmaceuticals/chemical/{chemicalName}")
    fun getChironPharmaceuticalsByChemicalName(@Path("chemicalName") chemicalName: String?): Single<List<Pharmaceuticals>>

    @GET("api/pharmaceuticals/manufacturer/{manufacturerName}")
    fun getChironPharmaceuticalsByManufactureNme(@Path("manufacturerName") manufacturerName: String?): Single<List<Pharmaceuticals>>

    @GET("api/pharmaceuticals/manufacturedate/{manufactureDate}")
    fun getChironPharmaceuticalsByManufactureDate(@Path("manufactureDate") manufactureDate: String?): Single<List<Pharmaceuticals>>

    @GET("api/pharmaceuticals/manufacturedbefore/{manufactureDate}")
    fun getChironPharmaceuticalsManufactureDateBefore(@Path("manufactureDate") manufactureDate: String?): Single<List<Pharmaceuticals>>

    @GET("api/pharmaceuticals/manufacturedafter/{manufactureDate}")
    fun getChironPharmaceuticalsManufactureDateAfter(@Path("manufactureDate") manufactureDate: String?): Single<List<Pharmaceuticals>>

    @GET("api/pharmaceuticals/manufacturedbetween/{manufactureDate}/{manufactureDate2}")
    fun getChironPharmaceuticalsManufactureDateBetween(@Path("manufactureDate") manufactureDate: String?,
                                                       @Path("manufactureDate2") manufactureDate2: String?): Single<List<Pharmaceuticals>>

    @GET("api/pharmaceuticals/expirydate/{expiryDate}")
    fun getChironPharmaceuticalsByExpiryDate(@Path("expiryDate") expiryDate: String?): Single<List<Pharmaceuticals>>

    @GET("api/pharmaceuticals/expiredbefore/{expiryDate}")
    fun getChironPharmaceuticalsByExpiryDateBefore(@Path("expiryDate") expiryDate: String?): Single<List<Pharmaceuticals>>

    @GET("api/pharmaceuticals/expiredafter/{expiryDate}")
    fun getChironPharmaceuticalsByExpiryDateAfter(@Path("expiryDate") expiryDate: String?): Single<List<Pharmaceuticals>>

    @GET("api/pharmaceuticals/expiredbetween/{expiryDate}/{expiryDate2}")
    fun getChironPharmaceuticalsByExpiryDateBetween(@Path("expiryDate") expiryDate: String?,
                                                    @Path("expiryDate2") expiryDate2: String?): Single<List<Pharmaceuticals>>


}