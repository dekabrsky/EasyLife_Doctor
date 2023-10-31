package ru.dekabrsky.scenarios.data.api

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import ru.dekabrsky.scenarios.data.model.PatientCodeResponse

interface DoctorPatientsApi {

    @POST("codes/generate/patient")
    fun generatePatient(): Single<PatientCodeResponse>

    @POST("codes/generate/child")
    fun generateChild(): Single<PatientCodeResponse>

    @GET("users/patients")
    fun getPatients(): Completable

}