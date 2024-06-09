package ru.dekabrsky.easylife.di.provider.network

import ru.dekabrsky.easylife.basic.di.ServerEndpoint
import ru.dekabrsky.easylife.basic.network.urlProvider.UrlProvider
import javax.inject.Inject

class AppUrlProvider @Inject constructor(@ServerEndpoint val baseEndpoint: String) : UrlProvider {

    override fun provideServerUrl() = baseEndpoint

    override fun provideServerUrlPostfix() = "api/v1/"
}