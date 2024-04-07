package ru.dekabrsky.ws.implementation2

import android.util.Log
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import ru.dekabrsky.italks.basic.di.WSOkHttpClient
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WsServiceCore @Inject constructor(
    @WSOkHttpClient private val okHttpClient: OkHttpClient,
    private val cache: WsServiceCache
) {

    private var ws: WebSocket? = null
    private var isOpen: Boolean = false
    private var disposables = CompositeDisposable()

    private fun Disposable.addFullLifeCycle(): Disposable {
        disposables.add(this)
        return this
    }

    fun setupSocket(baseUrl: String, socketEndpoint: String, responseListener: ResponseEventListener) {
        cache.baseUrl = baseUrl
        cache.socketEndpoint = socketEndpoint
        cache.listener = responseListener
    }

    fun createSocket() {
        ws ?: let {
            val url = getRequestUrl(cache.baseUrl, cache.socketEndpoint)
            val request = Request.Builder().url(url).build()
            ws = okHttpClient.newWebSocket(request, getWebSocketListener(cache.listener))
            okHttpClient.dispatcher.executorService.shutdown()
        }
    }

    private fun getRequestUrl(baseUrl: String, socketEndpoint: String): String =
        baseUrl.replace(HTTP_PREFIX, WSS_PREFIX) + socketEndpoint

    @Suppress("TooGenericExceptionCaught")
    private fun getWebSocketListener(listener: ResponseEventListener?) =
        object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                isOpen = true
                listener?.onSocketOpen?.invoke()
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                isOpen = false
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                try {
                    listener?.onMessageResponse?.invoke(text)
                } catch (e: Exception) {
                    Log.i(this::class.simpleName, e.localizedMessage.orEmpty())
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                closeSocket()
                tryReconnect()
            }
        }

    private fun tryReconnect() {
        closeSocket()
        Observable.timer(RECONNECT_DELAY, TimeUnit.SECONDS)
            .subscribeOn(RxSchedulers.io())
            .observeOn(RxSchedulers.main())
            .subscribe { createSocket() }
            .addFullLifeCycle()
    }

    fun doDestroy() {
        cancelAll()
        closeSocket()
    }

    private fun closeSocket() {
        ws?.close(NORMAL_CLOSURE_STATUS, null)
        ws = null
        okHttpClient.dispatcher.cancelAll()
        okHttpClient.dispatcher.executorService.shutdownNow()
    }

    fun sendData(data: String) {
        if (isOpen) {
            Log.i(this::class.simpleName, "Request: $data")
            ws?.send(data) ?: createSocket()
        }
    }

    private fun cancelAll() {
        disposables.dispose()
    }

    companion object {
        private const val HTTP_PREFIX = "https://"
        private const val WSS_PREFIX = "wss://"

        private const val NORMAL_CLOSURE_STATUS = 1000

        private const val RECONNECT_DELAY = 10L
    }

}