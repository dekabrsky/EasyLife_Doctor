package ru.dekabrsky.italks.di.provider.network

import ru.dekabrsky.italks.basic.di.ServerEndpoint
import ru.dekabrsky.italks.basic.network.urlProvider.UrlProvider
import javax.inject.Inject

class AppUrlProvider @Inject constructor(@ServerEndpoint val baseEndpoint: String) : UrlProvider {

    override fun provideServerUrl() = baseEndpoint

    override fun provideServerUrlPostfix() = "api/v1/"
}