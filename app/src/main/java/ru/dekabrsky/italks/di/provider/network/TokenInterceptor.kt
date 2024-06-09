package ru.dekabrsky.easylife.di.provider.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.dekabrsky.feature.loginCommon.presentation.model.TokenCache
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val tokenCache: TokenCache
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return tokenCache.accessToken?.let {
            val request = chain
                .request()
                .newBuilder()
                .addHeader("Authorization", "Bearer ${tokenCache.accessToken}")
                .build()
            chain.proceed(request)
        } ?: chain.proceed(chain.request())
    }
}