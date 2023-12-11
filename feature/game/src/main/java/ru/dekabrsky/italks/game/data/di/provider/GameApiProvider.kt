package ru.dekabrsky.italks.game.data.di.provider

import retrofit2.Retrofit
import ru.dekabrsky.italks.game.data.api.GameApi
import javax.inject.Inject
import javax.inject.Provider

class GameApiProvider @Inject constructor(private val retrofit: Retrofit) :
    Provider<GameApi> {

    override fun get(): GameApi = retrofit.create(GameApi::class.java)

}