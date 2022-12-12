package ru.dekabrsky.scenarios.di.provider

import retrofit2.Retrofit
import ru.dekabrsky.scenarios.data.api.ScenariosApi
import javax.inject.Inject
import javax.inject.Provider

class ScenariosApiProvider @Inject constructor(private val retrofit: Retrofit) :
    Provider<ScenariosApi> {

    override fun get(): ScenariosApi = retrofit.create(ScenariosApi::class.java)

}