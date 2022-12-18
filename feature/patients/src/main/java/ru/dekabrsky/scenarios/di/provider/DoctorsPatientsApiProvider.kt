package ru.dekabrsky.scenarios.di.provider

import retrofit2.Retrofit
import ru.dekabrsky.scenarios.data.api.DoctorPatientsApi
import javax.inject.Inject
import javax.inject.Provider

class DoctorsPatientsApiProvider @Inject constructor(private val retrofit: Retrofit) :
    Provider<DoctorPatientsApi> {

    override fun get(): DoctorPatientsApi = retrofit.create(DoctorPatientsApi::class.java)

}