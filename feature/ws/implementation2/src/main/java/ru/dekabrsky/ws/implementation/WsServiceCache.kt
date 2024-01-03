package ru.dekabrsky.ws.implementation2

import javax.inject.Inject

class WsServiceCache @Inject constructor() {

    var baseUrl: String = ""
    var socketEndpoint: String = ""
    var listener: ResponseEventListener? = null
}