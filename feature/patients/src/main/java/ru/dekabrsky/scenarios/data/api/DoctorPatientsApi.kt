package ru.dekabrsky.scenarios.data.api

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.dekabrsky.common.domain.model.InvitePatientRequest
import ru.dekabrsky.scenarios.data.model.PatientCodeResponse
import ru.dekabrsky.scenarios.presentation.view.InvitePatientFragment

interface DoctorPatientsApi {
    @POST("invites/create")
    fun invite(@Body request: InvitePatientRequest): Single<PatientCodeResponse>
}