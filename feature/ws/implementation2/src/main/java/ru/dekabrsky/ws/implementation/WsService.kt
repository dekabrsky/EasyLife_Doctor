package ru.dekabrsky.ws.implementation2

import com.google.gson.Gson
import io.reactivex.Observable
import ru.dekabrsky.easylife.basic.di.ServerEndpoint
import ru.dekabrsky.easylife.basic.rx.RxSchedulers
import ru.dekabrsky.ws.implementation2.data.mapper.WsRequestMapper
import ru.dekabrsky.ws.implementation2.data.mapper.WsResponseMapper
import ru.dekabrsky.ws.implementation2.data.model.ChannelType
import ru.dekabrsky.ws.implementation2.data.model.MethodType
import ru.dekabrsky.ws.implementation2.data.model.WsEventListener
import ru.dekabrsky.ws.implementation2.data.model.request.BaseWsTypeRequest
import ru.dekabrsky.ws.implementation2.data.response.BaseWsDataResponse
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WsService @Inject constructor(
    private val wsCore: WsServiceCore,
    private val requestMapper: WsRequestMapper,
    private val responseParser: WsResponseMapper,
    private val cache: RepositoryCache,
    @ServerEndpoint val baseEndpoint: String
) {

    init {
        wsCore.setupSocket(baseEndpoint, MESSAGES_ENDPOINT, getResponseEventListener())
        wsCore.createSocket()
    }

    private fun getResponseEventListener() =
        object : ResponseEventListener {
            override val onSocketOpen = ::sendConnect
            override val onMessageResponse = ::parseResponse
        }

    private fun parseResponse(response: String) {
        responseParser.parse(
            responseString = if (response.last() == RS_SYMBOL) response.dropLast(1) else response
        )
    }

    private fun subscribeOnDestroy() {
        cache.destroyDisposable =
            Observable.timer(DESTROY_DELAY, TimeUnit.MILLISECONDS)
                .repeatUntil { cache.unsubscribeCount == 0 }
                .subscribeOn(RxSchedulers.io())
                .observeOn(RxSchedulers.main())
                .doOnTerminate { cache.destroyDisposable?.dispose() }
                .subscribe { wsCore.doDestroy() }
    }

    private fun addSubscriber(listener: WsEventListener<*>) {
        cache.eventsList.add(listener)
    }

    private fun removeSubscriber(channelType: ChannelType) {
        val event = cache.getEvent(channelType)
        event?.let {
            cache.unsubscribeCount++
            cache.eventsList.remove(event)

            subscribeOnDestroy()
        }
    }

    private fun sendConnect() {
        requestMapper.mapConnect()
            .sendData()
    }

    fun sendRequest(channelType: ChannelType, request: List<Any>) {
        requestMapper.mapContentRequest(channelType, request).sendData()
    }

    private fun Any.sendData() {
        wsCore.sendData(Gson().toJson(this) + RS_SYMBOL)
    }

    fun <T : Any> subscribe(channelType: ChannelType, responseType: Class<*>): Observable<T> {
        return Observable
            .create<T> { emitter ->
                val wsEventListener = object : WsEventListener<T> {
                    override val channelType = channelType
                    override val responseType = responseType

                    override val onEvent: (BaseWsDataResponse<T>) -> Unit = { baseResponse ->
                        baseResponse.arguments.let { it.forEach { emitter.onNext(it) } }
                    }
                }
                addSubscriber(wsEventListener)
            }
            .doOnDispose {
                removeSubscriber(channelType)
            }
    }

    fun sendClose() = BaseWsTypeRequest(MethodType.UNSUBSCRIBE.id).sendData()

    companion object {
        private const val MESSAGES_ENDPOINT = "ws/messages"

        private const val DESTROY_DELAY = 100L

        private const val RS_SYMBOL = ''
    }

}