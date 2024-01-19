package ru.dekabrsky.scenarios.data.api

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import ru.dekabrsky.scenarios.data.model.InvitePatientRequest
import ru.dekabrsky.scenarios.data.model.PatientCodeResponse

interface DoctorPatientsApi {
    @POST("invites/create")
    fun invite(@Body request: InvitePatientRequest): Single<PatientCodeResponse>
}