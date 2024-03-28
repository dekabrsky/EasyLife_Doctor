package ru.dekabrsky.scenarios.data.api

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.dekabrsky.callersbase.data.model.UserIdNameResponse
import ru.dekabrsky.callersbase.data.model.UsersListIdNameResponse
import ru.dekabrsky.scenarios.data.model.InvitePatientRequest
import ru.dekabrsky.scenarios.data.model.PatientCodeResponse

interface DoctorPatientsApi {
    @POST("invites/create")
    fun invite(@Body request: InvitePatientRequest): Single<PatientCodeResponse>

    @GET("users/patients")
    fun getPatients(): Single<UsersListIdNameResponse>
}