package ru.dekabrsky.stats.di

import retrofit2.Retrofit
import ru.dekabrsky.stats.data.api.StatsApi
import javax.inject.Inject
import javax.inject.Provider

class StatsApiProvider@Inject constructor(private val retrofit: Retrofit) :
    Provider<StatsApi> {

    override fun get(): StatsApi = retrofit.create(StatsApi::class.java)

}