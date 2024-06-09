package ru.dekabrsky.easylife.game.data.api

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.dekabrsky.easylife.game.data.model.GameConfigResponse
import ru.dekabrsky.easylife.game.data.model.GameProgressRequest
import ru.dekabrsky.easylife.game.data.model.GameProgressResponse

interface GameApi {
    @GET("games")
    fun getGamesConfigs(): Single<List<GameConfigResponse>>

    @POST("games/pass")
    fun postGameProgress(@Body requestBody: GameProgressRequest): Single<GameProgressResponse>
}