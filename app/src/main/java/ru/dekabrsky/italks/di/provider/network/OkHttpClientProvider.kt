package ru.dekabrsky.italks.di.provider.network

import okhttp3.*
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider


class OkHttpClientProvider : Provider<OkHttpClient> {

    private var cookieJar: CookieJar? = null
    private var tokenInterceptor: TokenInterceptor? = null

    @Inject
    fun inject(cookieJar: CookieJar) {
        this.cookieJar = cookieJar
    }

    @Inject
    fun inject(interceptor: TokenInterceptor) {
        this.tokenInterceptor = interceptor
    }

    override fun get(): OkHttpClient = prepareClient().build()

    private fun prepareClient(): OkHttpClient.Builder {

        val builder = OkHttpClient.Builder()
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        builder.readTimeout(TIME_OUT, TimeUnit.SECONDS)
        builder.writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        builder.followSslRedirects(true)
        cookieJar?.let { builder.cookieJar(cookieJar!!) }
        tokenInterceptor?.let { builder.addInterceptor(tokenInterceptor!!) }

        return builder
    }

    companion object {
        private const val TIME_OUT = 500L
    }
}