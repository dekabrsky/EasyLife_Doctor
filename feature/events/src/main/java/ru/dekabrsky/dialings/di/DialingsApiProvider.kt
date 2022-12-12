package ru.dekabrsky.dialings.di

import retrofit2.Retrofit
import ru.dekabrsky.dialings.data.api.DialingsApi
import javax.inject.Inject
import javax.inject.Provider

class DialingsApiProvider @Inject constructor(private val retrofit: Retrofit) :
    Provider<DialingsApi> {

    override fun get(): DialingsApi = retrofit.create(DialingsApi::class.java)

}