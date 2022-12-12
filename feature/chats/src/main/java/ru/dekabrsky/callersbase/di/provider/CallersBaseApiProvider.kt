package ru.dekabrsky.callersbase.di.provider

import retrofit2.Retrofit
import ru.dekabrsky.callersbase.data.api.CallersBaseApi
import javax.inject.Inject
import javax.inject.Provider

class CallersBaseApiProvider @Inject constructor(private val retrofit: Retrofit) :
    Provider<CallersBaseApi> {

    override fun get(): CallersBaseApi = retrofit.create(CallersBaseApi::class.java)

}