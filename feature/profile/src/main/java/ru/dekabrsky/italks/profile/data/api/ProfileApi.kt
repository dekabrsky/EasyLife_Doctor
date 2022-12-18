package ru.dekabrsky.italks.profile.data.api

import io.reactivex.Single
import retrofit2.http.POST
import ru.dekabrsky.italks.profile.data.model.response.CodeResponse

interface ProfileApi {
    @POST("/api/v1/codes/generate/parent")
    fun createParent(): Single<CodeResponse>
}