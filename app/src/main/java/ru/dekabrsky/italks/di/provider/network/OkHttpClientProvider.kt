package ru.dekabrsky.italks.di.provider.network

import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider


class OkHttpClientProvider @Inject constructor() : Provider<OkHttpClient> {

    companion object {
        private const val TIME_OUT = 500L
    }

    override fun get(): OkHttpClient {

        val builder = OkHttpClient.Builder()
        builder.connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
        builder.readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
        builder.writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
        builder.followSslRedirects(true)
        builder.connectionSpecs(listOf(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS))
        return builder.build()
    }

}