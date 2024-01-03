package ru.dekabrsky.ws.implementation2

import io.reactivex.disposables.Disposable
import ru.dekabrsky.ws.implementation2.data.model.ChannelType
import ru.dekabrsky.ws.implementation2.data.model.WsEventListener
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryCache @Inject constructor() {

    val eventsList = mutableListOf<WsEventListener<*>>()

    var unsubscribeCount = 0

    var destroyDisposable: Disposable? = null

    fun getEvent(channelType: ChannelType) =
        eventsList.firstOrNull {
            it.channelType.name.equals(channelType.name, ignoreCase = true)
        }

}