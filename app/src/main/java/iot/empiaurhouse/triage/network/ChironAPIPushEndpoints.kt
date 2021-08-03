package iot.empiaurhouse.triage.network

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChironAPIPushEndpoints {

    @POST("api/patient")
    suspend fun pushChironPatientData(@Body patientRequestBody: RequestBody): Response<ResponseBody>

    @POST("api/deletepatient")
    suspend fun deleteChironPatientData(@Body patientRequestBody: RequestBody): Response<ResponseBody>

    @POST("api/diagnosis")
    suspend fun pushChironDiagnosisData(@Body diagnosisRequestBody: RequestBody): Response<ResponseBody>

    @POST("api/deletediagnosis")
    suspend fun deleteChironDiagnosisData(@Body diagnosisRequestBody: RequestBody): Response<ResponseBody>

    @POST("api/practitioner")
    suspend fun pushChironPractitionerData(@Body practitionerRequestBody: RequestBody): Response<ResponseBody>

    @POST("api/deletepractitioner")
    suspend fun deleteChironPractitionerData(@Body practitionerRequestBody: RequestBody): Response<ResponseBody>

    @POST("api/doctor")
    suspend fun pushChironMedicalDoctorData(@Body doctorRequestBody: RequestBody): Response<ResponseBody>

    @POST("api/deletedoctor")
    suspend fun deleteChironMedicalDoctorData(@Body doctorRequestBody: RequestBody): Response<ResponseBody>

    @POST("api/registerednurse")
    suspend fun pushChironRegisteredNurseData(@Body registerednurseRequestBody: RequestBody): Response<ResponseBody>

    @POST("api/deleteregisterednurse")
    suspend fun deleteChironRegisteredNurseData(@Body registerednurseRequestBody: RequestBody): Response<ResponseBody>

    @POST("api/nursepractitioner")
    suspend fun pushChironNursePractitionerData(@Body nursepractitionerRequestBody: RequestBody): Response<ResponseBody>

    @POST("api/deletenursepractitioner")
    suspend fun deleteChironNursePractitionerData(@Body nursepractitionerRequestBody: RequestBody): Response<ResponseBody>

    @POST("api/pharmaceutical")
    suspend fun pushChironPharmaceuticalData(@Body pharmaceuticalRequestBody: RequestBody): Response<ResponseBody>

    @POST("api/deletepharmaceutical")
    suspend fun deleteChironPharmaceuticalData(@Body pharmaceuticalRequestBody: RequestBody): Response<ResponseBody>



}