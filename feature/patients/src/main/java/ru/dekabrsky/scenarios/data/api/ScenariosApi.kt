package ru.dekabrsky.scenarios.data.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.dekabrsky.italks.basic.network.utils.Direction
import ru.dekabrsky.italks.basic.network.utils.SortVariants
import ru.dekabrsky.scenarios.data.model.ScenarioPageableResponse

interface ScenariosApi {
    @GET("scenario")
    fun getScenarios(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20,
        @Query("direction") direction: String = Direction.ASC.name,
        @Query("sortBy") sortBy: String = SortVariants.NAME.name
    ): Observable<ScenarioPageableResponse>
}