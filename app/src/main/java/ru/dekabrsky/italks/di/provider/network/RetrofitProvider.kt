package ru.dekabrsky.italks.di.provider.network

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.dekabrsky.italks.basic.network.urlProvider.UrlProvider
import javax.inject.Inject
import javax.inject.Provider

class RetrofitProvider @Inject constructor(
    private val client: OkHttpClient,
    private val factory: CallAdapter.Factory,
    private val urlProvider: UrlProvider,
) : Provider<Retrofit> {

    override fun get(): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(urlProvider.provideBaseUrl())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(factory)
            .build()
    }
}