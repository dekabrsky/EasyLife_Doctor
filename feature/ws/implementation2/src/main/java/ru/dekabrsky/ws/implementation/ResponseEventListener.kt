package ru.dekabrsky.ws.implementation2

interface ResponseEventListener {

    val onSocketOpen: () -> Unit
    val onMessageResponse: (String) -> Unit

}