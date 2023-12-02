package ru.dekabrsky.italks.game.data.api

import io.reactivex.Single
import retrofit2.http.GET
import ru.dekabrsky.italks.game.data.model.GameConfigResponse

interface GameApi {
    @GET("games")
    fun getGamesConfigs(): Single<List<GameConfigResponse>>
}