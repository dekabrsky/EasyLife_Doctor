package ru.dekabrsky.ws.implementation2.data.mapper

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ru.dekabrsky.ws.implementation2.RepositoryCache
import ru.dekabrsky.ws.implementation2.data.model.MethodType
import ru.dekabrsky.ws.implementation2.data.model.WsEventListener
import ru.dekabrsky.ws.implementation2.data.response.BaseWsChannelResponse
import ru.dekabrsky.ws.implementation2.data.response.BaseWsDataResponse
import ru.dekabrsky.ws.implementation2.data.response.BaseWsSimpleResponse
import javax.inject.Inject

class WsResponseMapper @Inject constructor(
    private val cache: RepositoryCache
) {

    private val gson by lazy {
        GsonBuilder()
            .setLenient()
            .create()
    }

    fun parse(responseString: String) {
        Log.d("WS_RESPONSE", responseString)
        val responseSimple = Gson().fromJson(responseString, BaseWsSimpleResponse::class.java)
        when (responseSimple.type) {
            MethodType.RESULT.id -> {
                parseSubscribeResponse(responseString)
            }
            MethodType.UNSUBSCRIBE.id -> {
                cache.unsubscribeCount--
            }
        }
    }

    @Suppress("UseIfInsteadOfWhen")
    private fun parseSubscribeResponse(response: String) {
        val channelResponse = gson.fromJson(response, BaseWsChannelResponse::class.java)
        val channelType = channelResponse.target

        cache.getEvent(channelType)?.let { event ->
            fireResponseEvent(response, event)
        }
    }

    private fun <T> fireResponseEvent(response: String, eventListener: WsEventListener<T>) {
        val responseType = TypeExtractionUtils.getType(BaseWsDataResponse::class.java, eventListener.responseType)
        val parsedResponse = gson.fromJson<BaseWsDataResponse<T>>(response, responseType)

        eventListener.onEvent(parsedResponse)
    }

}