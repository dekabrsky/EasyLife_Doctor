package ru.dekabrsky.callersbase_common.presentation.model

import java.io.Serializable

class ChatUiModel (
    val date: String,
    val name: String,
    val lastMessage: String,
    val newMessagesCount: Int,
    //val fullData: CallersBaseEntity,
    //val avatarLink: String
) : Serializable