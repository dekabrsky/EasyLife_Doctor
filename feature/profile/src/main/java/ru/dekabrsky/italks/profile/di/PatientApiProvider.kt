package ru.dekabrsky.easylife.profile.di

import retrofit2.Retrofit
import ru.dekabrsky.easylife.profile.data.api.ProfileApi
import javax.inject.Inject
import javax.inject.Provider

class PatientApiProvider @Inject constructor(private val retrofit: Retrofit) :
    Provider<ProfileApi> {

    override fun get(): ProfileApi = retrofit.create(ProfileApi::class.java)

}